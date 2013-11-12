package com.tl.resource.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TOrderInforExample;

public class TOrderInforDAOImpl extends SqlMapClientDaoSupport implements
		TOrderInforDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public TOrderInforDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int countByExample(TOrderInforExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_order_infor.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByExample(TOrderInforExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"t_order_infor.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByPrimaryKey(String id) {
		TOrderInfor key = new TOrderInfor();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"t_order_infor.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public void insert(TOrderInfor record) {
		getSqlMapClientTemplate().insert(
				"t_order_infor.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public void insertSelective(TOrderInfor record) {
		getSqlMapClientTemplate().insert(
				"t_order_infor.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public List selectByExample(TOrderInforExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"t_order_infor.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public TOrderInfor selectByPrimaryKey(String id) {
		TOrderInfor key = new TOrderInfor();
		key.setId(id);
		TOrderInfor record = (TOrderInfor) getSqlMapClientTemplate()
				.queryForObject(
						"t_order_infor.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExampleSelective(TOrderInfor record,
			TOrderInforExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update(
						"t_order_infor.ibatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExample(TOrderInfor record, TOrderInforExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_order_infor.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKeySelective(TOrderInfor record) {
		int rows = getSqlMapClientTemplate().update(
				"t_order_infor.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKey(TOrderInfor record) {
		int rows = getSqlMapClientTemplate().update(
				"t_order_infor.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds
	 * to the database table t_order_infor
	 * 
	 * @ibatorgenerated Wed Oct 14 14:13:27 CST 2009
	 */
	private static class UpdateByExampleParms extends TOrderInforExample {
		private Object record;

		public UpdateByExampleParms(Object record, TOrderInforExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public TOrderInfor insertOrder(TOrderInfor order) {
		return (TOrderInfor) this.getSqlMapClientTemplate().insert(
				"t_order_infor.ibatorgenerated_insertSelective", order);
	}

	@Override
	public List<TOrderInfor> getOrderList(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		return (List<TOrderInfor>) this.getSqlMapClientTemplate().queryForList(
				"t_order_infor.getOrderList", parmMap);
	}

	@Override
	public int getOrderTotal(Map<String, Object> parmMap) {
		// TODO Auto-generated method stub
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("t_order_infor.getOrderTotalBySearch", parmMap);
		return count.intValue();
	}

	@Override
	public String cancelAudit(String id) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update("t_order_infor.cancelAudit", id);
		return null;
	}

	@Override
	public String endAudit(String id) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update("t_order_infor.endAudit", id);
		return null;
	}

	@Override
	public String submitAudit(String id) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update("t_order_infor.submitAudit", id);
		return null;
	}

	@Override
	public List<OrderInfoDto> getOrderInfoByType(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				"orderInfor.getOrderInfoByType", paramMap);
	}

	@Override
	public Integer getOrderInfoTotalByType(Map<String, Object> paramMap) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"orderInfor.getOrderTotalByType", paramMap);
	}

	@Override
	public List<OrderInfoDto> getOrderInfoByCode(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList(
				"orderInfor.getOrderInfoByCode", paramMap);
	}

	@Override
	public OrderInfoDto getOrderInfoByCode(String orderCode) {
		return (OrderInfoDto) this.getSqlMapClientTemplate().queryForObject(
				"orderInfor.getOrderByOrderCode", orderCode);
	}

	@Override
	public OrderInfoDto getExcelOrderInfor(String id) {
		return (OrderInfoDto) this.getSqlMapClientTemplate().queryForObject(
				"t_order_infor.getExcelOrderInfor", id);
	}

	@Override
	public Map<String, Object> getOrderTotalMoneys(Map<String, Object> parmMap) {
		Map<String, Object> totalMap = (Map<String, Object>) this
				.getSqlMapClientTemplate().queryForObject(
						"t_order_infor.getOrderMoneysTotalBySearch", parmMap);
		return totalMap;
	}

	@Override
	public void updateFlag(String id, String value) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("value", value);
		this.getSqlMapClientTemplate().update("t_order_infor.updateFlag",
				paramMap);
	}

}