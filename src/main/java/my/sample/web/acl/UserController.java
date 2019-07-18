package my.sample.web.acl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.ServletContextResource;

import my.sample.common.pub.MyManagerException;
import my.sample.common.tools.HttpResponseHelper;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.primary.entity.User;
import my.sample.dao.primary.entity.User.UserStatus;
import my.sample.dao.repo.Spe.UserPageSpe;
import my.sample.manager.acl.RoleManager;
import my.sample.manager.acl.UserManager;
import my.sample.model.acl.UserInfoModel;
import my.sample.web.AbstractController;

@Controller
@RequestMapping(value = "acl/user")
public class UserController extends AbstractController {

	@Autowired
	private UserManager manager;
	@Autowired
	private RoleManager roleManager;
	private static final String prefix = "acl/user/";
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manageGet(@ModelAttribute("queryModel") UserPageSpe spe, Model model) {
		return prefix + "manage";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public String managePost(@ModelAttribute("queryModel") UserPageSpe spe, Model model) {
		Page<User> page = manager.pageQuery(spe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "queryResult";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUserGet(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model) {
		List<Role> roles = roleManager.getAll();
		model.addAttribute("roles", roles);
		return prefix + "add";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUserPost(@ModelAttribute("addModel") UserInfoModel userInfoModel, Model model, 
			HttpServletRequest request, HttpServletResponse response) throws MyManagerException, IOException {
		User user = manager.add(userInfoModel);
		model.addAttribute("data", user);
		addSuccess(response, "成功添加角色！");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/updateUserRole/{id}", method = RequestMethod.GET)
	public String editRoleGet(@PathVariable("id") String id, Model model) {
		User user = manager.findById(id);
		List<Role> roles = roleManager.getAll();
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		return prefix + "editRole";
	}
	
	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	public String editRolePost(
			@RequestParam("userId") String userId,
			@RequestParam("roleId") String roleId,
			Model model, HttpServletResponse response) throws MyManagerException, IOException {
		User user = manager.updateRole(userId, roleId);
		model.addAttribute("data", user);
		addSuccess(response, "成功变更账号："+ user.getLoginName() +"角色");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/status/{id}/{status}", method = RequestMethod.POST)
	public String editStatus(@PathVariable("id") String id, @PathVariable("status") UserStatus status,
			Model model, HttpServletResponse response) 
			throws MyManagerException, IOException {
		User user = manager.updateStatus(id, status);
		model.addAttribute("data", user);
		addSuccess(response, "成功变更账号："+user.getLoginName()+"状态");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/showProfilePic/{userId}")
	public void showProfilePic(@PathVariable("userId") String userId, HttpServletResponse response) 
			throws IOException, SQLException {
		User user = manager.findById(userId);
		if(user.getProfilePicture() == null) {
			File profilePic = new ServletContextResource(ContextLoader.getCurrentWebApplicationContext().getServletContext(), "/assets/img/profile/default-user.png").getFile();
			HttpResponseHelper.showPicture(new FileInputStream(profilePic), response);
		} else {
			HttpResponseHelper.showPicture(user.getProfilePicture().getBinaryStream(), response);
		}
	}
}
