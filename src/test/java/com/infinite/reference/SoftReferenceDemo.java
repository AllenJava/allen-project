package com.infinite.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * 
 * @author allen
 * JVM参数配置：-Xms10M -Xmx10M -Xmn4M 
 * 软引用：使用SoftReference表示
 * 说明：对象使用软引用时，即使内存不足时，会被GC回收，保证内存可用；
 *      如果软可及对象被GC回收后还是内存不足时，才会出现OutOfMemoryError
 * 
 * 参考链接：https://blog.51cto.com/alinazh/1276173
 */
public class SoftReferenceDemo {
	
	public static final int _1MB=1024*1024;

	public static void main(String[] args) {
		
		Object strongRef=new byte[5*_1MB];
		
		/**
		 * 不使用ReferenceQueue（引用队列）方式
		 */
		
//		//将软引用ref指向强可及对象Object
//		SoftReference<Object> softRef=new SoftReference<>(strongRef);
//		
//		//因为strongRef强引用也指向强可及对象Object，所以结束strongRef对这个Object实例的强引用
//		//，此时这个Object对象成为了软可及对象
//		strongRef=null;
//		
//		//此时内存充足，所以GC不会回收这个Object软可及对象
//		System.gc();//手动gc
//		if(softRef.get()==null){
//			System.out.println("softRef指向的Object软可及对象 已被GC回收~");
//		}else{
//			System.out.println(softRef.get());
//			System.out.println("softRef指向的Object软可及对象 未被GC回收~");
//		}
//		
//		//再创建一个5M的Object对象，此时内存不够分配
//		Object strongRef2=new byte[5*_1MB];
//		
//		//此时内存不足，所以GC回收这个Object软可及对象
//		if(softRef.get()==null){
//			System.out.println("softRef指向的Object软可及对象 已被GC回收~");
//		}else{
//			System.out.println(softRef.get());
//			System.out.println("softRef指向的Object软可及对象 未被GC回收~");
//		}
//		
//		System.out.println(strongRef2);
		
		/**
		 * 使用ReferenceQueue（引用队列）方式
		 */
		//ReferenceQueue中保存的对象是Reference对象，而且是已经失去了它所软引用的对象（即上面的Object软可及对象）的Reference对象
		ReferenceQueue queue=new ReferenceQueue<>();
		//将软引用ref指向强可及对象Object，使用ReferenceQueue方式
		SoftReference<Object> softRef2=new SoftReference<Object>(strongRef, queue);
		strongRef=null;
		
		//此时内存充足，所以GC不会回收这个Object软可及对象
		System.gc();//手动gc
		if(softRef2.get()==null){
			System.out.println("softRef2指向的Object软可及对象 已被GC回收~");
		}else{
			System.out.println(softRef2.get());
			System.out.println("softRef2 指向的Object软可及对象 未被GC回收~");
		}
		
		SoftReference<Object> sr=null;
		//当我们调用queue的poll()方法的时候，如果这个队列中不是空队列，那么将返回队列前面的那个Reference对象。
		//在任何时候，我们都可以调用ReferenceQueue的poll()方法来检查是否有它所关心的非强可及对象被回收
		//于是我们可以把这些失去所软引用的对象的SoftReference对象清除掉
		if((sr=(SoftReference<Object>) queue.poll())!=null){
			System.out.println(queue.poll());
		}else{
			System.out.println("ReferenceQueue 为空，即 不存在可以清除回收的Reference对象");
		}
		
		//再创建一个5M的Object对象，此时内存不够分配
		Object strongRef2=new byte[5*_1MB];
		
		//此时内存不足，所以GC回收这个Object软可及对象
		if(softRef2.get()==null){
			System.out.println("softRef2指向的Object软可及对象 已被GC回收~");
		}else{
			System.out.println(softRef2.get());
			System.out.println("softRef2指向的Object软可及对象 未被GC回收~");
		}
		
		if((sr=(SoftReference<Object>) queue.poll())!=null){
			System.out.println("ReferenceQueue 不为空，"+sr+"，即存在无用的Reference对象");
			//清除sr，即无用的Reference对象
			sr.clear();
		}else{
			System.out.println("ReferenceQueue 为空，即 不存在可以清除回收的Reference对象");
		}
		
		System.out.println(strongRef2);
	}
}
