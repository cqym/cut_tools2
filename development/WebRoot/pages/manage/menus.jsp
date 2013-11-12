<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
var PATH = '<%=path%>';
//-->
</SCRIPT>
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />

<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>
<script type="text/javascript">
<!--
Ext.ffc = function(){};
Ext.ftl = function(){};
Ext.zhj = function(){};
//-->
</script>
<script language="javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>

<script type="text/javascript" src="<%=path%>/js/manage/audit.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/roles.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/users.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/departs.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/workTrust.js"></script>

<script type="text/javascript" src="<%=path%>/js/manage/ComboTree.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/menus.js"></script>
<script language="javascript" src="<%=path%>/js/manage/SessionProvider.js"></script>
<script language="javascript" src="<%=path%>/js/manage/SessionProvider.js"></script>

<script language="javascript" src="<%=path%>/js/manage/BillDocument.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>

</head>
<body>
<script type="text/javascript" src="<%=path%>/shared/examples.js"></script>
<div id="container">
    <div id="toolbar"></div>
</div>
<br /><br /><br /><br /><br />
<div id="win" class="x-hidden">
<div id="tabs"></div>
</div>
<div id="ffc_role_modules_div">
<div id="ffc_role_modules_tree">
<div id="ffc_role_modules_div2">
<div id="ffc_role_modules_tree2">
</div>
</div>
</body>
</html>
