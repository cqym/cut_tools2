package com.tl.resource.dao;

import java.util.List;

public interface FrameWorkBaseDao {
  public List selectByParam(String sqlId, Object example);

  public int countByParam(String sqlId, Object example);

  public int deleteByParam(String sqlId, Object example);

  public void insert(String sqlId, Object example);

  public void update(String sqlId, Object example);

  public Object getRecordObject(String sqlId, Object example);
}
