package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOutStockDetailDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class ExportDataOutStockHandler implements IExportDataBusinessHandler {
  private TOutStockInforDAO outStockInforDAO = (TOutStockInforDAO) SystemInstance.getInstance().getBean("TOutStockInforDAO");

  private TOutStockDetailDAO outStockDetailDAO = (TOutStockDetailDAO) SystemInstance.getInstance().getBean("TOutStockDetailDAO");

  private TOrderDetailDAO orderDetailDao = (TOrderDetailDAO) SystemInstance.getInstance().getBean("TOrderDetailDAOImpl");

  private TQuotationProductDetailDAO quotationProductDetailDAO = (TQuotationProductDetailDAO) SystemInstance.getInstance().getBean(
    "TQuotationProductDetailDAO");

  private TOutStockInfor outStockInfor;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> model = new HashMap<String, Object>();
    outStockInfor = outStockInforDAO.selectByPrimaryKey(id);
    model.put("outStockInfor", outStockInfor);
    TOutStockDetail po = new TOutStockDetail();
    po.setOutStockInforId(outStockInfor.getId());
    List<com.tl.resource.dao.pojo.TOutStockDetail> list = outStockDetailDAO.selectDetailHasReserveInforByRecord(po);
    TOrderDetailExample odexp = new TOrderDetailExample();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TOutStockDetail outStockDetail = (TOutStockDetail) iterator.next();

      String productId = outStockDetail.getContractProductDetailId();
      odexp.clear();
      odexp.createCriteria().andContractProductDetailIdEqualTo(productId);
      List<TOrderDetail> orderDetails = orderDetailDao.selectByExample(odexp);
      if (orderDetails.size() > 0) {
        outStockDetail.setOrderPrice(orderDetails.get(0).getPrice());
        if (productId != null && productId.length() > 32) {
          productId = productId.substring(0, 32);
        }
        TQuotationProductDetail qpdetailpo = quotationProductDetailDAO.selectByPrimaryKey(productId);
        if (qpdetailpo != null) {
          outStockDetail.setSalePrice(qpdetailpo.getNetPrice());
        }
      }
    }
    model.put("outStockDetail", list);
    model.put("outStockTypeName", getOutStockType(outStockInfor.getOutStockType()));

    return model;
  }

  private String getOutStockType(Integer outStockType) {//0直接出库，1合同出库(提取库存)，2报价单出库，3材料出库,4合同出库(采购)
    String typeName = null;
    switch (outStockType) {
    case 0:
      typeName = "直接出库";
      break;
    case 1:
      typeName = "合同出库(提取库存)";
      break;
    case 2:
      typeName = "预订出库(采购)";
      break;
    case 3:
      typeName = "材料出库";
      break;
    case 4:
      typeName = "合同出库(采购)";
      break;
    case 5:
      typeName = "预订出库(提取库存)";
      break;
    case 6:
      typeName = "试刀出库(提取库存)";
      break;
    case 7:
      typeName = "试到出库(采购)";
      break;
    default:
      typeName = "";
      break;
    }
    return typeName;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return outStockInfor.getOutStockCode();
  }

}
