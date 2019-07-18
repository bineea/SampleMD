package my.sample.common.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//1.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
//2.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
@MappedSuperclass
public abstract class StringUUIDEntity extends BaseEntity {
	
	protected String id;

	@Id
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		if (id == null) id = UUID.randomUUID().toString().replace("-", "");
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
