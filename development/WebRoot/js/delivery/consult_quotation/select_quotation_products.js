Ext.ffc.select_Quotation_products = function (quotationInforId,callBackMethod,isHadId){

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
							var seachParams = searchForm.getForm().getValues();
							
							seachParams['start'] = 0;
							seachParams['limit'] = 15;
 						    contractListStore.load({params:seachParams});
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
		header : '报价单分项',
		width:100,
		dataIndex : 'proSortName'
	},{
		header : '项目编号',
		width:80,
		dataIndex : 'projectCode'
	},{
		header : '序号',
		width:80,
		dataIndex : 'serialNumber'
	}, {
		header : '牌号',
		width:120,
		dataIndex : 'brandCode'
	},{
		header : '货品名称',
		dataIndex : 'productName',
		width:120
	},{
		header : '货品编号',
		dataIndex : 'productCode',
		width:100
	},{
		header : '计量单位',
		width:80,
		dataIndex : 'productUnit'
	},{
		header : '报价单数量',
		width:90,
		dataIndex : 'amount'
	},{
		header : '已提数量',
		width:90,
		dataIndex : 'arrivalAmount'
	},{
		header : '交货数量',
		width:90,
		dataIndex : 'deliveryAmount'
	},{
		header : '库存数量',
		width:90,
		dataIndex : 'reserveAmount'
	},{
		header : '品牌',
		width:60,
		dataIndex : 'productBrand'
	},{
		header : 'id',
		hidden : true,
		dataIndex : 'id'
	},{
		header : 'toolsId',
		hidden : true,
		dataIndex : 'toolsId'
	},{
		header : 'parentToolsId',
		hidden : true,
		dataIndex : 'parentToolsId'
	},{
		header : 'leaf',
		hidden : true,
		dataIndex : 'leaf'
	},{
		header : 'reserveInforId',
		hidden : true,
		dataIndex : 'reserveInforId'
	},{
		header : 'quotationProjectSortId',
		hidden : true,
		dataIndex : 'quotationProjectSortId'
	}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var contractListStore = new Ext.data.JsonStore({
		url : PATH + '/outStock/outStockEditAction.do?ffc=consultQuotationProducts&quotationInforId=' + quotationInforId,
		root : 'items',
		totalProperty : 'totalCount',
		//autoLoad : true,
		fields : ['id', 'productCode', 'brandCode', 'toolsId', 'parentToolsId', 'leaf', 'productName', 'productUnit',
				 	'proSortName','amount', 'price', 'projectCode',"serialNumber",'productBrand','memo','reserveAmount',
			'reserveInforId','quotationProjectSortId','orderAmount','arrivalAmount','deliveryAmount']
	});
	
	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '报价单产品信息！'
        });
var grid = new Ext.grid.GridPanel({
		// width : 450,
		bodyStyle:'width:100%',
		//height : 300,
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
				pageSize : 15,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : contractListStore
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择报价单产品',
			width:820,
			height:495,
			buttonAlign:'right',
			maximizable :true,
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
                //title: 'South',
                margins: '-5 5 5 5',
                items : [grid]
            }],
			buttons: [{
				text : "确  定",
				iconCls : 'save-icon',
				handler : function(obj) {
					//currencyName,customerCode,taxRate,sellerName
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择产品!");
						return ;
					}
					var dataArr = [];
					for(var i = 0;i < arr.length;i++){
						var d = arr[i].data;
						if(d.reserveAmount == '' || d.reserveAmount * 1 == 0){
							Ext.Msg.alert("消息", "所选报价单产品中存在库存为0数据,不能执行出库操作!");
							grid.getView().getRow(i).style.backgroundColor = '#FFCCFF';
							grid.getView().getRow(i).style.color = '#FF0000';
						}else if(isHadId(d.id)){
							Ext.Msg.alert("消息", "在所选报价单产品中,部分产品已在本出库单中存在!");
							grid.getView().getRow(i).style.backgroundColor = '#66FFCC';
							grid.getView().getRow(i).style.color = '#FF0000';
						}else{
						    dataArr.push(d);
						}
					}

					if(dataArr.length > 0){
					    callBackMethod(dataArr,select_quotations_win);
					}
					
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
			//obj2['status'] = 4;
			//obj2['editorName'] = '';
		//alert(Ext.encode(obj2));
		     obj2['start'] = 0;
			 obj2['limit'] = 15;
		contractListStore.load({params:obj2});
}


