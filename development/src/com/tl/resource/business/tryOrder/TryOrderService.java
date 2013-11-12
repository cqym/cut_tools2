package com.tl.resource.business.tryOrder;

import com.tl.resource.dao.pojo.TOrderInfor;


public interface TryOrderService {
	

	
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