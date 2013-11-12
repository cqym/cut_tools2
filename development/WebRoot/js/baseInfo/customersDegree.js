

//------------------保存用户更改信息------------------------------------
Ext.zhj.EditProRecord = Ext.data.Record.create([
    {name: 'row'},
    {name : 'field'},
    {name : 'originalValue'}
]);


// -----------------------------------客户等级信息维护----------------------

Ext.zhj.CustomersDegreeRecord = Ext.data.Record.create({
			name : 'degreeName',
			name : 'degreeCode'
		});

/**
 * 添加客户等级信息
 * 
 * @class Ext.zhj.AddCustomersDegreeFormWindow
 * @extends Ext.Window
 */
Ext.zhj.AddCustomersDegreeFormWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.zhj.AddCustomersDegreeFormWindow.superclass.constructor.call(this,
				{
					title : "添加客户等级",
					height : 170,
					width : 400,
					plain : true,
					bodyStyle : 'padding:15px',
					closeAction : 'hide',
					constrainHeader : true,
					constrain : true,
					modal : true,
					items : {
						xtype : "form",
						labelWidth : 100,
						defaultType : "textfield",
						frame : true,
						labelAlign : 'right',
						buttonAlign : 'right',
						bodyStyle : 'padding:5px;',
						items : [{
									fieldLabel : "客户等级名称",
									name : "degreeName",
									anchor : '70%'
								}, {
									fieldLabel : "客户等级编码",
									name : "degreeCode",
									anchor : '70%'
								}]
					},
					buttons : [{
						text : '保存',
						handler : function() {
							var _formValues = this.findByType('form')[0]
									.getForm().getValues();

							var _degreeName = _formValues.degreeName;
							var _degreeCode = _formValues.degreeCode;
							if (_degreeName == '') {
								Ext.Msg.alert('提示', '客户等级名称不能为空!.');
								return false;
							}
							if (_degreeCode == '') {
								Ext.Msg.alert('提示', '客户等级编码不能为空!.');
								return false;
							}
							var customersDegreeRecord = new Ext.zhj.CustomersDegreeRecord();
							customersDegreeRecord
									.set('degreeName', _degreeName);
							customersDegreeRecord
									.set('degreeCode', _degreeCode);
							this.fireEvent('addCustomersDegreeRecordSubmit',
									customersDegreeRecord);
							this.hide();
						},
						scope : this

					}, {
						text : '取消',
						handler : function() {
							this.hide();
						},
						scope : this
					}]
				});

		this.addEvents('addCustomersDegreeRecordSubmit');
	}
});

Ext.zhj.onClickCusDegRebDetail = function(_degreeId, _grid) {
	// alert(_iid);
	/*
	 * alert('_grid' + _grid);
	 * 
	 * var record = _grid.getSelectionModel().getSelected();
	 * 
	 * alert('_grid' + record.get('id'));
	 * 
	 * return false;
	 */
	var cusDegRebDetailWindow = new Ext.zhj.CusDegRebDetailWindow({
				_id : _degreeId
			});
	cusDegRebDetailWindow.show();
};

