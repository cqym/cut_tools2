<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>刀具销售管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/editable-column-tree.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-patch.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/row-plugin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/grid-examples.css" />
<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>
 </head>

 <body>
  <div id="s"></div>
 </body>
</html>
<script type="text/javascript">
<!--
 Ext.onReady(function(){
     
 });
//-->
</script>