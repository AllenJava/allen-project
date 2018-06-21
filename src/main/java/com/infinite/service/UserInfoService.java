package com.infinite.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.infinite.common.exception.BusinessException;
import com.infinite.dao.PermissionInfoMapper;
import com.infinite.dao.UserInfoMapper;
import com.infinite.dao.po.PermissionInfo;
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
	private PermissionInfoMapper permissionInfoMapper;
	
	@Resource
	private RemoteService remoteService;
	
	//注入自己
	@Resource
	private UserInfoService userInfoService;
	
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
	
	
	public void testTransaction1(){
	    try {
	        UserInfo entity=new UserInfo();
	        entity.setName("test");
	        this.userInfoMapper.insertSelective(entity);
	        if(true)
	            throw new RuntimeException("新增user失败!");           
        } catch (Exception e) {
            // TODO: handle exception
            /**
             * userInfoService通过注入自己，因为在同一个业务类方法中调用内部方法，不会产生新事务
             * （在spring默认的代理模式下，只有外部方法调用才会被spring的事务拦截器拦截）
             */
            this.userInfoService.testTransaction1FailHandler();
            throw new RuntimeException(e);  
        } 
	}
	
	//设置事务传播基本为REQUIRES_NEW，表示重新创建一个新的事务，如果当前存在事务，暂停当前的事务。
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testTransaction1FailHandler(){
        PermissionInfo permission=new PermissionInfo();
        permission.setName("新增user失败");
        this.permissionInfoMapper.insertSelective(permission);
	}
	
	
	public void testTransaction2(){
        for (int i = 0; i < 5; i++) {
            this.userInfoService.testTransAdd();
        }
        
        if(true)
            throw new RuntimeException("test");
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void testTransAdd(){       
        UserInfo entity=new UserInfo();
        entity.setName("testTransAdd");
        this.userInfoMapper.insertSelective(entity);
	}

}
