

/**
 * 
 * 单据编号信息列表
 * @class Ext.zhj.BillsCodeGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.BillsCodeGrid = Ext.extend(Ext.grid.GridPanel, {
	isModifyHide : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/billsCodeListAction.do',
					root : 'billsCodeList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					fields : ['billType', 'billTypeName', 'billDefNormal',
							'serialNumber', 'id']
				});
		Ext.zhj.BillsCodeGrid.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : Ext.getBody().getHeight() - 70,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "billsCodeGird",
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无单据编号定义信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
				text : "更改编号规则",
				iconCls : 'icon-modify',
				hidden : this.isModifyHide,
				handler : function() {
					var _store = this.getStore();
					var _count = this.getSelectionModel().getCount();
					var _record = this.getSelectionModel().getSelected();
					if (_count == 0) {
						Ext.Msg.alert('提示', '请选择要更改的编号信息!.');
						return;
					} else {
						// Ext.Msg.alert('提示', '稍等,吃完饭再弄!.');
						var updateBillsCodeWindow = new Ext.zhj.UpdateBillsCodeWindow(
								{
									rowrecord : _record
								});

						/*
						 * updateBillsCodeWindow.on("show",function(){
						 * updateBillsCodeWindow.updateBillsCodeForm.getForm().loadRecord(_record);
						 * });
						 */

						updateBillsCodeWindow.show();

						// return;
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
						header : '编号',
						dataIndex : 'billType'
					}, {
						header : '名称',
						dataIndex : 'billTypeName',
						width : 300
					}, {
						header : '规则',
						dataIndex : 'billDefNormal',
						width : 400
					}, {
						header : '流水号',
						dataIndex : 'serialNumber'
					}, {
						header : 'ID',
						hidden : true,
						dataIndex : 'id'
					}])

		});

	}

});

/**
 * 单据编号信息
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
					if("001005" == childModule[j].id) {
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
			var billsCodeGrid = new Ext.zhj.BillsCodeGrid(getConfig());
			billsCodeGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var billsCodeViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'fit',
						items : [billsCodeGrid]

					});
			billsCodeViewPort.render("billsCodeViewPort");
		})

/**
 * 更新单据编号规则
 * 
 * @class Ext.zhj.UpdateBillsCodeForm
 * @extends Ext.FormPanel
 */
