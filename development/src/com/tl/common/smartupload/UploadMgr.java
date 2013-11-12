package com.tl.common.smartupload;

import javax.servlet.jsp.PageContext;

public class UploadMgr {
	/**
	 * 文件上传方法.
	 * @param su
	 * @param pageContext
	 * @return
	 * @throws Exception
	 */
	public static String fileUpload(SmartUpload su,PageContext pageContext) throws Exception {
	    File suFile = null;
	    int fileCount = 0;
	    try {
	    	//获取传递过来的参数
	    	//String userId = su.getRequest().getParameter("user_id");
	    	//String passId = su.getRequest().getParameter("pass_id");
	    	//System.out.println(su.getRequest().getParameter("PHPSESSIDX"));

	        String fileExt = "";
	        int fileSize = 0;
	        String AllowedExtensions = ",jpg,jpeg,gif,chm,txt,pdf,doc,xls,";//允许上传的文件类型
	        double maxFileSize = 15*1024;//单文件最大大小，单位KB
	        //校验文件类型和大小
	        for (int i=0; i<su.getFiles().getCount();i++) {
	            suFile = su.getFiles().getFile(i);
	            if (suFile.isMissing())
	                continue;
	            //校验文件大小
	            fileSize = suFile.getSize()/1024;//字节转换成KB
	            if(fileSize==0) fileSize=1;
	            if(maxFileSize<fileSize) throw new Exception("单个上传文件的容量不能超过["+maxFileSize+"KB]");
	
	            //校验文件类型
	            if (suFile.getFileExt() == null
	                    || "".equals(suFile.getFileExt())) {
	                fileExt = ",,";
	            } else {
	                fileExt = "," + suFile.getFileExt().toLowerCase() + ",";
	            }
	            if (!"".equals(AllowedExtensions)
	                    && AllowedExtensions.indexOf(fileExt) == -1) {
	                throw new Exception("您上传的文件[" + suFile.getFileName()
	                        + "]的类型为系统禁止上传的文件类型，不能上传！");
	            }
	            fileCount++;
	        }
	        if (fileCount==0) throw new Exception("请选择上传的文件");
	        //准备保存文件
	        //String filePath="D:\\tomcat\\webapps\\test\\photo\\";//这里填写项目中存放上传文件的物理路径
	        String filePath="D:\\tmp\\";
	        for (int i=0; i<su.getFiles().getCount();i++) {
	            suFile = su.getFiles().getFile(i);
	            suFile.saveAs(filePath+suFile.getFileName(),SmartUpload.SAVE_PHYSICAL);//保存文件
	        }
	        //成功返回null
	        return null;
	    } finally {
	    	//
	    }
	}
}
