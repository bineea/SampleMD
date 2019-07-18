package my.sample.dao.primary.entity;
///* Copyright © 2009 ccc
// * All rights reserved.
// * 
// * Date: 2009-6-5
// * author: Peream  (peream@gmail.com)
// *
// */
//package my.sample.dao.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import javax.validation.constraints.NotNull;
//
//import my.sample.common.entity.StringUUIDEntity;
//import my.sample.dao.entity.dict.SysConfType;
//
//@Entity
//@Table(name = "sample_system_info", uniqueConstraints = { @UniqueConstraint(columnNames = { "conf_type", "user_id" }) })
//public class SystemInfo extends StringUUIDEntity
//{
//	public static final String SYSTEM_USER_ID = "system";
//	@NotNull
//	private SysConfType confType;
//	@NotNull
//	private String userId;
//	@NotNull
//	private String jsonStr;
//	private boolean ziped;
//	private LocalDateTime updateTime;
//
//	public SystemInfo()
//	{
//
//	}
//
//	@Column(name = "update_time")
//	public LocalDateTime getUpdateTime()
//	{
//		return updateTime;
//	}
//
//	public void setUpdateTime(LocalDateTime updateTime)
//	{
//		this.updateTime = updateTime;
//	}
//
//	@Column(name = "ziped", nullable = false)
//	public boolean isZiped()
//	{
//		return ziped;
//	}
//
//	public void setZiped(boolean ziped)
//	{
//		this.ziped = ziped;
//	}
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "conf_type", nullable = false, length = 20)
//	public SysConfType getConfType()
//	{
//		return confType;
//	}
//
//	@Column(name = "user_id", nullable = false, length = 32)
//	public String getUserId()
//	{
//		return userId;
//	}
//
//	@Column(name = "json_str", nullable = false, length = 4000)
//	public String getJsonStr()
//	{
//		return jsonStr;
//	}
//
//	public void setConfType(SysConfType confType)
//	{
//		this.confType = confType;
//	}
//
//	public void setUserId(String userId)
//	{
//		this.userId = userId;
//	}
//
//	public void setJsonStr(String jsonStr)
//	{
//		this.jsonStr = jsonStr;
//	}
//
//}
