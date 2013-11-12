package com.tl.resource.business.dto;

import java.util.List;

public class QuoCodeTreeDto {
	private String uiProvider="col";
	private List<QuoCodeTreeDto> children;
	private Integer leaf=1;
	private String code;
	
	public String getUiProvider() {
		return uiProvider;
	}
	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}
	public List<QuoCodeTreeDto> getChildren() {
		return children;
	}
	public void setChildren(List<QuoCodeTreeDto> children) {
		this.children = children;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
