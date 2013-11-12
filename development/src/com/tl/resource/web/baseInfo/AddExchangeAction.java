/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TExchangeRate;

/**
 * @author xtaia
 * 添加汇率信息
 */
public class AddExchangeAction extends Action {


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
		
		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '添加汇率信息成功'}";
		
		String exchangeFormInfoPar = request.getParameter("exchangeFormInfoPar");
		
		
		JSONObject exchangeObj = JSONObject.fromObject(exchangeFormInfoPar);
		JSONObject exchangeDate = exchangeObj.getJSONObject("data");
	
		try {
			TExchangeRate exchangeInfo = (TExchangeRate)JSONObject.toBean(exchangeDate, TExchangeRate.class);
			exchangeInfo.setId(GenerateSerial.getUUID());
			baseInfoService.saveExchange(exchangeInfo);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '添加汇率信息失败'}";
		}
	
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		
		return null;
	}



}
