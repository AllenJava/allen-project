package com.infinite.common.mq.redismq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: Receiver
* @Description: 消息订阅者（消费者）
* @author chenliqiao
* @date 2019年4月16日 上午11:34:47
*
 */
@Component
public class Receiver  implements MessageListener{

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);
 
    @Autowired
    private StringRedisTemplate redisTemplate;
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        logger.info("收到的mq消息" + deserialize);
    }
}
