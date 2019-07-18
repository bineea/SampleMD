package my.sample.config.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import my.sample.config.manager.AppConfig;
import my.sample.manager.AclHandlerInterceptor;

//配置spring-servlet.xml
//配置注解驱动mvc 等效于 <mvc:annotation-driven/>
@EnableWebMvc
//注解该类为配置类
@Configuration
//注解需要扫描的包
@ComponentScan(basePackages = {AppConfig.APP_NAME + ".web"})
public class WebConfig implements WebMvcConfigurer
{
	//配置视图解析器
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	//配置静态资源
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}
	
	@Autowired
	private AclHandlerInterceptor aclInterceptor;

	//覆盖父类方法注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(aclInterceptor);
	}
	
	//上传文件组件，解析请求数据
	//CommonsMultipartResolver 使用 commons Fileupload 来处理 multipart 请求，所以在使用时，必须要引入相应的 jar 包；
	//StandardServletMultipartResolver 是基于 Servlet 3.0来处理 multipart 请求的，所以不需要引用其他 jar 包，但是必须使用支持 Servlet 3.0的容器才可以，以tomcat为例，从 Tomcat 7.0.x的版本开始就支持 Servlet 3.0了。
	@Bean
	public MultipartResolver multipartResolver()
	{
		return new StandardServletMultipartResolver();
	}
	
	//MVC请求对象格式化绑定
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(String.class, new TrimStringFormatter());
		registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
		registry.addFormatterForFieldType(LocalTime.class, new LocalTimeFormatter());
		registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
	}
}
