package com.tl.resource.business.purchaseOrder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.ObjectUtil;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.planOrder.PlanOrderService;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TContractProjectSortInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TReservePlanInforDAO;
import com.tl.resource.dao.TReservePlanMainDAOImpl;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TReservePlanInfor;
import com.tl.resource.dao.pojo.TReservePlanInforExample;
import com.tl.resource.dao.pojo.TReservePlanMain;
import com.tl.resource.dao.pojo.TReservePlanMainExample;
import com.tl.resource.dao.pojo.TSuppliersInfor;
import com.tl.resource.dao.pojo.TOrderDetailExample.Criteria;

public class PurchaseOrderEditService {
  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TContractInforDAO contractInforDao;

  private TSuppliersInforDAO suppliersInforDao;

  private TContractProductDetailDAO contractProductDetailDao;

  private TContractProjectSortInforDAO contractProjectSortInforDao;

  private TOrderPriceHistoryDAO orderPriceHistoryDao;

  private TAccessoriesDAO accessoriesDAO;

  private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;

  private TCompanyInforDAO companyInforDAO;

  private TQuotationProductDetailDAO quotationProductDetailDAO;

  private BillsCodeDefService billsCodeDefService;

  private TQuotationInforDAO quotationInforDAO;

  private PlanOrderService planOrderService;

  private TReservePlanMainDAOImpl reservePlanMainDAO;

  private TReservePlanInforDAO reservePlanInforDao;

  public OrderInfoDto consultContractInfor(String contractId, String supplierId, String brand, String leaf, String toolsTypeName) {
    OrderInfoDto oi = new OrderInfoDto();
    TContractInfor conInfor = contractInforDao.selectByPrimaryKey(contractId);
    oi.setContractCode(conInfor.getContractCode());
    oi.setSupplierId(supplierId);
    oi.setOwnContactPerson(conInfor.getOwnContactPerson());
    oi.setCustomerCode(conInfor.getCustomerCode());
    oi.setCustomerName(conInfor.getCustomerName());
    oi.setMome(conInfor.getMemo());
    oi.setContractDeliveryAddress(conInfor.getDeliveryAddressType());
    oi.setContractTrafficMode(conInfor.getTrafficMode());
    oi.setContractUrgentLevel(conInfor.getUrgentLevel());
    oi.setOtherConvention(conInfor.getOtherConvention());
    if ("1".equals(leaf)) {
      oi.setOrderType(OrderInfoDto.ORDER_TYPE_CONTRACT);
    } else {
      oi.setOrderType(OrderInfoDto.ORDER_TYPE_CONTRACT_SELF);
      oi.setClosingAccountMode(conInfor.getClosingAccountMode());
      oi.setTrafficMode(conInfor.getTrafficMode());
      oi.setDeliveryAddressType(conInfor.getDeliveryAddressType());
    }
    initOrderInforAboutDefultValues(oi);
    initOrderInforAboutSupplier(oi);

    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("productBrand", brand);
    parmMap.put("contractId", contractId);
    parmMap.put("supplierId", supplierId);
    parmMap.put("startIndex", 0);
    parmMap.put("pageSize", Integer.MAX_VALUE);
    parmMap.put("leaf", leaf);
    parmMap.put("noHasYd", "y");
    parmMap.put("toolsTypeName", toolsTypeName);
    oi.setOrderDetail(orderDetailDao.selectOrderDetailFromContract(parmMap));

    setDetailTempId(oi.getOrderDetail());
    return oi;
  }

  private void initOrderInforAboutDefultValues(OrderInfoDto oi) {
    oi.setEffectConditions("本合同一式两份，卖方一份，买方一份，双方签字盖章后生效。");
    oi.setDefaultDuty("无。");
    oi.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    oi.setQualityStandard("参照供方标准");
    oi.setOtherConvention("无。");
    oi.setTaxRate(new BigDecimal(0.17));
  }

  private void setDetailTempId(List orderDetail) {
    // TODO Auto-generated method stub
    for (Iterator iterator = orderDetail.iterator(); iterator.hasNext();) {
      com.tl.resource.business.dto.OrderDetialsDto item = (com.tl.resource.business.dto.OrderDetialsDto) iterator.next();
      item.setId(GenerateSerial.getUUID());
    }
  }

