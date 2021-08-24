package com.vnpay.repository;

import com.vnpay.model.Payment;
public interface PaymentRepository {
	void save(Payment payment);
	Payment find(Long id);
//	Map findAll();
//	void update(Payment customer);
//	void delete(Long id);
}
