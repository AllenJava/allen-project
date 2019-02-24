package com.infinite.service.dto;

import java.util.List;

/**
 * 
* @ClassName: SsoUserListResponse
* @Description: sso查询用户列表返回结果类
* @author chenliqiao
* @date 2018年1月25日 下午5:00:43
*
 */
public class SsoUserListResponse {
	
	/**结果码**/
	private String resultCode;
	
	/**结果信息**/
	private String message;
	
	/**结果集合**/
	private List<SsoUserInfo> result;

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

	public List<SsoUserInfo> getResult() {
		return result;
	}

	public void setResult(List<SsoUserInfo> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "SsoUserListResponse [resultCode=" + resultCode + ", message=" + message + ", result=" + result + "]";
	}
	
	

}
