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

import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.business.dto.CurSalesPriceHistoryDto;

/**
 * @author xtaia 根据产品获取销售历史
 */
public class CusSalesProductHistoryListAction extends Action {

	private ProductCorrelationService productCorrelationService;

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
		String pid = request.getParameter("pid");
		String customerCode = request.getParameter("customerCode");

		Map<String, Object> parmMap = new HashMap<String, Object>();

		String searchStr = request.getParameter("searchStr");
		if (searchStr != null) {

			JSONObject searchJson = JSONObject.fromObject(searchStr);
			parmMap.put("customerInforId", searchJson
					.getString("customerName"));
		}

		parmMap.put("start", Integer.parseInt(start));
		parmMap.put("limit", Integer.parseInt(limit));
		parmMap.put("product_tool_infor_id", "null".equals(productId) ? pid : productId);
		parmMap.put("customerCode", customerCode);
		// System.out.println("$$$$$$$$$$$$$$$$");
		// 得到已销售某种产品总数
		int total = productCorrelationService
				.getCusSalesHistoryProductTotalByProductId(parmMap);
		// System.out.println("###############");

		List<CurSalesPriceHistoryDto> list = productCorrelationService
				.getCusSalesHistoryProductByPageAndProductId(parmMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{totalProperty : " + total
				+ ", cusSalesProductHistoryList : " + jsonStr + "}";

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}

}
