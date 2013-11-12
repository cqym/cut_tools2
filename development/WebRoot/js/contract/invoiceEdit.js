

//搜索条件 start18729332582

Ext.ffc.invoiceMainForm = Ext.extend(Ext.FormPanel,{
		constructor : function(_cfg) {
				if(_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				Ext.ffc.invoiceMainForm.superclass.constructor.call(this, {
					width : 810,
					labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
					frame:true,labelWidth:70,monitorValid:false,
					items:[
					   {layout:'column',border:false,labelSeparator:':',frame : true,
					   defaults:{layout: 'form',border:false,columnWidth:.3},
					   items:[
						    {items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'90%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: _cfg.invoiceType * 1 == 0 ? '客户名称' : '供应商名称',name: 'customerName',anchor:'90%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: '注册地址',name: 'comAdress',anchor:'100%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: '税号',name: 'taxCode',anchor:'90%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: '开户银行',name: 'bank',anchor:'90%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: '帐号',name: 'accountNumber',anchor:'100%',readOnly:true}]},
							{items: [{xtype:'textfield',fieldLabel: '电话',name: 'phoneFirst',anchor:'90%',readOnly:true}]},
						  {columnWidth:0.7,items: [{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'90%'}]}
					   ]//items
					  }
					]//items
				});
		}
})//FormPanel 
	

Ext.ffc.invoiceProductsGrid = Ext.extend(Ext.grid.EditorGridPanel,{
	contractListStore : null,
	selectionModel : null,
		columnModel : null,
		constructor : function(_cfg) {
					if(_cfg == null) {
						_cfg = {};
					}
					Ext.apply(this, _cfg);
					if(this.isHidden == false || this.isHidden == 'false'){
						var that = this;
						this.tbar = [{
											text:'清 零',
											iconCls:'icon-add',
											listeners : {
												'click' : function(obj) {
													var selectionRecords = this.selectionModel.getSelections();
													if(selectionRecords == null || selectionRecords.length == 0){
														Ext.Msg.alert("消息", "没有选择需要清零的行!");
														return;
													}
													for(var i = 0; i < selectionRecords.length ; i++){
														selectionRecords[i].set('money',0);
														selectionRecords[i].set('invoiceAmount',0);
														selectionRecords[i].set('invoiceDate',null);
													}
												},scope:this
											}
										},{
												text:'复制行',
												iconCls:'icon-add',
												listeners : {
													'click' : function(obj) {
														var selectionRecords = this.selectionModel.getSelections();
														if(selectionRecords == null || selectionRecords.length == 0){
															Ext.Msg.alert("消息", "没有选择需要复制的行!");
															return;
														}
														var store = this.getStore();	
														var index = store.indexOf(selectionRecords[0]);
														var num = 1 + Math.floor(Math.random()*(10000));  
														var newRecord = selectionRecords[0].copy("temp" + num);
														newRecord.data.id = "temp" + num; 				
														store.insert(index,[newRecord]);
													},scope:this
												}
										},{
												text:'删除行',
												iconCls:'icon-add',
												listeners : {
													'click' : function(obj) {
														var selectionRecords = this.selectionModel.getSelections();
														if(selectionRecords == null || selectionRecords.length == 0){
															Ext.Msg.alert("消息", "没有选择需要删除的行!");
															return;
														}
														var currId = selectionRecords[0].data.id;
														var store = this.getStore();
														if(Ext.isEmpty(currId)){
															Ext.Msg.alert("消息", "非复制行,不能删除!");
															return ;
														}
														
														Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中行!', function(btn){
															if(btn != 'yes'){return ;}

															if(currId.match(/^temp/)){//如果是刚复制的节点
																store.remove(selectionRecords[0]);
																return ;
															}
															var conDetailId = selectionRecords[0].data.contractDetailId;
																Ext.Ajax.request({
																	method: "post",
																	params: { 'id' : currId,'conDetailId':conDetailId},
																	url: PATH + "/contract/invoiceAction.do?ffc=deleteInvoiceInfor",
																	success: function(response){
																		if(response.responseText != ''){
																			Ext.Msg.alert("消息", response.responseText);
																		}else{
																			store.reload();
																		}
																	}
																});
														});
													},scope:this
												}
										},{
												text:'统一设置开票日期',
												iconCls:'icon-add',
												tooltip:'只设置，已有【发票金额】，无【开票日期】的数据',
												listeners : {
													'click' : function(obj) {
														var store = this.getStore();
														var deliveryDate = null;
														var dataf = new Ext.form.DateField({format:'Y-m-d'});
														var menu = new Ext.menu.DateMenu();
														menu["on"]("select", function(a,b){
															var bb = dataf.formatDate(dataf.parseDate(b));
															store.each(function(record){
															    if(record.get('money') != 0 && record.get('invoiceDate') == ''){
																    record.set('invoiceDate',bb);
																}
															});
															menu.hide();
														});
														menu.show(obj.getEl());	
													},scope:this
												}
										},{
												text:'已全部开票',
												iconCls:'icon-add',
												listeners : {
													'click' : function(obj) {
														Ext.MessageBox.confirm('系统提示', '请确认是否已全部开票!', function(btn){
															if(btn != 'yes'){return ;}
															var fdata = that.ownerCt.ownerCt.mainForm.getForm().getValues();
															var conId = that.contractInforId;
																Ext.Ajax.request({
																	method: "post",
																	params: { 'conId' : conId,'value' : 2},
																	url: PATH + "/contract/invoiceAction.do?ffc=updateContractInvoiceStatus",
																	success: function(response){
																		if(response.responseText != ''){
																			Ext.Msg.alert("消息", response.responseText);
																		}else{
																			Ext.Msg.alert("消息", "本合同已设置为【全部开票】");
																		}
																	}
																});
														});
													},scope:this
												}
										}];
							}
					this.contractListStore = new Ext.data.JsonStore({
						url : PATH + '/contract/invoiceAction.do?ffc=viewInvoiceDetail&contractInforId=' + _cfg.contractInforId + "&invoiceType=" + _cfg.invoiceType,
						root : 'items',
						totalProperty : 'totalCount',
						//autoLoad : true,
						fields : ['id', 'productCode', 'brandCode', 'toolsId', 'parentToolsId', 'leaf', 'productName', 'productUnit',
									'proSortName','amount', 'price', 'projectCode',"serialNumber",'productBrand','memo','contractProjectSortId',
							'contractInforId','contractDetailId','rebate','netPrice','money','contractMoney','taxNetPrice','taxMoney','editDateString',
								'invoiceInfoId','invoiceDate','invoiceAmount']
					});
					this.contractListStore.load();
					this.selectionModel = new Ext.grid.CheckboxSelectionModel({
						listeners : {
							rowselect : function(selectionModel, rowIndex, record) {
								if(record.data.money * 1 == 0 || record.data.invoiceAmount * 1 == 0){
								    record.set('money',record.data.taxMoney);
									record.set('invoiceAmount',record.data.amount);
								}
							}
						}
					});//复选框
					// 定义一个ColumnModel
					this.columnModel = new Ext.grid.ColumnModel(
						[	new Ext.grid.RowNumberer(), 
							this.selectionModel,
							{
								header : '合同分项',
								dataIndex : 'proSortName'
							},{
								header : '项目编号',
								width: 50,
								dataIndex : 'projectCode'
							},{
								header : '序号',
								width: 40,
								dataIndex : 'serialNumber'
							}, {
								header : '牌号',
								dataIndex : 'brandCode'
							},{
								header : '货品名称',
								dataIndex : 'productName',
								width:100
							},{
								header : '货品编号',
								dataIndex : 'productCode',
								width:100
							},{
								header : '计量单位',
								width: 40,
								dataIndex : 'productUnit'
							},{
								header : '合同数量',
								dataIndex : 'amount'
							},{
								header : '发票数量',
								dataIndex : 'invoiceAmount',
								editor:new Ext.form.NumberField({
									enableKeyEvents : true,
									allowBlank:false,
									listeners : {
										'focus' : function(f){
											f.selectText(0,f.getValue().length);
										}
									}
								})
							},{
								header : '净  价',
								dataIndex : 'netPrice'
							},{
								header : '含税单价',
								dataIndex : 'taxNetPrice'
							},{
								header : '合同金额',
								dataIndex : 'taxMoney'
							},{
								header : '开票金额',
								dataIndex : 'money',
								editor:new Ext.form.NumberField({
									enableKeyEvents : true,
									allowBlank:false,
									listeners : {
										'focus' : function(f){
											f.selectText(0,f.getValue().length);
										}
									}
								})
							},{
								header : '开票日期',
								dataIndex : 'invoiceDate',
								editor: {xtype:'datefield',format:'Y-m-d',value:new Date(),altFormats:'Y-m-d'},
								renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
									if(!value) return '';
									if(typeof(value) == 'string') return value;
									else{
										var str = value.format('Y-m-d');
										record.set('invoiceDate',str);
										return str;
									}
								}
							},{
								header : '编制日期',
								dataIndex : 'editDateString'//,
								//editor: new Ext.form.DateField({format :'Y-m-d'}),
								//renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
								//	alert(value);
								//}
							},{
								header : '品牌',
								dataIndex : 'productBrand'
							},{
								header : '备注',
								dataIndex : 'memo',
								editor: {xtype:'textfield'}
							},{
								header : 'id',
								hidden : true,
								dataIndex : 'id'
							},{
								header : 'toolsId',
								hidden : true,
								dataIndex : 'toolsId'
							},{
								header : 'leaf',
								hidden : true,
								dataIndex : 'leaf'
							},{
								header : 'contractProjectSortId',
								hidden : true,
								dataIndex : 'contractProjectSortId'
							},{
								header : 'contractInforId',
								hidden : true,
								dataIndex : 'contractInforId'
							},{
								header : 'contractDetailId',
								hidden : true,
								dataIndex : 'contractDetailId'
							},{
								header : 'rebate',
								hidden : true,
								dataIndex : 'rebate'
							},{
								header : 'invoiceInfoId',
								hidden : true,
								dataIndex : 'invoiceInfoId'
							}
						]);

			Ext.ffc.invoiceProductsGrid.superclass.constructor.call(this, {
				bodyStyle:'width:100%',
				height : 300,
				enableHdMenu : false,
				border : false,
				stripeRows : true,
				view : new Ext.grid.GridView({
					//forceFit:true,
					//autoFill :true,
					deferEmptyText : false,
					emptyText : '无合同产品信息！'
				}),
				ds : this.contractListStore,
				cm : this.columnModel,
				sm : this.selectionModel,
				listeners:{
							'afteredit' : function(e){
								var record = e.record;
								if(e.field == 'invoiceAmount' && record.get('invoiceAmount') * 1 > 0){
									record.set('money',record.get("invoiceAmount") * record.get("taxNetPrice"));
								}
							}
						},
				bbar : new Ext.PagingToolbar({
						pageSize : 20,
						emptyMsg: "没有记录",
						displayInfo: true,
						displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
						store : this.contractListStore,
						listeners : {
							beforechange : function( pagingToolbar,params ) {
								var arr = pagingToolbar.store.getModifiedRecords();
								if(arr.length > 0){
									pagingToolbar.store.commitChanges();
									if(confirm("当前页数据有改动，是否要保存!")){
									    return false;
									}else{
									    return true;
									}
								}
								return true;
							}
						}
					})
			});
		}
});

