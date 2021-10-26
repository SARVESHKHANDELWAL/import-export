package com.ie.loginorsignup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LoginorsignupApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginorsignupApplication.class, args);
	}

}
