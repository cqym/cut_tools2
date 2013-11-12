package com.tl.resource.dao;

import com.tl.resource.business.dto.DeliveryProductDetailDto;
import com.tl.resource.dao.pojo.TDeliveryDetail;
import com.tl.resource.dao.pojo.TDeliveryDetailExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TDeliveryDetailDAOImpl extends SqlMapClientDaoSupport implements TDeliveryDetailDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TDeliveryDetailDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int countByExample(TDeliveryDetailExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_delivery_detail.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByExample(TDeliveryDetailExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"t_delivery_detail.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByPrimaryKey(String id) {
		TDeliveryDetail key = new TDeliveryDetail();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"t_delivery_detail.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insert(TDeliveryDetail record) {
		getSqlMapClientTemplate().insert(
				"t_delivery_detail.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insertSelective(TDeliveryDetail record) {
		getSqlMapClientTemplate().insert(
				"t_delivery_detail.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public List selectByExample(TDeliveryDetailExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"t_delivery_detail.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TDeliveryDetail selectByPrimaryKey(String id) {
		TDeliveryDetail key = new TDeliveryDetail();
		key.setId(id);
		TDeliveryDetail record = (TDeliveryDetail) getSqlMapClientTemplate()
				.queryForObject(
						"t_delivery_detail.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExampleSelective(TDeliveryDetail record,
			TDeliveryDetailExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_delivery_detail.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExample(TDeliveryDetail record,
			TDeliveryDetailExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_delivery_detail.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKeySelective(TDeliveryDetail record) {
		int rows = getSqlMapClientTemplate()
				.update(
						"t_delivery_detail.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKey(TDeliveryDetail record) {
		int rows = getSqlMapClientTemplate().update(
				"t_delivery_detail.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table t_delivery_detail
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	private static class UpdateByExampleParms extends TDeliveryDetailExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				TDeliveryDetailExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public List<DeliveryProductDetailDto> getDeliveryDetail(String id,
			String id2) {
		// TODO Auto-generated method stub
		return null;
	}
}