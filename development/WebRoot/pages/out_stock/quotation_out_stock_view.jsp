<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/quotation_out_stock/out_stock_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/quotation_out_stock/out_stock_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation_products.js"></script>
<div id="quotation_out_stock_list_"></div>
<div id="quotation_out_stock_quogrid"></div>
<div id="quotation_out_stock_contractEditWinEl"></div>
<div id="quotation_out_stock_quoProductTreeEl"></div>