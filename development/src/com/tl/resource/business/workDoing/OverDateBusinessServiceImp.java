package com.tl.resource.business.workDoing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.resource.business.WaitWorksInforServiceImp;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TModulesDefDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.pojo.TModulesDef;

public class OverDateBusinessServiceImp implements OverDateBusinessService {
  public static final String CONTRACT_DELIVERY_OVER_DATE_NODE_ID = "contractDeliveryOverDate";

  public static final String SCHEDULE_DELIVERY_OVER_DATE_NODE_ID = "sheduleDeliveryOverDate";

  public static final String TRY_DELIVERY_OVER_DATE_NODE_ID = "tryDeliveryOverDate";

  public static final String SCHEDULE_TO_CONTRACT_OVER_DATE_NODE_ID = "scheduleToContractOverDate";

  public static final String XIN_YV_DU_OVER_DATE_NODE_ID = "xinYvDuOverDate";

  public static final String TRY_REPORT_OVER_DATE_NODE_ID = "tryReportOverDate";

  public static final String CONTRACT_VIEW_URL = "/js/contract/contract_view_win.js";

  public static final String GENERAL_QUO_VIEW_URL = "/js/quotation/generalQuo/QuoDetailWindow.js";

  private TContractInforDAO contractInforDAO;

  private TQuotationInforDAO quotationInforDAO;

  private TModulesDefDAO modulesDefDAO;

  @Override
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor) {
    if ("".equals(pid) || "odatebroot".equals(pid)) {
      return getBusinessSortNodes(pid, loginInfor);
    } else if (CONTRACT_DELIVERY_OVER_DATE_NODE_ID.equals(pid)) {
      return getContractDeliveryOverDate(loginInfor);
    } else if (SCHEDULE_DELIVERY_OVER_DATE_NODE_ID.equals(pid)) {
      return getScheduleDeliveryOverDate(loginInfor);
    } else if (TRY_DELIVERY_OVER_DATE_NODE_ID.equals(pid)) {
      return getTryDeliveryOverDate(loginInfor);
    } else if (SCHEDULE_TO_CONTRACT_OVER_DATE_NODE_ID.equals(pid)) {
      return getScheduleToContractOverDate(loginInfor);
    } else if (XIN_YV_DU_OVER_DATE_NODE_ID.equals(pid)) {
      return getXinYuDuOverDate(loginInfor);
    } else if (TRY_REPORT_OVER_DATE_NODE_ID.equals(pid)) {
      return getTryReportOverDate(loginInfor);
    }
    return null;
  }

  private List<BusinessInforDto> getScheduleDeliveryOverDate(LoginInforDto loginInfor) {

    return getQuosOverDate(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_SCHEDULE, "delivery");
  }

  private List<BusinessInforDto> getQuosOverDate(String currUserId, String quotationType, String overDate) {
    TModulesDef m = null;
    if (QuotationDto.QUO_TYPE_SCHEDULE.equals(quotationType)) {
      m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.EXPECTED_QUO_MODULE_CODE);
    } else {
      m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.TRY_QUO_MODULE_CODE);
    }
    HashMap para = new HashMap();
    para.put("currUserId", currUserId);
    para.put("quotationType", quotationType);
    para.put("overDateType", overDate);
    List quolist = quotationInforDAO.getQuosOverDate(para);
    List<BusinessInforDto> rtList = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = quolist.iterator(); iterator.hasNext();) {
      QuotationDto quotationDto = (QuotationDto) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(quotationDto.getQuotationCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setDescription(m.getModuleName());
      dto.setUrl(GENERAL_QUO_VIEW_URL);
      dto.setId(quotationDto.getId());
      rtList.add(dto);
    }
    return rtList;
  }

  private List<BusinessInforDto> getTryReportOverDate(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    return getQuosOverDate(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_TRY, "tryReport");
  }

  private List<BusinessInforDto> getXinYuDuOverDate(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    return null;
  }

  private List<BusinessInforDto> getScheduleToContractOverDate(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    return getQuosOverDate(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_SCHEDULE, "willFormal");
  }

  private List<BusinessInforDto> getTryDeliveryOverDate(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    return getQuosOverDate(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_TRY, "delivery");
  }

  private int getQuoOverDateCount(String currUserId, String quotationType, String overDate) {
    HashMap para = new HashMap();
    para.put("currUserId", currUserId);
    para.put("quotationType", quotationType);
    para.put("overDateType", overDate);
    return quotationInforDAO.getQuosCountDeliveryOverDateSql(para);
  }

  private List<BusinessInforDto> getContractDeliveryOverDate(LoginInforDto loginInfor) {
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_MODULE_CODE);

    Map<String, Object> para = new HashMap<String, Object>();
    para.put("currUserId", loginInfor.getUser().getId());
    para.put("noYear", "2010");//不显示的年份
    List<ContractInforDto> list = contractInforDAO.contractInforsDeliveryOverDate(para);
    List<BusinessInforDto> rtList = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      ContractInforDto contractInforDto = (ContractInforDto) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(contractInforDto.getContractCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setDescription(m.getModuleName());
      dto.setUrl(CONTRACT_VIEW_URL);
      dto.setId(contractInforDto.getId());
      rtList.add(dto);
    }
    return rtList;
  }

  private List<BusinessInforDto> getBusinessSortNodes(String pid, LoginInforDto loginInfor) {
    List<BusinessInforDto> list = new ArrayList<BusinessInforDto>();
    BusinessInforDto dto = new BusinessInforDto();
    Map<String, Object> para = new HashMap<String, Object>();
    para.put("currUserId", loginInfor.getUser().getId());
    para.put("noYear", "2010");//不显示的年份
    Integer count = contractInforDAO.contractCountDeliveryOverDate(para);
    para.put("currUserId", loginInfor.getUser().getId());
    dto.setText("合同交货到期[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(CONTRACT_DELIVERY_OVER_DATE_NODE_ID);
    list.add(dto);

    count = getQuoOverDateCount(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_SCHEDULE, "delivery");
    dto = new BusinessInforDto();
    dto.setText("预定交货到期[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(SCHEDULE_DELIVERY_OVER_DATE_NODE_ID);
    list.add(dto);

    count = getQuoOverDateCount(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_TRY, "delivery");
    dto = new BusinessInforDto();
    dto.setText("试刀交货到期[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(TRY_DELIVERY_OVER_DATE_NODE_ID);
    list.add(dto);

    count = getQuoOverDateCount(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_SCHEDULE, "willFormal");
    dto = new BusinessInforDto();
    dto.setText("预订转合同到期[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(SCHEDULE_TO_CONTRACT_OVER_DATE_NODE_ID);
    list.add(dto);

    dto = new BusinessInforDto();
    dto.setText("信誉额度到期");
    dto.setParentId(pid);
    dto.setId(XIN_YV_DU_OVER_DATE_NODE_ID);
    list.add(dto);

    count = getQuoOverDateCount(loginInfor.getUser().getId(), QuotationDto.QUO_TYPE_TRY, "tryReport");
    dto = new BusinessInforDto();
    dto.setText("试刀报告到期[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(TRY_REPORT_OVER_DATE_NODE_ID);
    list.add(dto);
    return list;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

  public TModulesDefDAO getModulesDefDAO() {
    return modulesDefDAO;
  }

  public void setModulesDefDAO(TModulesDefDAO modulesDefDAO) {
    this.modulesDefDAO = modulesDefDAO;
  }

}
