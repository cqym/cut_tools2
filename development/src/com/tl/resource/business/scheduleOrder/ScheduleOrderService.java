package com.tl.resource.business.scheduleOrder;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;


public interface ScheduleOrderService {
	
	/**
	 *  预定订单列表
	 * @param parmMap
	 * @return
	 */
	public List<TOrderInfor> getScheduleOrderList(Map<String, Object> parmMap);
	/**
	 *  预定订单列表数量
	 * @param parmMap
	 * @return
	 */
	public int getScheduleOrderListCount(Map<String, Object> parmMap);
	
	
	/**
	 *  报价单列表
	 * @param parmMap
	 * @return
	 */
	public List<TQuotationInfor> getQuotationList(Map<String, Object> parmMap);
	/**
	 *  报价单列表数量
	 * @param parmMap
	 * @return
	 */
	public int getQuotationListCount(Map<String, Object> parmMap);
	
	/**
	 * 根据报价单得到报价单详细列表
	 * @param parmMap
	 * @return
	 */
	public List<TQuotationProductDetail> getQuotationDetailByInforId(Map<String, Object> parmMap);
	
	/**
	 * 根据报价单得到报价单详细列表数量
	 * @param parmMap
	 * @return
	 */
	public int getQuotationDetailCountByInforId(Map<String, Object> parmMap);
	
	/**
	 * 根据报价单详细查找供应商
	 * @param parmMap
	 * @return
	 */
	public List<TSuppliersInfor> getSupplierList(Map<String, Object> parmMap);
	
	/**
	 * 根据报价单详细查找供应商数量
	 * @param parmMap
	 * @return
	 */
	public int getSupplierListCount(Map<String, Object> parmMap);
	
	/**
	 * 根据供应商得到报价单详细列表
	 * @param parmMap
	 * @return
	 */
	public List<OrderDetialsDto> getQuotationDetailBySupplier(Map<String, Object> parmMap);
	
	/**
	 * 根据供应商得到报价单详细列表数量
	 * @param parmMap
	 * @return
	 */
	public int getQuotationDetailCountBySupplier(Map<String, Object> parmMap);
	
	/**
	 * 订单详细列表
	 * @param parmMap
	 * @return
	 */
	public List<OrderDetialsDto> getOrderDetailList(Map<String, Object> parmMap);
	
	/**
	 * 订单详细列表数量
	 * @param parmMap
	 * @return
	 */
	public int getOrderDetailListCount(Map<String, Object> parmMap);
	
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
	 * 根据id得到订单信息
	 * @param id
	 * @return
	 */
	public TOrderInfor getOrderInforById(String id);
	
	/**
	 * 根据报价单得到报价单详细分类
	 * @param contractId
	 * @return
	 */
	public List<TQuotationProjectSortInfor> getSPSIList(Map<String, Object> parmMap);
}
