Ext.namespace('Ext.ftl.quo.manager');
Ext.namespace('Ext.ftl.generalQuo.product');
Ext.namespace("Ext.ftl.protools");
QuotationManager = Ext.ftl.quo.manager;

Ext.ffc.AccountStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['value','status'],
						data : [
							[-1,'全部'],
							[0,'编制'],
							[1,'已确认']
						]
					});
		Ext.ffc.AccountStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '回款状态',
					valueField : 'value',
					hiddenName : 'status',
					width:100,
					mode : 'local',
					displayField : 'status',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
 * 状态下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
StatusCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var arr = [['全部',''],['编制', '0'],['待审批', '1'],['审批通过', '2'],
						['审批退回', '3'],['提交订货', '6']];
		if(this.quoType == 0 || this.quoType == 1 || this.quoType == 2){
			arr.push(['提交合同', '4']);
		}
		this.store = new Ext.data.SimpleStore({
						fields : ['status', 'value'],
						data : arr
					});
		StatusCombox.superclass.constructor.call(this, {
					fieldLabel : '状态',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'status',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

/**
 * 币别下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
CurrencyCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	rate : null,
	curRate : null,
	curid : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
			url : PATH + '/generalQuo/getCurrencyAction.do',
			fields : ['id', 'currencyName', 'rate', 'benchmark','text'],
			root : 'currency',
			listeners : {
				'load' : function(_store) {
					var a = null;
					if(_store.getCount() > 0) {
						_store.each(function (d) {
							if (d.data['benchmark'] == 1) {
								a = d;
								return false;
							}
						});
					}
					if(a){
						this.setFccValue(a.data['text']);
						if(this.curRate == null)
							this.curRate = a.data['rate'];
						this.curid = a.data['id'];
						if(this.ownerForm){
						    this.ownerForm.setValues('currencyId', a.data['id']); 	
						}
						//alert(this.curRate + "----------")
					}
				},scope : this
			}
		});
		
		CurrencyCombox.superclass.constructor.call(this, {
			fieldLabel : '币别',
			hiddenName : 'currencyName',
			mode : 'remote',
			displayField : 'text',
			valueField : 'text',
			//anchor: this['anchor'] == null ? '90%' : this['anchor'],
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			//value : '人民币',
			store : this.store,
			listeners : {
				'change' : function(_field, _newValue, oldValue) {
					//alert(oldValue + "++++++++");
					var oldRecord = this.store.getById(this.curid);
					var newRecord = this.findRecord(this.displayField,_newValue);
					var oldRate = this.curRate;
					var newRate = newRecord.data['rate'];
					//alert(oldRate  + " : " + newRate);
					this.rate = (oldRate/newRate);
					this.curRate = newRecord.data['rate'];
					this.curid = newRecord.data['id'];
					if(this.ownerForm){
						 this.ownerForm.setValues('currencyId', a.data['id']); 	
					}
				},scope : this
			}
		})
		this.addEvents('suc');
	},
	
	setFccValue:function (a) {
		//var a = this.value;
	    var c = null;
	    if (this.valueField) {
	        var b = this.findRecord(this.displayField, a);
	        if (b) {
	            c = b.data[this.valueField];
	        }
	    }
	    
	    if (c != null) {
	        this.hiddenField.value = c;
	        
	    }
	    this.value = a;
	    this.el.dom.value = a;
	}
})


orderCurrencyCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	rate : null,
	curRate : null,
	curid : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
			url : PATH + '/generalQuo/getCurrencyAction.do',
			fields : [{name: 'currencyId',mapping:'id'}, 
				'currencyName', 'rate', 'benchmark','text'],
			root : 'currency',
			autoLoad:true,
			listeners : {
				'load' : function(_store) {
					var a = null;
					if(_store.getCount() > 0) {
						_store.each(function (d) {
							if (d.data['benchmark'] == 1) {
								a = d;
								return false;
							}
						});
					}
					if(a){
					    this.setFccValue(a.data['text'],a.data['currencyName']);
						if(this.curRate == null)
							this.curRate = a.data['rate'];
							this.curid = a.data['currencyId'];
							if(this.ownerForm){
						    this.ownerForm.getForm().setValues({'currencyId': a.data['currencyId']}); 	
					    }
					}
				},scope : this
			}
		});
	orderCurrencyCombox.superclass.constructor.call(this, {
			fieldLabel : '币别',
			hiddenName : 'currencyName',
			mode : 'remote',
			displayField : 'text',
			valueField : 'text',
			//anchor: this['anchor'] == null ? '90%' : this['anchor'],
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			//value : '人民币',
			store : this.store,
			listeners : {
				'change' : function(_field, _newValue, oldValue) {
					//alert(oldValue + "++++++++");
					var oldRecord = this.store.getById(this.curid);
					var newRecord = this.findRecord(this.displayField,_newValue);
					var oldRate = this.curRate;
					var newRate = newRecord.data['rate'];
					//alert(oldRate  + " : " + newRate);
					this.rate = (oldRate/newRate);
					this.curRate = newRecord.data['rate'];
					this.curid = newRecord.data['currencyId'];
					if(this.ownerForm){
						 this.ownerForm.getForm().setValues({'currencyId': newRecord.data['currencyId']}); 	
					}
				},scope : this
			}
		})
		this.addEvents('suc');
	},
	setFccValue:function (a,b) {
	    if (b && this.hiddenField) {
	        this.hiddenField.value = b;
	    }
	    this.value = a;
		if(this.el && this.el.dom){
			this.el.dom.value = a;
		}
	}
})

/**
 * 税率下拉框
 * @class TaxrateCombox
 * @extends Ext.form.ComboBox
 */
TaxrateCombox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.JsonStore({
			url : PATH + '/baseInfo/taxRateManagerAction.do?method=getTaxRate',
			fields : ['id', {name: 'taxRate', type: 'float',mapping:"rate"}],
			root : 'taxRate',
			listeners : {
				'load' : function(_store) {
					if(_store.getCount() > 0) {
						this.setValue(_store.getAt(0).data['taxRate']);
					} else {
						this.setValue(0);
					}
				},scope : this
			}
		});
		TaxrateCombox.superclass.constructor.call(this, {
					fieldLabel : '税　　率',
					hiddenName : 'taxRate',
					mode : 'remote',
					displayField : 'taxRate',
					//readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
 * 卖方下拉框
 * @class SallerCombox
 * @extends Ext.form.ComboBox
 */
SallerCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
			url : PATH + '/generalQuo/getCompanyAction.do',
			fields : ['id', 'companyName'],
			root : 'saller',
			//autoLoad : true,
			listeners : {
				'load' : function(_store, _records) {
					if(_records.length > 0)
						this.setValue(_records[0].get('companyName'))
				},scope : this
			}
		});
		
		SallerCombox.superclass.constructor.call(this, {
			fieldLabel : '卖方名称',
			hiddenName : 'sellerName',
			valueField:'companyName',
			displayField : 'companyName',
			mode : 'remote',
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			//emptyText:'请选择卖方...',
			store : this.store
		})
	}
})

/**
 * 公司名称下拉框
 * @class SallerCombox
 * @extends Ext.form.ComboBox
 */
companyCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
			url : PATH + '/generalQuo/getCompanyAction.do',
			fields : ['id', 'companyName'],
			root : 'saller',
			autoLoad : true,
			listeners : {
				'load' : function(_store, _records) {
					if(_records.length > 0)
						this.setValue(_records[0].get('companyName'))
				},scope : this
			}
		});
		
		SallerCombox.superclass.constructor.call(this, {
			fieldLabel : '卖方名称',
			hiddenName : 'companyName',
			valueField:'companyName',
			displayField : 'companyName',
			mode : 'remote',
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			store : this.store
		})
	}
})


/**
付款条件
*/
Ext.zhj.PaymentConditionComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['paymentConditionId','paymentConditionMode'],
						data : [
							[0,'收到100%合同款即订货,货物与发票一同寄出。'],
							[1,'收到100%合同款即发货,发票与货物一同寄出。'],
							[2,'预付50%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							[3,'预付30%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							[4,'预付30%合同款即订货,发货前通知甲方付60%合同款即发货,货物验收合格后见证明付10%合同尾款即开发票。'],
							[5,'货交甲方，发票挂账120天内付清全款。'],
							[6,'货交甲方，发票挂账90天内付清全款。'],
							[7,'货交甲方，发票挂账30天内付清全款。'],
							[8,'货交甲方，发票挂账15天内付清全款。'],
							[9,'收到100%合同款即订货，不开票。'],
							[10,'收到100%合同款即发货，不开票。'],
							[11,'机床公司收到机床款后向我方付清刀具全款。'],
							[12,'货交甲方，发票挂账7天内付清全款。'],
							[13,'货交甲方，交货当天付清全款。']
						]
					});
		Ext.zhj.PaymentConditionComboBox.superclass.constructor.call(this, {
			fieldLabel : '付款条件',
					hiddenName : 'paymentCondition',
				    valueField : 'paymentConditionMode',
					mode : 'local',
					displayField : 'paymentConditionMode',
					//anchor:this['anchor'] == null ? '97%' : this['anchor'],
					//readOnly : true,
					frame : true,
					triggerAction : 'all',
					//emptyText:'请选择付款条件...',
					value : '收到100%的合同款即订货,货物与发票一同寄出。',
					store : this.store
		})
	}
})






/**
结算方式及期限
*/
Ext.ffc.ClosingAccountModeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['closingAccountMode'],
						data : [
							['收到100%合同款即订货,货物与发票一同寄出。'],
							['收到100%合同款即发货,发票与货物一同寄出。'],
							['预付50%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							['预付30%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							['预付30%合同款即订货,发货前通知甲方付60%合同款即发货,货物验收合格后见证明付10%合同尾款即开发票。'],
							['货交甲方，发票挂账120天内付清全款。'],
							['货交甲方，发票挂账90天内付清全款。'],
							['货交甲方，发票挂账30天内付清全款。'],
							['货交甲方，发票挂账15天内付清全款。'],
							['收到100%合同款即订货，不开票。'],
							['收到100%合同款即发货，不开票。'],
							['机床公司收到机床款后向我方付清刀具全款。'],
							['货交甲方，发票挂账7天内付清全款。'],
							['货交甲方，交货当天付清全款。']
						]
					});
		Ext.ffc.ClosingAccountModeComboBox.superclass.constructor.call(this, {
			fieldLabel : '结算方式及期限',
					hiddenName : 'closingAccountMode',
				    valueField : 'closingAccountMode',
					mode : 'local',
					displayField : 'closingAccountMode',
					editable : true,
					frame : true,
					triggerAction : 'all',
					emptyText:'请选择结算方式...',
					value : '',
					store : this.store
		})
	}
})



/**
订单结算方式及期限
*/
Ext.ffc.OrderClosingAccountModeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['closingAccountMode'],
						data : [
							['收到100%合同款即订货,货物与发票一同寄出。'],
							['收到100%合同款即发货,发票与货物一同寄出。'],
							['预付50%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							['预付30%合同款即订货,发货前通知甲方付清尾款即发货,发票随货物寄出。'],
							['预付30%合同款即订货,发货前通知甲方付60%合同款即发货,货物验收合格后见证明付10%合同尾款即开发票。'],
							['货交甲方，发票挂账120天内付清全款。'],
							['货交甲方，发票挂账90天内付清全款。'],
							['货交甲方，发票挂账30天内付清全款。'],
							['货交甲方，发票挂账15天内付清全款。'],
							['收到100%合同款即订货，不开票。'],
							['收到100%合同款即发货，不开票。'],
							['机床公司收到机床款后向我方付清刀具全款。'],
							['货交甲方，发票挂账7天内付清全款。'],
							['货交甲方，交货当天付清全款。']
						]
					});
		Ext.ffc.ClosingAccountModeComboBox.superclass.constructor.call(this, {
			fieldLabel : '结算方式及期限',
					hiddenName : 'closingAccountMode',
				    valueField : 'closingAccountMode',
					mode : 'local',
					displayField : 'closingAccountMode',
					
					frame : true,
					triggerAction : 'all',
					emptyText:'请选择结算方式...',
					value : '收到100%的合同款即订货,货物与发票一同寄出。',
					store : this.store
		})
	}
})

