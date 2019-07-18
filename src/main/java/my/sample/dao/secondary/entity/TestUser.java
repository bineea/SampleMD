package my.sample.dao.secondary.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import my.sample.common.entity.StringUUIDEntity;
import my.sample.dao.entity.dict.Male;

@Entity
@Table(name = "test_user")
public class TestUser extends StringUUIDEntity {

	public static final String DEFAULT_PWD = "123456";
	@NotNull
	@Size(max = 50, message = "{userName.error}")
	private String name;//昵称
	private Male male;
	@NotNull
	@Email
	private String email;//唯一
	@NotNull
	@Size(max = 20, message = "登录名长度不能超过20")
	private String loginName;//账号（字母+数字）唯一
	private String passwd;
	private String status;
	@JsonIgnore
	private Blob profilePicture; //头像（昵称首字母或数字图片）

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "male")
	public Male getMale() {
		return male;
	}

	public void setMale(Male male) {
		this.male = male;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "login_name")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "pass_wd")
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "profile_picture")
	public Blob getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Blob profilePicture) {
		this.profilePicture = profilePicture;
	}

}
