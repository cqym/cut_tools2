<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/delivery_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/try_tools_quo/delivery_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_quotation/select_quotation.js"></script>
<div id="try_tools_quo_delivery_list_"></div>
