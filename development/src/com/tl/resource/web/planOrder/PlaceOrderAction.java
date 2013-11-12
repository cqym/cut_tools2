package com.tl.resource.web.planOrder;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.planOrder.PlanOrderService;
import com.tl.resource.business.reserveOrder.ReserveOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class PlaceOrderAction extends Action{
	
	private PlanOrderService planOrderService;

	/**
	 * 提交下单
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids");
		if(ids!=null&&ids.length>0)
		{
			for(int i=0;i<ids.length;i++)
			{
				TOrderInfor order = new TOrderInfor();
				order.setId(ids[i]);
				order.setStatus(4);
				planOrderService.PlaceOrder(order);
			}
		}
		PrintWriter out = response.getWriter();
		out.write("{success : true, msg : '提交下单成功'}");
		out.flush();
		out.close();
		return null;
	}

	public PlanOrderService getPlanOrderService() {
		return planOrderService;
	}

	public void setPlanOrderService(PlanOrderService planOrderService) {
		this.planOrderService = planOrderService;
	}

	
}
