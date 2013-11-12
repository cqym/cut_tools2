

/**
 * 
 * 供应商信息搜索
 * 
 */
Ext.zhj.SearchSupplier = Ext.extend(Ext.FormPanel, {
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				Ext.zhj.SearchSupplier.superclass.constructor.call(this, {
							labelAlign : 'right',
							buttonAlign : 'right',
							bodyStyle : 'padding:5px;',
							el : 'searchSupplier',
							border : false,
							frame : true,
							labelWidth : 70,
							monitorValid : false,

							items : [{
								layout : 'column',
								border : false,
								labelSeparator : ':',
								frame : true,
								defaults : {
									layout : 'form',
									border : false,
									columnWidth : .3
								},
								listeners : {
									'render': function(p) {
										p.getEl().on('keypress', function(e){
											if(e.getKey() == e.ENTER){
												this.ownerCt.fireEvent('searchSupplier', this.ownerCt, this.ownerCt.getValues());
											}
										},this);
									}
								},
								bbar : ['->', {
									text : "搜  索",
									iconCls : 'icon-search',
									handler : function() {
										// 发布search事件
										this.fireEvent('searchSupplier', this,
												this.getValues());
									},
									scope : this
								}, '-', {
									text : "重  置",
									iconCls : 'icon-reset',
									handler : function() {
										this.getForm().reset();
									},
									scope : this
								}],

								items : [{
											items : [{
														xtype : 'textfield',
														fieldLabel : '供应商名称',
														name : 'supplierName',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '供应商编号',
														name : 'supplierCode',
														anchor : '90%'
													}]
										}]
							}]
						});
				/**
				 * 当前对象添加searchConpany方法
				 */
				this.addEvents("searchSupplier");
			},

			/**
			 * 获取搜索条件
			 * 
			 * @return {} 返回搜索条件:Record
			 */
			getValues : function() {
				var record = new Ext.data.Record(this.getForm().getValues());
				return record;
			}

		});

		
		
Ext.zhj.onClickDetailBrand = function(supplierId) {
	//alert(_id + '@@@@@');
	var _brankDetailWindow = new Ext.zhj.BrankDetailWindow({
				supplierId : supplierId
			});
	_brankDetailWindow.show();
}

