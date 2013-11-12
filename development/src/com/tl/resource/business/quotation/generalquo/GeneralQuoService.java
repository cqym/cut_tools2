package com.tl.resource.business.quotation.generalquo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.resource.business.dto.CompanyDto;
import com.tl.resource.business.dto.QuoCodeTreeDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.dto.SalesPriceHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.dao.pojo.TCusSalesPriceHistory;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public interface GeneralQuoService {
  /**
   * 获取报价信息
   * @param parmMap 参数MAP 包含查询条件和分布信息
   * @return 符合条件的报价单信息列表
   */
  public List<QuotationDto> getQuotationByPage(Map<String, Object> parmMap);

  /**
   * 得到客户信息
   * @param parmMap 参数MAP 包含查询条件和分布信息
   * @return 符合条件的客户信息列表
   */
  public List<TCustomersInfor> getCustomerInfoByBage(Map<String, Object> parmMap);

  /**
   * 得到客户总数
   * @param parmMap
   * @return
   */
  public int getCustomerInfoTotal(Map<String, Object> parmMap);

  /**
   * 添加报价信息
   * @param quotationInfor
   * @return
   */
  public TQuotationInfor insertQuotation(TQuotationInfor quotationInfor);

  /**
   * 添加报价单详细
   * @param quoDetail
   */
  public void insertQuoDetail(TQuotationProductDetail quoDetail);

  /**
   * 获取报价总数
   * @param parmMap 参数Map
   * @return
   */
  public int getQuotaionTotal(Map<String, Object> parmMap);

  /**
   * 获取公司信息供卖方下拉框使用
   * @return
   */
  public List<CompanyDto> getCompanyForCombox();

  /**
   * 删除报价信息
   * @param id
   */
  public void deleteQuotation(TQuotationInfor quoInfo) throws Exception;

  /**
   * 获取某报价详细信息
   * @param quoId 报价单ID
   * @return
   */
  public List<QuotationDetailDto> getQuoDetail(String quoId);

  /**
   * 审批退回
   * @param id
   * @return
   */
  public String cancelAudit(String id);

  /**
   * 审批通过
   * @param id
   * @return
   */
  public String endAudit(String id);

  /**
   * 提交审批
   * @param id
   * @return
   */
  public String submitAudit(String id);

  public QuotationDto getGeneralQuoInforById(String businessId);

  /**
   * 根据ID获取报价信息
   * @param id
   * @return
   */
  public QuotationDto getQuoInfoById(String id);

  /**
   * 修改报价信息
   * @param quoInfo
   */
  public void updateQuoInfo(TQuotationInfor quoInfo);

  /**
   * 根据ID获取报价详细
   * @param id
   * @return
   */
  public QuotationDetailDto getQuoDetailById(String id);

  /**
   * 修改报价详细信息
   * @param quoDetail
   */
  public void updateQuoDetail(TQuotationProductDetail quoDetail);

  /**
   * 删除报价详细
   * @param id
   */
  public void deleteQuoDetail(String id);

  /**
   * 批量删除报价详细
   * @param list
   */
  public void deleteQuoDetail(List<QuotationDetailDto> list);

  /**
   * 根据报价单获取所有详细信息
   * @param id
   * @return
   */
  public List<QuotationDetailDto> getQuoDetailByQuo(String id);

  /**
   * 获取对应产品的历史报价信息
   * @param paramMap
   * @return
   */
  public List<TCusSalesPriceHistory> getPriceHistoryByCustomer(Map<String, String> paramMap);

  /**
   * 获取产品历史报价信息，包含产品工具信息和历史报价信息的列表
   * @param paramMap
   * @return 
   */
  public List<SalesPriceHistoryDto> getSalesPriHistory(Map<String, String> paramMap);

  /**
   * 根据用户编号获取用户信息
   * @param customerCode
   * @return
   */
  public TCustomersInfor getCustomerByCusCode(String customerCode);

  /**
   * 获取币别信息
   * @return
   */
  public List<TExchangeRate> getCurrencyName();

  /**
   * 获取价格有变动的报价单详细信息
   * @param paramMap 参数Map 报价单ID
   * @return
   */
  public List<TQuotationProductDetail> getQuoDetailByPriChange(Map<String, String> paramMap);

  /**
   * 根据ID获取报价信息
   * @param id
   * @return 报价信息PO
   */
  public TQuotationInfor getQuotationById(TQuotationInfor quoInfo);

  /**
   * 添加历史报价信息
   * @param record
   */
  public void insertSelective(TCusSalesPriceHistory record);

  /**
   * 价格变动的产品，将本次价格保存至价格历史表
   * @param quoId
   */
  public void saveChangePriceProducts(String quoId);

  /**
   * 根据报价单删除报价单产品(ftl)
   * @param quoId
   */
  void deleteByQuoId(String quoId);

  /**
   * 根据名称获取币别
   * @param currencyName
   * @return
   */
  public TExchangeRate getCurrencyByName(String currencyName);

  public TExchangeRate getCurrencyById(String currencyId);

  public void updateQuoStatus(TQuotationInfor quoInfo);

  /**
   * 校验报价单是否包含非标品，包含返回 true, 否则返回false
   * @param quoId
   * @return
   */
  public boolean validator(String quoId);

  public String updateQuotation(JSONObject quoForm, JSONArray quoProductArray, JSONArray idArray);

  public String insertQuotation(JSONObject quoForm, JSONArray quoProductArray, UserDto userDto, BillsCodeDefService billsCodeDefService);

  /**
   * 根据客户获取报价单产品（复制报价单所用）
   * @param parmMap
   * @return
   */
  public List<QuotationDetailDto> getQuoDetail(Map<String, Object> parmMap);

  public boolean isPriceChange(String id);

  /**
   * 获取报价单产品中净价大于采购价格的产品
   * @param parmMap
   * @return
   */
  List<QuotationDetailDto> getOrderPrice4Quo(Map<String, Object> parmMap);

  public boolean isClosingAccountModeChange(String id);

  /**
   * 是否已全部交货
   * @param id
   * @return
   */
  public boolean isAllDeliveryed(String id);

  /**
   * 根据工具信息获取报价单产品
   * @param toolsId
   * @return
   */
  List<QuotationDetailDto> getQuoDetaiByToolsId(String toolsId);

  List<QuotationDto> getQuoInfoByImpQuoId(ArrayList<String> arrayList);

  List<QuoCodeTreeDto> getDeliveryCode(String quoId);

  List<QuoCodeTreeDto> getDeliveryCodeByQuoId(String quoId);

  /**
   * 预订报价单下的产品有多少还未转正式报价单
   * @param quoId
   * @return
   */
  Integer getNumByReserveQuo(String quoId);

  /**
   * 修改导入导入（导出）报价单单号
   * @param quoInfo
   */
  void updateImpQuoCode(TQuotationInfor quoInfo);

  /**
   * 修改预订报价单导出报价单号
   * @param parmMap
   * @return
   */
  Integer updateExportQuoCode(Map<String, Object> parmMap);

  public String deleteQuotation(Iterator<JSONObject> iterator);

  /**
   * 获取预订报价详细信息()
   * @param quoId 报价单ID
   * @return
   */
  public List<QuotationDetailDto> getQuoDetail4Reserve(String quoId);

  /**
   * 试刀详细
   */
  public List<QuotationDetailDto> getQuoDetail4TestCut(String quoId);

}
