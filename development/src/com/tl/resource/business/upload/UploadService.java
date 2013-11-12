package com.tl.resource.business.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;

import com.tl.common.smartupload.SmartUpload;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.dao.pojo.TAccessories;

public interface UploadService {

  /**
   * 文件上传方法.
   * @param su
   * @return
   * @throws Exception
   */
  public String fileUpload(SmartUpload su) throws Exception;

  /**
   * 文件上传方法.
   * @param su
   * @param pageContext
   * @return
   * @throws Exception
   */
  public String fileUpload(SmartUpload su, PageContext pageContext) throws Exception;

  /**
   * 文件上传方法.
   * @param su
   * @param pageContext
   * @return
   * @throws Exception
   */
  public String fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception;

  /**
   * 文件下载方法
   * @param su
   * @return
   * @throws Exception
   */
  public String fileDownLoad(SmartUpload su, TAccessories slave) throws Exception;

  /**
   * 文件下载方法
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public String fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception;

  /**
   * 删除文件
   * @param array
   * @throws Exception
   */
  public void deleteFile(JSONArray array, String path) throws Exception;

  /**
     * 获取产品附件信息
     * @param paramMap
     * @return
     */
  List<AccessoriesDto> getAccessoriesByBusId(Map<String, Object> paramMap);

  /**
   * 新增附件信息
   * @param accessories
   */
  void insertAccessories(TAccessories accessories);

  /**
   * 新增附件信息
   * @param accessories
   */
  void insertAccessories(AccessoriesDto accessoriesDto) throws Exception;

  /**
   * 删除附件信息
   * @param id
   */
  void deleteAccessories(String id);

  /**
   * 根据ID获取附件信息
   * @return
   */
  TAccessories getAccessoriesById(String id);

  public Integer getAccessoriesCount(Map<String, Object> paramMap);
}
