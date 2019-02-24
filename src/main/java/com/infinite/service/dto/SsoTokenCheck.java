package com.infinite.service.dto;

/**
 * 
* @ClassName: SsoTokenCheck
* @Description: sso远程服务校验token请求参数
* @author chenliqiao
* @date 2018年4月4日 下午12:03:16
*
 */
public class SsoTokenCheck {
	
	/**sso统一登录的token**/
	private String token;
	
	public SsoTokenCheck(String token){
		this.token=token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "SsoTokenCheck [token=" + token + "]";
	}


}
