package com.infinite.service.bo;

import java.util.List;

import com.infinite.dao.po.UserInfo;

/**
 * 
* @ClassName: UserInfoAdd
* @Description: 新增用户请求参数类
* @author chenliqiao
* @date 2018年4月4日 下午5:45:21
*
 */
public class UserInfoAdd {
	
	/**新增的用户列表**/
	private List<UserInfo> userInfos;

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	@Override
	public String toString() {
		return "UserInfoAdd [userInfos=" + userInfos + "]";
	}
	

}
