package com.tl.resource.business.selfOrder;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.ContractProductDetailDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.dao.pojo.TContractProjectSortInfor;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public interface SelfOrderService {

  /**
   * 合同订单列表
   * 
   * @param parmMap
   * @return
   */
  public List<TOrderInfor> getContractOrderList(Map<String, Object> parmMap);

  public int getContractOrderListCount(Map<String, Object> parmMap);

  /**
   * 根据合同ID查找订单详细总数
   * 
   * @param contractId
   * @return
   */
  public int getOrderDetialTotalCountByOrderId(String orderId);

  /**
   * 根据ID删除合同订单
   * 
   * @param id
   */
  public void deleteContractOrderById(String id);

  /**
   * 根据订单ID删除订单详细
   * 
   * @param id
   */
  public void deleteOrderDetailByOrderId(String orderId);

  public void deleteOrder(String[] ids);

  /**
   * 审批退回
   * 
   * @param id
   * @return
   */
  public String cancelAudit(String id);

  /**
   * 审批通过
   * 
   * @param id
   * @return
   */
  public String endAudit(String id);

  /**
   * 提交审批
   * 
   * @param id
   * @return
   */
  public String submitAudit(String id);

  /**
   * 根据id得到订单信息
   * 
   * @param id
   * @return
   */
  public TOrderInfor getOrderInforById(String id);

  /**
   * 根据查询条件得到合同需要下订单的合同列表
   * 
   * @param parmMap
   * @return
   */
  public List<ContractInforDto> getContractList(Map<String, Object> parmMap);

  public int getContractListCount(Map<String, Object> parmMap);

  /**
   * 根据合同ID查找适合的供应商
   * 
   * @param contractId
   * @return
   */
  public List<TSuppliersInfor> getSupplierList(Map<String, Object> parmMap);

  public int getSupplierListCount(Map<String, Object> parmMap);

  /**
   * 根据合同和供应商查找合同详细
   * 
   * @param parmMap
   * @return
   */
  public List<OrderDetialsDto> getContractDetailList(Map<String, Object> parmMap);

  /**
   * 根据合同和供应商查找合同详细总数
   * 
   * @param parmMap
   * @return
   */
  public Integer getContractDetailListCount(Map<String, Object> parmMap);

  /**
   * 根据合同ID得到合同详细分类
   * 
   * @param contractId
   * @return
   */
  public List<TContractProjectSortInfor> getCPSIList(String contractId);

  /**
   * 创建订单
   * 
   * @param order
   * @return
   */
  // public TOrderInfor insertOrder(TOrderInfor order);
  /**
   * 创建订单
   * 
   * @param order
   * @return
   */
  public void addOrder(TOrderInfor order, JSONArray orderDetails);

  /**
   * 插入订单详细
   * 
   * @param orderDetail
   */
  public OrderDetialsDto insertOrderDetail(OrderDetialsDto orderDetail);

  /**
   * 更新加工品订单
   * 
   * @param order
   */
  public void updateOrder(TOrderInfor order);

  /**
   * 更新加工品订单详细
   * 
   * @param orderDetail
   */
  public void updateOrderDetail(OrderDetialsDto orderDetail);

  public void updateOrder(TOrderInfor order, JSONArray orderDetails, String[] ids);

  /**
   * 提交下单
   * 
   * @param order
   * @return
   */
  public int PlaceOrder(TOrderInfor order);

  public boolean deleteOrderDetailById(String id, TOrderInfor orderInfor);

  /**
   * 根据合同ID查看合同详细
   * 
   * @param parmMap
   * @return
   */
  public List<ContractProductDetailDto> getContractDetail(Map<String, Object> parmMap);

  public int getContractDetailCount(Map<String, Object> parmMap);

  public TSuppliersInfor getSupplierById(String id);

  public List<OrderDetialsDto> getExcelOrderDetail(Map<String, Object> parmMap);

  public OrderInfoDto getExcelOrderInfor(String id);

  public List<AccessoriesDto> getAccessoriesByBussinesId(Map<String, Object> parmMap);

  /**
   * 加工订单（将合同中加工品带入订单）
   * 
   * @param parmMap
   * @return
   */
  public List<OrderDetialsDto> getPartContractDetail(Map<String, Object> parmMap);

  public void updateFlag(String id, String value);
}
