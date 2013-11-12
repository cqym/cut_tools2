Ext.ffc.select_contracts = function (callBackMethod,conType){

//搜索条件 start
var statusCombox = new Ext.ffc.ContractStatusComboBox({anchor:'90%'});
	statusCombox.setValue("执行中");
	statusCombox.disabled = true;
var	searchForm = new Ext.FormPanel({
		width : 810,
		region: 'north',
		layout:'fit',
		iconCls:'icon-grid',
		split: true,
		height : 120,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:70,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.3},
           bbar : ['->',{
						xtype:'button',
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var seachParams = searchForm.getForm().getValues();
							seachParams['status'] = 4;//执行状态合同
 						    for(var i in seachParams){
								contractListStore.setBaseParam(i, seachParams[i]);
							}
							contractListStore.load();
		           		}
	           		},'-',
					{  
						xtype:'button',
						text:'重置',
						iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								searchForm.getForm().reset();
							},scope : this
						}					
					}],
           items:[
              {items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'90%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '客户名称',name: 'customerName',anchor:'90%'}]},
              {items: [statusCombox]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endTime', fieldLabel: '日期起',name: 'startTime', format:'Y-m-d', emptyText:'',anchor:'90%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'startTime',fieldLabel: '至',name: 'endTime', format:'Y-m-d', emptyText:'',labelSeparator:'',anchor:'90%'}]}
           ]//items
          }
        ],//items
		listeners : {
			'render': function(p) {
				p.getEl().on('keypress', function(){
					if(window.event.keyCode == 13){
						var seachParams = searchForm.getForm().getValues();
							seachParams['status'] = 4;//执行状态合同
 						    for(var i in seachParams){
								contractListStore.setBaseParam(i, seachParams[i]);
							}
							contractListStore.load();
					}
				});
			}
		}
   })//FormPanel 
//搜索条件 end
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,{
		header : '合同编号',
		dataIndex : 'contractCode',
		width:170, 
		sortable: true
	}, {
		header : '客户名称',
		dataIndex : 'customerName',
		width:160, 
		sortable: true
	},{
		header : '签订日期',
		dataIndex : 'signDate',
		width:100, 
		sortable: true
	},{
		header : '交货方式',
		dataIndex : 'deliveryAddressType', 
		sortable: true
	},{
		header : '币别',
		dataIndex : 'currencyName', 
		sortable: true
	},{
		header : '货品金额',
		dataIndex : 'productMoney', 
		sortable: true
	},{
		header : '税率',
		dataIndex : 'taxRate'
	},{
		header : '税金',
		dataIndex : 'taxMoney'
	},{
		header : '税价合计',
		dataIndex : 'totalMoney'
	},{
		header : '整体折扣',
		dataIndex : 'overallRebate'
	},{
		header : '最终金额',
		dataIndex : 'finalMoney'
	},{
		header : '附   件',
		dataIndex : 'fileCount',
		sortable: true,
		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
			if(value * 1 > 0){
			    return '<span style="color:#990000">已传附件</span>';
			}
			return '<span style="color:#330000">未传附件</span>';
		}
	},{
		header : '制单人',
		dataIndex : 'userName', 
		sortable: true
	},{
		header : '制单时间',
		dataIndex : 'editDateString'
	},{
		header : '备注',
		dataIndex : 'memo'
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	},{
		header : '客户编号',
		hidden : true,
		dataIndex : 'customerCode'
	},{
		header : '客户锁定状态',
//		hidden : true,
		dataIndex : 'cusLockStatus'
	}]);
	
