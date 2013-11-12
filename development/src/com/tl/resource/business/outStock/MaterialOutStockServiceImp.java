package com.tl.resource.business.outStock;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.resource.business.dto.OutStockDetailDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOutStockDetailDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TReservePlanInforDAO;
import com.tl.resource.dao.TReservePlanMainDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractInforExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockDetailExample;
import com.tl.resource.dao.pojo.TOutStockInfor;

public class MaterialOutStockServiceImp implements MaterialOutStockService {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private TOrderInforDAO orderInforDao;

  private TOrderDetailDAO orderDetailDao;

  private TOutStockInforDAO outStockInforDAO;

  private TOutStockDetailDAO outStockDetailDAO;

  private TContractInforDAO contractInforDAO;

  private TReservePlanInforDAO reservePlanInforDAO;

  private TReservePlanMainDAO reservePlanMainDAO;

  @Override
  public OutStockInforDto consultOrderProducts(String orderId) {
    TOrderInfor orderInfor = orderInforDao.selectByPrimaryKey(orderId);
    OutStockInforDto osid = new OutStockInforDto();
    if (orderInfor.getOrderType() == 3) {
      TContractInforExample example = new TContractInforExample();
      example.createCriteria().andContractCodeEqualTo(orderInfor.getContractCode());
      List<TContractInfor> conList = contractInforDAO.selectByExample(example);
      if (conList != null && conList.size() > 0) {
        TContractInfor t = conList.get(0);
        osid.setContractId(t.getId());
        osid.setContractCode(t.getContractCode());
        osid.setCustomerCode(t.getCustomerCode());
        osid.setCustomerName(t.getCustomerName());
        osid.setMemo(t.getMemo());
      }
    } else {
      osid.setQuotationCode(orderInfor.getQuotationCode());
      osid.setQuotationId(orderInfor.getQuotationId());
      osid.setCustomerCode(orderInfor.getCustomerCode());
      osid.setCustomerName(orderInfor.getCustomerName());
      osid.setMemo(orderInfor.getMome());
    }

    osid.setOutStockType(3);
    osid.setOutStockDate(df.format(new Date()));
    List<OutStockDetailDto> outStockDetails = outStockDetailDAO.selectDetailHasOrderDetailInfor1(orderId);
    osid.setOutStockDetails(outStockDetails);
    return osid;
  }

  @Override
  public OutStockInforDto getOutStockInforById(String outStockInforId) {
    //    TOutStockDetailExample exp = new TOutStockDetailExample();
    //    exp.createCriteria().andOutStockInforIdEqualTo(outStockInforId);
    //    List<TOutStockDetail> list = outStockDetailDAO.selectByExample(exp);
    //    if (list != null && list.size() == 0)
    //      return null;
    //    TOutStockDetail det = list.get(0);
    //    TOrderDetailExample orderExp = new TOrderDetailExample();
    //    orderExp.createCriteria().andContractProductDetailIdEqualTo(det.getContractProductDetailId());
    //    List<TOrderDetail> l = orderDetailDao.selectByExample(orderExp);
    //    if (l != null && l.size() == 0)
    //      return null;
    // String orderInforId = l.get(0).getStockOrderInforId();

    //    List<OutStockDetailDto> detail = outStockDetailDAO.selectMeterialOutStockDetail(orderInforId, outStockInforId);

    TOutStockDetailExample example = new TOutStockDetailExample();
    example.createCriteria().andOutStockInforIdEqualTo(outStockInforId);
    List<TOutStockDetail> detail = outStockDetailDAO.selectByExample(example);
    List<OutStockDetailDto> newDetail = new ArrayList<OutStockDetailDto>();
    try {
      for (TOutStockDetail ele : detail) {
        OutStockDetailDto ddto = new OutStockDetailDto();
        BeanUtils.copyProperties(ddto, ele);
        newDetail.add(ddto);
      }
    } catch (IllegalAccessException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (InvocationTargetException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    OutStockInforDto dto = new OutStockInforDto();
    TOutStockInfor sipo = outStockInforDAO.selectByPrimaryKey(outStockInforId);
    try {
      BeanUtils.copyProperties(dto, sipo);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    dto.setOutStockDetails(newDetail);
    return dto;
  }

  @Override
  public void updateOutStockInfor(OutStockInforDto dto) {
    // TODO Auto-generated method stub

  }

  public TOutStockInforDAO getOutStockInforDAO() {
    return outStockInforDAO;
  }

  public void setOutStockInforDAO(TOutStockInforDAO outStockInforDAO) {
    this.outStockInforDAO = outStockInforDAO;
  }

  public TOutStockDetailDAO getOutStockDetailDAO() {
    return outStockDetailDAO;
  }

  public void setOutStockDetailDAO(TOutStockDetailDAO outStockDetailDAO) {
    this.outStockDetailDAO = outStockDetailDAO;
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

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  @Override
  public OutStockInforDto consultPlanProducts(String[] mainIds) {
    OutStockInforDto osid = new OutStockInforDto();
    /*TReservePlanMain mainInfor = reservePlanMainDAO.selectByPrimaryKey(mainId);
    TOrderInfor orderInfor = orderInforDao.selectByPrimaryKey(mainInfor.getOrderId());
    if (orderInfor != null) {
      osid.setContractCode(orderInfor.getContractCode());
      osid.setCustomerCode(orderInfor.getCustomerCode());
      osid.setCustomerName(orderInfor.getCustomerName());
      osid.setQuotationCode(orderInfor.getQuotationCode());
      osid.setQuotationId(orderInfor.getQuotationId());
    }
    TContractInforExample example = new TContractInforExample();
    example.createCriteria().andContractCodeEqualTo(mainInfor.getContractCode());
    List conInfs = contractInforDAO.selectByExample(example);
    if (!conInfs.isEmpty()) {
      TContractInfor con = (TContractInfor) conInfs.get(0);
      osid.setContractId(con.getId());
    }
    */
    osid.setOutStockType(3);
    osid.setStatus(1);
    osid.setOutStockDate(df.format(new Date()));
    osid.setOutStockDetails(convertList(reservePlanInforDAO.getReservePlanDetail(Arrays.asList(mainIds))));
    return osid;
  }

  private List<OutStockDetailDto> convertList(List<ReservePlanDetailDto> planList) {
    List<OutStockDetailDto> list = new ArrayList<OutStockDetailDto>();
    for (ReservePlanDetailDto planDto : planList) {
      OutStockDetailDto d = new OutStockDetailDto();
      try {
        BeanUtils.copyProperties(d, planDto);
        d.setContractAmount(planDto.getPlanAmount());
        d.setNeedAmount(new BigDecimal(Math.min(planDto.getReserveAmount().doubleValue(), planDto.getPlanAmount().doubleValue())));
        d.setContractProductDetailId(planDto.getId());
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      list.add(d);
    }
    return list;
  }

  public TReservePlanInforDAO getReservePlanInforDAO() {
    return reservePlanInforDAO;
  }

  public void setReservePlanInforDAO(TReservePlanInforDAO reservePlanInforDAO) {
    this.reservePlanInforDAO = reservePlanInforDAO;
  }

  public TReservePlanMainDAO getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAO reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

}
