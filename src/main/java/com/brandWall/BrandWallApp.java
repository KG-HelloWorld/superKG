package com.brandWall;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@ComponentScan
@EnableScheduling
@EnableAutoConfiguration
@EnableTransactionManagement
//@EnableSwagger2
public class BrandWallApp extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BrandWallApp.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BrandWallApp.class);
	}
	
	
	
}

