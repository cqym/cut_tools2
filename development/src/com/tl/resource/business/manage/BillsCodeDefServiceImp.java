package com.tl.resource.business.manage;

import java.util.Calendar;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.tl.resource.business.dto.BillsCodeDefDto;
import com.tl.resource.dao.TBillsCodeDefDAO;
import com.tl.resource.dao.pojo.TBillsCodeDef;
import com.tl.resource.dao.pojo.TBillsCodeDefExample;

public class BillsCodeDefServiceImp implements BillsCodeDefService{
	private static final String YEAR_REG_2 = "{YY}";
	private static final String YEAR_REG_4 = "{YYYY}";
	private static final String MONTH_REG = "{MM}";
	private static final String DAY_REG = "{DD}";
	private static final String SUPPLIERS_REG = "{SUPPLIERS}";
	private static final String CUSTOMERS_REG = "{CUSTOMERS}";
	private static final String SERIAL_NUMBER_REG = "\\{#*\\}";
	private static final String COMPANY_REG = "{COMPANYCODE}";
	private TBillsCodeDefDAO billsCodeDefDAO;
	@Override
	public List<BillsCodeDefDto> getAll() {
		TBillsCodeDefExample example = new TBillsCodeDefExample();
		example.setOrderByClause("bill_type");
		List<BillsCodeDefDto> billsCodeList = billsCodeDefDAO.selectByExample(example);
		return billsCodeList;
	}

	@Override
	public String getBillCode(String type, String gysbh, String khbh,String gsbh) {
		TBillsCodeDefExample example = new TBillsCodeDefExample();
		example.createCriteria().andBillTypeEqualTo(type);
		List<TBillsCodeDef> list = billsCodeDefDAO.selectByExample(example);
		if(list == null || list.size() == 0) return "没有此类型"; 
		TBillsCodeDef bcd = list.get(0);
		String billDefNormal = bcd.getBillDefNormal();
		if(billDefNormal == null || "".equals(billDefNormal)) return "此类型规则未定义";
		String code = billDefNormal;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendar.getTime());
		String tCode = code.replace(YEAR_REG_4,String.valueOf(calendar.get(Calendar.YEAR)));
		if(tCode.equals(code)){//如果相等，说明没有替换成功，尝试另外格式
			code = code.replace(YEAR_REG_2,String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4));
		}else{
			code = tCode;
		}
		String temp = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		temp = temp.length() == 1 ? "0" + temp : temp;
		code = code.replace(MONTH_REG,temp);
		temp = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		temp = temp.length() == 1 ? "0" + temp : temp;
		code = code.replace(DAY_REG,temp);
		Integer currNum = bcd.getSerialNumber();
		if(currNum == null) currNum = 0;
		if(bcd.getCurrentYear() == null || !bcd.getCurrentYear().equals(String.valueOf(calendar.get(Calendar.YEAR)))){
			currNum = 0;
			bcd.setCurrentYear(String.valueOf(calendar.get(Calendar.YEAR)));
			bcd.setSerialNumber(0);
		}
		currNum ++;
		int serialNumberLen = getSerialNumberCount(code);
		code = code.replaceFirst(SERIAL_NUMBER_REG,getSerialNumber(currNum,serialNumberLen));
		if(gysbh != null){
			code = code.replace(SUPPLIERS_REG,gysbh);
		}
		if(khbh != null){
			code = code.replace(CUSTOMERS_REG,khbh);
		}
		if(gsbh != null){
			code = code.replace(COMPANY_REG,gsbh);
		}
		bcd.setSerialNumber(currNum);
		if(bcd.getCurrentYear() ==  null){
			bcd.setCurrentYear(String.valueOf(calendar.get(Calendar.YEAR)));
		}
		billsCodeDefDAO.updateByPrimaryKeySelective(bcd);
		return code;
	}

	@Override
	public void update(TBillsCodeDef dto) {
		billsCodeDefDAO.updateByPrimaryKeySelective(dto);
	}

	public TBillsCodeDefDAO getBillsCodeDefDAO() {
		return billsCodeDefDAO;
	}

	public void setBillsCodeDefDAO(TBillsCodeDefDAO billsCodeDefDAO) {
		this.billsCodeDefDAO = billsCodeDefDAO;
	}
	
	private static int getSerialNumberCount(String conent){
		 try {
			PatternCompiler compiler = new Perl5Compiler();
			 org.apache.oro.text.regex.Pattern pattern = compiler.compile("\\{#*\\}", Perl5Compiler.CASE_INSENSITIVE_MASK);
			 PatternMatcher matcher = new Perl5Matcher();
			 if(matcher.contains(conent, pattern)){
				  org.apache.oro.text.regex.MatchResult ma = matcher.getMatch();
				 String temp = (ma.group(0));
				 return temp.length() - 2 > 0? temp.length() - 2 : 0;
			 }else{
				 //System.out.println( " 匹配失败！");
			 }
		} catch (MalformedPatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	private static String getSerialNumber(int serialNumber,int len){
		String num = String.valueOf(serialNumber);
		if(num.length() >= len) return num;
		int flen = len - num.length();
		StringBuffer sb = new StringBuffer(len);
		for (int i = 0; i < flen; i++) {
			sb.append("0");
		}
		sb.append(num);
		return sb.toString();
	}
}
