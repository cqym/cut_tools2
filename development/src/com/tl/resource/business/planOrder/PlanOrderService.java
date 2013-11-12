package com.tl.resource.business.planOrder;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public interface PlanOrderService {
  /**
   * 查看订单列表
   * @return
   */
  List<TOrderInfor> getReserveOrderList(int status);

  /**
   * 根据查询条件得到订单列表
   * @param parmMap
   * @return
   */
  List<TOrderInfor> getOrderList(Map<String, Object> parmMap);

  int getOrderListCount(Map<String, Object> parmMap);

  /**
   * 根据查询条件得到订单的数量
   * @param parmMap
   * @return
   */
  public int getOrderTotal(Map<String, Object> parmMap);

  /**
    * 根据订单ID得到订单详细列表
    * @param stockOrderInforId
    * @return
    */
  List<OrderDetialsDto> getOrderDetailsList(Map<String, Object> parmMap);

  /**
     * 获取属于根结点的产品总数
     * @return
     */
  public int getOrderDetailsTotal(String orderId);

  /**
   * 获取所有的供应商的信息
   * @return
   */
  public List<TSuppliersInfor> getSuppliersInforList(Map<String, Object> parmMap);

  int getSuppliersInforListCount(Map<String, Object> parmMap);

  /**
   * 创建订单
   * @param order
   * @return
   */
  public TOrderInfor insertOrder(TOrderInfor order);

  /**
   * 创建订单
   * @param order
   * @return
   */
  public void addOrder(TOrderInfor order, JSONArray orderDetails);

  /**
   * 插入订单详细
   * @param orderDetail
   */
  public OrderDetialsDto insertOrderDetail(OrderDetialsDto orderDetail);

  /**
   * 更新库存订单
   * @return
   */
  public void updateOrder(TOrderInfor order);

  /**
   * 更新库存订单详细
   * @param orderDetail
   * @return
   */
  public void updateOrderDetail(OrderDetialsDto orderDetail);

  /**
   * 删除订单
   * @param orderId
   */
  public void deleteOrder(String[] ids);

  /**
   * 根据orderId删除订单详细
   * @param orderId
   */
  public void deleteOrderDetail(String orderId);

  /**
   * 根据id删除orderDetail
   * @param id
   */
  public void deleteOrderDetailById(String[] id);

  /**
   * 根据搜索条件获取产品信息总数
   * @param parmMap
   * @return
   */
  public int getProToolsTotal(Map<String, Object> parmMap);

  /**
   * 根据搜索条件获取产品信息
   */
  public List<TreeDto> getProToolsBySearch(Map<String, Object> parmMap);

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

  /**
   * 根据id得到订单信息
   * @param id
   * @return
   */
  public TOrderInfor getOrderInforById(String id);

  /**
   * 根据供应商ID得到储备计划
   * @param supplierId
   * @return
   */
  public List<ReservePlanDetailDto> getPlanList(Map<String, Object> parmMap);

  public int getPlanListCount(Map<String, Object> parmMap);

  /**
  * 提交下单
  * @param order
  * @return
  */
  public int PlaceOrder(TOrderInfor order);

  public void updateOrder(TOrderInfor order, JSONArray orderDetails, String[] ids);

  public TSuppliersInfor getSupplierById(String id);

  public OrderInfoDto getExcelOrderInfor(String id);

  public List<AccessoriesDto> getAccessoriesByBussinesId(Map<String, Object> parmMap);

  List<OrderDetialsDto> consultPlanInfors(String supplierId, String[] planId);
}
