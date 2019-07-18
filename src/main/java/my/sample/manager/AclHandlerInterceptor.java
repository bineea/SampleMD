package my.sample.manager;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import my.sample.common.tools.WebTools;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.User;
import my.sample.dao.primary.repo.jpa.UserRepo;
import my.sample.dao.secondary.repo.jpa.TestUserRepo;
import my.sample.manager.acl.AclManagerImpl;
import my.sample.model.MySession;

@Service
public class AclHandlerInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String loginUri;
	private String noRightUri;
	
	@Autowired
	private AclManagerImpl aclManager;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TestUserRepo testUserRepo;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("-------------->>查询user表：");
		userRepo.findAll().forEach(u -> System.out.println(u.toJson()));
		logger.info("-------------->>查询test_user表：");
		testUserRepo.findAll().forEach(u -> System.out.println(u.toJson()));
		
		String uri = WebTools.getUri(request, false);
		if(uri.startsWith("/")) uri = uri.substring(1);
		//忽略登陆请求
		if(uri.startsWith("app/common/login")) return true;
		//忽略blog请求
		if(uri.startsWith("app/sample")) return true;
		//跳转到登陆页
		RequestMethod method = RequestMethod.valueOf(request.getMethod());
		if(!LoginHelper.alreadyLogin(request)) {
			request.getSession().invalidate();
			WebUtils.setSessionAttribute(request, MySession.ERROR_MSG, "会话已失效，请重新登陆");
			if(method == RequestMethod.GET)
				WebUtils.setSessionAttribute(request, MySession.LAST_URI, WebTools.getUri(request, true));
			response.sendRedirect(getLoginUri(request));
			return false;
		}
		//校验权限
		User loginUser = LoginHelper.getLoginUser(request);
		AppResource resource = aclManager.findByUrlMethod(uri, method);
		handleMenu(request, resource, method);
		String result = aclManager.checkAuth(loginUser.getRole(), resource);
		if (!StringUtils.hasText(result)) return true;
		// 权限校验失败
		logger.debug(result);
		WebUtils.setSessionAttribute(request, MySession.ERROR_MSG, result);
		if (method == RequestMethod.GET)
			WebUtils.setSessionAttribute(request, MySession.LAST_URI, WebTools.getUri(request, true));
		response.sendRedirect(getNorightUri(request) + "?method=" + method);
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		// session中存放的是字符串，此处转换成model的形式返回
		if (modelAndView == null) return;
		User loginUser = LoginHelper.getLoginUser(request);
		if (loginUser != null) modelAndView.addObject(MySession.LOGIN_USER, loginUser);
		//modelAndView.addObject(MySession.ROLE_MENU, LoginHelper.getCurrentRoleMenu(request));
		modelAndView.addObject(MySession.ROLE_MENU, (String) WebUtils.getSessionAttribute(request, MySession.ROLE_MENU));
		AppResource res = LoginHelper.getCurrentResource(request);
		if (res != null) modelAndView.addObject(MySession.CURRENT_RESOURCE, res);
	}
	
	private void handleMenu(HttpServletRequest request, AppResource resource, RequestMethod method) {
		
		if(resource == null) {
			WebUtils.setSessionAttribute(request, MySession.CURRENT_RESOURCE, null);
			return;
		}
		if(method != RequestMethod.GET) return;
		WebUtils.setSessionAttribute(request, MySession.CURRENT_RESOURCE, resource.toJson());
		String menuId = request.getParameter("myMenuId");
		if(StringUtils.hasText(menuId)) {
			WebUtils.setSessionAttribute(request, MySession.SESSION_MENU_ID, menuId);
			return;
		}
		WebUtils.setSessionAttribute(request, MySession.SESSION_MENU_ID, resource.getId());
	}
	
	private String getLoginUri(HttpServletRequest request) {
		
		if(!StringUtils.hasText(loginUri))
			loginUri = request.getContextPath() + "/app/common/login";
		int ran = new Random().nextInt(10);
		return loginUri + "?ran=" + ran;
	}
	
	private String getNorightUri(HttpServletRequest request) {
		
		if (noRightUri == null) noRightUri = request.getContextPath() + "/app/common/acl/noright";
		return noRightUri;
	}
}
