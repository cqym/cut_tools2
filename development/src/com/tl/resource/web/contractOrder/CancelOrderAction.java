package com.tl.resource.web.contractOrder;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;

/**
 * 作废订单
 * @author Administrator
 *
 */
public class CancelOrderAction extends Action{

	private ContractOrderService contractOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '订单作废成功'}";
		String[] ids = request.getParameterValues("ids");
		
		try {
			
			for(int i=0;i<ids.length;i++)
			{
				TOrderInfor order = new TOrderInfor();
				order.setId(ids[i]);
				order.setStatus(-1);
				contractOrderService.updateOrder(order, null);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '订单作废成功失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush(); 
		out.close();
		return null;
	}

	public ContractOrderService getContractOrderService() {
		return contractOrderService;
	}

	public void setContractOrderService(ContractOrderService contractOrderService) {
		this.contractOrderService = contractOrderService;
	}
	
	
}
