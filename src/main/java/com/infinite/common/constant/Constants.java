package com.infinite.common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 
* @ClassName: Constants
* @Description: 公共常量类
* @author chenliqiao
* @date 2018年4月9日 下午6:01:51
*
 */
public class Constants {
	
	/**鉴权token**/
	public static final String TOKEN="token";
	
	/**返回给前端的sso登录首页地址的参数名**/
	public static final String SSO_LOGIN_INDEX_URL_NAME="ssoLoginIndexUrl";
	
	/**登录用户信息缓存在redis中的过期时间（单位：分钟）**/
	public static final long CUR_USER_CACHING_EXPIRE_TIME=30;
	
	/**
	 * 
	* @ClassName: ApiResult
	* @Description: Api接口返回状态类
	* @author chenliqiao
	* @date 2018年4月4日 上午9:36:18
	*
	 */
	public enum ApiResult {

		SUCCESS(1,"success!"),
		
		FAIL(0,"fail!"),
		
		TOKEN_IS_NULL(401,"token can not be null!"),
		
		TOKEN_IS_INVALID(401,"token is invalid!"),
		
		NOT_ENOUGH_PERMISSION(401,"not enough permission!"),
		
		SYSTEM_ERROR(500,"system exceptions!");
		
		
		private int code;
		
		private String message;
		
		ApiResult(int code,String message){
			this.code=code;
			this.message=message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		
	}
	
	/**
	 * 
	* @ClassName: Remote
	* @Description: 远程服务调用常量类
	* @author chenliqiao
	* @date 2017年12月31日 下午9:26:26
	*
	 */
	public enum Remote {
		
		/**SSO服务调用返回状态码：成功**/
		SSO_RESPONSE_STATUS_SUCCESS("1","1"),
		
		/**SSO用户的状态**/
		SSO_USER_STATUS_YES("Y","1"),
		
		/**SSO用户的状态**/
		SSO_USER_STATUS_NO("N","0");
		
	    private String key;
		
		private String value;
		
		private Remote(String key,String value){
			this.key=key;
			this.value=value;
		}
		
		public static String getValue(String key){
			String value="";
			if(StringUtils.isEmpty(key)) return value;
			for (Remote entity : values()) {
				if(key.equals(entity.getKey())){
					value=entity.getValue();
					break;
				}
			}
			return value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	
	/**
	 * 
	* @ClassName: Log
	* @Description: 日志工具常量类
	* @author chenliqiao
	* @date 2018年4月4日 上午9:42:33
	*
	 */
	public interface Log {
		
		/**日志输出信息表达式-->消息**/
		public final String MESSAGE="Message-->{}";
		
		/**日志输出信息表达式-->参数**/
		public final String PARAMETER="Parameter-->{}";
		
		/**日志输出信息表达式-->消息-参数**/
		public final String MESSAGE_PARAMETER="Message-->{} - Parameter-->{}";
		
		/**日志输出信息表达式-->消息-参数-结果**/
		public final String MESSAGE_PARAMETER_RESULT="Message-->{} - Parameter-->{} - Result-->{}";
		
		/**日志输出信息表达式-->消息-地址**/
		public final String MESSAGE_ADDRESS="Message-->{} - Address-->{}";
		
		/**日志输出信息表达式-->消息-地址-结果**/
		public final String MESSAGE_ADDRESS_RESULT="Message-->{} - Address-->{} - Result-->{}";
		
		/**日志输出信息表达式-->消息-地址-参数-结果**/
		public final String MESSAGE_ADDRESS_PARAMETER_RESULT="Message-->{} - Address-->{} - Parameter-->{} - Result-->{}";

	}


}
