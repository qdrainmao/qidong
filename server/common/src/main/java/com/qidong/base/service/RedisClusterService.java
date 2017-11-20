package com.qidong.base.service;

public interface RedisClusterService {
    <V>void setRedis(String type, String key, V value);
    <V>void setRedis(String type, String key, V value, int exp);
    <V>V getRedis(String type, String key, Class cls);
}
