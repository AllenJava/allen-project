package com.infinite;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.infinite.common.lock.RedisDistributedLock;
import com.infinite.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	@Autowired
	private  RestTemplate restTemplate;
	
	@Autowired
	private Executor executor;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test(){
		for (int i = 0; i < 20; i++) {
			this.executor.execute(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(restTemplate.getForObject("http://localhost:3333/hystrixAdd", String.class));
				}
			});
			
		}
	}
	
	@Test
	public void testRestConfig(){
		String result=this.restTemplate.postForObject("http://localhost:2222/api/order/queryList", null, String.class);
		System.out.println(result);
	}
	
	@Test
	public void testUserService(){
	    //this.userInfoService.testTransaction1();
	    this.userInfoService.testTransaction2();
	}
	
	@Test
	public void testRedisDistributeLock(){
	    System.out.println("test redis distribute lock start======");
	    for (int i = 0; i < 5; i++) {
	        new Thread(()->{
	            RedisDistributedLock lock=new RedisDistributedLock(stringRedisTemplate);
                if(lock.tryLock("test_redis_distribute_lock", 60000)){
                    try {
                        System.out.println(Thread.currentThread().getName()+" acquire lock success!");
                    } finally {
                        lock.unLock("test_redis_distribute_lock");
                    }
                }else{
                    System.out.println(Thread.currentThread().getName()+" acquire lock failure!");
                }
	        }).start();
	    }
	}

}
