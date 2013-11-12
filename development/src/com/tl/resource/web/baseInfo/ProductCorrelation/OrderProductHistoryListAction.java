/**
 * 
 */
package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.business.dto.OrderPriceHistoryDto;

/**
 * @author xtaia
 *
 */
public class OrderProductHistoryListAction extends Action {
	

	
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

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		
		String productId = request.getParameter("productId");
		
		//System.out.println("@@@@@@@" + productId);
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		
		
		String searchStr = request.getParameter("searchStr");
		
		if(searchStr != null){
			
			JSONObject  searchJson = JSONObject.fromObject(searchStr);
			parmMap.put("suppliersInforId", searchJson.getString("supplierName"));
			
			
		}
		if(start == null){ start = "0";}
		if(limit == null){ limit = "20";}
		parmMap.put("start", Integer.parseInt(start));
		parmMap.put("limit", Integer.parseInt(limit));
		parmMap.put("productToolsInforId", productId);
		
		//System.out.println("$$$$$$$$$$$$$$$$");
		//寰楀埌宸查攢鍞煇绉嶄骇鍝佹�鏁�
		int total = productCorrelationService.getOrderHistoryProductTotalByProductId(parmMap) ;
		//System.out.println("###############");
		
		System.out.println(parmMap);
		List<OrderPriceHistoryDto> list = productCorrelationService.getOrderHistoryProductByPageAndProductId(parmMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{totalProperty : " + total + ", orderProductHistoryList : "  + jsonStr + "}";
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}

}
