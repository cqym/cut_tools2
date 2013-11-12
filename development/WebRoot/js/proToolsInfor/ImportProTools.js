Ext.namespace("Ext.ftl.protools.imp");
Toolsimport = Ext.ftl.protools.imp;

Toolsimport.ImportDataForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		Toolsimport.ImportDataForm.superclass.constructor.call(this, {
			fileUpload : true,
			//width : 620,
			frame : true,
			autoHeight : true,
			//bodyStyle : 'padding: 10px 10px 0 10px;',
			style:"padding:5px",
			labelWidth : 50,
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {
				anchor : '90%',
				allowBlank : false,
				labelAlign : 'right',
				buttonAlign : 'right'
			},
			items : [{
				xtype : 'fileuploadfield',
				buttonText : '浏览',
				labelAlign : 'right',
				fieldLabel : '文件',
				//inputType : 'file',
				name : "filepath"
			}],
			buttons : [{
				text : '导入',
				handler : function() {
					var _filename = this.getForm().items.items[0]
							.getValue();
					var index = _filename.lastIndexOf('.xls');
					if (index == -1) {
						Ext.Msg.show({
							title : '提示',
							msg : '文件格式错误,请选择xls格式文档！',
							buttons : Ext.Msg.OK,
							width : 200,
							icon : Ext.MessageBox.INFO
						});
						return false;
					}
					this.getForm().submit({
						url : PATH + '/proTools/importToolsAction.do?method=importTools',
						waitMsg : '正在导入数据...',
						success : function(_form, _action) {
							this.fireEvent('importsuccess');
							Ext.Msg.show({
								title : '提示',
								msg : _action.result.msg,
								buttons : Ext.Msg.OK,
								width : 200,
								icon : Ext.MessageBox.INFO
							});
						},
						failure : function(_form, _action) {
							var errorMsg = _action.result.msg;
							Ext.Msg.show({
								title : '提示',
								msg : "导入失败，请将模板文件下载，修改后重新导入！",
								buttons : Ext.Msg.OK,
								width : 350,
								icon : Ext.MessageBox.INFO
							});
							window.open(PATH +　_action.result.msg[0]);
						},scope : this
					});
				},scope : this
			}, {
				text : '重置',
				handler : function() {
					this.getForm().reset();
				},scope : this
			}]

		});
		
		this.addEvents('importsuccess');
	}
});

/**
 * 产品面价历史
 * 
 * @class ImportSalesPriceWindow
 * @extends Ext.Window
 */
Toolsimport.ImportSalesPriceWindow = Ext.extend(Ext.Window, {
	importDataForm : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.apply(this, _cfg);
		this.importDataForm = new Toolsimport.ImportDataForm();
		Toolsimport.ImportSalesPriceWindow.superclass.constructor.call(this, {
			title : "导入产品",
			width : 400,
			height : 150,
			plain : true,
			constrain : true,
			modal : true,
			layout : 'fit',
			closeAction : 'hide',
			tbar : [{
				text:'模板下载',
				iconCls:'icon-down',
				handler : function() {
					window.open(PATH +　"/upload/templete/toolsTemplete.xls");
				}
			}],
			/*buttons : [{
				text : "关闭",
				handler : function() {
					this.close();
				},
				scope : this
			}],*/
			items : [this.importDataForm]
		})
	}

})