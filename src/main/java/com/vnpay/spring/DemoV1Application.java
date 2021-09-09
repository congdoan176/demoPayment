package com.vnpay.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.vnpay")
public class DemoV1Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoV1Application.class, args);
	}
}
