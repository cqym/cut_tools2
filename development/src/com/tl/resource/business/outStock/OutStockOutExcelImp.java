package com.tl.resource.business.outStock;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tl.common.util.WebUtils;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TOutStockDetailDAO;
import com.tl.resource.dao.TOutStockInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.pojo.TAccessories;
import com.tl.resource.dao.pojo.TAccessoriesExample;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCompanyInforExample;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TOutStockDetail;
import com.tl.resource.dao.pojo.TOutStockInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;

public class OutStockOutExcelImp implements OutStockOutExcel {
	public static final String TempletePath = "\\upload\\templete\\outStock_templete.xls";
	private TContractInforDAO contractInforDAO;
	private String logoPath,footerPath,basePath;
	private TCompanyInforDAO companyInforDAO;
	private TAccessoriesDAO accessoriesDAO;
	private TQuotationInforDAO quotationInforDAO;
	private TOutStockInforDAO outStockInforDAO;
	private TOutStockDetailDAO outStockDetailDAO;
	private TCompanyInfor comInfor = new TCompanyInfor();
	private TOutStockInfor outStockInfor = null;
	private String sourceCode;
	private HSSFSheet dataSheet;
	private HSSFWorkbook workbook;
	@Override
	public void exportExcel(String conId, HttpServletResponse response,
			HttpServletRequest request) throws IOException { 
		outStockInfor = outStockInforDAO.selectByPrimaryKey(conId);
		basePath = request.getRealPath("/");
		FileInputStream fs = new FileInputStream(basePath + TempletePath);
        workbook = new HSSFWorkbook(fs);
        dataSheet = workbook.getSheetAt(0);
		initCompanyInfor(outStockInfor.getOutStockType());
		String businessId = comInfor.getId();
		initImgPaths(businessId);
		exportExcelWorkbook();
		
		//输出Excel文件.
		response.setContentType("application/vnd.ms-excel"); 
		WebUtils.setDownloadableHeader(response, outStockInfor.getOutStockCode() + ".xls");
		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	private void initCompanyInfor(Integer type) {
		if(type == 1 || type == 3 || type == 4){//有合同的情况
			TContractInfor conInfro = contractInforDAO.getContractByCode(outStockInfor.getContractCode());//.selectByPrimaryKey(outStockInfor.getContractId());
			sourceCode = conInfro.getContractCode();
			TCompanyInforExample comExp = new TCompanyInforExample();
			comExp.createCriteria().andCompanyNameEqualTo(conInfro.getSellerName());
			List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
			if(companys != null && companys.size() > 0){
				comInfor = companys.get(0);
			}
		}else if(type == 2 || type == 5 || type == 6 || type == 7){//报价单出库
			TQuotationInfor quoInfor = quotationInforDAO.selectByPrimaryKey(outStockInfor.getQuotationId());
			sourceCode = quoInfor.getQuotationCode();
			TCompanyInforExample comExp = new TCompanyInforExample();
			comExp.createCriteria().andCompanyNameEqualTo(quoInfor.getSellerName());
			List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
			if(companys != null && companys.size() > 0){
				comInfor = companys.get(0);
			}
		}else if(type == 0){//直接出库
			TCompanyInforExample comExp = new TCompanyInforExample();
			List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
			if(companys != null && companys.size() > 0){
				comInfor = companys.get(0);
			}
		}
	}
	private void initImgPaths(String businessId) {
		List<Integer> types = new ArrayList<Integer>();
		types.add(3);
		types.add(4);
		TAccessoriesExample accExp = new TAccessoriesExample();
		accExp.createCriteria().andBusinessIdEqualTo(businessId).andBusinessTypeIn(types);//logo
		List<TAccessories> logoList = accessoriesDAO.selectByExample(accExp);
		if(logoList != null && logoList.size() > 0){
			for (Iterator iterator = logoList.iterator(); iterator.hasNext();) {
				TAccessories accessories = (TAccessories) iterator.next();
				if(accessories.getBusinessType() == 3){
					logoPath = accessories.getPath();
					if(logoPath != null){
						logoPath = basePath + logoPath.replaceAll("\\/", "\\\\");
					}
				}else if(accessories.getBusinessType() == 4){
					footerPath = accessories.getPath();
					if(footerPath != null){
						footerPath = basePath + footerPath.replaceAll("\\/", "\\\\");
					}
				}
			}
		}
	}
	private void exportExcelWorkbook() {
	
		Sheet s = this.dataSheet;
		int rowIndex = 0;
		Drawing patriarch = s.createDrawingPatriarch(); 
		rowIndex = createSheetHeaderInfor(workbook, s,rowIndex,"出　库　单",patriarch);
		rowIndex++;
		//rowIndex = createSheetTableHeader(workbook, s,rowIndex);
		rowIndex = createSheetTableData(workbook, s,rowIndex,Integer.MAX_VALUE);//待修改
		if(rowIndex < 14){rowIndex = 13;} 
		if(this.footerPath != null)
			insertFooterImage(s,(HSSFWorkbook)workbook,(short)0,++rowIndex,(short)9,rowIndex,patriarch);
	}
	private void insertFooterImage(Sheet s, HSSFWorkbook wb,short x1,int y1,short x2,int y2,Drawing patriarch){
		HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,255,x1,y1,x2,y2);       
		patriarch.createPicture(anchor , wb.addPicture(getImageByteArray(this.footerPath),HSSFWorkbook.PICTURE_TYPE_JPEG));  
	}
	private int createSheetTableData(Workbook wb, Sheet s, int rowIndex,
			int maxValue) {
		TOutStockDetail po = new TOutStockDetail();
		po.setOutStockInforId(this.outStockInfor.getId());
		List<com.tl.resource.dao.pojo.TOutStockDetail> list = outStockDetailDAO.selectDetailHasReserveInforByRecord(po );
		CellStyle tcs;
		Cell c0;
		Row r8;
		int i = 0;
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
		for (Iterator iterator = list.iterator(); i < maxValue && iterator.hasNext();) {
			TOutStockDetail outStockDetail = (TOutStockDetail) iterator.next();
			
			r8 = s.createRow(++rowIndex);
			c0 = r8.createCell(0);
			c0.setCellValue(outStockDetail.getProjectCode());
			
			c0 = r8.createCell(1);
			c0.setCellValue(outStockDetail.getSerialNumber());
			
			c0 = r8.createCell(2);
			c0.setCellValue(outStockDetail.getProductName());
			
			c0 = r8.createCell(3);
			c0.setCellValue(outStockDetail.getBrandCode());
			
			c0 = r8.createCell(4);
			c0.setCellValue(outStockDetail.getProductUnit());
			
			c0 = r8.createCell(5);
			BigDecimal tmp = outStockDetail.getOrderAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//订单数量
			
			c0 = r8.createCell(6);
			tmp = outStockDetail.getAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//数量
			
			c0 = r8.createCell(7);
			tmp = outStockDetail.getAllOutAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//累计出库数量
			
			c0 = r8.createCell(8);
			c0.setCellValue(outStockDetail.getProductBrand());
			
			c0 = r8.createCell(9);
			c0.setCellValue("");
			
			
		}
		return rowIndex;
	}
	private Cell getCell(Row r3,int index) {
		Cell c0 = (r3.getCell(index) == null ? r3.createCell(index) : r3.getCell(index));
		return c0;
	}
	private int createSheetHeaderInfor(Workbook wb, Sheet s, int rowIndex,
			String header, Drawing patriarch) {
		HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,255,(short) 0,0,(short)9,0); 
		if(this.logoPath != null)
			patriarch.createPicture(anchor , wb.addPicture(getImageByteArray(this.logoPath),HSSFWorkbook.PICTURE_TYPE_JPEG));
	
