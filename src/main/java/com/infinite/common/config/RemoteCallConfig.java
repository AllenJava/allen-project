package com.infinite.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: RemoteCallConfig
* @Description: 远程服务调用配置类
* @author chenliqiao
* @date 2018年4月4日 下午2:15:13
*
 */
@Component
public class RemoteCallConfig {
	
	/**sso检查token接口地址**/
	@Value("${SSO_CHECK_TOKEN_URL}")
	public String ssoCheckTokenUrl;
	
	/**sso服务调用方的平台类型**/
	@Value("${EXCLUSIVE_SYSTEM}")
	public String systemPlatform;
	
	/**sso用户信息查询接口地址**/
	@Value("${SSO_CENTER__USER_QUERY_URL}")
	public String ssoUserQueryUrl;
	
	/**sso系统token中转页地址**/
	@Value("${SSO_LOGIN_SERVICE_URL}")
	public String ssoLoginIndexUrl;
	
	/**sso系统登录页地址**/
	@Value("${SSO_LOGIN_PAGE_URL}")
	public String ssoLoginPageUrl;
	
	/**系统前端首页地址**/
	@Value("${SYSTEM_FRONT_INDEX_URL}")
	public String systemIndexUrl;

}
