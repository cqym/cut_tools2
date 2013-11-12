Ext.namespace("Ext.baseInfo.taxRate");
//命名空间别名
Taxrate = Ext.baseInfo.taxRate;

Taxrate.TaxRateForm = Ext.extend(Ext.FormPanel, {
	constructor : function() {
		Taxrate.TaxRateForm.superclass.constructor.call(this, {
			defaultType : "textfield",
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "390"},
			labelAlign : 'right',
			buttonAlign : 'right',
			labelWidth : 50,
			items : [
				{xtype : 'numberfield', fieldLabel : "税率", name : 'rate', anchor : '85%', allowBlank : false,
						minValue : 0,
						maxValue : 100
				}, 
				{fieldLabel : "备注", name : 'memo', anchor : '85%'},
				{fieldLabel : "id", name : 'id', hidden : true, hideLabel : true}
			]
		})
	},
	
	/**
	 * 设置表单值
	 * @param {} _r
	 */
	setValues : function(_r) {
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单值
	 * @return {}
	 */
	getValues : function() {
		var _record = new Ext.data.Record(this.getForm().getValues());
		return _record;
	},
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		return this.getForm().isValid();
	}
});

/**
 * 添加品牌信息
 * @class Probrand.MangerWindow
 * @extends Ext.Window
 */
Taxrate.MangerWindow = Ext.extend(Ext.Window, {
	taxRateForm : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.taxRateForm = new Taxrate.TaxRateForm();
		Taxrate.MangerWindow.superclass.constructor.call(this, {
			height : 150,
			width : 300,
			plain : true,
			//bodyStyle : 'padding:15px',
			closeAction : 'hide',
			constrainHeader : true,
			constrain : true,
			modal : true,
			items : this.taxRateForm
		});
		this.addEvents("addsuccess");
	},
	
	ajaxPost : function(_postUrl, _r) {
		//alert(Ext.encode(_r.data));return;
		Ext.Ajax.request({
			url: _postUrl,
			params: { taxRate: Ext.encode(_r.data) },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
											   	
				if(responseArray.success == true){
					this.hide();
					this.fireEvent("addsuccess");
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
});

Taxrate.AddWindow = Ext.extend(Taxrate.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Taxrate.AddWindow.superclass.constructor.call(this, {
			title : "添加税率",
			buttons : [{
				text : '保存',
				handler : function() {
					if(!this.taxRateForm.validator())
						return;
					this.ajaxPost(Taxrate.MangerWindow.ADD_URL, this.taxRateForm.getValues());
				},scope : this

			}, {
				text : '取消',
				handler : function() {
					this.hide();
				},scope : this
			}]
		})
	}
})

Taxrate.ModifyWindow = Ext.extend(Taxrate.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Taxrate.ModifyWindow.superclass.constructor.call(this, {
			title : "修改税率",
			buttons : [{
				text : '保存',
				handler : function() {
					if(!this.taxRateForm.validator())
						return;
					this.ajaxPost(Taxrate.MangerWindow.MODIFY_URL, this.taxRateForm.getValues());
				},
				scope : this

			}, {
				text : '取消',
				handler : function() {
					this.hide();
				},
				scope : this
			}]
		})
	}
})

/**
 * 
 * 品牌信息列表
 * 
 * @class Probrand.BrnadGridPanel
 * @extends Ext.grid.GridPanel
 */
