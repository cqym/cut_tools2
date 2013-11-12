<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=path%>/js/proToolsInfor/treeSerializer.js"></script>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/material_out_stock/out_stock_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/material_out_stock/out_stock_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/reserve_plan/select_process_orders.js"></script>
<div id="material_out_stock_list_"></div>