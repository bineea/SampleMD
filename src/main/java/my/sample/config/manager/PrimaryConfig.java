package my.sample.config.manager;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

@Configuration
//注解开启对Spring Data JPA Repostory的支持
@EnableJpaRepositories(
		basePackages ={ AppConfig.APP_NAME + ".dao.primary.repo.jpa"}, 
		entityManagerFactoryRef = "primaryEntityManager", 
		transactionManagerRef = "primaryTransactionManager")
//注解开启注解式事务的支持，通知Spring，@Transactional注解的类被事务的切面包围
/*AdviceMode共有两种模式：
PROXY(代理模式，jdk动态代理和cglib动态代理)
ASPECTJ(编译时增强模式，编译时对类进行增强生成新的AOP类)，需要配置AnnotationTransactionAspect
*/
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
public class PrimaryConfig {
	
	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource primaryDataSource;
	
	@Autowired
	@Qualifier("databaseProperties")
	private PropertiesFactoryBean databaseProperties;
	
	@Value("#{databaseProperties.dialect}")
	private String dialect;
	

	@Primary
	@Bean(name = "primaryEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManager()
	{
		LocalContainerEntityManagerFactoryBean entityFactory = new LocalContainerEntityManagerFactoryBean();
		entityFactory.setDataSource(primaryDataSource);
		entityFactory.setPackagesToScan(new String[] {AppConfig.APP_NAME+".dao.primary.entity"});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(false);
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setDatabasePlatform(dialect);
		
		entityFactory.setJpaVendorAdapter(vendorAdapter);
		return entityFactory;
	}
	
	@Primary
	@Bean(name = "primaryTransactionManager")
	public JpaTransactionManager transactionManager()
	{
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManager().getObject());
		return transactionManager;
	}
	
	@Primary
	@Bean(name = "primaryAnnotationTransactionAspect")
	public AnnotationTransactionAspect annotationTransactionAspect() {
		AnnotationTransactionAspect aspect = AnnotationTransactionAspect.aspectOf();
		aspect.setTransactionManager(transactionManager());
		return aspect;
	}
}
