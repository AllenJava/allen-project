package com.infinite.common.exception;

/**
 * 
* @ClassName: BusinessException
* @Description: 自定义业务异常类
* @author chenliqiao
* @date 2018年4月9日 下午5:49:57
*
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
