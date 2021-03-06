package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TAccountsInforExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TAccountsInforDAOImpl extends SqlMapClientDaoSupport implements TAccountsInforDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TAccountsInforDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int countByExample(TAccountsInforExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_accounts_infor.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByExample(TAccountsInforExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"t_accounts_infor.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int deleteByPrimaryKey(String id) {
		TAccountsInfor key = new TAccountsInfor();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"t_accounts_infor.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insert(TAccountsInfor record) {
		getSqlMapClientTemplate().insert(
				"t_accounts_infor.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public void insertSelective(TAccountsInfor record) {
		getSqlMapClientTemplate().insert(
				"t_accounts_infor.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public List selectByExample(TAccountsInforExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"t_accounts_infor.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public TAccountsInfor selectByPrimaryKey(String id) {
		TAccountsInfor key = new TAccountsInfor();
		key.setId(id);
		TAccountsInfor record = (TAccountsInfor) getSqlMapClientTemplate()
				.queryForObject(
						"t_accounts_infor.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExampleSelective(TAccountsInfor record,
			TAccountsInforExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_accounts_infor.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByExample(TAccountsInfor record,
			TAccountsInforExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"t_accounts_infor.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKeySelective(TAccountsInfor record) {
		int rows = getSqlMapClientTemplate().update(
				"t_accounts_infor.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	public int updateByPrimaryKey(TAccountsInfor record) {
		int rows = getSqlMapClientTemplate().update(
				"t_accounts_infor.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table t_accounts_infor
	 * @ibatorgenerated  Wed Oct 14 14:13:27 CST 2009
	 */
	private static class UpdateByExampleParms extends TAccountsInforExample {
		private Object record;

		public UpdateByExampleParms(Object record, TAccountsInforExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

	@Override
	public List<TAccountsInfor> getAccountsInfoListByByReserveId(
			Map<String, Object> parmMap) {
		List list = getSqlMapClientTemplate().queryForList(
				"t_accounts_infor.getAccountsInfoListByByReserveId", parmMap);
		return list;
	}

	@Override
	public int updateMatAmountByInvoiceId(TAccountsInfor accountRecordt) {
		// TODO Auto-generated method stub
		int rows = getSqlMapClientTemplate().update(
				"t_accounts_infor.updateMatAmountByInvoiceId", accountRecordt);
		return rows;
	}

	@Override
	public BigDecimal selectMatOutAmountByProductCode(String productCode) {
		BigDecimal rt = (BigDecimal) getSqlMapClientTemplate().queryForObject("t_accounts_infor.selectMatOutAmountByProductCode", productCode);
		return rt;
	}
}