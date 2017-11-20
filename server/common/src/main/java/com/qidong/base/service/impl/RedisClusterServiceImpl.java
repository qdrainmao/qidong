package com.qidong.base.service.impl;

import com.qidong.base.service.RedisClusterService;
import com.qidong.utils.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.dubbo.common.json.JSON;
import org.json.JSONObject;

@Service("redisClusterService")
public class RedisClusterServiceImpl implements RedisClusterService{
    private static Logger LOGGER = LoggerFactory.getLogger(RedisClusterServiceImpl.class);

    public static final int TIME_ONE_HOUR_SECONDS = 60 * 60; // 1小时
    public static final int TIME_ONE_DAY_SECONDS = TIME_ONE_HOUR_SECONDS * 24; // 1天

    JedisPoolConfig poolConfig;
    Set<HostAndPort> nodes;

    {
        poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);

        nodes = new HashSet<HostAndPort>();
        HostAndPort hostAndPort = new HostAndPort("192.168.1.18", 7000);
        HostAndPort hostAndPort1 = new HostAndPort("192.168.1.18", 7001);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.1.18", 7002);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.1.18", 7003);
        HostAndPort hostAndPort4 = new HostAndPort("192.168.1.18", 7004);
        HostAndPort hostAndPort5 = new HostAndPort("192.168.1.18", 7005);
        nodes.add(hostAndPort);nodes.add(hostAndPort1);nodes.add(hostAndPort2);nodes.add(hostAndPort3);nodes.add(hostAndPort4);nodes.add(hostAndPort5);
    }

    public <V> void setRedis(String type, String key, V value, int exp){
        String redisKey = getKey(type, key);

        JedisCluster jedisCluster = getJedisCluster();
        try {
            String result = jedisCluster.set(redisKey, JSON.json(value));

            jedisCluster.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public <V> void setRedis(String type, String key, V value) {
        setRedis(type, key, value,  TIME_ONE_DAY_SECONDS);
    }

    public <V> V getRedis(String type, String key, Class cls){
        String redisKey = getKey(type, key);
        JedisCluster jedisCluster = getJedisCluster();
        String jsonValue = jedisCluster.get(redisKey);

//        V result = (V) new JSONObject(jsonValue);
        V result = jsonValue==null ? null : (V) ReflectUtil.getInstance(jsonValue, cls);
        try {
            jedisCluster.close();
            return result;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private JedisCluster getJedisCluster(){
        return new JedisCluster(nodes, 1000, 1000, 1, poolConfig);
    }

    private String getKey(String type, String key) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(type).append("_").append(key);
        return buffer.toString().trim();
    }
}
