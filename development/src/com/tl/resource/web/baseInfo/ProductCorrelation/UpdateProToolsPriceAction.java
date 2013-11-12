/**
 * 
 */
package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TSalesPriceHistory;

/**
 * @author xtaia
 * 更新产品信息
 */
public class UpdateProToolsPriceAction extends Action {
	private static final DateFormat df = new SimpleDateFormat("yyyy");
	private ProductCorrelationService productCorrelationService ;

	public ProductCorrelationService getProductCorrelationService() {
		return productCorrelationService;
	}

	public void setProductCorrelationService(
			ProductCorrelationService productCorrelationService) {
		this.productCorrelationService = productCorrelationService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '修改产品市场价格信息成功'}";
		String modifProToolsPriceRecordsPar = request.getParameter("modifProToolsPriceRecordsPar");
		//工序列表信息
		JSONArray proToolsRecords = JSONArray.fromObject(modifProToolsPriceRecordsPar);
		
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		df.format(new Date());
		try {
			List<TSalesPriceHistory> tsalesPriceHistorys = new ArrayList<TSalesPriceHistory>();
			Iterator<JSONObject> iter = proToolsRecords.iterator();
			while(iter.hasNext()) {
				JSONObject productToolsObject = iter.next();
				TProductToolsInfor tproductToolsInfor = productCorrelationService.getProToolInfoByID(productToolsObject.getString("productToolInforId"));
				TSalesPriceHistory tsalesPriceHistory  = new TSalesPriceHistory();
				tsalesPriceHistory.setId(GenerateSerial.getUUID());
				tsalesPriceHistory.setHistoryPrice(new BigDecimal(productToolsObject.getDouble("salePrice")));
				tsalesPriceHistory.setProductToolInforId(tproductToolsInfor.getId());
				
				tsalesPriceHistory.setSalePriceDate(productToolsObject.getString("salePriceDate"));
				tsalesPriceHistory.setParentId(tproductToolsInfor.getParentId());
				tsalesPriceHistory.setLeaf(tproductToolsInfor.getLeaf());
				tsalesPriceHistory.setBrandCode(tproductToolsInfor.getBrandCode());
				tsalesPriceHistory.setProductCode(tproductToolsInfor.getProductCode());
				tsalesPriceHistory.setUserName(userDto.getUserName());
				tsalesPriceHistory.setUserId(userDto.getId());
				tsalesPriceHistory.setProductBrand(tproductToolsInfor.getProductBrand());
				tsalesPriceHistorys.add(tsalesPriceHistory);
			}
			productCorrelationService.saveObject(tsalesPriceHistorys);
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '修改产品市场价格信息失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}



}
