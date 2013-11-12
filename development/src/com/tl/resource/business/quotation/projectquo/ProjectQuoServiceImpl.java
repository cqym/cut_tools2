package com.tl.resource.business.quotation.projectquo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.util.ExcelUtil;
import com.tl.common.util.StringHelper;
import com.tl.resource.business.dto.AccessoriesDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.dao.TAccessoriesDAO;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.TQuotationProjectSortInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProductDetailExample;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

public class ProjectQuoServiceImpl implements ProjectQuoService {

	//报价单主表
	private TQuotationInforDAO quoInfoDAO;
	//工序Dao
	private TQuotationProjectSortInforDAO quoProInfoDAO;
	//报价单对应的产品信息
	private TQuotationProductDetailDAO quoProdDetDAO ;
	
	private TCompanyInforDAO tcompanyInforDAO;
	private TAccessoriesDAO accessoriesDao;
	private TCustomersInforDAO customersInforDAO;
	
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

	public TQuotationProductDetailDAO getQuoProdDetDAO() {
		return quoProdDetDAO;
	}

	public void setQuoProdDetDAO(TQuotationProductDetailDAO quoProdDetDAO) {
		this.quoProdDetDAO = quoProdDetDAO;
	}

	public TQuotationProjectSortInforDAO getQuoProInfoDAO() {
		return quoProInfoDAO;
	}

	public void setQuoProInfoDAO(TQuotationProjectSortInforDAO quoProInfoDAO) {
		this.quoProInfoDAO = quoProInfoDAO;
	}

	@Override
	public List<QuotationDto> getQuotationByPage(Map<String, Object> parmMap) {
		return quoInfoDAO.getQuotationByPage(parmMap);
	}
	
	public TQuotationInforDAO getQuoInfoDAO() {
		return quoInfoDAO;
	}
	public void setQuoInfoDAO(TQuotationInforDAO quoInfoDAO) {
		this.quoInfoDAO = quoInfoDAO;
	}

	@Override
	public List<TCustomersInfor> getCustomerInfoByBage(
			Map<String, Object> parmMap) {
		return quoInfoDAO.getCustomerInfoByBage(parmMap);
	}

	@Override
	public TQuotationInfor insertQuotation(TQuotationInfor quotationInfor) {
		return quoInfoDAO.insertQuotation(quotationInfor);
	}

	@Override
	public int getQuotaionTotal(Map<String, Object> parmMap) {
		return quoInfoDAO.getQuotaionTotal(parmMap);
	}

	@Override
	public List<TQuotationProjectSortInfor> getWorkOrderList(Map<String, Object> parmMap) {
		
		return quoProInfoDAO.getWorkOrderList(parmMap);
	}

	@Override
	public void insertQuoDetail(TQuotationProductDetail po2) {
		quoProdDetDAO.insert(po2);
		
	}

	@Override
	public void insertQuoProSort(TQuotationProjectSortInfor tquoproinfo) {
		quoProInfoDAO.insert(tquoproinfo);
	}

	@Override
	public List<QuotationDetailDto> getQuoDetailByWorkOrder(
			Map<String, Object> parmMap) {
		
		return quoProdDetDAO.getQuoDetailList(parmMap);
	}

	@Override
	public TQuotationProductDetail getQuoDetailById(String id) {
		return quoProdDetDAO.selectByPrimaryKey(id);
	}

	@Override
	public TQuotationInfor getQuoInfoByID(String id) {
		
		return quoInfoDAO.selectByPrimaryKey(id);
	}

	@Override
	public TQuotationProjectSortInfor getQuoProSortInfoByID(String workorderid) {
		return quoProInfoDAO.selectByPrimaryKey(workorderid);
	}

	@Override
	public void updateQuoDetail(TQuotationProductDetail po2) {
		
		quoProdDetDAO.updateByPrimaryKeySelective(po2);
	}

