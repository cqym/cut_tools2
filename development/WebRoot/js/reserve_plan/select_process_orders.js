Ext.ffc.select_process_orders = function (callBackMethod,initParams){
var	searchForm = new Ext.FormPanel({
		width : 810,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:80,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.4},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var obj2 = searchForm.getForm().getValues();
							if(initParams){
								Ext.applyIf(obj2,initParams);
							}
 						    var seachParams = {searchStr:Ext.encode(obj2),orderType:obj2.orderType};
							for(var i in seachParams){
								contractListStore.setBaseParam(i, seachParams[i]);
							}
							contractListStore.load();
		           		}
	           		},'-',
					{  
						text:'重置'  
						,iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								searchForm.getForm().reset();
							},scope : this
						}					
					}],
           items:[
			  {items: [{xtype:'textfield',fieldLabel: '加工订单编号',name: 'orderCode',anchor:'100%'},
					   {xtype:'hidden', readOnly : true, name: 'orderType',value:"3,7,8"},
					   {xtype:'hidden', readOnly : true, name: 'needEditPlan',value:"1"}]},
              {columnWidth:0.6,items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'65%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endDate', fieldLabel: '日期起',name: 'startTime', format:'Y-m-d', emptyText:'',anchor:'100%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'beginDate',fieldLabel: '至',name: 'endTime', format:'Y-m-d', emptyText:'',labelSeparator:'',anchor:'100%'}]}
           ]//items
          }
        ]//items
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,{
		header : '加工订单编号',
		dataIndex : 'orderCode',
		width: 180
	},{
		header : '订单类型',
		dataIndex : 'orderType',
		width: 100,
		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [[3,'合同加工订单'],
							[7,'预定加工订单'],
							[8,'试刀加工订单']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
		}
	},{
		header : '合同编号',
		dataIndex : 'contractCode',
		width: 180
	}, {
		header : '制单人',
		dataIndex : 'userName',
		width: 60
	},{
		header : '制单时间',
		dataIndex : 'editDateCopy',
		width: 120
	},{
		header : '备注',
		dataIndex : 'memo',
		width: 60
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var contractListStore = new Ext.data.JsonStore({
		url : PATH + '/selfOrder/listAction.do',
		root : 'root',
		totalProperty : 'totalProperty',
		//autoLoad : true,
		fields : ['id', 'contractCode', 'orderCode','userName', 'editDateCopy','memo','orderType']
	});
	
	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '加工订单信息！'
        });
var grid = new Ext.grid.GridPanel({
		// width : 450,
		bodyStyle:'width:100%',
		height : 300,
		//autoHeight : true,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		//el : 'quogrid',
        view : gv,
		ds : contractListStore,
		cm : cm,
		sm:sm,
		bbar : new Ext.PagingToolbar({
				pageSize : 20,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : contractListStore
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择加工订单',
			width:820,
			height:495,
			buttonAlign:'right',
			maximizable :true,
			modal : true,
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
				layout: 'fit',
                split: true,
                width: 200,
                height : 120,
                minSize: 140,
                maxSize: 300,
                collapsible: true,
                margins: '5 5 5 5',
                items : [searchForm]
                
            }, {
                region: 'center',
                layout: 'fit',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                margins: '-5 5 5 5',
                items : [grid]
            }],
			buttons: [/*{
				text : "直接编制计划",
				iconCls : 'save-icon',
				handler : function(obj) {
					callBackMethod([],select_quotations_win);
					
				}
	         },*/{
				text : "确  定",
				iconCls : 'save-icon',
				handler : function(obj) {
					//currencyName,customerCode,taxRate,sellerName
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择加工订单!");
						return ;
					}

					callBackMethod(arr,select_quotations_win);
					
				}
	         },{
				text : "取  消",
				iconCls : 'save-icon',
				handler : function() {
					
					select_quotations_win.close();
				}
	         }]
        });
		select_quotations_win.show(this);

		var obj2 = searchForm.getForm().getValues();
		if(initParams){
			Ext.applyIf(obj2,initParams);
		}
		
		var seachParams = {searchStr:Ext.encode(obj2),orderType:obj2.orderType};
		for(var i in seachParams){
			contractListStore.setBaseParam(i, seachParams[i]);
		}
		contractListStore.load();
}


