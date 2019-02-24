package com.infinite.java8;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallerStreamTest {
    
    public static void main(String[] args) {        
        //性能测试
        System.out.println("iterativeSum done in: "+measureSumPerformance(ParallerStream::iterativeSum, 10000000)+" msecs");
        System.out.println("sequentialSum done in: "+measureSumPerformance(ParallerStream::sequentialSum, 10000000)+" msecs");
        System.out.println("parallelSum done in: "+measureSumPerformance(ParallerStream::parallelSum, 10000000)+" msecs");
        
        //使用IntStream|DoubleStream|LongStream 数值流，避免自动装箱和拆箱的开销~
        System.out.println("sequentialSumReversion1 done in: "+measureSumPerformance(ParallerStream::sequentialSumReversion1, 10000000)+" msecs");
        System.out.println("parallelSumReversion1 done in: "+measureSumPerformance(ParallerStream::parallelSumReversion1, 10000000)+" msecs");
    }
    
    /**
     * 耗时计算
     */
    public static long measureSumPerformance(Function<Long, Long> adder,long n){
        long fastest=Long.MAX_VALUE;
        for(int i=0;i<10;i++){
            long startTime=System.nanoTime();
            long sum=adder.apply(n);
            long duration=(System.nanoTime()-startTime)/1000000;
            //System.out.println("Result:"+sum);
            if(duration<fastest){
                fastest=duration;
            }
        }
        return fastest;
    }
    
    public static class ParallerStream{
        /**
         * java7实现
         */
        public static long iterativeSum(long n){
            long result=0;
            for(long i=1;i<n;i++){
                result=result+i;
            }
            return result;
        }
        
        /**
         * 顺序流自然数求和
         */
        public static long sequentialSum(long n){
            return Stream.iterate(0L, i -> i+1).limit(n).reduce(0L, Long::sum);
        }
        
        /**
         * 顺序流自然数求和改版1：使用LongStream
         */
        public static long sequentialSumReversion1(long n){
            return LongStream.range(0L, n).sum();
        }
        
        /**
         * 并行流自然数求和：parallel()
         */
        public static long parallelSum(long n){
            return Stream.iterate(0L, i ->i+1).limit(n).parallel().reduce(0L, Long::sum);
        }
        
        /**
         * 并行流自然数求和改版1：使用LongStream
         */
        public static long parallelSumReversion1(long n){
            return LongStream.range(0L, n).parallel().sum();
        }
    }       

}
