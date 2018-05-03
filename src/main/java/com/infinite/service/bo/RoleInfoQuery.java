package com.infinite.service.bo;

/**
 * 
* @ClassName: RoleInfoQuery
* @Description: 角色信息查询参数类
* @author chenliqiao
* @date 2018年4月4日 上午10:36:20
*
 */
public class RoleInfoQuery {
	
	/**角色名称**/
	private String roleName;
	
	/**状态**/
	private Integer status;
	
	/**分页页号**/
	private Integer pageNum=1;
	
	/**分页页大小**/
	private Integer pageSize=10;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "RoleInfoQuery [roleName=" + roleName + ", status=" + status + ", pageNum=" + pageNum + ", pageSize="
				+ pageSize + "]";
	}

}
