Ext.namespace("Ext.baseInfo.proBrand");
//命名空间别名
Probrand = Ext.baseInfo.proBrand;

/**
 * 品牌来源
 * @class Probrand.ProSourceStore
 * @extends Ext.data.JsonStore
 */
Probrand.ProSourceStore = Ext.extend(Ext.data.JsonStore, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		
		Probrand.ProSourceStore.superclass.constructor.call(this, {
			url : Probrand.ProSourceStore.STORE_URL,
			fields : ['id', 'name'],
			root : 'proSource'
		})
	}
})

Probrand.ProSourceCombo = Ext.extend(Ext.form.ComboBox, {
	ownerCt : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Probrand.ProSourceCombo.superclass.constructor.call(this, {
			store : new Probrand.ProSourceStore(),
	        allowBlank : false,
	        anchor : '85%',
	        editable : false,
	        hideTrigger : false,//隐藏触发按钮
	        displayField: 'name',
	        forceSelection: true,//限制选择的值必须是下拉列表中的值
	        triggerAction: 'all',
	        selectOnFocus:true,//当获得焦点时立即选中输入栏中存在的所有文本
			fieldLabel : '来 源',
			hiddenName : 'sourceName',
			valueField : 'name',
			emptyText:'请选择产品来源',
			listeners : {
				'select' : function(combo, record,index) {
					var _formRecord = this.ownerCt.getValues();
					_formRecord.set('sourceId', record.id);
					this.ownerCt.setValues(_formRecord);
				}
			}
		})
	}
})

/**
 * 采购价格执行期
 * @class Probrand.OrderPriceRunDateStore
 * @extends Ext.data.JsonStore
 */
Probrand.OrderPriceRunDateStore = Ext.extend(Ext.data.JsonStore, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		Probrand.OrderPriceRunDateStore.superclass.constructor.call(this, {
			url : PATH + "/baseInfo/brandManagerAction.do?method=getRunDate",
			fields : ['orderPriceRunDate'],
			root : 'runDate',
			listeners : {
				'load' : function() {
					//alert(this.getCount());
				}
			}
		})
	}
})

Probrand.OrderPriceRunDateCombo = Ext.extend(Ext.form.ComboBox, {
	ownerCt : null,
	datestore : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.datestore = new Probrand.OrderPriceRunDateStore();
		Probrand.OrderPriceRunDateCombo.superclass.constructor.call(this, {
			store : this.datestore ,
	        //allowBlank : false,
	        anchor : '85%',
	        editable : false,
	        hideTrigger : false,//隐藏触发按钮
	        displayField: 'orderPriceRunDate',
	        //forceSelection: true,//限制选择的值必须是下拉列表中的值
	        triggerAction: 'all',
	        selectOnFocus:true,//当获得焦点时立即选中输入栏中存在的所有文本
			fieldLabel : this['fieldLabel'] == null ? '采购执行期' : this['fieldLabel'],
			hiddenName : this['hiddenName'] == null ? 'orderPriceRunDate' : this['hiddenName'],
			valueField : 'orderPriceRunDate',
			emptyText:'请选择品牌采购执行期',
			listeners : {
				'beforequery' : function(){
					//alert("EEEEEEEEEEEEEEEEE");
					var record = this.ownerCt.getValues();
					this.datestore.baseParams.proBrandName = record.get('name');
				}
			}
		})
	}
})






