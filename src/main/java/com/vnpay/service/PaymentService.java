package com.vnpay.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vnpay.config.YamlBankProperties;
import com.vnpay.controller.PaymentController;
import com.vnpay.dto.PaymentRequest;
import com.vnpay.dto.PaymentResponse;
import com.vnpay.model.Bank;
import com.vnpay.model.Payment;
import com.vnpay.repositoryImpl.PaymentRepositoryImpl;
import com.vnpay.util.sha256Hmac;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepositoryImpl paymentRepositoryImpl;
	@Autowired
	YamlBankProperties yamlBankProperties;

	private static Logger logger = LogManager.getLogger(PaymentController.class);
	
	public PaymentResponse savePayment(PaymentRequest paymentRequest) throws Exception {
		logger.info("PaymentRequest: TokenKey-{},Data-{}", paymentRequest.getTokenKey() ,paymentRequest.toString());
		String bankCodeReq = paymentRequest.getBankCode();
		List<Bank> listBank = yamlBankProperties.getBanks();
		if (listBank.stream().filter(b -> b.getBankCode().equals(bankCodeReq)).collect(Collectors.toList()).size() == 0) {
			logger.info("Response check bank error: {}", new PaymentResponse("02", "Bank does not exist"));
			return new PaymentResponse("02", "Bank does not exist");
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
			return new PaymentResponse("03","CheckSum incorrect or non-existent");
		}
		try {
			paymentRepositoryImpl.save(new Payment(paymentRequest.getTokenKey(), paymentRequest.getBankCode(), paymentRequest.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Save data to redis error: {}", e.getMessage());
		}
		logger.info("Response : " + new PaymentResponse("00", "success"));
		return new PaymentResponse("00", "success");
	}
	public Payment findPaymentByTokenKey(String tokenKey) {
		logger.info("Get detail by TokenKey ");
		Payment payment = null;
		try {
			payment = paymentRepositoryImpl.find(tokenKey);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Get detail by TokenKey error: "+ e.getMessage());
		}
		return payment;
	}
}
