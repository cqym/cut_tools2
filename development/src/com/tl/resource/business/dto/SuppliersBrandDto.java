package com.tl.resource.business.dto;

public class SuppliersBrandDto {

	private String id;
	private String tSuppliersId;
	private String brand;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String gettSuppliersId() {
        return tSuppliersId;
    }

	public void settSuppliersId(String tSuppliersId) {
        this.tSuppliersId = tSuppliersId;
    }


}
