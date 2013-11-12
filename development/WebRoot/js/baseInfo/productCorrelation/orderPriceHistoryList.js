

Ext.zhj.detailProduceOrderHistoryWindow = function(value, metaData, record, rowIndex, colIndex, store) {
	var pb = record.get('productBrand');
	var bc = record.get('brandCode');
	return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickProOrderHisDetail(\''
			+ value + '\',\'' + pb + '\',\'' + bc + '\')">查看</a>'
};

Ext.zhj.onClickProOrderHisDetail = function(_iid,pb,bc) {
	// alert(_iid);
	var orderPriceHistoryDetailWindow = new Ext.zhj.OrderPriceHistoryDetailWindow(
			{
				_id : _iid,
				productBrand : pb,
				brandCode : bc
			});

	// 监听搜索事件
	orderPriceHistoryDetailWindow.searchOrderHistoryForm.on({
				'searchOrderPriceHistory' : function(_form, _values) {
					orderPriceHistoryDetailWindow.orderPriceProductGrid
							.setSearchStr(_values);
					orderPriceHistoryDetailWindow.orderPriceProductGrid
							.getStore().reload();
				},
				scope : this
			});

	orderPriceHistoryDetailWindow.show();
};

/**
 * 
 * 采购信息搜索
 * 
 */
Ext.zhj.SearchOrderPrice = Ext.extend(Ext.FormPanel, {
			proSort : null,
			productSource : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				this.proSort = new Ext.zhj.protools.ProSortCombo();
				this.productSource = new Ext.zhj.ProductSourceCombox();
				Ext.zhj.SearchOrderPrice.superclass.constructor.call(this, {
							labelAlign : 'right',
							buttonAlign : 'right',
							bodyStyle : 'padding:5px;',
							el : 'searchOrderPrice',
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
												this.ownerCt.fireEvent('searchOrderPrice', this.ownerCt, this.ownerCt.getValues());
											}
										},this);
									}
								},
								bbar : ['->', {
									text : "搜  索",
									iconCls : 'icon-search',
									handler : function() {
										// 发布search事件
										this.fireEvent('searchOrderPrice',
												this, this.getValues());
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
														fieldLabel : '货品名称',
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
										}, {
											items : [this.productSource]
										}]
							}]
						});
				/**
				 * 当前对象添加searchReserve方法
				 */
				this.addEvents("searchOrderPrice");
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
 * 
 * 产品采购历史信息列表
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.OrderPriceHistoryGrid = Ext.extend(Ext.grid.GridPanel, {
	searchRecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/orderPriceHistoryListAction.do',
					root : 'orderPriceHistoryList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['productName', 'productCode', 'brandCode',
							'productSortCode', 'productBrand', 'productSource','salePrice',
							'productToolsInforId','runDate','stockPriceDate']
				});
		ds.on({
					'beforeload' : function() {
						if (this.searchRecord != null) {
							var _searchStr = Ext.util.JSON
									.encode(this.searchRecord.data);
							ds.baseParams.searchStr = _searchStr;
						}
					},
					scope : this
				});
		Ext.zhj.OrderPriceHistoryGrid.superclass.constructor.call(this, {
					bodyStyle : 'width:100%',
					layout: 'fit',
					enableHdMenu : false,
					border : false,
					stripeRows : true,
					el : "orderPriceHistoryGird",
					frame : true,
					ds : ds,
					view : new Ext.grid.GridView({
								deferEmptyText : false,
								emptyText : '无产品采购信息！'
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
								dataIndex : 'brandCode',
								width : 180
							}, {
								header : '组别编号',
								sortable:true,
								dataIndex : 'productSortCode'
							}, {
								header : '品牌',
								sortable:true,
								dataIndex : 'productBrand'
							},{
								header : '价格执行期',
								sortable:true,
								dataIndex : 'stockPriceDate'
							}, {
								header : '市场面价',
									sortable:true,
								dataIndex : 'salePrice'
							}, {
								header : '来源',
								sortable:true,
								dataIndex : 'productSource'
							}, {
								header : '查看详细',
								dataIndex : 'productToolsInforId',
								renderer : Ext.zhj.detailProduceOrderHistoryWindow
							}]),
					bbar : new Ext.PagingToolbar({
								pageSize : (PAGESIZE > 0 ? PAGESIZE : 20),
								emptyMsg : "没有记录",
								displayInfo : true,
								displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
								store : ds
							})
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
 * 产品采购历史信息
 */

Ext.onReady(function() {
			var searchOrderPriceForm = new Ext.zhj.SearchOrderPrice();
			var orderPriceHistoryGrid = new Ext.zhj.OrderPriceHistoryGrid();

			// 监听搜索事件
			searchOrderPriceForm.on({
						'searchOrderPrice' : function(_form, _values) {
							orderPriceHistoryGrid.setSearchStr(_values);
							orderPriceHistoryGrid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
					});

			orderPriceHistoryGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var orderPriceHistoryViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout: 'border',
						items : [{
									region : "north",
									layout: 'fit',
									height : 140,
									frame : true,
									collapsible : true,
									margins : '5 5 5 5',
									items : [searchOrderPriceForm]
								}, {
									region : "center",
									layout: 'fit',
									height : Ext.getBody().getHeight() - 190,
									collapsible : true,
									margins : '5 5 5 5',
									items : [orderPriceHistoryGrid]

								}]

					});
			orderPriceHistoryViewPort.render("orderPriceHistoryViewPort");
		})
