package com.tl.resource.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FrameWorkBaseDaoImp extends SqlMapClientDaoSupport implements FrameWorkBaseDao {
  public FrameWorkBaseDaoImp() {
    super();
  }

  @Override
  public int countByParam(String sqlId, Object example) {
    Integer count = (Integer) getSqlMapClientTemplate().queryForObject(sqlId, example);
    return count.intValue();
  }

  @Override
  public int deleteByParam(String sqlId, Object example) {
    int rows = getSqlMapClientTemplate().delete(sqlId, example);
    return rows;
  }

  @Override
  public Object getRecordObject(String sqlId, Object example) {
    return this.getSqlMapClientTemplate().queryForObject(sqlId, example);
  }

  @Override
  public void insert(String sqlId, Object record) {
    // TODO Auto-generated method stub
    getSqlMapClientTemplate().insert(sqlId, record);
  }

  @Override
  public List selectByParam(String sqlId, Object example) {
    // TODO Auto-generated method stub
    return this.getSqlMapClientTemplate().queryForList(sqlId, example);
  }

  @Override
  public void update(String sqlId, Object record) {
    // TODO Auto-generated method stub
    getSqlMapClientTemplate().update(sqlId, record);
  }

}
