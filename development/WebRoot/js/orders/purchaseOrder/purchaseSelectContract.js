Ext.namespace('Ext.ffc.PurchaseOrder');

Ext.ffc.PurchaseOrder.cOContractGrid =  Ext.extend(Ext.grid.GridPanel,{
		store:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			var proxy = new Ext.data.HttpProxy({url: PATH + "/contractOrder/contractList.do?conType=orderOut&leaf=" + this.leaf});
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
											{name: 'editDate', type: 'string',mapping:"editDateString"},
											{name: 'userId', type: 'string',mapping:"userId"},
											{name: 'userName', type: 'string',mapping:"userName"},
											{name: 'ownContactPerson', type: 'string',mapping:"ownContactPerson"},
											{name: 'cusLockStatus', type: 'string',mapping:"cusLockStatus"}
											   ]);
			var reader = new Ext.data.JsonReader({ totalProperty: "totalProperty",root: "root"}, Bill);
			this.store = new Ext.data.Store({
				remoteSort : true,
				proxy: proxy,
				reader: reader,
				listeners : {
					load:function(){
						setRowLockStatus(this);
					},scope:this
				}
			});
			this.store.load({params: {start: 0, limit: 15}});
			Ext.ffc.PurchaseOrder.cOContractGrid.superclass.constructor.call(this, {
				height:520,
				store:this.store,
				columns: [
					 new Ext.grid.RowNumberer(),
					{header: "id",dataIndex: "id",width: 40,hidden:true},
					{header: "合同编号",dataIndex:'contractCode' ,width: 230,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
					{header: "卖方",dataIndex:'sellerName',width: 100,hidden:true},
					{header: "币别id",dataIndex:'currencyId',width: 100,hidden:true},
					{header: "币别",dataIndex:'currencyName',width: 100,hidden:true},
					{header: "紧急度",dataIndex:'urgentLevel', width: 100,hidden:true},
					{header: "客户编号",dataIndex:'customerCode',width: 60,hidden:true},
					{header: "合同类型",dataIndex:'contractType' ,width: 100,hidden:true},
					{header: "客户名称",dataIndex:'customerName' ,width: 150,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
					{header: "状态",dataIndex:'status' ,width: 100,hidden:true},
					{header: "签订日期",dataIndex:'signDate',width: 100,sortable:true},
					{header: "签订地点",dataIndex:'signAddress' ,width: 100,hidden:true},
					{header: "参照标准",dataIndex:'reference' ,width: 100,sortable:true},
					{header: "运输方式",dataIndex:'trafficMode'  ,width: 60,hidden:true},
					{header: "结算方式id",dataIndex:'closingAccountModeId', width: 100,hidden:true},
					{header: "结算方式",dataIndex:'closingAccountMode' ,width: 100,sortable:true},
					{header: "交货地点和方式",dataIndex:'deliveryAddressType',width: 100,sortable:true},
					{header: "其他约定",dataIndex:'otherConvention',width: 100,sortable:true},	
					{header: "合同生效条件",dataIndex:'effectConditions',width: 100,sortable:true},
					{header: "货品金额", dataIndex:'productMoney',width: 100,sortable:true},
					{header: "税率", dataIndex:"taxRate",width: 60,sortable:true},
					{header: "税金", dataIndex:'taxMoney',width: 70,sortable:true} ,
					{header: "价税合计", dataIndex:'totalMoney',width: 100,sortable:true},
					{header: "整体折扣率", dataIndex:'overallRebate',width: 60,sortable:true},
					{header: "最终金额", dataIndex:'finalMoney',width: 100,sortable:true}, 
					{header: "备注", dataIndex:'memo',width: 60,hidden:false},
					{header: "编制时间", dataIndex:'editDate',width: 100,sortable:true},
					{header: "编制人Id", dataIndex:'userId',width: 60,hidden:true}, 
					{header: "编制人", dataIndex:'userName',width: 100,sortable:true},
					{header: "我方负责人", dataIndex:'ownContactPerson',width: 100,sortable:true}
				],
				bbar: new Ext.PagingToolbar({
					store: this.store,
					pageSize: 15,
					displayInfo: true,
					displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
					emptyMsg: "<i>没有数据</i>"
				}),
				listeners : {
					sortchange : function(grid, sortInfo ){
						setRowLockStatus(grid);
					}
				}
			})
		}
	})
