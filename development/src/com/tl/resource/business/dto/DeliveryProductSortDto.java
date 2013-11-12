package com.tl.resource.business.dto;

import java.util.List;

public class DeliveryProductSortDto {
	private String id;
	private String name;
	private List<DeliveryProductDetailDto> deliveryProductDetail;

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

	public List<DeliveryProductDetailDto> getDeliveryProductDetail() {
		return deliveryProductDetail;
	}

	public void setDeliveryProductDetail(
			List<DeliveryProductDetailDto> deliveryProductDetail) {
		this.deliveryProductDetail = deliveryProductDetail;
	}

}
