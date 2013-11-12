package com.tl.test.improtsaleprice;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImportSaleT { 

	
	
	static String filepath = "D:\\project\\program\\cut_tools2\\development\\WebRoot\\upload\\templete\\delivery_templete.xls" ;
	InputStream fs;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    HSSFCell cell;
    
    public ImportSaleT() throws IOException
    {
        fs = new FileInputStream(filepath);
        workbook = new HSSFWorkbook(fs);
    }
    
    public void resultSetToExcel() throws IOException
    {
        if (workbook == null)
        {
            System.out.println("WorkSheet is null");
            return;
        }
        
        sheet = workbook.getSheetAt(0);
        if (sheet!=null)
        {
        	int from = 13,end = 20,jg = end - from + 1;
        	HSSFSheet tempSheet = workbook.createSheet("temp");
        	for (int i = from,j = 0; i <= end; i++,j++) {
        		HSSFRow row = sheet.getRow(i);
                HSSFRow tRow = tempSheet.createRow(j);
                copyRow(row, tRow);
                for (int j2 = 0,len =  row.getLastCellNum(); j2 < len; j2++) {
					row.createCell(j2);
				}
			}
        	from = 50;
        	for (int i = from,j = 0,len = tempSheet.getLastRowNum(); j <= len; i++,j++) {
        		HSSFRow tRow = sheet.getRow(i);
        		if(tRow == null) tRow = sheet.createRow(i);
                HSSFRow sRow = tempSheet.getRow(j);
                copyRow(sRow, tRow);
            
			}
        	workbook.removeSheetAt(workbook.getSheetIndex("temp"));
            FileOutputStream fout = new FileOutputStream("D:\\project\\program\\cut_tools2\\development\\WebRoot\\upload\\templete\\bb.xls");
            workbook.write(fout);
            fout.flush();
            fout.close();
            JOptionPane.showMessageDialog(null,"报表生成!");
        }
    }

	private void copyRow(HSSFRow sRow, HSSFRow tRow) {
		for (int i = 0,len = sRow.getLastCellNum(); i < len; i++) {
			HSSFCell tc = tRow.getCell(i);
			HSSFCell sc = sRow.getCell(i);
			tc = (tc == null ? tRow.createCell(i) : tc);
			sc = (sc == null ? sRow.createCell(i) : sc);
			tc.setCellStyle(sc.getCellStyle());
				switch (sc.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						tc.setCellValue(sc.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BLANK:	
					    break;
					case HSSFCell.CELL_TYPE_FORMULA:
						
						break;
					case HSSFCell.CELL_TYPE_STRING:
						tc.setCellValue(sc.getStringCellValue());
						break;
					default:
						break;
				}
				
			
		}
	}

    public static void main(String[] args) throws IOException
    {
    	ImportSaleT dt = new ImportSaleT();
        dt.resultSetToExcel();
       // dt.resultSetToExcel(str,1);
    }

}
