package com.tl.resource.business.dto;

import java.util.List;

import com.tl.resource.dao.pojo.TAccountsInfor;

public class AccountsInforPagerDto {
	private long totalProperty;
	private List<TAccountsInfor> root;
	
	public long getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(long totalProperty) {
		this.totalProperty = totalProperty;
	}
	public List<TAccountsInfor> getRoot() {
		return root;
	}
	public void setRoot(List<TAccountsInfor> root) {
		this.root = root;
	}
	
}
