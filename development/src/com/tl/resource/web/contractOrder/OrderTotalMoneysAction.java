package com.tl.resource.web.contractOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.UserDto;

public class OrderTotalMoneysAction extends DispatchAction {
	private ContractOrderService contractOrderService;
	public ActionForward getOrderTotalMoneys(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		String status = request.getParameter("status");
		if(status!=null&&status.equals("全部"))
		{
			status = null;
		}
		response.setContentType("text/html;charset=utf-8");		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("contractCode", request.getParameter("contractCode"));
		parmMap.put("orderCode", request.getParameter("orderCode"));
		parmMap.put("supplierName", request.getParameter("supplierName"));
		parmMap.put("startTime", request.getParameter("startTime"));
		parmMap.put("endTime", request.getParameter("endTime"));
		parmMap.put("orderType", request.getParameter("orderType"));
		parmMap.put("status", status);	
		parmMap.put("customerName", request.getParameter("customerName"));	
		parmMap.put("ownContactPerson", request.getParameter("ownContactPerson"));	
		parmMap.put("startIndex", request.getParameter("startIndex"));
		parmMap.put("pageSize", request.getParameter("pageSize"));
		parmMap.put("userId", userDto.getId());
		Map<String, Object> bean = contractOrderService.getOrderTotalMoneys(parmMap );
		PrintWriter out = response.getWriter();
		out.println(JSONObject.fromObject(bean).toString());
		out.flush();
		out.close();
	    return null;
	}
	public ContractOrderService getContractOrderService() {
		return contractOrderService;
	}
	public void setContractOrderService(ContractOrderService contractOrderService) {
		this.contractOrderService = contractOrderService;
	}
	
}
