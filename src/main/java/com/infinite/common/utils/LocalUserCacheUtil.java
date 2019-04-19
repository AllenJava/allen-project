package com.infinite.common.utils;

import com.infinite.service.dto.CurrentUserCacheDTO;

/**
 * 
* @ClassName: LocalUserCacheUtil
* @Description: 本地用户缓存信息
* @author chenliqiao
* @date 2019年3月12日 下午3:04:20
*
 */
public class LocalUserCacheUtil {
    
    private static final ThreadLocal<CurrentUserCacheDTO> LOCAL_USER=new ThreadLocal<>();
    
    private static final ThreadLocal<String> LOCAL_USER_ACCOUNT=new ThreadLocal<>();
    
    public static void set(CurrentUserCacheDTO userCache){
        LOCAL_USER.set(userCache);
        
        //直接缓存用户账号，以便全局使用
        if(userCache.getBaseInfo()!=null){
            LOCAL_USER_ACCOUNT.set(userCache.getBaseInfo().getAccount());
        }
    }
    
    public static CurrentUserCacheDTO get(){
        return LOCAL_USER.get();
    }
    
    public static String getCurrentUserAccount(){
        return LOCAL_USER_ACCOUNT.get();
    }
    
    public static void clearUser(){
        LOCAL_USER.remove();
    }

}
