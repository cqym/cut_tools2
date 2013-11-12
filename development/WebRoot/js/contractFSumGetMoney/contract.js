Ext.BLANK_IMAGE_URL = PATH + "/extjs/resources/images/default/s.gif";
	var resultStr = [];
	var pageInfoLabel = new Ext.form.Label();
	    pageInfoLabel['setInfor'] = function(){
	    	
	        this.setText("      当前页: " + pageObject.currentPage + "/" + pageObject.pageCount);
	    }
	     
	  	   
Ext.onReady(function(){

	var record = Ext.data.Record.create([
	    {name: 'contractCode'},
	    {name: 'customerName'},
	    {name: 'startTime'},
		{name: 'endTime'}
	]);
 
	var contractparmRecord = new record();

	var proxy = new Ext.data.HttpProxy({url: PATH + "/contract/contractAccountsAction.do?ffc=contractList"});

	var Bill = Ext.data.Record.create([
									{name: 'id', type: 'string',mapping:"id"},
									{name: 'contractCode', type: 'string',mapping:"contractCode"},
									{name: 'sellerName', type: 'string',mapping:"sellerName"},
								//  {name: 'currencyId', type: 'string',mapping:"currencyId"},
									{name: 'currencyName' , type: 'string',mapping:"currencyName"},
									{name: 'signDate', type: 'string',mapping:"signDate"},
									{name: 'urgentLevel', type: 'int',mapping:"urgentLevel"},
									{name: 'customerCode', type: 'string',mapping:"customerCode"},
									{name: 'contractType', type: 'int',mapping:"contractType"},
									{name: 'customerName' , type: 'string',mapping:"customerName"},
									{name: 'status', type: 'string',mapping:"status"},
									{name: 'signAddress', type: 'string',mapping:"signAddress"},
									{name: 'reference', type: 'string',mapping:"reference"},
									{name: 'trafficMode', type: 'string',mapping:"trafficMode"},
									{name: 'closingAccountModeId', type: 'string',mapping:"closingAccountModeId"},
									{name: 'closingAccountMode', type: 'string',mapping:"closingAccountMode"},
									{name: 'deliveryAddressType', type: 'string',mapping:"deliveryAddressType"},
									{name: 'otherConvention', type: 'string',mapping:"otherConvention"},
									{name: 'effectConditions', type: 'string',mapping:"effectConditions"},
									{name: 'productMoney', type: 'string',mapping:"productMoney"},
									{name: "taxRate", type: "string", mapping: "taxRate"},
									{name: 'taxMoney', type: 'string',mapping:"taxMoney"},
									{name: 'totalMoney', type: 'string',mapping:"totalMoney"},
									{name: 'overallRebate', type: 'string',mapping:"overallRebate"},
									{name: 'finalMoney', type: 'string',mapping:"finalMoney"},
									{name: 'memo', type: 'string',mapping:"memo"},
									{name: 'editDate', type: 'string',mapping:"editDate"},
									{name: 'userId', type: 'string',mapping:"userId"},
									{name: 'userName', type: 'string',mapping:"userName"}
	                                   ]);
	var reader = new Ext.data.JsonReader({ totalProperty: "totalCount",root: "items"}, Bill);
	var store = new Ext.data.Store({
		proxy: proxy,
		reader: reader
	});
	store.load({params: {start: 0, limit: 20}});
	
	
	var cm = new Ext.grid.ColumnModel([
	                           	    new Ext.grid.RowNumberer(),
	                           	    {header: "id",dataIndex: "id",width: 40,hidden:true},
	                           		{header: "合同编号",dataIndex:'contractCode' ,width: 230,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
	                           		{header: "卖方",dataIndex:'sellerName',width: 100,hidden:true},
	                           		{header: "币别id",dataIndex:'currencyId',width: 100},
	                           		{header: "币别",dataIndex:'currencyName',width: 100},
	                           		{header: "签订日期",dataIndex:'signDate',width: 100},
	                           		{header: "紧急度",dataIndex:'urgentLevel', width: 100},
	                           		{header: "客户编号",dataIndex:'customerCode',width: 60,hidden:true},
									{header: "合同类型",dataIndex:'contractType' ,width: 100},
	                           		{header: "客户名称",dataIndex:'customerName' ,width: 100,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
	                           		{header: "状态",dataIndex:'status' ,width: 100},
	                           		{header: "签订地点",dataIndex:'signAddress' ,width: 100,hidden:true},
	                           		{header: "参照标准",dataIndex:'reference' ,width: 60},
	                           		{header: "运输方式",dataIndex:'trafficMode'  ,width: 60},
	                           		{header: "结算方式id",dataIndex:'closingAccountModeId', width: 100},
	                           		{header: "结算方式",dataIndex:'closingAccountMode' ,width: 100},
	                           		{header: "交货地点和方式",dataIndex:'deliveryAddressType',width: 100,hidden:true},
	                           		{header: "其他约定",dataIndex:'otherConvention',width: 100},	
	                           		{header: "合同生效条件",dataIndex:'effectConditions',width: 100,hidden:true},
	                           		{header: "货品金额", dataIndex:'productMoney',width: 60},
									{header: "税率", dataIndex:"taxRate",width: 60},
									{header: "税金", dataIndex:'taxMoney',width: 60} ,
									{header: "价税合计", dataIndex:'totalMoney',width: 60},
									{header: "整体折扣率", dataIndex:'overallRebate',width: 60},
									{header: "最终金额", dataIndex:'finalMoney',width: 60}, 
									{header: "备注", dataIndex:'memo',width: 60},
									{header: "编制时间", dataIndex:'editDate',width: 60},
									{header: "编制人Id", dataIndex:'userId',width: 60}, 
									{header: "编制人", dataIndex:'userName',width: 60}
	                           	]);

	
	cut_tools.contract_account.contract_list =  Ext.extend(Ext.grid.GridPanel,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			cut_tools.contract_account.contract_list.superclass.constructor.call(this, {
				height:500,
				store: store,
				cm: cm,
				tbar:new Ext.Toolbar({  
					items:[new Ext.form.Label({
						html : "合同编号:&nbsp;"
					
					}), new Ext.form.TextField({
						name : 'contractCode',
						id : 'contractCode',
						listeners : {
							'change' : function() {
								contractparmRecord.contractCode = this.getValue();
							}
						}
					
					}), '-', new Ext.form.Label({
						html : "客户:&nbsp;"
					
					}), new Ext.form.TextField({
						name : 'customerName',
						id : 'customerName',
						listeners : {
							'change' : function() {
								contractparmRecord.customerName = this.getValue();
							}
						}
					
					}),'-', new Ext.form.Label({
						html : "编制日期:&nbsp;"
					
					}),new Ext.form.DateField({
					name: 'startTime',
					format:'Y-m-d',
					listeners : {
						'change' : function() {
							contractparmRecord.startTime = this.getValue();
						}
					}
				  }),'至',new Ext.form.DateField({
					name: 'endTime',
					format:'Y-m-d',
					listeners : {
						'change' : function() {
							contractparmRecord.endTime = this.getValue();
						}
					}
				  }),'-',{  
							text:'搜索'  
							,iconCls:'icon-key',
							listeners : {
								'click' : function(){
									var _searchStr = Ext.util.JSON.encode(contractparmRecord);

									store.baseParams = {searchStr:_searchStr};
									store.reload();
								}
							}
							
						}, '-'  
					]  
				}),
				bbar: new Ext.PagingToolbar({
					store: store,
					pageSize: 20,
					displayInfo: true,
					displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
					emptyMsg: "<i>没有数据</i>"
				})
			})
		}
	})

	cut_tools.contract_account.contract_win = Ext.extend(Ext.Window,{
		contract_list:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.contract_list = new cut_tools.contract_account.contract_list();
			cut_tools.contract_account.contract_win.superclass.constructor.call(this, {
				renderTo: Ext.getBody(),                                                        
				title:"选择合同",                                                               
				height:500,                                                                     
				width:800,                                                                                                                             
				modal:true,                                                                     
				plain:true,                                                                     
				draggable:false,                                                                
				layout:"fit",                                                                   
				buttons : [{                                                                    
					text : "确定",                                                              
					handler : function() {                                                      
					var selectedItem = this.contract_list.getSelectionModel().getCount();            
					var record  = this.contract_list.getSelectionModel().getSelected();              
						if (1!=selectedItem) {                                                  
							Ext.Msg.alert('系统提示', '请选择一条记录!');                       
							return;                                                             
						}else                                                                   
						{                                                                       
							this.close();  
							var records = Ext.data.Record.create([
								{name: 'contractInforId'},
								{name: 'contractCode'},
								{name: 'customerCode'},
								{name: 'customerName'},
								{name: 'contractMoney'}
							]);
							var contractRecord = new records({
									contractInforId:record.get('id'),
									contractCode:record.get('contractCode'),
									customerCode:record.get('customerCode'),
									customerName:record.get('customerName'),
									contractMoney:record.get('finalMoney')
							});
							var win = new cut_tools.contract_account._addwin();
							win.on('show',function(){
									win.nav2.addGrid.getForm().loadRecord(contractRecord);
								})	
							win.show();              
						}                                                                       
					},                                                                          
					scope : this                                                                
				 },{                                                                            
					text : "取消",                                                              
					handler : function() {                                                      
					 this.close();                            
					},                                                                          
					scope : this                                                                
				 }],                                                                            
				items : [this.contract_list]                                                         
			})
		}
	})
})
