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
<SCRIPT LANGUAGE="JavaScript">
<!--
var PATH = '<%=path%>';
//-->
</SCRIPT>
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-patch.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/grid-examples.css" />

<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>

</head>
<body >

<div id="waitDiv">
</div>

</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
Ext.onReady(function(){
	function opWaitWorksInforData(temp){
		var rt = [];
		var preStr = "现有 ["
		if(temp.waitorderConCount > 0){
				rt.push([preStr + temp.waitorderConCount + "] 份合同，需要编制合同订单!"]);
			}
			if(temp.waitContractQuoCount > 0){
				rt.push([preStr + temp.waitContractQuoCount + "] 份报价单，需要编制合同!"]);
			}
			if(temp.waitSelfOrderConCount > 0){
				rt.push([preStr + temp.waitSelfOrderConCount + "] 份合同，需要加工订单!"]);
			}
			if(temp.waitExpectedQuoCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.waitExpectedQuoCount + "] 份预定报价单，需要编制预定订单!"]);
			}
			if(temp.waitExpectedQuo4SelfOrderCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.waitExpectedQuo4SelfOrderCount + "] 份预定报价单，需要编制预定加工订单!"]);
			}
			if(temp.waitTryToolsQuoCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.waitTryToolsQuoCount + "] 份试刀申请，需要编制试刀订单!"]);
			}
			if(temp.waitTryToolsQuo4SelfOrderCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.waitTryToolsQuo4SelfOrderCount + "] 份试刀申请，需要编制试刀加工订单!"]);
			}
			if(temp.contractCountCouldUploadFile > 0){
				//flashFlag = true;
				rt.push([preStr + temp.contractCountCouldUploadFile + "] 份合同，需要上传附件!"]);
			}
			if(temp.expectedQuo2QuoCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.expectedQuo2QuoCount + "] 份预定报价单，需要转正式报价单!"]);
			}
			if(temp.tryTools2DeliveryCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.tryTools2DeliveryCount + "] 份试刀申请，需要交货!"]);
			}
			if(temp.tryTools2UploadReportCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.tryTools2UploadReportCount + "] 份试刀申请，需要上传试刀报告!"]);
			}
			if(temp.tryTools2UploadApplyCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.tryTools2UploadApplyCount + "] 份试刀申请，需要上传试刀申请!"]);
			}
			if(temp.wait2PlanSelfOrderCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.wait2PlanSelfOrderCount + "] 份加工订单，需要编制采购计划!"]);
			}
			if(temp.wait2PlanOrderPlanCount > 0){
				//flashFlag = true;
				rt.push([preStr + temp.wait2PlanOrderPlanCount + "] 份计划，需要编制材料采购订单!"]);
			}
			return rt;
	}

 var store = new Ext.data.ArrayStore({
        fields: [
           {name: 'message'},
        ]
    });

var bwidth = Ext.getBody().getWidth();
var bheight = 150;
	var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: "待办任务描述", width: 260, dataIndex: 'message'},
        ],
        height:bheight,
        width:bwidth,
		stripeRows : true
	});
    grid.render('waitDiv');

	Ext.Ajax.request({
		url: PATH + '/WaitWorksInforAction.do',
		params: {ffc:'requestInfos'},
		success: function(response){
			eval("var temp = " + response.responseText);
			var arr = opWaitWorksInforData(temp);
			store.loadData(arr);
		}
	});
});

//-->
</SCRIPT>