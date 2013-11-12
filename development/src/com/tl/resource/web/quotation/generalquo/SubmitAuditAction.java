package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;

public class SubmitAuditAction extends Action {
	private IAuditService auditService;
	private GeneralQuoService generalQuoService;

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
		
		String quoId = request.getParameter("quoId");
		
		JSONArray jsonArray = JSONArray.fromObject(quoId);
		Iterator<JSONObject> iterator = jsonArray.iterator();
		
		String resultStr = "";
		
		while(iterator.hasNext()) {
			JSONObject obj = iterator.next();
			String id = obj.getString("id");
			String quoType = obj.getString("quotationType");
			
			if("0".equals(quoType)) {
				//if(generalQuoService.validator(id)) {
					//resultStr = auditService.submitBusiness("012", id, userDto);
				//} else {
					resultStr = auditService.submitBusiness("001", id, userDto);
				//}
			} else if("3".equals(quoType)) {
				resultStr = auditService.submitBusiness("014", id, userDto);
			} else if("4".equals(quoType)) {
				resultStr = auditService.submitBusiness("015", id, userDto);
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

	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}

	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}
	
	
}
