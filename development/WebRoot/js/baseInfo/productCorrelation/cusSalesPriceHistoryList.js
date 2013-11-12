/**
 * 
 * 销售产品信息搜索
 * 
 */
Ext.zhj.SearchProduct = Ext.extend(Ext.FormPanel, {
	proSort : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		//产品组别信息
		this.proSort = new Ext.zhj.protools.ProSortCombo();
		Ext.zhj.SearchProduct.superclass.constructor.call(this, {
			width : 1000,
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
			el : 'searchProduct',
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
								this.ownerCt.fireEvent('searchProduct', this.ownerCt, this.ownerCt.getValues());
							}
						},this);
					}
				},
				bbar : ['->', {
					text : "搜  索",
					iconCls : 'icon-search',
					handler : function() {
						// 发布search事件
						this.fireEvent('searchProduct', this, this.getValues());
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
										fieldLabel : '产品名称',
										name : 'productName',
										anchor : '90%'
									}]
						}, {
							items : [{
										xtype : 'textfield',
										fieldLabel : '货品编号',
										name : 'productCode',
										anchor : '90%'
									}]
						}, {
							items : [this.proSort]
						}, {
							items : [{
										xtype : 'textfield',
										fieldLabel : '牌号',
										name : 'brandCode',
										anchor : '90%'
									}]
						}, {
							items : [{
										xtype : 'textfield',
										fieldLabel : '品牌',
										name : 'productBrand',
										anchor : '90%'
									}]
						}]
			}]
		});
		/**
		 * 当前对象添加searchConpany方法
		 */
		this.addEvents("searchProduct");
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
//----------------产品销售历史价格--------------------
Ext.zhj.detailProduceSalesHistoryWindow = function(proTooId) {
	return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickProSalHisDetail(\''
			+ proTooId + '\')">查看</a>';
};


Ext.zhj.detailProduceSalesAllHistoryWindow = function(value, cellmeta, record, rowIndex, columnIndex, store) {
	return '<a href="javascript:Ext.ffc.viewSaleBill({toolsId:\'' + value + '\',brandCode:\'' + record.get('brandCode') + '\'})">查看</a>';
};

Ext.zhj.onClickProSalHisDetail = function(_iid) {
	// alert(_iid);
	var cusSalesPriceHistoryDetailWindow = new Ext.zhj.CusSalesPriceHistoryDetailWindow(
			{
				_id : _iid
			});

	// 监听搜索事件
	cusSalesPriceHistoryDetailWindow.searchCusSalesHistoryForm.on({
				'searchCusSalesHistory' : function(_form, _values) {
					cusSalesPriceHistoryDetailWindow.cusSalesProductGrid
							.setSearchStr(_values);
					cusSalesPriceHistoryDetailWindow.cusSalesProductGrid
							.getStore().reload();
				},
				scope : this
			});

	cusSalesPriceHistoryDetailWindow.show();
};


//----------------产品面价历史价格----------------------------------------
Ext.zhj.detailSalesPriceHistoryWindow = function(proTooId) {
	return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickSalPriceHisDetail(\''
			+ proTooId + '\')">查看</a>'
};

Ext.zhj.onClickSalPriceHisDetail = function(_iid) {
	var salesPriceHistoryDetailWindow = new Ext.zhj.SalesPriceHistoryDetailWindow(
			{
				_id : _iid
			});
	salesPriceHistoryDetailWindow.show();
};



//------------------保存用户更改信息------------------------------------
var editProRecord = Ext.data.Record.create([
    {name: 'row'},
    {name : 'field'},
    {name : 'originalValue'}
]);


/**
 * 产品销售历史信息列表
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.CusSalesPriceHistoryGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	editProRecords : null,
	flag : null,
	isSaveHide : true, isCancelHide : true, isImportHide:true,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		//编辑记录级
		this.editProRecords = new Array();
		this.flag = 0;
		
		var fm = Ext.form;
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/cusSalesPriceHistoryListAction.do',
					root : 'cusSalesPriceHistoryList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['productName', 'productCode', 'brandCode',
							'salePrice', 'stockPrice', 'stockPriceDate',
							'salePriceDate', 'productSortCode', 'productBrand',
							'productSource', 'productToolInforId']
				});
		ds.on({
			'beforeload' : function() {
				if (this.searchRecord != null) {
					var _searchStr = Ext.util.JSON
							.encode(this.searchRecord.data);
					ds.baseParams.searchStr = _searchStr;
				}
				
				var dd = this.getStore().getModifiedRecords();
				if (dd.length > 0) {
					Ext.Msg.show({
								title : '提示',
								msg : '&nbsp;&nbsp;&nbsp;&nbsp;产品市场价格信息已经更改，请保存！',
								buttons : Ext.Msg.OK,
								width : 350,
								icon : Ext.MessageBox.INFO
							});
					return false;
				}

			},
			scope : this
		});
		ds.on('load', function() {
					this.getStore().clearModified();
				}, this);
		
		
		Ext.zhj.CusSalesPriceHistoryGrid.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : Ext.getBody().getHeight() - 200,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "cusSalesPriceHistoryGird",
			frame : true,
			ds : ds,
			tbar : [{
				text : "保存更改",
				iconCls : 'icon-submit',
				hidden : this.isSaveHide,
				handler : function() {
					var _store = this.getStore();
					var records = _store.getModifiedRecords();
					if (records.length < 1) {
						Ext.MessageBox.alert('提示', '市场价格记录没有修改！');
					} else {
						var arr = [];
						for (var i = 0; i < records.length; i++) {
							var rec = records[i].data;
							if(Ext.isEmpty(rec.salePriceDate)){
							   Ext.Msg.show({title : '错误提示',
											 msg : "请设定品牌[" + rec.productBrand + "]的价格执行期！",
											 buttons : Ext.Msg.OK,
											 width : 260,
											 icon : Ext.MessageBox.ERROR
											});
								return ;
							}
							arr.push(rec);
						}
						alert(Ext.encode(arr));
						Ext.Ajax.request({
							url : PATH + '/baseInfo/updateProToolsPriceAction.do',
							params : {
								modifProToolsPriceRecordsPar : Ext.encode(arr)
							},
							success : function(response) {
								var responseArray = Ext.util.JSON
										.decode(response.responseText);
								if (responseArray.success == true) {
									Ext.Msg.show({
												title : '成功提示',
												msg : responseArray.msg,
												buttons : Ext.Msg.OK,
												width : 260,
												icon : Ext.MessageBox.INFO
											});
									_store.commitChanges();
								} else {
									Ext.Msg.show({
												title : '错误提示',
												msg : responseArray.msg,
												buttons : Ext.Msg.OK,
												width : 260,
												icon : Ext.MessageBox.ERROR
											});
								}
							}
						});
					}
				},
				scope : this
			}, {
				xtype:'tbseparator',
				hidden : this.isSaveHide
			},{ text : "取消更改", 
					 iconCls : 'icon-reset',
					 hidden : this.isCancelHide,
					 handler : function(){
						  for(var c = 0 ; c < this.editProRecords.length ; c++){
						  	var index = this.editProRecords[c].get('row');
						  	var field = this.editProRecords[c].get('field');
						  	var originalValue = this.editProRecords[c].get('originalValue');
						  	this.getStore().getAt(index).set(field,originalValue);
						  }
						  this.getStore().commitChanges();
					  	},
					 scope : this
			 }, {
				xtype:'tbseparator',
				hidden : this.isCancelHide
			},{ text : "导入面价信息", 
					 iconCls : 'icon-add',
					 hidden : this.isImportHide,
					 handler : function(){
						 var importSalesPriceWindow = new Ext.zhj.ImportSalesPriceWindow();
						 importSalesPriceWindow.show();
					 },
					 scope : this
					 }],
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无产品销售信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 50
							}), {
						header : '货品名称',sortable : true,
						dataIndex : 'productName'
					}, {
						header : '货品编号',sortable : true,
						dataIndex : 'productCode'
					}, {
						header : '牌号',sortable : true,
						dataIndex : 'brandCode',
						width : 180
					}, {
						header : '组别编号',sortable : true,
						dataIndex : 'productSortCode'
					}, {
						header : '品牌',sortable : true,
						dataIndex : 'productBrand'
					}, {
						header : '来源名称',sortable : true,
						dataIndex : 'productSource'
					}, {
						header : '采购价格',
						dataIndex : 'stockPrice',
						hidden : true,
						width : 0
					}, {
						header : '价格执行期',sortable : true,
						dataIndex : 'salePriceDate'
					},{
						header : '市场价格',sortable : true,
						dataIndex : 'salePrice',
						editor : new fm.NumberField({
									allowBlank : false,
									allowNegative : false,
									maxValue : 100000
								})
					}, {
						header : '面价历史详细',
						// hidden : true,
						dataIndex : 'productToolInforId',
						renderer : Ext.zhj.detailSalesPriceHistoryWindow
					}, {
						header : '合同销售详细',
						// hidden : true,
						dataIndex : 'productToolInforId',
						renderer : Ext.zhj.detailProduceSalesHistoryWindow
					}, {
						header : '历史报价记录',//所有销售详细
						// hidden : true,
						dataIndex : 'productToolInforId',
						renderer : Ext.zhj.detailProduceSalesAllHistoryWindow
					}]),
			bbar : new Ext.PagingToolbar({
						pageSize : PAGESIZE,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : ds
					}),
			listeners : {'afteredit' : function(e){
				//alert(e.field + e.originalValue + e.row );
				var record = new editProRecord({row : e.row, field : e.field ,originalValue : e.originalValue });
				this.editProRecords[this.flag] = record;
				this.flag++;
			},scope : this}
		});

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
 * 产品销售历史信息
 */

Ext.onReady(function() {
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("002" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("002004" == childModule[j].id) {
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
			var cusSalesPriceHistoryGrid = new Ext.zhj.CusSalesPriceHistoryGrid(getConfig());
			var searchProductForm = new Ext.zhj.SearchProduct();
			cusSalesPriceHistoryGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			// 监听搜索事件
			searchProductForm.on({
						'searchProduct' : function(_form, _values) {
							cusSalesPriceHistoryGrid.setSearchStr(_values);
							cusSalesPriceHistoryGrid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
					});
			var cusSalesPriceHistoryViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'border',
						items : [{
									region : "north",
									height : 130,
									frame : true,
									collapsible : true,
									margins : '5 5 5 5',
									items : [searchProductForm]
								}, {
									region : "center",
									layout: 'fit',
									collapsible : true,
									margins : '5 5 5 5',
									items : [cusSalesPriceHistoryGrid]
								}]

					});
			cusSalesPriceHistoryViewPort.render("cusSalesPriceHistoryViewPort");
		})
