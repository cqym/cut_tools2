package com.tl.resource.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.audit.dto.PageHeaderInfor;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.AuditDao;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TModulesDefDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TReservePlanMainDAO;
import com.tl.resource.dao.TResourcePurviewDAO;
import com.tl.resource.dao.pojo.AuditTypeFlowInfor;
import com.tl.resource.dao.pojo.TModulesDef;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationInforExample;
import com.tl.resource.dao.pojo.TReservePlanMainExample;
import com.tl.resource.dao.pojo.TResourcePurview;
import com.tl.resource.dao.pojo.TResourcePurviewExample;

public class WaitWorksInforServiceImp implements WaitWorksInforService {
  public static final String ORDER_MODULE_CODE = "005";

  public static final String CONTRACT_ORDER_MODULE_CODE = "005002";

  public static final String CONTRACT_ORDER_ADD_MODULE_CODE = "005002001";

  public static final String CONTRACT_MODULE_CODE = "004";

  public static final String CONTRACT_ADD_MODULE_CODE = "004001";

  public static final String EXPECTED_ORDER_MODULE_CODE = "005006";

  public static final String EXPECTED_ADD_ORDER_MODULE_CODE = "005006001";

  public static final String EXPECTED_SELF_ORDER_MODULE_CODE = "009003";

  public static final String EXPECTED_ADD_SELF_ORDER_MODULE_CODE = "009003001";

  public static final String TRY_TOOLS_ORDER_MODULE_CODE = "005007";

  public static final String TRY_TOOLS_ADD_ORDER_MODULE_CODE = "005007001";

  public static final String TRY_TOOLS_SELF_ORDER_MODULE_CODE = "009004";

  public static final String TRY_TOOLS_ADD_SELF_ORDER_MODULE_CODE = "009004001";

  public static final String CONTRACT_DELIVERY_MODULE_CODE = "008001";

  public static final String EXPECTED_DELIVERY_MODULE_CODE = "008002";

  public static final String TRY_DELIVERY_MODULE_CODE = "008003";

  public static final String PLAN_MODULE_CODE = "009001";

  public static final String EXPECTED_QUO_MODULE_CODE = "003003";

  public static final String TRY_QUO_MODULE_CODE = "003004";

  private AuditDao auditDao;

  private TQuotationInforDAO quoInfoDAO;

  private TContractInforDAO contractInforDAO;

  private TResourcePurviewDAO resourcePurviewDAO;

  private TModulesDefDAO modulesDefDAO;

  private TOrderInforDAO orderInforDao;

  private TReservePlanMainDAO reservePlanMainDAO;

