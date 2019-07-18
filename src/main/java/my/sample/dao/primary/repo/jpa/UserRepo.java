package my.sample.dao.primary.repo.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.sample.dao.primary.entity.User;

public interface UserRepo extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

	@Query(value = "select u from User u where u.loginName = ?1 and u.passwd = ?2")
	User findByLoginNamePasswd(String loginName, String passwd);
	
	Page<User> findAll(Specification<User> spec, Pageable pageable);
	
	@Query(value = "select u from User u where u.loginName = ?1")
	User findByLoginName(String loginName);
	
	@Query(value = "select u from User u where u.email = ?1")
	User findByEmail(String email);
	
	@Query(value = "select u from User u where u.email = ?1 and u.id != ?2")
	User findByEmailUnequalId(String email, String id);
}