var contractListStore = new Ext.data.JsonStore({
    remoteSort : true,
	url : PATH + '/contract/contractViewAction.do?ffc=contractList&conType=' + conType,
	root : 'items',
	totalProperty : 'totalCount',
	fields : ['id', 'contractCode', 'customerName', 'productMoney', 'taxRate', 'taxMoney', 'totalMoney', 'overallRebate',
				'finalMoney','userName', 'editDateString', 'status',"deliveryAddressType",'currencyName','signDate','fileCount','cusLockStatus','customerCode'],
	listeners : {
			load:function(){
				setRowLockStatus();
			}
	}
});
function setRowLockStatus(){
	var s = grid.getStore();
	for(var rowIndex = 0,len = s.getCount();rowIndex < len ; rowIndex++){
		var r = s.getAt(rowIndex);
		if(r.data.cusLockStatus * 1 == 1){
			grid.getView().getRow(rowIndex).style.backgroundColor = '#FFCCFF';
			grid.getView().getRow(rowIndex).style.color = '#FF0000';
		}
	}
}
var grid = new Ext.grid.GridPanel({
		region: 'center',
		layout:'fit',
		bodyStyle:'width:100%',
		height : 150,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		split: true,
		ds : contractListStore,
		cm : cm,
		sm:sm,
		listeners : {
		    rowclick : function(grid, rowIndex, e){
				var s = grid.getStore();
				var r = s.getAt(rowIndex);
				contractProductListStore.setBaseParam('id',r.id);
				contractProductListStore.load({params:{start:0,limit:10,id:r.id}});
			},
			sortchange : function(grid, sortInfo ){
				setRowLockStatus();
			}
		},
		bbar : new Ext.PagingToolbar({
				pageSize : 10,
				emptyMsg : "没有记录",
				displayInfo : true,
        		displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
				store : contractListStore
			})
});

var contractProductListStore = new Ext.data.JsonStore({
		remoteSort : true,
		url : PATH + '/contract/contractViewAction.do?ffc=contractAllDetailView&limit=10',
		root : 'items',
		totalProperty : 'totalCount',
		fields : ['id', 'proSortName', 'projectCode', 'serialNumber', 'productBrand', 'productCode', 'productName', 'amount',
				 	'productUnit','orderAmount', 'deliveryAmount', 'arrivalAmount','brandCode']
	});
var productCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
		header : '明细id',
		dataIndex : 'contractCode',
		width:170,
		hidden:true
	}, {
		header : '分项名称',
		dataIndex : 'proSortName',
		width:160
	},{
		header : '项目编号',
		dataIndex : 'projectCode',
		width:100
	},{
		header : '序号',
		dataIndex : 'serialNumber'
	},{
		header : '牌号',
		dataIndex : 'brandCode'
	},{
		header : '产品名称',
		dataIndex : 'productName'
	},{
		header : '产品编号',
		dataIndex : 'productCode'
	},{
		header : '品牌',
		dataIndex : 'productBrand'
	},{
		header : '合同数量',
		dataIndex : 'amount'
	},{
		header : '计量单位',
		dataIndex : 'productUnit'
	},{
		header : '采购数量',
		dataIndex : 'orderAmount'
	},{
		header : '到货数量',
		dataIndex : 'arrivalAmount'
	},{
		header : '累计交付数量',
		dataIndex : 'deliveryAmount'
	}]);
var productGrid = new Ext.grid.GridPanel({
		region: 'south',
		layout:'fit',
		bodyStyle:'width:100%',
		height : 150,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		split: true,
		ds : contractProductListStore,
		cm : productCm,
		bbar : new Ext.PagingToolbar({
				pageSize : 10,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : contractProductListStore
			})
});
    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择合同',
			width:820,
			height:495,
			maximizable :true,
			buttonAlign:'right',
            items: [searchForm, grid,productGrid],
			buttons: [{
				text : "确  定",
				handler : function(obj) {
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择合同!");
						return ;
					}
					var customerCode = arr[0].get("customerCode");
					Ext.Ajax.request({
							method: "post",
							url: PATH + '/baseInfo/customersListAction.do?searchStr={customerCode:"' + customerCode+'"}',
							success: function(response){
								eval("var temp = " + response.responseText);
								var cusList = temp.customersList;
								var customer = null;
								if(cusList.length > 0){
								    customer = cusList[0];
								}
								callBackMethod(arr,select_quotations_win,customer);
							}
					});	
				}
	         },{
				text : "取  消",
				handler : function() {
					select_quotations_win.close();
				}
	         }]
        });
		select_quotations_win.show(this);

		var seachParams = searchForm.getForm().getValues();
			seachParams['status'] = 4;//执行状态合同
			for(var i in seachParams){
				contractListStore.setBaseParam(i, seachParams[i]);
			}
			contractListStore.load();
}


