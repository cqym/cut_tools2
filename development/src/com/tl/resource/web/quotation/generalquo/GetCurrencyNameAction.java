package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TExchangeRate;

public class GetCurrencyNameAction extends Action {
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
		List<TExchangeRate> list = generalQuoService.getCurrencyName();
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		String resultStr = "{currency : " + jsonStr + "}";
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
}
