package com.tl.resource.web.scheduleOrder;

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

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.ReserveOrderBean;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;

public class QuotationListAction extends Action{
	
	private ScheduleOrderService scheduleOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		response.setContentType("text/html;charset=utf-8");		
		String psize = request.getParameter("limit") == null?"20":request.getParameter("limit");
		String sIndex = request.getParameter("start") == null?"0":request.getParameter("start");
		Integer pageSize = Integer.parseInt(psize);
		Integer startIndex = Integer.parseInt(sIndex);
		String quotationCode = request.getParameter("quotationCode");
		String customerName = request.getParameter("customerName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String quotationType = request.getParameter("quotationType");
		String leaf = request.getParameter("leaf");
		String outStockType = request.getParameter("outStockType");
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("startIndex", startIndex);
		parmMap.put("pageSize", pageSize);
		parmMap.put("userId", userDto.getId());
		parmMap.put("quotationCode", quotationCode);
		parmMap.put("customerName", customerName);
		parmMap.put("startTime", startTime);
		parmMap.put("endTime", endTime);
		parmMap.put("currUserId", userDto.getId());
		parmMap.put("quotationType", quotationType);
		parmMap.put("leaf", leaf);
		parmMap.put("outStockType", outStockType);
		int total = scheduleOrderService.getQuotationListCount(parmMap);
		List<TQuotationInfor> quotationInforList = scheduleOrderService.getQuotationList(parmMap);
		PrintWriter out = response.getWriter();
		out.write("{root:"+JSONArray.fromObject(quotationInforList).toString()+",totalProperty:"+total+"}");
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
