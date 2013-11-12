<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/out_stock/try_tools_quo/out_stock_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/quotation_out_stock/out_stock_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation_products.js"></script>
<div id="try_tools_quo_out_stock_list_"></div>