package com.infinite.common.lock;

/**
 * 
* @ClassName: DistributedLock
* @Description: 分布式锁通用接口 
* @author chenliqiao
* @date 2019年4月1日 上午11:28:21
*
 */
public interface DistributedLock {
    
    /**
     * 获取锁
     */
    boolean tryLock(String key,long expire);
    
    /**
     * 释放锁
     */
    boolean unLock(String key);

}
