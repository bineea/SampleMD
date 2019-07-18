package my.sample.model.acl;

import org.springframework.web.multipart.MultipartFile;

import my.sample.dao.entity.dict.Male;
import my.sample.dao.primary.entity.Role;
import my.sample.dao.primary.entity.User.UserStatus;
import my.sample.model.BaseModel;

public class UserInfoModel extends BaseModel {

	private String userId;
	private String name;
	private Male male;
	private String email;
	private String loginName;// 账号（字母+数字）
	private String passwd;
	private UserStatus status = UserStatus.NORMAL;
	private Role role;
	private String roleId;
	private String oldPasswd;// 老密码
	private String confirmPw;// 确认密码
	private boolean rememberMe;// 记住账号
	private MultipartFile profilePic;//头像

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Male getMale() {
		return male;
	}

	public void setMale(Male male) {
		this.male = male;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getConfirmPw() {
		return confirmPw;
	}

	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public MultipartFile getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(MultipartFile profilePic) {
		this.profilePic = profilePic;
	}

}
