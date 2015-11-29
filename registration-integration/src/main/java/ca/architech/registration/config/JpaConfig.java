package ca.architech.registration.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ca.architech.registration.repository"})
public class JpaConfig {

	private final String PACKAGES_TO_SCAN = "ca.architech.registration.domain";

	@Value("${database.password}")
	private String password;

	@Value("${database.url}")
	private String url;

	@Value("${database.username}")
	private String username;

	@Value("${database.driverClassName}")
	private String driverClassName;

	@Value("${hibernate.dialect}")
	private String dbDialect;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	
	@Value("${hibernate.ejb.naming_strategy}")
	private String ejbNamingStrategy;
	
	@Value("${hibernate.show_sql}")
	private String showSql;
	
	@Value("${hibernate.format_sql}")
	private String formatSql;
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(this.driverClassName);
        dataSourceConfig.setJdbcUrl(this.url);
        dataSourceConfig.setUsername(this.username);
        dataSourceConfig.setPassword(this.password);
        
        return new HikariDataSource(dataSourceConfig);
	}
	
	 @Bean
	 LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	    entityManagerFactoryBean.setDataSource(dataSource);
	    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	    entityManagerFactoryBean.setPackagesToScan(this.PACKAGES_TO_SCAN);
 
        Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", this.dbDialect);
		jpaProperties.put("hibernate.hbm2ddl.auto", this.hbm2ddlAuto);
		jpaProperties.put("hibernate.ejb.naming_strategy", this.ejbNamingStrategy);
		jpaProperties.put("hibernate.show_sql", this.showSql);
		jpaProperties.put("hibernate.format_sql", this.formatSql);
 
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        return entityManagerFactoryBean;
    }
	 
	@Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
