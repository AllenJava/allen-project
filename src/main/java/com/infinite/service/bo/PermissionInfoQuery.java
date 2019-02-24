package com.infinite.service.bo;

/**
 * 
* @ClassName: PermissionInfoQuery
* @Description: 权限信息查询参数类
* @author chenliqiao
* @date 2018年4月4日 上午10:35:54
*
 */
public class PermissionInfoQuery {
	
	/**父级id**/
	private Integer parentId;
	
	/**权限名称**/
	private String name;
	
	/**分页页号**/
	private Integer pageNum=1;
	
	/**分页页大小**/
	private Integer pageSize=10;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "PermissionInfoQuery [parentId=" + parentId + ", name=" + name + ", pageNum=" + pageNum + ", pageSize="
				+ pageSize + "]";
	}
	

}
