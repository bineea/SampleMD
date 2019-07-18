package my.sample.dao.primary.entity;
//package my.sample.dao.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import my.sample.common.entity.StringUUIDEntity;
//import my.sample.dao.entity.dict.LogType;
//
//@Entity
//@Table(name = "sample_system_log")
//public class SystemLog extends StringUUIDEntity
//{
//	private User user;
//	private LocalDateTime optime;
//	private String ip;
//	private LogType logType;
//	private String info;
//
//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = false)
//	public User getUser()
//	{
//		return user;
//	}
//
//	public void setUser(User user)
//	{
//		this.user = user;
//	}
//
//	@Column(nullable = false)
//	public LocalDateTime getOptime()
//	{
//		return optime;
//	}
//
//	public void setOptime(LocalDateTime optime)
//	{
//		this.optime = optime;
//	}
//
//	@Column(nullable = false, length = 50)
//	public String getIp()
//	{
//		return ip;
//	}
//
//	public void setIp(String ip)
//	{
//		this.ip = ip;
//	}
//
//	@Enumerated(EnumType.STRING)
//	@Column(name = "log_type", nullable = false, length = 10)
//	public LogType getLogType()
//	{
//		return logType;
//	}
//
//	public void setLogType(LogType logType)
//	{
//		this.logType = logType;
//	}
//
//	@Column(length = 300, nullable = false)
//	public String getInfo()
//	{
//		return info;
//	}
//
//	public void setInfo(String info)
//	{
//		this.info = info;
//	}
//
//}
