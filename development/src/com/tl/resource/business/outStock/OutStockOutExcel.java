package com.tl.resource.business.outStock;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OutStockOutExcel {
	public void exportExcel(String conId,HttpServletResponse response, HttpServletRequest request)throws IOException;
}
