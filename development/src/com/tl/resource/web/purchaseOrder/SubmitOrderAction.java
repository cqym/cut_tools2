package com.tl.resource.web.purchaseOrder;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.business.dto.UserDto;

public class SubmitOrderAction extends DispatchAction {
  private IAuditService auditService;

  public IAuditService getAuditService() {
    return auditService;
  }

  public void setAuditService(IAuditService auditService) {
    this.auditService = auditService;
  }

  private void submitOrder(HttpServletRequest request, HttpServletResponse response, String auditType) throws IOException {
    response.setContentType("text/html;charset=utf-8");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    String[] ids = request.getParameterValues("ids");
    String resultStr = null;
    if (ids != null && ids.length > 0) {
      for (int i = 0; i < ids.length; i++) {
        resultStr = auditService.submitBusiness(auditType, ids[i], userDto);
      }
    }
    PrintWriter out = response.getWriter();
    out.write(resultStr == null ? "" : resultStr);
    out.flush();
    out.close();
  }

  public ActionForward submitContractOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "005");
    return null;
  }

  public ActionForward submitPlanOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "007");
    return null;
  }

  public ActionForward submitReserverOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "004");
    return null;
  }

  public ActionForward submitSheduleOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "008");
    return null;
  }

  public ActionForward submitScheduleSelfOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "016");
    return null;
  }

  public ActionForward submitSelfOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "006");
    return null;
  }

  public ActionForward submitTryOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "019");
    return null;
  }

  public ActionForward submitTrySelfOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    submitOrder(request, response, "017");
    return null;
  }

}
