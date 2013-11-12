package com.tl.resource.business.scheduleOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.QuotationDetailForOrderDto;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TQuotationProjectSortInforDAO;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderPriceHistory;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;
import com.tl.resource.dao.pojo.TSalesPriceHistory;
import com.tl.resource.dao.pojo.TSuppliersInfor;


public class ScheduleOrderServiceImpl implements ScheduleOrderService {

	private TOrderInforDAO orderInforDao;
	private TQuotationInforDAO quotationInfoDAO;
	private TQuotationProductDetailDAO quotationProductDetailDAO;
	private TSuppliersInforDAO suppliersInforDao;
	private TOrderDetailDAO orderDetailDao;
	private TQuotationProjectSortInforDAO quotationProjectSortInforDAO;
	private TOrderPriceHistoryDAO orderPriceHistoryDao;
	private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;
	
	@Override
	public List<TOrderInfor> getScheduleOrderList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return orderInforDao.getOrderList(parmMap);
	}

	@Override
	public int getScheduleOrderListCount(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		Integer in = orderInforDao.getOrderTotal(parmMap);
		return in.intValue();
	}

	@Override
	public List<TQuotationInfor> getQuotationList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationInfoDAO.getQuotationListForOrder(parmMap);
	}

	@Override
	public int getQuotationListCount(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationInfoDAO.getQuotationListCountForOrder(parmMap);
	}
	
	@Override
	public List<TQuotationProductDetail> getQuotationDetailByInforId(
			Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationProductDetailDAO.getQuotationDetailByIdForScheduleOrder(parmMap);
	}

	@Override
	public int getQuotationDetailCountByInforId(
			Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationProductDetailDAO.getQuotationDetailCountById(parmMap);
	}

	@Override
	public List<TSuppliersInfor> getSupplierList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return suppliersInforDao.getSupplierListByQid(parmMap);
	}

	@Override
	public int getSupplierListCount(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return suppliersInforDao.getSupplierListCountByQid(parmMap);
	}

	@Override
	public List<OrderDetialsDto> getQuotationDetailBySupplier(
			Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationProductDetailDAO.getQuotationDetailBySupplier(parmMap);
	}

	@Override
	public int getQuotationDetailCountBySupplier(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationProductDetailDAO.getQuotationDetailCountBySupplier(parmMap);
	}

	@Override
	public List<OrderDetialsDto> getOrderDetailList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return orderDetailDao.getSOrderDetailList(parmMap);
	}

	@Override
	public int getOrderDetailListCount(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return orderDetailDao.getSOrderDetailListCount(parmMap);
	}
	
	

	@Override
	public String cancelAudit(String id) {
		// TODO Auto-generated method stub
		orderInforDao.cancelAudit(id);
		return null;
	}

	@Override
	public String endAudit(String id) {
		// TODO Auto-generated method stub
		orderInforDao.endAudit(id);
//		Map<String, Object> parmMap = new HashMap<String, Object>();
//		parmMap.put("orderId", id);
//		OrderInfoDto orderInfor = orderInforDao.getExcelOrderInfor(id);
//		List<OrderDetialsDto> orderDetailList  = orderDetailDao.getROrderDetailsList(parmMap);
//		if(orderDetailList!=null&&orderDetailList.iterator().hasNext())
//		{
//			for(int i = 0;i<orderDetailList.size();i++)
//			{
//				orderPriceHistoryDao.insertSelective(changeModel(orderDetailList.get(i),orderInfor));
//			}
//		}
		return null;
	}
	
	public TOrderPriceHistory changeModel(OrderDetialsDto orderDetail,OrderInfoDto orderInfor){
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("toolsId", orderDetail.getToolsId());
		List<TSalesPriceHistory> list = tsalesPriceHistoryDAO.getHistoryMarketPrice(parmMap);
		
		
		TOrderPriceHistory  orderPirceHistory = new TOrderPriceHistory(); 
		orderPirceHistory.setId(GenerateSerial.getUUID());
		orderPirceHistory.setSuppliersInforId(orderInfor.getSupplierId());
		orderPirceHistory.setProductToolsInforId(orderDetail.getToolsId());
		orderPirceHistory.setLeaf(1);
		orderPirceHistory.setParentId("root");
		orderPirceHistory.setProductCode(orderDetail.getProductCode());
		orderPirceHistory.setHistoryPrice(orderDetail.getPrice());
		orderPirceHistory.setBrandCode(orderDetail.getBrandCode());
		orderPirceHistory.setEditDate(new Date());
		orderPirceHistory.setUserId(orderInfor.getUserId());
		orderPirceHistory.setUserName(orderInfor.getUserName());
		orderPirceHistory.setStockPriceDate(orderInfor.getEditDate());
		if(list.size()>0)
		{
			orderPirceHistory.setHistoryMarketPrice(list.get(0).getHistoryPrice());
		}
		return orderPirceHistory;
	}

	@Override
	public TOrderInfor getOrderInforById(String id) {
		// TODO Auto-generated method stub
		return orderInforDao.selectByPrimaryKey(id);
	}

	@Override
	public String submitAudit(String id) {
		// TODO Auto-generated method stub
		orderInforDao.submitAudit(id);
		return null;
	}

	@Override
	public List<TQuotationProjectSortInfor> getSPSIList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return quotationProjectSortInforDAO.getSPSIList(parmMap);
	}

	public TOrderInforDAO getOrderInforDao() {
		return orderInforDao;
	}

	public void setOrderInforDao(TOrderInforDAO orderInforDao) {
		this.orderInforDao = orderInforDao;
	}

	public TQuotationInforDAO getQuotationInfoDAO() {
		return quotationInfoDAO;
	}

	public void setQuotationInfoDAO(TQuotationInforDAO quotationInfoDAO) {
		this.quotationInfoDAO = quotationInfoDAO;
	}

	public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
		return quotationProductDetailDAO;
	}

	public void setQuotationProductDetailDAO(
			TQuotationProductDetailDAO quotationProductDetailDAO) {
		this.quotationProductDetailDAO = quotationProductDetailDAO;
	}

	public TSuppliersInforDAO getSuppliersInforDao() {
		return suppliersInforDao;
	}

	public void setSuppliersInforDao(TSuppliersInforDAO suppliersInforDao) {
		this.suppliersInforDao = suppliersInforDao;
	}

	public TOrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(TOrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public TQuotationProjectSortInforDAO getQuotationProjectSortInforDAO() {
		return quotationProjectSortInforDAO;
	}

	public void setQuotationProjectSortInforDAO(
			TQuotationProjectSortInforDAO quotationProjectSortInforDAO) {
		this.quotationProjectSortInforDAO = quotationProjectSortInforDAO;
	}

	public TOrderPriceHistoryDAO getOrderPriceHistoryDao() {
		return orderPriceHistoryDao;
	}

	public void setOrderPriceHistoryDao(TOrderPriceHistoryDAO orderPriceHistoryDao) {
		this.orderPriceHistoryDao = orderPriceHistoryDao;
	}

	public TSalesPriceHistoryDAO getTsalesPriceHistoryDAO() {
		return tsalesPriceHistoryDAO;
	}

	public void setTsalesPriceHistoryDAO(TSalesPriceHistoryDAO tsalesPriceHistoryDAO) {
		this.tsalesPriceHistoryDAO = tsalesPriceHistoryDAO;
	}
	
	
}
