package com.tl.resource.dao.pojo;

public class TProductBrand {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column t_product_brand.id
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    private String id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column t_product_brand.name
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    private String name;
    
    private String sourceId;
    
    private String sourceName;
    
    private String orderPriceRunDate;

    private String salePriceRunDate;
    
    private String backupNameFirst;
    
    private String backupNameSec;
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column t_product_brand.id
     *
     * @return the value of t_product_brand.id
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column t_product_brand.id
     *
     * @param id the value for t_product_brand.id
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column t_product_brand.name
     *
     * @return the value of t_product_brand.name
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column t_product_brand.name
     *
     * @param name the value for t_product_brand.name
     *
     * @ibatorgenerated Wed Nov 25 18:10:59 CST 2009
     */
    public void setName(String name) {
        this.name = name;
    }

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getOrderPriceRunDate() {
		return orderPriceRunDate;
	}

	public void setOrderPriceRunDate(String orderPriceRunDate) {
		this.orderPriceRunDate = orderPriceRunDate;
	}

	public String getSalePriceRunDate() {
		return salePriceRunDate;
	}

	public void setSalePriceRunDate(String salePriceRunDate) {
		this.salePriceRunDate = salePriceRunDate;
	}

	public String getBackupNameFirst() {
		return backupNameFirst;
	}

	public void setBackupNameFirst(String backupNameFirst) {
		this.backupNameFirst = backupNameFirst;
	}

	public String getBackupNameSec() {
		return backupNameSec;
	}

	public void setBackupNameSec(String backupNameSec) {
		this.backupNameSec = backupNameSec;
	}
	
	
}