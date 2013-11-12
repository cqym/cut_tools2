

/**
 * 
 * 根据产品ID获取产品所有的销售记录
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.CusSalesProductGrid = Ext.extend(Ext.grid.GridPanel, {
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
					+ '/baseInfo/cusSalesProductHistoryListAction.do?productId='
					+ this.productId,
			root : 'cusSalesProductHistoryList',
			totalProperty : 'totalProperty',
			autoLoad : false,
			fields : ['productName', 'productCode', 'brandCode',
					'historyPrice', 'rebate', 'netPrice', 'userName',
					'customerName', 'editDate','quotationCode','contractCode', 'id']
		});
		
		ds.on({'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								ds.baseParams.searchStr = _searchStr;
								//alert(_searchStr);
							}
						},scope : this
					});
		
		Ext.zhj.CusSalesProductGrid.superclass.constructor.call(this, {
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
						emptyText : '该产品无销售记录！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			/*tbar : [{text : "" , handler : function(){
					alert("ddd");
			}},'-', {
						text : "删除",
						handler : function() {
							var _store = this.getStore();

							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的客户历史销售信息!.');
								return;
							} else {
								record = selectionModel.getSelected();
								
							//	alert(record.get("id"));
								Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/deleteCurSalerPriceAction.do',
									params : {
										curSalerPriceIdPar : record.get("id")
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 250,
														icon : Ext.MessageBox.INFO
													});
											_store.remove(record);
											_store.reload();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 250,
														icon : Ext.MessageBox.ERROR
													});
											return;
										}
									}

								});
							}

						},
						scope : this
					}],*/
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
						dataIndex : 'brandCode'
					}, {
						header : '历史面价',
						dataIndex : 'historyPrice'
						
					}, {
						header : '折扣',
						dataIndex : 'rebate'
						
					}, {
						header : '净价',
						dataIndex : 'netPrice'
						
					}, {
						header : '编制人',
						dataIndex : 'userName'
						
					}, {
						header : '客户名称',
						dataIndex : 'customerName'
					}, {
						header : '合同分项名称',
						dataIndex : 'quotationCode',
						width : 150
					},{
						header : '合同编号',
						dataIndex : 'contractCode',
						width : 150
					}, {
						header : '编制时间',
						dataIndex : 'editDate',
						width : 180
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
 * 客户产品销售历史
 * 
 * @class CusSalesPriceHistoryDetailWindow
 * @extends Ext.Window
 */
Ext.zhj.CusSalesPriceHistoryDetailWindow = Ext.extend(Ext.Window, {
			cusSalesProductGrid : null,
			searchCusSalesHistoryForm : null,
			_id : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				};
				Ext.apply(this, _cfg);
				this.cusSalesProductGrid = new Ext.zhj.CusSalesProductGrid({
							productId : this._id
						});
				this.searchCusSalesHistoryForm = new Ext.zhj.SearchCusSalesHistoryForm();
				this.cusSalesProductGrid.getStore().load({
							params : {
								start : 0,
								limit : 10
							}
						});
				Ext.zhj.CusSalesPriceHistoryDetailWindow.superclass.constructor
						.call(this, {
									title : "合同销售详细",
									width : 1000,
									height : 580,
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
									items : [{
												region : "north",
												height : 110,
												frame : true,
												collapsible : true,
												margins : '5 5 5 5',
												items : [this.searchCusSalesHistoryForm]
											}, {

												region : "center",
												height : 470,
												collapsible : true,
												margins : '5 5 5 5',
												items : [this.cusSalesProductGrid]

											}]
								})
			}

		})