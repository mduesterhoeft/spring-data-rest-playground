package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringDataRestValidationApplication {

	@Bean
	@Primary
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRestValidationApplication.class, args);
	}
}
