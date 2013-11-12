
/**
 * 改变基准状态
 * @param {} value
 * @return {String}
 */
function changeStatus(value) {
	switch (value) {
		case 0 :
			return "<span style='color:green;font-weight:bold;'>否</span>";
			break;
		case 1 :
			return "<span style='color:red;font-weight:bold;'>是</span>";
			break;
	}
}

/**
 * 汇率基本信息列表
 * 
 * @class Ext.zhj.CompanyGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.ExchangeGrid = Ext.extend(Ext.grid.GridPanel, {
	isAddHide : true,isModifyHide : true,isDelHide : true, isBasicHide : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var editor = new Ext.ux.grid.RowEditor({
					saveText : '提交',
					cancelText : '取消'
				});
		/**
		 * 公司信息更新
		 */
		editor.on('afteredit', function(grid, changes, r, rowIndex) {
					// alert(r.get('companyName'));
					var updateExchangeInfo = Ext.util.JSON.encode(r.data);
					Ext.Ajax.request({
								url : PATH + '/baseInfo/updateExchangeAction.do',
								params : {
									updateExchangeInfoPar : updateExchangeInfo
								},
								success : function(response) {
									var responseArray = Ext.util.JSON
											.decode(response.responseText);
									if (responseArray.success == true) {
										Ext.Msg.show({
													title : '成功提示',
													msg : responseArray.msg,
													buttons : Ext.Msg.OK,
													width : 200,
													icon : Ext.MessageBox.INFO
												});
										return true;
										// _addCompanyWindow.close();
									} else {
										Ext.Msg.show({
													title : '错误提示',
													msg : responseArray.msg,
													buttons : Ext.Msg.OK,
													width : 200,
													icon : Ext.MessageBox.ERROR
												});
										return false;
									}
								}

							});
				});
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/exchangeListAction.do',
					root : 'exchangeList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					fields : ['currencyName', 'currencyMark', 'rate','benchmark','id']
				});
		Ext.zhj.ExchangeGrid.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : Ext.getBody().getHeight() - 70,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "exchangeGird",
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无汇率信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
						text : "添加",
						iconCls : 'icon-add',
						hidden : this.isModifyHide,
						handler : function() {
							var addExchangeWindow = new Ext.zhj.AddExchangeWindow();
							addExchangeWindow.on('addExchangeSuccess',function(){
								this.getStore().reload();
							},this);
							addExchangeWindow.show();
						},
						scope : this

					}, {
						xtype:'tbseparator',
						hidden : this.isAddHide
					},{
						text : "修改",
						iconCls : 'icon-modify',
						hidden : this.isAddHide,
						handler : function() {
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();
							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要修改的汇率信息!.');
								return;
							}
							var updateObj = selectionModel.getSelected();
							var addExchangeWindow = new Ext.zhj.AddExchangeWindow({
							    updateObj : updateObj
							});
							addExchangeWindow.on('addExchangeSuccess',function(){
								this.getStore().reload();
							},this);
							addExchangeWindow.show();
						},
						scope : this

					}, {
						xtype:'tbseparator',
						hidden : this.isAddHide
					}, {
						text : "删除",
						iconCls : 'icon-delete',
						hidden : this.isDelHide,
						handler : function() {

							var _store = this.getStore();

							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的汇率信息!.');
								return;
							} else {
								var record = selectionModel.getSelected();
								Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/deleteExchangeAction.do',
									params : {
										exchangeIdPar : record.get("id")
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.INFO
													});
											_store.remove(record);
											_store.reload();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.ERROR
													});
											return;
										}
									}

								});
							}
						},
						scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isDelHide
					}, {
						text : "设为基准",
						iconCls : 'icon-submit',
						hidden : this.isBasicHide,
						handler : function() {

							var _store = this.getStore();

							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要设置为基准的汇率信息!.');
								return;
							} else {
								record = selectionModel.getSelected();
								Ext.MessageBox.confirm('信息提示', '确定要将此货币设定为基准货币！',
										function(button, text) {
											if (button == "yes") {
												this.setBenchmark(record);
											}
										}, this);
							}
						},
						scope : this
					}],
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 50
							}), {
						header : '货币名称',
						dataIndex : 'currencyName',
						editor : {
							xtype : 'textfield',
							allowBlank : false
						}
					}, {
						header : '符号',
						dataIndex : 'currencyMark',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '汇率',
						dataIndex : 'rate',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							decimalPrecision : 10
						}
					}, {
						header : '是否基准',
						dataIndex : 'benchmark',
						renderer : changeStatus
					},{
						header : 'ID',
						hidden : true,
						dataIndex : 'id'
					}]),
			bbar : new Ext.PagingToolbar({
						pageSize : PAGESIZE,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : ds
					})
		});

	},
	setBenchmark : function(record){
		
		//alert(record.get('id') + '基准');
		var _store = this.getStore();
		Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/setBenchmarkExchangeAction.do',
									params : {
										exchangeIdPar : record.get("id")
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.INFO
													});
											
											_store.reload();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.ERROR
													});
											return;
										}
									}

								});
	
	}

});


