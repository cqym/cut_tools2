/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TQuotationInfor;

/**
 * @author xtaia
 * 项目报价单导出报表
 */
public class ExcelAction extends Action {
	
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
		String quoId = request.getParameter("quoId").trim();
		TQuotationInfor quoDto = projectQuoService.getQuoInfoByID(quoId);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=quotation-" + quoDto.getQuotationCode() + ".xls");
		//物理地址
		String realPath = request.getSession().getServletContext().getRealPath("");
		projectQuoService.exportExcel(quoDto,response.getOutputStream(),realPath);
		//System.out.println(quoId + "@@@@@@@@@@@@@@@@@@@@@@@@");
		
		return null;
	}
	

}
