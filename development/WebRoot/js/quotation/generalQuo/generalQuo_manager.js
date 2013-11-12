//命名空间
Ext.namespace('Ext.ftl.generalQuo.manage');

//命名空间别名
Quomanager = Ext.ftl.generalQuo.manage;


//------------------------------------报价单编制--------------------------------------
Quomanager.QuotationForm = Ext.extend(Ext.FormPanel, {
	isReadOnly : false,
	currCombox : null,
	sallerCombox : null,
	taxRateCombox : null,
	paymentCombox : null,
	quoDateField : null,
	startDateField : null,
	endDateField : null,
	currIdField : null,
	willFormalDateField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.currIdField = new Ext.form.TextField({
			xtype:'hidden',fieldLabel: 'currency',name: 'currency',hidden : true, hideLabel : true
		})
		this.currCombox = new CurrencyCombox({disabled : this.isReadOnly,x:600,y:93,width:170});
		this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,width:170,x:860,y:33,listeners:{"change":taxRateComboxChange}});
		this.sallerCombox = new SallerCombox({disabled : this.isReadOnly,x:600,y:3,width:170});
		this.paymentCombox = new Ext.zhj.PaymentConditionComboBox({width : 680,disabled : this.isReadOnly, x:90,y:153});
		this.quoDateField = new Ext.form.DateField({
			fieldLabel: '报价日期', format:'Y-m-d',name: 'quotationDate' ,x:90,y:33, width:170, format:'Y-m-d', 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
		})
		this.startDateField = new Ext.form.DateField({
			fieldLabel: '报价有效期', format:'Y-m-d',name: 'validStartDate', format:'Y-m-d', x:90,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '报价有效期开始日期不能晚于结束日期',value:new Date(),
			validator : function(_value) {
				var endDate = this.ownerCt.endDateField.getValue();
				if(!Ext.isEmpty(endDate)) {
					if(_value < endDate.format("Y-m-d")) {
						return true;
					} else { 
						return false;
					}
				} else {
					return true;
				}
			}
		})
		
		this.endDateField = new Ext.form.DateField({
			fieldLabel: '至', format:'Y-m-d',name: 'validEndDate', format:'Y-m-d', x:340,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '报价有效期结束日期不能早于开始日期',
			validator : function(_value) {
				var startDate = this.ownerCt.startDateField.getValue();
				if(!Ext.isEmpty(startDate)) {
					if(_value > startDate.format("Y-m-d")) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		})
		
		//是否预订
		/*this.checkBox = new Ext.form.Checkbox({
			xtype:'textfield' ,name: 'willOrderExpected', inputValue : 1, 
			width : 170, readOnly : this.isReadOnly, allowBlank : false,x:790,y:183,
			disabled : this.isReadOnly,hidden : true, hideLabel : true
		})*/
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:860,y:213, width:170,validationEvent : false,disabled : this.isReadOnly,
			hidden : true, hideLabel : true
		})
		
		this.exemplarInvoiceField = new Ext.form.Checkbox({
			name: 'exemplarInvoice', x:1035,y:35,disabled:true
		});
		
		this.currCombox.on({
			'change' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.currCombox.store.on({
			'load' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.startDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.endDateField.validate();
			},scope : this
		})
		
		this.endDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.startDateField.validate();
			},scope : this
		})
		
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var config = [
			//1
			{xtype:'label',text: '报价单号:',x:0,y:5,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:3, width:170,name: 'quotationCode'},
			{xtype:'label',text: '客户编号:',x:250,y:5,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerCode',readOnly : true,x:340,y:3,width:170},
			{xtype:'label',text: '卖方名称:',x:510,y:5,style:this.lableStyle_},
		    this.sallerCombox,
			{xtype:'label',text: '货品金额:',x:770,y:5,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'productMoney',readOnly : true,x:860,y:3,width:170},
			//2
			{xtype:'label',text: '报价日期:',x:0,y:35,style:this.lableStyle_},
			this.quoDateField,
			{xtype:'label',text: '客户联系人:',x:250,y:35,style:this.lableStyle_},
			{xtype:'textfield',  name: 'cusContactPerson',x:340,y:33,width:170},
			{xtype:'label',text: '我方负责人:',x:510,y:35,style:this.lableStyle_},
		    {xtype:'textfield',  name: 'userName',readOnly : true,x:600,y:33,width:170},
			{xtype:'label',text: '税率:',x:770,y:35,style:this.lableStyle_},
			this.taxRateCombox,
			this.exemplarInvoiceField,
			{xtype:'label',text: '形式发票',x:1055,y:35,style:"font-size:9pt;text-align:left;width:85px"},
			//3
			{xtype:'label',text: '紧急程度:',x:0,y:65,style:this.lableStyle_},
			new Ext.ffc.UrgentLevelCombox({x:90,y:63, width:170,disabled : this.isReadOnly}),
			{xtype:'label',text: '客户电话',x:250,y:65,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerPhone',x:340,y:63,width:170},
			{xtype:'label',text: '制 单 人:',x:510,y:65,style:this.lableStyle_},
			{xtype:'textfield', name: 'editorName',readOnly : true,x:600,y:63,width:170},
			{xtype:'label',text: '税　　金:',x:770,y:65,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:860,y:63,width:170},
			//4
			{xtype:'label',text: '报价状态:',x:0,y:95,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:93, width:170,name: 'status'},
			//new Ext.ffc.ContractStatusComboBox({readOnly : true,disabled:true,x:90,y:93,width:170}),
			{xtype:'label',text: '客户传真:',x:250,y:95,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerFax',x:340,y:93,width:170},
			{xtype:'label',text: '币　别:',x:510,y:95,style:this.lableStyle_},
			this.currCombox,
			{xtype:'label',text: '税价合计:',x:770,y:95,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'totalMoney',readOnly : true,x:860,y:93,width:170},
			//5
			{xtype:'label',text: '报价有效期:始',x:0,y:125,style:this.lableStyle_},
			this.startDateField,
			{xtype:'label',text: '至:',x:250,y:125,style:this.lableStyle_},
			this.endDateField,
			{xtype:'label',text: '整单折扣(%):',x:770,y:125,style:this.lableStyle_},
			{xtype:'numberfield',name: 'overallRebate',emptyText : 0, readOnly : this.isReadOnly,
                   		allowDecimals : false,
                   		allowNegative : false,
                   		decimalPrecision : 0,
						minValue : 0,
						maxValue : 100,
						x:860,y:123,width:170,
                   		validator : function(_value) {
			           		if(_value >= 0 && _value <= 100) {
			           			return true;
			           		} else {
			           			return false;
			           		}
			           }
                   },
             //6
             {xtype:'label',text: '付款条件:',x:0,y:155,style:this.lableStyle_},
             this.paymentCombox,
             {xtype:'label',text: '最终金额:',x:770,y:155,style:this.lableStyle_},
             {xtype:'numberfield', name: 'finalMoney', allowBlank : false, x:860,y:153,width:170},
             //7
             {xtype:'label',text: '交货方式:',x:0,y:185,style:this.lableStyle_},
             
             //{xtype:'textfield' ,name: 'deliveryType', width : 680, readOnly : this.isReadOnly, allowBlank : false,x:90,y:183},
             new Ext.ffc.DeliveryTypeComboBox({width : 680, readOnly : this.isReadOnly, allowBlank : false,x:90,y:183}),
            /* {xtype:'label',text: '是否预订:',x:770,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.checkBox,*/
             //8
             {xtype:'label',text: '备注:',x:0,y:215,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:213, width : 680},
             {xtype:'label',text: '预计转正日期:',x:770,y:215,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.willFormalDateField,
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true},
             this.currIdField 
		];
		
		Quomanager.QuotationForm.superclass.constructor.call(this, {
			width : 1000,
            labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
            frame:true,labelWidth:75,monitorValid:false,
            layout : 'absolute',
            items : config
		})
	},
	
	changeStatus : function(value) {
		switch (value) {
			case 0 :
			return "编制";
			break;
			case 1 :
			return "待审批";
			break;
			case 2 :
			return "审批通过";
			break;
			case 3 :
			return "审批退回";
			break;
			case 4 :
			return "提交合同";
			break;
			case 5 :
			return "已经生成合同";
			break;
			default :
				return "编制";
		}
	},
	
	/**
	 * 设置表单的值
	 * @param {} _r
	 */
	setValues : function(_r) {
		Ext.Ajax.request({
			url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
			params: { currencyName: _r.data['currencyName'], currencyId : _r.data['currency']},
			success : function(response) {
				var record = Ext.decode(response.responseText);
				if(!Ext.isEmpty(record))
					this.currCombox.curRate = record.rate;							   	
			},scope : this
		});
		_r.set('status', this.changeStatus(_r.data['status']));
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		var _overallRebate = this.getValues().data['overallRebate'];
		
		var startDate = this.startDateField.getValue();
		var endDate = this.endDateField.getValue();
		
		/*if(this.checkBox.getValue()) {
			if(this.willFormalDateField.getValue() == "") {
				Ext.Msg.show({
					title:'信息提示',
					msg: '预计转正日期不允许为空！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return false;
			}
		}*/
		
		if(!Ext.isEmpty(startDate) && !Ext.isEmpty(endDate)) {
			if(startDate.format("Y-m-d") > endDate.format("Y-m-d")) {
				return false;
			}
		}
		if(_overallRebate < 0 || _overallRebate > 100)
			return false;
		return this.getForm().isValid();
	}
})

