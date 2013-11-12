package com.tl.resource.web.toolsInfor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.business.protoolsinfo.ProToolsInforService;
import com.tl.resource.dao.pojo.TProductBrand;
import com.tl.resource.dao.pojo.TProductSort;
import com.tl.resource.dao.pojo.TProductSource;

public class LoadComboDataAction extends DispatchAction {
  private ProToolsInforService proToolsInforService;

  public ProToolsInforService getProToolsInforService() {
    return proToolsInforService;
  }

  public void setProToolsInforService(ProToolsInforService proToolsInforService) {
    this.proToolsInforService = proToolsInforService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String method = request.getParameter("method");

    if ("getProSort".equals(method)) {
      return getProSort(mapping, form, request, response);
    } else if ("getProBrand".equals(method)) {
      return getproBrand(mapping, form, request, response);
    } else if ("getProSource".equals(method)) {
      return getProSource(mapping, form, request, response);
    } else if ("getTreeDto".equals(method)) {
      return getTreeDto(mapping, form, request, response);
    } else {
      return null;
    }
  }

  //根据产品牌号 名称 品牌 来源 查找产品 判断该产品是否已经存在
  private ActionForward getTreeDto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {

    response.setContentType("text/html;charset=utf-8");
    String treeDtoStr = request.getParameter("treeDto");

    JSONObject treeJsonObj = JSONObject.fromObject(treeDtoStr);

    TreeDto treeDto = (TreeDto) JSONObject.toBean(treeJsonObj, TreeDto.class);

    List<TreeDto> proDto = proToolsInforService.getTreeDto(treeDto);
    boolean flag = false;
    if (proDto != null && proDto.size() > 0) {
      flag = true;
    }

    //返回前台字符串
    String resultStr = "{success : " + flag + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //产品来源
  private ActionForward getProSource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String name = request.getParameter("name");

    List<TProductSource> list = proToolsInforService.getProSourceByAll();
    String jsonStr = JSONArray.fromObject(list).toString();

    //返回前台字符串
    String resultStr = "{proSource : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //产品品牌
  private ActionForward getproBrand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    response.setContentType("text/html;charset=utf-8");
    String sourceName = request.getParameter("name");
    String brandName = request.getParameter("brandName");
    List<TProductBrand> list = proToolsInforService.getProBrandBySorce(sourceName, brandName);
    String jsonStr = JSONArray.fromObject(list).toString();

    //返回前台字符串
    String resultStr = "{proBrand : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  //产品组别
  private ActionForward getProSort(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String brand = request.getParameter("name");

    List<TProductSort> list = proToolsInforService.getProSortByBrand(brand);

    String jsonStr = JSONArray.fromObject(list).toString();

    //返回前台字符串
    String resultStr = "{proSort : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getProSortBySortCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String sortCode = request.getParameter("sortCode");

    List<TProductSort> list = proToolsInforService.getProSortBySortCode(sortCode);

    PrintWriter out = response.getWriter();
    if (list.size() > 0) {
      out.print(JSONObject.fromObject(list.get(0)));
    } else {
      out.print("false");
    }
    out.flush();
    out.close();
    return null;
  }

}