  public OrderInfoDto consultScheduleQuo(String scheduleQuoId, String supplierId, String brand, String leaf, String toolsTypeName) {
    Integer orderType = OrderInfoDto.ORDER_TYPE_SCHEDLE;
    if ("0".equals(leaf)) {
      orderType = OrderInfoDto.ORDER_TYPE_SCHEDLE_SELF;
    }
    return consultQuotation(scheduleQuoId, supplierId, brand, orderType, OutStockInforDto.OUT_STOCK_TYPE_SCHEDULE_TQ, leaf, toolsTypeName);
  }

  public OrderInfoDto consultQuotation(String QuoId, String supplierId, String brand, Integer orderType, String outStockType, String leaf,
    String toolsTypeName) {
    OrderInfoDto oi = new OrderInfoDto();
    TQuotationInfor quopo = this.quotationInforDAO.selectByPrimaryKey(QuoId);
    oi.setQuotationCode(quopo.getQuotationCode());
    oi.setQuotationId(quopo.getId());
    oi.setSupplierId(supplierId);
    oi.setOwnContactPerson(quopo.getUserName());
    oi.setCustomerCode(quopo.getCustomerCode());
    oi.setCustomerName(quopo.getCustomerName());
    oi.setMome(quopo.getMemo());
    //oi.setDeliveryAddressType(quopo.getDeliveryType());
    oi.setOrderType(orderType);
    oi.setClosingAccountMode(" ");
    oi.setTrafficMode(" ");
    oi.setDeliveryAddressType(" ");
    initOrderInforAboutDefultValues(oi);
    initOrderInforAboutSupplier(oi);

    Map<String, Object> detailParam = new HashMap<String, Object>();
    detailParam.put("startIndex", 0);
    detailParam.put("pageSize", Integer.MAX_VALUE);
    detailParam.put("quotationCode", quopo.getQuotationCode());
    detailParam.put("supplierId", supplierId);
    detailParam.put("brand", brand);
    detailParam.put("outStockType", outStockType);
    detailParam.put("leaf", leaf);
    detailParam.put("toolsTypeName", toolsTypeName);

    oi.setOrderDetail(this.quotationProductDetailDAO.getQuotationDetailBySupplier(detailParam));
    setDetailTempId(oi.getOrderDetail());
    return oi;
  }