Quomanager.ReserveQuotationForm = Ext.extend(Ext.FormPanel, {
	isReadOnly : false,
	currCombox : null,
	sallerCombox : null,
	taxRateCombox : null,
	paymentCombox : null,
	quoDateField : null,
	startDateField : null,
	endDateField : null,
	currIdField : null,
	willFormalDateField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.currIdField = new Ext.form.TextField({
			xtype:'hidden',fieldLabel: 'currency',name: 'currency',hidden : true, hideLabel : true
		})
		this.currCombox = new CurrencyCombox({disabled : this.isReadOnly,x:600,y:93,width:170});
		this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,width:170,x:860,y:33,listeners:{"change":taxRateComboxChange}});
		this.sallerCombox = new SallerCombox({disabled : this.isReadOnly,x:600,y:3,width:170});
		this.paymentCombox = new Ext.zhj.PaymentConditionComboBox({width : 680, allowBlank : false,disabled : this.isReadOnly, x:90,y:213,hidden : true, hideLabel : true});
		this.quoDateField = new Ext.form.DateField({
			fieldLabel: '报价日期', format:'Y-m-d',name: 'quotationDate' ,x:90,y:33, width:170, format:'Y-m-d', 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
		})
		this.startDateField = new Ext.form.DateField({
			fieldLabel: '报价有效期', format:'Y-m-d',name: 'validStartDate', format:'Y-m-d', x:860,y:213, width:170, 
			emptyText:'', allowBlank : true, blankText:'该项为必填项!',disabled : this.isReadOnly,hidden : true, hideLabel : true,
			invalidText : '报价有效期开始日期不能晚于结束日期',value:new Date()
		})
		
		this.endDateField = new Ext.form.DateField({
			fieldLabel: '至', format:'Y-m-d',name: 'validEndDate', format:'Y-m-d', x:340,y:123, width:170, 
			emptyText:'', allowBlank : true, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '报价有效期结束日期不能早于开始日期',hidden : true, hideLabel : true
		})
		
		//是否预订
		/*this.checkBox = new Ext.form.Checkbox({
			xtype:'textfield' ,name: 'willOrderExpected', inputValue : 1, 
			width : 170, readOnly : this.isReadOnly, allowBlank : false,x:790,y:183,
			disabled : this.isReadOnly,hidden : true, hideLabel : true
		})*/
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:90,y:123, width:170,validationEvent : false,disabled : this.isReadOnly
		});
		
		this.exemplarInvoiceField = new Ext.form.Checkbox({
			name: 'exemplarInvoice', x:1035,y:35,disabled:true
		});
		this.currCombox.on({
			'change' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.currCombox.store.on({
			'load' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.startDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.endDateField.validate();
			},scope : this
		})
		
		this.endDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.startDateField.validate();
			},scope : this
		})
		
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var config = [
			//1
			{xtype:'label',text: '报价单号:',x:0,y:5,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:3, width:170,name: 'quotationCode'},
			{xtype:'label',text: '客户编号:',x:250,y:5,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerCode',readOnly : true,x:340,y:3,width:170},
			{xtype:'label',text: '卖方名称:',x:510,y:5,style:this.lableStyle_},
		    this.sallerCombox,
			{xtype:'label',text: '货品金额:',x:770,y:5,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'productMoney',readOnly : true,x:860,y:3,width:170},
			//2
			{xtype:'label',text: '预订日期:',x:0,y:35,style:this.lableStyle_},
			this.quoDateField,
			{xtype:'label',text: '客户联系人:',x:250,y:35,style:this.lableStyle_},
			{xtype:'textfield',  name: 'cusContactPerson',x:340,y:33,width:170},
			{xtype:'label',text: '我方负责人:',x:510,y:35,style:this.lableStyle_},
		    {xtype:'textfield',  name: 'userName',readOnly : true,x:600,y:33,width:170},
			{xtype:'label',text: '税率:',x:770,y:35,style:this.lableStyle_},
			this.taxRateCombox,
			this.exemplarInvoiceField,
			{xtype:'label',text: '形式发票',x:1055,y:35,style:"font-size:9pt;text-align:left;width:85px"},
			//3
			{xtype:'label',text: '紧急程度:',x:0,y:65,style:this.lableStyle_},
			new Ext.ffc.UrgentLevelCombox({x:90,y:63, width:170,disabled : this.isReadOnly}),
			{xtype:'label',text: '客户电话',x:250,y:65,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerPhone',x:340,y:63,width:170},
			{xtype:'label',text: '制 单 人:',x:510,y:65,style:this.lableStyle_},
			{xtype:'textfield', name: 'editorName',readOnly : true,x:600,y:63,width:170},
			{xtype:'label',text: '税　　金:',x:770,y:65,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:860,y:63,width:170},
						
			//4
			{xtype:'label',text: '报价状态:',x:0,y:95,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:93, width:170,name: 'status'},
			//new Ext.ffc.ContractStatusComboBox({readOnly : true,disabled:true,x:90,y:93,width:170}),
			{xtype:'label',text: '客户传真:',x:250,y:95,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerFax',x:340,y:93,width:170},
			{xtype:'label',text: '币　别:',x:510,y:95,style:this.lableStyle_},
			this.currCombox,
			{xtype:'label',text: '税价合计:',x:770,y:95,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'totalMoney',readOnly : true,x:860,y:93,width:170},
			//5
			{xtype:'label',text: '报价有效期:始',x:770,y:215,style:this.lableStyle_,hidden : true, hideLabel : true},
			this.startDateField,
			{xtype:'label',text: '至:',x:250,y:125,style:this.lableStyle_,hidden : true, hideLabel : true},
			this.endDateField,
			{xtype:'label',text: '整单折扣(%):',x:770,y:125,style:this.lableStyle_,hidden : true, hideLabel : true},
			{xtype:'numberfield',name: 'overallRebate',emptyText : 0, readOnly : this.isReadOnly,
                   		allowDecimals : false,
                   		allowNegative : false,
                   		decimalPrecision : 0,
						minValue : 0,
						maxValue : 100,hidden : true, hideLabel : true,
						x:860,y:123,width:170,
                   		validator : function(_value) {
			           		if(_value >= 0 && _value <= 100) {
			           			return true;
			           		} else {
			           			return false;
			           		}
			           }
                   },
             //6
             {xtype:'label',text: '付款条件:',x:0,y:215,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.paymentCombox,
             {xtype:'label',text: '最终金额:',x:770,y:155,style:this.lableStyle_,hidden : true, hideLabel : true},
             {xtype:'numberfield', name: 'finalMoney', allowBlank : true, x:860,y:153,width:170,hidden : true, hideLabel : true},
             //7
             {xtype:'label',text: '交货方式:',x:0,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             {xtype:'textfield' ,name: 'deliveryType', width : 680, readOnly : this.isReadOnly, allowBlank : true,x:90,y:183,hidden : true, hideLabel : true},
             /*{xtype:'label',text: '是否预订:',x:770,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.checkBox,*/
             //8
             {xtype:'label',text: '备注:',x:0,y:155,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:153, width : 680},
             {xtype:'label',text: '预计转合同日期:',x:-10,y:125,style:"font-size:9pt;text-align:right;width:95px"},
             this.willFormalDateField,
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true},
             this.currIdField
		];
		
		Quomanager.ReserveQuotationForm.superclass.constructor.call(this, {
			width : 1000,
            labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
            frame:true,labelWidth:75,monitorValid:false,
            layout : 'absolute',
            items : config
		})
	},
	
	changeStatus : function(value) {
		switch (value) {
			case 0 :
			return "编制";
			break;
			case 1 :
			return "待审批";
			break;
			case 2 :
			return "审批通过";
			break;
			case 3 :
			return "审批退回";
			break;
			case 4 :
			return "提交合同";
			break;
			case 5 :
			return "已经生成合同";
			break;
			default :
				return "编制";
		}
	},
	
	/**
	 * 设置表单的值
	 * @param {} _r
	 */
	setValues : function(_r) {
		Ext.Ajax.request({
			url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
			params: { currencyName: _r.data['currencyName'], currencyId : _r.data['currency']},
			success : function(response) {
				var record = Ext.decode(response.responseText);
				if(!Ext.isEmpty(record))
					this.currCombox.curRate = record.rate;							   	
			},scope : this
		});
		_r.set('status', this.changeStatus(_r.data['status']));
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		//var _overallRebate = this.getValues().data['overallRebate'];
		
		/*var startDate = this.startDateField.getValue();
		var endDate = this.endDateField.getValue();*/
		if(this.willFormalDateField.getValue() == "") {
			Ext.Msg.show({
				title:'信息提示',
				msg: '预计转合同日期不允许为空！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return false;
		}
		
		/*if(!Ext.isEmpty(startDate) && !Ext.isEmpty(endDate)) {
			if(startDate.format("Y-m-d") > endDate.format("Y-m-d")) {
				return false;
			}
		}*/
		/*if(_overallRebate < 0 || _overallRebate > 100)
			return false;*/
		return this.getForm().isValid();
	}
})

Quomanager.TestCutQuotationForm = Ext.extend(Ext.FormPanel, {
	isReadOnly : false,
	currCombox : null,
	sallerCombox : null,
	taxRateCombox : null,
	paymentCombox : null,
	quoDateField : null,
	startDateField : null,
	endDateField : null,
	currIdField : null,
	//checkBox : null,
	willFormalDateField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.currIdField = new Ext.form.TextField({
			xtype:'hidden',fieldLabel: 'currency',name: 'currency',hidden : true, hideLabel : true
		})
		this.currCombox = new CurrencyCombox({disabled : this.isReadOnly,x:600,y:93,width:170});
		this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,width:170,x:860,y:33,
			listeners:{"change":taxRateComboxChange}
		});
		this.sallerCombox = new SallerCombox({disabled : this.isReadOnly,x:600,y:3,width:170});
		this.paymentCombox = new Ext.zhj.PaymentConditionComboBox({width : 680,disabled : this.isReadOnly, x:90,y:213,hidden : true, hideLabel : true});
		this.quoDateField = new Ext.form.DateField({
			fieldLabel: '报价日期', format:'Y-m-d',name: 'quotationDate' ,x:90,y:33, width:170, format:'Y-m-d', 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
		})
		this.startDateField = new Ext.form.DateField({
			fieldLabel: '报价有效期', format:'Y-m-d',name: 'validStartDate', format:'Y-m-d', x:90,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '试刀开始日期不能晚于结束日期',value:new Date(),
			validator : function(_value) {
				var endDate = this.ownerCt.endDateField.getValue();
				if(!Ext.isEmpty(endDate)) {
					if(_value < endDate.format("Y-m-d")) {
						return true;
					} else { 
						return false;
					}
				} else {
					return true;
				}
			}
		})
		
		this.endDateField = new Ext.form.DateField({
			fieldLabel: '至', format:'Y-m-d',name: 'validEndDate', format:'Y-m-d', x:340,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '试刀结束日期不能早于开始日期',
			validator : function(_value) {
				var startDate = this.ownerCt.startDateField.getValue();
				if(!Ext.isEmpty(startDate)) {
					if(_value > startDate.format("Y-m-d")) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		})
		
		//是否预订
		/*this.checkBox = new Ext.form.Checkbox({
			xtype:'textfield' ,name: 'willOrderExpected', inputValue : 1, 
			width : 170, readOnly : this.isReadOnly, allowBlank : false,x:790,y:183,
			disabled : this.isReadOnly,hidden : true, hideLabel : true
		})*/
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:860,y:213, width:170,validationEvent : false,disabled : this.isReadOnly,hidden : true, hideLabel : true
		});
		
		this.exemplarInvoiceField = new Ext.form.Checkbox({
			name: 'exemplarInvoice', x:1035,y:35,disabled:true
		});
		
		this.currCombox.on({
			'change' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.currCombox.store.on({
			'load' : function() {
				this.currIdField.setValue(this.currCombox.curid);
			},scope : this
		})
		
		this.startDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.endDateField.validate();
			},scope : this
		})
		
		this.endDateField.on({
			'change' : function(_filed, _newValue, _oldValue) {
				this.startDateField.validate();
			},scope : this
		})
		
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var config = [
			//1
			{xtype:'label',text: '申请单号:',x:0,y:5,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:3, width:170,name: 'quotationCode'},
			{xtype:'label',text: '客户编号:',x:250,y:5,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerCode',readOnly : true,x:340,y:3,width:170},
			{xtype:'label',text: '卖方名称:',x:510,y:5,style:this.lableStyle_},
		    this.sallerCombox,
			{xtype:'label',text: '货品金额:',x:770,y:5,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'productMoney',readOnly : true,x:860,y:3,width:170},
			//2
			{xtype:'label',text: '申请日期:',x:0,y:35,style:this.lableStyle_},
			this.quoDateField,
			{xtype:'label',text: '客户联系人:',x:250,y:35,style:this.lableStyle_},
			{xtype:'textfield',  name: 'cusContactPerson',x:340,y:33,width:170},
			{xtype:'label',text: '我方负责人:',x:510,y:35,style:this.lableStyle_},
		    {xtype:'textfield',  name: 'userName',readOnly : true,x:600,y:33,width:170},
			{xtype:'label',text: '税率:',x:770,y:35,style:this.lableStyle_},
			this.taxRateCombox,
			this.exemplarInvoiceField,
			{xtype:'label',text: '形式发票',x:1055,y:35,style:"font-size:9pt;text-align:left;width:85px"},
			//3
			{xtype:'label',text: '紧急程度:',x:0,y:65,style:this.lableStyle_},
			new Ext.ffc.UrgentLevelCombox({x:90,y:63, width:170,disabled : this.isReadOnly}),
			{xtype:'label',text: '客户电话',x:250,y:65,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerPhone',x:340,y:63,width:170},
			{xtype:'label',text: '制 单 人:',x:510,y:65,style:this.lableStyle_},
			{xtype:'textfield', name: 'editorName',readOnly : true,x:600,y:63,width:170},
			{xtype:'label',text: '税　　金:',x:770,y:65,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:860,y:63,width:170},
			//4
			{xtype:'label',text: '报价状态:',x:0,y:95,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:93, width:170,name: 'status'},
			//new Ext.ffc.ContractStatusComboBox({readOnly : true,disabled:true,x:90,y:93,width:170}),
			{xtype:'label',text: '客户传真:',x:250,y:95,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerFax',x:340,y:93,width:170},
			{xtype:'label',text: '币　别:',x:510,y:95,style:this.lableStyle_},
			this.currCombox,
			{xtype:'label',text: '税价合计:',x:770,y:95,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'totalMoney',readOnly : true,x:860,y:93,width:170},
			//5
			{xtype:'label',text: '试刀开始日期:',x:-10,y:125,style:"font-size:9pt;text-align:right;width:95px"},
			this.startDateField,
			{xtype:'label',text: '试刀完成日期:',x:250,y:125,style:this.lableStyle_},
			this.endDateField,
			{xtype:'label',text: '整单折扣(%):',x:770,y:125,style:this.lableStyle_,hidden : true, hideLabel : true},
			{xtype:'numberfield',name: 'overallRebate',emptyText : 0, readOnly : this.isReadOnly,
                   		allowDecimals : false,
                   		allowNegative : false,
                   		decimalPrecision : 0,
						minValue : 0,
						maxValue : 100,hidden : true, hideLabel : true,
						x:860,y:123,width:170,
                   		validator : function(_value) {
			           		if(_value >= 0 && _value <= 100) {
			           			return true;
			           		} else {
			           			return false;
			           		}
			           }
                   },
             //6
             {xtype:'label',text: '付款条件:',x:0,y:215,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.paymentCombox,
             {xtype:'label',text: '最终金额:',x:770,y:155,style:this.lableStyle_,hidden : true, hideLabel : true},
             {xtype:'numberfield', name: 'finalMoney', allowBlank : true, x:860,y:153,width:170,hidden : true, hideLabel : true},
             //7
             {xtype:'label',text: '交货方式:',x:0,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             {xtype:'textfield' ,name: 'deliveryType', width : 680, readOnly : this.isReadOnly, allowBlank : true,x:90,y:183,hidden : true, hideLabel : true},
             /*{xtype:'label',text: '是否预订:',x:770,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.checkBox,*/
             //8
             {xtype:'label',text: '备注:',x:0,y:155,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:153, width : 680},
             {xtype:'label',text: '转合同日期:',x:770,y:215,style:"font-size:9pt;text-align:right;width:95px",hidden : true, hideLabel : true},
             this.willFormalDateField,
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true},
             this.currIdField
		];
		
		Quomanager.TestCutQuotationForm.superclass.constructor.call(this, {
			width : 1000,
            labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
            frame:true,labelWidth:75,monitorValid:false,
            layout : 'absolute',
            items : config
		})
	},
	
	changeStatus : function(value) {
		switch (value) {
			case 0 :
			return "编制";
			break;
			case 1 :
			return "待审批";
			break;
			case 2 :
			return "审批通过";
			break;
			case 3 :
			return "审批退回";
			break;
			case 4 :
			return "提交合同";
			break;
			case 5 :
			return "已经生成合同";
			break;
			default :
				return "编制";
		}
	},
	
	/**
	 * 设置表单的值
	 * @param {} _r
	 */
	setValues : function(_r) {
		Ext.Ajax.request({
			url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
			params: { currencyName: _r.data['currencyName'], currencyId : _r.data['currency']},
			success : function(response) {
				var record = Ext.decode(response.responseText);
				if(!Ext.isEmpty(record))
					this.currCombox.curRate = record.rate;							   	
			},scope : this
		});
		_r.set('status', this.changeStatus(_r.data['status']));
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		//var _overallRebate = this.getValues().data['overallRebate'];
		
		var startDate = this.startDateField.getValue();
		var endDate = this.endDateField.getValue();
		
		/*if(this.checkBox.getValue()) {
			if(this.willFormalDateField.getValue() == "") {
				Ext.Msg.show({
					title:'信息提示',
					msg: '预计转正日期不允许为空！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return false;
			}
		}*/
		
		if(!Ext.isEmpty(startDate) && !Ext.isEmpty(endDate)) {
			if(startDate.format("Y-m-d") > endDate.format("Y-m-d")) {
				return false;
			}
		}
		/*if(_overallRebate < 0 || _overallRebate > 100)
			return false;*/
		return this.getForm().isValid();
	}
})

Quomanager.onSlaveClick = function(_id) {
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : 1});
	slaveWindow.show();
}



Quomanager.ProductsTree = Ext.extend(Ext.tree.ColumnTree, {
	loaderUrl : null,
	customerRecord : null,
	ownerCt : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Quomanager.ProductsTree.superclass.constructor.call(this, {
			//el:'tree-ct',
	        //width:1050,
			height: 325,
			bodyStyle:'width:100%',
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        title: '产品信息',
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
	        }],
	        columns:[
	        	{header : '图标',width : 80,disEnableEdit : true},
	        	{header : '项目编号',width : 70,dataIndex : 'projectCode'}, 
	        	{header : '序号',width : 40,dataIndex : 'serialNumber',disEnableEdit : true},
	        	{header:'牌号',width:180,dataIndex:'brandCode',disEnableEdit:true},
	        	{header:'名称',width:100,dataIndex:'productName',disEnableEdit:true},
	        	{header:'数量',width:50,dataIndex:'amount',
	        		listeners : {
						'click' : function() {
							try{
								var e = arguments[0] || window.event;
								var obj = e.srcElement || e.target;
								if(obj.node.parentNode.id != 'root')
									e.cancelBubble = true;
							}catch(e){
								alert(e);
							}
						}
				    }},
	        	{header:'计量单位',width:80,dataIndex:'productUnit',disEnableEdit:true},
	        	{header:'单价',width:80,name : 'price',dataIndex: 'price',disEnableEdit:true},
	        	{header:'折扣(%)',width:60,dataIndex:'rebate'},
	        	{header:'净价',width:80,dataIndex:'netPrice'},
	        	{header:'金额',width:80,dataIndex:'money',disEnableEdit:true},
	        	{header:'含税净价',width:80,dataIndex:'taxNetPrice',disEnableEdit:true},
	        	{header:'含税金额',width:80,dataIndex:'taxMoney',disEnableEdit:true},
	        	{header:'交货期限',width:80,dataIndex:'deliveryDate',disEnableEdit:true,
	        		listeners : {
						'click' : function() {
							try{
								var e = arguments[0] || window.event;
								var obj = e.srcElement || e.target;
								var dataf = new Ext.form.DateField({format:'Y-m-d'});
								var menu = new (Ext.menu.DateMenu)({hideOnClick: false});
									menu["on"]("select", function(a,b){
										var bb = dataf.formatDate(dataf.parseDate(b));
										obj.innerHTML = bb;
										obj.node.cols["deliveryDate"] = bb;
										obj.node.attributes["deliveryDate"] = bb;
										menu.hide();
									}, obj);
									menu.show(obj, "tl-bl");
							}catch(e){
								alert(e);
							}
						}
					}
	        	},
	        	{header:'客户确认方案图',width:100,disEnableEdit:true,dataIndex:'slaveFile', renderer : function(colValue, node, data) {
	        		if(data.parentToolsId != 'root')
	        			return;
	        		if(colValue > 0) {
	        			var id = data.toolsId
	        			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + id + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
						return str;
	        		}
	        	}},
	        	{header:'价格变动',width:0,dataIndex:'priceChange', hidden : true},
	        	{header:'品牌', width:80,dataIndex:'productBrand',disEnableEdit:true},
	        	{header:'备注1', width:80,dataIndex:'memo'},
	        	{header:'备注2',width:80,dataIndex:'workshop'},
	        	{header:'备注3',width:80,dataIndex:'reportCode'}
	        ],
	
	        loader: new Ext.tree.TreeLoader({
	            dataUrl: this.loaderUrl == null ? '#' : this.loaderUrl,
	            autoLoad : false,
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        }),
	        
	        listeners : {
	        	'render' : function() {
	        		var loa = this.getLoader();
					
					loa.on('load', function() {
						var _rootNode = this.getRootNode();
						_rootNode.eachChild(function(curNode) {
							if(curNode.attributes.priceChange == 1) {
								//curNode.ui.addRowNodeClass('red-row');
								curNode.cols['priceChange'] = 1;
								curNode.attributes['priceChange'] = 1;
								curNode.ui.setInnerHTMLValue('priceChange',1);
								
								curNode.ui.setColumnsClass('rebate','blue-column-font');
								curNode.ui.setColumnsClass('netPrice','blue-column-font');
								curNode.ui.setColumnsClass('money','blue-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','blue-column-font');
								curNode.ui.setColumnsClass('taxMoney','blue-column-font');
							} else if(curNode.attributes.priceChange == 2) {
								curNode.ui.setColumnsClass('netPrice','red-column-font');
								curNode.ui.setColumnsClass('money','red-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','red-column-font');
								curNode.ui.setColumnsClass('taxMoney','red-column-font');
							} else if(curNode.attributes.priceChange == 3) {
								curNode.ui.setColumnsClass('rebate','blue-column-font');
								curNode.ui.setColumnsClass('netPrice','red-column-font');
								curNode.ui.setColumnsClass('money','red-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','red-column-font');
								curNode.ui.setColumnsClass('taxMoney','red-column-font');
							} else if(curNode.attributes.priceChange == 4) {
								curNode.ui.setColumnsClass('rebate','green-column-font');
								curNode.ui.setColumnsClass('netPrice','green-column-font');
								curNode.ui.setColumnsClass('money','green-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','green-column-font');
								curNode.ui.setColumnsClass('taxMoney','green-column-font');
							} else {
								curNode.cols['priceChange'] = 0;
								curNode.attributes['priceChange'] = 0;
								curNode.ui.setInnerHTMLValue('priceChange',0);
							}
						})
					},this)
	        	}
	        }
		})
		
		this.addEvents('ondelete');
		this.addEvents('oninsertdelete');
		this.addEvents('onaddnode');
	},
	
	validator : function() {
		var flag = true;
		var rootNode = this.getRootNode();
		rootNode.eachChild(function(_node) {
			var _amount = _node.attributes.amount;
			var _rebate = _node.attributes.rebate;
			var _deliveryDate = _node.attributes.deliveryDate;
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
	
	onSetDeliveryDate : function(_btn, e) {
		var rootNode = this.getRootNode();
		var firstChild = rootNode.firstChild;
		if(firstChild == null) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请添加报价产品后，再设定交货日期！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		} else if(!Ext.isEmpty(firstChild.attributes.deliveryDate)){
			var devDate = firstChild.attributes.deliveryDate;
			rootNode.eachChild(function(curNode) {
				curNode.cols['deliveryDate'] = devDate;
				curNode.attributes['deliveryDate'] = devDate;
				curNode.ui.setInnerHTMLValue('deliveryDate',devDate);
			})
		} else {
			var dataf = new Ext.form.DateField({format:'Y-m-d'});
			var menu = new Ext.menu.DateMenu();
			menu["on"]("select", function(a,b){
				var bb = dataf.formatDate(dataf.parseDate(b));
				rootNode.eachChild(function(curNode) {
					curNode.cols['deliveryDate'] = bb;
					curNode.attributes['deliveryDate'] = bb;
					curNode.ui.setInnerHTMLValue('deliveryDate',bb);
				})
				menu.hide();
			});
			menu.show(_btn.getEl());
		}
	},
	
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
			var _curRate = this.ownerCt.ownerCt.northPanel.quotationForm.currCombox.curRate;
			var _price = _newNode.price * (1/_curRate);
			
			_newNode.price = _price.toFixed(2);
			_newNode.projectCode = this.getRootNode().childNodes.length + 1;
			_newNode.serialNumber = _newNode.projectCode;
			this.appendChild(_newNode);
			//this.getTopToolbar().items.get(4).fireEvent('click');
			var _node = this.getRootNode().lastChild;
			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + _node.attributes.toolsId + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
			if(_node.attributes.slaveFile > 0) {
				_node.cols['slaveFile'] = str;
				_node.attributes['slaveFile'] = str;
				_node.ui.setInnerHTMLValue('slaveFile',str);
			}
			//Ext.ffc.util.debug(this.getRootNode().lastChild.'查看');
			this.fireEvent('onaddnode', this, _newNode);
		},this)
		proListWindow.show();
	},
	
	onDeleteSubmit : function() {
		var selectedNode = this.getSelectionModel().getSelectedNode();
	    if(!selectedNode) {
	        Ext.Msg.show({
				title:'信息提示',
				msg: '请选择一条记录进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
	    } else {
	        if(selectedNode.parentNode.id != 'root') {
	        	Ext.Msg.show({
					title:'信息提示',
					msg: '请选择根结点进行删除操作！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return;
	        }
	    }
	        		
	    //如果删除的节点不在数据库中直接删除，否则先删除数据库的记录，再删除前台数据。
	    if(typeof(selectedNode.attributes.quotationInforId)=="undefined") {
       		selectedNode.remove();
        	this.fireEvent('ondelete');
	    } else {
	        paramStr = selectedNode.toJsonString();
	        
	        selectedNode.remove();
	        var _node = {id : selectedNode.id};
			this.fireEvent('oninsertdelete',this, _node);
	     }
	},
	
	onProjectSortSubmit : function() {
		var	rootNode = this.getRootNode();
		if(!rootNode.childNodes) return ;
								
		var treeLoader = this.getLoader() 
								
		var temp = {"children":[]};
		var ffNodes = rootNode.childNodes;
		for(var i = 0 ;i < ffNodes.length;i++){
									
			temp.children.push(Ext.tree.toNewTreeNode(ffNodes[i].attributes,{'uiProvider':'col'},true));
		}
								
		var children = temp.children;
		temp.children = children.sort(function(f,s){
			if(f.projectCode * 1 > s.projectCode * 1){
				return 1;
			} else if(f.projectCode * 1 == s.projectCode * 1) {
				if(f.serialNumber * 1 > s.serialNumber * 1)
					return 1;
			} 
			return -1;
		});
		
		
		var rowNum = 0;
		var oldNum = 0;
		for(var i = 0;i < temp.children.length;i++){
			if(temp.children[i].projectCode != oldNum){
				rowNum ++;
			}
			oldNum = temp.children[i].projectCode;
			temp.children[i].projectCode = rowNum;
		}
		
		if(rootNode.childNodes){
			for(var i = rootNode.childNodes.length-1;i >= 0 ;i--){
				rootNode.removeChild(rootNode.childNodes[i]);
			}
		}
		var serialNumber = 1;
								
		for(var i = 0; i < temp.children.length;i++){
			temp.children[i]["serialNumber"] = serialNumber++;
			//temp.children[i]["projectCode"] = serialNumber++;
			var tn = treeLoader.createNode(temp.children[i]);
			rootNode.appendChild(tn); 
			if(temp.children[i].priceChange == 1){
				//tn.ui.addRowNodeClass('red-row');
				tn.ui.setColumnsClass('rebate','blue-column-font');
				tn.ui.setColumnsClass('netPrice','blue-column-font');
				tn.ui.setColumnsClass('money','blue-column-font');
				tn.ui.setColumnsClass('taxNetPrice','blue-column-font');
				tn.ui.setColumnsClass('taxMoney','blue-column-font');
			} else if(temp.children[i].priceChange == 2) {
				tn.ui.setColumnsClass('netPrice','red-column-font');
				tn.ui.setColumnsClass('money','red-column-font');
				tn.ui.setColumnsClass('taxNetPrice','red-column-font');
				tn.ui.setColumnsClass('taxMoney','red-column-font');
			} else if(temp.children[i].priceChange == 3) {
				tn.ui.setColumnsClass('rebate','blue-column-font');
				tn.ui.setColumnsClass('netPrice','red-column-font');
				tn.ui.setColumnsClass('money','red-column-font');
				tn.ui.setColumnsClass('taxNetPrice','red-column-font');
				tn.ui.setColumnsClass('taxMoney','red-column-font');
			} else if(temp.children[i].priceChange == 4) {
				tn.ui.setColumnsClass('rebate','green-column-font');
				tn.ui.setColumnsClass('netPrice','green-column-font');
				tn.ui.setColumnsClass('money','green-column-font');
				tn.ui.setColumnsClass('taxNetPrice','green-column-font');
				tn.ui.setColumnsClass('taxMoney','green-column-font');
			} 
		}
	},
	
	appendChild : function(_newNode) {
		this.getRootNode().appendChild(_newNode);
	}
})

Quomanager.NorthPanel = Ext.extend(Ext.Panel, {
	quotationForm : null,
	isReadOnly : false,
	quoType : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.quotationForm = this.buildFormPanel(this.quoType);
		var _height = 280;
		if(this.quoType == 3 || this.quoType == 4){
			_height = 220;
		}
			
		Quomanager.NorthPanel.superclass.constructor.call(this, {
			region: 'north',
            iconCls:'icon-grid',
            title: this.quoType == 4 ? '试刀申请信息' : '报价单信息',
            split: true,
            width: 200,
            layout : 'fit',
            height : _height,
            minSize: 175,
            maxSize: 575,
            collapsible: true,
            margins: '5 5 5 5',
            items : [this.quotationForm]
		})
	},
	
	buildFormPanel : function(_quoType) {
		switch (_quoType) {
			case 0 :
			return new Quomanager.QuotationForm({isReadOnly : this.isReadOnly});
			break;
			case 3 :
			return new Quomanager.ReserveQuotationForm({isReadOnly : this.isReadOnly});
			break;
			case 4 :
			return new Quomanager.TestCutQuotationForm({isReadOnly : this.isReadOnly});
		}
	}
})

Quomanager.CenterPanel = Ext.extend(Ext.Panel, {
	loaderUrl : null,
	productTree : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg  == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.productTree = new Quomanager.ProductsTree({
			loaderUrl : this.loaderUrl,
			customerRecord : this.customerRecord
		});
		Quomanager.CenterPanel.superclass.constructor.call(this, {
			region: 'center',
            //contentEl: 'grid',
            split: true,
            height: 100,
            minSize: 100,
            layout : 'fit',
            maxSize: 200,
            collapsible: true,
            //title: 'South',
            margins: '-5 5 5 5',
            items : [this.productTree]	
		})
	}
})

