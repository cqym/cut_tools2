package com.tl.resource.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProductDetailExample;

public interface TQuotationProductDetailDAO {

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int countByExample(TQuotationProductDetailExample example);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int deleteByExample(TQuotationProductDetailExample example);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int deleteByPrimaryKey(String id);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  void insert(TQuotationProductDetail record);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  void insertSelective(TQuotationProductDetail record);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  List selectByExample(TQuotationProductDetailExample example);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  TQuotationProductDetail selectByPrimaryKey(String id);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int updateByExampleSelective(TQuotationProductDetail record, TQuotationProductDetailExample example);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int updateByExample(TQuotationProductDetail record, TQuotationProductDetailExample example);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int updateByPrimaryKeySelective(TQuotationProductDetail record);

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_quotation_product_detail
   * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
   */
  int updateByPrimaryKey(TQuotationProductDetail record);

  /**
   * 添加报价单详细
   * @param quoDetail
   */
  public void insertQuoDetail(TQuotationProductDetail quoDetail);

  /**
   * 获取某报价详细信息 只获取Root节点信息
   * @param quoId 报价单ID
   * @return
   */
  public List<QuotationDetailDto> getQuoDetail(String quoId);

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
   * 根据报价单获取所有详细信息
   * @param id
   * @return
   */
  public List<QuotationDetailDto> getQuoDetailByQuo(String id);

  /**
   * 获取价格有变动的报价单详细信息
   * @param paramMap
   * @return
   */
  public List<TQuotationProductDetail> getQuoDetailByPriChange(Map<String, String> paramMap);

  /**
   * 根据参数获取产品信息
   * @param parmMap
   * @return
   */
  List<QuotationDetailDto> getQuoDetailList(Map<String, Object> parmMap);

  /**
   * 根据报价单ID删除对应产品信息
   * @param quoId
   */
  public void deleteProByQuoId(String quoId);

  /**
   * 更具工序ID删除产品信息
   * @param workOrderId
   */
  void deleteProByWorkId(String workOrderId);

  PaginationSupport getProducts4OutStock(Map params, int startIndex, int pageSize);

  public List<com.tl.resource.business.dto.QuotationDetailOutStockDto> getQuotationProductionsWillOutStock(String quotationInforId, String leaf);

  /**
   * 根据报价单删除报价单产品(ftl)
   * @param quoId
   */
  void deleteByQuoId(String quoId);

  /**
   * 根据报价单得到报价单详细列表
   * @param parmMap
   * @return
   */
  public List<TQuotationProductDetail> getQuotationDetailByInforId(Map<String, Object> parmMap);

  /**
   * 根据报价单得到报价单详细列表数量
   * @param parmMap
   * @return
   */
  public int getQuotationDetailCountByInforId(Map<String, Object> parmMap);

  /**
   * 根据供应商得到报价单详细列表
   * @param parmMap
   * @return
   */
  public List<OrderDetialsDto> getQuotationDetailBySupplier(Map<String, Object> parmMap);

  /**
   * 根据供应商得到报价单详细列表数量
   * @param parmMap
   * @return
   */
  public int getQuotationDetailCountBySupplier(Map<String, Object> parmMap);

  /**
   * 根据客户获取报价单产品（复制报价单所用）
   * @param parmMap
   * @return
   */
  public List<QuotationDetailDto> getQuoDetail(Map<String, Object> parmMap);

  /**
   * 根据报价单得到报价单详细列表(非标品)
   * @param parmMap
   * @return
   */
  public List<QuotationDetailForOrderDto> getQuoDetailById(Map<String, Object> parmMap);

  /**
   * 根据报价单得到报价单详细列表数量(非标品)
   * @param parmMap
   * @return
   */
  public int getQuoDetailCountById(Map<String, Object> parmMap);

  /**
   * 根据供应商得到报价单详细列表(非标品)
   * @param parmMap
   * @return
   */
  public List<OrderDetialsDto> getQuoDetailBySupplier(Map<String, Object> parmMap);

  /**
   * 根据供应商得到报价单详细列表数量(非标品)
   * @param parmMap
   * @return
   */
  public int getQuoDetailCountBySupplier(Map<String, Object> parmMap);

  /**
   * 根据客户获取报价单产品（项目复制报价单调用）
   * @param parmMap
   * @return
   */
  List<QuotationDetailDto> getProductList4Copy(Map<String, Object> parmMap);

  List<QuotationDetailDto> getOrderPrice4Quo(Map<String, Object> parmMap);

  /**
   * 根据工具信息获取报价单产品
   * @param toolsId
   * @return
   */
  List<QuotationDetailDto> getQuoDetaiByToolsId(String toolsId);

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

  int getQuotationDetailCountById(Map<String, Object> parmMap);

  List<TQuotationProductDetail> getQuotationDetailByIdForScheduleOrder(Map<String, Object> parmMap);

  int getQuoDetailFbForScheduleSelfOrderCountById(Map<String, Object> parmMap);

  List<QuotationDetailForOrderDto> getQuoDetailFbForScheduleSelfOrderById(Map<String, Object> parmMap);

  int updateQuotationDetailOrderAmount(HashMap<String, String> para);

  int updateQuotationDetailArrivalAmount(HashMap<String, String> para);

  int updateQuotationDetailDeliveryAmount(HashMap<String, String> para);

  int updateQuotationDetailOutAmount(HashMap<String, String> para);

  /**
   * 此方法返回的预定明细为，减去已转合同的明细
   * @param id
   * @return
   */
  List<QuotationDetailDto> getYuDingQuoDetail2CreateContract(String id);

  Integer getYuDingQuoDetail2CreateContractCount(String id);
}