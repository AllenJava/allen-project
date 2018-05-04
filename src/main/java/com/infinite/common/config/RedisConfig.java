package com.infinite.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
* @ClassName: RedisConfig
* @Description: redisTemplate配置类
* @author chenliqiao
* @date 2018年4月9日 上午10:31:28
*
 */
@Configuration
@EnableCaching
public class RedisConfig {
	
	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate){
		CacheManager cacheManager=new RedisCacheManager(redisTemplate);
		return cacheManager;
	}
	
	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		
		//更改序列化方式
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		
		return redisTemplate;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		return stringRedisTemplate;
	}

}
