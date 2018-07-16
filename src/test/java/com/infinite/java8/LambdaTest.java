package com.infinite.java8;

import java.util.Arrays;

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
    }

}
