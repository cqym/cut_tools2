package com.tl.resource.business.baseInfo;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.DegreeRebateDto;
import com.tl.resource.business.dto.SuppliersBrandDto;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersDegree;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TOwnContactPerson;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TRebate;
import com.tl.resource.dao.pojo.TReserveInfor;
import com.tl.resource.dao.pojo.TSuppliersBrand;
import com.tl.resource.dao.pojo.TSuppliersInfor;
import com.tl.resource.dao.pojo.TTaxRate;
import com.tl.resource.dao.pojo.TUserInfor;

public interface BaseInfoService {

	/**
	 * 保存公司信息
	 * @param companyInfo
	 */
	void saveCompany(TCompanyInfor companyInfo);

	/**
	 * 根据查询条件得到公司总数
	 * @param parmMap
	 * @return
	 */
	int getCompanyTotal(Map<String, Object> parmMap);

	/**
	 * 根据分页信息得到公司信息列表
	 * @param parmMap
	 * @return
	 */
	List<TCompanyInfor> getCompanyByPage(Map<String, Object> parmMap);

	/**
	 * 删除公司信息按ID
	 * @param companyId
	 */
	void deleteCompanyById(String companyId);

	/**
	 * 更新公司信息
	 * @param companyInfo
	 */
	void updateCompany(TCompanyInfor companyInfo);

	/**
	 * 得到所有的用户等级
	 * @param parmMap 
	 * @return
	 */
	List<TCustomersDegree> getAllCustomersDegree(Map<String, Object> parmMap);

	/**
	 * 得到用户等级按Id
	 * @param customserDegreeId
	 * @return
	 */
	TCustomersDegree getCustomersDegreeById(String customserDegreeId);

	/**
	 * 保存客户信息
	 * @param customersInfo
	 * @throws Exception 
	 */
	void addCustomersInfo(TCustomersInfor customersInfo) throws Exception;
	/**
	 * 得到客户信息总数
	 * @param parmMap 
	 * @return
	 */
	int getCustomersCount(Map<String, Object> parmMap);
	/**
	 * 根据条件得到客户信息列表
	 * @param parmMap
	 * @return
	 */
	List<TCustomersInfor> getCustomersList(Map<String, Object> parmMap);
	/**
	 * 根据ID删除客户信息
	 * @param customersId
	 */
	void deleteCustomersById(String customersId);
	/**
	 * 更新客户信息
	 * @param customersInfo
	 * @throws Exception 
	 */
	void updateCustomers(TCustomersInfor customersInfo) throws Exception;

	/**
	 * 得到供应商信息总数
	 * @param parmMap 
	 * @return
	 */
	int getSuppliersTotal(Map<String, Object> parmMap);
	/**
	 * 根据条件得到供应商信息列表
	 * @param parmMap
	 * @return
	 */
	List<TSuppliersInfor> getSuppliersByPage(Map<String, Object> parmMap);

	/**
	 * 删除供应商信息
	 * @param supplierIdPar
	 */ 	
	void deleteSupplierById(String supplierIdPar);
	/**
	 * 保存供应商信息
	 * @param supplierInfo
	 */
	void saveSupplier(TSuppliersInfor supplierInfo);
	/**
	 * 更新供应商信息
	 * @param supplierInfor
	 */
	void updateSupplier(TSuppliersInfor supplierInfor);

	/**
	 * 汇率列表信息总数
	 * @return
	 */
	int getExchangeTotal();

	/**
	 * 根据条件得到汇率信息列表
	 * @param parmMap
	 * @return
	 */
	List<TExchangeRate> getExchangeByPage(Map<String, Object> parmMap);
	/**
	 * 保存汇率信息
	 * @param exchangeInfo
	 */
	void saveExchange(TExchangeRate exchangeInfo);
	/**
	 * 删除汇率信息成功
	 * @param exchangeId
	 */
	void deleteExchangeById(String exchangeId);
	/**
	 * 更新汇率信息
	 * @param exchangeInfo
	 */
	void updateExchange(TExchangeRate exchangeInfo);

	/**
	 * 获取库存信息总数
	 * @param parmMap
	 * @return
	 */
	int getReserveTotal(Map<String, Object> parmMap);

	/**
	 * 获取库存信息列表
	 * @param parmMap
	 * @return
	 */
	List<TReserveInfor> getReserveByPage(Map<String, Object> parmMap);

