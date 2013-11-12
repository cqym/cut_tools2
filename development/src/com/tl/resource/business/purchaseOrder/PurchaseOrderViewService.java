package com.tl.resource.business.purchaseOrder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

import com.tl.common.util.ObjectUtil;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractInforExample;
import com.tl.resource.dao.pojo.TOrderDetailExample;

public class PurchaseOrderViewService {
  private static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0");
  static {
    BigDecimalConverter bd = new BigDecimalConverter(BIGDECIMAL_ZERO);
    ConvertUtils.register(bd, java.math.BigDecimal.class);
  }

  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TContractInforDAO contractInforDao;

  public OrderInfoDto getPurchaseOrderById(String id) {

    OrderInfoDto orderDto = new OrderInfoDto();
    ObjectUtil.copyObjectPropertys(orderInforDao.selectByPrimaryKey(id), orderDto);
    initContractQuotationInfor(orderDto);
    orderDto.setTaxMoney(orderDto.getProductMoney().multiply(orderDto.getTaxRate()));
    orderDto.setOrderDetail(getOrderDetailByOrderInforId(id, orderDto.getOrderType()));
    return orderDto;
  }

  private void initContractQuotationInfor(OrderInfoDto orderDto) {
    // TODO Auto-generated method stub
    if (orderDto.getContractCode() == null) {
      return;
    }
    TContractInforExample example = new TContractInforExample();
    example.createCriteria().andContractCodeEqualTo(orderDto.getContractCode());
    List<TContractInfor> list = contractInforDao.selectByExample(example);
    if (list.size() > 0) {
      orderDto.setContractDeliveryAddress(list.get(0).getDeliveryAddressType());
      orderDto.setContractTrafficMode(list.get(0).getTrafficMode());
      orderDto.setContractUrgentLevel(list.get(0).getUrgentLevel());
    }

  }

  public List<OrderDetialsDto> getOrderDetailByOrderInforId(String id, Integer orderType) {
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(id);
    example.setOrderByClause("contract_project_sort_id,serial_number");

    List<OrderDetialsDto> detail = null;
    try {
      if (OrderInfoDto.ORDER_TYPE_CONTRACT.equals(orderType) || OrderInfoDto.ORDER_TYPE_CONTRACT_SELF.equals(orderType)) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("orderId", id);
        detail = orderDetailDao.getContractOrderDetailsListHasFileHasRemainAmount(parmMap);
      } else if (OrderInfoDto.ORDER_TYPE_SCHEDLE.equals(orderType) || OrderInfoDto.ORDER_TYPE_SCHEDLE_SELF.equals(orderType)
        || OrderInfoDto.ORDER_TYPE_TRY.equals(orderType) || OrderInfoDto.ORDER_TYPE_TRY_SELF.equals(orderType)) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("orderId", id);
        detail = orderDetailDao.getSOrderDetailList(parmMap);
      } else {
        detail = ObjectUtil.copyListElementsPropertys(orderDetailDao.selectByExample(example), OrderDetialsDto.class);
      }
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return detail;
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

  public TContractInforDAO getContractInforDao() {
    return contractInforDao;
  }

  public void setContractInforDao(TContractInforDAO contractInforDao) {
    this.contractInforDao = contractInforDao;
  }

}
