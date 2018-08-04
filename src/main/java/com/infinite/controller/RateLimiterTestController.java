package com.infinite.controller;

import java.util.concurrent.Semaphore;
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
	
	/**令牌桶容量，一秒生成5个令牌**/
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
	
	public static void main(String[] args) throws Exception {
		/**
		 * 平滑限流
		 */
		RateLimiter rateLimiter=RateLimiter.create(5);
		/**
		 * acquire令牌桶中有足够令牌时，则直接消费，
		 * 没有足够时，则消费未来的令牌，后续acquire调用时，需要等待已消费的未来令牌生产所需时长
		 */
		System.out.println("a:"+rateLimiter.acquire(5));
		TimeUnit.SECONDS.sleep(1);
		System.out.println("b:"+rateLimiter.acquire(5));
		TimeUnit.SECONDS.sleep(1);
		System.out.println("c:"+rateLimiter.acquire(1));
		System.out.println("d:"+rateLimiter.acquire(1));
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					rateLimiter.acquire(5);
					System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
				}
			}).start();
		}
		
		/**
		 * 简单限流
		 */
		Semaphore semaphore=new Semaphore(5);
		Long startTime=System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						semaphore.acquire();
						System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						//semaphore.release();
					}
				}
			}).start();
		}
		System.out.println("消耗时长："+(System.currentTimeMillis()-startTime));
	}

}
