package com.qidong.base.service;

import com.qidong.base.dto.UserDto;

public interface RedisService {
    Long setUserDto(String token, UserDto userDto);
    UserDto getUserDto(String token);
    void delUserDto(String token);
    <V>Long setRedis(String type, String key, V value);
    <V>Long setRedis(String type, String key, V value, int exp);
    <V>V getRedis(String type, String key);
    void delRedis(String type, String key);
}
