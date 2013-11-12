package com.tl.resource.business.protoolsinfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.tl.common.enums.ToolsTypes;
import com.tl.common.smartupload.Constant;
import com.tl.common.smartupload.File;
import com.tl.common.smartupload.SmartUpload;
import com.tl.common.smartupload.SmartUploadException;
import com.tl.resource.business.arrival.ArrivalService;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TProductSource;
import com.tl.resource.dao.pojo.TProductToolsInfor;

public class ImportToolsServiceImpl implements ImportToolsService {

  private ProToolsInforService proToolsInforService;

  private ArrivalService arrivalService;

  private String realPath;

  private java.io.File TempFile = null;

  private boolean validatorData(java.io.File file) throws IOException {
    boolean flag = true;
    HSSFWorkbook wb = null;
    try {
      FileInputStream is = new FileInputStream(file);
      wb = new HSSFWorkbook(is);

      int sheetNum = wb.getNumberOfSheets();

      //数据错误时，单元格样式
      CellStyle normalStyle = wb.createCellStyle();
      Font valueFont = wb.createFont();
      normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
      // 垂直居中
      normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      normalStyle.setWrapText(true);
      //valueFont.setFontHeightInPoints((short) 12);
      valueFont.setColor(Font.COLOR_RED);
      normalStyle.setFont(valueFont);

      for (int i = 0; i < sheetNum; i++) {
        HSSFSheet childSheet = wb.getSheetAt(i);
        int rowNum = childSheet.getPhysicalNumberOfRows();

        for (int j = 2; j < rowNum; j++) {
          StringBuffer error = new StringBuffer();
          Double amount = 0d;
          HSSFRow row = childSheet.getRow(j);
          if (row == null)
            continue;
          int cellNum = row.getPhysicalNumberOfCells();

          String brandCode = row.getCell(0) == null ? "" : row.getCell(0).toString();//牌号
          if ("".equals(brandCode))
            continue;
          String productName = row.getCell(1) == null ? "" : row.getCell(1).toString();//名称
          try {
            amount = Double.valueOf(row.getCell(2) == null ? "" : row.getCell(2).toString());//数量
          } catch (NumberFormatException e) {
            if (row.getCell(2) != null) {
              flag = false;
              error.append("数量格式有误 ");
              row.getCell(2).setCellStyle(normalStyle);
            }
          }
          String unit = row.getCell(3) == null ? "" : row.getCell(3).toString();
          String productSortCode = row.getCell(4) == null ? "" : row.getCell(4).toString();//组别
          String productBrand = row.getCell(5) == null ? "" : row.getCell(5).toString();//品牌
          String productSource = row.getCell(6) == null ? "" : row.getCell(6).toString();//来源
          HSSFCell temp = row.getCell(9);
          String toolsTypeName = (temp == null ? "" : temp.toString());//产品类别

          TreeDto treeDto = new TreeDto();
          treeDto.setBrandCode(brandCode);
          treeDto.setProductName(productName);
          treeDto.setProductBrand(productBrand);
          treeDto.setProductSource(productSource);
          treeDto.setProductSortCode(productSortCode);
          treeDto.setProductUnit(unit);

          List<TreeDto> dto = proToolsInforService.getTreeDto(treeDto);
          List<TProductSort> sortList = proToolsInforService.getProSort4Import(treeDto);
          List<TProductBrand> brandList = proToolsInforService.getBrand4import(treeDto);
          List<TProductSource> sourceList = proToolsInforService.getProSourceByName(productSource);

          int errCount = 0;

          if ("".equals(brandCode)) {
            errCount++;
            flag = false;
            error.append("牌号不能为空 ");
          }

          if ("".equals(productName)) {
            errCount++;
            flag = false;
            error.append("名称不能为空 ");
          }

          if ("".equals(unit)) {
            errCount++;
            flag = false;
            error.append("计量单位不能为空 ");
          }

          if ("".equals(productBrand)) {
            errCount++;
            flag = false;
            error.append("品牌不能为空 ");
          }

          if ("".equals(productSortCode)) {
            errCount++;
            flag = false;
            error.append("组别不能为空 ");
          }

          if ("".equals(productSource)) {
            errCount++;
            flag = false;
            error.append("来源不能为空 ");
          }

          if (errCount == 6) {
            continue;
          }

          //                    if(!"采购".equals(productSource)) {
          //                    	flag = false;
          //                    	row.getCell(6).setCellStyle(normalStyle);
          //                    	error.append("来源应为采购 ");
          //                    }

          if (sortList == null || sortList.size() == 0) {
            flag = false;
            row.getCell(4).setCellStyle(normalStyle);
            error.append("组别不存在 ");
          }

          if (brandList == null || brandList.size() == 0) {
            flag = false;
            row.getCell(5).setCellStyle(normalStyle);
            error.append("品牌不存在 ");
          }

          if (sourceList == null || sourceList.size() == 0) {
            flag = false;
            if (row.getCell(6) != null) {
              row.getCell(6).setCellStyle(normalStyle);
              error.append("来源不存在 ");
            }
          }

          if (dto != null && dto.size() > 0) {
            flag = false;
            error.append("此工具信息已经存在 ");
          }
          if (toolsTypeName != null && toolsTypeName.length() > 0 && ToolsTypes.label2Id(toolsTypeName) == null) {
            flag = false;
            error.append("产品类别[" + toolsTypeName + "]不存在");
          }
          HSSFCell cell = row.getCell(11);
          if (cell != null) {
            cell.setCellValue(error.toString());

          } else {
            row.createCell(11).setCellValue(error.toString());

          }
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (!flag) {
      String filePath = new StringBuffer(this.getRealPath()).append(file.getName()).toString();
      FileOutputStream fileOutd = new FileOutputStream(filePath);
      wb.write(fileOutd);
      fileOutd.close();
    }
    return flag;
  }

  private List<String> insertDataToDB(java.io.File file) {
    boolean flag = true;
    Integer insertNumber = 0;
    List<String> errorList = new ArrayList<String>();
    try {

      flag = this.validatorData(file);
      if (flag) {
        FileInputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        int sheetNum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetNum; i++) {
          HSSFSheet childSheet = wb.getSheetAt(i);
          int rowNum = childSheet.getPhysicalNumberOfRows();

          for (int j = 2; j < rowNum; j++) {
            Double amount = 0d;
            HSSFRow row = childSheet.getRow(j);
            if (row == null)
              continue;
            //int cellNum=row.getPhysicalNumberOfCells();   
            HSSFCell temp = row.getCell(0);
            //String brandCode = (temp == null ? "" : temp.toString());//牌号
            String s = "";
            if (temp != null && !"".equals(temp.toString())) {
              int cellType = temp.getCellType();

              if (HSSFCell.CELL_TYPE_NUMERIC == cellType) {
                BigDecimal b = null;
                if (this.isInt(temp.toString())) {
                  b = new BigDecimal(temp.getNumericCellValue());
                } else {
                  b = BigDecimal.valueOf(temp.getNumericCellValue());
                }
                s = b.toString();
              } else {
                s = temp.toString();
              }
            } else {
              continue;
            }
            String brandCode = (temp == null ? "" : s);//牌号

            if ("".equals(brandCode))
              continue;
            temp = row.getCell(1);
            String productName = (temp == null ? "" : temp.toString());//名称
            try {
              temp = row.getCell(2);
              amount = Double.valueOf((temp == null ? "0" : temp.toString()));//数量
            } catch (NumberFormatException e) {
              e.printStackTrace();
            }
            temp = row.getCell(3);
            String productUnit = temp == null ? "" : temp.toString();//计量单位
            temp = row.getCell(4);
            String productSortCode = (temp == null ? "" : temp.toString());
            ;//组别
            temp = row.getCell(5);
            String productBrand = (temp == null ? "" : temp.toString());
            ;//品牌
            temp = row.getCell(6);
            String productSource = (temp == null ? "" : temp.toString());
            ;//来源
            temp = row.getCell(7);
            String memo = (temp == null ? "" : temp.toString());
            ;//备注
            temp = row.getCell(8);
            String reserveCode = (temp == null ? "" : temp.toString());//库存代码
            temp = row.getCell(9);
            String toolsTypeName = (temp == null ? "" : temp.toString());//产品类别

            TreeDto treeDto = new TreeDto();
            treeDto.setBrandCode(brandCode);
            treeDto.setProductName(productName);
            treeDto.setProductBrand(productBrand);
            treeDto.setProductSource(productSource);
            treeDto.setProductSortCode(productSortCode);

            //组别
            List<TProductSort> sortList = proToolsInforService.getProSort4Import(treeDto);
            String sortId = sortList.get(0).getId();

            List<TreeDto> dto = proToolsInforService.getTreeDto(treeDto);

            String id = proToolsInforService.getId("");
            TProductToolsInfor proToolsInfo = new TProductToolsInfor();
            proToolsInfo.setId(id);
            proToolsInfo.setParentId(TProductToolsInfor.ROOT_PRARENT_ID);
            proToolsInfo.setBrandCode(brandCode);
            proToolsInfo.setProductName(productName);
            proToolsInfo.setProductUnit(productUnit);
            proToolsInfo.setProductBrand(productBrand);
            proToolsInfo.setProductSource(productSource);
            proToolsInfo.setProductSortId(sortId);
            proToolsInfo.setMemo(memo);
            proToolsInfo.setProductSortCode(productSortCode);
            proToolsInfo.setToolsTypeName(toolsTypeName);
            proToolsInfo.setToolsTypeId(String.valueOf(ToolsTypes.label2Id(toolsTypeName)));
            if ("采购".equals(productSource.trim())) {
              proToolsInfo.setLeaf(1);
            } else {
              proToolsInfo.setLeaf(0);//如果导入自制，则先让其保持有下级状态，以备在系统中添加下级
            }
            proToolsInfo.setStockPriceDate(new Date());
            proToolsInfo.setProductCode(new StringBuffer(productSortCode).append("-").append(id).toString());

            if (dto != null && dto.size() == 0) {
              proToolsInforService.insertProTools(proToolsInfo);
              insertNumber++;
            }
          }
        }
        errorList.add(insertNumber.toString());
      } else {
        errorList.add(Constant.TEMP_DIR + "/" + file.getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return errorList;
  }

  @Override
  public String importTools(SmartUpload su) {
    File suFile = null;
    List<String> list = null;
    int fileCount = 0;
    try {
      String fileExt = "";
      int fileSize = 0;
      String AllowedExtensions = Constant.ALLOWEDEXTENSIONS;//允许上传的文件类型
      double maxFileSize = Constant.MAXFILESIZE;//单文件最大大小，单位KB
      //校验文件类型和大小
      for (int i = 0; i < su.getFiles().getCount(); i++) {
        suFile = su.getFiles().getFile(i);
        if (suFile.isMissing())
          continue;
        //校验文件大小
        fileSize = suFile.getSize() / 1024;//字节转换成KB
        if (fileSize == 0)
          fileSize = 1;
        if (maxFileSize < fileSize)
          throw new Exception("单个上传文件的容量不能超过[" + maxFileSize + "KB]");

        //校验文件类型
        if (suFile.getFileExt() == null || "".equals(suFile.getFileExt())) {
          fileExt = ",,";
        } else {
          fileExt = "," + suFile.getFileExt().toLowerCase() + ",";
        }
        if (!"".equals(AllowedExtensions) && AllowedExtensions.indexOf(fileExt) == -1) {
          throw new Exception("您上传的文件[" + suFile.getFileName() + "]的类型为系统禁止上传的文件类型，不能上传！");
        }
        fileCount++;
      }
      if (fileCount == 0)
        throw new Exception("请选择上传的文件");

      for (int i = 0; i < su.getFiles().getCount(); i++) {
        suFile = su.getFiles().getFile(i);
        String fileName = suFile.getFileName();
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        java.io.File file = suFile.saveTempFile("tools", new StringBuffer(".").append(suFile.getFileExt()).toString(), this.getTempFile());
        list = this.insertDataToDB(file);
        if (list != null && list.size() == 0)
          file.delete();

      }
      if (list != null && list.size() > 0) {
        if (isNumeric(list.get(0)))
          return list.get(0);
        else
          return JSONArray.fromObject(list).toString();
      } else {
        //成功返回null
        return null;
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    } finally {
      su = null;
    }
    return null;
  }

  private boolean isNumeric(String str) {
    for (int i = str.length(); --i >= 0;) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String importTools(HttpServletRequest request, HttpServletResponse response) {
    SmartUpload su = null;
    try {
      String folderName = request.getSession().getServletContext().getRealPath(Constant.TEMP_DIR);
      java.io.File file = new java.io.File(folderName);
      if (!file.exists()) {
        file.mkdir();
      }
      String realPath = folderName + java.io.File.separator;
      this.setTempFile(file);
      this.setRealPath(realPath);
      su = null;
      HttpSession session = request.getSession();
      ServletContext application = session.getServletContext();

      su = new SmartUpload();
      su.initialize(application, session, request, response, null);
      su.upload();
    } catch (ServletException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SmartUploadException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return this.importTools(su);
  }

  public ProToolsInforService getProToolsInforService() {
    return proToolsInforService;
  }

  public void setProToolsInforService(ProToolsInforService proToolsInforService) {
    this.proToolsInforService = proToolsInforService;
  }

  public ArrivalService getArrivalService() {
    return arrivalService;
  }

  public void setArrivalService(ArrivalService arrivalService) {
    this.arrivalService = arrivalService;
  }

  public String getRealPath() {
    return realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  public java.io.File getTempFile() {
    return TempFile;
  }

  public void setTempFile(java.io.File tempFile) {
    TempFile = tempFile;
  }

  @Override
  public void exportTools(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    List<TreeDto> list = proToolsInforService.getTools4Export(request, response);
    response.setHeader("Content-Disposition", "attachment; filename=tools-" + list.size() + ".xls");
    OutputStream os = response.getOutputStream();

    String realPath = request.getSession().getServletContext().getRealPath("");
    this.setRealPath(realPath);

    HSSFWorkbook wb = this.createWorkBook(Constant.TOOLS_LIST_FILE);

    int pageSize = 60000;//一个Excel sheet 显示的工具条数
    int count = list.size() / pageSize + 1;
    int startIndex = 0;
    for (int i = 0; i < count; i++) {
      int toIndex = (i + 1) * pageSize;
      if (i == count - 1) {
        toIndex = list.size();
      }

      List<TreeDto> list2 = list.subList(startIndex, toIndex);
      HSSFSheet childSheet = null;
      if (i < wb.getNumberOfSheets())
        childSheet = wb.getSheetAt(i);
      else {
        childSheet = wb.createSheet("sheet" + (i + 1));
        insertTitle(wb, childSheet);
      }
      this.insertRow(list2, wb, childSheet, 0);
      startIndex += pageSize;
    }

    wb.write(os);
    os.flush();
    os.close();
  }

  private HSSFWorkbook createWorkBook(String fileName) {
    HSSFWorkbook wb = null;
    FileInputStream is;
    try {
      java.io.File file = new java.io.File(this.getRealPath() + fileName);
      is = new FileInputStream(file);
      wb = new HSSFWorkbook(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return wb;
  }

  private void insertTitle(HSSFWorkbook wb, HSSFSheet sheet) {
    HSSFRow row = sheet.createRow(0);
    setCellValue(row, 0, "牌号");
    setCellValue(row, 1, "历史牌号");
    setCellValue(row, 2, "货品编号");
    setCellValue(row, 3, "名称");
    setCellValue(row, 4, "计量单位");
    //setCellValue(row, 5, "组别");
    setCellValue(row, 5, "品牌");
    // setCellValue(row, 7, "来源");
    setCellValue(row, 6, "创建日期");
    setCellValue(row, 7, "备注");
  }

  public void insertRow(List<TreeDto> list, HSSFWorkbook wb, HSSFSheet sheet, int startRow) {
    int rows = list.size();
    HSSFFont font = wb.createFont();
    font.setFontHeightInPoints((short) 10);
    for (int i = 0; i < rows; i++) {
      HSSFRow targetRow = null;
      targetRow = sheet.createRow(++startRow);
      TreeDto dto = list.get(i);
      this.createTableRow(dto, targetRow);
    }

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

  private HSSFCell setCellValue(HSSFRow row, int cellIndex, String Value, CellStyle normalStyle) {
    HSSFCell cell1 = row.createCell(cellIndex);
    cell1.setCellValue(Value);
    cell1.setCellStyle(normalStyle);
    return cell1;
  }

  /**  
   * 创建行  
   * @param cells  
   * @param rowIndex  
   */
  private void createTableRow(TreeDto treeDto, HSSFRow row) {
    setCellValue(row, 0, treeDto.getBrandCode());
    setCellValue(row, 1, treeDto.getBrandCodeHistory());
    setCellValue(row, 2, treeDto.getProductCode());
    setCellValue(row, 3, treeDto.getProductName());
    setCellValue(row, 4, treeDto.getProductUnit());
    //setCellValue(row, 5, treeDto.getProductSortCode());
    setCellValue(row, 5, treeDto.getProductBrand());
    //setCellValue(row, 7, treeDto.getProductSource());
    setCellValue(row, 6, treeDto.getCreateDateStr());
    setCellValue(row, 7, treeDto.getMemo());
  }

  private boolean isInt(String str) {
    Pattern pattern = Pattern.compile(".0+$");
    Matcher matcher = pattern.matcher(str);

    if (matcher.find()) {
      return true;
    } else {
      return false;
    }
  }
}
