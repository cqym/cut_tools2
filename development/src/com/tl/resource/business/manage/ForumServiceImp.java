package com.tl.resource.business.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.TForumDAO;
import com.tl.resource.dao.TForumPersonsDAO;
import com.tl.resource.dao.pojo.TForum;
import com.tl.resource.dao.pojo.TForumExample;
import com.tl.resource.dao.pojo.TForumPersons;
import com.tl.resource.dao.pojo.TForumPersonsExample;
import com.tl.resource.dao.pojo.TForumExample.Criteria;

public class ForumServiceImp implements ForumService {
  private TForumDAO forumDao = null;

  private TForumPersonsDAO forumPersonsDao = null;

  @Override
  public void add(TForum forum) {
    forum.setId(GenerateSerial.getUUID());
    forumDao.insert(forum);

    TForumPersons acceptPerson = new TForumPersons();
    acceptPerson.setForumId(forum.getId());
    acceptPerson.setId(GenerateSerial.getUUID());
    acceptPerson.setPsersonType(TForumPersons.ACCEPT_PERSON_TYPE);
    acceptPerson.setUserId(forum.getAcceptUserId());
    acceptPerson.setUserName(forum.getAcceptUserName());
    acceptPerson.setStatus(TForumPersons.INIT_STATUS);
    forumPersonsDao.insert(acceptPerson);

    TForumPersons copyPerson = new TForumPersons();
    copyPerson.setForumId(forum.getId());
    copyPerson.setId(GenerateSerial.getUUID());
    copyPerson.setPsersonType(TForumPersons.COPY_PERSON_TYPE);
    copyPerson.setUserId(forum.getCopyUserId());
    copyPerson.setUserName(forum.getCopyUserName());
    copyPerson.setStatus(TForumPersons.INIT_STATUS);
    forumPersonsDao.insert(copyPerson);
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub
    forumDao.deleteByPrimaryKey(id);
  }

  @Override
  public PaginationSupport findForum4Page(Map<String, String> param, Integer startIndex, Integer pageSize) {
    TForumExample example = new TForumExample();
    Criteria c = example.createCriteria();
    if (param.get("forumType") != null) {
      c.andForumTypeEqualTo(Integer.valueOf(param.get("forumType")));
    }
    if (param.get("status") != null) {
      c.andStatusEqualTo(Integer.valueOf(param.get("status")));
    }
    if (param.get("title") != null) {
      c.andTitleLike("%" + param.get("title") + "%");
    }
    if (param.get("parentId") != null) {
      c.andParentIdEqualTo(param.get("parentId"));
    }
    if (param.get("receiverUserId") != null) {
      c.andAcceptUserIdEqualTo(param.get("receiverUserId"));
    }
    example.setOrderByClause(" edit_date desc");
    return new PaginationSupport(forumDao.selectByExampleWithBLOBs(example), new Long(forumDao.countByExample(example)).intValue(), pageSize,
      startIndex);

  }

  @Override
  public void update(TForum forum) {
    // TODO Auto-generated method stub
    forumDao.updateByPrimaryKeySelective(forum);
  }

  public TForum getTForumById(String id) {
    // TODO Auto-generated method stub
    return forumDao.selectByPrimaryKey(id);
  }

  @Override
  public void endById(String id) {
    // TODO Auto-generated method stub
    TForum po = forumDao.selectByPrimaryKey(id);
    po.setStatus(TForum.STATUS_END_OP);
    forumDao.updateByPrimaryKeyWithoutBLOBs(po);
  }

  public TForumDAO getForumDao() {
    return forumDao;
  }

  public void setForumDao(TForumDAO forumDao) {
    this.forumDao = forumDao;
  }

  public TForumPersonsDAO getForumPersonsDao() {
    return forumPersonsDao;
  }

  public void setForumPersonsDao(TForumPersonsDAO forumPersonsDao) {
    this.forumPersonsDao = forumPersonsDao;
  }

  @Override
  public List<TForum> getWaitReturnForumMsg(String userId) {
    TForumPersonsExample example = new TForumPersonsExample();
    example.createCriteria().andStatusEqualTo(TForumPersons.INIT_STATUS).andUserIdEqualTo(userId);
    List<TForumPersons> fs = forumPersonsDao.selectByExample(example);

    List<TForum> waitReturnForumList = new ArrayList<TForum>();
    for (TForumPersons fp : fs) {
      waitReturnForumList.add(this.forumDao.selectByPrimaryKey(fp.getForumId()));
    }
    return waitReturnForumList;
  }

  @Override
  public void followTheme(TForum forum) {
    forum.setId(GenerateSerial.getUUID());
    forumDao.insert(forum);

    TForumPersonsExample example = new TForumPersonsExample();
    example.createCriteria().andUserIdEqualTo(forum.getUserId()).andForumIdEqualTo(forum.getParentId());
    List<TForumPersons> list = forumPersonsDao.selectByExample(example);
    for (TForumPersons forumPerson : list) {
      forumPerson.setStatus(TForumPersons.RETURNED_STATUS);
      forumPersonsDao.updateByPrimaryKey(forumPerson);
    }
  }

  @Override
  public List<TForum> getThemeFlowsByForumId(String id) {
    TForumExample example = new TForumExample();
    example.createCriteria().andParentIdEqualTo(id);
    example.setOrderByClause("edit_date");

    return forumDao.selectByExampleWithBLOBs(example);
  }
}
