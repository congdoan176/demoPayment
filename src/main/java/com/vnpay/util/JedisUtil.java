package com.vnpay.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vnpay.controller.PaymentController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {
	@Autowired
    private JedisPool jedisPool;
	
	private static Logger logger = LogManager.getLogger(PaymentController.class);
	public Long save(String key, String field,String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hset(key, field, value);
        } catch (Exception e) {
        	logger.info("Hset redis Ex: ", e);
            return -1L;
        } finally {
            // The business operation is completed and the connection is returned to the connection pool
//             jedisPool.returnResource(jedis);
             jedis.close();
        }
    }

    /**
     * Get the specified Value according to the passed in key
     * @param key
     * @return
     * @author hw
     * @date 2018 14 December 2003
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            return "-1";
        } finally {
            jedis.close();
        }
    }
}
