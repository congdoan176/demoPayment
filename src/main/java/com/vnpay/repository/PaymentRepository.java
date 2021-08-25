package com.vnpay.repository;

import com.vnpay.model.Payment;
public interface PaymentRepository {
	void save(Payment payment);
	Payment find(String tokenKey);
}
