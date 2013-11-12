package com.tl.resource.business.contract;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ContractListOutExcel {
	public void exportExcel(Map<String,Object> mparams,HttpServletResponse response, HttpServletRequest request)throws IOException;
}
