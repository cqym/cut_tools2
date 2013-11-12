<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/audit_infor/PagingToolbarEx.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_infor_detail.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_his_infor_list.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_infor_detail_his.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_group_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/load_business_infor.js"></script>
<div id="grid-main-audit-panel" style="border:1px solid blue;position:absolute;">
<div id="grid-audit"></div>
<div id="grid-audit_his"></div>
</div>
<div id="audit-detail-win">
<div id="grid-audit-detail"></div>
</div>
<div id="quoProductTree"></div>
<SCRIPT LANGUAGE="JavaScript">
<!--
Ext.ffc.setAuditInforsPosition = function(){
	var gmap = document.getElementById('grid-main-audit-panel');
	var pNode = gmap.parentNode;
	gmap.style.left = (pNode.offsetWidth - gmap.offsetWidth) + 'px';
};
//-->
</SCRIPT>