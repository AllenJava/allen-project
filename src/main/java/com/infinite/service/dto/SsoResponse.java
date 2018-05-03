package com.infinite.service.dto;

/**
 * 
* @ClassName: SsoResponse
* @Description: 调用sso服务返回结果类
* @author chenliqiao
* @date 2018年4月4日 下午12:08:16
* 
* @param <T>
 */
public class SsoResponse<T> {
	
	/**结果码**/
	private String resultCode;
	
	/**结果信息**/
	private String message;
	
	/**结果集合**/
	private T result;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SsoResponse [resultCode=" + resultCode + ", message=" + message + ", result=" + result + "]";
	}
	
	

}
