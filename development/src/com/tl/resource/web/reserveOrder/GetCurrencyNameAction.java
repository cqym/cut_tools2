package com.tl.resource.web.reserveOrder;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.dto.CurrencyDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.business.reserveOrder.ReserveOrderService;
import com.tl.resource.dao.pojo.TExchangeRate;

public class GetCurrencyNameAction extends Action {
	private ReserveOrderService reserveOrderServiceImpl;


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		List<CurrencyDto> list = reserveOrderServiceImpl.getCurrencyName();
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		String resultStr = "{currency : " + jsonStr + "}";
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}


	public ReserveOrderService getReserveOrderServiceImpl() {
		return reserveOrderServiceImpl;
	}


	public void setReserveOrderServiceImpl(
			ReserveOrderService reserveOrderServiceImpl) {
		this.reserveOrderServiceImpl = reserveOrderServiceImpl;
	}
	
	
	
}
