package com.infinite.common.utils;

import java.math.BigDecimal;

/**
 * 
* @ClassName: BigDecimalUtil
* @Description: BigDecimal工具类
* @author chenliqiao
* @date 2018年8月2日 下午4:29:50
*
 */
public class BigDecimalUtil {
    
    /**
     * 加法
     */
    public static BigDecimal add(Object value1,Object value2){
        value1=value1!=null?value1:0;
        value2=value2!=null?value2:0;
        BigDecimal b1=new BigDecimal(String.valueOf(value1));
        BigDecimal b2=new BigDecimal(String.valueOf(value2));
        return b1.add(b2);
    }
    
    /**
     * 减法
     */
    public static BigDecimal sub(Object value1,Object value2){
        value1=value1!=null?value1:0;
        value2=value2!=null?value2:0;
        BigDecimal b1=new BigDecimal(String.valueOf(value1));
        BigDecimal b2=new BigDecimal(String.valueOf(value2));
        return b1.subtract(b2);
    }
    
    /**
     * 乘法
     */
    public static BigDecimal mul(Object value1,Object value2){
        value1=value1!=null?value1:0;
        value2=value2!=null?value2:0;
        BigDecimal b1=new BigDecimal(String.valueOf(value1));
        BigDecimal b2=new BigDecimal(String.valueOf(value2));
        return b1.multiply(b2);
    }
    
    /**
     * 除法（保留两位小数，采用BigDecimal.ROUND_HALF_UP策略）
     */
    public static BigDecimal dev(Object value1,Object value2){
        value1=value1!=null?value1:0;
        value2=value2!=null?value2:0;
        BigDecimal b1=new BigDecimal(String.valueOf(value1));
        BigDecimal b2=new BigDecimal(String.valueOf(value2));
        BigDecimal result=null;
        try {
            result=b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e) {
            //捕捉除数为零异常，并做兼容，返回0
            result=new BigDecimal(0);
        }
        return result;
    }    

}
