package com.infinite.common.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: Task
* @Description: 任务类
* @author chenliqiao
* @date 2018年5月3日 上午11:52:10
*
 */
@Component
public class Task {
	
	/**
	 * 任务方法1
	 */
	@Async("executor")//executor为自定义的线程池名
	public void taskMethod1(){
	}
	
	/**
	 * 任务方法2
	 */
	@Async("executor")//executor为自定义的线程池名
	public void taskMethod2(){
	}

}
