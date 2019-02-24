package com.infinite.service.bo;

import java.util.List;

import com.infinite.dao.po.RoleInfo;
import com.infinite.dao.po.UserInfo;

/**
 * 
* @ClassName: CurrentUserInfo
* @Description: 当前登录用户的信息
* @author chenliqiao
* @date 2018年4月8日 下午5:10:24
*
 */
public class CurrentUserInfo {
	
	/**基本信息**/
	private UserInfo baseInfo;
	
	/**角色名称**/
	private RoleInfo roleInfo;
	
	/**拥有的权限英文集合**/
	private List<String> permissionEnNames;

	public UserInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public List<String> getPermissionEnNames() {
		return permissionEnNames;
	}

	public void setPermissionEnNames(List<String> permissionEnNames) {
		this.permissionEnNames = permissionEnNames;
	}

	@Override
	public String toString() {
		return "CurrentUserInfo [baseInfo=" + baseInfo + ", roleInfo=" + roleInfo + ", permissionEnNames="
				+ permissionEnNames + "]";
	}

}
