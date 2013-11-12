package com.tl.resource.web.reserveOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.SuppliersInforDto;
import com.tl.resource.business.reserveOrder.ReserveOrderService;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class SuppliersInforListAction extends Action{
	
	private ReserveOrderService reserveOrderServiceImpl;

	

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String size = request.getParameter("limit") == null?"15":request.getParameter("limit");
		String start = request.getParameter("start") == null?"0":request.getParameter("start");
		int pageSize = Integer.parseInt(size);
		int startIndex = Integer.parseInt(start);
		String supplierCode = "";
		String supplierName = "";
		String contactPerson = "";
		String brand = "";
		String searchStr = request.getParameter("searchStr");
		if(searchStr != null && !"".equals(searchStr)) {
			
			JSONObject search = JSONObject.fromObject(searchStr);		
			try {
				supplierCode = search.getString("supplierCode");
				supplierName = search.getString("supplierName");
				contactPerson = search.getString("contactPerson");
				brand = search.getString("brand");
			} catch(Exception e) {
				
			}
		}
		Map<String, Object> mapStr = new HashMap<String, Object>();
		mapStr.put("start", startIndex);
		mapStr.put("limit", pageSize);
		mapStr.put("supplierCode", supplierCode);
		mapStr.put("supplierName", supplierName);
		mapStr.put("contactPerson", contactPerson);
		mapStr.put("brand", brand);
		List<TSuppliersInfor> suppliersInfor = reserveOrderServiceImpl.getSuppliersInforList(mapStr);
		int total = reserveOrderServiceImpl.getSuppliersInforListCount(mapStr);
		try {
			String jsonResult = JSONArray.fromObject(suppliersInfor).toString();
			PrintWriter out = response.getWriter();
			out.write("{totalProperty:"+total+",root:" + jsonResult + "}");
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
