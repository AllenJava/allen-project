package com.infinite.service.dto;

import java.util.Date;
/**
 * 
* @ClassName: SsoUserInfo
* @Description: 调用sso服务查询用户信息 返回用户信息类
* @author chenliqiao
* @date 2017年12月31日 下午8:25:04
*
 */
public class SsoUserInfo {
	
    private Integer id;
	
	private String account;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private String systemPlatform;
	
	private String status;
	
	private Date lastLoginTime;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private String isDeleted;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSystemPlatform() {
		return systemPlatform;
	}
	public void setSystemPlatform(String systemPlatform) {
		this.systemPlatform = systemPlatform;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public String toString() {
		return "SsoUserInfo [id=" + id + ", account=" + account + ", name=" + name + ", email=" + email + ", phone="
				+ phone + ", systemPlatform=" + systemPlatform + ", status=" + status + ", lastLoginTime="
				+ lastLoginTime + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", isDeleted="
				+ isDeleted + "]";
	}
	
	
	

}
