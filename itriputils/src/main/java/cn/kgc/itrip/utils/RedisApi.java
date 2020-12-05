package cn.kgc.itrip.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class RedisApi {
    @Resource
    private JedisPool jedisPool;

    /**
     * 添加数据
     * @param key
     * @param value
     */
    public void set(String key,String value){
        Jedis resource = jedisPool.getResource();
        resource.set(key,value);
        //将连接放回到连接池中
        jedisPool.returnBrokenResource(resource);
    }

    public Long del(String ... key){
        Jedis resource = jedisPool.getResource();
        Long rowCount =resource.del(key);
        jedisPool.returnBrokenResource(resource);
        return rowCount;
    }

    /**
     * 设置带有过期时间的数据
     * @param key
     * @param second
     * @param value
     */
    public void setEx(String key,Integer second,String value){
        Jedis resource=jedisPool.getResource();
        resource.setex(key,second,value);
        jedisPool.returnBrokenResource(resource);
    }

    /**
     * 查询过期时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        Jedis resource = jedisPool.getResource();
        Long ttl = resource.ttl(key);
        jedisPool.returnBrokenResource(resource);
        return ttl;
    }

    /**
     * 判断指定的token是否存在
     * @param token
     * @return
     */
    public boolean exists(String token){
        Jedis resource=jedisPool.getResource();
        Boolean exists = resource.exists(token);
        jedisPool.returnBrokenResource(resource);
        return exists;
    }

    /**
     * 根据token获取指定的数据
     * @param token
     * @return
     */
    public String get(String token){
        Jedis resource=jedisPool.getResource();
        String s = resource.get(token);
        jedisPool.returnBrokenResource(resource);
        return s;
    }
}
