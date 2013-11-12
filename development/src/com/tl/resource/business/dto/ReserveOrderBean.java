package com.tl.resource.business.dto;

import java.util.List;

import com.tl.resource.dao.pojo.TOrderInfor;



public class ReserveOrderBean {

	private long totalProperty;
	private List<TOrderInfor> root;



	public ReserveOrderBean(long totalProperty, List<TOrderInfor> root) {
		super();
		this.totalProperty = totalProperty;
		this.root = root;
	}

	public ReserveOrderBean() {
		// TODO Auto-generated constructor stub
	}

		public long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<TOrderInfor> getRoot() {
		return root;
	}

	public void setRoot(List<TOrderInfor> root) {
		this.root = root;
	}
	
	
}
