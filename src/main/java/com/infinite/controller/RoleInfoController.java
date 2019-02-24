package com.infinite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinite.common.annotation.LoginRequire;
import com.infinite.controller.vo.ResultBean;
import com.infinite.dao.po.RoleInfo;
import com.infinite.service.PermissionInfoService;
import com.infinite.service.RoleInfoService;
import com.infinite.service.bo.PermissionTreeNode;
import com.infinite.service.bo.RoleInfoAssign;
import com.infinite.service.bo.RoleInfoQuery;

/**
 * 
* @ClassName: RoleInfoController
* @Description: 角色信息控制层
* @author chenliqiao
* @date 2018年4月4日 下午6:07:24
*
 */
@RestController
public class RoleInfoController {
	
	@Autowired
	private RoleInfoService roleInfoService;
	
	@Autowired
	private PermissionInfoService permissionInfoService;
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value="/role/queryList",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<PageInfo<RoleInfo>> queryList(@RequestBody RoleInfoQuery request){
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		return new ResultBean<>(new PageInfo<>(this.roleInfoService.findByCondition(request)));
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/role/add",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<String> add(@RequestBody RoleInfo request){
		this.roleInfoService.add(request);
		return new ResultBean<>();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/role/delete",method=RequestMethod.GET)
	@LoginRequire
	public ResultBean<String> delete(Integer roleId){
		this.roleInfoService.delete(roleId);
		return new ResultBean<>();
	}
	
	/**
	 * 分配权限
	 */
	@RequestMapping(value="/role/assignPermission",method=RequestMethod.POST)
	@LoginRequire
	public ResultBean<String> assignPermission(@RequestBody RoleInfoAssign request){
		this.permissionInfoService.assignPermission(request);
		return new ResultBean<>();
	}
	
	
	/**
	 * 查询权限树
	 */
	@RequestMapping(value="/role/getPermissionTree",method=RequestMethod.GET)
	@LoginRequire
	public ResultBean<List<PermissionTreeNode>> getPermissionTree(Integer roleId){
		return new ResultBean<>(this.permissionInfoService.buildPermissionTree(roleId));
	}

}
