package my.sample.dao.primary.entity;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import my.sample.common.entity.StringUUIDEntity;

@Entity
@Table(name = "sample_resource", uniqueConstraints = { @UniqueConstraint(columnNames = { "url", "request_method" }) })
public class AppResource extends StringUUIDEntity
{
	@NotNull
	@Size(max = 100)
	private String name;// 资源名称
	@Size(max = 300)
	private String url;// ex:/info/index.do
	private RequestMethod requestMethod = RequestMethod.GET;
	@NotNull
	private MenuType menuType;// 菜单类型，有些资源要验证，但不一定是菜单资源比如修改.do,删除.do
	@NotNull
	private Integer list;// 菜单排序
	private String menuId;// 对应上级菜单的ID
	@Size(max = 100)
	private String logoStyle;// 菜单样式

	// 非持久化信息
	private boolean hasResource;// 当前角色是否有这个菜单
	private boolean hasChild;// 当前栏目菜单是否有子菜单
	private transient MultipartFile logoFile;
	private Pattern resPattern;// 该资源能匹配的正则
	private String realUrl;// 真正请求的URL

	@Transient
	public String getRealUrl()
	{
		return realUrl;
	}

	public void setRealUrl(String realUrl)
	{
		this.realUrl = realUrl;
	}

	@JsonIgnore
	@Transient
	public Pattern getResPattern()
	{
		if (resPattern != null) return resPattern;
		resPattern = Pattern.compile(url.replaceAll("\\{\\w+\\}", "\\\\w+") + "_" + requestMethod.name());
		return resPattern;
	}

	public void setResPattern(Pattern resPattern)
	{
		this.resPattern = resPattern;
	}

	@JsonIgnore
	@Transient
	public MultipartFile getLogoFile()
	{
		return logoFile;
	}

	public void setLogoFile(MultipartFile logoFile)
	{
		this.logoFile = logoFile;
	}

	@Transient
	public boolean isHasChild()
	{
		return hasChild;
	}

	public void setHasChild(boolean hasChild)
	{
		this.hasChild = hasChild;
	}

	@Transient
	public boolean isHasResource()
	{
		return hasResource;
	}

	public void setHasResource(boolean hasResource)
	{
		this.hasResource = hasResource;
	}

	public AppResource()
	{

	}

	public AppResource(String id)
	{
		this.id = id;
	}

	@Column(nullable = false, length = 200)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "url", length = 300, nullable = false)
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "request_method", length = 10, nullable = false)
	public RequestMethod getRequestMethod()
	{
		return requestMethod;
	}

	public void setRequestMethod(RequestMethod requestMethod)
	{
		this.requestMethod = requestMethod;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "menu_type", nullable = false, length = 10)
	public MenuType getMenuType()
	{
		return menuType;
	}

	public void setMenuType(MenuType menuType)
	{
		this.menuType = menuType;
	}

	@Column(nullable = false, scale = 6)
	public Integer getList() {
		return list;
	}

	public void setList(Integer list) {
		this.list = list;
	}

	@Column(name = "menu_id", length = 32)
	public String getMenuId()
	{
		return menuId;
	}

	public void setMenuId(String menuId)
	{
		this.menuId = menuId;
	}
	
	@Column(name = "logo_style")
	public String getLogoStyle() {
		return logoStyle;
	}

	public void setLogoStyle(String logoStyle) {
		this.logoStyle = logoStyle;
	}

	public enum MenuType
	{
		COLUMN("栏目菜单") {},
		MENU("菜单"){},
		NOT_MENU("非菜单") {},;
		private String value;

		private MenuType(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}

	public static String getRequest(String url, RequestMethod method)
	{
		return url + "_" + method.name();
	}

}
