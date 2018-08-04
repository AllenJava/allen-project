package com.infinite.singletonTest;

import java.util.Objects;

/**
 * 
* @ClassName: SingleTonDemo1
* @Description: 静态内部类方式实现单例（延迟加载）
* @author chenliqiao
* @date 2018年6月19日 上午9:33:19
*
 */
public class SingleTonDemo1 {
	
	private SingleTonDemo1(){}
	
    private static class SingletonHandler{
    	public static final SingleTonDemo1 instance=new SingleTonDemo1();
    }
    
    public static SingleTonDemo1 getInstance(){
    	return SingletonHandler.instance;
    }
	
	public static void main(String[] args) {		
		System.out.println(SingleTonDemo1.getInstance());
		System.out.println(SingleTonDemo1.getInstance());
		System.out.println(SingleTonDemo1.getInstance());
		System.out.println(SingleTonDemo1.getInstance());
	}

}
