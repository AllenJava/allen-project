package com.infinite.singletonTest;

/**
 * 
* @ClassName: SingleTonDemo3
* @Description: 枚举方式实现单例
* @author chenliqiao
* @date 2018年6月19日 上午9:50:08
*
 */
public enum SingleTonDemo3 {
	instance;
	
	public void doSomeThing(){
        System.out.println("do something!");
    }
	
	public static void main(String[] args) {
		SingleTonDemo3.instance.doSomeThing();
		SingleTonDemo3.instance.doSomeThing();
		SingleTonDemo3.instance.doSomeThing();
	}
}
