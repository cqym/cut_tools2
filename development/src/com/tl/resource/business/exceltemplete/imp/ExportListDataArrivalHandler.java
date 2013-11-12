package com.tl.resource.business.exceltemplete.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;
import com.tl.resource.dao.TProductArrivalDetailDAO;
import com.tl.resource.dao.TProductArrivalInforDAO;
import com.tl.resource.dao.pojo.TProductArrivalInfor;

public class ExportListDataArrivalHandler implements IExportListDataBusinessHandler {
  private TProductArrivalInforDAO arrInfoDao = (TProductArrivalInforDAO) SystemInstance.getInstance().getBean("TProductArrivalInforDAO");

  private TProductArrivalDetailDAO arrProductDao = (TProductArrivalDetailDAO) SystemInstance.getInstance().getBean("TProductArrivalDetail");

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub

    List<TProductArrivalInfor> inforList = arrInfoDao.getArrivalInfoBySearch(para);
    List detail = new ArrayList();
    for (Iterator iterator = inforList.iterator(); iterator.hasNext();) {
      TProductArrivalInfor proArrInfor = (TProductArrivalInfor) iterator.next();
      proArrInfor.setArrivalProductDtos(arrProductDao.getArrivalDetailByView(proArrInfor.getId()));
      detail.add(proArrInfor);
    }
    Map<String, Object> rt = new HashMap<String, Object>();
    rt.put("list", detail);
    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return "arrivalDetail";
  }

}
