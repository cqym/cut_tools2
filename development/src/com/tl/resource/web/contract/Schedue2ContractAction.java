package com.tl.resource.web.contract;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.tl.resource.business.contract.ContractEditService;
import com.tl.resource.business.dto.ContractDetailDto;

public class Schedue2ContractAction extends DispatchAction {

  private ContractEditService contractEditService = null;

  public ActionForward findSchedue2ConInfors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    Map<String, String> mparams = new HashMap<String, String>();
    mparams.put("quoDetailId", request.getParameter("quoDetailId"));

    List list = contractEditService.findSchedue2ConInfors(mparams);
    String rt = JSONArray.fromObject(list).toString();
    PrintWriter out = response.getWriter();
    out.println("{items:" + rt + ",totalCount:" + list.size() + "}");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateShedQuo2ConDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String conDetailJsonStr = request.getParameter("conDetailJsonStr");
    JSONArray conctractInforArray = JSONArray.fromObject(conDetailJsonStr);
    List<ContractDetailDto> list = new ArrayList<ContractDetailDto>();
    for (Iterator iterator = conctractInforArray.iterator(); iterator.hasNext();) {
      JSONObject obj = (JSONObject) iterator.next();
      ContractDetailDto dto = (ContractDetailDto) JSONObject.toBean(obj, ContractDetailDto.class);
      list.add(dto);
    }
    contractEditService.updateShedQuo2ConDetail(list);
    //String rt = JSONObject.fromObject("").toString();
    out.println("{success:true}");
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

}
