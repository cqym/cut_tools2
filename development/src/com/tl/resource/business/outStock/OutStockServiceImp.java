package com.tl.resource.business.outStock;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ContractProductDetailDto;
import com.tl.resource.business.dto.OutStockDetailDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.QuotationDetailOutStockDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.TAccountsInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TOutStockDetailDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TReserveInforDAO;
import com.tl.resource.dao.TResourcePurviewDAO;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TAccountsInforExample;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockDetailExample;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TOutStockInforExample;
import com.tl.resource.dao.pojo.TReserveInfor;
import com.tl.resource.dao.pojo.TReserveInforExample;
import com.tl.resource.dao.pojo.TResourcePurview;
import com.tl.resource.dao.pojo.TResourcePurviewExample;
import com.tl.resource.dao.pojo.TReserveInforExample.Criteria;

public class OutStockServiceImp implements OutStockService {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private TReserveInforDAO reserveInforDAO;

  private TOutStockInforDAO outStockInforDAO;

  private TOutStockDetailDAO outStockDetailDAO;

  private TAccountsInforDAO accountsInforDAO;

  private TContractProductDetailDAO contractProductDetailDAO;

  private TQuotationProductDetailDAO quotationProductDetailDAO;

  private TResourcePurviewDAO resourcePurviewDAO;

  @Override
  public void addOutStockInfor(OutStockInforDto dto) {
    // TODO Auto-generated method stub
    TOutStockInfor record = new TOutStockInfor();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    record.setId(GenerateSerial.getUUID());
    outStockInforDAO.insert(record);

    List<OutStockDetailDto> list = dto.getOutStockDetails();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        OutStockDetailDto outStockDetailDto = (OutStockDetailDto) iterator.next();

        TReserveInfor reserveInRec = new TReserveInfor();
        reserveInRec.setId(outStockDetailDto.getReserveInforId());
        reserveInRec.setAmount(outStockDetailDto.getAmount());
        int rows = reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);
        if (rows == 0) {
          TReserveInfor tresult = reserveInforDAO.selectByPrimaryKey(outStockDetailDto.getReserveInforId());
          outStockDetailDto.setReserveAmount(tresult.getAmount());
          throw new RuntimeException(JSONObject.fromObject(outStockDetailDto).toString());//如果修改库存数量失败，则返回当前库存数量
        }
        TOutStockDetail po = new TOutStockDetail();
        reserveInRec.setId(po.getReserveInforId());
        BeanUtils.copyProperties(po, outStockDetailDto);
        po.setId(GenerateSerial.getUUID());
        po.setOutStockInforId(record.getId());
        outStockDetailDAO.insert(po);

