package com.qidong.base.service;

import com.qidong.base.dto.UserDto;

public interface ICacheService {
    boolean setUserDto(String token, UserDto userDto);
    /*UserDto getUserDto(String token);
    boolean delUserDto(String token);
    <V>boolean setRedis(String type, String key, V value);
    <V>boolean setRedis(String type, String key, V value, int exp);
    <V>V getRedis(String type, String key);
    boolean delRedis(String type, String key);*/
}
