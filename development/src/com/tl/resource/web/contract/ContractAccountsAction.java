package com.tl.resource.web.contract;

import java.io.PrintWriter;
import java.util.Date;
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
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.contract.ContractAccountsService;
import com.tl.resource.business.dto.ContractAccountsInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class ContractAccountsAction  extends DispatchAction{
	private ContractAccountsService contractAccountsService;
	public ActionForward addContractAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String conctractAccountJsonStr = request.getParameter("conctractAccount");
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto user = loginInfor.getUser();
		PrintWriter out = response.getWriter();
		JSONObject conctractInforJson = JSONObject.fromObject(conctractAccountJsonStr);
		JSONObject conctractInforJsons = conctractInforJson.getJSONObject("data");
		ContractAccountsInforDto dto = (ContractAccountsInforDto) JSONObject.toBean(conctractInforJsons,ContractAccountsInforDto.class);
		dto.setEditDate(new Date());
		dto.setUserName(user.getTrueName());
		dto.setUserId(user.getId());
		contractAccountsService.addContractAccount(dto);
		out.println("{success : true, msg : '创建合同回款记录成功'}");
		out.flush();
		out.close();
		return null;
	}
	public ActionForward updateContractAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String conctractAccountJsonStr = request.getParameter("conctractAccount");
		PrintWriter out = response.getWriter();
		JSONObject conctractInforJson = JSONObject.fromObject(conctractAccountJsonStr);
		JSONObject conctractInforJsons = conctractInforJson.getJSONObject("data");
		ContractAccountsInforDto dto = (ContractAccountsInforDto) JSONObject.toBean(conctractInforJsons,ContractAccountsInforDto.class);
		contractAccountsService.updateContractAccount(dto);
		out.println("{success : true, msg : '更新合同回款记录成功'}");
		out.flush();
		out.close();
		return null;
	}
	public ActionForward deleteContractAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String[] ids = request.getParameterValues("ids");
		PrintWriter out = response.getWriter();
		for (int i = 0; i < ids.length; i++) {
			contractAccountsService.deleteContractAccount(ids[i]);
		}
		
		out.print(true);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward confirmContractAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String[] ids = request.getParameterValues("ids");
		PrintWriter out = response.getWriter();
		contractAccountsService.confirmContractAccount(ids);
		out.println("{success : true, msg : '合同回款记录确认成功'}");
		out.flush();
		out.close();
		return null;
	}
	public ActionForward viewContractAccountList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String startIndex = request.getParameter("start");
		String pageSize = request.getParameter("limit");
		if(startIndex == null){
			startIndex = "0";
		}
		if(pageSize == null){
			pageSize = "10";
		}
		
		String searchStr = request.getParameter("searchStr");
		//System.out.println("------------------:" + searchStr);
		String contractCode = "";
		String customerName = "";
		String startDate = "";
		String endDate = "";
		String status = "";
		JSONObject search = JSONObject.fromObject(searchStr);
		
		try {
			contractCode = search.getString("contractCode");
		} catch(Exception e) {
			
		}
		try {
			customerName = search.getString("customerName");
		} catch(Exception e) {
			
		}
		try {
			startDate = search.getString("startDate");
			if(startDate.length()>1)
			{
				startDate = startDate.substring(0, 10);
			}

		} catch(Exception e) {
			
		}
		try {
			endDate = search.getString("endTime");
			if(endDate.length()>1)
			{
				endDate = endDate.substring(0, 10);
			}
		} catch(Exception e) {
			
		}
		try {
			status = search.getString("status");
		} catch(Exception e) {
			
		}
		
//		
//		Map<String,String> mparams = new HashMap<String,String>();
//		mparams.put("contractCode", request.getParameter("contractCode"));
//		mparams.put("customerName", request.getParameter("customerName"));
//		mparams.put("status", request.getParameter("status"));
//		mparams.put("startDate", request.getParameter("startTime"));
//		mparams.put("endDate", request.getParameter("endTime"));
		Map<String,String> mparams = new HashMap<String,String>();
		mparams.put("contractCode", contractCode);
		mparams.put("customerName", customerName);
		mparams.put("status", status);
		mparams.put("startDate", startDate);
		mparams.put("endDate", endDate);

		PaginationSupport pageInfor = contractAccountsService.findContractAccounts(mparams , Integer.valueOf(startIndex), Integer.valueOf(pageSize));
		String rt = JSONObject.fromObject(pageInfor).toString();

		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward contractList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String startIndex = request.getParameter("start");
		String pageSize = request.getParameter("limit");
		if(startIndex == null){
			startIndex = "0";
		}
		if(pageSize == null){
			pageSize = "10";
		}
		Map<String,String> mparams = new HashMap<String,String>();
		mparams.put("contractCode", request.getParameter("contractCode"));
		mparams.put("customerName", request.getParameter("customerName"));
		mparams.put("startDate", request.getParameter("startTime"));
		mparams.put("endDate", request.getParameter("endTime"));
		PaginationSupport pageInfor = contractAccountsService.findContractList(mparams , Integer.valueOf(startIndex), Integer.valueOf(pageSize));
		String rt = JSONObject.fromObject(pageInfor).toString();

		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	public ContractAccountsService getContractAccountsService() {
		return contractAccountsService;
	}

	public void setContractAccountsService(
			ContractAccountsService contractAccountsService) {
		this.contractAccountsService = contractAccountsService;
	}
	
	
	
	
}
