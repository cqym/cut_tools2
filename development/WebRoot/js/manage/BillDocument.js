
/**
 * 单据管理 Grid
 * @class BillDocGrid
 * @extends Ext.grid.EditorGridPanel
 */
BillDocGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	billType : null,
	statusCombo : null,
	billCodeField : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.statusCombo = this.buildStatusCombo(this.billType);
		this.billCodeField = new Ext.form.TextField({
        	name : 'billCode',
	      	enableKeyEvents: true,
	      	listeners : {
            	'specialkey' : function(field, e) {
            		if(e.getKey() == e.ENTER) {
            			//this.fireEvent('change');
            			this.store.baseParams.billCode = field.getValue();
            			this.getStore().reload({params : {start : 0, limit : 15}});
            		}
            	},
            	
	      		'change' : function(field) {
	      			
	      		},scope : this
	      	}
        
        })
		var ds = new Ext.data.JsonStore({
			url : PATH + '/manage/billdoc/billDocumentAction.do?method=getBillDocument',
			root : 'billDoc',
			totalProperty : 'totalProperty',
			autoLoad : false,
			baseParams : {billType : this.billType},
			remoteSort : true,
			fields : ['id', 'billCode', 'status']
		});
		this.sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});//复选框
		BillDocGrid.superclass.constructor.call(this, {
			
			//title : '品牌信息',
			iconCls:'icon-grid',
			enableHdMenu : false,
			border : true,
			stripeRows : true,
			frame : true,
			ds : ds,
			sm : this.sm,
			clicksToEdit: 1,
			cm : new Ext.grid.ColumnModel([this.sm, new Ext.grid.RowNumberer({}), 
				{
					header : '单据编号',
					sortable : true,
					width : 340,
					dataIndex : 'billCode'
				}, {
					header : '单据状态',
					sortable : true,
					editor : this.statusCombo,
					renderer : function(value,metadata,record,rowIndex,colIndex,store) {
						var viewValue = "";
						this.editor.store.each(function(_r) {
							if(!Ext.isEmpty(_r.data['value']) && _r.data['value'] == value) {
								//alert(_r.data['value'] + " == " + value + " ; " + _r.data['status'])
								viewValue = _r.data['status'];
								return false;
							}
						});
						return viewValue;
					},
					dataIndex : 'status'
				}, {
					header : 'id',
					sortable : true,
					hidden : true,
					dataIndex : 'id'
				}]),
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '无单据信息！'
			}),
			
			tbar : [{
				text : "保存更改",
				iconCls:'icon-modify',
				//hidden : this.isModifyHide,
				handler : function(){
					try {
						var selRecord = this.getSelected();
						Ext.MessageBox.show({
							title:'系统提示',
							msg: '确定修改单据状态？',
							buttons: Ext.MessageBox.OKCANCEL,
							fn: this.handleSave,//若以此种方法调用，需要指定scope
							scope : this
						});
					} catch(_e) {
						Ext.Msg.show({
							title : '信息提示',
							msg : _e.message,
							width : 260,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
					}
					
				},scope : this
			},'->',new Ext.form.Label({
            	html : "&nbsp;单据编号:&nbsp;"
            
            }), this.billCodeField
            ,'-',{  
					text:'搜索'  
					,iconCls:'icon-search',
					listeners : {
						'click' : function(){
							this.store.baseParams.billCode = this.billCodeField.getValue();
							this.getStore().load({params : {start : 0, limit : 15}});
						},scope : this
					}
					
				},'-',{  
					text:'重置'  
					,iconCls:'icon-reset',
					listeners : {
						'click' : function(){
							this.billCodeField.reset();
						},scope : this
					}
					
				}],
			
			bbar : new Ext.PagingToolbar({
				pageSize : 15,
				emptyMsg : "没有记录",
				displayInfo : true,
				displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
		
		})
	},
	
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择单据再进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	handleSave : function(_btn) {
		if(_btn == 'ok') {
			var selRecord = Ext.encode(this.getSelected().data);
			Ext.Ajax.request({
				url : PATH + '/manage/billdoc/billDocumentAction.do?method=updateBillDoc',
				params : { billDoc : selRecord, billType : this.billType},
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						Ext.Msg.show({
							title : '成功提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
						this.store.commitChanges();
					} else {
						Ext.Msg.show({
							title : '错误提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				},
				scope : this
			});
		}
	},
	
	buildStatusCombo : function(_billType) {
		switch (_billType) {
			case 1 :
				return new Ext.ftl.ContractStatusComboBox();
			case 2 :
				return new Ext.ftl.QuotationStatusCombox();
			case 3 :
				return new Ext.ftl.BillStockOrderComboBox();
			case 4 : 
				return new Ext.ftl.BillArrivalStatusCombo();
			case 5 : 
				return new Ext.ftl.OutStockStatusComboBox();
			case 6 : 
				return new Ext.ftl.DeliveryStatusComboBox();
			case 7 : 
				return new Ext.ffc.AccountStatusComboBox();
			case 8 :
				return new Ext.ffc.ReservePlanStatusComboBox({anchor:'100%'});
		}
	}
	
})
/**
 * 单据管理菜单
 * @class BillMenu
 * @extends Ext.menu.Menu
 */
BillMenu = Ext.extend(Ext.menu.Menu, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		BillMenu.superclass.constructor.call(this, {
			items : [{
		 		text: '合同管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(1)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '报价单管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(2)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '订单管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(3)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '入库单管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(4)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '出库单管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(5)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '交货单管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(6)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '付款管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(7)
		        	},scope : this
		        },
		        pressed: false	
		 	},{
		 		text: '物料申请管理',
		        enableToggle: true,
		        listeners : {
		        	'click' : function() {
		        		this.onBillDocClick(8)
		        	},scope : this
		        },
		        pressed: false	
		 	}]
		})
	},
	
	onBillDocClick : function(_billType) {
		var billGrid = new BillDocGrid({billType : _billType});
		
		var billDocWindow = new Ext.Window({
			constrainHeader : true,
			width : 600,
			height : 500,
			modal : true,
			title :  '单据列表',
			layout: 'fit',
			resizable :false,
			closeAction : 'hide',
			items: billGrid
		}).show();
		
		billGrid.getStore().load({
			params : {
				start : 0,
				limit : 15
			}	
		});
		
	}
})