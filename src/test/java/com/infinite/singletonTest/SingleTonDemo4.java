package com.infinite.singletonTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 
* @ClassName: SingleTonDemo4
* @Description: 双重检查锁实现单例
* @author chenliqiao
* @date 2018年6月19日 上午10:14:34
*
 */
public class SingleTonDemo4 {
	
	private static SingleTonDemo4 instance=null;
	
	private SingleTonDemo4(){}; 
	
	public static SingleTonDemo4 getInstance(){
		//第一次检查对象是否已实例
		if(instance==null){
			
			//多线程访问时，线程会在此处等待锁释放
			synchronized (SingleTonDemo4.class) {
				
				//第二次检查对象是否已实例
				if(instance==null){
					instance=new SingleTonDemo4();
				}
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
//		for (int i=0;i<20;i++) {
//			Thread thread=new Thread(new Runnable() {				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					System.out.println(SingleTonDemo4.getInstance());
//				}
//			});
//			
//			thread.start();
//		}
	    
	    /**
	     * list删除元素问题，建议采用iterator
	     */
	    //使用iterator.remove()能正常删除
	    List<Integer> list=new ArrayList<>();
	    list.add(1);
	    list.add(2);
	    list.add(3);
//	    Iterator<Integer> iterator=list.iterator();
//	    while(iterator.hasNext()){
//	        Integer value=iterator.next();
//	        if(Objects.equals(value, 1)){
//	            //使用list.remove(value)，会报ConcurrentModificationException异常
//	            iterator.remove();
//	        }
//	    }
//	    System.out.println(list);
//	    
//	    //下面代码foreach也会报ConcurrentModificationException异常
//	    list.add(4);
//        list.add(5);
//        list.add(6);
//	    for (Integer value2 : list) {
//            if(Objects.equals(value2, 1)){
//                list.remove(value2);
//            }
//        }
//	    System.out.println(list);
	    
	    
	    /**
	     * ArrayList在多线程环境下线程不安全问题
	     * 原因：iterator实现中的expectedModCount != modCount导致
	     */
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Iterator<Integer> iterator=list.iterator();
//                while(iterator.hasNext()){
//                    Integer value=iterator.next();
//                    System.out.println(value);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//        
//	    new Thread(new Runnable() {            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Iterator<Integer> iterator=list.iterator();
//                while(iterator.hasNext()){
//                    Integer value=iterator.next();
//                    if(Objects.equals(value, 2)){
//                        iterator.remove();
//                    }
//                }
//            }
//        }).start();
	    
	    
	    
	    /**
	     * Vector是线程安全，但是也会报ConcurrentModificationException异常
	     * 原因：
	     * 虽然Vector的方法采用了synchronized进行了同步，但是实际上通过Iterator访问的情况下，每个线程里面返回的是不同的iterator，也即是说expectedModCount是每个线程私有。
	     * expectedModCount为线程私有，两条线程各自改动后，与共享变量modCount不相等导致
	     */
//	    Vector<Integer> vector=new Vector<>();
//	    vector.addElement(1);
//	    vector.addElement(2);
//	    vector.addElement(3);
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Iterator<Integer> iterator=vector.iterator();
//                while(iterator.hasNext()){
//                    Integer value=iterator.next();
//                    System.out.println(value);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//        
//        new Thread(new Runnable() {            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Iterator<Integer> iterator=vector.iterator();
//                while(iterator.hasNext()){
//                    Integer value=iterator.next();
//                    if(Objects.equals(value, 2)){
//                        iterator.remove();
//                    }
//                }
//            }
//        }).start();
//	    

	    /**
	     * 使用并发包java.util.concurrent线程安全类CopyOnWriteArrayList
	     */
	    CopyOnWriteArrayList<Integer> concurrentList=new CopyOnWriteArrayList<>();
	    concurrentList.add(1);
	    concurrentList.add(2);
	    concurrentList.add(3);
	    
        new Thread(new Runnable() {
          
              @Override
              public void run() {
                  // TODO Auto-generated method stub
                  Iterator<Integer> iterator=concurrentList.iterator();
                  while(iterator.hasNext()){
                      Integer value=iterator.next();
                      System.out.println(value);
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                      }
                  }
              }
        }).start();
          
        new Thread(new Runnable() {            
              @Override
              public void run() {
                  // TODO Auto-generated method stub
                  Iterator<Integer> iterator=concurrentList.iterator();
                  while(iterator.hasNext()){
                      Integer value=iterator.next();
                      if(Objects.equals(value, 2)){
                          concurrentList.remove(value);
                      }
                  }
              }
        }).start();  
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(concurrentList);
	
	}
	

}
