/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.MatReserveInforService;
import com.tl.resource.dao.pojo.TMatAccountsInfor;

/**
 * @author xtaia 库存对应的帐页信息
 */
public class MatAccountsInfoListByReserveIdAction extends Action {

  private MatReserveInforService matReserveInforService;

  public MatReserveInforService getMatReserveInforService() {
    return matReserveInforService;
  }

  public void setMatReserveInforService(MatReserveInforService matReserveInforService) {
    this.matReserveInforService = matReserveInforService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");

    String reserveId = request.getParameter("reserveId");

    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("reserveInforId", reserveId);

    //System.out.println("@@@@@@@@@@" + reserveId);
    List<TMatAccountsInfor> list = matReserveInforService.getAccountsInfoListByByReserveId(parmMap);
    //List<TAccountsInfor> list = null;
    String jsonStr = JSONArray.fromObject(list).toString();
    String resultStr = "{accountsInfoList : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
