package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;

public class GetQuoInfoAction extends Action {
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
		String id = request.getParameter("quoId");
		QuotationDto quotation = generalQuoService.getQuoInfoById(id);
		
		quotation.setCustomerCode(new StringBuffer(quotation.getCustomerCode()).append("-").append(quotation.getCustomerName()).toString());
		
		String resultJson = JSONObject.fromObject(quotation).toString();
		String resultStr = "{ success : true, data : " + resultJson + "}";
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
	
}
