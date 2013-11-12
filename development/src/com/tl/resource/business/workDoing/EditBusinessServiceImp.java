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
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TModulesDefDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TResourcePurviewDAO;
import com.tl.resource.dao.pojo.TModulesDef;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationInforExample;
import com.tl.resource.dao.pojo.TResourcePurview;
import com.tl.resource.dao.pojo.TResourcePurviewExample;

public class EditBusinessServiceImp implements EditBusinessService {
  public static final String EDIT_CONTRACT_NODE_ID = "waitEditContract";

  public static final String EDIT_ORDER_NODE_ID = "waitEditOrder";

  public static final String EDIT_PLAN_NODE_ID = "waitEditPlan";

  public static final String EDIT_OUT_STOCK_NODE_ID = "waitEditOut";

  public static final String EDIT_DELIVERY_NODE_ID = "waitEditDelivery";

  public static final String EDIT_ORDER_CONTRACT = "waitEditContractOrder";

  public static final String EDIT_ORDER_SCHEDULE_QUO = "waitEditScheduleOrder";

  public static final String EDIT_ORDER_TRY_QUO = "waitEditTryOrder";

  public static final String EDIT_ORDER_SELF = "waitEditSelfOrder";

  public static final String EDIT_ORDER_SCHEDULE_SELF_QUO = "waitEditScheduleSelfOrder";

  public static final String EDIT_ORDER_TRY_SELF = "waitEditTrySelfOrder";

  public static final String EDIT_ORDER_MAT = "waitEditMatOrder";

  private TModulesDefDAO modulesDefDAO;

  private TResourcePurviewDAO resourcePurviewDAO;

  private TQuotationInforDAO quotationInforDAO;

  private TContractInforDAO contractInforDAO;

  private TOrderInforDAO orderInforDao;