	/**
	 * 获取库存帐页信息
	 * @param parmMap
	 * @return
	 */
	List<TAccountsInfor> getAccountsInfoListByByReserveId(Map<String, Object> parmMap);

	/**
	 * 折扣信息总数
	 * @param parmMap
	 * @return
	 */
	int getDegreeRebateTotalByDegreeId(Map<String, Object> parmMap);

	/**
	 * 折扣信息详情
	 * @param parmMap
	 * @return
	 */
	List<DegreeRebateDto> getDegreeRebateByPageAndDegreeId(
			Map<String, Object> parmMap);

	/**
	 * 更新折扣信息
	 * @param trebate
	 */
	void updateRebate(TRebate trebate);
	/**
	 * 添加折扣信息
	 * @param trebate
	 */
	void saveRebate(TRebate trebate);
	/**
	 * 添加客户等级信息
	 * @param customersDegreeInfo
	 */
	void saveCustomersDegree(TCustomersDegree customersDegreeInfo);

	/**
	 * 得到所有用户信息
	 * @return
	 */
	List<TUserInfor> getAllUserInfor();

	void setBenchmarkExchangeById(String exchangeId);

	/**
	 * 获取品牌信息(ftl 2009-12-22)
	 * @param parmMap
	 * @return
	 */
	public List<TProductBrand> getProductBrand(Map<String, Object> parmMap);
	
	/**
	 * 获取品牌信息总数(ftl 2009-12-22)
	 * @return
	 */
	public Integer getProductBrandTotal(Map<String, Object> paramMap);

	/**
	 * 添加品牌信息(ftl 2009-12-23)
	 * @param brand
	 */
	public void insertBrand(TProductBrand brand);
	
	/**
	 * 修改品牌信息(ftl 2009-12-23)
	 * @param brand
	 */
	public void updateBrand(TProductBrand brand);
	
	/**
	 * 删除品牌信息(ftl 2009-12-23)
	 * @param brand
	 */
	public void deleteBrand(String brandId);
	
	public void deleteBrand(JSONArray array);

	/**
	 * 根据供应商得到客户品牌总数
	 * @param parmMap
	 * @return
	 */
	public int getSuppliersBrandTotalBySupplierId(Map<String, Object> parmMap);

	/**
	 * 根据供应商得到客户品牌信息
	 * @param parmMap
	 * @return
	 */
	public List<SuppliersBrandDto> getSuppliersBrandByPageAndSupplierId(
			Map<String, Object> parmMap);

	void saveSuppliersBrand(TSuppliersBrand suppliersBrandInfo);

	void deleteSupplierBrankById(String suppliersBrandIdPar);

	/**
	 * 得到所有产品组别信息
	 * @return
	 */
	List<TProductSort> getAllProductSort();

	/**
	 * 更新客户等级信息
	 * @param tcusDegree
	 */
	void updateRebate(TCustomersDegree tcusDegree);
	/**
	 * 根据品牌得到采购价格执行期
	 * @param paramMap
	 * @return
	 */
	List<TProductBrand> getRunData(Map<String, Object> paramMap);

	/**
	 * 效验公司信息是否存在
	 * @param companyInfo
	 * @return
	 */
	boolean checkCompany(TCompanyInfor companyInfo);

	void deleteCustomerDegreeByIds(String[] ids);

	/**
	 * 获取税率
	 * @return
	 */
	List<TTaxRate> getTaxRate();
	
	/**
	 * 税率总记录数
	 * @return
	 */
	int getTaxRateCount();
	
	/**
	 * 添加税率
	 * @param taxRate
	 * @throws Exception
	 */
	void addTaxRate(TTaxRate taxRate) throws Exception;
	
	/**
	 * 修改税率
	 * @param taxRate
	 * @throws Exception
	 */
	void modifyTaxRate(TTaxRate taxRate) throws Exception;
	
	/**
	 * 删除税率
	 * @param array
	 * @throws Exception
	 */
	void deleteTaxRate(JSONArray array) throws Exception;

	PaginationSupport getWillSelectUser(Map<String, Object> parmMap);
    int getWillSelectUserCount(Map<String, Object> parmMap);
    List<TOwnContactPerson> getSelectedUser(Map<String, Object> parmMap);
    
    void saveClientLeader(String customerId, List<String> idList);
}
