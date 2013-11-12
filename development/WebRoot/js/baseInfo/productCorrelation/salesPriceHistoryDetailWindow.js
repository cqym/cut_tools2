

/**
 * 
 * 根据产品ID获取产品所有的面价记录
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.SalesProductGrid = Ext.extend(Ext.grid.GridPanel, {
	productId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var editor = new Ext.ux.grid.RowEditor({
					saveText : '提交',
					cancelText : '取消'
				});
		var ds = new Ext.data.JsonStore({
			url : PATH
					+ '/baseInfo/salesProductHistoryListAction.do?productId='
					+ this.productId,
			root : 'salesProductHistoryList',
			totalProperty : 'totalProperty',
			autoLoad : false,
			fields : ['productName', 'productCode', 'productSortCode','brandCode','productBrand','productSource','salePriceDate',
					'userName', 'historyPrice', 'editDate', 'id','userId', 'productToolInforId']
		});
		Ext.zhj.SalesProductGrid.superclass.constructor.call(this, {
			// bodyStyle : 'width:100%',
			width : 983,
			height : 400,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			//el : "cusSalesProductGird",
			//plugins : [editor],
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '该产品无历史面价记录！'
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
						dataIndex : 'productName'
					}, {
						header : '货品编号',
						dataIndex : 'productCode'
					}, {
						header : '牌号',
						dataIndex : 'brandCode',
						width : 180
					}, {
						header : '历史面价',
						dataIndex : 'historyPrice'
						
					}, {
						header : '编制人',
						dataIndex : 'userName'
						
					}, {
						header : '更改时间',
						dataIndex : 'salePriceDate',
						width : 140
					}, {
						header : 'ID',
						hidden : true,
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
 * 产品面价历史
 * @class SalesPriceHistoryDetailWindow
 * @extends Ext.Window
 */
Ext.zhj.SalesPriceHistoryDetailWindow = Ext.extend(Ext.Window, {
			salesProductGrid : null,
			_id : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				};
				Ext.apply(this, _cfg);
				this.salesProductGrid = new Ext.zhj.SalesProductGrid({productId : this._id});
				this.salesProductGrid.getStore().load({
							params : {
								start : 0,
								limit : 10
							}
						});
				Ext.zhj.SalesPriceHistoryDetailWindow.superclass.constructor
						.call(this, {
									title : "产品面价历史详细",
									width : 1000,
									height : 470,
									plain : true,
									closable : true,
									constrain : true,
									modal : true,
									buttons : [{
												text : "关闭",
												handler : function() {
													this.close();
												},
												scope : this
											}],
									items : [this.salesProductGrid]
								})
			}

		})