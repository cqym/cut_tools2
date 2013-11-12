Ext.namespace('Ext.ftl.quotation');
//报价产品列表入口
var _selectQuoProductwindow;

Ext.ftl.quotation.ProjectNumberCombo = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['projectCode'],
						data : [[1],[2],[3],[4],[5]]
					});
		Ext.ftl.quotation.ProjectNumberCombo.superclass.constructor.call(this, {
					fieldLabel : '项目编号',
					hiddenName : 'projectCode',
					allowBlank : false,
					blankText : '项目编号不允许为空！',
					mode : 'local',
					displayField : 'projectCode',
					anchor:'90%',
					readOnly : false,
					frame : true,
					triggerAction : 'all',
					value : 1,
					store : this.store
		})
	},
	
	validator : function() {
		if(Ext.isNumber(this.getValue()*1)) {
			return true;
		} else {
			return false;
		}
	}
})

Ext.ftl.quotation.ProjectNumberForm = Ext.extend(Ext.FormPanel, {
	projectNumber : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		this.projectNumber = new Ext.ftl.quotation.ProjectNumberCombo();
		Ext.apply(this, _cfg);
		Ext.ftl.quotation.ProjectNumberForm.superclass.constructor.call(this, {
			width:290,  
			height:100,
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "400"},
			labelWidth : 55,
			items : this.projectNumber
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
		return this.projectNumber.validator();
	}
})

Ext.ftl.quotation.ProjectNumerWindow = Ext.extend(Ext.Window, {
	projectNumberForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.projectNumberForm = new Ext.ftl.quotation.ProjectNumberForm();
		Ext.ftl.quotation.ProjectNumerWindow.superclass.constructor.call(this, {
			title: '项目编号',  
			width:350,  
			height:150,  
			plain:true,
			closable : true,
			resizable : false,
			constrainHeader : true,
			modal : true,
			closeAction:'hide',
			layout:"form",
			buttons : [{
				text : "确定",
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
			items : this.projectNumberForm
		})
		
		this.addEvents('onsubmitclick');
	},
	
	onSubmitClick : function() {
		if(!this.projectNumberForm.validator()) {
			return;
		}
		var projectNumber = this.projectNumberForm.getValues().get('projectCode')
		this.fireEvent('onsubmitclick', this, projectNumber);
		this.hide();
	}
})
