package com.tl.resource.web.quotation.generalquo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.QuoCodeTreeDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.business.quotation.generalquo.GeneralQuoToExcel;
import com.tl.resource.business.quotation.generalquo.QuoListToExcel;
import com.tl.resource.dao.pojo.TExchangeRate;

public class ExcelAction extends DispatchAction {
  private GeneralQuoService generalQuoService;

  private GeneralQuoToExcel excelService;

  private QuoListToExcel quoListToExcel;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String method = request.getParameter("method");

    if ("toExcel".equals(method)) {
      return toExcel(mapping, form, request, response);
    } else if ("getCurrencyByName".equals(method)) {
      return getCurrencyByName(mapping, form, request, response);
    } else if ("quoListToExcel".equals(method)) {
      return quoListToExcel(mapping, form, request, response);
    } else if ("getOrderPrice4Quo".equals(method)) {
      return getOrderPrice4Quo(mapping, form, request, response);
    } else if ("getQuoInfoByImpQuoId".equals(method)) {
      return getQuoInfoByImpQuoId(mapping, form, request, response);
    } else if ("getQuoInfo4Export".equals(method)) {
      return getQuoInfo4Export(mapping, form, request, response);
    } else if ("getQuoInfo4TestCut".equals(method)) {
      return getQuoInfo4TestCut(mapping, form, request, response);
    } else {
      return null;
    }
  }

  //试刀交货单
  private ActionForward getQuoInfo4TestCut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String quoId = request.getParameter("quoId");

    List<QuoCodeTreeDto> list = generalQuoService.getDeliveryCodeByQuoId(quoId);

    List<QuotationDto> list2 = new ArrayList<QuotationDto>();
    for (QuoCodeTreeDto dto : list) {
      QuotationDto quoDto = new QuotationDto();
      quoDto.setQuotationCode(dto.getCode());
      list2.add(quoDto);
    }

    String jsonStr = JSONArray.fromObject(list2).toString();
    PrintWriter out = response.getWriter();
    out.write(jsonStr);
    out.flush();
    out.close();
    return null;
  }

  //预订报价单导出报价单调用
  private ActionForward getQuoInfo4Export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String quoId = request.getParameter("quoId");

    String[] array = quoId.split(",");
    ArrayList<String> arrayList = new ArrayList<String>();
    for (String id : array) {
      arrayList.add(id);
    }

    List<QuotationDto> list = generalQuoService.getQuoInfoByImpQuoId(arrayList);
    List<QuoCodeTreeDto> codeList = new ArrayList<QuoCodeTreeDto>();
    for (QuotationDto quoDto : list) {
      //报价编号
      String quoCode = quoDto.getQuotationCode();
      QuoCodeTreeDto rootNode = new QuoCodeTreeDto();
      rootNode.setCode(quoCode);
      rootNode.setLeaf(0);

      //合同编号
      String contractCode = quoDto.getContractCode();
      if (!"".equals(contractCode) && contractCode != null) {
        QuoCodeTreeDto contractNode = new QuoCodeTreeDto();
        contractNode.setCode(contractCode);
        List<QuoCodeTreeDto> deiveryCodelist = generalQuoService.getDeliveryCode(quoDto.getId());
        contractNode.setChildren(deiveryCodelist);
        contractNode.setLeaf(0);

        List<QuoCodeTreeDto> contractCodeList = new ArrayList<QuoCodeTreeDto>();
        contractCodeList.add(contractNode);

        rootNode.setChildren(contractCodeList);
      }

      codeList.add(rootNode);
    }

    String str = JSONArray.fromObject(codeList).toString();

    PrintWriter out = response.getWriter();
    out.write(str);
    out.flush();
    out.close();
    return null;
  }

  //正式报价单信息
  private ActionForward getQuoInfoByImpQuoId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String quoId = request.getParameter("quoId");

    String[] array = quoId.split(",");
    ArrayList<String> arrayList = new ArrayList<String>();
    for (String id : array) {
      arrayList.add(id);
    }
    List<QuotationDto> list = null;

    list = generalQuoService.getQuoInfoByImpQuoId(arrayList);

    String jsonStr = JSONArray.fromObject(list).toString();
    PrintWriter out = response.getWriter();
    out.write(jsonStr);
    out.flush();
    out.close();
    return null;
  }

  //获取报价单产品中净价大于采购价格的产品
  private ActionForward getOrderPrice4Quo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String quoId = request.getParameter("quoId");
    List<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();

    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("quoId", quoId);

    if (quoId != null && !"".equals(quoId)) {
      list = this.generalQuoService.getOrderPrice4Quo(parmMap);
    }

    StringBuffer resultStr = new StringBuffer();

    int i = 1;
    for (QuotationDetailDto dto : list) {
      if (dto.getProSortName() != null && !"".equals(dto.getProSortName())) {
        resultStr.append("工序名称: ").append(dto.getProSortName()).append(",");
      }
      int mod = i % 3;
      if (mod == 0) {
        resultStr.append("序号: ").append(dto.getSerialNumber()).append("<br>");
      } else {
        resultStr.append("序号: ").append(dto.getSerialNumber());
        if (i == list.size())
          continue;
        resultStr.append("; ");
      }
      i++;
    }

    String jsonStr = JSONArray.fromObject(list).toString();
    PrintWriter out = response.getWriter();
    out.write(resultStr.toString());
    out.flush();
    out.close();
    return null;
  }

  //导出全部报价单
  private ActionForward quoListToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    String quotationType = request.getParameter("quotationType");
    String searchStr = request.getParameter("searchStr");

    JSONObject searchJson = null;
    String quotationCode = "";
    String editorName = "";
    String status = null;
    String customerName = "";
    String beginDate = "";
    String endDate = "";
    String userName = "";
    if (searchStr != null && !"null".equals(searchStr)) {
      searchJson = JSONObject.fromObject(searchStr);
      if (searchJson != null) {
        JSONObject search = searchJson.getJSONObject("data");
        quotationCode = search.getString("quotationCode");
        String s = search.getString("status");
        if ("全部".equals(s) || "".equals(s)) {
          status = null;
        } else {
          status = s;
        }
        editorName = search.getString("editorName");
        customerName = search.getString("customerName");
        beginDate = search.getString("beginDate");
        endDate = search.getString("endDate");
        userName = search.getString("userName");
      }
    }

    UserDto userDto = null;
    String userId = "";
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }
    if (userDto != null) {
      userId = userDto.getId();
    }
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("userId", userId);
    parmMap.put("quotationType", quotationType);
    parmMap.put("resourceType", 1);

    parmMap.put("quotationCode", quotationCode);
    parmMap.put("status", status);
    parmMap.put("editorName", editorName);
    parmMap.put("customerName", customerName);
    parmMap.put("beginDate", beginDate);
    parmMap.put("endDate", endDate);
    parmMap.put("userName", userName);

    int total = generalQuoService.getQuotaionTotal(parmMap);

    response.setHeader("Content-Disposition", "attachment; filename=quotation-" + total + ".xls");
    parmMap.put("start", 0);
    parmMap.put("limit", total);
    List<QuotationDto> list = generalQuoService.getQuotationByPage(parmMap);
    String realPath = request.getSession().getServletContext().getRealPath("");
    quoListToExcel.setRealPath(realPath);
    quoListToExcel.exportExcel(response.getOutputStream(), list);
    return null;
  }

  private ActionForward getCurrencyByName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String currencyName = request.getParameter("currencyName");
    String currencyId = request.getParameter("currencyId");
    TExchangeRate exchangeRate = null;
    if (!"".equals(currencyId) && null != currencyId) {
      exchangeRate = generalQuoService.getCurrencyById(currencyId);
    } else {
      exchangeRate = generalQuoService.getCurrencyByName(currencyName);
    }
    String jsonStr = JSONObject.fromObject(exchangeRate).toString();

    String resultStr = "{currency : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(jsonStr);
    out.flush();
    out.close();

    return null;
  }

  //导出Excel
  private ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String quoId = request.getParameter("quoId");

    QuotationDto quoDto = generalQuoService.getGeneralQuoInforById(quoId);
    List<QuotationDetailDto> list = generalQuoService.getQuoDetail(quoId);
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=quotation-" + quoDto.getQuotationCode() + ".xls");
    String logoPath = request.getSession().getServletContext().getRealPath("");
    excelService.setLogoPath(logoPath);
    excelService.exportExcel(response.getOutputStream(), list, quoDto);
    return null;
  }

  public GeneralQuoService getGeneralQuoService() {
    return generalQuoService;
  }

  public void setGeneralQuoService(GeneralQuoService generalQuoService) {
    this.generalQuoService = generalQuoService;
  }

  public GeneralQuoToExcel getExcelService() {
    return excelService;
  }

  public void setExcelService(GeneralQuoToExcel excelService) {
    this.excelService = excelService;
  }

  public QuoListToExcel getQuoListToExcel() {
    return quoListToExcel;
  }

  public void setQuoListToExcel(QuoListToExcel quoListToExcel) {
    this.quoListToExcel = quoListToExcel;
  }

}
