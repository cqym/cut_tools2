package com.tl.resource.business.exceltemplete.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.delivery.DeliveryEditService;
import com.tl.resource.business.dto.DeliveryInforDto;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;
import com.tl.resource.dao.pojo.TDeliveryInfor;

public class ExportListDataDeliveyHandler implements IExportListDataBusinessHandler {
  DeliveryEditService deliveryEditService = (DeliveryEditService) SystemInstance.getInstance().getBean("DeliveryEditService");

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub
    para.put("currentUserId", para.get("userId"));
    PaginationSupport page = deliveryEditService.findDeliveryInfors(para, 0, Integer.MAX_VALUE);
    List list = page.getItems();
    List detail = new ArrayList();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TDeliveryInfor infor = (TDeliveryInfor) iterator.next();
      DeliveryInforDto inf = deliveryEditService.getDeliveryInforById(infor.getId());
      detail.add(inf);
    }
    Map<String, Object> rt = new HashMap<String, Object>();
    rt.put("list", detail);
    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return "deliveryDetail";
  }
}
