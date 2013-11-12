/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TQuotationInfor;

/**
 * @author xtaia
 * 得到报价单基本信息
 */
public class GetQuoInfoByIdAction extends Action {
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
		String quoId = request.getParameter("quoId");
		
		TQuotationInfor quotation = projectQuoService.getQuoInfoByID(quoId);
		String resultJson = JSONObject.fromObject(quotation).toString();
		String resultStr = "{ success : true, data : " + resultJson + "}";
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	



}
