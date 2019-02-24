package com.infinite.service.dto;

/**
 * 
* @ClassName: SsoUserQuery
* @Description: 调用sso服务查询用户信息 请求参数类
* @author chenliqiao
* @date 2018年4月4日 下午3:49:59
*
 */
public class SsoUserQuery {

	/**用户姓名**/
	private String name;
	
	/**账户名**/
	private String account;
	
	/**平台类型**/
	private String systemPlatform;
	
	/**页大小**/
	private Integer pageSize=1000;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSystemPlatform() {
		return systemPlatform;
	}

	public void setSystemPlatform(String systemPlatform) {
		this.systemPlatform = systemPlatform;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "SsoUserQuery [name=" + name + ", account=" + account + ", systemPlatform=" + systemPlatform
				+ ", pageSize=" + pageSize + "]";
	}

}
