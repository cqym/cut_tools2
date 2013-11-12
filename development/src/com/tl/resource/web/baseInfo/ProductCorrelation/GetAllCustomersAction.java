/**
 * 
 */
package com.tl.resource.web.baseInfo.ProductCorrelation;

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

import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.dao.pojo.TCustomersInfor;

/**
 * @author xtaia
 * 得到所有客户信息
 * 
 */
public class GetAllCustomersAction extends Action {

	
	private ProductCorrelationService productCorrelationService ;

	public ProductCorrelationService getProductCorrelationService() {
		return productCorrelationService;
	}

	public void setProductCorrelationService(
			ProductCorrelationService productCorrelationService) {
		this.productCorrelationService = productCorrelationService;
	}
	
	
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
    	String customerName = request.getParameter("customerName");
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("customerName", customerName);
		
		
		List<TCustomersInfor> list = productCorrelationService.getAllCustomersList(parmMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{customersInfor : "  + jsonStr + "}";
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}

	
	



}
