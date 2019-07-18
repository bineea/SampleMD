package my.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import my.sample.common.pub.MyManagerException;
import my.sample.model.MySession;

/**
 * @author Administrator
 * 无权限跳转、退出
 */
@Controller
public class AclController extends AbstractController {

	@RequestMapping(value = "/common/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		WebUtils.setSessionAttribute(request, MySession.LOGIN_USER, null);
		return "redirect:/app/index?myMenuId=index";
	}
	
	@RequestMapping("/common/acl/noright")
	public String noRight(@RequestParam("method") RequestMethod method, HttpServletRequest request, Model model,
			HttpServletResponse response) throws MyManagerException
	{
		String reason = (String) WebUtils.getSessionAttribute(request, MySession.ERROR_MSG);
		if (!StringUtils.hasText(reason)) reason = "原因未知";
		if (method == RequestMethod.POST) throw new MyManagerException(reason);
		model.addAttribute("reason", reason);
		logger.debug("没有权限,{}", reason);
		return "acl/noright";
	}
}
