package com.tl.resource.business.dto;

import java.util.List;

public class RolesPageDto {

	private int totalCount ;
	private List<RolesDto> roles;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<RolesDto> getRoles() {
		return roles;
	}
	public void setRoles(List<RolesDto> roles) {
		this.roles = roles;
	}
	
}