/**
运输方式及费用
*/
Ext.ffc.TrafficModeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['trafficMode'],
						data : [
						  [''],
							['邮寄至合同约定交提货地点，费用买方负担。'],
							['邮寄至合同约定交提货地点，费用卖方负担。'],
							['送货至合同约定交提货地点，费用卖方负担。'],
							['送货至合同约定交提货地点，费用买方负担。'],
							['买方自提，费用买方负担。']
						]
					});
		Ext.ffc.ClosingAccountModeComboBox.superclass.constructor.call(this, {
			fieldLabel : '运输方式及费用',
					hiddenName : 'trafficMode',
				    valueField : 'trafficMode',
					mode : 'local',
					displayField : 'trafficMode',
					frame : true,
					triggerAction : 'all',
					emptyText:'运输方式及费用...',
					value : '',
					store : this.store
		})
	}
})


/**
交货方式
*/
Ext.ffc.DeliveryTypeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['deliveryType'],
						data : [
						  [''],
							['邮寄至合同约定交提货地点，费用买方负担。'],
							['邮寄至合同约定交提货地点，费用卖方负担。'],
							['送货至合同约定交提货地点，费用卖方负担。'],
							['送货至合同约定交提货地点，费用买方负担。'],
							['买方自提，费用买方负担。']
						]
					});
		Ext.ffc.DeliveryTypeComboBox.superclass.constructor.call(this, {
			    fieldLabel : '交货方式',
					hiddenName : 'deliveryType',
				  valueField : 'deliveryType',
					mode : 'local',
					displayField : 'deliveryType',
					frame : true,
					triggerAction : 'all',
					emptyText:'交货方式...',
					value : '',
					store : this.store
		})
	}
})


/**
合同状态(-1作废，0编制，1待审批，2审批通过，3审批退回，4执行，5终结)
*/
Ext.ffc.ContractStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['status','contractStatusText'],
						data : [
							[-2,'全部'],
							[0,'编制'],
							[1,'待审批'],
							[2,'审批通过'],
							[3,'审批退回'],
							[4,'执行'],
							[5,'终结'],
							[-1,'作废']
						]
					});
		Ext.ffc.ContractStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '合同状态',
					valueField : 'status',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'contractStatusText',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store,
					originalValue : -2
		})
	}
})

/**
 * 订单紧急程度下拉框
 * @class Ext.ftl.UrgencyComboBox
 * @extends Ext.form.ComboBox
 */
Ext.ftl.UrgencyComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function() {
		this.store = new Ext.data.SimpleStore({
						fields : ['urgentLevel', 'value'],
						data : [['全部',''],['一般', '0'],['紧急', '1']]
					});
		Ext.ftl.UrgencyComboBox.superclass.constructor.call(this, {
			fieldLabel : '紧急程度',
					hiddenName : 'urgentLevel',
					mode : 'local',
					displayField : 'urgentLevel',
					valueField : 'value',
					anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

/**
 * 订单状态
 * @class Ext.ftl.StockOrderComboBox
 * @extends Ext.form.ComboBox
 */
Ext.ftl.StockOrderComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['status','value'],
						data : [['全部',''],['编制', '0'],['待审批', '1'],['审批通过', '2'],
						['审批退回', '3'], ['已下单', '4'], ['已做计划', '6'], ['到货完毕', '5'], ['已作废', '-1']]
					});
		Ext.ftl.StockOrderComboBox.superclass.constructor.call(this, {
			fieldLabel : '状态',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'status',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

/**
 * 合同紧急下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
Ext.ffc.UrgentLevelCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.SimpleStore({
						fields : ['urgentLevelText', 'urgentLevel'],
						data : [['一般','0'],['紧急', '1']]
					});
		Ext.ffc.UrgentLevelCombox.superclass.constructor.call(this, {
					fieldLabel : '紧急程度',
					hiddenName : 'urgentLevel',
					mode : 'local',
					displayField : 'urgentLevelText',
					valueField : 'urgentLevel',
					value:0,
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
交货单状态(0编制，1待审批，2审批通过，3审批退回)
*/
Ext.ffc.DeliveryStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['status','deliveryStatusText'],
						data : [
							[-1,'全部'],
							[0,'编制'],
							[1,'待审批'],
							[2,'审批通过'],
							[3,'审批退回']
						]
					});
		Ext.ffc.DeliveryStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '交货单状态',
					valueField : 'status',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'deliveryStatusText',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
出库单状态(0编制，1待审批，2审批通过，3审批退回)
*/
Ext.ffc.OutStockStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['status','outStockStatusText'],
						data : [
							[-1,'全部'],
							[0,'编制'],
							[1,'已确认']
						]
					});
		Ext.ffc.OutStockStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '出库状态',
					valueField : 'status',
					hiddenName : 'status',
					width:100,
					mode : 'local',
					displayField : 'outStockStatusText',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})


/**
出库单类型(0直接出库，1合同出库，2报价单出库，3材料出库)
*/
Ext.ffc.OutStockTypesComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['outStockType','outStockTypeText'],
						data : [
							[0,'直接出库'],
							[1,'合同出库'],
							[2,'报价单出库'],
							[3,'材料出库']
						]
					});
		Ext.ffc.OutStockTypesComboBox.superclass.constructor.call(this, {
					fieldLabel : '出库性质',
					valueField : 'outStockType',
					hiddenName : 'outStockType',
					mode : 'local',
					displayField : 'outStockTypeText',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
储备计划状态(0编制，1已确认,2已采购)
*/
Ext.ffc.ReservePlanStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['value','status'],
						data : [
							[-1,'全部'],
							[0,'编制'],
							[2,'已确认'],
							[4,'已做订单'],
							[5,'全部提取']
						]
					});
		Ext.ffc.ReservePlanStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '计划状态',
					valueField : 'value',
					hiddenName : 'value',
					mode : 'local',
					displayField : 'status',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})
Ext.ffc.YAER = new Date().format('Y');
Ext.ftl.HistoryPriceYearCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
			url : PATH + '/proTools/listAction.do?method=getHistoryYear',
			fields : ['salePriceDate'],
			root : 'historyYear'
		});
		
		Ext.ftl.HistoryPriceYearCombox.superclass.constructor.call(this, {
			//fieldLabel : '卖方',
			hiddenName : 'salePriceDate',
			valueField:'salePriceDate',
			displayField : 'salePriceDate',
			mode : 'local',
			anchor : '90%',
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			store : this.store//,
			//value : Ext.ffc.YAER
		})
	}
})

/**
 * 供应商品牌
 * @class Ext.ftl.SuppliersBrand
 * @extends Ext.form.ComboBox
 */
Ext.ftl.SuppliersBrand =  Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function() {
		this.store = new Ext.data.JsonStore({
			url : PATH
					+ '/baseInfo/supplierBrankListAction.do?',
			fields : ['id', 'tSuppliersId', 'brand'],
			root : 'supplierBrankList'
		});
		Ext.ftl.SuppliersBrand.superclass.constructor.call(this, {
			fieldLabel : '品牌',
			hiddenName : 'brand',
			mode : 'remote',
			displayField : 'brand',
			valueField : 'brand',
			width : this['width'] == null ? 120 : this['width'],
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			emptyText : '请选择...',
			store : this.store
		});
	}
});
//--------------------单据管理-------------
//报价单
Ext.ftl.QuotationStatusCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.SimpleStore({
						fields : ['status', 'value'],
						data : [['编制', '0'],['待审批', '1'],['审批通过', '2'],
						['审批退回', '3'], ['提交合同', '4'], ['已经生成合同', '5'],
						['提交订货', '6'], ['已转正式报价', '7']]
					});
		Ext.ftl.QuotationStatusCombox.superclass.constructor.call(this, {
					fieldLabel : '状态',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'status',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

//合同状态
Ext.ftl.ContractStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['value','status'],
						data : [
							[0,'编制'],
							[1,'待审批'],
							[2,'审批通过'],
							[3,'审批退回'],
							[4,'执行'],
							[5,'终结'],
							[-1,'作废']
						]
					});
		Ext.ftl.ContractStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '合同状态',
					valueField : 'value',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'status',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store,
					originalValue : -2
		})
	}
})

//订单状态
Ext.ftl.BillStockOrderComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function() {
		this.store = new Ext.data.SimpleStore({
						fields : ['status','value'],
						data : [['编制', '0'],['待审批', '1'],['审批通过', '2'],
						['审批退回', '3'], ['已下单', '4'], ['已做计划', '6'], ['到货完毕', '5']]
					});
		Ext.ftl.StockOrderComboBox.superclass.constructor.call(this, {
			fieldLabel : '状态',
					hiddenName : 'status',
					mode : 'local',
					displayField : 'status',
					valueField : 'value',
					anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

//入库单
Ext.ftl.BillArrivalStatusCombo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.SimpleStore({
						fields : ['status', 'value'],
						data : [['入库 未确认', '0'],['入库 已确认', '1'],['入库 已作废', '-1']]
					});
		Ext.ftl.BillArrivalStatusCombo.superclass.constructor.call(this, {
			fieldLabel : '状态',
			hiddenName : 'status',
			mode : 'local',
			displayField : 'status',
			valueField : 'value',
			//anchor:'90%',
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			value : '全部',
			store : this.store
		})
	}
})

//出库单
Ext.ftl.OutStockStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['value','status'],
						data : [
							[0,'编制'],
							[1,'已确认']
						]
					});
		Ext.ftl.OutStockStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '出库状态',
					valueField : 'value',
					hiddenName : 'status',
					width:100,
					mode : 'local',
					displayField : 'status',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

//交货单
Ext.ftl.DeliveryStatusComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['value','status'],
						data : [
							[0,'编制'],
							[1,'待审批'],
							[2,'审批通过'],
							[3,'审批退回']
						]
					});
		Ext.ftl.DeliveryStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '交货单状态',
					valueField : 'value',
					hiddenName : 'status',
					width:100,
					mode : 'local',
					displayField : 'status',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

/**
 * 产品对应销售历史
 * @class Ext.ftl.CusSalesProductGrid
 * @extends Ext.grid.GridPanel
 */	    
Ext.ftl.CusSalesProductGrid = Ext.extend(Ext.grid.GridPanel, {
	productId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
			url : PATH
					+ '/baseInfo/cusSalesProductHistoryListAction.do?productId='
					+ this.productId,
			root : 'cusSalesProductHistoryList',
			totalProperty : 'totalProperty',
			autoLoad : false,
			fields : ['productName', 'productCode', 'brandCode',
					'historyPrice', 'rebate', 'netPrice', 'userName',
					'customerName', 'editDate','quotationCode', 'id']
		});
		if(!this.noPageView){
			this.bbar = new Ext.PagingToolbar({
				pageSize : 10,
				emptyMsg : "没有记录",
				displayInfo : true,
				displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
		}
		Ext.ftl.CusSalesProductGrid.superclass.constructor.call(this, {
			height : 50,
			enableHdMenu : false,
			stripeRows : true,
			title : '产品历史价格',
			collapsible : true,
			split : true,
			ds : ds,
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '该产品无销售记录！'
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
					
				},{
					header : '货品名称',
					dataIndex : 'productName'
				}, {
					header : '货品编号',
					dataIndex : 'productCode'
				}, {
					header : '牌号',
					dataIndex : 'brandCode'
				}, {
					header : '客户名称',
					dataIndex : 'customerName'
				}, {
					header : '报价单编号',
					dataIndex : 'quotationCode',
					width : 150
				}, {
					header : '编制时间',
					dataIndex : 'editDate',
					width : 180
				}, {
					header : 'ID',
					hidden : true,
					dataIndex : 'id'
			}])
			
		});

	}
});
//-------------------------------------为报价单添加产品列表window-----------------------------------------

