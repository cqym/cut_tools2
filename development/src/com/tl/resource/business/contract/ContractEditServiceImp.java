package com.tl.resource.business.contract;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.baseInfo.CustomerService;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.ContractDetailDto;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.ContractProductDetailDto;
import com.tl.resource.business.dto.ContractProductSortDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TContractAccountsInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TContractProjectSortInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TDeliveryInforDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TProductArrivalInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TQuotationProjectSortInforDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractProductDetail;
import com.tl.resource.dao.pojo.TContractProductDetailExample;
import com.tl.resource.dao.pojo.TContractProjectSortInfor;
import com.tl.resource.dao.pojo.TContractProjectSortInforExample;
import com.tl.resource.dao.pojo.TCusSalesPriceHistory;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderInforExample;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationInforExample;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;
import com.tl.resource.dao.pojo.TQuotationProjectSortInforExample;

public class ContractEditServiceImp implements ContractEditService {

  private TContractInforDAO contractInforDAO;

  private TContractProductDetailDAO contractProductDetailDAO;

  private TContractProjectSortInforDAO contractProjectSortInforDAO;

  private TQuotationInforDAO quotationInforDAO;

  private TQuotationProductDetailDAO quotationProductDetailDAO;

  private TQuotationProjectSortInforDAO quotationProjectSortInforDAO;

  private TContractAccountsInforDAO contractAccountsInforDAO;

  private GeneralQuoService generalQuoService;

  private TCustomersInforDAO customersInforDAO;

  private TOrderInforDAO orderInforDAO;

  private TOrderDetailDAO orderDetailDAO;

  private TOutStockInforDAO outStockInforDAO;

  private TDeliveryInforDAO deliveryInforDAO;

  private TProductArrivalInforDAO productArrivalInforDAO;

  private CustomerService customerService;

  private TAccessoriesDAO accessoriesDAO;

