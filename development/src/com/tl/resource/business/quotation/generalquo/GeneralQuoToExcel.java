package com.tl.resource.business.quotation.generalquo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.tl.common.context.SystemInstance;
import com.tl.common.smartupload.Constant;
import com.tl.common.util.StringHelper;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TExchangeRate;

public class GeneralQuoToExcel {
  public static final String TempletePath = "\\upload\\templete\\generalQuo.xls";

  private TCompanyInforDAO tcompanyInforDAO;

  private TAccessoriesDAO accessoriesDao;

  private TExchangeRateDAO exchangeRateDAO;

  private String logoPath;

  private String realPath;

  public TCompanyInforDAO getTcompanyInforDAO() {
    return tcompanyInforDAO;
  }

  public void setTcompanyInforDAO(TCompanyInforDAO tcompanyInforDAO) {
    this.tcompanyInforDAO = tcompanyInforDAO;
  }

  public TAccessoriesDAO getAccessoriesDao() {
    return accessoriesDao;
  }

  public void setAccessoriesDao(TAccessoriesDAO accessoriesDao) {
    this.accessoriesDao = accessoriesDao;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public String getRealPath() {
    return realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  private HSSFWorkbook createWorkBook() {
    HSSFWorkbook wb = null;
    FileInputStream is;
    try {
      File file = new File(this.getLogoPath() + Constant.GENERALQUO_FILE);
      is = new FileInputStream(file);
      wb = new HSSFWorkbook(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return wb;
  }

  private AccessoriesDto getAccDto(String busId, int busType) {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("busId", busId);
    paramMap.put("busType", busType);
    List<AccessoriesDto> list = accessoriesDao.getAccessoriesByBusId(paramMap);
    if (list != null && list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }

  private void insertImage(HSSFWorkbook sysEventWorkBook, HSSFSheet demoSheet, TCompanyInfor comInfo, int footRow) {
    BufferedImage bufferImg = null;
    BufferedImage footerImg = null;
    String extName = "";
    String footExtName = "";
    File file = null;
    File footFile = null;

    // 页眉图片logo
    AccessoriesDto headerSlave = this.getAccDto(comInfo.getId(), 3);
    if (headerSlave != null) {
      String headerPath = headerSlave.getPath();
      headerPath = headerPath.replaceAll("\\/", "//");
      extName = StringHelper.getExtName(headerPath, '.');
      String headerFilePath = new StringBuffer(logoPath).append(headerPath).toString();

      file = new java.io.File(headerFilePath);
    }
    // 页脚图片logo
    AccessoriesDto footerSlave = this.getAccDto(comInfo.getId(), 4);
    if (footerSlave != null) {
      String footerPath = footerSlave.getPath();
      footerPath = footerPath.replaceAll("\\/", "//");
      footExtName = StringHelper.getExtName(footerPath, '.');
      String footerFilePath = new StringBuffer(logoPath).append(footerPath).toString();

      footFile = new java.io.File(footerFilePath);
    }
    try {
      ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
      ByteArrayOutputStream footerbyteArrayOut = new ByteArrayOutputStream();

      HSSFPatriarch patriarch = demoSheet.createDrawingPatriarch();
      HSSFClientAnchor anchor = new HSSFClientAnchor(0, 30, 131, 36, (short) 0, 0, (short) 11, 1);
      HSSFClientAnchor footAnchor = new HSSFClientAnchor(0, 100, 131, 36, (short) 0, footRow, (short) 11, footRow + 2);

      // 插入页眉图片
      if (file != null && file.canRead()) {
        bufferImg = ImageIO.read(file);
        if ("png".equalsIgnoreCase(extName)) {
          ImageIO.write(bufferImg, "png", byteArrayOut);
          patriarch.createPicture(anchor, sysEventWorkBook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        } else if ("jpg".equalsIgnoreCase(extName)) {
          ImageIO.write(bufferImg, "jpg", byteArrayOut);
          patriarch.createPicture(anchor, sysEventWorkBook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

        }
      }

      // 插入页脚图片
      if (footFile != null && footFile.canRead()) {
        footerImg = ImageIO.read(footFile);
        if ("png".equalsIgnoreCase(footExtName)) {
          ImageIO.write(footerImg, "png", footerbyteArrayOut);
          patriarch.createPicture(footAnchor, sysEventWorkBook.addPicture(footerbyteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
        } else if ("jpg".equalsIgnoreCase(footExtName)) {
          ImageIO.write(footerImg, "jpg", footerbyteArrayOut);
          patriarch.createPicture(footAnchor, sysEventWorkBook.addPicture(footerbyteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void fillDataToCell(HSSFRow row, String value0, String value1) {
    HSSFCell cell = row.getCell(2);
    cell.setCellValue(value0);
    HSSFCell cell2 = row.getCell(8);
    cell2.setCellValue(value1);
  }

  private void fillDataToCell(HSSFRow row, int cell, String value0, int cell2, String value1) {
    HSSFCell cell1 = row.getCell(cell) == null ? row.createCell(cell) : row.getCell(cell);
    cell1.setCellValue(value0);
    HSSFCell cell3 = row.getCell(cell2) == null ? row.createCell(cell2) : row.getCell(cell2);
    cell3.setCellValue(new BigDecimal(value1).doubleValue());
  }

  private void fillDataToCell(HSSFRow row, String value) {
    HSSFCell cell = row.getCell(8) == null ? row.createCell(8) : row.getCell(8);
    cell.setCellValue(value);
  }

  private void fillDataToCell(HSSFRow row, Double value) {
    HSSFCell cell = row.getCell(8);
    if (cell != null) {
      cell.setCellValue(value);
    }
  }

  public void fillDataToQuo(HSSFSheet demoSheet, QuotationDto quoPro) {

    HSSFRow row2 = demoSheet.getRow(2);
    fillDataToCell(row2, quoPro.getQuotationCode(), quoPro.getCustomerName());

    HSSFRow row3 = demoSheet.getRow(3);
    fillDataToCell(row3, quoPro.getQuotationDate(), "");

    HSSFRow row4 = demoSheet.getRow(4);
    fillDataToCell(row4, quoPro.getUserName(), quoPro.getCusContactPerson());

    HSSFRow row5 = demoSheet.getRow(5);
    fillDataToCell(row5, quoPro.getEditorName(), quoPro.getCustomerPhone());

    HSSFRow row6 = demoSheet.getRow(6);
    TExchangeRate expo = exchangeRateDAO.selectByPrimaryKey(quoPro.getCurrency());
    fillDataToCell(row6, expo.getCurrencyName(), quoPro.getCustomerFax());
  }

  private HSSFCell setCellValue(HSSFRow row, int cellIndex, String Value) {
    HSSFCell cell1 = row.createCell(cellIndex);
    cell1.setCellValue(Value);
    return cell1;
  }

  private HSSFCell setCellValue(HSSFRow row, int cellIndex, int Value) {
    HSSFCell cell1 = row.createCell(cellIndex);
    cell1.setCellValue(Value);
    return cell1;
  }

  private HSSFCell setCellValue(HSSFRow row, int cellIndex, Double Value) {
    HSSFCell cell1 = row.createCell(cellIndex);
    cell1.setCellValue(Value);
    return cell1;
  }

  /**
   * 创建行
   * 
   * @param cells
   * @param rowIndex
   */
  private void createTableRow(QuotationDetailDto product, HSSFRow row) {
    setCellValue(row, 0, new Integer(product.getProjectCode()));
    setCellValue(row, 1, product.getSerialNumber());
    setCellValue(row, 2, product.getProductName());
    setCellValue(row, 3, product.getBrandCode());
    setCellValue(row, 4, product.getAmount().doubleValue());
    setCellValue(row, 5, product.getPrice().doubleValue());
    setCellValue(row, 6, product.getRebate().doubleValue());
    setCellValue(row, 7, product.getNetPrice().doubleValue());
    setCellValue(row, 8, product.getMoney().doubleValue());
    setCellValue(row, 9, product.getDeliveryDate());
    setCellValue(row, 10, product.getProductBrand());
    setCellValue(row, 11, product.getMemo());
  }

  private void createTableRow(QuotationDetailDto product, HSSFRow row, boolean hasMemo2, boolean hasMemo3) {
    this.createTableRow(product, row);
    if (hasMemo2) {
      setCellValue(row, 12, product.getWorkshop());
    }
    if (hasMemo3) {
      setCellValue(row, 13, product.getReportCode());
    }
  }

  public void insertRow(List<QuotationDetailDto> quoProList, HSSFWorkbook wb, HSSFSheet sheet, int startRow) {
    int rows = quoProList.size();
    sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows, true, true);
    boolean hasMemo2 = this.hasMemo2(quoProList);
    boolean hasMemo3 = this.hasMemo3(quoProList);
    HSSFFont font = wb.createFont();
    font.setFontHeightInPoints((short) 10);
    for (int i = 0; i < rows; i++) {

      HSSFRow sourceRow = null;
      HSSFRow targetRow = null;
      HSSFCell sourceCell = null;
      HSSFCell targetCell = null;
      short m;

      sourceRow = sheet.getRow(startRow);
      targetRow = sheet.createRow(++startRow);
      // targetRow.setHeightInPoints(sourceRow.getHeightInPoints());

      for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum() - 1; m++) {
        sourceCell = sourceRow.getCell(m);
        targetCell = targetRow.createCell(m);

        // targetCell.setEncoding(sourceCell.getEncoding());
        if (sourceCell != null) {
          HSSFCellStyle sytle = sourceCell.getCellStyle();
          sytle.setFont(font);
          targetCell.setCellStyle(sytle);
          targetCell.setCellStyle(sourceCell.getCellStyle());
          targetCell.setCellType(sourceCell.getCellType());
          targetCell.setCellValue(i);
        }
      }

      QuotationDetailDto quoProduct = quoProList.get(i);
      createTableRow(quoProduct, targetRow, hasMemo2, hasMemo3);
    }

    if (hasMemo2) {
      this.createMemoCell(wb, sheet, 12, "备注1");
      this.createMemoLine(sheet, 7 + rows, 12);
    }
    if (hasMemo3) {
      this.createMemoCell(wb, sheet, 13, "备注2");
      this.createMemoLine(sheet, 7 + rows, 13);
    }
  }

  private boolean hasMemo2(List<QuotationDetailDto> quoProList) {
    boolean flag = false;
    for (QuotationDetailDto dto : quoProList) {
      if (!"".equals(dto.getWorkshop()) && null != dto.getWorkshop()) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  private boolean hasMemo3(List<QuotationDetailDto> quoProList) {
    boolean flag = false;
    for (QuotationDetailDto dto : quoProList) {
      if (!"".equals(dto.getReportCode()) && null != dto.getReportCode()) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  private void createMemoCell(HSSFWorkbook wb, HSSFSheet sheet, int cellIndex, String memo) {
    HSSFRow row7 = sheet.getRow(7);
    HSSFCell cell11 = row7.getCell(11);

    HSSFCellStyle sytle = cell11.getCellStyle();
    HSSFFont font = wb.createFont();
    font.setFontHeightInPoints((short) 10);
    sytle.setFont(font);

    HSSFCell cell = row7.createCell(cellIndex);
    cell.setCellStyle(sytle);
    cell.setCellValue(memo);
  }

  private void createMemoLine(HSSFSheet sheet, int rowIndex, int cellInex) {
    HSSFRow rowLine = sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex);
    HSSFCell cell11 = rowLine.getCell(11);

    HSSFCellStyle sytle = cell11.getCellStyle();

    HSSFCell cell = rowLine.createCell(cellInex);
    cell.setCellStyle(sytle);
  }

  public void createCountInfo(HSSFSheet demoSheet, int rowIndex, QuotationDto quoPro) {
    int step = rowIndex;
    HSSFRow row1 = demoSheet.getRow((short) step);
    fillDataToCell(row1, quoPro.getProductMoney().doubleValue());
    HSSFRow row2 = demoSheet.getRow((short) step + 1);
    fillDataToCell(row2, quoPro.getTaxRate().doubleValue());
    HSSFRow row3 = demoSheet.getRow((short) step + 2);
    fillDataToCell(row3, quoPro.getTaxMoney().doubleValue());
    HSSFRow row4 = demoSheet.getRow((short) step + 3);
    fillDataToCell(row4, quoPro.getTotalMoney().doubleValue());

    int rebateRow = step + 4;
    if (quoPro.getOverallRebate() != null && new BigDecimal(quoPro.getOverallRebate()).compareTo(BigDecimal.ZERO) != 0) {
      HSSFRow row5 = demoSheet.getRow((short) rebateRow);
      fillDataToCell(row5, 7, "整单折扣：", 8, new Double(Double.valueOf(quoPro.getOverallRebate()) / 100).toString());
      rebateRow += 1;
    }
    if (quoPro.getFinalMoney() != null) {
      HSSFRow row6 = demoSheet.getRow((short) rebateRow);
      fillDataToCell(row6, 7, "最终金额：", 8, quoPro.getFinalMoney());
    }
  }

  public void createMemoInfo(HSSFWorkbook sysEventWorkBook, HSSFSheet demoSheet, int rowIndex, QuotationDto quoPro, String deliveryDate) {
    TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoPro.getSellerName());

    int startIndex = rowIndex + 7;

    HSSFRow validDateRow = demoSheet.getRow((short) startIndex);
    validDateRow.setHeight((short) 400);
    if (quoPro.getValidStartDate() != null && quoPro.getValidEndDate() != null)
      setCellValue(validDateRow, 2, quoPro.getValidStartDate() + " ―― " + quoPro.getValidEndDate());

    HSSFRow otherRow = demoSheet.getRow((short) startIndex + 1);
    otherRow.setHeight((short) 400);

    otherRow.setHeight((short) 400);
    setCellValue(otherRow, 2, "1.付款条件：" + quoPro.getPaymentCondition());

    if (comInfo != null) {
      HSSFRow unitRow = demoSheet.getRow((short) startIndex + 2);
      unitRow.setHeight((short) 400);
      setCellValue(unitRow, 2, "单位名称：" + comInfo.getCompanyName());

      HSSFRow bankRow = demoSheet.getRow((short) startIndex + 3);
      bankRow.setHeight((short) 400);
      setCellValue(bankRow, 2, "开户行：" + comInfo.getBank());

      HSSFRow bankCodeRow = demoSheet.getRow((short) startIndex + 4);
      bankCodeRow.setHeight((short) 400);
      setCellValue(bankCodeRow, 2, "账   号：" + comInfo.getAccountNumber());
    }

    HSSFRow deliveryRow = demoSheet.getRow((short) startIndex + 5);
    deliveryRow.setHeight((short) 400);
    if (deliveryDate != null)
      setCellValue(deliveryRow, 2, "2.交货期：" + deliveryDate);

    HSSFRow row = demoSheet.createRow((short) startIndex + 8);
    row.setHeight((short) 400);
    setCellValue(row, 2, "买方签章__________");
    if (comInfo != null)
      insertImage(sysEventWorkBook, demoSheet, comInfo, startIndex + 10);
  }

  public void exportExcel(OutputStream os, List<QuotationDetailDto> quoProList, QuotationDto quoPro) throws IOException {
    String path = this.getLogoPath() + TempletePath;
    FileInputStream fs = new FileInputStream(path);

    TCustomersInforDAO customersInforDAO = (TCustomersInforDAO) SystemInstance.getInstance().getBean("TCustomersInforDAO");

    TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoPro.getSellerName());

    TCustomersInfor cust = new TCustomersInfor();
    TCustomersInforExample example = new TCustomersInforExample();
    example.createCriteria().andCustomerCodeEqualTo(quoPro.getCustomerCode());
    List cusList = customersInforDAO.selectByExample(example);
    if (cusList.size() > 0) {
      cust = (TCustomersInfor) cusList.get(0);
    }

    Map<String, Object> businessData = new HashMap<String, Object>();
    businessData.put("companyInfor", comInfo);
    businessData.put("quoProList", quoProList);
    businessData.put("quoPro", quoPro);
    businessData.put("customer", cust);

    XLSTransformer tf = new XLSTransformer();
    try {
      Workbook workbook = tf.transformXLS(fs, businessData);
      workbook.write(os);
    } catch (ParsePropertyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    os.flush();
    os.close();
  }

  public TExchangeRateDAO getExchangeRateDAO() {
    return exchangeRateDAO;
  }

  public void setExchangeRateDAO(TExchangeRateDAO exchangeRateDAO) {
    this.exchangeRateDAO = exchangeRateDAO;
  }

}
