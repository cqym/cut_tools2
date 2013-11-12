package com.tl.resource.web.quotation.generalquo;

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
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;

public class ListAction extends Action {
  private GeneralQuoService generalQuoService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String searchStr = request.getParameter("searchStr");
    String quotationType = request.getParameter("quotationType");
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    String userId = "";
    if (userDto != null) {
      userId = userDto.getId();
    }

    JSONObject searchJson = null;
    String quotationCode = "";
    String editorName = "";
    String status = null;
    String customerName = "";
    String beginDate = "";
    String endDate = "";
    String userName = "";
    String memo = "";
    String year = null;
    String transferContract = null;
    String yuDingWeiZhuanHeTong = request.getParameter("yuDingWeiZhuanHeTong");
    if (searchStr != null && !"null".equals(searchStr)) {
      searchJson = JSONObject.fromObject(searchStr);
      if (searchJson != null) {
        JSONObject search = searchJson.getJSONObject("data");
        quotationCode = search.has("quotationCode") ? search.getString("quotationCode") : null;
        String s = search.has("status") ? search.getString("status") : null;
        if ("全部".equals(s) || "".equals(s)) {
          status = null;
        } else {
          status = s;
        }
        String st = search.has("transferContract") ? search.getString("transferContract") : null;
        if ("全部".equals(st) || "".equals(st)) {
          transferContract = null;
        } else {
          transferContract = st;
        }
        editorName = search.has("editorName") ? search.getString("editorName") : null;
        customerName = search.has("customerName") ? search.getString("customerName") : null;
        beginDate = search.has("beginDate") ? search.getString("beginDate") : null;
        endDate = search.has("endDate") ? search.getString("endDate") : null;
        userName = search.has("userName") ? search.getString("userName") : null;
        memo = search.has("memo") ? search.getString("memo") : null;
        year = search.has("year") ? search.getString("year") : null;
      }
    }

    String start = request.getParameter("start");
    String limit = request.getParameter("limit");

    Map<String, Object> parmMap = new HashMap<String, Object>();

    parmMap.put("quotationCode", quotationCode);
    parmMap.put("status", status);
    parmMap.put("editorName", editorName);
    parmMap.put("customerName", customerName);
    parmMap.put("beginDate", beginDate);
    parmMap.put("endDate", endDate);
    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));
    parmMap.put("quotationType", quotationType);
    parmMap.put("userId", userId);
    parmMap.put("resourceType", 1);
    parmMap.put("userName", userName);
    parmMap.put("memo", memo);
    parmMap.put("year", year);
    parmMap.put("transferContract", transferContract);
    parmMap.put("yuDingWeiZhuanHeTong", yuDingWeiZhuanHeTong);
    int total = generalQuoService.getQuotaionTotal(parmMap);

    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editTimeStr".equals(sort)) {
        sort = "editDate";
      }
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    List<QuotationDto> list = generalQuoService.getQuotationByPage(parmMap);

    String jsonStr = JSONArray.fromObject(list).toString();

    String resultStr = "{totalProperty : " + total + ", quoList : " + jsonStr + "}";

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
