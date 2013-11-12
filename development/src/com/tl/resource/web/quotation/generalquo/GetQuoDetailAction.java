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

import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;

public class GetQuoDetailAction extends Action {
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
		String quoId = request.getParameter("quoId");
		String cusId = request.getParameter("cusId");
		String method = request.getParameter("method");
		List<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();
		
		if(quoId != null && !"".equals(quoId)) {
			if(cusId != null && !"".equals(cusId)) {
				Map<String, Object> parmMap = new HashMap<String, Object>();
				parmMap.put("quoId", quoId);
				parmMap.put("cusId", cusId);
				list = this.generalQuoService.getQuoDetail(parmMap);
			} else {
				if("3".equals(method)) {
					//预订
					list = this.generalQuoService.getQuoDetail4Reserve(quoId);
				} else if("4".equals(method)) {
					//试刀
					list = generalQuoService.getQuoDetail4TestCut(quoId);
				} else {
					//普通(项目)
					list = this.generalQuoService.getQuoDetail(quoId);
				}
			}
		}
		
		String jsonStr = JSONArray.fromObject(list).toString();
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	
}
