package com.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogappApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogappApplication.class, args);

	}
	    @Bean //it helps us to create a bean when Spring IOC will no able to do so
		//Now ModelMapper Class is managed by Spring IOC
		public ModelMapper getModelMapper(){

		return new ModelMapper();
		}

}
