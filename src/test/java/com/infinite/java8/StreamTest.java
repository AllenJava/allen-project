package com.infinite.java8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    
    public static void main(String[] args) {       
        /**
         * 流操作：映射
         */
        //map
        List<String> fruits=Arrays.asList("apple","pear","banana","orange","apple","banana");
        System.out.println(fruits.stream().map(String::length).collect(Collectors.toList()));
        //flatMap(扁平化流)
        //首先map将数据源映射成字符串数组, 然后通过Arrays.stream()方法将每个数组转化流Stream<String[]>，最后再由flatMap转化为扁平化流，即合并为一个流
        List<String> characters=fruits.stream().map(e -> e.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(characters);
        
        /**
         * 流操作：筛选和切片
         */
        //filter(谓词筛选，返回boolean的接口函数)
        List<String> filterFruits=fruits.stream().filter(e -> e.length()>4).collect(Collectors.toList());
        System.out.println(filterFruits);
        //limit(截断流)
        List<String> limitFruits=fruits.stream().filter(e -> e.length()>4).limit(2).collect(Collectors.toList());
        System.out.println(limitFruits);
        //distinct(去重)
        List<String> distinctFruits=fruits.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctFruits);
        //skip(跳过元素)
        List<String> skipFruits=fruits.stream().distinct().skip(3).collect(Collectors.toList());
        System.out.println(skipFruits);
        
        /**
         * 查找与匹配(终端操作)
         */
        //anyMatch(检查谓词是否至少匹配一个元素)
        boolean anyMatch=fruits.stream().anyMatch(e ->e.contains("a"));
        System.out.println(anyMatch);
        //allMatch(检查谓词是否匹配所有元素)
        boolean allMatch=fruits.stream().allMatch(e ->e.contains("a"));
        System.out.println(allMatch);
        //noneMatch(检查谓词是否不匹配所有元素)
        boolean noneMatch=fruits.stream().noneMatch(e -> e.length()>4);
        System.out.println(noneMatch);
        //findAny(返回当前流中任意的元素)
        Optional<String> findAnyOptional=fruits.stream().filter(e ->e.length()>4).findAny();
        System.out.println(findAnyOptional.get());
        //findFirst(返回当前流中第一个元素)
        Optional<String> findFirstOptional=fruits.stream().filter(e ->e.contains("b")).findFirst();
        System.out.println(findFirstOptional.get());
        
        /**
         * 归约
         */
        List<Menu> menuList=new ArrayList<>();
        menuList.add(new Menu("A", 20.0, 100));
        menuList.add(new Menu("B", 30.0, 180));
        menuList.add(new Menu("C", 50.0, 220));
        menuList.add(new Menu("D", 80.0, 300));
        //元素求和
        double totalPrice=menuList.stream().map(Menu::getPrice).reduce(0.0, (a,b)->a+b);
        Optional<Double> totalPriceOptional=menuList.stream().map(Menu::getPrice).reduce((a,b)->a+b);
        Optional<Double> totalPriceOptional2=menuList.stream().map(Menu::getPrice).reduce(Double::sum);
        double totalPrice2=menuList.stream().mapToDouble(Menu::getPrice).sum();
        System.out.println(totalPrice);
        System.out.println(totalPriceOptional.get());
        System.out.println(totalPriceOptional2.get());
        System.out.println(totalPrice2);
        //最大值和最小值
        Optional<Integer> maxOptional=menuList.stream().map(Menu::getCalories).reduce((x,y)->x>y?x:y);
        Optional<Integer> minOptional=menuList.stream().map(Menu::getCalories).reduce((x,y)->x>y?y:x);
        System.out.println(maxOptional.get()+" "+minOptional.get());
        Optional<Double> maxOptional2=menuList.stream().map(Menu::getPrice).reduce(Double::max);
        Optional<Double> minOptional2=menuList.stream().map(Menu::getPrice).reduce(Double::min);
        System.out.println(maxOptional2.get()+" "+minOptional2.get());
        //计算流中元素的个数
        //map+reduce
        Optional<Integer>countOptional=menuList.stream().map(e->1).reduce(Integer::sum);
        //使用count()
        long count=menuList.stream().count();
        System.out.println(countOptional.get());
        System.out.println(count);
        
        /**
         * 数值流
         */
        //此做法Integer转换为int,会有拆箱成本~
        int totalCalories=menuList.stream().map(Menu::getCalories).reduce(0,Integer::sum);
        System.out.println(totalCalories);
        //采用intStream，原始类型流特化避免了暗含的装箱成本
        IntStream intStream=menuList.stream().mapToInt(Menu::getCalories);
        totalCalories=intStream.reduce(0,Integer::sum);
        System.out.println(totalCalories);
        double totalPrices=menuList.stream().mapToDouble(Menu::getPrice).reduce(0,Double::sum);
        System.out.println(totalPrices);
        //数值流转回对象流
        intStream=menuList.stream().mapToInt(Menu::getCalories);
        Stream<Integer> stream=intStream.boxed();
        System.out.println(stream);
        //生成数值范围
        //range不包含结束值
        IntStream.range(2, 50).filter(e ->e%2==0).forEach(e ->{
            System.out.print(e+" ");
        });
        //rangeClosed包含结束值
        IntStream.rangeClosed(50, 101).filter(e ->e%2==1).forEach(e ->{
            System.out.print(e+" ");
        });
        
        
        /**
         * 构建流
         */
        //由值创建流：Stream.of
        Stream<String> ofStream=Stream.of("shoes","clothes","cat","dress");
        ofStream.map(String::toUpperCase).forEach(System.out::println);
        //得到一个空流：Stream.empty()
        Stream<String> emptyStream=Stream.empty();
        emptyStream.map(String::toUpperCase).forEach(System.out::println);
        //由数组创建流
        int[] intArray={21,3,66,34,86,99};
        IntStream arrayStream=Arrays.stream(intArray);
        System.out.println(arrayStream.sum());
        //由文件生成流(Files.lines得到一个流，然后其中每个元素都对应定文件中的一行)
        Stream<String> lines=null;
        try{
            lines=Files.lines( Paths.get("C:\\Users\\035\\Desktop\\2222.sql"),Charset.defaultCharset());
            lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lines.close();
        }
        //有函数创建流：生成无限流
        //iterate(没加limit则为无限流，会永远计算下去)
        Stream.iterate(0, n->n+2).limit(10).forEach(System.out::println);
        //generate(无状态，生成的新值不会作为函数的入参)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        
    }
    
    
    /**
     * 
    * @ClassName: Demo
    * @Description: 
    * @author chenliqiao
    * @date 2018年11月12日 下午2:18:47
    *
     */
    public static class Demo{
        private String name;
        private int status;
        public Demo(String name,int status){
            this.name=name;
            this.status=status;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
    }
    
    /**
     * 
    * @ClassName: Menu
    * @Description: 菜单类
    * @author chenliqiao
    * @date 2018年11月12日 下午2:16:13
    *
     */
    public static class Menu{
        private String name;
        private Double price;
        /**热量/卡路里**/
        private Integer calories;
        
        public Menu(String name,Double price,Integer calories){
            this.name=name;
            this.price=price;
            this.calories=calories;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Double getPrice() {
            return price;
        }
        public void setPrice(Double price) {
            this.price = price;
        }
        public Integer getCalories() {
            return calories;
        }
        public void setCalories(Integer calories) {
            this.calories = calories;
        }
    }

}
