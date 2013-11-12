
	var suppliGrid4Direct = Ext.extend(Ext.grid.GridPanel,{
		store:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			var proxy = new Ext.data.HttpProxy({url: PATH + "/reserveOrder/suppliersInforList.do"});
			var Bill = Ext.data.Record.create([
											{name: 'id', type: 'string',mapping:"id"},
											{name: 'supplierCode', type: 'string',mapping:"supplierCode"},
											{name: 'supplierName', type: 'string',mapping:"supplierName"},
											{name: 'supplierShortName' , type: 'string',mapping:"supplierShortName"},
											{name: 'contactPersonFirst', type: 'string',mapping:"contactPersonFirst"},
											{name: 'phoneFirst', type: 'string',mapping:"phoneFirst"},
											{name: 'faxFirst', type: 'string',mapping:"faxFirst"},
											{name: 'contactPersonSec' , type: 'string',mapping:"contactPersonSec"},
											{name: 'phoneSec', type: 'string',mapping:"phoneSec"},
											{name: 'faxSec', type: 'string',mapping:"faxSec"},
											{name: 'brandFirst', type: 'string',mapping:"brandFirst"},
											{name: 'brandSec', type: 'string',mapping:"brandSec"},
											{name: 'brandThird', type: 'string',mapping:"brandThird"},
											{name: 'brandFourth', type: 'string',mapping:"brandFourth"},
											{name: 'contractAddress', type: 'string',mapping:"contractAddress"},
											{name: 'postcode', type: 'string',mapping:"postcode"},
											{name: 'comAdress', type: 'string',mapping:"comAdress"},
											{name: 'bank', type: 'string',mapping:"bank"},
											{name: "accountNumber", type: "string", mapping: "accountNumber"},
											{name: 'homePage', type: 'string',mapping:"homePage"},
											{name: 'email', type: 'string',mapping:"email"},
											{name: 'ownContactPerson', type: 'string',mapping:"ownContactPerson"},
											{name: 'memo', type: 'string',mapping:"memo"}
											]);
			var reader = new Ext.data.JsonReader({ totalProperty: "totalProperty",root: "root"}, Bill);
			this.store = new Ext.data.Store({
				proxy: proxy,
				reader: reader
			});
			suppliGrid4Direct.superclass.constructor.call(this, {
				height:500,
				store: this.store,
				columns: [
						 new Ext.grid.RowNumberer(),
						{header: "id",dataIndex: "id",width: 40,hidden:true},
						{header: "供货商编号",dataIndex:'supplierCode' ,width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "供货商名称",dataIndex:'supplierName',width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "简称",dataIndex:'supplierShortName',width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "联系人1",dataIndex:'contactPersonFirst',width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "电话1",dataIndex:'phoneFirst',width: 100,sortable:true},
						{header: "传真1",dataIndex:'faxFirst', width: 100,sortable:true},
						{header: "联系人2",dataIndex:'contactPersonSec',width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "电话2",dataIndex:'phoneSec' ,width: 100,sortable:true},
						{header: "传真2",dataIndex:'faxSec' ,width: 100,sortable:true},
						{header: "品牌1",dataIndex:'brandFirst' ,width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "品牌2",dataIndex:'brandSec' ,width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "品牌3",dataIndex:'brandThird'  ,width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "品牌4",dataIndex:'brandFourth', width: 100,sortable:true,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
						{header: "合同地址",dataIndex:'contractAddress' ,width: 100,sortable:true},
						{header: "邮编",dataIndex:'postcode',width: 100,sortable:true},
						{header: "通讯地址",dataIndex:'comAdress',width: 100,sortable:true},
						{header: "开户银行",dataIndex:'bank',width: 100,sortable:true},	
						{header: "帐号",dataIndex:'accountNumber',width: 100,sortable:true},
						{header: "主页", dataIndex:'homePage',width: 100,sortable:true},
						{header: "email", dataIndex:"email",width: 100,sortable:true},
						{header: "供应商负责人", dataIndex:"ownContactPerson",width: 120,sortable:true},
						{header: "备注", dataIndex:'memo',width: 100,sortable:true}	
				],
				bbar: new Ext.PagingToolbar({
					store: this.store,
					pageSize: 15,
					displayInfo: true,
					displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
					emptyMsg: "<i>没有数据</i>"
				})
			})
		}
	});