  @Override
  public int findWaitAuditInfors(LoginInforDto loginInfor) {
    List<AuditTypeFlowInfor> list = auditDao.findWaitAuditTypeInfor(loginInfor.getUser());
    if (list == null)
      return 0;
    int count = 0;
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      AuditTypeFlowInfor auditTypeFlowInfor = (AuditTypeFlowInfor) iterator.next();
      count += auditTypeFlowInfor.getCount();
    }
    return count;
  }

  @Override
  public PaginationSupport findWaitContract2OrderInfors(LoginInforDto loginInfor, int startIndex, int pageSize, int orderType) {
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), CONTRACT_ORDER_MODULE_CODE);//合同订单模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (CONTRACT_ORDER_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return null;
    List<PageHeaderInfor> headerlist = new ArrayList<PageHeaderInfor>();
    PageHeaderInfor phi1 = new PageHeaderInfor();
    phi1.setHeader("id");
    phi1.setDataIndex("id");
    phi1.setSortable(false);
    phi1.setHidden(true);
    headerlist.add(phi1);

    PageHeaderInfor phi2 = new PageHeaderInfor();
    phi2.setHeader("合同编号");
    phi2.setDataIndex("contract_code");
    phi2.setSortable(true);
    phi2.setHidden(false);
    phi2.setWidth(200);
    headerlist.add(phi2);

    List<LinkBusinessObject> bolist = new ArrayList<LinkBusinessObject>();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("conType", "orderOut");
    params.put("leaf", orderType);
    params.put("status", 4);
    params.put("startIndex", startIndex);
    params.put("pageSize", pageSize);

    List<ContractInforDto> conList = contractInforDAO.getContractList(params);
    for (Iterator iterator = conList.iterator(); iterator.hasNext();) {
      com.tl.resource.business.dto.ContractInforDto dto = (com.tl.resource.business.dto.ContractInforDto) iterator.next();
      LinkBusinessObject bo = new LinkBusinessObject();
      bo.setId(dto.getId());
      bo.setName(dto.getContractCode());
      bo.addProperty("id", dto.getId());
      bo.addProperty("contract_code", dto.getContractCode());
      bolist.add(bo);
    }
    int totalCount = contractInforDAO.getContractListCount(params);

    PaginationSupport pageInfor = new PaginationSupport(bolist, totalCount, pageSize, startIndex);
    pageInfor.setHeaders(headerlist);
    return pageInfor;
  }

  @Override
  public PaginationSupport findWaitQuotation2ContractInfors(LoginInforDto loginInfor, int startIndex, int pageSize) {
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), CONTRACT_MODULE_CODE);//合同模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (CONTRACT_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return null;
    List<PageHeaderInfor> headerlist = new ArrayList<PageHeaderInfor>();
    PageHeaderInfor phi1 = new PageHeaderInfor();
    phi1.setHeader("id");
    phi1.setDataIndex("id");
    phi1.setSortable(false);
    phi1.setHidden(true);
    headerlist.add(phi1);

    PageHeaderInfor phi2 = new PageHeaderInfor();
    phi2.setHeader("报价单编号");
    phi2.setDataIndex("QuotationCode");
    phi2.setSortable(true);
    phi2.setHidden(false);
    phi2.setWidth(200);
    headerlist.add(phi2);

    PageHeaderInfor phi3 = new PageHeaderInfor();
    phi3.setHeader("币别");
    phi3.setDataIndex("currencyName");
    phi3.setSortable(true);
    phi3.setHidden(true);
    phi3.setWidth(200);
    headerlist.add(phi3);

    PageHeaderInfor phi4 = new PageHeaderInfor();
    phi4.setHeader("客户编号");
    phi4.setDataIndex("customerCode");
    phi4.setSortable(true);
    phi4.setHidden(true);
    phi4.setWidth(200);
    headerlist.add(phi4);

    PageHeaderInfor phi5 = new PageHeaderInfor();
    phi5.setHeader("税率");
    phi5.setDataIndex("taxRate");
    phi5.setSortable(true);
    phi5.setHidden(true);
    phi5.setWidth(200);
    headerlist.add(phi5);

    PageHeaderInfor phi6 = new PageHeaderInfor();
    phi6.setHeader("卖方");
    phi6.setDataIndex("sellerName");
    phi6.setSortable(true);
    phi6.setHidden(true);
    phi6.setWidth(200);
    headerlist.add(phi6);

    PageHeaderInfor phi7 = new PageHeaderInfor();
    phi7.setHeader("报价单类型");
    phi7.setDataIndex("quotationType");
    phi7.setSortable(true);
    phi7.setHidden(true);
    phi7.setWidth(200);
    headerlist.add(phi7);

    TResourcePurviewExample rpdexp = new TResourcePurviewExample();
    rpdexp.createCriteria().andUserIdEqualTo(loginInfor.getUser().getId()).andResourceTypeEqualTo(1);
    List<TResourcePurview> quoPurviewList = resourcePurviewDAO.selectByExample(rpdexp);
    List<String> userIdList = new ArrayList<String>();
    for (Iterator iterator = quoPurviewList.iterator(); iterator.hasNext();) {
      TResourcePurview object = (TResourcePurview) iterator.next();
      userIdList.add(object.getTargetUserId());
    }

    TQuotationInforExample example = new TQuotationInforExample();
    example.createCriteria().andStatusEqualTo(4).andEditorIdIn(userIdList);
    int totalCount = quoInfoDAO.countByExample(example);
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    List<TQuotationInfor> quoList = quoInfoDAO.selectByExample(example);
    List<LinkBusinessObject> bolist = new ArrayList<LinkBusinessObject>();
    for (Iterator iterator = quoList.iterator(); iterator.hasNext();) {
      TQuotationInfor item = (TQuotationInfor) iterator.next();
      LinkBusinessObject bo = new LinkBusinessObject();
      bo.setId(item.getId());
      bo.setName(item.getQuotationCode());
      bo.addProperty("id", item.getId());
      bo.addProperty("QuotationCode", item.getQuotationCode());

      bo.addProperty("currencyName", item.getQuotationCode());
      bo.addProperty("customerCode", item.getQuotationCode());
      bo.addProperty("taxRate", item.getQuotationCode());
      bo.addProperty("sellerName", item.getQuotationCode());
      bo.addProperty("quotationType", item.getQuotationType());

      bolist.add(bo);
    }
    PaginationSupport pageInfor = new PaginationSupport(bolist, totalCount, pageSize, startIndex);
    pageInfor.setHeaders(headerlist);
    return pageInfor;
  }

  public AuditDao getAuditDao() {
    return auditDao;
  }

  public void setAuditDao(AuditDao auditDao) {
    this.auditDao = auditDao;
  }

  public TQuotationInforDAO getQuoInfoDAO() {
    return quoInfoDAO;
  }

  public void setQuoInfoDAO(TQuotationInforDAO quoInfoDAO) {
    this.quoInfoDAO = quoInfoDAO;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TResourcePurviewDAO getResourcePurviewDAO() {
    return resourcePurviewDAO;
  }

  public void setResourcePurviewDAO(TResourcePurviewDAO resourcePurviewDAO) {
    this.resourcePurviewDAO = resourcePurviewDAO;
  }

  @Override
  public int findWaitContract2OrderCount(LoginInforDto loginInfor, int orderType) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasQx = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), CONTRACT_ORDER_MODULE_CODE);//合同订单模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (CONTRACT_ORDER_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasQx = true;
        break;
      }
    }
    if (!hasQx)
      return 0;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("conType", "orderOut");
    params.put("leaf", orderType);
    params.put("status", 4);
    int count = contractInforDAO.getContractListCount(params);
    return count;
  }

  @Override
  public int findWaitQuotation2ContractCount(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), CONTRACT_MODULE_CODE);//合同模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (CONTRACT_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    TResourcePurviewExample rpdexp = new TResourcePurviewExample();
    rpdexp.createCriteria().andUserIdEqualTo(loginInfor.getUser().getId()).andResourceTypeEqualTo(1);
    List<TResourcePurview> quoPurviewList = resourcePurviewDAO.selectByExample(rpdexp);
    List<String> userIdList = new ArrayList<String>();
    for (Iterator iterator = quoPurviewList.iterator(); iterator.hasNext();) {
      TResourcePurview object = (TResourcePurview) iterator.next();
      userIdList.add(object.getTargetUserId());
    }
    if (userIdList.size() == 0)
      return 0;
    List<Integer> tcList = new ArrayList<Integer>();
    tcList.add(QuotationDto.TRANSFER_CONTRACT_NEVER);
    tcList.add(QuotationDto.TRANSFER_CONTRACT_PART);
    TQuotationInforExample example = new TQuotationInforExample();
    example.createCriteria().andStatusEqualTo(4).andTransferContractIn(tcList).andEditorIdIn(userIdList);
    int totalCount = quoInfoDAO.countByExample(example);
    return totalCount;
  }

  public TModulesDefDAO getModulesDefDAO() {
    return modulesDefDAO;
  }

  public void setModulesDefDAO(TModulesDefDAO modulesDefDAO) {
    this.modulesDefDAO = modulesDefDAO;
  }

  @Override
  public int findWaitExpectedQuotation2OrderCount(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), EXPECTED_ORDER_MODULE_CODE);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (EXPECTED_ADD_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", 3);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", 1);
    params.put("outStockType", 5);
    int totalCount = quoInfoDAO.getQuotationListCountForOrder(params);
    return totalCount;
  }

  @Override
  public int findWaitExpectedQuotation2SelfOrderCount(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), EXPECTED_SELF_ORDER_MODULE_CODE);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (EXPECTED_ADD_SELF_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", 3);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", 0);
    params.put("outStockType", 5);
    int totalCount = quoInfoDAO.getQuotationListCountForOrder(params);
    return totalCount;
  }

  @Override
  public int findWaitTryToolsQuotation2OrderCount(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), TRY_TOOLS_ORDER_MODULE_CODE);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (TRY_TOOLS_ADD_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", 4);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", 1);
    params.put("outStockType", 5);
    int totalCount = quoInfoDAO.getQuotationListCountForOrder(params);
    return totalCount;
  }

  @Override
  public int findWaitTryToolsQuotation2SelfOrderCount(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), TRY_TOOLS_SELF_ORDER_MODULE_CODE);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (TRY_TOOLS_ADD_SELF_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", 4);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", 0);
    params.put("outStockType", 5);
    int totalCount = quoInfoDAO.getQuotationListCountForOrder(params);
    return totalCount;
  }

  @Override
  public int getContractCountCouldUploadFile(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), CONTRACT_MODULE_CODE);//合同模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (CONTRACT_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", loginInfor.getUser().getId());
    int rt = contractInforDAO.getContractCountCouldUploadFile(params);
    return rt;
  }

  @Override
  public int getExpectedQuo2QuoCount(LoginInforDto loginInfor) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    int totalCount = quoInfoDAO.getExpectedQuo2QuoCount(params);
    return totalCount;
  }

  @Override
  public int getTryTools2DeliveryCount(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), TRY_TOOLS_ORDER_MODULE_CODE);//试刀订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (TRY_TOOLS_ADD_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加试刀订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    int totalCount = quoInfoDAO.getTryTools2DeliveryCount(params);
    return totalCount;
  }

  @Override
  public int getTryTools2UploadReportCount(LoginInforDto loginInfor) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    int totalCount = quoInfoDAO.getTryTools2UploadReportCount(params);
    return totalCount;
  }

  @Override
  public int getTryTools2UploadApplyCount(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    int totalCount = quoInfoDAO.getTryTools2UploadApplyCount(params);
    return totalCount;
  }

  @Override
  public int findWaitSelfOrder2Plan(LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("orderType", "3,7,8");
    parmMap.put("needEditPlan", "1");
    parmMap.put("userId", loginInfor.getUser().getId());
    parmMap.put("status", "4");
    return orderInforDao.getOrderTotal(parmMap);
  }

  public TOrderInforDAO getOrderInforDao() {
    return orderInforDao;
  }

  public void setOrderInforDao(TOrderInforDAO orderInforDao) {
    this.orderInforDao = orderInforDao;
  }

  public TReservePlanMainDAO getReservePlanMainDAO() {
    return reservePlanMainDAO;
  }

  public void setReservePlanMainDAO(TReservePlanMainDAO reservePlanMainDAO) {
    this.reservePlanMainDAO = reservePlanMainDAO;
  }

  @Override
  public int findWait2PlanOrderPlanCount(LoginInforDto loginInfor) {
    TReservePlanMainExample example = new TReservePlanMainExample();
    example.createCriteria().andStatusEqualTo(2);
    return reservePlanMainDAO.countByExample(example);
  }

}
