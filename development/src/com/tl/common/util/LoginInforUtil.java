package com.tl.common.util;

import javax.servlet.http.HttpServletRequest;

import com.tl.resource.business.dto.LoginInforDto;


public class LoginInforUtil {
	public static void setLoginInfor(HttpServletRequest request,LoginInforDto loginInfor){
		request.getSession().setAttribute("loginInfor", loginInfor);
	} 
	public static LoginInforDto getLoginInfor(HttpServletRequest request){
		return (LoginInforDto) request.getSession().getAttribute("loginInfor");
	}
}
