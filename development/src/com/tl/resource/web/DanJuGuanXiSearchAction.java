package com.tl.resource.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.search.DanJuGuanXiSearchService;

public class DanJuGuanXiSearchAction extends DispatchAction {
  private DanJuGuanXiSearchService danJuGuanXiSearchService;

  public ActionForward requestDanJu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String code = request.getParameter("code");
    PrintWriter out = response.getWriter();
    Map<String, List<Object>> rt = null;
    try {
      rt = danJuGuanXiSearchService.findDanJuListByCode(code);
      out.print(JSONObject.fromObject(rt).toString());
    } catch (RuntimeException e) {
      e.printStackTrace();
      out.print("{msg:'" + e.getMessage() + "'}");
    }

    out.flush();
    out.close();
    return null;
  }

  public DanJuGuanXiSearchService getDanJuGuanXiSearchService() {
    return danJuGuanXiSearchService;
  }

  public void setDanJuGuanXiSearchService(DanJuGuanXiSearchService danJuGuanXiSearchService) {
    this.danJuGuanXiSearchService = danJuGuanXiSearchService;
  }

}
