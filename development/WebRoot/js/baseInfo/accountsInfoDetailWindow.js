
/**
 * 定义命名空间
 */
Ext.namespace('Ext.zhj.accountsInfoDetail');


function changeAccountType(value) {
		switch (value) {
			case 0 :
				return "<span style='color:blue;font-weight:bold;'>初始库存</span>";
				break;
			case 1 :
				return "<span style='color:gray;font-weight:bold;'>入库</span>";
				break;
			case 2 :
				return "<span style='color:green;font-weight:bold;'>出库</span>";
				break;
			case 3 :
				return "<span style='color:red;font-weight:bold;'>移库</span>";
				break;
			case 4 :
				return "<span style='color:red;font-weight:bold;'>库存出库</span>";
				break;
		}
}


/**
 * 帐页信息列表
 * @class Ext.zhj.accountsInfoDetail.AccountsInfoList
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.accountsInfoDetail.AccountsInfoList = Ext.extend(Ext.grid.GridPanel, {
	reserveId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		var ds = new Ext.data.JsonStore({
					url : PATH + '/projectQuo/accountsInfoListByReserveIdAction.do',
					root : 'accountsInfoList',
					autoLoad : false,
					fields : ['reserveInforId', 'reserveCode', 'accountType',
							'invoiceCode', 'amount' , 'price','money','brandCode','productCode','productName',
							'productUnit','productBrand','productSource','invoiceId','currencyName','createAccountTimeStr','id','memo','productPosition']
				});
		var _reserveId = this.reserveId;
		ds.on('beforeload', function() {
					ds.baseParams.reserveId = _reserveId;
				});
				
		var _grid = this;
		/*
		ds.on('load',function(_store){
			
			for(var i = 0 ; i < _store.getCount(); i++ ){
				var idx = _store.indexOf(_store.getAt(i));
				var row = _grid.getView().getRow(idx);//根据行号获取行对象
				var element = Ext.get(row);
				element.removeClass('x-grid3-row-selected');
				var color = _store.getAt(i).get('accountType')*1;
				switch(color){
					case 0 :
						//element.addClass('blue-row');
						break;
					case 1 :
						//element.addClass('gray-row');
						break;
					case 2 :
						//element.addClass('green-row');
						break;
					case 3 :
						//element.addClass('red-row');
						break;
				}
				
				
			}
		})*/
		Ext.zhj.accountsInfoDetail.AccountsInfoList.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : 330,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			frame : true, 
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无此库存产品对应的帐页信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),

			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						header : '名称',
						dataIndex : 'productName',
						width : 130
					}, {
						header : '牌号',
						dataIndex : 'brandCode',
						width : 230
					},{
						header : '货品编号',
						dataIndex : 'productCode',
						width : 130
					}, {
						header : '库区/库位',sortable : true,//new addd
						dataIndex : 'productPosition'
					},{
						header : '库存代码',
						dataIndex : 'reserveCode'
					}, {
						header : '计量单位',
						dataIndex : 'productUnit'
					}, {
						header : '数量',
						dataIndex : 'amount',
						width : 60
					},{
						header : '单价',
						dataIndex : 'price',
						width : 60
					},{
						header : '货品金额',
						dataIndex : 'money',
						width : 60
					},{
						header : '品牌',sortable : true,
						dataIndex : 'productBrand'
					},{
						header : '来源',sortable : true,
						dataIndex : 'productSource'
					},{
						header : '备注',sortable : true,//new add
						dataIndex : 'memo'
					},{
						header : '记账日期',
						dataIndex : 'createAccountTimeStr',
						width : 160
					},{
						header : '帐页类型',
						dataIndex : 'accountType',
						renderer : changeAccountType
					}, {
						header : '单据编号',
						dataIndex : 'invoiceCode'
					},    {
						header : '币别',
						dataIndex : 'currencyName'
					},  {
						header : 'ID',
						hidden : true,
						dataIndex : 'id'
					}])
		});

	}
});


/**
 *  库存对应的帐页信息窗口
 * @class Ext.zhj.AccountsInfoDetailWindow
 * @extends Ext.Window
 */
Ext.zhj.AccountsInfoDetailWindow = Ext.extend(Ext.Window, {
	reserveId : null,
	accountsInfoList : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.accountsInfoList = new Ext.zhj.accountsInfoDetail.AccountsInfoList({
					reserveId : this.reserveId
				});
		
		this.accountsInfoList.getStore().reload();

		Ext.zhj.AccountsInfoDetailWindow.superclass.constructor.call(this, {
					title : "查看产品库存对应的帐页详细",
					width : 1050,
					height : 400,
					plain : true,
					constrain : true,
					closable : true,
					closeAction : 'close',
					modal : true,
					buttons : [{
								text : "关闭",
								handler : function() {
									this.close();
								},
								scope : this
							}],
					items : [ {
								height : 390,
								margins : '2 2 2 2',
								items : [this.accountsInfoList]
							}]
				})
	}
})