	@Override
	public void updateQuoInfo(TQuotationInfor quoInfo) {
		//quoInfoDAO.updateByPrimaryKey(quoInfo);
		quoInfoDAO.updateByPrimaryKeySelective(quoInfo);
	}

	@Override
	public void updateQuoProSortInfo(TQuotationProjectSortInfor tquoproinfo) {
		
		quoProInfoDAO.updateByPrimaryKeySelective(tquoproinfo);
	}

	@Override
	public void deleteQuoInfoById(String quoId) {
		String impCode = this.getQuoInfoByID(quoId).getImpToQuoCode();
		//删除产品详细信息
		quoProdDetDAO.deleteProByQuoId(quoId);
		//删除工序
		quoProInfoDAO.deleteWorkOrderByQuoId(quoId);
		
		quoInfoDAO.deleteByPrimaryKey(quoId);
		
		if(impCode != null && !"".equals(impCode)) {
			String[] ids = impCode.split(",");
			for(int i = 0; i < ids.length; i++) {
				//正式报价单
				String exportCode = this.getQuoInfoByID(ids[i]).getImpToQuoCode();
				String newExportCode = this.buildExportCode(exportCode, quoId);
				TQuotationInfor resQuoInfo = new TQuotationInfor();
				resQuoInfo.setId(ids[i]);
				resQuoInfo.setImpToQuoCode(newExportCode);
				resQuoInfo.setStatus(6);
				quoInfoDAO.updateImpQuoCode(resQuoInfo);
				this.updateQuoStatus(resQuoInfo);
			}
		}
	}
	
	private String buildExportCode(String exportCode, String quoId) {
		List<String> codeList = new ArrayList<String>();
		if(exportCode != null && !"".equals(exportCode)) {
			String[] codeArray = exportCode.split(",");
			for(String code : codeArray) {
				codeList.add(code);
			}
		}
		
		String newImpCode = "";
		
		if(codeList.contains(quoId)) {
			codeList.remove(quoId);
			for(int i = 0; i < codeList.size(); i++) {
				if("".equals(newImpCode)) {
					newImpCode = codeList.get(i);
				} else {
					newImpCode += "," + codeList.get(i);
				}
			}
		}
		
		return newImpCode;
	}

	@Override
	public void deleteWorkOrderById(String workOrderId) {
		//删除产品详细信息
		quoProdDetDAO.deleteProByWorkId(workOrderId);
		//删除工序
		quoProInfoDAO.deleteByPrimaryKey(workOrderId); 
		
	}

	@Override
	public String cancelAudit(String id) {
		TQuotationInfor quoInfo = new TQuotationInfor();
		quoInfo.setId(id);
		quoInfo.setStatus(3);
		quoInfoDAO.updateStatus(id, 3);
		return null;
	}

	@Override
	public String endAudit(String id) {
		/**
		 * 未实现
		 */
		TQuotationInfor quoInfo = new TQuotationInfor();
		quoInfo.setId(id);
		quoInfo.setStatus(2);
		quoInfoDAO.updateStatus(id, 2);
		return null;
	}

	@Override
	public String submitAudit(String id) {
		
		TQuotationInfor quoInfo = new TQuotationInfor();
		quoInfo.setId(id);
		quoInfo.setStatus(1);
		quoInfoDAO.updateStatus(id, 1);
		return null;
		
	}

	@Override
	public void deleteProductById(String productId) {
		QuotationDetailDto dto = quoProdDetDAO.getQuoDetailById(productId);
		//QuotationDetailDto dto = generalQuoService.getQuoDetailById(productId);
		if(dto != null)
			deleteQuoPro(dto);
		
	}
	
	private void deleteQuoPro(QuotationDetailDto dto) {
		/*List<QuotationDetailDto> children = dto.getChildren();
		if(children != null) {
			for(QuotationDetailDto dto2 : children) {
				dto2 = quoProdDetDAO.getQuoDetailById(dto2.getId());
				List<QuotationDetailDto> list = dto2.getChildren();
				
				if(list != null && list.size() > 0) {
					deleteQuoPro(dto2);
				}
				quoProdDetDAO.deleteQuoDetail(dto2.getId());
			}
		}*/
		quoProdDetDAO.deleteQuoDetail(dto.getId());
	}

