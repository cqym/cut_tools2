package com.tl.resource.web.outStock;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.outStock.OutStockListOutExcel;
import com.tl.resource.business.outStock.OutStockOutExcel;

public class OutStockOutExcelAction extends DispatchAction{
	private OutStockOutExcel outStockOutExcel;
	private OutStockListOutExcel outStockListOutExcel;
	public ActionForward expertExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String conId = request.getParameter("id");
		outStockOutExcel.exportExcel(conId, response,request);
		return null;
	}
	public ActionForward expertList2Excel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> mparams = new HashMap<String,Object>();
		mparams.put("contractCode", request.getParameter("contractCode"));
		mparams.put("status", request.getParameter("status"));
		if("-1".equals(mparams.get("status"))){
			mparams.put("status", null);
		}
		mparams.put("outStockCode", request.getParameter("outStockCode"));
		mparams.put("startDate", request.getParameter("startDate"));
		mparams.put("endDate", request.getParameter("endDate"));
		mparams.put("outStockType", request.getParameter("outStockType"));//
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto user = loginInfor.getUser();
		mparams.put("currentUserId", user.getId());
		String sort = request.getParameter("sort");
		if(sort != null){
			if("editDateString".equals(sort)){
				sort = "editDate";
			}
			mparams.put("sort", RegexUtils.toDataBaseColName(sort));
			mparams.put("dir", request.getParameter("dir"));
		}
		outStockListOutExcel.exportExcel(mparams, response, request);
		return null;
	}
	public OutStockOutExcel getOutStockOutExcel() {
		return outStockOutExcel;
	}
	public void setOutStockOutExcel(OutStockOutExcel outStockOutExcel) {
		this.outStockOutExcel = outStockOutExcel;
	}

	public OutStockListOutExcel getOutStockListOutExcel() {
		return outStockListOutExcel;
	}

	public void setOutStockListOutExcel(OutStockListOutExcel outStockListOutExcel) {
		this.outStockListOutExcel = outStockListOutExcel;
	}
}
