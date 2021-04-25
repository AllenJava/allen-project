package com.infinite.reference;

/**
 * 
 * @author allen
 * JVM参数配置：-Xms10M -Xmx10M -Xmn4M
 * 强引用：最常见的引用，例如 Object a=new Object()
 * 说明：对象使用强引用时，即使内存不足时，也不会被GC回收，而是直接出现OutOfMemoryError
 */
public class StrongReferenceDemo {
	
	public static final int _1MB=1024*1024;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[] obj=new byte[1*_1MB];
		//System.gc();
        //System.out.println(obj);
        
        byte[] obj2=new byte[10*_1MB];
	}

}