Taxrate.TaxRateGridPanel = Ext.extend(Ext.grid.GridPanel, {
	isAddHide : true,isModifyHide : true,isDelHide : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
			url : PATH + '/baseInfo/taxRateManagerAction.do?method=getTaxRate',
			root : 'taxRate',
			totalProperty : 'totalProperty',
			autoLoad : false,
			remoteSort : true,
			fields : ['rate', 'memo','id']
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();//复选框
		Taxrate.TaxRateGridPanel.superclass.constructor.call(this, {
			//title : '品牌信息',
			iconCls:'icon-grid',
			enableHdMenu : false,
			border : true,
			stripeRows : true,
			frame : true,
			ds : ds,
			sm : this.sm,
			cm : new Ext.grid.ColumnModel([
				this.sm,
				new Ext.grid.RowNumberer({
					header : '序号',
					width : 50
				}), {
					header : '税率',
					sortable : true,
					dataIndex : 'rate', 
					renderer : function(colValue, metadata, record){
		        		return parseFloat(colValue).toFixed(2);
		        	}
				}, {
						header : '备注',
						sortable : true,
						dataIndex : 'memo'
				}]),
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '无税率信息！'
			}),
			
			tbar : [{
				text : "添加",
				iconCls:'icon-add',
				hidden : this.isAddHide,
				handler : function(){
					var addWindow = new Taxrate.AddWindow()
					addWindow.on('addsuccess', function() {
						this.getStore().reload();
					},this)
					addWindow.show();
				},scope : this
			},{
				xtype:'tbseparator',
				hidden : this.isAddHide
			},{
				text : "修改",
				iconCls:'icon-modify',
				hidden : this.isModifyHide,
				handler : function(){
					try {
						var selRecord = this.getSelected();
						var modifyWindow = new Taxrate.ModifyWindow()
						modifyWindow.on('addsuccess', function() {
							this.getStore().reload();
						},this)
						modifyWindow.show();
						modifyWindow.taxRateForm.setValues(selRecord);
					} catch(_e) {
						Ext.Msg.show({
							title : '系统提示',
							msg : _e.message,
							buttons : Ext.Msg.OK,
							width : 200,
							icon : Ext.MessageBox.INFO
						});
					}
				},scope : this
			},{
				xtype:'tbseparator',
				hidden : this.isModifyHide
			},{
				text : "删除",
				iconCls:'icon-delete',
				hidden : this.isDelHide,
				handler : function(){
					var selNodes = this.getSelections();
					if(selNodes.length > 0) {
						Ext.MessageBox.show({
							title:'系统提示',
							msg: '请确认要删除当前税率!',
							buttons: Ext.MessageBox.OKCANCEL,
							fn: this.handleDelete,//若以此种方法调用，需要指定scope
							scope : this
						});
					}
				},scope : this
			}],
			bbar : new Ext.PagingToolbar({
				pageSize : PAGESIZE,
				emptyMsg : "没有记录",
				displayInfo : true,
				displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
		});
	},
	
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择税率后再进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	/**
	 * 返回所有选中的记录
	 * @return {} Record数组
	 */
	getSelections : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择税率后再进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	
	deleteAjaxPost : function(_postUrl, _ids) {
		Ext.Ajax.request({
			url: _postUrl,
			params: { taxRate: _ids },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
											   	
				if(responseArray.success == true){
					this.getStore().reload();
					var idArray = responseArray.data;
					if(!Ext.isEmpty(idArray)) {
						var _codeStr = "";
						if(idArray.length > 0) {
							_codeStr = this.buildCodeStr(idArray);
						}
						Ext.Msg.show({
							title : '系统提示',
							width : 300,
							msg : responseArray.msg + "<br>" + _codeStr,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
					}
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
	},
	
	buildCodeStr : function(idArray) {
		var _codeStr = "[";
		for(var i = 0; i < idArray.length; i++) {
			if(i == idArray.length-1) {
				_codeStr += idArray[i] + "]";
			} else {
				_codeStr += idArray[i] + "<br>";
			}
		} 
		return _codeStr;
	},
	
	handleDelete : function(_btn) {
		if(_btn == 'ok') {
			var selNodes = this.getSelections();
			var _ids = [];
			for(var i = 0; i < selNodes.length; i++) {
				_ids.push(selNodes[i].id);
			}
			this.deleteAjaxPost(Taxrate.MangerWindow.DELETE_URL, Ext.encode(_ids))
		}
	}
});

/**
 * 客户等级信息维护入口
 */
Ext.onReady(function() {
	Taxrate.MangerWindow.ADD_URL = PATH + '/baseInfo/taxRateManagerAction.do?method=addTaxRate';
	Taxrate.MangerWindow.MODIFY_URL = PATH + '/baseInfo/taxRateManagerAction.do?method=modifyTaxRate';
	Taxrate.MangerWindow.DELETE_URL = PATH + '/baseInfo/taxRateManagerAction.do?method=deleteTaxRate';
	
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("001" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("001007" == childModule[j].id) {
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
	
	var taxRateGrid = new Taxrate.TaxRateGridPanel(getConfig());
	taxRateGrid.getStore().load({
		params : {
			start : 0,
			limit : PAGESIZE
		}
	});
	var taxRatePanel = new Ext.Panel({
		width : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight() - 55,
		layout : "fit",
		items:[taxRateGrid]
	});
	taxRatePanel.render("proBrandCt");
	Ext.ffc.ResizeManager.addResizeObject(taxRatePanel);
})
