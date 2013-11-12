package com.tl.resource.web.delivery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.delivery.DeliveryListOutExcel;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class DeliveryOutExcelAction extends DispatchAction {
	private com.tl.resource.business.delivery.DeliveryOutExcel deliveryOutExcel;
	private DeliveryListOutExcel deliveryListOutExcel;
	public ActionForward expertExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String conId = request.getParameter("id");
		deliveryOutExcel.exportExcel(conId, response,request);
		return null;
	}
	public ActionForward expertList2Excel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> mparams = new HashMap<String,Object>();
		mparams.put("contractCode", request.getParameter("contractCode"));
		mparams.put("customerName", request.getParameter("customerName"));
		mparams.put("status", request.getParameter("status"));
		if("-1".equals(mparams.get("status"))){
			mparams.put("status", null);
		}
		mparams.put("startTime", request.getParameter("startTime"));
		mparams.put("endTime", request.getParameter("endTime"));
		mparams.put("deliveryType", request.getParameter("deliveryType"));
		mparams.put("deliveryCode", request.getParameter("deliveryCode"));
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto user = loginInfor.getUser();
		mparams.put("currentUserId", user.getId());
		deliveryListOutExcel.exportExcel(mparams , response, request);
		return null;
	}
	public com.tl.resource.business.delivery.DeliveryOutExcel getDeliveryOutExcel() {
		return deliveryOutExcel;
	}
	public void setDeliveryOutExcel(
			com.tl.resource.business.delivery.DeliveryOutExcel deliveryOutExcel) {
		this.deliveryOutExcel = deliveryOutExcel;
	}
	public DeliveryListOutExcel getDeliveryListOutExcel() {
		return deliveryListOutExcel;
	}
	public void setDeliveryListOutExcel(DeliveryListOutExcel deliveryListOutExcel) {
		this.deliveryListOutExcel = deliveryListOutExcel;
	}
	
}
