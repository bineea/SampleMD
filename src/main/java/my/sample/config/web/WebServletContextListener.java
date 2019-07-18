package my.sample.config.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import my.sample.manager.AbstractManager;

@WebListener
public class WebServletContextListener extends AbstractManager implements ServletContextListener {

	//启动listener的时候还没有初始化bean工厂，不可能注入什么东西
	//启动web应用时，系统调用Listener的该方法
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("-------------Start context init-------------");
		logger.info("-------------End context init-------------");
	}
}
