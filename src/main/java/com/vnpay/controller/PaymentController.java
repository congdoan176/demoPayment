package com.vnpay.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vnpay.dto.PaymentRequest;
import com.vnpay.dto.PaymentResponse;
import com.vnpay.model.Payment;
import com.vnpay.service.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	@PostMapping(value = "/addPayment", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<PaymentResponse> addPayment(@Valid @RequestBody PaymentRequest paymentRequest) throws Exception {
		ThreadContext.put("token", UUID.randomUUID().toString());
		PaymentResponse paymentResponse = new PaymentResponse();
		try {
			paymentResponse = paymentService.savePayment(paymentRequest);
		} finally {
			ThreadContext.pop();
	        ThreadContext.clearAll();
		}
		return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
		
	}

	@GetMapping(value = "/detail")
	public Payment findPayment(@RequestParam String tokenKey) {
		
		return paymentService.findPaymentByTokenKey(tokenKey);
	}
}
