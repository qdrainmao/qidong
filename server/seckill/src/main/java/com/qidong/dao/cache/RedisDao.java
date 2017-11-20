package com.qidong.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.qidong.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDao {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
//        jedisPool = new JedisPool(ip, port,);
        jedisPool = new JedisPool(new JedisPoolConfig(), ip, port, 1000, "123456");
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckillId;
            //redis并没有实现序列号操作
            //从缓存中get->byte[] ->反序列化->Object(Seckill)
            //序列化protobuf性能最好，采用自定义序列化,要求必须是各pojo(有get set 方法)
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null){
                Seckill seckill = schema.newMessage();//空对象
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);//按照schema把数据传入seckill, seckill被反序列化
                //这种序列号空间能压缩到原生java序列化的五分之一到十分之一
                return seckill;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }

    public String putSeckill(Seckill seckill){
        //set Object(Seckill) -> 序列化 -> byte[]
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //超时缓存,一小时
            int timeout = 60 * 60;
            String result = jedis.setex(key.getBytes(), timeout, bytes);
            //result 如果错误返回错误信息，如果正确返回OK
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            jedis.close();
        }

        return null;
    }
}
