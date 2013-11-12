/**
 * 
 * 公司信息搜索
 * 
 */
Ext.zhj.SearchConpany = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.zhj.SearchConpany.superclass.constructor.call(this, {
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
			el : 'searchConpany',
			border : false,
			frame : true,
			labelWidth : 70,
			monitorValid : false,
			enableKeyEvents: true,
			
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
								this.ownerCt.fireEvent('searchConpany', this.ownerCt, this.ownerCt.getValues());
							}
						},this);
					}
				},
				bbar : ['->', {
					text : "搜  索",
					xtype:'button',
					iconCls : 'icon-search',
					handler : function() {
						// 发布search事件
						this.fireEvent('searchConpany', this, this.getValues());
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
										fieldLabel : '公司名称',
										name : 'companyName',
										anchor : '90%'
									}]
						}, {
							items : [{
										xtype : 'textfield',
										fieldLabel : '公司代码',
										name : 'companyCode',
										anchor : '90%'
									}]
						}]
			}]
		});
		/**
		 * 当前对象添加searchConpany方法
		 */
		this.addEvents("searchConpany");
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

/**
 * 公司logo type ：3 
 * @param {} _id
 */
Ext.zhj.onSlaveClick = function(_id) {
	window.event.cancelBubble = true;
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : 3});
	slaveWindow.show();
}

/**
 * 公司footer type ：4
 * @param {} _id
 */
Ext.zhj.onSlaveClickFooter = function(_id) {
	//alert("######");
	window.event.cancelBubble = true;
	var slaveWindowFooter = new Slave.SlaveManageWindow({busId : _id, busType : 4});
	slaveWindowFooter.show();
}

