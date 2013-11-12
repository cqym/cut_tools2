<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/delivery_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/delivery_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation.js"></script>
<div id="quo_delivery_list_"></div>
<div id="quo_delivery_quogrid"></div>
<div id="quo_delivery_contractEditWinEl"></div>
<div id="quo_delivery_quoProductTreeEl"></div>