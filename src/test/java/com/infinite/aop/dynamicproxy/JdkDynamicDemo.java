package com.infinite.aop.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
* @ClassName: JdkDynamicDemo
* @Description: JDK动态代理
*       参考url:  https://blog.csdn.net/yaomingyang/article/details/80981004
* @author chenliqiao
* @date 2019年3月19日 上午11:36:48
*
 */
public class JdkDynamicDemo {
    
    /**
     * 
    * @ClassName: Waiter
    * @Description: 目标接口类
    * @author chenliqiao
    * @date 2019年3月19日 上午11:37:25
    *
     */
    public interface Waiter{
        void greetTo();
        void serveTo();
    }
    
    /**
     * 
    * @ClassName: NativeWaiter
    * @Description: 目标实现类
    * @author chenliqiao
    * @date 2019年3月19日 上午11:37:53
    *
     */
    public static class NativeWaiter implements Waiter{

        @Override
        public void greetTo() {
            System.out.println("greet to ...");
        }

        @Override
        public void serveTo() {
            System.out.println("serve to ...");
        }        
    }
    
    /**
     * 
    * @ClassName: WaiterInvocationHandler
    * @Description: InvocationHandler接口是proxy代理实例的调用处理程序实现的一个接口，
    *               每一个proxy代理实例都有一个关联的调用处理程序；在代理实例调用方法时，方法调用被编码分派到调用处理程序的invoke方法
    *            注：将横切逻辑和目标类方法逻辑编织在一起
    * @date 2019年3月19日 上午11:38:19
    *
     */
    static class WaiterInvocationHandler implements InvocationHandler{
        
        //被代理的目标对象
        private Object target;
        
        public WaiterInvocationHandler(Object target){
            this.target=target;
        }

        /**
         * proxy:生成的代理对象，在invoke()方法中不需要用到
         * method：目标类的方法
         * args：目标类的参数
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //横切逻辑
            System.out.println("hello mr/miss xxx");
            
            //反射机制调用目标方法
            Object object=method.invoke(target, args);
            
            //横切逻辑
            System.out.println("welcome to you next time");
            
            return object;
        }
        
    }
    
    public static void main(String[] args) {
        //目标类对象
        Waiter waiter=new NativeWaiter();
        
        //InvocationHandler对象
        WaiterInvocationHandler invocationHandler=new WaiterInvocationHandler(waiter);
        
        //生成代理对象
        Waiter proxyInstance=(Waiter) Proxy.newProxyInstance(waiter.getClass().getClassLoader(), waiter.getClass().getInterfaces(), invocationHandler);
        System.out.println(proxyInstance.getClass().getName());
        
        //通过代理对象调用业务方法
        proxyInstance.greetTo();
        proxyInstance.serveTo();
    }

}
