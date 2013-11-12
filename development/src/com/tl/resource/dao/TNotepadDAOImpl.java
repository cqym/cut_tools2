package com.tl.resource.dao;

import com.tl.resource.dao.pojo.TNotepad;
import com.tl.resource.dao.pojo.TNotepadExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TNotepadDAOImpl extends SqlMapClientDaoSupport implements TNotepadDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public TNotepadDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int countByExample(TNotepadExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("t_notepad.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int deleteByExample(TNotepadExample example) {
        int rows = getSqlMapClientTemplate().delete("t_notepad.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int deleteByPrimaryKey(String id) {
        TNotepad key = new TNotepad();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("t_notepad.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public void insert(TNotepad record) {
        getSqlMapClientTemplate().insert("t_notepad.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public void insertSelective(TNotepad record) {
        getSqlMapClientTemplate().insert("t_notepad.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public List selectByExample(TNotepadExample example) {
        List list = getSqlMapClientTemplate().queryForList("t_notepad.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public TNotepad selectByPrimaryKey(String id) {
        TNotepad key = new TNotepad();
        key.setId(id);
        TNotepad record = (TNotepad) getSqlMapClientTemplate().queryForObject("t_notepad.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int updateByExampleSelective(TNotepad record, TNotepadExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_notepad.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int updateByExample(TNotepad record, TNotepadExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("t_notepad.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int updateByPrimaryKeySelective(TNotepad record) {
        int rows = getSqlMapClientTemplate().update("t_notepad.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    public int updateByPrimaryKey(TNotepad record) {
        int rows = getSqlMapClientTemplate().update("t_notepad.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table t_notepad
     *
     * @ibatorgenerated Mon May 24 16:08:51 CST 2010
     */
    private static class UpdateByExampleParms extends TNotepadExample {
        private Object record;

        public UpdateByExampleParms(Object record, TNotepadExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}