// 查看详细渲染器
Ext.zhj.detailBrankWindow = function(supplierId) {
	return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickDetailBrand(\''
			+ supplierId + '\')">查看</a>'
}

function changeLevelStyle(value) {
	function getStarts(count){
	    var str = '';
		for(var i = 0;i < count ; i++)
		{
			str += '<img src="' + PATH + '/images/14.png">';
		}
		return str;
	}
	switch (value) {
		case 0 :
			return "<span style='font-weight:bold;font-size:10;color:#00aa00'></span>";
			break;
		case 1 :
			return "<span style='font-weight:bold;font-size:10;;color:#00aa00'>" + getStarts(1) + "</span>";
			break;
		case 2 :
			return "<span style='font-weight:bold;font-size:10;;color:#00aa00'>" + getStarts(2) + "</span>";
			break;
		case 3 :
			return "<span style='font-weight:bold;font-size:10;;color:#00aa00'>" + getStarts(3) + "</span>";
			break;
		case 4 :
			return "<span style='font-weight:bold;font-size:10;;color:#00aa00'>" + getStarts(4) + "</span>";
			break;
		case 5 :
			return "<span style='font-weight:bold;font-size:10;;color:#00aa00'>" + getStarts(5) + "</span>";
			break;
	}
}


/**
 * 供应商基本信息列表
 * 
 * @class Ext.zhj.CompanyGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.SupplierGrid = Ext.extend(Ext.grid.GridPanel, {
	isAddHide : true,isModifyHide : true,isDelHide : true,
	searchRecord : null,
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
					var updateSupplierInfo = Ext.util.JSON.encode(r.data);
					var _store = this.ownerCt.getStore();
					Ext.Ajax.request({
								url : PATH + '/baseInfo/updateSupplierAction.do',
								params : {
									updateSupplierInfoPar : updateSupplierInfo
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
										return true;
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
					url : PATH + '/baseInfo/supplierListAction.do',
					root : 'supplierList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['supplierCode', 'supplierName', 'supplierShortName', 'contactPersonFirst',
							'phoneFirst', 'faxFirst', 'contactPersonSec','phoneSec','faxSec','contractAddress',
							'postcode', 'comAdress', 'bank','accountNumber','homePage','email','memo','taxCode','ownContactPerson','level','id']
				});
		ds.on({'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								ds.baseParams.searchStr = _searchStr;
								//alert('我被调用了searchStr = ' + _searchStr);
							}
						},scope : this
					});
	    //我方负责人
	    var _storeUserInfo = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/getUserInforListAction.do',
					fields : ['id','userName','trueName'],
					root : 'userInfo'
		});	
		var _storeSupplierLevel = new Ext.data.SimpleStore({
						fields : ['level', 'value'],
						data : [['*', '1'],['**', '2'],['***', '3'],
						['****', '4'], ['*****', '5']]
					});
		Ext.zhj.SupplierGrid.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : Ext.getBody().getHeight() - 175,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "supplierGrid",
			frame : true,
			//plugins : this.isModifyHide ? [] : [editor],
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无供应商信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
						text : "新增供应商",
						iconCls : 'icon-add',
						hidden : this.isAddHide,
						handler : function() {
							var addSupplierWindow = new Ext.zhj.AddSupplierWindow({
									isAdd : true,
									formUrl : '/baseInfo/addSupplierAction.do'
								});
							addSupplierWindow.on('supplierAddSuccess',function(){
								this.getStore().reload();
							},this);
							addSupplierWindow.show();
						},
						scope : this

					}, {
						xtype:'tbseparator',
						hidden : this.isAddHide
					},{
						text : "修改供应商",
						iconCls : 'icon-add',
						hidden : this.isModifyHide,
						handler : function() {
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();
							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要修改的供应商信息!.');
								return;
							} else {
								var record = selectionModel.getSelected();
								var updateSupplierWindow = new Ext.zhj.AddSupplierWindow({
									isAdd : false,
									formUrl : '/baseInfo/updateSupplierAction.do'
								});
								var farr = updateSupplierWindow.findByType('form');
								if(farr.length == 0) return ;
								var f = farr[0];
								updateSupplierWindow.on('show',function() {
										f.getForm().setValues(record.data);
									}, this);
								updateSupplierWindow.on('close',function() {
										this.getStore().reload();
									}, this);
								updateSupplierWindow.show();
							}
						},scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isModifyHide
					}, {
						text : "删除供应商",
						iconCls : 'icon-delete',
						hidden : this.isDelHide,
						handler : function() {

							var _store = this.getStore();

							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的供应商信息!.');
								return;
							} else {
						Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中供应商信息!', function(btn){
							if(btn != 'yes'){return ;}
								record = selectionModel.getSelected();
								Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/deleteSupplierAction.do',
									params : {
										supplierIdPar : record.get("id")
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
							});
							}
						},
						scope : this
					}],
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						header : '供货商名称',
						dataIndex : 'supplierName',
						sortable : true,
						editor : {
							xtype : 'textfield',
							allowBlank : false
						}
					}, {
						header : '供货商编号',
						dataIndex : 'supplierCode',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '简称',
						dataIndex : 'supplierShortName',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '我方负责人',
						dataIndex : 'ownContactPerson',
						sortable : true,
						editor : {
							xtype : 'combo',
							hideLabel : true,
							hiddenName : 'ownContactPerson',
							mode : 'remote',
							displayField : 'trueName',
							valueField : 'trueName',
							width : '20',
							readOnly : true,
							triggerAction : 'all',
							emptyText : '请选择...',
							store : _storeUserInfo
						}
					}, {
						header : '供应商级别',
						dataIndex : 'level',
						sortable : true,
						renderer : changeLevelStyle,
						editor : {
							xtype : 'combo',
							hideLabel : true,
							hiddenName : 'level',
							mode : 'local',
							displayField : 'level',
							valueField : 'value',
							readOnly : true,
							frame : true,
							triggerAction : 'all',
							emptyText : '请选择...',
							store : _storeSupplierLevel
						}
					}, {
						header : '品牌',
						dataIndex : 'id',
						sortable : true,
						renderer : Ext.zhj.detailBrankWindow
					}, {
						header : '联系人1',
						dataIndex : 'contactPersonFirst',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}

					}, {
						header : '电话1',
						dataIndex : 'phoneFirst',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '传真1',
						dataIndex : 'faxFirst',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '联系人2',
						dataIndex : 'contactPersonSec',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '电话2',
						dataIndex : 'phoneSec',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '传真2',
						dataIndex : 'faxSec',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '合同地址',
						sortable : true,
						dataIndex : 'contractAddress',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '邮编',
						sortable : true,
						dataIndex : 'postcode',
						editor : {
							xtype : 'numberfield'
						}
					}, {
						header : '通讯地址',
						sortable : true,
						dataIndex : 'comAdress',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '开户银行',
						sortable : true,
						dataIndex : 'bank',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '帐号',
						sortable : true,
						dataIndex : 'accountNumber',
						editor : {
							xtype : 'numberfield'
						}
					}, {
						header : '税号',
						dataIndex : 'taxCode',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '主页',
						sortable : true,
						dataIndex : 'homePage',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : 'E-mail',
						sortable : true,
						dataIndex : 'email',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '备注',
						sortable : true,
						dataIndex : 'memo',
						editor : {
							xtype : 'textfield'
						}
					}, {
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
	/**
	 * 为搜索条件设值
	 * @param {} _value
	 */
	setSearchStr : function(_value) {
		this.searchRecord = _value;
	}

});


