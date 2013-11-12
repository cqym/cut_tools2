

/**
 * 
 * 根据产品ID获取产品所有的采购记录
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.OrderPriceProductGrid = Ext.extend(Ext.grid.GridPanel, {
	searchRecord : null,
	productId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		// this.productId =
		Ext.apply(this, _cfg);
		var editor = new Ext.ux.grid.RowEditor({
					saveText : '提交',
					cancelText : '取消'
				});
		var ds = new Ext.data.JsonStore({
					url : PATH
							+ '/baseInfo/orderProductHistoryListAction.do?productId='
							+ this.productId,
					root : 'orderProductHistoryList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['productName', 'productCode', 'brandCode',
							'historyPrice', 'userName', 'supplierName',
							'editDate', 'stockPriceDate',
							'productToolsInforId', 'id','historyMarketPrice','historyRebate']
				});
				
		ds.on({'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								ds.baseParams.searchStr = _searchStr;
								//alert(_searchStr);
							}
						},scope : this
					});
		Ext.zhj.OrderPriceProductGrid.superclass.constructor.call(this, {
					width : 980,
					height : 400,
//					enableHdMenu : false,
					border : false,
					stripeRows : true,
					//plugins : [editor],
					frame : true,
					ds : ds,
					view : new Ext.grid.GridView({
								deferEmptyText : false,
								emptyText : '该产品无采购记录！'
							}),
					selModel : new Ext.grid.RowSelectionModel({
								singleSelect : true
							}),
					tbar : [{
								text : "录入采购价格",
								handler : function() {
										var toolsId = this.productId;
										var productBrand = this.productBrand;
										var brandCode = this.brandCode;
										var grid = this;
										select_supplier({
											callBackMethod:function(record){
												new Ext.zhj.OrderPriceHistoryEditWindow({
													supplierId:record.get('id'),
													supplierName:record.get('supplierName'),
													productBrand : productBrand,
													brandCode : brandCode,
													toolsId : toolsId,
													grid : grid
												}).show();
											}
										});
								}, scope : this
							}, '-', {
										 text : "删除", handler : function() {
											 var _store = this.getStore();
											 
											 var selectionModel = this.getSelectionModel(); 
											 var selectedCount = selectionModel.getCount();
											 
											 if (selectedCount == 0) {
												Ext.Msg.alert('提示','请选择要删除的采购历史信息!.'); 
												return; 
											 } else {
												 Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中历史价格!', function(btn){
													if(btn != 'yes'){return ;}
													record = selectionModel.getSelected(); //
													 Ext.Ajax.request({ 
														 url : PATH + '/baseInfo/orderProductPriceEditAction.do?m=deleteOrderProductPrice',
														 params : { id : record .get("id") }, 
														 success : function(response) { 
															 if(response.responseText == true || response.responseText == 'true') {
																 Ext.Msg.show({ title : '成功提示', 
																	 msg : "删除成功!", 
																	 buttons : Ext.Msg.OK, 
																	 width : 250, 
																	 icon : Ext.MessageBox.INFO 
																 });
																 _store.remove(record);
																 _store.reload(); 
															} else {
																 Ext.Msg.show({ title : '错误提示', 
																	 msg : '删除失败！', 
																	 buttons : Ext.Msg.OK, 
																	 width : 250, 
																	 icon : Ext.MessageBox.ERROR }); 
																 return; 
															} 
														 }
													 });
												});
											  } 
										 }, scope : this
										 }],
					cm : new Ext.grid.ColumnModel([
							new Ext.grid.CheckboxSelectionModel(),
							new Ext.grid.RowNumberer({
										header : '序号',
										width : 50
									}), {
								header : '货品名称',
								sortable:true,
								dataIndex : 'productName'
							}, {
								header : '货品编号',
								sortable:true,
								dataIndex : 'productCode'
							}, {
								header : '牌号',
								sortable:true,
								dataIndex : 'brandCode'
							},{
								header : '历史市场价',
								sortable:true,
								dataIndex : 'historyMarketPrice'

							},{
								header : '历史折扣',
								sortable:true,
								dataIndex : 'historyRebate'

							},{
								header : '历史价格',
								sortable:true,
								dataIndex : 'historyPrice'

							}, {
								header : '供应商名称',
								sortable:true,
								dataIndex : 'supplierName'

							}, {
								header : '采购价格变动时间',
								dataIndex : 'stockPriceDate',
								sortable:true,
								width : 170
							}, {
								header : '编制人',
								sortable:true,
								dataIndex : 'userName'

							}, {
								header : '编制时间',
								dataIndex : 'editDate',
								sortable:true,
								width : 170
							}, {
								header : 'ID',
								hidden : true,
								sortable:true,
								dataIndex : 'id'
							}]),
					bbar : new Ext.PagingToolbar({
								pageSize : 10,
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
 * 客户产品采购历史
 * 
 * @class CusSalesPriceHistoryDetailWindow
 * @extends Ext.Window
 */
