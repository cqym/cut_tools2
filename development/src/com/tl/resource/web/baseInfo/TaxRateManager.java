package com.tl.resource.web.baseInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TTaxRate;

public class TaxRateManager extends DispatchAction {
	private BaseInfoService baseInfoService;

	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		
		if("getTaxRate".equals(method)) {
			return getTaxRate(mapping, form, request, response);
		} else if("addTaxRate".equals(method)) {
			return addTaxRate(mapping, form, request, response);
		} else if("modifyTaxRate".equals(method)) {
			return modifyTaxRate(mapping, form, request, response);
		} else if("deleteTaxRate".equals(method)) {
			return deleteTaxRate(mapping, form, request, response);
		} else {
			return null;
		}
	}

	//删除税率
	private ActionForward deleteTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String taxRateIds = request.getParameter("taxRate");
		String resultStr = "{success : true, msg : '删除税率成功！'}";
		try {
			JSONArray array = JSONArray.fromObject(taxRateIds);
			baseInfoService.deleteTaxRate(array);
		} catch(Exception e) {
			resultStr = "{success : true, msg : '删除税率失败！'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//修改税率
	private ActionForward modifyTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String taxRateStr = request.getParameter("taxRate");
		String resultStr = "{success : true, msg : '修改税率成功！'}";
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(taxRateStr);
			TTaxRate taxRate = (TTaxRate)JSONObject.toBean(jsonObject, TTaxRate.class);
			baseInfoService.modifyTaxRate(taxRate);
		} catch(Exception e) {
			resultStr = "{success : false, msg : '" + e.getMessage() + ", 修改税率失败！'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//添加税率
	private ActionForward addTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String taxRateStr = request.getParameter("taxRate");
		String resultStr = "{success : true, msg : '添加税率成功！'}";
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(taxRateStr);
			TTaxRate taxRate = (TTaxRate)JSONObject.toBean(jsonObject, TTaxRate.class);
			taxRate.setId(GenerateSerial.getUUID());
			baseInfoService.addTaxRate(taxRate);
		} catch(Exception e) {
			resultStr = "{success : false, msg : '" + e.getMessage() + ", 添加税率失败！'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//取得税率
	private ActionForward getTaxRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		List<TTaxRate> list = baseInfoService.getTaxRate();
		String jsonStr = JSONArray.fromObject(list).toString();
		int total = baseInfoService.getTaxRateCount();
		
		//返回前台字符串
		String resultStr = "{totalProperty : " + total + ", taxRate : "  + jsonStr + "}";
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
}
