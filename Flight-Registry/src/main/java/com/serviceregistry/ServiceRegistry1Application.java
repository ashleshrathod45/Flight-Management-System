package com.serviceregistry;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceRegistry1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistry1Application.class, args);
	}

}
