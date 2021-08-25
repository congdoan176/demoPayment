package com.vnpay.api;

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
	@NotNull
	long realAmount;
	@NotNull
	@NotBlank
	String promotionCode;
}
