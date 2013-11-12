Ext.ffc.select_orders = function (callBackMethod,orderType){

//搜索条件 start
var statusCombox = new Ext.ffc.ContractStatusComboBox({anchor:'90%'});
	statusCombox.setValue("下单");
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
           defaults:{layout: 'form',border:false,columnWidth:.5},
           bbar : ['->',{
						xtype:'button',
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var seachParams = searchForm.getForm().getValues();
							seachParams['status'] = '4,6';//
							seachParams['supplierOwnContactPerson'] = '';
							seachParams['customerName'] = '';
							seachParams['ownContactPerson'] = '';
							seachParams['memo'] = '';
 							var searchStr = Ext.encode(seachParams);
							contractListStore.baseParams.searchStr = searchStr;
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
              {items: [{xtype:'textfield',fieldLabel: '订单编号',name: 'orderCode',anchor:'80%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '供应商名称',name: 'supplierName',anchor:'80%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endTime', fieldLabel: '日期起',name: 'startTime', format:'Y-m-d', emptyText:'',anchor:'80%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'startTime',fieldLabel: '至',name: 'endTime', format:'Y-m-d', emptyText:'',labelSeparator:'',anchor:'80%'}]},
			  {items: [{xtype:'hidden', name: 'orderType',x:570,y:3, width:170,value:orderType}]}
           ]//items
          }
        ],//items
		listeners : {
			'render': function(p) {
				p.getEl().on('keypress', function(){
					if(window.event.keyCode == 13){
						var seachParams = searchForm.getForm().getValues();
							seachParams['status'] = '4,6';//
							seachParams['supplierOwnContactPerson'] = '';
							seachParams['customerName'] = '';
							seachParams['ownContactPerson'] = '';
							seachParams['memo'] = '';
 							var searchStr = Ext.encode(seachParams);
							contractListStore.baseParams.searchStr = searchStr;
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
		header : '订单编号',
		dataIndex : 'orderCode',
		width:170, 
		sortable: true
	}, {
		header : '供应商',
		dataIndex : 'supplierName',
		width:160, 
		sortable: true
	},{
		header : '订货日期',
		dataIndex : 'orderDate',
		width:100, 
		sortable: true
	},{
		header : '供应商我方负责人',
		dataIndex : 'supplierOwnContactPerson', 
		sortable: true
	},{
		header : '我方负责人',
		dataIndex : 'ownContactPerson', 
		sortable: true
	},{
		header : '币别',
		dataIndex : 'currencyName', 
		sortable: true
	},{
		header : '最终金额',
		dataIndex : 'finalMoney'
	},{
		header : '合同编号',
		dataIndex : 'contractCode'
	},{
		header : '预订/试刀编号',
		dataIndex : 'quotationCode'
	},{
		header : '',
		dataIndex : 'orderType',
		hidden:true
	},{
		header : '',
		dataIndex : 'supplierId',
		hidden:true
	}]);
	
var contractListStore = new Ext.data.JsonStore({
    remoteSort : true,
	url : PATH + '/contractOrder/listAction.do',
	root : 'root',
	totalProperty : 'totalProperty',
	fields : ['id', 'orderCode', 'supplierName', 'orderDate', 'supplierOwnContactPerson', 'ownContactPerson', 'currencyName', 'finalMoney',
				'contractCode','quotationCode','orderType','customerCode','customerName','supplierId'],
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



    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择订单',
			width:820,
			height:495,
			maximizable :true,
			buttonAlign:'right',
            items: [searchForm, grid],
			buttons: [{
				text : "确  定",
				handler : function(obj) {
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择订单!");
						return ;
					}
					callBackMethod(arr,select_quotations_win);
					select_quotations_win.close();
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
			seachParams['status'] = '4,6';//已下单
			seachParams['memo'] = '';
		var searchStr = Ext.encode(seachParams);
		contractListStore.baseParams.searchStr = searchStr;
	    contractListStore.load();
}