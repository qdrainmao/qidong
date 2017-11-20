package com.qidong.base.service.impl;

import com.qidong.base.dto.UserDto;
import com.qidong.base.enums.TypeEnum;
import com.qidong.base.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/*@Service("iCacheService")*/
public class ICacheServiceImpl implements ICacheService {
    private static Logger LOGGER = LoggerFactory.getLogger(ICacheServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final int TIME_ONE_HOUR_SECONDS = 60 * 60; // 1小时
    public static final int TIME_ONE_DAY_SECONDS = TIME_ONE_HOUR_SECONDS * 24; // 1天

    public boolean setUserDto(String token, UserDto userDto) {
        LOGGER.debug("token:"+token+", value:"+userDto.toString());

        return setRedis(TypeEnum.TYPE_USER.getName(), token, userDto);
    }

    public UserDto getUserDto(String token) {
        LOGGER.debug("token:"+token);

        return getRedis(TypeEnum.TYPE_USER.getName(), token);
    }

    public boolean delUserDto(String token) {
        LOGGER.debug("token:"+token);

        return delRedis(TypeEnum.TYPE_USER.getName(), token);
    }

    public <V> boolean setRedis(String type, String key, V value) {
        return setRedis(type, key, value,  TIME_ONE_DAY_SECONDS);
    }

    public <V> boolean setRedis(String type, String key, V value, int exp) {
        LOGGER.debug(type+","+key+","+value.toString()+","+exp);

        String redisKey = getKey(type, key);
        if (redisTemplate != null){
            redisTemplate.opsForValue().set(redisKey, value);
        }
        return redisTemplate.expire(redisKey, exp, TimeUnit.SECONDS);
    }

    public <V> V getRedis(String type, String key) {
        String redisKey = getKey(type, key);
        V value = (V)redisTemplate.opsForValue().get(redisKey);
        return value;
    }

    public boolean delRedis(String type, String key) {
        String redisKey = getKey(type, key);
        redisTemplate.opsForValue().getOperations().delete(redisKey);
        return true;
    }

    private String getKey(String type, String key) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(type).append("_").append(key);
        return buffer.toString().trim();
    }
}
