<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/main/forum.js"></script>
<script type="text/javascript" src="<%=path%>/js/main/mainWorkRegion.js"></script>

<div id="main_panel_"></div>
