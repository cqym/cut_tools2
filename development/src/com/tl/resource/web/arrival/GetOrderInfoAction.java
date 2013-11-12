package com.tl.resource.web.arrival;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.arrival.ArrivalService;
import com.tl.resource.business.dto.ArrivalOrderDetialsDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;

/**
 * 根据订单类型获取订单信息
 * @author ftl
 *
 */
public class GetOrderInfoAction extends DispatchAction {
	//到货单管理Service
	private ArrivalService arrivalService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		if("getStockOrder".equals(method)) {
			return getStockOrder(mapping, form, request, response);
		} else if("getReserveOrder".equals(method)) {
			return getReserveOrder(mapping, form, request, response);
		} else if("getOrderDetail".equals(method)) {
			return getOrderDetail(mapping, form, request, response);
		} else if("getOrderByCode".equals(method)) {
			return getOrderByCode(mapping, form, request, response);
		} else if("getSubscribeOrder".equals(method)) {
			return getSubscribeOrder(mapping, form, request, response);
		} else if("getTestCutOrder".equals(method)) {
			return getTestCutOrder(mapping, form, request, response);
		} else {
			return null;
		}
	}
	
	//试刀订单
	private ActionForward getTestCutOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String orderCode = request.getParameter("orderCode");

		
		//返回前台字符串
		String resultStr = getOrder(new Integer[]{6, 8}, orderCode);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}

	//预订订单
	private ActionForward getSubscribeOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String orderCode = request.getParameter("orderCode");

		
		//返回前台字符串
		String resultStr = getOrder(new Integer[]{5, 7}, orderCode);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}

	private ActionForward getOrderByCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String orderCode = request.getParameter("orderCode");
		
		OrderInfoDto order = arrivalService.getOrderInfoByCode(orderCode);
		//int total = arrivalService.getOrderDetailsTotalByOrderId(orderId);
		
		Integer jsonStr = order.getOrderType();
		
		//返回前台字符串
		//String resultStr = "{totalProperty : " + total + ",orderProducts : " + jsonStr + "}";
		String resultStr = "{orderType : " + jsonStr + "}";
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}

	//获取订单详细
	private ActionForward getOrderDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String orderId = request.getParameter("orderId");
		
		List<ArrivalOrderDetialsDto> list = arrivalService.getDetailWithHasArrivalByOrderId(orderId);
		//int total = arrivalService.getOrderDetailsTotalByOrderId(orderId);
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		//返回前台字符串
		//String resultStr = "{totalProperty : " + total + ",orderProducts : " + jsonStr + "}";
		String resultStr = "{orderProducts : " + jsonStr + "}";
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	//获取采购订单
	private ActionForward getStockOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String orderCode = request.getParameter("orderCode");

		
		//返回前台字符串
		String resultStr = getOrder(new Integer[]{1, 3}, orderCode);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	//获取储备订单
	private ActionForward getReserveOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String orderCode = request.getParameter("orderCode");
		//返回前台字符串
		String resultStr = getOrder(new Integer[]{2, 4}, orderCode);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	private String getOrder(Integer[] orderType, String orderCode) {
		//参数Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer orderTypeStr = new StringBuffer();
		for (int i = 0; i < orderType.length; i++) {
			orderTypeStr.append(orderType[i].toString());
			if(i < orderType.length-1) {
				orderTypeStr.append(",");
			}
		}
		//订单类型 1,3：采购, 2,4：储备, 5：预订
		if(orderType != null) {
			paramMap.put("orderType", orderTypeStr);
		}
		paramMap.put("orderCode", orderCode);
		
		System.out.println(paramMap);
		//订单列表
		List<OrderInfoDto> list = arrivalService.getOrderInfoByCode(paramMap);
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		//返回前台字符串
		String resultStr = "{orderInfo : "  + jsonStr + "}";
		
		return resultStr;
	}
	
	public ArrivalService getArrivalService() {
		return arrivalService;
	}
	public void setArrivalService(ArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}
	
}
