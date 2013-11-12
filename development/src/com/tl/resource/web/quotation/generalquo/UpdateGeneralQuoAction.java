package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class UpdateGeneralQuoAction extends Action {
	private GeneralQuoService generalQuoService;

	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}

	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '修改报价信息成功'}";
		String quoStr = request.getParameter("quoForm");
		String quoProductStr = request.getParameter("quoProduct"); 
		String idStr = request.getParameter("ids");
		
		JSONObject quoObj = JSONObject.fromObject(quoStr);
		
		JSONObject quoForm = quoObj.getJSONObject("data");
		JSONArray quoProductArray = JSONArray.fromObject(quoProductStr);
		JSONArray idArray = JSONArray.fromObject(idStr);
		//System.out.println(quoProductStr);
		resultStr = generalQuoService.updateQuotation(quoForm, quoProductArray, idArray);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
}