Quoproduct = Ext.ftl.generalQuo.product;

/**
 * 搜索Record
 */   
Quoproduct.SearchRecord = Ext.data.Record.create([
		    {name: 'productCode'},
		    {name: 'brandCode'},
		    {name: 'salePriceDate'},
		    {name: 'productName'}
		]);
/**
 * 产品树搜索栏
 * @class Ext.ftl.protools.SearchBar
 * @extends Ext.Toolbar
 */
Quoproduct.SearchBar = Ext.extend(Ext.Toolbar, {
	targetTree : null,
	paramRecord : null,
	yearCombo : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.paramRecord = new Quoproduct.SearchRecord();
		this.yearCombo = new Ext.ftl.HistoryPriceYearCombox();
		this.proBrand = new Ext.zhj.ProductBrankCombox();
		this.yearCombo.on({
			'specialkey' : function(field, e) {
        		if(e.getKey() == e.ENTER) {
					for(var i = 0 ,len = this.items.length;i < len ; i++){
						if(this.items.get(i).text == '搜索'){
							field.fireEvent('change', field);
							this.items.get(i).fireEvent('click');
							break;
						}
					}
	            }
        	},
			'select' : function(_combo) {
				this.paramRecord.salePriceDate = _combo.getValue();
			},scope : this
		}),
		this.proBrand.on({
			'specialkey' : function(field, e) {
        		if(e.getKey() == e.ENTER) {
					for(var i = 0 ,len = this.items.length;i < len ; i++){
						if(this.items.get(i).text == '搜索'){
							field.fireEvent('change', field);
							this.items.get(i).fireEvent('click');
							break;
						}
					}
	            }
        	},
			'select' : function(_combo) {
				this.paramRecord.productBrand = _combo.getValue();
				this.yearCombo.reset();
				this.yearCombo.store.reload({params:{productBrand:_combo.getValue()}});
			},scope : this
		});
		Quoproduct.SearchBar.superclass.constructor.call(this, {
			renderTo:this.targetTree.tbar,
            items:[new Ext.form.Label({
            	html : "牌号:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'brandCode',
		      	id : 'brandCode',
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
						    for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
	            					this.items.get(i).fireEvent('click');
									break;
								}
						    }
	            		}
	            	},
		      		'change' : function(_field) {
		      			this.paramRecord.brandCode = _field.getValue().trim();
		      		},scope : this
		      	}
            
            }),'-',new Ext.form.Label({
            	html : "货品编号:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'productCode',
		      	id : 'productCode',
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
						    for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
	            					this.items.get(i).fireEvent('click');
									break;
								}
						    }
	            		}
	            	},
		      		'change' : function(_field) {
		      			this.paramRecord.productCode = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-', new Ext.form.Label({
            	html : "名称:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'productName',
		      	id : 'productName',
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
						    for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
	            					this.items.get(i).fireEvent('click');
									break;
								}
						    }
	            		}
	            	},
		      		'change' : function(_field) {
		      			this.paramRecord.productName = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-',new Ext.form.Label({
            	html : "品牌:&nbsp;"
            
            }),this.proBrand ,
			'-', new Ext.form.Label({
            	html : "价格执行期:&nbsp;"
            
            }), this.yearCombo,'-',{  
					text:'搜索'  
					,iconCls:'icon-search',
					listeners : {
						'click' : function(){
							var _searchStr = Ext.util.JSON.encode(this.paramRecord);
							this.fireEvent('onsearch',this, _searchStr);
						},scope:this
					}
					
				},'-',{  
					text:'重置'  
					,iconCls:'icon-reset',
					listeners : {
						'click' : function(){
							this.reset();
						},scope:this
					}
					
				}  
            ]
		})
		this.addEvents('onsearch');
	},
	
	reset : function() {
		this.yearCombo.reset();
		this.items.itemAt(1).reset();
		this.items.itemAt(4).reset();
		this.items.itemAt(7).reset();
		this.paramRecord = new Quoproduct.SearchRecord();
	}
})

Quoproduct.ProductTree = Ext.extend(Ext.tree.ColumnTree, {
	store : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.Store({
	       proxy: new Ext.data.HttpProxy({url:PATH + '/proTools/listAction.do'}),//调用的动作 
		   reader: new Ext.data.JsonReader(
				{
				   root: 'items',  //从struts2里面传递过来的参数 
				   totalProperty :'totalCount'
				}, 
				 [ //JSON数据的映射
					{name: 'id',mapping:'id',type:'string'}
				 ]
		  )
		});
		Quoproduct.ProductTree.superclass.constructor.call(this, {
			width:document.body.clientWidth,
			height: window.screen.availHeight-300,
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			tbar : [],
			listeners : {
				'render' : function() {
					var tl = this.store;
					var _searchStr = null;
					tl.on('beforeload', function(tl, node) {
						this.baseParams.searchStr = _searchStr;
					})

					var searchBar = new Quoproduct.SearchBar({
						targetTree : this
					})
					searchBar.on('onsearch', function(_searchBar, _str) {
						_searchStr = _str;
						tl.reload({params : {start : 0, limit : typeof(PAGESIZE) != 'undefined' ? PAGESIZE : 20}});
					})
					
					this.store.load({params:{start:0,limit:20,salePriceDate:new Date().getYear()}});
				},
				
				'load' : function(node) {
					this.fireEvent('click', node);
				},
				
				'click' : function(node, e) {
					if(node.isLeaf())
						return;
					if(!node.isExpanded()) { 
						node.expand();
					} else {
						node.collapse();
					}
					if(node.parentNode.id != 'root'  || node.hasChildNodes()) 
						return;
					Ext.Ajax.request({
						url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
						params: { nodeId: node.id },
						success : function(response) {
							var responseArray = Ext.util.JSON.decode(response.responseText); 
							var nodes = responseArray.items;
							if(nodes.length == 0) return;
							var arr = [];
							for(var i = 0 ;i < nodes.length;i++){
								arr.push(nodes[i]);					
							}
							if(node.hasChildNodes()) 
								return;
							for(var i = 0; i < arr.length;i++){
								node.appendChild(arr[i]); 
							}
							
						},scope : this
					});
				}
			},
			enableDD:false,
	        title: '产品信息',
	        columns:[
	        	{header:'牌号',width:300,dataIndex:'brandCode'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
		        {header:'名称',width:100,dataIndex:'productName'},
		        {header:'计量单位',width:100,dataIndex:'productUnit'},
		        {header:'组别',width:100,dataIndex:'productSortCode'},
		        {header:'单价',width:50,dataIndex:'salePrice'},
		        {header:'折扣',width:100,dataIndex:'rebate'},
		        {header:'品牌',width:100,dataIndex:'productBrand'},
		        {header:'来源',width:100,dataIndex:'productSource'},
		        {header:'备注',width:180,dataIndex:'memo'},
				{header:'销售记录',width:80,dataIndex:'',
					renderer : function(colValue, node, data) {
						if(data.parentId != 'root')
							return;

						var id = data.toolsId;
						//var cust =  this.customerRecord.data;
						return '<a href=\"#\"  onclick="Ext.ffc.viewSaleBill({toolsId:\'' + id + '\',brandCode:\'' + data.brandCode + '\'})">查看</a>';
					},scope:this
				},
		        {header:'附件',width:60,hidden : false, dataIndex:'slaveFile', renderer : function(colValue, node, data) {
	        		if(data.parentId != 'root')
	        			return;
	        		if(colValue > 0) {
	        			
	        			var id = data.toolsId
	        			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + id + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
						return str;
	        		}
	        	}},
		        {header:'父ID',width:0,hidden : true,dataIndex:'parentId'},
		        {header:'toolsId',width:0,hidden : true,dataIndex:'toolsId'}
	        ],
	        
	        bbar : new Ext.ffc.TreePagingToolbar({
	            pageSize: typeof(PAGESIZE) == 'undefined' ?  20 : typeof(PAGESIZE) != 'undefined' ? PAGESIZE : 20,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	            emptyMsg: "没有记录"
	        }),
	        
	        loader: new Ext.tree.TreeLoader({
	            //dataUrl:PATH + '/proTools/listAction.do?pageObjectName=pageObject&pageCount=' + Quoproduct.PageObj.pageCount,
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeLoader({
	        	id:"root",
	            text:'Tasks'
	        })
		})
	},
	
	getSelected : function() {
		return this.getSelectionModel().getSelectedNode();
	}
})


Quoproduct.HistoryProductTree = Ext.extend(Ext.tree.ColumnTree, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Quoproduct.HistoryProductTree.superclass.constructor.call(this, {
			height: 400,
			bodyStyle:'width:100%',
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        title: '历史报价信息',
	        columns:[
	        	{header:'牌号', width:300,dataIndex:'brandCode'},
	        	{header:'历史面价',width:100,dataIndex:'historyPrice'},
	        	{header:'折扣',width:50,dataIndex:'rebate'},
	        	{header:'净价',width:50,dataIndex:'netPrice'},
				{header:'含税单价',width:100,dataIndex:'taxNetPrice'},
	        	{header:'币别',width:100,dataIndex:'currencyName'},
				{header:'合同编号', width:300,dataIndex:'contractCode'},
	        	{header:'报价单编号', width:300,dataIndex:'quotationCode'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
	        	{header:'编制时间',width:100,dataIndex:'editDateString'},
	        	{header:'编制人',width:100,dataIndex:'userName'},
	        	{header : '品牌', width : 0, dataIndex : 'productBrand', hidden : true},
		        {header : '名称', width : 0, dataIndex : 'productName', hidden : true},
		        {header : '计量单位', width : 0, dataIndex : 'productUnit', hidden : true},
		        {header:'toolsId',width:0,hidden : true,dataIndex:'productToolInforId'},
		        {header:'rate',width:0,hidden : true,dataIndex:'rate'}
		    ],
	
	        loader: new Ext.tree.TreeLoader({
	            dataUrl: PATH + '/generalQuo/getHistoryPriAction.do',
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		})
	},
	getSelected : function() {
		return this.getSelectionModel().getSelectedNode();
	}
})

Quoproduct.NorthPanel = Ext.extend(Ext.Panel, {
	productTree : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.productTree = new Quoproduct.ProductTree({
			customerRecord : this.customerRecord
		});
		Quoproduct.NorthPanel.superclass.constructor.call(this, {
			region: 'north',
            iconCls:'icon-grid',
            //title: '工具信息',
            //contentEl: 'south',
            split: true,
            width: 200,
            height : 400,
            minSize: 175,
            layout : 'fit',
            maxSize: 400,
            collapsible: true,
            margins: '5 5 5 5',
            items : [this.productTree]
		})
	}
})

Quoproduct.CenterPanel = Ext.extend(Ext.Panel, {
	historyTree : null,
	
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.historyTree = new Quoproduct.HistoryProductTree();
		Quoproduct.CenterPanel.superclass.constructor.call(this, {
			region: 'center',
            split: true,
            height: 100,
            minSize: 100,
            layout : 'fit',
            maxSize: 200,
            collapsible: false,
            margins: '-5 5 5 5',
            items : [this.historyTree]
		})
	}
})

Quoproduct.ProductListWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new Quoproduct.NorthPanel({
			customerRecord : this.customerRecord
		});
		this.centerPanel = new Quoproduct.CenterPanel();
		Quoproduct.ProductListWindow.superclass.constructor.call(this, {
			title:"添加产品",  
			width:1050,  
			height:600,  
			plain:true,
			closable : true,
			modal : true,
			constrainHeader : true,
			closeAction:'hide',
			layout:"border",  
			buttons : [{
				text : "确定",
				listeners : {
					'click' : function() {
						this.onSubmitClick();
					},scope : this
				},
				scope : this
			},{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			listeners : {
				'render' : function() {
					var proTree = this.northPanel.productTree;
					var historyTree = this.centerPanel.historyTree;
					
					proTree.on('dblclick', function() {
						this.buttons[0].fireEvent('click');
					},this)
					
					historyTree.on('dblclick', function() {
						this.buttons[0].fireEvent('click');
					},this)
				}
			},
		items : [this.northPanel, this.centerPanel]
		})
		this.addEvents('onclick')
	},
	
	onSubmitClick : function() {
		var projectNumber = null; 
		var selectedNode = null;
		var proTree = this.northPanel.productTree;
		var historTree = this.centerPanel.historyTree;
		var isHistory = false;
		
		if(proTree.getSelected()) {
			selectedNode = proTree.getSelected();
		} else if(historTree.getSelected()) {
			isHistory = true;
			selectedNode = historTree.getSelected();
		}
		
		if(!selectedNode) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择一条记录进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(selectedNode.parentNode.id != 'root') {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择根结点进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(!selectedNode.isLeaf() && !isHistory) {
			Ext.Ajax.request({
				url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
				params: { nodeId: selectedNode.id },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
					var nodes = responseArray.items;
					Ext.apply(selectedNode.attributes, {'children' : nodes})
					this.addNodeToTree(selectedNode);
				},scope : this
			});
		} else {
			this.addNodeToTree(selectedNode, isHistory);
		}
	},
	
	addNodeToTree : function(selectedNode, isHistory) {
		var newNode = Ext.tree.toNewTreeNode(selectedNode.attributes,{'uiProvider':'col','priceChange':''},false,['salePriceDate','stockPriceDate','editDate','editDateString','productToolInforId']);
		if(isHistory) {
			_price = selectedNode.attributes.historyPrice;
			Ext.apply(newNode, {'priceChange' : 4, 'toolsId' : selectedNode.attributes.productToolInforId})
		} else {
			_price = selectedNode.attributes.salePrice;
			Ext.apply(newNode, {'priceChange' : 0})
		}
		Ext.apply(newNode, {'price' : _price, 'serialNumber' : 10000})
		newNode.amount=1;
		newNode.memo="";
		this.fireEvent('onclick',newNode);
	}
	
})

