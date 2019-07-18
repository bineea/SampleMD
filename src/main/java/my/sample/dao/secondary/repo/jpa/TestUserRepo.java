package my.sample.dao.secondary.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import my.sample.dao.primary.entity.User;
import my.sample.dao.secondary.entity.TestUser;

public interface TestUserRepo extends JpaRepository<TestUser, String>, JpaSpecificationExecutor<TestUser> {

	@Query(value = "select u from TestUser u where u.loginName = ?1 and u.passwd = ?2")
	User findByLoginNamePasswd(String loginName, String passwd);
	
	@Query(value = "select u from TestUser u where u.loginName = ?1")
	User findByLoginName(String loginName);
	
	@Query(value = "select u from TestUser u where u.email = ?1")
	User findByEmail(String email);
	
	@Query(value = "select u from TestUser u where u.email = ?1 and u.id != ?2")
	User findByEmailUnequalId(String email, String id);
}
