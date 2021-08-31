package com.vnpay.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PaymentRequest {
	@NotNull
	String tokenKey;
	@NotNull
	String apiID;
	@NotNull
	String mobile;
	@NotNull
	String bankCode;
	@NotNull
	String accountNo;
	@NotNull
	String payDate;
	@NotNull
	String additionalData;
	@NotNull
	long debitAmount;
	@NotNull
	String respCode;
	@NotNull
	String respDesc;
	@NotNull
	String traceTransfer;
	@NotNull
	String messageType;
	@NotNull
	String checkSum;
	@NotNull
	String orderCode;
	@NotNull
	String userName;
	@NotNull(message = "realAmount not Null")
	long realAmount;
	@NotNull(message = "Promotion Code Null")
	@NotBlank(message = "Promotion Code Blank")
	String promotionCode;
}
