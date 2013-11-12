Ext.ffc.select_quotations = function (callBackMethod,option){

//搜索条件 start
var statusCombox = new StatusCombox();
	statusCombox.setValue('4');
	statusCombox.disabled = true;
var _quotationType = -1;
var _yuDingWeiZhuanHeTong = null;
var _status = '4';
var _transferContract = '0';//未转，和部分转
var quoTypeComboBox = new Ext.ffc.QuoTypeComboBox({
	listeners : {
		'change' : function(combox,newValue,oldValue) {
			var arr = searchForm.find('hiddenName','status');
			var objStatus = null;
			if(arr.length > 0){
			    objStatus = arr[0];
			}
		  if(newValue == 3 || newValue == 4){//预订报价，试刀报价
			    if(objStatus != null){
				     objStatus.setValue('2,6,7');
						 _status =  '2,5,6,7';
						 _quotationType = newValue;
						 _yuDingWeiZhuanHeTong = '1';
						 _transferContract = '0,1';
				  }
			}else{
				 if(objStatus != null){
				     objStatus.setValue('4');
					  _status =  '4';
					 _quotationType = -1;
					 _yuDingWeiZhuanHeTong = null;
					 _transferContract = '0';
				 }
			}
			loadData();
		}
	},
	value:0
});
var	searchForm = new Ext.FormPanel({
		width : 1000,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:70,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.3},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							loadData();
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
			        {items: [{xtype:'textfield',fieldLabel: '客户',name: 'customerName',anchor:'90%'}]},
              {items: [statusCombox]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endDate', fieldLabel: '日期起',name: 'beginDate', format:'Y-m-d', id:'beginDate',emptyText:'',anchor:'90%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'beginDate',fieldLabel: '至',name: 'endDate', format:'Y-m-d', id:'endDate', emptyText:'',labelSeparator:'',anchor:'90%'}]},
			        {items: [quoTypeComboBox]}
           ]//items
          }
        ]//items
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel();//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,{
		header : '报价单类型',
		width:80,
		dataIndex : 'quotationType',
		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [[0,'普通报价'],
							[1,'项目报价'],
							[2,'零星报价'],[3,'预订报价'],[4,'试刀报价']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
		}
	},{
		header : '报价单号',
		width:180,
		dataIndex : 'quotationCode'
	}, {
		header : '客户名称',
		width:150,
		dataIndex : 'customerName'
	},{
		header : '卖方',
		width:60,
		dataIndex : 'sellerName'
	},{
		header : '制单人',
	    width:60,
		dataIndex : 'editorName'
	},{
		header : '报价日期',
		width:80,
		dataIndex : 'quotationDate'
	},{
		header : '交货方式',
		width:100,
		dataIndex : 'deliveryType'
	},{
		header : '币别',
		width:60,
		dataIndex : 'currencyName'
	},{
		header : '货品金额',
		width:100,
		dataIndex : 'productMoney'
	},{
		header : '税率',
		width:50,
		dataIndex : 'taxRate'
	},{
		header : '税金',
		width:100,
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
		header : '备注',
		width:100,
		dataIndex : 'memo'
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
		//autoLoad : true,
		fields : ['quotationCode', 'customerName', 'editorName', 'quotationDate', 'deliveryType', 'currencyName', 'productMoney', 'taxRate',
				 	'taxMoney','totalMoney', 'paymentCondition', 'memo', 'id','cusContactPerson','customerCode','sellerName','quotationType']
	});

	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '无报价信息！'
        });
var grid = new Ext.grid.GridPanel({
	    width : 1000,
		bodyStyle:'width:100%',
		height : 300,
		//autoHeight : true,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		//el : 'quogrid',
        view : gv,
		ds : ds,
		cm : cm,
		sm:sm,
		bbar : new Ext.PagingToolbar({
				pageSize : 5,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '参照报价单',
			width : Ext.getBody().getWidth(),
			height:495,
			maximizable :true,
			modal : true,
			buttonAlign:'right',
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
				layout:'fit',
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
				layout:'fit',
                //contentEl: 'quogrid',
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
	         },{
				text : "退  回",
				handler : function() {
					var arr = sm.getSelections();
					var ids = [];
					for(var i = 0;i < arr.length;i++){
						ids.push(arr[i].get("id"));
					}
					if(ids.length == 0){
						Ext.Msg.alert("消息", "请选择要退回的【报价单】!");
						return;
					}
					Ext.MessageBox.confirm('系统提示', '请确认是否要将所选择的【报价单】退回至【已审核状态】!', function(btn){
						if(btn != 'yes'){return ;}
						Ext.Ajax.request({
							method: "post",
							params: { 'ids' : ids},
							url: PATH + "/contract/contractEditAction.do?ffc=unTreadQuo2Audited",
							success: function(response){
								Ext.Msg.alert("消息", "您所选择要【报价单】已经成功退回为【已审核】状态!");
								grid.getStore().reload();
							}
						});
				   });
				}
	         }]
        });
		select_quotations_win.show(this);

		function loadData(){
			var obj2 = searchForm.getForm().getValues();
				obj2['editorName'] = '';
				obj2['userName'] = '';
				obj2['memo'] = '';
				obj2['status'] = _status;
				obj2['transferContract'] = _transferContract;
				
			var seachParams = {start : 0, limit : 10,searchStr:Ext.encode({data:obj2}),quotationType:_quotationType,yuDingWeiZhuanHeTong:_yuDingWeiZhuanHeTong};
			for(var i in seachParams){
					grid.getStore().setBaseParam(i, seachParams[i]);
			}
			grid.getStore().load();
		}
		loadData();
}


