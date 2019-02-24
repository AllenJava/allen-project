package com.infinite.dao.po;

/**
 * 
* @ClassName: UserExtendInfo
* @Description: 用户信息拓展实体类
* @author chenliqiao
* @date 2017年12月29日 下午2:26:02
*
 */
public class UserExtendInfo extends UserInfo{
	
	/**用户角色名称**/
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserExtendInfo [roleName=" + roleName + "]";
	}
	
	

}
