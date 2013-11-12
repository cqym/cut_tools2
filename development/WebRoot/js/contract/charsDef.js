
Ext.ffc.StatisticsWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	    Ext.apply(this, _cfg);
		Ext.ffc.StatisticsWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : Ext.getBody().getWidth() - 200,
			height : 443,
			modal : true,
			layout: 'border',
			resizable :false,
			modal : true
		});
	}
});


Ext.ffc.StatisticsMoneyByMonthsForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	Ext.apply(this, _cfg);
    Ext.ffc.StatisticsMoneyByMonthsForm.superclass.constructor.call(this, {
			labelWidth: 75, 
			frame:true,
			header:true,
			collapsible: true,
			width: 200,
			region: 'west',
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 100},
			defaultType: 'textfield',
			items: [{
					fieldLabel: '客户名称',
					name: 'customerName',
					allowBlank:true,
					listeners : {
						'focus' : function(){
							var t = this;
							ww_select_custormer(function(v){
								t.setValue(v);
							});
						}
					}
				},{
					fieldLabel: '客户负责人',
					name: 'ownContactPerson',
					allowBlank:true
				},
				{xtype:'datefield',fieldLabel: '开始时间',name: 'startTime',anchor:'100%', format:'Y-m'},
				{xtype:'datefield',fieldLabel: '结束时间',name: 'endTime',anchor:'100%', format:'Y-m'}
			],
			buttons : [
					{xtype:'button',text: '提交',iconCls:'icon-search',name: 'seachBt',width:80,
						handler : function() {
							var p = this.getForm().getValues();
							var _store = this._store;
							for(var i in p){
								_store.setBaseParam(i, p[i]);
							}
							_store.load();
						},scope : this
					},{  
						text:'重置',iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								this.getForm().reset();
							},scope : this
						}
						
				}]
		});
	}
});

Ext.ffc.StatisticsMoneyPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	Ext.apply(this, _cfg);
    Ext.ffc.StatisticsMoneyPanel.superclass.constructor.call(this, {
			width : Ext.getBody().getWidth() - 400,
			layout:'fit',
			region: 'center',
			split:true,
			items: {
				xtype: 'linechart',
				store: this._store,
				xField: 'NAME',
				yField: 'visits',
				yAxis: new Ext.chart.NumericAxis({
					displayName: 'visits',
					abelRenderer : Ext.util.Format.numberRenderer('0,0')
				}),
			    tipRenderer : function(chart, record){
					return record.data.NAME + "月，" + Ext.util.Format.number(record.data.visits, '0,0');
				},
				listeners: {
					itemclick: function(o){
						var rec = this._store.getAt(o.index);
						Ext.example.msg('Item Selected', 'You chose {0}.', rec.get('name'));
					},scope:this
				}
			}
		});
	}
});

Ext.ffc.StatisticsMoneyForOwnerPersonForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	Ext.apply(this, _cfg);
    Ext.ffc.StatisticsMoneyForOwnerPersonForm.superclass.constructor.call(this, {
			labelWidth: 75, 
			frame:true,
			header:true,
			collapsible: true,
			width: 200,
			region: 'west',
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 100},
			defaultType: 'textfield',
			items: [
				{xtype:'datefield',fieldLabel: '开始时间',name: 'startTime',anchor:'100%', format:'Y-m'},
				{xtype:'datefield',fieldLabel: '结束时间',name: 'endTime',anchor:'100%', format:'Y-m'}
			],
			buttons : [
					{xtype:'button',text: '提交',iconCls:'icon-search',name: 'seachBt',width:80,
						handler : function() {
							var p = this.getForm().getValues();
							var _store = this._store;
							for(var i in p){
								_store.setBaseParam(i, p[i]);
							}
							_store.load();
						},scope : this
					},{  
						text:'重置',iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								this.getForm().reset();
							},scope : this
						}
						
				}]
		});
	}
});

