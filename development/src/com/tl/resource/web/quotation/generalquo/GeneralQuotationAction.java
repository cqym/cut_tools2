package com.tl.resource.web.quotation.generalquo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.audit.IAuditBusinessObject;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class GeneralQuotationAction extends Action {
	private IAuditBusinessObject generalQuoAudit;
	private IAuditService auditService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sourceId = request.getParameter("sourceId");
		LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
		UserDto userDto = null;
		if(loginInfor != null){
			userDto  = loginInfor.getUser();
			auditService.setAuditBusinessObject(generalQuoAudit);
			auditService.submitBusiness(generalQuoAudit.getBusinessType(), sourceId, userDto);
		}
		return null;
	}
	
	public IAuditBusinessObject getGeneralQuoAudit() {
		return generalQuoAudit;
	}
	public void setGeneralQuoAudit(IAuditBusinessObject generalQuoAudit) {
		this.generalQuoAudit = generalQuoAudit;
	}
	public IAuditService getAuditService() {
		return auditService;
	}
	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}
	
	
}