/**
 * 直接出库调用，产品列表窗口不包含历史价格
 * @class Quoproduct.ProductWindow
 * @extends Ext.Window
 */
Quoproduct.ProductWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.centerPanel = new Quoproduct.ProductTree({
			region : 'center'
		});
		Quoproduct.ProductWindow.superclass.constructor.call(this, {
			title:"添加产品",  
			width:1050,  
			height:500,  
			plain:true,
			closable : true,
			modal : true,
			constrainHeader : true,
			closeAction:'hide',
			layout:"border",  
			buttons : [{
				text : "确定",
				listeners : {
					'click' : function() {
						this.onSubmitClick();
					},scope : this
				},
				scope : this
			},{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			listeners : {
				'render' : function() {
					this.centerPanel.on('dblclick', function() {
						this.buttons[0].fireEvent('click');
					},this)
				}
			},
		items : [this.centerPanel]
		})
		this.addEvents('onclick')
	},
	
	onSubmitClick : function() {
		var projectNumber = null; 
		var selectedNode = null;
		var proTree = this.centerPanel;
		
		if(proTree.getSelected()) {
			selectedNode = proTree.getSelected();
		}
		
		if(!selectedNode) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择一条记录进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(selectedNode.parentNode.id != 'root') {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择根结点进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(!selectedNode.isLeaf()) {
			Ext.Ajax.request({
				url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
				params: { nodeId: selectedNode.id },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
					var nodes = responseArray.items;
					Ext.apply(selectedNode.attributes, {'children' : nodes})
					this.addNodeToTree(selectedNode);
				},scope : this
			});
		} else {
			this.addNodeToTree(selectedNode);
		}
	},
	
	addNodeToTree : function(selectedNode, isHistory) {
		var newNode = Ext.tree.toNewTreeNode(selectedNode.attributes,{},false,
		['salePriceDate','stockPriceDate','editDate','editDateString',
		'productToolInforId','priceChange','uiProvider','iconCls',
		'currencyName','rebate','salePrice','slaveFile','stockPrice',
		'brandCodeHistory','createDateStr','parentId','amount','productSortCode',
		'productSortId','productSource']);
		newNode.memo="";
		newNode.parentToolsId=selectedNode.attributes.parentId;
		if(selectedNode.attributes.leaf) {
			newNode.leaf = 1;
		} else {
			newNode.leaf = 0;
		}
		
		this.fireEvent('onclick',newNode);
	}
	
})


//-------------------------------------报价单表格开始---------------------------------
QuotationManager.ProToolsListTree = Ext.extend(Ext.tree.ColumnTree, {
	store : null,
	nodeId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		QuotationManager.ProToolsListTree.superclass.constructor.call(this, {
			//el:'tree-ct',
	        width:647,
			height: 400,
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			listeners : {
				'render' : function() {
					this.getLoader().on('load', function(_load, _node, _response) {
						var _rootNode = this.getRootNode();
						//alert(_response.responseText);
						_rootNode.appendChild(Ext.decode(_response.responseText));
						this.fireEvent('nodeadd', _rootNode)
						//_rootNode.expandChildNodes(true);
						//_node.expand();
					},this)
				},scope : this
			},
			enableDD:false,
	        title: '产品信息列表',
	        columns:[
	        	{header : '图标',width : 100,disEnableEdit : true},
	        	{header:'牌号',width:200,dataIndex:'brandCode'},
	        	{header:'名称',width:100,dataIndex:'productName'},
	        	{header:'计量单位',width:100,dataIndex:'productUnit'},
	        	{header : "数量", width:40,dataIndex : 'amount'},
	        	{header:'组别',width:100,dataIndex:'productSortCode'},
	        	{header:'品牌',width:100,dataIndex:'productBrand'},
	        	{header:'来源',width:100,dataIndex:'productSource'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
	        	{header : "历史牌号", width:200,dataIndex : 'brandCodeHistory'},
				{header:'附件',width:60,hidden : false, dataIndex:'slaveFile', renderer : function(colValue, node, data) {
				    var toolsId = data.id;
	        		if(data.parentId != 'root'){
					    toolsId = data.productCode.substr(data.productCode.indexOf('-') + 1);//！此处如果productCode的编码形式发生改变，会有问题
					}
	        		if(colValue > 0) {
						return "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + toolsId + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
	        		}
					return '';
	        	}},
				{header:'创建日期',width:100,dataIndex:'createDateStr'},
	        	{header:'ui',width:0,dataIndex:'uiProvider', hidden : true},
	        	{header:'备注',width:180,dataIndex:'memo'}
	        ],
	        
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl: PATH + '/proTools/listAction.do?method=getToolsById',
	            autoLoad : false,
	            baseParams : {'toolsId' : this.nodeId},
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		});
		
		this.addEvents("nodeadd");
	}
});

QuotationManager.ProToolsListWindow = Ext.extend(Ext.Window, {
	proToolsListTree : null,
	nodeId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.proToolsListTree = new QuotationManager.ProToolsListTree({'nodeId' : this.nodeId});
		this.proToolsListTree.on({
			//当前节点添加后再执行展开方法。
			'nodeadd' : function(_rootNode) {
				_rootNode.firstChild.expand();
			}
		})
		QuotationManager.ProToolsListWindow.superclass.constructor.call(this, {
			title: '产品信息',  
			width:800,  
			height:300,  
			plain:true,
			modal : true,
			closable : true,
			constrainHeader : true,
			layout : 'fit',
			//maximizable : true,
			closeAction:'hide',
			items : this.proToolsListTree
		})
	}
})

