package com.tl.resource.business.delivery;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DeliveryOutExcel {
	public void exportExcel(String conId,HttpServletResponse response, HttpServletRequest request)throws IOException;
}
