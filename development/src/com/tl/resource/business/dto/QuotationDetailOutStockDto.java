package com.tl.resource.business.dto;

import java.math.BigDecimal;

/**
 * 出库参照报价明细dto
 * @author tonglian
 *
 */
public class QuotationDetailOutStockDto extends QuotationDetailDto{
	private String proSortName;
	private String reserveInforId;
	private BigDecimal reserveAmount = BigDecimal.ZERO;
	private BigDecimal deliveryAmount = BigDecimal.ZERO;
	private BigDecimal arrivalAmount = BigDecimal.ZERO;
	private Boolean leaf;
	public String getProSortName() {
		return proSortName;
	}
	public void setProSortName(String proSortName) {
		this.proSortName = proSortName;
	}
	public BigDecimal getReserveAmount() {
		return reserveAmount;
	}
	public void setReserveAmount(BigDecimal reserveAmount) {
		this.reserveAmount = reserveAmount;
		if(reserveAmount == null)this.reserveAmount = BigDecimal.ZERO;
	}
	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
		if(deliveryAmount == null)this.deliveryAmount = BigDecimal.ZERO;
	}
	public BigDecimal getArrivalAmount() {
		return arrivalAmount;
	}
	public void setArrivalAmount(BigDecimal arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
		if(arrivalAmount == null)this.arrivalAmount = BigDecimal.ZERO;
	}
	public String getReserveInforId() {
		return reserveInforId;
	}
	public void setReserveInforId(String reserveInforId) {
		this.reserveInforId = reserveInforId;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	
}
