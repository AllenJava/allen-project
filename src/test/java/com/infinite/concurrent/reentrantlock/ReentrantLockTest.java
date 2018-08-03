package com.infinite.concurrent.reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    
    //非公平(默认，允许插队)
    private ReentrantLock unfairLock=new ReentrantLock();
    //公平(按照请求顺序)
    private ReentrantLock fairLock=new ReentrantLock(true);
    
    private static Map<String,Object> dataMap=new HashMap<>();
    
    /**
     * 非线程安全
     */
    public void addData(String key,Object data){
        if(!dataMap.isEmpty()){
            System.out.println("return: "+key);
            return;
        }
        
        System.out.println("add: "+key);
        dataMap.put(key, data);
    }
    
    /**
     * reentrantLock 非公平锁
     */
    public void addDataIfAbsent(String key,Object data){
            if(unfairLock.tryLock()){
                try {
                    if(!dataMap.isEmpty()){
                        System.out.println("return: "+key);
                        return;
                    }
                    
                    System.out.println("add: "+key);
                    dataMap.put(key, data);
                } finally {
                    // TODO: handle finally clause
                    unfairLock.unlock();
                }
            }
    }
    
    /**
     * reentrantLock 公平锁
     */
    public void addDataIfAbsent2(String key,Object data){
        if(fairLock.tryLock()){
            try {
                if(!dataMap.isEmpty()){
                    System.out.println("return: "+key);
                    return;
                }
                
                System.out.println("add: "+key);
                dataMap.put(key, data);
            } finally {
                fairLock.unlock();
            }
        }
}
    
    public static void main(String[] args) throws Exception { 
        ReentrantLockTest test=new ReentrantLockTest();
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {               
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //test.addData(Thread.currentThread().getName(),System.currentTimeMillis()); 
                    //test.addDataIfAbsent(Thread.currentThread().getName(),System.currentTimeMillis());
                    test.addDataIfAbsent2(Thread.currentThread().getName(),System.currentTimeMillis());
                }
            }).start();
        }
        
        TimeUnit.SECONDS.sleep(5);
        System.out.println(dataMap+" size: "+dataMap.size());
        
    }

}
