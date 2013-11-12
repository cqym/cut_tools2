package com.tl.resource.business.arrival;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.ArrivalOrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.dao.TAccountsInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TMatAccountsInforDAO;
import com.tl.resource.dao.TMatReserveInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOutStockDetailDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TProductArrivalDetailDAO;
import com.tl.resource.dao.TProductArrivalInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TReserveInforDAO;
import com.tl.resource.dao.TReservePlanInforDAO;
import com.tl.resource.dao.TReservePlanMainDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TMatAccountsInfor;
import com.tl.resource.dao.pojo.TMatReserveInfor;
import com.tl.resource.dao.pojo.TMatReserveInforExample;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockDetailExample;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TProductArrivalDetail;
import com.tl.resource.dao.pojo.TProductArrivalInfor;
import com.tl.resource.dao.pojo.TReserveInfor;
import com.tl.resource.dao.pojo.TReservePlanInfor;
import com.tl.resource.dao.pojo.TReservePlanMain;
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * 到货管理service
 * @author ftl
 *
 */
public class ArrivalServiceImpl implements ArrivalService {

  //订单信息Dao
  private TOrderInforDAO orderInfoDao;

  //订单信息详细Dao
  private TOrderDetailDAO orderDetailDao;

  //到货单Dao
  private TProductArrivalInforDAO arrInfoDao;

  //到货单详细Dao
  private TProductArrivalDetailDAO arrProductDao;

  //库存Dao
  private TReserveInforDAO reserveInfoDao;

  //帐页信息DAO
  private TAccountsInforDAO accountsInfoDao;

  //供应商信息Dao
  private TSuppliersInforDAO supplierDao;

  //生成编号Service
  private BillsCodeDefService billsCodeDefService;

  //出库单Dao
  private TOutStockInforDAO outStockInfoDao;

  //出库单详细Dao
  private TOutStockDetailDAO outStockDetailDao;

  //合同Dao
  private TContractInforDAO contractInforDAO;

  private TContractProductDetailDAO contractProductDetailDAO;

  private TReservePlanInforDAO reservePlanInforDao;

  private TReservePlanMainDAO reservePlanMainDAO;

  private TMatReserveInforDAO matReserveInforDAO;

  private TMatAccountsInforDAO matAccountsInforDAO;

  private TQuotationProductDetailDAO quotationProductDetailDAO;

  public TOutStockInforDAO getOutStockInfoDao() {
    return outStockInfoDao;
  }

  public void setOutStockInfoDao(TOutStockInforDAO outStockInfoDao) {
    this.outStockInfoDao = outStockInfoDao;
  }

  public TOutStockDetailDAO getOutStockDetailDao() {
    return outStockDetailDao;
  }

