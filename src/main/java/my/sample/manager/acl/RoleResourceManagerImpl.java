package my.sample.manager.acl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.RoleResource;
import my.sample.dao.primary.entity.AppResource.MenuType;
import my.sample.dao.primary.repo.jpa.RoleResourceRepo;
import my.sample.model.acl.MenuModel;

@Service
public class RoleResourceManagerImpl implements RoleResourceManager {

	@Autowired
	private RoleResourceRepo roleResourceRepo;
	
	@Override
	public List<MenuModel> getRoleMenuList(String roleId) {
		
		List<MenuModel> menuList = new ArrayList<>();
		List<RoleResource> roleResourceList = roleResourceRepo.findByRoleId(roleId);
		List<AppResource> columnList = roleResourceList.stream().filter(roleResource -> roleResource.getResource().getMenuType() == MenuType.COLUMN)
				.filter(roleResource -> !StringUtils.hasText(roleResource.getResource().getMenuId()))
				.map(roleResource -> roleResource.getResource()).collect(Collectors.toList());
		columnList.stream().forEach(column -> {
			menuList.add(getRoleMenu(column, roleId));
		});
		return menuList;
	}
	
	private MenuModel getRoleMenu(AppResource resource, String roleId) {
		MenuModel menuModel = new MenuModel();
		menuModel.setResource(resource);
		if(resource.getMenuType() == MenuType.COLUMN)
		{
			menuModel.setColumn(true);
			roleResourceRepo.findByMenuIdRoleId(resource.getId(), roleId).stream()
											.map(roleResource -> roleResource.getResource())
											.sorted(new Comparator<AppResource>() {

												@Override
												public int compare(AppResource res1, AppResource res2) {
													return res1.getList().compareTo(res2.getList());
												}
												
											})
											.forEach(res -> {
												menuModel.setHasColumns(true);
												menuModel.getColumnMenu().add(getRoleMenu(res,roleId));
											});
		}
		return menuModel;
	}

}
