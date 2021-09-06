package com.vnpay.spring;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnpay.controller.PaymentController;
import com.vnpay.dto.PaymentRequest;
import com.vnpay.dto.PaymentResponse;
import com.vnpay.util.StatusCode;

@SpringBootTest
public class TestPaymentController {
	@Autowired
	PaymentController controller;

	@Test
	public void addPaymentTest() throws Exception {
		String data = "{\r\n" + 
				"	\"tokenKey\": \"1601353776839FT19310RH6P1\",\r\n" + 
				"	\"apiID\": \"restPayment\",\r\n" + 
				"	\"mobile\": \"0345225630\",\r\n" + 
				"	\"bankCode\": \"970445\",\r\n" + 
				"	\"accountNo\": \"0001100014211002\",\r\n" + 
				"	\"payDate\": \"20200929112923\",\r\n" + 
				"	\"additionalData\": \"11\",\r\n" + 
				"	\"debitAmount\": 11200,\r\n" + 
				"	\"respCode\": \"00\",\r\n" + 
				"	\"respDesc\": \"SUCCESS\",\r\n" + 
				"	\"traceTransfer\": \"FT19310RH6P1\",\r\n" + 
				"	\"messageType\": \"1\",\r\n" + 
				"	\"checkSum\": \"07cb3cb1adce3aad292fc722213e0ce493d12e25c2761d8d5424aef25d5dbec4\",\r\n" + 
				"	\"orderCode\": \"FT19310RH6P1\",\r\n" + 
				"	\"userName\": \"cntest001\",\r\n" + 
				"	\"realAmount\": \"11100\",\r\n" + 
				"	\"promotionCode\": \"11\"\r\n" + 
				"}\r\n";
		ObjectMapper mapper = new ObjectMapper();
		PaymentRequest paymentRequest = mapper.readValue(data, PaymentRequest.class);
		PaymentResponse paymentResponse = new PaymentResponse(StatusCode.SAVE_PAYMENT_SUCCESS, "success");
		
		Assertions.assertEquals(paymentResponse, controller.addPayment(paymentRequest));
	}
}
