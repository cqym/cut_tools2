package com.tl.resource.web.delivery;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.business.dto.UserDto;

public class SubmitAuditDeliveryInforAction extends Action {
	private IAuditService auditService;

	public IAuditService getAuditService() {
		return auditService;
	}

	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		
		String deliveryType = request.getParameter("deliveryType");
		String[] ids = request.getParameterValues("ids");
		String auditType = "";
		if("contract".equals(deliveryType)){
			auditType = "011";
		}else if("quotation".equals(deliveryType)){
			auditType = "010";	
		}else if("tryToools".equals(deliveryType)){
			auditType = "018";	
		}
		
		String resultStr = "";
		if(ids != null) {
			for (int i = 0; i < ids.length; i++) {
				resultStr = auditService.submitBusiness(auditType, ids[i], userDto);
			}
		}
		if(null == resultStr) {
			resultStr = "";
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

}