QuotationManager.productsGridColumn = Ext.extend(Ext.grid.ColumnModel, {
	readOnly : null,
	sm : null,
	parentCt : null,
	numbField : null,
	amountField : null,
	rebateField : null,
	netPriceField : null,
	isProject : null,//是否项目报价
	singleSetAssemblField : null,
	store : null,
	/**
	 * 单套刀具采购数量编辑框
	 * @type 
	 */
	singleSetStockField : null,
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		var _hidden = true;
		//var _noYuDingHidden = true;//不是预订为true
		if(this.isDetail && (this.quoType == 3 || this.quoType == 4)) 
			_hidden = false;
		//if(this.isDetail && this.quoType == 3) 
		//	_noYuDingHidden = false;
		var zhuanhetongHidden = !this.isDetail && true;
		//项目编号编辑框
		this.numbField = new Ext.form.NumberField({
		   gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           maxValue: 100000,
           enableKeyEvent : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		}
           }
  		});
  		
  		//数量编辑框
  		this.amountField = new Ext.form.NumberField({
  		   gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           maxValue: 100000,
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},
           		'keydown': function(f, e){
				    if(e.ctrlKey && e.getKey() == e.V){
				   	   var cm = this;
					   Ext.ffc.PasteEdition(function(data){
					   		cm.copy(data, 'amount');
					   });
				    }
				},scope : this
           }
  		});
  		
  		//折扣编辑框
  		this.rebateField = new Ext.form.NumberField({
  		   gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           minValue: 0,
		   maxValue:100,
		   decimalPrecision :0,
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},
           		'keydown': function(f, e){
				    if(e.ctrlKey && e.getKey() == e.V){
				   	   var cm = this;
					   Ext.ffc.PasteEdition(function(data){
					   		cm.copy(data, 'rebate');
					   });
				    }
				},scope : this
           }
  		});
  		
  		//净价编辑框
  		this.netPriceField = new Ext.form.NumberField({
  			gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},
           		'keydown': function(f, e){
				    if(e.ctrlKey && e.getKey() == e.V){
				   	   var cm = this;
					   Ext.ffc.PasteEdition(function(data){
					   		cm.copy(data, 'netPrice');
					   });
				    }
				},scope : this
           }
  		});
  		
  		//净价编辑框
  		this.taxNetPriceField = new Ext.form.NumberField({
  			gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},scope : this
           }
  		});
  		
  		//单套刀具装配数量编辑框
  		this.singleSetAssemblField = new Ext.form.NumberField({
  			gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},
           		'keydown': function(f, e){
				    if(e.ctrlKey && e.getKey() == e.V){
				   	   var cm = this;
					   Ext.ffc.PasteEdition(function(data){
					   		cm.copy(data, 'singleSetAssemblyAmount');
					   });
				    }
				},scope : this
           }
  		});
  		
  		//单套刀具采购数量编辑框
  		this.singleSetStockField = new Ext.form.NumberField({
  			gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,//不允许负数
           enableKeyEvents : true,
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		},
           		'keydown': function(f, e){
				    if(e.ctrlKey && e.getKey() == e.V){
				   	   var cm = this;
					   Ext.ffc.PasteEdition(function(data){
					   		cm.copy(data, 'singleSetStockAmount');
					   });
				    }
				},scope : this
           }
  		});
  		
		QuotationManager.productsGridColumn.superclass.constructor.call(this, [this.sm,
			{header : '图标',width : 40, dataIndex : 'leaf', 
				renderer : function(colValue, node, data) {
					if(!colValue) {
						return '<img src="' + PATH + '/extjs/resources/images/default/tree/folder.gif">';
					} else {
						return '<img src="' + PATH + '/extjs/resources/images/default/tree/leaf.gif">';
					}
				}},
        	{header : '项目编号',width : 60,dataIndex : 'projectCode',editor : this.numbField, sortable: false}, 
        	/*new Ext.grid.RowNumberer({
				header : '序号',
				dataIndex : 'serialNumber',
				sortable : true,
				width : 35,
				renderer:function (b, c, a, d) {
					//a Record d: 行号
					//alert(d);
					//Ext.ffc.util.debug(a.data);
					//a.set('serialNumber', '');
					//a.set('serialNumber', d+1);
				    if (this.rowspan) {
				        c.cellAttr = "rowspan=\"" + this.rowspan + "\"";
				    }
				    return d + 1;
				}
			}),*/
        	{header : '序号',width : 40,dataIndex : 'serialNumber', hidden : false},
        	{header:'牌号',width:180,dataIndex:'brandCode'},
        	{header:'名称',width:100,dataIndex:'productName'},
        	{header:'货品编号',width:100,dataIndex:'productCode'},
        	{header : '规格描述',width : 100,dataIndex : 'toolDescription', hidden : this.isProject,
        		editor : new Ext.form.TextField({
	        		gridObj : this.parentCt,
	    			listeners : {
		           		'specialkey' : Ext.ftl.gridEditorkeyMove
		            }
	        	})
        	},
        	{header : '单套刀具装配数量',width : 105,dataIndex : 'singleSetAssemblyAmount',
				hidden : this.isProject,
				editor :　this.singleSetAssemblField
			},
			{header : '单套刀具采购数量',width : 105,dataIndex : 'singleSetStockAmount',
				hidden : this.isProject,
				editor : this.singleSetStockField
			},
        	{header: this.isProject ? '数量' : '数量(n套采购数量)',width: this.isProject ? 50 : 107,dataIndex:'amount',
        		editor : this.isProject ? this.amountField : null
        	},
        	{header:'计量单位',width:80,dataIndex:'productUnit'},
        	{header:'单价',width:80,name : 'price',dataIndex: 'price',
        		renderer : function(colValue, metadata, record){
	        		return parseFloat(colValue).toFixed(2);
        	}},
        	{header:'折扣(%)',width:60,dataIndex:'rebate', editor : this.rebateField,
        		renderer : function(colValue, metadata, record){
	        		this.rendRebateColor(colValue, metadata, record);
	        		return colValue;
        	},scope : this},
        	{header:'净价',width:80,dataIndex:'netPrice', editor : this.netPriceField,
        		renderer : function(colValue, metadata, record){
	        		this.rendColor(colValue, metadata, record);
	        		return parseFloat(colValue).toFixed(2);
        	},scope : this},
        	{header:'金额',width:80,dataIndex:'money', renderer : function(colValue, metadata, record){
        		this.rendColor(colValue, metadata, record);
        		return parseFloat(colValue).toFixed(2);
        	},scope : this},
        	{header:'含税净价',width:80,dataIndex:'taxNetPrice', 
        		editor : this.isEditor ? this.taxNetPriceField : null,
        		renderer : function(colValue, metadata, record){
	        		this.rendTaxNetPriceColor(colValue, metadata, record);
	        		return parseFloat(colValue).toFixed(2);
        	},scope : this},
        	{header:'含税金额',width:80,dataIndex:'taxMoney', renderer : function(colValue, metadata, record){
        		this.rendColor(colValue, metadata, record);
        		return parseFloat(colValue).toFixed(2);
        	},scope : this},
        	{header : '工具代码',width : 80,dataIndex : 'toolCode',hidden : this.isProject,
        		editor : new Ext.form.TextField({
	        		gridObj : this.parentCt,
	    			listeners : {
		           		'specialkey' : Ext.ftl.gridEditorkeyMove
		            }
	        	})
        	},
        	
        	{header: '订单数量',width: 80,dataIndex:'orderAmount',
        	  	hidden : _hidden
        	},
        	{header: '已到/已提数量',width: 90,dataIndex:'arrivalAmount',
        		hidden : _hidden
        	},
        	{header: '已交数量',width: 80,dataIndex:'deliveryAmount',
        		hidden : _hidden
        	},
        	{header: '已转数量',width: 80,dataIndex:'contractAmount',hidden:zhuanhetongHidden,
				renderer : function(colValue, metadata, record){
				    
	        		return '<span style="color:#990000;font-weight:bold" id="' + record.id + 'yzsl" ondblclick="updateYuDing2ConWindow(this)" onmousemove="viewContractInfor(this)">' + colValue + '</span>';
        	     }
        	},
        	{header:'交货期限',width:100,dataIndex:'deliveryDate',
        		renderer : function(colValue, metadata, record){
        			if(Ext.isEmpty(colValue))
        				return '';
        			if(typeof(colValue) == 'string') 
						return colValue;
					else {
						var value =  colValue.format('Y-m-d');
						record.set('deliveryDate', value);
						return value;
					}
        		
        		}, 
	        	editor : new Ext.form.DateField({
	        	   gridObj : this.parentCt,
	        	   format:'Y-m-d',
		           listeners : {
		           		'specialkey' : Ext.ftl.gridEditorkeyMove
		           }
	        	})
	        },
        	{header:'客户确认方案图',width:100,dataIndex:'slaveFile', renderer : function(colValue, node, data) {
        		if(colValue > 0) {
        			var id = data.data.toolsId
        			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + id + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
					return str;
        		}
        	}},
        	{header:'价格变动',width:0,dataIndex:'priceChange', hidden : true},
        	{header:'品牌', width:80,dataIndex:'productBrand'},
        	{header:'备注1', width:80,dataIndex:'memo',
        		editor : new Ext.form.TextField({
        			gridObj : this.parentCt,enableKeyEvents : true,
        			listeners : {
		           		'specialkey' : Ext.ftl.gridEditorkeyMove,
		           		'keydown': function(f, e){
						    if(e.ctrlKey && e.getKey() == e.V){
						   	   var cm = this;
							   Ext.ffc.PasteEdition(function(data){
							   		cm.copy4Memo(data, 'memo');
							   });
						    }
						},scope : this
		            }
        		})},
        	{header:'备注2',width:80,dataIndex:'workshop',editor : new Ext.form.TextField({
        		gridObj : this.parentCt,enableKeyEvents : true,
    			listeners : {
	           		'specialkey' : Ext.ftl.gridEditorkeyMove,
	           		'keydown': function(f, e){
						    if(e.ctrlKey && e.getKey() == e.V){
						   	   var cm = this;
							   Ext.ffc.PasteEdition(function(data){
							   		cm.copy4Memo(data, 'workshop');
							   });
						    }
						},scope : this
	            }
        	})},
        	{header:'备注3',width:80,dataIndex:'reportCode',editor : new Ext.form.TextField({
        		gridObj : this.parentCt,enableKeyEvents : true,
    			listeners : {
	           		'specialkey' : Ext.ftl.gridEditorkeyMove,
	           		'keydown': function(f, e){
						    if(e.ctrlKey && e.getKey() == e.V){
						   	   var cm = this;
							   Ext.ffc.PasteEdition(function(data){
							   		cm.copy4Memo(data, 'reportCode');
							   });
						    }
						},scope : this
	            }
        	})}
	    ])
	},
	
	copy : function(data, column) {
		var _store = this.store;
   	    var startIndex = this.rowIndex;//开始行
   	    var alowCopyCount = _store.getCount()-startIndex;//允许复制行总数
   	    var _parentCt = this.parentCt;
		var arr = data.split(/\n/);
        if(arr.length-1 > alowCopyCount) {
        	Ext.Msg.show({
				title : '信息提示',
				msg : "复制行数不得大于现有行数！",
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
        } else {
        	for(var i = 0; i < arr.length-1; i++) {
        		var value = arr[i];
        		if(Ext.isNumber(value*1)) {
        			_store.getAt(startIndex+i).set(column, arr[i]);
        			e = new Object({
		        		'grid' : this.parentCt,
		        		'record' : _store.getAt(startIndex+i),
		        		'field' : column
		        	});
		        	_parentCt.fireEvent('afteredit', e);//触发此事件，重新计算
        		} else {
        			Ext.Msg.show({
						title : '信息提示',
						msg : "复制的记录应为数字！",
						width : 260,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
        		}
        			
        	}
        	
        }
	},
	
	copy4Memo : function(data, column) {
		var _store = this.store;
   	    var startIndex = this.rowIndex;//开始行
   	    var alowCopyCount = _store.getCount()-startIndex;//允许复制行总数
   	    var _parentCt = this.parentCt;
		var arr = data.split(/\n/);
        if(arr.length-1 > alowCopyCount) {
        	Ext.Msg.show({
				title : '信息提示',
				msg : "复制行数不得大于现有行数！",
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
        } else {
        	for(var i = 0; i < arr.length-1; i++) {
        		var value = arr[i];
        		_store.getAt(startIndex+i).set(column, arr[i]);
        	}
        	
        }
	},
	
	formatDate : function(value) {
		if(typeof(value) == 'string') 
			return value;
		else
			return value ? value.dateFormat('Y-m-d') : '';
	},
	
	//折扣颜色渲染 0没改动1改折扣2改净价3折扣净价都改4历史价格
	rendRebateColor : function(colValue, metadata, record) {
		switch (record.data['priceChange']*1) {
			case 1 :
			case 11 :
				metadata.css = "blue-column-font";
				break;
			case 3 :
			case 13 :
				metadata.css = "blue-column-font";
				break;
			case 4 :
			case 14 :
				metadata.css = "green-column-font";
				break;
		}
	},
	//净价， 金额， 含税净价， 含税金额颜色渲染
	//0没改动1改折扣2改净价3折扣净价都改4历史价格
	rendColor : function(colValue, metadata, record) {
		switch (record.data['priceChange']*1) {
			case 1 :
			case 11 :
				metadata.css = "blue-column-font";
				break;
			case 2 :
			case 12 :
				metadata.css = "red-column-font";
				break;
			case 3 :
			case 13 :
				metadata.css = "red-column-font";
				break;
			case 4 :
			case 14 :
				metadata.css = "green-column-font";
				break;
		}
	},
	
	rendTaxNetPriceColor : function(colValue, metadata, record) {
		switch (record.data['priceChange']*1) {
			case 1 :
				metadata.css = "blue-column-font";
				break;
			case 2 :
				metadata.css = "red-column-font";
				break;
			case 3 :
				metadata.css = "red-column-font";
				break;
			case 4 :
				metadata.css = "green-column-font";
				break;
			case 11 :
			case 12 :
			case 13 :
			case 14 :
				metadata.css = "teal-column-font";
				break;
		}
	}
})

QuotationManager.productsGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	loaderUrl : null,
	customerRecord : null,
	ownerCt : null,
	sm : null,
	fieldName : null,
	rowIndex : null,
	columnIndex : null,
	quotationForm : null,//报价单FormPanel
	workGrid : null,//项目工序grid
	workRecord : null,//项目当前工序Record
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.sm = new Ext.grid.CheckboxSelectionModel();
		
		
		this.store = new Ext.data.JsonStore({
			url : this.loaderUrl == null ? '#' : this.loaderUrl,
			//remoteSort : true,
			//totalProperty : 'totalProperty',
			//root : 'arrivalInfo',
			autoLoad : true,
			fields : [
				{name: 'projectCode', type: 'float',mapping:"projectCode"},
				'serialNumber','brandCode','productName','amount','productUnit',
				'price','rebate','netPrice','money','taxNetPrice',
				'taxMoney','deliveryDate','slaveFile','priceChange',
				'productBrand','memo','workshop','reportCode','leaf','toolsId','quotationInforId','id',
				'toolDescription','singleSetAssemblyAmount','singleSetStockAmount','toolCode','productCode',
				'orderAmount','arrivalAmount','deliveryAmount','contractAmount'
			]
		})
		
		this.store.on({
			'remove' : function(_store, _record, _index) {
				var _quotationForm = this.quotationForm; 
				if(Ext.isEmpty(this.workRecord)) {
					this.calculate4quo();
					/*if(_store.getCount() > 0)
						this.getTopToolbar().items.get(4).fireEvent('click');*/
				} else {
					this.calculate4projectQuo(this.workRecord);
					/*if(_store.getCount() > 0)
						this.getTopToolbar().items.get(4).fireEvent('click');*/
				}
			},scope : this
		})
		
		this.cm = new QuotationManager.productsGridColumn({
			readOnly : this.addButtonHide,
			sm : this.sm,
			isProject : Ext.isEmpty(this.workGrid) ? true : false,//工序为空为普通报价，否则为项目报价
			parentCt : this,
			store : this.store,
			quoType : this.quoType,
			isDetail : this.isDetail,
			isEditor : this.isEditor
		});
		
		QuotationManager.productsGrid.superclass.constructor.call(this, {
			sm : this.sm,
			cm : this.cm,
			title : '产品信息',
			clicksToEdit:1,//单击修改
			region : 'center',
			margins: '-5 5 5 5',
			listeners : {
				'cellclick' : function(grid, rowIndex, columnIndex, e) {
					grid.getColumnModel().rowIndex = rowIndex;
					this.rowIndex = rowIndex;
					this.columnIndex = columnIndex;
					this.fieldName = grid.getColumnModel().getDataIndex(columnIndex); // Get field name
					var record = grid.getStore().getAt(rowIndex);  // Get the Record
				    var data = record.get(this.fieldName);
					if(this.fieldName == 'leaf' && !data) {
						new QuotationManager.ProToolsListWindow({'nodeId' : record.get('toolsId')}).show();
					}
				},
				'afteredit' : function(e) {
					var _record = e.record;
					var _field = e.field;
					
					if(_field == 'amount') {
						this.calculate4Amount(_record,null, this.workRecord)
					}
					
					if(_field == 'rebate') {
						_record.set('priceChange', 1);
						this.calculate(_record,null, this.workRecord);
					}
					
					if(_field == 'netPrice') {
						var _priceChange = _record.data['priceChange'];
						_record.set('priceChange', Ext.ffc.getPriceChangeForNetPri(_priceChange));
						this.calculate4Netprice(_record,null, this.workRecord);
					} 
					
					if(_field == 'singleSetStockAmount') {
						//配套刀具套数
						var _supportAmount = parseInt(this.workRecord.get('supportAmount'));
						//备用刀具套数
						var _backupAmount = parseInt(this.workRecord.get('backupAmount'));
						//单套刀具采购数量
						var _singleSetStockAmount = parseInt(_record.get('singleSetStockAmount'));
						//数量=（配套刀具套数+备用刀具套数）*单套刀具采购数量
						var _amount = (_supportAmount + _backupAmount)* _singleSetStockAmount;
						_record.set('amount', _amount);
						this.calculate(_record,null, this.workRecord);
					}
					
					if(_field == 'taxNetPrice') {
						var _priceChange = _record.get('priceChange');
						_record.set('priceChange', this.pad(_priceChange, 2));
						this.calculate4TaxNetPrice(_record,null, this.workRecord);
					}
				},scope : this
			},
			tbar : [{
	        	text : "添加",
				iconCls:'icon-add',
				handler : function() {
					fn : this.onAddSubmit();
				},scope : this
	        },'-',{
	        	text : "删除",
	        	iconCls : 'icon-delete',
	        	handler : function() {
	        		fn : this.onDeleteSubmit();
	        	},scope : this
	        },'-',{
	        	text : '项目号排序',
	        	iconCls : 'icon-sort',
	        	listeners : {
	        		'click' : function() {
	        			fn : this.onProjectSortSubmit();
	        		},scope : this
	        	}
	        },'-',{
	        	text : '统一设置交货期限',
	        	iconCls : 'icon-date',
	        	listeners : {
	        		'click' : function(_btn, e) {
	        			fn : this.onSetDeliveryDate(_btn, e);
	        		},scope : this
	        	}
	        }]
		})
		this.addEvents('oninsertdelete');
		this.addEvents('onaddnode');
	},
	
	pad : function (num,n){
	    //return (eval('1E'+len)+v+'').slice(1)
		return Ext.ffc.pad(num,n);
	},
	
	validator : function() {
		var flag = true;
		this.store.each(function(_record) {
			var _amount = _record.data['amount'];
			var _rebate = _record.data['rebate'];
			var _deliveryDate = _record.data['deliveryDate'];
			if(_amount < 0) {
				flag = false;
				throw new Error("产品数量必须大于0！");
				return false
			}
			if(_rebate < 0) {
				throw new Error("产品折扣必须大于0！");
				flag = false;
				return false
			}
			if(_rebate > 100) {
				throw new Error("产品折扣必须小于100！");
				flag = false;
				return false
			}
			if(Ext.isEmpty(_deliveryDate)) {
				throw new Error("交货期不允许为空！");
				flag = false;
				return false
			}
		})
		
		return flag;
	},
	
	/**
	 * 产品价格计算
	 * @param {} record
	 */
	calculate : function(record, _rate, _workRecord) {
		
		//var _quotationForm = this.ownerCt.northPanel.quotationForm;
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		
		var _amount = record.data['amount'];//数量
		var _rebate = (100-record.data['rebate']*1)/100;//折扣
		
		var _price = record.data['price'];//单价
		var _netPrice = record.data['netPrice'];//净价
		
		if(!Ext.isEmpty(_rate)) {
			if(_price != 0) {
				_price = (_price*_rate).toFixed(2);
				_netPrice = (_price*_rebate).toFixed(2);//净价=单价*折扣
			} else {
				if(Ext.isEmpty(_netPrice)) {
					_netPrice = 0;
				} else {
					_netPrice = (_netPrice*_rate).toFixed(2);
				}
			}
		} else {
		
			if(_price != 0) {
				_netPrice = (_price*_rebate).toFixed(2);//净价=单价*折扣
			} else {
				if(Ext.isEmpty(_netPrice)) {
					_netPrice = 0;
				}
			}
		}
		
		var _money = (_amount*_netPrice).toFixed(2);//金额=数量*净价
		var _taxNetPrice = (_netPrice*(1+_taxRate*1)).toFixed(2);//含税净价 = 净价*税率
		var _taxMoney = (_money*(1+_taxRate*1)).toFixed(2);//含税金额=金额*(1 + 税率)
		
		record.set('price', _price);
		record.set('netPrice', _netPrice);
		record.set('money', _money);
		record.set('taxNetPrice', _taxNetPrice);
		record.set('taxMoney', _taxMoney);
		
		//计算报价单货品金额开始
		if(Ext.isEmpty(_workRecord))
			this.calculate4quo();
		else
			this.calculate4projectQuo(_workRecord);
		//计算报价单货品金额结束
	},
	
	calculate4TaxRate : function(record, _rate, _workRecord) {
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		
		var _netPrice = record.data['netPrice'];//净价
		
		var _money = record.data['money'];
		var _taxNetPrice = (_netPrice*(1+_taxRate*1)).toFixed(2);//含税净价 = 净价*税率
		var _taxMoney = (_money*(1+_taxRate*1)).toFixed(2);//含税金额=金额*(1 + 税率)
		
		record.set('taxNetPrice', _taxNetPrice);
		record.set('taxMoney', _taxMoney);
		
		//计算报价单货品金额开始
		if(Ext.isEmpty(_workRecord))
			this.calculate4quo();
		else
			this.calculate4projectQuo(_workRecord);
		//计算报价单货品金额结束
	},
	
	//含税净价
	calculate4TaxNetPrice : function(record, _rate, _workRecord) {
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		
		var _taxNetPrice = record.data['taxNetPrice'];//净价
		var _amount = record.data['amount'];//数量
		
		var _taxMoney = (_amount*_taxNetPrice).toFixed(2);//含税金额=（含税净价*数量）---金额*(1 + 税率)
		
		record.set('taxMoney', _taxMoney);
		
		//计算报价单货品金额开始
		if(Ext.isEmpty(_workRecord))
			this.calculate4quo();
		else
			this.calculate4projectQuo(_workRecord);
		//计算报价单货品金额结束
	},
	
	calculate4Netprice : function(record, _rate, _workRecord) {
		
		//var _quotationForm = this.ownerCt.northPanel.quotationForm;
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		
		var _amount = record.data['amount'];//数量
		//var _rebate = (100-record.data['rebate']*1)/100;//折扣
		
		var _price = record.data['price'];//单价
		var _netPrice = record.data['netPrice'];//净价
		
		var _money = (_amount*_netPrice).toFixed(2);//金额=数量*净价
		var _taxNetPrice = (_netPrice*(1+_taxRate*1)).toFixed(2);//含税净价 = 净价*税率
		var _taxMoney = (_money*(1+_taxRate*1)).toFixed(2);//含税金额=金额*(1 + 税率)
		
		//record.set('price', _price);
		record.set('netPrice', _netPrice);
		record.set('money', _money);
		record.set('taxNetPrice', _taxNetPrice);
		record.set('taxMoney', _taxMoney);
		
		//计算报价单货品金额开始
		if(Ext.isEmpty(_workRecord))
			this.calculate4quo();
		else
			this.calculate4projectQuo(_workRecord);
		//计算报价单货品金额结束
	},
	
	calculate4Amount : function(record, _rate, _workRecord) {
		
		//var _quotationForm = this.ownerCt.northPanel.quotationForm;
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		
		var _amount = record.data['amount'];//数量
		
		var _netPrice = record.data['netPrice'];//净价
		
		var _money = (_amount*_netPrice).toFixed(2);//金额=数量*净价
		//var _taxNetPrice = (_netPrice*(1+_taxRate*1)).toFixed(2);//含税净价 = 净价*税率
		//var _taxMoney = (_money*(1+_taxRate*1)).toFixed(2);//含税金额=金额*(1 + 税率)
		var _taxNetPrice = record.get('taxNetPrice');
		var _taxMoney = (_taxNetPrice*_amount).toFixed(2);//含税金额=含税净价*数量
		
		//record.set('netPrice', _netPrice);
		record.set('money', _money);
		//record.set('taxNetPrice', _taxNetPrice);
		record.set('taxMoney', _taxMoney);
		
		//计算报价单货品金额开始
		if(Ext.isEmpty(_workRecord))
			this.calculate4quo();
		else
			this.calculate4projectQuo(_workRecord);
		//计算报价单货品金额结束
	},
	
	/**
	 * 普通报价单价格计算
	 */
	calculate4quo : function() {
		//var _quotationForm = this.ownerCt.northPanel.quotationForm;
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		var product_money = 0; //货品金额
		this.store.each(function(_record) {
			product_money += _record.get('money')*1;
		});
		
		var _overallRebate = _quotationForm.findByType('numberfield')[3].getValue();//整单折扣
		if(_overallRebate == "") {
			_overallRebate = 0;
		}
		var _formTaxMoney = parseFloat(product_money * _taxRate).toFixed(2); //税金
		var _totalMoney = parseFloat(parseFloat(product_money) + parseFloat(_formTaxMoney)).toFixed(2);//税价合计
		var _finalMoney = parseFloat(_totalMoney*(100-_overallRebate)/100).toFixed(2);//最终金额
		_quotationForm.findByType('numberfield')[0].getEl().dom.value=product_money.toFixed(2)//.setValue(product_money);
		_quotationForm.findByType('numberfield')[1].getEl().dom.value=_formTaxMoney;
		_quotationForm.findByType('numberfield')[2].getEl().dom.value=_totalMoney;
		_quotationForm.findByType('numberfield')[4].getEl().dom.value=_finalMoney;
	},
	
	/**
	 * 项目报价单计算
	 */
	calculate4projectQuo : function(_workRecord) {
		var _quotationForm = this.quotationForm;
		var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
		var product_money = 0; //货品金额
		var work_money = 0; //工序金额小计
		
		//产品金额合计
		this.store.each(function(_record) {
			work_money += _record.get('money')*1;
		});
		
		_workRecord.set('totalMoney', work_money.toFixed(2));
		
		//工序金额合计
		this.workGrid.store.each(function(record) {
			product_money += record.get('totalMoney')*1;
		})
		
		var _overallRebate = _quotationForm.findByType('numberfield')[3].getValue();//整单折扣
		if(_overallRebate == "") {
			_overallRebate = 0;
		}
		var _formTaxMoney = parseFloat(product_money * _taxRate).toFixed(2); //税金
		var _totalMoney = parseFloat(parseFloat(product_money) + parseFloat(_formTaxMoney)).toFixed(2);//税价合计
		var _finalMoney = parseFloat(_totalMoney*(100-_overallRebate)/100).toFixed(2);//最终金额
		_quotationForm.findByType('numberfield')[0].getEl().dom.value=product_money.toFixed(2)//.setValue(product_money);
		_quotationForm.findByType('numberfield')[1].getEl().dom.value=_formTaxMoney;
		_quotationForm.findByType('numberfield')[2].getEl().dom.value=_totalMoney;
		_quotationForm.findByType('numberfield')[4].getEl().dom.value=_finalMoney;
	},
	
	/**
	 * 重新载入表单数据
	 */
	reload : function() {
		this.getStore().reload();
	},
	
	/**
	 * 获取所选取的记录的第一条。如果没有选择，抛出一个异常。
	 * @return {} Record
	 */
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择一条记录进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	/**
	 * 返回所有选中的记录
	 * @return {}
	 */
	getSelections : function() {
		var _sm = this.getSelectionModel();
		
		if(_sm.getCount() == 0) {
			throw Error('请选择一条记录进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	
	/**
	 * 删除选中记录
	 */
	remove : function() {
		try {
			var _record = this.getSelected();
			this.getStore().remove(_record);
		}catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message,// == null ? '请选择到货单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
		
	},
	
	removeRecord : function(_record) {
		this.getStore().remove(_record);
	},
	
	removeRecords : function(_arr) {
		for(var i = 0; i < _arr.length; i++) {
			this.removeRecord(this.getStore().getById(_arr[i].get("id")));
		}
	},
	
	//添加产品
	onAddSubmit : function() {
		var proListWindow = new Quoproduct.ProductListWindow({
			customerRecord : this.customerRecord
		})
		var _proTree = proListWindow.northPanel.productTree;
		var _historyTree = proListWindow.centerPanel.historyTree;
		_proTree.store.baseParams.customerId = this.customerRecord.get("customerId");
		var _historyTreeLoader = _historyTree.getLoader();
		_proTree.on('click', function(_node) {
			_historyTreeLoader.baseParams.proId = _node.id
			_historyTreeLoader.baseParams.customerId = this.customerRecord.get("customerId");
			_historyTree.getRootNode().reload();
		})
		
		_historyTree.on('click', function() {
			var selectedItem = _proTree.getSelectionModel().getSelectedNode();
            if(selectedItem) {
                selectedItem.unselect();
            }
		})
		
		proListWindow.on('onclick', function(_newNode) {
			var _quotationForm = this.quotationForm;
			var _curRate = _quotationForm.currCombox.curRate;
			//alert(_newNode.priceChange);
			//如果选择历史价格，需要进行汇率转换
			var _rate = 1;
			if(!Ext.isEmpty(_newNode.rate))
				_rate = _newNode.rate;
			var _price = _newNode.price * (_rate/_curRate);
			
			_newNode.price = _price.toFixed(2);
			_newNode.projectCode = this.getStore().getCount() + 1;
			_newNode.serialNumber = _newNode.projectCode;
			
			var record = new Ext.data.Record();
			Ext.apply(record.data, _newNode);
			
			var _taxRate = _quotationForm.taxRateCombox.getValue();//税率
			var _amount = record.data['amount'];//数量
			var _rebate = (100-record.data['rebate']*1)/100;//折扣
				
			var _netPrice = 0;//净价=单价*折扣
			
			if(_newNode.priceChange == 4) {
				_netPrice = _newNode.netPrice*(_rate/_curRate);
			} else {
				if(_price != 0) {
					_netPrice = (_newNode.price*_rebate).toFixed(2);//净价=单价*折扣
				} else {
					if(!Ext.isEmpty(_newNode.netPrice))
						_netPrice = _newNode.netPrice*(_rate/_curRate);
				}
			}
			
			var _money = (_amount*_netPrice).toFixed(2);//金额=数量*净价
			var _taxNetPrice = (_netPrice*(1+_taxRate*1)).toFixed(2);//含税净价 = 净价*税率
			var _taxMoney = (_money*(1+_taxRate*1)).toFixed(2);//含税金额=金额*(1 + 税率)
			
			record.set('netPrice', _netPrice);
			record.set('money', _money);
			record.set('taxNetPrice', _taxNetPrice);
			record.set('taxMoney', _taxMoney);
			
			//如果是项目报价单设置 单套刀具装配数量及采购数量默认值为1
			if(!Ext.isEmpty(this.workRecord)) {
				record.set('singleSetAssemblyAmount',1);
				record.set('singleSetStockAmount',1);
			}
			
			this.store.add(record);
			
			if(Ext.isEmpty(this.workRecord))
				this.calculate4quo();
			else
				this.calculate4projectQuo(this.workRecord);
			
			this.fireEvent('onaddnode', this, record);
		},this)
		proListWindow.show();
	},
	
	//删除产品
	onDeleteSubmit : function() {
		try {
			var record = this.getSelected();
			//如果删除的节点不在数据库中直接删除，否则将产品ID放入数组，再删除前台数据。
		    if(!Ext.isEmpty(record.data['quotationInforId']) &&
		    	record.data['quotationInforId'] == this.quotationForm.getValues().data['id']) {
		    		this.removeRecord(record);
		        	var _node = {id : record.id};
					this.fireEvent('oninsertdelete',this, _node);
					this.onProjectSortSubmit();//删除记录后重新排序
	       		
		    } else {
		        this.removeRecord(record);
		        this.onProjectSortSubmit();//删除记录后重新排序
		     }
		
		} catch(_e) {
			 Ext.Msg.show({
				title:'信息提示',
				msg: _e.message,
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
		}
		
	},
	
	//项目号排序
	onProjectSortSubmit : function() {
	    this.store.sort('projectCode', 'ASC');
		
		var rowNum = 0;
		var oldNum = 0;
		var _serNum = 1;
		this.store.each(function(record) {
			var _projectNumber = record.data['projectCode'];
			if(_projectNumber != oldNum) {
				rowNum ++;
			}
			oldNum = _projectNumber;
			//修改项目号
			record.set('projectCode', rowNum);
			//修改序号
			record.set('serialNumber', _serNum);
			_serNum++;
		})
	},
	
	//统一设置交货期
	onSetDeliveryDate : function(_btn, e) {
		var _store = this.store;
		var firstRecord = _store.getAt(0);
		if(Ext.isEmpty(firstRecord)) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请添加报价产品后，再设定交货日期！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		} 
		
		if(!Ext.isEmpty(firstRecord.get('deliveryDate'))) {
			this.setDeliveryDate(firstRecord.get('deliveryDate'));
		} else {
			var dataf = new Ext.form.DateField({format:'Y-m-d'});
			var menu = new Ext.menu.DateMenu();
			menu["on"]("select", function(a,b){
				var bb = dataf.formatDate(dataf.parseDate(b));
				this.setDeliveryDate(bb);
				menu.hide();
			},this);
			menu.show(_btn.getEl());
		}
	},
	//设置交货期
	setDeliveryDate : function(_date) {
		this.store.each(function(_record) {
			_record.set('deliveryDate', _date)
		})
	}
	
})

//-----------------------------------展示预订报价单的窗口----------------------------------
Ext.ftl.QuoInfoStore = Ext.extend(Ext.data.JsonStore, {
	quoIdStr : null,
	quoType :null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var actionType = 'getQuoInfoByImpQuoId';
		if(this.quoType == 4) {
			//4 试刀
			actionType = 'getQuoInfo4TestCut';
		}
		
		Ext.ftl.QuoInfoStore.superclass.constructor.call(this, {
			url: PATH +　'/generalQuo/excelAction.do?method=' + actionType,
			baseParams : {quoId : this.quoIdStr},
			autoLoad : true,
	        fields: ['quotationCode']
		})
	}
})

Ext.ftl.QuoInfoListView = Ext.extend(Ext.ListView, {
	store : null,
	quoIdStr : null,
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.ftl.QuoInfoStore({quoIdStr : this.quoIdStr, quoType : this.quoType});
		Ext.ftl.QuoInfoListView.superclass.constructor.call(this, {
			store: this.store,
	        multiSelect: true,
	        reserveScrollOffset: true,
			
	        columns: [{
	            header: this.quoType == 4 ? '交货单号' : '报价单编号',
	            width : 1,
	            dataIndex: 'quotationCode',
	            tpl: '{quotationCode}'
	        }]
		})
	}
})

Ext.ftl.QuoInfoListPanel = Ext.extend(Ext.Panel, {
	listView : null,
	quoIdStr : null,
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.listView = new Ext.ftl.QuoInfoListView({quoIdStr : this.quoIdStr, quoType : this.quoType});
		Ext.ftl.QuoInfoListPanel.superclass.constructor.call(this, {
	        collapsible:false,
	        layout:'fit',
	        //title:'附件列表',
	        iconCls:'icon-grid',
	        closeAction:'hide',
	        items: this.listView
		})
	}
})

Ext.ftl.QuoInfoViewWindow = Ext.extend(Ext.Window, {
	listDataPanel : null,
	quoIdStr : null,
	slaveStore : null,
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		//3 预订
		if(this.quoType == 3) {
			this.listDataPanel = new QuotationManager.QuoCodeListTree({quoIdStr : this.quoIdStr});
		} else {
			this.listDataPanel = new Ext.ftl.QuoInfoListPanel({quoIdStr : this.quoIdStr, quoType : this.quoType});
		}
		Ext.ftl.QuoInfoViewWindow.superclass.constructor.call(this, {
			title : this.quoType == 4 ? '交货单信息' : '报价单信息',
	        width:400,
	        height : 300,
	        layout:'fit',
	        closeAction : 'hide',
	        constrainHeader : true,
	        maximizable : true,
	        pageX : 450,
	        pageY : 70,
	        items : this.listDataPanel
		})
	}
})

QuotationManager.QuoCodeListTree = Ext.extend(Ext.tree.ColumnTree, {
	quoIdStr : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		QuotationManager.QuoCodeListTree.superclass.constructor.call(this, {
	        width:647,
			height: 400,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        columns:[
	        	{header : '图标',width : 100,disEnableEdit : true},
	        	{header:'单据编号',width:200,dataIndex:'code'}
	        ],
	        
	        loader: new Ext.tree.TreeLoader({
	        	dataUrl: PATH + '/generalQuo/excelAction.do?method=getQuoInfo4Export',
	            autoLoad : true,
	            baseParams : {quoId : this.quoIdStr},
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		});
	}
});
Ext.ffc.LockStatusComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.SimpleStore({
						fields : ['lockStatus', 'value'],
						data : [['正常',0],['锁定', 1]]
					});
		Ext.ffc.LockStatusComboBox.superclass.constructor.call(this, {
					fieldLabel : '锁定状态',
					hiddenName : 'lockStatus',
					mode : 'local',
					displayField : 'lockStatus',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '请选择状态',
					store : this.store
		})
	}
})
//-------------------------------------报价单表格结束---------------------------------

