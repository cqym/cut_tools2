package com.tl.resource.business.contract;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import com.tl.common.util.WebUtils;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TContractProjectSortInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCompanyInforExample;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractProductDetail;
import com.tl.resource.dao.pojo.TContractProductDetailExample;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;

public class ContractOutExcelImp implements ContractOutExcel {
  private java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

  private java.text.DecimalFormat amountdf = new java.text.DecimalFormat("#");

  public static final String TempletePath = "\\upload\\templete\\contract_templete.xls";

  private TContractInforDAO contractInforDAO;

  private TContractProductDetailDAO contractProductDetailDAO;

  private TContractProjectSortInforDAO contractProjectSortInforDAO;

  private TCompanyInforDAO companyInforDAO;

  private TCustomersInforDAO customersInforDAO;

  private TAccessoriesDAO accessoriesDAO;

  private TExchangeRateDAO exchangeRateDAO;

  private Map<String, CellStyle> styles;

  private TCompanyInfor comInfor = new TCompanyInfor();

  private TCustomersInfor cusInfor = new TCustomersInfor();

  private TContractInfor conPo;

  private String logoPath, footerPath, basePath;

  private HSSFSheet dataSheet;

  private HSSFWorkbook workbook;

  @Override
  public void exportExcel(String conId, HttpServletResponse response, HttpServletRequest request) throws IOException {
    // 生成Excel文件.
    conPo = contractInforDAO.selectByPrimaryKey(conId);
    String path = request.getRealPath("") + TempletePath;
    FileInputStream fs = new FileInputStream(path);

    TCompanyInforExample comExp = new TCompanyInforExample();
    comExp.createCriteria().andCompanyNameEqualTo(conPo.getSellerName());
    List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
    if (companys != null && companys.size() > 0) {
      comInfor = companys.get(0);
    }
    TCustomersInforExample cusExp = new TCustomersInforExample();
    cusExp.createCriteria().andCustomerCodeEqualTo(conPo.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(cusExp);
    if (cusList != null && cusList.size() > 0) {
      cusInfor = cusList.get(0);
    }

    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andContractInforIdEqualTo(conPo.getId()).andParentToolsIdEqualTo("root");

    example.setOrderByClause("contract_Project_Sort_Id,project_Code,serial_Number");
    List<TContractProductDetail> list = contractProductDetailDAO.selectByExample(example);

    Map<String, Object> businessData = new HashMap<String, Object>();
    businessData.put("companyInfor", comInfor);
    businessData.put("customerInfor", cusInfor);
    businessData.put("contractDetail", list);
    businessData.put("contractInfor", conPo);

    //exportExcelWorkbook();

    // 输出Excel文件.
    response.setContentType("application/vnd.ms-excel");
    WebUtils.setDownloadableHeader(response, conPo.getContractCode() + ".xls");
    XLSTransformer tf = new XLSTransformer();
    try {
      Workbook workbook = tf.transformXLS(fs, businessData);
      workbook.write(response.getOutputStream());
    } catch (ParsePropertyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    response.getOutputStream().flush();
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TContractProductDetailDAO getContractProductDetailDAO() {
    return contractProductDetailDAO;
  }

  public void setContractProductDetailDAO(TContractProductDetailDAO contractProductDetailDAO) {
    this.contractProductDetailDAO = contractProductDetailDAO;
  }

  public TContractProjectSortInforDAO getContractProjectSortInforDAO() {
    return contractProjectSortInforDAO;
  }

  public void setContractProjectSortInforDAO(TContractProjectSortInforDAO contractProjectSortInforDAO) {
    this.contractProjectSortInforDAO = contractProjectSortInforDAO;
  }

  public Map<String, CellStyle> getStyles() {
    return styles;
  }

  public void setStyles(Map<String, CellStyle> styles) {
    this.styles = styles;
  }

  public TCompanyInforDAO getCompanyInforDAO() {
    return companyInforDAO;
  }

  public void setCompanyInforDAO(TCompanyInforDAO companyInforDAO) {
    this.companyInforDAO = companyInforDAO;
  }

  public TCustomersInforDAO getCustomersInforDAO() {
    return customersInforDAO;
  }

  public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
    this.customersInforDAO = customersInforDAO;
  }

  public TAccessoriesDAO getAccessoriesDAO() {
    return accessoriesDAO;
  }

  public void setAccessoriesDAO(TAccessoriesDAO accessoriesDAO) {
    this.accessoriesDAO = accessoriesDAO;
  }

  public TExchangeRateDAO getExchangeRateDAO() {
    return exchangeRateDAO;
  }

  public void setExchangeRateDAO(TExchangeRateDAO exchangeRateDAO) {
    this.exchangeRateDAO = exchangeRateDAO;
  }

}
