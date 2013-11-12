package com.tl.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelUtil {
	public static void copyRow(HSSFRow sRow, HSSFRow tRow) {
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
}