function setRowLockStatus(grid){
	var s = grid.getStore();
	for(var rowIndex = 0,len = s.getCount();rowIndex < len ; rowIndex++){
		var r = s.getAt(rowIndex);
		if(r.data.cusLockStatus * 1 == 1){
			grid.getView().getRow(rowIndex).style.backgroundColor = '#FFCCFF';
			grid.getView().getRow(rowIndex).style.color = '#FF0000';
		}
	}
}	

Ext.ffc.PurchaseOrder.cOContractTree =  Ext.extend(Ext.grid.GridPanel,{
		contractId:null,
		store:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
				
			var proxy = new Ext.data.HttpProxy({url:PATH + "/contractOrder/contractDetail.do" });
			var Bill = Ext.data.Record.create([
											{name: 'brandCode', type: 'string',mapping:"brandCode"},
											{name: 'productCode', type: 'string',mapping:"productCode"},
											{name: 'productName', type: 'string',mapping:"productName"},
											{name: 'productUnit' , type: 'string',mapping:"productUnit"},
											{name: 'amount', type: 'string',mapping:"amount"},
											{name: 'remainAmount', type: 'int',mapping:"remainAmount"},
											{name: 'productBrand', type: 'string',mapping:"productBrand"},
											{name: 'price', type: 'int',mapping:"price"},
											{name: 'parentToolsId' , type: 'string',mapping:"parentToolsId"},
											{name: 'toolsId', type: 'string',mapping:"toolsId"},
											{name: 'leaf', type: 'int',mapping:"leaf"},
											{name: 'id', type: 'string',mapping:"id"},
											{name: 'contractProjectSortId', type: 'string',mapping:"contractProjectSortId"},
											{name: 'proSortName', type: 'string',mapping:"proSortName"},
											{name: 'projectCode', type: 'string',mapping:"projectCode"},
											{name: 'serialNumber', type: 'string',mapping:"serialNumber"},
											{name: 'deliveryDate', type: 'string',mapping:"deliveryDate"},
											{name: 'toolsTypeName', type: 'string',mapping:"toolsTypeName"}
											]);
			var reader = new Ext.data.JsonReader({root: "root"}, Bill);
			this.store = new Ext.data.Store({
				proxy: proxy,
				reader: reader
			});
			this.store.baseParams.contractId = this.contractId;
			this.store.baseParams.leaf = this.leaf;
			
			this.store.load({params: {start: 0, limit: 15}});
			Ext.ffc.PurchaseOrder.cOContractTree.superclass.constructor.call(this, {
				height:520,
				store:this.store,
				sm:new Ext.grid.CheckboxSelectionModel(),
				columns: [
					 new Ext.grid.RowNumberer(),
					 {header:'',
							width:40,
							resizable : true,
							dataIndex:'leaf',
										renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
											if(!value) {
												return '<img src="' + PATH + '/extjs/resources/images/default/tree/folder.gif" style="cursor:hand" onclick="new QuotationManager.ProToolsListWindow({\'nodeId\' : \'' + record.get('toolsId') + '\'}).show();">';
											} else {
												return '<img src="' + PATH + '/extjs/resources/images/default/tree/leaf.gif">';
											}
										}
					},
					{header:'序号',width:70,dataIndex:'serialNumber',sortable:true},
					{header:'项目号',width:70,dataIndex:'projectCode',sortable:true},
					{header:'工具牌号',width:220,dataIndex:'brandCode',sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
					{header:'货品编号',width:100,dataIndex:'productCode',sortable:true},
					{header:'名称',width:100,dataIndex:'productName',sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
					{header:'计量单位',width:70,dataIndex:'productUnit',sortable:true},
					{header:'合同数量',width:70,dataIndex:'amount',sortable:true},
					{header:'剩余采购数量',width:100,dataIndex:'remainAmount',sortable:true},
					{header:'品牌',width:100,dataIndex:'productBrand',sortable:true},
					{header:'产品类别',width:100,dataIndex:'toolsTypeName',sortable:true},
					{header:'采购价格',width:70,dataIndex:'price',sortable:true},
					{header:'交货期',width:90,dataIndex:'deliveryDate',sortable:true},
					{header:'父ID',width:0,hidden : true,dataIndex:'parentToolsId'},
					{header:'工具ID',width:0,hidden : true,dataIndex:'toolsId'},
					{header:'叶子节点',width:0,hidden : true,dataIndex:'leaf'},
					{header:'ID',width:0,dataIndex:'id',hidden : true},
					{header:'合同分_主键',width:0,dataIndex:'contractProjectSortId',hidden : true},
					{header:'合同分名称',width:200,dataIndex:'proSortName',sortable:true}
				],
				listeners : {
					sortchange : function(grid, sortInfo ){
						setRowLockStatus(grid);
					}
				}
			})
		}
	})

