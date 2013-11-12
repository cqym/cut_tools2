Ext.namespace("Ext.ftl.brand");
Ext.ftl.brand.ComboStore = Ext.extend(Ext.data.JsonStore, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		Ext.ftl.brand.ComboStore.superclass.constructor.call(this, {
			url : PATH + '/baseInfo/brandManagerAction.do?method=getProBrandByName',
			fields : ['name','id'],
			root : 'brands'
		})
	}
})

Ext.ftl.brand.Combo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	ownerCt : null,
	orderStoreUrl : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.ftl.brand.ComboStore();
		Ext.ftl.brand.Combo.superclass.constructor.call(this, {
			store: this.store,
	        fieldLabel : '品牌名称',
	        hiddenName : 'name',
	        valueField:'name',
	        allowBlank : false,
	        blankText : '请输入品牌名称',
	        hideTrigger : false,//隐藏触发按钮
	        displayField:'name',
	        typeAhead: true,//当开始输入字符时，在指定的延迟之后会自动匹配剩下的内容，如果找到了匹配的内容则自动选中它
	        forceSelection: false,//限制选择的值必须是下拉列表中的值
	        triggerAction: 'all',
	        queryParam : 'name',//发送至后台的查寻参数值
	        emptyText:'请输入品牌名称',
	        selectOnFocus:true,//当获得焦点时立即选中输入栏中存在的所有文本
	        minChars : 1
	        //enableKeyEvents: true,
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

//-----------------供应商对应品牌信息-----------------------------------
Ext.zhj.SupplierBrankGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	supplierId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		
		var fm = Ext.form;
		var ds = new Ext.data.JsonStore({
					url : PATH
							+ '/baseInfo/supplierBrankListAction.do?supplierId='
							+ this.supplierId,
					root : 'supplierBrankList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					fields : ['tSuppliersId', 'brand', 'id']
				});
		Ext.zhj.SupplierBrankGrid.superclass.constructor.call(this, {
			width : 980,
			height : 505,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			frame : true,
			ds : ds,
			view : new Ext.grid.GridView({
						deferEmptyText : false,
						emptyText : '无此供应商对应的品牌信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
				text : "添加品牌",
				iconCls : 'icon-add',
				handler : function() {
					
					var addSupplierBrankWindow = new Ext.zhj.AddSupplierBrankWindow({supplierId : this.supplierId});
					addSupplierBrankWindow.on('supplierBrankAddSuccess',function(){
						this.getStore().reload();
					},this);
					addSupplierBrankWindow.show();
				},
				scope : this
			}, '-', {
				text : "删除品牌",
				iconCls : 'icon-delete',
				handler : function() {
							var _store = this.getStore();
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的供应商品牌信息!');
								return;
							} else {
								record = selectionModel.getSelected();
								Ext.Ajax.request({
									url : PATH
											+ '/baseInfo/deleteSuppliersBrandAction.do',
									params : {
										suppliersBrandIdPar : record.get("id")
									},
									success : function(response) {
										var responseArray = Ext.util.JSON
												.decode(response.responseText);
										if (responseArray.success == true) {
											Ext.Msg.show({
														title : '成功提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 220,
														icon : Ext.MessageBox.INFO
													});
											_store.remove(record);
											_store.reload();
										} else {
											Ext.Msg.show({
														title : '错误提示',
														msg : responseArray.msg,
														buttons : Ext.Msg.OK,
														width : 220,
														icon : Ext.MessageBox.ERROR
													});
											return;
										}
									}

								});
							}
						},
				scope : this
			}],
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 50
							}), {
						header : '品牌',
						dataIndex : 'brand'
					},{
						header : 'ID',
						hidden : true,
						dataIndex : 'id'
					}]),
			bbar : new Ext.PagingToolbar({
						pageSize : 10,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : ds
					})
		});
	}
});

/**
 * 供应商对应品牌信息
 * @class BrankDetailWindow
 * @extends Ext.Window
 */
Ext.zhj.BrankDetailWindow = Ext.extend(Ext.Window, {

			supplierBrankGrid : null,
			supplierId : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				};
				Ext.apply(this, _cfg);
				this.supplierBrankGrid = new Ext.zhj.SupplierBrankGrid({
							supplierId : this.supplierId
						});
				this.supplierBrankGrid.getStore().load({
							params : {
								start : 0,
								limit : 10
							}
						});
				Ext.zhj.BrankDetailWindow.superclass.constructor.call(this,
						{
							title : "供应商对应产品品牌信息",
							width : 1000,
							height : 580,
							plain : true,
							closable : false,
							constrainHeader : true,
							constrain : true,
							modal : true,
							buttons : [{
										text : "关闭",
										handler : function() {
											this.close();
										},
										scope : this
									}],
							items : [this.supplierBrankGrid]
						})
			}

		})

/**
 * 添加客户品牌信息窗口
 */
Ext.zhj.AddSupplierBrankWindow = Ext.extend(Ext.Window, {
	supplierId : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		Ext.zhj.AddSupplierBrankWindow.superclass.constructor.call(this, {
					title : "新增供应商品牌信息",
					height : 150,
					width : 400,
					plain : true,
					bodyStyle : 'padding:15px',
					closeAction : 'close',
					constrain : true,
					modal : true,
					frame : true,
					items : {
						xtype : "form",
						labelWidth : 100,
						defaultType : "textfield",
						frame : true,
						labelAlign : 'right',
						buttonAlign : 'right',
						bodyStyle : 'padding:5px;',

						items : [new Ext.ftl.brand.Combo()]
					},
					buttons : [{
						text : '确定',
						handler : function() {

							// 当前窗口
							var _addSupplierBrankWindow = this.ownerCt.ownerCt;
							/**
							 * 表单对象
							 */
							var addForm = _addSupplierBrankWindow.findByType('form')[0]
									.getForm();
							var record = new Ext.data.Record(addForm
									.getValues());
							var d = record.data;
							d.brand = d.name;
							var supplierBrankFormInfo = Ext.util.JSON.encode(d);
							//alert(supplierBrankFormInfo);
							Ext.Ajax.request({
										url : PATH
												+ '/baseInfo/addSupplierBrankAction.do?supplierId=' + _addSupplierBrankWindow.supplierId,
										params : {
											supplierBrankFormInfoPar : supplierBrankFormInfo
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
												_addSupplierBrankWindow.fireEvent('supplierBrankAddSuccess');
												_addSupplierBrankWindow.close();
											} else {
												Ext.Msg.show({
															title : '错误提示',
															msg : responseArray.msg,
															buttons : Ext.Msg.OK,
															width : 200,
															icon : Ext.MessageBox.ERROR
														});
												return;
											}
										}

									},this);
						}

					}, {
						text : '取消',
						handler : function() {
							this.ownerCt.ownerCt.close();
						}
					}]

				});
				this.addEvents('supplierBrankAddSuccess');
	}

});