/**
 * 公司基本信息列表
 * 
 * @class Ext.zhj.CompanyGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.CompanyGrid = Ext.extend(Ext.grid.GridPanel, {
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
					// alert(r.get('companyName'));
					var updateCompanyInfo = Ext.util.JSON.encode(r.data);
					var _store = this.store;
					Ext.Ajax.request({
								url : PATH + '/baseInfo/updateComPanyAction.do',
								params : {
									updateCompanyInfoPar : updateCompanyInfo
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
										_store.commitChanges();
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
				}, this);
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/companyListAction.do',
					root : 'companyList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['companyName', 'companyCode', 'fax', 'phone',
							'bank', 'accountNumber', 'companyAddress',
							'contactPerson', 'postcode', 'regAddress',
							'taxCode', 'id']
				});
		ds.on({'beforeload' : function() {
						if (this.searchRecord != null) {
							var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
							ds.baseParams.searchStr = _searchStr;
						}
					},
					scope : this
				});
		Ext.zhj.CompanyGrid.superclass.constructor.call(this, {
			// bodyStyle : 'width:100%',
			height : Ext.getBody().getHeight() - 175,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无公司信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
						text : "新增公司信息",
						iconCls : 'icon-add',
						hidden : this.isAddHide,
						handler : function() {
							var addCompanyWindow = new Ext.zhj.AddCompanyWindow({
							    formUrl : '/baseInfo/addComPanyAction.do'
							});
							addCompanyWindow.on('companyAddSuccess',
									function() {
										this.getStore().reload();
									}, this);
							addCompanyWindow.show();
						},scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isAddHide
					},{
						text : "修改公司信息",
						iconCls : 'icon-add',
						hidden : this.isModifyHide,
						handler : function() {
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();
							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要修改的公司信息!.');
								return;
							} else {
									var record = selectionModel.getSelected();
									var updateCompanyWindow = new Ext.zhj.AddCompanyWindow({
										formUrl : '/baseInfo/updateComPanyAction.do'
									});
									var farr = updateCompanyWindow.findByType('form');
									if(farr.length == 0) return ;
									var f = farr[0];
									updateCompanyWindow.on('show',
										function() {
											f.getForm().setValues(record.data);
										}, this);
									updateCompanyWindow.on('close',
										function() {
											this.getStore().reload();
										}, this);
									updateCompanyWindow.show();
							}
						},scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isAddHide
					}, {
						text : "删除公司信息",
						iconCls : 'icon-delete',
						hidden : this.isDelHide,
						handler : function() {
							var _store = this.getStore();
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的公司信息!.');
								return;
							} else {
								Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中公司信息!', function(btn){
									if(btn != 'yes'){return ;}
									var record = selectionModel.getSelected();
									Ext.Ajax.request({url : PATH + '/baseInfo/deleteComPanyAction.do',
										params : {
											companyIdPar : record.get("id")
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
						},scope : this
					}],
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						header : '公司名称',
						dataIndex : 'companyName',
						sortable : true,
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '公司名称不能为空!'
						}
					}, {
						header : '公司代码',
						dataIndex : 'companyCode',
						sortable : true,
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '公司代码不能为空!'
						}
					}, {
						header : '传真',
						sortable : true,
						dataIndex : 'fax',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '传真不能为空!'
						}
					}, {
						header : '电话',
						sortable : true,
						dataIndex : 'phone',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '电话不能为空!'
						}

					}, {
						header : '开户银行',
						sortable : true,
						dataIndex : 'bank',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '开户银行不能为空!'
						}
					}, {
						header : '帐号',
						sortable : true,
						dataIndex : 'accountNumber',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '帐号不能为空!'
						}
					}, {
						header : '税号',
						sortable : true,
						dataIndex : 'taxCode',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							blankText : '税号不能为空!'
						}
					}, {
						header : '注册地址',
						sortable : true,
						dataIndex : 'regAddress',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '注册地址不能为空!'
						}
					}, {
						header : '联系地址',
						sortable : true,
						dataIndex : 'companyAddress',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '联系地址不能为空!'
						}
					}, {
						header : '法人',
						sortable : true,
						dataIndex : 'contactPerson',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '联系人不能为空!'
						}
					}, {
						header : '邮编',
						sortable : true,
						dataIndex : 'postcode',
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							blankText : '邮编不能为空!'
						}
					}, {
						header : '公司Logo',
						sortable : true,
						width : 100,
						dataIndex : 'id',
						renderer : function(id) {
								var str = "<a href=\"#\" onclick=\"Ext.zhj.onSlaveClick('"
										+ id
										+ "');\"><span style='color:blue;font-weight:bold;'>查看</span></a>";
								return str;
							
						}
					}, {
						header : '公司footer',
						width : 100,
						sortable : true,
						dataIndex : 'id',
						renderer : function(id) {
								var str = "<a href=\"#\" onclick=\"Ext.zhj.onSlaveClickFooter('"
										+ id
										+ "');\"><span style='color:blue;font-weight:bold;'>查看</span></a>";
								return str;
							
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
	// 附件管理
	onSlaveClick : function() {
		try {
			var _store = this.getStore();

			var selectionModel = this.getSelectionModel();
			var selectedCount = selectionModel.getCount();

			if (selectedCount == 0) {
				Ext.Msg.alert('提示', '请选择要查看的公司信息!.');
				return;
			} else {
				var record = selectionModel.getSelected();
				// alert(record.get('id'));
				var slaveWindow = new Slave.SlaveManageWindow({
							busId : record.get('id'),
							busType : 1
						});
				slaveWindow.show();
			}

		} catch (_err) {
			Ext.Msg.show({
						title : '信息提示',
						msg : _err.message,
						width : 260,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
		}
	},
	/**
	 * 为搜索条件设值
	 * 
	 * @param {}
	 *            _value
	 */
	setSearchStr : function(_value) {
		this.searchRecord = _value;
	}

});

/**
 * 公司信息
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
					if("001001" == childModule[j].id) {
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
			var searchConpanyForm = new Ext.zhj.SearchConpany();
			var companyGrid = new Ext.zhj.CompanyGrid(getConfig());

			// 监听搜索事件
			searchConpanyForm.on({
						'searchConpany' : function(_form, _values) {
							companyGrid.setSearchStr(_values);
							// alert('DDDDDDD' + _values.get('companyName'));
							companyGrid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
					});

			companyGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var companyViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'border',
						items : [{
									region : "north",
									layout : 'fit',
									height : 105,
									frame : true,
									collapsible : true,
									margins : '5 5 5 5',
									items : [searchConpanyForm]
								}, {

									region : "center",
									layout : 'fit',
									collapsible : true,
									margins : '5 5 5 5',
									items : [companyGrid]

								}]
					});

			companyViewPort.render("companyViewPort");
		})

/**
 * 添加公司基本信息窗口
 */
