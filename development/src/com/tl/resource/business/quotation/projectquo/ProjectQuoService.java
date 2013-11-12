package com.tl.resource.business.quotation.projectquo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

public interface ProjectQuoService {
	
	
	/**
	 * 获取报价信息
	 * @param parmMap 参数MAP 包含查询条件和分布信息
	 * @return 符合条件的报价单信息列表
	 */
	public List<QuotationDto> getQuotationByPage(Map<String, Object> parmMap);
	
	/**
	 * 得到客户信息
	 * @param parmMap 参数MAP 包含查询条件和分布信息
	 * @return 符合条件的客户信息列表
	 */
	public List<TCustomersInfor> getCustomerInfoByBage(Map<String, Object> parmMap);
	
	/**
	 * 添加报价信息
	 * @param quotationInfor
	 * @return
	 */
	public TQuotationInfor insertQuotation(TQuotationInfor quotationInfor);
	
	/**
	 * 获取报价总数
	 * @param parmMap 参数Map
	 * @return
	 */
	public int getQuotaionTotal(Map<String, Object> parmMap);

	/**
	 * 获取工单信息
	 * @param parmMap
	 * @return
	 */
	public List<TQuotationProjectSortInfor> getWorkOrderList(Map<String, Object> parmMap);

	/**
	 * 插入工单对应的产品详细信息
	 * @param po2
	 */
	public void insertQuoDetail(TQuotationProductDetail po2);
	
	/**
	 * 插入工单
	 * @param tquoproinfo
	 */
	public void insertQuoProSort(TQuotationProjectSortInfor tquoproinfo);

	/**
	 * 得到产品信息
	 * @param parmMap 参数parmMap 包含查询条件和分布信息
	 * @return 符合条件的产品信息
	 */
	public List<QuotationDetailDto> getQuoDetailByWorkOrder(
			Map<String, Object> parmMap);
	
	/**
	 * 根据报价单主表id获取基本信息
	 * @param id
	 * @return
	 */
	public TQuotationInfor getQuoInfoByID(String id);

	/**
	 * 升级工单信息
	 * @param quoInfo
	 */
	public void updateQuoInfo(TQuotationInfor quoInfo);
	
	public void updateQuoStatus(TQuotationInfor quoInfo);

	/**
	 * 根据工单id获取工单信息
	 * @param workorderid
	 * @return
	 */
	public TQuotationProjectSortInfor getQuoProSortInfoByID(String workorderid);

	/**
	 * 更新工单信息
	 * @param tquoproinfo
	 */
	public void updateQuoProSortInfo(TQuotationProjectSortInfor tquoproinfo);

	/**
	 * 根据产品详细ID获取产品信息
	 * @param id
	 * @return
	 */
	public TQuotationProductDetail getQuoDetailById(String id);
	
	/**
	 * 更新产品信息
	 * @param po2
	 */
	public void updateQuoDetail(TQuotationProductDetail po2);

	/**
	 * 删除报价单信息及其工单、产品信息
	 * @param quoId
	 */
	public void deleteQuoInfoById(String quoId);

	/**
	 * 删除工序信息、产品信息
	 * @param workOrderId
	 */
	public void deleteWorkOrderById(String workOrderId);

	
	
	//***********以下审批流程,用户方法'回调'*****************//
	/**
	 * 审批退回
	 * @param id
	 * @return
	 */
	public String cancelAudit(String id);
	/**
	 * 审批通过
	 * @param id
	 * @return
	 */
	public String endAudit(String id);
	/**
	 * 提交审批
	 * @param id
	 * @return
	 */
	public String submitAudit(String id) ;

	/**
	 * 删除产品信息按id
	 * @param productId
	 */
	public void deleteProductById(String productId);
	/**
	 * 项目报价单导出Excel
	 * @param quoDto
	 */
	public void exportExcel(TQuotationInfor quoDto,OutputStream os,String realPath);
	
	/**
	 * 校验报价单是否包含非标品，包含返回 true, 否则返回false
	 * @param quoId
	 * @return
	 */
	public boolean validator(String quoId);

	/**
	 * 根据客户获取报价单产品（项目复制报价单调用）
	 * @param parmMap
	 * @return
	 */
	List<QuotationDetailDto> getProductList4Copy(Map<String, Object> parmMap);

	public boolean isPriceChange(String id);

	public boolean isClosingAccountModeChange(String id);
	
	public int deleteQuoDetail(JSONArray ids);
}
