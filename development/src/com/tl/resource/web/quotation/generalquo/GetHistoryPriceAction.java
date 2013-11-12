package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.dto.SalesPriceHistoryDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TCusSalesPriceHistory;

public class GetHistoryPriceAction extends Action {
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
		String proId = request.getParameter("proId");
		String customerId = request.getParameter("customerId");
		
		List<SalesPriceHistoryDto> list = new ArrayList<SalesPriceHistoryDto>();
		
		if(proId != null && customerId != null) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("proId", proId);
			paramMap.put("customerId", customerId);
			
			list = generalQuoService.getSalesPriHistory(paramMap);
		}
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
		
		return null;
	}
}
