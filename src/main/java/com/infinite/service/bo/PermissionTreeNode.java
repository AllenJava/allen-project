package com.infinite.service.bo;

import java.util.List;

import com.infinite.dao.po.PermissionInfo;


/**
 * 
* @ClassName: PermissionTreeNode
* @Description: 权限树节点信息
* @author chenliqiao
* @date 2018年4月8日 下午3:14:17
*
 */
public class PermissionTreeNode extends PermissionInfo{
	
	/**是否已选中（即是否已分配）**/
	private boolean ifSelected;
	
	/**子节点**/
	private List<PermissionTreeNode> childNodes;
	

	public List<PermissionTreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<PermissionTreeNode> childNodes) {
		this.childNodes = childNodes;
	}

	public boolean isIfSelected() {
		return ifSelected;
	}

	public void setIfSelected(boolean ifSelected) {
		this.ifSelected = ifSelected;
	}

	@Override
	public String toString() {
		return "PermissionTreeNode [ifSelected=" + ifSelected + ", childNodes=" + childNodes + "]";
	}

	
}
