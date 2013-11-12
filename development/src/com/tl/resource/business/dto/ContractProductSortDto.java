package com.tl.resource.business.dto;

import java.util.List;

public class ContractProductSortDto {
	private String id;
	private String name;
	private List<ContractProductDetailDto> conProductDetail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ContractProductDetailDto> getConProductDetail() {
		return conProductDetail;
	}
	public void setConProductDetail(List<ContractProductDetailDto> conProductDetail) {
		this.conProductDetail = conProductDetail;
	}
	
}
