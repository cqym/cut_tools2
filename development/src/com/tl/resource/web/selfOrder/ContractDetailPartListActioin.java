package com.tl.resource.web.selfOrder;

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

import com.tl.resource.business.dto.ContractDetailDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.selfOrder.SelfOrderService;

public class ContractDetailPartListActioin  extends Action{

	private SelfOrderService selfOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		String contractCode = request.getParameter("contractCode");
		String supplierId = request.getParameter("supplierId");
		Map<String, Object> parmMap = new HashMap<String, Object>(); 
		parmMap.put("contractCode", contractCode);
		parmMap.put("supplierId", supplierId);	
		List<OrderDetialsDto> contractDetailList = selfOrderService.getPartContractDetail(parmMap);
		String pageInfoJson = JSONArray.fromObject(contractDetailList).toString();
		PrintWriter out = response.getWriter();
		out.write(pageInfoJson);
		out.flush();
		out.close();
		return null;
	}

	public SelfOrderService getSelfOrderService() {
		return selfOrderService;
	}

	public void setSelfOrderService(SelfOrderService selfOrderService) {
		this.selfOrderService = selfOrderService;
	}
	
	
}
