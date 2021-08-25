package com.vnpay.model;

import java.io.Serializable;
import java.util.Calendar;

import lombok.Data;

@Data
public class Payment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tokenKey;
	private String bankCode;
	private String requestData;
	private long createdAt = Calendar.getInstance().getTimeInMillis();
	
	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(String tokenKey, String bankCode, String requestData) {
		super();
		this.tokenKey = tokenKey;
		this.bankCode = bankCode;
		this.requestData = requestData;
	}
	
}
