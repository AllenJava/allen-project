package com.infinite.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
* @ClassName: MyClassLoader
* @Description: 自定义类加载器:
*               1、如果不想打破双亲委派模型，那么只需要重写findClass方法即可
                2、如果想打破双亲委派模型，那么就重写整个loadClass方法
* @author chenliqiao
* @date 2019年3月4日 下午3:29:45
*
 */
public class MyClassLoader extends ClassLoader{
    
    private String classFilePath;
    
    public MyClassLoader(String classFilePath){
        this.classFilePath=classFilePath;
    }
    
    /**
     * 不破坏双亲委派模型，只需要重写findClass()方法
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData=null;
        try {
            // 获取类的字节数组
            classData=getClassData(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(classData==null){
            throw new ClassNotFoundException();
        }
        
        //使用defineClass生成class对象
        return defineClass(name, classData, 0, classData.length);
    }
    
    /**
     * 编写获取class文件并转换为字节码流的逻辑
     * @param className
     * @return
     * @throws IOException 
     */
    private byte[] getClassData(String className) throws IOException {
        // 读取类文件的字节
        InputStream ins=null;
        ByteArrayOutputStream baos=null;
        try {
            ins = new FileInputStream(classFilePath);
            baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesNumRead = 0;
            // 读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }
            return baos.toByteArray();
        }finally {
            ins.close();
            baos.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
//        MyClassLoader myClassLoader=new MyClassLoader("F:\\MyClass.class");
//        Class<?> myClass=myClassLoader.loadClass("com.infinite.classloader.MyClass");
//        Object myClassInstance=myClass.newInstance();
//        System.out.println(myClassInstance);
//        
        MyClassLoader myClassLoader2=new MyClassLoader("F:\\String.class");
        Class<?> myStringClass=myClassLoader2.loadClass("com.infinite.classloader.String");
        Object myStringInstance=myStringClass.newInstance();
        System.out.println(myStringInstance);
        
        ClassLoader systemClassLoader=ClassLoader.getSystemClassLoader();
        Class<?> sysStringClass=systemClassLoader.loadClass("com.infinite.classloader.String");
        Object sysStringInstance=sysStringClass.newInstance();
        System.out.println(sysStringInstance);
        
        System.out.println(myStringClass.getClassLoader());
        System.out.println(sysStringClass.getClassLoader());
    }
 
}
