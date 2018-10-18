package com.infinite.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.infinite.common.constant.Constants;
import com.infinite.common.exception.BusinessException;
import com.infinite.controller.vo.ResultBean;

/**
 * 
* @ClassName: ControllerAspect
* @Description: controller控制层切面（用于该层的异常处理和日志打印）
* @author chenliqiao
* @date 2018年4月9日 下午3:35:54
*
 */
@Aspect
@Component
public class ControllerAspect {
	
	private static final Logger LOG=LoggerFactory.getLogger(ControllerAspect.class);
	
	/**
	 * 定义切点
	 */
	@Pointcut("execution(public * com.infinite.controller..*.*(..))")
	public void controllerAspect(){	
	}
	
	@Around("controllerAspect()")
	public Object handlerControllerMethod(ProceedingJoinPoint pjp){
		ResultBean<?> result;
		
		try {
			result=(ResultBean<?>) pjp.proceed();
			LOG.info(pjp.getSignature().getName());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			result=this.handlerException(pjp, e);
		}
		
		return result;
	}
	
	/**
	 * 异常统一处理
	 */
	private ResultBean<?> handlerException(ProceedingJoinPoint pjp,Throwable e){
		ResultBean<?> result=new ResultBean<>();
		
		//已知异常
		if(e instanceof BusinessException){
			result.setCode(Constants.ApiResult.FAIL.getCode());
			result.setMessage(e.getLocalizedMessage());
		}
		//未知异常
		else{
			LOG.error(pjp.getSignature().getName());
			result.setCode(Constants.ApiResult.SYSTEM_ERROR.getCode());
			result.setMessage(Constants.ApiResult.SYSTEM_ERROR.getMessage());
		}
		
		return result;
	}

}
