/**
 * 定义命名空间
 */
Ext.namespace('Ext.ftl.search');

/**
 * 搜索Store, 绑定到当前combo的数据源
 * @class Ext.ftl.search.ComboStore
 * @extends Ext.data.JsonStore
 */
Ext.ftl.search.ComboStore = Ext.extend(Ext.data.JsonStore, {
	orderStoreUrl : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		Ext.ftl.search.ComboStore.superclass.constructor.call(this, {
			url : this.orderStoreUrl == null ? Ext.ftl.search.Combo.STORE_URL : this.orderStoreUrl,
			fields : ['orderCode','id','supplierName','supplierId','contractCode',
			'orderType','customerName','customerCode','quotationId','quotationCode',
			{name: 'memo', type: 'string',mapping:"mome"}
			],
			root : 'orderInfo'
		})
	}
})

/**
 * 定单自动补全ComboBox
 * @class Ext.ftl.arrival.order.Combo
 * @extends Ext.form.ComboBox
 */
Ext.ftl.search.Combo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	ownerCt : null,
	orderStoreUrl : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = this.store == null ? new Ext.ftl.search.ComboStore({orderStoreUrl : this.orderStoreUrl , referenceObj : this}) : this.store;
		Ext.ftl.search.Combo.superclass.constructor.call(this, {
			store: this.store,
	        fieldLabel : '订单编号',
	        hiddenName : 'orderCode',
	        valueField:'id',
	        allowBlank : false,
	        blankText : '请输入订单编号',
	        hideTrigger : true,//隐藏触发按钮
	        displayField:'orderCode',
	        typeAhead: true,//当开始输入字符时，在指定的延迟之后会自动匹配剩下的内容，如果找到了匹配的内容则自动选中它
	        forceSelection: false,//限制选择的值必须是下拉列表中的值
	        triggerAction: 'all',
	        queryParam : 'orderCode',//发送至后台的查寻参数值
	        emptyText:'请输入订单编号',
	        selectOnFocus:true,//当获得焦点时立即选中输入栏中存在的所有文本
	        //enableKeyEvents: true,
	        listeners : {
	        	'render' : function() {
	        		this.store.on('load', function() {
	        			var total = this.store.getCount();
	        			
		        		if(total > 0) {
		        			this.setValue(this.store.getAt(0).get('id'))
		        			this.ownerCt.ownerCt.buttons[0].show();
		        		}
	        		},this)
	        	},
	        	
	        	'change' : function() {
	        		if(Ext.isEmpty(this.getValue()))
	        			this.ownerCt.ownerCt.buttons[0].hide();
	        	},
	        	
	        	'select' : function() {
	        		this.ownerCt.ownerCt.buttons[0].show();
	        	}
	        }
		})
	},
	/**
	 * 根据订单Id获取订单Record
	 * @param {} _id 订单Id
	 * @return {} 订单 Record
	 */
	getRecordById : function(_id) {
		if(Ext.isEmpty(_id)) {
			throw Error('请输入订单编号！');
		} else {
			if(this.getStore().getCount() == 0) {
				throw Error('请输入正确订单编号！');	
			} else {
				return this.getStore().getById(_id);
			}
		}
	}
});

/**
 * 定单搜索Form
 * @class Ext.ftl.search.SearchForm
 * @extends Ext.FormPanel
 */
Ext.ftl.search.SearchForm = Ext.extend(Ext.FormPanel, {
	orderCombo : null,
	orderComboStoreUrl : null,//订单搜索URL
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.orderCombo = new Ext.ftl.search.Combo({
			orderStoreUrl : this.orderComboStoreUrl,
			ownerCt : this
		});
		Ext.ftl.search.SearchForm.superclass.constructor.call(this, {
			width:290,  
			height:100,
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "400"},
			labelWidth : 55,
			items : this.orderCombo
		})
	},
	
	/**
	 * 获取表单值
	 * @return {} Record
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		return this.getForm().isValid();
	}
})

/**
 * 定单搜索Window
 * @class Ext.ftl.search.OrderSearchWindow
 * @extends Ext.Window
 */
Ext.ftl.search.OrderSearchWindow = Ext.extend(Ext.Window, {
	searchForm : null,
	orderComboStoreUrl : null,//订单搜索URL
	orderType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.searchForm = new Ext.ftl.search.SearchForm({
			orderComboStoreUrl : this.orderComboStoreUrl
		});
		Ext.ftl.search.OrderSearchWindow.superclass.constructor.call(this, {
			title: this['title'] == null ? "订单搜索" : this['title'],  
			width:350,  
			height:150,  
			plain:true,
			closable : true,
			resizable : true,
			constrainHeader : true,
			closeAction:'hide',
			layout:"form",
			buttonAlign : 'center',
			buttons : [{
				text : "确定",
				hidden : true,
				handler : function() {
					fn : this.onSubmitClick();
				},scope : this
			},{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			items : this.searchForm
		})
		
		this.addEvents("onsubmitclick");
	},
	
	/**
	 * 单击确定时触发
	 */
	onSubmitClick : function() {
		//搜索Form
		var _form = this.searchForm;
		var spiltUrl = this.orderComboStoreUrl.split("=");
		if(!_form.validator()) {
			return;
		}
		var _r = _form.getValues();
		var orderRecord = null;
		//下拉框
		try {
			orderRecord = this.searchForm.orderCombo.getRecordById(_r.get('orderCode'));
			
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message == null ? '请输入订单编号！' : _err.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		
		var isOrderHidden = false;//1为采购订单为false不隐藏, 2为储备订单：true
		/*if(orderRecord.get('orderType') != 1) {
			isOrderHidden = true;
		}*/
		//orderType 1 合同入库 2 储备入库 5 预订入库 6 试刀入库
		if(this.orderType == 2) {
			isOrderHidden = true;
		}
		var arrivalAddWindow = new Ext.ftl.arrival.add.ArrivalAddWindow({
			orderId : _r.get('orderCode'),
			isOrder : isOrderHidden,
			orderType : this.orderType
		});
		
		arrivalAddWindow.on('onsubmit',function(_form, _record) {
			this.fireEvent("onsubmitclick",_form, _record);
        },this)
        
		var _arrInfoform = arrivalAddWindow.northPanel.arrivalInfoForm;
		arrivalAddWindow.show();
		//alert(Ext.encode(orderRecord.data));
		_arrInfoform.setValues(orderRecord);
		this.hide();
	}
})
//订单搜索URL根据订单类型及订单编号获取订单
Ext.ftl.search.Combo.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getStockOrder';