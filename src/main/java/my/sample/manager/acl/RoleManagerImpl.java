package my.sample.manager.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import my.sample.common.pub.MyManagerException;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.primary.entity.RoleResource;
import my.sample.dao.primary.entity.AppResource.MenuType;
import my.sample.dao.primary.repo.jpa.AppResourceRepo;
import my.sample.dao.primary.repo.jpa.RoleRepo;
import my.sample.dao.primary.repo.jpa.RoleResourceRepo;
import my.sample.dao.repo.Spe.RolePageSpe;
import my.sample.manager.AbstractManager;
import my.sample.model.acl.ResourceTreeModel;
import my.sample.model.acl.TreeStateModel;

@Service
public class RoleManagerImpl extends AbstractManager implements RoleManager {

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private AppResourceRepo resourceRepo;
	@Autowired
	private RoleResourceRepo roleResourceRepo;
	@Autowired
	private RoleResourceMapHolder roleResourceHolder;

	@Override
	public Page<Role> pageQuery(RolePageSpe spe) {
		return roleRepo.findAll(spe.handleSpecification(), spe.getPageRequest());
	}

	@Override
	public List<ResourceTreeModel> getAllResource(String roleId) {
		List<ResourceTreeModel> allMenu = resourceRepo.findByMenuType(MenuType.COLUMN).stream()
				.filter(resource -> !StringUtils.hasText(resource.getMenuId())).sorted((res1, res2) -> {
					return res1.getList().compareTo(res2.getList());
				}).map(resource -> {
					ResourceTreeModel rootModel = ResourceTreeModel.initModel(resource);
					return getResourceTreeModel(resource, roleId, rootModel);
				}).collect(Collectors.toList());
		return allMenu;
	}
	
	private ResourceTreeModel getResourceTreeModel(AppResource resource, String roleId, ResourceTreeModel parentModel) {
		List<AppResource> resourceList = resourceRepo.findByMenuId(resource.getId());
		if (resourceList == null || resourceList.isEmpty()) {
			parentModel.setChildren(false);
			RoleResource roleResource = roleResourceRepo.findByRoleResourceId(resource.getId(), roleId);
			if(roleResource != null)
				parentModel.setState(new TreeStateModel().setSelected(true));
		}
		else {
			List<ResourceTreeModel> modelList = new ArrayList<>();
			resourceList.stream().forEach(res -> {
				ResourceTreeModel model = ResourceTreeModel.initModel(res);
				if(res.getMenuType() != MenuType.COLUMN)
					model.setType("link");
				modelList.add(getResourceTreeModel(res, roleId, model));
			});
			parentModel.setChildren(modelList);
		}
		return parentModel;
	}

	@Override
	@Transactional
	public void configRole(String roleId, String[] resourceIds) throws MyManagerException {
		Assert.hasText(roleId, "roleId不能为空");
		Assert.notEmpty(resourceIds, "resourceIds不能为空");
		Role role = roleRepo.findById(roleId).orElse(null);
		if(role == null)
			throw new MyManagerException("角色不存在，可能已经被其他用户删除");
		if(role.isSystem())
			throw new MyManagerException("不能配置系统内置角色的权限");
		roleResourceRepo.deleteByRoleId(roleId);
		for(String resourceId : resourceIds) {
			AppResource resource = resourceRepo.findById(resourceId).orElse(null);
			if(resource == null) continue;
			if(roleResourceRepo.findByRoleResourceId(resourceId, roleId) != null) continue;
			RoleResource roleResource = new RoleResource(role, resource);
			roleResourceRepo.save(roleResource);
		}
		// 清空角色拥有权限的缓存
		roleResourceHolder.removeRoleMenus(roleId);
	}

	@Override
	public List<Role> getAll() {
		return roleRepo.findAll();
	}

	@Override
	@Transactional
	public void add(Role role) throws MyManagerException {
		roleValid(role);
		Role ro = roleRepo.findByName(role.getName());
		if(ro != null)
			throw new MyManagerException("该名称【"+ro.getName()+"】角色已存在");
		roleRepo.save(role);
	}

	@Override
	@Transactional
	public void update(Role role) throws MyManagerException {
		roleValid(role);
		Role ro = roleRepo.findById(role.getId()).orElse(null);
		if(ro == null)
			throw new MyManagerException("该角色信息不存在");
		if(ro.isSystem())
			throw new MyManagerException("系统内置角色不允许修改");
		ro = roleRepo.findByName(role.getName());
		if(ro != null && !ro.getId().equals(role.getId()))
			throw new MyManagerException("该名称【"+ro.getName()+"】角色已存在");
		roleRepo.save(role);
	}

	@Override
	@Transactional
	public void deleteById(String id) throws MyManagerException {
		Assert.hasText(id, "id不能为空");
		roleRepo.deleteById(id);
	}

	private void roleValid(Role role) throws MyManagerException {
		if(!StringUtils.hasText(role.getName()))
			throw new MyManagerException("角色名称不能为空");
		if(role.getList() == null)
			throw new MyManagerException("角色序号不能为空");
		if(role.getName().length() > 20)
			throw new MyManagerException("角色名称长度超长");
	}

	@Override
	public Role findById(String id) {
		Assert.hasText(id, "id不能为空");
		return roleRepo.findById(id).orElse(null);
	}
}
