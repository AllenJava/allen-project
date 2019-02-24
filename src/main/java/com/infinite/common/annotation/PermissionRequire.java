package com.infinite.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: PermissionRequire
* @Description: 检查是否拥有权限（在controller的方法上使用此注解，该方法在被调用时会检查是否拥有访问权限）
* @author chenliqiao
* @date 2018年1月5日 下午2:41:51
*
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionRequire {
	
	/**
	 * 
	* @Title: name
	* @Description: 权限名称标记
	* @param @return
	* @return String
	* @throws
	 */
	String name();
}
