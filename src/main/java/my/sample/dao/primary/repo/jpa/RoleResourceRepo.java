package my.sample.dao.primary.repo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import my.sample.dao.primary.entity.RoleResource;

public interface RoleResourceRepo extends JpaRepository<RoleResource, String>, JpaSpecificationExecutor<RoleResource> {

	@Query(value = "select r from RoleResource r where r.role.id = ?1")
	List<RoleResource> findByRoleId(String roleId);
	
	@Query(value = "select r from RoleResource r where r.resource.id = ?1")
	List<RoleResource> findByResourceId(String resourceId);

	@Query(value = "select r from RoleResource r where r.resource.menuId = ?1 and r.role.id = ?2")
	List<RoleResource> findByMenuIdRoleId(String menuId, String roleId);
	
	@Query(value = "select r from RoleResource r where r.resource.id = ?1 and r.role.id = ?2")
	RoleResource findByRoleResourceId(String resourceId, String roleId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from RoleResource r where r.role.id = ?1")
	int deleteByRoleId(String roleId);
}
