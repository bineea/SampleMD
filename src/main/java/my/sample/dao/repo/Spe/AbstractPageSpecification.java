package my.sample.dao.repo.Spe;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import my.sample.common.pub.CommonAbstract;
import my.sample.model.MyFinals;

public abstract class AbstractPageSpecification<E> extends CommonAbstract {

	protected int pageNo = MyFinals.DEFAULT_PAGE_NUM;
	protected int pageSize = MyFinals.DEFAULT_PAGE_SIZE;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageRequest getPageRequest() {
		return PageRequest.of(pageNo, pageSize);
	}
	
	public abstract Specification<E> handleSpecification();
}
