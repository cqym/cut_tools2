Ext.ffc.select_reserves_products = function (callBackMethod,isHadId){

//搜索条件 start
var statusCombox = new Ext.ffc.ContractStatusComboBox();
	statusCombox.setValue("执行中");
	statusCombox.disabled = true;
var	searchForm = new Ext.FormPanel({
		width : 810,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:70,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.3},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var obj2 = searchForm.getForm().getValues();
								obj2['status'] = 4;
							var seachParams = obj2;
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
              {items: [{xtype:'textfield',fieldLabel: '货品编号',name: 'productCode',anchor:'90%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '货品名称',name: 'productName',anchor:'90%'}]},
              {items: [{xtype:'textfield',fieldLabel: '品牌',name: 'productBrand',anchor:'90%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '牌号',name: 'brandCode',anchor:'90%'}]}
           ]//items
          }
        ]//items
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel();//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,
	{
		header : '库存代码',
		dataIndex : 'reserveCode'
	},{
		header : '货品编号',
		dataIndex : 'productCode'
	}, {
		header : '牌号',
		dataIndex : 'brandCode'
	},{
		header : '货品名称',
		dataIndex : 'productName',
		width:100
	},{
		header : '计量单位',
		dataIndex : 'productUnit'
	},{
		header : '库存数量',
		dataIndex : 'amount'
	},{
		header : '组别',
		dataIndex : 'productSort'
	},{
		header : '品牌',
		dataIndex : 'productBrand'
	},{
		header : '来源',
		dataIndex : 'productSource'
	},{
		header : '货位',
		dataIndex : 'productPosition'
	},{
		header : '备注',
		dataIndex : 'memo'
	},{
		header : 'id',
		hidden : true,
		dataIndex : 'id'
	},{
		header : 'tools_id',
		hidden : true,
		dataIndex : 'tools_id'
	},{
		header : 'parentToolsId',
		hidden : true,
		dataIndex : 'parentToolsId'
	},{
		header : 'leaf',
		hidden : true,
		dataIndex : 'leaf'
	},{
		header : '客户编号',
		hidden : true,
		dataIndex : 'customerCode'
	}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var contractListStore = new Ext.data.JsonStore({
		url : PATH + '/outStock/outStockEditAction.do?ffc=consultReserveInfors',
		root : 'items',
		totalProperty : 'totalCount',
		//autoLoad : true,
		fields : ['id', 'productCode', 'brandCode', 'toolsId', 'parentToolsId', 'leaf', 'productName', 'productUnit',
				 	'reserveCode','amount', 'price', 'productSort',"currencyName",'productBrand','productSource','productPosition','memo']
	});
	
	var gv = new Ext.grid.GridView({
        	forceFit:true,
            autoFill :true,
            deferEmptyText : false,
            emptyText : '无合同信息！'
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
				pageSize : 10,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : contractListStore
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择库存',
			width:820,
			height:495,
			buttonAlign:'right',
			maximizable :true,
            listeners : {
            	'render' : function() {
            		
            		//grid.render();
            		//_store = grid.getStore();
            		//_store.on("beforeLoad", function() {
		           		//this.baseParams.searchStr = Ext.util.JSON.encode(_searchRecord);
		           //	})
            	}
            },
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
				layout: 'fit',
                //contentEl: 'south',
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
                //contentEl: 'quogrid',
				layout: 'fit',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 5 5 5',
                items : [grid]
            }],
			buttons: [{
				text : "确  定",
				handler : function(obj) {
					//currencyName,customerCode,taxRate,sellerName    -----------得到行号？
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择库存产品!");
						return ;
					}
					var store = grid.getStore();
					var dataArr = [];
					for(var i = 0;i < arr.length;i++){
						if(isHadId(arr[i].id)){
							Ext.Msg.alert("消息", "在所选库存产品中,部分产品已在本出库单中存在!");
							var rnum = -1;
							for(var j = 0,len = store.getCount();j < len ;j++ )
							{
								if(store.getAt(j).id == arr[i].id){
								    rnum = j;
									break;
								}; 
							}
							if(rnum >= 0){
								grid.getView().getRow(rnum).style.backgroundColor = '#66FFCC';
								grid.getView().getRow(rnum).style.color = '#FF0000';
							}
							return ;
						}
						dataArr.push(arr[i].data);
					}
					callBackMethod(dataArr,select_quotations_win);
					
				}
	         },{
				text : "取  消",
				handler : function() {
					
					select_quotations_win.close();
				}
	         }]
        });
		select_quotations_win.show(this);

		var obj2 = searchForm.getForm().getValues();
			obj2['status'] = 4;
		var seachParams = obj2;
		for(var i in seachParams){
			contractListStore.setBaseParam(i, seachParams[i]);
		}
		contractListStore.load();
}


