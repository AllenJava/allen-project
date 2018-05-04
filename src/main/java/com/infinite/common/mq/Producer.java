package com.infinite.common.mq;

import javax.jms.Queue;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import com.infinite.common.constant.Constants;

/**
 * 
* @ClassName: Producer
* @Description: 消息队列生产者
* @author chenliqiao
* @date 2018年5月3日 上午11:24:28
*
 */
@Component
public class Producer {
	
	private static final Logger LOG=LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Queue queue;
	
	@Autowired
	private Topic topic;
	
	public static final int QUEUE=1;
	
	public static final int TOPIC=2;
	
	/**
	 * 发送消息到消息队列
	 */
	public void send(int type,Object content){
		if(type==QUEUE){
			LOG.info(Constants.Log.MESSAGE_PARAMETER,"put queue message to mq",content);
			this.jmsMessagingTemplate.convertAndSend(this.queue, content);
		}
		else if(type==TOPIC){
			LOG.info(Constants.Log.MESSAGE_PARAMETER,"put topic message to mq",content);
			this.jmsMessagingTemplate.convertAndSend(this.topic, content);
		}else{
			LOG.warn(Constants.Log.MESSAGE_PARAMETER,"type is invalid!",type);
		}
	}

}
