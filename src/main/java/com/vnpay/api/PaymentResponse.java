package com.vnpay.api;

import lombok.Data;

@Data
public class PaymentResponse {
	String code;
	String message;
	public PaymentResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public PaymentResponse() {
		super();
	}
	
	
}
