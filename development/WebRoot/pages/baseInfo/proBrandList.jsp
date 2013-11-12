<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script>
var PAGESIZE = parseInt((Ext.getBody().getHeight()-250)/24);
</script>

<div id="proBrandCt"></div>

<!-- 品牌信息列表 -->
<script type="text/javascript" 	src="<%=path%>/js/baseInfo/BrandManager.js"></script>





