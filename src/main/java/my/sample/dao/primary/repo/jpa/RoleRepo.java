package my.sample.dao.primary.repo.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.sample.dao.primary.entity.Role;

public interface RoleRepo extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

	Page<Role> findAll(Specification<Role> spec, Pageable pageable);
	
	@Query(value = "select r from Role r where r.name = ?1")
	Role findByName(String name);
}
