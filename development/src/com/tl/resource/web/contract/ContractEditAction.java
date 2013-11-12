package com.tl.resource.web.contract;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.contract.ContractEditService;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.ContractProductDetailDto;
import com.tl.resource.business.dto.ContractProductSortDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;

public class ContractEditAction extends DispatchAction {
  private ContractEditService contractEditService = null;

  private BillsCodeDefService billsCodeDefService;

  public ActionForward consultGeneralQuo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    ContractInforDto conInfor = contractEditService.consultGeneralQuo(ids);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    conInfor.setUserId(user.getId());
    conInfor.setUserName(user.getTrueName());
    out.println(JSONObject.fromObject(conInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultYuDingQuo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    String contractTaxRateStr = request.getParameter("contractTaxRate");
    BigDecimal contractTaxRate = null;
    if (contractTaxRateStr != null) {
      contractTaxRate = new BigDecimal(contractTaxRateStr);
    }
    ContractInforDto conInfor = contractEditService.consultYuDingQuo(ids, contractTaxRate);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    conInfor.setUserId(user.getId());
    conInfor.setUserName(user.getTrueName());
    out.println(JSONObject.fromObject(conInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultProjectQuo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    ContractInforDto conInfor = contractEditService.consultProjectQuo(id);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    conInfor.setUserId(user.getId());
    conInfor.setUserName(user.getTrueName());
    out.println(JSONObject.fromObject(conInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String conctractInforJsonStr = request.getParameter("conctractInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject conctractInforJson = JSONObject.fromObject(conctractInforJsonStr);
    ContractInforDto dto = (ContractInforDto) JSONObject.toBean(conctractInforJson, ContractInforDto.class);
    dto.setEditDate(new Date());
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());
    dto.setContractCode(billsCodeDefService.getBillCode("03", null, dto.getCustomerCode(), "WW"));
    //System.out.println("getFinalMoney:" + dto.getFinalMoney());

    JSONArray productSortsArray = conctractInforJson.getJSONArray("contractProductSorts");
    List<ContractProductSortDto> conProSortsList = new ArrayList<ContractProductSortDto>();
    dto.setContractProductSorts(conProSortsList);
    for (Iterator iterator = productSortsArray.iterator(); iterator.hasNext();) {
      JSONObject sortjson = (JSONObject) iterator.next();
      ContractProductSortDto sortDto = (ContractProductSortDto) JSONObject.toBean(sortjson, ContractProductSortDto.class);
      JSONArray proDetailArray = sortjson.getJSONArray("conProductDetail");
      List<ContractProductDetailDto> conProductDetailList = converProDetail(proDetailArray);
      sortDto.setConProductDetail(conProductDetailList);
      conProSortsList.add(sortDto);
    }

    try {
      contractEditService.addContract(dto);
      out.println(true);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.println(false);
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String conctractInforJsonStr = request.getParameter("conctractInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject conctractInforJson = JSONObject.fromObject(conctractInforJsonStr);
    ContractInforDto dto = (ContractInforDto) JSONObject.toBean(conctractInforJson, ContractInforDto.class);
    //dto.setEditDate(new Date());
    dto.setUpdateDate(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());
    //System.out.println("getFinalMoney:" + dto.getFinalMoney());
    //System.out.println(dto.getContractProductSorts());
    JSONArray productSortsArray = conctractInforJson.getJSONArray("contractProductSorts");
    List<ContractProductSortDto> conProSortsList = new ArrayList<ContractProductSortDto>();
    dto.setContractProductSorts(conProSortsList);
    for (Iterator iterator = productSortsArray.iterator(); iterator.hasNext();) {
      JSONObject sortjson = (JSONObject) iterator.next();
      ContractProductSortDto sortDto = (ContractProductSortDto) JSONObject.toBean(sortjson, ContractProductSortDto.class);
      JSONArray proDetailArray = sortjson.getJSONArray("conProductDetail");
      List<ContractProductDetailDto> conProductDetailList = converProDetail(proDetailArray);
      sortDto.setConProductDetail(conProductDetailList);
      conProSortsList.add(sortDto);
    }

    try {
      contractEditService.updateContract(dto);
      out.println(true);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.println(false);
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      contractEditService.deleteContracts(ids[i]);
    }
    PrintWriter out = response.getWriter();
    out.println(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward runContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    String[] ids = request.getParameterValues("ids");
    PrintWriter out = response.getWriter();
    try {
      for (int i = 0; i < ids.length; i++) {
        contractEditService.runContract(ids[i], loginInfor);
      }
      out.print(true);
    } catch (Exception e) {
      out.print(e.getMessage());
      e.printStackTrace();
    }

    out.flush();
    out.close();
    return null;
  }

  private List<ContractProductDetailDto> converProDetail(JSONArray proDetailJsonArray) {
    // TODO Auto-generated method stub
    List<ContractProductDetailDto> list = new ArrayList<ContractProductDetailDto>();
    for (Iterator iterator = proDetailJsonArray.iterator(); iterator.hasNext();) {
      JSONObject contractProductDetailJsonDto = (JSONObject) iterator.next();
      ContractProductDetailDto productDetailDto = (ContractProductDetailDto) JSONObject.toBean(contractProductDetailJsonDto,
        ContractProductDetailDto.class);
      if ("".equals(productDetailDto.getProjectCode())) {
        productDetailDto.setProjectCode(null);
      }
      list.add(productDetailDto);
      if (productDetailDto.getLeaf() == 0) {//if no leaf
        productDetailDto.setChildren(converProDetail(contractProductDetailJsonDto.getJSONArray("children")));
      }
    }
    return list;
  }

  public ActionForward endContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    String ret = null;
    String id = null;
    for (int i = 0; i < ids.length; i++) {
      ret = contractEditService.endContract(ids[i]);
      id = ids[i];
      if (ret != null) {
        break;
      }
    }
    PrintWriter out = response.getWriter();
    out.println(new StringBuffer(100).append("{id:'").append(id).append("',message:'").append(ret == null ? "" : ret).append("'}"));
    out.flush();
    out.close();
    return null;
  }

  /**
   * 作废合同
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward voidContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      contractEditService.voidContract(ids[i]);
      break;
    }
    PrintWriter out = response.getWriter();
    //out.println(new StringBuffer(100).append("{id:'").append(id).append("',message:'").append(ret == null ? "" : ret).append("'}"));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteContractDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    String contractId = request.getParameter("contractId");
    BigDecimal overallRebate = request.getParameter("overallRebate") == null ? BigDecimal.ZERO
      : new BigDecimal(request.getParameter("overallRebate"));
    contractEditService.deleteContractDetail(contractId, overallRebate, ids);

    out.println("");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward unTreadQuo2Audited(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    contractEditService.unTreadQuo2Audited(ids);
    out.println("");
    out.flush();
    out.close();
    return null;
  }

  public ContractEditService getContractEditService() {
    return contractEditService;
  }

  public void setContractEditService(ContractEditService contractEditService) {
    this.contractEditService = contractEditService;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

}
