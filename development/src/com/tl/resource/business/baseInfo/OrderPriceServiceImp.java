package com.tl.resource.business.baseInfo;

import java.util.Date;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TProductToolsInforDAO;
import com.tl.resource.dao.pojo.TOrderPriceHistory;
import com.tl.resource.dao.pojo.TProductToolsInfor;

public class OrderPriceServiceImp implements OrderPriceService {
  private TProductToolsInforDAO productToolsInforDAO;

  private TOrderPriceHistoryDAO orderPriceHistoryDAO;

  @Override
  public void addOrderPrice(OrderPriceHistoryDto dto, UserDto user) {
    // TODO Auto-generated method stub
    TProductToolsInfor tools = productToolsInforDAO.selectByPrimaryKey(dto.getProductToolsInforId());
    TOrderPriceHistory record = new TOrderPriceHistory();
    record.setId(GenerateSerial.getUUID());
    record.setEditDate(new Date());
    record.setBrandCode(tools.getBrandCode());
    record.setHistoryPrice(dto.getHistoryPrice());
    record.setLeaf(tools.getLeaf());
    record.setParentId(tools.getParentId());
    record.setProductCode(tools.getProductCode());
    record.setProductToolsInforId(dto.getProductToolsInforId());
    record.setSuppliersInforId(dto.getSuppliersInforId());
    record.setUserId(user.getId());
    record.setUserName(user.getUserName());
    orderPriceHistoryDAO.insert(record);
  }

  @Override
  public void deleteOrderPriceById(String id) {
    // TODO Auto-generated method stub
    orderPriceHistoryDAO.deleteByPrimaryKey(id);
  }

  @Override
  public void updateOrderPrice(OrderPriceHistoryDto dto) {
    // TODO Auto-generated method stub
    TOrderPriceHistory p = orderPriceHistoryDAO.selectByPrimaryKey(dto.getId());
    p.setHistoryPrice(dto.getHistoryPrice());
    orderPriceHistoryDAO.updateByPrimaryKey(p);
  }

  public TProductToolsInforDAO getProductToolsInforDAO() {
    return productToolsInforDAO;
  }

  public void setProductToolsInforDAO(TProductToolsInforDAO productToolsInforDAO) {
    this.productToolsInforDAO = productToolsInforDAO;
  }

  public TOrderPriceHistoryDAO getOrderPriceHistoryDAO() {
    return orderPriceHistoryDAO;
  }

  public void setOrderPriceHistoryDAO(TOrderPriceHistoryDAO orderPriceHistoryDAO) {
    this.orderPriceHistoryDAO = orderPriceHistoryDAO;
  }

}
