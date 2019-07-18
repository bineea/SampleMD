package my.sample.manager.acl;

import org.springframework.web.bind.annotation.RequestMethod;

import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.Role;

public interface AclManager {

	AppResource findByUrlMethod(String url, RequestMethod method);
	
	String checkAuth(Role role, AppResource resource); 
}