Probrand.ProBrandForm = Ext.extend(Ext.FormPanel, {
	proSource : null,//来源
	orderPriceRunDateCombo : null,
	salesDateCombo : null,
	constructor : function() {
		this.proSource = new Probrand.ProSourceCombo({
			ownerCt : this
		});
		this.orderPriceRunDateCombo = new Probrand.OrderPriceRunDateCombo({ownerCt : this});
		this.salesDateCombo = new Probrand.OrderPriceRunDateCombo({
			ownerCt : this, 
			fieldLabel : '销售执行期',
			hiddenName : 'salePriceRunDate'
		});
		Probrand.ProBrandForm.superclass.constructor.call(this, {
			width : 490,
			height : 230,
			defaultType : "textfield",
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "390"},
			labelAlign : 'right',
			buttonAlign : 'right',
			labelWidth : 100,
			items : [
				{fieldLabel : "常用名", name : 'name', anchor : '85%', allowBlank : false,emptyText:'请输入品牌名称'}, 
				{fieldLabel : "备用名称1", name : 'backupNameFirst', anchor : '85%'},
				{fieldLabel : "备用名称2", name : 'backupNameSec', anchor : '85%'},
				this.proSource,
				{fieldLabel : "sourceId", name : 'sourceId', hidden : true, hideLabel : true},
				this.orderPriceRunDateCombo,
				this.salesDateCombo,
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
Probrand.MangerWindow = Ext.extend(Ext.Window, {
	brandForm : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.brandForm = new Probrand.ProBrandForm();
		Probrand.MangerWindow.superclass.constructor.call(this, {
			height : 250,
			width : 500,
			plain : true,
			//bodyStyle : 'padding:15px',
			closeAction : 'hide',
			constrainHeader : true,
			constrain : true,
			modal : true,
			items : this.brandForm
		});
		this.addEvents("addsuccess");
	},
	
	ajaxPost : function(_postUrl, _r) {
		//alert(Ext.encode(_r.data));return;
		Ext.Ajax.request({
			url: _postUrl,
			params: { brand: Ext.encode(_r.data) },
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

Probrand.AddWindow = Ext.extend(Probrand.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Probrand.AddWindow.superclass.constructor.call(this, {
			title : "添加品牌",
			buttons : [{
				text : '保存',
				handler : function() {
					if(!this.brandForm.validator())
						return;
					this.ajaxPost(Probrand.MangerWindow.ADD_URL, this.brandForm.getValues());
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

Probrand.ModifyWindow = Ext.extend(Probrand.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Probrand.ModifyWindow.superclass.constructor.call(this, {
			title : "修改品牌",
			buttons : [{
				text : '保存',
				handler : function() {
					if(!this.brandForm.validator())
						return;
					this.ajaxPost(Probrand.MangerWindow.MODIFY_URL, this.brandForm.getValues());
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
*查询
*/
Probrand.search = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Probrand.search.superclass.constructor.call(this, {
	        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
	        frame:true,labelWidth:70,monitorValid:false,
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
	              {items: [{xtype:'textfield',fieldLabel: '常用名',name: 'name',anchor:'90%'}]},
				  {items: [{xtype:'textfield',fieldLabel: '来源名称',name: 'sourceName',anchor:'90%'}]},
				  {items: [{xtype:'datefield',fieldLabel: '采购执行期',name: 'orderPriceRunDate',anchor:'90%',format:'Y-m-d'}]},
				  {items: [{xtype:'datefield',fieldLabel: '销售执行期',name: 'salePriceRunDate',anchor:'90%',format:'Y-m-d'}]}
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


/**
 * 
 * 品牌信息列表
 * 
 * @class Probrand.BrnadGridPanel
 * @extends Ext.grid.GridPanel
 */
Probrand.BrnadGridPanel = Ext.extend(Ext.grid.GridPanel, {
	isAddHide : true,isModifyHide : true,isDelHide : true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
			url : PATH + '/baseInfo/brandManagerAction.do?method=getProBrand',
			root : 'brands',
			totalProperty : 'totalProperty',
			autoLoad : false,
			remoteSort : true,
			fields : ['name', 'sourceName','id','sourceId','orderPriceRunDate', 'salePriceRunDate','backupNameFirst', 'backupNameSec']
		});
		this.sm = new Ext.grid.CheckboxSelectionModel();//复选框
		Probrand.BrnadGridPanel.superclass.constructor.call(this, {
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
					header : '常用名',
					sortable : true,
					dataIndex : 'name'
				}, {
						header : '备用名称1',
						sortable : true,
						dataIndex : 'backupNameFirst'
				}, {
						header : '备用名称2',
						sortable : true,
						dataIndex : 'backupNameSec'
				}, /*{
					header : '来源名称',
					sortable : true,
					dataIndex : 'sourceName'
				}, */{
					header : '采购执行期',
					sortable : true,
					dataIndex : 'orderPriceRunDate'
				}, {
						header : '销售执行期',
						sortable : true,
						dataIndex : 'salePriceRunDate'
				}]),
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '无品牌信息！'
			}),
			
			tbar : [{
				text : "添加",
				iconCls:'icon-add',
				hidden : this.isAddHide,
				handler : function(){
					var addWindow = new Probrand.AddWindow()
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
					var selRecord = this.getSelected();
					var modifyWindow = new Probrand.ModifyWindow()
					modifyWindow.on('addsuccess', function() {
						this.getStore().reload();
					},this)
					modifyWindow.show();
					modifyWindow.brandForm.setValues(selRecord);
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
							msg: '请确认要删除当前品牌!',
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
			throw Error('请选择品牌后再进行操作！');
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
			throw Error('请选择品牌后再进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	
	deleteAjaxPost : function(_postUrl, _ids) {
		Ext.Ajax.request({
			url: _postUrl,
			params: { brand: _ids },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
											   	
				if(responseArray.success == true){
					this.getStore().reload();
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
	
	handleDelete : function(_btn) {
		if(_btn == 'ok') {
			var selNodes = this.getSelections();
			var _ids = [];
			for(var i = 0; i < selNodes.length; i++) {
				_ids.push(selNodes[i].id);
			}
			this.deleteAjaxPost(Probrand.MangerWindow.DELETE_URL, Ext.encode(_ids))
		}
	}
});

/**
 * 客户等级信息维护入口
 */
Ext.onReady(function() {
	Probrand.ProSourceStore.STORE_URL = PATH + '/proTools/getProSortByCode.do?method=getProSource';
	Probrand.MangerWindow.ADD_URL = PATH + '/baseInfo/brandManagerAction.do?method=addBrand';
	Probrand.MangerWindow.MODIFY_URL = PATH + '/baseInfo/brandManagerAction.do?method=modifyBrand';
	Probrand.MangerWindow.DELETE_URL = PATH + '/baseInfo/brandManagerAction.do?method=deleteBrand';
	
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("002" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("002005" == childModule[j].id) {
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
	
	var proBrandGrid = new Probrand.BrnadGridPanel(getConfig());
	var proSearch = new Probrand.search();
	proBrandGrid.getStore().load({
		params : {
			start : 0,
			limit : PAGESIZE
		}
	});
	var brandPanel = new Ext.Panel({
		width : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight() - 55,
		layout : "border",
		listeners : {
					'render' : function() {
						//监听搜索事件。
						proSearch.on({
							'search' : function(_form, _values) {
								var _grid = proBrandGrid.getStore();
								_grid.baseParams.name = _values.name;
								_grid.baseParams.sourceName = _values.sourceName;
								_grid.baseParams.orderPriceRunDate = _values.orderPriceRunDate;
								_grid.baseParams.salePriceRunDate = _values.salePriceRunDate;
								_grid.load({params : {start : 0, limit : PAGESIZE}});
							},
							scope : this
						})
					}
				},
		items:[{
				region: 'north',
				iconCls:'icon-grid',
				title: '品牌查询',
				split: true,
				height : 110,
				minSize: 110,
				maxSize: 110,
				collapsible: true,
				margins: '5 5 5 5',
				items : [proSearch]
				
			},{
				region: 'center',
				split: true,
				height : 300,
				minSize: 300,
				maxSize: 300,
				collapsible: true,
				layout: 'fit',
				margins: '-5 5 5 5',
				items : [proBrandGrid]
			}]
	});
	brandPanel.render("proBrandCt");
})
