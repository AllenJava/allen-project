package com.infinite.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.channels.Channel;
import java.util.Hashtable;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author allen
 * 虚引用（幽灵引用）：用PhantomReference表示
 * 说明：“虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。
 *      如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 *      虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：
 *      虚引用必须和引用队列 （ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，
 *      如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
 *      程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。
 *      如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
 */
public class PhantomReferenceDemo {
	
	public static final int _1MB=1024*1024;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Object strongRef=new byte[5*_1MB];
		
		//虚引用必须和引用队列 （ReferenceQueue）联合使用
		ReferenceQueue queue=new ReferenceQueue<>();
		PhantomReference<Object> phantomRef=new PhantomReference<Object>(strongRef, queue);
		strongRef=null;
		
		System.out.println(phantomRef.get());
		System.out.println(queue.poll());
		
		Object strongRef2=new byte[5*_1MB];
	}

}
