package com.dongyv.blog.service.redis.impl;

import com.dongyv.blog.service.redis.ICacheCompone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheCompone implements ICacheCompone {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void putString(String key, String object, long expire, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, object, expire, timeUnit);
    }

    @Override
    public void putObject(String key, Object object, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, object, expire, timeUnit);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getObject(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        return object;
    }

    @Override
    public void increment(String key,int num) {
        redisTemplate.opsForValue().increment(key, num);
    }
}

