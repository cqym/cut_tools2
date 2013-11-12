package com.tl.resource.business.quotation.generalquo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.CompanyDto;
import com.tl.resource.business.dto.QuoCodeTreeDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.dto.SalesPriceHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCusSalesPriceHistoryDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.pojo.TCusSalesPriceHistory;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProductDetailExample;

public class GeneralQuoServiceImpl implements GeneralQuoService {

  private TQuotationInforDAO quoInfoDAO;

  private TQuotationProductDetailDAO quoDetailDAO;

  private TCompanyInforDAO companyDAO;

  private TSalesPriceHistoryDAO priceHistoryDAO;

  private TCusSalesPriceHistoryDAO cusSalesPriDAO;

  private TExchangeRateDAO exchangeRateDAO;

  private TCustomersInforDAO customersInforDAO;

  @Override
  public List<QuotationDto> getQuotationByPage(Map<String, Object> parmMap) {
    return quoInfoDAO.getQuotationByPage(parmMap);
  }

  public TQuotationInforDAO getQuoInfoDAO() {
    return quoInfoDAO;
  }

  public void setQuoInfoDAO(TQuotationInforDAO quoInfoDAO) {
    this.quoInfoDAO = quoInfoDAO;
  }

  @Override
  public List<TCustomersInfor> getCustomerInfoByBage(Map<String, Object> parmMap) {
    return quoInfoDAO.getCustomerInfoByBage(parmMap);
  }

  @Override
  public TQuotationInfor insertQuotation(TQuotationInfor quotationInfor) {
    return quoInfoDAO.insertQuotation(quotationInfor);
  }

  public TQuotationProductDetailDAO getQuoDetailDAO() {
    return quoDetailDAO;
  }

  public void setQuoDetailDAO(TQuotationProductDetailDAO quoDetailDAO) {
    this.quoDetailDAO = quoDetailDAO;
  }

  @Override
  public void insertQuoDetail(TQuotationProductDetail quoDetail) {
    quoDetailDAO.insertQuoDetail(quoDetail);
  }

  @Override
  public int getQuotaionTotal(Map<String, Object> parmMap) {
    return quoInfoDAO.getQuotaionTotal(parmMap);
  }

  @Override
  public int getCustomerInfoTotal(Map<String, Object> parmMap) {
    return quoInfoDAO.getCustomerInfoTotal(parmMap);
  }

  @Override
  public List<CompanyDto> getCompanyForCombox() {
    return companyDAO.getCompanyForCombox();
  }

  public TCompanyInforDAO getCompanyDAO() {
    return companyDAO;
  }

  public void setCompanyDAO(TCompanyInforDAO companyDAO) {
    this.companyDAO = companyDAO;
  }

  @Override
  public void deleteQuotation(TQuotationInfor quoInfo) throws Exception {
    quoInfoDAO.deleteQuotation(quoInfo);
  }

  @Override
  public List<QuotationDetailDto> getQuoDetail(String quoId) {
    return quoDetailDAO.getQuoDetail(quoId);
  }

  @Override
  public String cancelAudit(String id) {
    TQuotationInfor quoInfo = new TQuotationInfor();
    quoInfo.setId(id);
    quoInfo.setStatus(3);
    //quoInfoDAO.updateByPrimaryKeySelective(quoInfo);
    quoInfoDAO.updateStatus(id, 3);
    return null;
  }

  @Override
  public String endAudit(String id) {
    TQuotationInfor quoInfo = new TQuotationInfor();
    quoInfo.setId(id);
    quoInfo = this.getQuotationById(quoInfo);
    quoInfo.setStatus(2);
    quoInfoDAO.updateStatus(id, 2);

    return null;
  }

