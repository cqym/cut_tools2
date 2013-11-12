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
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.business.scheduleSelfOrder.ScheduleSelfOrderService;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class QuotationDetailListBySupplierAction extends Action{

	private ScheduleSelfOrderService scheduleSelfOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");		
		String quotationCode = request.getParameter("quotationCode");
		String supplierId = request.getParameter("supplierId");
		String productBrand = request.getParameter("productBrand");
		String contractProjectSortId = request.getParameter("id");
		String brandCode = request.getParameter("brandCode");
		String productName = request.getParameter("productName");
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("quotationCode", quotationCode);
		parmMap.put("supplierId", supplierId);		
		parmMap.put("contractProjectSortId", contractProjectSortId);
		parmMap.put("brandCode", brandCode);
		parmMap.put("productName", productName);
		int total = scheduleSelfOrderService.getQuoDetailCountBySupplier(parmMap);
		List<OrderDetialsDto> quotationInforList = scheduleSelfOrderService.getQuoDetailBySupplier(parmMap);
//		PrintWriter out = response.getWriter();
//		out.write("{root:"+JSONArray.fromObject(quotationInforList).toString()+",totalProperty:"+total+"}");
		
//		PaginationSupport pageInfo  = new PaginationSupport(quotationInforList,total);
		String pageInfoJson = JSONArray.fromObject(quotationInforList).toString();
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
