package com.backend.ingresso.data.utilityExternal;

import com.backend.ingresso.data.utilityExternal.Interface.ICacheRedisUti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheRedisUti implements ICacheRedisUti {
    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public CacheRedisUti(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getString(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setString(String key, String value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value);
        Boolean result = redisTemplate.expire(key, timeout, unit);
    }

    public void remove(String key){
        Boolean resultRemove = redisTemplate.delete(key);
    }
}
