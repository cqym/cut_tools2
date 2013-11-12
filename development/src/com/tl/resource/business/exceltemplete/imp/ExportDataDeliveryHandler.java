package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TDeliveryDetailDAO;
import com.tl.resource.dao.TDeliveryInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.pojo.TContractProductDetail;
import com.tl.resource.dao.pojo.TDeliveryDetail;
import com.tl.resource.dao.pojo.TDeliveryDetailExample;
import com.tl.resource.dao.pojo.TDeliveryInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class ExportDataDeliveryHandler implements IExportDataBusinessHandler {
  private TDeliveryInforDAO deliveryInforDAO = (TDeliveryInforDAO) SystemInstance.getInstance().getBean("TDeliveryInforDAO");

  private TDeliveryDetailDAO deliveryDetailDAO = (TDeliveryDetailDAO) SystemInstance.getInstance().getBean("TDeliveryDetailDAO");

  private TQuotationProductDetailDAO quoDetailDAO = (TQuotationProductDetailDAO) SystemInstance.getInstance().getBean("TQuotationProductDetailDAO");

  private TContractProductDetailDAO contractProductDetailDAO = (TContractProductDetailDAO) SystemInstance.getInstance().getBean(
    "contractProductDetailDAO");;

  TDeliveryInfor deliveryInfor;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> model = new HashMap<String, Object>();
    deliveryInfor = deliveryInforDAO.selectByPrimaryKey(id);
    model.put("deliveryInfor", deliveryInfor);
    TDeliveryDetailExample example = new TDeliveryDetailExample();
    example.createCriteria().andDeliveryInforIdEqualTo(id);
    example.setOrderByClause("pro_sort_name,serial_number");
    List<TDeliveryDetail> detail = deliveryDetailDAO.selectByExample(example);
    model.put("deliveryDetail", detail);
    if (deliveryInfor.getDeliveryType() == 0) {
      for (Iterator iterator = detail.iterator(); iterator.hasNext();) {
        TDeliveryDetail deliveryDetail = (TDeliveryDetail) iterator.next();
        TContractProductDetail cpo = contractProductDetailDAO.selectByPrimaryKey(deliveryDetail.getContractProductDetailId());
        deliveryDetail.setNetPrice(cpo.getNetPrice());
      }
    } else {
      for (Iterator iterator = detail.iterator(); iterator.hasNext();) {
        TDeliveryDetail deliveryDetail = (TDeliveryDetail) iterator.next();
        TQuotationProductDetail qpo = quoDetailDAO.selectByPrimaryKey(deliveryDetail.getContractProductDetailId());
        deliveryDetail.setNetPrice(qpo.getNetPrice());
      }
    }
    return model;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return deliveryInfor.getDeliveryCode();
  }

}
