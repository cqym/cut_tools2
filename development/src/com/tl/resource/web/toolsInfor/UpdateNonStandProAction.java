package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.protoolsinfo.ProToolsInforService;

public class UpdateNonStandProAction extends DispatchAction {
  private ProToolsInforService proToolsInforService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");

    String resultStr = "{success : true, msg : '修改自制产品成功'}";
    String json = request.getParameter("content");

    try {
      JSONObject jsonObj = JSONObject.fromObject(json);
      JSONObject proTools = jsonObj.getJSONObject("proTools");
      JSONArray jsonArray = proTools.getJSONArray("children");
      String sycQuoToosInfor = null;
      if (jsonObj.containsKey("sycQuoToosInfor")) {
        sycQuoToosInfor = jsonObj.getString("sycQuoToosInfor");
      }
      proToolsInforService.updateNonStandPro(jsonArray, sycQuoToosInfor);
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '" + e.getMessage() + ", 修改失败'}";
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
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
