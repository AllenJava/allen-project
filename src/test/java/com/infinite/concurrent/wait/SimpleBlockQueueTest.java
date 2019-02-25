package com.infinite.concurrent.wait;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: SimpleBlockQueueTest
* @Description: 使用wait和notify机制实现自定义简单的阻塞队列
* @author chenliqiao
* @date 2019年2月25日 下午5:02:52
*
 */
public class SimpleBlockQueueTest {
    
    private  Object lock=new Object();
    
    private  int queueSize=10;
    
    private  List<Integer> list=new ArrayList<>();
    
    private void put(Integer element) throws Exception{
        synchronized (lock) {
               //不符合条件，wait
               while(list.size()==queueSize){
                   System.out.println("元素->"+element+"put失败，队列已满");
                   lock.wait();
               }
               
               //符合条件
               list.add(element);
               System.out.println("元素->"+element+"put成功!");
               //TimeUnit.SECONDS.sleep(1);
               lock.notifyAll();               
        }
    }
    
    private  Integer poll() throws Exception{
        synchronized (lock) {
            //不符合条件，wait
            while(list.isEmpty()){
                //System.out.println("队列已空");
                lock.wait();
            }
            
            //符合条件
            Integer result=list.get(list.size()-1);
            list.remove(result);
            System.out.println("消费元素->"+result+"成功!");
            //TimeUnit.SECONDS.sleep(1);
            lock.notifyAll();
            return result;
        }
    }
    
    public static void main(String[] args) {
        //自定义队列
        SimpleBlockQueueTest simpleQueue=new SimpleBlockQueueTest();
        
        for (int i = 0; i < 31; i++) {
            int fi=i;
            new Thread(new Runnable() {               
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        simpleQueue.put(fi);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        
        
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {               
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        simpleQueue.poll();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}
