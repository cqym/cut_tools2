<%@ page language="java" import="java.util.*,com.tl.common.context.SystemInstance,com.tl.resource.dao.TAccessoriesDAO" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>刀具销售管理系统</title>
<head>
</head>
<body >
<%
TAccessoriesDAO accessoriesDAO = (TAccessoriesDAO) SystemInstance.getInstance().getBean("TAccessoriesDAO"); 

%>

</body>
</html>
