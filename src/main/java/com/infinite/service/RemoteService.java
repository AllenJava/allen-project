package com.infinite.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infinite.common.config.RemoteCallConfig;
import com.infinite.common.constant.Constants;
import com.infinite.common.utils.JsonUtil;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.dto.SsoResponse;
import com.infinite.service.dto.SsoTokenCheck;
import com.infinite.service.dto.SsoUserInfo;
import com.infinite.service.dto.SsoUserListResponse;
import com.infinite.service.dto.SsoUserQuery;

/**
 * 
* @ClassName: RemoteService
* @Description: 远程调用业务类
* @author chenliqiao
* @date 2018年4月4日 上午11:56:10
*
 */
@Service
public class RemoteService {
	
	private static final Logger LOG=LoggerFactory.getLogger(RemoteService.class);
	
	@Resource
	private RestTemplate restTemplate;
	
	@Resource
	private RemoteCallConfig config;
	
	
	/**
	 * 调用sso接口查询token是否有效
	 */
	public boolean checkTokenFromSso(String token){
		SsoTokenCheck request=new SsoTokenCheck(token);
		String jsonResult=null;
		try {	
			//设置参数，并发送请求
			jsonResult=this.restTemplate.postForObject(config.ssoCheckTokenUrl, request, String.class);
			SsoResponse<String> result=JsonUtil.jsonToBean(jsonResult, SsoResponse.class);
			if(result==null||!Constants.Remote.SSO_RESPONSE_STATUS_SUCCESS.getValue().equals(result.getResultCode()))
            	return false;
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(Constants.Log.MESSAGE_ADDRESS_PARAMETER_RESULT,"check token error！",config.ssoCheckTokenUrl,request,jsonResult);
			throw new RuntimeException(e);
		}		
		return true;
	} 
	
	/**
	 * 调用sso接口查询,根据token获取用户信息
	 */
	public SsoUserInfo getSsoUserInfo(String token){
		SsoTokenCheck request=new SsoTokenCheck(token);
		String jsonResult=null;
		try {	
			//设置参数，并发送请求
			jsonResult=this.restTemplate.postForObject(config.ssoCheckTokenUrl, request, String.class);
			SsoResponse<String> result=JsonUtil.jsonToBean(jsonResult, SsoResponse.class);
			if(result==null||!Constants.Remote.SSO_RESPONSE_STATUS_SUCCESS.getValue().equals(result.getResultCode()))
            	return null;
			
			return JsonUtil.jsonToBean(result.getResult(),SsoUserInfo.class);
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(Constants.Log.MESSAGE_ADDRESS_PARAMETER_RESULT,"check token error！",config.ssoCheckTokenUrl,request,jsonResult);
			throw new RuntimeException(e);
		}		
	}
	
	
	/**
	 * 调用sso接口获取用户列表信息
	 */
	public List<UserInfo> getSsoUserList(SsoUserQuery request){
		String jsonResult=null;
		try {
			//设置参数，并发送请求
			request.setSystemPlatform(config.systemPlatform);
			jsonResult=this.restTemplate.postForObject(config.ssoUserQueryUrl, request, String.class);
			SsoUserListResponse result=JsonUtil.jsonToBean(jsonResult, SsoUserListResponse.class);
            if(result==null||!Constants.Remote.SSO_RESPONSE_STATUS_SUCCESS.getValue().equals(result.getResultCode()))
            	throw new Exception();
            
			//转换为本地的用户对象
            if(CollectionUtils.isEmpty(result.getResult())) return null;
            List<UserInfo> userInfoList=new ArrayList<>(); 
            for (SsoUserInfo ssoUserInfo : result.getResult()) {
            	UserInfo userInfo=new UserInfo();
            	BeanUtils.copyProperties(ssoUserInfo, userInfo);
            	userInfo.setStatus(Integer.parseInt(Constants.Remote.getValue(ssoUserInfo.getStatus())));
            	userInfo.setMobile(ssoUserInfo.getPhone());
            	userInfoList.add(userInfo);
			}
            return userInfoList;
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(Constants.Log.MESSAGE_ADDRESS_PARAMETER_RESULT,"query sso user error！",config.ssoUserQueryUrl,request,jsonResult);
			throw new RuntimeException(e);
		}
	}

}
