/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.quotation.projectquo.ProjectQuoService;

/**
 * @author xtaia
 *
 */
public class GetQuoDetailAction extends Action {
	

	private ProjectQuoService projectQuoService;
	public ProjectQuoService getProjectQuoService() {
		return projectQuoService;
	}
	public void setProjectQuoService(ProjectQuoService projectQuoService) {
		this.projectQuoService = projectQuoService;
	}
	
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String workOrderId = request.getParameter("workOrderId");
		String cusId = request.getParameter("cusId");
		List<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();
		
//		System.out.println("*************&&&&&&&&&&&&&&&*");
//		System.out.println("**************"+workOrderId+"***************");
//		System.out.println("*************&&&&&&&&&&&&&&&*");
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		
		if(workOrderId != null && !"".equals(workOrderId)) {
			parmMap.put("quotation_project_sort_id", workOrderId);
			if(cusId != null && !"".equals(cusId)) {
				parmMap.put("cusId", cusId);
				list = projectQuoService.getProductList4Copy(parmMap);
			} else {
				list = this.projectQuoService.getQuoDetailByWorkOrder(parmMap);
			}
		}
		
		String jsonStr = JSONArray.fromObject(list).toString();
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
		out.flush();
		out.close();
		
		return null;
	}
	

}