		rowIndex += 2;
		int colIndex1 = 2;
		int colIndex2 = 6;
		Row r3 = s.getRow(rowIndex);
		Cell c0 = getCell(r3,colIndex1);
		c0.setCellValue(this.outStockInfor.getOutStockCode());
		Cell c5 = getCell(r3,colIndex2);
		c5.setCellValue(this.outStockInfor.getContractCode());
		
		
		
		Row r5 = s.getRow(++rowIndex);
		Cell c1 = getCell(r5,colIndex1);
		c1.setCellValue(outStockInfor.getOutStockDate());
		c5 = getCell(r5,colIndex2);
		c5.setCellValue(outStockInfor.getQuotationCode());
	
		Row r6 = s.getRow(++rowIndex);
		c5 = getCell(r6,colIndex1);
		c5.setCellValue(getOutStockType(outStockInfor.getOutStockType()));  
		c5 = getCell(r6,colIndex2);
		c5.setCellValue(outStockInfor.getCustomerName());
		
		Row r7 = s.getRow(++rowIndex);
		c5 = getCell(r7,colIndex1);
		c5.setCellValue(outStockInfor.getUserName());  
		return rowIndex;
	}
	private String getOutStockType(Integer outStockType) {//0直接出库，1合同出库(提取库存)，2报价单出库，3材料出库,4合同出库(采购)
		String typeName = null;
		switch (outStockType) {
		case 0:
			typeName = "直接出库";
			break;
		case 1:
			typeName = "合同出库(提取库存)";
			break;
		case 2:
			typeName = "报价单出库";
			break;
		case 3:
			typeName = "材料出库";
			break;
		case 4:
			typeName = "合同出库(采购)";
			break;
		default:
			typeName = "";
			break;
		}
		return typeName;
	}
	private byte[] getImageByteArray(String logoPath2) {
		 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();;  
		 try {
			if(logoPath2 == null) return null;
			String[] temp = logoPath2.split("\\.");
			String extName = temp.length >= 2 ? temp[temp.length - 1] : "";
			BufferedImage bufferImg = ImageIO.read(new File(logoPath2));  
			ImageIO.write(bufferImg,extName,byteArrayOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return byteArrayOut.toByteArray();
	}
	private CellStyle getCellStyle(short boldweight,short fontSize,String borderType,short alignType,boolean isTextWrap,Workbook wb){
//		DataFormat df = wb.createDataFormat();
		
		Font font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight(boldweight);
		
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(alignType);
		style.setWrapText(isTextWrap);
		if("all".equals(borderType) || "right".equals(borderType)){
			style.setBorderRight(CellStyle.BORDER_MEDIUM);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		}
		if("all".equals(borderType) || "left".equals(borderType)){
			style.setBorderLeft(CellStyle.BORDER_MEDIUM);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		}
		if("all".equals(borderType) || "top".equals(borderType)){
			style.setBorderTop(CellStyle.BORDER_MEDIUM);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		}
		if("all".equals(borderType) || "bottom".equals(borderType)){
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		}
		return style;
	}
	public TContractInforDAO getContractInforDAO() {
		return contractInforDAO;
	}
	public void setContractInforDAO(TContractInforDAO contractInforDAO) {
		this.contractInforDAO = contractInforDAO;
	}
	public TCompanyInforDAO getCompanyInforDAO() {
		return companyInforDAO;
	}
	public void setCompanyInforDAO(TCompanyInforDAO companyInforDAO) {
		this.companyInforDAO = companyInforDAO;
	}
	public TAccessoriesDAO getAccessoriesDAO() {
		return accessoriesDAO;
	}
	public void setAccessoriesDAO(TAccessoriesDAO accessoriesDAO) {
		this.accessoriesDAO = accessoriesDAO;
	}
	public TQuotationInforDAO getQuotationInforDAO() {
		return quotationInforDAO;
	}
	public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
		this.quotationInforDAO = quotationInforDAO;
	}
	public TOutStockInforDAO getOutStockInforDAO() {
		return outStockInforDAO;
	}
	public void setOutStockInforDAO(TOutStockInforDAO outStockInforDAO) {
		this.outStockInforDAO = outStockInforDAO;
	}
	public TOutStockDetailDAO getOutStockDetailDAO() {
		return outStockDetailDAO;
	}
	public void setOutStockDetailDAO(TOutStockDetailDAO outStockDetailDAO) {
		this.outStockDetailDAO = outStockDetailDAO;
	}
	public TOutStockInfor getOutStockInfor() {
		return outStockInfor;
	}
	public void setOutStockInfor(TOutStockInfor outStockInfor) {
		this.outStockInfor = outStockInfor;
	}

}
