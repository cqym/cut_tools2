package com.tl.resource.web.quotation.generalquo;

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

import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TCustomersInfor;
/**
 * 根据用户编号获取用户信息
 * @author Administrator
 *
 */
public class GetCusByCuscodeAction extends Action {
	private GeneralQuoService generalQuoService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String customerCode = request.getParameter("customerCode");
		TCustomersInfor customer = null;
		String customerId = "";
		String resultStr = "{success : false, msg : '客户信息不存在，你确定继续吗？'}";
		if(null != customerCode && !"".equals(customerCode)) {
			customer = generalQuoService.getCustomerByCusCode(customerCode);
			if(customer != null) {
				customerId = customer.getId();
				resultStr = "{success : true, customerId : '" + customerId + "'}";
			}
		}
		PrintWriter out = response.getWriter();
		
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}
	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}
}
