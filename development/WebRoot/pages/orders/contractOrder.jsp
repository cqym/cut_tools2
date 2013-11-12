<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=path%>/js/out_stock/contract_out_stock/out_stock_edit_win.js"></script>
 
<script type="text/javascript" src="<%=path %>/js/orders/contractOrder/contractOrder.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/SupHisPrice_win.js"></script>

<script type="text/javascript" src="<%=path%>/js/contract/invoiceEdit.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_account_edit.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_accounts.js"></script>

<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseProductsWindow.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseOrderWin.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSupplier.js"></script>
<script type="text/javascript" src="<%=path%>/js/orders/purchaseOrder/purchaseSelectContract.js"></script>

<div id="contract_orders"></div>

	


