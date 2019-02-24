package com.infinite.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 
* @ClassName: TaskPoolConfig
* @Description: 自定义线程池
* @author chenliqiao
* @date 2018年5月3日 上午11:43:35
*
 */
@Configuration
@EnableAsync
public class TaskPoolConfig {
	
	@Bean
	public Executor executor(){
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(50);
		executor.setKeepAliveSeconds(60);
		executor.setThreadNamePrefix("allen-task-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}
}
