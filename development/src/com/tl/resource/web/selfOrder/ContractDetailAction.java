package com.tl.resource.web.selfOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.ContractProductDetailDto;
import com.tl.resource.business.selfOrder.SelfOrderService;

public class ContractDetailAction extends Action{

	private SelfOrderService selfOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String size = request.getParameter("limit") == null?"15":request.getParameter("limit");
		String start = request.getParameter("start") == null?"0":request.getParameter("start");
		int pageSize = Integer.parseInt(size);
		int startIndex = Integer.parseInt(start);
		String contractId = request.getParameter("contractId");
		Map<String, Object> parmMap = new HashMap<String, Object>(); 
		parmMap.put("contractId", contractId);
		parmMap.put("startIndex", startIndex);
		parmMap.put("pageSize", pageSize);
		int total = selfOrderService.getContractDetailCount(parmMap);
		List<ContractProductDetailDto> contractDetailList = selfOrderService.getContractDetail(parmMap);
		PaginationSupport pageInfo  = new PaginationSupport(contractDetailList,total,pageSize,startIndex);
		String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
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
