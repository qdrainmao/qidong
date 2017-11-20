package redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestCluster {
    @Test
    public void test1(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);

        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        HostAndPort hostAndPort = new HostAndPort("192.168.1.12", 7000);
        HostAndPort hostAndPort1 = new HostAndPort("192.168.1.12", 7001);
        HostAndPort hostAndPort2 = new HostAndPort("192.168.1.12", 7002);
        HostAndPort hostAndPort3 = new HostAndPort("192.168.1.12", 7003);
        HostAndPort hostAndPort4 = new HostAndPort("192.168.1.12", 7004);
        HostAndPort hostAndPort5 = new HostAndPort("192.168.1.12", 7005);
        nodes.add(hostAndPort);nodes.add(hostAndPort1);nodes.add(hostAndPort2);nodes.add(hostAndPort3);nodes.add(hostAndPort4);nodes.add(hostAndPort5);

        JedisCluster jedisCluster = new JedisCluster(nodes, 1000, 1000, 1, poolConfig);
        String s = jedisCluster.get("foo");
        System.out.println(s);

        try {
            jedisCluster.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
