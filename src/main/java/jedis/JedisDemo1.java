package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author: baifengxiao
 * @date: 2022/4/6 11:46
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.44.101", 6379);
        String value = jedis.ping();
        System.out.println(value);
        jedis.close();
    }

    //String操作
    @Test
    public void demo01() {
        Jedis jedis = new Jedis("192.168.44.101", 6379);

        //添加
        jedis.set("name", "lucy");
        //获取
        String name = jedis.get("name");
        System.out.println(name);

        //多个
        jedis.mset("k1", "v1", "k2", "v2");
        List<String> list = jedis.mget("k1", "k2");
        for (String key : list) {
            System.out.println(key);
        }

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    //List操作
    @Test
    public void demo02() {
        Jedis jedis = new Jedis("192.168.44.101", 6379);
        jedis.lpush("key1", "lucy", "mary", "jack");
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println(values);
    }

    //Set操作
    @Test
    public void demo03() {
        Jedis jedis = new Jedis("192.168.44.101", 6379);
        jedis.sadd("setname", "lucy", "mary", "jack");
        Set<String> names = jedis.smembers("setname");
        System.out.println(names);
    }

    //hash操作
    @Test
    public void demo04() {
        Jedis jedis = new Jedis("192.168.44.101", 6379);
        jedis.hset("hashsetname", "name", "lucy");
        String hget = jedis.hget("hashsetname", "name");
        System.out.println(hget);
    }

    //hash操作
    @Test
    public void demo05() {
        Jedis jedis = new Jedis("192.168.44.101", 6379);
        jedis.zadd("china", 100d, "shanghai");
        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }


}
