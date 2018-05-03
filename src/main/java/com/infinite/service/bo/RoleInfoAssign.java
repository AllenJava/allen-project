package com.infinite.service.bo;

import java.util.Arrays;

/**
 * 
* @ClassName: RoleInfoAssign
* @Description: 分配权限请求参数对象
* @author chenliqiao
* @date 2018年1月5日 上午9:52:13
*
 */
public class RoleInfoAssign {
	
	/**角色id**/
	private Integer roleId;
	
	/**分配的权限id数组**/
	private Integer[] permissionIds;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
	public String toString() {
		return "RoleInfoAssign [roleId=" + roleId + ", permissionIds=" + Arrays.toString(permissionIds) + "]";
	}
	
	

}