Ext.zhj.AddCompanyWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		Ext.zhj.AddCompanyWindow.superclass.constructor.call(this, {
			// bodyStyle : 'width:100%',
			title : "公司信息",
			height : 400,
			width : 400,
			plain : true,
			bodyStyle : 'padding:15px',
			closeAction : 'close',
			constrain : true,
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
							fieldLabel : "ID",
							name : "id",
							xtype : 'hidden'
						},{
							fieldLabel : "公司名称",
							name : "companyName",
							anchor : '85%'
						}, {
							fieldLabel : "公司代码",
							name : "companyCode",
							anchor : '85%'
						}, {
							fieldLabel : "电话",
							name : "phone",
							anchor : '85%'
						}, {
							fieldLabel : "传真",
							name : "fax",
							anchor : '85%'
						}, {
							fieldLabel : "开户银行",
							name : "bank",
							anchor : '85%'
						}, {
							fieldLabel : "帐号",
							name : "accountNumber",
							anchor : '85%',
							xtype : 'textfield'
						}, {
							fieldLabel : "注册地址",
							name : "regAddress",
							anchor : '85%'
						}, {
							fieldLabel : "税号",
							name : "taxCode",
							anchor : '85%'
						}, {
							fieldLabel : "联系地址",
							name : "companyAddress",
							anchor : '85%'
						}, {
							fieldLabel : "法人",
							name : "contactPerson",
							anchor : '85%'
						}, {
							fieldLabel : "邮编",
							name : "postcode",
							anchor : '85%',
							xtype : 'textfield'
						}]
			},
			buttons : [{
				text : '保存',
				handler : function() {

					// 当前窗口
					var _addCompanyWindow = this.ownerCt.ownerCt;
					var _formUrl = _addCompanyWindow.formUrl;
					/**
					 * 表单对象
					 */
					var addForm = _addCompanyWindow.findByType('form')[0]
							.getForm();
							
				    var _formValues = addForm.getValues();
					var _companyName = _formValues.companyName;
					var _companyCode = _formValues.companyCode;
					var _phone = _formValues.phone;
					var _fax = _formValues.fax;
					var _bank = _formValues.bank;
					var _accountNumber = _formValues.accountNumber;
					var _regAddress = _formValues.regAddress;
					var _taxCode = _formValues.taxCode;
					var _companyAddress = _formValues.companyAddress;
					var _contactPerson = _formValues.contactPerson;
					var _postcode = _formValues.postcode;
					
					if (_companyName == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '公司名称不能为空',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_companyCode == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '公司代码不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_phone == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '电话不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_fax == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '传真号码不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						
						return false;
					}
					if (_bank == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '开户银行不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_accountNumber == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '帐号不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}	
					if (_regAddress == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '注册地址不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_taxCode == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '税号不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_companyAddress == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '联系地址不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_contactPerson == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '联系人不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					if (_postcode == '') {
						Ext.Msg.show({
								title : '提示',
								msg : '邮政编号不能为空!',
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
						});
						return false;
					}
					var companyFormInfo = Ext.util.JSON.encode(addForm.getValues());
					Ext.Ajax.request({
								url : PATH + _formUrl,
								params : {
									companyFormInfoPar : companyFormInfo
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
										_addCompanyWindow
												.fireEvent('companyAddSuccess');
										_addCompanyWindow.close();
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

							}, this);
				}

			}, {
				text : '取消',
				handler : function() {
					this.ownerCt.ownerCt.close();
				}
			}]

		});
		this.addEvents('companyAddSuccess');
	}

});
