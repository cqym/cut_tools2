
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>JSP Page</title> 
<SCRIPT LANGUAGE="JavaScript">
<!--
var dept = '222';
var firebow = {logistics:{}};
//-->
</SCRIPT>
<link rel="stylesheet" type="text/css" href="<%=path%>/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/ext-patch.css" />
<script type="text/javascript" src="<%=path%>/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/extjs/ext-all.js"></script>

<script type="text/javascript" src="inBillByContract.js"></script>
<script type="text/javascript" src="inBillByNoPlan.js"></script>
<script type="text/javascript" src="inBillDetailPanel.js"></script>
<script type="text/javascript" src="inBillWindow_new.js"></script>

<body >
<div id='mainBody' ></div>
</body>
</html>


<SCRIPT LANGUAGE="JavaScript">
<!--

Ext.onReady(function(){
	var quotationType = 0;
	var jsonData = [{"amount":2,"arrivalAmount":0,"brandCode":"A731-R112.5-60-SP","children":[],"contractInforId":"","contractProjectSortId":"","deliveryAmount":0,"deliveryDate":"2010-05-07","fileCount":0,"iconCls":"task-folder","id":"1004121006463905a86d61d757dec0a3","leaf":0,"loader":null,"memo":"","money":7000,"netPrice":3500,"orderAmount":0,"parentToolsId":"root","price":0,"priceChange":2,"proSortName":"","processCode":"","productBrand":"万威制造","productCode":"WW10-0043956","productName":"板桥","productUnit":"个","projectCode":"1","rebate":0,"remainAmount":0,"reportCode":"","reserveAmount":0,"reserveInforId":"","serialNumber":1,"singleSetAssemblyAmount":0,"singleSetStockAmount":0,"status":0,"taxMoney":8190,"taxNetPrice":4095,"toolCode":"","toolDescription":"","toolsId":"0043956","uiProvider":"col","workshop":""},{"amount":4,"arrivalAmount":0,"brandCode":"SRFCR2520-25","children":[],"contractInforId":"","contractProjectSortId":"","deliveryAmount":0,"deliveryDate":"2010-05-07","fileCount":0,"iconCls":"task-folder","id":"100412100646421e8aae2a02e262e6bc","leaf":0,"loader":null,"memo":"","money":3400,"netPrice":850,"orderAmount":0,"parentToolsId":"root","price":0,"priceChange":2,"proSortName":"","processCode":"","productBrand":"万威制造","productCode":"WW10-0043957","productName":"刀夹","productUnit":"个","projectCode":"2","rebate":0,"remainAmount":0,"reportCode":"","reserveAmount":0,"reserveInforId":"","serialNumber":2,"singleSetAssemblyAmount":0,"singleSetStockAmount":0,"status":0,"taxMoney":3978,"taxNetPrice":994.5,"toolCode":"","toolDescription":"","toolsId":"0043957","uiProvider":"col","workshop":""}];
	var gStore = new Ext.data.Store({
		   proxy:new Ext.data.MemoryProxy(jsonData),
		   reader: new Ext.data.JsonReader({
		   
		 }, 
		 [
			{name: 'id',mapping:'id',type:'string'},
			{name: 'productCode',mapping:'productCode',type:'string'},
			{name: 'projectCode',mapping:'projectCode',type:'float'},
			{name: 'serialNumber',mapping:'serialNumber',type:'float'},
			{name: 'brandCode',mapping:'brandCode',type:'string'},
			{name: 'productName',mapping:'productName',type:'string'},
			{name: 'singleSetAssemblyAmount',mapping:'singleSetAssemblyAmount',type:'float'},
			{name: 'singleSetStockAmount',mapping:'singleSetStockAmount',type:'float'},
			{name: 'amount',mapping:'amount',type:'float'},
			{name: 'productUnit',mapping:'productUnit',type:'string'},
			{name: 'price',mapping:'price',type:'float'},
			{name: 'rebate',mapping:'rebate',type:'float'},
			{name: 'netPrice',mapping:'netPrice',type:'float'},
			{name: 'money',mapping:'money',type:'float'},
			{name: 'taxNetPrice',mapping:'taxNetPrice',type:'float'},
			{name: 'taxMoney',mapping:'taxMoney',type:'float'},
			{name: 'productBrand',mapping:'productBrand',type:'string'},
			{name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
			{name: 'memo',mapping:'memo',type:'string'},
			{name: 'workshop',mapping:'workshop',type:'string'},
			{name: 'reportCode',mapping:'reportCode',type:'string'}
        ])
    });

 var egrid = new Ext.grid.EditorGridPanel({
			layout:'fit',
			clicksToEdit:1,
			ds : gStore,
			store : gStore,
			height: 500,
	        columns:[new Ext.grid.RowNumberer(),
			{
	            dataIndex:'id',
				hidden:true
	        },{
	            header:'货品编号',
	            width:100,
	            resizable : true,
	            dataIndex:'productCode'
	        },{
	            header:'项目编号',
	            width:60,
	            resizable : true,
	            dataIndex:'projectCode'
	        },{
	            header:'序号',
	            width:40,
	            resizable : true,
	            dataIndex:'serialNumber'
	        },{
	            header:'工具牌号',
	            width:200,
	            resizable : true,
	            dataIndex:'brandCode'
	        },{
	            header:'名称',
	            width:100,
	            dataIndex:'productName'
	        },{
	            header:'单套刀具装配数量',
	            width:120,
	            dataIndex:'singleSetAssemblyAmount',
				hidden : quotationType != 1
	        },{
	            header:'单套刀具采购数量',
	            width:120,
	            dataIndex:'singleSetStockAmount',
				hidden : quotationType != 1
	        },{
	            header:'数量',
	            width:50,
	            dataIndex:'amount',
				editor:new Ext.form.NumberField()
	        },{
	            header:'计量单位',
	            width:60,
	            dataIndex:'productUnit'
	        },{
	            header:'单价',
	            width:60,
	            name : 'price',
	            dataIndex:'price'
	        },{
	            header:'折扣',
	            width:50,
	            dataIndex:'rebate'//,
				//renderer:rebateRender
	        },{
	            header:'净价',
	            width:80,
	            dataIndex:'netPrice'//,
				//renderer:netPriceRender
	        },{
	            header:'金额',
	            width:80,
	            dataIndex:'money'//,
				//renderer:moneyRender
	        },{
	            header:'含税净价',
	            width:80,
	            dataIndex:'taxNetPrice'//,
				//renderer:taxNetPriceRender
	        },{
	            header:'含税金额',
	            width:80,
	            dataIndex:'taxMoney'//,
				//renderer:taxMoneyRender
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand'
			},{
	            header:'交货期限',
	            width:80,
	            dataIndex:'deliveryDate'
	        },{
	            header:'备注1',
	            width:80,
	            dataIndex:'memo',
				editor:new Ext.form.TextField()
	        },{
	            header:'备注2',
	            width:80,
	            dataIndex:'workshop',
				editor:new Ext.form.TextField()
	        },{
	            header:'备注3',
	            width:80,
	            dataIndex:'reportCode',
				editor:new Ext.form.TextField()
	        }]
	    });
		egrid.render('mainBody');
		//gStore.load();
});
//-->
</SCRIPT>