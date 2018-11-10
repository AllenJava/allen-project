package com.infinite.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaTest {
    
    public static void main(String[] args) {
        Arrays.asList("a","b","c").forEach(e -> System.out.print(e));
        System.out.println();
        
        Arrays.asList("a","b","c").forEach((String e) -> System.out.print(e));
        System.out.println();
        
        Arrays.asList("1","2","3").forEach(e -> {
            System.out.print(e);
            System.out.print(e+1);
        });
        System.out.println();
        

        String separator = ",";
        Arrays.asList("j","k","L").forEach(e -> System.out.print(e+separator));
        System.out.println();
        
        Integer[] sortArr={898,675,5425};
        Arrays.asList(sortArr).sort((e1,e2) -> e2.compareTo(e1));       
        Arrays.asList(898,675,5425).sort((e1,e2) -> {
            int result=e1.compareTo(e2);
            return result;
        });
        System.out.println(Arrays.asList(sortArr));  
        
        
        
        /**
         * 自定义函数接口使用
         */
        List<Apple> appleList=new ArrayList<>();
        appleList.add(new Apple("red", 100.00));
        appleList.add(new Apple("green", 150.00));
        appleList.add(new Apple("yellow", 180.00));
        
        //普通实现
        List<Apple> greenAppleList=filterList(appleList, new AppleColorPrediate());
        List<Apple> weightAppleList=filterList(appleList, new AppleWeightrPrediate());
        System.out.println(greenAppleList);
        System.out.println(weightAppleList);
        
        //匿名内部类实现
        List<Apple> redAppleList=filterList(appleList,new Prediate<Apple>() {
            @Override
            public boolean isMatched(Apple apple) {
                // TODO Auto-generated method stub
                return Objects.equals("red", apple.getColor());
            }
        });
        List<Apple> weightAppleList2=filterList(appleList, new Prediate<Apple>() {
            @Override
            public boolean isMatched(Apple apple) {
                // TODO Auto-generated method stub
                return apple.weight>=180;
            }
        });
        System.out.println(redAppleList);
        System.out.println(weightAppleList2);
        
        //Lambda方式实现(注意：Lambda表达式的签名必须与接口函数的抽象方法一样，即(参数)->(boolean表达式))
        List<Apple> yellowAppleList=filterList(appleList, (Apple apple)->Objects.equals("yellow", apple.getColor()));
        List<Apple> weightAppleList3=filterList(appleList, (Apple apple)->apple.getWeight()<=100);
        System.out.println(yellowAppleList);
        System.out.println(weightAppleList3);
        
        
        
        
        /**
         * Lambda排序
         */
        //实现1：使用list.sort()
        appleList.sort((Apple apple1,Apple apple2)->apple1.getWeight().compareTo(apple2.getWeight()));
        System.out.println(appleList);
        //实现2：使用Collections(匿名内部类实现)
        Collections.sort(appleList,new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) { 
                // TODO Auto-generated method stub
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(appleList);
        //实现3：使用Collections（Lambda表达式实现）
        Collections.sort(appleList, (Apple apple1,Apple apple2)->apple2.getWeight().compareTo(apple1.getWeight()));
        System.out.println(appleList);
        
        
        
        
        /**
         * API自带几个函数接口
         */
        //1.Predicate
        Predicate<Apple> applePredicate=new Predicate<Apple>() {           
            @Override
            public boolean test(Apple apple) {
                return Objects.equals("red", apple.getColor());
            }
        };
        List<Apple> appleFilterList=filterList(appleList, applePredicate);
        System.out.println(appleFilterList);
        List<Apple> appleFilterList2=filterList(appleList, (Apple apple)->apple.getWeight()>150);
        System.out.println(appleFilterList2);
        
        //2.Consumer
        Consumer<Apple> appleConsumer=new Consumer<Apple>() {  
            @Override
            public void accept(Apple apple) {
                System.out.println(apple);
            }
        };
        printList(appleList, appleConsumer);
        printList(appleList, (Apple apple)->{
            System.out.println(apple);
            System.out.println("~~~");
        });
        
        //3.Function
        Function<Apple, String> appleFunction=new Function<Apple, String>() {           
            @Override
            public String apply(Apple t) {              
                return t.getWeight()<=150?t.getColor():null;
            }
        };
        List<String> appleColors=functionList(appleList, appleFunction);
        System.out.println(appleColors);
        List<String> appleColors2=functionList(appleList, (Apple apple)->apple.getWeight()>=180?apple.getColor():null);
        System.out.println(appleColors2);
    }
    
    /**
     * 
    * @ClassName: Prediate
    * @Description: 函数接口类（只能有一个抽象方法）
    * @author chenliqiao
    * @date 2018年11月7日 上午11:01:09
    * 
    * @param <T>
     */
    public interface Prediate<T>{
        boolean isMatched(T object);
    }
    
    /**
     * 
    * @ClassName: Apple
    * @Description: 实体类
    * @author chenliqiao
    * @date 2018年11月7日 上午11:01:20
    *
     */
    static class Apple{
        
        private String color;
        
        private Double weight;
        
        public Apple(String color,Double weight){
            this.color=color;
            this.weight=weight;
        }
        
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public Double getWeight() {
            return weight;
        }
        public void setWeight(Double weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Apple [color=" + color + ", weight=" + weight + "]";
        }
    }
    
    /**
     * 
    * @ClassName: AppleColorPrediate
    * @Description: 函数接口实现类（根据颜色过滤）
    * @author chenliqiao
    * @date 2018年11月7日 上午11:01:32
    *
     */
    static class AppleColorPrediate implements Prediate<Apple>{

        @Override
        public boolean isMatched(Apple apple) {
            // TODO Auto-generated method stub
            return "green".equals(apple.getColor());
        }
        
    }
    
    /**
     * 
    * @ClassName: AppleWeightrPrediate
    * @Description: 函数接口实现类（根据重量过滤）
    * @author chenliqiao
    * @date 2018年11月7日 上午11:01:56
    *
     */
    static class AppleWeightrPrediate implements Prediate<Apple>{

        @Override
        public boolean isMatched(Apple apple) {
            // TODO Auto-generated method stub
            return apple.getWeight()<=150;
        }
    }
    
    /**
     * 根据函数接口实例过滤数据方法
     */
    public static List<Apple> filterList(List<Apple> appleList,Prediate<Apple> prediate){
        List<Apple> resultList=new ArrayList<>();
        for (Apple apple : appleList) {
            if(prediate.isMatched(apple)){
                resultList.add(apple);
            }
        }
        return resultList;
    }
    
    /**
     * 根据Java 8 API函数接口 [Predicate]实例操作方法
     */
    public static <T> List<T> filterList(List<T> list,Predicate<T> predicate){
        List<T> resultList=new ArrayList<>();
        for (T object : list) {
            if(predicate.test(object)){
                resultList.add(object);
            }
        }
        return resultList;
    }
    
    /**
     * 根据Java 8 API函数接口 [Consumer]实例操作方法
     */
    public static <T> void printList(List<T> list,Consumer<T> consumer){
        for (T t : list) {
            consumer.accept(t);
        }
    }
    
    /**
     * 根据Java 8 API函数接口 [Function]实例操作方法
     */
    public static <T,R> List<R> functionList(List<T> list,Function<T,R> function){
        List<R> resultList=new ArrayList<>();
        for (T t : list) {
            R r=function.apply(t);
            if(r!=null){
                resultList.add(r); 
            }
        }
        return resultList;
    }

}
