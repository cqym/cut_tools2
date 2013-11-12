package com.tl.resource.web.dataExp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.excelutils.ExcelUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.WebUtils;
import com.tl.resource.dao.pojo.TContractInfor;

public class ExpDataByTempleteAction extends DispatchAction {
  public ActionForward exp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("application/vnd.ms-excel");
    WebUtils.setDownloadableHeader(response, "contractList.xls");
    TContractInfor c = new TContractInfor();
    c.setContractCode("124121212");
    ExcelUtils.addValue("contract", c);
    ExcelUtils.export(request.getSession().getServletContext(), "/upload/templete/contract_templete.xls", response.getOutputStream());
    response.getOutputStream().flush();

    return null;
  }
}
