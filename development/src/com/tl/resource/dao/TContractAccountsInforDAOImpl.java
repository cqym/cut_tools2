package com.tl.resource.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tl.resource.dao.pojo.TContractAccountsInfor;
import com.tl.resource.dao.pojo.TContractAccountsInforExample;

public class TContractAccountsInforDAOImpl extends SqlMapClientDaoSupport implements TContractAccountsInforDAO {

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public TContractAccountsInforDAOImpl() {
    super();
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int countByExample(TContractAccountsInforExample example) {
    Integer count = (Integer) getSqlMapClientTemplate().queryForObject("t_contract_accounts_infor.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int deleteByExample(TContractAccountsInforExample example) {
    int rows = getSqlMapClientTemplate().delete("t_contract_accounts_infor.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int deleteByPrimaryKey(String id) {
    TContractAccountsInfor key = new TContractAccountsInfor();
    key.setId(id);
    int rows = getSqlMapClientTemplate().delete("t_contract_accounts_infor.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public void insert(TContractAccountsInfor record) {
    getSqlMapClientTemplate().insert("t_contract_accounts_infor.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public void insertSelective(TContractAccountsInfor record) {
    getSqlMapClientTemplate().insert("t_contract_accounts_infor.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public List selectByExample(TContractAccountsInforExample example) {
    List list = getSqlMapClientTemplate().queryForList("t_contract_accounts_infor.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public TContractAccountsInfor selectByPrimaryKey(String id) {
    TContractAccountsInfor key = new TContractAccountsInfor();
    key.setId(id);
    TContractAccountsInfor record = (TContractAccountsInfor) getSqlMapClientTemplate().queryForObject(
      "t_contract_accounts_infor.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int updateByExampleSelective(TContractAccountsInfor record, TContractAccountsInforExample example) {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = getSqlMapClientTemplate().update("t_contract_accounts_infor.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int updateByExample(TContractAccountsInfor record, TContractAccountsInforExample example) {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = getSqlMapClientTemplate().update("t_contract_accounts_infor.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int updateByPrimaryKeySelective(TContractAccountsInfor record) {
    int rows = getSqlMapClientTemplate().update("t_contract_accounts_infor.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator.
   * This method corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  public int updateByPrimaryKey(TContractAccountsInfor record) {
    int rows = getSqlMapClientTemplate().update("t_contract_accounts_infor.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator.
   * This class corresponds to the database table t_contract_accounts_infor
   *
   * @ibatorgenerated Thu Nov 26 20:05:56 CST 2009
   */
  private static class UpdateByExampleParms extends TContractAccountsInforExample {
    private Object record;

    public UpdateByExampleParms(Object record, TContractAccountsInforExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  @Override
  public List findContractList(Map<String, Object> str) {
    // TODO Auto-generated method stub
    return getSqlMapClientTemplate().queryForList("t_contract_infor.findContractList", str);
  }

  @Override
  public int findContractListCount(Map<String, Object> str) {
    // TODO Auto-generated method stub
    Integer in = (Integer) getSqlMapClientTemplate().queryForObject("t_contract_infor.findContractListCount", str);
    return in.intValue();
  }

  @Override
  public BigDecimal getContractNeedMoney(String contractId) {
    BigDecimal m = (BigDecimal) getSqlMapClientTemplate().queryForObject("t_contract_accounts_infor.getContractNeedMoney", contractId);
    return m;
  }

  @Override
  public BigDecimal getSumAccountByCustomerCode(String customerCode) {
    // TODO Auto-generated method stub
    BigDecimal m = (BigDecimal) getSqlMapClientTemplate().queryForObject("t_contract_accounts_infor.getSumAccountByCustomerCode", customerCode);
    return m;
  }
}