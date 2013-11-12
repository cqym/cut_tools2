function getContractAccountStore(contractCode){
	return new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({url: PATH + "/contract/contractAccountsAction.do?ffc=viewContractAccountList&searchStr={contractCode:'" + contractCode + "'}"}),
					reader: new Ext.data.JsonReader(
								{ totalProperty: "totalCount",root: "items"}, 
								[
								   {name: 'id', type: 'string',mapping:"id"},
								   {name: 'contractInforId', type: 'string',mapping:"contractInforId"},
								   {name: 'contractCode', type: 'string',mapping:"contractCode"},
								   {name: 'customerCode', type: 'string',mapping:"customerCode"},
								   {name: 'customerName', type: 'string',mapping:"customerName"},
								   {name: 'memo', type: 'string',mapping:"memo"},
								   {name: 'editDate',mapping:"editDate"},
								   {name: 'status', type: 'int',mapping:"status"},
								   {name: 'contractMoney', type: 'string',mapping:"contractMoney"},
								   {name: 'money', type: 'string',mapping:"money"},
								   {name: 'userId', type: 'string',mapping:"userId"},
								   {name: "userName", type: "string", mapping: "userName"},
									{name: 'contractType', type: 'int',mapping:"contractType"}
								]
							)
				});
}

cut_tools.contract_account.index_grid = Ext.extend(Ext.grid.GridPanel,{
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = getContractAccountStore(_cfg.contractCode),
		this.pageToolBbar = new Ext.PagingToolbar({
					store: this.store,
					pageSize: 20,
					displayInfo: true,
					displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
					emptyMsg: "<i>没有数据</i>"
				});
		this.isHidden = _cfg.isHidden;
		
		if(this.isHidden == false || this.isHidden == 'false'){
			this.tbar = [{
						text:'创建合同' + this.titleDef + '记录',
						
						iconCls:'save-icon',
						listeners: {
							'click' : function(obj){
								var g = obj.ownerCt.ownerCt;
								var win = new cut_tools.contract_account._FccEditWin({
									contractAccountInfor:{
										contractInforId:g.contractId,
										contractCode:g.contractCode,
										customerCode:g.customerCode,
										customerName:g.customerName,
										contractMoney:g.finalMoney,
									    contractType:g.contractType
									},
									titleDef:this.titleDef,
									listeners: {
										'close' : function(){
											g.getStore().reload();
										}
									}
									});
								win.show();
							},scope:this
						}
					},{
						xtype:'tbseparator'
						
					},{
						text:'修改合同' + this.titleDef + '记录',
						
						iconCls:'save-icon',
						listeners: {
							'click' : function(obj){
								var g = obj.ownerCt.ownerCt;
								var selectedItem = g.getSelectionModel().getCount();
								var record  = g.getSelectionModel().getSelected();
									if (1!=selectedItem) {
										Ext.Msg.alert('系统提示', '请选择要修改的合同' + this.titleDef + '记录！');
										return;
									}
									var stu = record.get('status');
									if(stu != 0)
									{
										Ext.Msg.alert('系统提示', '该状态下合同' + this.titleDef + '记录不能被修改！');
										return;
									}
									var win = new cut_tools.contract_account._FccEditWin({
											contractAccountInfor : record.data,
											titleDef:this.titleDef,
											listeners : {
												'close' : function(){
													g.getStore().reload();
												}
											}
										});
									win.show();								
							},scope:this
						}
					},{
						xtype:'tbseparator'
						
					},{
						text:'删除合同' + this.titleDef + '记录',
						
						iconCls:'delete-icon',
						listeners: {
							'click' : function(obj){
								var g = obj.ownerCt.ownerCt;
								var selectedItem = g.getSelectionModel().getCount();					
									if (1!=selectedItem) {
										Ext.Msg.alert('系统提示', '请选择要删除的合同' + this.titleDef + '记录！');
										return;
									}
									var record = g.getSelectionModel().getSelected();
									var stu = record.get('status');
									if(stu != 0){
										Ext.Msg.alert('系统提示', '该状态下合同' + this.titleDef + '记录不能被删除！');
										return;
									}
									var titleDef = this.titleDef;
									Ext.MessageBox.show({
										title:'系统提示',
										msg: '请确认要删除合同' + titleDef + '记录!',
										buttons: Ext.MessageBox.OKCANCEL,
										fn: function (btn){
												if(btn == 'ok') {	
													var _id = record.get('id');		
													Ext.Ajax.request({
														url: PATH + '/contract/contractAccountsAction.do?ffc=deleteContractAccount',
														params: { ids: _id },
														success : function(response) {
															 if(response.responseText == true || response.responseText == 'true'){
																Ext.Msg.show({
																	title:'成功提示',
																	msg: '删除成功！',
																	buttons: Ext.Msg.OK,
																	icon: Ext.MessageBox.INFO
																});
																g.getStore().reload();
															 } else {
																Ext.Msg.show({
																	title:'错误提示',
																	msg: '删除失败！',
																	buttons: Ext.Msg.OK,
																	icon: Ext.MessageBox.ERROR
																});
															 } 
														},scope : this
													});
												}
											}
									});													
							},scope:this//click end
						}
					},{
						xtype:'tbseparator'
						
					},{
						text:'确认合同' + this.titleDef + '记录',
						iconCls:'save-icon',
						listeners: {
							'click' : function(obj){
								var g = obj.ownerCt.ownerCt;
								var selectedItem = g.getSelectionModel().getCount();					
									if (1!=selectedItem) {
										Ext.Msg.alert('系统提示', '请选择要确认的合同' + this.titleDef + '记录！');
										return;
									}
									var record = g.getSelectionModel().getSelected();
									var stu = record.get('status');
									if(stu != 0)
									{
										Ext.Msg.alert('系统提示', '该合同' + this.titleDef + '记录已经确认过！');
										return;
									}
								var handleDelete = function (btn){
										var ds = g.getStore() 
										if(btn == 'ok') {	
											var _id = record.get('id');		
											Ext.Ajax.request({
												url: PATH + '/contract/contractAccountsAction.do?ffc=confirmContractAccount',
												params: { ids: _id },
												success : function(response) {
													var responseArray = Ext.util.JSON.decode(response.responseText); 
													 if(responseArray.success == true){
														Ext.Msg.show({
															title:'成功提示',
															msg: responseArray.msg,
															buttons: Ext.Msg.OK,
															icon: Ext.MessageBox.INFO
														});
														ds.reload();
													 } else {
														Ext.Msg.show({
															title:'错误提示',
															msg: responseArray.msg,
															buttons: Ext.Msg.OK,
															icon: Ext.MessageBox.ERROR
														});
													 } 
												},scope : this
											});
										
										}
									}
									Ext.MessageBox.show({
										title:'系统提示',
										msg: '是否确认该合同' + this.titleDef + '记录!',
										buttons: Ext.MessageBox.OKCANCEL,
										fn: handleDelete
									});											
							},scope:this
						}
					}];
		}
		
		cut_tools.contract_account.index_grid.superclass.constructor.call(this, {
				ds : this.store,
				columns: [
					new Ext.grid.RowNumberer(),
					{header: "id",dataIndex: "id",width: 200,hidden:true},
					{header: "合同信_主键",dataIndex: 'contractInforId',width: 200,hidden:true},
					{header: "合同编号",dataIndex: 'contractCode',width: 200},
					{header: "客户编号",dataIndex: 'customerCode', width: 80},
					{header: "客户名称",dataIndex: 'customerName',width: 100},
					{header: "合同金额",dataIndex: 'contractMoney',width: 80},
					{header: "回款金额",dataIndex: 'money',width: 80},
					{header: "状态",dataIndex: 'status',width: 50,
						renderer:function(value){
							if(value == 0){
								return "编制";
							}else if(value == 1){
								return "确认";
							}
						}
					},
					{header: "编制时间",dataIndex: 'editDate',width: 100,
						renderer:function(value){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d');
						}
					},	
					{header: "编制人Id",dataIndex: 'userId',width: 100,hidden:true},
					{header: "合同类型",dataIndex: 'contractType',width: 100,hidden:true},
					{header: "编制人", dataIndex: "userName",width: 80},
					{header: "备注",dataIndex: 'memo',width: 100, id: "remark"}
				],
				
				bbar: this.pageToolBbar
		})
	}	
});	
	
Ext.ffc.ContractAccountWindow = Ext.extend(Ext.Window, {  
	grid:null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.grid = new cut_tools.contract_account.index_grid({titleDef:this.titleDef,contractCode:_cfg.contractCode,isHidden:_cfg.hidden});
		this.grid.contractId = _cfg.contractId;
		this.grid.contractCode = _cfg.contractCode;
		this.grid.customerCode = _cfg.customerCode;
		this.grid.customerName = _cfg.customerName;
		this.grid.finalMoney = _cfg.finalMoney;
		Ext.ffc.ContractAccountWindow.superclass.constructor.call(this, {
			width : Ext.getBody().getWidth(),
			height : 600,
			constrainHeader : true,
			modal : true,
			title :  this.contractCode + '-合同' + this.titleDef + '信息',
			layout :  'fit',
			items : [this.grid],
			listeners: {
				show : function(){
					this.grid.store.load({params:{searchStr:Ext.encode({contractCode:this.contractCode})}});
				},
				close:this.closeFunction
			}
		});
	}
});