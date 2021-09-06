package com.vnpay.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vnpay.config.YamlBankProperties;
import com.vnpay.controller.PaymentController;
import com.vnpay.dto.PaymentRequest;
import com.vnpay.dto.PaymentResponse;
import com.vnpay.model.Bank;
import com.vnpay.model.Payment;
import com.vnpay.repositoryImpl.PaymentRepositoryImpl;
import com.vnpay.util.StatusCode;
import com.vnpay.util.sha256Hmac;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepositoryImpl paymentRepositoryImpl;
	@Autowired
	YamlBankProperties yamlBankProperties;

	private static Logger logger = LogManager.getLogger(PaymentController.class);
	
	public PaymentResponse savePayment(PaymentRequest paymentRequest) throws Exception {
		logger.info("PaymentRequest: TokenKey-{}, Data-{}", paymentRequest.getTokenKey() ,paymentRequest.toString());
		Bank  bank = checkBankcode(paymentRequest.getBankCode());
		if(null == bank) {
			return new PaymentResponse(StatusCode.BANK_CODE_NOT_EXIST, "Bank does not exist");
		}
		// checkSum
		if (!hmacCheckSum(paymentRequest, bank.getPrivateKey())) {
			logger.info("Response checkSum error: {}", new PaymentResponse(StatusCode.CHECK_SUM_ERROR, "CheckSum incorrect or non-existent"));
			return new PaymentResponse(StatusCode.CHECK_SUM_ERROR,"CheckSum incorrect or non-existent");
		}
		try {
			paymentRepositoryImpl.save(new Payment(paymentRequest.getTokenKey(), paymentRequest.getBankCode(), paymentRequest.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Save data to redis error: ", e);
			return new PaymentResponse(StatusCode.SAVE_PAYMENT_ERROR, "Save data error");
		}
		logger.info("Response : " + new PaymentResponse(StatusCode.SAVE_PAYMENT_SUCCESS, "success"));
		return new PaymentResponse(StatusCode.SAVE_PAYMENT_SUCCESS, "success");
	}
	public Payment findPaymentByTokenKey(String tokenKey) {
		logger.info("Get detail by TokenKey ");
		Payment payment = null;
		try {
			payment = paymentRepositoryImpl.find(tokenKey);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Get detail by TokenKey error: ", e);
			
		}
		return payment;
	}
	
	public Bank checkBankcode(String bankCode) {
		List<Bank> listBank = yamlBankProperties.getBanks();
		Bank bank = null;
		 if(listBank.stream().filter(b -> b.getBankCode().equals(bankCode)).collect(Collectors.toList()).size() > 0) {
			 bank = listBank.stream().filter(b -> b.getBankCode().equals(bankCode)).collect(Collectors.toList()).get(0);
		 }
		return bank;
	}
	public boolean hmacCheckSum(PaymentRequest paymentRequest, String privateKey) throws Exception {
		
		String hmacCheckSum = paymentRequest.getMobile() + paymentRequest.getBankCode() + paymentRequest.getAccountNo()
				+ paymentRequest.getPayDate() + paymentRequest.getDebitAmount() + paymentRequest.getRespCode()
				+ paymentRequest.getTraceTransfer() + paymentRequest.getMessageType() + privateKey;
		
		String encodeCheckSum = sha256Hmac.encode(hmacCheckSum, privateKey);
		if(encodeCheckSum.equals(paymentRequest.getCheckSum())) {
			return true;
		}
		return false;
	}
}
