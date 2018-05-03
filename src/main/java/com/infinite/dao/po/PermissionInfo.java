package com.infinite.dao.po;

import java.util.Date;

/**
 * 
* @ClassName: PermissionInfo
* @Description: 权限信息实体映射类
* @author chenliqiao
* @date 2018年1月3日 下午5:45:36
*
 */
public class PermissionInfo {
	
    private Integer id;

    /**父级id**/
    private Integer parentId;

    /**名称**/
    private String name;

    /**英文名称**/
    private String enName;

    /**顺序编号**/
    private Integer orderNum;
    
    /**类型（1 菜单；2 按钮；3 功能）**/
    private Integer type;

    /**描述**/
    private String description;

    /**访问地址**/
    private String apiUrl;

    /**创建时间**/
    private Date createTime;

    /**状态（0：停用；1：启用）**/
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl == null ? null : apiUrl.trim();
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
		return "PermissionInfo [id=" + id + ", parentId=" + parentId + ", name=" + name + ", enName=" + enName
				+ ", orderNum=" + orderNum + ", type=" + type + ", description=" + description + ", apiUrl=" + apiUrl
				+ ", createTime=" + createTime + ", status=" + status + "]";
	}

    
}