import org.junit.Before;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test {
    JedisPool pool;
    Jedis jedis;

    @Before
   public void setup(){
       pool = new JedisPool(new JedisPoolConfig(), "192.168.1.12", 7000, 1000);
       jedis = pool.getResource();
   }

    @org.junit.Test
    public void testGet() {
        System.out.println(jedis.get("test1"));
    }

    @org.junit.Test
    public void testString(){
        jedis.set("name","jin");
        jedis.expire("name", 10);
        System.out.println(jedis.get("name"));

//        jedis.mset("name", "minxr", "jarorwar", "tony");
//        System.out.println(jedis.mget("name", "jarorwar"));
    }
}
