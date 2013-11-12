package com.tl.resource.business.dto;

import java.util.List;

public class DepartmentsDto {
	private String id;
	
	private String departName;
	
	private String parentId;
	
	private Integer leaf;
    private String text;          //节点显示
    private String cls = "folder-icon";           //图标
    private String href;          //链接
    private String hrefTarget;    //链接指向
    private boolean expandable = false;   //是否展开
    private String description;   //描述信息 
	private List<DepartmentsDto> children;
	private String departId;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.departId = id;
		this.id = id;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
		this.text = departName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public void setChildren(List<DepartmentsDto> tempChildren) {
		// TODO Auto-generated method stub
		this.children = tempChildren;
	}

	public List<DepartmentsDto> getChildren() {
		return children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}
	
	
}
