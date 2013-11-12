package com.tl.resource.business.exceltemplete.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;
import com.tl.resource.business.outStock.OutStockService;
import com.tl.resource.dao.pojo.TOutStockInfor;

public class ExportListDataOutStockHandler implements IExportListDataBusinessHandler {
  private OutStockService outStockService = (OutStockService) SystemInstance.getInstance().getBean("OutStockService");

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub
    para.put("currentUserId", para.get("userId"));
    PaginationSupport page = outStockService.findOutStockInfors(para, 0, Integer.MAX_VALUE);
    List list = page.getItems();
    List detail = new ArrayList();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TOutStockInfor infor = (TOutStockInfor) iterator.next();
      OutStockInforDto inf = outStockService.getOutStockInforDtoById(infor.getId());
      detail.add(inf);
    }
    Map<String, Object> rt = new HashMap<String, Object>();
    rt.put("list", detail);
    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return "outStockDetail";
  }
}
