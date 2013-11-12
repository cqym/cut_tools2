package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TQuotationProjectSortInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetailExample;
import com.tl.resource.dao.pojo.TQuotationProjectSortInforExample;

public class ExportDataProjectQuotationHandler implements IExportDataBusinessHandler {
	private TCompanyInforDAO tcompanyInforDAO = (TCompanyInforDAO) SystemInstance.getInstance().getBean("TCompanyInforDAO");
	private TExchangeRateDAO exchangeRateDAO = (TExchangeRateDAO) SystemInstance.getInstance().getBean("TExchangeRateDAO");
	private TQuotationInforDAO quoInfoDAO = (TQuotationInforDAO) SystemInstance.getInstance().getBean("TQuotationInfoDAO");
	private TQuotationProductDetailDAO quoDetailDAO = (TQuotationProductDetailDAO) SystemInstance.getInstance().getBean("TQuotationProductDetailDAO");
	private TQuotationProjectSortInforDAO quoProInfoDAO = (TQuotationProjectSortInforDAO) SystemInstance.getInstance().getBean("TQuotationProjectSortInforDAO");
	TQuotationInfor quoInfor;
	@Override
	public Map<String, Object> getBusinessData(String id) {
		Map<String, Object> rt = new HashMap<String, Object>();
		quoInfor = quoInfoDAO.selectByPrimaryKey(id);
		rt.put("quoInfor", quoInfor);
		TQuotationProjectSortInforExample sortExp = new TQuotationProjectSortInforExample();
		sortExp.createCriteria().andQuotationInforIdEqualTo(id);
		List sortInfors = quoProInfoDAO.selectByExample(sortExp);
		rt.put("sortInfors", sortInfors);
		TQuotationProductDetailExample detailexample = new TQuotationProductDetailExample();
		detailexample.createCriteria().andQuotationInforIdEqualTo(id);
		List list = quoDetailDAO.selectByExample(detailexample);
		rt.put("quoDetail", list);
		TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoInfor.getSellerName());
		rt.put("companyInfor", comInfo);
		TExchangeRate expo = exchangeRateDAO.selectByPrimaryKey(quoInfor.getCurrency());
		rt.put("exchangeRate", expo);
		return rt;
	}
	@Override
	public String getMainCode() {
		// TODO Auto-generated method stub
		return quoInfor.getQuotationCode();
	}

}
