package com.infinite.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.infinite.common.annotation.LoginRequire;
import com.infinite.common.annotation.PermissionRequire;
import com.infinite.common.config.RemoteCallConfig;
import com.infinite.common.constant.Constants;
import com.infinite.common.utils.JsonUtil;
import com.infinite.controller.vo.ResultBean;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.bo.CurrentUserInfo;
import com.infinite.service.dto.SsoUserInfo;

/**
 * 
* @ClassName: SecurityService
* @Description: 系统登录鉴权业务类
* @author chenliqiao
* @date 2018年4月4日 上午11:04:03
*
 */
@Service
public class SecurityService {
	
    private static final Logger LOG=LoggerFactory.getLogger(SecurityService.class);
    
    @Autowired
    private RemoteService remoteService;
    
    @Autowired
	private RemoteCallConfig config;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private RoleInfoService roleInfoService;
	
	@Autowired
	private PermissionInfoService permissionInfoService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	/**
	 * 检查是否已登录
	 */
	public boolean checkIfLogined(HttpServletRequest request, HttpServletResponse response, Method method) throws Exception{ 
		ResultBean<Object> result=new ResultBean<>();
		try {
			//检查请求方法是否有检查登录注解（LoginRequire）
			Annotation loginRequire=method.getAnnotation(LoginRequire.class);
			if(loginRequire==null) return true;

            //token为空
            if(StringUtils.isEmpty(request.getHeader(Constants.TOKEN))){
            	this.setSsoLoginUrl(result, Constants.ApiResult.TOKEN_IS_NULL.getCode(),Constants.ApiResult.TOKEN_IS_NULL.getMessage()); 
            	response.setContentType("application/json;charset=UTF-8");
        		response.getWriter().write(JsonUtil.beanToJson(result));
        		return false;
            }
            //token无效
            if(!this.remoteService.checkTokenFromSso(request.getHeader(Constants.TOKEN))){
            	this.setSsoLoginUrl(result, Constants.ApiResult.TOKEN_IS_INVALID.getCode(),Constants.ApiResult.TOKEN_IS_INVALID.getMessage());
        		response.getWriter().write(JsonUtil.beanToJson(result));
        		return false;
            }
            
            //缓存登录信息
            this.saveLoginInfoToCache(request.getHeader(Constants.TOKEN));
            
		} catch (Exception e) {
			LOG.error(Constants.Log.MESSAGE,"auth error!",e);
			this.setSsoLoginUrl(result, Constants.ApiResult.SYSTEM_ERROR.getCode(),Constants.ApiResult.SYSTEM_ERROR.getMessage());
    		response.getWriter().write(JsonUtil.beanToJson(result));
    		return false;
		}
		return true;
	}	
	
	/**
	 * 检查是否拥有权限访问
	 */
	public boolean checkIfPermitted(HttpServletRequest request, HttpServletResponse response, Method method) throws Exception{
		ResultBean<Object> result=new ResultBean<>();
		try {
			//检查请求方法是否有检查权限注解（PermissionRequire）
			PermissionRequire permissionRequire=method.getAnnotation(PermissionRequire.class);
			if(permissionRequire==null) return true;
			
			//检查是否拥有权限
			if(!this.ifOwnPermission(permissionRequire.name(),request.getHeader(Constants.TOKEN))){
				result.setCode(Constants.ApiResult.NOT_ENOUGH_PERMISSION.getCode());
				result.setMessage(Constants.ApiResult.NOT_ENOUGH_PERMISSION.getMessage());
				response.getWriter().write(JsonUtil.beanToJson(result));
				return false;
			}
		} catch (Exception e) {
			LOG.error(Constants.Log.MESSAGE,"check if own permission error ！",e);
			result.setCode(Constants.ApiResult.SYSTEM_ERROR.getCode());
			result.setMessage(Constants.ApiResult.SYSTEM_ERROR.getMessage());
			result.setData(e.getMessage());
			response.getWriter().write(JsonUtil.beanToJson(result));
			return false;
		}
		return true;
	}
	
	/*************************************私有方法**************************************/

	/**
	 * 设置sso的登录地址
	 */
	private void setSsoLoginUrl(ResultBean<Object> result,int code,String message)throws Exception{
    	result.setCode(code);
    	result.setMessage(message);
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.SSO_LOGIN_INDEX_URL_NAME,config.ssoLoginIndexUrl);
		result.setData(map);
	}
	
	/**
	 * 登录成功后，缓存用户信息
	 */
	private void saveLoginInfoToCache(String token)throws Exception{
		//判断是否已缓存了该用户信息
		if(this.redisTemplate.opsForValue().get(token)!=null)
			return;
		
		//通过token查询sso的用户信息
		SsoUserInfo ssoUserInfo=this.remoteService.getSsoUserInfo(token);
		//检查本地是否存在该用户信息
		UserInfo baseInfo=ssoUserInfo!=null ? this.userInfoService.findById(ssoUserInfo.getId()):null;
		if(baseInfo==null)
			throw new Exception("本地不存在该用户信息!");
		
		CurrentUserInfo curUserInfo=new CurrentUserInfo();
		//保存用户基本信息、角色信息和权限英文名集合
		curUserInfo.setBaseInfo(baseInfo);
		curUserInfo.setRoleInfo(this.roleInfoService.findById(baseInfo.getRoleId()));
		curUserInfo.setPermissionEnNames(this.permissionInfoService.findPermissionEnNamesByRoleId(baseInfo.getRoleId()));
		//缓存到redis中,并设置过期时间
		this.redisTemplate.opsForValue().set(token,JsonUtil.beanToJson(curUserInfo),Constants.CUR_USER_CACHING_EXPIRE_TIME,TimeUnit.MINUTES);
	}
	
	/**
	 * 检查是否拥有访问当前方法的权限
	 */
	private boolean ifOwnPermission(String currentPermissionName,String token)throws Exception{
		//从缓存中获取用户信息
		if(this.redisTemplate.opsForValue().get(token)==null)
			return false;
		CurrentUserInfo curUserInfo=JsonUtil.jsonToBean((String) this.redisTemplate.opsForValue().get(token), CurrentUserInfo.class);
		
		//根据用户信息中的权限英文集合属性进行判断
		List<String> permissionNames=curUserInfo.getPermissionEnNames();
		if(CollectionUtils.isEmpty(permissionNames))
			return false;
		if(!permissionNames.contains(currentPermissionName))
			return false;
		return true;
	}
    

}
