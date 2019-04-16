package com.infinite.common.mq.redismq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: Publisher
* @Description: 消息发布者（生产者）
* @author chenliqiao
* @date 2019年4月16日 上午11:32:45
*
 */
@Component
public class Publisher {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
 
    public String sendMessage(String name) {
        try {
            redisTemplate.convertAndSend("TOPIC_USERNAME", name);
            return "消息发送成功了";
 
        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }

}