/**
 * 汇率信息
 */

Ext.onReady(function() {
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("001" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("001004" == childModule[j].id) {
						var _configArr = childModule[j].children;
						if(_configArr.length > 0) {
							for(var k = 0; k < _configArr.length; k++) {
								if(k != _configArr.length-1)
									_configStr += _configArr[k].url + ",";
								else 
									_configStr += _configArr[k].url + "}"
							}
						} else {
							_configStr += "}";
						}
						
						break;
					}
				}
				
				break;
			}
		}
		return Ext.decode(_configStr);
	}
			var exchangeGrid = new Ext.zhj.ExchangeGrid(getConfig());
			exchangeGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var exchangeViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'fit',
						items : [exchangeGrid]

					});
			exchangeViewPort.render("exchangeViewPort");
		})



/**
 * 添加汇率基本信息窗口
 */
Ext.zhj.AddExchangeWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		Ext.zhj.AddExchangeWindow.superclass.constructor.call(this, {
					bodyStyle : 'width:100%',
					title : "添加汇率信息",
					height : 200,
					width : 400,
					plain : false,
					bodyStyle : 'padding:15px',
					closeAction : 'close',
					constrain : false,
					modal : true,
					frame : true,
					items : {
						xtype : "form",
						labelWidth : 100,
						defaultType : "textfield",
						frame : true,
						labelAlign : 'right',
						buttonAlign : 'right',
						bodyStyle : 'padding:5px;',

						items : [{
									fieldLabel : "id",
									xtype : 'hidden',
									name : "id",
									value : this.updateObj ? this.updateObj.get("id") : null
								},{
									fieldLabel : "货币名称",
									name : "currencyName",
									anchor : '70%',
									value : this.updateObj ? this.updateObj.get("currencyName") : ''
								}, {
									fieldLabel : "符号",
									name : "currencyMark",
									anchor : '70%',
									value : this.updateObj ? this.updateObj.get("currencyMark") : ''
								}, {
									fieldLabel : "汇率",
									name : "rate",
									anchor : '70%',
									xtype : 'numberfield',
									decimalPrecision : 4,
									value : this.updateObj ? this.updateObj.get("rate") : ''
								}]
					},
					buttons : [{
						text : '保存',
						handler : function() {
							// 当前窗口
							var _addExchangeWindow = this.ownerCt.ownerCt;
							/**
							 * 表单对象
							 */
							var addForm = _addExchangeWindow.findByType('form')[0]
									.getForm();
							
							var _formValues = addForm.getValues();
							var _currencyName = _formValues.currencyName;
							var _currencyMark = _formValues.currencyMark;
							var _rate = _formValues.rate;
							
							if (_currencyName == '') {
								Ext.Msg.alert('提示', '货币名称不能为空!.');
								return false;
							}
							if (_currencyMark == '') {
								Ext.Msg.alert('提示', '货币符号不能为空!.');
								return false;
							}
							if (_rate == '') {
								Ext.Msg.alert('提示', '汇率不能为空!.');
								return false;
							}
							
							var record = new Ext.data.Record(addForm
									.getValues());
							var exchangeFormInfo = Ext.util.JSON.encode(record);
							var _url = PATH + '/baseInfo/addExchangeAction.do';
							var param = {exchangeFormInfoPar : exchangeFormInfo};
							if(_addExchangeWindow.updateObj){
							    _url = PATH + '/baseInfo/updateExchangeAction.do';
								param = {updateExchangeInfoPar: Ext.util.JSON.encode(record.data)};
							}
							Ext.Ajax.request({
										url : _url,
										params : param,
										success : function(response) {
											var responseArray = Ext.util.JSON
													.decode(response.responseText);
											if (responseArray.success == true) {
												Ext.Msg.show({
															title : '成功提示',
															msg : responseArray.msg,
															buttons : Ext.Msg.OK,
															width : 200,
															icon : Ext.MessageBox.INFO
														});
											    _addExchangeWindow.fireEvent("addExchangeSuccess");
												_addExchangeWindow.close();
											} else {
												Ext.Msg.show({
															title : '错误提示',
															msg : responseArray.msg,
															buttons : Ext.Msg.OK,
															width : 200,
															icon : Ext.MessageBox.ERROR
														});
												return;
											}
										}

									});
						}

					}, {
						text : '取消',
						handler : function() {
							this.ownerCt.ownerCt.close();
						}
					}]

				});
		this.addEvents("addExchangeSuccess");

	}

});
