package com.tl.resource.business.reservePlan;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.ReservePlanMainInforDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.dao.TMatAccountsInforDAO;
import com.tl.resource.dao.TMatReserveInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TProductToolsInforDAO;
import com.tl.resource.dao.TReservePlanInforDAO;
import com.tl.resource.dao.TReservePlanMainDAO;
import com.tl.resource.dao.pojo.TMatAccountsInfor;
import com.tl.resource.dao.pojo.TMatAccountsInforExample;
import com.tl.resource.dao.pojo.TMatReserveInfor;
import com.tl.resource.dao.pojo.TMatReserveInforExample;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TProductToolsInforExample;
import com.tl.resource.dao.pojo.TReservePlanInfor;
import com.tl.resource.dao.pojo.TReservePlanInforExample;
import com.tl.resource.dao.pojo.TReservePlanMain;
import com.tl.resource.dao.pojo.TReservePlanMainExample;
import com.tl.resource.dao.pojo.TOrderDetailExample.Criteria;

public class ReservePlanServiceImp implements ReservePlanService {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private TOrderDetailDAO orderDetailDao;

  private TOrderInforDAO orderInforDao;

  private TReservePlanInforDAO reservePlanInforDAO;

  private BillsCodeDefService billsCodeDefService;

  private TReservePlanMainDAO reservePlanMainDAO;

  private TMatReserveInforDAO matReserveInforDAO;

  private TMatAccountsInforDAO matAccountsInforDAO;

  private TProductToolsInforDAO productToolsInforDAO;

