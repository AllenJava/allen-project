package com.infinite.common.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.infinite.common.constant.Constants;

/**
 * 
* @ClassName: Consumer
* @Description: 定义一个消息队列的消费者类
* @author chenliqiao
* @date 2018年5月3日 上午11:34:10
*
 */
//@Component
public class Consumer {
	
	private static final Logger LOG=LoggerFactory.getLogger(Consumer.class);
	
	
	/**
	 * 消费点对点队列的消息
	 */
	@JmsListener(destination="allen.queue")
	public void receiveQueue(String text){
		LOG.info(Constants.Log.MESSAGE_PARAMETER,"consume queue message",text);
	}
	
	/**
	 * 消费topic队列的消息
	 */
	@JmsListener(destination="allen.topic")
	public void recieveSub(String text){
		LOG.info(Constants.Log.MESSAGE_PARAMETER,"consume topic message",text);
	}

}
