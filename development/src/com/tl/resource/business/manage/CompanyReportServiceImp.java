package com.tl.resource.business.manage;

import java.util.Map;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.CompanyReportsDAO;
import com.tl.resource.dao.pojo.CompanyReports;
import com.tl.resource.dao.pojo.CompanyReportsExample;

public class CompanyReportServiceImp implements CompanyReportService {
  private CompanyReportsDAO companyReportsDAO;

  @Override
  public void addCompanyReport(CompanyReports cr) {
    // TODO Auto-generated method stub
    cr.setId(GenerateSerial.getUUID());
    companyReportsDAO.insert(cr);
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub
    companyReportsDAO.deleteByPrimaryKey(id);
  }

  @Override
  public PaginationSupport findReports4Page(Map<String, String> param, Integer startIndex, Integer pageSize) {
    CompanyReportsExample example = new CompanyReportsExample();

    PaginationSupport pageInfor = new PaginationSupport(companyReportsDAO.selectByExampleWithBLOBs(example), companyReportsDAO
      .countByExample(example), pageSize, startIndex);
    return pageInfor;

  }

  @Override
  public void updateCompanyReport(CompanyReports cr) {
    // TODO Auto-generated method stub
    companyReportsDAO.updateByPrimaryKeySelective(cr);
  }

  public CompanyReportsDAO getCompanyReportsDAO() {
    return companyReportsDAO;
  }

  public void setCompanyReportsDAO(CompanyReportsDAO companyReportsDAO) {
    this.companyReportsDAO = companyReportsDAO;
  }

  @Override
  public CompanyReports getReportLast() {
    // TODO Auto-generated method stub
    return companyReportsDAO.getReportLast();
  }

}
