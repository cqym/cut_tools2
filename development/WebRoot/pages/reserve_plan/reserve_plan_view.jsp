<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/out_stock/material_out_stock/out_stock_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/reserve_plan/reserve_plan_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/reserve_plan/select_process_orders.js"></script>
<script type="text/javascript" src="<%=path%>/js/reserve_plan/reserve_plan_edit_win_new.js"></script>
<div id="reserve_plan_list_"></div>