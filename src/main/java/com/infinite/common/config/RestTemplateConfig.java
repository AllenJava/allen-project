package com.infinite.common.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * restTemplate配置类
 * @author allen
 *
 */
@Configuration
public class RestTemplateConfig {
	
	/**
	 * 初始化RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate(){
		//根据http客户端工厂配置初始化RestTemplate
		RestTemplate restTemplate=new RestTemplate(clientHttpRequestFactory());	
		
		//自定义HttpMessageConverter转换器
		this.customSetHttpMessageConverter(restTemplate); 
		
		return restTemplate;
	}
	
	/**
	 * 初始化http客户端工厂
	 */
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory(){		
		HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
		
		/**
		 * 配置连接池
		 */
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager=new PoolingHttpClientConnectionManager();
		//最大连接数
		poolingHttpClientConnectionManager.setMaxTotal(2000);
		//同路由最大并发数（限制实际使用数量是由DefaultMaxPerRoute决定，而非MaxTotal，如果DefaultMaxPerRoute设置过小，无法支持大并发请求）
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(200);
		httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
		
		//重试次数（默认不开启用，即false）
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3,true));
		
		/**
		 * 创建http客户端对象
		 */
		HttpClient httpClient=httpClientBuilder.build();

		/**
		 * 创建http客户端工厂，并设置超时间等属性
		 */
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory=new HttpComponentsClientHttpRequestFactory(httpClient);
		//连接超时时间
		clientHttpRequestFactory.setConnectTimeout(2000);
		//数据读取超时时间
		clientHttpRequestFactory.setReadTimeout(20000);
		//连接不够用时的等待时间
		clientHttpRequestFactory.setConnectionRequestTimeout(5000);
		
		return clientHttpRequestFactory;
	}
	
	/**
	 * 自定义设置HttpMessageConverter转换器
	 */
	private void customSetHttpMessageConverter(RestTemplate restTemplate){
		List<HttpMessageConverter<?>> messageConverters=restTemplate.getMessageConverters();
		for (int i = 0; i < messageConverters.size(); i++) {
			/**
			 * 	1.重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
			 */
			if(StringHttpMessageConverter.class==messageConverters.get(i).getClass()){
				restTemplate.getMessageConverters().set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
			}
			
			/**
			 * 2.加入FastJson转换器
			 * 注：spring的json转换器默认使用的是Jackson，json字符串和对应的Entity如果有字段对不上就会报错，
			 *     而FastJson则不会报错，所以很多时候都会用FastJSON替换默认的Jackson。
			 */
			if(MappingJackson2HttpMessageConverter.class==messageConverters.get(i).getClass()){
				restTemplate.getMessageConverters().set(i, new FastJsonHttpMessageConverter());
			}
		}
	}

}
