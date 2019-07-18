package my.sample.dao.primary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import my.sample.common.entity.StringUUIDEntity;

@Entity
@Table(name = "sample_role")
public class Role extends StringUUIDEntity
{
	public static final String DEFAULT_ID = "default";
	@NotNull
	@Size(max = 30)
	private String name;
	@NotNull
	@Min(value=1)
	@Max(value=999)
	private Integer list;// 排序
	private String info;
	private boolean system;// 是否系统自带
	public Role()
	{

	}

	public Role(String id)
	{
		this.id = id;
	}

	@Column(nullable = false)
	public boolean isSystem()
	{
		return system;
	}

	public void setSystem(boolean system)
	{
		this.system = system;
	}

	@Column(nullable = false, length = 100, unique = true)
	public String getName()
	{
		return StringUtils.hasText(name) ? name.trim() : name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(nullable = false)
	public Integer getList()
	{
		return list;
	}

	public void setList(Integer list)
	{
		this.list = list;
	}

	@Column(length = 255)
	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}
}
