package com.tl.resource.web.contractOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.dao.pojo.TOrderDetail;

/**
 * 比较订单与合同中采购价格，如果订单中高出，则返回订单详细的序号
 * @author ls
 *
 */
public class CmprStockPrice extends Action{

	private ContractOrderService contractOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> parmMap = new HashMap<String, Object>(); 
		parmMap.put("conType", request.getParameter("conType"));
		parmMap.put("orderInforId", request.getParameter("orderInforId"));
		List<TOrderDetail> list = contractOrderService.cmprStockPrice(parmMap);
		String resultStr = null;//返回值
		String numStr = "";
		if(list.size()>0)
		{
			for(int i = 0;i<list.size();i++)
			{
				numStr += list.get(i).getSerialNumber() +",";
			}
			resultStr = "{success : true,numStr:'"+numStr+"'}";//订单添加成功时将订单的ID返回到页面
		}
		else
		{
			resultStr = "{success : false}";//订单添加成功时将订单的ID返回到页面
		}
	System.out.println(list.size());
	System.out.println(resultStr);
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
