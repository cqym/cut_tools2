package com.tl.resource.business.delivery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFChildAnchor;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFShapeGroup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
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
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TDeliveryDetailDAO;
import com.tl.resource.dao.TDeliveryInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.pojo.TAccessories;
import com.tl.resource.dao.pojo.TAccessoriesExample;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCompanyInforExample;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TDeliveryDetail;
import com.tl.resource.dao.pojo.TDeliveryDetailExample;
import com.tl.resource.dao.pojo.TDeliveryInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;

public class DeliveryOutExcelImp implements DeliveryOutExcel{
	public static final String TempletePath = "\\upload\\templete\\delivery_templete.xls";
	private TContractInforDAO contractInforDAO;
	private TDeliveryInforDAO deliveryInforDAO;
	private TDeliveryDetailDAO deliveryDetailDAO;
	private String logoPath,footerPath,basePath;
	private TCompanyInforDAO companyInforDAO;
	private TCustomersInforDAO customersInforDAO;
	private TAccessoriesDAO accessoriesDAO;
	private TQuotationInforDAO quotationInforDAO;
	private TQuotationProductDetailDAO quotationProductDetailDAO;
	private TCompanyInfor comInfor = new TCompanyInfor();
	private TCustomersInfor cusInfor = new TCustomersInfor();
	private TDeliveryInfor deInfor;
	private TContractInfor conInfro;
	private TQuotationInfor quotationInfor;
	private HSSFSheet dataSheet;
	private HSSFWorkbook workbook;

	@Override
	public void exportExcel(String conId, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		deInfor = deliveryInforDAO.selectByPrimaryKey(conId);
		basePath = request.getRealPath("/");
		FileInputStream fs = new FileInputStream(basePath + TempletePath);
        workbook = new HSSFWorkbook(fs);
        dataSheet = workbook.getSheetAt(0);
		String custCode = null;
		if(deInfor.getDeliveryType() == 0){//0按合同交货，1按报价单交货
			conInfro = contractInforDAO.selectByPrimaryKey(deInfor.getContractInforId());
			custCode = conInfro.getCustomerCode();
			TCompanyInforExample comExp = new TCompanyInforExample();
			comExp.createCriteria().andCompanyNameEqualTo(conInfro.getSellerName());
			List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp );
			if(companys != null && companys.size() > 0){
				comInfor = companys.get(0);
			}
		}else{
			quotationInfor = quotationInforDAO.selectByPrimaryKey(deInfor.getQuotationId());
			custCode = quotationInfor.getCustomerCode();
			TCompanyInforExample comExp = new TCompanyInforExample();
			comExp.createCriteria().andCompanyNameEqualTo(quotationInfor.getSellerName());
			List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
			if(companys != null && companys.size() > 0){
				comInfor = companys.get(0);
			}
		}
		List<Integer> types = new ArrayList<Integer>();
		types.add(3);
		types.add(4);
		TAccessoriesExample accExp = new TAccessoriesExample();
		accExp.createCriteria().andBusinessIdEqualTo(comInfor.getId()).andBusinessTypeIn(types);//logo
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
		TCustomersInforExample cusExp = new TCustomersInforExample();
		cusExp.createCriteria().andCustomerCodeEqualTo(custCode);
	    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(cusExp );
		if(cusList != null && cusList.size() > 0){
			 cusInfor = cusList.get(0);
		}
		 exportExcelWorkbook();
		
