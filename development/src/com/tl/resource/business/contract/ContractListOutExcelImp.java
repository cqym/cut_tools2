package com.tl.resource.business.contract;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.util.PaginationSupport;
import com.tl.common.util.WebUtils;
import com.tl.resource.dao.TContractInforDAO;

public class ContractListOutExcelImp implements ContractListOutExcel {
  private TContractInforDAO contractInforDAO;

  private static final int sheetMaxRowCount = 60000;

  private static final String[] headers = { "合同编号", "客户名称", "合同状态", "我方负责人", "最终金额", "备注", "合同附件", "编制人", "编制时间" };

  @Override
  public void exportExcel(Map<String, Object> mparams, HttpServletResponse response, HttpServletRequest request) throws IOException {
    PaginationSupport page = contractInforDAO.findContractViewPanelInfors(mparams, 0, Integer.MAX_VALUE);
    List list = page.getItems();
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet();
    HSSFRow r = sheet.createRow(0);
    for (int i = 0; i < headers.length; i++) {
      HSSFCell cell = r.createCell(i);
      cell.setCellValue(headers[i]);
    }
    int rowCount = 1;
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      //ContractViewPanelDto
      com.tl.resource.business.dto.ContractInforDto c = (com.tl.resource.business.dto.ContractInforDto) iterator.next();
      if (rowCount > sheetMaxRowCount) {
        rowCount = 1;
        sheet = workbook.createSheet();
      }
      HSSFRow row = sheet.createRow(rowCount);
      HSSFCell cell = row.createCell(0);
      cell.setCellValue(c.getContractCode());

      cell = row.createCell(1);
      cell.setCellValue(c.getCustomerName());

      cell = row.createCell(2);
      cell.setCellValue(getStatusText(c.getStatus()));

      cell = row.createCell(3);
      cell.setCellValue(c.getOwnContactPerson());

      cell = row.createCell(4);
      cell.setCellValue(c.getFinalMoney().doubleValue());

      cell = row.createCell(5);
      cell.setCellValue(c.getMemo());

      cell = row.createCell(6);
      cell.setCellValue(c.getFileCount() > 0 ? "已传附件" : "未传附件");

      cell = row.createCell(7);
      cell.setCellValue(c.getUserName());

      cell = row.createCell(8);
      cell.setCellValue(c.getEditDateString());

      rowCount++;
    }

    response.setContentType("application/vnd.ms-excel");
    WebUtils.setDownloadableHeader(response, "contractList.xls");
    workbook.write(response.getOutputStream());
    response.getOutputStream().flush();
  }

  private String getStatusText(Integer status) {
    String rt = "";
    switch (status) {
    case 0:
      rt = "编制";
      break;
    case 1:
      rt = "待审批";
      break;
    case 2:
      rt = "审批通过";
      break;
    case 3:
      rt = "审批退回";
      break;
    case 4:
      rt = "执行";
      break;
    case 5:
      rt = "终结";
      break;
    case -1:
      rt = "作废";
      break;
    default:
      break;
    }
    return rt;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

}
