package com.infinite.spring.resource;

import java.io.UnsupportedEncodingException;

import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * spring定义的资源加载接口ResourceLoader
 * @author allen
 *
 */
public class ResourceLoaderTest {
	
	public static void main(String[] args) {
		//ResourceLoader
		
	    //ResourcePatternResolver resourcePatternResolver=new 
		
//		String a="123";
//		String b="123";
//		String c=new String("123");
//		System.out.println(a==b);
//		System.out.println(a==c);
//		c.intern();
//		System.out.println(a==c);
//		System.out.println(a);
//		System.out.println(c);
//		
//		int i=130;
//		byte bb=(byte)i;
//		System.out.println(bb); 
//		
//		int k=0;
//		Integer j=new Integer(0);
//		System.out.println(k==j);
//		
//		int aa=20;
//		System.out.println(aa);
//		change(aa);
//		System.out.println(aa);
		
//		int index=1;
//		try {
//			synchronized (ResourceLoaderTest.class) {
//				while(true){
//					index++;
//					if(index==10){
//						throw new RuntimeException("抛异常");
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(Thread.currentThread().getName()+"->"+System.currentTimeMillis());
		
//		char charA='中';
//		System.out.println((int)charA);
//		
//		//Integer 常量池
//		Integer a=127;
//		Integer b=new Integer(127);
//		Integer c=new Integer(127);
//		System.out.println(a==b);
//		System.out.println(a==c);
//		System.out.println(b==c);
//		
//		//超过Integer 常量池范围（-128至127）
//		Integer d=128;
//		Integer e=128;
//		System.out.println(d==e);
		
		//字符串字节数
		String str1="中文";
		try {
			System.out.println(str1.getBytes("UTF-8").length);
			System.out.println(str1.getBytes("GBK").length);
			System.out.println(str1.getBytes("ISO-8859-1").length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//Integer字节数
		Integer int1=-1;
		System.out.println(int1.BYTES);
		
		Object obj=new Object();	
	}
	
	public static void change(int aa){
		aa=50;
		System.out.println(aa);
	}

}