Ext.ffc.PurchaseOrder.cOContractSelectForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.ffc.PurchaseOrder.cOContractSelectForm.superclass.constructor.call(this, {
	        labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
	        frame:true,labelWidth:55,monitorValid:false,
	        items:[
	           {layout:'column',border:false,labelSeparator:':',frame : true,
	           defaults:{layout: 'form',border:false,columnWidth:.25},
	           bbar : ['->',{
			           		text : "搜  索",
			           		iconCls : 'icon-search',
			           		handler : function() {
			           			this.fireEvent('search',this, this.getValues());
			           		},scope : this
		           		},
		           		'-',{
			           		text : "重  置",
			           		iconCls : 'icon-reset',
			           		handler : function () {
			           			this.getForm().reset();
			           		},scope : this
	           		}],
	           items:[
	        {items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'100%'}]},
				  {items: [{xtype:'textfield',fieldLabel: '客户名称',name: 'customerName',anchor:'100%'}]},
				  {items: [{xtype:'datefield',fieldLabel: '编制日期',name: 'startTime',anchor:'100%',format:'Y-m-d'}]},
				  {items: [{xtype:'datefield',fieldLabel: '至',name: 'endTime',anchor:'100%',format:'Y-m-d'}]}
	           ]//items
	          }
	        ],
			keys : {
				key:Ext.EventObject.ENTER,
				fn:function(btn,e){
					this.fireEvent('search',this, this.getValues());
				},
				scope : this
			}
		})
		this.addEvents('search');
	},
	getValues : function() {
	var record = this.getForm().getValues();
	return record;
	}
})

