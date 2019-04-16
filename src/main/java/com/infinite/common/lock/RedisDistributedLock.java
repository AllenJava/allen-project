package com.infinite.common.lock;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * 
* @ClassName: RedisDistributedLock
* @Description: redis分布式锁
* @author chenliqiao
* @date 2019年4月1日 上午11:36:15
*
 */
public class RedisDistributedLock implements DistributedLock{
    
    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
    
    private StringRedisTemplate stringRedisTemplate;

    //释放锁的原子lua脚本
    public static final String UNLOCK_LUA;
    
    //存储当前请求线程的锁value
    private static final ThreadLocal<String> CURRENT_THREAD_LOCK_VALUE=new ThreadLocal<>();
    
    static{
        UNLOCK_LUA="if(redis.call('get',KEYS[1])==ARGV[1])"+
                  "then"+
                  "return redis.call('del',KEYS[1])"+
                  "else"+
                  "return 0"+
                  "end";
    }
    
    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }

    @Override
    public boolean tryLock(String key, long expire) {
        // 将value设置为当前时间戳+随机数
        String value = System.currentTimeMillis() + UUID.randomUUID().toString();

        System.out.println(Thread.currentThread().getName()+"->"+value);
        //原子调用setnx和expire
        String result=stringRedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection=connection.getNativeConnection();
                if(nativeConnection instanceof Jedis){
                    return ((Jedis) nativeConnection).set(key, value, "NX", "PX", expire);
                }else if(nativeConnection instanceof JedisCluster){
                    return ((JedisCluster) nativeConnection).set(key, value, "NX", "PX", expire);
                }
                return "";
            }
        });
        
        //加锁成功
        if(Objects.equals("ok", result)){
            CURRENT_THREAD_LOCK_VALUE.set(value);
            return true;
        }
        return false;
    }

    @Override
    public boolean unLock(String key) {
        List<String> keys=Arrays.asList(key);
        List<String> values=Arrays.asList(CURRENT_THREAD_LOCK_VALUE.get());
        try {
            Long result=stringRedisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnection=connection.getNativeConnection();
                    if(nativeConnection instanceof Jedis){
                        return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, values);
                    }else if(nativeConnection instanceof JedisCluster){
                        return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, values);
                    }
                    return 0L;
                }
            });
            
            //释放锁成功，清除CURRENT_THREAD_LOCK_VALUE中的value
            if(result!=null && result>0){
                CURRENT_THREAD_LOCK_VALUE.remove();
                return true;
            }
        } catch (Exception e) {
            logger.error("unlock redis distribute lock fail!", e);
        }
        return false;
    }

}
