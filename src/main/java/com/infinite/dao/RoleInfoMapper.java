package com.infinite.dao;

import java.util.List;

import com.infinite.dao.po.RoleInfo;
import com.infinite.service.bo.RoleInfoQuery;

/**
 * 
* @ClassName: RoleInfoMapper
* @Description: 角色信息持久化类
* @author chenliqiao
* @date 2017年12月29日 下午2:39:28
*
 */
public interface RoleInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
    
    /**
     * 根据条件查询
     */
    List<RoleInfo> findByCondition(RoleInfoQuery condition);
    
    
    
}