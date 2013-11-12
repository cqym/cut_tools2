

Ext.ffc.ProductToolsGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.myStore = new Ext.data.JsonStore({
					url : PATH + '/contractOrder/SupHisPriceAction.do?mydear=findSupToolsList',
					root : 'items',
					baseParams : {supplierId:this.supplierId},
					totalProperty: "totalCount",
					autoLoad: true,
					fields : ['productName', 'productCode', 'brandCode',
							'productSortCode', 'productBrand', 'productSource','salePrice',
							'productToolsInforId','id']
				});
		Ext.ffc.ProductToolsGrid.superclass.constructor.call(this, {
			title:'工具信息',
			region: 'west',
			ds : this.myStore,
			store: this.myStore,
			split: true,
			columns: [new Ext.grid.RowNumberer(),//自动行号
							{
								header : '货品名称',
								dataIndex : 'productName'
							},{
								header : '货品编号',
								dataIndex : 'productCode'
							},{
								header : '牌号',
								width: 200,
								dataIndex : 'brandCode'
							},{
								header : '品牌',
								dataIndex : 'productBrand'
							},{
								header : '市场面价',
								dataIndex : 'salePrice'
							},{
								header : 'ID',
								hidden : true,
								dataIndex : 'id'
							}
			],
			layout:'fit',
			listeners:{
			   rowclick : function( grid, rowIndex, e ){
				   var productId = grid.getStore().getAt(rowIndex).data.id;
					grid.ownerCt.orderHisPriceGrid.myStore.reload({params:{productId:productId}});
			   }
			},
			bbar : new Ext.PagingToolbar({
								pageSize : 20,
								emptyMsg : "没有记录",
								displayInfo : true,
								displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
								store : this.myStore
							})
		})
	}
})
Ext.ffc.OrderHisPriceGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.myStore = new Ext.data.JsonStore({
					url : PATH
							+ '/baseInfo/orderProductHistoryListAction.do',
					root : 'orderProductHistoryList',
					totalProperty : 'totalProperty',
					fields : ['productName', 'productCode', 'brandCode',
							'historyPrice', 'userName', 'supplierName',
							'editDate', 'stockPriceDate',
							'productToolsInforId', 'id','historyMarketPrice','historyRebate']
				});
		Ext.ffc.OrderHisPriceGrid.superclass.constructor.call(this, {
			title:'采购历史价格',
			region: 'center',
			split: true,
			ds : this.myStore,
			store: this.myStore,
			columns: [new Ext.grid.RowNumberer(), 
							{
								header : '牌号',
								dataIndex : 'brandCode'
							},{
								header : '历史市场价',
								dataIndex : 'historyMarketPrice'

							},{
								header : '历史折扣',
								dataIndex : 'historyRebate'

							},{
								header : '历史价格',
								dataIndex : 'historyPrice'

							}, {
								header : '供应商名称',
								dataIndex : 'supplierName'

							}, {
								header : '采购价格变动时间',
								dataIndex : 'stockPriceDate',
								width : 170
							}, {
								header : '编制人',
								dataIndex : 'userName'

							}, {
								header : '编制时间',
								dataIndex : 'editDate',
								width : 170
							}, {
								header : 'ID',
								hidden : true,
								dataIndex : 'id'
							}
			],
			layout:'fit',
			bbar : new Ext.PagingToolbar({
								pageSize : 20,
								emptyMsg : "没有记录",
								displayInfo : true,
								displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
								store : this.myStore
							})
		})
	}
})

Ext.ffc.OrderHisPriceSearchWin = Ext.extend(Ext.Window,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.productToolsGrid = new Ext.ffc.ProductToolsGrid({supplierId:this.supplierId});
			this.orderHisPriceGrid = new Ext.ffc.OrderHisPriceGrid();
			this.lableStyle_ = "font-size:9pt;text-align:right;width:80px";
			this.searchForm = new Ext.FormPanel({
				height:50,
				frame: true,
				layout : 'absolute',
				region: 'north',
				items :[
					{xtype:'label',text: '供应商:',x:-30,y:5,style:this.lableStyle_},
					{xtype:'textfield', x:50,y:3, width:170,name: 'supplierName',value:this.supplierName},
					{xtype:'label',text: '牌号:',x:200,y:5,style:this.lableStyle_},
					{xtype:'textfield',  name: 'brandCode',x:280,y:3,width:170},
					{xtype:'hidden',  name: 'supplierId',value:this.supplierId},
					{xtype:'button',  name: 'bt',text:' 搜 索 ',x:470,y:3,width:70,
						listeners : {
							'click':function(){
								var fvalues = this.searchForm.getForm().getValues();
								this.productToolsGrid.myStore.reload({params:fvalues});
							},scope:this
						}
					}

				]
			});
			Ext.ffc.OrderHisPriceSearchWin.superclass.constructor.call(this, {
				title : "采购历史价格查询",                                                               
				height : 530,                                                                     
				width : 800,
				constrainHeader : true,
				maximizable : true,
				modal:true,
				layout: 'border',
				split: true,
				items : [
					this.productToolsGrid,
					this.orderHisPriceGrid,
					this.searchForm
				]
			})
		}
});
