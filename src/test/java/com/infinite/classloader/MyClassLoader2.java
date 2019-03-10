package com.infinite.classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
* @ClassName: MyClassLoader2
* @Description: 打破双亲委派模型
* @author chenliqiao
* @date 2019年3月5日 上午10:32:20
*
 */
public class MyClassLoader2 extends ClassLoader{
    
    private String classFilePath;
    
    public MyClassLoader2(String classFilePath){
        this.classFilePath=classFilePath;
    }

    /**
     * 打破双亲委派模型需要重写loadClass()方法
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        Class<?> c = findLoadedClass(name);
        if(c!=null){
            return c;
        }
        
        //以"java."的包，jdk添加了保护权限，所以仍然交由jdk的类加载器处理
        if(name.startsWith("java.")){
            return ClassLoader.getSystemClassLoader().loadClass(name);
        }
        
        //读取自定义的字节流
        byte[] classData=null;
        FileInputStream is=null;
        try {
            is=new FileInputStream(classFilePath);
            classData=new byte[is.available()];
            is.read(classData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //根据自定义字节流创建class对象
        return defineClass(name, classData, 0, classData.length);
    }  
    
    public static void main(String[] args) throws ClassNotFoundException {
        
        MyClassLoader2 myClassLoader=new MyClassLoader2("F:\\String.class");
        Class<?> myClass=myClassLoader.loadClass("com.infinite.classloader.String");
        
        ClassLoader systemClassLoader=ClassLoader.getSystemClassLoader();
        Class<?> systemClass=systemClassLoader.loadClass("com.infinite.classloader.String");
        
        System.out.println(myClass==systemClass);
        System.out.println(myClassLoader);
        System.out.println(systemClassLoader);
    }
    
}
