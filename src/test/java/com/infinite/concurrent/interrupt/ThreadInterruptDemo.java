package com.infinite.concurrent.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断
 * 参考 url: https://blog.csdn.net/qq_39682377/article/details/81449451
 * interrupt（）是给线程设置中断标志；interrupted（）是检测中断并清除中断状态；
 * isInterrupted（）只检测中断。
 * 还有重要的一点就是interrupted（）作用于当前线程，
 * interrupt（）和isInterrupted（）作用于此线程，即代码中调用此方法的实例所代表的线程。
 * @ClassName: ThreadInterruptDemo
 * @Description: 
 * @author chenliqiao
 * @date 2019年3月18日 上午9:36:21
 *
 */
public class ThreadInterruptDemo {
    
    public static void main(String[] args) {
        
        ThreadA threadA=new ThreadA();
        ThreadB threadB=new ThreadB();
        threadA.start();
        threadB.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //interrupt()方法用于中断此线程（此线程指threadA、threadB），但实际上只是给线程设置一个中断标志，线程仍会继续运行
        //如果想要是实现调用interrupt()方法真正的终止线程，则可以在线程的run方法中做处理即可，比如直接跳出run（）方法使线程结束，视具体情况而定
        threadA.interrupt();
        threadB.interrupt();
        
        System.out.println(threadA.getName()+" 调用isInterrupted:"+threadA.isInterrupted());
        System.out.println(threadA.getName()+" 是否存活:"+threadA.isAlive());
        
        System.out.println(threadB.getName()+" 调用isInterrupted:"+threadB.isInterrupted());
        System.out.println(threadB.getName()+" 是否存活:"+threadB.isAlive());
        
        //main方法的线程
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName()+" 调用isInterrupted:"+Thread.currentThread().isInterrupted());
        //interrupted()是检测中断并清除中断状态,只针对当前线程，即main线程，即使这样调用threadA.interrupted()，也是检测main线程中断并清除其中断状态
        System.out.println(Thread.currentThread().getName()+" 调用interrupted:"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+" 调用interrupted:"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+" 是否存活:"+Thread.currentThread().isAlive());
        
    }
    
    private static class ThreadA extends Thread{
        @Override
        public void run() {
            
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
                //用于响应interrupt()方法设置的中断标识，线程自己检测到中断标识为true时，优雅终止自身
                if(this.isInterrupted()){
                    break;
                }
            }
        }
    }
    
    private static class ThreadB extends Thread{
        @Override
        public void run() {
            
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
                try {
                    //当前线程ThreadB调用sleep进入阻塞状态
                    //如果外部其他线程调用ThreadB.interrupt()发起对当前线程ThreadB中断操作，会中断sleep的阻塞，同时抛出InterruptedException
                    //InterruptedException会清除中断标识
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //用于响应interrupt()方法设置的中断标识，线程自己检测到中断标识为true时，优雅终止自身
                if(this.isInterrupted()){
                    break;
                }
            }
        }
    }

}
