package com.tl.resource.business.dto;

import java.io.Serializable;
import java.util.List;

public class ModulesCheckBoxDto implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8981988589012616511L;
	private String id;
	private String parentId;
	private String moduleName;
	private Short leaf;
    private String text;          //�ڵ���ʾ
    private String cls = "folder-icon";           //ͼ��
   // private boolean leaf;         //�Ƿ�Ҷ��
    private String href;          //����
    private String hrefTarget;    //����ָ��
    private boolean expandable = false;   //�Ƿ�չ��
    private String description;   //������Ϣ 
    private boolean hidden;
	private List<ModulesCheckBoxDto> children;
	private boolean checked;
	
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
	}

	public Short getLeaf() {
		return leaf;
	}

	public void setLeaf(Short leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return moduleName;
	}

	public void setText(String text) {
		this.moduleName = text;
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

	public List<ModulesCheckBoxDto> getChildren() {
		return children;
	}

	public void setChildren(List<ModulesCheckBoxDto> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
}
