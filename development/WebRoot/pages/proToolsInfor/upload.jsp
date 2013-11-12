<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.tl.common.smartupload.SmartUpload"%>
<%@ page import="com.tl.common.smartupload.UploadMgr"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String pageErrorInfo = null;
	SmartUpload su = null;
	String msg = "上传成功！";
	try{
		su = new SmartUpload();
		su.initialize(pageContext);
		su.upload();
		pageErrorInfo = UploadMgr.fileUpload(su,pageContext);
		if(pageErrorInfo==null){
			out.print("{success : true}");
		}
	}catch(Exception e){
		System.out.println(e.getMessage());
		msg = e.getMessage();
	}finally{
		su = null;
		if(pageErrorInfo!=null){
			out.print("{success : false, msg : " + msg + "}");
		}
	}

    System.out.println("文件大小： " + request.getContentLength());
   	 %>