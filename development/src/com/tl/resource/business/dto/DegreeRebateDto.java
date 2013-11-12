package com.tl.resource.business.dto;

import java.math.BigDecimal;

/**
 * 客户等级对应组别折扣信息Dto
 * @author xtaia
 */

public class DegreeRebateDto {
	
	private String degreeName ;
	
	private String degreeCode ;
	
	private String productBrand ;
	
	private String sortName ;

	private String sortCode ;
	
	private BigDecimal rebate ;
	
	private String productSortId ;
	
	private String customersDegreeId ;
	
	private String id ;

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getDegreeCode() {
		return degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public String getProductSortId() {
		return productSortId;
	}

	public void setProductSortId(String productSortId) {
		this.productSortId = productSortId;
	}

	public String getCustomersDegreeId() {
		return customersDegreeId;
	}

	public void setCustomersDegreeId(String customersDegreeId) {
		this.customersDegreeId = customersDegreeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
