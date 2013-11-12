package com.tl.resource.web.scheduleSelfOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.business.scheduleSelfOrder.ScheduleSelfOrderService;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class QuotationDetailListAction extends Action{

	private ScheduleSelfOrderService scheduleSelfOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");		
		String psize = request.getParameter("limit") == null?"20":request.getParameter("limit");
		String sIndex = request.getParameter("start") == null?"0":request.getParameter("start");
		Integer pageSize = Integer.parseInt(psize);
		Integer startIndex = Integer.parseInt(sIndex);
		String qId = request.getParameter("qId");
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("startIndex", startIndex);
		parmMap.put("pageSize", pageSize);
		parmMap.put("qId", qId);
		parmMap.put("outStockType", 5);
		int total = scheduleSelfOrderService.getQuoDetailCountById(parmMap);
		List<QuotationDetailForOrderDto> quotationInforList = scheduleSelfOrderService.getQuoDetailById(parmMap);
//		PrintWriter out = response.getWriter();
//		out.write("{root:"+JSONArray.fromObject(quotationInforList).toString()+",totalProperty:"+total+"}");
		
		PaginationSupport pageInfo  = new PaginationSupport(quotationInforList,total,pageSize,startIndex);
		String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
		PrintWriter out = response.getWriter();
		out.write(pageInfoJson);
		
		out.flush();
		out.close();
		return null;
	}

	public ScheduleSelfOrderService getScheduleSelfOrderService() {
		return scheduleSelfOrderService;
	}

	public void setScheduleSelfOrderService(
			ScheduleSelfOrderService scheduleSelfOrderService) {
		this.scheduleSelfOrderService = scheduleSelfOrderService;
	}


	
}
