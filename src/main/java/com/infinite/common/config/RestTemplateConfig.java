package com.infinite.common.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
	/**连接池最大连接数**/
	private final int maxTotal=2000;
	
	/**同路由最大并发数**/
	private final int defaultMaxPerRoute=200;
	
	/**重试次数**/
	private final int retryCount=3;
	
	/**连接超时时间**/
	private final int connectTimeout=5000;
	
	/**数据读取超时时间**/
	private final int readTimeout=10000;
	
	/**连接不够用时的等待时间**/
	private final int connectionRequestTimeout=5000;
	
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
		poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
		//同路由最大并发数（限制实际使用数量是由DefaultMaxPerRoute决定，而非MaxTotal，如果DefaultMaxPerRoute设置过小，无法支持大并发请求）
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
		
		//重试次数（默认不开启用，即false）
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount,true));
		
		/**
		 * 创建http客户端对象
		 */
		HttpClient httpClient=httpClientBuilder.build();

		/**
		 * 创建http客户端工厂，并设置超时间等属性
		 */
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory=new HttpComponentsClientHttpRequestFactory(httpClient);
		//连接超时时间
		clientHttpRequestFactory.setConnectTimeout(connectTimeout);
		//数据读取超时时间
		clientHttpRequestFactory.setReadTimeout(readTimeout);
		//连接不够用时的等待时间
		clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
		
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
			
//			/**
//			 * 2.加入FastJson转换器
//			 * 注：spring的json转换器默认使用的是Jackson，json字符串和对应的Entity如果有字段对不上就会报错，
//			 *     而FastJson则不会报错，所以很多时候都会用FastJSON替换默认的Jackson。
//			 */
//			if(MappingJackson2HttpMessageConverter.class==messageConverters.get(i).getClass()){
//				restTemplate.getMessageConverters().set(i, new FastJsonHttpMessageConverter());
//			}
			
			/**
             * 2.加入FastJson转换器
             * 注a：spring的json转换器默认使用的是Jackson，json字符串和对应的Entity如果有字段对不上就会报错，
             *     而FastJson则不会报错，所以很多时候都会用FastJSON替换默认的Jackson。
             * 注b：
             * fastjson从1.1.41升级到1.2.28之后，请求报错：json java.lang.IllegalArgumentException: 'Content-Type' cannot contain wildcard type '*'
             *  原因是在1.1.41中，FastJsonHttpMessageConverter初始化时，设置了MediaType。
             *  public FastJsonHttpMessageConverter(){
             *      super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
             *  }
             *  而在1.2.28中，设置的MediaType为‘/’，即：
             *  public FastJsonHttpMessageConverter() {
             *  super(MediaType.ALL);  // 
             *  }
             *  后续在org.springframework.http.converter.AbstractHttpMessageConverter.write过程中，又要判断Content-Type不能含有通配符，这应该是一种保护机制,并强制用户自己配置MediaType
             */
            if(MappingJackson2HttpMessageConverter.class==messageConverters.get(i).getClass()){
                 FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
                 List<MediaType> supportedMediaTypes = new ArrayList<>();
                 supportedMediaTypes.add(MediaType.APPLICATION_JSON);
                 supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
                 supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
                 supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
                 supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
                 supportedMediaTypes.add(MediaType.APPLICATION_PDF);
                 supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
                 supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
                 supportedMediaTypes.add(MediaType.APPLICATION_XML);
                 supportedMediaTypes.add(MediaType.IMAGE_GIF);
                 supportedMediaTypes.add(MediaType.IMAGE_JPEG);
                 supportedMediaTypes.add(MediaType.IMAGE_PNG);
                 supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
                 supportedMediaTypes.add(MediaType.TEXT_HTML);
                 supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
                 supportedMediaTypes.add(MediaType.TEXT_PLAIN);
                 supportedMediaTypes.add(MediaType.TEXT_XML);
                 fastConverter.setSupportedMediaTypes(supportedMediaTypes);
                restTemplate.getMessageConverters().set(i, fastConverter);
            }
		}
	}

}