  @Override
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor) {
    if ("".equals(pid) || "edbroot".equals(pid)) {
      return getBusinessSortNodes(pid, loginInfor);
    } else if (EDIT_CONTRACT_NODE_ID.equals(pid)) {
      return getQuoListForEditContract(loginInfor);
    }
    //    else if (EDIT_ORDER_NODE_ID.equals(pid)) {//如果当前Pid是订单编制节点，则加载订单分类节点
    //      return this.getOrderTypes(loginInfor, pid);
    //    } 
    else if (EDIT_PLAN_NODE_ID.equals(pid)) {
      return getSelfOrderForEditPlan(loginInfor);
    } else if (EDIT_DELIVERY_NODE_ID.equals(pid)) {
      return getQuoAndcConForEditDelivery(loginInfor);
    } else if (EDIT_ORDER_CONTRACT.equals(pid)) {//合同订单
      return getContractsForEditOrder(loginInfor, 1);
    } else if (EDIT_ORDER_SCHEDULE_QUO.equals(pid)) {//预订订单
      return getQuosForEditOrder(loginInfor, 3, 1);
    } else if (EDIT_ORDER_TRY_QUO.equals(pid)) {//试刀订单
      return getQuosForEditOrder(loginInfor, 4, 1);
    } else if (EDIT_ORDER_SELF.equals(pid)) {//加工订单
      return getContractsForEditOrder(loginInfor, 0);
    } else if (EDIT_ORDER_SCHEDULE_SELF_QUO.equals(pid)) {//预订加工
      return getQuosForEditOrder(loginInfor, 3, 0);
    } else if (EDIT_ORDER_TRY_SELF.equals(pid)) {//试刀加工
      return getQuosForEditOrder(loginInfor, 4, 0);
    } else if (EDIT_ORDER_MAT.equals(pid)) {//材料储备

    }
    return null;
  }

  private List<BusinessInforDto> getQuoAndcConForEditDelivery(LoginInforDto loginInfor) {
    List<BusinessInforDto> conList = getContractsForEditDelivery(loginInfor);
    return conList;
  }

  private int getQuoAndcConCountForEditDelivery(LoginInforDto loginInfor) {
    int count = getContractsCountForEditDelivery(loginInfor);
    return count;
  }

  private List<BusinessInforDto> getContractsForEditDelivery(LoginInforDto loginInfor) {
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_DELIVERY_MODULE_CODE);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    List<ContractInforDto> conlist = contractInforDAO.contractInforsWaitDelivery(params);
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = conlist.iterator(); iterator.hasNext();) {
      ContractInforDto contractInforDto = (ContractInforDto) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(contractInforDto.getContractCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setDescription(m.getModuleName());
      dto.setUrl(m.getUrl());
      blist.add(dto);
    }
    return blist;
  }

  private int getContractsCountForEditDelivery(LoginInforDto loginInfor) {
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_DELIVERY_MODULE_CODE);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    int count = contractInforDAO.contractCountWaitDelivery(params);
    return count;
  }

  private List<BusinessInforDto> getSelfOrderForEditPlan(LoginInforDto loginInfor) {
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.PLAN_MODULE_CODE);
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("orderType", "3,7,8");
    parmMap.put("needEditPlan", "1");
    parmMap.put("userId", loginInfor.getUser().getId());
    parmMap.put("status", "4");
    List<TOrderInfor> orderList = orderInforDao.getOrderList(parmMap);
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = orderList.iterator(); iterator.hasNext();) {
      TOrderInfor orderInfor = (TOrderInfor) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(orderInfor.getOrderCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setDescription(m.getModuleName());
      dto.setUrl(m.getUrl());
      dto.setBusinessData(orderInfor);
      blist.add(dto);
    }
    return blist;
  }

  private int getSelfOrderCountForEditPlan(LoginInforDto loginInfor) {
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.PLAN_MODULE_CODE);
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("orderType", "3,7,8");
    parmMap.put("needEditPlan", "1");
    parmMap.put("userId", loginInfor.getUser().getId());
    parmMap.put("status", "4");
    int count = orderInforDao.getOrderTotal(parmMap);

    return count;
  }

  private List<BusinessInforDto> getOrderTypes(LoginInforDto loginInfor, String pid) {
    List<BusinessInforDto> list = new ArrayList<BusinessInforDto>();
    BusinessInforDto dto = new BusinessInforDto();
    int count = getContractsCountForEditOrder(loginInfor, 1);
    dto.setText("合同采购订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_CONTRACT);
    list.add(dto);

    dto = new BusinessInforDto();
    count = getQuosCountForEditOrder(loginInfor, 3, 1);
    dto.setText("预订采购订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_SCHEDULE_QUO);
    list.add(dto);

    dto = new BusinessInforDto();
    count = getQuosCountForEditOrder(loginInfor, 4, 1);
    dto.setText("试刀采购订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_TRY_QUO);
    list.add(dto);

    dto = new BusinessInforDto();
    count = getContractsCountForEditOrder(loginInfor, 0);
    dto.setText("合同加工订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_SELF);
    list.add(dto);

    dto = new BusinessInforDto();
    count = getQuosCountForEditOrder(loginInfor, 3, 0);
    dto.setText("预订加工订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_SCHEDULE_SELF_QUO);
    list.add(dto);

    dto = new BusinessInforDto();
    count = getQuosCountForEditOrder(loginInfor, 4, 0);
    dto.setText("试刀加工订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_TRY_SELF);
    list.add(dto);

    dto = new BusinessInforDto();
    count = 0;//getQuoCountForEditContract(loginInfor);
    dto.setText("材料采购订单[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_ORDER_MAT);
    list.add(dto);

    return list;
  }

  //  private List<BusinessInforDto> getQuoOrConForEditOrder(LoginInforDto loginInfor) {
  //    List<BusinessInforDto> conList = getContractsForEditOrder(loginInfor);
  //    conList = conList == null ? new ArrayList<BusinessInforDto>() : conList;
  //    List<BusinessInforDto> quoList = getQuosForEditOrder(loginInfor, 3);
  //    quoList = quoList == null ? new ArrayList<BusinessInforDto>() : quoList;
  //    conList.addAll(quoList);
  //    quoList = getQuosForEditOrder(loginInfor, 4);
  //    quoList = quoList == null ? new ArrayList<BusinessInforDto>() : quoList;
  //    conList.addAll(quoList);
  //    return conList;
  //  }

  private List<BusinessInforDto> getQuosForEditOrder(LoginInforDto loginInfor, Integer quoType, int leaf) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    String moduleCode = null;
    String mouuleAddCode = null;
    if (quoType == 3 && leaf == 1) {
      moduleCode = WaitWorksInforServiceImp.EXPECTED_ORDER_MODULE_CODE;
      mouuleAddCode = WaitWorksInforServiceImp.EXPECTED_ADD_ORDER_MODULE_CODE;
    } else if (quoType == 4 && leaf == 1) {
      moduleCode = WaitWorksInforServiceImp.TRY_TOOLS_ORDER_MODULE_CODE;
      mouuleAddCode = WaitWorksInforServiceImp.TRY_TOOLS_ADD_ORDER_MODULE_CODE;
    } else if (quoType == 3 && leaf == 0) {
      moduleCode = WaitWorksInforServiceImp.EXPECTED_SELF_ORDER_MODULE_CODE;
      mouuleAddCode = WaitWorksInforServiceImp.EXPECTED_ADD_SELF_ORDER_MODULE_CODE;
    } else if (quoType == 4 && leaf == 0) {
      moduleCode = WaitWorksInforServiceImp.TRY_TOOLS_SELF_ORDER_MODULE_CODE;
      mouuleAddCode = WaitWorksInforServiceImp.TRY_TOOLS_ADD_SELF_ORDER_MODULE_CODE;
    }
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), moduleCode);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (mouuleAddCode.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return null;

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", quoType);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", leaf);
    params.put("outStockType", 5);
    params.put("startIndex", 0);
    params.put("pageSize", 9999999);
    List<TQuotationInfor> quoList = quotationInforDAO.getQuotationListForOrder(params);
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = quoList.iterator(); iterator.hasNext();) {
      TQuotationInfor quotationInfor = (TQuotationInfor) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(quotationInfor.getQuotationCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setBusinessData(quotationInfor);
      blist.add(dto);
    }
    return blist;
  }

  private int getQuosCountForEditOrder(LoginInforDto loginInfor, Integer quoType, Integer leaf) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), WaitWorksInforServiceImp.EXPECTED_ORDER_MODULE_CODE);//预定订单模块
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (WaitWorksInforServiceImp.EXPECTED_ADD_ORDER_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quotationType", quoType);
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", leaf);
    params.put("outStockType", 5);
    params.put("startIndex", 0);
    params.put("pageSize", 9999999);
    int count = quotationInforDAO.getQuotationListCountForOrder(params);
    return count;
  }

  private List<BusinessInforDto> getContractsForEditOrder(LoginInforDto loginInfor, int leaf) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasQx = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), WaitWorksInforServiceImp.CONTRACT_ORDER_MODULE_CODE);//合同订单模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (WaitWorksInforServiceImp.CONTRACT_ORDER_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasQx = true;
        break;
      }
    }
    if (!hasQx)
      return null;
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_ORDER_MODULE_CODE);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", leaf);
    List<ContractInforDto> conlist = contractInforDAO.contractInforsWaitOrder(params);
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = conlist.iterator(); iterator.hasNext();) {
      ContractInforDto contractInforDto = (ContractInforDto) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(contractInforDto.getContractCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setUrl(m.getUrl());
      dto.setDescription(m.getModuleName());
      dto.setId(contractInforDto.getId());
      dto.setBusinessData(contractInforDto);
      blist.add(dto);
    }
    return blist;
  }

  private int getContractsCountForEditOrder(LoginInforDto loginInfor, Integer leaf) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasQx = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), WaitWorksInforServiceImp.CONTRACT_ORDER_MODULE_CODE);//合同订单模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (WaitWorksInforServiceImp.CONTRACT_ORDER_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同订单功能
        hasQx = true;
        break;
      }
    }
    if (!hasQx)
      return 0;
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_ORDER_MODULE_CODE);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currUserId", loginInfor.getUser().getId());
    params.put("leaf", leaf);

    Integer count = contractInforDAO.contractCountWaitOrder(params);
    return count;
  }

  private List<BusinessInforDto> getQuoListForEditContract(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), WaitWorksInforServiceImp.CONTRACT_MODULE_CODE);//合同模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (WaitWorksInforServiceImp.CONTRACT_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return null;
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_MODULE_CODE);
    TResourcePurviewExample rpdexp = new TResourcePurviewExample();
    rpdexp.createCriteria().andUserIdEqualTo(loginInfor.getUser().getId()).andResourceTypeEqualTo(1);
    List<TResourcePurview> quoPurviewList = resourcePurviewDAO.selectByExample(rpdexp);
    List<String> userIdList = new ArrayList<String>();
    for (Iterator iterator = quoPurviewList.iterator(); iterator.hasNext();) {
      TResourcePurview object = (TResourcePurview) iterator.next();
      userIdList.add(object.getTargetUserId());
    }
    if (userIdList.size() == 0)
      return null;
    List<Integer> tcList = new ArrayList<Integer>();
    tcList.add(QuotationDto.TRANSFER_CONTRACT_NEVER);
    tcList.add(QuotationDto.TRANSFER_CONTRACT_PART);
    TQuotationInforExample example = new TQuotationInforExample();
    example.createCriteria().andStatusEqualTo(4).andTransferContractIn(tcList).andEditorIdIn(userIdList);
    List<TQuotationInfor> quolist = quotationInforDAO.selectByExample(example);
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    for (Iterator iterator = quolist.iterator(); iterator.hasNext();) {
      TQuotationInfor quopo = (TQuotationInfor) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(quopo.getQuotationCode());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setUrl(m.getUrl());
      dto.setId(quopo.getId());
      dto.setDescription(quopo.getQuotationType().toString());
      blist.add(dto);
    }
    return blist;
  }

  private int getQuoCountForEditContract(LoginInforDto loginInfor) {
    List<ModulesDto> list = loginInfor.getModules();
    boolean hasModule = false;
    List<TModulesDef> ulist = modulesDefDAO.getUsersModules(loginInfor.getUser().getId(), WaitWorksInforServiceImp.CONTRACT_MODULE_CODE);//合同模块Id
    for (Iterator iterator = ulist.iterator(); iterator.hasNext();) {
      TModulesDef modulesDef = (TModulesDef) iterator.next();
      if (WaitWorksInforServiceImp.CONTRACT_ADD_MODULE_CODE.equals(modulesDef.getId())) {//增加合同功能
        hasModule = true;
        break;
      }
    }
    if (!hasModule)
      return 0;
    TModulesDef m = modulesDefDAO.selectByPrimaryKey(WaitWorksInforServiceImp.CONTRACT_MODULE_CODE);
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
    int count = quotationInforDAO.countByExample(example);
    return count;
  }

  private int getQuoOrConConuntForEditOrder(LoginInforDto loginInfor) {
    int count = getContractsCountForEditOrder(loginInfor, null);
    count += getQuosCountForEditOrder(loginInfor, 3, null);
    count += getQuosCountForEditOrder(loginInfor, 4, null);
    return count;
  }

  private List<BusinessInforDto> getBusinessSortNodes(String pid, LoginInforDto loginInfor) {
    List<BusinessInforDto> list = new ArrayList<BusinessInforDto>();
    BusinessInforDto dto = new BusinessInforDto();
    int count = getQuoCountForEditContract(loginInfor);
    dto.setText("待编制合同[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_CONTRACT_NODE_ID);
    list.add(dto);

    //    dto = new BusinessInforDto();
    //    count = getQuoOrConConuntForEditOrder(loginInfor);
    //    dto.setText("待编制订单[" + count + "]");
    //    dto.setParentId(pid);
    //    dto.setBusinessCount(count);
    //    dto.setId(EDIT_ORDER_NODE_ID);
    list.addAll(this.getOrderTypes(loginInfor, pid));

    dto = new BusinessInforDto();
    count = getSelfOrderCountForEditPlan(loginInfor);
    dto.setText("待编制生产物资申请[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_PLAN_NODE_ID);
    list.add(dto);

    dto = new BusinessInforDto();
    dto.setText("待编制出库");
    dto.setParentId(pid);
    dto.setId(EDIT_OUT_STOCK_NODE_ID);
    list.add(dto);

    count = getQuoAndcConCountForEditDelivery(loginInfor);
    dto = new BusinessInforDto();
    dto.setText("待编制交货[" + count + "]");
    dto.setParentId(pid);
    dto.setBusinessCount(count);
    dto.setId(EDIT_DELIVERY_NODE_ID);
    list.add(dto);
    return list;
  }

  public TModulesDefDAO getModulesDefDAO() {
    return modulesDefDAO;
  }

  public void setModulesDefDAO(TModulesDefDAO modulesDefDAO) {
    this.modulesDefDAO = modulesDefDAO;
  }

  public TResourcePurviewDAO getResourcePurviewDAO() {
    return resourcePurviewDAO;
  }

  public void setResourcePurviewDAO(TResourcePurviewDAO resourcePurviewDAO) {
    this.resourcePurviewDAO = resourcePurviewDAO;
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TOrderInforDAO getOrderInforDao() {
    return orderInforDao;
  }

  public void setOrderInforDao(TOrderInforDAO orderInforDao) {
    this.orderInforDao = orderInforDao;
  }

}
