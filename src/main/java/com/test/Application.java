package com.test;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author weberli
 *
 */
@ComponentScan
@Configuration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() throws PropertyVetoException {
	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
	transactionManager.setDataSource(dataSource());
	return transactionManager;
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	

	driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	
	//Attempt to get properties from system, if not use defaults
    String dbName = System.getProperty("RDS_DB_NAME");
    String userName = System.getProperty("RDS_USERNAME");
    String password = System.getProperty("RDS_PASSWORD");
    String hostname = System.getProperty("RDS_HOSTNAME");
    String port = System.getProperty("RDS_PORT");
	
	
	if(dbName == null){
		dbName = "testdb";
		System.err.println("No dbName in properties, using Default:" + dbName);
	}
	
	if(userName == null){
		userName = "test";
		System.err.println("No username in properties, using Default:" + userName);
	}

	if(password == null){
		password = "test";
		System.err.println("No password in properties, using Default:" + password);
	}
	
	if(hostname == null){
		hostname = "localhost";
		System.err.println("No hostname in properties, using Default:" + hostname);
	}
	
	if(port == null){
		port = "3306";
		System.err.println("No port in properties, using Default:" + port);
	}
	
	
	driverManagerDataSource.setUrl("jdbc:mysql://" + hostname + ":" + port + "/"+ dbName);
	driverManagerDataSource.setUsername(userName);
	driverManagerDataSource.setPassword(password);

	return driverManagerDataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

	return jdbcTemplate;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
