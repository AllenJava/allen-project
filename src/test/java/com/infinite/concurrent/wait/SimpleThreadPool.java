package com.infinite.concurrent.wait;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadPool {
    
    private int coreSize;
    
    private int maxSize;
    
    private Queue<Runnable> waitTaskQueue;
    
    private AtomicInteger threadCount;
    private AtomicInteger runningThreadCount;
    
    public SimpleThreadPool(int coreSize,int maxSize,Queue<Runnable> waitTaskQueue){
        this.coreSize=coreSize;
        this.maxSize=maxSize;
        this.waitTaskQueue=waitTaskQueue;
    }
    
    private void execute(Runnable task){
        int count=runningThreadCount.get();
        if(count==coreSize){
            waitTaskQueue.add(task);
            return;
        }
        
        runningThreadCount.incrementAndGet();
        
        ExecutorService es=Executors.newFixedThreadPool(1);
    }

}
