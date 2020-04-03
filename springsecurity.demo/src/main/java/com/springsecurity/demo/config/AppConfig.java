package com.springsecurity.demo.config;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//@EnableWebMvc
@Configuration
@ComponentScan("com.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {
	
	//set up variable to hold properties read from persistence-mysql.properties
	@Autowired
	private Environment env;
			
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean
    public ViewResolver viewResolver() {
        
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      
        viewResolver.setPrefix("WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
      
        return viewResolver;
    }
	
	
	//define a bean for security data source
	@Bean
	public DataSource securityDataSource() {
		
		//create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource(); 
		
		//set the jdbc driver class (unnecessary)
		/*try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			
			throw new RuntimeException(e);
		}*/
		
		//log the connection props
		logger.info("jdbc.url = " + env.getProperty("jdbc.url"));
		logger.info("jdbc.user = " + env.getProperty("jdbc.user"));
		
		
		//set database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		//set connection pool props
		securityDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
		securityDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
		securityDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
		securityDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
		
		return securityDataSource;
		
		
	}
	

}
