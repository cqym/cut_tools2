package com.tl.resource.web.toolsInfor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.business.protoolsinfo.ProToolsInforService;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TSalesPriceHistory;

public class ListProToolsAction extends DispatchAction {

	private ProToolsInforService proToolsInforService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		
		if("getTools4Product".equals(method)) {
			return getTools4Product(mapping, form, request, response);
		} if("getTools4RootNode".equals(method)) {
			return getTools4RootNode(mapping, form, request, response);
		} if("getToolsWithChildren".equals(method)) {
			return getToolsWithChildren(mapping, form, request, response);
		} if("getToolsById".equals(method)) {
			return getToolsById(mapping, form, request, response);
		} if("getHistoryYear".equals(method)) {
			return getHistoryYear(mapping, form, request, response);
		} else {
			return getTools4Quotation(mapping, form, request, response);
		}
	}
	
	//获取历史面价对应的年度
	private ActionForward getHistoryYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String productBrand = request.getParameter("productBrand");
		List<String> list = proToolsInforService.getHistoryYear(productBrand);
		
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{historyYear : " + jsonStr + "}";
		//System.out.println(resultStr);
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	//根据ID获取工具信息
	private ActionForward getToolsById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String toolsId = request.getParameter("toolsId");
		
		TreeDto tools = proToolsInforService.getProductToolsInfoById(toolsId);
		
		String resultJson = JSONObject.fromObject(tools).toString();
		PrintWriter out = response.getWriter();
		out.write(resultJson);
		out.flush();
		out.close();
		return null;
	}

	private ActionForward getToolsWithChildren(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pageInfoJson = getToolsWithChild(mapping, form, request, response);
		PrintWriter out = response.getWriter();
		out.write(pageInfoJson);
		out.flush();
		out.close();
		return null;
	}

	//级联子节点
	private ActionForward getTools4RootNode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String pageInfoJson = getProTools(mapping, form, request, response, true);
		PrintWriter out = response.getWriter();
		//System.out.println(pageInfoJson);
		out.write(pageInfoJson);
		out.flush();
		out.close();
		return null;
	}
	
	//工具信息模块调用只获取根节点下的产品
	private ActionForward getTools4Product(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String pageInfoJson = getProTools(mapping, form, request, response, false);
		PrintWriter out = response.getWriter();
		//System.out.println(pageInfoJson);
		out.write(pageInfoJson);
		out.flush();
		out.close();
		return null;
	}

	//报价模块调用
	private ActionForward getTools4Quotation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		//String pageName = request.getParameter("pageObjectName");
		String fromIndex = request.getParameter("start");
		String pageCount = request.getParameter("limit");
		String customerId = request.getParameter("customerId");
		//搜索字符串
		
		String searchStr = request.getParameter("searchStr");
		String productCode = "";
		String brandCode = "";
		String productName = "";
		String salePriceDate = "";
		String productBrand = "";
		if(searchStr != null && !"".equals(searchStr)) {	
			JSONObject search = JSONObject.fromObject(searchStr);
		    if(search.has("productCode"))productCode = search.getString("productCode");
			if(search.has("brandCode"))brandCode = search.getString("brandCode");
			if(search.has("productName"))productName = search.getString("productName");
			if(search.has("salePriceDate"))salePriceDate = search.getString("salePriceDate");
			if(search.has("productBrand"))productBrand = search.getString("productBrand");	
		}
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("productCode", productCode);
		parmMap.put("brandCode", brandCode);
		parmMap.put("productName", productName);
		parmMap.put("customerId", customerId);
		parmMap.put("salePriceDate", salePriceDate);
		parmMap.put("productBrand", productBrand);
		ArrayList list = new ArrayList();
		pageCount = pageCount == null ? "10" : pageCount;
		Integer pageSize = Integer.parseInt(pageCount);
		Integer startIndex = 0;
		if(fromIndex != null) {
			startIndex = Integer.parseInt(fromIndex);
		}
		
		int total = proToolsInforService.getProToolsTotal(parmMap);
		
		PaginationSupport pageInfo  = new PaginationSupport(list,total,pageSize,startIndex);
		
		parmMap.put("pageInfo", pageInfo);
		
		List<TreeDto> proList = proToolsInforService.getProToolsBySearch(parmMap);
		pageInfo.setItems(proList);
		//String jsonResult = JSONArray.fromObject(proList).toString();
		String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
		PrintWriter out = response.getWriter();
		//System.out.println(pageInfoJson);
		out.write(pageInfoJson);
		out.flush();
		out.close();
		return null;
	}
	
	private String getProTools(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, boolean isCascade) {
		response.setContentType("text/html;charset=utf-8");
		//String pageName = request.getParameter("pageObjectName");
		String fromIndex = request.getParameter("start");
		String pageCount = request.getParameter("limit");
		String nodeId = request.getParameter("nodeId");
		//System.out.println("-------------------------------" + fromIndex + "," + pageCount);
		String customerId = request.getParameter("customerId");
		//搜索字符串
		
		String searchStr = request.getParameter("searchStr");
		String productCode = "";
		String brandCode = "";
		String productName = "";
		String productBrand = "";
		if(searchStr != null && !"".equals(searchStr)) {
			
			JSONObject search = JSONObject.fromObject(searchStr);
			
			try {
				productCode = search.getString("productCode");
			} catch(Exception e) {
				
			}
			try {
				brandCode = search.getString("brandCode");
			} catch(Exception e) {
				
			}
			try {
				productName = search.getString("productName");
			} catch(Exception e) {
				
			}
			
			if(search.has("productBrand"))
				productBrand = search.getString("productBrand");
		}
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("productCode", productCode);
		parmMap.put("brandCode", brandCode);
		parmMap.put("productName", productName);
		parmMap.put("customerId", customerId);
		parmMap.put("productBrand", productBrand);
		
		ArrayList list = new ArrayList();
		pageCount = pageCount == null ? "10" : pageCount;
		Integer pageSize = Integer.parseInt(pageCount);
		Integer startIndex = 0;
		if(fromIndex != null) {
			startIndex = Integer.parseInt(fromIndex);
		}
		
		int total = proToolsInforService.getProToolsTotal(parmMap);
		
		PaginationSupport pageInfo  = new PaginationSupport(list,total,pageSize,startIndex);
		
		parmMap.put("pageInfo", pageInfo);
		List<TreeDto> proList = new ArrayList<TreeDto>();
		if(isCascade) {
			parmMap.put("parentId", nodeId);
			proList = proToolsInforService.getToolsBySearch(parmMap);
		} else {
			proList = proToolsInforService.getToolsByRootNode(parmMap);
		}
		//List<TreeDto> proList = proToolsInforService.getToolsBySearch(parmMap);
		//List<TreeDto> proList = proToolsInforService.getToolsByRootNode(parmMap);
		pageInfo.setItems(proList);
		//String jsonResult = JSONArray.fromObject(proList).toString();
		
		String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
		//System.out.println(pageInfoJson);
		return pageInfoJson;
	}
	
	private String getToolsWithChild(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		//String pageName = request.getParameter("pageObjectName");
		String fromIndex = request.getParameter("start");
		String pageCount = request.getParameter("limit");
		String customerId = request.getParameter("customerId");
		//搜索字符串
		
		String searchStr = request.getParameter("searchStr");
		String productCode = "";
		String brandCode = "";
		String productName = "";
		if(searchStr != null && !"".equals(searchStr)) {
			
			JSONObject search = JSONObject.fromObject(searchStr);
			
			try {
				productCode = search.getString("productCode");
			} catch(Exception e) {
				
			}
			try {
				brandCode = search.getString("brandCode");
			} catch(Exception e) {
				
			}
			try {
				productName = search.getString("productName");
			} catch(Exception e) {
				
			}
		}
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("productCode", productCode);
		parmMap.put("brandCode", brandCode);
		parmMap.put("productName", productName);
		parmMap.put("customerId", customerId);
		
		ArrayList list = new ArrayList();
		pageCount = pageCount == null ? "10" : pageCount;
		Integer pageSize = Integer.parseInt(pageCount);
		Integer startIndex = 0;
		if(fromIndex != null) {
			startIndex = Integer.parseInt(fromIndex);
		}
		
		int total = proToolsInforService.getProToolsTotal(parmMap);
		
		PaginationSupport pageInfo  = new PaginationSupport(list,total,pageSize,startIndex);
		
		parmMap.put("pageInfo", pageInfo);
		List<TreeDto> proList = new ArrayList<TreeDto>();
		
		proList = proToolsInforService.getToolsWithChildren(parmMap);
		pageInfo.setItems(proList);
		
		String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
		//System.out.println(pageInfoJson);
		return pageInfoJson;
	}
	
	public ProToolsInforService getProToolsInforService() {
		return proToolsInforService;
	}
	public void setProToolsInforService(ProToolsInforService proToolsInforService) {
		this.proToolsInforService = proToolsInforService;
	}
	
}
