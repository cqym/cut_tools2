package com.tl.resource.web.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.upload.UploadService;
import com.tl.resource.dao.pojo.TAccessories;

public class UploadManagerAction extends DispatchAction {

  private UploadService uploadService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String method = request.getParameter("method");

    if ("upload".equals(method)) {
      return upload(mapping, form, request, response);
    } else if ("download".equals(method)) {
      return download(mapping, form, request, response);
    } else if ("delete".equals(method)) {
      return delete(mapping, form, request, response);
    } else if ("getslave".equals(method)) {
      return getAccessories(mapping, form, request, response);
    } else if ("getslaveById".equals(method)) {
      return getslaveById(mapping, form, request, response);
    } else {
      return null;
    }
  }

  private ActionForward getslaveById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String slaveId = request.getParameter("slaveId");

    TAccessories slave = uploadService.getAccessoriesById(slaveId);
    String jsonStr = JSONObject.fromObject(slave).toString();
    //返回前台字符串
    String resultStr = "{slave : " + jsonStr + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //删除附件
  private ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String ids = request.getParameter("ids");
    JSONArray array = JSONArray.fromObject(ids);
    String resultStr = "{success : true, msg : '删除成功！'}";
    String path = request.getSession().getServletContext().getRealPath("");
    if (null != array && array.size() > 0) {
      try {
        uploadService.deleteFile(array, path);
      } catch (Exception e) {
        e.printStackTrace();
        resultStr = "{success : false, msg : '删除失败！'}";
      }
    }
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //获取附件信息
  private ActionForward getAccessories(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String busId = request.getParameter("busId");
    String busType = request.getParameter("busType");
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "5";
    }
    //参数Map
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("busId", busId);
    paramMap.put("busType", busType);
    paramMap.put("startIndex", startIndex);
    paramMap.put("pageSize", pageSize);

    List<AccessoriesDto> list = uploadService.getAccessoriesByBusId(paramMap);
    Integer totalCount = uploadService.getAccessoriesCount(paramMap);
    String jsonStr = JSONArray.fromObject(list).toString();

    //返回前台字符串
    String resultStr = "{slaves : " + jsonStr + ",totalCount:" + totalCount + "}";
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //上传
  private ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("text/html;charset=utf-8");
    request.setCharacterEncoding("utf-8");
    String pageErrorInfo = null;
    String resultStr = "";
    try {
      pageErrorInfo = uploadService.fileUpload(request, response);
      if (pageErrorInfo == null) {
        resultStr = "{success : true}";
      }
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false,msg:'" + e.getMessage() + "'}";

    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //下载
  private ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
    //response.setContentType("text/html;charset=utf-8");
    //request.setCharacterEncoding("utf-8");
    //response.setCharacterEncoding("utf-8");
    try {
      uploadService.fileDownLoad(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }

    return null;
  }

  public UploadService getUploadService() {
    return uploadService;
  }

  public void setUploadService(UploadService uploadService) {
    this.uploadService = uploadService;
  }

}
