package com.tl.resource.business.exceltemplete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.common.util.WebUtils;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TExpTempleteDAO;
import com.tl.resource.dao.TExpTempleteTypeDAO;
import com.tl.resource.dao.pojo.TAccessories;
import com.tl.resource.dao.pojo.TAccessoriesExample;
import com.tl.resource.dao.pojo.TExpTemplete;
import com.tl.resource.dao.pojo.TExpTempleteExample;
import com.tl.resource.dao.pojo.TExpTempleteType;
import com.tl.resource.dao.pojo.TExpTempleteExample.Criteria;

public class ExpTempleteServiceImp implements ExpTempleteService {
  private TExpTempleteDAO expTempleteDAO;

  private TAccessoriesDAO accessoriesDAO;

  private TExpTempleteTypeDAO expTempleteTypeDAO;

  @Override
  public void deleteTempleteById(String id) {
    // TODO Auto-generated method stub
    expTempleteDAO.deleteByPrimaryKey(id);
  }

  @Override
  public TExpTemplete getTExpTempleteById(String id) {
    // TODO Auto-generated method stub
    return expTempleteDAO.selectByPrimaryKey(id);
  }

  @Override
  public PaginationSupport getTExpTempletes(Map<String, String> params, int startIndex, int pageSize) {
    TExpTempleteExample example = new TExpTempleteExample();
    Criteria c = example.createCriteria();
    if (params.get("templeteName") != null) {
      c.andTempleteNameLike("%" + params.get("templeteName") + "%");
    }
    if (params.get("memo") != null) {
      c.andMemoLike("%" + params.get("memo") + "%");
    }
    if (params.get("templeteType") != null && !"".equals(params.get("templeteType"))) {
      c.andTempleteTypeEqualTo(Integer.valueOf(params.get("templeteType")));
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      if (params.get("startDate") != null && params.get("endDate") != null) {
        Date v1 = sdf.parse(params.get("startDate").toString());
        Date v2 = sdf.parse(params.get("endDate").toString());
        c.andCreateTimeBetween(v1, v2);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    example.setOrderByClause("create_time desc");
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    List listDto = expTempleteDAO.selectByExample(example);
    int count = expTempleteDAO.countByExample(example);
    PaginationSupport page = new PaginationSupport(listDto, count, pageSize, startIndex);
    return page;
  }

  @Override
  public void insertTemplete(TExpTemplete instance) {
    // TODO Auto-generated method stub
    instance.setId(GenerateSerial.getUUID());
    expTempleteDAO.insert(instance);
  }

  @Override
  public void updateTemplete(TExpTemplete instance) {
    // TODO Auto-generated method stub
    expTempleteDAO.updateByPrimaryKeySelective(instance);
  }

  public TExpTempleteDAO getExpTempleteDAO() {
    return expTempleteDAO;
  }

  public void setExpTempleteDAO(TExpTempleteDAO expTempleteDAO) {
    this.expTempleteDAO = expTempleteDAO;
  }

  @Override
  public void deleteTempleteByIds(String[] id) {
    // TODO Auto-generated method stub
    for (int i = 0; i < id.length; i++) {
      this.deleteTempleteById(id[i]);
    }
  }

  @Override
  public void expertExce(String bussinessId, String templeteId, HttpServletResponse response, String basePath) throws InstantiationException,
    IllegalAccessException, ClassNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
    // TODO Auto-generated method stub
    OutputStream out = response.getOutputStream();
    XLSTransformer tf = new XLSTransformer();
    TAccessoriesExample example = new TAccessoriesExample();
    example.createCriteria().andBusinessIdEqualTo(templeteId);
    List accList = accessoriesDAO.selectByExample(example);
    TAccessories acc = null;
    if (accList != null && accList.size() > 0) {
      acc = (TAccessories) accList.get(0);
    }
    if (acc == null) {
      throw new RuntimeException("没有找到导出模板文件！");
    }
    TExpTemplete temp = expTempleteDAO.selectByPrimaryKey(templeteId);
    if (temp == null) {
      throw new RuntimeException("没有找到模板数据！");
    }
    TExpTempleteType tempType = expTempleteTypeDAO.selectByPrimaryKey(temp.getTempleteType());
    if (tempType == null) {
      throw new RuntimeException("没有找到模板类型数据！");
    }

    String path = new File(basePath).getParent() + acc.getPath().replaceAll("\\/", "\\\\");

    IExportDataBusinessHandler param = (IExportDataBusinessHandler) Class.forName(tempType.getClassName()).newInstance();
    Map<String, Object> data = param.getBusinessData(bussinessId);
    WebUtils.setDownloadableHeader(response, param.getMainCode() + ".xls");
    List helpList = new ArrayList();
    for (int i = 0; i < 20; i++) {
      helpList.add(new ArrayList());
    }
    data.put("helplist", helpList);
    Workbook workbook = tf.transformXLS(new FileInputStream(path), data);
    workbook.write(out);
    out.flush();

  }

  @Override
  public void expertListExce(Map para, String templeteId, HttpServletResponse response, String basePath) throws InstantiationException,
    IllegalAccessException, ClassNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
    // TODO Auto-generated method stub
    OutputStream out = response.getOutputStream();
    XLSTransformer tf = new XLSTransformer();
    TAccessoriesExample example = new TAccessoriesExample();
    example.createCriteria().andBusinessIdEqualTo(templeteId);
    List accList = accessoriesDAO.selectByExample(example);
    TAccessories acc = null;
    if (accList != null && accList.size() > 0) {
      acc = (TAccessories) accList.get(0);
    }
    if (acc == null) {
      throw new RuntimeException("没有找到导出模板文件！");
    }
    TExpTemplete temp = expTempleteDAO.selectByPrimaryKey(templeteId);
    if (temp == null) {
      throw new RuntimeException("没有找到模板数据！");
    }
    TExpTempleteType tempType = expTempleteTypeDAO.selectByPrimaryKey(temp.getTempleteType());
    if (tempType == null) {
      throw new RuntimeException("没有找到模板类型数据！");
    }

    String path = new File(basePath).getParent() + acc.getPath().replaceAll("\\/", "\\\\");

    IExportListDataBusinessHandler param = (IExportListDataBusinessHandler) Class.forName(tempType.getClassName()).newInstance();
    Map<String, Object> data = param.getBusinessData(para);
    WebUtils.setDownloadableHeader(response, param.getMainCode() + ".xls");

    Workbook workbook = tf.transformXLS(new FileInputStream(path), data);
    workbook.write(out);
    out.flush();
  }

  public TAccessoriesDAO getAccessoriesDAO() {
    return accessoriesDAO;
  }

  public void setAccessoriesDAO(TAccessoriesDAO accessoriesDAO) {
    this.accessoriesDAO = accessoriesDAO;
  }

  public TExpTempleteTypeDAO getExpTempleteTypeDAO() {
    return expTempleteTypeDAO;
  }

  public void setExpTempleteTypeDAO(TExpTempleteTypeDAO expTempleteTypeDAO) {
    this.expTempleteTypeDAO = expTempleteTypeDAO;
  }

}
