package com.tl.resource.business.arrival;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.smartupload.Constant;
import com.tl.common.util.DateComparator;
import com.tl.common.util.StringHelper;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TContractInfor;

public class ArrivalToExcel {
	private TCompanyInforDAO tcompanyInforDAO;
	private TAccessoriesDAO accessoriesDao;
	private TContractInforDAO contractInforDAO;
	private String logoPath;
	public TCompanyInforDAO getTcompanyInforDAO() {
		return tcompanyInforDAO;
	}
	public void setTcompanyInforDAO(TCompanyInforDAO tcompanyInforDAO) {
		this.tcompanyInforDAO = tcompanyInforDAO;
	}
	public TAccessoriesDAO getAccessoriesDao() {
		return accessoriesDao;
	}
	public void setAccessoriesDao(TAccessoriesDAO accessoriesDao) {
		this.accessoriesDao = accessoriesDao;
	}
	public TContractInforDAO getContractInforDAO() {
		return contractInforDAO;
	}
	public void setContractInforDAO(TContractInforDAO contractInforDAO) {
		this.contractInforDAO = contractInforDAO;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	private HSSFWorkbook createWorkBook() {
		HSSFWorkbook wb = null;
		FileInputStream is;
		try {
			File file = new File(this.getLogoPath() + Constant.ARRIVAL_FILE);
			is = new FileInputStream(file);
			wb=new HSSFWorkbook(is);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
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
    
    private void insertImage(HSSFWorkbook sysEventWorkBook, HSSFSheet demoSheet, TCompanyInfor comInfo, int footRow) {
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
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0,30,131,36,(short) 0,0,(short) 8,1);
	        HSSFClientAnchor footAnchor = new HSSFClientAnchor(0,100,131,36,(short) 0,footRow,(short) 8,footRow+2);
	        
	        //插入页眉图片
	        if(file != null && file.canRead()) {
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
    
    private void fillDataToCell(HSSFRow row, String value0, String value1) {
    	HSSFCell cell = row.getCell(2);
    	cell.setCellValue(value0);
    	HSSFCell cell2 = row.getCell(5);
    	cell2.setCellValue(value1);
    }
    
    private void fillDataToCell(HSSFRow row, int cellIndex, String value) {
    	HSSFCell cell = row.getCell(cellIndex);
    	cell.setCellValue(value);
    }
    
    private void fillDataToCell(HSSFRow row, Double value) {
    	HSSFCell cell = row.getCell(5);
    	if(cell != null) {
    		cell.setCellValue(value);
    	}
    }
    
    public void fillDataToArrival(HSSFSheet demoSheet, ArrivalInforDto arrDto) {

    	HSSFRow row2 = demoSheet.getRow(2);
    	String arrType = "";
    	Integer orderType = arrDto.getOrderType();
    	if(orderType != null) {
	    	if(orderType == 1 || orderType == 3) {
	    		arrType = "合同";
	    	} else if(orderType == 2 || orderType == 4) {
	    		arrType = "储备";
	    	}
    	} else {
    		arrType = "直接";
    	}
    	fillDataToCell(row2, arrDto.getArrivalCode(), arrDto.getOrderCode());
    	
    	HSSFRow row3 = demoSheet.getRow(3);
    	
    	fillDataToCell(row3, arrDto.getDeliveryDate(), arrDto.getSupplierName());
    	
    	HSSFRow row4 = demoSheet.getRow(4);
    	fillDataToCell(row4, 2, arrType);
    	
    	if(orderType != null) {
	    	if(orderType == 1 || orderType == 3 && !"".equals(arrDto.getContractCode())) {
				fillDataToCell(row4, 5, arrDto.getContractCode());
			}
    	}
    	
    	HSSFRow row5 = demoSheet.getRow(5);
    	fillDataToCell(row5, arrDto.getUserName(), arrDto.getCustomerName());
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, String Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, int Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, Double Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    /**  
     * 创建行  
     * @param cells  
     * @param rowIndex  
     */  
    public void createTableRow(ProductArrivalDetailDto product,HSSFRow row)   
    {   
    	if(product.getProjectCode() != null)
    		setCellValue(row, 0, new Integer(product.getProjectCode()));
    	else
    		setCellValue(row, 0, "");
        setCellValue(row, 1, product.getSerialNumber().doubleValue());
        setCellValue(row, 2, product.getProductName());
        setCellValue(row, 3, product.getBrandCode());
        setCellValue(row, 4, product.getOrderAmount().doubleValue());
        setCellValue(row, 5, product.getArrivalAmount().doubleValue());
        if(product.getHasArrivalAmount() != null)
        	setCellValue(row, 6, product.getHasArrivalAmount().intValue());
        else 
        	setCellValue(row, 6, "");
        setCellValue(row, 7, product.getProductBrand());
        setCellValue(row, 8, product.getMemo());
          
    }
    
    public void insertRow(List<ProductArrivalDetailDto> quoProList, HSSFWorkbook wb, HSSFSheet sheet, int startRow) {   
    	int rows = quoProList.size();
        sheet.shiftRows(startRow + 1, sheet.getLastRowNum(), rows,true,true);    

        for (int i = 0; i < rows; i++) {   
               
              HSSFRow sourceRow = null;   
              HSSFRow targetRow = null;  
              HSSFCell sourceCell = null;
              HSSFCell targetCell = null;
              short m;
                 
              sourceRow = sheet.getRow(startRow); 
              targetRow = sheet.createRow(++startRow); 
              ///targetRow.setHeightInPoints(sourceRow.getHeightInPoints());
              
              for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum()-1; m++) {
                  sourceCell = sourceRow.getCell(m);
                  targetCell = targetRow.createCell(m);
                  //targetCell.setEncoding(sourceCell.getEncoding());
                  if(sourceCell != null) {
                	  HSSFCellStyle sytle = sourceCell.getCellStyle();
                	  HSSFFont font = wb.createFont();
                	  font.setFontHeightInPoints((short)10);
                	  sytle.setFont(font);
	                  targetCell.setCellStyle(sytle);
	                  targetCell.setCellType(sourceCell.getCellType());
	                  targetCell.setCellValue(i);
                  }
               }
              
              //ExcelUtil.copyRow(sourceRow, targetRow); 
              ProductArrivalDetailDto quoProduct = quoProList.get(i);
	  	      createTableRow(quoProduct, targetRow);
              
        }   
           
    } 
    
    
    public void exportExcel(OutputStream os, List<ProductArrivalDetailDto> quoProList, ArrivalInforDto arrDto) throws IOException {
		HSSFWorkbook wb = this.createWorkBook();
		int sheetNum=wb.getNumberOfSheets();
		TContractInfor contract = null;
    	TCompanyInfor comInfo = null;
		String contractCode = arrDto.getContractCode();
		
		
		
		for(int i=0;i<sheetNum;i++)   
        {   
            HSSFSheet childSheet = wb.getSheetAt(i);  
            fillDataToArrival(childSheet, arrDto);
            if(quoProList != null && quoProList.size() > 0)
            	insertRow(quoProList, wb, childSheet, 6);
            if(contractCode != null && !"".equals(contractCode)) {
        		contract = this.getContractByCode(contractCode);
        		if(contract != null) {
	        		String companyName = contract.getSellerName();
	        		comInfo = tcompanyInforDAO.getCompanyByName(companyName);
	        		this.insertImage(wb, childSheet, comInfo, quoProList.size()+8);
        		}
        	}
        }
		wb.write(os);
        os.flush();
        os.close();
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
}
