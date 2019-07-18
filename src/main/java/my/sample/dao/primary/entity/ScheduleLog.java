package my.sample.dao.primary.entity;
///*
// * Date: 2012-3-7
// * author: Peream  (peream@gmail.com)
// *
// */
//package my.sample.dao.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
//import my.sample.common.entity.StringUUIDEntity;
//
//@Entity
//@Table(name = "sample_schedule_log")
//public class ScheduleLog extends StringUUIDEntity
//{
//	private String taskName;
//	private LocalDateTime startTime;
//	private LocalDateTime endTime;
//	private boolean bySystem;
//	private String currentCron;
//	private long execTime;
//
//	@Column(name = "exec_time", nullable = false)
//	public long getExecTime()
//	{
//		return execTime;
//	}
//
//	public void setExecTime(long execTime)
//	{
//		this.execTime = execTime;
//	}
//
//	@Column(name = "by_system", nullable = false)
//	public boolean isBySystem()
//	{
//		return bySystem;
//	}
//
//	@Column(name = "current_cron", nullable = false, length = 50)
//	public String getCurrentCron()
//	{
//		return currentCron;
//	}
//
//	public void setBySystem(boolean bySystem)
//	{
//		this.bySystem = bySystem;
//	}
//
//	public void setCurrentCron(String currentCron)
//	{
//		this.currentCron = currentCron;
//	}
//
//	@Column(name = "task_name", nullable = false, length = 100)
//	public String getTaskName()
//	{
//		return taskName;
//	}
//
//	public void setTaskName(String taskName)
//	{
//		this.taskName = taskName;
//	}
//
//	@Column(name = "start_time", nullable = false)
//	public LocalDateTime getStartTime()
//	{
//		return startTime;
//	}
//
//	public void setStartTime(LocalDateTime startTime)
//	{
//		this.startTime = startTime;
//	}
//
//	@Column(name = "end_time", nullable = false)
//	public LocalDateTime getEndTime()
//	{
//		return endTime;
//	}
//
//	public void setEndTime(LocalDateTime endTime)
//	{
//		this.endTime = endTime;
//	}
//
//	@Transient
//	public String getTimeStr()
//	{
//		return execTime + "毫秒";
//	}
//
//}
