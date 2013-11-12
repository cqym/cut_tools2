<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
var PATH = '<%=path%>';
var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
</script>
	<link rel="stylesheet" type="text/css" href="<%=path %>/extjs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/editable-column-tree.css" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-patch.css" />
	<script type="text/javascript" src="<%=path %>/extjs/src/locale/ext-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/js/arrival/directArrival/supplier.js"></script>
<div id="direct_arrival_index"></div>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path %>/js/arrival/directArrival/direct_arrival_manage.js"></script>
<script type="text/javascript" src="<%=path %>/js/arrival/order_search.js"></script>
<script type="text/javascript" src="<%=path %>/js/arrival/directArrival/direct_arrival_index.js"></script>
