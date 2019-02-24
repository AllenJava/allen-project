package com.infinite.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    
    public static void main(String[] args) {
        String[] strs={"java8", "is", "easy", "to", "use"};
        List<String[]> distinctStrs=Arrays.stream(strs)
                .map(str -> str.split(""))
                .distinct()
                .collect(Collectors.toList());
        distinctStrs.forEach(e -> System.out.println(Arrays.asList(e)));
        
        
        String[] strs1={"java8", "is", "easy", "to", "use"};
        List<String> distinctStrs1=Arrays.stream(strs1)
                .map(str -> str.split(""))
                .flatMap(Arrays::stream)//::表示把方法作为函数传入sream内部，使得stream的每个元素都传入方法内部执行
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinctStrs1);
        
        
        Integer[]  intArr={1,3,5,2,4,6,99,56,78,88,32,57};
        List<Integer> intArrResult=Arrays.stream(intArr)
                .skip(3)
                .filter(e -> e>6)
                .peek(e -> System.out.println(e))
                .limit(8)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(intArrResult);
        
        Demo demo1=new Demo("A",1);
        Demo demo2=new Demo("B",0);
        Demo demo3=new Demo("C",1);
        Demo demo4=new Demo("D",0);
        Demo demo5=new Demo("E",1);
        List<Demo> demoList=Arrays.asList(demo1,demo2,demo3,demo4,demo5);
        demoList.forEach(e -> {
            if(e.getStatus()==1)
                System.out.print(e.getName());
        });
        System.out.println();
        
        List<String> demoNameList=demoList.stream().filter(e -> e.getStatus()==1).map(Demo::getName).sorted().collect(Collectors.toList());
        System.out.println(demoNameList);
        
        /**
         * 流只能消费一次
         */
        List<String> strList=Arrays.asList("apple","orange","pear","banana");
        Stream<String> srtStream=strList.stream();
        srtStream.forEach(e -> System.out.println(e));
        //运行会提示：stream has already been operated upon or closed
        srtStream.forEach(e -> System.out.println(e));
        
    }
    
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

}