	@SuppressWarnings("static-access")
	@Override
	public void exportExcel(TQuotationInfor quoDto,OutputStream os,String realPath) {
		ExportExcelService ees = new ExportExcelService();
		try {
			String filepath = realPath + "\\upload\\templete\\项目报价单.xls";
			FileInputStream fileinput = new FileInputStream(filepath);
			// 创建工作薄
			HSSFWorkbook workbook = new HSSFWorkbook(fileinput);
			//fileinput.close();
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("quotation_infor_id",quoDto.getId());
			//查找工序
			List<TQuotationProjectSortInfor> workOrderList = this.getWorkOrderList(parmMap);
			
			
			//工作薄中所有sheet的名称
			String[] sheetNames = new String[workOrderList.size()+1];
			int i = 0;
			sheetNames[i++] = quoDto.getQuotationCode() + "项目报价单";
			for(TQuotationProjectSortInfor obj : workOrderList){
				sheetNames[i++] =  obj.getProSortName();
			}
			//创建设置sheet名称
			ees.createSheet(workbook, sheetNames);
		
			TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoDto.getSellerName());
		
			/**
			 * 设置主表表头
			 */
			HSSFSheet sheet0 = (HSSFSheet) workbook.getSheetAt(0);
			
					
			
			HSSFCellStyle normalStyle = ees.createCellStyle(workbook,1);
			sheet0.getRow(1).getCell(0).setCellStyle(normalStyle);
			sheet0.getRow(1).getCell(0).setCellValue(sheetNames[0]);
			
			
			
			
			int b = 1;
			int activerownumber = 4 ;
			int startRow = 4;
			int rows = workOrderList.size() ;
			sheet0.shiftRows(startRow+1, sheet0.getLastRowNum(), workOrderList.size(),true,true); 
			
			 for (int a = 0; a < rows; a++) {   
	               
	              HSSFRow sourceRow = null;   
	              HSSFRow targetRow = null;  
	              HSSFCell sourceCell = null;
	              HSSFCell targetCell = null;
	              short m;
	                 
	              sourceRow = sheet0.getRow(startRow);   
	              targetRow = sheet0.createRow(++startRow); 
	              //targetRow.setRowStyle(sourceRow.getRowStyle());
	              
	              for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum()-1; m++) {
	                  sourceCell = sourceRow.getCell(m);
	                  targetCell = targetRow.createCell(m);
	          
	                  //targetCell.setEncoding(sourceCell.getEncoding());
	                  if(sourceCell != null) {
		                  targetCell.setCellStyle(sourceCell.getCellStyle());
		                  targetCell.setCellType(sourceCell.getCellType());
		                  targetCell.setCellValue(a);
	                  }
	               }	             
	        } 
			
			
			
			
			HSSFCellStyle linknormalStyle = ees.createCellStyle(workbook,5);
			normalStyle = ees.createCellStyle(workbook, 4);
			
