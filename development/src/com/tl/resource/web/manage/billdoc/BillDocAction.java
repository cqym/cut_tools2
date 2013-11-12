package com.tl.resource.web.manage.billdoc;

import java.io.PrintWriter;
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

import com.tl.resource.business.dto.BillDocumentDto;
import com.tl.resource.business.dto.DepartmentsDto;
import com.tl.resource.business.manage.BillDocumentService;

public class BillDocAction extends DispatchAction {
	private BillDocumentService billDocumentService;

	public BillDocumentService getBillDocumentService() {
		return billDocumentService;
	}

	public void setBillDocumentService(BillDocumentService billDocumentService) {
		this.billDocumentService = billDocumentService;
	}
	
	public ActionForward getBillDocument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String billTypeStr = request.getParameter("billType");
		String billCode = request.getParameter("billCode");
		
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		Integer start = 0;
		Integer limit = 15;
		if(startStr != null && !"".equals(startStr)) {
			start = Integer.parseInt(startStr);
		}
		
		if(limitStr != null && !"".equals(limitStr)) {
			limit = Integer.parseInt(limitStr);
		}
		
		if(billTypeStr == null || "".equals(billTypeStr))
			billTypeStr = "1";
		Integer billType = Integer.valueOf(billTypeStr);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("billType", billType);
		paramMap.put("billCode", billCode);
		paramMap.put("limit", limit);
		paramMap.put("start", start);
		
		List<BillDocumentDto> list = billDocumentService.getBillDocument(paramMap);
		Integer total = billDocumentService.getBillDocTotal(paramMap);
		
		String resultStr = "{totalProperty : " + total + ", billDoc : "  + JSONArray.fromObject(list).toString() + "}";
		
		out.println(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward updateBillDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		String billDocStr = request.getParameter("billDoc");//单据
		String billTypeStr = request.getParameter("billType");//单据类型
		
		String resultStr = "{success : false, msg : '修改单据状态失败！'}";
		
		Integer billType = Integer.valueOf(billTypeStr);
		
		JSONObject jsonObj = JSONObject.fromObject(billDocStr);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("billType", billType);
		if(jsonObj.has("id"))
			paramMap.put("id", jsonObj.getString("id"));
		if(jsonObj.has("status"))
			paramMap.put("status", jsonObj.getString("status"));
		
		int num = billDocumentService.updateBillDoc(paramMap);
		if(num > 0)
			resultStr = "{success : true, msg : '修改单据状态成功！'}";
	
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
}
