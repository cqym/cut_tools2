<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
var _editerName = LoginInfor.user.trueName;
</script>
	
	<div id = "index_ct_reserve"></div>
	<script type="text/javascript" src="<%=path%>/js/query_statistics/products_query/view_sale_bill.js"></script>

    <script type="text/javascript" src="<%=path %>/js/quotation/generalQuo/calculate.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/calculateNew.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/generalQuo/generalQuo_manager.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/projectQuo_manager.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/reserveQuo/ExportReserveQuo.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/reserveQuo/reserveQuo_index.js"></script>
	<script type="text/javascript" src="<%=path %>/js/quotation/reserveQuo/edit2ContractInfor.js"></script>
	<script type="text/javascript" src="<%=path %>/js/baseInfo/toolscombox.js"></script>