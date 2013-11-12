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
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.business.dto.CurSalesPriceHistoryDto;

/**
 * @author xtaia
 * 销售历史信息列表
 */
public class CusSalesPriceHistoryListAction extends Action {
	

	
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
		
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		
		String searchStr = request.getParameter("searchStr");
		if(searchStr != null){
			JSONObject  searchJson = JSONObject.fromObject(searchStr);
			parmMap.put("productName", searchJson.getString("productName"));
			parmMap.put("productCode", searchJson.getString("productCode"));
			parmMap.put("productSort", searchJson.getString("sortCode"));
			parmMap.put("brandCode", searchJson.getString("brandCode"));
			parmMap.put("productBrand", searchJson.getString("productBrand"));
		}
		
		//System.out.println("###############searchStr" + searchStr);
		
		parmMap.put("start", Integer.parseInt(start));
		parmMap.put("limit", Integer.parseInt(limit));
		
		//System.out.println("$$$$$$$$$$$$$$$$");
		//得到已销售产品种类总数
		int total = productCorrelationService.getCusSalesHistoryProductTotal(parmMap) ;
		//System.out.println("###############");
		String sort = request.getParameter("sort");
		if(sort != null){
			parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
			parmMap.put("dir", request.getParameter("dir"));
		}
		
		List<CurSalesPriceHistoryDto> list = productCorrelationService.getCusSalesHistoryProductByPage(parmMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{totalProperty : " + total + ", cusSalesPriceHistoryList : "  + jsonStr + "}";
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}

	
	
	
	
	


}
