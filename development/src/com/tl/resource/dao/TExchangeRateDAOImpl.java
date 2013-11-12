package com.tl.resource.dao;

import com.tl.resource.business.dto.CurrencyDto;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TExchangeRateExample;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TExchangeRateDAOImpl extends SqlMapClientDaoSupport implements TExchangeRateDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TExchangeRateDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int countByExample(TExchangeRateExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_exchange_rate.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByExample(TExchangeRateExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"t_exchange_rate.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByPrimaryKey(String id) {
		TExchangeRate key = new TExchangeRate();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"t_exchange_rate.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insert(TExchangeRate record) {
		getSqlMapClientTemplate().insert(
				"t_exchange_rate.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insertSelective(TExchangeRate record) {
		getSqlMapClientTemplate().insert(
				"t_exchange_rate.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public List selectByExample(TExchangeRateExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"t_exchange_rate.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TExchangeRate selectByPrimaryKey(String id) {
		TExchangeRate key = new TExchangeRate();
		key.setId(id);
		TExchangeRate record = (TExchangeRate) getSqlMapClientTemplate()
				.queryForObject(
						"t_exchange_rate.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExampleSelective(TExchangeRate record,
			TExchangeRateExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_exchange_rate.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExample(TExchangeRate record,
			TExchangeRateExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_exchange_rate.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKeySelective(TExchangeRate record) {
		int rows = getSqlMapClientTemplate().update(
				"t_exchange_rate.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKey(TExchangeRate record) {
		int rows = getSqlMapClientTemplate().update(
				"t_exchange_rate.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table t_exchange_rate
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	private static class UpdateByExampleParms extends TExchangeRateExample {
		private Object record;

		public UpdateByExampleParms(Object record, TExchangeRateExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public List<TExchangeRate> getCurrencyName() {
		return this.getSqlMapClientTemplate().queryForList("t_exchange_rate.getCurrencyName");
	}

	@Override
	public List<TExchangeRate> getExchangeByPage(Map<String, Object> parmMap) {
		return this.getSqlMapClientTemplate().queryForList("t_exchange_rate.getExchangeByPage",parmMap);
	}

	@Override
	public int getExchangeTotal() {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_exchange_rate.getExchangeTotal");
		return count.intValue();
	}

	@Override
	public void setBenchmarkExchangeById(String exchangeId) {
		getSqlMapClientTemplate().update(
				"t_exchange_rate.setBenchmarkExchangeById",
				exchangeId);
		
	}

	@Override
	public void updateOldBenchmark() {
		getSqlMapClientTemplate().update(
				"t_exchange_rate.updateOldBenchmark");
		
	}

	@Override
	public List<CurrencyDto> getCurrency() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("t_exchange_rate.getCurrency");
	}

	@Override
	public TExchangeRate getCurrencyByName(String currencyName) {
		return (TExchangeRate)this.getSqlMapClientTemplate().queryForObject("t_exchange_rate.getCurrencyByName", currencyName);
	}
	
}