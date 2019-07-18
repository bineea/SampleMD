package my.sample.manager.acl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.RoleResource;
import my.sample.dao.primary.repo.jpa.RoleResourceRepo;

@Component
public class RoleResourceMapHolder {
	
	public static Map<String, Map<String, AppResource>> roleResourceMap = new ConcurrentHashMap<>();
	
	@Autowired
	private RoleResourceRepo roleResourceRepo;
	
	public Map<String, AppResource> handleMenu(String roleId) {
		
		Map<String, AppResource> resourceMap = roleResourceMap.get(roleId);
		if(resourceMap == null || resourceMap.isEmpty()){
			resourceMap = new ConcurrentHashMap<>();
			List<RoleResource> roleResourceList = roleResourceRepo.findByRoleId(roleId);
			List<AppResource> resourceList = roleResourceList.stream().map(roleResource -> roleResource.getResource()).collect(Collectors.toList());
			for(AppResource resource:resourceList) {
				resourceMap.put(resource.getId(), resource);
			}
			roleResourceMap.put(roleId, resourceMap);
		}
		return resourceMap;
	}
	
	public boolean hasResource(String roleId, String resourceId) {
		
		Map<String, AppResource> resourceMap = handleMenu(roleId);
		return resourceMap.get(resourceId) != null;
	}
	
	/**
	 * 配置完角色权限后调用本方法清空角色拥有权限的缓存
	 * @param roleId
	 */
	public void removeRoleMenus(String roleId)
	{
		roleResourceMap.remove(roleId);
	}
}
