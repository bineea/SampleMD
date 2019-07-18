package my.sample.manager.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import my.sample.common.pub.MyManagerException;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.RoleResource;
import my.sample.dao.primary.entity.AppResource.MenuType;
import my.sample.dao.primary.repo.jpa.AppResourceRepo;
import my.sample.dao.primary.repo.jpa.RoleResourceRepo;
import my.sample.dao.repo.Spe.AppResourcePageSpe;
import my.sample.model.MyFinals;
import my.sample.model.acl.ResourceTreeModel;

@Service   
public class ResourceManagerImpl implements ResourceManager {

	@Autowired
	private AppResourceRepo resourceRepo;
	@Autowired
	private RoleResourceRepo roleResourceRepo;
	
	@Override
	public List<AppResource> findRootMenu() {
		List<AppResource> resourceList = resourceRepo.findByMenuType(MenuType.COLUMN).stream()
														.filter(resource -> !StringUtils.hasText(resource.getMenuId()))
														.sorted((res1, res2) -> {
															return res1.getList().compareTo(res2.getList());
														})
														.collect(Collectors.toList());
		return resourceList;
	}

	@Override
	public List<AppResource> listByParent(String menuId) {
		
		List<AppResource> resourceList = resourceRepo.findByMenuId(menuId);
		return resourceList;
	}

	@Override
	public List<ResourceTreeModel> getResourceTree(String resourceId) {

		List<ResourceTreeModel> treeList = new ArrayList<>();
		if(MyFinals.jsTreeRootReq.equals(resourceId))
		{
			findRootMenu().stream().forEach(resource -> {
				ResourceTreeModel rootModel = ResourceTreeModel.initModel(resource);
				rootModel.setChildren(true);
				treeList.add(rootModel);
			});
		}
		else
		{
			Optional<AppResource> resourceOptional = resourceRepo.findById(resourceId);
			resourceOptional.ifPresent(resource -> {
				ResourceTreeModel rootModel = ResourceTreeModel.initModel(resource);
				List<ResourceTreeModel> childrenlList = new ArrayList<>();
				listByParent(resourceId).stream().forEach(res -> {
					ResourceTreeModel model = new ResourceTreeModel();
					model = ResourceTreeModel.initModel(res);
					if(res.getMenuType() != MenuType.COLUMN)
						model.setType("link");
					else
						model.setChildren(true);
					childrenlList.add(model);
				});
				rootModel.setChildren(childrenlList);
				treeList.add(rootModel);
			});
		}
		return treeList;
	}

	@Override
	public Page<AppResource> pageQuery(AppResourcePageSpe pageSpe) {
		return resourceRepo.findAll(pageSpe.handleSpecification(), pageSpe.getPageRequest());
	}

	@Override
	public AppResource findById(String id) {
		Assert.hasText(id, "id参数不能为空");
		return resourceRepo.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void deleteById(String id) throws MyManagerException {
		Assert.hasText(id, "id参数不能为空");
		Optional<AppResource> resourceOptional = resourceRepo.findById(id);
		if(!resourceOptional.isPresent()) return;
		List<AppResource> resourceList = resourceRepo.findByMenuId(id);
		if(resourceList != null && resourceList.size() > 0)
			throw new MyManagerException("该资源菜单下存在子资源菜单，请先删除子资源菜单");
		List<RoleResource> roleResourceList = roleResourceRepo.findByResourceId(id);
		if(roleResourceList != null && roleResourceList.size() > 0)
			throw new MyManagerException("该资源菜单已经被角色引用，请先修改角色的权限配置");
		resourceRepo.delete(resourceOptional.get());
	}

	@Transactional
	@Override
	public AppResource add(AppResource resource) throws MyManagerException {
		resourceValid(resource);
		if(resource.getRequestMethod() != null && StringUtils.hasText(resource.getUrl()))
		{
			AppResource res = resourceRepo.findByUrlMethod(resource.getUrl(), resource.getRequestMethod());
			if(res != null) throw new MyManagerException("该URI的资源已经存在:"+resource.getUrl()+"---"+resource.getRequestMethod().name());
		}
		if(StringUtils.hasText(resource.getMenuId()))
		{
			Optional<AppResource> resOptinal = resourceRepo.findById(resource.getMenuId());
			if(!resOptinal.isPresent()) throw new MyManagerException("指定的父资源不存在");
		}
		resourceRepo.save(resource);
		return resource;
	}

	@Transactional
	@Override
	public AppResource update(AppResource resource) throws MyManagerException {
		resourceValid(resource);
		Optional<AppResource> resOptinal = resourceRepo.findById(resource.getId());
		if(!resOptinal.isPresent()) throw new MyManagerException("指定的资源不存在:" + resource.getUrl());
		AppResource res = resOptinal.get();
		res.setName(resource.getName());
		res.setList(resource.getList());
		res.setLogoStyle(resource.getLogoStyle());
		resourceRepo.save(res);
		return res;
	}
	
	private void resourceValid(AppResource resource) throws MyManagerException {
		if(!StringUtils.hasText(resource.getName()))
			throw new MyManagerException("资源名称不能为空");
		if(resource.getMenuType() == null)
			throw new MyManagerException("资源类型不能为空");
		if(resource.getList() == null)
			throw new MyManagerException("资源序号不能为空");
		if(resource.getMenuType() != MenuType.COLUMN && resource.getRequestMethod() == null)
			throw new MyManagerException("资源请求方式不能为空");
		if(resource.getMenuType() != MenuType.COLUMN && !StringUtils.hasText(resource.getUrl()))
			throw new MyManagerException("资源url不能为空");
	}
}
