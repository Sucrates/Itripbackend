package com.ytzl.itrip.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by Su_crates on 2018/4/20.
 */

@Component
public class RedisAPI {

    @Resource
    private JedisPool jedisPool;

    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {

            String result = jedis.set(key, value);

            jedisPool.returnResource(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }
    }

    public void set(String key, String value, int expire) {
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.setex(key, expire, value);
            jedisPool.returnResource(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.get(key);
            jedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return null;
        }
    }

    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long result = jedis.ttl(key);
            jedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return null;
        }
    }

    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Boolean result = jedis.exists(key);
            jedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return false;
        }
    }

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long result = jedis.del(key);
            jedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return null;
        }
    }
}