/**
 * 供应商信息
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
					if("001003" == childModule[j].id) {
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
			var supplierGrid = new Ext.zhj.SupplierGrid(getConfig());
			var searchSupplierForm = new Ext.zhj.SearchSupplier();
			//监听搜索事件
			searchSupplierForm.on({
						'searchSupplier' : function(_form, _values) {
							supplierGrid.setSearchStr(_values);
							supplierGrid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
			});
			supplierGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var supplierViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'border',
						items : [{
									region : "north",
									height : 105,
									frame : true,
									collapsible : true,
									margins : '5 5 5 5',
									items : [searchSupplierForm]
								}, {
									region : "center",
									height : Ext.getBody().getHeight() - 175,
									collapsible : true,
									margins : '5 5 5 5',
									items : [supplierGrid]
								}]

					});
			supplierViewPort.render("supplierViewPort");
		})



/**
 * 添加供应商基本信息窗口
 */

Ext.zhj.AddSupplierWindow = Ext.extend(Ext.Window, {
	ownContactPersonCombox : null,
	supplierLevelCombox : null,
	isAdd : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.ownContactPersonCombox = new Ext.zhj.OwnContactPersonCombox();
		this.supplierLevelCombox = new Ext.zhj.SupplierLevelCombox();
		Ext.zhj.AddSupplierWindow.superclass.constructor.call(this, {
					bodyStyle : 'width:100%',
					title : "新增供应商信息",
					height : 420,
					width : 600,
					plain : true,
					bodyStyle : 'padding:15px',
					closeAction : 'close',
					constrain : true,
					modal : true,
					frame : true,
					items : [{
						xtype : 'form',
						layout : 'column',
						border : false,
						labelSeparator : ':',
						frame : true,
						defaults : {
							layout : 'form',
							border : false,
							columnWidth : .5,
							labelAlign : 'right',
							labelWidth : '60'
						},
						items : [{
									items : [{
												xtype : 'hidden',
												fieldLabel : 'ID',
												name : 'id'
											},{
												xtype : 'textfield',
												fieldLabel : '供货商名称',
												name : 'supplierName',
												allowBlank : false,
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '供货商编号',
												name : 'supplierCode',
												allowBlank : false,
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '简称',
												name : 'supplierShortName',
												allowBlank : false,
												anchor : '90%'
											}]
								},{
									items : [this.ownContactPersonCombox]
								},{
									items : [this.supplierLevelCombox]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '联系人1',
												name : 'contactPersonFirst',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '电话1',
												name : 'phoneFirst',
												emptyText : '',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '传真1',
												name : 'faxFirst',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '联系人2',
												name : 'contactPersonSec',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '电话2',
												name : 'phoneSec',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '传真2',
												name : 'faxSec',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '合同地址',
												name : 'contractAddress',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '邮编',
												name : 'postcode',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '通讯地址',
												name : 'comAdress',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '开户银行',
												name : 'bank',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '帐号',
												name : 'accountNumber',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '税号',
												name : 'taxCode',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '主页',
												name : 'homePage',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : 'E-mail',
												name : 'email',
												anchor : '90%'
											}]
								}, {
									items : [{
												xtype : 'textfield',
												fieldLabel : '备注',
												name : 'memo',
												anchor : '90%'
											}]
								}]
					}],
					buttons : [{
						text : '保存',
						handler : function() {

							// 当前窗口
							var _addSupplierWindow = this.ownerCt.ownerCt;
							/**
							 * 表单对象
							 */
							var addForm = _addSupplierWindow.findByType('form')[0].getForm();
							if(!addForm.isValid())
								return;
							var supplierFormInfo = Ext.util.JSON.encode(addForm.getValues());
							if(_addSupplierWindow.isAdd) {
								var record = new Ext.data.Record(addForm
									.getValues());
								//alert(Ext.util.JSON.encode(record));
								supplierFormInfo = Ext.util.JSON.encode(record);
							}
							
							Ext.Ajax.request({
										url : PATH + _addSupplierWindow.formUrl,
										params : {
											supplierFormInfoPar : supplierFormInfo
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
												_addSupplierWindow.fireEvent('supplierAddSuccess');
												_addSupplierWindow.close();
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
				this.addEvents('supplierAddSuccess');

	}

});












