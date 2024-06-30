package com.employeemanagement.employeedetails;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.jwt.authentication.auth_api.service.*")
public class EmployeedetailsApplication {
	
	@Autowired
    SessionFactory sessionFactory;

	public static void main(String[] args) {
		SpringApplication.run(EmployeedetailsApplication.class, args);
	}

	
}
