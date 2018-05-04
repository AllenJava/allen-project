package com.infinite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinite.common.mq.Producer;
import com.infinite.controller.vo.ResultBean;

/**
 * 
* @ClassName: ActiveMQTestController
* @Description: 用于测试ActiveMQ
* @author chenliqiao
* @date 2018年5月4日 下午2:39:44
*
 */
@RestController
public class ActiveMQTestController {
	
	@Autowired
	private Producer producer;

	@RequestMapping(value="/activeMQ/test/addMessage")
	public ResultBean<Object> addMessage(@RequestParam(required=true) String type, @RequestParam(required=true) String message){
		this.producer.send(Integer.parseInt(type), message);
		return new ResultBean<Object>();
	}
}