/**
 * 
 * 产品采购历史信息列表
 * 
 * @class Ext.zhj.SalesPriceHistoryGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.CustomersDegreeGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	isAddHide : true,isSaveHide : true,isCancelHide : true,isViewHide: true,
	editProRecords : null,
	flag : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		//编辑记录级
		this.editProRecords = new Array();
		this.flag = 0;
		
		var fm = Ext.form;
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/getCustomersDegreeAction.do',
					root : 'customersDegree',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['degreeCode', 'degreeName', 'beDeleted','id']
				});
		ds.on('beforeload', function() {
					var dd = this.getStore().getModifiedRecords();
					if (dd.length > 0) {

						Ext.Msg.show({
									title : '提示',
									msg : '&nbsp;&nbsp;&nbsp;&nbsp;客户等级信息已经更改，请保存！',
									buttons : Ext.Msg.OK,
									width : 350,
									icon : Ext.MessageBox.INFO
								});
						return false;
					}
				}, this);
		if(this.isViewHide){
			ds.on('load', function( store , records, options ) {
						for(var i = 0,len = records.length;i < len ;i++){
							if(records[i].get('beDeleted') == 1){
								store.remove(records[i]);
							}
						}
					}, this);
		}
		this.checkboxs = new Ext.grid.CheckboxSelectionModel();
		Ext.zhj.CustomersDegreeGrid.superclass.constructor.call(this, {
			width : Ext.getBody().getWidth()/3,
			height : Ext.getBody().getHeight() - 70,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			region : 'west',
			el : "customersDegreeGrid",
			frame : true,
			ds : ds,
			tbar : [{
				text : "添加客户等级",
				iconCls : 'icon-add',
				hidden : this.isAddHide,
				handler : function() {
					var addCustomersDegree = new Ext.zhj.AddCustomersDegreeFormWindow();
					// var _store = this.getStore();
					addCustomersDegree.on('addCustomersDegreeRecordSubmit',
							function(customersDegreeRecord) {
								var customersDegree = Ext.util.JSON
										.encode(customersDegreeRecord);
								var _store = this.getStore();
								Ext.Ajax.request({
									url : PATH + '/baseInfo/addCustomersDegreeAction.do',
									params : {
										customersDegreePar : customersDegree
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.INFO
													});
											_store.reload();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.ERROR
													});
										}
									}
								});

							}, this);
					addCustomersDegree.show();
				},
				scope : this
			},{
				xtype:'tbseparator',
				hidden : this.isAddHide
			},{
				text : "保存更改",
				iconCls : 'icon-submit',
				hidden : this.isSaveHide,
				handler : function() {
					var _store = this.getStore();
					var records = _store.getModifiedRecords();
					if (records.length < 1) {
						Ext.MessageBox.alert('提示', '客户等级记录没有修改！');
					} else {
						var modifCustDegRecords = '';
						for (var i = 0; i < records.length; i++) {
							var rec = records[i];
							modifCustDegRecords += Ext.util.JSON
									.encode(rec.data);
							modifCustDegRecords += ',';
						}
						Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/updateCustomersDegreeAction.do',
									params : {
										modifCustDegRecordsPar : modifCustDegRecords
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.INFO
													});
											_store.commitChanges();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 200,
														icon : Ext.MessageBox.ERROR
													});
										}
									}
								});
					}
				
				},
				scope : this
			
			},{
				xtype:'tbseparator',
				hidden : this.isSaveHide
			},{
				text : "删除",
				iconCls : 'icon-delete',
				hidden : this.isCancelHide,
				handler : function() {
					var selection = this.getSelectionModel();
					if(selection.getSelections() == null || selection.getSelections().length == 0) return ;
					var ids = [selection.getSelections()[0].data.id];
					var grid = this;
					Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中客户等级!', function(btn){
						if(btn != 'yes'){return ;}
							Ext.Ajax.request({
								method: "post",
								params: { 'ids' : ids},
								url: PATH + "/baseInfo/DeleteCustomersDegreeAction.do",
								success: function(response){
									grid.getStore().reload();
								}
						    });
					});
				},
				scope : this
			}],
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无产品采购信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			cm :    new Ext.grid.ColumnModel([
					this.checkboxs,
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 50
							}), {
						header : '客户等级名称',
						dataIndex : 'degreeName',
						sortable : true,
						editor : new fm.TextField({
									allowBlank : false,
									allowNegative : false
								})
					}, {
						header : '客户等级编码',
						sortable : true,
						dataIndex : 'degreeCode',
						editor : new fm.TextField({
									allowBlank : false,
									allowNegative : false
								})
					}, {
						header : '专用等级',
						dataIndex : 'beDeleted',
						hidden : true
					}, {
						header : '查看详细',
						dataIndex : 'id',
						hidden : true,
						renderer : this.renderDetail,
						scope : this
						// renderer : Ext.zhj.detailCustomersDegreeRebateWindow
				}]),
			bbar : new Ext.PagingToolbar({
						pageSize : 20,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : ds
					}),
			listeners : {
				'rowclick' : function(obj, rowIndex, evt) {
					var row = obj.getStore().getAt(rowIndex);
					var _id = row.id;
					this.fireEvent('rowselected',_id);
				},'afteredit' : function(e){
					var record = new Ext.zhj.EditProRecord({row : e.row, field : e.field ,originalValue : e.originalValue });
					this.editProRecords[this.flag] = record;
					this.flag++;
				},scope : this
			}
		});
		this.addEvents('rowselected');
	},
	renderDetail : function(degreeId) {
		var _grid = this;
		return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickCusDegRebDetail(\''
				+ degreeId + "\',\'" + _grid + '\')">设定</a>'
	}
	

});

/**
 * 客户等级信息维护入口
 */
Ext.onReady(function() {
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("001" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("001006" == childModule[j].id) {
						var _configArr = childModule[j].children;
						if(_configArr.length > 0) {
							for(var k = 0; k < _configArr.length; k++) {
								if(k != _configArr.length-1)
									_configStr += _configArr[k].url + ",";
								else 
									_configStr += _configArr[k].url + "}"
							}
						} else {
							_configStr += "}";
						}
						
						break;
					}
				}
				
				break;
			}
		}
		return Ext.decode(_configStr);
	}
			var customersDegreeGrid = new Ext.zhj.CustomersDegreeGrid(getConfig());
			var degreeRebateGrid = new Ext.zhj.DegreeRebateGrid(getConfig());
			degreeRebateGrid.getStore().load({
						params : {
							start : 0,
							limit : 20
						}
					});
					
			customersDegreeGrid.getStore().load({
						params : {
							start : 0,
							limit : 20
						}
					});
					
			customersDegreeGrid.on('rowselected',function(_id){
				degreeRebateGrid.getStore().baseParams.degreeId = _id;
				degreeRebateGrid.getStore().reload();
			},this);
			var customersDegreeViewPort = new Ext.Panel({
						width : Ext.getBody().getWidth(),
						height : Ext.getBody().getHeight() - 55,
						layout : 'border',
						items : [customersDegreeGrid, degreeRebateGrid]
					});
			customersDegreeViewPort.render("customersDegreeViewPort");
		})
