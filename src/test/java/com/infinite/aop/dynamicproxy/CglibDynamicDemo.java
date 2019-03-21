package com.infinite.aop.dynamicproxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 
* @ClassName: CglibDynamicDemo
* @Description: CGLIB字节码动态代理
*       参考url:  https://blog.csdn.net/yhl_jxy/article/details/80633194
* @author chenliqiao
* @date 2019年3月19日 下午2:12:07
*
 */
public class CglibDynamicDemo {
    
    /**
     * 
    * @ClassName: NativeWaiter
    * @Description: 目标类
    * @author chenliqiao
    * @date 2019年3月19日 上午11:37:53
    *
     */
    public static class NativeWaiter{

        public void greetTo() {
            System.out.println("greet to ...");
        }

        /**
         * private 和 final不能被子类继承，因此不能被子类对象代理
         */
        final void serveTo() {
            System.out.println("serve to ...");
        }  
        
        private void serveTo2() {
            System.out.println("serve2 to ...");
        }
    }
    
    public static class NativeWaiterCglibProxy implements MethodInterceptor{
        
        /**
         * 生成目标类的子类代理实例
         * targetClass：目标类的类型
         */
        public Object newProxyInstance(Class<?> targetClass){
            Enhancer enhancer=new Enhancer();
            enhancer.setSuperclass(targetClass);
            enhancer.setCallback(this); 
            return enhancer.create();
        }

        /**
         * sub:子代理类实例
         * targetMethod：目标类被代理的方法
         * targetAgrs：被代理方法的入参
         * methodProxy：子类代理方法
         */
        @Override
        public Object intercept(Object sub, Method targetMethod, Object[] targetAgrs, MethodProxy methodProxy) throws Throwable {
            System.out.println("***********");
            Object object=methodProxy.invokeSuper(sub, targetAgrs);
            System.out.println("+++++++++++");
            return object;
        }
    }
    
    public static void main(String[] args) {
        NativeWaiterCglibProxy proxy=new NativeWaiterCglibProxy();
        NativeWaiter proxyInstance=(NativeWaiter) proxy.newProxyInstance(NativeWaiter.class);
        proxyInstance.greetTo();
        proxyInstance.serveTo();
        proxyInstance.serveTo2();
    }

}
