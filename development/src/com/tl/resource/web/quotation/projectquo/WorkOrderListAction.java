/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

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

import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

/**
 * @author xtaia
 *
 */
public class WorkOrderListAction extends Action{
	
	private ProjectQuoService projectQuoService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String quoid = request.getParameter("quoid");
		
		//System.out.println("*****quoid****"+quoid+"****start****" );
		
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("quotation_infor_id",quoid);
		
		List<TQuotationProjectSortInfor> list = projectQuoService.getWorkOrderList(parmMap);
		
		
		
		
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{workOrderList : "  + jsonStr + "}";
		
		
		
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		
		return null;
	}
	
	public ProjectQuoService getProjectQuoService() {
		return projectQuoService;
	}
	public void setProjectQuoService(ProjectQuoService projectQuoService) {
		this.projectQuoService = projectQuoService;
	}
	

}
