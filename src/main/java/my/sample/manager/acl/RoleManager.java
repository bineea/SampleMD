package my.sample.manager.acl;

import java.util.List;

import org.springframework.data.domain.Page;

import my.sample.common.pub.MyManagerException;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.repo.Spe.RolePageSpe;
import my.sample.model.acl.ResourceTreeModel;

public interface RoleManager {

	/**
	 * 分页查询
	 * @param spe
	 * @return
	 */
	Page<Role> pageQuery(RolePageSpe spe);
	
	/**
	 * 获取所有资源，并选中该角色拥有资源
	 * @param roleId
	 * @return
	 */
	List<ResourceTreeModel> getAllResource(String roleId);
	
	/**
	 * 配置角色资源
	 * @param roleId
	 * @param resourceIds
	 */
	void configRole(String roleId, String[] resourceIds) throws MyManagerException;
	
	/**
	 * 获取所有角色
	 * @return
	 */
	List<Role> getAll();
	
	/**
	 * 添加角色
	 * @param role
	 * @throws MyManagerException
	 */
	void add(Role role) throws MyManagerException;
	
	/**
	 * 更新角色
	 * @param role
	 * @throws MyManagerException
	 */
	void update(Role role) throws MyManagerException;
	
	/**
	 * 通过id删除角色
	 * @param id
	 * @throws MyManagerException
	 */
	void deleteById(String id) throws MyManagerException;
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	Role findById(String id);
}
