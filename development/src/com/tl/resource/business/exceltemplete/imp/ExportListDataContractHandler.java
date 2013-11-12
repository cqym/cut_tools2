package com.tl.resource.business.exceltemplete.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.contract.ContractEditService;
import com.tl.resource.business.contract.ContractViewService;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;

public class ExportListDataContractHandler implements IExportListDataBusinessHandler {
  private ContractEditService contractEditService = (ContractEditService) SystemInstance.getInstance().getBean("contractEditService");

  private ContractViewService contractViewService = (ContractViewService) SystemInstance.getInstance().getBean("contractViewService");

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub
    para.put("currUserId", para.get("userId"));
    PaginationSupport inforPage = contractViewService.findContractViewPanelInfors(para, 0, Integer.MAX_VALUE);
    Map<String, Object> rt = new HashMap<String, Object>();
    if (!para.containsKey("noDetail")) {
      List inforList = inforPage.getItems();
      List detail = new ArrayList();
      for (Iterator iterator = inforList.iterator(); iterator.hasNext();) {
        ContractInforDto conInfor = (ContractInforDto) iterator.next();
        detail.add(contractEditService.getContractInforById(conInfor.getId()));
      }
      rt.put("list", detail);
    } else {
      rt.put("list", inforPage.getItems());
    }

    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return "contractDetail";
  }

}
