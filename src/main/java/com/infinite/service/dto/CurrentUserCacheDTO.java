package com.infinite.service.dto;

import java.util.List;

import com.infinite.dao.po.RoleInfo;
import com.infinite.dao.po.UserInfo;


/**
 * 
* @ClassName: CurrentUserCacheDTO
* @Description: 当前用户缓存信息DTO
* @author chenliqiao
* @date 2019年1月8日 下午2:58:44
*
 */
public class CurrentUserCacheDTO {
    
    /**用户基本信息**/
    private UserInfo baseInfo;
    
    /**用户角色信息**/
    private RoleInfo roleInfo;
    
    /**拥有的菜单权限代码集合**/
    private List<String> menuCodeList;
    
    /**数据权限中已分配的渠道id**/
    private Integer channelId;
    
    /**切换前的用户信息**/
    private CurrentUserCacheDTO previousUserCache;

    public UserInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(UserInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public RoleInfo getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleInfo roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<String> getMenuCodeList() {
        return menuCodeList;
    }

    public void setMenuCodeList(List<String> menuCodeList) {
        this.menuCodeList = menuCodeList;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public CurrentUserCacheDTO getPreviousUserCache() {
        return previousUserCache;
    }

    public void setPreviousUserCache(CurrentUserCacheDTO previousUserCache) {
        this.previousUserCache = previousUserCache;
    }
    
}
