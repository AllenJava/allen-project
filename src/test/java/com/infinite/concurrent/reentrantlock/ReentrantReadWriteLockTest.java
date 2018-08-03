package com.infinite.concurrent.reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    
    //读写锁
    private ReadWriteLock lock=new ReentrantReadWriteLock();
    
    //写独享锁
    private Lock writeLock=lock.writeLock();
    
    //读共享锁
    private Lock readLock=lock.readLock();
    
    //互斥锁
    private Lock metuxLock=new ReentrantLock();
    
    public static final Map<String, Object> readWriteMap=new HashMap<>();
    
    /**
     * 写独享锁
     */
    public void put(String key,Object value){
        if(writeLock.tryLock()){
            try {
                readWriteMap.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }
    }
    
    /**
     * 读共享锁（并发性较好）
     */
    public Object get(String key){
        Object value=null;
        try {
            readLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            value=readWriteMap.get(key);
        } finally {
            readLock.unlock();
        }
        return value;
    }
    
    /**
     * 读独享锁（并发性差）
     */
    public Object getByMetux(String key){
        Object value=null;
        try {
            metuxLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            value=readWriteMap.get(key);
        } finally {
            metuxLock.unlock();
        }
        return value;
    }
    
    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLockTest test=new ReentrantReadWriteLockTest();
        //write
        for (int i = 0; i < 5; i++) { 
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.put(Thread.currentThread().getName(), System.currentTimeMillis());
                }
            }).start();
        }
        
        TimeUnit.SECONDS.sleep(10);
        System.out.println(readWriteMap);
        System.out.println("size: "+readWriteMap.size());
        
        //read
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("请求时间： "+System.currentTimeMillis()+" 结果："+test.get("Thread-3"));
                    //System.out.println("请求时间： "+System.currentTimeMillis()+" 结果："+test.getByMetux("Thread-3"));
                }
            }).start();
        }
    }

}
