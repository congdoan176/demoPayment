package com.vnpay.repositoryImpl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.vnpay.model.Payment;
import com.vnpay.repository.PaymentRepository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{
	
	private static final String KEY = "Payment";

	private RedisTemplate<String, ?> redisTemplate;
	private HashOperations<String, String, Payment> hashOperations;
	
	@Autowired
	public PaymentRepositoryImpl(RedisTemplate<String, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}
	
	public void save(Payment payment) {
		// TODO Auto-generated method stub
		try {
			hashOperations.put(KEY, payment.getTokenKey(), payment);
		} catch (Exception e) {
			// TODO: handle exception
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	public Payment find(String tokenKey) {
		// TODO Auto-generated method stub
		return (Payment) hashOperations.get(KEY, tokenKey);
	}

}
