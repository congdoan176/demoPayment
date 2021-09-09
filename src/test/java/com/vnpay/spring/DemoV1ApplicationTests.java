package com.vnpay.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vnpay.service.PaymentService;

@SpringBootTest
class DemoV1ApplicationTests {
	@Autowired
	PaymentService paymentService;
	
	@Test
	void contextLoads() {
	}
}