  private void addChildsByParent(TQuotationProductDetail quoProDetail, TCusSalesPriceHistory salPriHistory) {
    List<TQuotationProductDetail> list = quoProDetail.getChildren();
    if (list != null && list.size() > 0) {
      for (TQuotationProductDetail quoDetail : list) {
        TCusSalesPriceHistory child = new TCusSalesPriceHistory();
        child.setId(GenerateSerial.getUUID());
        child.setParentId(salPriHistory.getId());
        child.setProductToolInforId(quoDetail.getToolsId());
        child.setLeaf(quoDetail.getLeaf());
        child.setBrandCode(quoDetail.getBrandCode());
        child.setProductCode(quoDetail.getProductCode());
        child.setRebate(quoDetail.getRebate());
        child.setNetPrice(quoDetail.getNetPrice());

        child.setEditDate(salPriHistory.getEditDate());
        child.setUserId(salPriHistory.getUserId());
        child.setUserName(salPriHistory.getUserName());
        child.setSalePriceDate(new Date());
        child.setStatus(0);
        child.setQuotationCode(salPriHistory.getQuotationCode());
        child.setCustomerInforId(salPriHistory.getCustomerInforId());
        child.setTaxNetPrice(quoDetail.getTaxNetPrice());
        this.insertSelective(child);

        if (quoDetail.getChildren() != null && quoDetail.getChildren().size() > 0) {
          addChildsByParent(quoDetail, child);
        }
      }
    }
  }

  @Override
  public String submitAudit(String id) {
    TQuotationInfor quoInfo = new TQuotationInfor();
    quoInfo.setId(id);
    quoInfo.setStatus(1);
    quoInfoDAO.updateStatus(id, 1);
    return null;
  }

