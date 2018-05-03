package com.infinite.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinite.common.annotation.LoginRequire;
import com.infinite.controller.vo.ResultBean;
import com.infinite.dao.po.UserExtendInfo;
import com.infinite.dao.po.UserInfo;
import com.infinite.service.UserInfoService;
import com.infinite.service.bo.UserInfoAdd;
import com.infinite.service.bo.UserInfoQuery;
import com.infinite.service.dto.SsoUserQuery;

/**
 * 
* @ClassName: UserInfoController
* @Description: 用户信息控制层
* @author chenliqiao
* @date 2018年4月4日 上午10:46:52
*
 */
@RestController
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value="/user/queryList",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<PageInfo<UserExtendInfo>> queryList(@RequestBody UserInfoQuery request){
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		return new ResultBean<>(new PageInfo<>(this.userInfoService.findByCondition(request)));
	}
	
	/**
	 * 获取sso中心用户列表信息 
	 */
	@RequestMapping(value="/user/querySsoUserList",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<List<UserInfo>> querySsoUserList(@RequestBody SsoUserQuery request){
		return new ResultBean<>(this.userInfoService.findSsoUserList(request));
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<String> add(@RequestBody UserInfoAdd request){
		this.userInfoService.add(request);
		return new ResultBean<>();
	}
	
	/**
	 * 测试session和cookies
	 */
	@RequestMapping(value="/user/testSessionAndCookies",method=RequestMethod.GET)
	public ResultBean<String> testSessionAndCookies(String browseName,HttpServletRequest request,HttpSession sesssion){
		if(sesssion.getAttribute("browseName")==null){
			sesssion.setAttribute("browseName", browseName);
		}else{
			System.out.println(sesssion.getAttribute("browseName"));
		}
		
		Cookie[] cookies=request.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName()+":"+cookie.getValue());
		}
		return new ResultBean<>();
	}

}