		//输出Excel文件.
		response.setContentType("application/vnd.ms-excel"); 
		WebUtils.setDownloadableHeader(response, deInfor.getDeliveryCode() + ".xls");
		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
	} 

	private void exportExcelWorkbook() {
		Workbook wb = this.workbook;
		Sheet s = this.dataSheet;//wb.createSheet("交货单正本");
		int rowIndex = 0;
		Drawing patriarch = s.createDrawingPatriarch(); 
		rowIndex = createSheetHeaderInfor(wb, s,rowIndex,"交　货　单",patriarch);
		//rowIndex = createSheetTableHeader(wb, s,rowIndex);
		
		rowIndex = createSheetTableData(wb, s,rowIndex,Integer.MAX_VALUE);//待修改
		TDeliveryDetailExample example = new TDeliveryDetailExample();
		example.createCriteria().andDeliveryInforIdEqualTo(deInfor.getId());
		int count = deliveryDetailDAO.countByExample(example );
		if(count > 6){//待完善,需要详细清单
			
		}
		rowIndex ++;
		HSSFPatriarch pdraw = (HSSFPatriarch) patriarch;
		HSSFShapeGroup group = pdraw.createGroup(new HSSFClientAnchor(0,0,1023,255,(short)0,rowIndex,(short)9,rowIndex + 7));
		HSSFTextbox textbox1 = group.createTextbox(new HSSFChildAnchor(0,0,1023,255));
		HSSFRichTextString temp = new HSSFRichTextString("尊敬的用户：\n								        请您在收到货后_______日内将此单签字回传（逾期将视为验收合格），谢谢合作！\n         	传真号码：\n 	电子邮箱：\n												收货人签字：\n							收货日期：\n							");
		
		Font f = wb.createFont();
		f.setFontHeight((short) (35.7 * 7));
		temp.applyFont(f);
		textbox1.setString(temp);
		rowIndex += 7;
		insertFooterImage(s,(HSSFWorkbook)wb,(short)0,++rowIndex,(short)9,rowIndex,patriarch);
	}
	private void insertFooterImage(Sheet s, HSSFWorkbook wb,short x1,int y1,short x2,int y2,Drawing patriarch){
		HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,255,x1,y1,x2,y2);   
		if(this.footerPath!=null)
		{
			//System.out.println("..................footerPath" + footerPath);
			patriarch.createPicture(anchor , wb.addPicture(getImageByteArray(this.footerPath),HSSFWorkbook.PICTURE_TYPE_JPEG)); 
		}
		 
	}

	private int createSheetTableData(Workbook wb, Sheet s, int rowIndex, int maxCount) {
		TDeliveryDetailExample example = new TDeliveryDetailExample();
		example.createCriteria().andDeliveryInforIdEqualTo(deInfor.getId());
		example.setOrderByClause("pro_sort_name,serial_number");
		List<TDeliveryDetail> detail = deliveryDetailDAO.selectByExample(example );
		CellStyle tcs;
		Cell c0;
		Row r8;
		int i = 0;
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
		for (Iterator iterator = detail.iterator(); i < maxCount && iterator.hasNext();) {
			TDeliveryDetail deliveryDetail = (TDeliveryDetail) iterator.next();
			
			r8 = s.createRow(++rowIndex);
			c0 = r8.createCell(0);
			c0.setCellValue(deliveryDetail.getProjectCode());
			
			c0 = r8.createCell(1);
			c0.setCellValue(deliveryDetail.getSerialNumber());
			
			c0 = r8.createCell(2);
			c0.setCellValue(deliveryDetail.getProductName());
			
			c0 = r8.createCell(3);
			c0.setCellValue(deliveryDetail.getBrandCode());
			
//			c0 = r8.createCell(4);
//			c0.setCellValue(deliveryDetail.getProductUnit());
			
			c0 = r8.createCell(4);
			BigDecimal tmp = deliveryDetail.getContractAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//订单数量
			
			c0 = r8.createCell(5);
			tmp = deliveryDetail.getAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//数量
			
			c0 = r8.createCell(6);
			tmp = deliveryDetail.getAllDeliveryAmount();
			tmp = tmp == null ? BigDecimal.ZERO : tmp;
			c0.setCellValue(df.format(tmp.doubleValue()));//累计交货数量
			
			c0 = r8.createCell(7);
			c0.setCellValue(deliveryDetail.getProductCode());
			
			c0 = r8.createCell(8);
			c0.setCellValue(deliveryDetail.getMemo());
		}
		return rowIndex;
	}
	
	private int createSheetHeaderInfor(Workbook wb, Sheet s, int rowIndex,String header, Drawing patriarch) {
		HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,1023,255,(short) 0,0,(short)9,0);       
		patriarch.createPicture(anchor , wb.addPicture(getImageByteArray(this.logoPath),HSSFWorkbook.PICTURE_TYPE_JPEG));
		rowIndex = 2;
		int colIndex1 = 2;
		int colIndex2 = 6;
		Row r3 = dataSheet.getRow(rowIndex++);
		Cell c0 = getCell(r3,colIndex1);
		c0.setCellValue(deInfor.getDeliveryCode());
		Cell c5 = getCell(r3,colIndex2);
		c5.setCellValue(deInfor.getCustomerName());