			for(int z = 0 ; z < workOrderList.size() ; z++ ){
				TQuotationProjectSortInfor obj = workOrderList.get(z);
				HSSFRow row = sheet0.getRow(activerownumber);
				row.setHeight((short)500);
				row.getCell(0).setCellValue(b++);
				row.getCell(1).setCellValue(obj.getProSortName());
				//row.getCell(7).setCellValue("");
				row.getCell(2).setCellFormula("HYPERLINK(\"#"+obj.getProSortName()+"!A1\",\"详见清单\")");
				row.getCell(3).setCellValue(obj.getMachineModel());
				row.getCell(4).setCellValue(obj.getMachineCount());
				row.getCell(5).setCellValue(obj.getSupportAmount().doubleValue());
				row.getCell(6).setCellValue(obj.getBackupAmount().doubleValue());
				
				double ss = obj.getSupportAmount().doubleValue() + obj.getBackupAmount().doubleValue();
				row.getCell(7).setCellValue(obj.getTotalMoney().doubleValue()/ss);
				
				row.getCell(8).setCellValue(obj.getTotalMoney().doubleValue());
				row.getCell(9).setCellValue("");
				row.getCell(10).setCellValue("");
				
				row.getCell(0).setCellStyle(normalStyle);
				row.getCell(1).setCellStyle(normalStyle);
				row.getCell(2).setCellStyle(linknormalStyle);
				row.getCell(3).setCellStyle(normalStyle);
				row.getCell(4).setCellStyle(normalStyle);
				row.getCell(5).setCellStyle(normalStyle);
				row.getCell(6).setCellStyle(normalStyle);
				row.getCell(7).setCellStyle(normalStyle);
				row.getCell(8).setCellStyle(normalStyle);
				row.getCell(9).setCellStyle(normalStyle);
				row.getCell(10).setCellStyle(normalStyle);
				
				HSSFRow trow = sheet0.createRow(++activerownumber);
				if(z != (workOrderList.size() - 1)){
					ExcelUtil.copyRow(row,trow);
				}
				
			}
			
			
			//合计部分开始行数
			int activeRow  =  4 + workOrderList.size() + 1;
			
			normalStyle = ees.createCellStyle(workbook,4);
			//合计行
			sheet0.getRow(activeRow).getCell(8).setCellStyle(normalStyle);
			sheet0.getRow(activeRow++).getCell(8).setCellValue(quoDto.getProductMoney().doubleValue());
			
			//税率行
			sheet0.getRow(activeRow).getCell(8).setCellStyle(normalStyle);
			sheet0.getRow(activeRow++).getCell(8).setCellValue(quoDto.getTaxRate().doubleValue()*100+ "%");
			
			//税   金：
			sheet0.getRow(activeRow).getCell(8).setCellStyle(normalStyle);
			sheet0.getRow(activeRow++).getCell(8).setCellValue(quoDto.getTaxMoney().doubleValue());
			
			//含税总价：
			sheet0.getRow(activeRow).getCell(8).setCellStyle(normalStyle);
			sheet0.getRow(activeRow++).getCell(8).setCellValue(quoDto.getTotalMoney().doubleValue());
			activeRow++;//间隔
			
			this.insertImage(workbook,sheet0,comInfo,realPath);
			
			/**
			 * 导出工序对应Excel
			 */
			for(int z = 0;z < workOrderList.size() ; z++){
				int y = z + 1;
				exportWorkOrderExcel(workbook,workbook.getSheetAt(y),workOrderList.get(z),quoDto.getTaxRate().doubleValue());
				this.insertImage(workbook,workbook.getSheetAt(y),comInfo,realPath);
			} 
			
			
			workbook.write(os);
			os.close();
			
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	

