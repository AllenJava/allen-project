package com.infinite.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infinite.dao.RoleInfoMapper;
import com.infinite.dao.po.RoleInfo;
import com.infinite.service.bo.RoleInfoQuery;

/**
 * 
* @ClassName: RoleInfoService
* @Description: 角色业务类
* @author chenliqiao
* @date 2018年4月4日 下午6:07:09
*
 */
@Service
@Transactional
public class RoleInfoService {
	
	@Resource
	private RoleInfoMapper roleInfoMapper;
	
	@Resource
	private RolePermissionInfoService rolePermissionInfoService;
	
	/**
     * 根据条件查询
     */
    public List<RoleInfo> findByCondition(RoleInfoQuery condition){
    	return this.roleInfoMapper.findByCondition(condition);
    }
    
    /**
     * 根据id查询
     */
    public RoleInfo findById(Integer id){
    	return this.roleInfoMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 新增
     */
    public void add(RoleInfo roleInfo){
    	roleInfo.setCreateTime(new Date());
    	this.roleInfoMapper.insertSelective(roleInfo);
    }
    
    /**
     * 删除
     */
    public void delete(Integer id){
    	this.roleInfoMapper.deleteByPrimaryKey(id);
    	
    	//删除角色权限关联信息
    	this.rolePermissionInfoService.deleteByRoleId(id);
    }

}
