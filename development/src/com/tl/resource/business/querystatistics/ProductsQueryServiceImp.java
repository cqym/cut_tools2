package com.tl.resource.business.querystatistics;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.FrameWorkBaseDao;

public class ProductsQueryServiceImp implements ProductsQueryService {
  private FrameWorkBaseDao frameWorkBaseDao;

  @Override
  public PaginationSupport getList(Map<String, Object> mparams, Integer startIndex, Integer pageSize) {
    mparams.put("startIndex", startIndex);
    mparams.put("pageSize", pageSize);
    int count = frameWorkBaseDao.countByParam("query_statistics.ProductsStatisticsCount", mparams);
    List list = frameWorkBaseDao.selectByParam("query_statistics.ProductsStatisticsList", mparams);
    PaginationSupport pageInfor = new PaginationSupport(list, count, pageSize, startIndex);
    return pageInfor;
  }

  public FrameWorkBaseDao getFrameWorkBaseDao() {
    return frameWorkBaseDao;
  }

  public void setFrameWorkBaseDao(FrameWorkBaseDao frameWorkBaseDao) {
    this.frameWorkBaseDao = frameWorkBaseDao;
  }

  @Override
  public PaginationSupport getSaleBillList(Map<String, Object> mparams, Integer startIndex, Integer pageSize) {
    mparams.put("startIndex", startIndex);
    mparams.put("pageSize", pageSize);
    int count = frameWorkBaseDao.countByParam("query_statistics.ProductsSaleBillStatisticsCount", mparams);
    List list = frameWorkBaseDao.selectByParam("query_statistics.ProductsSaleBillStatisticsList", mparams);
    PaginationSupport pageInfor = new PaginationSupport(list, count, pageSize, startIndex);
    return pageInfor;
  }

}