//单位
Ext.ftl.UnitComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['id','productUnit'],
						data : [
							[0,'个'],
							[1,'把'],
							[2,'片'],
							[3,'台'],
							[4,'块'],
							[5,'支'],
							[6,'套'],
							[7,'件'],
							[8,'公斤'],
							[9,'根'],
							[10,'付'],
							[11,'批'],
							[12,'条']
						]
					});
		Ext.ftl.UnitComboBox.superclass.constructor.call(this, {
					fieldLabel : '计量单位',
					valueField : 'productUnit',
					hiddenName : 'productUnit',
					anchor : '95.5%',
					emptyText:'请选择单位',
					mode : 'local',
					displayField : 'productUnit',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

Ext.ffc.YearComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.currYear = new Date().format('Y');
		var arr = [['所有年份','']];
		for(var i = 2009;i <= this.currYear ;i++ ){
			arr.push([i,i]);
		}
		this.store = new Ext.data.SimpleStore({
						fields : ['year', 'value'],
						data : arr
					});
		Ext.ffc.YearComboBox.superclass.constructor.call(this, {
					fieldLabel : '年度',
					hiddenName : 'year',
					mode : 'local',
					displayField : 'year',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : this.currYear,
					store : this.store
		})
	}
});

/**
 * 订单类型
 * @class Ext.ftl.StockOrderComboBox
 * @extends Ext.form.ComboBox
 */
