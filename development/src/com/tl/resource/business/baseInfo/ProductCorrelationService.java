/**
 * 
 */
package com.tl.resource.business.baseInfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.resource.business.dto.CurSalesPriceHistoryDto;
import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.SalesPriceHistoryProDto;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TSalesPriceHistory;
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * @author xtaia
 * 产品相关
 */
public interface ProductCorrelationService {

  /**
   * 根据条件获取产品组别信息
   * @param parmMap
   * @return
   */
  List<TProductSort> getProductSortByPage(Map<String, Object> parmMap);

  /**
   * 添加组别信息
   * @param productSortInfo
   */
  void saveObject(TProductSort productSortInfo);

  /**
   * 删除产品组别信息
   * @param productSortId
   */
  void deleteProductSortById(String productSortId);

  /**
   * 更新产品组别信息
   * @param productSortInfo
   */
  void updateObject(TProductSort productSortInfo);

  /**
   * 得到已销售产品种类总数
   * @param parmMap 
   * @return
   */
  int getCusSalesHistoryProductTotal(Map<String, Object> parmMap);

  /**
   * 根据条件获取已销售产品种类信息 
   * @param parmMap
   * @return
   */
  List<CurSalesPriceHistoryDto> getCusSalesHistoryProductByPage(Map<String, Object> parmMap);

  /**
   * 根据产品获取其销售记录总数
   * @param parmMap
   * @return
   */
  int getCusSalesHistoryProductTotalByProductId(Map<String, Object> parmMap);

  /**
   * 根据条件获取产品销售记录信息
   * @param parmMap
   * @return
   */
  List<CurSalesPriceHistoryDto> getCusSalesHistoryProductByPageAndProductId(Map<String, Object> parmMap);

  /**
   * 删除客户销售历史记录
   * @param curSalerPriceIdPar
   */
  void deleteCurSalerPriceById(String curSalerPriceIdPar);

  /**
   * 获取已采购产品种类总数
   * @return
   */
  int getOrderPriceHistoryProductTotal(Map<String, Object> parmMap);

  /**
   * 获取已采购产品种类详细信息
   * @param parmMap
   * @return
   */
  List<OrderPriceHistoryDto> getOrderPriceHistoryProductByPage(Map<String, Object> parmMap);

  /**
   * 根据产品ID获取采购历史信息总数
   * @param parmMap
   * @return
   */
  int getOrderHistoryProductTotalByProductId(Map<String, Object> parmMap);

  /**
   * 根据产品id获取产品采购历史信息详情
   * @param parmMap
   * @return
   */
  List<OrderPriceHistoryDto> getOrderHistoryProductByPageAndProductId(Map<String, Object> parmMap);

  /**
   * 删除采购历史信息
   * @param orderPriceHistoryIdPar
   */
  void deleteOrderPriceHistoryById(String orderPriceHistoryIdPar);

  /**
   * 获取所有供应商信息
   * @param parmMap
   * @return
   */
  List<TSuppliersInfor> getAllSupplierList(Map<String, Object> parmMap);

  /**
   * 获取所有客户信息
   * @param parmMap
   * @return
   */
  List<TCustomersInfor> getAllCustomersList(Map<String, Object> parmMap);

  /**
   * 更新产品信息
   * @param tproductToolsInfor
   */
  void updateObject(TProductToolsInfor tproductToolsInfor);

  /**
   * 保存历史面价信息
   * @param tsalesPriceHistory
   */
  void saveObject(List<TSalesPriceHistory> tsalesPriceHistory);

  /**
   * 得到产品信息按ID
   * @param string
   * @return
   */
  TProductToolsInfor getProToolInfoByID(String id);

  /**
   * 
   * @param parmMap
   * @return
   */
  int getSalesHistoryProductTotalByProductId(Map<String, Object> parmMap);

  List<SalesPriceHistoryProDto> getSalesHistoryProductByPageAndProductId(Map<String, Object> parmMap);

  /**
   * 导入销售面价数据
   * 分为三步：
   * 1.上传文件
   * 2.验证上传文件有效性
   * 3.持久化数据
   * @param request
   * @param response
   * @return
   */
  Map<String, Object> importSalesPriceData(HttpServletRequest request, HttpServletResponse response);

  /**
   * 产品品牌信息
   * @param parmMap
   * @return
   */
  List<TProductBrand> getProductBrankList(Map<String, Object> parmMap);

  /**
   * Excel导入面价
   * @param request
   * @param response
   * @return
   */
  Map<String, Object> importSalesPriceExcelData(HttpServletRequest request, HttpServletResponse response);

  int getProductSortTotal(Map<String, Object> parmMap);

}
