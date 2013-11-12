<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_panel_list_view.js"></script>
<div id="contract_panel_list_"></div>