package my.sample.dao.repo.Spe;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.sample.dao.primary.entity.Role;

public class RolePageSpe extends AbstractPageSpecification<Role> {

	private String name;//角色名称
	
	@Override
	public Specification<Role> handleSpecification() {
		Specification<Role> spe = (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(StringUtils.hasText(name))
				predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), like(name)));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.asc(root.get("list").as(Integer.class)),criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
