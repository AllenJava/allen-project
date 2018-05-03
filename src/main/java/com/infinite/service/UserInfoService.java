package com.infinite.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infinite.common.exception.BusinessException;
import com.infinite.dao.UserInfoMapper;
import com.infinite.dao.po.UserExtendInfo;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.bo.UserInfoAdd;
import com.infinite.service.bo.UserInfoQuery;
import com.infinite.service.dto.SsoUserQuery;

/**
 * 
* @ClassName: UserInfoService
* @Description: 用户信息业务操作类
* @author chenliqiao
* @date 2018年4月4日 上午10:00:09
*
 */
@Service
@Transactional
public class UserInfoService {
	
	@Resource
	private UserInfoMapper userInfoMapper;
	
	@Resource
	private RemoteService remoteService;
	
	
	/**
	 * 根据条件查询用户列表
	 */
	public List<UserExtendInfo> findByCondition(UserInfoQuery userInfoQuery){
		return this.userInfoMapper.findByCondition(userInfoQuery);
	}
	
	/**
	 * 查询sso用户列表信息
	 */
	public List<UserInfo> findSsoUserList(SsoUserQuery request){
		List<UserInfo> ssoUsers=this.remoteService.getSsoUserList(request);
		if(CollectionUtils.isEmpty(ssoUsers))
			return ssoUsers;
		
		//剔除本地已存在的用户
		
		return ssoUsers;
	}
	
	/**
	 * 新增用户
	 */
	public void add(UserInfoAdd userInfoAdd){
		if(CollectionUtils.isEmpty(userInfoAdd.getUserInfos()))
			throw new BusinessException("新增用户列表不能为空"); 
		
		for (UserInfo userInfo : userInfoAdd.getUserInfos()) {
			this.userInfoMapper.insertSelective(userInfo);
		}
	}
	
	/**
	 * 根据id查询
	 */
	public UserInfo findById(Integer id){
		return this.userInfoMapper.selectByPrimaryKey(id);
	}

}
