package com.truechain.task.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository<T> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ValueOperations<String, T> valueOperations;

    @Autowired
    private HashOperations<String, String, T> hashOperations;

    public T get(String key) {
        return valueOperations.get(key);
    }

    /**
     * set
     *
     * @param key
     * @param domain
     */
    public void set(String key, T domain) {
        valueOperations.set(key, domain);
    }

    /**
     * increment
     *
     * @param key
     * @param value
     * @return
     */
    public Long increment(String key, long value) {
        return valueOperations.increment(key, value);
    }

    /**
     * increment
     *
     * @param key
     * @param value
     * @return
     */
    public Long increment(String key, long value, long expire, TimeUnit timeUnit) {
        Long result = valueOperations.increment(key, value);
        redisTemplate.expire(key, expire, timeUnit);
        return result;
    }

    /**
     * set
     *
     * @param key
     * @param domain
     * @param expire
     * @param timeUnit
     */
    public void set(String key, T domain, long expire, TimeUnit timeUnit) {
        valueOperations.set(key, domain, expire, timeUnit);
    }

    /**
     * delete
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * hasKey
     *
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public T get(String key, String hKey) {
        return hashOperations.get(key, hKey);
    }

    /**
     * put
     *
     * @param key
     * @param hKey
     * @param domain
     */
    public void put(String key, String hKey, T domain) {
        hashOperations.put(key, hKey, domain);
    }

    /**
     * put
     *
     * @param key
     * @param hKey
     * @param domain
     * @param expire
     */
    public void put(String key, String hKey, T domain, long expire) {
        hashOperations.put(key, hKey, domain);
        if (expire != -1) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }


}