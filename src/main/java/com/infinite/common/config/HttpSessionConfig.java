package com.infinite.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 
* @ClassName: HttpSessionConfig
* @Description: spring session 配置类
* @author chenliqiao
* @date 2018年4月24日 下午2:37:48
*
 */
@Configuration
@EnableRedisHttpSession//注解开启redis httpSession
public class HttpSessionConfig {
}
