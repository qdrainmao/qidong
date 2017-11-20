package com.qidong.base.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.qidong.base.dto.UserDto;
import com.qidong.base.enums.TypeEnum;
import com.qidong.base.service.RedisService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.io.IOException;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    public static final int TIME_ONE_HOUR_SECONDS = 60 * 60; // 1小时
    public static final int TIME_ONE_DAY_SECONDS = TIME_ONE_HOUR_SECONDS * 24; // 1天

    @Autowired
    private JedisPool jedisPool;

    public Long setUserDto(String token, UserDto userDto) {
        LOGGER.debug("token:"+token);

        return getRedis(TypeEnum.TYPE_USER.getName(), token);
    }

    public UserDto getUserDto(String token) {
        LOGGER.debug("token:"+token);

        return getRedis(TypeEnum.TYPE_USER.getName(), token);
    }

    public void delUserDto(String token) {
        LOGGER.debug("token:"+token);

        delRedis(TypeEnum.TYPE_USER.getName(), token);
    }

    public <V> Long setRedis(String type, String key, V value) {
        return setRedis(type, key, value,  TIME_ONE_DAY_SECONDS);
    }

    public <V> Long setRedis(String type, String key, V value, int exp) {
        LOGGER.debug(type+","+key+","+value.toString()+","+exp);

        String redisKey = getKey(type, key);
        if (jedisPool != null){
            try {
                Jedis jedis = jedisPool.getResource();
                jedis.set(redisKey, JSON.json(value));
                jedis.close();
                return jedis.expire(redisKey, exp);
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public <V> V getRedis(String type, String key) {
        String redisKey = getKey(type, key);
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get(redisKey);
        V value = (V) new JSONObject(str);
        jedis.close();
        return value;
    }

    public void delRedis(String type, String key) {
        String redisKey = getKey(type, key);
        Jedis jedis = jedisPool.getResource();
        jedis.del(redisKey);
        jedis.close();
    }

    private String getKey(String type, String key) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(type).append("_").append(key);
        return buffer.toString().trim();
    }
}
