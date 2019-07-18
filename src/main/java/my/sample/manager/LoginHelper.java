package my.sample.manager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import my.sample.common.tools.JsonTools;
import my.sample.common.tools.WebTools;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.User;
import my.sample.model.MyFinals;
import my.sample.model.MySession;
import my.sample.model.acl.MenuModel;

public class LoginHelper {

	private static Logger logger = LoggerFactory.getLogger(LoginHelper.class);

	public static boolean alreadyLogin(HttpServletRequest request) {
		User loginUser = getLoginUser(request);
		return (loginUser != null && StringUtils.hasText(loginUser.getId()));
	}

	public static User getLoginUser(HttpServletRequest request) {
		try {
			String loginUser = (String) WebUtils.getSessionAttribute(request, MySession.LOGIN_USER);
			if (StringUtils.hasText(loginUser))
				return JsonTools.json2Object(loginUser, User.class);
		} catch (IOException e) {
			logger.error("获取登陆用户信息异常，异常信息：{}", e);
		}
		return null;
	}
	
	public static List<MenuModel> getCurrentRoleMenu(HttpServletRequest request)
	{
		String str = (String) WebUtils.getSessionAttribute(request, MySession.ROLE_MENU);
		try
		{
			return str == null ? new ArrayList<MenuModel>() : JsonTools.json2List(str, MenuModel.class);
		}
		catch (IOException e)
		{
			logger.error("", e);
			return new ArrayList<MenuModel>();
		}
	}
	
	public static AppResource getCurrentResource(HttpServletRequest request)
	{
		String str = (String) WebUtils.getSessionAttribute(request, MySession.CURRENT_RESOURCE);
		try
		{
			if (str != null) return JsonTools.json2Object(str, AppResource.class);
		}
		catch (IOException e)
		{
			logger.error("", e);
		}
		return null;
	}
	
	public static void addLoginSession(HttpServletRequest request, User user, List<MenuModel> menus) {
		
		Assert.notNull(user, "账号信息不能为空");
		String ip = WebTools.getIpAddress(request);
		if ("0:0:0:0:0:0:0:1".equals(ip)) ip = "127.0.0.1";
		
		HttpSession session = request.getSession(false);
		session.invalidate();
		WebUtils.setSessionAttribute(request, MySession.LOGIN_IP, ip);
		WebUtils.setSessionAttribute(request, MySession.LOGIN_USER, user.toJson());
		WebUtils.setSessionAttribute(request, MySession.LOGIN_ROLE, user.getRole().getId());
		WebUtils.setSessionAttribute(request, MySession.LOGIN_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		try {
			String menuJson = JsonTools.writeValueAsString(menus);
			WebUtils.setSessionAttribute(request, MySession.ROLE_MENU, menuJson);
			logger.debug("role menu:{}", menuJson);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public static void addLoginCookie(String accountNumber, HttpServletResponse response ) {
		
		byte[] accountByte = null;
		try {
			accountByte = accountNumber.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String accountStr = Base64.getEncoder().encodeToString(accountByte);
		Cookie account = new Cookie(MyFinals.COOKIE_USER, accountStr);
		account.setMaxAge(7*24*60*60);
		response.addCookie(account);
	
	}
}
