package com.infinite.java8;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * 
* @ClassName: CompletableFutureTest
* @Description: 组合式异步编程
* @author chenliqiao
* @date 2018年11月21日 上午11:11:30
*
 */
public class CompletableFutureTest {
    
    public static void main(String[] args) {
        //future实现
        System.out.println("future实现:");
        System.out.println(futureCalculate());
        
        //CompletableFuture实现
        System.out.println("CompletableFuture实现:");
        System.out.println(completableFutureCalculate());
    }
    
    
    /**
     * JDK7的Future
     */
    public static Integer futureCalculate(){
        ExecutorService executor=Executors.newCachedThreadPool();
        Future<Integer> future=executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return IntStream.range(1, 100).sum();
            }
        });
        
        //处理其他事情
        doSomethingOther();
        
        //从future获取结果
        Integer result=null;
        try {
            result = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //线程中断异常
            e.printStackTrace();
        } catch (ExecutionException e) {
            //逻辑异常
            e.printStackTrace();
        } catch (TimeoutException e) {
            //从future获取结果超时
            e.printStackTrace();
        }
        executor.shutdown();
        return result;
    }       
    
    /**
     * JDK8的CompletableFuture
     */
    public static Integer completableFutureCalculate(){
        CompletableFuture<Integer> completableFuture=new CompletableFuture<>();
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                completableFuture.complete(IntStream.range(1, 100).sum());
            }
        }).start();
        
        //处理其他事情
        doSomethingOther();
        
        //从completableFuture获取结果
        Integer result=null;
        try {
            result=completableFuture.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //线程中断异常
            e.printStackTrace();
        } catch (ExecutionException e) {
            //逻辑异常
            e.printStackTrace();
        } catch (TimeoutException e) {
            //从future获取结果超时
            e.printStackTrace();
        }
        return result;
    }
    
    public static void doSomethingOther(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("doSomethingOther...");
    }

}
