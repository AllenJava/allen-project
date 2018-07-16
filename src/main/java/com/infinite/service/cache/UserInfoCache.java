package com.infinite.service.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.infinite.common.utils.JsonUtil;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.UserInfoService;

/**
 * 
* @ClassName: UserInfoCache
* @Description: 用户信息缓存
* @author chenliqiao
* @date 2018年7月13日 下午4:40:01
*
 */
@Component
public class UserInfoCache {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private UserInfoService userInfoService;
    
    private static final String USER_CACHE_REDIS_KEY="user_cache_redis_key_%s";
    
    @Autowired
    private Executor executor;
    
    public UserInfo getUserInfo(Integer userId){
        UserInfo userInfo=null;
        String key=String.format(USER_CACHE_REDIS_KEY, userId);
        Object result=this.redisTemplate.opsForValue().get(key);      
        if(result!=null){
            System.out.println("缓存命中，走缓存！");
            userInfo=JsonUtil.jsonToBean(String.valueOf(result), UserInfo.class); 
            return userInfo;
        }
        
        //没命中缓存，走DB
        userInfo=this.userInfoService.findById(userId);
        System.out.println("缓存不命中，走DB！");
        if(userInfo!=null){
            //注意：此处设置过期时间，在高并发的情况下，如果大量的key在同一时刻过期失效，可能会造成缓存雪崩。
            //this.redisTemplate.opsForValue().set(key, JsonUtil.beanToJson(userInfo), 1800, TimeUnit.SECONDS);
            
            //防止缓存雪崩：在每个过期时间后+随机时间值，防止大量key的过期时间相同，在同一个时刻失效，请求全部转发到DB，DB瞬时压力过重雪崩。
            this.redisTemplate.opsForValue().set(key, JsonUtil.beanToJson(userInfo), 1800+RandomUtils.nextLong(1, 999), TimeUnit.SECONDS);
        }
        
        //没命中DB，说明数据不存在，为了防止高并发时造成缓存穿透，也设置null值到缓存中,并设置过期时间
        System.out.println("走DB也不命中，设置缓存穿透策略!");
        this.redisTemplate.opsForValue().set(key, "", 1, TimeUnit.MINUTES);
        
        return userInfo;
    }
    
    /**
     * 根据互斥锁获取
     */
    public UserInfo getUserInfoByMutex(Integer userId){
        UserInfo userInfo=null;
        String key=String.format(USER_CACHE_REDIS_KEY, userId);
        Object result=this.redisTemplate.opsForValue().get(key);   
        if(result!=null){
            System.out.println("缓存命中，走缓存！");
            userInfo=JsonUtil.jsonToBean(String.valueOf(result), UserInfo.class); 
            return userInfo;
        }
        
        //没命中缓存，走DB
        //为了防止缓存击穿（缓存在某个时间点过期的时候，恰好在这个时间点对这个Key有大量的并发请求过来，这些请求发现缓存过期一般都会从后端DB加载数据并回设到缓存，这个时候大并发的请求可能会瞬间把后端DB压垮。）
        //加互斥锁
        String mutexKey="mutex_key";
        if(this.redisTemplate.opsForValue().setIfAbsent(mutexKey, mutexKey)){
            System.out.println("没命中缓存，走DB！");
            //设置互斥锁过期时间(防止主动释放锁失败时，锁能够自动释放) 
            this.redisTemplate.expire(mutexKey, 5, TimeUnit.SECONDS);
            
            //重新从数据库加载数据
            userInfo=this.userInfoService.findById(userId);
            this.redisTemplate.opsForValue().set(key, JsonUtil.beanToJson(userInfo), 1800+RandomUtils.nextLong(1, 999), TimeUnit.SECONDS);
            
            //释放锁
            this.redisTemplate.delete(mutexKey);
            
            return userInfo;
        }else{
            //其他线程获取不到锁，等待50毫秒之后，重新获取
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                getUserInfoByMutex(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
                
            }
            System.out.println("ssss");
            return null;
        }        
    }
    
    
    /**
     * 根据value内设置过期时间获取（永不过期）
     * 从功能上看，如果不过期，那不就成静态的了吗？所以我们把过期时间存在key对应的value里，如果发现要过期了，通过一个后台的异步线程进行缓存的构建，也就是“逻辑”过期
     * 从实战看，这种方法对于性能非常友好，唯一不足的就是构建缓存时候，其余线程(非构建缓存的线程)可能访问的是老数据，但是对于一般的互联网功能来说这个还是可以忍受。
     */
    public UserInfo getUserInfoByValueTime(Integer userId){
        String key=String.format(USER_CACHE_REDIS_KEY, userId);
        Object result=this.redisTemplate.opsForValue().get(key);   
        Map<String,Object> value=JsonUtil.jsonToBean(String.valueOf(result), Map.class);
        Long endTime=value!=null?Long.valueOf(String.valueOf(value.get("endTime"))):0;
        //根据value中自定义的过期时间判断，未过期
        if(endTime>System.currentTimeMillis()/1000){
            System.out.println("缓存命中，走缓存！");
            return JsonUtil.jsonToBean(String.valueOf(value.get("userInfo")), UserInfo.class);
        }
        
        //根据value中自定义的过期时间判断，已过期,异步更新
        executor.execute(new Runnable() {            
            @Override
            public void run() {
                // TODO Auto-generated method stub
              //互斥锁
              String mutexKey="mutex_key";
              if(redisTemplate.opsForValue().setIfAbsent(mutexKey, mutexKey)){
                  System.out.println("没命中缓存，走DB！");
                  
                  //设置互斥锁过期时间(防止主动释放锁失败时，锁能够自动释放) 
                  redisTemplate.expire(mutexKey, 5, TimeUnit.SECONDS);
                  
                  //重新从数据库加载数据
                  UserInfo userInfo=userInfoService.findById(userId);userInfo.setName("new name3");
                  Map<String,Object> value=new HashMap<>();
                  value.put("userInfo", userInfo);
                  value.put("endTime", System.currentTimeMillis()/1000+3);
                  redisTemplate.opsForValue().set(key, JsonUtil.beanToJson(value));
                  
                  //释放互斥锁
                  redisTemplate.delete(mutexKey);
              }
            }
        });
        
        //返回旧值（单位时间越小，返回旧值越多，比如20个线程/每秒，或者20个线程/每半秒）
        return JsonUtil.jsonToBean(String.valueOf(value.get("userInfo")), UserInfo.class);
    }
    
}
