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
import my.sample.common.tools.HttpResponseHelper;
import my.sample.common.tools.JsonTools;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.AppResource.MenuType;
import my.sample.dao.repo.Spe.AppResourcePageSpe;
import my.sample.manager.acl.ResourceManager;
import my.sample.model.acl.ResourceTreeModel;
import my.sample.web.AbstractController;

@Controller
@RequestMapping("acl/resource")
public class ResourceController extends AbstractController {
	
	@Autowired
	private ResourceManager manager;
	private final String prefix = "acl/resource/";

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manageGet(@ModelAttribute("queryModel") AppResourcePageSpe pageSpe, Model model) throws IOException {
		model.addAttribute("rootMenu", JsonTools.writeValueAsString(manager.findRootMenu()));
		return prefix + "manage";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public String managePost(@ModelAttribute("queryModel") AppResourcePageSpe pageSpe, Model model) {
		Page<AppResource> page = manager.pageQuery(pageSpe);
		model.addAttribute("queryResult", page.getContent());
		model.addAttribute("currentPage", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		return prefix + "queryResult";
	}
	
	@RequestMapping(value = "/loadResource", method = RequestMethod.GET)
	public void loadResource(@RequestParam(value = "id") String id,HttpServletRequest request, 
			HttpServletResponse response) throws IOException
	{
		List<ResourceTreeModel> treeList = manager.getResourceTree(id);
		HttpResponseHelper.responseJson(JsonTools.writeValueAsString(treeList), response);
	}
	
	@RequestMapping(value = "/addResource", method = RequestMethod.GET)
	public String addResourceGet(
			@RequestParam(value = "menuType", required = true) MenuType menuType,
			@RequestParam(value = "menuId", required = false) String menuId,
			@RequestParam(value = "parentName", required = false) String parentName,
			@RequestParam(value = "isRootMenu", required = true) boolean isRootMenu,
			@ModelAttribute("addModel") AppResource resource,
			Model model) {
		model.addAttribute("menuType", menuType);
		model.addAttribute("menuId", menuId);
		model.addAttribute("parentName", parentName);
		model.addAttribute("isRootMenu", isRootMenu);
		return prefix + "add";
	}
	
	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	public String addResourcePost(@ModelAttribute("addModel") AppResource resource, Model model,
			HttpServletRequest request, HttpServletResponse response) throws IOException, MyManagerException {
		AppResource res = manager.add(resource);
		model.addAttribute("data", res);
		addSuccess(response, "添加资源成功");
		return prefix + "result";
	}
	
	@RequestMapping(value = "updateResource/{id}", method = RequestMethod.GET)
	public String updateResourceGet(@PathVariable("id") String id,  Model model) {
		model.addAttribute("editModel", manager.findById(id));
		return prefix + "edit";
	}
	
	@RequestMapping(value = "updateResource", method = RequestMethod.POST)
	public String updateResourcePost(@ModelAttribute("editModel") AppResource resource, Model model,
			HttpServletRequest request, HttpServletResponse response) throws IOException, MyManagerException {
		AppResource res = manager.update(resource);
		model.addAttribute("data", res);
		addSuccess(response, "更新资源成功");
		return prefix + "result";
	}
	
	@RequestMapping(value = "/deleteResource/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") String id, HttpServletRequest request, Model model, 
			HttpServletResponse response) throws IOException, MyManagerException {
		manager.deleteById(id);
		addSuccess(response, "删除资源成功");
		return prefix + "result";
	}
}
