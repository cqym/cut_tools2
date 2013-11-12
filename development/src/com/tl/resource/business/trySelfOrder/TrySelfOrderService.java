package com.tl.resource.business.trySelfOrder;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.dao.pojo.TContractProjectSortInfor;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;


public interface TrySelfOrderService {
	

	
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