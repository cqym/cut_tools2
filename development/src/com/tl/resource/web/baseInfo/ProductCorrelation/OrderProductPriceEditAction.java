package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.baseInfo.OrderPriceService;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.UserDto;

public class OrderProductPriceEditAction extends DispatchAction {
	private OrderPriceService orderPriceService; 
	public ActionForward addOrderProductPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String OrderPriceHistoryJsonStr = request.getParameter("OrderPriceHistory");
		JSONObject OrderPriceHistoryJson = JSONObject.fromObject(OrderPriceHistoryJsonStr);
		OrderPriceHistoryDto dto = (OrderPriceHistoryDto) JSONObject.toBean(OrderPriceHistoryJson,OrderPriceHistoryDto.class);
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto user = loginInfor.getUser();
		
		try {
			orderPriceService.addOrderPrice(dto , user);
			out.print("true");
		} catch (Exception e) {
			out.print("false");
			e.printStackTrace();
		}
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward deleteOrderProductPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		orderPriceService.deleteOrderPriceById(id);
		out.print("true");
		out.flush();
		out.close();
		return null;
	}
	public OrderPriceService getOrderPriceService() {
		return orderPriceService;
	}
	public void setOrderPriceService(OrderPriceService orderPriceService) {
		this.orderPriceService = orderPriceService;
	}
	
}