Ext.ftl.StockOrderTypeComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['orderType','value'],
						data : [['全部',''],['合同订单', '1'],['储备订单', '2'],
						['加工订单', '3'], ['材料储备订单', '4'], ['预定订单', '5'],
						 ['试刀订单', '6'], ['预定加工订单', '7'], ['试刀加工订单', '8']]
					});
		Ext.ftl.StockOrderTypeComboBox.superclass.constructor.call(this, {
			fieldLabel : '订单状态',
					hiddenName : 'orderType',
					mode : 'local',
					displayField : 'orderType',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
});

/**
 * 订单采购、加工
 * @class Ext.ftl.StockOrderComboBox
 * @extends Ext.form.ComboBox
 */
Ext.ftl.StockOrderCgjgComboBox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['cgjg','value'],
						data : [['全部',''],['采购', '0'],['加工', '1']]
					});
		Ext.ftl.StockOrderCgjgComboBox.superclass.constructor.call(this, {
			fieldLabel : '订单状态',
					hiddenName : 'cgjg',
					mode : 'local',
					displayField : 'cgjg',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
});

function viewContractInfor(obj){
	Ext.Ajax.request({
		url: PATH + '/contract/contractInforsViewPanel.do?ffc=getContractInforsByYuDingDetailId',
		params: { detailId: obj.id.substring(0,32) },
		success : function(response) {
			var responseArray = Ext.util.JSON.decode(response.responseText); 
			var recs = responseArray;
			var str = '';
			for(var i = 0;i < recs.length ;i++ ){
				str += recs[i].contractCode + '转'+ recs[i].amount + '\n';
			}
			/*
			new Ext.ToolTip({
				target: obj.id,
				width: 250,
				html: str,
				dismissDelay: 15000,
				anchor: 'top',
				hidden: true
			});
			*/
			obj.title = str;
		}
	});   
}
function updateYuDing2ConWindow(obj){
    var win = new Ext.ffc.Reserve2ContractGridWindow({quoDetailId:obj.id.substring(0,32)});
	win.show();
}
/**
报价单类型，新增合同专用
*/
Ext.ffc.QuoTypeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['quoType','quotationTypeText'],
						data : [
							[0,'普通/项目报价'],
							[3,'预订报价'],
							[4,'试刀报价']
						]
					});
		Ext.ffc.QuoTypeComboBox.superclass.constructor.call(this, {
					fieldLabel : '报价单类型',
					valueField : 'quoType',
					hiddenName : 'quoType',
					mode : 'local',
					displayField : 'quotationTypeText',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

function taxRateComboxChange(field, newValue, oldValue ){
			var formPanel = field.ownerCt;
			var form = formPanel.getForm();
			if(newValue * 1 == 0){
							form.findField('exemplarInvoice').setDisabled(false);
			}else{
							form.findField('exemplarInvoice').setDisabled(true);
							form.findField('exemplarInvoice').setValue(0);
			}
}


/**
 * 产品对应采购价格
 * @class Ext.ffc.ProductOrderProductGrid
 * @extends Ext.grid.GridPanel
 */	    
Ext.ftl.ProductOrderProductGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var ds = new Ext.data.JsonStore({
					url : PATH
							+ '/baseInfo/orderProductHistoryListAction.do',
					root : 'orderProductHistoryList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['productName', 'productCode', 'brandCode',
							'historyPrice', 'userName', 'supplierName',
							'editDate', 'stockPriceDate',
							'productToolsInforId', 'id','historyMarketPrice','historyRebate']
				});
		
		Ext.ftl.ProductOrderProductGrid.superclass.constructor.call(this, {
			width : 280,
			enableHdMenu : false,
			stripeRows : true,
			title : '采购价格',
			collapsible : true,
			split : true,
			ds : ds,
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '该产品无采购价格记录！'
			}),
			selModel : new Ext.grid.RowSelectionModel({
				singleSelect : true
			}),
			cm : new Ext.grid.ColumnModel([
				 {
					header : '日期',
					dataIndex : 'editDate',
					width:90,
					sortable:true
				}, {
					header : '未税采购净价',
					dataIndex : 'historyPrice',
					width:120
				}, {
					header : '供应商',
					dataIndex : 'supplierName'
					
				}, {
					header : '进销差价',
					renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
						var price = record.get('historyPrice');
						if(this.netPrice > price){
						    return '<span style="color:green">' + (this.netPrice - price).toFixed(2) + '</span>';	
						}else{
							  return '<span style="color:red">' + (this.netPrice - price).toFixed(2) + '</span>';	
						}
					},scope:this
				},{
					header : '毛利率',
					renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){
						var price = record.get('historyPrice');
						if(this.netPrice > price){
						    return '<span style="color:green">' + ((this.netPrice - price)/this.netPrice).toFixed(2) + '</span>';	
						}else{
							  return '<span style="color:red">' + ((this.netPrice - price)/this.netPrice).toFixed(2) + '</span>';	
						}
					},scope:this
				}])
		});

	}
});


