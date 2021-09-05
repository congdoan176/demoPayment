package com.vnpay.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.vnpay.model.Bank;
import com.vnpay.service.PaymentService;

@SpringBootTest
class DemoV1ApplicationTests {
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	PaymentService paymentService;
	
	@Test
	void contextLoads() {
	}
}
