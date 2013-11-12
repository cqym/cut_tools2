package com.tl.resource.web.baseInfo;

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

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TProductBrand;

/**
 * 品牌信息管理Action
 * @author ftl
 *
 */
public class BrandManagerAction extends DispatchAction {
	private BaseInfoService baseInfoService;

	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		
		if("getProBrand".equals(method)) {
			return getProBrand(mapping, form, request, response);
		} if("addBrand".equals(method)) {
			return addBrand(mapping, form, request, response);
		} if("modifyBrand".equals(method)) {
			return modifyBrand(mapping, form, request, response);
		} if("deleteBrand".equals(method)) {
			return deleteBrand(mapping, form, request, response);
		} if("getRunDate".equals(method)) {
			return getRunDate(mapping, form, request, response);
		} if("getProBrandByName".equals(method)) {
			return getProBrandByName(mapping, form, request, response);
		} else {
			return null;
		}
	}
	//根据名称获取品牌
	private ActionForward getProBrandByName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String name = request.getParameter("name");
		
		//参数Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name",name);
		Integer total = baseInfoService.getProductBrandTotal(paramMap);
		paramMap.put("start", 0);
		paramMap.put("limit", total);
		
		
		List<TProductBrand> list = baseInfoService.getProductBrand(paramMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		
		//返回前台字符串
		String resultStr = "{totalProperty : " + total + ", brands : "  + jsonStr + "}";
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	//删除品牌
	private ActionForward deleteBrand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String brandId = request.getParameter("brand");
		String resultStr = "{success : true, msg : '删除品牌成功！'}";
		
		try {
			JSONArray array = JSONArray.fromObject(brandId);
			baseInfoService.deleteBrand(array);
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : true, msg : '删除品牌失败！'}";
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//修改品牌
	private ActionForward modifyBrand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String brandStr = request.getParameter("brand");
		String resultStr = "{success : true, msg : '修改品牌成功！'}";
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(brandStr);
			
			TProductBrand brand = (TProductBrand)JSONObject.toBean(jsonObject, TProductBrand.class);
			baseInfoService.updateBrand(brand);
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : true, msg : '修改品牌失败！'}";
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//添加品牌
	private ActionForward addBrand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String brandStr = request.getParameter("brand");
		String resultStr = "{success : true, msg : '添加品牌成功！'}";
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(brandStr);
			
			TProductBrand brand = (TProductBrand)JSONObject.toBean(jsonObject, TProductBrand.class);
			brand.setId(GenerateSerial.getUUID());
			baseInfoService.insertBrand(brand);
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : true, msg : '添加品牌失败！'}";
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}

	//获取品牌信息
	private ActionForward getProBrand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String name = request.getParameter("name");
		String sourceName = request.getParameter("sourceName");
		String orderPriceRunDate = request.getParameter("orderPriceRunDate");
		String salePriceRunDate = request.getParameter("salePriceRunDate");
		
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		Integer start = 0;
		Integer limit = 20;
		if(startStr != null && !"".equals(startStr)) {
			start = Integer.parseInt(startStr);
		}
		
		if(limitStr != null && !"".equals(limitStr)) {
			limit = Integer.parseInt(limitStr);
		}
		//参数Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		paramMap.put("name",name);
		paramMap.put("sourceName",sourceName);
		paramMap.put("orderPriceRunDate",orderPriceRunDate);
		paramMap.put("salePriceRunDate",salePriceRunDate);
		
		String sort = request.getParameter("sort");
		if(sort != null){
			paramMap.put("sort", RegexUtils.toDataBaseColName(sort));
			paramMap.put("dir", request.getParameter("dir"));
		}
		
		List<TProductBrand> list = baseInfoService.getProductBrand(paramMap);
		String jsonStr = JSONArray.fromObject(list).toString();
		
		Integer total = baseInfoService.getProductBrandTotal(paramMap);
		
		//返回前台字符串
		String resultStr = "{totalProperty : " + total + ", brands : "  + jsonStr + "}";
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	/**
	 * 品牌执行期
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ActionForward getRunDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		
	    String  proBrandName = request.getParameter("proBrandName");
		//参数Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("proBrandName", proBrandName);
		List<TProductBrand> list = baseInfoService.getRunData(paramMap);
		
		String jsonStr = JSONArray.fromObject(list).toString();
		
		//返回前台字符串
		String resultStr = "{runDate : "  + jsonStr + "}";
		//防止前台store，根据id覆盖
		//String newresultStr = resultStr.replace("id", "idd");
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
}
