package com.tl.resource.business.protoolsinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.smartupload.SmartUpload;

public interface ImportToolsService {
  /**
   * 文件导入方法.
   * @param su
   * @return
   * @throws Exception
   */
  public String importTools(SmartUpload su) throws Exception;

  /**
   * 文件导入方法.
   * @param su
   * @param pageContext
   * @return
   * @throws Exception
   */
  public String importTools(HttpServletRequest request, HttpServletResponse response);

  /**
   * 工具导出方法
   * @param os
   * @param list
   */
  public void exportTools(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
