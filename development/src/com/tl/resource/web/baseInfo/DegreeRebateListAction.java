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

import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.business.dto.DegreeRebateDto;

/**
 * @author xtaia
 * 等级对应组别折扣信息
 */
public class DegreeRebateListAction extends Action {

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

		String degreeId = request.getParameter("degreeId");
		
		
		//System.out.println("@@@@@@@@@@@@@" + degreeId + "WWWWWWWWWW" +  start + "DDDDDDDDD" + limit);

		Map<String, Object> parmMap = new HashMap<String, Object>();

	/*	String searchStr = request.getParameter("searchStr");
		if (searchStr != null) {

			JSONObject searchJson = JSONObject.fromObject(searchStr);
			parmMap.put("customerInforId", searchJson
					.getString("customerName"));
		}*/

		parmMap.put("start", Integer.parseInt(start));
		parmMap.put("limit", Integer.parseInt(limit));
		parmMap.put("degreeId", degreeId);

		// 得到已销售某种产品总数
		int total = baseInfoService.getDegreeRebateTotalByDegreeId(parmMap);

		String sort = request.getParameter("sort");
		if(sort != null){
			parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
			parmMap.put("dir", request.getParameter("dir"));
		}
		
		List<DegreeRebateDto> list = baseInfoService.getDegreeRebateByPageAndDegreeId(parmMap);
		
		//第一次未选择客户等级无信息
		if((degreeId == null) || ("".equals(degreeId))){
			total = 0 ;
			list = null;
		}
		
		
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{totalProperty : " + total
				+ ", degreeRebateList : " + jsonStr + "}";

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}



}
