package com.infinite.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.infinite.service.SecurityService;

/**
 * 
* @ClassName: GlobalInterceptor
* @Description: 系统全局拦截器
* @author chenliqiao
* @date 2018年4月4日 上午11:05:11
*
 */
@Component
public class GlobalInterceptor implements HandlerInterceptor{ 
	
	@Autowired
	private SecurityService securityService;

	/**
	 * 控制层controller执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果不是映射到方法上的，直接放行
		if(!(handler instanceof HandlerMethod)) return true;
		
		//获取请求的方法
		HandlerMethod handlerMethod=(HandlerMethod) handler;
		Method method=handlerMethod.getMethod();
		
		//检查是否已登录
		if(!this.securityService.checkIfLogined(request, response, method)) return false;
		
		//检查是否拥有权限
		if(!this.securityService.checkIfPermitted(request, response, method))  return false;
		
		return true;
	}

	/**
	 * 控制层controller执行之后，且视图渲染之前调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 视图渲染之后调用，一般用于资源清理操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
