package my.sample.web.acl;

import java.io.IOException;
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

import my.sample.common.pub.MyManagerException;
import my.sample.common.tools.JsonTools;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.repo.Spe.RolePageSpe;
import my.sample.manager.acl.RoleManager;
import my.sample.model.acl.ResourceTreeModel;
import my.sample.web.AbstractController;

@Controller
@RequestMapping(value = "acl/role")
public class RoleController extends AbstractController {
	
	@Autowired
	private RoleManager manager;

	private static final String prefix = "acl/role/";
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manageGet(@ModelAttribute("queryModel") RolePageSpe pageSpe, Model model) {
		return prefix + "manage";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public String managePost(@ModelAttribute("queryModel") RolePageSpe pageSpe, Model model) {
		Page<Role> page = manager.pageQuery(pageSpe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "queryResult";
	}
	
	@RequestMapping(value = "/configRole/{id}", method = RequestMethod.GET)
	public String configGet(@PathVariable("id") String id, HttpServletRequest request, Model model) throws IOException {
		List<ResourceTreeModel> treeList = manager.getAllResource(id);
		model.addAttribute("treeJson", JsonTools.writeValueAsString(treeList));
		model.addAttribute("roleId", id);
		return prefix + "conf";
	}
	
	@RequestMapping(value = "/configRole", method = RequestMethod.POST)
	public void configPost(
			@RequestParam("roleId") String roleId,
			@RequestParam("resourceIds") String[] resourceIds,
			Model model, HttpServletResponse response) throws IOException, MyManagerException {
		manager.configRole(roleId, resourceIds);
		addSuccess(response, "成功配置资源");
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public String addRoleGet(@ModelAttribute("addModel") Role role, Model model) {
		return prefix + "add";
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public String addRolePost(@ModelAttribute("addModel") Role role, Model model,
			HttpServletResponse response) throws MyManagerException, IOException {
		manager.add(role);
		model.addAttribute("data", role);
		addSuccess(response, "成功添加角色");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/updateRole/{id}", method = RequestMethod.GET)
	public String updateRoleGet(@PathVariable("id") String id, Model model) {
		Role role = manager.findById(id);
		model.addAttribute("editModel", role);
		return prefix + "edit";
	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public String updateRolePost(@ModelAttribute("editModel") Role role, Model model, HttpServletResponse response) 
			throws MyManagerException, IOException {
		manager.update(role);
		model.addAttribute("data", role);
		addSuccess(response, "成功更新角色信息");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/deleteRole/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") String id, Model model, HttpServletResponse response) 
			throws MyManagerException, IOException {
		manager.deleteById(id);
		addSuccess(response, "成功删除角色");
		return prefix + "result";
	}
}
