package com.infinite.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedThreadPoolDemo {
    
    private static final ExecutorService threadPool=Executors.newFixedThreadPool(5);
    
    public static void main(String[] args) {
        
        try {
            for (int i=0;i<10;i++) {
                threadPool.execute(new Runnable() {                
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            TimeUnit.SECONDS.sleep(2);
                            System.out.println(Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
            }
        } finally {
            threadPool.shutdown();
            System.out.println("isTerminated->"+threadPool.isTerminated());
        }
        
    }

}
