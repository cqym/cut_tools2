package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TTaxRate;
import com.tl.resource.dao.pojo.TTaxRateExample;
import java.util.List;

public interface TTaxRateDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int countByExample(TTaxRateExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int deleteByExample(TTaxRateExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	void insert(TTaxRate record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	void insertSelective(TTaxRate record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	List selectByExample(TTaxRateExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	TTaxRate selectByPrimaryKey(String id);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int updateByExampleSelective(TTaxRate record, TTaxRateExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int updateByExample(TTaxRate record, TTaxRateExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int updateByPrimaryKeySelective(TTaxRate record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_tax_rate
	 * @ibatorgenerated  Thu Jul 22 15:47:57 CST 2010
	 */
	int updateByPrimaryKey(TTaxRate record);
}