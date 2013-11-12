/**
 * 
 */
package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.ProductCorrelationService;

/**
 * @author xtaia
 * 删除客户销售历史记录
 */
public class DeleteCurSalerPriceAction extends Action {


	
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
		String resultStr = "{success : true, msg : '删除客户销售历史信息成功'}";
		
		String curSalerPriceIdPar = request.getParameter("curSalerPriceIdPar").trim();
		
		
		try {
			productCorrelationService.deleteCurSalerPriceById(curSalerPriceIdPar);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '删除客户销售历史信息失败'}";
		}

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		
		return null;
	}


}