/**
 * 转合同下拉框
 * @class TransferContractCombox
 * @extends Ext.form.ComboBox
 */
TransferContractCombox = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var arr = [['全部',''],['未转合同', '0'],['部分转合同', '1']];
		if(this.quoType == 3 || this.quoType == 4){
			arr.push(['全部转合同', '2']);
		}else{
		  arr.push(['已转合同', '2']);
		}
		this.store = new Ext.data.SimpleStore({
						fields : ['transferContract', 'value'],
						data : arr
					});
		TransferContractCombox.superclass.constructor.call(this, {
					fieldLabel : '生成合同',
					hiddenName : 'transferContract',
					mode : 'local',
					displayField : 'transferContract',
					valueField : 'value',
					//anchor:'90%',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					value : '全部',
					store : this.store
		})
	}
})

Ext.ftl.protools.onSlaveClick = function(_id) {
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : 1});
	slaveWindow.show();
}


Ext.ffc.AuditMsgWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this._form = new Ext.FormPanel({
			    //layout:'form',
				labelWidth :60,
				frame:true,
				margins: '-5 5 5 5',
				//labelAlign :'right',
			    items: [{
					fieldLabel: '审核意见',
					name: 'audtMsg',
					xtype:'textarea',
					allowBlank:false,
					width:470,
					x:10,
					y:0,
					margins: '-5 5 5 5'
				}],
          buttons: [{
          		text: '审核通过',
		    			handler:function(){
								var _this = this;
								var msg = _this._form.getForm().getValues()['audtMsg'];
								if(msg == ''){
									msg = "同意";
								}
								var arr = _this.auditContentsObj;
								var checkedArr = [];
								for(var i = 0;i < arr.length;i++){
									if(arr[i].checked){
										checkedArr.push(arr[i].value);
									}
								}
								if(checkedArr.length < arr.length){
								    Ext.Msg.show({title:'信息提示',msg: '请选择本次审批需要审核的【审核内容】，在单据的左下角！',buttons: Ext.Msg.OK,width : 200,icon: Ext.MessageBox.INFO});
								    return ;	
								}
								Ext.MessageBox.confirm('系统提示', '请确认是否要【审批通过】!', function(btn){
									if(btn != 'yes'){return ;}
									_this.executeAudit([_this.busId],msg,1,checkedArr);
								});
		    			},scope:this
          	},{
          		text: '审核退回',
		    			handler:function(){
		    				var _this = this;
		    				var msg = '';
								if(!this._form.getForm().isValid()){
									return ;
								}
								Ext.MessageBox.confirm('系统提示', '请确认是否要【审批退回】!', function(btn){
									if(btn != 'yes'){return ;}
									_this.executeAudit([_this.busId],_this._form.getForm().getValues().audtMsg,0,[]);
								});
		    			},scope:this
          	}]
			});
		
		Ext.ffc.AuditMsgWindow.superclass.constructor.call(this, {
			title:"请填写意见并审核",
			width:600,
			height:160,
			layout:'fit',
		  modal:true,
			items:this._form,
			executeAudit : function(pbussinessIds,pcomment,popType,auditContentArray){
				  var grid = this.grid;
					var auditTypeObj = this.auditType;
					var _this = this;
					
					Ext.Ajax.request({
							method: "post",
							params: { bussinessIds:pbussinessIds,auditType: auditTypeObj.auditTypeId,auditInforId:auditTypeObj.auditFlowId,comment:pcomment,opType:popType,auditContentArray:auditContentArray},
							url: PATH + "/manage/audit/auditInforMangeAction.do?ffc=executeAudit",
							success: function(response){
								if(response.responseText == 'true'){
									Ext.MessageBox.alert('系统提示', '审批成功!', function(){
										_this.grid.loadData(0,100);
										_this.bussinessWindow.close();
										_this.close();
									});		
								}else{
								  Ext.MessageBox.alert('系统提示', response.responseText);	
								}
							},
							failure:function(response){
								Ext.MessageBox.alert('系统提示', response.responseText);	
							}
					});
		  },scope:this
		})
}}); 

Ext.ffc.UrgentLevelTranction = function (urgentLevelId){
    if(urgentLevelId * 1 == 0){
	    return '一般';
	}else if(urgentLevelId * 1 == 1){
		return '紧急';
	}
	return '无';
}


Ext.ffc.ToolsTypeComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	getNameById : function(_id){
	    if(!_id){
		    throw Error('查找产品类别，未输入id');
		}
		var name = '';
		this.store.each(function(item){
		    if(_id == item.get("toolsTypeId")){
			    name = item.get("toolsTypeName");
			}
		});
		return name;
	},
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.SimpleStore({
						fields : ['toolsTypeId','toolsTypeName'],
						data : [
							['',''],
							[1,'机加'],
							[2,'整硬'],
							[3,'刀片'],
							[4,'修磨']
						]
					});
		Ext.ffc.ToolsTypeComboBox.superclass.constructor.call(this, {
					fieldLabel : '产品类别',
					valueField : 'toolsTypeId',
					hiddenName : 'toolsTypeId',
					width:100,
					mode : 'local',
					displayField : 'toolsTypeName',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})


Ext.ffc.pad = function(num,n){
    return Array(Math.abs(('' + num).length - ( n + 1 ))).join(1) + num;
} 

Ext.ffc.getPriceChangeForNetPri = function(_priceChange){
    if( _priceChange== 1 || _priceChange== 11  || _priceChange== 13) {
		return 3;
	} else if(_priceChange == 0 || _priceChange == 4 || _priceChange == 10 || _priceChange == 14) {
		return 2;
	}
	return 0;
}