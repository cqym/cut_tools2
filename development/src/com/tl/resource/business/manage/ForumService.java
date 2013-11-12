package com.tl.resource.business.manage;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.pojo.TForum;

public interface ForumService {
  void add(TForum forum);

  void deleteById(String id);

  void update(TForum forum);

  public PaginationSupport findForum4Page(Map<String, String> param, Integer startIndex, Integer pageSize);

  TForum getTForumById(String id);

  void endById(String parameter);

  List<TForum> getWaitReturnForumMsg(String userId);

  void followTheme(TForum forum);

  List<TForum> getThemeFlowsByForumId(String id);

}
