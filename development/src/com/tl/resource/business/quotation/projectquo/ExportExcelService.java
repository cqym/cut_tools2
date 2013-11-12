/**
 * 
 */
package com.tl.resource.business.quotation.projectquo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.tl.common.util.StringHelper;
import com.tl.resource.dao.pojo.TCompanyInfor;

/**
 * @author xtaia
 * 导出excel工具类
 */
public class ExportExcelService {
	
	 
	/**
	 * 创建工作薄
	 * @return
	 */
	 public HSSFWorkbook createWorkbook(){
		 HSSFWorkbook sysEventWorkBook = new HSSFWorkbook();
		 return sysEventWorkBook;
	 }
	 /**
	  * 创建表
	  * 根据表名数组长度确定创建sheet数目
	  * @param workbook 
	  * @param sheetname
	  */
	 public static void createSheet(Workbook workbook,String[] sheetnames){
		 //主sheet名
		 workbook.setSheetName(0, sheetnames[0]);
		 workbook.setSheetName(1, sheetnames[1]);
		 
		 for(int i = 2 ; i < sheetnames.length ; i++){
			 
			 //多个工序复制模板工序
			 org.apache.poi.ss.usermodel.Sheet  clonesheet =  workbook.cloneSheet(1);
			 workbook.setSheetName(i, sheetnames[i]);
			
		 }
	 }

	 /**
	  * 根据传递的sheet及行列数，创建表格
	  * @param sheet
	  * @param rows
	  * @param columns
	  */
	 public void createSheetCell(HSSFSheet sheet,int rows,int columns){
		 for(int i = 0 ; i < rows ; i++){
			 HSSFRow row = sheet.createRow(i);
			 for(int a = 0 ; a < columns ; a++){
				 row.createCell(a);
			 }
		 }
	 }
	 
	 /**
	  * 合并单元格
	  * @param sheet
	  * @param rownumber
	  * @param begincolumn
	  * @param endcolumn
	  */
	 public void joinCell(HSSFSheet sheet,int beginrow,int endrow, int begincolumn,int endcolumn){
		 sheet.addMergedRegion(new CellRangeAddress(beginrow, endrow, (short) begincolumn, (short) endcolumn));
		 //sheet.addMergedRegion(new Region(beginrow,(short)begincolumn,endrow,(short)endcolumn));
	 }

	 /**
	  * 创建单元格样式
	  * 1 表示文字居中，字号15，黑体
	  * 2 表示文字居中，字号10，黑体，上下左右边框为1
	  * 3 表示文字居中，字号10，上下左右边框为1 
	  * 4 表示文字居中，字号10,黑体
	  * 5 表示文字居中，字号10，超链接，上下左右边框为1 
	  * @param workbook
	  * @return
	  */
	 public HSSFCellStyle createCellStyle(HSSFWorkbook workbook,int type){
		 HSSFCellStyle normalStyle = workbook.createCellStyle();
		 HSSFFont valueFont = workbook.createFont();
		 switch (type){
		 	case 1 :
		    	normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		    	//垂直居中
		 		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	normalStyle.setWrapText(true);
		    	valueFont.setFontHeightInPoints((short)15);
		    	valueFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	normalStyle.setFont(valueFont);
		    	break;
		 	case 2 : 
		 		//水平居中
		 		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 		//垂直居中
		 		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	normalStyle.setWrapText(true);
		    	valueFont.setFontHeightInPoints((short)10);
		    	valueFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	normalStyle.setFont(valueFont);
		    	normalStyle.setBorderBottom((short)1);
		    	normalStyle.setBorderLeft((short)1);
		    	normalStyle.setBorderRight((short)1);
		    	normalStyle.setBorderTop((short)1);
		    	
		 		break;
			case 3 : 
		 		//水平居中
		 		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 		//垂直居中
		 		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	normalStyle.setWrapText(true);
		    	valueFont.setFontHeightInPoints((short)10);
		    	normalStyle.setFont(valueFont);
		    	normalStyle.setBorderBottom((short)1);
		    	normalStyle.setBorderLeft((short)1);
		    	normalStyle.setBorderRight((short)1);
		    	normalStyle.setBorderTop((short)1);
		    	
		 		break;
			case 4 :
				//水平居中
		 		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 		//垂直居中
		 		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	normalStyle.setWrapText(true);
		    	valueFont.setFontHeightInPoints((short)10);
		    	valueFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    	normalStyle.setFont(valueFont);
		    	break;
			case 5 :
				//水平居中
		 		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 		//垂直居中
		 		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		    	normalStyle.setWrapText(true);
		    	valueFont.setFontHeightInPoints((short)10);
		    	//valueFont.setUnderline(HSSFFont.U_SINGLE);
		    	valueFont.setColor(HSSFColor.BLUE.index);
		    	normalStyle.setFont(valueFont);
		    	
		    	break;
		 }
			
		 return normalStyle;	
	 }
	 
	/**
	 * 设置表中数据头
	 * @param row
	 * @param tableheadernames
	 * @param normalStyle
	 */
	public void setTableHeader(HSSFRow row,String[] tableheadernames,HSSFCellStyle normalStyle){
		for(int i = 0 ; i < tableheadernames.length ; i++){
			row.getCell(i).setCellStyle(normalStyle);
			row.getCell(i).setCellValue(tableheadernames[i]);
		}
	}
	/**
	 * 设置表中列的宽度
	 * @param sheet
	 * @param columnWidths
	 */
	public void setSheetColumnWidth(HSSFSheet sheet, int[] columnWidths) {
		for(int i = 0 ; i < columnWidths.length ; i++){
			sheet.setColumnWidth(i, columnWidths[i]);
		}
	}
	
	/**
	 * 设置表中公司信息
	 * @param sheet0
	 * @param logoFile
	 * @param comInfo
	 */
	public void setSheetCompanyInfo(HSSFWorkbook workbook,HSSFSheet sheet0, String logoFile,
			TCompanyInfor comInfo) {
		//System.out.println("@@@@@@@@@@@@@@@@@@" + logoFile);
	    BufferedImage bufferImg =null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
		String extName = StringHelper.getExtName(logoFile, '.');
    	//文件不存在未做处理
		File file = new java.io.File(logoFile);
        try {
			bufferImg = ImageIO.read(file);
	        ImageIO.write(bufferImg,"png",byteArrayOut); 
	        HSSFPatriarch patriarch = sheet0.createDrawingPatriarch(); 
	        HSSFClientAnchor anchor = new HSSFClientAnchor(0,100,131,36,(short) 0,0,(short)2,2); 
	        //插入图片
	      //插入图片
	        if("png".equalsIgnoreCase(extName)) {
	        	ImageIO.write(bufferImg,"png",byteArrayOut);
	        	patriarch.createPicture(anchor , workbook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG)); 
	        } else if("jpg".equalsIgnoreCase(extName)) {
	        	ImageIO.write(bufferImg,"jpg",byteArrayOut);
	        	patriarch.createPicture(anchor , workbook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
	        }
	        //patriarch.createPicture(anchor , workbook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG)); 
		} catch (IOException e) {
			//System.out.println("公司logo不存在" + comInfo.getCompanyName());
			//e.printStackTrace();
		} 
		
	}
	 


	
	 
	
}