Ext.zhj.OrderPriceHistoryDetailWindow = Ext.extend(Ext.Window, {
			
			orderPriceProductGrid : null,
			searchOrderHistoryForm : null,
			_id : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				};
				Ext.apply(this, _cfg);
				this.orderPriceProductGrid = new Ext.zhj.OrderPriceProductGrid(
						{
							productId : this._id,
							productBrand : this.productBrand,
							brandCode : this.brandCode
						});
				this.searchOrderHistoryForm = new Ext.zhj.SearchOrderHistoryForm();
				this.orderPriceProductGrid.getStore().load({
							params : {
								start : 0,
								limit : 10
							}
						});
				Ext.zhj.OrderPriceHistoryDetailWindow.superclass.constructor
						.call(this, {
									title : "产品采购历史详细",
									width : 1000,
									height : 580,
									plain : true,
									constrain : true,
									closable : true,
									modal : true,
									buttons : [{
												text : "关闭",
												handler : function() {
													this.close();
												},
												scope : this
											}],
									items : [{
												region : "north",
												// itemCls :
												// title : "项目报价",
												layout:'fit',
												height : 110,
												frame : true,
												collapsible : true,
												margins : '5 5 5 5',
												items : [this.searchOrderHistoryForm]
											}, {

												region : "center",
												layout:'fit',
												collapsible : true,
												margins : '5 5 5 5',
												items : [this.orderPriceProductGrid]

											}]
								})
			}

		})


/*采购历史价格编制窗口*/

Ext.zhj.OrderPriceHistoryEditWindow = Ext.extend(Ext.Window, {
	f:null,
	constructor : function(_cfg) {
					if (_cfg == null) {
						_cfg = {};
					};
					Ext.apply(this, _cfg);
					this.f = new Ext.FormPanel({
										labelWidth: 75, 
										frame:true,
										bodyStyle:'padding:5px 5px 0',
										width: 100,
										defaults: {width: 200},
										defaultType: 'textfield',
										items: [{
												xtype:'hidden',
												fieldLabel: '工具id',
												name: 'productToolsInforId',
												allowBlank:false,
												value:this.toolsId
											},{
												fieldLabel: '牌号',
												name: 'brandCode',
												readOnly:true,
												value : this.brandCode
											},{
												fieldLabel: '品牌',
												name: 'productBrand',
												readOnly:true,
												value : this.productBrand
											},{
												fieldLabel: '供应商',
												name: 'supplierName',
												allowBlank:false,
												value : this.supplierName
											},{
												xtype:'hidden',
												fieldLabel: '供应商id',
												name: 'suppliersInforId',
												allowBlank:false,
												value:this.supplierId
											},{
												xtype:'numberfield',
												fieldLabel: '采购价格',
												name: 'historyPrice',
												allowBlank:false
											}
										]
									});

		Ext.zhj.OrderPriceHistoryEditWindow.superclass.constructor.call(this, {
									title : "采购价格编制",
									width : 320,
									height : 210,
									plain : true,
									constrain : true,
									closable : true,
									modal : true,
									layout: 'fit',
									buttons : [{
												text : "保存",
												handler : function() {
													var win = this;
													var fvalues = this.f.getForm().getValues();
													if(fvalues.historyPrice == '' || fvalues.historyPrice * 1 <= 0){
														Ext.Msg.alert("消息", "价格必须为大于零的数字!");
													    return ;
													}
													Ext.Ajax.request({
														url: PATH + '/baseInfo/orderProductPriceEditAction.do?m=addOrderProductPrice',
														params: {OrderPriceHistory:Ext.encode(fvalues)},
														success : function(response) {
															if(response.responseText == 'true' || response.responseText == true){
																Ext.Msg.alert("消息", "添加成功!");
																win.grid.getStore().load({params:{start : 0, limit : 10 }});
															}else{
																Ext.Msg.alert("消息", "添加失败!");
															}
															win.close();
														}
													});
												},
												scope : this
											},{
												text : "关闭",
												handler : function() {
													this.close();
												},
												scope : this
											}],
									items : [this.f]
		});
	}
});