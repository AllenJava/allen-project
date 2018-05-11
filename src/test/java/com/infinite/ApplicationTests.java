package com.infinite;

import java.util.concurrent.Executor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	@Autowired
	private  RestTemplate restTemplate;
	
	@Autowired
	private Executor executor;

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

}