var  rSupplierSelectForm4Direct = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		rSupplierSelectForm4Direct.superclass.constructor.call(this, {
	        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
	        frame:true,labelWidth:70,monitorValid:false,
	        items:[
	           {layout:'column',border:false,labelSeparator:':',frame : true,
	           defaults:{layout: 'form',border:false,columnWidth:.5},
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
	              {items: [{xtype:'textfield',fieldLabel: '供货商编号',name: 'supplierCode',anchor:'90%'}]},
				  {items: [{xtype:'textfield',fieldLabel: '供货商名称',name: 'supplierName',anchor:'90%'}]},
				  {items: [{xtype:'textfield',fieldLabel: '联系人',name: 'contactPerson',anchor:'90%'}]},
				  {items: [{xtype:'textfield',fieldLabel: '品牌',name: 'brand',anchor:'90%'}]}
	           ]
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
	
	var supplierWin4Direct = Ext.extend(Ext.Window,{
		selectForm2:null,
		supplier_grid:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.selectForm2 = new rSupplierSelectForm4Direct();
			this.supplier_grid = new suppliGrid4Direct();
			this.supplier_grid.on('rowdblclick',function(grid,rowIndex,e)
			{
				var s = grid.getStore();
				var r = s.getAt(rowIndex);
				var records = Ext.data.Record.create([
						{name: 'supplierName'},
						{name: 'contactPerson'},
						{name: 'supplierId'},
						{name: 'supplierOwnContactPerson'},
						{name: 'supplierContactPerson'},
						{name: 'supplierPhone'},
						{name: 'supplierFax'}
					]);
					var contractOrderRecord = new records({
						supplierName:r.get('supplierName'),
						contactPerson:r.get('contactPersonFirst'),
						supplierId:r.get('id'),
						supplierOwnContactPerson:r.get('ownContactPerson'),
						supplierOwnContactPerson:r.get('ownContactPerson'),
						supplierContactPerson:r.get('contactPersonFirst'),
						supplierPhone:r.get('phoneFirst'),
						supplierFax:r.get('faxFirst')
					});
					this.fireEvent('onsubmit', contractOrderRecord);
					this.close();
			},this);
			supplierWin4Direct.superclass.constructor.call(this, {
				renderTo: Ext.getBody(),
				title:"选择供应商",  
				height:505,
				width:900,
//				modal:true,
				plain:true,
				draggable:true,
				resizable:false,
				maximizable: true,
				layout: 'border',
				listeners : {
					'render' : function() {
						this.selectForm2.on({
							'search' : function(_form, _values) {
								var _grid = this.supplier_grid.store;
								var searchStr = _values;
								_grid.baseParams.searchStr = Ext.encode(searchStr);
								_grid.reload();
							},
							scope : this
						})
					}
				},
				buttons : [{
					text : "确定",
					handler : function() {
					var selectedItem = this.supplier_grid.getSelectionModel().getCount();
					var record  = this.supplier_grid.getSelectionModel().getSelected();
						if (1!=selectedItem) {
							Ext.Msg.alert('系统提示', '请选择一条记录!');
							return;
						}
						var records = Ext.data.Record.create([
							{name: 'supplierName'},
							{name: 'contactPerson'},
							{name: 'supplierId'},
							{name: 'supplierOwnContactPerson'},
							{name: 'supplierContactPerson'},
							{name: 'supplierPhone'},
							{name: 'supplierFax'}
						]);
						var contractOrderRecord = new records({
							supplierName:record.get('supplierName'),
							contactPerson:record.get('contactPersonFirst'),
							supplierId:record.get('id'),
							supplierOwnContactPerson:record.get('ownContactPerson'),
							supplierContactPerson:record.get('contactPersonFirst'),
							supplierPhone:record.get('phoneFirst'),
							supplierFax:record.get('faxFirst')
						});
						this.fireEvent('onsubmit', contractOrderRecord);
						this.close();
					},
					scope : this
				 },{
					text : "取消",
					handler : function() {
					 this.close();
					},
					scope : this
				 }],
				items : [
					{
						region: 'north',
						iconCls:'icon-grid',
						title: '供应商查询',
						split: true,
						height : 140,
						minSize: 140,
						maxSize: 140,
						collapsible: true,
						margins: '5 5 5 5',
						items : [this.selectForm2]
						
					}, {
						region: 'center',
						split: true,
						minSize: 100,
						maxSize: 200,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : [this.supplier_grid]
					}
				]
			})
			this.addEvents('onsubmit');
		}
	})
	
	








