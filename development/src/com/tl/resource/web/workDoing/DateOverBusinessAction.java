package com.tl.resource.web.workDoing;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.workDoing.OverDateBusinessService;

public class DateOverBusinessAction extends DispatchAction {
  private OverDateBusinessService overDateBusinessService;

  public ActionForward getOverDateBusinessNodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String pid = request.getParameter("node");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    List<BusinessInforDto> list = overDateBusinessService.getBusinessDataInfor(pid, loginInfor);
    String rt = JSONArray.fromObject(list).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public OverDateBusinessService getOverDateBusinessService() {
    return overDateBusinessService;
  }

  public void setOverDateBusinessService(OverDateBusinessService overDateBusinessService) {
    this.overDateBusinessService = overDateBusinessService;
  }

}