  public void setOutStockDetailDao(TOutStockDetailDAO outStockDetailDao) {
    this.outStockDetailDao = outStockDetailDao;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

  public TOrderInforDAO getOrderInfoDao() {
    return orderInfoDao;
  }

  public void setOrderInfoDao(TOrderInforDAO orderInfoDao) {
    this.orderInfoDao = orderInfoDao;
  }

  public TProductArrivalInforDAO getArrInfoDao() {
    return arrInfoDao;
  }

  public void setArrInfoDao(TProductArrivalInforDAO arrInfoDao) {
    this.arrInfoDao = arrInfoDao;
  }

  public TOrderDetailDAO getOrderDetailDao() {
    return orderDetailDao;
  }

  public void setOrderDetailDao(TOrderDetailDAO orderDetailDao) {
    this.orderDetailDao = orderDetailDao;
  }

  public TProductArrivalDetailDAO getArrProductDao() {
    return arrProductDao;
  }

  public void setArrProductDao(TProductArrivalDetailDAO arrProductDao) {
    this.arrProductDao = arrProductDao;
  }

  public TReserveInforDAO getReserveInfoDao() {
    return reserveInfoDao;
  }

  public void setReserveInfoDao(TReserveInforDAO reserveInfoDao) {
    this.reserveInfoDao = reserveInfoDao;
  }

  public TAccountsInforDAO getAccountsInfoDao() {
    return accountsInfoDao;
  }

  public void setAccountsInfoDao(TAccountsInforDAO accountsInfoDao) {
    this.accountsInfoDao = accountsInfoDao;
  }

  @Override
  public List<OrderInfoDto> getOrderInfoByType(Map<String, Object> paramMap) {
    return orderInfoDao.getOrderInfoByType(paramMap);
  }

  @Override
  public Integer getOrderInfoTotalByType(Map<String, Object> paramMap) {
    return orderInfoDao.getOrderInfoTotalByType(paramMap);
  }

  @Override
  public Integer getArrInfoTotalBySearch(Map<String, Object> paramMap) {
    return arrInfoDao.getArrInfoTotalBySearch(paramMap);
  }

  public TSuppliersInforDAO getSupplierDao() {
    return supplierDao;
  }

  public void setSupplierDao(TSuppliersInforDAO supplierDao) {
    this.supplierDao = supplierDao;
  }

  @Override
  public List<TProductArrivalInfor> getArrivalInfoBySearch(Map<String, Object> paramMap) {
    return arrInfoDao.getArrivalInfoBySearch(paramMap);
  }

  @Override
  public int getOrderDetailsTotal(String orderId) {
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(orderId);
    return orderDetailDao.countByExample(example);
  }

  @Override
  public void insertArrivalInfo(TProductArrivalInfor arrInfo) {
    arrInfoDao.insertSelective(arrInfo);
  }

  @Override
  public void insertArrProduct(TProductArrivalDetail arrProduct) {
    arrProductDao.insertSelective(arrProduct);

  }

  @Override
  public List<ProductArrivalDetailDto> getArrivalDetail(String arrivalId) {
    return arrProductDao.getArrivalDetail(arrivalId);
  }

  @Override
  public void updateArrivalInfo(TProductArrivalInfor arrivalInfo) {
    arrInfoDao.updateByPrimaryKeySelective(arrivalInfo);
  }

  @Override
  public TProductArrivalDetail getArrivalDetailById(TProductArrivalDetail arrivalDetail) {
    return arrProductDao.selectByPrimaryKey(arrivalDetail.getId());
  }

  @Override
  public void updateArrivalDetail(TProductArrivalDetail arrivalDetail) {
    arrProductDao.updateByPrimaryKeySelective(arrivalDetail);

  }

  @Override
  public void deleteArrivalDetail(String id) {
    arrProductDao.deleteByPrimaryKey(id);
  }

  @Override
  public TProductArrivalDetail getArrDetailWithChildren(String arrivalId) {
    return arrProductDao.getArrDetailWithChildren(arrivalId);
  }

  @Override
  public void deleteArrivalDetail(List<ProductArrivalDetailDto> list) {
    if (list != null && list.size() > 0) {
      for (ProductArrivalDetailDto arrDetail : list) {
        this.deleteArrivalDetail(arrDetail.getId());
      }
    }
  }

  @Override
  public List<OrderInfoDto> getOrderInfoByCode(Map<String, Object> paramMap) {
    return orderInfoDao.getOrderInfoByCode(paramMap);
  }

  @Override
  public TProductArrivalInfor getArrivalInfoById(String id) {
    return arrInfoDao.selectByPrimaryKey(id);
  }

  @Override
  public void deleteArrivalInfo(String id) {
    arrInfoDao.deleteByPrimaryKey(id);
  }

  @Override
  public TReserveInfor getReserveInfoByProCode(String id) {
    return reserveInfoDao.getReserveInfoByProCode(id);
  }

  @Override
  public void insertReserveInfo(TReserveInfor reserveInfo) {
    reserveInfoDao.insertSelective(reserveInfo);
  }

  @Override
  public void updateReserveInfo(TReserveInfor reserveInfo) {
    reserveInfoDao.updateByPrimaryKeySelective(reserveInfo);
  }

  @Override
  public void insertAccountsInfo(TAccountsInfor accountsInfo) {
    accountsInfoDao.insertSelective(accountsInfo);
  }

  @Override
  public void updateAccountsInfo(TAccountsInfor accountsInfo) {
    accountsInfoDao.updateByPrimaryKeySelective(accountsInfo);
  }

  @Override
  public TSuppliersInfor getSupplierById(String id) {
    return supplierDao.selectByPrimaryKey(id);
  }

  @Override
  public List<ArrivalOrderDetialsDto> getDetailWithHasArrivalByOrderId(String orderId) {
    return orderDetailDao.getDetailWithHasArrivalByOrderId(orderId);
  }

  @Override
  public OrderInfoDto getOrderInfoByCode(String orderCode) {
    // TODO Auto-generated method stub
    return orderInfoDao.getOrderInfoByCode(orderCode);
  }

  @Override
  public TProductArrivalInfor getArrInfoWithProById(String arrInfoId) {
    return arrInfoDao.getArrInfoWithProById(arrInfoId);
  }

  @Override
  public void addReveiveInfo(UserDto userDto, TProductArrivalInfor arrInfo) throws Exception {
    List<TProductArrivalDetail> list = arrInfo.getArrivalProducts();//到货产品
    if (list == null || list.size() == 0) {
      return;
    }
    //根据订单编号获取订单
    OrderInfoDto orderInfo = this.getOrderInfoByCode(arrInfo.getOrderCode());
    TContractInfor contract = contractInforDAO.getContractByCode(orderInfo.getContractCode());
    //订单类型
    Integer orderType = orderInfo.getOrderType();
    TOutStockInfor outStockInfo = new TOutStockInfor();
    //获取供应商编号
    TSuppliersInfor supp = supplierDao.selectByPrimaryKey(arrInfo.getSupplierId());
    String suppCode = "";
    if (supp != null)
      suppCode = supp.getSupplierCode();
    //客户编号
    String customerCode = "";
    if (arrInfo.getCustomerCode() != null)
      customerCode = arrInfo.getCustomerCode();

    //先删一次入库对应的出库（可能是修改状态二次确认），以保证本次插入能成功
    TOutStockDetailExample delOutDetailExample = new TOutStockDetailExample();
    delOutDetailExample.createCriteria().andOutStockInforIdEqualTo(arrInfo.getId());
    outStockDetailDao.deleteByExample(delOutDetailExample);
    outStockInfoDao.deleteByPrimaryKey(arrInfo.getId());

    if (orderType == 1 || orderType == 3) {
      //若为合同订单首先在出库单主表中添加出库单
      BeanUtils.copyProperties(outStockInfo, arrInfo);
      outStockInfo.setId(arrInfo.getId());
      outStockInfo.setContractId(contract.getId());
      outStockInfo.setOutStockType(4);//1 合同出库(采购)
      outStockInfo.setEditDate(new Date());//编制日期
      outStockInfo.setUserId(userDto.getId());//编制人ID
      outStockInfo.setUserName(userDto.getUserName());//编制人名称
      outStockInfo.setConfirmDate(new Date());
      outStockInfo.setConfirmUserName(userDto.getUserName());
      outStockInfo.setOutStockCode(billsCodeDefService.getBillCode("08", null, null, null));//出库单编号
      outStockInfoDao.insertSelective(outStockInfo);
    } else if (orderType == 5 || orderType == 7) {
      //预订订单出库
      BeanUtils.copyProperties(outStockInfo, arrInfo);
      outStockInfo.setId(arrInfo.getId());
      //outStockInfo.setContractId(contract.getId());
      outStockInfo.setOutStockType(2);//1 预订报价单出库(采购)
      outStockInfo.setEditDate(new Date());//编制日期
      outStockInfo.setUserId(userDto.getId());//编制人ID
      outStockInfo.setUserName(userDto.getUserName());//编制人名称
      outStockInfo.setConfirmDate(new Date());
      outStockInfo.setConfirmUserName(userDto.getUserName());
      outStockInfo.setOutStockCode(billsCodeDefService.getBillCode("08", null, null, null));//出库单编号
      outStockInfoDao.insertSelective(outStockInfo);
    } else if (orderType == 6 || orderType == 8) {
      //试刀订单出库
      BeanUtils.copyProperties(outStockInfo, arrInfo);
      outStockInfo.setId(arrInfo.getId());
      //outStockInfo.setContractId(contract.getId());
      outStockInfo.setOutStockType(7);//1 试刀出库(采购)
      outStockInfo.setEditDate(new Date());//编制日期
      outStockInfo.setUserId(userDto.getId());//编制人ID
      outStockInfo.setUserName(userDto.getUserName());//编制人名称
      outStockInfo.setConfirmDate(new Date());
      outStockInfo.setConfirmUserName(userDto.getUserName());
      outStockInfo.setOutStockCode(billsCodeDefService.getBillCode(buildCodeType(outStockInfo.getOutStockType()), suppCode, customerCode, null));//出库单编号
      outStockInfoDao.insertSelective(outStockInfo);
    }

    for (TProductArrivalDetail arrDetail : list) {
      TOrderDetail orderDetail = this.orderDetailDao.selectByPrimaryKey(arrDetail.getOrderDetailId());

      //库存账页表
      TAccountsInfor accountsInfo = new TAccountsInfor();
      BeanUtils.copyProperties(accountsInfo, arrDetail);//属性copy

      accountsInfo.setId(GenerateSerial.getUUID());
      accountsInfo.setAmount(arrDetail.getArrivalAmount());//到货数量
      accountsInfo.setMoney(arrDetail.getProductMoney());//金额
      if (orderType == 2) {
        //储备订单入库业务流程
        //如果库存表中还未有该产品信息则在库存表中添加该产品信息库存信息，否则只对库存容量进行修改 此两种操作都需要同时操作 库存账页表
        //库存表
        TReserveInfor reserveInfo = this.getReserveInfoByProCode(arrDetail.getProductCode());
        if (reserveInfo == null) {
          //库存
          reserveInfo = new TReserveInfor();
          BeanUtils.copyProperties(reserveInfo, arrDetail);//属性copy
          reserveInfo.setId(GenerateSerial.getUUID());
          reserveInfo.setAmount(arrDetail.getArrivalAmount());//库存表中库存数量
          this.insertReserveInfo(reserveInfo);

          //库存帐页
          accountsInfo.setReserveInforId(reserveInfo.getId());
          accountsInfo.setInvoiceId(arrDetail.getId());
          accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
          accountsInfo.setCreateAccountTime(new Date());
          accountsInfo.setAccountType(1); //设置帐页类型 0为初始库存 1为入库
          this.insertAccountsInfo(accountsInfo);
        } else {
          reserveInfo.setAmount(reserveInfo.getAmount().add(arrDetail.getArrivalAmount()));
          this.updateReserveInfo(reserveInfo);
          accountsInfo.setAccountType(1);
          accountsInfo.setReserveInforId(reserveInfo.getId());
          accountsInfo.setInvoiceId(arrDetail.getId());
          accountsInfo.setCreateAccountTime(new Date());
          accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
          this.insertAccountsInfo(accountsInfo);
        }
      } else if (orderType == 4) {
        //材料入库 库存表中库存数量始终为0 每次入库只维护帐页表
        TMatReserveInforExample example = new TMatReserveInforExample();
        example.createCriteria().andProductCodeEqualTo(arrDetail.getProductCode());
        List<TMatReserveInfor> matReserveInfos = matReserveInforDAO.selectByExample(example);
        if (matReserveInfos.size() == 0) {
          //库存
          TMatReserveInfor reserveInfo = new TMatReserveInfor();
          BeanUtils.copyProperties(reserveInfo, arrDetail);//属性copy
          reserveInfo.setId(GenerateSerial.getUUID());
          reserveInfo.setAmount(BigDecimal.ZERO);//库存表中库存数量
          matReserveInforDAO.insert(reserveInfo);

          //库存帐页
          TMatAccountsInfor matAccou = new TMatAccountsInfor();
          matAccou.setId(GenerateSerial.getUUID());
          matAccou.setReserveInforId(reserveInfo.getId());
          matAccou.setInvoiceId(arrDetail.getId());
          matAccou.setInvoiceCode(arrInfo.getArrivalCode());
          matAccou.setCreateAccountTime(new Date());
          matAccou.setAccountType(1); //设置帐页类型 0为初始库存 1为入库 
          matAccou.setAmount(arrDetail.getArrivalAmount());
          matAccountsInforDAO.insert(matAccou);

          TReservePlanInfor planDetail = reservePlanInforDao.selectByPrimaryKey(orderDetail.getContractProductDetailId());
          TReservePlanMain mainPlan = reservePlanMainDAO.selectByPrimaryKey(planDetail.getMainId());
          TMatAccountsInfor matAccouOut = new TMatAccountsInfor();
          matAccouOut.setId(GenerateSerial.getUUID());
          matAccouOut.setReserveInforId(reserveInfo.getId());
          matAccouOut.setInvoiceId(orderDetail.getContractProductDetailId());
          matAccouOut.setInvoiceCode(mainPlan.getPlanCode());
          matAccouOut.setCreateAccountTime(new Date());
          matAccouOut.setAmount(arrDetail.getArrivalAmount());
          matAccouOut.setAccountType(2); //设置帐页类型 0为初始库存 2为出库
          matAccountsInforDAO.insert(matAccouOut);

        } else {
          TMatReserveInfor reserveInfo = matReserveInfos.get(0);
          TMatAccountsInfor matAccou = new TMatAccountsInfor();
          matAccou.setId(GenerateSerial.getUUID());
          matAccou.setAccountType(1); //设置帐页类型 0为初始库存 1为入库 4 材料入库
          matAccou.setReserveInforId(reserveInfo.getId());
          matAccou.setInvoiceId(arrDetail.getId());
          matAccou.setCreateAccountTime(new Date());
          matAccou.setInvoiceCode(arrInfo.getArrivalCode());
          matAccou.setAmount(arrDetail.getArrivalAmount());
          matAccountsInforDAO.insert(matAccou);

          TReservePlanInfor planDetail = reservePlanInforDao.selectByPrimaryKey(orderDetail.getContractProductDetailId());
          TReservePlanMain mainPlan = reservePlanMainDAO.selectByPrimaryKey(planDetail.getMainId());
          TMatAccountsInfor matAccouOut = new TMatAccountsInfor();
          matAccouOut.setId(GenerateSerial.getUUID());
          matAccouOut.setReserveInforId(reserveInfo.getId());
          matAccouOut.setInvoiceId(orderDetail.getContractProductDetailId());
          matAccouOut.setInvoiceCode(mainPlan.getPlanCode());
          matAccouOut.setCreateAccountTime(new Date());
          matAccouOut.setAccountType(2); //设置帐页类型 0为初始库存 2为出库
          matAccouOut.setAmount(arrDetail.getArrivalAmount());
          matAccountsInforDAO.insert(matAccouOut);
        }

        reservePlanInforDao.updateReservePlanArrivalAmountById(orderDetail.getContractProductDetailId());

      } else if (orderType == 1 || orderType == 3 || orderType == 5 || orderType == 6 || orderType == 7 || orderType == 8) {
        //合同订单 不入库存表直接入帐页表，同时直接出库
        //库存表
        TReserveInfor reserveInfo = this.getReserveInfoByProCode(arrDetail.getProductCode());
        if (reserveInfo == null) {
          reserveInfo = new TReserveInfor();
          BeanUtils.copyProperties(reserveInfo, arrDetail);//属性copy
          reserveInfo.setId(GenerateSerial.getUUID());
          reserveInfo.setAmount(BigDecimal.ZERO);//库存表中库存数量为0
          this.insertReserveInfo(reserveInfo);
        }
        //库存账页入库
        accountsInfo.setAccountType(1);//1 入库
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(arrDetail.getId());
        accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
        accountsInfo.setCreateAccountTime(new Date());
        this.insertAccountsInfo(accountsInfo);

        //出库单出库
        TOutStockDetail outStockDetail = new TOutStockDetail();
        BeanUtils.copyProperties(outStockDetail, arrDetail);

        outStockDetail.setId(GenerateSerial.getUUID());
        outStockDetail.setAmount(arrDetail.getArrivalAmount());//出库数量
        outStockDetail.setReserveInforId(reserveInfo.getId());//库存表
        outStockDetail.setOutStockInforId(outStockInfo.getId());//出库单ID
        outStockDetail.setMoney(arrDetail.getProductMoney());//金额

        outStockDetail.setContractProductDetailId(orderDetail.getContractProductDetailId());//合同明细Id
        outStockDetail.setContractProjectSortId(orderDetail.getContractProjectSortId());
        outStockDetail.setProSortName(orderDetail.getProSortName());
        outStockDetail.setProjectCode(orderDetail.getProjectCode());
        if (orderDetail.getSerialNumber() != null)
          outStockDetail.setSerialNumber(orderDetail.getSerialNumber().intValue());
        outStockDetail.setContractAmount(orderDetail.getContractAmount());//合同数量

        outStockDetailDao.insertSelective(outStockDetail);

        //库存账页出库
        accountsInfo.setId(GenerateSerial.getUUID());
        accountsInfo.setAccountType(2);//2 出库
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(outStockDetail.getId());//存的是出库详细ID
        accountsInfo.setInvoiceCode(outStockInfo.getOutStockCode());
        accountsInfo.setCreateAccountTime(new Date());
        this.insertAccountsInfo(accountsInfo);
      }
    }
    TProductArrivalInfor i = new TProductArrivalInfor();
    i.setId(arrInfo.getId());
    i.setStatus(arrInfo.getStatus());
    i.setConfirmDate(new Date());
    i.setConfirmUserName(userDto.getUserName());
    this.updateArrivalInfo(i);

  }

  @Override
  public void insertOutStockDetail(TOutStockDetail outStockDetail) {
    // TODO Auto-generated method stub

  }

  @Override
  public void insertOutStockInfor(TOutStockInfor outStockInfo) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateOrderByIdSelective(TOrderInfor orderInfo) {
    this.orderInfoDao.updateByPrimaryKeySelective(orderInfo);
  }

  @Override
  public ArrivalInforDto getArrInfoWithOrderType(String arrInfoId) {
    return arrInfoDao.getArrInfoWithOrderType(arrInfoId);
  }

  @Override
  public Integer getCanArrivalPro(String orderId) {
    return orderDetailDao.getCanArrivalPro(orderId);
  }

  @Override
  public List<ProductArrivalDetailDto> getArrivalDetailByView(String arrivalId) {
    return arrProductDao.getArrivalDetailByView(arrivalId);
  }

  @Override
  public String arrivalSubmit(JSONArray array, UserDto userDto) {
    String resultStr = "{success : true, msg : '确认入库提交成功！'}";
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String arrInfoId = iterator.next();
      TProductArrivalInfor arrInfo = this.getArrInfoWithProById(arrInfoId);
      if (arrInfo != null) {
        try {
          arrInfo.setStatus(1);
          this.addReveiveInfo(userDto, arrInfo);

          String orderId = arrInfo.getOrderInforId();
          Integer arrTotal = this.getCanArrivalPro(orderId);

          OrderInfoDto orderDto = this.getOrderInfoByCode(arrInfo.getOrderCode());
          //如果arrtotal= 0
          if (arrTotal == 0 && orderDto.getStatus() != 5) {
            TOrderInfor orderInfo = new TOrderInfor();
            orderInfo.setId(orderId);
            orderInfo.setStatus(5);
            try {
              this.updateOrderByIdSelective(orderInfo);
            } catch (Exception e) {
              e.printStackTrace();
              resultStr = "{success : false, msg : '确认入库：修改订单状态提交失败！'}";
            }
            /*if(!orderIdList.contains(orderId)) {
            	orderIdList.add(orderId);
            }*/
          }
          if (ArrivalInforDto.ARRIVAL_TYPE_CONTRACT.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_CONTRACT_SELF.equals(arrInfo.getArrivalType())) {
            updateContractDetailArrivalAmount(arrInfo.getContractCode());
          } else if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
            updateQuotationDetailArrivalAmount(arrInfo.getQuotationCode());
          }
          if (ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_SCHEDLE_SELF.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY.equals(arrInfo.getArrivalType())
            || ArrivalInforDto.ARRIVAL_TYPE_TRY_SELF.equals(arrInfo.getArrivalType())) {
            sycContractArrivalDetail(arrInfo.getQuotationCode());
          }
        } catch (Exception e) {
          e.printStackTrace();
          resultStr = "{success : false, msg : '确认入库提交失败！'}";
        }

      }

    }
    return resultStr;
  }

  @Override
  public void sycContractArrivalDetail(String quotationCode) {
    // TODO Auto-generated method stub
    contractProductDetailDAO.sycContractArrivalDetail(quotationCode);
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  @Override
  public Integer getDirectArrTotalBySearch(Map<String, Object> paramMap) {
    return this.arrInfoDao.getDirectArrTotalBySearch(paramMap);
  }

  @Override
  public List<TProductArrivalInfor> getDirectArrivalBySearch(Map<String, Object> paramMap) {
    return this.arrInfoDao.getDirectArrivalBySearch(paramMap);
  }

  @Override
  public List<TProductArrivalDetail> getDirectArrDetail(String arrivalId) {
    return this.arrProductDao.getDirectArrDetail(arrivalId);
  }

  @Override
  public TProductArrivalInfor getDirectArrInfoById(String id) {
    return arrInfoDao.getDirectArrInfoById(id);
  }

  @Override
  public String directArrivalAudit(JSONArray array, UserDto userDto) {
    //直接入库 确认入库
    String resultStr = "{success : true, msg : '确认入库提交成功！'}";
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String arrInfoId = iterator.next();
      TProductArrivalInfor arrInfo = this.getArrInfoWithProById(arrInfoId);

      if (arrInfo != null) {
        try {
          arrInfo.setStatus(1);
          if (arrInfo.getArrivalType() == null) {
            throw new Exception("入库类型为空，不能确认所入库房!");
          }
          if (arrInfo.getArrivalType() == 0) {
            this.addDirectReveive(userDto, arrInfo);
          } else if (arrInfo.getArrivalType() == -1) {
            this.addMatDirectReveive(userDto, arrInfo);
          }
        } catch (Exception e) {
          e.printStackTrace();
          resultStr = "{success : false, msg : '确认入库提交失败！'}";
        }

      }

    }
    return resultStr;
  }

  private void addMatDirectReveive(UserDto userDto, TProductArrivalInfor arrInfo) throws IllegalAccessException, InvocationTargetException {
    // TODO Auto-generated method stub
    List<TProductArrivalDetail> list = arrInfo.getArrivalProducts();//到货产品
    if (list == null || list.size() == 0) {
      return;
    }

    for (TProductArrivalDetail arrDetail : list) {
      //库存表
      TMatReserveInforExample matResDao = new TMatReserveInforExample();
      matResDao.createCriteria().andProductCodeEqualTo(arrDetail.getProductCode());
      List<TMatReserveInfor> listres = matReserveInforDAO.selectByExample(matResDao);
      TMatReserveInfor reserveInfo = new TMatReserveInfor();
      if (listres.size() == 0) {
        reserveInfo = null;
      } else {
        reserveInfo = listres.get(0);
      }
      //库存账页表
      TMatAccountsInfor accountsInfo = new TMatAccountsInfor();
      BeanUtils.copyProperties(accountsInfo, arrDetail);//属性copy

      accountsInfo.setId(GenerateSerial.getUUID());
      accountsInfo.setAmount(arrDetail.getArrivalAmount());//到货数量
      accountsInfo.setMoney(arrDetail.getProductMoney());//金额
      //储备订单入库业务流程
      //如果库存表中还未有该产品信息则在库存表中添加该产品信息库存信息，否则只对库存容量进行修改 此两种操作都需要同时操作 库存账页表
      if (reserveInfo == null) {
        //库存
        reserveInfo = new TMatReserveInfor();
        BeanUtils.copyProperties(reserveInfo, arrDetail);//属性copy
        reserveInfo.setId(GenerateSerial.getUUID());
        reserveInfo.setAmount(arrDetail.getArrivalAmount());//库存表中库存数量
        matReserveInforDAO.insert(reserveInfo);

        //库存帐页
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(arrDetail.getId());//单据ID存的是 单据详细ID
        accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
        accountsInfo.setCreateAccountTime(new Date());
        accountsInfo.setAccountType(1); //设置帐页类型 0为初始库存 1 入库
        matAccountsInforDAO.insert(accountsInfo);
      } else {
        reserveInfo.setAmount(reserveInfo.getAmount().add(arrDetail.getArrivalAmount()));
        matReserveInforDAO.updateByPrimaryKey(reserveInfo);
        accountsInfo.setAccountType(1);
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(arrDetail.getId());//单据ID存的是 单据详细ID
        accountsInfo.setCreateAccountTime(new Date());
        accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
        matAccountsInforDAO.insert(accountsInfo);
      }
    }
    TProductArrivalInfor i = new TProductArrivalInfor();
    i.setId(arrInfo.getId());
    i.setStatus(arrInfo.getStatus());
    this.updateArrivalInfo(i);
  }

  //直接入库，库存业务流程
  private void addDirectReveive(UserDto userDto, TProductArrivalInfor arrInfo) throws Exception {
    List<TProductArrivalDetail> list = arrInfo.getArrivalProducts();//到货产品
    if (list == null || list.size() == 0) {
      return;
    }

    for (TProductArrivalDetail arrDetail : list) {
      //库存表
      TReserveInfor reserveInfo = this.getReserveInfoByProCode(arrDetail.getProductCode());
      //库存账页表
      TAccountsInfor accountsInfo = new TAccountsInfor();
      BeanUtils.copyProperties(accountsInfo, arrDetail);//属性copy

      accountsInfo.setId(GenerateSerial.getUUID());
      accountsInfo.setAmount(arrDetail.getArrivalAmount());//到货数量
      accountsInfo.setMoney(arrDetail.getProductMoney());//金额
      //储备订单入库业务流程
      //如果库存表中还未有该产品信息则在库存表中添加该产品信息库存信息，否则只对库存容量进行修改 此两种操作都需要同时操作 库存账页表
      if (reserveInfo == null) {
        //库存
        reserveInfo = new TReserveInfor();
        BeanUtils.copyProperties(reserveInfo, arrDetail);//属性copy
        reserveInfo.setId(GenerateSerial.getUUID());
        reserveInfo.setAmount(arrDetail.getArrivalAmount());//库存表中库存数量
        this.insertReserveInfo(reserveInfo);

        //库存帐页
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(arrDetail.getId());//单据ID存的是 单据详细ID
        accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
        accountsInfo.setCreateAccountTime(new Date());
        accountsInfo.setAccountType(1); //设置帐页类型 0为初始库存 1 入库
        this.insertAccountsInfo(accountsInfo);
      } else {
        reserveInfo.setAmount(reserveInfo.getAmount().add(arrDetail.getArrivalAmount()));
        this.updateReserveInfo(reserveInfo);
        accountsInfo.setAccountType(1);
        accountsInfo.setReserveInforId(reserveInfo.getId());
        accountsInfo.setInvoiceId(arrDetail.getId());//单据ID存的是 单据详细ID
        accountsInfo.setCreateAccountTime(new Date());
        accountsInfo.setInvoiceCode(arrInfo.getArrivalCode());
        this.insertAccountsInfo(accountsInfo);
      }
    }
    TProductArrivalInfor i = new TProductArrivalInfor();
    i.setId(arrInfo.getId());
    i.setStatus(arrInfo.getStatus());
    this.updateArrivalInfo(i);
  }

  @Override
  public List<ProductArrivalDetailDto> getDetail4Direct(String arrivalId) {
    return this.arrProductDao.getDetail4Direct(arrivalId);
  }

  @Override
  public Integer invalidArrivalInfo(Iterator<String> iterator) {
    Integer num = 0;
    while (iterator.hasNext()) {
      String arrId = iterator.next();
      TProductArrivalInfor arrInfo = new TProductArrivalInfor();
      arrInfo.setId(arrId);
      arrInfo.setStatus(-1);
      num += this.updateStatus(arrInfo);
    }
    return num;
  }

  @Override
  public Integer updateStatus(TProductArrivalInfor arrInfo) {
    return this.arrInfoDao.updateStatus(arrInfo);
  }

  public TContractProductDetailDAO getContractProductDetailDAO() {
    return contractProductDetailDAO;
  }

  public void setContractProductDetailDAO(TContractProductDetailDAO contractProductDetailDAO) {
    this.contractProductDetailDAO = contractProductDetailDAO;
  }

  @Override
  public void updateContractDetailArrivalAmount(String contractCode) {
    // TODO Auto-generated method stub
    HashMap<String, String> para = new HashMap<String, String>();
    para.put("contractCode", contractCode);
    contractProductDetailDAO.updateContractDetailArrivalAmount(para);
  }

  public TReservePlanInforDAO getReservePlanInforDao() {
    return reservePlanInforDao;
  }

  public void setReservePlanInforDao(TReservePlanInforDAO reservePlanInforDao) {
    this.reservePlanInforDao = reservePlanInforDao;
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

  public TReservePlanMainDAO getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAO reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

  @Override
  public void updateQuotationDetailArrivalAmount(String quotationCode) {
    HashMap<String, String> para = new HashMap<String, String>();
    para.put("quotationCode", quotationCode);
    quotationProductDetailDAO.updateQuotationDetailArrivalAmount(para);
  }

  public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
    return quotationProductDetailDAO;
  }

  public void setQuotationProductDetailDAO(TQuotationProductDetailDAO quotationProductDetailDAO) {
    this.quotationProductDetailDAO = quotationProductDetailDAO;
  }

  private String buildCodeType(Integer outType) {
    //0 直接出库 1 合同出库(提取库存) 2 预定报价单出库(采购)，3 材料出库,
    //4 合同出库(采购),5 预定报价单出库(提取库存), 6 试刀(提取库存) 7 试刀(采购)
    String codeType = "";
    switch (outType) {
    case 2:
      codeType = "18";
      break;
    case 4:
      codeType = "08";
      break;
    case 7:
      codeType = "19";
      break;

    }
    return codeType;
  }
}
