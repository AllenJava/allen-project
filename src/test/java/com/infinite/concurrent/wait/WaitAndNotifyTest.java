package com.infinite.concurrent.wait;

import java.util.concurrent.TimeUnit;

/**
 * 使用wait和notify实现两条线程交替打印数字
 * 
 * wait和notify的经典范式：
 * 一.等待方（wait）:
 * 1.获取对象的锁；
 * 2.如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件；
 * 3.条件满足则执行对应的逻辑；
 * 对应的伪代码：
 * synchronized (对象) {
 *    while(条件不满足){
 *      对象.wait();
 *    }
 *    
 *    对应的处理逻辑
 * }
 * 
 * 二.通知方（notify）：
 * 1.获取对象的锁；
 * 2.改变条件；
 * 3.通知所有等待在对象上的线程；
 * 对应的伪代码如下：
 * synchronized (lock) {
 *   改变条件
 *   对象.notifyAll();
 * }
 * @author allen
 *
 */
public class WaitAndNotifyTest {
	
	private static Object lock=new Object();
	
	/**flag标识：true表示奇数，false表示偶数**/
	private static Boolean flag=true;;
	
	private static int count=1;
	
	/**
	 * wait线程
	 * @author allen
	 *
	 */
	static class WaitThread implements Runnable{

		@Override
		public void run() {
			synchronized (lock) {				
				while(true){
					//不符合条件，调用lock.wait()，线程进入等待队列，并释放锁
					//注意：即使当前线程被notify通知后，仍要进行条件检查，因此使用while而不是if
					while(!flag){
						try {
							TimeUnit.SECONDS.sleep(2);
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//符合条件，线程往下执行
					System.out.println("wait线程打印奇数："+count++);
					flag=false;
					lock.notify();
				}
			}
		}
		
	}
	
	/**
	 * noity线程
	 * @author allen
	 *
	 */
	static class NotifyThead implements Runnable{

		@Override
		public void run() {
			synchronized (lock) {
				while(true){
					//不符合条件，调用lock.wait()，线程进入等待队列，并释放锁
					//注意：即使当前线程被notify通知后，仍要进行条件检查，因此使用while而不是if
					while(flag){
						try {
							TimeUnit.SECONDS.sleep(2);
							lock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//符合条件，线程往下执行
					System.out.println("notify线程打印偶数："+count++);
					flag=true;
					lock.notify();
				}                 
			}			
		}
	}
	
	public static void main(String[] args) {
		new Thread(new WaitThread()).start();
		new Thread(new NotifyThead()).start();
	}

}
