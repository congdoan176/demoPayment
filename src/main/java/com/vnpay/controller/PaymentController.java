package com.vnpay.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vnpay.api.PaymentResponse;
import com.vnpay.config.YamlBankProperties;
import com.vnpay.model.Bank;
import com.vnpay.model.Payment;
import com.vnpay.repositoryImpl.PaymentRepositoryImpl;
import com.vnpay.util.sha256Hmac;

@RestController
public class PaymentController {

	@Autowired
	PaymentRepositoryImpl paymentRepositoryImpl;
	@Autowired
	YamlBankProperties yamlBankProperties;

	private static Logger logger = LogManager.getLogger(PaymentController.class);

	@PostMapping(value = "/addPayment", consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<PaymentResponse> addPayment(@Valid @RequestBody PaymentRequest paymentRequest) throws Exception {
		logger.info("PaymentRequest: TokenKey-{},Data-{}", paymentRequest.getTokenKey() ,paymentRequest.toString());
		String bankCodeReq = paymentRequest.getBankCode();
		List<Bank> listBank = yamlBankProperties.getBanks();
		if (listBank.stream().filter(b -> b.getBankCode().equals(bankCodeReq)).collect(Collectors.toList()).size() == 0) {
			logger.info("Response check bank error: {}", new PaymentResponse("02", "Bank does not exist"));
			return new ResponseEntity<>(new PaymentResponse("02", "Bank does not exist"), HttpStatus.BAD_REQUEST);
		}
		// checkSum
		String privateKey = listBank.stream().filter(b -> b.getBankCode().equals(bankCodeReq))
				.collect(Collectors.toList()).get(0).getPrivateKey();
		
		String hmacCheckSum = paymentRequest.getMobile() + bankCodeReq + paymentRequest.getAccountNo()
				+ paymentRequest.getPayDate() + paymentRequest.getDebitAmount() + paymentRequest.getRespCode()
				+ paymentRequest.getTraceTransfer() + paymentRequest.getMessageType() + privateKey;
		String encodeCheckSum = sha256Hmac.encode(hmacCheckSum, privateKey);
		System.out.println("sum: "+ encodeCheckSum);
		if(!encodeCheckSum.equals(paymentRequest.getCheckSum())) {
			logger.info("Response checkSum error: {}", new PaymentResponse("03", "CheckSum incorrect or non-existent"));
			return new ResponseEntity<>(new PaymentResponse("03","CheckSum incorrect or non-existent"), HttpStatus.BAD_REQUEST);
		}
		try {
			paymentRepositoryImpl.save(new Payment(paymentRequest.getTokenKey(), paymentRequest.getBankCode(), paymentRequest.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Save data to redis error: {}", e.getMessage());
		}
		logger.info("Response : " + new PaymentResponse("00", "success"));
		return new ResponseEntity<>(new PaymentResponse("00", "success"),HttpStatus.OK);
	}

	@GetMapping(value = "/detail")
	public String findPayment(@RequestParam String tokenKey) {
		logger.info("Get detail by TokenKey ");
		String data = "";
		try {
			data = paymentRepositoryImpl.find(tokenKey).toString();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Get detail by TokenKey error: "+ e.getMessage());
		}
		return data;
	}
}
