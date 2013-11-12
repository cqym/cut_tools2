package com.tl.resource.business.upload;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.smartupload.Constant;
import com.tl.common.smartupload.File;
import com.tl.common.smartupload.SmartUpload;
import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.pojo.TAccessories;
import com.tl.resource.dao.pojo.TAccessoriesExample;
import com.tl.resource.dao.pojo.TAccessoriesExample.Criteria;

public class UploadServiceImpl implements UploadService {
  private TAccessoriesDAO accessoriesDao;

  private String realPath;

  @Override
  public String fileUpload(SmartUpload su) throws Exception {
    File suFile = null;
    int fileCount = 0;
    String busId = "";
    String busType = "";
    String userId = "";
    String userName = "";
    try {
      //获取传递过来的参数
      busId = su.getRequest().getParameter("busId");
      busType = su.getRequest().getParameter("busType");
      userId = su.getRequest().getParameter("userId");
      userName = su.getRequest().getParameter("userName");

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
      //准备保存文件
      //这里填写项目中存放上传文件的物理路径
      String filePath = this.getRealPath();

      for (int i = 0; i < su.getFiles().getCount(); i++) {
        String id = GenerateSerial.getUUID();
        String path = new StringBuffer(filePath).append(id).append(".").append(suFile.getFileExt()).toString();

        suFile = su.getFiles().getFile(i);
        suFile.saveAs(path, SmartUpload.SAVE_PHYSICAL);//保存文件

        TAccessories slave = new TAccessories();
        slave.setId(id);
        slave.setFileName(suFile.getFileName());
        slave.setFileSize(new Long(suFile.getSize()));
        slave.setUploadTime(new Date());
        slave.setBusinessType(Integer.parseInt(busType));
        slave.setUserId(userId);
        slave.setUserName(userName);
        slave.setBusinessId(busId);
        slave.setPath("/upload/" + id + "." + suFile.getFileExt());
        accessoriesDao.insertSelective(slave);

      }
      //成功返回null
      return null;
    } finally {
      su = null;
    }
  }

  @Override
  public String fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    java.io.File file = new java.io.File(Constant.UPLOAD_DIR);
    if (!file.exists()) {
      file.mkdir();
    }
    String realPath = Constant.UPLOAD_DIR + java.io.File.separator;
    this.setRealPath(realPath);
    SmartUpload su = null;
    HttpSession session = request.getSession();
    ServletContext application = session.getServletContext();

    su = new SmartUpload();
    su.initialize(application, session, request, response, null);
    su.upload();

    return this.fileUpload(su);

  }

  @Override
  public String fileUpload(SmartUpload su, PageContext pageContext) throws Exception {
    return null;
  }

  @Override
  public String fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
    SmartUpload su = null;
    HttpSession session = request.getSession();
    ServletContext application = session.getServletContext();
    String id = request.getParameter("id");
    TAccessories slave = this.getAccessoriesById(id);

    // 新建一个SmartUpload对象 
    su = new SmartUpload();
    // 初始化 
    su.initialize(application, session, request, response, null);

    // 设定contentDisposition为null以禁止浏览器自动打开文件， 
    //保证点击链接后是下载文件。若不设定，则下载的文件扩展名为 
    //doc时，浏览器将自动用word打开它。扩展名为pdf时， 
    //浏览器将用acrobat打开。 
    su.setContentDisposition(null);

    return this.fileDownLoad(su, slave);
  }

  @Override
  public String fileDownLoad(SmartUpload su, TAccessories slave) throws Exception {
    String filePath = slave.getPath();
    String fileName = slave.getFileName();
    try {
      java.io.File f = new java.io.File(Constant.UPLOAD_DIR);
      su.downloadFile(f.getParent() + filePath, null, fileName);
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("文件下载失败！");
    } finally {
      su = null;
    }
  }

  @Override
  public void deleteFile(JSONArray array, String path) throws Exception {
    Iterator<String> iterator = array.iterator();
    while (iterator.hasNext()) {
      String id = iterator.next();
      TAccessories slave = this.getAccessoriesById(id);
      String p = slave.getPath();
      p = p.replaceAll("\\/", "//");
      String filePath = new StringBuffer(path).append(p).toString();

      java.io.File file = new java.io.File(filePath);

      if (file.delete()) {
        this.deleteAccessories(id);
      } else if (!file.exists()) {
        this.deleteAccessories(id);
      }
    }
  }

  @Override
  public List<AccessoriesDto> getAccessoriesByBusId(Map<String, Object> paramMap) {
    return accessoriesDao.getAccessoriesByBusId(paramMap);
  }

  public TAccessoriesDAO getAccessoriesDao() {
    return accessoriesDao;
  }

  public void setAccessoriesDao(TAccessoriesDAO accessoriesDao) {
    this.accessoriesDao = accessoriesDao;
  }

  @Override
  public void insertAccessories(TAccessories accessories) {
    accessoriesDao.insertSelective(accessories);
  }

  @Override
  public void insertAccessories(AccessoriesDto accessoriesDto) throws Exception {
    TAccessories accessories = new TAccessories();
    BeanUtils.copyProperties(accessories, accessoriesDto);
    this.insertAccessories(accessories);
  }

  @Override
  public void deleteAccessories(String id) {
    accessoriesDao.deleteByPrimaryKey(id);
  }

  public String getRealPath() {
    return realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  @Override
  public TAccessories getAccessoriesById(String id) {
    return accessoriesDao.selectByPrimaryKey(id);
  }

  @Override
  public Integer getAccessoriesCount(Map<String, Object> paramMap) {
    TAccessoriesExample example = new TAccessoriesExample();
    Criteria c = example.createCriteria();
    if (paramMap.get("busType") != null) {
      c.andBusinessTypeEqualTo(Integer.valueOf(paramMap.get("busType").toString()));
    }
    return accessoriesDao.countByExample(example);
  }

}