        TAccountsInfor accountRecord = new TAccountsInfor();
        BeanUtils.copyProperties(accountRecord, outStockDetailDto);
        accountRecord.setId(GenerateSerial.getUUID());
        accountRecord.setInvoiceId(po.getId());
        accountRecord.setAccountType(2);
        accountRecord.setInvoiceCode(record.getOutStockCode());
        accountRecord.setCreateAccountTime(new Date());
        accountsInforDAO.insert(accountRecord);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    updateConOrQuoDetailOutAmount(dto);
  }

  private void updateConOrQuoDetailOutAmount(OutStockInforDto dto) {
    if (OutStockInforDto.OUT_STOCK_TYPE_CONTRACT_TQ.equals(dto.getOutStockType().toString())) {
      HashMap<String, String> para = new HashMap<String, String>();
      para.put("contractCode", dto.getContractCode());
      contractProductDetailDAO.updateContractDetailOutAmount(para);
    } else if (OutStockInforDto.OUT_STOCK_TYPE_SCHEDULE_TQ.equals(dto.getOutStockType().toString())
      || OutStockInforDto.OUT_STOCK_TYPE_TRY_TQ.equals(dto.getOutStockType().toString())) {
      HashMap<String, String> para = new HashMap<String, String>();
      para.put("quotationCode", dto.getQuotationCode());
      quotationProductDetailDAO.updateQuotationDetailOutAmount(para);
    }
    if (OutStockInforDto.OUT_STOCK_TYPE_SCHEDULE_TQ.equals(dto.getOutStockType().toString())
      || OutStockInforDto.OUT_STOCK_TYPE_TRY_TQ.equals(dto.getOutStockType().toString())) {
      contractProductDetailDAO.sycContractOutDetail(dto.getQuotationCode());
    }
  }

  @Override
  public String cancelAudit(String businessId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PaginationSupport consultReserveInfors(Map params, int startIndex, int pageSize) {
    TReserveInforExample example = new TReserveInforExample();
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    Criteria c = example.createCriteria();
    c.andAmountGreaterThan(BigDecimal.ZERO);
    if (null != params.get("brandCode") && !"".equals(params.get("brandCode"))) {
      c.andBrandCodeLike("%" + params.get("brandCode") + "%");
    }
    if (null != params.get("productCode") && !"".equals(params.get("productCode"))) {
      c.andProductCodeLike("%" + params.get("productCode") + "%");
    }
    if (null != params.get("productName") && !"".equals(params.get("productName"))) {
      c.andProductNameLike("%" + params.get("productName") + "%");
    }
    if (null != params.get("productBrand") && !"".equals(params.get("productBrand"))) {
      c.andProductBrandLike("%" + params.get("productBrand") + "%");
    }

    List list = reserveInforDAO.selectByExample(example);
    int count = reserveInforDAO.countByExample(example);

    PaginationSupport p = new PaginationSupport(list, count, pageSize, startIndex);
    return p;
  }

  @Override
  public void deleteOutStockInforDto(String id) {
    //TOutStockDetailExample example = new TOutStockDetailExample();
    //example.createCriteria().andReserveInforIdEqualTo(id);
    //outStockDetailDAO.deleteByExample(example);
    TOutStockDetailExample example = new TOutStockDetailExample();
    example.createCriteria().andOutStockInforIdEqualTo(id);
    List<TOutStockDetail> allDeleOutStockDetails = outStockDetailDAO.selectByExample(example);
    List<String> allDeleIds = new ArrayList<String>();//已删除数据id
    for (Iterator iterator = allDeleOutStockDetails.iterator(); iterator.hasNext();) {
      TOutStockDetail outStockDetail = (TOutStockDetail) iterator.next();
      TReserveInfor reserveInRec = new TReserveInfor();
      reserveInRec.setId(outStockDetail.getReserveInforId());
      reserveInRec.setAmount(BigDecimal.ZERO.subtract(outStockDetail.getAmount()));//减去负数量，等于加库存
      reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);
      allDeleIds.add(outStockDetail.getId());
    }

    if (allDeleIds.size() > 0) {
      TAccountsInforExample accontDeleEx = new TAccountsInforExample();
      accontDeleEx.createCriteria().andInvoiceIdIn(allDeleIds);
      accountsInforDAO.deleteByExample(accontDeleEx);//删除帐页

      TOutStockDetailExample example2 = new TOutStockDetailExample();
      example2.createCriteria().andIdIn(allDeleIds);//删除出库单
      outStockDetailDAO.deleteByExample(example2);
    }

    TOutStockInfor po = outStockInforDAO.selectByPrimaryKey(id);

    OutStockInforDto dto = new OutStockInforDto();
    dto.setOutStockType(po.getOutStockType());
    dto.setContractCode(po.getContractCode());
    dto.setQuotationCode(po.getQuotationCode());
    updateConOrQuoDetailOutAmount(dto);

    outStockInforDAO.deleteByPrimaryKey(id);
  }

  @Override
  public String endAudit(String businessId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PaginationSupport findOutStockInfors(Map params, int startIndex, int pageSize) {
    TOutStockInforExample example = new TOutStockInforExample();
    com.tl.resource.dao.pojo.TOutStockInforExample.Criteria c = example.createCriteria();
    if (null != params.get("contractCode") && !"".equals(params.get("contractCode"))) {
      c.andContractCodeLike("%" + params.get("contractCode") + "%");
    }
    if (null != params.get("quotationCode") && !"".equals(params.get("quotationCode"))) {
      c.andQuotationCodeLike("%" + params.get("quotationCode") + "%");
    }
    if (null != params.get("status") && !"".equals(params.get("status"))) {
      c.andStatusEqualTo(Integer.valueOf(params.get("status").toString()));
    }
    if (null != params.get("outStockCode") && !"".equals(params.get("outStockCode"))) {
      c.andOutStockCodeLike("%" + params.get("outStockCode") + "%");
    }
    if (null != params.get("memo") && !"".equals(params.get("memo"))) {
      c.andMemoLike("%" + params.get("memo") + "%");
    }
    if (null != params.get("outStockType") && !"".equals(params.get("outStockType"))) {
      String tString = params.get("outStockType").toString();
      String[] types = tString.split("\\,");
      List<Integer> list = new ArrayList<Integer>();
      for (int i = 0; i < types.length; i++) {
        list.add(Integer.valueOf(types[i]));
      }
      c.andOutStockTypeIn(list);
    }

    try {
      if (null != params.get("startDate") && !"".equals(params.get("startDate"))) {
        c.andEditDateGreaterThanOrEqualTo(df.parse(params.get("startDate").toString()));
      }
      if (null != params.get("endDate") && !"".equals(params.get("endDate"))) {
        c.andEditDateLessThanOrEqualTo(df.parse(params.get("endDate").toString()));
      }
      if (null != params.get("year") && !"".equals(params.get("year"))) {
        String year = (String) params.get("year");
        Date startDate = df.parse(year + "-01-01");
        Date endDate = df.parse(year + "-12-31");
        c.andEditDateBetween(startDate, endDate);
      }
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    TResourcePurviewExample purExp = new TResourcePurviewExample();
    purExp.createCriteria().andUserIdEqualTo((String) params.get("currentUserId")).andResourceTypeEqualTo(5);
    List userList = resourcePurviewDAO.selectByExample(purExp);
    List<String> userIds = new ArrayList<String>();
    for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
      TResourcePurview user = (TResourcePurview) iterator.next();
      userIds.add(user.getTargetUserId());
    }
    c.andUserIdIn(userIds);

    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    if (params.get("sort") != null && params.get("dir") != null) {
      example.setOrderByClause(params.get("sort").toString() + " " + params.get("dir").toString());
    } else {
      example.setOrderByClause(" edit_date desc");
    }
    List<TOutStockInfor> list = outStockInforDAO.selectByExample(example);
    int count = outStockInforDAO.countByExample(example);
    PaginationSupport ps = new PaginationSupport(list, count, pageSize, startIndex);
    return ps;
  }

  @Override
  public OutStockInforDto getOutStockInforDtoById(String id) {
    // TODO Auto-generated method stub
    TOutStockInfor osiPo = outStockInforDAO.selectByPrimaryKey(id);
    OutStockInforDto osiDto = new OutStockInforDto();
    List<OutStockDetailDto> outStockDetails = new ArrayList<OutStockDetailDto>();
    try {
      ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
      BeanUtils.copyProperties(osiDto, osiPo);
      TOutStockDetail red = new TOutStockDetail();
      red.setOutStockInforId(osiPo.getId());
      List<TOutStockDetail> list = outStockDetailDAO.selectDetailHasReserveInforByRecord(red);
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TOutStockDetail outStockDetail = (TOutStockDetail) iterator.next();
        OutStockDetailDto e = new OutStockDetailDto();
        BeanUtils.copyProperties(e, outStockDetail);
        e.setOldAmount(e.getAmount());
        outStockDetails.add(e);
      }

      osiDto.setOutStockDetails(outStockDetails);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return osiDto;
  }

  @Override
  public String submitAudit(String businessId) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 总体思路，先处理删除数据，数量减少数据，释放库存，再处理新增数据，数量增加的数据
   */
  @Override
  public void updateOutStockInfor(OutStockInforDto dto) {
    // TODO Auto-generated method stub
    TOutStockInfor record = new TOutStockInfor();
    try {
      BeanUtils.copyProperties(record, dto);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    outStockInforDAO.updateByPrimaryKeySelective(record);

    //1：获取旧数据所有id
    List<String> ids = new ArrayList<String>();//存旧数据，所有id
    List<OutStockDetailDto> list = dto.getOutStockDetails();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      OutStockDetailDto outStockDetailDto = (OutStockDetailDto) iterator.next();
      if (outStockDetailDto.getNewRecord() == null || !outStockDetailDto.getNewRecord().equals(1)) {//不是新增
        ids.add(outStockDetailDto.getId());
      }
    }
    //2.根据1，获取已经删除的数据，加库存，删帐页，删出库单
    List<String> allDeleIds = new ArrayList<String>();//已删除数据id
    TOutStockDetailExample example = new TOutStockDetailExample();
    example.createCriteria().andIdNotIn(ids).andOutStockInforIdEqualTo(record.getId());//删除本次提交明细中不包括的，原先有的数据，
    List<TOutStockDetail> allDeleOutStockDetails = outStockDetailDAO.selectByExample(example);

    for (Iterator iterator = allDeleOutStockDetails.iterator(); iterator.hasNext();) {
      TOutStockDetail outStockDetail = (TOutStockDetail) iterator.next();
      TReserveInfor reserveInRec = new TReserveInfor();
      reserveInRec.setId(outStockDetail.getReserveInforId());
      reserveInRec.setAmount(BigDecimal.ZERO.subtract(outStockDetail.getAmount()));//减去负数量，等于加库存
      reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);
      allDeleIds.add(outStockDetail.getId());
    }

    if (allDeleIds.size() > 0) {
      TAccountsInforExample accontDeleEx = new TAccountsInforExample();
      accontDeleEx.createCriteria().andInvoiceIdIn(allDeleIds);
      accountsInforDAO.deleteByExample(accontDeleEx);//删除帐页

      TOutStockDetailExample example2 = new TOutStockDetailExample();
      example2.createCriteria().andIdIn(allDeleIds);//删除出库单
      outStockDetailDAO.deleteByExample(example2);
    }
    //3.处理旧数据中，数量减少的情况，加库存，修改帐页，修改出库单
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        OutStockDetailDto outStockDetailDto = (OutStockDetailDto) iterator.next();
        if (outStockDetailDto.getNewRecord() == null || !outStockDetailDto.getNewRecord().equals(1)) {//不是新增
          BigDecimal newValue = outStockDetailDto.getAmount();
          BigDecimal oldValue = outStockDetailDto.getOldAmount();
          if (newValue.compareTo(oldValue) < 0) {//如果是减少了数量
            TReserveInfor reserveInRec = new TReserveInfor();
            reserveInRec.setId(outStockDetailDto.getReserveInforId());
            reserveInRec.setAmount(newValue.subtract(oldValue));
            int rows = reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);//减少库存
            if (rows == 0) {
              TReserveInfor tresult = reserveInforDAO.selectByPrimaryKey(outStockDetailDto.getReserveInforId());
              outStockDetailDto.setReserveAmount(tresult.getAmount());
              throw new RuntimeException(JSONObject.fromObject(outStockDetailDto).toString());//如果修改库存数量失败，则返回当前库存数量
            }

            TAccountsInfor accountRecord = new TAccountsInfor();
            accountRecord.setAmount(newValue);
            TAccountsInforExample updateExAcc = new TAccountsInforExample();
            updateExAcc.createCriteria().andInvoiceIdEqualTo(outStockDetailDto.getId());
            accountsInforDAO.updateByExampleSelective(accountRecord, updateExAcc);//修改帐页

            TOutStockDetail po = new TOutStockDetail();
            BeanUtils.copyProperties(po, outStockDetailDto);
            outStockDetailDAO.updateByPrimaryKeySelective(po);//修改出库单
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
    //4. 处理新增数据，和数量增加情况
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        OutStockDetailDto outStockDetailDto = (OutStockDetailDto) iterator.next();
        //System.out.println("------------------[" + outStockDetailDto.getNewRecord() + "]");
        if (outStockDetailDto.getNewRecord() != null && outStockDetailDto.getNewRecord().equals(1)) {//新增
          TReserveInfor reserveInRec = new TReserveInfor();
          reserveInRec.setId(outStockDetailDto.getReserveInforId());
          reserveInRec.setAmount(outStockDetailDto.getAmount());
          int rows = reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);
          if (rows == 0) {
            TReserveInfor tresult = reserveInforDAO.selectByPrimaryKey(outStockDetailDto.getReserveInforId());
            outStockDetailDto.setReserveAmount(tresult.getAmount());
            throw new RuntimeException(JSONObject.fromObject(outStockDetailDto).toString());//如果修改库存数量失败，则返回当前库存数量
          }

          TOutStockDetail po = new TOutStockDetail();
          BeanUtils.copyProperties(po, outStockDetailDto);
          po.setId(GenerateSerial.getUUID());
          po.setOutStockInforId(record.getId());
          outStockDetailDAO.insert(po);

          TAccountsInfor accountRecord = new TAccountsInfor();
          BeanUtils.copyProperties(accountRecord, outStockDetailDto);
          accountRecord.setId(GenerateSerial.getUUID());
          accountRecord.setInvoiceId(po.getId());
          accountRecord.setAccountType(2);
          accountsInforDAO.insert(accountRecord);
        } else {//如果原来就有，则是修改
          BigDecimal newValue = outStockDetailDto.getAmount();
          BigDecimal oldValue = outStockDetailDto.getOldAmount();
          if (newValue.compareTo(oldValue) > 0) {//如果是增加了数量，减少数量已经处理过了
            TReserveInfor reserveInRec = new TReserveInfor();
            reserveInRec.setId(outStockDetailDto.getReserveInforId());
            reserveInRec.setAmount(newValue.subtract(oldValue));
            int rows = reserveInforDAO.updateAmountByPrimaryKey(reserveInRec);//减少库存
            if (rows == 0) {
              TReserveInfor tresult = reserveInforDAO.selectByPrimaryKey(outStockDetailDto.getReserveInforId());
              outStockDetailDto.setReserveAmount(tresult.getAmount());
              throw new RuntimeException(JSONObject.fromObject(outStockDetailDto).toString());//如果修改库存数量失败，则返回当前库存数量
            }

            TAccountsInfor accountRecord = new TAccountsInfor();
            accountRecord.setAmount(newValue);
            TAccountsInforExample updateExAcc = new TAccountsInforExample();
            updateExAcc.createCriteria().andInvoiceIdEqualTo(outStockDetailDto.getId());
            accountsInforDAO.updateByExampleSelective(accountRecord, updateExAcc);//修改帐页

            TOutStockDetail po = new TOutStockDetail();
            BeanUtils.copyProperties(po, outStockDetailDto);
            outStockDetailDAO.updateByPrimaryKeySelective(po);
          }

        }
      }//
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    updateConOrQuoDetailOutAmount(dto);
  }

  public TReserveInforDAO getReserveInforDAO() {
    return reserveInforDAO;
  }

  public void setReserveInforDAO(TReserveInforDAO reserveInforDAO) {
    this.reserveInforDAO = reserveInforDAO;
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

  public TAccountsInforDAO getAccountsInforDAO() {
    return accountsInforDAO;
  }

  public void setAccountsInforDAO(TAccountsInforDAO accountsInforDAO) {
    this.accountsInforDAO = accountsInforDAO;
  }

  @Override
  public PaginationSupport consultContractProducts(Map params, int startIndex, int pageSize) {
    PaginationSupport pageInfor = contractProductDetailDAO.findContractDetail(params, startIndex, pageSize);
    return pageInfor;
  }

  @Override
  public PaginationSupport consultQuotationProducts(Map params, int startIndex, int pageSize) {
    // TODO Auto-generated method stub

    return quotationProductDetailDAO.getProducts4OutStock(params, startIndex, pageSize);
  }

  public TContractProductDetailDAO getContractProductDetailDAO() {
    return contractProductDetailDAO;
  }

  public void setContractProductDetailDAO(TContractProductDetailDAO contractProductDetailDAO) {
    this.contractProductDetailDAO = contractProductDetailDAO;
  }

  public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
    return quotationProductDetailDAO;
  }

  public void setQuotationProductDetailDAO(TQuotationProductDetailDAO quotationProductDetailDAO) {
    this.quotationProductDetailDAO = quotationProductDetailDAO;
  }

  @Override
  public void affirmOutStock(String id, UserDto user) {
    // TODO Auto-generated method stub
    TOutStockInfor record = new TOutStockInfor();
    record.setId(id);
    record.setStatus(1);
    record.setConfirmDate(new Date());
    record.setConfirmUserName(user.getTrueName());
    outStockInforDAO.updateByPrimaryKeySelective(record);
  }

  @Override
  public List<OutStockDetailDto> getWillOutStockContractDetail(String contractId, String leaf) {
    // TODO Auto-generated method stub
    List<ContractProductDetailDto> list = contractProductDetailDAO.getWillOutStockContractDetail(contractId, leaf);
    List<OutStockDetailDto> outList = new ArrayList<OutStockDetailDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        ContractProductDetailDto contractProductDetailDto = (ContractProductDetailDto) iterator.next();
        OutStockDetailDto dto = new OutStockDetailDto();
        BeanUtils.copyProperties(dto, contractProductDetailDto);
        dto.setContractAmount(contractProductDetailDto.getAmount());
        dto.setId(GenerateSerial.getUUID());
        dto.setContractProductDetailId(contractProductDetailDto.getId());
        dto.setAmount(BigDecimal.ZERO);
        dto.setOldAmount(BigDecimal.ZERO);
        dto.setNewRecord(1);
        dto.setAllOutAmount(contractProductDetailDto.getOutAmount());
        dto.setMemo2(contractProductDetailDto.getWorkshop());
        outList.add(dto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return outList;
  }

  @Override
  public List<OutStockDetailDto> getWillOutStockQuotationDetail(String quotationInforId, String leaf) {
    List<QuotationDetailOutStockDto> list = quotationProductDetailDAO.getQuotationProductionsWillOutStock(quotationInforId, leaf);
    List<OutStockDetailDto> outList = new ArrayList<OutStockDetailDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        QuotationDetailOutStockDto quotationDetailOutStockDto = (QuotationDetailOutStockDto) iterator.next();
        OutStockDetailDto dto = new OutStockDetailDto();
        BeanUtils.copyProperties(dto, quotationDetailOutStockDto);
        dto.setContractAmount(quotationDetailOutStockDto.getAmount());
        dto.setId(GenerateSerial.getUUID());
        dto.setContractProductDetailId(quotationDetailOutStockDto.getId());
        dto.setAmount(BigDecimal.ZERO);
        dto.setOldAmount(BigDecimal.ZERO);
        dto.setNewRecord(1);
        dto.setAllOutAmount(quotationDetailOutStockDto.getArrivalAmount());
        dto.setLeaf(quotationDetailOutStockDto.getLeaf() ? 1 : 0);
        dto.setMemo2(quotationDetailOutStockDto.getWorkshop());
        outList.add(dto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return outList;
  }

  public TResourcePurviewDAO getResourcePurviewDAO() {
    return resourcePurviewDAO;
  }

  public void setResourcePurviewDAO(TResourcePurviewDAO resourcePurviewDAO) {
    this.resourcePurviewDAO = resourcePurviewDAO;
  }

}
