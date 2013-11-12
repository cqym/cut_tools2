<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>刀具销售管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/editable-column-tree.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-patch.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/row-plugin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/grid-examples.css" />

<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
var PATH = '<%=path%>';
var LoginInfor = <%=request.getAttribute("LoginInfor")%>;
function backspace(e){
   var ev = window.event || e; 
   if(ev.srcElement.tagName != 'INPUT' && ev.srcElement.tagName != 'TEXTAREA' && ev.keyCode == 8 ){
       ev.returnValue=false; 
   }
}

Ext.ffc = function(){};
Ext.ftl = function(){};
Ext.zhj = function(){};

//1、命名空间注册工具类
var Namespace = new Object();

Namespace.register = function(path) {
var arr = path.split(".");
var ns = "";
for ( var i = 0; i < arr.length; i++) {
 if (i > 0)
  ns += ".";
 ns += arr[i];
 eval("if(typeof(" + ns + ") == 'undefined') " + ns + " = new Object();");
}
}

Namespace.register("cut_tools.reserve_order");

Namespace.register("cut_tools.contract_order");
Namespace.register("cut_tools.self_order");
Namespace.register("cut_tools.plan_order");
Namespace.register("cut_tools.q_order");
Namespace.register("cut_tools.t_order");
Namespace.register("cut_tools.qs_order");
Namespace.register("cut_tools.ts_order");
Namespace.register("cut_tools.contract_account");

Ext.ffc.util = {
	toObjectString:function(obj){
	    var str = '';
		for(var i in obj){
		    str += i + ':' + obj[i] + ",\n";
		}
		return str;
	},
	showDebugString:function(str){
		Ext.MessageBox.show({
			   title: 'Debug Window',
			   msg: '程序测试窗口：',
			   width:300,
			   value:str,
			   buttons: Ext.MessageBox.OKCANCEL,
			   multiline: true,
			   animEl: 'mb3'
		   });
	},
	debug:function(obj){
		Ext.ffc.util.showDebugString(Ext.ffc.util.toObjectString(obj));
	}
}

Ext.ffc.PasteEdition = function(callBackMethod){
	Ext.ffc.a$ = document.createElement('TEXTAREA');
	document.body.appendChild(Ext.ffc.a$);
	Ext.ffc.a$.focus();
	setTimeout(Ext.ffc.wgzmb,10,callBackMethod);
}
Ext.ffc.wgzmb = function (callBackMethod){
	var str = Ext.ffc.a$.value;
	document.body.removeChild(Ext.ffc.a$);
	Ext.ffc.a$ = null;
	callBackMethod(str);
}

//-->
</SCRIPT>
<script type='text/javascript' src='<%=path%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=path%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=path%>/dwr/interface/NoticeMessageUtil.js'></script>



<script type="text/javascript" src="<%=path%>/js/manage/states.js"></script>
<script type="text/javascript" src="<%=path%>/js/manage/ComboTree.js"></script>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/js/main/menus.js"></script>
<script type="text/javascript" src="<%=path%>/js/proToolsInfor/ColumnNodeUI.js"></script>
<script type="text/javascript" src="<%=path%>/js/proToolsInfor/treeSerializer.js"></script>
<script type="text/javascript" src="<%=path%>/js/quotation/generalQuo/statusCombox.js"></script>
<script language="javascript" src="<%=path%>/js/manage/SessionProvider.js"></script>

<script type="text/javascript" src="<%=path%>/js/audit_infor/work_doing_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/load_business_infor.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_view.js"></script>
<script type="text/javascript" src="<%=path%>/js/audit_infor/audit_his_infor_list.js"></script>

<script type="text/javascript" src="<%=path %>/js/quotation/projectQuo/TabCloseMenu.js"></script>
<script type="text/javascript" 	src="<%=path%>/js/quotation/projectQuo/RowEditor.js"></script>
<script type="text/javascript" src="<%=path%>/js/contract/contract_edit_win.js"></script>
<script type="text/javascript" src="<%=path %>/js/quotation/generalQuo/generalQuo_index.js"></script>

<script type="text/javascript" src="<%=path %>/js/orders/orderCommon.js"></script>
<script type="text/javascript" src="<%=path%>/js/out_stock/contract_out_stock/out_stock_edit_win.js"></script>
<script type="text/javascript" src="<%=path%>/js/delivery/consult_contract/select_contract_products.js"></script>


<script type="text/javascript" src="<%=path %>/js/reserve_plan/reserve_plan_edit_win_new.js"></script>

<script type="text/javascript" src="<%=path %>/js/arrival/arrival_index.js"></script>
<script type="text/javascript" src="<%=path %>/js/arrival/directArrival/direct_arrival_list.js"></script>

<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUpload.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload/ImageView.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload/SlaveManager.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/plugins/SwfUploadPanel/SwfUploadPanel.js"></script>

<script type="text/javascript" src="<%=path %>/extjs/src/locale/ext-lang-zh_CN.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=path%>/extjs/RowExpander.js"></script>
<script type="text/javascript" src="<%=path %>/js/baseInfo/toolscombox.js"></script>
</head>
<body onload="DWREngine.setActiveReverseAjax(true);" onkeydown="backspace(event);">

<div id="container">
    <div id="toolbar"></div>
</div>
<div id="win" class="x-hidden">
<div id="tabs"></div>
</div>
</body>
</html>