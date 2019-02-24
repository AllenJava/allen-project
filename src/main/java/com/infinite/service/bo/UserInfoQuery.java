package com.infinite.service.bo;

/**
 * 
* @ClassName: UserInfoQueryBo
* @Description: 用户信息查询参数类
* @author chenliqiao
* @date 2018年4月4日 上午9:26:23
*
 */
public class UserInfoQuery {
	
	/**姓名**/
	private String name;
	
	/**账户**/
	private String account;
	
	/**角色id**/
	private Integer roleId;
	
	/**状态（0  停用；1  启用；）**/
	private Integer status;
	
	/**分页页号**/
	private Integer pageNum=1;
	
	/**分页页大小**/
	private Integer pageSize=10;

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

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserInfoQuery [name=" + name + ", account=" + account + ", roleId=" + roleId + ", status=" + status
				+ ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}


}
