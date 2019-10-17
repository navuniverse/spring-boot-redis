/**
 * 
 */
package com.rockingengineering.spring.redis.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author naveen
 *
 * @date 16-Oct-2019
 */
@Service
public class RedisService<T> {

	@Value("${redis.key-prefix}")
	private String redisKeyPrefix;

	@Autowired
	private RedisTemplate<String, T> redisTemplate;

	private ValueOperations<String, T> valueOperations;

	private HashOperations<String, Object, T> hashOperation;

	@PostConstruct
	public void init() {
		valueOperations = redisTemplate.opsForValue();
		hashOperation = redisTemplate.opsForHash();
	}

	public void putMap(String redisKey, Object key, T data) {
		hashOperation.put(getKey(redisKey), key, data);
	}

	public T getMapAsSingleEntry(String redisKey, Object key) {
		return hashOperation.get(getKey(redisKey), key);
	}

	public Map<Object, T> getMapAsAll(String redisKey) {
		return hashOperation.entries(getKey(redisKey));
	}

	public void putValue(String key, T value) {
		valueOperations.set(getKey(key), value);
	}

	public void putValueWithExpireTime(String key, T value, long timeout, TimeUnit unit) {
		valueOperations.set(getKey(key), value, timeout, unit);
	}

	public T getValue(String key) {
		return valueOperations.get(getKey(key));
	}

	public void setExpire(String key, long timeout, TimeUnit unit) {
		redisTemplate.expire(getKey(key), timeout, unit);
	}

	private String getKey(String key) {

		if (redisKeyPrefix != null && redisKeyPrefix.length() > 0) {
			System.out.println("Adding prefix " + redisKeyPrefix + " to key " + key);
			key = redisKeyPrefix + key;
		}

		System.out.println("Generated Redis Key " + key);

		return key;
	}
}