package com.tl.resource.business.manage;

import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.pojo.CompanyReports;

public interface CompanyReportService {
  public void addCompanyReport(CompanyReports cr);

  public void updateCompanyReport(CompanyReports cr);

  public void deleteById(String id);

  public PaginationSupport findReports4Page(Map<String, String> param, Integer startIndex, Integer pageSize);

  public CompanyReports getReportLast();
}