Ext.zhj.UpdateBillsCodeForm = Ext.extend(Ext.FormPanel, {
	rowrecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		Ext.zhj.UpdateBillsCodeForm.superclass.constructor.call(this, {
			labelWidth : 100,
			defaultType : "textfield",
			frame : true,
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
			bbar : [{
				text : "流水号",

				handler : function() {

					Ext.MessageBox.prompt('流水号', '请输入流水号位数:', showInput);

					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					function showInput(btn, text) {
						if (Ext.isNumber(text * 1) && (text * 1 >= 1)) {
							var _billDefNormal = '';

							for (var i = 0; i < text * 1; i++) {
								_billDefNormal += '#';
							}
							// 判断原有"位数"位置
							var a = _OldBillDefNormal.indexOf('#');
							var b = _OldBillDefNormal.lastIndexOf('#');
							if (a > 0) {
								// 替换原有"位数"字符
								_billDefNormal = _OldBillDefNormal.substring(0,
										a)
										+ _billDefNormal
										+ _OldBillDefNormal.substring(b + 1,
												_OldBillDefNormal.length);
								_form.findByType('textfield')[0]
										.setValue(_billDefNormal);
							} else {
								_billDefNormal = _OldBillDefNormal + "{"
										+ _billDefNormal + "}";
								_form.findByType('textfield')[0]
										.setValue(_billDefNormal);
							}
						} else if (text.length == 0) {
							return false;
						} else {
							Ext.MessageBox.alert('错误', text + '为空或不是一个有效的自然数!');
							return false;
						}
					}

				},
				scope : this
			}, '-', {
				text : "自定义字符",

				handler : function() {
					var pat = new RegExp("[^a-zA-Z0-9\-]", "i");
					Ext.MessageBox.prompt('自定义字符', '请输入自定义字符:', showInputZI);

					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					function showInputZI(btn, text) {
						if (pat.test(text) == true) {
							Ext.MessageBox.alert('错误', text + '含有非法字符!!');
							return false;
						} else {
							var _billDefNormal = _OldBillDefNormal + text;
							_form.findByType('textfield')[0]
									.setValue(_billDefNormal);
							return true;
						}
					}

				},
				scope : this
			}, '-', {
				text : "公司编号",

				handler : function() {
					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					var a = _OldBillDefNormal.indexOf('{COMPANYCODE}');
					if (a < 0) {
						var _billDefNormal = _OldBillDefNormal + '{COMPANYCODE}';
						_form.findByType('textfield')[0]
								.setValue(_billDefNormal);
						return true;
					} else {
						return false;
					}
				},
				scope : this
			}, '-', {
				text : "供应商编号",
				handler : function() {
					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					var a = _OldBillDefNormal.indexOf('{SUPPLIERS}');
					if (a < 0) {
						var _billDefNormal = _OldBillDefNormal + '{SUPPLIERS}';
						_form.findByType('textfield')[0]
								.setValue(_billDefNormal);
						return true;
					} else {
						return false;
					}

				},
				scope : this
			}, '-', {
				text : "客户编号",

				handler : function() {
					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					var a = _OldBillDefNormal.indexOf('{CUSTOMERS}');
					if (a < 0) {
						var _billDefNormal = _OldBillDefNormal + '{CUSTOMERS}';
						_form.findByType('textfield')[0]
								.setValue(_billDefNormal);
						return true;
					} else {
						return false;
					}
				},
				scope : this
			}, '-', {
				text : "  年  ",

				handler : function() {

					var checkYearStyleWindow = new Ext.zhj.CheckYearStyleWindow({_form : this});
					checkYearStyleWindow.show();

				},
				scope : this
			}, '-', {
				text : "  月  ",

				handler : function() {
					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					var a = _OldBillDefNormal.indexOf('{MM}');
					if (a < 0) {
						var _billDefNormal = _OldBillDefNormal + '{MM}';
						_form.findByType('textfield')[0]
								.setValue(_billDefNormal);
						return true;
					} else {
						return false;
					}
				},
				scope : this
			}, '-', {
				text : "  日  ",

				handler : function() {
					// 当前表单
					var _form = this;
					// 当前表单中单据编号规则
					var _OldBillDefNormal = _form.getForm().getValues().billDefNormal;

					var a = _OldBillDefNormal.indexOf('{DD}');
					if (a < 0) {
						var _billDefNormal = _OldBillDefNormal + '{DD}';
						_form.findByType('textfield')[0]
								.setValue(_billDefNormal);
						return true;
					} else {
						return false;
					}
				},
				scope : this
			}],
			items : [{
						fieldLabel : this.rowrecord.get('billTypeName') + "规则",
						name : "billDefNormal",
						anchor : '100%',
						readOnly : true
					}, {
						fieldLabel : "规则ID",
						hideLabel : true,
						name : "id",
						value : this.rowrecord.get('id'),
						anchor : '100%',
						hidden : true
					}]

		});
	}

});

/**
 * 修改单据编号规则窗口
 */
