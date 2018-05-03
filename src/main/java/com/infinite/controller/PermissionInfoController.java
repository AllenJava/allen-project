package com.infinite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinite.common.annotation.LoginRequire;
import com.infinite.controller.vo.ResultBean;
import com.infinite.dao.po.PermissionExtendInfo;
import com.infinite.dao.po.PermissionInfo;
import com.infinite.service.PermissionInfoService;
import com.infinite.service.bo.PermissionInfoQuery;

/**
 * 
* @ClassName: PermissionInfoController
* @Description: 权限信息控制层
* @author chenliqiao
* @date 2018年4月8日 下午2:12:52
*
 */
@RestController
public class PermissionInfoController {
	
	@Autowired
	private PermissionInfoService permissionInfoService;
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value="/permission/queryList",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<PageInfo<PermissionExtendInfo>> queryList(@RequestBody PermissionInfoQuery request){
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		return new ResultBean<>(new PageInfo<>(this.permissionInfoService.findByCondition(request)));
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/permission/add",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<String> add(@RequestBody PermissionInfo request){
		this.permissionInfoService.add(request);
		return new ResultBean<>();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/permission/delete",method=RequestMethod.GET)
	@LoginRequire
	public ResultBean<String> delete(Integer permissionId){
		this.permissionInfoService.delete(permissionId);
		return new ResultBean<>();
	}

}
