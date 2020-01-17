package com.springboot.microservices.gitactivities.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@ComponentScan(value = "com.springboot.microservices.gitactivities.*")
@EnableSwagger2
public class Project5VkakarlaSpringbootGitactivitiesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project5VkakarlaSpringbootGitactivitiesServiceApplication.class, args);
	}

	@Bean
	public Docket saggerapi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.springboot.microservices.gitactivities.controller")).build();
	}


}