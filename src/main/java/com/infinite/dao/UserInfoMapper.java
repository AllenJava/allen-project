package com.infinite.dao;

import java.util.List;

import com.infinite.dao.po.UserExtendInfo;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.bo.UserInfoQuery;

/**
 * 
* @ClassName: UserInfoMapper
* @Description: 用户信息持久化类
* @author chenliqiao
* @date 2018年4月3日 下午5:14:58
*
 */
public interface UserInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    /**
     * 根据条件查询用户列表
     */
    List<UserExtendInfo> findByCondition(UserInfoQuery userInfoQuery);
    
}