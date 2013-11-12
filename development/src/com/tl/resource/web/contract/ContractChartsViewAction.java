package com.tl.resource.web.contract;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.contract.ContractViewService;

public class ContractChartsViewAction  extends DispatchAction{
	private ContractViewService contractViewService;
	public ActionForward contractMonthMoneysChart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("ownContactPerson", request.getParameter("ownContactPerson"));
		parmMap.put("customerName", request.getParameter("customerName"));
		parmMap.put("startTime", request.getParameter("startTime"));
		parmMap.put("endTime", request.getParameter("endTime"));
		List<Map<String, Object>> list = contractViewService.getContractMonthMoneys(parmMap);
		String rt = JSONArray.fromObject(list).toString();
		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	public ActionForward contractMoneysForOwnPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("startTime", request.getParameter("startTime"));
		parmMap.put("endTime", request.getParameter("endTime"));
		List<Map<String, Object>> list = contractViewService.getContractMoneysForOwnPerson(parmMap);
		String rt = JSONArray.fromObject(list).toString();
		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	public ActionForward getContractMoneysGroupByMonthForTowYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("year1", request.getParameter("year1"));
		parmMap.put("year2", request.getParameter("year2"));
		parmMap.put("ownContactPerson", request.getParameter("ownContactPerson"));
		parmMap.put("customerName", request.getParameter("customerName"));
		List<Map<String, Object>> list = contractViewService.getContractMoneysGroupByMonthForTowYear(parmMap);
		String rt = JSONArray.fromObject(list).toString();
		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	public ContractViewService getContractViewService() {
		return contractViewService;
	}
	public void setContractViewService(ContractViewService contractViewService) {
		this.contractViewService = contractViewService;
	}

}
