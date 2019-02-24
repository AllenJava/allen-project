package com.infinite.dao.po;

import java.util.Date;

/**
 * 
* @ClassName: RoleInfo
* @Description: 角色信息映射实体类
* @author chenliqiao
* @date 2018年4月3日 下午6:06:12
*
 */
public class RoleInfo {
	
    private Integer id;

    /**角色名称**/
    private String name;

    /**角色描述**/
    private String description;

    /**创建时间**/
    private Date createTime;

    /**状态**/
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "RoleInfo [id=" + id + ", name=" + name + ", description=" + description + ", createTime=" + createTime
				+ ", status=" + status + "]";
	}
    
    
}