package com.tl.resource.business.quotation.generalquo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.tl.common.util.DateComparator;
import com.tl.common.util.StringHelper;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;


public class ExcelService {
	private TCompanyInforDAO tcompanyInforDAO;
	private TAccessoriesDAO accessoriesDao;
	
	private String logoPath;
	//表头   
    public final String[] tableHeader = {"项目", "序号", "名称", "牌号", 
    	"数量","单价","折扣","净价","金额","交货期","品牌","备注"};
    //创建工作本   
    public HSSFWorkbook sysEventWorkBook = new HSSFWorkbook();   
    //创建表   
    public HSSFSheet demoSheet = sysEventWorkBook.createSheet("报价单");   
    //表头的单元格个数目   
    public final short cellNumber = (short)tableHeader.length;   
    //数据库表的列数   
    public static final int columNumber = 12;
    
    /**
     * 合并单元格
     * @param rowIndex 行号
     */
    private void joinCell(int rowIndex) {
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 0, (short) 1));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 2, (short) 5));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 6, (short) 7));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 8, (short) 11));
    }
    
    private void joinCountCell(int rowIndex) {
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 6, (short) 7));
    }
    
    private AccessoriesDto getAccDto(String busId, int busType) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("busId", busId);
        paramMap.put("busType", busType);
        List<AccessoriesDto> list = accessoriesDao.getAccessoriesByBusId(paramMap);
        if(list != null && list.size() > 0) {
        	return list.get(0);
        } else {
        	return null;
        }
    }
    
    private void insertImage(TCompanyInfor comInfo, int footRow) {
        BufferedImage bufferImg =null;
        BufferedImage footerImg =null;
        String extName = "";
        String footExtName = "";
        File file = null;
        File footFile = null;
        
        //页眉图片logo
        AccessoriesDto headerSlave = this.getAccDto(comInfo.getId(), 3);
        if(headerSlave != null) {
	        String headerPath = headerSlave.getPath();
	        headerPath = headerPath.replaceAll("\\/", "//");
	        extName = StringHelper.getExtName(headerPath, '.');
	        String headerFilePath = new StringBuffer(logoPath).append(headerPath).toString();
	        
	    	file = new java.io.File(headerFilePath);
        }
        //页脚图片logo
        AccessoriesDto footerSlave = this.getAccDto(comInfo.getId(), 4);
        if(footerSlave != null) {
	        String footerPath = footerSlave.getPath();
	        footerPath = footerPath.replaceAll("\\/", "//");
	        footExtName = StringHelper.getExtName(footerPath, '.');
	        String footerFilePath = new StringBuffer(logoPath).append(footerPath).toString();
	        
	    	footFile = new java.io.File(footerFilePath);
        }
        try {
        	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
        	ByteArrayOutputStream footerbyteArrayOut = new ByteArrayOutputStream(); 
			
	        HSSFPatriarch patriarch = demoSheet.createDrawingPatriarch(); 
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0,100,131,36,(short) 0,0,(short) 2,2);
	        HSSFClientAnchor footAnchor = new HSSFClientAnchor(0,100,131,36,(short) 0,footRow,(short) 2,footRow+2);
	        
	        //插入页眉图片
	        if(file != null) {
		        bufferImg = ImageIO.read(file);
		        if("png".equalsIgnoreCase(extName)) {
		        	ImageIO.write(bufferImg,"png",byteArrayOut);
		        	patriarch.createPicture(anchor , sysEventWorkBook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));
		        	
		        } else if("jpg".equalsIgnoreCase(extName)) {
		        	ImageIO.write(bufferImg,"jpg",byteArrayOut);
		        	patriarch.createPicture(anchor , sysEventWorkBook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
		        	
		        }
	        }
	        
	        //插入页脚图片
	        if(footFile != null) {
		        footerImg = ImageIO.read(footFile);
		        if("png".equalsIgnoreCase(footExtName)) {
		        	ImageIO.write(footerImg,"png",footerbyteArrayOut);
		        	patriarch.createPicture(footAnchor , sysEventWorkBook.addPicture(footerbyteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));
		        } else if("jpg".equalsIgnoreCase(footExtName)) {
		        	ImageIO.write(footerImg,"jpg",footerbyteArrayOut);
		        	patriarch.createPicture(footAnchor , sysEventWorkBook.addPicture(footerbyteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
		        }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    /**  
     * 创建表头  
     * @return  
     */  
    public void createTableHeader()   
    {   
    	demoSheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 8, (short) 11));
    	demoSheet.addMergedRegion(new CellRangeAddress(1, 1, (short) 8, (short) 11));
    	demoSheet.addMergedRegion(new CellRangeAddress(2, 2, (short) 8, (short) 11));
    	demoSheet.addMergedRegion(new CellRangeAddress(3, 3, (short) 0, (short) 11));
    	
    	demoSheet.setColumnWidth(3, 10000);
    	for(int i = 4; i < 9; i++) {
    		joinCell(i);
    	}
    	
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont font = sysEventWorkBook.createFont();
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	font.setFontHeightInPoints((short)16);
    	normalStyle.setFont(font);
    	
       /* HSSFHeader header = demoSheet.getHeader();   
        header.setCenter("报价单列表");*/
        
        
        HSSFRow askTitle = demoSheet.createRow((short)3);
        askTitle.setHeight((short)500);
        HSSFCell askTitleCell = askTitle.createCell(0);
        askTitleCell.setCellValue("报价单");
        askTitleCell.setCellStyle(normalStyle);
        
        HSSFCellStyle titleStyle = sysEventWorkBook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setBorderBottom((short)1);
        titleStyle.setBorderLeft((short)1);
        titleStyle.setBorderRight((short)1);
        titleStyle.setBorderTop((short)1);
    	HSSFFont titleFont = sysEventWorkBook.createFont();
    	titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	titleFont.setFontHeightInPoints((short)10);
    	titleStyle.setFont(titleFont);
        HSSFRow headerRow = demoSheet.createRow((short) 9);
        headerRow.setHeight((short)500);
        for(int i = 0; i < cellNumber; i++)   
        {   
            HSSFCell headerCell = headerRow.createCell(i);   
            //headerCell.setEncoding(HSSFCell.ENCODING_UTF_16);   
            headerCell.setCellValue(tableHeader[i]);   
            headerCell.setCellStyle(titleStyle);
        }   
    }
    
    /**
     * 单元格填充数据
     * @param row 行号
     * @param cellNameIndex 
     * @param cellValueIndex
     * @param cellName
     * @param value
     */
    private void fillDataToCell(HSSFRow row, int cellNameIndex, int cellValueIndex, String cellName, String value) {
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont valueFont = sysEventWorkBook.createFont();
    	valueFont.setFontHeightInPoints((short)10);
    	normalStyle.setFont(valueFont);
    	
    	row.setHeight((short)400);
    	HSSFCell cell = row.createCell(cellNameIndex);
    	cell.setCellValue(new HSSFRichTextString(cellName));
    	cell.setCellStyle(normalStyle);
    	HSSFCell cell2 = row.createCell(cellValueIndex);
    	cell2.setCellValue(value);
    	cell2.setCellStyle(normalStyle);
    }
    
    private void fillDataToCell(HSSFRow row, int cellNameIndex, int cellValueIndex, String cellName, double value) {
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont valueFont = sysEventWorkBook.createFont();
    	valueFont.setFontHeightInPoints((short)10);
    	normalStyle.setFont(valueFont);
    	
    	row.setHeight((short)400);
    	HSSFCell cell = row.createCell(cellNameIndex);
    	cell.setCellValue(new HSSFRichTextString(cellName));
    	cell.setCellStyle(normalStyle);
    	HSSFCell cell2 = row.createCell(cellValueIndex);
    	cell2.setCellValue(value);
    	cell2.setCellStyle(normalStyle);
    }
    
    public void creataeAskRow(QuotationDto quoPro) {

    	HSSFRow row2 = demoSheet.createRow(4);
    	fillDataToCell(row2, 0,2,"客户名称：", quoPro.getCustomerName());
    	fillDataToCell(row2, 6,8,"报价日期：", quoPro.getQuotationDate());
    	
    	HSSFRow row3 = demoSheet.createRow(5);
    	fillDataToCell(row3, 6,8,"报价单编号：", quoPro.getQuotationCode());
    	
    	HSSFRow row4 = demoSheet.createRow(6);
    	fillDataToCell(row4, 0,2,"对方联系人：", quoPro.getCusContactPerson());
    	fillDataToCell(row4, 6,8,"我方联系人：", quoPro.getUserName());
    	
    	HSSFRow row5 = demoSheet.createRow(7);
    	fillDataToCell(row5, 0,2,"电话号码：", quoPro.getCustomerPhone());
    	fillDataToCell(row5, 6,8,"制单人：", quoPro.getEditorName());
    	
    	HSSFRow row6 = demoSheet.createRow(8);
    	fillDataToCell(row6, 0,2,"传真号码：", quoPro.getCustomerFax());
    	fillDataToCell(row6, 6,8,"币别：", quoPro.getCurrencyName());
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, String Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellStyle(style);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, int Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellStyle(style);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, Double Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellStyle(style);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    /**  
     * 创建行  
     * @param cells  
     * @param rowIndex  
     */  
    public void createTableRow(QuotationDetailDto product,short rowIndex, HSSFCellStyle style)   
    {   
        //创建第rowIndex行   
        HSSFRow row = demoSheet.createRow((short) rowIndex);   
        row.setHeight((short)500);  
        	
        //创建第i个单元格   
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        setCellValue(row, 0, style, new Integer(product.getProjectCode()));
        setCellValue(row, 1, style, product.getSerialNumber());
        setCellValue(row, 2, style, product.getProductName());
        setCellValue(row, 3, style, product.getBrandCode());
        setCellValue(row, 4, style, product.getAmount().doubleValue());
        setCellValue(row, 5, style, product.getPrice().doubleValue());
        setCellValue(row, 6, style, product.getRebate().doubleValue());
        setCellValue(row, 7, style, product.getNetPrice().doubleValue());
        setCellValue(row, 8, style, product.getMoney().doubleValue());
        setCellValue(row, 9, style, product.getDeliveryDate());
        setCellValue(row, 10, style, product.getProductBrand());
        setCellValue(row, 11, style, product.getMemo());
          
    }
    
    public void createCountInfo(int rowIndex, QuotationDto quoPro) {
    	int step = rowIndex;
    	for(int i = rowIndex; i < rowIndex+4; i++) {
    		joinCountCell(i);
    	}
    	HSSFRow row1 = demoSheet.createRow((short) step); 
    	fillDataToCell(row1, 6,8,"货品金额：", quoPro.getProductMoney().doubleValue());
    	HSSFRow row2 = demoSheet.createRow((short) step+1); 
    	fillDataToCell(row2, 6,8,"税    率：", quoPro.getTaxRate().doubleValue());
    	HSSFRow row3 = demoSheet.createRow((short) step+2); 
    	fillDataToCell(row3, 6,8,"税    金：", quoPro.getTaxMoney().doubleValue());
    	HSSFRow row4 = demoSheet.createRow((short) step+3); 
    	fillDataToCell(row4, 6,8,"税价合计：", quoPro.getTotalMoney().doubleValue());
    }
    
    private void fillDataToHeader(TCompanyInfor comInfo) {
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont font = sysEventWorkBook.createFont();
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	normalStyle.setFont(font);
    	
    	HSSFRow unitRow = demoSheet.createRow((short) 0);
    	setCellValue(unitRow, 8, normalStyle, comInfo.getCompanyName());
    	
    	HSSFRow phoneRow = demoSheet.createRow((short) 1);
    	setCellValue(phoneRow, 8, normalStyle, "Tel : " + comInfo.getPhone());
    	
    	HSSFRow faxRow = demoSheet.createRow((short) 2);
    	setCellValue(faxRow, 8, normalStyle, "Fax : " + comInfo.getFax());
    }
    
    private void fillDataToFooter(TCompanyInfor comInfo, int footRow) {
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow, footRow, (short) 8, (short) 11));
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow+1, footRow+1, (short) 8, (short) 11));
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow+2, footRow+2, (short) 8, (short) 11));
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont font = sysEventWorkBook.createFont();
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	normalStyle.setFont(font);
    	
    	HSSFRow unitRow = demoSheet.createRow((short) footRow);
    	setCellValue(unitRow, 8, normalStyle, comInfo.getCompanyName());
    	
    	HSSFRow phoneRow = demoSheet.createRow((short) footRow+1);
    	setCellValue(phoneRow, 8, normalStyle, "Tel : " + comInfo.getPhone());
    	
    	HSSFRow faxRow = demoSheet.createRow((short) footRow+2);
    	setCellValue(faxRow, 8, normalStyle, "Fax : " + comInfo.getFax());
    }
    
    public void createMemoInfo(int rowIndex, HSSFCellStyle style, QuotationDto quoPro, String deliveryDate) {
    	TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoPro.getSellerName());
    	
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    	//normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont valueFont = sysEventWorkBook.createFont();
    	valueFont.setFontHeightInPoints((short)10);
    	normalStyle.setFont(valueFont);
    	int startIndex = rowIndex + 5;
    	
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex, startIndex, (short) 0, (short) 1));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex, startIndex, (short) 2, (short) 3));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+1, startIndex+1, (short) 0, (short) 1));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+1, startIndex+1, (short) 2, (short) 7));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+2, startIndex+2, (short) 2, (short) 5));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+3, startIndex+3, (short) 2, (short) 5));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+4, startIndex+4, (short) 2, (short) 5));
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+5, startIndex+5, (short) 2, (short) 5));
    	//demoSheet.addMergedRegion(new Region(startIndex+6, (short) 2, startIndex+6, (short) 3));
    	
    	demoSheet.addMergedRegion(new CellRangeAddress(startIndex+8, startIndex+8, (short) 2, (short) 3));
    	
    	if(comInfo != null) {
	    	insertImage(comInfo, startIndex+10);
	    	fillDataToHeader(comInfo);
	    	fillDataToFooter(comInfo, startIndex+10);
    	}
    	
    	HSSFRow validRow = demoSheet.createRow((short) startIndex);
    	validRow.setHeight((short)400);
    	setCellValue(validRow, 0, normalStyle, "报价有效期：");
    	
    	HSSFRow otherRow = demoSheet.createRow((short) startIndex+1);
    	otherRow.setHeight((short)400);
    	setCellValue(otherRow, 0, normalStyle, "其它事项：");
    	
    	otherRow.setHeight((short)400);
    	setCellValue(otherRow, 2, normalStyle, "1.附款条件：" + quoPro.getPaymentCondition());
    	
    	if(comInfo != null) {
	    	HSSFRow unitRow = demoSheet.createRow((short) startIndex+2);
	    	unitRow.setHeight((short)400);
	    	setCellValue(unitRow, 2, normalStyle, "单位名称：" + comInfo.getCompanyName());
	    	
	    	HSSFRow bankRow = demoSheet.createRow((short) startIndex+3);
	    	bankRow.setHeight((short)400);
	    	setCellValue(bankRow, 2, normalStyle, "开户行：" + comInfo.getBank());
	    	
	    	HSSFRow bankCodeRow = demoSheet.createRow((short) startIndex+4);
	    	bankCodeRow.setHeight((short)400);
	    	setCellValue(bankCodeRow, 2, normalStyle, "账   号：" + comInfo.getAccountNumber());
    	}
    	
    	HSSFRow deliveryRow = demoSheet.createRow((short) startIndex+5);
    	deliveryRow.setHeight((short)400);
    	setCellValue(deliveryRow, 2, normalStyle, "2.交货期：" + deliveryDate);
    	
    	HSSFRow row = demoSheet.createRow((short) startIndex+8);
    	row.setHeight((short)400);
    	setCellValue(row, 2, normalStyle, "买方签章__________");
    	
    }
       
    /**  
     * 创建整个Excel表  
     * @throws SQLException   
     *  
     */  
    public void createExcelSheeet(List<QuotationDetailDto> quoProList, QuotationDto quoPro)
    {   
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	normalStyle.setBorderBottom((short)1);
    	normalStyle.setBorderLeft((short)1);
    	normalStyle.setBorderRight((short)1);
    	normalStyle.setBorderTop((short)1);
    	HSSFFont valueFont = sysEventWorkBook.createFont();
    	valueFont.setFontHeightInPoints((short)10);
    	normalStyle.setFont(valueFont);
    	
        createTableHeader();   
        creataeAskRow(quoPro); 
        int rowIndex = 10;
        if(quoProList != null && quoProList.size() > 0) {
        	Iterator<QuotationDetailDto> iterator = quoProList.iterator();
	        while(iterator.hasNext()) {
	        	QuotationDetailDto quoProduct = iterator.next();
	        	createTableRow(quoProduct,(short)rowIndex,normalStyle);   
	        	rowIndex++;
	        }
        }
        
        String deliveryDate = "";
        if(quoProList != null && quoProList.size() > 0) {
	        DateComparator comp = new DateComparator();
	        Collections.sort(quoProList, comp);
	        deliveryDate = quoProList.get(0).getDeliveryDate();
	        createCountInfo(rowIndex, quoPro);
        }
        createMemoInfo(rowIndex, normalStyle, quoPro, deliveryDate);
    }   
    /**  
     * 导出表格  
     * @param sheet  
     * @param os  
     * @throws IOException  
     */  
    public void exportExcel(HSSFSheet sheet,OutputStream os) throws IOException   
    {   
        sheet.setGridsPrinted(true);   
        HSSFFooter footer = sheet.getFooter();   
        footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());   
        sysEventWorkBook.write(os);
        os.flush();
        os.close();
    }

	public TCompanyInforDAO getTcompanyInforDAO() {
		return tcompanyInforDAO;
	}

	public void setTcompanyInforDAO(TCompanyInforDAO tcompanyInforDAO) {
		this.tcompanyInforDAO = tcompanyInforDAO;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public TAccessoriesDAO getAccessoriesDao() {
		return accessoriesDao;
	}

	public void setAccessoriesDao(TAccessoriesDAO accessoriesDao) {
		this.accessoriesDao = accessoriesDao;
	}
}