/**
 * 报价单编制窗口
 * @class Quomanager.MangerWindow
 * @extends Ext.Window
 */
Quomanager.MangerWindow = Ext.extend(Ext.Window, {
	isReadOnly : false,
	northPanel : null,
	centerPanel : null,
	loaderUrl : null,
	_treeEdit : null,
	customerRecord : null,
	insertDeletes : null,
	loadMarsk : null,
	quoType : null,
	_grid : null,
	isDetail : false,//是否详细窗口
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new Quomanager.NorthPanel({isReadOnly : this.isReadOnly, quoType : this.quoType});
		this.centerPanel = new Quomanager.CenterPanel({
			loaderUrl : this.loaderUrl,
			customerRecord : this.customerRecord
		});
		//报价产品Grid开始
		this._grid = new QuotationManager.productsGrid({
			loaderUrl : this.loaderUrl,
			customerRecord : this.customerRecord,
			quotationForm : this.northPanel.quotationForm,
			quoType : this.quoType,
			isDetail : this.isDetail,
			isEditor : this.isEditor
		})
		//报价产品Grid结束
		/*var sm = this.centerPanel.productTree.getSelectionModel()
		if(sm){
			sm.on('beforeselect',function(thisObj, bNode, aNode){
				var parentId = bNode.parentNode.id;
				if(parentId != 'root'){
				    return false;
				}
				return true;
			});
		}
		
		this._treeEdit = new Ext.tree.ColumnTreeEditor(this.centerPanel.productTree,{
			completeOnEnter: true,
			autosize: true,
			ignoreNoChange: true,
			listeners : {
				specialkey : Ext.ffc.gridTreeEditorkeyMove.moveCallBack
		    }
		});*/
		this.insertDeletes = [];
		Quomanager.MangerWindow.superclass.constructor.call(this, {
			title: this['title'],  
			width:Ext.getBody().getWidth()-100,  
			height:600,  
			plain:true,
			closable : true,
			//modal : true,
			maximizable : true,
			constrainHeader : true,
			closeAction:'hide',
			layout:"border",
			listeners : {
				'render' : function() {
					fn : this.handlerShow();
					if(this.setAuditContent)
					   this.setAuditContent();
				},scope : this
			},
			items : [this.northPanel, this._grid]
		})
		this.addEvents('onsubmitsuccess');
	},
	
	ajaxPost : function(postUrl, parmStr1, parmStr2, _ids) {
		var isAdd = false;
		if(Ext.isEmpty(_ids)) {
			isAdd = true;
			_ids = Ext.encode({});
		}
		//alert(parmStr2);alert(this.quoType);return;
		Ext.Ajax.request({
			url: postUrl,
			params: { quoProduct: parmStr1, quoForm : parmStr2, ids : _ids, quoType : this.quoType },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
											   	
				if(responseArray.success == true){
					Ext.Msg.show({
						title:'成功提示',
						msg: responseArray.msg,
						buttons: Ext.Msg.OK,
						icon: Ext.MessageBox.INFO
					});
					
					if(isAdd) {
						this.hide();
					} else {
						/*var rootNode = this.centerPanel.productTree.getRootNode();
						rootNode.reload();*/
						this._grid.reload();
					}
					this.fireEvent('onsubmitsuccess');
					this.loadMarsk.hide();
					this.insertDeletes = [];
				} else {
					this.loadMarsk.hide();
					Ext.Msg.show({
						title:'错误提示',
						msg: responseArray.msg,
						buttons: Ext.Msg.OK,
						icon: Ext.MessageBox.ERROR
					});
				} 
			},scope : this
		});
	},
	
	handlerShow : function() {
		var _qTree = this.centerPanel.productTree;
		var _quotationForm = this.northPanel.quotationForm;
		var overallRebate = _quotationForm.findByType('numberfield')[3];
		
		//整单折扣处理
		overallRebate.on("change", function() {
			var _tMoney = _quotationForm.findByType('numberfield')[2].getValue();
			var _finRebat = this.getValue();
			if(_finRebat == "") {
				_finRebat = 0;
			}
			var finMoney = parseFloat(_tMoney * (100-_finRebat)/100).toFixed(2);
						
			_quotationForm.findByType('numberfield')[4].setValue(finMoney);
		})
					
		var _taxCombox = _quotationForm.taxRateCombox;//税率
		var _currCombox = _quotationForm.currCombox;//币别
		var _ed = null;
		var _value = null;
		var _oldValue = null;
		
		//税率处理
		_taxCombox.on("change", function() {
			/*var rootNode = _qTree.getRootNode();
			var editFiled = this._treeEdit.ffcFiled;
			rootNode.eachChild(function(curNode) {
				editFiled.editNode = curNode;
				this._treeEdit.fireEvent('fccAfterUpdateNodeEvent', this,editFiled,_value,_oldValue);
			},this)*/
			
			var _store = this._grid.store;
			_store.each(function(record) {
				this._grid.calculate4TaxRate(record);
			}, this)
			
		},this)
		
		//汇率处理
		_currCombox.on("change", function(_field, _newValue, oldValue) {
			/*var rootNode = _qTree.getRootNode();
			var editFiled = this._treeEdit.ffcFiled;
			rootNode.eachChild(function(curNode) {
				editFiled.editNode = curNode;
				this._treeEdit.fireEvent('fccAfterUpdateNodeEvent', this, editFiled, _value, _oldValue, _field.rate);
			},this)*/
			
			var _store = this._grid.store;
			_store.each(function(record) {
				this._grid.calculate(record,_field.rate);
			}, this)
		},this)
		
		//报价单Grid产品事件
		this._grid.on({
			'onaddnode' : function(_grid, _record) {
				//导出预订报价单产品添加到普通报价单后，重新计算报价单产品金额
				this._grid.calculate4quo();
			},
			'oninsertdelete' : function(grid, _node) {
				this.insertDeletes.push(_node);
			},scope : this
 		})
		
		/*//新结点加入处理
		_qTree.on('onaddnode', function(_tree, _node) {
			var editFiled = this._treeEdit.ffcFiled;
			editFiled.editNode = _qTree.getNodeById(_node.id);
			this._treeEdit.fireEvent('fccAfterUpdateNodeEvent', this,editFiled,_node.amount,_oldValue);
		},this)
		//报价产品删除
		_qTree.on('ondelete', function() {
			var _taxRate = _taxCombox.getValue();//税率
			var rootNode = _qTree.getRootNode();
			//_quForm = this.northPanel.quotationForm;
			calculateOnDelete(rootNode,_taxRate, _quotationForm);
		},this)
		
		_qTree.on('oninsertdelete', function(_tree, _node) {
			var _taxRate = _taxCombox.getValue();//税率
			var rootNode = _qTree.getRootNode();
			calculateOnDelete(rootNode,_taxRate, _quotationForm);
			this.insertDeletes.push(_node);
		},this)*/
		
		//复制报价单产品加载后触发事件
		if(this.isCopy) {
			/*_qTree.getLoader().on('load', function() {
				var _taxRate = _taxCombox.getValue();//税率
				var rootNode = _qTree.getRootNode();
				calculateOnDelete(rootNode,_taxRate, _quotationForm);
			})*/
			
			this._grid.store.on({
				'load' : function() {
					this._grid.calculate4quo();
				},scope : this
			})
		}
		
		/*this._treeEdit.addEvents("fccAfterUpdateNodeEvent");
						    
		this._treeEdit.on("fccAfterUpdateNodeEvent",function(obj,ed,value,oldValue,_rate){
			if(obj.editColIndex == 'projectCode') return;
			if(obj.editColIndex == 'memo') return;
			if(obj.editColIndex == 'workshop') return;
			if(obj.editColIndex == 'reportCode') return;
			_ed = ed; _value = value; _oldValue = oldValue;
			var _taxRate = _taxCombox.getValue();
			var rootNode = _qTree.getRootNode();
			_quForm = this.northPanel.quotationForm;
			calculate(rootNode, _taxRate, _quForm, obj,ed,value,oldValue,_rate);
		},this);*/
	}
})

