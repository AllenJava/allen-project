package com.infinite.controller.vo;

import com.infinite.common.constant.Constants;

/**
 * 
* @ClassName: ResultBean
* @Description: 接口返回通用类
* @author chenliqiao
* @date 2018年4月3日 下午5:22:02
* 
* @param <T>
 */
public class ResultBean<T> {
	
	private Integer code;
	
	private String message;
	
	private T data;
	
	public ResultBean(){
		this.code=Constants.ApiResult.SUCCESS.getCode();
		this.message=Constants.ApiResult.SUCCESS.getMessage();
	}
	
	public ResultBean(T data){
		this.code=Constants.ApiResult.SUCCESS.getCode();
		this.message=Constants.ApiResult.SUCCESS.getMessage();
		this.data=data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultBean [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	

}
