package my.sample.config.web;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import my.sample.config.manager.AppConfig;

//配置web.xml
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  
{
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	//指定需要由DispatcherServlet映射的路径，如果是"/"，意思是由DispatcherServlet处理所有向该应用发起的请求
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/app/*" };
	}
	
	//重写registerContextLoaderListener方法，添加额外的Listener
	@Override
	public void registerContextLoaderListener(ServletContext servletContext) {
		servletContext.setInitParameter("logbackConfigLocation", "classpath:config/logback.xml");
		servletContext.addListener(LogbackConfigListener.class);
		servletContext.setInitParameter("debug", "true");
		super.registerContextLoaderListener(servletContext);
	}
	
	//在AbstractAnnotationConfigDispatcherServletInitializer将DispatcherServlet注册到Servlet容器中之后，就会调用customizeRegistration()，并将Servlet注册后得到的Registration.Dynamic传递进来。通过重载customizeRegistration()方法，我们可以对DispatcherServlet进行额外的配置。
	//重写customizeRegistration方法，添加额外配置
	@Override
	public void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
		MultipartConfigElement multipartConfig = new MultipartConfigElement("", 1000000000L, -1, 0);
		registration.setMultipartConfig(multipartConfig);
	}
	
	//重写onStartup方法，添加额外servlet
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}
	
}
