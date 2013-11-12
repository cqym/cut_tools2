package com.tl.resource.business.planOrder;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TProductBrandDAO;
import com.tl.resource.dao.TProductToolsInforDAO;
import com.tl.resource.dao.TRebateDAO;
import com.tl.resource.dao.TReservePlanInforDAO;
import com.tl.resource.dao.TReservePlanMainDAOImpl;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.TSuppliersBrandDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderInforExample;
import com.tl.resource.dao.pojo.TOrderPriceHistory;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductBrandExample;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TProductToolsInforExample;
import com.tl.resource.dao.pojo.TRebate;
import com.tl.resource.dao.pojo.TRebateExample;
import com.tl.resource.dao.pojo.TReservePlanInfor;
import com.tl.resource.dao.pojo.TReservePlanInforExample;
import com.tl.resource.dao.pojo.TReservePlanMain;
import com.tl.resource.dao.pojo.TSalesPriceHistory;
import com.tl.resource.dao.pojo.TSalesPriceHistoryExample;
import com.tl.resource.dao.pojo.TSuppliersBrand;
import com.tl.resource.dao.pojo.TSuppliersBrandExample;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class PlanOrderServiceImpl implements PlanOrderService {

  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TSuppliersInforDAO suppliersInforDao;

  private TSuppliersBrandDAO suppliersBrandDAO;

  private TProductToolsInforDAO proToolsInforDao;

  private TReservePlanInforDAO reservePlanInforDao;

  private TOrderPriceHistoryDAO orderPriceHistoryDao;

  private TAccessoriesDAO accessoriesDAO;

  private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;

  private TReservePlanMainDAOImpl reservePlanMainDAO;

  private TProductBrandDAO productBrandDAO;

  private TRebateDAO rebateDAO;

  @SuppressWarnings("unchecked")
  @Override
  public List<TOrderInfor> getReserveOrderList(int status) {
    // TODO Auto-generated method stub
    TOrderInforExample order = new TOrderInforExample();
    order.or(order.createCriteria().andStatusEqualTo(status));
    return orderInforDao.selectByExample(order);
  }

  @Override
  public List<TOrderInfor> getOrderList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderInforDao.getOrderList(parmMap);
  }

  @Override
  public int getOrderListCount(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderInforDao.getOrderTotal(parmMap);
  }

  @Override
  public int getOrderTotal(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderInforDao.getOrderTotal(parmMap);
  }

  @Override
  public List<OrderDetialsDto> getOrderDetailsList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return orderDetailDao.getOrderDetailsListsOfHasFileData(parmMap);
  }

  @Override
  public int getOrderDetailsTotal(String orderId) {
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(orderId);
    return orderDetailDao.countByExample(example);
  }

  @Override
  public List<TSuppliersInfor> getSuppliersInforList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return suppliersInforDao.getPlanDetailSuppliersByPage(parmMap);
  }

  @Override
  public int getSuppliersInforListCount(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    Integer in = suppliersInforDao.getPlanSuppliersTotal(parmMap);
    return in.intValue();
  }

  @Override
  public OrderDetialsDto insertOrderDetail(OrderDetialsDto orderDetail) {
    return orderDetailDao.insertOrderDetail(orderDetail);
  }

  @Override
  public void updateOrder(TOrderInfor order) {
    // TODO Auto-generated method stub
    orderInforDao.updateByPrimaryKeySelective(order);
  }

  @Override
  public void updateOrderDetail(OrderDetialsDto orderDetail) {
    // TODO Auto-generated method stub
    TOrderDetail orderDetailpo = new TOrderDetail();
    try {
      BeanUtils.copyProperties(orderDetailpo, orderDetail);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    orderDetailDao.updateByPrimaryKeySelective(orderDetailpo);
  }

  @Override
  public void deleteOrder(String[] orderId) {
    // TODO Auto-generated method stub
    if (orderId != null && orderId.length >= 0) {
      HashSet<String> mainIds = new HashSet<String>();
      for (int i = 0; i < orderId.length; i++) {
        TOrderDetailExample orderExp = new TOrderDetailExample();
        orderExp.createCriteria().andStockOrderInforIdEqualTo(orderId[i]);
        List<TOrderDetail> odetail = orderDetailDao.selectByExample(orderExp);//获取当前订单明细
        for (Iterator iterator = odetail.iterator(); iterator.hasNext();) {
          TOrderDetail orderDetail = (TOrderDetail) iterator.next();
          TReservePlanInfor planDetial = reservePlanInforDao.selectByPrimaryKey(orderDetail.getContractProductDetailId());//订单明细对应的计划明细
          mainIds.add(planDetial.getMainId());//收集计划主表id,为改变状态用
        }
        reservePlanInforDao.updateReservePlanByOrderId(orderId[i]);//设置订单明细的状态
        orderDetailDao.deleteByOrderId(orderId[i]);//删除订单明细
        orderInforDao.deleteByPrimaryKey(orderId[i]);//删除订单主表数据
      }

      for (Iterator iterator = mainIds.iterator(); iterator.hasNext();) {//查找mainId对应的明细是否，都已不是4，如果都不是4，则设定计划主表为2
        String mainId = (String) iterator.next();
        TReservePlanInforExample example = new TReservePlanInforExample();
        example.createCriteria().andStatusEqualTo(4).andMainIdEqualTo(mainId);
        int cnt = reservePlanInforDao.countByExample(example);
        if (cnt == 0) {
          TReservePlanMain record = reservePlanMainDAO.selectByPrimaryKey(mainId);
          record.setStatus(2);
          reservePlanMainDAO.updateByPrimaryKey(record);
        }
      }
    }

  }

  @Override
  public TOrderInfor insertOrder(TOrderInfor order) {
    return orderInforDao.insertOrder(order);
  }

  @Override
  public void addOrder(TOrderInfor order, JSONArray orderDetails) {
    if (order != null) {
      orderInforDao.insertOrder(order);

      Iterator<JSONObject> iterator = orderDetails.iterator();
      ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
      int f = 0;//序号
      while (iterator.hasNext()) {
        JSONObject details = iterator.next();
        String id = GenerateSerial.getUUID();
        HashSet<String> mainIdSet = new HashSet<String>();
        if (details.has("contractProductDetailId")) {
          String planInforId = details.getString("contractProductDetailId");
          TReservePlanInfor plan = reservePlanInforDao.selectByPrimaryKey(planInforId);
          plan.setId(details.getString("contractProductDetailId"));
          plan.setStatus(4);
          plan.setMatStockOrderDetailId(id);
          if (details.has("orderAmount") && details.getString("orderAmount") != null) {
            plan.setOrderAmount(new BigDecimal(details.getString("orderAmount")));
          }
          reservePlanInforDao.updateByPrimaryKey(plan);
          mainIdSet.add(plan.getMainId());
        }
        details.remove("rid");
        for (Iterator iterator2 = mainIdSet.iterator(); iterator2.hasNext();) {
          String mid = (String) iterator2.next();
          TReservePlanInforExample example = new TReservePlanInforExample();
          example.createCriteria().andMainIdEqualTo(mid).andStatusEqualTo(2);
          int count = reservePlanInforDao.countByExample(example);
          if (count == 0) {
            TReservePlanMain mainobj = reservePlanMainDAO.selectByPrimaryKey(mid);
            mainobj.setStatus(4);
            reservePlanMainDAO.updateByPrimaryKey(mainobj);
          }
        }

        OrderDetialsDto dto = null;
        try {
          dto = (OrderDetialsDto) JSONObject.toBean(details, OrderDetialsDto.class);
          if (details.getString("leaf").equals("true")) {
            dto.setLeaf(1);
          } else if (details.getString("leaf").equals("false")) {
            dto.setLeaf(0);
          }
          dto.setSerialNumber(BigDecimal.valueOf(Double.valueOf(String.valueOf(f))));
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        dto.setId(id);
        dto.setParentToolsId("root");
        dto.setStockOrderInforId(order.getId());
        orderDetailDao.insertOrderDetail(dto);
      }
      f++;
    }

  }

  @Override
  public void updateOrder(TOrderInfor order, JSONArray orderDetails, String[] ids) {
    // TODO Auto-generated method stub
    if (order != null) {
      orderInforDao.updateByPrimaryKeySelective(order);

      Iterator<JSONObject> iterator = orderDetails.iterator();
      ArrayList<OrderDetialsDto> list = new ArrayList<OrderDetialsDto>();
      int f = 0;//序号
      while (iterator.hasNext()) {
        JSONObject details = iterator.next();
        String id = details.getString("id");
        if (details.getString("id") == null || ("").equals(details.getString("id"))) {
          id = GenerateSerial.getUUID();
        }
        if (details.has("pid")) {
          if (details.getJSONArray("pid").size() > 0) {
            for (int i = 0; i < details.getJSONArray("pid").size(); i++) {
              TReservePlanInfor record = new TReservePlanInfor();
              record.setId(details.getJSONArray("pid").get(i).toString());
              record.setStatus(4);
              record.setMatStockOrderDetailId(id);
              reservePlanInforDao.updateByPrimaryKeySelective(record);
            }
          }
          details.remove("pid");
        }

        OrderDetialsDto dto = null;
        try {
          dto = (OrderDetialsDto) JSONObject.toBean(details, OrderDetialsDto.class);
          dto.setLeaf(0);
          dto.setSerialNumber(BigDecimal.valueOf(Double.valueOf(String.valueOf(f))));
        } catch (Exception e) {
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
          dto.setId(id);
          dto.setStockOrderInforId(order.getId());
          orderDetailDao.insertOrderDetail(dto);
        }

        f++;
      }
    }
    deletePlanOrderDetail(ids);
  }

  private void deletePlanOrderDetail(String[] ids) {
    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {//对删除的订单明细进行处理
        orderDetailDao.deleteByPrimaryKey(ids[i]);//删除订单明细

        TReservePlanInforExample example = new TReservePlanInforExample();
        example.createCriteria().andMatStockOrderDetailIdEqualTo(ids[i]);
        TReservePlanInfor pl = null;
        List planInforList = reservePlanInforDao.selectByExample(example);//根据订单id查找到对应计划的产品明细
        if (planInforList != null && planInforList.size() > 0) {
          pl = (TReservePlanInfor) planInforList.get(0);
        }
        pl.setStatus(2);//修改计划的产品状态为 未做订单
        reservePlanInforDao.updateByPrimaryKey(pl);

        if (pl != null) {
          TReservePlanMain manrec = new TReservePlanMain();
          manrec.setId(pl.getMainId());
          manrec.setStatus(2);
          reservePlanMainDAO.updateByPrimaryKeySelective(manrec);//修改计划的主表的状态为 ‘已确定状态’
        }
      }
    }
  }

  @Override
  public void deleteOrderDetail(String orderId) {
    // TODO Auto-generated method stub
    orderDetailDao.deleteByOrderId(orderId);
  }

  @Override
  public void deleteOrderDetailById(String[] ids) {
    // TODO Auto-generated method stub
    for (int i = 0; i < ids.length; i++) {
      orderDetailDao.deleteByPrimaryKey(ids[i]);
    }

  }

  @Override
  public List<TreeDto> getProToolsBySearch(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return proToolsInforDao.getOrderProToolsList(parmMap);
  }

  @Override
  public int getProToolsTotal(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return proToolsInforDao.getOrderProToolsTotal(parmMap);
  }

  @Override
  public List<ReservePlanDetailDto> getPlanList(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return reservePlanInforDao.getPlanList(parmMap);
  }

  @Override
  public int getPlanListCount(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return reservePlanInforDao.getPlanListCount(parmMap);
  }

  @Override
  public int PlaceOrder(TOrderInfor order) {
    // TODO Auto-generated method stub
    return orderInforDao.updateByPrimaryKeySelective(order);
  }

  @Override
  public TSuppliersInfor getSupplierById(String id) {
    return suppliersInforDao.selectByPrimaryKey(id);
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

  private TOrderPriceHistory changeModel(TOrderDetail orderDetail, OrderInfoDto orderInfor) {
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
  public OrderInfoDto getExcelOrderInfor(String id) {
    // TODO Auto-generated method stub
    return orderInforDao.getExcelOrderInfor(id);
  }

  @Override
  public List<AccessoriesDto> getAccessoriesByBussinesId(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return accessoriesDAO.getAccessoriesByBusId(parmMap);
  }

  public TReservePlanInforDAO getReservePlanInforDao() {
    return reservePlanInforDao;
  }

  public void setReservePlanInforDao(TReservePlanInforDAO reservePlanInforDao) {
    this.reservePlanInforDao = reservePlanInforDao;
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

  @Override
  public List<OrderDetialsDto> consultPlanInfors(String supplierId, String[] planId) {
    ArrayList<String> ids = new ArrayList<String>();
    for (int i = 0; i < planId.length; i++) {
      ids.add(planId[i]);
    }
    TReservePlanInforExample example = new TReservePlanInforExample();
    example.createCriteria().andMainIdIn(ids).andStatusEqualTo(2);
    List<TReservePlanInfor> planList = reservePlanInforDao.selectByExample(example);

    TSuppliersBrandExample sbexp = new TSuppliersBrandExample();
    sbexp.createCriteria().andTSuppliersIdEqualTo(supplierId);
    List<TSuppliersBrand> brandList = suppliersBrandDAO.selectByExample(sbexp);

    List<OrderDetialsDto> od = new ArrayList<OrderDetialsDto>();
    try {
      for (Iterator iterator = planList.iterator(); iterator.hasNext();) {
        TReservePlanInfor plan = (TReservePlanInfor) iterator.next();
        plan.setPlanAmount(plan.getPlanAmount() == null ? BigDecimal.ZERO : plan.getPlanAmount());
        plan.setOutAmount(plan.getOutAmount() == null ? BigDecimal.ZERO : plan.getOutAmount());
        if (plan.getPlanAmount().compareTo(plan.getOutAmount()) == 0) {//如果计划数量等于提取数量，则不进行采购
          continue;
        }
        OrderDetialsDto dto = new OrderDetialsDto();
        BeanUtils.copyProperties(dto, plan);
        dto.setOrderAmount(plan.getPlanAmount().subtract(plan.getOutAmount()));//计划数量减去提取数量，即为需订货数量
        dto.setPlanDetailId(plan.getId());
        dto.setContractDeliveryDate(plan.getDeliveryDate());
        dto.setDeliveryDate(null);
        dto.setContractProductDetailId(plan.getId());
        for (Iterator iterator2 = brandList.iterator(); iterator2.hasNext();) {
          TSuppliersBrand b = (TSuppliersBrand) iterator2.next();
          if (b.getBrand().equals(plan.getProductBrand())) {
            od.add(dto);
            break;
          }
        }

        TProductBrand pbrandPo = getProductBrandByBrand(dto.getProductBrand());
        if (pbrandPo == null) {
          continue;
        }
        TSalesPriceHistory salesPricePo = getSalePriceHistory(dto.getProductCode(), pbrandPo.getSalePriceRunDate());
        if (salesPricePo == null) {
          continue;
        }

        TProductToolsInforExample toolsExample = new TProductToolsInforExample();
        toolsExample.createCriteria().andProductCodeEqualTo(dto.getProductCode());
        List<TProductToolsInfor> toolsList = proToolsInforDao.selectByExample(toolsExample);
        if (toolsList.size() == 0) {
          continue;
        }
        TRebate rebatePo = getRebate(toolsList.get(0).getProductSortId());
        if (rebatePo == null) {
          continue;
        }
        BigDecimal price = salesPricePo.getHistoryPrice();
        BigDecimal rebate = rebatePo.getRebate();
        if (price != null && rebate != null) {
          dto.setPrice(new BigDecimal(price.doubleValue() * (1 - rebate.doubleValue() / 100d)));
        }
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return od;
  }

  private TRebate getRebate(String sortId) {
    TRebateExample rebateExp = new TRebateExample();
    rebateExp.createCriteria().andCustomersDegreeIdEqualTo("xxx").andProductSortIdEqualTo(sortId);
    List<TRebate> rebateList = rebateDAO.selectByExample(rebateExp);
    TRebate rebatePo = null;
    if (rebateList.size() > 0) {
      rebatePo = rebateList.get(0);
    }
    return rebatePo;
  }

  private TSalesPriceHistory getSalePriceHistory(String productCode, String runDate) {
    if (runDate == null) {
      runDate = "";
    }
    if (productCode == null) {
      productCode = "";
    }
    TSalesPriceHistoryExample spricehisExp = new TSalesPriceHistoryExample();
    spricehisExp.createCriteria().andProductCodeEqualTo(productCode).andSalePriceDateEqualTo(runDate);
    List<TSalesPriceHistory> salsesPriceList = tsalesPriceHistoryDAO.selectByExample(spricehisExp);
    TSalesPriceHistory salesPricePo = null;
    if (salsesPriceList.size() > 0) {
      salesPricePo = salsesPriceList.get(0);
    }
    return salesPricePo;
  }

  private TProductBrand getProductBrandByBrand(String productBrand) {
    TProductBrandExample pbrandExp = new TProductBrandExample();
    pbrandExp.createCriteria().andNameEqualTo(productBrand);
    List<TProductBrand> pblist = productBrandDAO.selectByExample(pbrandExp);
    TProductBrand pbrandPo = null;
    if (pblist.size() > 0) {
      pbrandPo = pblist.get(0);
    }
    return pbrandPo;
  }

  public TSuppliersBrandDAO getSuppliersBrandDAO() {
    return suppliersBrandDAO;
  }

  public void setSuppliersBrandDAO(TSuppliersBrandDAO suppliersBrandDAO) {
    this.suppliersBrandDAO = suppliersBrandDAO;
  }

  public TReservePlanMainDAOImpl getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAOImpl reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

  public TProductBrandDAO getProductBrandDAO() {
    return productBrandDAO;
  }

  public void setProductBrandDAO(TProductBrandDAO productBrandDAO) {
    this.productBrandDAO = productBrandDAO;
  }

  public TRebateDAO getRebateDAO() {
    return rebateDAO;
  }

  public void setRebateDAO(TRebateDAO rebateDAO) {
    this.rebateDAO = rebateDAO;
  }

}