  @Override
  public void addReservePlan(ReservePlanMainInforDto dto) {
    // TODO Auto-generated method stub
    try {
      TReservePlanMain mainrecord = new TReservePlanMain();
      BeanUtils.copyProperties(mainrecord, dto);
      mainrecord.setId(GenerateSerial.getUUID());
      if (mainrecord.getPlanType() != null && mainrecord.getPlanType() == 0) {
        mainrecord.setPlanCode(billsCodeDefService.getBillCode("28", null, null, null));
      } else {
        mainrecord.setPlanCode(billsCodeDefService.getBillCode("07", null, null, null));
      }
      reservePlanMainDAO.insert(mainrecord);

      List<ReservePlanDetailDto> list = dto.getReservePlanDetail();
      TOrderDetail orderDetailRecord = new TOrderDetail();
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        ReservePlanDetailDto reservePlanDetailDto = (ReservePlanDetailDto) iterator.next();
        TReservePlanInfor record = new TReservePlanInfor();
        BeanUtils.copyProperties(record, reservePlanDetailDto);
        record.setId(GenerateSerial.getUUID());
        record.setEditDate(dto.getEditTime());
        record.setUserId(dto.getUserId());
        record.setUserName(dto.getUserName());
        record.setContractCode(dto.getContractCode());
        record.setContractId(dto.getContractId());
        record.setOrderInforId(dto.getOrderId());
        record.setOrderCode(dto.getOrderCode());
        record.setStatus(0);

        record.setMainId(mainrecord.getId());
        reservePlanInforDAO.insert(record);

        if (record.getOutAmount() != null && record.getOutAmount().doubleValue() > 0) {//如果有提取库存
          createMatReserveRecord(mainrecord.getPlanCode(), record);
        }
      }
      setOrderStatus(dto.getOrderId());
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void createMatReserveRecord(String planCode, TReservePlanInfor record) {
    TMatReserveInfor matrepo = getMatReserveInforByProductCode(record.getProductCode());
    matrepo.setAmount(matrepo.getAmount().subtract(record.getOutAmount()));
    if (matrepo.getAmount().compareTo(BigDecimal.ZERO) == -1) {//如果库存数量被提取后小于零
      throw new RuntimeException("牌号为：[" + matrepo.getBrandCode() + "]的产品提取库存失败!");
    }
    matReserveInforDAO.updateByPrimaryKey(matrepo);

    TMatAccountsInfor mataccpo = new TMatAccountsInfor();
    mataccpo.setAccountType(2);
    mataccpo.setAmount(record.getOutAmount());
    mataccpo.setBrandCode(record.getBrandCode());
    mataccpo.setCreateAccountTime(new Date());
    mataccpo.setId(GenerateSerial.getUUID());
    mataccpo.setInvoiceCode(planCode);
    mataccpo.setInvoiceId(record.getId());
    mataccpo.setProductBrand(record.getProductBrand());
    mataccpo.setProductCode(record.getProductCode());
    mataccpo.setProductName(record.getProductName());
    mataccpo.setProductUnit(record.getProductUnit());
    mataccpo.setReserveCode(matrepo.getReserveCode());
    mataccpo.setReserveInforId(matrepo.getId());
    matAccountsInforDAO.insert(mataccpo);
  }

  private TMatReserveInfor getMatReserveInforByProductCode(String productCode) {
    TMatReserveInforExample mriPoexp = new TMatReserveInforExample();
    mriPoexp.createCriteria().andProductCodeEqualTo(productCode);
    List<TMatReserveInfor> matrelist = matReserveInforDAO.selectByExample(mriPoexp);
    if (matrelist.size() == 0) {
      return null;
    }
    TMatReserveInfor matrepo = matrelist.get(0);
    return matrepo;
  }

  private void setOrderStatus(String orderId) {
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(orderId).andLeafEqualTo(0);
    List listFb = orderDetailDao.selectByExample(example);
    int totalCount = listFb.size();
    Integer count = orderDetailDao.getNoEditPlanProductsCount(orderId);
    TOrderInfor orderInforRecord = orderInforDao.selectByPrimaryKey(orderId);
    if (orderInforRecord != null) {
      if (count == 0) {// 所有非标品都有计划了
        orderInforRecord.setEditedPlan(TOrderInfor.ALL_EDIT_PLAN);
      } else if (totalCount > count) {// 部分有计划
        orderInforRecord.setEditedPlan(TOrderInfor.PART_EDIT_PLAN);
      } else {// 没有做计划
        orderInforRecord.setEditedPlan(TOrderInfor.NO_EDIT_PLAN);
      }
      orderInforDao.updateByPrimaryKeySelective(orderInforRecord);
    }
  }

  @Override
  public ReservePlanMainInforDto consultReserveInfors(String orderId) {
    // 获取订单主要信息
    ReservePlanMainInforDto dto = new ReservePlanMainInforDto();
    TOrderInfor orderInfor = orderInforDao.selectByPrimaryKey(orderId);
    dto.setContractCode(orderInfor.getContractCode());
    // dto.setContractId(orderInfor)
    dto.setOrderCode(orderInfor.getOrderCode());
    dto.setOrderId(orderId);
    dto.setEditTime(new Date());
    dto.setUrgentLevel(orderInfor.getUrgentLevel());
    List<ReservePlanDetailDto> reservePlanDetail = new ArrayList<ReservePlanDetailDto>();
    dto.setReservePlanDetail(reservePlanDetail);
    dto.setMemo(orderInfor.getMome());
    dto.setPlanType(orderInfor.getOrderType());
    // List<OrderDetialsDto> list =
    // orderDetailDao.getOrderDetailsHasReserveInfor1(orderId);
    // 获取已经做了计划的订单明细
    TReservePlanInforExample planex = new TReservePlanInforExample();
    planex.createCriteria().andOrderInforIdEqualTo(orderId);
    List plans = reservePlanInforDAO.selectByExample(planex);
    ArrayList orderDetailIds = new ArrayList();
    for (Iterator iterator = plans.iterator(); iterator.hasNext();) {
      TReservePlanInfor plan = (TReservePlanInfor) iterator.next();
      orderDetailIds.add(plan.getParentToolsId());
    }
    // 获取没有做计划的订单明细
    TOrderDetailExample example = new TOrderDetailExample();
    Criteria c = example.createCriteria();
    c.andStockOrderInforIdEqualTo(orderId).andLeafEqualTo(0).andParentToolsIdEqualTo("root");
    if (orderDetailIds.size() > 0) {
      c.andIdNotIn(orderDetailIds);
    }
    List listFb = orderDetailDao.selectByExample(example);
    List<OrderDetialsDto> dtolist = new ArrayList<OrderDetialsDto>();
    try {
      for (Iterator iterator = listFb.iterator(); iterator.hasNext();) {
        TOrderDetail detail = (TOrderDetail) iterator.next();
        OrderDetialsDto ddto = new OrderDetialsDto();
        BeanUtils.copyProperties(ddto, detail);
        dtolist.add(ddto);
      }
      dto.setOrderDetailList(dtolist);
    } catch (IllegalAccessException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (InvocationTargetException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    //从产品信息获取下级组建
    for (Iterator iterator = dtolist.iterator(); iterator.hasNext();) {
      OrderDetialsDto orderDetialsDto = (OrderDetialsDto) iterator.next();
      String parentToolsId = orderDetialsDto.getToolsId();
      TProductToolsInforExample tollsExa = new TProductToolsInforExample();
      tollsExa.createCriteria().andParentIdEqualTo(parentToolsId);
      List<TProductToolsInfor> childrenList = productToolsInforDAO.selectByExample(tollsExa);
      for (Iterator iterator2 = childrenList.iterator(); iterator2.hasNext();) {
        TProductToolsInfor tools = (TProductToolsInfor) iterator2.next();
        ReservePlanDetailDto dtod = new ReservePlanDetailDto();
        dtod.setId(GenerateSerial.getUUID());
        dtod.setToolsId(tools.getId());
        dtod.setProductBrand(tools.getProductBrand());
        dtod.setBrandCode(tools.getBrandCode());
        dtod.setProductName(tools.getProductName());
        dtod.setProductCode(tools.getProductCode());
        dtod.setProductUnit(tools.getProductUnit());
        dtod.setLeaf(1);
        dtod.setOrderAmount(BigDecimal.ZERO);
        dtod.setOrderInforId(orderDetialsDto.getStockOrderInforId());
        dtod.setParentToolsId(orderDetialsDto.getId());
        TMatReserveInfor matrepo = getMatReserveInforByProductCode(dtod.getProductCode());
        if (matrepo == null) {
          dtod.setReserveAmount(BigDecimal.ZERO);
        } else {
          dtod.setReserveAmount(matrepo.getAmount());
        }
        reservePlanDetail.add(dtod);
      }
    }
    // 获取订单从工具信息带过来的组件信息
    //    try {
    //      example.clear();
    //      example.createCriteria().andStockOrderInforIdEqualTo(orderId).andLeafEqualTo(1).andParentToolsIdNotEqualTo("root");
    //      List list = orderDetailDao.selectByExample(example);
    //      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    //        TOrderDetail detail = (TOrderDetail) iterator.next();
    //        ReservePlanDetailDto dtod = new ReservePlanDetailDto();
    //        BeanUtils.copyProperties(dtod, detail);
    //        dtod.setStockOrderDetailId(dtod.getId());
    //        dtod.setId(GenerateSerial.getUUID());
    //        if (dtod.getOrderAmount() == null)
    //          dtod.setOrderAmount(BigDecimal.ZERO);
    //        TMatReserveInfor matrepo = getMatReserveInforByProductCode(dtod.getProductCode());
    //        if (matrepo == null) {
    //          dtod.setReserveAmount(BigDecimal.ZERO);
    //        } else {
    //          dtod.setReserveAmount(matrepo.getAmount());
    //        }
    //        reservePlanDetail.add(dtod);
    //      }
    //    } catch (IllegalAccessException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    } catch (InvocationTargetException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    }
    return dto;
  }

  private List<ReservePlanDetailDto> copyChildrenNodes(OrderDetialsDto orderDetialsDto) {
    List<ReservePlanDetailDto> reservePlanDetail = new ArrayList<ReservePlanDetailDto>();
    List<OrderDetialsDto> list = orderDetialsDto.getChildren();
    if (list == null)
      return null;
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        OrderDetialsDto torderDetialsDto = (OrderDetialsDto) iterator.next();
        ReservePlanDetailDto dtod = new ReservePlanDetailDto();
        BeanUtils.copyProperties(dtod, torderDetialsDto);
        dtod.setStockOrderDetailId(dtod.getId());
        dtod.setId(null);
        reservePlanDetail.add(dtod);
        if (torderDetialsDto.getLeaf() == 0) {
          dtod.setChildren(copyChildrenNodes(torderDetialsDto));
        } else {
          if (dtod.getOrderAmount().doubleValue() <= dtod.getRemainAmount().doubleValue()) {
            dtod.setPlanAmount(BigDecimal.ZERO);
          } else {
            dtod.setPlanAmount(dtod.getOrderAmount().subtract(dtod.getRemainAmount()));
          }
        }
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return reservePlanDetail;
  }

  @Override
  public void deleteReservePlanById(String id) {

    TReservePlanInforExample example = new TReservePlanInforExample();
    example.createCriteria().andMainIdEqualTo(id);
    List<TReservePlanInfor> list = reservePlanInforDAO.selectByExample(example);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TReservePlanInfor reservePlanInfor = (TReservePlanInfor) iterator.next();
      if (reservePlanInfor.getOutAmount() != null && reservePlanInfor.getOutAmount().doubleValue() > 0) {
        reservePlanInfor.setOutAmount(BigDecimal.ZERO);
        updateMatReserveRecord(reservePlanInfor);
      }
    }
    reservePlanInforDAO.deleteByExample(example);
    TReservePlanMain po = reservePlanMainDAO.selectByPrimaryKey(id);
    reservePlanMainDAO.deleteByPrimaryKey(id);
    if (po != null) {
      setOrderStatus(po.getOrderId());
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public ReservePlanMainInforDto getReservePlanMainInfor(String planId) {
    String orderId = null;
    ReservePlanMainInforDto dto = new ReservePlanMainInforDto();
    TReservePlanMain mainPo = reservePlanMainDAO.selectByPrimaryKey(planId);
    try {
      BeanUtils.copyProperties(dto, mainPo);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    TReservePlanInforExample example = new TReservePlanInforExample();
    example.createCriteria().andMainIdEqualTo(planId);
    List<TReservePlanInfor> list = reservePlanInforDAO.selectByExample(example);
    List<ReservePlanDetailDto> planDto = new ArrayList<ReservePlanDetailDto>();
    List<String> orderIds = new ArrayList<String>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TReservePlanInfor reservePlanInfor = (TReservePlanInfor) iterator.next();
        ReservePlanDetailDto dtod = new ReservePlanDetailDto();
        orderId = reservePlanInfor.getOrderInforId();
        BeanUtils.copyProperties(dtod, reservePlanInfor);
        orderIds.add(dtod.getParentToolsId());
        TMatReserveInfor matrepo = getMatReserveInforByProductCode(dtod.getProductCode());
        if (matrepo == null) {
          dtod.setReserveAmount(BigDecimal.ZERO);
        } else {
          dtod.setReserveAmount(matrepo.getAmount());
        }
        planDto.add(dtod);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    dto.setReservePlanDetail(planDto);

    TOrderDetailExample exampleorder = new TOrderDetailExample();
    exampleorder.createCriteria().andIdIn(orderIds);// .andStockOrderInforIdEqualTo(orderId).andLeafEqualTo(0).andParentToolsIdEqualTo("root");
    List listFb = orderDetailDao.selectByExample(exampleorder);
    List<OrderDetialsDto> dtolist = new ArrayList<OrderDetialsDto>();
    try {
      for (Iterator iterator = listFb.iterator(); iterator.hasNext();) {
        TOrderDetail detail = (TOrderDetail) iterator.next();
        OrderDetialsDto ddto = new OrderDetialsDto();
        BeanUtils.copyProperties(ddto, detail);
        dtolist.add(ddto);
      }
      dto.setOrderDetailList(dtolist);
    } catch (IllegalAccessException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (InvocationTargetException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return dto;
  }

  @Override
  public void updateReservePlan(ReservePlanMainInforDto dto) {
    TReservePlanMain mainPo = reservePlanMainDAO.selectByPrimaryKey(dto.getId());
    mainPo.setMemo(dto.getMemo());
    mainPo.setUrgentLevel(dto.getUrgentLevel());
    reservePlanMainDAO.updateByPrimaryKey(mainPo);
    List<ReservePlanDetailDto> plans = dto.getReservePlanDetail();

    try {
      for (Iterator iterator = plans.iterator(); iterator.hasNext();) {
        ReservePlanDetailDto reservePlanDetailDto = (ReservePlanDetailDto) iterator.next();
        if (reservePlanDetailDto.getStatus() != null) {
          TReservePlanInfor oldPlanpo = reservePlanInforDAO.selectByPrimaryKey(reservePlanDetailDto.getId());

          TReservePlanInfor record = new TReservePlanInfor();
          record.setId(reservePlanDetailDto.getId());
          record.setPlanAmount(reservePlanDetailDto.getPlanAmount());
          record.setMemo(reservePlanDetailDto.getMemo());
          record.setMemo2(reservePlanDetailDto.getMemo2());
          record.setDeliveryDate(reservePlanDetailDto.getDeliveryDate());
          record.setOutAmount(reservePlanDetailDto.getOutAmount());
          record.setProductCode(reservePlanDetailDto.getProductCode());
          reservePlanInforDAO.updateByPrimaryKeySelective(record);
          if ((oldPlanpo.getOutAmount() == null || oldPlanpo.getOutAmount().compareTo(BigDecimal.ZERO) == 0) && record.getOutAmount() != null
            && record.getOutAmount().doubleValue() > 0) {//如果修改之前 的提取是数量是0，并且本次提取库存数量大于零，则本次需要新建
            createMatReserveRecord(mainPo.getPlanCode(), record);
          } else if (oldPlanpo.getOutAmount() == null || oldPlanpo.getOutAmount().compareTo(BigDecimal.ZERO) == 1) {
            //如果修改之前的提取数量大于零，则本次需要修改
            updateMatReserveRecord(record);
          }
        } else {
          TReservePlanInfor record = new TReservePlanInfor();
          BeanUtils.copyProperties(record, reservePlanDetailDto);
          record.setId(GenerateSerial.getUUID());
          record.setEditDate(dto.getEditTime());
          record.setUserId(dto.getUserId());
          record.setUserName(dto.getUserName());
          record.setContractCode(dto.getContractCode());
          record.setContractId(dto.getContractId());
          record.setOrderInforId(dto.getOrderId());
          record.setOrderCode(dto.getOrderCode());
          record.setStatus(0);

          record.setMainId(mainPo.getId());
          reservePlanInforDAO.insert(record);

          if (record.getOutAmount() != null && record.getOutAmount().doubleValue() > 0) {//如果有提取库存
            createMatReserveRecord(mainPo.getPlanCode(), record);
          }
        }
        if (reservePlanDetailDto.getStockOrderDetailId() != null) {
          TOrderDetail orderDetailRecord = new TOrderDetail();
          orderDetailRecord.setId(reservePlanDetailDto.getStockOrderDetailId());
          orderDetailRecord.setOrderAmount(reservePlanDetailDto.getOrderAmount());
          orderDetailDao.updateByPrimaryKeySelective(orderDetailRecord);
        }
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void updateMatReserveRecord(TReservePlanInfor record) {
    TMatAccountsInforExample example = new TMatAccountsInforExample();
    example.createCriteria().andInvoiceIdEqualTo(record.getId());
    List<TMatAccountsInfor> accList = matAccountsInforDAO.selectByExample(example);
    TMatAccountsInfor oldAcc = accList.get(0);
    if (oldAcc.getAmount().compareTo(record.getOutAmount()) != 0) {//提取数量有变化
      record.setOutAmount(record.getOutAmount() == null ? BigDecimal.ZERO : record.getOutAmount());
      oldAcc.setAmount(oldAcc.getAmount() == null ? BigDecimal.ZERO : oldAcc.getAmount());
      double addedAmount = record.getOutAmount().doubleValue() - oldAcc.getAmount().doubleValue();//增加的数量
      TMatReserveInfor reserveInfor = matReserveInforDAO.selectByPrimaryKey(oldAcc.getReserveInforId());
      reserveInfor.setAmount(reserveInfor.getAmount().subtract(new BigDecimal(addedAmount)));//库存数量加上此增量
      if (reserveInfor.getAmount().compareTo(BigDecimal.ZERO) == -1) {//如果库存数量被提取后小于零
        throw new RuntimeException("牌号为：[" + reserveInfor.getBrandCode() + "]的产品提取库存失败!");
      }
      matReserveInforDAO.updateByPrimaryKey(reserveInfor);

      if (record.getOutAmount().compareTo(BigDecimal.ZERO) == 0) {
        matAccountsInforDAO.deleteByPrimaryKey(oldAcc.getId());
      } else {
        oldAcc.setAmount(record.getOutAmount());
        matAccountsInforDAO.updateByPrimaryKey(oldAcc);//修改账页表对应数据
      }
    }
  }

  public TOrderDetailDAO getOrderDetailDao() {
    return orderDetailDao;
  }

  public void setOrderDetailDao(TOrderDetailDAO orderDetailDao) {
    this.orderDetailDao = orderDetailDao;
  }

  public TReservePlanInforDAO getReservePlanInforDAO() {
    return reservePlanInforDAO;
  }

  public void setReservePlanInforDAO(TReservePlanInforDAO reservePlanInforDAO) {
    this.reservePlanInforDAO = reservePlanInforDAO;
  }

  public TOrderInforDAO getOrderInforDao() {
    return orderInforDao;
  }

  public void setOrderInforDao(TOrderInforDAO orderInforDao) {
    this.orderInforDao = orderInforDao;
  }

  @Override
  public PaginationSupport findReservePlans(Map params, int startIndex, int pageSize) {
    TReservePlanMainExample example = new TReservePlanMainExample();
    com.tl.resource.dao.pojo.TReservePlanMainExample.Criteria c = example.createCriteria();
    // if(params.get("productCode") != null &&
    // !"".equals(params.get("productCode"))){
    // c.andProductCodeLike("%" + params.get("productCode") + "%");
    // }
    if (params.get("contractCode") != null && !"".equals(params.get("contractCode"))) {
      c.andContractCodeLike("%" + params.get("contractCode") + "%");
    }
    if (params.get("planCode") != null && !"".equals(params.get("planCode"))) {
      c.andPlanCodeLike("%" + params.get("planCode") + "%");
    }
    if (params.get("orderCode") != null && !"".equals(params.get("orderCode"))) {
      c.andOrderCodeLike("%" + params.get("orderCode") + "%");
    }
    if (params.get("status") != null && !"".equals(params.get("status"))) {
      c.andStatusEqualTo(Integer.valueOf(params.get("status").toString()));
    }
    if (params.get("memo") != null && !"".equals(params.get("memo"))) {
      c.andMemoLike("%" + params.get("memo").toString() + "%");
    }
    if (params.get("planType") != null && !"".equals(params.get("planType"))) {
      List values = new ArrayList();
      String planTypeStr = (String) params.get("planType");
      String[] arr = planTypeStr.split("\\,");
      for (int i = 0; i < arr.length; i++) {
        values.add(arr[i]);
      }
      c.andPlanTypeIn(values);
    }
    try {
      if (params.get("startDate") != null && !"".equals(params.get("startDate"))) {
        c.andEditTimeGreaterThanOrEqualTo(df.parse(params.get("startDate").toString()));
      }
      if (params.get("endDate") != null && !"".equals(params.get("endDate"))) {
        c.andEditTimeLessThanOrEqualTo(df.parse(params.get("endDate").toString()));
      }
      if (null != params.get("year") && !"".equals(params.get("year"))) {
        String year = (String) params.get("year");
        Date startDate = df.parse(year + "-01-01");
        Date endDate = df.parse(year + "-12-31");
        c.andEditTimeBetween(startDate, endDate);
      }
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (params.get("sort") != null && params.get("dir") != null) {
      example.setOrderByClause(params.get("sort") + " " + params.get("dir"));
    } else {
      example.setOrderByClause("edit_time desc");
    }
    int count = reservePlanMainDAO.countByExample(example);
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    ArrayList<ReservePlanMainInforDto> planList = new ArrayList<ReservePlanMainInforDto>();
    List<TReservePlanMain> list = reservePlanMainDAO.selectByExample(example);
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TReservePlanMain reservePlanMain = (TReservePlanMain) iterator.next();
        ReservePlanMainInforDto mainDto = new ReservePlanMainInforDto();
        BeanUtils.copyProperties(mainDto, reservePlanMain);
        planList.add(mainDto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PaginationSupport page = new PaginationSupport(planList, count, pageSize, startIndex);
    return page;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

  @Override
  public void confirmReservePlanById(String id) {
    // TReservePlanInfor record = new TReservePlanInfor();
    // record.setId(id);
    // record.setStatus(2);
    // reservePlanInforDAO.updateByPrimaryKeySelective(record);

    TReservePlanMain record = reservePlanMainDAO.selectByPrimaryKey(id);
    record.setStatus(2);
    reservePlanMainDAO.updateByPrimaryKey(record);

    TReservePlanInforExample example = new TReservePlanInforExample();
    example.createCriteria().andMainIdEqualTo(id);
    List list = reservePlanInforDAO.selectByExample(example);
    boolean isAllOut = true;
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TReservePlanInfor repo = (TReservePlanInfor) iterator.next();
      repo.setPlanAmount(repo.getPlanAmount() == null ? BigDecimal.ZERO : repo.getPlanAmount());
      repo.setOutAmount(repo.getOutAmount() == null ? BigDecimal.ZERO : repo.getOutAmount());
      if (isAllOut && repo.getPlanAmount().compareTo(repo.getOutAmount()) != 0) {//如果计划数量不等于提取数量
        isAllOut = false;
      }
      repo.setStatus(2);
      reservePlanInforDAO.updateByPrimaryKey(repo);
      //      if (repo.getStockOrderDetailId() != null) {
      //        TOrderDetail orderDetailRecord = orderDetailDao.selectByPrimaryKey(repo.getStockOrderDetailId());
      //        orderDetailRecord.setId(repo.getStockOrderDetailId());
      //        orderDetailRecord.setOrderAmount(repo.getPlanAmount());
      //        orderDetailDao.updateByPrimaryKey(orderDetailRecord);
      //      }
    }
    if (isAllOut) {
      record.setStatus(5);
      reservePlanMainDAO.updateByPrimaryKey(record);
    }
  }

  public TReservePlanMainDAO getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAO reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

  public TMatReserveInforDAO getMatReserveInforDAO() {
    return matReserveInforDAO;
  }

  public void setMatReserveInforDAO(TMatReserveInforDAO matReserveInforDAO) {
    this.matReserveInforDAO = matReserveInforDAO;
  }

  public TMatAccountsInforDAO getMatAccountsInforDAO() {
    return matAccountsInforDAO;
  }

  public void setMatAccountsInforDAO(TMatAccountsInforDAO matAccountsInforDAO) {
    this.matAccountsInforDAO = matAccountsInforDAO;
  }

  @Override
  public List<ReservePlanDetailDto> findPlanDetailByMainId(String mainId, String status) {
    TReservePlanInforExample example = new TReservePlanInforExample();
    example.createCriteria().andMainIdEqualTo(mainId).andStatusEqualTo(Integer.valueOf(status));
    List<TReservePlanInfor> list = reservePlanInforDAO.selectByExample(example);
    List<ReservePlanDetailDto> planDto = new ArrayList<ReservePlanDetailDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TReservePlanInfor reservePlanInfor = (TReservePlanInfor) iterator.next();
        ReservePlanDetailDto dto = new ReservePlanDetailDto();
        BeanUtils.copyProperties(dto, reservePlanInfor);

        planDto.add(dto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return planDto;
  }

  public TProductToolsInforDAO getProductToolsInforDAO() {
    return productToolsInforDAO;
  }

  public void setProductToolsInforDAO(TProductToolsInforDAO productToolsInforDAO) {
    this.productToolsInforDAO = productToolsInforDAO;
  }

}
