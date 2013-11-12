package com.tl.resource.business.dto;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class ModulesDto implements Serializable {
  private static final long serialVersionUID = 1111L;

  private String id;

  private String parentId;

  private String moduleName;

  private String url;

  private Short leaf;

  private String text; //�ڵ���ʾ

  private String cls = "folder-icon"; //ͼ��

  // private boolean leaf;         //�Ƿ�Ҷ��
  private String href; //����

  private String hrefTarget; //����ָ��

  private boolean expandable = true; //�Ƿ�չ��

  private String description; //������Ϣ 

  private int serialNumber;

  private String proCode;

  private String icoPath;

  private List<ModulesDto> children;

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

  //	public boolean isLeaf(){
  //		return this.leaf == 1;
  //	}

  public List<ModulesDto> getChildren() {
    return children;
  }

  public void setChildren(List<ModulesDto> children) {
    this.children = children;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(int serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getProCode() {
    return proCode;
  }

  public void setProCode(String proCode) {
    this.proCode = proCode;
  }

  public String getIcoPath() {
    return icoPath;
  }

  public void setIcoPath(String icoPath) {
    this.icoPath = icoPath;
  }

  public boolean hasChild(String moCode) {
    if (this.children == null || moCode == null)
      return false;
    for (Iterator iterator = children.iterator(); iterator.hasNext();) {
      ModulesDto dto = (ModulesDto) iterator.next();
      if (moCode.equals(dto.getId())) {
        return true;
      }
    }
    return false;
  }
}
