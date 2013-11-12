package com.tl.resource.business.trySelfOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderPriceHistory;
import com.tl.resource.dao.pojo.TSalesPriceHistory;

public class TrySelfOrderServiceImpl implements TrySelfOrderService {

  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TOrderPriceHistoryDAO orderPriceHistoryDao;

  private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;

  @Override
  public String cancelAudit(String id) {
    // TODO Auto-generated method stub
    orderInforDao.cancelAudit(id);
    return null;
  }

  @Override
  public String endAudit(String id) {
    // TODO Auto-generated method stub
    orderInforDao.endAudit(id);
    OrderInfoDto orderInfor = orderInforDao.getExcelOrderInfor(id);
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(id);
    List<TOrderDetail> orderDetailList = orderDetailDao.selectByExample(example);
    for (TOrderDetail orderDetail : orderDetailList) {
      orderPriceHistoryDao.insertSelective(changeModel(orderDetail, orderInfor));
    }
    return null;
  }

  public TOrderPriceHistory changeModel(TOrderDetail orderDetail, OrderInfoDto orderInfor) {

    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("toolsId", orderDetail.getToolsId());
    List<TSalesPriceHistory> list = tsalesPriceHistoryDAO.getHistoryMarketPrice(parmMap);

    TOrderPriceHistory orderPirceHistory = new TOrderPriceHistory();
    orderPirceHistory.setId(GenerateSerial.getUUID());
    orderPirceHistory.setSuppliersInforId(orderInfor.getSupplierId());
    orderPirceHistory.setProductToolsInforId(orderDetail.getToolsId());
    orderPirceHistory.setLeaf(1);
    orderPirceHistory.setParentId("root");
    orderPirceHistory.setProductCode(orderDetail.getProductCode());
    orderPirceHistory.setHistoryPrice(orderDetail.getPrice());
    orderPirceHistory.setBrandCode(orderDetail.getBrandCode());
    orderPirceHistory.setEditDate(new Date());
    orderPirceHistory.setUserId(orderInfor.getUserId());
    orderPirceHistory.setUserName(orderInfor.getUserName());
    orderPirceHistory.setStockPriceDate(orderInfor.getEditDate());
    if (list.size() > 0) {
      orderPirceHistory.setHistoryMarketPrice(list.get(0).getHistoryPrice());
    }
    return orderPirceHistory;
  }

  @Override
  public TOrderInfor getOrderInforById(String id) {
    // TODO Auto-generated method stub
    return orderInforDao.selectByPrimaryKey(id);
  }

  @Override
  public String submitAudit(String id) {
    // TODO Auto-generated method stub
    orderInforDao.submitAudit(id);
    return null;
  }

  public TOrderInforDAO getOrderInforDao() {
    return orderInforDao;
  }

  public void setOrderInforDao(TOrderInforDAO orderInforDao) {
    this.orderInforDao = orderInforDao;
  }

  public TOrderDetailDAO getOrderDetailDao() {
    return orderDetailDao;
  }

  public void setOrderDetailDao(TOrderDetailDAO orderDetailDao) {
    this.orderDetailDao = orderDetailDao;
  }

  public TOrderPriceHistoryDAO getOrderPriceHistoryDao() {
    return orderPriceHistoryDao;
  }

  public void setOrderPriceHistoryDao(TOrderPriceHistoryDAO orderPriceHistoryDao) {
    this.orderPriceHistoryDao = orderPriceHistoryDao;
  }

  public TSalesPriceHistoryDAO getTsalesPriceHistoryDAO() {
    return tsalesPriceHistoryDAO;
  }

  public void setTsalesPriceHistoryDAO(TSalesPriceHistoryDAO tsalesPriceHistoryDAO) {
    this.tsalesPriceHistoryDAO = tsalesPriceHistoryDAO;
  }

}
