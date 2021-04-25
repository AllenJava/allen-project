package com.infinite.reference;

import java.lang.ref.WeakReference;

/**
 * 
 * @author allen
 * 弱引用：使用WeakReference表示
 * 说明：只具有弱引用的对象拥有更短暂的生命周期。
 *       在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
 *       不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，
 *       因此不一定会很快发现那些只具有弱引用的对象
 */
public class WeakReferenceDemo {
	
	public static final int _1MB=1024*1024;

	public static void main(String[] args) {
		
		Object strongRef=new byte[5*_1MB];
		
		WeakReference<Object> weakRef=new WeakReference<Object>(strongRef);
		
		//因为strongRef强引用也指向强可及对象Object，所以结束strongRef对这个Object实例的强引用
		//，此时这个Object对象成为了弱可及对象
		strongRef=null;
		
		//弱引用指向的弱可及对象，即Object对象，每次GC都会被回收
		//System.gc();//手动gc
		if(weakRef.get()==null){
			System.out.println("weakRef对象已被GC回收~");
		}else{
			System.out.println("weakRef对象未被GC回收~");
		}
		
		//再创建一个5M的Object对象，此时内存不够分配
		Object strongRef2=new byte[5*_1MB];
		
		if(weakRef.get()==null){
			System.out.println("weakRef对象已被GC回收~");
		}else{
			System.out.println("weakRef对象未被GC回收~");
		}
		
		System.out.println(strongRef2);
	}
}
