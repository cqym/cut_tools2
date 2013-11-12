package com.tl.resource.audit;

import java.util.List;

import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.dao.pojo.TAuditHistory;

public interface IAuditBusinessObject {
	/**
	 * 获得业务分类编号
	 * 
	 * @return
	 */
	public String getBusinessType();
	/**
	 * 提交审核
	 * @return 返回""表示成功
	 */
	public String submitAudit();
	/**
	 * 结束审批
	 * @return返回""表示成功
	 */
	public String endAudit();
	/**
	 * 中止审批
	 * @return 返回""表示成功
	 */
	public String cancelAudit();
	
	/**
	 * 获得审批数据对象
	 * @return
	 */
	public TAuditHistory getAbo(); 
	/**
	 * @param bo 审批数据对象
	 */
	public void setAbo(TAuditHistory bo);

	/**
	 * 根据业务id获取BO对象
	 * @param string
	 */
	public void loadBo(String id); 
	/**
	 * 获得业务对象
	 */
	public LinkBusinessObject getBo();
	/**
	 * 审批查看时的页面的url
	 * @return
	 */
	public String getUrl();
	/**
	 * 获得页面显示信息的列描述集合
	 * @return
	 */
	public List getShowColumns();
	
}