Ext.ffc.StatisticsMoneyForOwnerPersonPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	Ext.apply(this, _cfg);
    Ext.ffc.StatisticsMoneyForOwnerPersonPanel.superclass.constructor.call(this, {
        frame:true,
        width : Ext.getBody().getWidth() - 400,
		region: 'center',
        layout:'fit',
        items: {
            xtype: _cfg.charXtype,
            store: this._store,
            xField: 'own_contact_person',
            yAxis: new Ext.chart.NumericAxis({
                //displayName: 'Visits',
                labelRenderer : Ext.util.Format.numberRenderer('0,0')
            }),
            tipRenderer : function(chart, record, index, series){
                if(series.yField == 'money'){
                    return "[" + record.data.own_contact_person + "]：" + chart.ownerCt.moneyTitle + Ext.util.Format.number(record.data.money, '0,0');
                }else{
                    return "[" + record.data.own_contact_person + "]：" + chart.ownerCt.hMoneyTitle + Ext.util.Format.number(record.data.h_money, '0,0') ;
                }
            },scope:this,
            chartStyle: {
                padding: 10,
                animationEnabled: true,
                font: {
                    name: 'Tahoma',
                    color: 0x444444,
                    size: 11
                },
                dataTip: {
                    padding: 5,
                    border: {
                        color: 0x99bbe8,
                        size:1
                    },
                    background: {
                        color: 0xDAE7F6,
                        alpha: .9
                    },
                    font: {
                        name: 'Tahoma',
                        color: 0x15428B,
                        size: 10,
                        bold: true
                    }
                },
                xAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xeeeeee}
                },
                yAxis: {
                    color: 0x69aBc8,
                    majorTicks: {color: 0x69aBc8, length: 4},
                    minorTicks: {color: 0x69aBc8, length: 2},
                    majorGridLines: {size: 1, color: 0xdfe8f6}
                }
            },
            series: [{
                type: _cfg.charType,
                //displayName: 'Page Views',
                yField: 'money',
                style: {
                    image:'bar.gif',
                    mode: 'stretch',
                    color:0x99BBE8
                }
            },{
                type:_cfg.charType,
                //displayName: 'Visits',
                yField: 'h_money',
                style: {
                    color: 0xFFCCCC
                }
            }]
        }
    });
	}
});

Ext.ffc.StatisticsMoneyByYearMonthsForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
	Ext.apply(this, _cfg);
    Ext.ffc.StatisticsMoneyByYearMonthsForm.superclass.constructor.call(this, {
			labelWidth: 75, 
			frame:true,
			header:true,
			collapsible: true,
			width: 200,
			region: 'west',
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 100},
			defaultType: 'textfield',
			items: [{
					fieldLabel: '客户名称',
					name: 'customerName',
					allowBlank:true,
					listeners : {
						'focus' : function(){
							var t = this;
							ww_select_custormer(function(v){
								t.setValue(v);
							});
						}
					}
				},{
					fieldLabel: '客户负责人',
					name: 'ownContactPerson',
					allowBlank:true
				},
				new Ext.ffc.YearComboBox({fieldLabel: '第一年度',name: 'year1',hiddenName:'year1',displayField:'year1'}),
				new Ext.ffc.YearComboBox({fieldLabel: '对比年度',name: 'year2',hiddenName:'year2',displayField:'year2'})
			],
			buttons : [
					{xtype:'button',text: '提交',iconCls:'icon-search',name: 'seachBt',width:80,
						handler : function() {
							var p = this.getForm().getValues();
							var _store = this._store;
							for(var i in p){
								_store.setBaseParam(i, p[i]);
							}
							_store.load();
						},scope : this
					},{  
						text:'重置',iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								this.getForm().reset();
							},scope : this
						}
						
				}]
		});
	}
});

