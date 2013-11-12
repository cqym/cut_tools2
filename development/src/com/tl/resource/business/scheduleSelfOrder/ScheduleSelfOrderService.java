package com.tl.resource.business.scheduleSelfOrder;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.dao.pojo.TOrderInfor;



public interface ScheduleSelfOrderService {
	
	/**
	 * 根据报价单得到报价单详细列表(非标品)
	 * @param parmMap
	 * @return
	 */
	public List<QuotationDetailForOrderDto> getQuoDetailById(Map<String, Object> parmMap);
	
	/**
	 * 根据报价单得到报价单详细列表数量(非标品)
	 * @param parmMap
	 * @return
	 */
	public int getQuoDetailCountById(Map<String, Object> parmMap);
	
	/**
	 * 根据供应商得到报价单详细列表(非标品)
	 * @param parmMap
	 * @return
	 */
	public List<OrderDetialsDto> getQuoDetailBySupplier(Map<String, Object> parmMap);
	
	/**
	 * 根据供应商得到报价单详细列表数量(非标品)
	 * @param parmMap
	 * @return
	 */
	public int getQuoDetailCountBySupplier(Map<String, Object> parmMap);
	
	/**
	 * 根据订单ID查找订单详细(预定加工订单详细)
	 * @param contractId
	 * @return
	 */
	public List<OrderDetialsDto> getSSOrderDetailsList(Map<String, Object> parmMap);
	
	/**
	 * 根据订单ID查找订单详细总数(预定加工订单详细)
	 * @param contractId
	 * @return
	 */
	public int getSSOrderDetailsListCount(Map<String, Object> parmMap);
	
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
	
}
