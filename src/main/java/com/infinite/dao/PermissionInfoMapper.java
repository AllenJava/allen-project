package com.infinite.dao;

import java.util.List;

import com.infinite.dao.po.PermissionExtendInfo;
import com.infinite.dao.po.PermissionInfo;
import com.infinite.service.bo.PermissionInfoQuery;

/**
 * 
* @ClassName: PermissionInfoMapper
* @Description: 权限持久化类
* @author chenliqiao
* @date 2018年1月3日 下午5:41:50
*
 */
public interface PermissionInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    PermissionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);
    
    /**
     * 
    * @Title: findByCondition
    * @Description: 根据条件查询
    * @param @param condition
    * @param @return
    * @return List<PermissionExtendInfo>
    * @throws
     */
    List<PermissionExtendInfo> findByCondition(PermissionInfoQuery condition);
    
    /**
     * 
    * @Title: findByParentId
    * @Description: 根据父级Id查询记录
    * @param @param parentId
    * @param @return
    * @return List<PermissionInfo>
    * @throws
     */
    List<PermissionInfo> findByParentId(Integer parentId);
    
    
    /**
     * 
    * @Title: findRootPermissions
    * @Description: 查询根节点的权限信息
    * @param @return
    * @return List<PermissionInfo>
    * @throws
     */
    List<PermissionInfo> findRootPermissions();
    
    
    /**
     * 
    * @Title: findPermissionsByRoleId
    * @Description: 根据角色id查询权限列表
    * @param @param roleId
    * @param @return
    * @return List<PermissionInfo>
    * @throws
     */
    List<PermissionInfo> findPermissionsByRoleId(Integer roleId);
    
    
    /**
     * 
    * @Title: findMaxOrderNum
    * @Description: 获取最大的顺序编号
    * @param @return
    * @return Integer
    * @throws
     */
    Integer findMaxOrderNum();
}