  @Override
  public void addContract(ContractInforDto dto) {
    TContractInfor conInfor = new TContractInfor();
    try {
      BeanUtils.copyProperties(conInfor, dto);
      conInfor.setId(GenerateSerial.getUUID());
      conInfor.setEditDate(new Date());
      contractInforDAO.insert(conInfor);
      boolean exeFlag = true;
      List<ContractProductSortDto> sortList = dto.getContractProductSorts();
      for (Iterator iterator = sortList.iterator(); iterator.hasNext();) {
        ContractProductSortDto contractProductSortDto = (ContractProductSortDto) iterator.next();
        TContractProjectSortInfor sortpo = new TContractProjectSortInfor();
        sortpo.setContractInforId(conInfor.getId());
        sortpo.setId(contractProductSortDto.getId());
        sortpo.setProSortName(contractProductSortDto.getName());
        contractProjectSortInforDAO.insert(sortpo);
        List<ContractProductDetailDto> deList = contractProductSortDto.getConProductDetail();
        for (Iterator iterator2 = deList.iterator(); iterator2.hasNext();) {
          ContractProductDetailDto contractProductDetailDto = (ContractProductDetailDto) iterator2.next();
          contractProductDetailDto.setContractInforId(conInfor.getId());
          contractProductDetailDto.setContractProjectSortId(sortpo.getId());
          TContractProductDetail detPo = new TContractProductDetail();
          BeanUtils.copyProperties(detPo, contractProductDetailDto);
          contractProductDetailDAO.insert(detPo);
          if (contractProductDetailDto.getLeaf() == 0) {
            saveProductsChildren(conInfor.getId(), sortpo.getId(), contractProductDetailDto.getChildren());
          }
        }
      }

      changeQuotationStatus(conInfor.getId(), conInfor.getContractType());
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * 非项目报价单转的合同，修改对应报价单状态
   * @param contractId
   */
  private void changeNoProjConsQuotaionTransferStatus(String contractId) {
    List<TContractProjectSortInfor> sortList = getContarctSortByContarctId(contractId);
    for (Iterator iterator = sortList.iterator(); iterator.hasNext();) {
      TContractProjectSortInfor sortInfor = (TContractProjectSortInfor) iterator.next();
      TQuotationInforExample example = new TQuotationInforExample();
      example.createCriteria().andQuotationCodeEqualTo(sortInfor.getProSortName());
      List<TQuotationInfor> quoInforList = quotationInforDAO.selectByExample(example);
      if (quoInforList.size() > 0) {
        changeQuotationTransferContractStataus(contractId, quoInforList.get(0).getId(), quoInforList.get(0).getQuotationType());
      }
    }
  }

  /**
   * 获取合同的产品分类数据，根据合同id
   * @param contractId
   * @return
   */
  private List<TContractProjectSortInfor> getContarctSortByContarctId(String contractId) {
    TContractProjectSortInforExample example = new TContractProjectSortInforExample();
    example.createCriteria().andContractInforIdEqualTo(contractId);
    return contractProjectSortInforDAO.selectByExample(example);
  }

  private void changeQuotationTransferContractStataus(String contractId, String quoInforId, Integer quoType) {
    if (quoType.intValue() == Integer.valueOf(QuotationDto.QUO_TYPE_GENERAL)) {
      updateTransferContract(quoInforId, QuotationDto.TRANSFER_CONTRACT_ALL, contractId);
      return;
    }
    Integer quoDetailCount = quotationProductDetailDAO.getYuDingQuoDetail2CreateContractCount(quoInforId);
    TQuotationInfor quoInfor = new TQuotationInfor();
    quoInfor.setId(quoInforId);
    quoInfor.setContractInforId(contractId);
    if (quoDetailCount > 0) {//如果有 则转了一部分
      updateTransferContract(quoInforId, QuotationDto.TRANSFER_CONTRACT_PART, contractId);
    } else {//如果没有则全转了
      updateTransferContract(quoInforId, QuotationDto.TRANSFER_CONTRACT_ALL, contractId);
    }
  }

  private void updateTransferContract(String quoInforId, Integer transfer, String contractId) {
    TQuotationInfor quoInfor = new TQuotationInfor();
    quoInfor.setId(quoInforId);
    quoInfor.setContractInforId(contractId);
    quoInfor.setTransferContract(transfer);
    quoInfor.setBigDecimal2Null();
    quotationInforDAO.updateByPrimaryKeySelective(quoInfor);//设定报价单状态，报价单建立关联关系
  }

  private void saveProductsChildren(String conid, String conSortid, List<ContractProductDetailDto> children) throws IllegalAccessException,
    InvocationTargetException {
    // TODO Auto-generated method stub
    if (children == null || children.size() == 0)
      return;
    for (Iterator iterator2 = children.iterator(); iterator2.hasNext();) {
      ContractProductDetailDto contractProductDetailDto = (ContractProductDetailDto) iterator2.next();
      contractProductDetailDto.setContractInforId(conid);
      contractProductDetailDto.setContractProjectSortId(conSortid);
      TContractProductDetail detPo = new TContractProductDetail();
      BeanUtils.copyProperties(detPo, contractProductDetailDto);
      contractProductDetailDAO.insert(detPo);
      if (contractProductDetailDto.getLeaf() == 0) {
        saveProductsChildren(conid, conSortid, contractProductDetailDto.getChildren());
      }
    }
  }

  @Override
  public ContractInforDto consultGeneralQuo(String[] ids) {
    List<String> idss = new ArrayList<String>();
    for (int i = 0; i < ids.length; i++) {
      idss.add(ids[i]);
    }
    TQuotationInfor qinfor = quotationInforDAO.getQuoInforGroupBy(idss);
    ContractInforDto conInfor = new ContractInforDto();
    conInfor.setTotalMoney(qinfor.getTotalMoney());
    conInfor.setTaxMoney(qinfor.getTaxMoney());
    conInfor.setCustomerCode(qinfor.getCustomerCode());
    conInfor.setCustomerName(qinfor.getCustomerName());
    conInfor.setCurrencyName(qinfor.getCurrencyName());

    conInfor.setSellerName(qinfor.getSellerName());
    conInfor.setTaxRate(qinfor.getTaxRate());
    conInfor.setProductMoney(qinfor.getProductMoney());
    conInfor.setClosingAccountMode(qinfor.getPaymentCondition());
    conInfor.setContractType(ContractInforDto.CONTRACT_TYPE_GENERAL);

    if (idss.size() > 0) {
      TQuotationInfor tqinfor = quotationInforDAO.selectByPrimaryKey(idss.get(0));
      conInfor.setOwnContactPerson(tqinfor.getUserName());
      conInfor.setCustomerFax(tqinfor.getCustomerFax());
      conInfor.setCustomerPhone(tqinfor.getCustomerPhone());
      conInfor.setCusContactPerson(tqinfor.getCusContactPerson());
      conInfor.setCurrencyId(tqinfor.getCurrency());
      conInfor.setExemplarInvoice(tqinfor.getExemplarInvoice());
      //conInfor.setMemo(tqinfor.getMemo());
    }
    TQuotationInforExample example = new TQuotationInforExample();
    example.createCriteria().andIdIn(idss);
    List<TQuotationInfor> list = quotationInforDAO.selectByExample(example);
    StringBuffer bz = new StringBuffer(100);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TQuotationInfor quo = (TQuotationInfor) iterator.next();
      bz.append(quo.getMemo()).append("   ");
    }
    conInfor.setMemo(bz.toString());
    conInfor.setEffectConditions("本合同一式   份，卖方   份，买方   份，经双方签字盖章后生效。");
    conInfor.setOtherConvention("无");
    conInfor.setSignAddress("无");

    TCustomersInforExample custExample = new TCustomersInforExample();
    custExample.createCriteria().andCustomerCodeEqualTo(qinfor.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(custExample);
    if (cusList != null && cusList.size() > 0) {
      TCustomersInfor cus = cusList.get(0);
      conInfor.setDeliveryAddressType(cus.getContractAddress());
    }
    List<ContractProductSortDto> contractProductSorts = new ArrayList<ContractProductSortDto>();
    BigDecimal finalMoney = BigDecimal.ZERO;
    for (int i = 0; i < ids.length; i++) {
      QuotationDto quodto = quotationInforDAO.getQuoInfoById(ids[i]);
      ContractProductSortDto e = new ContractProductSortDto();
      e.setId(quodto.getId());
      e.setName(quodto.getQuotationCode());
      List<QuotationDetailDto> qproDtos = quotationProductDetailDAO.getQuoDetail(quodto.getId());
      List<ContractProductDetailDto> conProductDetail = convertToContractProductDetailDtos(qproDtos);
      e.setConProductDetail(conProductDetail);
      contractProductSorts.add(e);
      finalMoney = finalMoney.add(new BigDecimal(quodto.getFinalMoney() == null ? "0" : quodto.getFinalMoney()));
    }
    conInfor.setContractProductSorts(contractProductSorts);
    conInfor.setFinalMoney(finalMoney);
    return conInfor;
  }

  private List<ContractProductDetailDto> convertToContractProductDetailDtos(List<QuotationDetailDto> qproDtos) {
    List<ContractProductDetailDto> cpddtos = new ArrayList<ContractProductDetailDto>();
    try {
      for (Iterator iterator = qproDtos.iterator(); iterator.hasNext();) {
        QuotationDetailDto quotationDetailDto = (QuotationDetailDto) iterator.next();
        ContractProductDetailDto cpdd = new ContractProductDetailDto();
        BeanUtils.copyProperties(cpdd, quotationDetailDto);
        if (quotationDetailDto.isLeaf()) {
          cpdd.setLeaf(1);
        }
        if (StringUtils.isNumeric(quotationDetailDto.getSlaveFile())) {
          cpdd.setFileCount(Integer.valueOf(quotationDetailDto.getSlaveFile()));
        }
        cpddtos.add(cpdd);
        if (quotationDetailDto.getChildren() != null && quotationDetailDto.getChildren().size() > 0) {
          cpdd.setChildren(convertToContractProductDetailDtos(quotationDetailDto.getChildren()));
        }
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return cpddtos;
  }

  @Override
  public ContractInforDto consultProjectQuo(String id) {
    TQuotationInfor qinfor = quotationInforDAO.selectByPrimaryKey(id);
    ContractInforDto conInfor = new ContractInforDto();
    conInfor.setTotalMoney(qinfor.getTotalMoney());
    conInfor.setTaxMoney(qinfor.getTaxMoney());
    conInfor.setCustomerCode(qinfor.getCustomerCode());
    conInfor.setCustomerName(qinfor.getCustomerName());
    conInfor.setCurrencyId(qinfor.getCurrency());
    conInfor.setCurrencyName(qinfor.getCurrencyName());
    conInfor.setSellerName(qinfor.getSellerName());
    conInfor.setTaxRate(qinfor.getTaxRate());
    conInfor.setProductMoney(qinfor.getProductMoney());
    conInfor.setContractType(ContractInforDto.CONTRACT_TYPE_PROJECT);
    conInfor.setOwnContactPerson(qinfor.getUserName());
    conInfor.setCustomerFax(qinfor.getCustomerFax());
    conInfor.setCustomerPhone(qinfor.getCustomerPhone());
    conInfor.setCusContactPerson(qinfor.getCusContactPerson());
    conInfor.setEffectConditions("本合同一式   份，卖方   份，买方   份，经双方签字盖章后生效。");
    conInfor.setOtherConvention("无");
    conInfor.setSignAddress("无");
    conInfor.setMemo(qinfor.getMemo());
    conInfor.setClosingAccountMode(qinfor.getPaymentCondition());
    conInfor.setExemplarInvoice(qinfor.getExemplarInvoice());
    TCustomersInforExample custExample = new TCustomersInforExample();
    custExample.createCriteria().andCustomerCodeEqualTo(qinfor.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(custExample);
    if (cusList != null && cusList.size() > 0) {
      TCustomersInfor cus = cusList.get(0);
      conInfor.setDeliveryAddressType(cus.getContractAddress());
    }

    List<ContractProductSortDto> contractProductSorts = new ArrayList<ContractProductSortDto>();
    TQuotationProjectSortInforExample proSortex = new TQuotationProjectSortInforExample();
    proSortex.createCriteria().andQuotationInforIdEqualTo(id);
    List<TQuotationProjectSortInfor> qproSortList = quotationProjectSortInforDAO.selectByExample(proSortex);
    for (Iterator iterator = qproSortList.iterator(); iterator.hasNext();) {
      TQuotationProjectSortInfor quotationProjectSortInfor = (TQuotationProjectSortInfor) iterator.next();
      ContractProductSortDto e = new ContractProductSortDto();
      e.setId(quotationProjectSortInfor.getId());
      e.setName(quotationProjectSortInfor.getProSortName());

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("quotation_project_sort_id", e.getId());
      List<QuotationDetailDto> qproDtos = quotationProductDetailDAO.getQuoDetailList(params);
      List<ContractProductDetailDto> conProductDetail = convertToContractProductDetailDtos(qproDtos);
      e.setConProductDetail(conProductDetail);
      contractProductSorts.add(e);
    }
    conInfor.setFinalMoney(qinfor.getFinalMoney());
    conInfor.setContractProductSorts(contractProductSorts);
    return conInfor;
  }

  @Override
  public void deleteContracts(String id) {
    TQuotationInforExample quoExp = new TQuotationInforExample();
    quoExp.createCriteria().andContractInforIdEqualTo(id);
    List<TQuotationInfor> list = quotationInforDAO.selectByExample(quoExp);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TQuotationInfor quotationInfor = (TQuotationInfor) iterator.next();
      quotationInfor.setContractInforId(null);
      //quotationInfor.setStatus(2);
      quotationInfor.setTransferContract(QuotationDto.TRANSFER_CONTRACT_NEVER);
      quotationInforDAO.updateByPrimaryKey(quotationInfor);//删除合同后，将对应报价单状态置为 ’提交合同‘，将合同号设置为空
    }

    TContractProductDetailExample deleExe = new TContractProductDetailExample();
    deleExe.createCriteria().andContractInforIdEqualTo(id);
    //contractInforDAO;
    contractProductDetailDAO.deleteByExample(deleExe);

    TContractProjectSortInforExample delSortEx = new TContractProjectSortInforExample();
    delSortEx.createCriteria().andContractInforIdEqualTo(id);
    contractProjectSortInforDAO.deleteByExample(delSortEx);

    contractInforDAO.deleteByPrimaryKey(id);

  }

  @Override
  public PaginationSupport findContractInfors(Map params, int startIndex, int pageSize) {
    // TODO Auto-generated method stub
    return contractInforDAO.findContractInfors(params, startIndex, pageSize);
  }

  @Override
  public ContractInforDto getContractInforById(String id) {
    // TODO Auto-generated method stub
    ContractInforDto dto = new ContractInforDto();
    TContractInfor po = contractInforDAO.selectByPrimaryKey(id);
    try {
      BeanUtils.copyProperties(dto, po);
      TContractProjectSortInforExample detailEx = new TContractProjectSortInforExample();
      detailEx.createCriteria().andContractInforIdEqualTo(id);
      List<TContractProjectSortInfor> sortList = contractProjectSortInforDAO.selectByExample(detailEx);
      List<ContractProductSortDto> contractProductSorts = new ArrayList<ContractProductSortDto>();
      for (Iterator iterator = sortList.iterator(); iterator.hasNext();) {
        TContractProjectSortInfor sortpo = (TContractProjectSortInfor) iterator.next();
        ContractProductSortDto dto2 = new ContractProductSortDto();
        BeanUtils.copyProperties(dto2, sortpo);
        dto2.setName(sortpo.getProSortName());

        Map<String, String> params = new HashMap<String, String>();
        params.put("contractId", id);//
        params.put("contractSortId", dto2.getId());
        //List<ContractProductDetailDto> plist = contractProductDetailDAO.getProductDetail(dto.getId(),dto2.getId());
        PaginationSupport pageInfor = contractProductDetailDAO.findContractDetail(params, 0, Integer.MAX_VALUE);//new PaginationSupport(plist);
        List<ContractProductDetailDto> conProductDetail = pageInfor.getItems();
        dto2.setConProductDetail(conProductDetail);
        contractProductSorts.add(dto2);
      }
      dto.setContractProductSorts(contractProductSorts);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dto;
  }

  @Override
  public void updateContract(ContractInforDto dto) {
    // TODO Auto-generated method stub
    TContractInfor po = new TContractInfor();
    try {

      BeanUtils.copyProperties(po, dto);
      po.setUserName(null);
      po.setUserId(null);
      contractInforDAO.updateByPrimaryKeySelective(po);

      List<ContractProductSortDto> sortList = dto.getContractProductSorts();
      checkContractSortAndDelete(dto, sortList);//删除一在界面删除的分类
      checkInertAndUpdate(dto, sortList);//修改或保存

      changeQuotationStatus(dto.getId(), dto.getContractType());
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void checkInertAndUpdate(ContractInforDto dto, List<ContractProductSortDto> sortList) throws IllegalAccessException,
    InvocationTargetException {
    for (Iterator iterator = sortList.iterator(); iterator.hasNext();) {
      ContractProductSortDto contractProductSortDto = (ContractProductSortDto) iterator.next();
      TContractProjectSortInforExample sortExp = new TContractProjectSortInforExample();
      sortExp.createCriteria().andIdEqualTo(contractProductSortDto.getId());
      int count = contractProjectSortInforDAO.countByExample(sortExp);
      if (count > 0) {//如果原先就有，则修改
        List<ContractProductDetailDto> detailList = contractProductSortDto.getConProductDetail();
        for (Iterator iterator2 = detailList.iterator(); iterator2.hasNext();) {
          ContractProductDetailDto contractProductDetailDto = (ContractProductDetailDto) iterator2.next();
          TContractProductDetail record = new TContractProductDetail();
          BeanUtils.copyProperties(record, contractProductDetailDto);
          contractProductDetailDAO.updateByPrimaryKeySelective(record);

          //如果当前合同明细数量发生变化，修改对应订单明细的合同数量
          TOrderDetailExample orderDeEx = new TOrderDetailExample();
          orderDeEx.createCriteria().andContractProductDetailIdEqualTo(record.getId()).andContractAmountNotEqualTo(record.getAmount());
          TOrderDetail orderDe = new TOrderDetail();
          orderDe.setContractAmount(record.getAmount());
          orderDetailDAO.updateByExampleSelective(orderDe, orderDeEx);
        }
      } else {//如果原先没有，则新增
        TContractProjectSortInfor sortpo = new TContractProjectSortInfor();
        sortpo.setContractInforId(dto.getId());
        sortpo.setId(contractProductSortDto.getId());
        sortpo.setProSortName(contractProductSortDto.getName());
        contractProjectSortInforDAO.insert(sortpo);
        List<ContractProductDetailDto> deList = contractProductSortDto.getConProductDetail();
        for (Iterator iterator2 = deList.iterator(); iterator2.hasNext();) {
          ContractProductDetailDto contractProductDetailDto = (ContractProductDetailDto) iterator2.next();
          contractProductDetailDto.setContractInforId(dto.getId());
          contractProductDetailDto.setContractProjectSortId(sortpo.getId());
          TContractProductDetail detPo = new TContractProductDetail();
          BeanUtils.copyProperties(detPo, contractProductDetailDto);
          contractProductDetailDAO.insert(detPo);
          if (contractProductDetailDto.getLeaf() == 0) {
            saveProductsChildren(dto.getId(), sortpo.getId(), contractProductDetailDto.getChildren());
          }
        }

      }
    }
  }

  private void checkContractSortAndDelete(ContractInforDto dto, List<ContractProductSortDto> sortList) {
    TContractProjectSortInforExample sortEx = new TContractProjectSortInforExample();
    sortEx.createCriteria().andContractInforIdEqualTo(dto.getId());
    List<TContractProjectSortInfor> sortpoList = contractProjectSortInforDAO.selectByExample(sortEx);//得到当前合同，所有分类
    for (Iterator iterator = sortpoList.iterator(); iterator.hasNext();) {
      TContractProjectSortInfor contractProjectSortInfor = (TContractProjectSortInfor) iterator.next();
      boolean flag = false;
      for (Iterator iterator2 = sortList.iterator(); iterator2.hasNext();) {
        ContractProductSortDto contractProductSortDto = (ContractProductSortDto) iterator2.next();
        if (contractProjectSortInfor.getId().equals(contractProductSortDto.getId())) {
          flag = true;
          break;
        }
      }
      if (!flag) {//如果在当前提交数据中，找不到库中的分类，则表明将其删除
        TContractProductDetailExample sortExp = new TContractProductDetailExample();
        sortExp.createCriteria().andContractProjectSortIdEqualTo(contractProjectSortInfor.getId());
        contractProductDetailDAO.deleteByExample(sortExp);
        contractProjectSortInforDAO.deleteByPrimaryKey(contractProjectSortInfor.getId());
      }
    }
  }

  /**
   * 修改合同所对应报价单的 转合同状态
   * @param dto
   */
  private void changeQuotationStatus(String contractId, Integer contractType) {
    if (ContractInforDto.CONTRACT_TYPE_PROJECT.equals(contractType)) {
      List<TContractProjectSortInfor> sortsList = this.getContarctSortByContarctId(contractId);
      TQuotationProjectSortInfor qpsi = quotationProjectSortInforDAO.selectByPrimaryKey(sortsList.get(0).getId());
      updateTransferContract(qpsi.getQuotationInforId(), QuotationDto.TRANSFER_CONTRACT_ALL, contractId);
    } else {
      changeNoProjConsQuotaionTransferStatus(contractId);
    }
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TContractProductDetailDAO getContractProductDetailDAO() {
    return contractProductDetailDAO;
  }

  public void setContractProductDetailDAO(TContractProductDetailDAO contractProductDetailDAO) {
    this.contractProductDetailDAO = contractProductDetailDAO;
  }

  public TContractProjectSortInforDAO getContractProjectSortInforDAO() {
    return contractProjectSortInforDAO;
  }

  public void setContractProjectSortInforDAO(TContractProjectSortInforDAO contractProjectSortInforDAO) {
    this.contractProjectSortInforDAO = contractProjectSortInforDAO;
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

  public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
    return quotationProductDetailDAO;
  }

  public void setQuotationProductDetailDAO(TQuotationProductDetailDAO quotationProductDetailDAO) {
    this.quotationProductDetailDAO = quotationProductDetailDAO;
  }

  public TQuotationProjectSortInforDAO getQuotationProjectSortInforDAO() {
    return quotationProjectSortInforDAO;
  }

  public void setQuotationProjectSortInforDAO(TQuotationProjectSortInforDAO quotationProjectSortInforDAO) {
    this.quotationProjectSortInforDAO = quotationProjectSortInforDAO;
  }

  @Override
  public String cancelAudit(String businessId) {
    // TODO Auto-generated method stub
    TContractInfor record = new TContractInfor();
    record.setId(businessId);
    record.setStatus(3);
    contractInforDAO.updateByPrimaryKeySelective(record);
    return null;
  }

  @Override
  public String endAudit(String businessId) {
    // TODO Auto-generated method stub

    TContractInfor record = new TContractInfor();
    record.setId(businessId);
    record.setStatus(2);
    contractInforDAO.updateByPrimaryKeySelective(record);

    saveChangePriceProducts(businessId);

    //    TQuotationInforExample quoexample = new TQuotationInforExample();
    //    quoexample.createCriteria().andContractInforIdEqualTo(businessId);
    //    List<TQuotationInfor> list = quotationInforDAO.selectByExample(quoexample);
    //    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    //      TQuotationInfor object = (TQuotationInfor) iterator.next();
    //      generalQuoService.saveChangePriceProducts(object.getId());
    //    }
    return null;
  }

  public void saveChangePriceProducts(String contractId) {
    TContractInfor contractInfor = contractInforDAO.selectByPrimaryKey(contractId);
    //客户信息
    TCustomersInfor customer = null;
    if (contractInfor.getCustomerCode() != null && !"".equals(contractInfor.getCustomerCode())) {
      customer = generalQuoService.getCustomerByCusCode(contractInfor.getCustomerCode());
    }

    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andContractInforIdEqualTo(contractId);
    List<TContractProductDetail> detail = contractProductDetailDAO.selectByExample(example);//contractProductDetailDAO.selectContractDetailOfPriceChange(contractId);

    for (TContractProductDetail contractProductDetail : detail) {
      TCusSalesPriceHistory salPriHistory = new TCusSalesPriceHistory();
      salPriHistory.setId(GenerateSerial.getUUID());
      salPriHistory.setParentId(contractProductDetail.getParentToolsId());
      salPriHistory.setProductToolInforId(contractProductDetail.getToolsId());
      salPriHistory.setLeaf(contractProductDetail.getLeaf());
      salPriHistory.setBrandCode(contractProductDetail.getBrandCode());
      salPriHistory.setProductCode(contractProductDetail.getProductCode());
      salPriHistory.setRebate(contractProductDetail.getRebate());
      salPriHistory.setNetPrice(contractProductDetail.getNetPrice());
      salPriHistory.setHistoryPrice(contractProductDetail.getPrice());

      salPriHistory.setEditDate(contractInfor.getEditDate());
      salPriHistory.setUserId(contractInfor.getUserId());
      salPriHistory.setUserName(contractInfor.getUserName());
      salPriHistory.setSalePriceDate(new Date());
      salPriHistory.setStatus(0);

      salPriHistory.setQuotationCode(getQuoCodeByContractDetail(contractProductDetail));
      salPriHistory.setTaxNetPrice(contractProductDetail.getTaxNetPrice());
      salPriHistory.setContractCode(contractInfor.getContractCode());
      if (customer != null) {
        salPriHistory.setCustomerInforId(customer.getId());
      }

      generalQuoService.insertSelective(salPriHistory);
    }
  }

  private String getQuoCodeByContractDetail(TContractProductDetail contractProductDetail) {

    TContractProjectSortInfor sort = this.contractProjectSortInforDAO.selectByPrimaryKey(contractProductDetail.getContractProjectSortId());

    return sort.getProSortName();
  }

  @Override
  public String submitAudit(String businessId) {
    // TODO Auto-generated method stub
    TContractInfor record = new TContractInfor();
    record.setId(businessId);
    record.setStatus(1);
    contractInforDAO.updateByPrimaryKeySelective(record);
    return null;
  }

  @Override
  public PaginationSupport findContractDetail(Map params, int startIndex, int pageSize) {

    return contractProductDetailDAO.findContractDetail(params, startIndex, pageSize);
  }

  @Override
  public void runContract(String conId, LoginInforDto loginInfor) {
    // TODO Auto-generated method stub
    TContractInfor contractInfor = contractInforDAO.selectByPrimaryKey(conId);

    HashMap<String, Object> param = new HashMap<String, Object>();
    param.put("busId", conId);
    List<AccessoriesDto> acc = accessoriesDAO.getAccessoriesByBusId(param);

    TCustomersInfor customer = customerService.getCustomerInforByCode(contractInfor.getCustomerCode());
    if (TCustomersInfor.MUST_HAVE_FILE.equals(customer.getContractRunCondi()) && acc.size() == 0) {
      throw new RuntimeException("合同：[" + contractInfor.getContractCode() + "]执行失败，此客户所属合同必须上传附件，才能执行！");
    }
    Integer countConDetailNoAcc = this.contractProductDetailDAO.countConDetailNoAcc(conId);
    if (!loginInfor.hasModule("004017") && countConDetailNoAcc != null && countConDetailNoAcc > 0) {
      throw new RuntimeException("合同：[" + contractInfor.getContractCode() + "]执行失败，此合同中有未上传附件的自制产品，不能执行！");
    }
    TContractInfor record = new TContractInfor();
    record.setId(conId);
    record.setStatus(4);
    contractInforDAO.updateByPrimaryKeySelective(record);

    //contractInforDAO.relationScheduleInfor(conId);

    TContractProjectSortInforExample sortexp = new TContractProjectSortInforExample();
    sortexp.createCriteria().andContractInforIdEqualTo(conId);
    List sortList = contractProjectSortInforDAO.selectByExample(sortexp);
    for (Iterator iterator = sortList.iterator(); iterator.hasNext();) {
      TContractProjectSortInfor s = (TContractProjectSortInfor) iterator.next();
      TQuotationInforExample quoExp = new TQuotationInforExample();
      quoExp.createCriteria().andQuotationCodeEqualTo(s.getProSortName());
      List quolist = quotationInforDAO.selectByExample(quoExp);
      for (Iterator iterator2 = quolist.iterator(); iterator2.hasNext();) {
        TQuotationInfor quo = (TQuotationInfor) iterator2.next();
        if (QuotationDto.QUO_TYPE_SCHEDULE.equals(quo.getQuotationType().toString())
          || QuotationDto.QUO_TYPE_TRY.equals(quo.getQuotationType().toString())) {
          contractProductDetailDAO.sycContractOrderDetail(quo.getQuotationCode());/*将预定报价单的后续业务数量同步过来*/
          contractProductDetailDAO.sycContractOutDetail(quo.getQuotationCode());
          contractProductDetailDAO.sycContractDeliveryDetail(quo.getQuotationCode());
          contractProductDetailDAO.sycContractArrivalDetail(quo.getQuotationCode());
        }
      }
    }

    customerService.setQianKuanDate(contractInfor.getCustomerCode());//设置客户的欠款时间
  }

  @Override
  public String endContract(String conId) {
    // TODO Auto-generated method stub
    BigDecimal needMoney = contractAccountsInforDAO.getContractNeedMoney(conId);
    if (needMoney.doubleValue() > 1) {//小于一块钱，就认为已经交清
      return "本合同还有[" + needMoney + "]余款未付，不能终结!";
    }
    BigDecimal needDa = contractInforDAO.getContractNeedDeliveryAmount(conId);
    if (needDa.doubleValue() > 0) {
      return "本合同还有[" + needDa + "]个货品未交付，不能终结!";
    }
    TContractInfor record = new TContractInfor();
    record.setId(conId);
    record.setStatus(5);
    contractInforDAO.updateByPrimaryKeySelective(record);
    return null;
  }

  @Override
  public void voidContract(String conId) {
    // TODO Auto-generated method stub
    TContractInfor po = contractInforDAO.selectByPrimaryKey(conId);
    if (po == null)
      return;
    Integer type = po.getContractType();
    if (type == 0 || type == 1 || type == 2 || type == 3) {//-1作废，0编制，1待审批，2审批通过，3审批退回，4执行，5终结
      TContractInfor record = new TContractInfor();
      record.setId(conId);
      record.setStatus(-1);
      contractInforDAO.updateByPrimaryKeySelective(record);
    } else if (type == 4) {
      TContractInfor record = new TContractInfor();
      record.setId(conId);
      record.setStatus(-1);
      contractInforDAO.updateByPrimaryKeySelective(record);

      TOrderInforExample orderExample = new TOrderInforExample();
      List<Integer> ids = new ArrayList<Integer>();
      ids.add(0);
      ids.add(1);
      ids.add(2);
      ids.add(3);
      ids.add(4);

      orderExample.createCriteria().andContractCodeEqualTo(po.getContractCode()).andOrderTypeEqualTo(1).andStatusIn(ids);
      TOrderInfor orderRecord = new TOrderInfor();
      orderRecord.setOrderType(2);
      this.orderInforDAO.updateByExampleSelective(orderRecord, orderExample);
      //			
      //			TProductArrivalInforExample arriExample = new TProductArrivalInforExample();
      //			arriExample.createCriteria().andContractCodeEqualTo(po.getContractCode());
      //			TProductArrivalInfor arriRecord = new TProductArrivalInfor();
      //			arriRecord.setStatus(2);
      //			this.productArrivalInforDAO.updateByExampleSelective(arriRecord, arriExample);
      //			
      //			TDeliveryInfor deliRecord = new TDeliveryInfor();
      //			deliRecord.setStatus(4);
      //			TDeliveryInforExample deliExample = new TDeliveryInforExample();
      //			deliExample.createCriteria().andContractCodeEqualTo(po.getContractCode());
      //			this.deliveryInforDAO.updateByExampleSelective(deliRecord , deliExample);
      //			
      //			TOutStockInfor outRecord = new TOutStockInfor();
      //			outRecord.setStatus(2);
      //			TOutStockInforExample outExample = new TOutStockInforExample();
      //			outExample.createCriteria().andContractCodeEqualTo(po.getContractCode());
      //			this.outStockInforDAO.updateByExampleSelective(outRecord, outExample);
    }
    TQuotationInforExample quoExp = new TQuotationInforExample();
    quoExp.createCriteria().andContractInforIdEqualTo(conId);
    List<TQuotationInfor> list = quotationInforDAO.selectByExample(quoExp);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TQuotationInfor quotationInfor = (TQuotationInfor) iterator.next();
      quotationInfor.setContractInforId(null);
      quotationInfor.setStatus(4);
      quotationInforDAO.updateByPrimaryKey(quotationInfor);//作废合同后，将对应报价单状态置为 ’提交合同‘，将合同号设置为空
    }
  }

  public TContractAccountsInforDAO getContractAccountsInforDAO() {
    return contractAccountsInforDAO;
  }

  public void setContractAccountsInforDAO(TContractAccountsInforDAO contractAccountsInforDAO) {
    this.contractAccountsInforDAO = contractAccountsInforDAO;
  }

  public GeneralQuoService getGeneralQuoService() {
    return generalQuoService;
  }

  public void setGeneralQuoService(GeneralQuoService generalQuoService) {
    this.generalQuoService = generalQuoService;
  }

  public TOrderInforDAO getOrderInforDAO() {
    return orderInforDAO;
  }

  public void setOrderInforDAO(TOrderInforDAO orderInforDAO) {
    this.orderInforDAO = orderInforDAO;
  }

  public TOutStockInforDAO getOutStockInforDAO() {
    return outStockInforDAO;
  }

  public void setOutStockInforDAO(TOutStockInforDAO outStockInforDAO) {
    this.outStockInforDAO = outStockInforDAO;
  }

  public TDeliveryInforDAO getDeliveryInforDAO() {
    return deliveryInforDAO;
  }

  public void setDeliveryInforDAO(TDeliveryInforDAO deliveryInforDAO) {
    this.deliveryInforDAO = deliveryInforDAO;
  }

  public TProductArrivalInforDAO getProductArrivalInforDAO() {
    return productArrivalInforDAO;
  }

  public void setProductArrivalInforDAO(TProductArrivalInforDAO productArrivalInforDAO) {
    this.productArrivalInforDAO = productArrivalInforDAO;
  }

  public TCustomersInforDAO getCustomersInforDAO() {
    return customersInforDAO;
  }

  public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
    this.customersInforDAO = customersInforDAO;
  }

  @Override
  public void deleteContractDetail(String contractId, BigDecimal overallRebate, String[] ids) {
    for (int i = 0; i < ids.length; i++) {
      contractProductDetailDAO.deleteByPrimaryKey(ids[i]);
    }
    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andContractInforIdEqualTo(contractId);
    List list = contractProductDetailDAO.selectByExample(example);
    BigDecimal sum = BigDecimal.ZERO;
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TContractProductDetail cpd = (TContractProductDetail) iterator.next();
      sum = sum.add(cpd.getMoney() == null ? BigDecimal.ZERO : cpd.getMoney());
    }
    TContractInfor record = contractInforDAO.selectByPrimaryKey(contractId);
    record.setProductMoney(sum);
    record.setTaxMoney(sum.multiply(record.getTaxRate()));
    record.setTotalMoney(sum.add(record.getTaxMoney()));
    record.setOverallRebate(overallRebate);
    record.setFinalMoney(record.getTotalMoney().multiply(new BigDecimal("100").subtract(overallRebate).divide(new BigDecimal("100"))));
    contractInforDAO.updateByPrimaryKey(record);

    changeQuotationStatus(contractId, record.getContractType());

  }

  @Override
  public void unTreadQuo2Audited(String[] ids) {
    for (int i = 0; i < ids.length; i++) {
      TQuotationInfor record = quotationInforDAO.selectByPrimaryKey(ids[i]);
      if (record.getStatus() != null && record.getStatus() == 4) {
        record.setStatus(2);
        quotationInforDAO.updateByPrimaryKeySelective(record);
      }
    }
  }

  public CustomerService getCustomerService() {
    return customerService;
  }

  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public ContractInforDto consultYuDingQuo(String[] ids, BigDecimal contractTaxRate) {
    List<String> idss = new ArrayList<String>();
    for (int i = 0; i < ids.length; i++) {
      idss.add(ids[i]);
    }
    ContractInforDto conInfor = new ContractInforDto();
    conInfor.setContractType(ContractInforDto.CONTRACT_TYPE_GENERAL);
    if (idss.size() > 0) {
      TQuotationInfor tqinfor = quotationInforDAO.selectByPrimaryKey(idss.get(0));
      conInfor.setOwnContactPerson(tqinfor.getUserName());
      conInfor.setCustomerFax(tqinfor.getCustomerFax());
      conInfor.setCustomerPhone(tqinfor.getCustomerPhone());
      conInfor.setCusContactPerson(tqinfor.getCusContactPerson());
      conInfor.setCurrencyId(tqinfor.getCurrency());

      conInfor.setCustomerCode(tqinfor.getCustomerCode());
      conInfor.setCustomerName(tqinfor.getCustomerName());
      conInfor.setCurrencyName(tqinfor.getCurrencyName());
      conInfor.setSellerName(tqinfor.getSellerName());
      conInfor.setTaxRate(contractTaxRate != null ? contractTaxRate : tqinfor.getTaxRate());
      conInfor.setClosingAccountMode(tqinfor.getPaymentCondition());
      conInfor.setExemplarInvoice(tqinfor.getExemplarInvoice());
    }
    TQuotationInforExample example = new TQuotationInforExample();
    example.createCriteria().andIdIn(idss);
    List<TQuotationInfor> list = quotationInforDAO.selectByExample(example);
    StringBuffer bz = new StringBuffer(100);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TQuotationInfor quo = (TQuotationInfor) iterator.next();
      bz.append(quo.getMemo()).append("   ");
    }
    conInfor.setMemo(bz.toString());
    conInfor.setEffectConditions("本合同一式   份，卖方   份，买方   份，经双方签字盖章后生效。");
    conInfor.setOtherConvention("无");
    conInfor.setSignAddress("无");

    TCustomersInforExample custExample = new TCustomersInforExample();
    custExample.createCriteria().andCustomerCodeEqualTo(conInfor.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(custExample);
    if (cusList != null && cusList.size() > 0) {
      TCustomersInfor cus = cusList.get(0);
      conInfor.setDeliveryAddressType(cus.getContractAddress());
    }
    List<ContractProductSortDto> contractProductSorts = new ArrayList<ContractProductSortDto>();
    BigDecimal productMoney = BigDecimal.ZERO;
    BigDecimal totalMoney = BigDecimal.ZERO;
    for (int i = 0; i < ids.length; i++) {
      QuotationDto quodto = quotationInforDAO.getQuoInfoById(ids[i]);
      ContractProductSortDto e = new ContractProductSortDto();
      e.setId(GenerateSerial.getUUID());
      e.setName(quodto.getQuotationCode());
      List<QuotationDetailDto> qproDtos = quotationProductDetailDAO.getYuDingQuoDetail2CreateContract(quodto.getId());
      totalMoney = totalMoney.add(recountDetailTaxMoney(qproDtos, contractTaxRate));
      productMoney = productMoney.add(recountDetailMoney(qproDtos));
      setQuoDetailIds(qproDtos);
      List<ContractProductDetailDto> conProductDetail = convertToContractProductDetailDtos(qproDtos);
      e.setConProductDetail(conProductDetail);
      contractProductSorts.add(e);
    }
    conInfor.setTotalMoney(totalMoney);
    conInfor.setTaxMoney(totalMoney.subtract(productMoney));
    conInfor.setProductMoney(productMoney);

    conInfor.setContractProductSorts(contractProductSorts);
    conInfor.setFinalMoney(productMoney);
    return conInfor;
  }

  private void setQuoDetailIds(List<QuotationDetailDto> qproDtos) {
    // TODO Auto-generated method stub
    TContractProductDetailExample exp = new TContractProductDetailExample();
    for (Iterator iterator = qproDtos.iterator(); iterator.hasNext();) {
      QuotationDetailDto quotationDetailDto = (QuotationDetailDto) iterator.next();
      exp.clear();
      exp.createCriteria().andIdLike(quotationDetailDto.getId() + "#%");
      int c = contractProductDetailDAO.countByExample(exp);
      quotationDetailDto.setId(quotationDetailDto.getId() + "#" + c);
    }
  }

  private BigDecimal recountDetailMoney(List<QuotationDetailDto> qproDtos) {
    BigDecimal sum = BigDecimal.ZERO;
    for (Iterator iterator = qproDtos.iterator(); iterator.hasNext();) {
      QuotationDetailDto quotationDetailDto = (QuotationDetailDto) iterator.next();
      sum = sum.add(quotationDetailDto.getMoney());
    }
    return sum;
  }

  private BigDecimal recountDetailTaxMoney(List<QuotationDetailDto> qproDtos, BigDecimal contractTaxRate) {
    BigDecimal sum = BigDecimal.ZERO;
    for (Iterator iterator = qproDtos.iterator(); iterator.hasNext();) {
      QuotationDetailDto quotationDetailDto = (QuotationDetailDto) iterator.next();
      BigDecimal netprice = quotationDetailDto.getNetPrice();
      if (contractTaxRate != null) {
        quotationDetailDto.setTaxNetPrice(netprice.add(netprice.multiply(contractTaxRate)));
      }
      quotationDetailDto.setTaxMoney(quotationDetailDto.getTaxNetPrice().multiply(quotationDetailDto.getAmount()));
      sum = sum.add(quotationDetailDto.getTaxMoney());
    }
    return sum;
  }

  @Override
  public List findSchedue2ConInfors(Map<String, String> mparams) {
    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andIdLike(mparams.get("quoDetailId") + "%");
    List<TContractProductDetail> list = contractProductDetailDAO.selectByExample(example);
    List<ContractDetailDto> dtoList = new ArrayList<ContractDetailDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TContractProductDetail detail = (TContractProductDetail) iterator.next();
        ContractDetailDto dto = new ContractDetailDto();
        BeanUtils.copyProperties(dto, detail);
        dto.setContractProductDetailId(detail.getId());
        TContractInfor po = contractInforDAO.selectByPrimaryKey(detail.getContractInforId());
        if (po != null) {
          dto.setContractCode(po.getContractCode());
          dto.setStatus(po.getStatus());
        }
        dtoList.add(dto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dtoList;
  }

  @Override
  public void updateShedQuo2ConDetail(List<ContractDetailDto> list) {
    // TODO Auto-generated method stub
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      ContractDetailDto conDetailDto = (ContractDetailDto) iterator.next();
      TContractProductDetail detail = contractProductDetailDAO.selectByPrimaryKey(conDetailDto.getContractProductDetailId());
      detail.setSumArrivalAmount(conDetailDto.getSumArrivalAmount());
      detail.setSumDeliveryAmount(conDetailDto.getSumDeliveryAmount());
      detail.setSumOrderAmount(conDetailDto.getSumOrderAmount());
      detail.setSumOutAmount(conDetailDto.getSumOutAmount());
      contractProductDetailDAO.updateByPrimaryKey(detail);
    }
  }

  /**
   * 1.价格，结算方式 是否改变
   */
  @Override
  public boolean isFinancialContentChange(String id) {
    // TODO Auto-generated method stub
    TContractInfor infor = contractInforDAO.selectByPrimaryKey(id);
    //获取报价信息
    List<TQuotationInfor> quoInfors = getQuotationInforsByConId(id, infor);

    for (Iterator iterator = quoInfors.iterator(); iterator.hasNext();) {
      TQuotationInfor quotationInfor = (TQuotationInfor) iterator.next();
      if (quotationInfor.getPaymentCondition() != null && !quotationInfor.getPaymentCondition().equals(infor.getClosingAccountMode())) {//如果结算方式发生变化
        return true;
      }
    }

    //获取合同明细信息
    TContractProductDetailExample conExp = new TContractProductDetailExample();
    conExp.createCriteria().andContractInforIdEqualTo(id);
    List<TContractProductDetail> conDetails = contractProductDetailDAO.selectByExample(conExp);
    //获取报价单明细信息
    for (Iterator iterator = conDetails.iterator(); iterator.hasNext();) {
      TContractProductDetail conDetail = (TContractProductDetail) iterator.next();
      TQuotationProductDetail quoDetail = quotationProductDetailDAO.selectByPrimaryKey(conDetail.getId().substring(0, 32));//取前32位，如果是预订则id会后面加警号
      if (!conDetail.getNetPrice().equals(quoDetail.getNetPrice())) {//如果价格发生变化
        return true;
      }
    }
    return false;
  }

  private List<TQuotationInfor> getQuotationInforsByConId(String id, TContractInfor infor) {
    List<TQuotationInfor> quoInfors = new ArrayList<TQuotationInfor>();
    if (ContractInforDto.CONTRACT_TYPE_GENERAL.equals(infor.getContractType())) {//如果是普通报价单，或者预订报价单生成 的合同
      TContractProjectSortInforExample sortExp = new TContractProjectSortInforExample();
      sortExp.createCriteria().andContractInforIdEqualTo(id);
      List sorts = contractProjectSortInforDAO.selectByExample(sortExp);
      List<String> quoCodes = new ArrayList<String>();
      for (Iterator iterator = sorts.iterator(); iterator.hasNext();) {
        TContractProjectSortInfor sort = (TContractProjectSortInfor) iterator.next();
        quoCodes.add(sort.getProSortName());
      }
      //根据报价单号，获取报价单信息
      TQuotationInforExample quoExp = new TQuotationInforExample();
      quoExp.createCriteria().andQuotationCodeIn(quoCodes);
      quoInfors.addAll(quotationInforDAO.selectByExample(quoExp));
    } else {//如果是项目报价单
      TQuotationInforExample quoExp = new TQuotationInforExample();
      quoExp.createCriteria().andContractInforIdEqualTo(id);
      quoInfors.addAll(quotationInforDAO.selectByExample(quoExp));
    }
    return quoInfors;
  }

  //2.数量，交货期 是否变动
  @Override
  public boolean isWuLiuContentChange(String id) {
    // TODO Auto-generated method stub

    //获取合同明细信息
    TContractProductDetailExample conExp = new TContractProductDetailExample();
    conExp.createCriteria().andContractInforIdEqualTo(id);
    List<TContractProductDetail> conDetails = contractProductDetailDAO.selectByExample(conExp);
    //获取报价单明细信息
    for (Iterator iterator = conDetails.iterator(); iterator.hasNext();) {
      TContractProductDetail conDetail = (TContractProductDetail) iterator.next();
      TQuotationProductDetail quoDetail = quotationProductDetailDAO.selectByPrimaryKey(conDetail.getId().substring(0, 32));//取前32位，如果是预订则id会后面加警号
      if (!conDetail.getAmount().equals(quoDetail.getAmount())
        || (conDetail.getDeliveryDate() != null && !conDetail.getDeliveryDate().equals(quoDetail.getDeliveryDate()))) {//如果数量,交货期发生变化
        return true;
      }
    }
    return false;
  }

  public TOrderDetailDAO getOrderDetailDAO() {
    return orderDetailDAO;
  }

  public void setOrderDetailDAO(TOrderDetailDAO orderDetailDAO) {
    this.orderDetailDAO = orderDetailDAO;
  }

  public TAccessoriesDAO getAccessoriesDAO() {
    return accessoriesDAO;
  }

  public void setAccessoriesDAO(TAccessoriesDAO accessoriesDAO) {
    this.accessoriesDAO = accessoriesDAO;
  }

}
