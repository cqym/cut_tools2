package com.tl.resource.business.reserveOrder;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.CurrencyDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TProductToolsInforDAO;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderInforExample;
import com.tl.resource.dao.pojo.TOrderPriceHistory;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TProductToolsInforExample;
import com.tl.resource.dao.pojo.TSalesPriceHistory;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class ReserveOrderServiceImpl implements ReserveOrderService {

  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TSuppliersInforDAO suppliersInforDao;

  private TProductToolsInforDAO proToolsInforDao;

  private TExchangeRateDAO exchangeRateDao;

  private TOrderPriceHistoryDAO orderPriceHistoryDao;

  private TAccessoriesDAO accessoriesDAO;

  private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;

  @Override
  public List<TOrderInfor> getReserveOrderList(int status) {
    // TODO Auto-generated method stub
    TOrderInforExample order = new TOrderInforExample();
    order.or(order.createCriteria().andStatusEqualTo(status));
    return orderInforDao.selectByExample(order);
  }

  @Override
  public List<TOrderInfor> getReserveOrderList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderInforDao.getOrderList(parmMap);
  }

  @Override
  public int getOrderTotal(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderInforDao.getOrderTotal(parmMap);
  }

  @Override
  public int getOrderDetailsListCount(String orderId) {
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(orderId);
    return orderDetailDao.countByExample(example);
  }

  @Override
  public List<TSuppliersInfor> getSuppliersInforList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    //		TSuppliersInforExample suppliersInfor = new TSuppliersInforExample();
    //		suppliersInfor.setOrderByClause("supplier_short_name");
    //		return suppliersInforDao.selectByExample(suppliersInfor);
    return suppliersInforDao.getSuppliersByPage(parmMap);
  }

  @Override
  public int getSuppliersInforListCount(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    Integer in = suppliersInforDao.getSuppliersTotal(parmMap);
    return in.intValue();
  }

  @Override
  public OrderDetialsDto insertOrderDetail(OrderDetialsDto orderDetail) {
    return orderDetailDao.insertOrderDetail(orderDetail);
  }

  @Override
  public void deleteOrder(String[] orderId) {
    // TODO Auto-generated method stub
    if (orderId != null && orderId.length > 0) {
      for (int i = 0; i < orderId.length; i++) {
        orderDetailDao.deleteByOrderId(orderId[i]);
        orderInforDao.deleteByPrimaryKey(orderId[i]);
      }
    }

  }

  @Override
  public TOrderInfor insertOrder(TOrderInfor order) {
    return orderInforDao.insertOrder(order);
  }

  @Override
  public void deleteOrderDetail(String orderId) {
    // TODO Auto-generated method stub
    orderDetailDao.deleteByOrderId(orderId);
  }

  @Override
  public void deleteOrderDetailById(String[] id, TOrderInfor orderInfor) {
    // TODO Auto-generated method stub
    for (int i = 0; i < id.length; i++) {
      orderDetailDao.deleteByPrimaryKey(id[i]);
    }
    orderInforDao.updateByPrimaryKeySelective(orderInfor);
  }

  @Override
  public List<TreeDto> getOrderProToolsList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return proToolsInforDao.getOrderProToolsList(parmMap);
  }

  @Override
  public int getProToolsTotal(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return proToolsInforDao.getOrderProToolsTotal(parmMap);
  }

  @Override
  public void addOrder(TOrderInfor order, JSONArray orderDetails) {
    if (order != null) {
      orderInforDao.insertOrder(order);

      Iterator<JSONObject> iterator = orderDetails.iterator();
      ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
      int i = 0;
      while (iterator.hasNext()) {
        JSONObject details = iterator.next();
        OrderDetialsDto dto = null;
        try {
          dto = (OrderDetialsDto) JSONObject.toBean(details, OrderDetialsDto.class);
          if (details.getString("leaf").equals("true")) {
            dto.setLeaf(1);
          } else if (details.getString("leaf").equals("false")) {
            dto.setLeaf(0);
          }
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        dto.setSerialNumber(BigDecimal.valueOf(Double.valueOf(String.valueOf(i))));
        dto.setId(GenerateSerial.getUUID());
        dto.setParentToolsId("root");
        dto.setStockOrderInforId(order.getId());
        orderDetailDao.insertOrderDetail(dto);
        i++;
      }
    }

  }

  private ArrayList<OrderDetialsDto> addChildrenorderDe(JSONObject orderDe, OrderDetialsDto dto, String orderId) {
    JSONArray arr = orderDe.getJSONArray("children");
    ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
    for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {
      JSONObject top = (JSONObject) iterator2.next();
      String nodeId = GenerateSerial.getUUID();
      OrderDetialsDto po2 = (OrderDetialsDto) JSONObject.toBean(top, OrderDetialsDto.class);
      po2.setParentToolsId(dto.getId());
      po2.setId(nodeId);
      po2.setStockOrderInforId(orderId);
      list.add(po2);
      JSONArray arr2 = null;
      try {
        arr2 = top.getJSONArray("children");

      } catch (Exception e) {
        po2.setLeaf(1);
      }
      orderDetailDao.insertOrderDetail(po2);
      if (arr2 != null && arr2.size() > 0) {
        po2.setChildren(addChildrenorderDe(top, po2, orderId));
      } else {
        //System.out.println("ddd:");
      }
    }
    dto.setChildren(list);
    return list;
  }

  @Override
  public void updateOrder(TOrderInfor order, JSONArray orderDetails, String[] ids) {
    // TODO Auto-generated method stub
    if (order != null) {
      orderInforDao.updateByPrimaryKeySelective(order);

      Iterator<JSONObject> iterator = orderDetails.iterator();
      ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
      int i = 0;
      while (iterator.hasNext()) {
        JSONObject details = iterator.next();
        OrderDetialsDto dto = null;
        try {
          dto = (OrderDetialsDto) JSONObject.toBean(details, OrderDetialsDto.class);
          if (details.getString("leaf").equals("true")) {
            dto.setLeaf(1);
          } else if (details.getString("leaf").equals("false")) {
            dto.setLeaf(0);
          }
          dto.setSerialNumber(BigDecimal.valueOf(Double.valueOf(String.valueOf(i))));
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        if (dto.getId() != null && dto.getId().length() == 32) {
          TOrderDetail orderDetailpo = new TOrderDetail();
          try {
            BeanUtils.copyProperties(orderDetailpo, dto);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
            e.printStackTrace();
          }
          orderDetailpo.setContractProductDetailId(null);
          orderDetailDao.updateByPrimaryKeySelective(orderDetailpo);
        } else {
          dto.setId(GenerateSerial.getUUID());
          dto.setStockOrderInforId(order.getId());
          orderDetailDao.insertOrderDetail(dto);
        }
      }
      i++;
    }
    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        orderDetailDao.deleteByPrimaryKey(ids[i]);
      }
    }
  }

  private ArrayList<OrderDetialsDto> updateChildrenorderDe(JSONObject orderDe, OrderDetialsDto dto, String orderId) {
    JSONArray arr = orderDe.getJSONArray("children");
    ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
    for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {
      JSONObject top = (JSONObject) iterator2.next();
      OrderDetialsDto po2 = (OrderDetialsDto) JSONObject.toBean(top, OrderDetialsDto.class);
      po2.setParentToolsId(dto.getId());
      po2.setStockOrderInforId(orderId);
      list.add(po2);
      JSONArray arr2 = null;
      try {
        arr2 = top.getJSONArray("children");

      } catch (Exception e) {
        po2.setLeaf(1);
      }
      if (po2.getId() != null && dto.getId().length() == 32) {
        TOrderDetail orderDetailpo = new TOrderDetail();
        try {
          BeanUtils.copyProperties(orderDetailpo, po2);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
        orderDetailpo.setContractProductDetailId(null);
        orderDetailDao.updateByPrimaryKeySelective(orderDetailpo);
      } else {
        po2.setId(GenerateSerial.getUUID());
        orderDetailDao.insertOrderDetail(po2);
      }
      if (arr2 != null && arr2.size() > 0) {
        po2.setChildren(addChildrenorderDe(top, po2, orderId));
      } else {
        //System.out.println("ddd:");
      }
    }
    dto.setChildren(list);
    return list;
  }

  //	@Override
  //	public void updateOrder(TOrderInfor order) {
  //		// TODO Auto-generated method stub
  //		orderInforDao.updateByPrimaryKeySelective(order);
  //	}
  //
  //	@Override
  //	public void updateOrderDetail(OrderDetialsDto orderDetail) {
  //		// TODO Auto-generated method stub
  //		TOrderDetail orderDetailpo = new TOrderDetail();
  //		try {
  //			BeanUtils.copyProperties(orderDetailpo, orderDetail);
  //		} catch (IllegalAccessException e) {
  //			// TODO Auto-generated catch block
  //			e.printStackTrace();
  //		} catch (InvocationTargetException e) {
  //			// TODO Auto-generated catch block
  //			e.printStackTrace();
  //		}
  //		
  //		orderDetailDao.updateByPrimaryKeySelective(orderDetailpo);
  //	}

  @Override
  public List<CurrencyDto> getCurrencyName() {
    // TODO Auto-generated method stub
    return exchangeRateDao.getCurrency();
  }

  @Override
  public TSuppliersInfor getSupplierById(String id) {
    return suppliersInforDao.selectByPrimaryKey(id);
  }

  @Override
  public OrderInfoDto getExcelOrderInfor(String id) {
    // TODO Auto-generated method stub
    return orderInforDao.getExcelOrderInfor(id);
  }

  public TOrderDetailDAO getOrderDetailDao() {
    return orderDetailDao;
  }

  public void setOrderDetailDao(TOrderDetailDAO orderDetailDao) {
    this.orderDetailDao = orderDetailDao;
  }

  public TOrderInforDAO getOrderInforDao() {
    return orderInforDao;
  }

  public void setOrderInforDao(TOrderInforDAO orderInforDao) {
    this.orderInforDao = orderInforDao;
  }

  public TSuppliersInforDAO getSuppliersInforDao() {
    return suppliersInforDao;
  }

  public void setSuppliersInforDao(TSuppliersInforDAO suppliersInforDao) {
    this.suppliersInforDao = suppliersInforDao;
  }

  public TProductToolsInforDAO getProToolsInforDao() {
    return proToolsInforDao;
  }

  public void setProToolsInforDao(TProductToolsInforDAO proToolsInforDao) {
    this.proToolsInforDao = proToolsInforDao;
  }

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

    TProductToolsInforExample example = new TProductToolsInforExample();
    example.createCriteria().andBrandCodeEqualTo(orderDetail.getBrandCode()).andProductCodeEqualTo(orderDetail.getProductCode()).andParentIdEqualTo(
      "root");

    TProductToolsInfor tools = (TProductToolsInfor) proToolsInforDao.selectByExample(example).get(0);

    TOrderPriceHistory orderPirceHistory = new TOrderPriceHistory();
    orderPirceHistory.setId(GenerateSerial.getUUID());
    orderPirceHistory.setSuppliersInforId(orderInfor.getSupplierId());
    orderPirceHistory.setProductToolsInforId(tools.getId());
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
  public String submitAudit(String id) {
    // TODO Auto-generated method stub
    orderInforDao.submitAudit(id);
    return null;
  }

  @Override
  public TOrderInfor getOrderInforById(String id) {
    // TODO Auto-generated method stub
    return orderInforDao.selectByPrimaryKey(id);
  }

  @Override
  public int PlaceOrder(TOrderInfor order) {
    // TODO Auto-generated method stub
    return orderInforDao.updateByPrimaryKeySelective(order);
  }

  @Override
  public List<AccessoriesDto> getAccessoriesByBussinesId(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return accessoriesDAO.getAccessoriesByBusId(parmMap);
  }

  public TExchangeRateDAO getExchangeRateDao() {
    return exchangeRateDao;
  }

  public void setExchangeRateDao(TExchangeRateDAO exchangeRateDao) {
    this.exchangeRateDao = exchangeRateDao;
  }

  public TOrderPriceHistoryDAO getOrderPriceHistoryDao() {
    return orderPriceHistoryDao;
  }

  public void setOrderPriceHistoryDao(TOrderPriceHistoryDAO orderPriceHistoryDao) {
    this.orderPriceHistoryDao = orderPriceHistoryDao;
  }

  public TAccessoriesDAO getAccessoriesDAO() {
    return accessoriesDAO;
  }

  public void setAccessoriesDAO(TAccessoriesDAO accessoriesDAO) {
    this.accessoriesDAO = accessoriesDAO;
  }

  public TSalesPriceHistoryDAO getTsalesPriceHistoryDAO() {
    return tsalesPriceHistoryDAO;
  }

  public void setTsalesPriceHistoryDAO(TSalesPriceHistoryDAO tsalesPriceHistoryDAO) {
    this.tsalesPriceHistoryDAO = tsalesPriceHistoryDAO;
  }

}
