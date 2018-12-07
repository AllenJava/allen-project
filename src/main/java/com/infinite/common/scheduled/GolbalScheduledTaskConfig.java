package com.infinite.common.scheduled;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@EnableAsync
public class GolbalScheduledTaskConfig {
    
//    /**
//     * ********验收spring多个定时任务之间是串行执行的 START***************
//     * 默认所有的定时任务都是串行调度的，一个线程，且即便crond完全相同的两个任务先后顺序也没法保证
//     */
//    @Scheduled(cron="0/1 * * * * ?")
//    public void task1(){
//        System.out.println(Thread.currentThread().getName()+": task1，"+System.currentTimeMillis());
//        //此处task1一直睡眠1s,而task2不会自动执行，说明任务task1和task2是串行执行的
//        while(true){
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } 
//        }
//    }
//    @Scheduled(cron="0/1 * * * * ?")
//    public void task2(){
//        System.out.println(Thread.currentThread().getName()+": task2，"+System.currentTimeMillis());
//    }
//    /**
//     * ********验收spring多个定时任务之间是串行执行的 END***************
//     */
//    
//    
//    /**
//     * *******spring多个定时任务之间实现并行执行 START****************
//     * 1.GolbalScheduledTaskConfig类加上@EnableAsync注解
//     * 2.技术任务加上@Async
//     * 3.未配置自定义线程池，线程数会暴涨
//     * 缺点： 如果计划任务中不小心写了死循环，会变成无限线程，最后进程崩溃`
//     */
//    @Scheduled(cron="0/1 * * * * ?")
//    @Async
//    public void task3(){
//        System.out.println(Thread.currentThread().getName()+": task3，"+System.currentTimeMillis());
//        while(true){
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } 
//        }
//    }
//    @Scheduled(cron="0/1 * * * * ?")
//    @Async
//    public void task4(){
//        System.out.println(Thread.currentThread().getName()+": task3，"+System.currentTimeMillis());
//    }
//    /**
//     * *******spring多个定时任务之间实现并行执行 END****************
//     */
    
    
    /**
     * *******spring多个定时任务之间实现并行执行 ,使用自定义线程池实现  START****************
     * 自定义线程池已配置，见：com.infinite.common.config.TaskPoolConfig
     */
    @Scheduled(cron="0/1 * * * * ?")
    @Async
    public void task5(){
        System.out.println(Thread.currentThread().getName()+": task3，"+System.currentTimeMillis());
        while(true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
    }
    /**
     * *******spring多个定时任务之间实现并行执行 ,使用自定义线程池实现  End****************
     */
}
