package my.sample.dao.repo.Spe;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import my.sample.dao.primary.entity.User;

public class UserPageSpe extends AbstractPageSpecification<User> {
	
	private String loginName;//账号
	private String name;//昵称

	@Override
	public Specification<User> handleSpecification() {
		Specification<User> spe = (root, query, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<Predicate>();
			if(StringUtils.hasText(loginName))
				predicateList.add(criteriaBuilder.like(root.get("loginName").as(String.class), like(loginName)));
			if(StringUtils.hasText(name))
				predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), like(name)));
			query.where(predicateList.stream().toArray(Predicate[]::new));
			query.orderBy(criteriaBuilder.desc(root.get("id").as(String.class)));
			return query.getRestriction();
		};
		return spe;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
