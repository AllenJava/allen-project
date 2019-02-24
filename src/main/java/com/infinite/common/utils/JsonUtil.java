package com.infinite.common.utils;

import com.alibaba.fastjson.JSON;


/**
 * 
* @ClassName: JsonUtil
* @Description: json转换工具类
* @author chenliqiao
* @date 2018年4月4日 上午11:45:49
*
 */
public class JsonUtil {

	/**
	 * 
	* @Title: beanToJson
	* @Description: javabean转换为json字符串
	* @param @param t
	* @param @return
	* @return String
	* @throws
	 */
	public static <T> String beanToJson(T t){
		return JSON.toJSONString(t);
	}
	
	/**
	 * 
	* @Title: jsonToBean
	* @Description: json转换为javabean
	* @param @param src
	* @param @param clazz
	* @param @return
	* @return T
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T jsonToBean(String src,Class clazz){
		return (T) JSON.parseObject(src, clazz);
	}	
	
	
}