Ext.ffc.PurchaseOrder.cOContractWin = Ext.extend(Ext.Window,{
		contract_list:null,
		selectForm2:null,
		cOContractTree:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.cOContractTree = new Ext.ffc.PurchaseOrder.cOContractTree({leaf:this.leaf});
			this.selectForm2 = new Ext.ffc.PurchaseOrder.cOContractSelectForm();
			this.contract_list = new Ext.ffc.PurchaseOrder.cOContractGrid({leaf:this.leaf});
			var record = null;
			
			this.contract_list.on('rowclick',function(grid,rowIndex,e) {
				var s = grid.getStore();
				record = s.getAt(rowIndex);
				this.cOContractTree.store.setBaseParam('contractId',record.id);
				this.cOContractTree.store.load({params:{start:0,limit:15,id:record.id}});
			},this);
			
			this.contract_list.on('rowdblclick',function(grid,rowIndex,e){//双击合同
				var s = grid.getStore();
				var record = s.getAt(rowIndex);
				this.callBackMethod({contractId:record.get("id"),customerCode:record.get("customerCode"),contractCode:record.get("contractCode")});
				
			},this);
			
			this.cOContractTree.on('rowdblclick',function(grid,rowIndex,e) {//双击合同明细
				var r = grid.getStore().getAt(rowIndex);
				var toolsTypeName = grid.getStore().baseParams.toolsTypeName;
				if(!toolsTypeName){
					  toolsTypeName = '';
				}
				this.callBackMethod({contractId:record.get("id"),customerCode:record.get("customerCode"),contractCode:record.get("contractCode"),productBrand:r.get('productBrand'),toolsTypeName:toolsTypeName});
			},this);
			
			Ext.ffc.PurchaseOrder.cOContractWin.superclass.constructor.call(this, {
				renderTo: Ext.getBody(),                                                        
				title:"选择合同",                                                               
				height:530,                                                                     
				width:800,                                                                                                                             
//				modal:true,                                                                     
				plain:true,    
				maximizable:true,
				draggable:true,    
				resizable:false,
				layout: 'border',  
				listeners : {
					'render' : function() {
						//监听搜索事件。
						this.selectForm2.on({
							'search' : function(_form, _values) {
								var _grid = this.contract_list.store;
								var searchStr = _values;
								_grid.baseParams.searchStr = Ext.encode(searchStr);
								_grid.reload();
							},
							scope : this
						})
					}
				},
			    //bbar :[],
				buttons : [{xtype: 'radiogroup',width:'240',style: 'background-color:#0099cc',fieldLabel: '产品类别：',
					items:[
						{boxLabel: '全部', name: 'cb-auto-1',checked:true,inputValue:''},
						{boxLabel: '刀片', name: 'cb-auto-1',inputValue:'刀片'},
						{boxLabel: '机加', name: 'cb-auto-1',inputValue:'机加'},
						{boxLabel: '整硬', name: 'cb-auto-1',inputValue:'整硬'},
						{boxLabel: '修磨', name: 'cb-auto-1',inputValue:'修磨'}	
				    ],
					listeners : {
					    change:function(groupObj,checkedObj){
							this.cOContractTree.store.setBaseParam('toolsTypeName',checkedObj.inputValue);
							this.cOContractTree.store.load();
						},scope:this
					}},{                                                                    
					text : "确定",                                                              
					handler : function() {                                                      
					var selectedItem = this.contract_list.getSelectionModel().getCount();            
					var record  = this.contract_list.getSelectionModel().getSelected();    
					var toolsTypeName = this.cOContractTree.getStore().baseParams.toolsTypeName;
//					var _record  = this.Ext.ffc.PurchaseOrder.cOContractTree.getSelectionModel().getSelected();      
						if (1!=selectedItem) {                                                  
							Ext.Msg.alert('系统提示', '请选择一条合同记录!');                       
							return;                                                             
						}else{   
							var cusLockStatus = record.get("cusLockStatus");
							//if(cusLockStatus * 1 == 1){
							//	Ext.Msg.alert("消息", "所选中合同,所属客户已经被锁定,不允许做订单!");
							//    return ;
							//}
							this.close(); 
						  if(!toolsTypeName){
						      toolsTypeName = '';
						  }
						  this.callBackMethod({contractId:record.get("id"),customerCode:record.get("customerCode"),contractCode:record.get("contractCode"),toolsTypeName:toolsTypeName});
						}                                                                       
					},scope : this                                                                
				 },{                                                                            
					text : "取消",                                                              
					handler : function() {                                                      
					 this.close();                            
					},                                                                          
					scope : this                                                                
				 }],                                                                            
				items : [
					{
						region: 'north',
						iconCls:'icon-grid',
						title: '合同查询',
						split: true,
						height : 100,
						collapsible: true,
						margins: '5 5 5 5',
						layout:'fit',
						items : [this.selectForm2]
						
					},{
						region: 'center',
						split: true,
						height : 200,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : [this.contract_list]
					},{
						region: 'south',
						split: true,
						height : 150,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : [this.cOContractTree]
					}
				]                                                         
			})
		}
	})
