package com.example.ServiceChecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ExternalServiceProperties.class)
public class ServiceCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCheckerApplication.class, args);
	}

}
