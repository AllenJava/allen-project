package com.infinite.singletonTest;

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
		for (int i=0;i<20;i++) {
			Thread thread=new Thread(new Runnable() {				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(SingleTonDemo4.getInstance());
				}
			});
			
			thread.start();
		}
	}

}
