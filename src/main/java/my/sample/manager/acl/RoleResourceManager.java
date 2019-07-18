package my.sample.manager.acl;

import java.util.List;

import my.sample.model.acl.MenuModel;

public interface RoleResourceManager {

	List<MenuModel> getRoleMenuList(String roleId);
}
