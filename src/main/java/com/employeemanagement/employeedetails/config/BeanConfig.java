package com.employeemanagement.employeedetails.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*@Configuration*/
public class BeanConfig {

	@Bean  
	public SessionFactory sessionFactory(HibernateEntityManagerFactory entityManagerFactory){  
	    return entityManagerFactory.getSessionFactory();  
	}
}
