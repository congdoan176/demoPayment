package com.vnpay.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vnpay.model.Bank;
import com.vnpay.service.PaymentService;

@SpringBootTest
public class TestPaymentService {

	@Autowired
	PaymentService paymentService;
	
	@Test
	public void testCheckBankCode() {
		Bank bank = new Bank();
		bank.setBankCode("970445");
		bank.setPrivateKey("xyz7891");
		bank.setIps("");
		Assertions.assertEquals(bank, paymentService.checkBankcode("970445"));
	}
}
