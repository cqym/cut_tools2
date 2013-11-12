package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.quotation.projectquo.ProjectQuoService;

public class ListAction extends Action {
  private ProjectQuoService projectQuoService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");

    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    String userId = "";
    if (userDto != null) {
      userId = userDto.getId();
    }

    Map<String, Object> parmMap = new HashMap<String, Object>();

    String searchStr = request.getParameter("searchStr");
    if (searchStr != null) {
      JSONObject searchJson = JSONObject.fromObject(searchStr);
      parmMap.put("quotationCode", searchJson.has("quotationCode") ? searchJson.getString("quotationCode") : null);
      parmMap.put("editorName", searchJson.has("editorName") ? searchJson.getString("editorName") : null);
      parmMap.put("customerName", searchJson.has("customerName") ? searchJson.getString("customerName") : null);
      parmMap.put("userName", searchJson.has("userName") ? searchJson.getString("userName") : null);
      if (searchJson.has("status") && (searchJson.getString("status") != null) && !"全部".equals(searchJson.getString("status"))) {
        parmMap.put("status", searchJson.getString("status"));
      }
      parmMap.put("beginDate", searchJson.has("beginDate") ? searchJson.getString("beginDate") : null);
      parmMap.put("endDate", searchJson.has("endDate") ? searchJson.getString("endDate") : null);
      parmMap.put("year", searchJson.has("year") ? searchJson.getString("year") : null);
      if (searchJson.has("transferContract") && (searchJson.getString("transferContract") != null)
        && !"全部".equals(searchJson.getString("transferContract"))) {
        parmMap.put("transferContract", searchJson.getString("transferContract"));
      }

    }

    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));
    parmMap.put("quotationType", 1);
    parmMap.put("userId", userId);
    parmMap.put("resourceType", 1);

    int total = projectQuoService.getQuotaionTotal(parmMap);

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    List<QuotationDto> list = projectQuoService.getQuotationByPage(parmMap);
    String jsonStr = JSONArray.fromObject(list).toString();
    String resultStr = "{totalProperty : " + total + ", quoList : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  public ProjectQuoService getProjectQuoService() {
    return projectQuoService;
  }

  public void setProjectQuoService(ProjectQuoService projectQuoService) {
    this.projectQuoService = projectQuoService;
  }

}