/**
 * 报价单添加窗口
 * @class Quomanager.AddWindow
 * @extends Quomanager.MangerWindow
 */
Quomanager.AddWindow = Ext.extend(Quomanager.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Quomanager.AddWindow.superclass.constructor.call(this, {
			title : this.quoType == 4 ? '添加试刀申请' : '添加报价单',
			modal : true,
			buttons : [{
				text : "保存",
				handler : function() {
					fn : this.onAddSubmit();		 		
				},
				scope : this
			  },{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}]
		})
	},
	
	onAddSubmit : function() {
		var _store = this._grid.store;
		/*var rootCode = this.centerPanel.productTree.getRootNode();*/
		try {
			//this.centerPanel.productTree.validator();
			this._grid.validator();
		} catch(_e) {
			Ext.Msg.show({
				title:'信息提示',
				msg: _e.message,
				buttons: Ext.Msg.OK,
				width : 200,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(!this.northPanel.quotationForm.validator())
			return;
		var record = new Ext.data.Record(this.northPanel.quotationForm.getForm().getValues());
		record.set('quotationType', this.quoType);
		if(record.get('exemplarInvoice') == 'on'){
			record.set('exemplarInvoice','1');
		}else{
			record.set('exemplarInvoice','0');
		}
		var _tempArr = [];
	/*	rootCode.eachChild(function(curNode) {
			_tempArr.push(Ext.tree.toNewTreeNode(curNode.attributes,{},false))
		})*/
		_store.each(function(_record) {
			_tempArr.push(_record.data)
		}) 
		if(_tempArr.length == 0) {
			Ext.Msg.show({
				title:'信息提示',
				msg: this.quoType == 4 ? '请添加试刀产品' : '请添加报价产品！',
				buttons: Ext.Msg.OK,
				width : 200,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		this.loadMarsk = new Ext.LoadMask(this.getEl(), {
		     msg : '正在保存数据，请稍候。。。。。。',
		     removeMask : true// 完成后移除
		 });
		this.loadMarsk .show(); //显示
		var parmStr1 = Ext.encode(_tempArr);
		var parmStr2 = Ext.util.JSON.encode(record);
		//alert(Ext.encode(this.northPanel.quotationForm.getValues().data));return;
		//alert(parmStr1);return;
		this.ajaxPost(PATH + '/generalQuo/addGeneralQuoAction.do', parmStr1, parmStr2);
	}
})

/**
 * 报价单修改窗口
 * @class Quomanager.ModifyWindow
 * @extends Quomanager.MangerWindow
 */
Quomanager.ModifyWindow = Ext.extend(Quomanager.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Quomanager.ModifyWindow.superclass.constructor.call(this, {
			title : this.quoType == 4 ? '修改试刀申请' : '修改报价单',
			loaderUrl : Quomanager.ModifyWindow.LOADER_URL,
			buttons : [{
				text : "保存",
				handler : function() {
					fn : this.onAddSubmit();		 		
				},
				scope : this
			  },{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}]
		})
	},
	
	onAddSubmit : function() {
		if(!this.northPanel.quotationForm.validator())
			return;
		var _store = this._grid.store;
		/*var rootCode = this.centerPanel.productTree.getRootNode();*/
		try {
			//this.centerPanel.productTree.validator();
			  this._grid.validator();
		} catch(_e) {
			Ext.Msg.show({
				title:'信息提示',
				msg: _e.message,
				buttons: Ext.Msg.OK,
				width : 200,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		this.loadMarsk = new Ext.LoadMask(this.getEl(), {
		     msg : '正在修改数据，请稍候。。。。。。',
		     removeMask : true// 完成后移除
		 });
		this.loadMarsk .show(); //显示
		var record = new Ext.data.Record(this.northPanel.quotationForm.getForm().getValues());
		if(record.get('exemplarInvoice') == 'on'){
			record.set('exemplarInvoice','1');
		}else{
			record.set('exemplarInvoice','0');
		}
		var _tempArr = [];
		/*rootCode.eachChild(function(curNode) {
			_tempArr.push(Ext.tree.toNewTreeNode(curNode.attributes,{},true,['quotationProjectSortId']))
		})*/
			
		_store.each(function(_record) {
			_tempArr.push(_record.data)
		})
		
		var parmStr1 = Ext.encode(_tempArr);//alert(parmStr1);return;
		/*if(record.get('willOrderExpected') == undefined)
		{
			record.set('willOrderExpected',0);
		}*/
		
		var parmStr2 = Ext.util.JSON.encode(record);//alert(parmStr2);return;
		var _insertDeletes = this.insertDeletes;
		var _ids = Ext.encode(_insertDeletes)
		this.ajaxPost(PATH + '/generalQuo/updateQuoAction.do', parmStr1, parmStr2, _ids);
		
		
	}
})

/**
 * 报价单明细窗口
 * @class Quomanager.DetailWindow
 * @extends Quomanager.MangerWindow
 */
Quomanager.DetailWindow = Ext.extend(Quomanager.MangerWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var _url = Quomanager.DetailWindow.LOADER_URL;
		if(this.quoType == 3) {
			_url = Quomanager.DetailWindow.LOADER_RESERVE_URL;
		} else if(this.quoType == 4) {
			_url = Quomanager.DetailWindow.LOADER_TESTCUT_URL;
		}
		Quomanager.DetailWindow.superclass.constructor.call(this, {
			loaderUrl : _url,
			isReadOnly : true,
			isDetail : true,
			header:true,
			bbar :[{xtype:'label',html : "审批内容:<font color = 'red'></font>,<font color='green'></font>"}],
			buttons : [{
				text : "关闭",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			setAuditContent:function(){
				      var _this = this;
		              var busId = _this._id;
			    	  	Ext.Ajax.request({
						   url: PATH + '/manage/audit/auditContentAction.do',
						   params: { busId: busId,m:'findAllAuditContentList' },
						   success: function(response){
								  eval("var arr = " + response.responseText);
								  var html = [];
								  html.push('<div>审批内容：');
								  for(var i = 0;i < arr.length;i++){
									html.push('<span style="color:green">');
									html.push(arr[i].auditContentName);
									html.push('</span>(');
									if(arr[i].auditPerson == ''){
										html.push('<span style="color:blue">待审');
									}else{
										html.push('<span style="color:red">');
										html.push(arr[i].auditPerson);
									}
									html.push('</span>)&nbsp;&nbsp;');
								  }
								  html.push('</div>');
								  _this.bbar.update(html.join(''));
						   }
						});
			},scope:this
      
		})
	}
})

Quomanager.ModifyWindow.LOADER_URL = PATH + '/generalQuo/getQuoDetailAction.do';
Quomanager.DetailWindow.LOADER_URL = PATH + '/generalQuo/getQuoDetailAction.do';
Quomanager.DetailWindow.LOADER_RESERVE_URL = PATH + '/generalQuo/getQuoDetailAction.do?method=3';
Quomanager.DetailWindow.LOADER_TESTCUT_URL = PATH + '/generalQuo/getQuoDetailAction.do?method=4';