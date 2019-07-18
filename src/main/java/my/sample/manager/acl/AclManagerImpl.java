package my.sample.manager.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import my.sample.common.pub.CommonAbstract;
import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.primary.repo.jpa.AppResourceRepo;

@Service
public class AclManagerImpl extends CommonAbstract implements AclManager {

	@Autowired
	private AppResourceRepo resourceRepo;
	@Autowired
	private RoleResourceMapHolder roleResourceHolder;
	
	@Override
	public AppResource findByUrlMethod(String url, RequestMethod method) {
		return resourceRepo.findByUrlMethod(url, method);
	}

	@Override
	public String checkAuth(Role role, AppResource resource) {
		if(role == null || !StringUtils.hasText(role.getId()))
			return "角色信息不存在";
		if(resource == null)
			return null;
		return roleResourceHolder.hasResource(role.getId(), resource.getId())?null:"角色("+role.getName()
			+")无权访问此URL("+resource.getUrl()+"_"+resource.getRequestMethod()+")";
	}
	
}
