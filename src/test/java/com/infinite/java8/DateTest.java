package com.infinite.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class DateTest {
    
    public static void main(String[] args) {
        /**
         * LocalDate和LocalTime
         */
        LocalDate date=LocalDate.of(2018, 12, 6);
        //获取年份
        System.out.println(date.getYear());
        //获取月份
        System.out.println(date.getMonth());
        //一个月中的第几天
        System.out.println(date.getDayOfMonth());
        //一年中的第几天
        System.out.println(date.getDayOfYear());
        //星期几
        System.out.println(date.getDayOfWeek());
        //月天数
        System.out.println(date.lengthOfMonth());
        //是否闰年
        System.out.println(date.isLeapYear());
        //获取当前时间
        System.out.println(LocalDate.now());
        LocalTime time=LocalTime.of(14, 59, 59);
        System.out.println(time.getHour());
        System.out.println(time.getMinute());
        System.out.println(time.getSecond());
        //使用parse
        System.out.println(LocalDate.parse("2018-12-06"));
        System.out.println(LocalTime.parse("15:07:58"));
        //LocalDateTime(LocalDate和LocalTime的复合类)
        LocalDateTime dt1=LocalDateTime.of(date, time);
        LocalDateTime dt2=LocalDateTime.of(2018, 12, 6, 15, 14, 30);
        System.out.println(dt1);
        System.out.println(dt2);
        //date.atTime(time) 或者 time.atDate(date)获取LocalDateTime对象
        LocalDateTime dt3=date.atTime(time);
        LocalDateTime dt4=date.atTime(15, 18, 25);
        LocalDateTime dt5=time.atDate(date);
        System.out.println(dt3);
        System.out.println(dt4);
        System.out.println(dt5);
        //LocalDateTime.toLocalDate() 或者 toLocalTime()
        LocalDate date1=dt1.toLocalDate();
        LocalTime time1=dt1.toLocalTime();
        System.out.println(date1);
        System.out.println(time1);
        
        //如果你已经有一个LocalDate对象，想要创建它的一个修改版，最直接也最简单的方法是使
        //用withAttribute方法。 withAttribute方法会创建对象的一个副本，并按照需要修改它的属性
        LocalDate date11=LocalDate.of(2018, 12, 7);
        LocalDate date12=date11.withYear(2011);
        LocalDate date13=date12.withDayOfMonth(25);
        LocalDate date14=date13.with(ChronoField.MONTH_OF_YEAR, 8);
        System.out.println(date11);
        System.out.println(date12);
        System.out.println(date13);
        System.out.println(date14);
        
        //以相对方式修改LocalDate对象的属性
        LocalDate date15=LocalDate.of(2018, 12, 7);
        LocalDate date16=date15.plusWeeks(1);
        LocalDate date17=date16.minusMonths(2);
        LocalDate date18=date17.plus(6, ChronoUnit.MONTHS);
        System.out.println(date15);
        System.out.println(date16);
        System.out.println(date17);
        System.out.println(date18);
        
        //打印输出及解析日期和时间对象
        //和老的java.util.DateFormat相比较，所有的DateTimeFormatter实例都是线程安全
        //的。所以，你能够以单例模式创建格式器实例，就像DateTimeFormatter所定义的那些常量，
        //并能在多个线程间共享这些实例
        LocalDate date19=LocalDate.of(2018, 12, 7);
        String dateStr1=date19.format(DateTimeFormatter.BASIC_ISO_DATE);
        String dateStr2=date19.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(dateStr1);
        System.out.println(dateStr2);
        LocalDate date20=LocalDate.parse("20181225", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date21=LocalDate.parse("2018-12-25", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(date20);
        System.out.println(date21);
        //DateTimeFormatter类还支持一个静态工ԇ方法，它可以按照某个特定的模式创建格式器
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date22=LocalDate.of(2018, 12, 7);
        String dateStr3=date22.format(formatter);
        LocalDate date23=LocalDate.parse(dateStr3, formatter);
        System.out.println(dateStr3);
        System.out.println(date23);
    }

}
