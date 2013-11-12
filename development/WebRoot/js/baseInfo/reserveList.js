


Ext.zhj.onClickAccountsInfoDetail = function(_id) {
		var _accountsInfoDetailWindow =  new Ext.zhj.AccountsInfoDetailWindow({reserveId : _id});
		_accountsInfoDetailWindow.show();
	}


Ext.zhj.AccountsInforWindow = function(id) {
		return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickAccountsInfoDetail(\''
				+ id + '\')">查看</a>'
	}
	


/**
 * 
 * 库存信息搜索
 * 
 */
Ext.zhj.SearchReserve = Ext.extend(Ext.FormPanel, {
	proSort : null,
	productSource : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.proSort = new Ext.zhj.protools.ProSortCombo();
		this.productSource = new Ext.zhj.ProductSourceCombox();
		Ext.zhj.SearchReserve.superclass.constructor.call(this, {
					labelAlign : 'right',
					buttonAlign : 'right',
					bodyStyle : 'padding:5px;',
					el : 'searchReserve',
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
										this.ownerCt.fireEvent('searchReserve', this.ownerCt, this.ownerCt.getValues());
									}
								},this);
							}
						},
						bbar : ['->', {
							text : "搜  索",
							iconCls : 'icon-search',
							handler : function() {
								//发布search事件
			           			this.fireEvent('searchReserve',this, this.getValues());
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
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '货品编号',
												name : 'productCode',
												anchor : '90%'
											}]
								},{
									items : [this.proSort]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '牌号',
												name : 'brandCode',
												anchor : '90%'
											}]
								},{
									items : [{
												xtype : 'textfield',
												fieldLabel : '品牌',
												name : 'productBrand',
												anchor : '90%'
											}]
								},{
									items : [this.productSource]
								}
										]
					}]					
				});
		/**
		 * 当前对象添加searchReserve方法
		 */
		this.addEvents("searchReserve");
	},
	
	/**
	 * 获取搜索条件
	 * @return {} 返回搜索条件:Record
	 */
	getValues : function() {
		var record = new Ext.data.Record(this.getForm().getValues());
		return record;
	}
	

});


/**
 * 
 * 库存信息列表
 * @class Ext.zhj.CompanyGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.ReserveGrid = Ext.extend(Ext.grid.GridPanel, {
	searchRecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/reserveListAction.do',
					root : 'reserveList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['productCode', 'brandCode', 'toolsId', 'parentToolsId',
							'leaf', 'productName', 'productUnit','reserveCode','amount','price','productSort','currencyName',
							'productBrand', 'productSource', 'productPosition','slaveFile','memo','id']
				});
		ds.on({'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								ds.baseParams.searchStr = _searchStr;
							}
						},scope : this
					});
		ds.on({'load' : function(sto) {
							var totalPrice = sto.reader.jsonData.totalPrice;
							if(Ext.DomQuery.select('TD[ID=totalPriceEl]',Ext.getDom(this.getBottomToolbar().getEl())).length){
								  Ext.DomHelper.overwrite(Ext.DomQuery.select('TD[ID=totalPriceEl]',Ext.getDom(this.getBottomToolbar().getEl()))[0],{tag:'td',id:'totalPriceEl',html:'总价：'+ totalPrice},false);
						  }else{
							    Ext.DomHelper.insertAfter(Ext.DomQuery.select('TD:first',Ext.getDom(this.getBottomToolbar().getEl()))[0],{tag:'td',id:'totalPriceEl',html:'总价：'+ totalPrice},false);
						  }
						},scope : this
					});
		Ext.zhj.ReserveGrid.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			layout: 'fit',
			//height : Ext.getBody().getHeight() - 210,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "reserveGird",
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无库存信息！'
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
						header : '货品名称',
						sortable : true,
						dataIndex : 'productName'
					}, {
						header : '牌号',sortable : true,
						dataIndex : 'brandCode',
						width : 180
					},{
						header : '货品编号',sortable : true,
						dataIndex : 'productCode'
					},  {
						header : '计量单位',sortable : true,
						dataIndex : 'productUnit'

					}, {
						header : '库存数量',//sortable : true,
						dataIndex : 'amount'
					}, {
						header : '品牌',sortable : true,
						dataIndex : 'productBrand'
					},{
						header : '来源',sortable : true,
						dataIndex : 'productSource'
					},{
						header : '备注',sortable : true,
						dataIndex : 'memo'
					},/*{
						header : '单价',
						dataIndex : 'price',
						hidden : true,
						width : 0
					}, {
						header : '组别',
						dataIndex : 'productSort',
						hidden : true,
						width : 0
					}, {
						header : '币别',
						dataIndex : 'currencyName',
						hidden : true,
						width : 0
					},   {
						header : '库存代码',sortable : true,
						dataIndex : 'reserveCode'
					},{
						header : '库区/库位',sortable : true,
						dataIndex : 'productPosition'
					}, {
						header : '附件',sortable : true,
						dataIndex : 'slaveFile'
					},  */{
						header : '查看详细',
						dataIndex : 'id',
						renderer : Ext.zhj.AccountsInforWindow
					}]),
			bbar : new Ext.PagingToolbar({
						pageSize : PAGESIZE,
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
 * 库存信息
 */
Ext.onReady(function() {
			
			var searchReserveForm = new Ext.zhj.SearchReserve();
			var reserveGrid = new Ext.zhj.ReserveGrid();
			
			//监听搜索事件
			searchReserveForm.on({
						'searchReserve' : function(_form, _values) {
							reserveGrid.setSearchStr(_values);
							reserveGrid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
			});
			
			reserveGrid.getStore().load({
						params : {
							start : 0,
							limit : PAGESIZE
						}
					});
			var companyViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						items : [{
				region : "north" ,
				height : 140 ,
				layout: 'fit',
				frame : true,
				collapsible : true,
				margins: '5 5 5 5',
				items : [searchReserveForm]
			},{
				
				region : "center",
				layout: 'fit',
				height : Ext.getBody().getHeight() - 190 ,
				collapsible : true,
				margins: '5 5 5 5',
				items : [reserveGrid]
				
			}]

					});

			companyViewPort.render("reserveViewPort");
		})



