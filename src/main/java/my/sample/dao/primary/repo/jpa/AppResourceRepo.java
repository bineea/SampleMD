package my.sample.dao.primary.repo.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMethod;

import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.AppResource.MenuType;

public interface AppResourceRepo extends JpaRepository<AppResource, String>, JpaSpecificationExecutor<AppResource> {

	@Query(value = "select a from AppResource a where a.url = ?1 and a.requestMethod = ?2")
	AppResource findByUrlMethod(String url, RequestMethod requestMethod);
	
	@Query(value = "select a from AppResource a where a.menuType = ?1 order by list asc")
	List<AppResource> findByMenuType(MenuType menuType);
	
	@Query(value = "select a from AppResource a where a.menuId =?1 order by list asc")
	List<AppResource> findByMenuId(String menuId);
	
	Page<AppResource> findAll(Specification<AppResource> spec, Pageable pageable);
}
