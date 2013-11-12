package com.tl.resource.web.audit;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.audit.IAuditFlowDefinedService;
import com.tl.resource.audit.dto.TAuditFlowDetailDto;
import com.tl.resource.audit.dto.TAuditFlowInforDto;
import com.tl.resource.audit.dto.TAuditTypeDto;

public class AuditFlowDefinedViewAction extends DispatchAction{
	IAuditFlowDefinedService auditFlowDefinedService;
	public ActionForward queryAuditTypes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		List<TAuditTypeDto> list = auditFlowDefinedService.getAllAuditTypes();
		out.println(new StringBuffer("{auditTypes:").append(JSONArray.fromObject(list).toString()).append(",totalCount:").append(list.size()).append("}"));
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward queryAuditInfors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		String auditType = request.getParameter("auditType");
		PrintWriter out = response.getWriter();
		List<TAuditFlowInforDto> list = auditFlowDefinedService.getFlowInforsByAuditType(auditType);
		out.println(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward queryAuditInforDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		String inforId = request.getParameter("inforId");
		PrintWriter out = response.getWriter();
		List<TAuditFlowDetailDto> list = auditFlowDefinedService.getFlowDetailByFlowInforId(inforId);
		out.println(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public IAuditFlowDefinedService getAuditFlowDefinedService() {
		return auditFlowDefinedService;
	}
	public void setAuditFlowDefinedService(
			IAuditFlowDefinedService auditFlowDefinedService) {
		this.auditFlowDefinedService = auditFlowDefinedService;
	}
	
}
