package com.infinite.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infinite.dao.RolePermissionInfoMapper;
import com.infinite.dao.po.RolePermissionInfo;

/**
 * 
* @ClassName: RolePermissionInfoService
* @Description: 角色权限信息业务操作类
* @author chenliqiao
* @date 2018年1月3日 下午5:54:30
*
 */
@Service
@Transactional
public class RolePermissionInfoService {
	
	@Resource
	private RolePermissionInfoMapper rolePermissionInfoMapper;
	
	
	/**
	 * 
	* @Title: deleteByRoleId
	* @Description: 根据角色id删除记录
	* @param @param roleId
	* @param @throws Exception
	* @return void
	* @throws
	 */
	public void deleteByRoleId(Integer roleId){
		this.rolePermissionInfoMapper.deleteByRoleId(roleId);
	}
	
	
	/**
	 * 
	* @Title: deleteByPermissionId
	* @Description: 根据权限id删除记录
	* @param @param permissionId
	* @param @throws Exception
	* @return void
	* @throws
	 */
	public void deleteByPermissionId(Integer permissionId){
		this.rolePermissionInfoMapper.deleteByPermissionId(permissionId);
	}
	
	
	/**
	 * 
	* @Title: add
	* @Description: 新增记录
	* @param @param entity
	* @param @throws Exception
	* @return void
	* @throws
	 */
	public void add(RolePermissionInfo entity){
		this.rolePermissionInfoMapper.insertSelective(entity);
	}
	
	
	/**
	 * 
	* @Title: findPermIdsByRoleId
	* @Description: 根据角色id查询权限id数组
	* @param @param roleId
	* @param @return
	* @param @throws Exception
	* @return List<Integer>
	* @throws
	 */
	public List<Integer> findPermIdsByRoleId(Integer roleId){
		return this.rolePermissionInfoMapper.findPermIdsByRoleId(roleId);
	}

}
