package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.protoolsinfo.ProToolsInforService;

public class CheckToolsBeEditQuoAction extends Action {
  private ProToolsInforService proToolsInforService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String toolsId = request.getParameter("toolsId");
    PrintWriter out = response.getWriter();
    out.write(String.valueOf(proToolsInforService.isToolsBeEditedQuotation(toolsId)));
    out.flush();
    out.close();
    return null;
  }

  public ProToolsInforService getProToolsInforService() {
    return proToolsInforService;
  }

  public void setProToolsInforService(ProToolsInforService proToolsInforService) {
    this.proToolsInforService = proToolsInforService;
  }

}
