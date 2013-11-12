package com.tl.resource.web.scheduleOrder;

import java.io.PrintWriter;
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

import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.SuppliersInforDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.dao.pojo.TContractProjectSortInfor;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;
/**
 * 分类下拉菜单
 * @author lisheng
 *
 */
public class QuotationProjectSortInforListAction extends Action{

	private ScheduleOrderService scheduleOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		String quotationCode = request.getParameter("quotationCode");
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("quotationCode",quotationCode);
		List<TQuotationProjectSortInfor> cpsi = scheduleOrderService.getSPSIList(parmMap);
		String jsonResult = JSONArray.fromObject(cpsi).toString();
		PrintWriter out = response.getWriter();
		out.write("{totalProperty:1,root:" + jsonResult + "}");
		out.flush();
		out.close();
		return null;
	}

	public ScheduleOrderService getScheduleOrderService() {
		return scheduleOrderService;
	}

	public void setScheduleOrderService(ScheduleOrderService scheduleOrderService) {
		this.scheduleOrderService = scheduleOrderService;
	}

	
	
	
}
