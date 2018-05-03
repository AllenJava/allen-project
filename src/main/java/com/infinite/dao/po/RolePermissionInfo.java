package com.infinite.dao.po;

/**
 * 
* @ClassName: RolePermissionInfo
* @Description: 角色权限实体映射类
* @author chenliqiao
* @date 2018年1月3日 下午5:49:25
*
 */
public class RolePermissionInfo {
	
    private Integer id;

    /**角色id**/
    private Integer roleId;

    /**权限id**/
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

	@Override
	public String toString() {
		return "RolePermissionInfo [id=" + id + ", roleId=" + roleId + ", permissionId=" + permissionId + "]";
	}
    
    
}