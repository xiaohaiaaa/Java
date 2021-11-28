package com.hai.test.service.impl;

import com.hai.test.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }

    @Override
    public void putForHash(String objectkey, String hashKey, String value) {
        redisTemplate.opsForHash().put(objectkey, hashKey, value);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean expire(String key, long millis) {
        return redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean persist(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long decrByKey(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
