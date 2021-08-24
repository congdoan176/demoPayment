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
	long mobile;
	@NotNull
	long bankCode;
	@NotNull
	long accountNo;
	@NotNull
	long payDate;
	@NotNull
	String additionalData;
	@NotNull
	long debitAmount;
	@NotNull
	int respCode;
	@NotNull
	String respDesc;
	@NotNull
	String traceTransfer;
	@NotNull
	int messageType;
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
