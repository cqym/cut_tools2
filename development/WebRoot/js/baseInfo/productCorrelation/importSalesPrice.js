

Ext.zhj.ImportDataForm = Ext.extend(Ext.FormPanel, {
	salesHistoryDateCombox : null,
	quoDateField : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		this.salesHistoryDateCombox = new Ext.zhj.SalesHistoryDateCombox();
		this.quoDateField = new Ext.form.DateField({
			fieldLabel: '价格执行期', format:'Y-m-d',name: 'salesDate', 
			emptyText:'',anchor:'90%', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
		});
		Ext.zhj.ImportDataForm.superclass.constructor.call(this, {
					// renderTo : 'fi-form',
					fileUpload : true,
					width : 480,
					frame : true,
					// title : 'File Upload Form',
					autoHeight : true,
					bodyStyle : 'padding: 10px 10px 0 10px;',
					labelWidth : 100,
					defaults : {
						anchor : '70%',
						allowBlank : false,
						labelAlign : 'right',
						buttonAlign : 'right'
					},
					items : [[this.quoDateField], {
								xtype : 'fileuploadfield',
								buttonText : '浏览',
								labelAlign : 'right',
								fieldLabel : '文件',
								// inputType : 'file',
								name : "filepath"
							}],
					buttons : [{
						text : '导入',
						handler : function() {

							var year = this.getForm().items.items[0].getValue();
							if (year == '') {
								Ext.Msg.show({
											title : '提示',
											msg : '请选择面价时间！',
											buttons : Ext.Msg.OK,
											width : 200,
											icon : Ext.MessageBox.INFO
										});
								return false;
							}
							var _filename = this.getForm().items.items[1]
									.getValue();
							var index = _filename.lastIndexOf('.xls');
							if (index == -1) {
								Ext.Msg.show({
											title : '提示',
											msg : '上传文件格式错误,只能上传xls格式文档！',
											buttons : Ext.Msg.OK,
											width : 200,
											icon : Ext.MessageBox.INFO
										});
								return false;
							}

							this.getForm().submit({
								url : PATH
										+ '/baseInfo/importSalePriceAction.do',
								waitMsg : '正在导入数据...',
								success : function(form, action) {
									Ext.Msg.show({
												title : '提示',
												msg : '数据导入成功!',
												buttons : Ext.Msg.OK,
												width : 200,
												icon : Ext.MessageBox.INFO
											});
								},
								failure : function(form, action) {
									Ext.Msg.show({
												title : '提示',
												msg : '部分数据导入成功!',
												buttons : Ext.Msg.OK,
												width : 200,
												icon : Ext.MessageBox.INFO
											});
									window.open(action.result.path);
								}
							});
						},
						scope : this
					}, {
						text : '重置',
						handler : function() {
							this.getForm().reset();
						},
						scope : this
					}]

				});
	}
});

/**
 * 产品面价历史
 * 
 * @class ImportSalesPriceWindow
 * @extends Ext.Window
 */
Ext.zhj.ImportSalesPriceWindow = Ext.extend(Ext.Window, {
	importDataForm : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		this.importDataForm = new Ext.zhj.ImportDataForm();
		Ext.zhj.ImportSalesPriceWindow.superclass.constructor.call(this, {
			title : "导入面价历史",
			width : 500,
			height : 170,
			plain : true,
			tbar : [{
				text : '模板下载',
				iconCls : 'icon-down',
				handler : function() {
					window
							.open(PATH
									+ "/upload/templete/salespricehistory.xls");
				}
			}],
			closable : true,
			constrain : true,
			modal : true,
			items : [this.importDataForm]
		})
	}

})