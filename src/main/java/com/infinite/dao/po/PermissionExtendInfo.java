package com.infinite.dao.po;

/**
 * 
* @ClassName: PermissionExtendInfo
* @Description: 权限拓展信息实体映射类
* @author chenliqiao
* @date 2018年4月4日 上午10:30:25
*
 */
public class PermissionExtendInfo extends PermissionInfo{
	
	/**父级名称**/
    private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "PermissionExtendInfo [parentName=" + parentName + "]";
	}
    
    
    
}