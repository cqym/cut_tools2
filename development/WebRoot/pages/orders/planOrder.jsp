<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/out_stock/material_out_stock/out_stock_edit_win.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/planOrder/planOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/SupHisPrice_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/planOrder/select_process_plans.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseProductsWindow.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseOrderWin.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSupplier.js"></script>

<div id="paln_order"></div>