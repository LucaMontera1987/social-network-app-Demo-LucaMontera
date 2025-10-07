package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class AppSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSocialApplication.class, args);
	}

}
