<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
var _editerName = LoginInfor.user.trueName;
</script>
	<div id = "index_ct_testCut"></div>
	<script type="text/javascript" src="<%=path%>/js/query_statistics/products_query/view_sale_bill.js"></script>
    <script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUpload.js"></script>
    <script type="text/javascript" src="<%=path%>/js/upload/SlaveManager.js"></script>
	<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.js"></script>
    <script type="text/javascript" src="<%=path %>/js/quotation/generalQuo/calculate.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/generalQuo/generalQuo_manager.js"></script>
	<script type="text/javascript" src="<%=path %>/js/baseInfo/toolscombox.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/testCutQuo/testCutQuo_index.js"></script>
	