	private void insertImage(HSSFWorkbook sysEventWorkBook, HSSFSheet demoSheet, TCompanyInfor comInfo,  String realPath) {
    	
    	
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
	        String headerFilePath = new StringBuffer(realPath).append(headerPath).toString();
	        
	    	file = new java.io.File(headerFilePath);
        }
        //页脚图片logo
        AccessoriesDto footerSlave = this.getAccDto(comInfo.getId(), 4);
        if(footerSlave != null) {
	        String footerPath = footerSlave.getPath();
	        footerPath = footerPath.replaceAll("\\/", "//");
	        footExtName = StringHelper.getExtName(footerPath, '.');
	        String footerFilePath = new StringBuffer(realPath).append(footerPath).toString();
	        
	    	footFile = new java.io.File(footerFilePath);
        }
        try {
        	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
        	ByteArrayOutputStream footerbyteArrayOut = new ByteArrayOutputStream(); 
			
	        HSSFPatriarch patriarch = demoSheet.createDrawingPatriarch(); 
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0,30,131,36,(short) 0,0,(short) 11,1);
	        HSSFClientAnchor footAnchor = new HSSFClientAnchor(0,100,131,36,(short) 0,demoSheet.getPhysicalNumberOfRows()-2,(short) 11,demoSheet.getPhysicalNumberOfRows());
	        
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
	        if(footFile != null && footFile.canRead()) {
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
	
 	private boolean hasMemo2(List<QuotationDetailDto> quoProList) {
    	boolean flag = false;
    	for(QuotationDetailDto dto : quoProList) {
    		if(!"".equals(dto.getWorkshop()) && null != dto.getWorkshop()) {
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    }
    
    private boolean hasMemo3(List<QuotationDetailDto> quoProList) {
    	boolean flag = false;
    	for(QuotationDetailDto dto : quoProList) {
    		if(!"".equals(dto.getProcessCode()) && null != dto.getProcessCode()) {
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    }
    
    private void createMemoCell(HSSFWorkbook wb, HSSFSheet sheet, int cellIndex, String memo) {
		HSSFRow row2 = sheet.getRow(2);
		HSSFCell cell13 = row2.getCell(13);

		HSSFCellStyle sytle = cell13.getCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		sytle.setFont(font);

		HSSFCell cell = row2.createCell(cellIndex);
		cell.setCellStyle(sytle);
		cell.setCellValue(memo);
	}
    
    private void createMemoLine(HSSFSheet sheet, int rowIndex, int cellInex) {
    	HSSFRow rowLine = sheet.getRow(rowIndex) == null ? sheet.createRow(rowIndex) : sheet.getRow(rowIndex); 
		HSSFCell cell11 = rowLine.getCell(11);

		HSSFCellStyle sytle = cell11.getCellStyle();

		HSSFCell cell = rowLine.createCell(cellInex);
		cell.setCellStyle(sytle);
    }
	 
	/**
	 * 导出工序对应的Excel
	 * @param workbook
	 * @param sheet0
	 * @param qps
	 */
	private void exportWorkOrderExcel(HSSFWorkbook workbook,HSSFSheet sheet,TQuotationProjectSortInfor qps,double taxRate){
		//导出数据工具类
		ExportExcelService eestools = new ExportExcelService();
		
		List<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();
		Map<String, Object> parmMap = new HashMap<String, Object>();
		String workOrderId = qps.getId();
		if(workOrderId != null && !"".equals(workOrderId)) {
			parmMap.put("quotation_project_sort_id", workOrderId);
			list = this.getQuoDetailByWorkOrder(parmMap);
		}
		
		/**
		 * 表头名称
		 */
		HSSFCellStyle normalStyle = eestools.createCellStyle(workbook,1);
		sheet.getRow(1).getCell(0).setCellStyle(normalStyle);
		sheet.getRow(1).getCell(0).setCellValue(qps.getProSortName());
		
		
		/***
		 * 移动模板数据
		 */
		int startRow = 2;
		int rows = list.size() ;
		sheet.shiftRows(startRow+1, sheet.getLastRowNum(), list.size(),true,true); 
		
		 for (int a = 0; a < rows; a++) {   
               
              HSSFRow sourceRow = null;   
              HSSFRow targetRow = null;  
              HSSFCell sourceCell = null;
              HSSFCell targetCell = null;
              short m;
                 
              sourceRow = sheet.getRow(startRow);   
              targetRow = sheet.createRow(++startRow); 
             
              for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {
                  sourceCell = sourceRow.getCell(m);
                  targetCell = targetRow.createCell(m);
                  if(sourceCell != null) {
	                  targetCell.setCellStyle(sourceCell.getCellStyle());
	                  targetCell.setCellType(sourceCell.getCellType());
	                  targetCell.setCellValue(a);
                  }
               }	             
        } 
		 
		boolean hasMemo2 = this.hasMemo2(list);
	    boolean hasMemo3 = this.hasMemo3(list);
		if(hasMemo2) {
        	this.createMemoCell(workbook, sheet, 14, "备注2");
		  	// this.createMemoLine(sheet, 7 + rows, 12); 
        }
        if(hasMemo3) {
        	this.createMemoCell(workbook, sheet, 15, "备注3");
		  	//this.createMemoLine(sheet, 7 + rows, 13);
        }  
		 
		 /**
		  * 添加数据
		  */
		normalStyle = eestools.createCellStyle(workbook, 3);
		int activerownumber = 3 ;	
		 for(QuotationDetailDto obj : list){
		    HSSFRow row = sheet.getRow(activerownumber);
		    row.setHeight((short)500);
		    row.getCell(0).setCellValue(obj.getProjectCode());
		    row.getCell(1).setCellValue(obj.getSerialNumber());
		    row.getCell(2).setCellValue(obj.getProductName());
		    row.getCell(3).setCellValue(obj.getToolDescription());
		    row.getCell(4).setCellValue(obj.getBrandCode());
		    row.getCell(5).setCellValue(obj.getProductUnit());
		    row.getCell(6).setCellValue(obj.getSingleSetAssemblyAmount().doubleValue());
		    row.getCell(7).setCellValue(obj.getSingleSetStockAmount().doubleValue());
		    row.getCell(8).setCellValue(obj.getPrice().doubleValue());
		    row.getCell(9).setCellValue(obj.getMoney().doubleValue());
		    row.getCell(10).setCellValue(obj.getToolCode());
		    row.getCell(11).setCellValue(obj.getProductBrand());
		    row.getCell(12).setCellValue(obj.getDeliveryDate());
		    row.getCell(13).setCellValue(obj.getMemo());
		    
		    if(hasMemo2) {
		    	HSSFCell cell14 = row.createCell(14);
		    	cell14.setCellValue(obj.getWorkshop());
		    }
		   if(hasMemo3) {
			   HSSFCell cell15 = row.createCell(15);
		    	cell15.setCellValue(obj.getProcessCode());
		   }
			
		    for(int w = 0 ; w < 16 ; w++){
				sheet.getRow(activerownumber).getCell(w).setCellStyle(normalStyle);
			}
			activerownumber++ ;
		}
	 
		//合计部分开始行数
		int activeRow  =  4 + list.size();
		
		normalStyle = eestools.createCellStyle(workbook,4);
		//合计行
		sheet.getRow(activeRow).getCell(9).setCellStyle(normalStyle);
		sheet.getRow(activeRow++).getCell(9).setCellValue(qps.getTotalMoney().doubleValue());
		
		//税率行
		sheet.getRow(activeRow).getCell(9).setCellStyle(normalStyle);
		sheet.getRow(activeRow++).getCell(9).setCellValue(taxRate*100 + "%");
		
		/**
		 * 计算税金,保留两位小数
		 */
		 BigDecimal bd = qps.getTotalMoney().multiply(new BigDecimal(Double.toString(taxRate)));
	     double d = round(bd.doubleValue(), 2);   
	     bd = null;   
	       
		
		//税金行
		sheet.getRow(activeRow).getCell(9).setCellStyle(normalStyle);
		sheet.getRow(activeRow++).getCell(9).setCellValue(d);
		
		
		//含税金额行
		sheet.getRow(activeRow).getCell(9).setCellStyle(normalStyle);
		sheet.getRow(activeRow++).getCell(9).setCellValue(d + qps.getTotalMoney().doubleValue());
		
		
		
		
		
	}
	
	 public double round(double d, int len) { // 进行四舍五入

		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，

		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	} 
	
	/**
	 * 公司logo信息
	 * 
	 * @param companeyId
	 * @return
	 */
	private String getCompanyLogoFile(String companeyId){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("busId", companeyId);
        List<AccessoriesDto> list = accessoriesDao.getAccessoriesByBusId(paramMap);
        String slavePath = "";
        if(list != null && list.size() > 0) {
        	AccessoriesDto slave = list.get(0);
        	slavePath = slave.getPath();
        	slavePath = slavePath.replaceAll("\\/", "//");
        }
        String filePath = new StringBuffer().append(slavePath).toString();
        return filePath;
	}

	@Override
	public boolean validator(String quoId) {
		boolean flag = false;
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("quotation_infor_id",quoId);
		
		List<TQuotationProjectSortInfor> list = this.getWorkOrderList(parmMap);
		for(TQuotationProjectSortInfor dto : list) {
			String id = dto.getId();
			Map<String, Object> idMap = new HashMap<String, Object>();
			idMap.put("quotation_project_sort_id", id);
			List<QuotationDetailDto> detailList = this.getQuoDetailByWorkOrder(idMap);
			if(this.validator(detailList)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	private boolean validator(List<QuotationDetailDto> detailList) {
		boolean flag = false;
		for(QuotationDetailDto dto : detailList) {
			if(!dto.isLeaf()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public void updateQuoStatus(TQuotationInfor quoInfo) {
		quoInfoDAO.updateStatus(quoInfo.getId(), quoInfo.getStatus());
	}

	@Override
	public List<QuotationDetailDto> getProductList4Copy(
			Map<String, Object> parmMap) {
		return quoProdDetDAO.getProductList4Copy(parmMap);
	}

	@Override
	public boolean isPriceChange(String id) {
		TQuotationProductDetailExample example = new TQuotationProductDetailExample();
		example.createCriteria().andPriceChangeNotEqualTo(0).andQuotationInforIdEqualTo(id);
		int priceChangeCount = quoProdDetDAO.countByExample(example);
		return priceChangeCount > 0;
	}

	@Override
	public boolean isClosingAccountModeChange(String id) {
		TQuotationInfor quo = quoInfoDAO.selectByPrimaryKey(id);
		TCustomersInforExample example = new TCustomersInforExample();
		example.createCriteria().andCustomerCodeEqualTo(quo.getCustomerCode());
		List<TCustomersInfor> list = customersInforDAO.selectByExample(example);
		if(list != null && list.size() > 0){
			String cam = list.get(0).getClosingAccountMode();
			cam = (cam == null ? "" : cam);
			return !cam.equals(quo.getPaymentCondition());
		}
		return true;
	}

	public TCustomersInforDAO getCustomersInforDAO() {
		return customersInforDAO;
	}

	public void setCustomersInforDAO(TCustomersInforDAO customersInforDAO) {
		this.customersInforDAO = customersInforDAO;
	}

	@Override
	public int deleteQuoDetail(JSONArray ids) {
		Iterator<JSONObject> iterator = ids.iterator();
		while(iterator.hasNext()) {
			JSONObject josnObj = iterator.next();
			String projectQuoId = josnObj.getString("id");
			int index = projectQuoId.indexOf("#");
			if(index != -1) {
				//项目报价单
				QuotationDetailDto proQuo = this.quoProdDetDAO.getQuoDetailById(projectQuoId);
				//删除项目报价单产品
				this.deleteProductById(projectQuoId);
				
				//截取#号后得到预订报价单产品ID，然后根据ID得到预订报价单产品，然后得到预订报价单ID
				String id = projectQuoId.substring(0, index);
				QuotationDetailDto detailDto = this.quoProdDetDAO.getQuoDetailById(id);
				if(detailDto != null) {
					//普通报价单ID
					String generalQuoId = proQuo.getQuotationInforId();
					//预订报价单ID
					String quoId = detailDto.getQuotationInforId();
					Map<String, Object> parmMap = new HashMap<String, Object>();
					parmMap.put("generalQuoID", generalQuoId);
					parmMap.put("quoID", quoId);
					
					int updatRresult = quoInfoDAO.updateImpQuoCode(parmMap);
					quoInfoDAO.updateStatus(quoId, 6);
					if(updatRresult > 0) {
						quoInfoDAO.updateExportQuoCode(parmMap);
					}
				}
			} else {
				this.deleteProductById(projectQuoId);
			}
		}
		return 0;
	}
}
