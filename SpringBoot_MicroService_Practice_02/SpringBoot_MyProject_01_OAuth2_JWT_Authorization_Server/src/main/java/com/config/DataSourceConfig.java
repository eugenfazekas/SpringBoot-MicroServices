package com.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Configuration
public class DataSourceConfig {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	
	@Value("${mysql.url}")
	private String url;
	
	@Value("${mysql.username}")
	private String username;
	
	@Value("${mysql.password}")
	private String password;

	public DataSource mySqlDataSource() {
		
		log.debug("DataSource url: "+url);
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	       
	        dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	            
	        return dataSource;
	    }
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
	    return new JdbcTemplate(mySqlDataSource());
	}
}
