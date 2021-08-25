package com.vnpay.repositoryImpl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vnpay.model.Payment;
import com.vnpay.repository.PaymentRepository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{
	
	private static final String KEY = "Payment";

	private RedisTemplate redisTemplate;
	private HashOperations hashOperations;
	
	@Autowired
	public PaymentRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	public void save(Payment payment) {
		// TODO Auto-generated method stub
		hashOperations.put(KEY, payment.getTokenKey(), payment);
	}
	public Payment find(String tokenKey) {
		// TODO Auto-generated method stub
		return (Payment) hashOperations.get(KEY, tokenKey);
	}

}
