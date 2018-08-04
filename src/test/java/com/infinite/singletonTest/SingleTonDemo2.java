package com.infinite.singletonTest;

/**
 * 
* @ClassName: SingleTonDemo2
* @Description: 懒汉模式实现单例（非延迟加载，即类初始化实例化）
* @author chenliqiao
* @date 2018年6月19日 上午9:36:41
*
 */
public class SingleTonDemo2 {
	
	private static SingleTonDemo2 instance=null;
	
	private SingleTonDemo2(){};
	
	static{
		instance=new SingleTonDemo2();
	}
	
	public static SingleTonDemo2 getInstance(){
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println(SingleTonDemo2.getInstance());
		System.out.println(SingleTonDemo2.getInstance());
		System.out.println(SingleTonDemo2.getInstance());
	}

}
