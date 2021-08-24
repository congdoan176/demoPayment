package com.vnpay.controller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vnpay.api.PaymentRequest;
import com.vnpay.config.YamlBankProperties;
import com.vnpay.model.Bank;
import com.vnpay.model.Payment;
import com.vnpay.repositoryImpl.PaymentRepositoryImpl;

@RestController
public class PaymentController {
	
	@Autowired PaymentRepositoryImpl paymentRepositoryImpl;
	@Autowired YamlBankProperties yamlBankProperties;
	
	
	@PostMapping(value = "/addPayment",consumes = {"application/json"},produces = {"application/json"})
	@ResponseBody
	public ResponseEntity addPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
		System.out.println("check data request: " + paymentRequest);
		System.out.println("check yaml: " + yamlBankProperties.getBanks());
		for (Bank bank : yamlBankProperties.getBanks()) {
			System.out.println("bank:" + bank);
		}
//		Payment payment = new Payment();
//		payment.setId(1L);
//		payment.setBankCode(2759L);
//		payment.setTokentKey("abc1234");
//		payment.setJsonData("4321abcd");
//		paymentRepositoryImpl.save(payment);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping(value = "/detail")
	public String findPayment(@RequestParam long id) {
		String data = paymentRepositoryImpl.find(id).toString();
		return data;
	}
}
