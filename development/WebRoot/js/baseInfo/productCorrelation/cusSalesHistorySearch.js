

//得到所有客户信息 
Ext.zhj.Customers_Combo_Store_URL = PATH + '/baseInfo/getAllCustomersAction.do';

/**
 * 搜索Store, 绑定到当前combo的数据源
 * 
 * @class Ext.ftl.search.ComboStore
 * @extends Ext.data.JsonStore
 */
Ext.zhj.CustomersComboStore = Ext.extend(Ext.data.JsonStore, {
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {}
				}
				Ext.apply(this, _cfg);

				Ext.zhj.CustomersComboStore.superclass.constructor.call(this, {
							url : Ext.zhj.Customers_Combo_Store_URL,
							fields : ['customerName', 'id', 'customerCode'],
							root : 'customersInfor'
						})
			}
		})

/**
 * 定单自动补全ComboBox
 * 
 * @class Ext.zhj.CusSalesCombo
 * @extends Ext.form.ComboBox
 */
Ext.zhj.CustomersCombo = Ext.extend(Ext.form.ComboBox, {
			store : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				this.store = new Ext.zhj.CustomersComboStore();
				Ext.zhj.CustomersCombo.superclass.constructor.call(this, {
					store : this.store,
					fieldLabel : '客户名称',
					hiddenName : 'customerName',
					minChars : 1,
					valueField : 'id',
					allowBlank : false,
					blankText : '请输入客户名称',
					hideTrigger : true,// 隐藏触发按钮
					displayField : 'customerName',
					typeAhead : true,// 当开始输入字符时，在指定的延迟之后会自动匹配剩下的内容，如果找到了匹配的内容则自动选中它
					forceSelection : true,// 限制选择的值必须是下拉列表中的值
					triggerAction : 'all',
					queryParam : 'customerName',// 发送至后台的查寻参数值
					emptyText : '请输入客户名称',
					selectOnFocus : true
						// 当获得焦点时立即选中输入栏中存在的所有文本
					})
			}
		});

/**
 * 定单搜索Form
 * 
 * @class Ext.ftl.search.SearchForm
 * @extends Ext.FormPanel
 */
Ext.zhj.SearchCusSalesHistoryForm = Ext.extend(Ext.FormPanel, {
	customersCombo : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.customersCombo = new Ext.zhj.CustomersCombo();
		Ext.zhj.SearchCusSalesHistoryForm.superclass.constructor.call(this, {
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
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
					columnWidth : .5
				},
				bbar : ['->', {
					text : "搜  索",
					iconCls : 'icon-search',
					handler : function() {
							
							var customerId = this.customersCombo.value;
							this.fireEvent('searchCusSalesHistory',this, this.getValues());
							/*if(Ext.isEmpty(customerId)){
							
								Ext.Msg.show({
										title : '信息提示',
										msg :  '请输入客户名称！',
										width : 260,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.INFO
									});
								return;
							}else{
								this.fireEvent('searchCusSalesHistory',this, this.getValues());
							}*/
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
							items : [this.customersCombo]
						}]
			}]
		}),
		/**
		 * 当前对象添加searchConpany方法
		 */
		this.addEvents("searchCusSalesHistory");
	},
	

	/**
	 * 获取表单值
	 * 
	 * @return {} Record
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},

	/**
	 * 校验表单输入值是否合法
	 * 
	 * @return {} 如果表单在客户端校验合法，返回true
	 */
	validator : function() {
		return this.getForm().isValid();
	}
})
