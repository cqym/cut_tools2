/**
 * 
 */
package com.tl.resource.web.baseInfo;

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

import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.business.dto.SuppliersBrandDto;

/**
 * @author xtaia
 * 供应商对应品牌信息
 */
public class SupplierBrankListAction extends Action {

	private BaseInfoService baseInfoService;

	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");

		String supplierId = request.getParameter("supplierId");

		Map<String, Object> parmMap = new HashMap<String, Object>();

		if(start == null) {
			start = "0";
		}
		
		if(limit == null) {
			limit = "100";
		}
		
		parmMap.put("start", Integer.parseInt(start));
		parmMap.put("limit", Integer.parseInt(limit));
		parmMap.put("supplierId", supplierId);
		
		

		// 得到已销售某种产品总数
		int total = baseInfoService.getSuppliersBrandTotalBySupplierId(parmMap);

		List<SuppliersBrandDto> list = baseInfoService.getSuppliersBrandByPageAndSupplierId(parmMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{totalProperty : " + total
				+ ", supplierBrankList : " + jsonStr + "}";

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}





}