  @Override
  public QuotationDto getGeneralQuoInforById(String businessId) {
    // TODO Auto-generated method stub
    QuotationDto dto = new QuotationDto();
    TQuotationInfor po = quoInfoDAO.selectByPrimaryKey(businessId);
    try {
      BeanUtils.copyProperties(dto, po);
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
  public QuotationDto getQuoInfoById(String id) {
    return quoInfoDAO.getQuoInfoById(id);
  }

  @Override
  public void updateQuoInfo(TQuotationInfor quoInfo) {
    quoInfoDAO.updateQuoInfo(quoInfo);//updateStatus(quoInfo.getId(),quoInfo.getStatus());
  }

  @Override
  public QuotationDetailDto getQuoDetailById(String id) {
    return quoDetailDAO.getQuoDetailById(id);
  }

  @Override
  public void updateQuoDetail(TQuotationProductDetail quoDetail) {
    quoDetailDAO.updateQuoDetail(quoDetail);
  }

  @Override
  public void deleteQuoDetail(String id) {
    quoDetailDAO.deleteQuoDetail(id);
  }

  @Override
  public void deleteQuoDetail(List<QuotationDetailDto> list) {
    if (list != null && list.size() > 0) {
      for (QuotationDetailDto quoDetail : list) {
        this.deleteQuoPro(this.getQuoDetailById(quoDetail.getId()));
      }
    }
  }

  private void deleteQuoPro(QuotationDetailDto dto) {
    /*List<QuotationDetailDto> children = dto.getChildren();
    if(children != null && children.size() > 0) {
    	for(QuotationDetailDto dto2 : children) {
    		dto2 = this.getQuoDetailById(dto2.getId());
    		List<QuotationDetailDto> list = dto2.getChildren();
    		
    		if(list != null && list.size() > 0) {
    			deleteQuoPro(dto2);
    		}
    		
    		this.deleteQuoDetail(dto2.getId());
    	}
    }*/

    if (dto != null) {
      this.deleteQuoDetail(dto.getId());
      String id = dto.getId();
      int index = id.indexOf("#");
      if (index != -1) {
        id = id.substring(0, index);
        QuotationDetailDto detailDto = this.quoDetailDAO.getQuoDetailById(id);
        if (detailDto != null) {
          //普通报价单ID
          String generalQuoId = dto.getQuotationInforId();
          //预订报价单ID
          String quoId = detailDto.getQuotationInforId();
          Map<String, Object> parmMap = new HashMap<String, Object>();
          parmMap.put("generalQuoID", generalQuoId);
          parmMap.put("quoID", quoId);

          int updatRresult = quoInfoDAO.updateImpQuoCode(parmMap);
          quoInfoDAO.updateStatus(quoId, 6);
          if (updatRresult > 0) {
            this.updateExportQuoCode(parmMap);
          }
        }
      }
    }
  }

  @Override
  public List<QuotationDetailDto> getQuoDetailByQuo(String id) {
    return quoDetailDAO.getQuoDetailByQuo(id);
  }

  @Override
  public List<TCusSalesPriceHistory> getPriceHistoryByCustomer(Map<String, String> paramMap) {
    return priceHistoryDAO.getPriceHistoryByCustomer(paramMap);
  }

  public TSalesPriceHistoryDAO getPriceHistoryDAO() {
    return priceHistoryDAO;
  }

  public void setPriceHistoryDAO(TSalesPriceHistoryDAO priceHistoryDAO) {
    this.priceHistoryDAO = priceHistoryDAO;
  }

  @Override
  public List<SalesPriceHistoryDto> getSalesPriHistory(Map<String, String> paramMap) {
    return priceHistoryDAO.getSalesPriHistory(paramMap);
  }

  @Override
  public TCustomersInfor getCustomerByCusCode(String customerCode) {
    return quoInfoDAO.getCustomerByCusCode(customerCode);
  }

  @Override
  public List<TExchangeRate> getCurrencyName() {
    return exchangeRateDAO.getCurrencyName();
  }

  public TExchangeRateDAO getExchangeRateDAO() {
    return exchangeRateDAO;
  }

  public void setExchangeRateDAO(TExchangeRateDAO exchangeRateDAO) {
    this.exchangeRateDAO = exchangeRateDAO;
  }

  @Override
  public List<TQuotationProductDetail> getQuoDetailByPriChange(Map<String, String> paramMap) {
    return quoDetailDAO.getQuoDetailByPriChange(paramMap);
  }

  @Override
  public TQuotationInfor getQuotationById(TQuotationInfor quoInfo) {
    return quoInfoDAO.getQuotationById(quoInfo);
  }

  @Override
  public void insertSelective(TCusSalesPriceHistory record) {
    cusSalesPriDAO.insertSelective(record);
  }

  public TCusSalesPriceHistoryDAO getCusSalesPriDAO() {
    return cusSalesPriDAO;
  }

  public void setCusSalesPriDAO(TCusSalesPriceHistoryDAO cusSalesPriDAO) {
    this.cusSalesPriDAO = cusSalesPriDAO;
  }

  @Override
  public void saveChangePriceProducts(String quoId) {
    TQuotationInfor quoInfo = quoInfoDAO.selectByPrimaryKey(quoId);
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("quoInfoId", quoId);
    //客户信息
    TCustomersInfor customer = null;
    if (quoInfo.getCustomerCode() != null && !"".equals(quoInfo.getCustomerCode())) {
      customer = quoInfoDAO.getCustomerByCusCode(quoInfo.getCustomerCode());
    }
    List<TQuotationProductDetail> list = this.getQuoDetailByPriChange(paramMap);

    for (TQuotationProductDetail quoDetail : list) {
      TCusSalesPriceHistory salPriHistory = new TCusSalesPriceHistory();
      salPriHistory.setId(GenerateSerial.getUUID());
      salPriHistory.setParentId(quoDetail.getParentToolsId());
      salPriHistory.setProductToolInforId(quoDetail.getToolsId());
      salPriHistory.setLeaf(quoDetail.getLeaf());
      salPriHistory.setBrandCode(quoDetail.getBrandCode());
      salPriHistory.setProductCode(quoDetail.getProductCode());
      salPriHistory.setRebate(quoDetail.getRebate());
      salPriHistory.setNetPrice(quoDetail.getNetPrice());
      salPriHistory.setHistoryPrice(quoDetail.getPrice());

      salPriHistory.setEditDate(quoInfo.getEditDate());
      salPriHistory.setUserId(quoInfo.getEditorId());
      salPriHistory.setUserName(quoInfo.getEditorName());
      salPriHistory.setSalePriceDate(new Date());
      salPriHistory.setStatus(0);

      salPriHistory.setQuotationCode(quoInfo.getQuotationCode());
      salPriHistory.setTaxNetPrice(quoDetail.getTaxNetPrice());
      if (customer != null) {
        salPriHistory.setCustomerInforId(customer.getId());
      }

      this.insertSelective(salPriHistory);

      addChildsByParent(quoDetail, salPriHistory);

    }
  }

  @Override
  public void deleteByQuoId(String quoId) {
    // TODO Auto-generated method stub
    quoDetailDAO.deleteByQuoId(quoId);
  }

  @Override
  public TExchangeRate getCurrencyByName(String currencyName) {
    return exchangeRateDAO.getCurrencyByName(currencyName);
  }

  @Override
  public void updateQuoStatus(TQuotationInfor quoInfo) {
    quoInfoDAO.updateStatus(quoInfo.getId(), quoInfo.getStatus());
  }

  @Override
  public boolean validator(String quoId) {
    boolean flag = false;
    List<QuotationDetailDto> list = this.getQuoDetail(quoId);
    for (QuotationDetailDto dto : list) {
      if (!dto.isLeaf()) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  public String updateQuotation(JSONObject quoForm, JSONArray quoProductArray, JSONArray idArray) {
    String resultStr = "{success : true, msg : '修改报价信息成功'}";
    try {

      List<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();
      Iterator<JSONObject> idIterator = idArray.iterator();
      while (idIterator.hasNext()) {
        JSONObject idObj = idIterator.next();
        QuotationDetailDto quoDetail = new QuotationDetailDto();
        quoDetail.setId(idObj.getString("id"));
        list.add(quoDetail);
      }

      if (list.size() > 0) {
        this.deleteQuoDetail(list);
      }

      TQuotationInfor quoInfo = (TQuotationInfor) JSONObject.toBean(quoForm, TQuotationInfor.class);

      if (quoInfo != null) {
        quoInfo.setCustomerCode(null);
        quoInfo.setCustomerName(null);
        quoInfo.setUpdateDate(new Date());//存报价单修改日期
        quoInfo.setEditorName(null);
        QuotationDto quotationInfo = this.getQuoInfoById(quoInfo.getId());
        if (quotationInfo != null) {
          //导入报价单编号
          String impToQuoCode = "";
          //预订报价单ID
          String resQuoId = "";

          if (quoInfo.getStatus() != 0 && quoInfo.getStatus() != 3)
            quoInfo.setStatus(0);

          Iterator<JSONObject> iterator = quoProductArray.iterator();
          while (iterator.hasNext()) {
            JSONObject quoProduct = iterator.next();
            TQuotationProductDetail quoDetail = null;

            quoDetail = (TQuotationProductDetail) JSONObject.toBean(quoProduct, TQuotationProductDetail.class);
            if (this.getQuoDetailById(quoDetail.getId()) != null) {

              if (!quoDetail.getQuotationInforId().equals(quoInfo.getId())) {
                //如果产品对应的报价单ID与当前报价单ID不同，则不修改，视为预订报价单重复导出
                continue;
              }
              quoDetail.putLeaf(quoProduct.getString("leaf"));
              quoDetail.setQuotationInforId(null);
              this.updateQuoDetail(quoDetail);

            } else {
              String id = GenerateSerial.getUUID();
              if (quoDetail.getQuotationInforId() == null) {
                //通过报价单ID是否为空，如果为NULL 不是预订报价单的产品，需要指定ID
                quoDetail.setId(id);
              } else {
                //预订报价单产品处理流程,由于此处是一个产品迭代，所以此处只需要执行一次。
                if ("".equals(resQuoId)) {
                  resQuoId = quoDetail.getQuotationInforId();//预订报价单ID
                  impToQuoCode = this.buildImpCode(quotationInfo.getImpToQuoCode(), resQuoId);
                }

              }
              quoDetail.setQuotationInforId(quoInfo.getId());
              quoDetail.setParentToolsId(TProductToolsInfor.ROOT_PRARENT_ID);
              quoDetail.putLeaf(quoProduct.getString("leaf"));
              if ("".equals(quoDetail.getQuotationProjectSortId()))
                quoDetail.setQuotationProjectSortId(null);
              this.insertQuoDetail(quoDetail);

            }

          }
          if (!"".equals(impToQuoCode))
            quoInfo.setImpToQuoCode(impToQuoCode);
          //quoInfo.setImpToQuoCode(null);
          this.updateQuoInfo(quoInfo);//修改报价单信息

          if (!"".equals(resQuoId)) {
            //修改预订报价单
            TQuotationInfor resQuo = new TQuotationInfor();
            resQuo.setId(resQuoId);

            String newImpCode = this.buildImpCode(this.getQuoInfoById(resQuoId).getImpToQuoCode(), quoInfo.getId());

            if (!"".equals(newImpCode)) {
              resQuo.setImpToQuoCode(newImpCode);
              this.updateImpQuoCode(resQuo);
            }

            if (this.getNumByReserveQuo(resQuoId) == 0) {
              resQuo.setStatus(7);
              this.updateQuoStatus(resQuo);//修改预订报价单状态为已导出预订
            }
          }
        } else {
          resultStr = "{success : false, msg : '你修改的报价信息不存在！'}";
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '修改报价信息失败！'}";
    }

    return resultStr;
  }

  private String buildImpCode(String impCode, String quoId) {
    List<String> codeList = new ArrayList<String>();
    if (impCode != null && !"".equals(impCode)) {
      String[] codeArray = impCode.split(",");
      for (String code : codeArray) {
        codeList.add(code);
      }
    }

    String newImpCode = "";

    if (!codeList.contains(quoId)) {
      if (codeList.size() > 0)
        newImpCode = new StringBuffer(impCode).append(",").append(quoId).toString();
      else
        newImpCode = quoId;
    }

    return newImpCode;
  }

  private ArrayList<TQuotationProductDetail> insertChildrenProtools(JSONObject proTools, TQuotationProductDetail dto) {
    JSONArray arr = proTools.getJSONArray("children");
    ArrayList<TQuotationProductDetail> list = new ArrayList<TQuotationProductDetail>();
    for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {
      JSONObject top = (JSONObject) iterator2.next();
      TQuotationProductDetail po2 = (TQuotationProductDetail) JSONObject.toBean(top, TQuotationProductDetail.class);
      po2.setParentToolsId(dto.getId());
      po2.setId(GenerateSerial.getUUID());
      po2.setQuotationInforId(dto.getQuotationInforId());
      po2.putLeaf(top.getString("leaf"));
      //po2.setToolsId(top.getString("id"));
      po2.setProjectCode(null);
      if ("".equals(po2.getQuotationProjectSortId()))
        po2.setQuotationProjectSortId(null);
      list.add(po2);
      JSONArray arr2 = null;
      try {
        arr2 = top.getJSONArray("children");

      } catch (Exception e) {
        po2.setLeaf(1);
      }

      this.insertQuoDetail(po2);

      if (arr2 != null && arr2.size() > 0) {
        po2.setChildren(insertChildrenProtools(top, po2));
      } else {
      }
      //throw new RuntimeException();
    }
    dto.setChildren(list);
    return list;
  }

  @Override
  public String insertQuotation(JSONObject quoForm, JSONArray quoProductArray, UserDto userDto, BillsCodeDefService billsCodeDefService) {
    String resultStr = "{success : true, msg : '添加报价信息成功'}";

    try {
      TQuotationInfor quoInfo = (TQuotationInfor) JSONObject.toBean(quoForm, TQuotationInfor.class);//this.quoJsonObj2Pojo(quoForm);

      if (quoInfo != null) {

        String cusCode = quoInfo.getCustomerCode();
        String customerCode = "";
        String customerName = "";
        if (cusCode != null && !"".equals(cusCode)) {
          int index = cusCode.indexOf("-");
          customerCode = cusCode.substring(0, index);
          customerName = cusCode.substring(index + 1, cusCode.length());
        }
        quoInfo.setCustomerCode(customerCode);
        quoInfo.setCustomerName(customerName);
        //quoInfo.setSellerName(quoForm.getString("companyName"));
        quoInfo.setId(GenerateSerial.getUUID());
        quoInfo.setEditDate(new Date());
        quoInfo.setStatus(0);
        if (userDto != null) {
          quoInfo.setEditorId(userDto.getId());
          quoInfo.setEditorName(userDto.getUserName());
        } else {
          return "{success : false, msg : '登录已经过期,请重新登录!'}";
        }
        //quoInfo.setQuotationType(0);
        //quoInfo.setSellerName(quoForm.getString("companyName"));
        String codeType = "02";
        if (quoInfo.getQuotationType() == 3) {
          codeType = "09";
        } else if (quoInfo.getQuotationType() == 4) {
          codeType = "10";
        }
        quoInfo.setQuotationCode(billsCodeDefService.getBillCode(codeType, null, customerCode, "WW"));
        this.insertQuotation(quoInfo);

        Iterator<JSONObject> iterator = quoProductArray.iterator();
        while (iterator.hasNext()) {
          JSONObject quoProduct = iterator.next();
          TQuotationProductDetail quoDetail = null;

          quoDetail = (TQuotationProductDetail) JSONObject.toBean(quoProduct, TQuotationProductDetail.class);

          quoDetail.setId(GenerateSerial.getUUID());
          quoDetail.setQuotationInforId(quoInfo.getId());
          quoDetail.setParentToolsId(TProductToolsInfor.ROOT_PRARENT_ID);
          quoDetail.putLeaf(quoProduct.getString("leaf"));
          if ("".equals(quoDetail.getQuotationProjectSortId()))
            quoDetail.setQuotationProjectSortId(null);
          //quoDetail.setPrice(BigDecimal.valueOf(quoProduct.getDouble("salePrice")));
          //quoDetail.setToolsId(quoProduct.getString("id"));
          //改变价格变动状态0 未改变，1 已改变
          if ("".equals(quoProduct.getString("priceChange"))) {
            quoDetail.setPriceChange(0);
          } else if (!"".equals(quoProduct.getString("priceChange")) && "1".equals(quoProduct.getString("priceChange"))) {
            quoDetail.setPriceChange(1);
          }

          this.insertQuoDetail(quoDetail);

          /*boolean hasChildNodes = true;
          
          if(!quoProduct.has("children"))
          	hasChildNodes = false;
          
          if(hasChildNodes) {
          	//insertChildrenProtools(quoProduct, quoDetail);
          }*/
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '添加报价信息失败'}";
    }

    return resultStr;
  }

  @Override
  public TExchangeRate getCurrencyById(String currencyId) {
    TExchangeRate rate = exchangeRateDAO.selectByPrimaryKey(currencyId);
    return rate;
  }

  @Override
  public List<QuotationDetailDto> getQuoDetail(Map<String, Object> parmMap) {
    return quoDetailDAO.getQuoDetail(parmMap);
  }

  @Override
  public boolean isPriceChange(String id) {
    TQuotationProductDetailExample example = new TQuotationProductDetailExample();
    example.createCriteria().andPriceChangeNotEqualTo(0).andQuotationInforIdEqualTo(id);
    int priceChangeCount = quoDetailDAO.countByExample(example);
    return priceChangeCount > 0;
  }

  @Override
  public List<QuotationDetailDto> getOrderPrice4Quo(Map<String, Object> parmMap) {
    return quoDetailDAO.getOrderPrice4Quo(parmMap);
  }

  @Override
  public boolean isClosingAccountModeChange(String id) {
    // TODO Auto-generated method stub
    TQuotationInfor quo = quoInfoDAO.selectByPrimaryKey(id);
    TCustomersInforExample example = new TCustomersInforExample();
    example.createCriteria().andCustomerCodeEqualTo(quo.getCustomerCode());
    List<TCustomersInfor> list = customersInforDAO.selectByExample(example);
    if (list != null && list.size() > 0) {
      String cam = list.get(0).getClosingAccountMode();
      cam = (cam == null ? "" : cam);
      return !cam.equals(quo.getPaymentCondition());
    }
    return true;
  }

  public TCustomersInforDAO getCustomersInforDAO() {
    return customersInforDAO;
  }

  public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
    this.customersInforDAO = customersInforDAO;
  }

  @Override
  @Deprecated
  public boolean isAllDeliveryed(String id) {
    TQuotationProductDetailExample example = new TQuotationProductDetailExample();
    example.createCriteria().andQuotationInforIdEqualTo(id).andIdNotLike("%#");
    int count = quoDetailDAO.countByExample(example);
    if (count > 0)
      return false;//如果此报价单不是  预定转正的报价，或者不是 全部 预定转正的报价，则不继续下面判断
    TQuotationInfor quo = quoInfoDAO.selectByPrimaryKey(id);
    if (quo.getImpToQuoCode() == null || quo.getImpToQuoCode().length() == 0)
      return false;
    String[] ids = quo.getImpToQuoCode().split("\\,");
    for (int j = 0; j < ids.length; j++) {
      if (!quoInfoDAO.isAllDeliveryed(ids[j])) {
        return false;//如果 有一个 没有交货完毕，则返回 未交货完毕
      }
    }
    return true;
  }

  @Override
  public List<QuotationDetailDto> getQuoDetaiByToolsId(String toolsId) {
    return quoDetailDAO.getQuoDetaiByToolsId(toolsId);
  }

  @Override
  public List<QuotationDto> getQuoInfoByImpQuoId(ArrayList<String> arrayList) {
    return quoInfoDAO.getQuoInfoByImpQuoId(arrayList);
  }

  @Override
  public List<QuoCodeTreeDto> getDeliveryCode(String quoId) {
    return quoInfoDAO.getDeliveryCode(quoId);
  }

  @Override
  public List<QuoCodeTreeDto> getDeliveryCodeByQuoId(String quoId) {
    return quoInfoDAO.getDeliveryCodeByQuoId(quoId);
  }

  @Override
  public Integer getNumByReserveQuo(String quoId) {
    return quoInfoDAO.getNumByReserveQuo(quoId);
  }

  @Override
  public void updateImpQuoCode(TQuotationInfor quoInfo) {
    quoInfoDAO.updateImpQuoCode(quoInfo);
  }

  @Override
  public Integer updateExportQuoCode(Map<String, Object> parmMap) {
    return quoInfoDAO.updateExportQuoCode(parmMap);
  }

  @Override
  public String deleteQuotation(Iterator<JSONObject> iterator) {
    String resultStr = "{success : true, msg : '删除报价信息成功!'}";
    while (iterator.hasNext()) {
      JSONObject jsonObj = iterator.next();
      String quoId = jsonObj.getString("id");
      String quoType = jsonObj.getString("quotationType");
      //正式报价单
      TQuotationInfor quoInfo = new TQuotationInfor();
      quoInfo.setId(quoId);
      try {
        String impCode = this.getQuotationById(quoInfo).getImpToQuoCode();
        this.deleteByQuoId(quoId);
        this.deleteQuotation(quoInfo);

        //如果为普通报价单 则执行下列操作
        if ("0".equals(quoType)) {
          //得到预订报价单
          if (impCode != null && !"".equals(impCode)) {
            String[] ids = impCode.split(",");
            for (int i = 0; i < ids.length; i++) {
              //正式报价单
              String exportCode = this.getQuoInfoById(ids[i]).getImpToQuoCode();
              String newExportCode = this.buildExportCode(exportCode, quoId);
              TQuotationInfor resQuoInfo = new TQuotationInfor();
              resQuoInfo.setId(ids[i]);
              resQuoInfo.setImpToQuoCode(newExportCode);
              resQuoInfo.setStatus(6);
              this.updateImpQuoCode(resQuoInfo);
              this.updateQuoStatus(resQuoInfo);
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        resultStr = "{success : false, msg : '删除报价信息失败!'}";
      }
    }

    return resultStr;
  }

  private String buildExportCode(String exportCode, String quoId) {
    List<String> codeList = new ArrayList<String>();
    if (exportCode != null && !"".equals(exportCode)) {
      String[] codeArray = exportCode.split(",");
      for (String code : codeArray) {
        codeList.add(code);
      }
    }

    String newImpCode = "";

    if (codeList.contains(quoId)) {
      codeList.remove(quoId);
      for (int i = 0; i < codeList.size(); i++) {
        if ("".equals(newImpCode)) {
          newImpCode = codeList.get(i);
        } else {
          newImpCode += "," + codeList.get(i);
        }
      }
    }

    return newImpCode;
  }

  @Override
  public List<QuotationDetailDto> getQuoDetail4Reserve(String quoId) {
    return quoDetailDAO.getQuoDetail4Reserve(quoId);
  }

  @Override
  public List<QuotationDetailDto> getQuoDetail4TestCut(String quoId) {
    return quoDetailDAO.getQuoDetail4TestCut(quoId);
  }
}