function ww_select_custormer(callBackMethod){
	var QuoInfoFormRecord = Ext.data.Record.create([
		{name: 'customerCode'},
		{name : 'customerName'},
		{name : 'cusContactPerson'},
		{name : 'customerPhone'},
		{name : 'customerFax'},
		{name : 'id'},
		{name : 'customerId'},
		{name : 'overallRebate'},
		{name : 'editorName'},
		{name : 'userName'},
		{name : 'paymentConditionMode'}
	]);
	var _selModel = new Ext.grid.RowSelectionModel({singleSelect : true});
	var _store = new Ext.data.JsonStore({
					autoLoad:true,
					url :PATH + "/generalQuo/getCustomerAction.do",
					remoteSort : true,
					totalProperty : 'totalProperty',
					root : 'customer',
					fields : ['customerCode','customerName','contactPersonFirst','phoneFirst','faxFirst','contactPersonSec','phoneSec','faxSec',
					'ownContactPerson','degreeCode','email','contractAddress','postcode','comAdress','bank',
					'accountNumber','taxCode','homePage','memo', 'id','closingAccountMode']
				});
		_store.load({params:{'start':0,"limit":20}});
	var CustomerGrid = new Ext.grid.GridPanel({
				title : '客户信息列表',
				hideHeaders : false,//隐藏表头。
				store : _store,
				//sm : sm,
				columns : [//sm,
					{header : '客户编号',dataIndex : 'customerCode',sortable: true},
					{header : '客户名称' ,  dataIndex : 'customerName',width:150,sortable: true},
					{header : '联系人1', dataIndex : 'contactPersonFirst',sortable: true},
					{header : '电话1', dataIndex : 'phoneFirst',sortable: true},
					{header : '传真1', dataIndex : 'faxFirst',sortable: true},
					{header : '联系人2', dataIndex : 'contactPersonSec',sortable: true},
					{header : '电话2', dataIndex : 'phoneSec',sortable: true},
					{header : '传真2', dataIndex : 'faxSec',sortable: true},
					{header : '我方联系人', dataIndex : 'ownContactPerson',sortable: true},
					{header : '客户等级', dataIndex : 'degreeCode',sortable: true},
					{header : 'Email', dataIndex : 'email',sortable: true},
					{header : '合同地址', dataIndex : 'contractAddress',sortable: true},
					{header : '邮编', dataIndex : 'postcode',sortable: true},
					{header : '通讯地址', dataIndex : 'comAdress',sortable: true},
					{header : '开户银行', dataIndex : 'bank',sortable: true},
					{header : '账号', dataIndex : 'accountNumber',sortable: true},
					{header : '税号', dataIndex : 'taxCode',sortable: true},
					{header : '主页', dataIndex : 'homePage',sortable: true},
					{header : '备注', dataIndex : 'memo',sortable: true},
					{header : 'id', dataIndex : 'id', hidden : true}
				],
				selModel : _selModel,
				width : 1250,
				height : 350,
				bodyStyle:'width:100%',
				iconCls:'icon-grid',
				//frame : true,
				stripeRows :true,
				viewConfig: {   
					forceFit:true,
					autoFill : true,
					deferEmptyText : false,
					//enableRowBody : false,
					emptyText : '无客户信息！'
				},
				enableHdMenu : false
			});

	Ext.ffc.CustomerWindow = Ext.extend(Ext.Window, {
		isCopy : false,
		quoRecord : null,
		grid : null,
		paramRecord : null,
		quoType : null,
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.grid = CustomerGrid;
			
			this.paramRecord = new QuoInfoFormRecord();
			Ext.ffc.CustomerWindow.superclass.constructor.call(this, {
				title : '客户信息',
				width : Ext.getBody().getWidth()-100,
				height : 450,
				closable : true,
				draggable : true,
				resizable : true,
				constrainHeader : true,
				layout : 'fit',
				items : this.grid,
				closeAction : 'hide',
				listeners : {
					'render' : function() {
						this.grid.on({
							'dblclick' : function() {
								this.buttons[0].fireEvent('click');
							},scope : this
						})
						var _customerStore = this.grid.getStore();
						_customerStore.on({
							"beforeLoad" : function() {
								_customerStore.baseParams.searchStr = Ext.util.JSON.encode(this.paramRecord);
							}, 
							scope:this
						});
					},scope : this
				},
				buttons : [{
					text : "确定",
					listeners : {
						'click' : function(_btn, e) {
							fn : this.onSubmitClick(_btn, e);
						},scope : this
					}
				},{
					text : "取消",
					handler : function () {
						this.hide();
					},scope : this
				}],
				//搜索栏
				tbar : [new Ext.form.Label({
					html : "&nbsp;客户编号:&nbsp;"
				
				}), new Ext.form.TextField({
					name : 'customerCode',
					id : 'customerCode',
					enableKeyEvents: true,
					listeners : {
						'specialkey' : function(field, e) {
							if(e.getKey() == e.ENTER) {
								this.fireEvent('change');
								this.ownerCt.ownerCt.grid.getStore().reload({params : {start : 0, limit : 20}});
							}
						},
						
						'change' : function() {
							this.ownerCt.ownerCt.paramRecord.customerCode = this.getValue();
						}
					}
				
				}),'-', new Ext.form.Label({
					html : "客户名称:&nbsp;"
				
				}), new Ext.form.TextField({
					name : 'customerName',
					id : 'customerName',
					listeners : {
						'specialkey' : function(field, e) {
							if(e.getKey() == e.ENTER) {
								this.fireEvent('change');
								this.ownerCt.ownerCt.grid.getStore().reload({params : {start : 0, limit : 20}});
							}
						},
						'change' : function() {
							this.ownerCt.ownerCt.paramRecord.customerName = this.getValue();
						}
					}
				
				}),'-',{  
						text:'搜索'  
						,iconCls:'icon-search',
						listeners : {
							'click' : function(){
								this.grid.getStore().load({params : {start : 0, limit : 20}});
							},scope : this
						}
						
					},'-',{  
						text:'重置'  
						,iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								var cusNameCMP = Ext.getCmp("customerName");
								var cusCodeCMP = Ext.getCmp("customerCode");
								cusNameCMP.reset();
								cusNameCMP.fireEvent("change");
								cusCodeCMP.reset();
								cusCodeCMP.fireEvent("change");
								
							},scope : this
						}
						
					}],
				//分页信息栏
				bbar : new Ext.PagingToolbar({
					pageSize : 20,
					emptyMsg: "没有记录",
					displayInfo: true,
					displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
					store : this.grid.getStore()
				})
			})
			this.addEvents('onsubsuccess');
		},
		
		onSubmitClick : function() {
			var record = _selModel.getSelected();
			if(callBackMethod) {
				callBackMethod(record.get("customerName"));
			}
			this.close();
		},scope:this
	})
	new Ext.ffc.CustomerWindow().show();
}