package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.protoolsinfo.ProToolsInforService;

/**
 * 增加非标品
 * @author Administrator
 *
 */
public class AddNonStandProAction extends Action {
  private ProToolsInforService proToolsInforService;

  //String parentId = "";
  public ProToolsInforService getProToolsInforService() {
    return proToolsInforService;
  }

  public void setProToolsInforService(ProToolsInforService proToolsInforService) {
    this.proToolsInforService = proToolsInforService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    String json = request.getParameter("content");
    String resultStr = "{success : true, msg : '添加自制产品成功'}";
    //构造JSON对象
    try {
      JSONObject jsonObj = JSONObject.fromObject(json);
      JSONObject proToolss = jsonObj.getJSONObject("proTools");

      if (proToolss.has("children") && proToolss.getJSONArray("children").isArray()) {
        JSONArray array = proToolss.getJSONArray("children");
        proToolsInforService.addNonStandPro(array);
      }

    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '添加自制产品失败，请重试'}";
    }

    response.setContentType("text/html;charset=utf-8");

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }
}