Ext.ffc.InvoiceEditWindow = Ext.extend(Ext.Window, {  
		mainForm : null,
		productsGrid : null,
		invoiceMainInfor : null,
		customerInfor : null,
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.mainForm = new Ext.ffc.invoiceMainForm({
				listeners : {
					afterrender : function(){
						var RecordClum = Ext.data.Record.create(
							[{name:'contractCode',mapping:'contractCode'},
							{name:'customerName',mapping:'customerName'},
							{name:'comAdress',mapping:'comAdress'},
							{name:'taxCode',mapping:'taxCode'},
							{name:'bank',mapping:'bank'},
							{name:'accountNumber',mapping:'accountNumber'},
							{name:'phoneFirst',mapping:'phoneFirst'},
							{name:'memo',mapping:'memo'}
							]
						);
						var customerInfor = _cfg.customerInfor;
						var tt = {'contractCode' : _cfg.invoiceMainInfor.contractCode,'memo' : _cfg.invoiceMainInfor.memo,'invoiceType':_cfg.invoiceType};
						Ext.applyIf(tt,customerInfor);
						var myNewRecord = new RecordClum(tt); 
						
						this.mainForm.getForm().loadRecord(myNewRecord);
					},scope:this
				}
			});
			this.productsGrid = new Ext.ffc.invoiceProductsGrid({
				'contractInforId' : _cfg.invoiceMainInfor.contractId,
				'invoiceType' : _cfg.invoiceMainInfor.invoiceType,
				'isHidden': _cfg.invoiceMainInfor.isHidden
			});
		    this.buttons = [];
			if(this.invoiceMainInfor.isHidden == false || this.invoiceMainInfor.isHidden == 'false'){
				this.buttons.push({
					text : "保  存",
					handler : function(obj) {
						var fdata = this.mainForm.getForm().getValues();
						this.invoiceMainInfor.memo =  fdata.memo;	
						var pdatas = [];
						var store = this.productsGrid.getStore();
						this.productsGrid.selectionModel.clearSelections();
							
						for(var i = 0 ,len = store.getCount(); i < len ; i++){
							var data = store.getAt(i).data;
							if(data.money != '' && data.money * 1 > 0 && data.invoiceDate == ''){
								Ext.Msg.alert("消息", "请选择开票日期!");
								return;
							}
							//if(data.money * 1 > 0){
							    pdatas.push(data);
							//}
						}
						if(pdatas.length <= 0){
							Ext.Msg.alert("消息", "没有发票信息可以保存!");
							return;
						}
						this.invoiceMainInfor.invoiceDetail =  pdatas;						
						var invoiceWindow = this;
						  Ext.Ajax.request({
								method: "post",
								params: { 'invoiceInfor' : Ext.encode(this.invoiceMainInfor)},
								url: PATH + "/contract/invoiceAction.do?ffc=addInvoiceInfor",
								success: function(response){
										if(response.responseText != ''){
											Ext.Msg.alert("消息", "保存失败！");
										}else{
											Ext.Msg.alert("消息", "保存成功！");
										}
										store.reload();
								}
							});
					},scope:this
				 });	
			}

			this.buttons.push({
					text : "取  消",
					handler : function() {
						this.close();
					},scope:this
			});
			Ext.ffc.InvoiceEditWindow.superclass.constructor.call(this, {
				layout : 'border',
				title : '发票编制',
				width : Ext.getBody().getWidth(),
				height : 495,
				buttonAlign : 'right',
				maximizable : true,
				constrainHeader : true,
				modal : true,
				items: [
				{
					region: 'north',
					layout: 'fit',
					split: true,
					width: 200,
					height : 120,
					minSize: 140,
					maxSize: 300,
					collapsible: true,
					margins: '5 5 5 5',
					items : [this.mainForm]
				}, {
					region: 'center',
					layout: 'fit',
					//contentEl: 'quogrid',
					split: true,
					height: 100,
					minSize: 100,
					maxSize: 200,
					collapsible: true,
					//title: 'South',
					margins: '-5 5 5 5',
					items : [this.productsGrid]
				}]
			});
		}
});