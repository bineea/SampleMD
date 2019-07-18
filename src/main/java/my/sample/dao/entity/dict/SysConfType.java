/*
 * Date: 2013-5-14
 * author: Peream  (peream@gmail.com)
 *
 */
package my.sample.dao.entity.dict;

/**
 * 
 * @author Peream <br>
 *         Create Time：2013-5-14 下午4:36:48<br>
 *         <a href="mailto:peream@gmail.com">peream@gmail.com</a>
 * @since 1.0
 * @version 1.0
 */
public enum SysConfType
{
	SYSTEM_PARA("系统参数") {},
	CRON_PARA("CRON参数") {},
	;
	private final String value;

	private SysConfType(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

}