//		if(deInfor.getDeliveryDate() != null && deInfor.getDeliveryDate().split("-").length >= 3){
//			String[] date = deInfor.getDeliveryDate().split("-");
//			c5.setCellValue(date[0] + "年" + date[1] + " 月" + date[2] + "日");
//		}
		
		Row r4 = s.getRow(rowIndex++);
		c5 = getCell(r4,colIndex1);
		c5.setCellValue( deInfor.getDeliveryDate());
		if(deInfor.getDeliveryDate() != null && deInfor.getDeliveryDate().split("-").length >= 3){
			String[] date = deInfor.getDeliveryDate().split("-");
			c5.setCellValue(date[0] + "年" + date[1] + " 月" + date[2] + "日");
		}
			
		Row r5 = s.getRow(rowIndex++);
		Cell c1 = getCell(r5,colIndex1);
		c1.setCellValue(deInfor.getQuotationCode());
		c5 = getCell(r5,colIndex2);
		c5.setCellValue(deInfor.getCusContactPerson());
	
		Row r6 = s.getRow(rowIndex++);
		c5 = getCell(r6,colIndex1);
		c5.setCellValue(deInfor.getContractCode());
		c5 = getCell(r6,colIndex2);
		c5.setCellValue(deInfor.getCustomerPhone());  
		
		Row r7 = s.getRow(rowIndex++);
		c5 = getCell(r7,colIndex1);
		c5.setCellValue(deInfor.getContactPerson());
		c5 = getCell(r7,colIndex2);
		c5.setCellValue(deInfor.getCustomerFax());
		
		Row r8 = s.getRow(rowIndex++);
		c5 = getCell(r8,colIndex1);
		c5.setCellValue(deInfor.getUserName());
		return rowIndex;
	}

	private Cell getCell(Row r3,int index) {
		Cell c0 = (r3.getCell(index) == null ? r3.createCell(index) : r3.getCell(index));
		return c0;
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
	public TDeliveryInforDAO getDeliveryInforDAO() {
		return deliveryInforDAO;
	}
	public void setDeliveryInforDAO(TDeliveryInforDAO deliveryInforDAO) {
		this.deliveryInforDAO = deliveryInforDAO;
	}
	public TDeliveryDetailDAO getDeliveryDetailDAO() {
		return deliveryDetailDAO;
	}
	public void setDeliveryDetailDAO(TDeliveryDetailDAO deliveryDetailDAO) {
		this.deliveryDetailDAO = deliveryDetailDAO;
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
	public TCustomersInforDAO getCustomersInforDAO() {
		return customersInforDAO;
	}
	public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
		this.customersInforDAO = customersInforDAO;
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
	public TQuotationProductDetailDAO getQuotationProductDetailDAO() {
		return quotationProductDetailDAO;
	}
	public void setQuotationProductDetailDAO(
			TQuotationProductDetailDAO quotationProductDetailDAO) {
		this.quotationProductDetailDAO = quotationProductDetailDAO;
	}

}
