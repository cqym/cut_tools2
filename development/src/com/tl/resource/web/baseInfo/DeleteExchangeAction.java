/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.BaseInfoService;

/**
 * @author xtaia
 * 删除汇率信息
 */
public class DeleteExchangeAction extends Action {
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
		String resultStr = "{success : true, msg : '删除汇率信息成功'}";
		
		String exchangeId = request.getParameter("exchangeIdPar").trim();
		
		
		try {
			baseInfoService.deleteExchangeById(exchangeId);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '删除汇率信息失败'}";
		}

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		
		return null;
	}




}