  public OrderInfoDto consultPlans(String[] planId, String supplierId, String brand) {
    OrderInfoDto oi = new OrderInfoDto();
    oi.setSupplierId(supplierId);
    oi.setOrderType(OrderInfoDto.ORDER_TYPE_MAT_RESERVE);
    oi.setClosingAccountMode(" ");
    oi.setTrafficMode(" ");
    oi.setDeliveryAddressType(" ");
    initOrderInforAboutDefultValues(oi);
    initOrderInforAboutSupplier(oi);
    oi.setOrderDetail(planOrderService.consultPlanInfors(supplierId, planId));
    for (Iterator iterator = oi.getOrderDetail().iterator(); iterator.hasNext();) {
      OrderDetialsDto d = (OrderDetialsDto) iterator.next();
      if (d.getPrice() != null && d.getOrderAmount() != null) {
        d.setProductMoney(d.getPrice().multiply(d.getOrderAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
      }
      d.setProjectCode("0");
    }
    setDetailTempId(oi.getOrderDetail());
    return oi;
  }

  public OrderInfoDto consultReserver(String supplierId) {
    OrderInfoDto oi = new OrderInfoDto();
    oi.setSupplierId(supplierId);
    oi.setOrderType(OrderInfoDto.ORDER_TYPE_RESERVE);
    initOrderInforAboutDefultValues(oi);
    initOrderInforAboutSupplier(oi);
    oi.setOrderDetail(new ArrayList());
    return oi;
  }

  public OrderInfoDto consultTryQuo(String tryQuoId, String supplierId, String brand, String leaf, String toolsTypeName) {
    // TODO Auto-generated method stub
    Integer orderType = OrderInfoDto.ORDER_TYPE_TRY;
    if ("0".equals(leaf)) {
      orderType = OrderInfoDto.ORDER_TYPE_TRY_SELF;
    }
    return this.consultQuotation(tryQuoId, supplierId, brand, orderType, OutStockInforDto.OUT_STOCK_TYPE_TRY_TQ, leaf, toolsTypeName);
  }

  private void initOrderInforAboutSupplier(OrderInfoDto oi) {
    TSuppliersInfor supplier = suppliersInforDao.selectByPrimaryKey(oi.getSupplierId());
    oi.setSupplierName(supplier.getSupplierName());
    oi.setContactPerson(supplier.getContactPersonFirst());
    oi.setSupplierOwnContactPerson(supplier.getOwnContactPerson());
    oi.setSupplierContactPerson(supplier.getContactPersonFirst());
    oi.setSupplierPhone(supplier.getPhoneFirst());
    oi.setSupplierFax(supplier.getFaxFirst());
  }

  public void addPurchaseOrder(OrderInfoDto dto) {
    // TODO Auto-generated method stub
    dto.setOrderCode(billsCodeDefService.getBillCode(buildCodeType(dto.getOrderType()), getSupplierCodeById(dto.getSupplierId()), dto
      .getCustomerCode(), null));
    dto.setId(GenerateSerial.getUUID());
    TOrderInfor record = new TOrderInfor();
    ObjectUtil.copyObjectPropertys(dto, record);
    record.setStatus(0);
    orderInforDao.insertSelective(record);

    for (Iterator iterator = dto.getOrderDetail().iterator(); iterator.hasNext();) {
      OrderDetialsDto detailDto = (OrderDetialsDto) iterator.next();

      detailDto.setId(GenerateSerial.getUUID());
      detailDto.setParentToolsId("root");
      detailDto.setStockOrderInforId(record.getId());
      orderDetailDao.insertOrderDetail(detailDto);
    }
    updateQuoOrConDetailOrderAmount(record);

    if (OrderInfoDto.ORDER_TYPE_MAT_RESERVE.equals(dto.getOrderType())) {
      updatePlanMainStatus(dto.getOrderDetail());
    }
    if (OrderInfoDto.ORDER_TYPE_CONTRACT_SELF.equals(dto.getOrderType()) || OrderInfoDto.ORDER_TYPE_SCHEDLE_SELF.equals(dto.getOrderType())
      || OrderInfoDto.ORDER_TYPE_TRY_SELF.equals(dto.getOrderType())) {
      updateOrderInforDeliveryDate(dto);
    }
  }

  private void updateOrderInforDeliveryDate(OrderInfoDto dto) {
    String minDeliveryDate = "9999-99-99";
    for (Iterator iterator = dto.getOrderDetail().iterator(); iterator.hasNext();) {
      OrderDetialsDto detail = (OrderDetialsDto) iterator.next();
      if (minDeliveryDate.compareTo(detail.getDeliveryDate()) > 0) {
        minDeliveryDate = detail.getDeliveryDate();
      }
    }
    TOrderInfor orderInfor = orderInforDao.selectByPrimaryKey(dto.getId());
    orderInfor.setDeliveryDate(minDeliveryDate);
    orderInforDao.updateByPrimaryKey(orderInfor);
  }

  private void updatePlanMainStatus(List<OrderDetialsDto> orderDetail) {
    Set<String> planMainIdSet = new HashSet<String>();
    for (Iterator iterator = orderDetail.iterator(); iterator.hasNext();) {
      OrderDetialsDto orderDetialsDto = (OrderDetialsDto) iterator.next();
      TReservePlanInfor planInfor = reservePlanInforDao.selectByPrimaryKey(orderDetialsDto.getContractProductDetailId());
      planInfor.setId(orderDetialsDto.getContractProductDetailId());
      planInfor.setStatus(4);
      planInfor.setMatStockOrderDetailId(orderDetialsDto.getId());
      planInfor.setOrderAmount(orderDetialsDto.getOrderAmount());
      reservePlanInforDao.updateByPrimaryKey(planInfor);

      TReservePlanInforExample example = new TReservePlanInforExample();
      example.createCriteria().andMainIdEqualTo(planInfor.getMainId()).andStatusEqualTo(2);
      int cnt = reservePlanInforDao.countByExample(example);
      if (cnt == 0) {
        planMainIdSet.add(planInfor.getMainId());
      }
    }
    if (planMainIdSet.size() == 0) {
      return;
    }
    TReservePlanMainExample planMainExp = new TReservePlanMainExample();
    planMainExp.createCriteria().andIdIn(Arrays.asList(planMainIdSet.toArray()));
    List<TReservePlanMain> mainList = reservePlanMainDAO.selectByExample(planMainExp);// .selectByPrimaryKey(planIds);
    for (Iterator iterator = mainList.iterator(); iterator.hasNext();) {
      TReservePlanMain reservePlanMain = (TReservePlanMain) iterator.next();
      reservePlanMain.setStatus(4);
      reservePlanMainDAO.updateByPrimaryKey(reservePlanMain);
    }
  }

  public String getSupplierCodeById(String id) {
    TSuppliersInfor sup = suppliersInforDao.selectByPrimaryKey(id);
    return sup.getSupplierCode();
  }

  public void updatePurchaseOrder(OrderInfoDto dto) {
    // TODO Auto-generated method stub
    TOrderInfor record = new TOrderInfor();
    ObjectUtil.copyObjectPropertys(dto, record);
    orderInforDao.updateByPrimaryKeySelective(record);

    for (Iterator iterator = dto.getOrderDetail().iterator(); iterator.hasNext();) {
      OrderDetialsDto detailDto = (OrderDetialsDto) iterator.next();
      TOrderDetail detailRecord = new TOrderDetail();
      ObjectUtil.copyObjectPropertys(detailDto, detailRecord);
      if (StringUtils.isEmpty(detailRecord.getId())) {
        detailRecord.setId(GenerateSerial.getUUID());
        detailRecord.setStockOrderInforId(record.getId());
        orderDetailDao.insert(detailRecord);
      } else {
        orderDetailDao.updateByPrimaryKey(detailRecord);
      }
    }

    updateQuoOrConDetailOrderAmount(record);
    sycArrivalDetailInfor(record.getId());
  }

  private void sycArrivalDetailInfor(String orderId) {
    // TODO Auto-generated method stub
    orderDetailDao.sycArrivalDetailOrderAmount(orderId);
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

  public TSuppliersInforDAO getSuppliersInforDao() {
    return suppliersInforDao;
  }

  public void setSuppliersInforDao(TSuppliersInforDAO suppliersInforDao) {
    this.suppliersInforDao = suppliersInforDao;
  }

  public TContractProductDetailDAO getContractProductDetailDao() {
    return contractProductDetailDao;
  }

  public void setContractProductDetailDao(TContractProductDetailDAO contractProductDetailDao) {
    this.contractProductDetailDao = contractProductDetailDao;
  }

  public TContractProjectSortInforDAO getContractProjectSortInforDao() {
    return contractProjectSortInforDao;
  }

  public void setContractProjectSortInforDao(TContractProjectSortInforDAO contractProjectSortInforDao) {
    this.contractProjectSortInforDao = contractProjectSortInforDao;
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

  public TCompanyInforDAO getCompanyInforDAO() {
    return companyInforDAO;
  }

  public void setCompanyInforDAO(TCompanyInforDAO companyInforDAO) {
    this.companyInforDAO = companyInforDAO;
  }

  public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
    return quotationProductDetailDAO;
  }

  public void setQuotationProductDetailDAO(TQuotationProductDetailDAO quotationProductDetailDAO) {
    this.quotationProductDetailDAO = quotationProductDetailDAO;
  }

  public String buildCodeType(Integer orderType) {
    //(1采购,2储备,3加工,4,材料储备,5预定订单,6试刀订单,7预定加工,8试刀加工)
    String codeType = "";
    switch (orderType) {
    case 1://采购
      codeType = "04";
      break;
    case 2://储备
      codeType = "11";
      break;
    case 3:
      codeType = "24";
      break;
    case 4:
      codeType = "23";
      break;
    case 5:
      codeType = "12";
      break;
    case 6:
      codeType = "13";
      break;
    case 7:
      codeType = "25";
      break;
    case 8:
      codeType = "26";
      break;
    }
    return codeType;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

  public PlanOrderService getPlanOrderService() {
    return planOrderService;
  }

  public void setPlanOrderService(PlanOrderService planOrderService) {
    this.planOrderService = planOrderService;
  }

  public void deleteOrderByIds(String[] ids) {
    // TODO Auto-generated method stub
    if (ids != null && ids.length > 0) {
      for (int i = 0; i < ids.length; i++) {
        TOrderDetailExample example = new TOrderDetailExample();
        example.createCriteria().andStockOrderInforIdEqualTo(ids[i]);
        orderDetailDao.deleteByExample(example);

        updateQuoOrConDetailOrderAmount(orderInforDao.selectByPrimaryKey(ids[i]));

        orderInforDao.deleteByPrimaryKey(ids[i]);
      }
    }
  }

  private void updateQuoOrConDetailOrderAmount(TOrderInfor order) {
    if (OrderInfoDto.ORDER_TYPE_CONTRACT_SELF.equals(order.getOrderType()) || OrderInfoDto.ORDER_TYPE_CONTRACT.equals(order.getOrderType())) {//合同订单，修改对应合同明细的订单数量
      HashMap<String, String> para = new HashMap<String, String>();
      para.put("contractCode", order.getContractCode());
      contractProductDetailDao.updateContractDetailOrderAmount(para);
    } else if (OrderInfoDto.ORDER_TYPE_SCHEDLE_SELF.equals(order.getOrderType()) || OrderInfoDto.ORDER_TYPE_TRY_SELF.equals(order.getOrderType())
      || OrderInfoDto.ORDER_TYPE_SCHEDLE.equals(order.getOrderType()) || OrderInfoDto.ORDER_TYPE_TRY.equals(order.getOrderType())) {//如果是预订，或者试刀，则修改对应报价单明细订单数量
      HashMap<String, String> para = new HashMap<String, String>();
      para.put("quotationCode", order.getQuotationCode());
      quotationProductDetailDAO.updateQuotationDetailOrderAmount(para);
      contractProductDetailDao.sycContractOrderDetail(order.getQuotationCode());
    }
  }

  public TReservePlanMainDAOImpl getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAOImpl reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

  public TReservePlanInforDAO getReservePlanInforDao() {
    return reservePlanInforDao;
  }

  public void setReservePlanInforDao(TReservePlanInforDAO reservePlanInforDao) {
    this.reservePlanInforDao = reservePlanInforDao;
  }

  public void placeOrder(String orderId) {
    TOrderDetailExample example = new TOrderDetailExample();
    Criteria c = example.createCriteria();
    c.andParentToolsIdEqualTo("root").andStockOrderInforIdEqualTo(orderId).andOrderAmountEqualTo(BigDecimal.ZERO);
    int count = orderDetailDao.countByExample(example);
    TOrderInfor order = orderInforDao.selectByPrimaryKey(orderId);
    if (count > 0) {
      throw new RuntimeException("订单【" + order.getOrderCode() + "】明细中，有采购数量为零的数据，不能进行订单确认");
    }

    order.setStatus(TOrderInfor.STATUS_PLACE);
    orderInforDao.updateByPrimaryKeySelective(order);
    updateQuoOrConDetailOrderAmount(order);
  }

  public static void main(String[] args) {
    BigDecimal bd = new BigDecimal(10);
    BigDecimal bd2 = new BigDecimal(1222.5678);
    System.out.println(bd.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP));
  }

  public void deleteOrderDetailByIds(String[] ids, TOrderInfor orderInfor) {
    for (int i = 0; i < ids.length; i++) {
      orderDetailDao.deleteByPrimaryKey(ids[i]);
    }
    orderInforDao.updateByPrimaryKeySelective(orderInfor);

    updateQuoOrConDetailOrderAmount(orderInfor);
  }
}
