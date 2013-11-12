package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;

public class ExportListDataQuotationHandler implements IExportListDataBusinessHandler {
  private GeneralQuoService generalQuoService = (GeneralQuoService) SystemInstance.getInstance().getBean("GeneralQuoService");

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub
    para.put("start", 0);
    para.put("limit", Integer.MAX_VALUE);
    para.put("resourceType", 1);
    if ("全部".equals(para.get("transferContract"))) {
      para.remove("transferContract");
    }
    List inforList = generalQuoService.getQuotationByPage(para);
    for (Iterator iterator = inforList.iterator(); iterator.hasNext();) {
      QuotationDto quoInfor = (QuotationDto) iterator.next();
      quoInfor.setDetail(generalQuoService.getQuoDetail(quoInfor.getId()));
    }
    Map<String, Object> rt = new HashMap<String, Object>();
    rt.put("list", inforList);
    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return "quotationDetail";
  }

}
