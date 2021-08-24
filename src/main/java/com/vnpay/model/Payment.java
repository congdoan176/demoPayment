package com.vnpay.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Payment implements Serializable{
//	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long bankCode;
	private String tokentKey;
	private String jsonData;
	public Payment(long id, long bankCode, String tokentKey, String jsonData) {
		this.id = id;
		this.bankCode = bankCode;
		this.tokentKey = tokentKey;
		this.jsonData = jsonData;
	}
	public Payment() {
		// TODO Auto-generated constructor stub
	}
	
	
}
