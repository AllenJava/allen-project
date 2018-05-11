package com.infinite.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;
import com.infinite.controller.vo.ResultBean;

/**
 * 
* @ClassName: RateLimiterTestController
* @Description: RateLimiter限流（单应用，分布式考虑redis实现）
* @author chenliqiao
* @date 2018年5月11日 下午4:19:35
*
 */
@RestController
public class RateLimiterTestController {
	
	/**一秒生成5个令牌**/
	private static final double PERMITS=5;
	
	RateLimiter rateLimiter=RateLimiter.create(PERMITS);
	
	@RequestMapping(value="/rateLimiter/getOrderNO")
	public ResultBean<String> getOrderNO(){
		/** 
	     * tryAcquire(long timeout, TimeUnit unit) 
	     * 从RateLimiter 获取许可如果该许可可以在不超过timeout的时间内获取得到的话， 
	     * 或者如果无法在timeout 过期之前获取得到许可的话，那么立即返回false（无需等待） 
	     */  
		if(!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
			System.out.println("获取失败!");
			return new ResultBean<>("error!");
		}
		
        String orderNo="NO"+System.currentTimeMillis();
		System.out.println("获取成功："+orderNo);
		return new ResultBean<String>(orderNo);
	}

}
