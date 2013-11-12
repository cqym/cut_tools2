<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/out_stock/contract_out_stock/out_stock_edit_win.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/selfOrder/selfOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/reserve_plan/reserve_plan_edit_win_new.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/SupHisPrice_win.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseProductsWindow.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseOrderWin.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSupplier.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSelectContract.js"></script>

<div id="self_order"></div>


