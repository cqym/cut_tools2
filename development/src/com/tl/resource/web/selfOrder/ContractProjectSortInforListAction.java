package com.tl.resource.web.selfOrder;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.SuppliersInforDto;
import com.tl.resource.business.selfOrder.SelfOrderService;
import com.tl.resource.dao.pojo.TContractProjectSortInfor;
/**
 * 分类下拉菜单
 * @author lisheng
 *
 */
public class ContractProjectSortInforListAction extends Action{

	private SelfOrderService selfOrderService;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		String contractCode = request.getParameter("contractCode");
		List<TContractProjectSortInfor> cpsi = selfOrderService.getCPSIList(contractCode);
		String jsonResult = JSONArray.fromObject(cpsi).toString();
		PrintWriter out = response.getWriter();
		out.write("{totalProperty:1,root:" + jsonResult + "}");
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
