Ext.ffc.select_quotations = function (quotationType,status,callBackMethod){

//搜索条件 start
var	searchForm = new Ext.FormPanel({
		width : 810,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:70,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.45},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var obj2 = searchForm.getForm().getValues();
								obj2['status'] = status;
								obj2['editorName'] = '';
								obj2['userName'] = '';
								obj2['memo'] = '';
							var seachParams = {start : 0, limit : 10,'quotationType':quotationType,searchStr:Ext.encode({data:obj2})};
							for(var i in seachParams){
									grid.getStore().setBaseParam(i, seachParams[i]);
							}
							grid.getStore().load()
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
              {items: [{xtype:'textfield',fieldLabel: '报价单号',name: 'quotationCode',anchor:'90%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '客户名称',name: 'customerName',anchor:'90%'}]},
              //{items: [{xtype:'hidden',fieldLabel: '状态',name: 'statusText',anchor:'90%',value:'审批通过/提交合同',readOnly:true}]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endDate', fieldLabel: '日期始',name: 'beginDate', format:'Y-m-d', id:'beginDate',emptyText:'',anchor:'90%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'beginDate',fieldLabel: '至',name: 'endDate', format:'Y-m-d', id:'endDate', emptyText:'',labelSeparator:'',anchor:'90%'}]}
           ]//items
          }
        ],//items
		listeners : {
			'render': function(p) {
				p.getEl().on('keypress', function(){
					if(window.event.keyCode == 13){
						var obj2 = searchForm.getForm().getValues();
								obj2['status'] = status;
								obj2['editorName'] = '';
								obj2['userName'] = '';
								obj2['memo'] = '';
							var seachParams = {start : 0, limit : 10,'quotationType':quotationType,searchStr:Ext.encode({data:obj2})};
							for(var i in seachParams){
									grid.getStore().setBaseParam(i, seachParams[i]);
							}
							grid.getStore().load()
					}
				});
			}
		}
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,{
		header : '报价单号',
		width:170,
		dataIndex : 'quotationCode'
	}, {
		header : '客户名称',
		width:170,
		dataIndex : 'customerName'
	},{
		header : '卖方',
		width:80,
		dataIndex : 'sellerName'
	},{
		header : '制单人',
		width:80,
		dataIndex : 'editorName'
	},{
		header : '报价日期',
		width:130,
		dataIndex : 'quotationDate'
	},{
		header : '编制日期',
		width:130,
		dataIndex : 'editTimeStr'
	},{
		header : '交货方式',
		width:100,
		dataIndex : 'deliveryType'
	},{
		header : '币别',
		width:50,
		dataIndex : 'currencyName'
	},{
		header : '货品金额',
		width:100,
		dataIndex : 'productMoney'
	},{
		header : '税率',
		width:80,
		dataIndex : 'taxRate'
	},{
		header : '税金',
		width:80,
		dataIndex : 'taxMoney'
	},{
		header : '税价合计',
		width:100,
		dataIndex : 'totalMoney'
	},{
		header : '付款条件',
		width:100,
		dataIndex : 'paymentCondition'
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	},{
		header : '客户联系人',
		hidden : true,
		dataIndex : 'cusContactPerson'
	},{
		header : '客户编号',
		hidden : true,
		dataIndex : 'customerCode'
	}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var ds = new Ext.data.JsonStore({
		url : PATH + '/generalQuo/listAction.do',
		root : 'quoList',
		totalProperty : 'totalProperty',
		fields : ['quotationCode', 'customerName', 'editorName', 'quotationDate', 'deliveryType', 'currencyName', 'productMoney', 'taxRate',
				 	'taxMoney','totalMoney', 'paymentCondition', 'memo', 'id','cusContactPerson','customerCode','sellerName','editTimeStr']
	});
	
	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '无报价信息！'
        });
var grid = new Ext.grid.GridPanel({
		//bodyStyle:'width:100%',
		height : 300,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
        view : gv,
		ds : ds,
		cm : cm,
		sm : sm,
		listeners : {
			'rowdblclick' : function( grid ,  rowIndex,  e) {
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择报价单!");
						return ;
					}
					callBackMethod(arr,select_quotations_win);
			}
		},
		bbar : new Ext.PagingToolbar({
				pageSize : 10,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '参照报价单',
			width:820,
			height:495,
			maximizable :true,
			buttonAlign:'right',
            items: [
            {
                region: 'north',
				layout:'fit',
                iconCls:'icon-grid',
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
				layout:'fit',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                margins: '-5 5 5 5',
                items : [grid]
            }],
			buttons: [{
				text : "确  定",
				handler : function(obj) {
					//currencyName,customerCode,taxRate,sellerName
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择报价单!");
						return ;
					}
					callBackMethod(arr,select_quotations_win);
					
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
			obj2['status'] = status;
			obj2['editorName'] = '';
			obj2['userName'] = '';
			obj2['memo'] = '';
		var seachParams = {start : 0, limit : 10,'quotationType':quotationType,searchStr:Ext.encode({data:obj2})};
		for(var i in seachParams){
			grid.getStore().setBaseParam(i, seachParams[i]);
		}
		grid.getStore().load()
}