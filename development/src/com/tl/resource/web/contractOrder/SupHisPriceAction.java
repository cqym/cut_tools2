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

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.protoolsinfo.ProToolsInforService;

public class SupHisPriceAction extends DispatchAction {
	private ProToolsInforService proToolsInforService;
	public ActionForward findSupToolsList(ActionMapping mapping, ActionForm form,
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
			pageSize = "20";
		}
		Map<String,Object> mparams = new HashMap<String,Object>();
		mparams.put("supplierId", request.getParameter("supplierId"));
		mparams.put("brandCode", request.getParameter("brandCode"));
		//System.out.println(mparams);
		PaginationSupport conInfor = proToolsInforService.findSupToolsList(mparams,startIndex,pageSize);
		out.println(JSONObject.fromObject(conInfor));
		out.flush();
		out.close();
		return null;
	}
	public ProToolsInforService getProToolsInforService() {
		return proToolsInforService;
	}
	public void setProToolsInforService(ProToolsInforService proToolsInforService) {
		this.proToolsInforService = proToolsInforService;
	}
	
}
