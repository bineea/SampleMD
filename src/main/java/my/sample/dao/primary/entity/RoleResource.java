package my.sample.dao.primary.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import my.sample.common.entity.StringUUIDEntity;

@Entity
@Table(name = "sample_role_resource")
public class RoleResource extends StringUUIDEntity
{
	private Role role;
	private AppResource resource;

	public RoleResource()
	{

	}

	public RoleResource(Role role, AppResource resource)
	{
		this.role = role;
		this.resource = resource;
	}

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	public AppResource getResource()
	{
		return resource;
	}

	public void setResource(AppResource resource)
	{
		this.resource = resource;
	}

}
