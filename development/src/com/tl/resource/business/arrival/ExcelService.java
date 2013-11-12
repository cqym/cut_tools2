package com.tl.resource.business.arrival;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

import com.tl.common.util.StringHelper;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TContractInfor;

public class ExcelService {
	private TCompanyInforDAO tcompanyInforDAO;
	private TAccessoriesDAO accessoriesDao;
	private TContractInforDAO contractInforDAO;
	
	private String logoPath;
	//表头   
    public static final String[] tableHeader = {"项目", "序号", "名称", "牌号", 
    	"订单数量","本次入库","累计入库","品牌","备注"};
    //创建工作本   
    public HSSFWorkbook sysEventWorkBook = new HSSFWorkbook();   
    //创建表   
    public HSSFSheet demoSheet = sysEventWorkBook.createSheet("入库单");   
    //表头的单元格个数目   
    public static final short cellNumber = (short)tableHeader.length;   
    //数据库表的列数   
    public static final int columNumber = 9;
    
    /**
     * 合并单元格
     * @param rowIndex 行号
     */
    private void joinCell(int rowIndex) {
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 0, (short) 1));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 2, (short) 3));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 4, (short) 5));
    	demoSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, (short) 6, (short) 8));
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
    	demoSheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 5, (short) 8));
    	demoSheet.addMergedRegion(new CellRangeAddress(1, 1, (short) 5, (short) 8));
    	demoSheet.addMergedRegion(new CellRangeAddress(2, 2, (short) 5, (short) 8));
    	demoSheet.addMergedRegion(new CellRangeAddress(3, 3, (short) 0, (short) 8));
    	
    	demoSheet.setColumnWidth(3, 10000);
    	for(int i = 4; i < 7; i++) {
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
    	
        HSSFHeader header = demoSheet.getHeader();   
        header.setCenter("入库单列表");
        
        
        HSSFRow askTitle = demoSheet.createRow((short)3);
        askTitle.setHeight((short)500);
        HSSFCell askTitleCell = askTitle.createCell(0);
        askTitleCell.setCellValue("入库单");
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
        HSSFRow headerRow = demoSheet.createRow((short) 7);
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
    
    public void creataeAskRow(ArrivalInforDto arrDto) {

    	HSSFRow row2 = demoSheet.createRow(4);
    	String arrType = "";
    	Integer orderType = arrDto.getOrderType();
    	if(orderType == 1 || orderType == 3) {
    		arrType = "合同";
    	} else if(orderType == 2 || orderType == 4) {
    		arrType = "储备";
    	}
    	fillDataToCell(row2, 0,2,"入库性质：", arrType);
    	fillDataToCell(row2, 4,6,"入库日期：", arrDto.getEditDateString());
    	
    	HSSFRow row3 = demoSheet.createRow(5);
    	if(orderType == 1 || orderType == 3 && !"".equals(arrDto.getContractCode())) {
    		fillDataToCell(row3, 0,2,"来源单号：", arrDto.getContractCode());
    	}
    	fillDataToCell(row3, 4,6,"入库单编号：", arrDto.getArrivalCode());
    	
    	HSSFRow row4 = demoSheet.createRow(6);
    	fillDataToCell(row4, 4,6,"制单人：", arrDto.getUserName());
    	
    }
    
    private static HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, String Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellStyle(style);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private static HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, int Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellStyle(style);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private static HSSFCell setCellValue(HSSFRow row, int cellIndex, HSSFCellStyle style, Double Value) {
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
    public void createTableRow(ProductArrivalDetailDto product,short rowIndex, HSSFCellStyle style)   
    {   
        //创建第rowIndex行   
        HSSFRow row = demoSheet.createRow((short) rowIndex);   
        row.setHeight((short)500);  
        	
        //创建第i个单元格   
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        setCellValue(row, 0, style, new Integer(product.getProjectCode()));
        setCellValue(row, 1, style, product.getSerialNumber().doubleValue());
        setCellValue(row, 2, style, product.getProductName());
        setCellValue(row, 3, style, product.getBrandCode());
        setCellValue(row, 4, style, product.getOrderAmount().doubleValue());
        setCellValue(row, 5, style, product.getArrivalAmount().doubleValue());
        setCellValue(row, 6, style, product.getHasArrivalAmount().doubleValue());
        setCellValue(row, 7, style, product.getProductBrand());
        setCellValue(row, 8, style, product.getMemo());
          
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
    	setCellValue(unitRow, 5, normalStyle, comInfo.getCompanyName());
    	
    	HSSFRow phoneRow = demoSheet.createRow((short) 1);
    	setCellValue(phoneRow, 5, normalStyle, "Tel : " + comInfo.getPhone());
    	
    	HSSFRow faxRow = demoSheet.createRow((short) 2);
    	setCellValue(faxRow, 5, normalStyle, "Fax : " + comInfo.getFax());
    }
    
    private void fillDataToFooter(TCompanyInfor comInfo, int footRow) {
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow, footRow, (short) 5, (short) 8));
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow+1, footRow+1, (short) 5, (short) 8));
    	demoSheet.addMergedRegion(new CellRangeAddress(footRow+2, footRow+2, (short) 5, (short) 8));
    	HSSFCellStyle normalStyle = sysEventWorkBook.createCellStyle();
    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    	normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	normalStyle.setWrapText(true);
    	HSSFFont font = sysEventWorkBook.createFont();
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	normalStyle.setFont(font);
    	
    	HSSFRow unitRow = demoSheet.createRow((short) footRow);
    	setCellValue(unitRow, 5, normalStyle, comInfo.getCompanyName());
    	
    	HSSFRow phoneRow = demoSheet.createRow((short) footRow+1);
    	setCellValue(phoneRow, 5, normalStyle, "Tel : " + comInfo.getPhone());
    	
    	HSSFRow faxRow = demoSheet.createRow((short) footRow+2);
    	setCellValue(faxRow, 5, normalStyle, "Fax : " + comInfo.getFax());
    }
       
    /**  
     * 创建整个Excel表  
     * @throws SQLException   
     *  
     */  
    public void createExcelSheeet(ArrivalInforDto arrDto, List<ProductArrivalDetailDto> list)
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
    	
    	TContractInfor contract = null;
    	TCompanyInfor comInfo = null;
    	String contractCode = arrDto.getContractCode();
    	
    	
        createTableHeader();   
        creataeAskRow(arrDto);
        int rowIndex = 8;
        if(list != null && list.size() > 0) {
        	Iterator<ProductArrivalDetailDto> iterator = list.iterator();
	        while(iterator.hasNext()) {
	        	ProductArrivalDetailDto product = iterator.next();
	        	createTableRow(product,(short)rowIndex,normalStyle);   
	        	rowIndex++;
	        }
        }
        
        if(contractCode != null && !"".equals(contractCode)) {
    		contract = this.getContractByCode(contractCode);
    		String companyName = contract.getSellerName();
    		comInfo = tcompanyInforDAO.getCompanyByName(companyName);
    		this.insertImage(comInfo, rowIndex+2);
        	this.fillDataToHeader(comInfo);
        	fillDataToFooter(comInfo, rowIndex+2);
    	}
        
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
	
	public TCompanyInfor getCompany(String name) {
		return null;
	}
	
	/**
	 * 根据订单编号获取合同信息
	 * @param orderCode 订单编号
	 * @return
	 */
	public TContractInfor getContractByCode(String contractCode) {
		TContractInfor contract = contractInforDAO.getContractByCode(contractCode);
		return contract;
	}

	public TContractInforDAO getContractInforDAO() {
		return contractInforDAO;
	}

	public void setContractInforDAO(TContractInforDAO contractInforDAO) {
		this.contractInforDAO = contractInforDAO;
	}
}
