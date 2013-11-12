<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.css" />

<script type="text/javascript" src="<%=path%>/js/contract/charsDef.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_account_edit.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_accounts.js"></script>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_view_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_list_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/select_quotation.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/invoiceEdit.js"></script>


<div id="contract_list_"></div>
<div id="quogrid"></div>
<div id="contractEditWinEl"></div>
<div id="quoProductTreeEl"></div>
