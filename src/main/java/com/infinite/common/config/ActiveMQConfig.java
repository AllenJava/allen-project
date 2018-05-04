package com.infinite.common.config;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * 
* @ClassName: ActiveMQConfig
* @Description: activeMQ消息队列配置类
* @author chenliqiao
* @date 2018年5月3日 上午11:05:16
*
 */
@Configuration
@EnableJms
public class ActiveMQConfig {
	
	/**
	 * 定义一个点对点队列
	 */
	@Bean
	public Queue queue(){
		return new ActiveMQQueue("allen.queue");
	}
	
	/**
	 * 定义一个主题
	 */
	@Bean
	public Topic topic(){
		return new ActiveMQTopic("allen.topic");
	}

}
