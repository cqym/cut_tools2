package com.tl.resource.web.contract;

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
import com.tl.resource.business.contract.ContractListOutExcel;
import com.tl.resource.business.contract.ContractOutExcel;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class ContractOutExcelAction extends DispatchAction{
	private ContractOutExcel contractOutExcel;
	private ContractListOutExcel contractListOutExcel;
	public ActionForward expertExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String conId = request.getParameter("contractId");
		contractOutExcel.exportExcel(conId, response,request);
		return null;
	}
	public ActionForward expertList2Excel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> mparams = new HashMap<String,Object>();
		mparams.put("contractCode", request.getParameter("contractCode"));
		mparams.put("customerName", request.getParameter("customerName"));
		if(!"-2".equals(request.getParameter("status"))){
			mparams.put("status", request.getParameter("status"));
		}
		mparams.put("startDate", request.getParameter("startDate"));
		mparams.put("endDate", request.getParameter("endDate"));
		
		mparams.put("invoiceStartDate", request.getParameter("invoiceStartDate"));
		mparams.put("invoiceEndDate", request.getParameter("invoiceEndDate"));
		
		mparams.put("accountStartDate", request.getParameter("accountStartDate"));
		mparams.put("accountEndDate", request.getParameter("accountEndDate"));
		
		mparams.put("userName", request.getParameter("userName"));
		mparams.put("orderStatus", request.getParameter("orderStatus"));
		mparams.put("orderArrivalStatus", request.getParameter("orderArrivalStatus"));
		mparams.put("allArrivalStatus", request.getParameter("allArrivalStatus"));
		mparams.put("deliveryStatus", request.getParameter("deliveryStatus"));
		mparams.put("contractAccountStatus", request.getParameter("contractAccountStatus"));
		mparams.put("invoiceStatus", request.getParameter("invoiceStatus"));
		mparams.put("ownContactPerson", request.getParameter("ownContactPerson"));
		
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto user = loginInfor.getUser();
		mparams.put("currUserId", user.getId());
		contractListOutExcel.exportExcel(mparams, response, request);
		return null;
	}
	public ContractOutExcel getContractOutExcel() {
		return contractOutExcel;
	}
	public void setContractOutExcel(ContractOutExcel contractOutExcel) {
		this.contractOutExcel = contractOutExcel;
	}
	public ContractListOutExcel getContractListOutExcel() {
		return contractListOutExcel;
	}
	public void setContractListOutExcel(ContractListOutExcel contractListOutExcel) {
		this.contractListOutExcel = contractListOutExcel;
	}
	
}
