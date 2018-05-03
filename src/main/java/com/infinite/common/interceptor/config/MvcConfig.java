package com.infinite.common.interceptor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.infinite.common.interceptor.GlobalInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private GlobalInterceptor globalInterceptor;
	
	
	/**
	 * 系统拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(globalInterceptor)
		         //拦截的请求
		        .addPathPatterns("/**")
		         //放行的请求
		        .excludePathPatterns("");
	}

}
