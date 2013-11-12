<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/orders/reserveOrder/reserveOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/SupHisPrice_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseProductsWindow.js"></script>

<script type="text/javascript" src="<%=path%>/js/contract/invoiceEdit.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_account_edit.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_accounts.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseOrderWin.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSupplier.js"></script>

<div id="reserve_order"></div>
