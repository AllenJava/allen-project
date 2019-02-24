package com.infinite.dao;

import java.util.List;

import com.infinite.dao.po.RolePermissionInfo;

/**
 * 
* @ClassName: RolePermissionInfoMapper
* @Description: 角色权限持久化类
* @author chenliqiao
* @date 2018年1月3日 下午5:44:58
*
 */
public interface RolePermissionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionInfo record);

    int insertSelective(RolePermissionInfo record);

    RolePermissionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermissionInfo record);

    int updateByPrimaryKey(RolePermissionInfo record);
    
    /**
     * 
    * @Title: deleteByRoleId
    * @Description: 根据角色id删除记录
    * @param @param roleId
    * @param @return
    * @return int
    * @throws
     */
    int deleteByRoleId(Integer roleId);
    
    
    /**
     * 
    * @Title: deleteByPermissionId
    * @Description: 根据权限id删除记录
    * @param @param permissionId
    * @param @return
    * @return int
    * @throws
     */
    int deleteByPermissionId(Integer permissionId);
    
    
    /**
     * 
    * @Title: findPermIdsByRoleId
    * @Description: 根据角色id查询权限id数组
    * @param @param roleId
    * @param @return
    * @return List<Integer>
    * @throws
     */
    List<Integer> findPermIdsByRoleId(Integer roleId);
}