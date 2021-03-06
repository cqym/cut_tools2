package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TAuditHistory;
import com.tl.resource.dao.pojo.TAuditHistoryExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TAuditHistoryDAOImpl extends SqlMapClientDaoSupport implements TAuditHistoryDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public TAuditHistoryDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int countByExample(TAuditHistoryExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("t_audit_history.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int deleteByExample(TAuditHistoryExample example) {
        int rows = getSqlMapClientTemplate().delete("t_audit_history.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int deleteByPrimaryKey(String id) {
        TAuditHistory key = new TAuditHistory();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("t_audit_history.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public void insert(TAuditHistory record) {
        getSqlMapClientTemplate().insert("t_audit_history.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public void insertSelective(TAuditHistory record) {
        getSqlMapClientTemplate().insert("t_audit_history.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public List selectByExample(TAuditHistoryExample example) {
        List list = getSqlMapClientTemplate().queryForList("t_audit_history.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public TAuditHistory selectByPrimaryKey(String id) {
        TAuditHistory key = new TAuditHistory();
        key.setId(id);
        TAuditHistory record = (TAuditHistory) getSqlMapClientTemplate().queryForObject("t_audit_history.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int updateByExampleSelective(TAuditHistory record, TAuditHistoryExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_audit_history.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int updateByExample(TAuditHistory record, TAuditHistoryExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_audit_history.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int updateByPrimaryKeySelective(TAuditHistory record) {
        int rows = getSqlMapClientTemplate().update("t_audit_history.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    public int updateByPrimaryKey(TAuditHistory record) {
        int rows = getSqlMapClientTemplate().update("t_audit_history.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table t_audit_history
     *
     * @ibatorgenerated Tue Oct 20 11:57:32 CST 2009
     */
    private static class UpdateByExampleParms extends TAuditHistoryExample {
        private Object record;

        public UpdateByExampleParms(Object record, TAuditHistoryExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public List getPersonsAuditHistroyRecord(String auditPersonId,
			String auditType, int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,String> params = new HashMap<String,String>();
		params.put("audit_person_id", auditPersonId);
		params.put("audit_type_id", auditType);
		params.put("startIndex", String.valueOf(startIndex));
		params.put("pageSize", String.valueOf(pageSize));
		return getSqlMapClientTemplate().queryForList("t_audit_history.getPersonsAuditHistroyRecord", params);
	}
}