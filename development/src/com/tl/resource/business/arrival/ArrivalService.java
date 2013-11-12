package com.tl.resource.business.arrival;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.ArrivalOrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TProductArrivalDetail;
import com.tl.resource.dao.pojo.TProductArrivalInfor;
import com.tl.resource.dao.pojo.TReserveInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public interface ArrivalService {
  /**
   * 根据类型获取订单 1：采购订单 2：储备订单
   * @param paramMap 查询参数
   * @return 订单信息Dto列表
   */
  public List<OrderInfoDto> getOrderInfoByType(Map<String, Object> paramMap);

  /**
   * 根据类型获取订单总数 1：采购订单 2：储备订单
   * @param paramMap 查询参数
   * @return
   */
  public Integer getOrderInfoTotalByType(Map<String, Object> paramMap);

  /**
   * 根据搜索条件获取到货单信息
   * @param paramMap
   * @return
   */
  public List<TProductArrivalInfor> getArrivalInfoBySearch(Map<String, Object> paramMap);

  /**
   *  根据搜索条件获取到货单信息数量
   * @param paramMap
   * @return
   */
  public Integer getArrInfoTotalBySearch(Map<String, Object> paramMap);

  /**
     * 获取属于根结点的产品总数
     * @return
     */
  public int getOrderDetailsTotal(String orderId);

  /**
   * 保存到货单信息
   * @param arrInfo
   */
  public void insertArrivalInfo(TProductArrivalInfor arrInfo);

  /**
   * 保存到货单信息
   * @param arrInfo
   */
  public void insertArrProduct(TProductArrivalDetail arrProduct);

  /**
   * 获取到货详细
   * @param arrivalId
   * @return
   */
  public List<ProductArrivalDetailDto> getArrivalDetail(String arrivalId);

  /**
   * 获取到货信息级联其孩子节点
   * @param arrivalId
   * @return
   */
  public TProductArrivalDetail getArrDetailWithChildren(String arrivalId);

  /**
   * 修改到货单信息
   * @param arrivalInfo
   */
  public void updateArrivalInfo(TProductArrivalInfor arrivalInfo);

  /**
   * 根据ID获取到货明细
   * @param arrivalDetail
   * @return
   */
  public TProductArrivalDetail getArrivalDetailById(TProductArrivalDetail arrivalDetail);

  /**
   * 修改到货单明细
   * @param arrivalDetail
   */
  public void updateArrivalDetail(TProductArrivalDetail arrivalDetail);

  /**
   * 删除到货单产品明细
   * @param arrProId
   */
  public void deleteArrivalDetail(String id);

  /**
   * 删除到货单产品明细孩子节点
   * @param list 产品列表
   */
  public void deleteArrivalDetail(List<ProductArrivalDetailDto> list);

  /**
  * 根据订单编号查找订单信息
  * @param paramMap
  * @return
  */
  public List<OrderInfoDto> getOrderInfoByCode(Map<String, Object> paramMap);

  /**
   * 根据ID获取到货单信息
   * @param id
   * @return
   */
  public TProductArrivalInfor getArrivalInfoById(String id);

  /**
   * 删除到货单
   * @param id
   */
  public void deleteArrivalInfo(String id);

  /**
   * 根据货品编号获取库存信息(ftl 2009-11-17)
   * @param id
   * @return
   */
  public TReserveInfor getReserveInfoByProCode(String id);

  /**
   * 添加库存信息
   * @param reserveInfo
   */
  public void insertReserveInfo(TReserveInfor reserveInfo);

  /**
   * 修改库存信息
   * @param reserveInfo
   */
  public void updateReserveInfo(TReserveInfor reserveInfo);

  /**
   * 添加帐页信息
   * @param accountsInfo
   */
  public void insertAccountsInfo(TAccountsInfor accountsInfo);

  /**
   * 修改帐页信息
   * @param accountsInfo
   */
  public void updateAccountsInfo(TAccountsInfor accountsInfo);

  /**
   * 根据ID获取供应商信息
   * @param id
   * @return
   */
  public TSuppliersInfor getSupplierById(String id);

  /**
   * 订单详细包含已经到货数量(ftl)2009-11-25
   * @param orderId
   * @return
   */
  public List<ArrivalOrderDetialsDto> getDetailWithHasArrivalByOrderId(String orderId);

  /**
   * 根据订单编号查找订单信息
   * @param paramMap
   * @return
   */
  public OrderInfoDto getOrderInfoByCode(String orderCode);

  /**
   * 根据Id获取到货单信息并抓取其对应的产品
   * @param arrInfoId
   * @return
   */
  public TProductArrivalInfor getArrInfoWithProById(String arrInfoId);

  /**
   * 到货确认后处理库存流程
   * @param list
   */
  public void addReveiveInfo(UserDto userDto, TProductArrivalInfor arrInfo) throws Exception;

  public void insertOutStockInfor(TOutStockInfor outStockInfo);

  public void insertOutStockDetail(TOutStockDetail outStockDetail);

  /**
   * 修改订单状态
   * @param orderInfo
   */
  public void updateOrderByIdSelective(TOrderInfor orderInfo);

  /**
   * 根据ID获取到货单信息
   * @param arrInfoId
   * @return
   */
  public ArrivalInforDto getArrInfoWithOrderType(String arrInfoId);

  /**
   * 订单产品是否已全部到货 大于0 未全部到货（ftl)2009-12-07
   * @param orderId
   * @return
   */
  public Integer getCanArrivalPro(String orderId);

  /**
     * 根据到货单ID获取到货单详细(查看明细调用)
     * @param arrivalId
     * @return
     */
  List<ProductArrivalDetailDto> getArrivalDetailByView(String arrivalId);

  String arrivalSubmit(JSONArray array, UserDto userDto);

  /**
  * 根据搜索条件获取直接入库单信息
  * @param paramMap
  * @return
  */
  public List<TProductArrivalInfor> getDirectArrivalBySearch(Map<String, Object> paramMap);

  /**
   *  根据搜索条件获取直接入库单信息数量
   * @param paramMap
   * @return
   */
  public Integer getDirectArrTotalBySearch(Map<String, Object> paramMap);

  /**
     * 获取直接入库详细
     * @param arrivalId
     * @return
     */
  List<TProductArrivalDetail> getDirectArrDetail(String arrivalId);

  public TProductArrivalInfor getDirectArrInfoById(String id);

  String directArrivalAudit(JSONArray array, UserDto userDto);

  //getDirectArrDetail
  List<ProductArrivalDetailDto> getDetail4Direct(String arrivalId);

  public Integer invalidArrivalInfo(Iterator<String> iterator);

  /**
   * 修改入库单的状态
   * @param arrInfo
   * @return
   */
  public Integer updateStatus(TProductArrivalInfor arrInfo);

  public void updateContractDetailArrivalAmount(String contractCode);

  public void updateQuotationDetailArrivalAmount(String quotationCode);

  public void sycContractArrivalDetail(String quotationCode);
}
