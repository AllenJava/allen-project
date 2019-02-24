package com.infinite.dao.po;

import java.util.Date;

/**
 * 
* @ClassName: UserInfo
* @Description: 用户信息实体映射类
* @author chenliqiao
* @date 2017年12月29日 下午2:15:18
*
 */
public class UserInfo {
	
    private Integer id;

    /**角色id**/
    private Integer roleId;

    /**姓名**/
    private String name;

    /**账户**/
    private String account;

    /**手机号码**/
    private String mobile;

    /**最后登录时间**/
    private Date lastLoginTime;

    /**创建时间**/
    private Date createTime;

    /**状态**/
    private Integer status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", roleId=" + roleId + ", name=" + name + ", account=" + account + ", mobile="
				+ mobile + ", lastLoginTime=" + lastLoginTime + ", createTime=" + createTime + ", status=" + status
				+ "]";
	}
    
    
    
}