Ext.zhj.UpdateBillsCodeWindow = Ext.extend(Ext.Window, {
	updateBillsCodeForm : null,
	rowrecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.updateBillsCodeForm = new Ext.zhj.UpdateBillsCodeForm({
					rowrecord : this.rowrecord
				});
		Ext.zhj.UpdateBillsCodeWindow.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			title : "修改" + this.rowrecord.get('billTypeName') + "规则信息",
			height : 180,
			width : 550,
			plain : true,
			bodyStyle : 'padding:15px',
			constrain : true,
			closeAction : 'close',
			modal : true,
			frame : true,
			items : [this.updateBillsCodeForm],
			buttons : [{
					text : '清空',
					handler : function(){
						this.updateBillsCodeForm.getForm().reset();
					},
					scope : this
					
				},{
				text : '确定',
				handler : function() {

					// 当前窗口
					var _updateBillsCodeWindow = this.ownerCt.ownerCt;
					/**
					 * 表单对象
					 */
					var _updateBillsCodeForm = _updateBillsCodeWindow.updateBillsCodeForm
							.getForm();
					var record = new Ext.data.Record(_updateBillsCodeForm
							.getValues());
					// alert(Ext.util.JSON.encode(record));
					var billsCodeFormInfo = Ext.util.JSON.encode(record);
					var dd = _updateBillsCodeWindow.rowrecord;
					Ext.Ajax.request({
								url : PATH
										+ '/baseInfo/updateBillsCodeAction.do',
								params : {
									billsCodeFormInfoPar : billsCodeFormInfo
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
										dd.set('billDefNormal',record.get('billDefNormal'));
										_updateBillsCodeWindow.close();
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

	}

});

/**
 * 选择年样式Window
 * @class Ext.zhj.CheckYearStyleWindow
 * @extends Ext.Window
 */
Ext.zhj.CheckYearStyleWindow = Ext.extend(Ext.Window, {
	_form : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		Ext.zhj.CheckYearStyleWindow.superclass.constructor.call(this, {
			title : "选择年的样式",
			bodyStyle : 'width:100%',
			height : 230,
			width : 530,
			plain : true,
			bodyStyle : 'padding:15px',
			closeAction : 'close',
			modal : true,
			frame : true,
			items : [{
						xtype : 'form',
						labelWidth : 100,
						frame : true,
						labelAlign : 'right',
						buttonAlign : 'right',
						items : {
							xtype : 'fieldset',
							title : '样式',
							autoHeight : true,
							defaultType : 'radio',
							items : [{
										fieldLabel : '',
										labelSeparator : '',
										boxLabel : 'YYYY',
										name : 'yearRadio',
										inputValue : 'YYYY',
										checked : true
									}, {
										fieldLabel : '',
										labelSeparator : '',
										boxLabel : 'YY',
										name : 'yearRadio',
										inputValue : 'YY'
									}]
						}
					}],
			buttons : [{
				text : '确定',
				handler : function() {
					// 当前窗口
					var _yearStyleWindow = this.ownerCt.ownerCt;
					//当前窗口form对象
					var _yearForm = _yearStyleWindow.findByType("form")[0];
					//选择后的年样式值
					var _yearValue = _yearForm.getForm().getValues().yearRadio;
					
					//获取单据编号规则Form中编辑框
					var _billTypeName = _yearStyleWindow._form.findByType('textfield')[0];
					//单据编辑window中_form
					var _OldBillDefNormal = _yearStyleWindow._form.getForm().getValues().billDefNormal;
				
					var isTrue = _OldBillDefNormal.indexOf('{'+_yearValue + '}');
					//alert(_yearValue + isTrue);
					
					if(isTrue != -1){
						//已添加过该年样式
						_yearStyleWindow.close();
						return false;
					}else{
						
						var _billDefNormal = '' ;
						if(_yearValue == 'YYYY'){
							var b = _OldBillDefNormal.lastIndexOf('{YY}');
							if(b != -1){
								var _billDefNormal = _OldBillDefNormal.substring(0,b+1)+ _yearValue + _OldBillDefNormal.substring(b + 3,_OldBillDefNormal.length);
							}else{
								var _billDefNormal = _OldBillDefNormal + "{" + _yearValue + "}";
								
							}							
						}else if(_yearValue == 'YY'){
							var a = _OldBillDefNormal.indexOf('{YYYY}');
							if(a != -1){
								var _billDefNormal = _OldBillDefNormal.substring(0,a+1)+ _yearValue + _OldBillDefNormal.substring(a + 5,_OldBillDefNormal.length);
							}else{
								var _billDefNormal = _OldBillDefNormal + "{" + _yearValue + "}";
							}
						}
						
						_billTypeName.setValue(_billDefNormal);
						_yearStyleWindow.close();
						return true;
						
					}
				}

			}, {
				text : '取消',
				handler : function() {
					this.ownerCt.ownerCt.close();
				}
			}]
		});

	}

});
