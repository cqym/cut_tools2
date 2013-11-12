/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.quotation.projectquo.ProjectQuoService;

/**
 * @author xtaia
 *
 */
public class DeteleWorkOrderAction extends Action{
	

	
	
	private ProjectQuoService projectQuoService;
	public ProjectQuoService getProjectQuoService() {
		return projectQuoService;
	}
	public void setProjectQuoService(ProjectQuoService projectQuoService) {
		this.projectQuoService = projectQuoService;
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '删除工序信息成功'}";
		
		String workOrderId = request.getParameter("workOrderId").trim();
		
		
		try {
			projectQuoService.deleteWorkOrderById(workOrderId);
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '删除工序信息失败'}";
		}

		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		
		return null;
	}
	
	



}
