package com.infinite.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infinite.common.exception.BusinessException;
import com.infinite.dao.PermissionInfoMapper;
import com.infinite.dao.po.PermissionExtendInfo;
import com.infinite.dao.po.PermissionInfo;
import com.infinite.dao.po.RolePermissionInfo;
import com.infinite.service.bo.PermissionInfoQuery;
import com.infinite.service.bo.PermissionTreeNode;
import com.infinite.service.bo.RoleInfoAssign;

/**
 * 
* @ClassName: PermissionInfoService
* @Description: 权限业务类
* @author chenliqiao
* @date 2018年4月8日 上午11:17:08
*
 */
@Service
@Transactional
public class PermissionInfoService {
	
	@Resource
	private PermissionInfoMapper permissionInfoMapper;
	
	@Resource
	private RolePermissionInfoService rolePermissionInfoService;
	
	/**
	 * 根据条件查询
	 */
	public List<PermissionExtendInfo> findByCondition(PermissionInfoQuery condition){
		return this.permissionInfoMapper.findByCondition(condition);
	}
	
	
	/**
	 * 新增
	 */
	public void add(PermissionInfo permissionInfo){
		permissionInfo.setCreateTime(new Date());
		this.permissionInfoMapper.insertSelective(permissionInfo);
	}
	
	/**
	 * 删除
	 */
	public void delete(Integer id){
		this.permissionInfoMapper.deleteByPrimaryKey(id);
		
		//删除权限角色关联信息
		this.rolePermissionInfoService.deleteByPermissionId(id);
	}
	
    /**
     * 分配权限
     */
	public void assignPermission(RoleInfoAssign roleInfoAssign){
		//参数校验
		if(roleInfoAssign.getRoleId()==null)
			throw new BusinessException("角色id不能为空!");
		
		//先清空原来的分配数据
		this.rolePermissionInfoService.deleteByRoleId(roleInfoAssign.getRoleId());
		
		//分配权限，并持久化
		if(roleInfoAssign.getPermissionIds()==null||roleInfoAssign.getPermissionIds().length==0)
			return;
		
		for (Integer permissionId : roleInfoAssign.getPermissionIds()) {
			RolePermissionInfo rolePermissionInfo=new RolePermissionInfo();
			rolePermissionInfo.setRoleId(roleInfoAssign.getRoleId());
			rolePermissionInfo.setPermissionId(permissionId);
			
			this.rolePermissionInfoService.add(rolePermissionInfo);
		}
	}
	
	/**
	 * 构建权限树
	 */
	public List<PermissionTreeNode> buildPermissionTree(Integer roleId){	
		//根据角色id获取已分配的权限id数组
		List<Integer> selectedPermIds=new ArrayList<>();
		selectedPermIds=this.rolePermissionInfoService.findPermIdsByRoleId(roleId);
		
		//获取作为根节点的权限信息列表
		List<PermissionInfo> rootPermissions=this.permissionInfoMapper.findRootPermissions();
		if(CollectionUtils.isEmpty(rootPermissions)) return null;
		
		//构建树节点，生成树
		List<PermissionTreeNode> permissionTrees=new ArrayList<>();
		for (PermissionInfo permissionInfo : rootPermissions) {
			permissionTrees.add(this.createNode(permissionInfo,selectedPermIds));
		}
		
		return permissionTrees;
	}
	
	/**
	 * 根据角色id查询权限列表
	 */
	public List<PermissionInfo> findPermissionsByRoleId(Integer roleId){
		return this.permissionInfoMapper.findPermissionsByRoleId(roleId);
	}
	
	/**
	 * 根据角色id查询权限的英文名称集合
	 */
	public List<String> findPermissionEnNamesByRoleId(Integer roleId){
		List<String> result=new ArrayList<>();
		List<PermissionInfo> permissions=this.findPermissionsByRoleId(roleId);
		if(CollectionUtils.isEmpty(permissions)) return result;		
		
		for (PermissionInfo permissionInfo : permissions) {
			result.add(permissionInfo.getEnName());
		}
		return result;
	}
	
	
	/********************************************私有方法*******************************************/
	
	/**
	 * 根据权限信息生成树节点
	 */
	private PermissionTreeNode createNode(PermissionInfo permissionInfo,List<Integer> selectedPermIds){
		//创建一个树节点对象
		PermissionTreeNode node=new PermissionTreeNode();
		
		//填充树节点信息
		this.fillNodeInfo(node, permissionInfo,selectedPermIds);
		
		//获取子节点的权限信息列表
		List<PermissionInfo> permissionInfos=this.permissionInfoMapper.findByParentId(permissionInfo.getId());
		if(CollectionUtils.isEmpty(permissionInfos)) return node;
		
		List<PermissionTreeNode> childNodes=new ArrayList<>();
		for (PermissionInfo permission: permissionInfos) {
			//递归生成树节点
			childNodes.add(this.createNode(permission,selectedPermIds));
		}
		
	    //设置子节点列表
		node.setChildNodes(childNodes);
		return node;
	}
	
	/**
	 * 根据权限信息，填充树节点信息
	 */
	private void fillNodeInfo(PermissionTreeNode node,PermissionInfo permissionInfo,List<Integer> selectedPermIds){
		BeanUtils.copyProperties(permissionInfo, node);
		//设置是否已选中
		node.setIfSelected(selectedPermIds.contains(node.getId()));
	}

}
