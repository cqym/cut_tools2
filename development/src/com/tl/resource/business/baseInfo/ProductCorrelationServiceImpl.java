/**
 * 
 */
package com.tl.resource.business.baseInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.tl.common.smartupload.Constant;
import com.tl.common.smartupload.File;
import com.tl.common.smartupload.SmartUpload;
import com.tl.common.smartupload.SmartUploadException;
import com.tl.common.util.GenerateSerial;
import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.CurSalesPriceHistoryDto;
import com.tl.resource.business.dto.ImportSalesPriceDto;
import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.SalesPriceHistoryProDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.TCusSalesPriceHistoryDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TOrderPriceHistoryDAO;
import com.tl.resource.dao.TProductBrandDAO;
import com.tl.resource.dao.TProductSortDAO;
import com.tl.resource.dao.TProductToolsInforDAO;
import com.tl.resource.dao.TSalesPriceHistoryDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TSalesPriceHistory;
import com.tl.resource.dao.pojo.TSalesPriceHistoryExample;
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * @author xtaia
 * 
 */
public class ProductCorrelationServiceImpl implements ProductCorrelationService {

  private String realPath;

  public String getRealPath() {
    return realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  private TProductSortDAO tproductSortDAO;

  public TProductSortDAO getTproductSortDAO() {
    return tproductSortDAO;
  }

  public void setTproductSortDAO(TProductSortDAO tproductSortDAO) {
    this.tproductSortDAO = tproductSortDAO;
  }

  private TCusSalesPriceHistoryDAO tcusSalesPriceHistoryDAO;

  public TCusSalesPriceHistoryDAO getTcusSalesPriceHistoryDAO() {
    return tcusSalesPriceHistoryDAO;
  }

  public void setTcusSalesPriceHistoryDAO(TCusSalesPriceHistoryDAO tcusSalesPriceHistoryDAO) {
    this.tcusSalesPriceHistoryDAO = tcusSalesPriceHistoryDAO;
  }

  private TProductToolsInforDAO tproductToolsInforDao;

  public TProductToolsInforDAO getTproductToolsInforDao() {
    return tproductToolsInforDao;
  }

  public void setTproductToolsInforDao(TProductToolsInforDAO tproductToolsInforDao) {
    this.tproductToolsInforDao = tproductToolsInforDao;
  }

  private TOrderPriceHistoryDAO torderPriceHistoryDAO;

  public TOrderPriceHistoryDAO getTorderPriceHistoryDAO() {
    return torderPriceHistoryDAO;
  }

  public void setTorderPriceHistoryDAO(TOrderPriceHistoryDAO torderPriceHistoryDAO) {
    this.torderPriceHistoryDAO = torderPriceHistoryDAO;
  }

  private TSuppliersInforDAO tsuppliersInforDAO;

  public TSuppliersInforDAO getTsuppliersInforDAO() {
    return tsuppliersInforDAO;
  }

  public void setTsuppliersInforDAO(TSuppliersInforDAO tsuppliersInforDAO) {
    this.tsuppliersInforDAO = tsuppliersInforDAO;
  }

  private TCustomersInforDAO tcustomersInforDAO;

  public TCustomersInforDAO getTcustomersInforDAO() {
    return tcustomersInforDAO;
  }

  public void setTcustomersInforDAO(TCustomersInforDAO tcustomersInforDAO) {
    this.tcustomersInforDAO = tcustomersInforDAO;
  }

  private TSalesPriceHistoryDAO tsalesPriceHistoryDAO;

  public TSalesPriceHistoryDAO getTsalesPriceHistoryDAO() {
    return tsalesPriceHistoryDAO;
  }

  public void setTsalesPriceHistoryDAO(TSalesPriceHistoryDAO tsalesPriceHistoryDAO) {
    this.tsalesPriceHistoryDAO = tsalesPriceHistoryDAO;
  }

  private TProductBrandDAO tproductBrandDAO;

  public TProductBrandDAO getTproductBrandDAO() {
    return tproductBrandDAO;
  }

  public void setTproductBrandDAO(TProductBrandDAO tproductBrandDAO) {
    this.tproductBrandDAO = tproductBrandDAO;
  }

  @Override
  public List<TProductSort> getProductSortByPage(Map<String, Object> parmMap) {
    return tproductSortDAO.getProductSortByPage(parmMap);
  }

  @Override
  public void saveObject(TProductSort productSortInfo) {
    tproductSortDAO.insert(productSortInfo);
  }

  @Override
  public void deleteProductSortById(String productSortId) {
    tproductSortDAO.deleteByPrimaryKey(productSortId);
  }

  @Override
  public void updateObject(TProductSort productSortInfo) {
    tproductSortDAO.updateByPrimaryKeySelective(productSortInfo);
  }

  @Override
  public List<CurSalesPriceHistoryDto> getCusSalesHistoryProductByPage(Map<String, Object> parmMap) {
    return tcusSalesPriceHistoryDAO.getCusSalesHistoryProductByPage(parmMap);
  }

  @Override
  public int getCusSalesHistoryProductTotal(Map<String, Object> parmMap) {
    return tcusSalesPriceHistoryDAO.getCusSalesHistoryProductTotal(parmMap);
  }

  @Override
  public List<CurSalesPriceHistoryDto> getCusSalesHistoryProductByPageAndProductId(Map<String, Object> parmMap) {
    return tcusSalesPriceHistoryDAO.getCusSalesHistoryProductByPageAndProductId(parmMap);
  }

  @Override
  public int getCusSalesHistoryProductTotalByProductId(Map<String, Object> parmMap) {
    return tcusSalesPriceHistoryDAO.getCusSalesHistoryProductTotalByProductId(parmMap);
  }

  @Override
  public void deleteCurSalerPriceById(String curSalerPriceIdPar) {
    tcusSalesPriceHistoryDAO.deleteByPrimaryKey(curSalerPriceIdPar);

  }

  @Override
  public List<OrderPriceHistoryDto> getOrderPriceHistoryProductByPage(Map<String, Object> parmMap) {
    return torderPriceHistoryDAO.getOrderPriceHistoryProductByPage(parmMap);
  }

  @Override
  public int getOrderPriceHistoryProductTotal(Map<String, Object> parmMap) {
    return torderPriceHistoryDAO.getOrderPriceHistoryProductTotal(parmMap);
  }

  @Override
  public List<OrderPriceHistoryDto> getOrderHistoryProductByPageAndProductId(Map<String, Object> parmMap) {
    return torderPriceHistoryDAO.getOrderHistoryProductByPageAndProductId(parmMap);
  }

  @Override
  public int getOrderHistoryProductTotalByProductId(Map<String, Object> parmMap) {
    return torderPriceHistoryDAO.getOrderHistoryProductTotalByProductId(parmMap);
  }

  @Override
  public void deleteOrderPriceHistoryById(String orderPriceHistoryIdPar) {
    torderPriceHistoryDAO.deleteByPrimaryKey(orderPriceHistoryIdPar);
  }

  @Override
  public List<TSuppliersInfor> getAllSupplierList(Map<String, Object> parmMap) {
    return tsuppliersInforDAO.getAllSupplierListBySupplierName(parmMap);
  }

  @Override
  public List<TCustomersInfor> getAllCustomersList(Map<String, Object> parmMap) {
    return tcustomersInforDAO.getAllCustomersListByCustomersName(parmMap);
  }

  @Override
  public void updateObject(TProductToolsInfor tproductToolsInfor) {
    tproductToolsInforDao.updateByPrimaryKey(tproductToolsInfor);
  }

  public void saveObject(List<TSalesPriceHistory> tsalesPriceHistory) {
    for (Iterator iterator = tsalesPriceHistory.iterator(); iterator.hasNext();) {
      TSalesPriceHistory salesPriceHistory = (TSalesPriceHistory) iterator.next();
      if (salesPriceHistory.getSalePriceDate() == null || "".equals(salesPriceHistory.getSalePriceDate())) {
        continue;
      }
      TSalesPriceHistoryExample example = new TSalesPriceHistoryExample();
      example.createCriteria().andSalePriceDateEqualTo(salesPriceHistory.getSalePriceDate()).andProductToolInforIdEqualTo(
        salesPriceHistory.getProductToolInforId());
      int count = tsalesPriceHistoryDAO.countByExample(example);
      if (count == 1) {
        TSalesPriceHistory record = new TSalesPriceHistory();
        record.setHistoryPrice(salesPriceHistory.getHistoryPrice());
        tsalesPriceHistoryDAO.updateByExampleSelective(record, example);
      } else {
        salesPriceHistory.setId(GenerateSerial.getUUID());
        tsalesPriceHistoryDAO.insert(salesPriceHistory);
      }
    }
  }

  @Override
  public TProductToolsInfor getProToolInfoByID(String id) {

    return tproductToolsInforDao.selectByPrimaryKey(id);
  }

  @Override
  public List<SalesPriceHistoryProDto> getSalesHistoryProductByPageAndProductId(Map<String, Object> parmMap) {
    return tsalesPriceHistoryDAO.getSalesHistoryProductByPageAndProductId(parmMap);
  }

  @Override
  public int getSalesHistoryProductTotalByProductId(Map<String, Object> parmMap) {
    return tsalesPriceHistoryDAO.getSalesHistoryProductTotalByProductId(parmMap);
  }

  @Override
  public Map<String, Object> importSalesPriceData(HttpServletRequest request, HttpServletResponse response) {
    boolean importstate = true;

    // 返回用户上传数据信息
    Map<String, Object> importinfoMap = new HashMap<String, Object>();
    try {
      // 上传文件
      Map<String, String> parMap = this.uploadSalsePriceXls(request, response);
      String filepath = parMap.get("filepath");
      String salesDate = parMap.get("salesDate");

      UserDto userDto = null;
      if (LoginInforUtil.getLoginInfor(request) != null) {
        userDto = LoginInforUtil.getLoginInfor(request).getUser();
      }
      // System.out.println("...filepath=" + filepath);
      if ((filepath != null) && (filepath.length() > 0)) {
        // 检验上传文件数据格式、内容正确
        //				boolean checkstate = this.checkFileData(filepath);
        boolean checkstate = true;
        System.err.println("checkstate = " + checkstate);
        if (checkstate) {
          boolean importdatastate = this.importData(filepath, salesDate, userDto);
          // 正常情况下不会出现如下问题
          // System.out.println("importdatastate:" + importdatastate);
          if (!importdatastate) {
            importstate = false;
            importinfoMap.put("filepath", filepath);
            //System.out.println("系统发生位置错误!");
          }
        } else {
          importinfoMap.put("filepath", filepath);
          importstate = false;
        }
      } else {
        importstate = false;
      }

    } catch (Exception e) {
      e.printStackTrace();
      importstate = false;
    } finally {
      importinfoMap.put("importstate", importstate);
      return importinfoMap;

    }

  }

  @Override
  public Map<String, Object> importSalesPriceExcelData(HttpServletRequest request, HttpServletResponse response) {
    // 返回用户上传数据信息
    Map<String, Object> importinfoMap = new HashMap<String, Object>();
    try {

      // 上传文件
      Map<String, String> parMap = this.uploadSalsePriceXls(request, response);
      String filepath = parMap.get("filepath");//上传到服务器上的excel的路径
      String salesDate = parMap.get("salesDate");//面价执行日期
      UserDto userDto = null;
      if (LoginInforUtil.getLoginInfor(request) != null) {
        userDto = LoginInforUtil.getLoginInfor(request).getUser();
      }
      //将数据插入临时表中
      //				 this.importData(filepath,salesDate, userDto);
      //将excel文件的数据生成本地临时的txt文件，并且将数据导入临时表中
      this.importData(filepath, salesDate, userDto);
      //将$替换成空格
      tsalesPriceHistoryDAO.replaceDollar();
      //将牌号前面或者后面的空格去掉
      tsalesPriceHistoryDAO.deleteSpace();
      //校验临时表中是否有重复数据，如果有重复则将记录的flag改为1
      List<ImportSalesPriceDto> repetInfor = tsalesPriceHistoryDAO.checkReptnForTempDate();
      if (repetInfor.size() > 0) {
        for (int i = 0; i < repetInfor.size(); i++) {
          Map<String, Object> parmMap = new HashMap<String, Object>();
          parmMap.put("brandCode", repetInfor.get(i).getBrandCode());
          parmMap.put("productBrand", repetInfor.get(i).getProductBrand());
          tsalesPriceHistoryDAO.updateReptnForTempDate(parmMap);
        }
      }
      //校验临时表中数据在工具信息中是否存在，如果不存在则讲flag改为2
      tsalesPriceHistoryDAO.updateExistForTempDate();

      //				 List<ImportSalesPriceDto> notExitInfor = tsalesPriceHistoryDAO.checkExistForTempDate();
      //				 if(notExitInfor.size()>0)
      //				 {
      //					 for(int i = 0 ;i < notExitInfor.size() ; i++)
      //					 {
      //						 Map<String, Object> parmMap = new HashMap<String, Object>();	
      //						 parmMap.put("tempId", notExitInfor.get(i).getId());
      //						 tsalesPriceHistoryDAO.updateExistForTempDate(parmMap);
      //					 }
      //				 }
      //校验是否Excel于面价表中是否有重复的数据，如果有则更新面价表中的价格，并且将临时表中记录的flag更改为3
      tsalesPriceHistoryDAO.updateSalePrice();
      //将临时表中的数据插入到面价表中
      tsalesPriceHistoryDAO.insertSalePriceHistory();
      //得到flag为1的临时表数据
      List<ImportSalesPriceDto> flagTow = tsalesPriceHistoryDAO.seleteFlagEqualsTow();
      //得到flag为2的临时表数据
      List<ImportSalesPriceDto> flagThree = tsalesPriceHistoryDAO.seleteFlagEqualsThree();

      //清空临时表
      tsalesPriceHistoryDAO.deleteTempDate();
      if (this.ToUserExcel(flagTow, flagThree, filepath, request, response))
      //返回错误的Excel文件
      {
        importinfoMap.put("filepath", filepath);
        importinfoMap.put("importstate", false);
      } else
        importinfoMap.put("importstate", true);

    } catch (ServletException e) {
      e.printStackTrace();
    } catch (SmartUploadException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return importinfoMap;
  }

  /**
   * 如果Excel中文件的的数据存在问题，讲错误信息导入Excel中，并且将Excel文件返回给客户端
   * @param flagTow//Excel中重复的数据
   * @param flagThree//Excel中不存在的数据
   * @param filepath//上传到服务器上的Excel的临时文件的路径
   */
  public boolean ToUserExcel(List<ImportSalesPriceDto> flagTow, List<ImportSalesPriceDto> flagThree, String filepath, HttpServletRequest request,
    HttpServletResponse response) throws IOException {

    boolean returnExcel = false;//是否向客户端返回excel文件，为true时向客户端返回excel文件

    if (flagTow.size() > 0 || flagThree.size() > 0) {
      returnExcel = true;

      // 读取文件
      FileInputStream fileinput = new FileInputStream(filepath);
      // 创建工作薄
      HSSFWorkbook workbook = new HSSFWorkbook(fileinput);
      fileinput.close();
      // 数据错误时，单元格样式
      CellStyle normalStyle = workbook.createCellStyle();
      Font valueFont = workbook.createFont();
      normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
      // 垂直居中
      normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      normalStyle.setWrapText(true);
      valueFont.setFontHeightInPoints((short) 12);
      valueFont.setColor(Font.COLOR_RED);
      normalStyle.setFont(valueFont);
      normalStyle.setBorderBottom((short) 1);
      normalStyle.setBorderLeft((short) 1);
      normalStyle.setBorderRight((short) 1);
      normalStyle.setBorderTop((short) 1);

      if (flagTow.size() > 0) {
        for (int i = 0; i < flagTow.size(); i++) {
          //得到临时表的rowNumber,下划线前面是工作区数，后面是行数
          String[] number = flagTow.get(i).getRowNumber().split("-");
          //得到工作区
          HSSFSheet childSheet = workbook.getSheetAt(Integer.parseInt(number[0]));
          //得到行数
          HSSFRow row = childSheet.getRow(Integer.parseInt(number[1]));
          //向Excel文件的最后一行写入错误信息
          HSSFCell cell = row.getCell(7);
          if (cell == null)
            cell = row.createCell(7);
          cell.setCellValue("此行导入数据重复，请检查！");
          cell.setCellStyle(normalStyle);
        }
      }
      if (flagThree.size() > 0) {
        for (int i = 0; i < flagThree.size(); i++) {
          //得到临时表的rowNumber,下划线前面是工作区数，后面是行数
          String[] number = flagThree.get(i).getRowNumber().split("-");
          //得到工作区
          HSSFSheet childSheet = workbook.getSheetAt(Integer.parseInt(number[0]));
          //得到行数
          HSSFRow row = childSheet.getRow(Integer.parseInt(number[1]));
          //向Excel文件的最后一行写入错误信息
          HSSFCell cell = row.getCell(7);
          if (cell == null)
            cell = row.createCell(7);
          cell.setCellValue("此行导入数据牌号不存在，请检查！");
          cell.setCellStyle(normalStyle);
        }
      }
      //输出Excel文件.
      FileOutputStream fileOutd = new FileOutputStream(filepath);
      workbook.write(fileOutd);
      fileOutd.close();
    }
    return returnExcel;
  }

  /**
   * 持久化数据
   * @param filepath
   * @param salesDate
   * @param userDto
   * @return
   * @throws IOException
   */
  public boolean importData(String filepath, String salesDate, UserDto userDto) throws IOException {
    boolean importDataState = true;
    // 读取文件
    FileInputStream fileinput = new FileInputStream(filepath);
    // 创建工作薄
    HSSFWorkbook workbook = new HSSFWorkbook(fileinput);
    fileinput.close();
    List<ImportSalesPriceDto> insertSalePriHis = new ArrayList<ImportSalesPriceDto>();//存储导入信心的dto
    int sheetNumber = workbook.getNumberOfSheets();//工作区数量
    if (sheetNumber > 0) {
      for (int i = 0; i < sheetNumber; i++) {
        HSSFSheet childSheet = workbook.getSheetAt(i);
        // 循环该子sheet row
        for (int r = 2, len = childSheet.getPhysicalNumberOfRows(); r < len; r++) {
          ImportSalesPriceDto imsDto = this.getSalePriceInfo(i, r, salesDate, childSheet.getRow(r), userDto, true);
          insertSalePriHis.add(imsDto);
        }
      }
    }
    //生成本地的临时文件
    String txtFilePath = this.createSalesDateFile(insertSalePriHis);
    // 向临时表中批量插入数据
    //		 tsalesPriceHistoryDAO.batchInsertSalePri(insertSalePriHis);
    tsalesPriceHistoryDAO.loadSalePriceDateFile(txtFilePath);
    return importDataState;
  }

  /**
   * 生成导入数据的本地文件
   * 
   * @param insertSalePriHis
   * @return
   */
  private String createSalesDateFile(List<ImportSalesPriceDto> insertSalePriHis) {

    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    // System.out.println(time.format(nowTime));
    String filepath = "c:/test.txt";
    boolean flag = false;//控制文本是否追加
    /* 查找文件，如果不存在，就创建 */
    java.io.File file = new java.io.File(filepath);
    /**
     * 删除原有文件，创建新文件
     */
    try {
      if (!file.exists()) {
        file.createNewFile();
      } else {
        file.delete();
        file.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    int i = 0;
    StringBuffer sb = new StringBuffer();
    for (ImportSalesPriceDto obj : insertSalePriHis) {
      sb.append(obj.getId() + "\t");
      sb.append(obj.getBrandCode() + "\t");
      sb.append(obj.getOldBrandCode() + "\t");
      sb.append(obj.getProductName() + "\t");
      sb.append(obj.getProductBrand() + "\t");
      sb.append(obj.getHistoryPrice() + "\t");
      sb.append(obj.getProductUnit() + "\t");
      sb.append(obj.getSalePriceDate() + "\t");
      sb.append(obj.getUserId() + "\t");
      sb.append(obj.getUserName() + "\t");
      sb.append(time.format(obj.getEditDate()) + "\t");
      sb.append(obj.getRowNumber() + "\t");
      sb.append(obj.getFlag() + "\t");
      sb.append("\r\n");
      i++;
      if (i == 500) {
        flag = true;
        this.method2(filepath, sb.toString(), flag);
        i = 0;
        sb = new StringBuffer();
      }
    }
    this.method2(filepath, sb.toString(), flag);

    return filepath.replace("/", "\\");
  }

  /**
   * 追加文件：使用FileWriter
   * 
   * @param fileName
   * @param content
   */
  public static void method2(String fileName, String content, boolean flag) {
    try {
      // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName, flag), "UTF-8");
      out.write(content);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private TSalesPriceHistory getSalPriAndUpdateProTool(ImportSalesPriceDto imsDto, String salesDate, UserDto userDto) {
    try {
      TProductToolsInfor proinfo = tproductToolsInforDao.getProTools(imsDto);
      if (proinfo != null) {
        if (imsDto.getOldBrandCode() != null) {
          proinfo.setBrandCode(imsDto.getBrandCode());
          tproductToolsInforDao.updateByPrimaryKeySelective(proinfo);
        }
        TSalesPriceHistory sph = new TSalesPriceHistory();
        sph.setId(GenerateSerial.getUUID());
        sph.setCustomerInforId(null);
        sph.setBrandCode(proinfo.getBrandCode());
        sph.setProductCode(proinfo.getProductCode());
        sph.setHistoryPrice(imsDto.getHistoryPrice());
        sph.setParentId(proinfo.getParentId());
        sph.setLeaf(proinfo.getLeaf());
        sph.setProductToolInforId(proinfo.getId());
        sph.setEditDate(new Date());
        sph.setSalePriceDate(salesDate);
        sph.setUserId(userDto.getId());
        sph.setUserName(userDto.getUserName());
        sph.setProductBrand(proinfo.getProductBrand());
        return sph;

      } else {
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 更新数据
   * 
   * @param imsDto
   * @return
   */
  private boolean updateSalePrice(ImportSalesPriceDto imsDto, String salesDate, UserDto userDto) {
    boolean updateState = true;
    try {
      TProductToolsInfor proinfo = tproductToolsInforDao.getProTools(imsDto);
      // System.out.println("proinfo.....====" + proinfo );
      if (proinfo != null) {
        if (imsDto.getOldBrandCode() != null) {
          // System.out.println("123.......");
          // 更新工具信息
          proinfo.setBrandCode(imsDto.getBrandCode());
          tproductToolsInforDao.updateByPrimaryKeySelective(proinfo);
        }
        TSalesPriceHistory sph = new TSalesPriceHistory();
        sph.setId(GenerateSerial.getUUID());
        sph.setBrandCode(imsDto.getBrandCode());
        sph.setProductCode(proinfo.getProductCode());
        sph.setHistoryPrice(imsDto.getHistoryPrice());
        sph.setParentId(proinfo.getParentId());
        sph.setLeaf(proinfo.getLeaf());
        sph.setProductToolInforId(proinfo.getId());
        sph.setEditDate(new Date());
        sph.setSalePriceDate(salesDate);
        sph.setUserId(userDto.getId());
        sph.setUserName(userDto.getUserName());
        // System.out.println("eeeeeeeeeeee" + sph.getProductCode() );
        // System.out.println("33333333......");
        tsalesPriceHistoryDAO.insert(sph);

      } else {
        updateState = false;
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return updateState;
  }

  /**
   * 文件上传
   * 
   * @param request
   * @param response
   * @return
   * @throws ServletException
   * @throws SmartUploadException
   * @throws IOException
   */
  private Map<String, String> uploadSalsePriceXls(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    SmartUploadException, IOException {

    Map<String, String> parMap = new HashMap<String, String>();

    String folderName = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_DIR);
    java.io.File file = new java.io.File(folderName);
    if (!file.exists()) {
      file.mkdir();
    }
    String realPath = folderName + java.io.File.separator;
    this.setRealPath(realPath);
    HttpSession session = request.getSession();
    ServletContext application = session.getServletContext();

    SmartUpload su = null;
    su = new SmartUpload();
    su.initialize(application, session, request, response, null);
    su.upload();
    File suFile = null;
    // 准备保存文件
    // 这里填写项目中存放上传文件的物理路径
    String filePath = this.getRealPath();
    suFile = su.getFiles().getFile(0);
    String id = GenerateSerial.getUUID();
    String path = new StringBuffer(filePath).append(id).append(".").append(suFile.getFileExt()).toString();
    suFile.saveAs(path, SmartUpload.SAVE_PHYSICAL);// 保存文件
    // 存入文件路径
    parMap.put("filepath", path);

    String salesDate = "";
    // 获取传递过来的参数
    salesDate = su.getRequest().getParameter("salesDate");
    // 其他参数
    parMap.put("salesDate", salesDate);

    return parMap;
  }

  //	// 检验文件内容格式正确,错误后修改其内容提示用户
  //	public boolean checkFileData(String filepath) throws IOException,
  //			InvalidFormatException {
  //		boolean fileState = true;
  //		// 读取文件
  //		FileInputStream fileinput = new FileInputStream(filepath);
  //		// 创建工作薄
  //		HSSFWorkbook workbook = new HSSFWorkbook(fileinput);
  //		fileinput.close();
  //		HSSFSheet childSheet = workbook.getSheetAt(0);
  //
  //		String ff = filepath;
  //		InputStream inp = new FileInputStream(ff);
  //		Workbook wbwrite = WorkbookFactory.create(inp);
  //		inp.close();
  //		org.apache.poi.ss.usermodel.Sheet childSheetWrite = wbwrite
  //				.getSheetAt(0);
  //
  //		// 数据错误时，单元格样式
  //		CellStyle normalStyle = wbwrite.createCellStyle();
  //		Font valueFont = wbwrite.createFont();
  //		normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
  //		// 垂直居中
  //		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
  //		normalStyle.setWrapText(true);
  //		valueFont.setFontHeightInPoints((short) 12);
  //		valueFont.setColor(Font.COLOR_RED);
  //		normalStyle.setFont(valueFont);
  //		normalStyle.setBorderBottom((short) 1);
  //		normalStyle.setBorderLeft((short) 1);
  //		normalStyle.setBorderRight((short) 1);
  //		normalStyle.setBorderTop((short) 1);
  //		
  //		ArrayList<ImportSalesPriceDto> dataCatchList = new ArrayList<ImportSalesPriceDto>();
  //		// 循环该子sheet row
  //		for (int r = 2, len = childSheet.getPhysicalNumberOfRows(); r < len; r++) {
  //
  ////			ImportSalesPriceDto imsDto = this.getSalePriceInfo(childSheet
  ////					.getRow(r), true);
  //			imsDto.setRowIndex(r);
  //			dataCatchList.add(imsDto);
  //			if(dataCatchList.size() >= 50){
  //				List<HashMap> tools = tproductToolsInforDao.checkProsToolsIsHaving(dataCatchList);
  //				if(tools.size() < dataCatchList.size()){
  //					for (Iterator iterator = tools.iterator(); iterator
  //							.hasNext();) {
  //						HashMap hashMap = (HashMap) iterator.next();
  //						String brandCode = (String) hashMap.get("brand_code");
  //						for (Iterator iterator2 = dataCatchList.iterator(); iterator2
  //								.hasNext();) {
  //							ImportSalesPriceDto dto = (ImportSalesPriceDto) iterator2.next();
  //							if(dto.getRowIndex() > 0 && dto.getBrandCode().equals(brandCode)){
  //								dto.setRowIndex(-1000);
  //								break;
  //							}
  //						}
  //					}
  //					for (Iterator iterator2 = dataCatchList.iterator(); iterator2.hasNext();) {
  //						ImportSalesPriceDto dto = (ImportSalesPriceDto) iterator2.next();
  //						if(dto.getRowIndex() > 0){
  //							Row row = childSheetWrite.getRow(dto.getRowIndex());
  //							Cell cellr = row.getCell(0);
  //							if (cellr == null)
  //								cellr = row.createCell(0);
  //							cellr.setCellStyle(normalStyle);
  //
  //							Cell c = row.getCell(7);
  //							if (c == null)
  //								c = row.createCell(7);
  //							c.setCellValue("此行导入数据牌号不存在，请检查！");
  //							c.setCellStyle(normalStyle);
  //
  //							fileState = false;
  //
  //						}
  //					}
  //				}
  //			}
  //		}
  //
  //		// Write the output to a file
  //		FileOutputStream fileOutd = new FileOutputStream(ff);
  //		wbwrite.write(fileOutd);
  //		fileOutd.close();
  //
  //		return fileState;
  //	}

  /**
   * 效验产品是否存在
   * 
   * @param imsDto
   * @return
   */
  private boolean checkProTool(ImportSalesPriceDto imsDto) {
    boolean isHaving = false;
    isHaving = tproductToolsInforDao.checkProToolsIsHaving(imsDto);
    return isHaving;
  }

  /**
   * 读取Excel的行数据
   * @param sheetNumber（当前sheet页数）
   * @param rowNumber（当前行数）
   * @param row
   * @param isChange
   * @return
   */
  private ImportSalesPriceDto getSalePriceInfo(int sheetNumber, int rowNumber, String salePriceDate, HSSFRow row, UserDto userDto, boolean isChange) {
    ImportSalesPriceDto imsDto = new ImportSalesPriceDto();
    int i = 6;
    for (int c = 0; c < i; c++) {// 循环该子sheet行对应的单元格项
      HSSFCell cell = row.getCell(c);
      String value = null;
      if (cell == null)
        continue;
      /**
       * 根据类型，获取单元值
       */
      switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_NUMERIC:
        value = "" + cell.getNumericCellValue();
        break;

      case HSSFCell.CELL_TYPE_STRING:
        value = cell.getStringCellValue();
        break;
      case HSSFCell.CELL_TYPE_BLANK:
        value = "";
        break;
      }
      //			if (isChange) {
      //				// 去除字符串中的空格、回车、换行符、制表符
      //				value = StringHelper.replaceBlank(value);
      //			}
      /**
       * 
       */
      switch (c) {
      case 0:
        imsDto.setBrandCode(value.replace(" ", "?"));
        break;
      case 1:
        imsDto.setOldBrandCode(value.replace(" ", "?"));
        break;
      case 2:
        imsDto.setProductName(value);
        break;
      case 3:
        imsDto.setProductBrand(value);
        break;
      case 4:
        try {
          imsDto.setHistoryPrice(BigDecimal.valueOf(Double.valueOf(value.toString())));
        } catch (NumberFormatException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          throw new RuntimeException("工作区：" + sheetNumber + ";行：" + rowNumber + ",列：" + c + "，从excel中读取数据出错：" + e.getMessage());
        }
        break;
      case 5:
        imsDto.setProductUnit(value);
        break;
      }
    }
    imsDto.setId(GenerateSerial.getUUID());
    imsDto.setRowNumber(sheetNumber + "-" + rowNumber);//用当前sheet页数和当前行数组合生成id
    imsDto.setSalePriceDate(salePriceDate);//面价执行日期
    imsDto.setUserId(userDto.getId());
    imsDto.setUserName(userDto.getUserName());
    imsDto.setEditDate(new Date());
    imsDto.setFlag(0);
    return imsDto;
  }

  @Override
  public List<TProductBrand> getProductBrankList(Map<String, Object> parmMap) {
    return tproductBrandDAO.getProductBrankList(parmMap);
  }

  @Override
  public int getProductSortTotal(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return tproductSortDAO.getProductSortTotal(parmMap);
  }

}
