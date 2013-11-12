package com.tl.resource.business.dto;

import java.io.Serializable;

public class Modules4AsyncDto implements Serializable {
	private static final long serialVersionUID = 1111L;

	private String id;
	
	private String parentId;
	
	private String moduleName;
	private String url;
	private Short leaf;
    private String text;          //�ڵ���ʾ
    private String cls = "folder-icon";           //ͼ��
   // private boolean leaf;         //�Ƿ�Ҷ��
    private String href;          //����
    private String hrefTarget;    //����ָ��
   // private boolean expandable = true;   //�Ƿ�չ��
    private String description;   //������Ϣ 
   
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
		this.text = moduleName;
	}

	public Short getLeaf() {
		return leaf;
	}

	public void setLeaf(Short leaf) {
		this.leaf = leaf;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	
}
