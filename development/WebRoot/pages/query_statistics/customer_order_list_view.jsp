<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/contract/charsDef.js"></script>
<script type="text/javascript" src="<%=path%>/js/query_statistics/customer_order_query/list_view.js"></script>
<div id="customer_order_query_list_"></div>