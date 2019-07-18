package my.sample.dao.repo.Spe;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.sample.dao.primary.entity.AppResource;
import my.sample.dao.primary.entity.AppResource.MenuType;

public class AppResourcePageSpe extends AbstractPageSpecification<AppResource> {

	private String menuId;// 对应上级菜单的ID
	private String name;// 资源名称
	private MenuType menuType;// 菜单类型
	
	@Override
	public Specification<AppResource> handleSpecification() {
		Specification<AppResource> spe = (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(StringUtils.hasText(menuId))
				predicateList.add(criteriaBuilder.equal(root.get("menuId"), menuId));
			if(StringUtils.hasText(name))
				predicateList.add(criteriaBuilder.like(root.get("name"), like(name)));
			if(menuType != null)
				predicateList.add(criteriaBuilder.equal(root.get("menuType"), menuType));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.asc(root.get("list")));
			return query.getRestriction();
		};
		return spe;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

}
