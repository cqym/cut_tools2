
/**
 * 定义命名空间
 */
Ext.namespace('Ext.zhj.qutation.projectQuo.manage');

/**
 * 命名空间别名
 */
ProQuomanager = Ext.zhj.qutation.projectQuo.manage;

ProQuomanager.WorkOrderRecord = Ext.data.Record.create({
	name : 'proSortName',
	name : 'machineModel',
	name : 'machineCount',
	name : 'supportAmount',
	name : 'backupAmount'
});

// ------------------------------------项目报价单工序--------------------------------------------
ProQuomanager.WorkOrderFormWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		ProQuomanager.WorkOrderFormWindow.superclass.constructor.call(this, {
					title : "添加工序",
					height : 230,
					width : 400,
					plain : true,
					bodyStyle : 'padding:0px',
					closeAction : 'hide',
					layout : 'fit',
					modal : true,
					items : {
						xtype : "form",
						labelWidth : 100,
						frame : true,
						labelAlign : 'right',
						buttonAlign : 'right',
						bodyStyle : 'padding:0px;',
						items : [{
									xtype : 'textfield',
									fieldLabel : "工序名称",
									name : "proSortName",
									anchor : '70%'
								}, {
									xtype : 'textfield',
									fieldLabel : '机床型号',
									name : 'machineModel',
									anchor : '70%'
								}, {
									xtype : 'numberfield',
									allowNegative : false,
									decimalPrecision : 0,
									baseChars : '0123456789',
									fieldLabel : '机床台数',
									name : 'machineCount',
									anchor : '70%'
								}, {
									xtype : 'numberfield',
									allowNegative : false,
									decimalPrecision : 0,
									baseChars : '0123456789',
									fieldLabel : "配套刀具套数",
									name : "supportAmount",
									anchor : '70%'
								}, {
									xtype : 'numberfield',
									allowNegative : false,
									decimalPrecision : 0,
									baseChars : '0123456789',
									fieldLabel : "备用刀具套数",
									name : "backupAmount",
									anchor : '70%'
								}]
					},
					buttons : [{
						text : '确定',
						handler : function() {
							var _formValues = this.findByType('form')[0]
									.getForm().getValues();
							var _proSortName = _formValues.proSortName;
							var _supportAmount = _formValues.supportAmount;
							var _backupAmount = _formValues.backupAmount;
							var _machineModel = _formValues.machineModel;
							var _machineCount = _formValues.machineCount;
							if (_proSortName == '') {
								Ext.Msg.alert('提示', '工序名不能为空!.');
								return false;
							}
							if (_supportAmount == '') {
								Ext.Msg.alert('提示', '配套刀具套数不能为空!.');
								return false;
							}
							if (_backupAmount == '') {
								Ext.Msg.alert('提示', '备用刀具套数不能为空!.');
								return false;
							}
							if (_machineModel == '') {
								Ext.Msg.alert('提示', '机床型号不能为空!.');
								return false;
							}
							if (_machineCount == '') {
								Ext.Msg.alert('提示', '机床台数不能为空!.');
								return false;
							}

							// 校验数量
							if ((!Ext.isNumber(_supportAmount * 1))
									|| (_supportAmount * 1 < 1)) {
								Ext.MessageBox.alert('错误提示', '配套刀具套数数量必须为大于0正整数！');
								return false;
							}

							// 校验数量
							if ((!Ext.isNumber(_backupAmount * 1))
									|| (_backupAmount * 1 < 0)) {
								Ext.MessageBox.alert('错误提示', '备用刀具套数数量不能为负数！');
								return false;
							}

							// 校验数量
							if ((!Ext.isNumber(_machineCount * 1))
									|| (_machineCount * 1 < 1)) {
								Ext.MessageBox.alert('错误提示', '机床台数数量必须为大于0正整数！');
								return false;
							}

							var workOrderRecord = new ProQuomanager.WorkOrderRecord();
							workOrderRecord.set('proSortName', _proSortName);
							workOrderRecord
									.set('supportAmount', _supportAmount);
							workOrderRecord.set('backupAmount', _backupAmount);
							workOrderRecord.set('machineModel', _machineModel);
							workOrderRecord.set('machineCount', _machineCount);
							this.fireEvent('addWorkOrderSubmit',
									workOrderRecord);
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

		this.addEvents('addWorkOrderSubmit');
	}
});

// ------------------------------项目报价单产品树-------------------------------------------

ProQuomanager.onSlaveClick = function(_id) {
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : 1});
	slaveWindow.show();
}

ProQuomanager.GetNewTree = Ext.extend(Ext.tree.ColumnTree, {
	loaderUrl : null,
	workOrderId : null,
	customerRecord : null,
	delProductRecordArray : null,
	delProductRecordArrayIndex : null,
	isCopy : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.delProductRecordArray = new Array();
		this.delProductRecordArrayIndex = 0;
		ProQuomanager.GetNewTree.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			margins : '0 0 0 0',
			rootVisible : false,
			autoScroll : true,
			expandable : false,
			// 是否拖动
			enableDD : false,
			layout:'fit',
			tbar : [{
				text : "添加",
				iconCls : 'icon-add',
				handler : function() {
					fn : this.addProduct();
				},
				scope : this
			}, {
				text : "删除",
				iconCls : 'icon-delete',
				handler : function() {
					fn : this.onDeleteSubmit();
				},
				scope : this
			}, {
				text : "项目号排序",
				iconCls : 'icon-sort',
				listeners : {
					'click' : function() {
						fn : this.onProjectSortSubmit();
					},
					scope : this
				},
				scope : this
			}, {
				text : '统一设置交货期限',
				iconCls : 'icon-date',
				listeners : {
					'click' : function(_btn, e) {
						fn : this.onSetDeliveryDate(_btn, e);
					},
					scope : this
				}
			}],
			columns : [{
				header : '图标',
				width : 70,
				disEnableEdit : true
			}, {
				header : '项目编号',
				width : 70,
				dataIndex : 'projectCode'
			}, {
				header : '序号',
				width : 40,
				dataIndex : 'serialNumber',
				disEnableEdit : true
			}, {
				header : '名称',
				width : 100,
				dataIndex : 'productName',
				disEnableEdit : true
			}, {
				header : '规格描述',
				width : 100,
				dataIndex : 'toolDescription',
				disEnableEdit : true
			}, {
				header : '工具牌号',
				width : 180,
				dataIndex : 'brandCode',
				disEnableEdit : true
			}, {
				header : '单套刀具装配数量',
				width : 130,
				dataIndex : 'singleSetAssemblyAmount',
				listeners : {
					'click' : function() {
						try {
							var e = arguments[0] || window.event;
							var obj = e.srcElement || e.target;
							if (obj.node.parentNode.id != 'root')
								e.cancelBubble = true;
						} catch (e) {
							alert(e);
						}
					}
				}
			}, {
				header : '单套刀具采购数量',
				width : 130,
				dataIndex : 'singleSetStockAmount',
				listeners : {
					'click' : function() {
						try {
							var e = arguments[0] || window.event;
							var obj = e.srcElement || e.target;
							if (obj.node.parentNode.id != 'root')
								e.cancelBubble = true;
						} catch (e) {
							alert(e);
						}
					}
				}
			}, {
				header : '数量(n套采购数量)',
				width : 130,
				dataIndex : 'amount',
				disEnableEdit : true
			}, {
				header : '计量单位',
				width : 80,
				dataIndex : 'productUnit',
				disEnableEdit : true
			}, {
				header : '单价',
				width : 50,
				name : 'price',
				dataIndex : 'price',
				disEnableEdit : true
			}, {
				header : '折扣',
				width : 50,
				dataIndex : 'rebate',
				listeners : {
					'click' : function() {
						try {
							var e = arguments[0] || window.event;
							var obj = e.srcElement || e.target;
							if (obj.node.parentNode.id != 'root')
								e.cancelBubble = true;
						} catch (e) {
							alert(e);
						}
					}
				}
			}, {
				header : '净价',
				width : 80,
				dataIndex : 'netPrice'
			}, {
				header : '金额',
				width : 80,
				dataIndex : 'money',
				disEnableEdit : true
			}, {
				header : '含税净价',
				width : 80,
				dataIndex : 'taxNetPrice'
			}, {
				header : '含税金额',
				width : 80,
				dataIndex : 'taxMoney'
			}, {
				header : '工具代码',
				width : 80,
				dataIndex : 'toolCode',
				disEnableEdit : true
			}, {
				header : '交货期限',
				width : 80,
				dataIndex : 'deliveryDate',
				disEnableEdit : true,
				listeners : {
					'click' : function() {
						try {
							var e = arguments[0] || window.event;
							var obj = e.srcElement || e.target;
							var dataf = new Ext.form.DateField({
										format : 'Y-m-d'
									});
							var menu = new (Ext.menu.DateMenu)({
										hideOnClick : false
									});
							menu["on"]("select", function(a, b) {
								var bb = dataf.formatDate(dataf
										.parseDate(b));
								obj.innerHTML = bb;
								obj.node.cols["deliveryDate"] = bb;
								obj.node.attributes["deliveryDate"] = bb;
								menu.hide();
							}, obj);
							menu.show(obj, "tl-bl");
						} catch (e) {
							alert(e);
						}
					}
				}
			}, {
				header : '价格变动',
				width : 0,
				dataIndex : 'priceChange',
				disEnableEdit : true,
				hidden : true
			}, {
				header : '品牌',
				width : 80,
				dataIndex : 'productBrand',
				disEnableEdit : true
			},{header:'客户确认方案图',width:100,dataIndex:'slaveFile', renderer : function(colValue, node, data) {
        		if(data.parentToolsId != 'root')
        			return;
        		if(colValue > 0) {
        			var id = data.toolsId
        			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + id + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
					return str;
        		}
        	}}, {
				header : '备注1',
				width : 80,
				dataIndex : 'memo'
			}, {
				header : '备注2',
				width : 80,
				dataIndex : 'workshop'
				// hidden : true
			}	, {
				header : '备注3',
				width : 80,
				dataIndex : 'processCode'
			}],
			
			listeners : {
				'render' : function() {
					var loa = this.getLoader();
					loa.on("beforeload", function(loa, node) {
						var _cusId = this.customerRecord.get('customerId');
						if(Ext.isEmpty(_cusId))
							_cusId = "";
						loa.baseParams.workOrderId = this.workOrderId;
						if(this.isCopy)
							loa.baseParams.cusId = _cusId;
					}, this)
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
					}, this)
				}
			},
			
			loader : new Ext.tree.TreeLoader({
				dataUrl : PATH + '/projectQuo/getQuoDetailAction.do',
				autoLoad : true,
				uiProviders : {
					'col' : Ext.tree.ColumnNodeUI
				}
			}),

			root : new Ext.tree.AsyncTreeNode({
				id : "root",
				text : 'Tasks'
			})
		});
		this.addEvents('deleteProductSuccess');
		this.addEvents('addNewNodeSuccess');
	},
	appendChild : function(_newNode) {
		this.getRootNode().appendChild(_newNode);
	},
	/**
	 * 添加产品
	 */
	addProduct : function() {
		var proListWindow = new ProQuoproduct.ProductListWindow({
			customerRecord : this.customerRecord
		})
		var _proTree = proListWindow.northPanel.productTree;
		var _historyTree = proListWindow.centerPanel.historyTree;
		_proTree.store.baseParams.customerId = this.customerRecord
				.get("customerId");
		var _historyTreeLoader = _historyTree.getLoader();
		
		_proTree.on('click', function(_node) {
			_historyTreeLoader.baseParams.proId = _node.id
			_historyTreeLoader.baseParams.customerId = this.customerRecord
					.get("customerId");
			_historyTree.getRootNode().reload();
		})

		_historyTree.on('click', function() {
			var selectedItem = _proTree.getSelectionModel()
					.getSelectedNode();
			if (selectedItem) {
				selectedItem.unselect();
			}
		})

		proListWindow.on('onclick', function(_newNode) {
			var _curRate = this.ownerCt.ownerCt.ownerCt.ownerCt.northPanel.currCombox.curRate;
			var _price = _newNode.price * (1/_curRate);
			_newNode.price = _price.toFixed(2);
			_newNode.projectCode = this.getRootNode().childNodes.length + 1;
			this.appendChild(_newNode);
			/**
			 * 这里触发计算事件
			 */
			// alert("EEEEEEEEEEEEEE");
			// /var editFiled = this.ffcFiled;
			// editFiled.editNode = this.getNodeById(_newNode.id);
			// this.fireEvent("fccAfterUpdateNodeEvent",this,editFiled,_newNode.singleSetAssemblyAmount,null);
			this.fireEvent("addNewNodeSuccess", this, _newNode);

			this.getTopToolbar().items.get(2).fireEvent('click');
		}, this)
		
		proListWindow.show();

	},
	/**
	 * 删除产品
	 */
	onDeleteSubmit : function() {
		var selectedNode = this.getSelectionModel().getSelectedNode();
		if (!selectedNode) {
			Ext.Msg.show({
						title : '信息提示',
						msg : '请选择一条记录进行操作！',
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
			return;
		} else {
			if (selectedNode.parentNode.id != 'root') {
				Ext.Msg.show({
							title : '信息提示',
							msg : '请选择根结点进行删除操作！',
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
				return;
			}
		}

		if (typeof(selectedNode.attributes.quotationInforId) == "undefined") {
			var _money = selectedNode.attributes.money;
			this.fireEvent('deleteProductSuccess', _money);
			selectedNode.remove();
		} else {
			var paramStr = selectedNode.toJsonString();
			this.delProductRecordArray[this.delProductRecordArrayIndex] = paramStr;
			this.delProductRecordArrayIndex++;
			var _money = selectedNode.attributes.money;
			this.fireEvent('deleteProductSuccess', _money);
			selectedNode.remove();
		}
		this.getTopToolbar().items.get(2).fireEvent('click');
	},
	/**
	 * 项目号排序
	 */
	onProjectSortSubmit : function() {
		var rootNode = this.getRootNode();
		if (!rootNode.childNodes)
			return;

		var treeLoader = this.getLoader()

		var temp = {
			"children" : []
		};
		var ffNodes = rootNode.childNodes;
		for (var i = 0; i < ffNodes.length; i++) {

			temp.children.push(Ext.tree.toNewTreeNode(ffNodes[i].attributes, {
						'uiProvider' : 'col'
					}, true));
		}

		var children = temp.children;
		temp.children = children.sort(function(f, s) {
					if (f.projectCode * 1 > s.projectCode * 1) {
						return 1;
					} else if(f.projectCode * 1 == s.projectCode * 1) {
						if(f.serialNumber * 1 > s.serialNumber * 1)
							return 1;
					} 
					return -1;
					
				});
		var rowNum = 0;
		var oldNum = 0;
		for (var i = 0; i < temp.children.length; i++) {
			if (temp.children[i].projectCode != oldNum) {
				rowNum++;
			}
			oldNum = temp.children[i].projectCode;
			temp.children[i].projectCode = rowNum;
		}

		if (rootNode.childNodes) {
			for (var i = rootNode.childNodes.length - 1; i >= 0; i--) {
				rootNode.removeChild(rootNode.childNodes[i]);
			}
		}
		var serialNumber = 1;

		for (var i = 0; i < temp.children.length; i++) {
			temp.children[i]["serialNumber"] = serialNumber++;
			var tn = treeLoader.createNode(temp.children[i]);
			rootNode.appendChild(tn);
			if (temp.children[i].priceChange == 1) {
				tn.ui.setColumnsClass('rebate', 'blue-column-font');
				tn.ui.setColumnsClass('netPrice', 'blue-column-font');
				tn.ui.setColumnsClass('money', 'blue-column-font');
				tn.ui.setColumnsClass('taxMoney', 'blue-column-font');
				tn.ui.setColumnsClass('taxNetPrice', 'blue-column-font');
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
	onSetDeliveryDate : function(_btn, e) {
		var rootNode = this.getRootNode();
		var firstChild = rootNode.firstChild;
		if (firstChild == null) {
			Ext.Msg.show({
						title : '信息提示',
						msg : '请添加报价产品后，再设定交货日期！',
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
			return;
		} else if (!Ext.isEmpty(firstChild.attributes.deliveryDate)) {
			var devDate = firstChild.attributes.deliveryDate;
			rootNode.eachChild(function(curNode) {
						curNode.cols['deliveryDate'] = devDate;
						curNode.attributes['deliveryDate'] = devDate;
						curNode.ui.setInnerHTMLValue('deliveryDate', devDate);
					})
		} else {
			var dataf = new Ext.form.DateField({
						format : 'Y-m-d'
					});
			var menu = new Ext.menu.DateMenu();
			menu["on"]("select", function(a, b) {
						var bb = dataf.formatDate(dataf.parseDate(b));
						rootNode.eachChild(function(curNode) {
									curNode.cols['deliveryDate'] = bb;
									curNode.attributes['deliveryDate'] = bb;
									curNode.ui.setInnerHTMLValue(
											'deliveryDate', bb);
								})
						menu.hide();
					});
			menu.show(_btn.getEl());
		}
	}
});

// ------------------------------------项目报价单编制--------------------------------------

/**
 * 项目报价单基本信息
 * 
 * @class ProQuomanager.QuotationForm
 * @extends Ext.FormPanel
 */
ProQuomanager.QuotationForm = Ext.extend(Ext.FormPanel, {
	currCombox : null,
	sallerCombox : null,
	taxRateCombox : null,
	paymentConditionComboBox : null,
	quoDateField : null,
	startDateField : null,
	endDateField : null,
	lableStyle_ : null,
	currIdField : null,
	checkBox : null,
	willFormalDateField : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
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
					fieldLabel : '报价日期',
					format : 'Y-m-d',
					name : 'quotationDate',
					format : 'Y-m-d',
					emptyText : '',
					x:90,y:33, width:170, 
					allowBlank : false,
					blankText : '该项为必填项!',
					disabled : this.isReadOnly
				});
				
		this.startDateField = new Ext.form.DateField({
			fieldLabel: '报价有效期', format:'Y-m-d',name: 'validStartDate', format:'Y-m-d', x:90,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '报价有效期开始日期不能晚于结束日期',
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
		this.checkBox = new Ext.form.Checkbox({
			xtype:'checkbox' ,name: 'willOrderExpected', inputValue : 1, 
			width : 170, readOnly : this.isReadOnly, allowBlank : false,x:790,y:183,
			disabled : this.isReadOnly,hidden : true, hideLabel : true
		})
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:860,y:213, width:170,validationEvent : false,disabled : this.isReadOnly,
			hidden : true, hideLabel : true
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
			{xtype:'textfield',  name: 'cusContactPerson',readOnly : true,x:340,y:33,width:170},
			{xtype:'label',text: '我方负责人:',x:510,y:35,style:this.lableStyle_},
		    {xtype:'textfield',  name: 'userName',readOnly : true,x:600,y:33,width:170},
			{xtype:'label',text: '税率:',x:770,y:35,style:this.lableStyle_},
			this.taxRateCombox,


			//3
			{xtype:'label',text: '紧急程度:',x:0,y:65,style:this.lableStyle_},
			new Ext.ffc.UrgentLevelCombox({x:90,y:63, width:170,disabled : this.isReadOnly}),
			{xtype:'label',text: '客户电话',x:250,y:65,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerPhone',readOnly : true,x:340,y:63,width:170},
			{xtype:'label',text: '制 单 人:',x:510,y:65,style:this.lableStyle_},
			{xtype:'textfield', name: 'editorName',readOnly : true,x:600,y:63,width:170},
			{xtype:'label',text: '税　　金:',x:770,y:65,style:this.lableStyle_},
			{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:860,y:63,width:170},
			//4
			{xtype:'label',text: '报价状态:',x:0,y:95,style:this.lableStyle_},
			{xtype:'textfield', readOnly : true, x:90,y:93, width:170,name: 'status'},
			//new Ext.ffc.ContractStatusComboBox({readOnly : true,disabled:true,x:90,y:93,width:170}),
			{xtype:'label',text: '客户传真:',x:250,y:95,style:this.lableStyle_},
			{xtype:'textfield',  name: 'customerFax',readOnly : true,x:340,y:93,width:170},
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
             {xtype:'label',text: '是否预订:',x:770,y:185,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.checkBox,
             //8
             {xtype:'label',text: '备注:',x:0,y:215,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:213, width : 680},
             {xtype:'label',text: '预计转正日期:',x:770,y:215,style:this.lableStyle_,hidden : true, hideLabel : true},
             this.willFormalDateField,
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true},
             this.currIdField
		];
		
		ProQuomanager.QuotationForm.superclass.constructor.call(this, {
			bodyStyle : '1000',
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
			frame : true,
			labelWidth : 75,monitorValid:false,
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
	 * 
	 * @param {}
	 *            _r
	 */
	setValues : function(_r) {
		Ext.Ajax.request({
			url : PATH
					+ '/generalQuo/excelAction.do?method=getCurrencyByName',
			params : {
				currencyName : _r.data['currencyName'], currencyId : _r.data['currency']
			},
			success : function(response) {
				var record = Ext.decode(response.responseText);
				if (!Ext.isEmpty(record))
					this.currCombox.curRate = record.rate;
			},
			scope : this
		});

		_r.set('status', this.changeStatus(_r.data['status']));
		this.getForm().loadRecord(_r);
	},

	/**
	 * 获取表单的值
	 * 
	 * @return {}
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
		if(this.checkBox.getValue()) {
			if(this.willFormalDateField.getValue() == "") {
				Ext.Msg.show({
					title:'信息提示',
					msg: '预计转正日期不允许为空！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return false;
			}
		}
		return this.getForm().isValid();
	}
})

/**
 * 报价单工序列表
 * 
 * @type
 */
ProQuomanager.WorkOrderGird = Ext.extend(Ext.grid.EditorGridPanel, {
	delWorkOrderRecordArray : null,
	delWorkOrderRecordArrayIndex : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		var fm = Ext.form;
		Ext.apply(this, _cfg);
		this.delWorkOrderRecordArray = new Array();
		this.delWorkOrderRecordArrayIndex = 0;
		ProQuomanager.WorkOrderGird.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			//height : 230,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			clicksToEdit:1,//单击修改
			view : new Ext.grid.GridView({
				deferEmptyText : false,
				emptyText : '无工单信息！'
			}),
			selModel : new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : {
						fn : function() {
							var record = this.getSelectionModel().getSelected();
							this.fireEvent('rowSelectedSuccess', record
											.get('workOrderNumber'));
						},
						scope : this
					}
				}
			}),
			tbar : [{
				text : "添加工序",
				iconCls : 'icon-add',
				handler : function() {
					var workOrderFormWindow = new ProQuomanager.WorkOrderFormWindow();
					workOrderFormWindow.on('addWorkOrderSubmit', function(
									workOrderRecord) {

								/**
								 * -------------------------------------
								 */
								workOrderRecord.set('workOrderNumber', this
												.getStore().getCount()
												+ 1);
								workOrderRecord.set('totalMoney', 0.00);

								/**
								 * 判断工序名称是否重复，若重复不允许添加
								 */
								var workOrderStore = this.getStore();
								for (var z = 0; z < workOrderStore.getCount(); z++) {
									var b = workOrderStore.getAt(z);
									if (b.get('proSortName') == workOrderRecord
											.get('proSortName')) {
										Ext.Msg.show({
													title : '错误提示',
													msg : '该工序已经存在！',
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.ERROR,
													width : 300
												});
										return false;
									}
								}

								this.getStore().add(workOrderRecord);
								this.fireEvent('addWorkOrderSuccess',
										workOrderRecord);
							}, this);
					workOrderFormWindow.show();
				},
				scope : this

			}, '-', {
				text : "删除工序",
				iconCls : 'icon-delete',
				handler : function() {
					var selectedCount = this.getSelectionModel().getCount();
					if (selectedCount == 0) {
						Ext.Msg.alert('提示', '请选择要删除的工序!.');
						return;
					} else {
						Ext.MessageBox.confirm('信息提示', '确定删除此工序吗?',
										function(button, text) {
											if (button == "yes") {
												var workOrderStore = this.getStore();
												var record = this.getSelectionModel().getSelected();
						
												var index = workOrderStore.indexOf(record);
												// alert(index);
						
												var number_order = record.get('workOrderNumber');
												var total_Money = record.get('totalMoney');
						
												var workOrderId = record.get('id');
												if ((workOrderId != null) && (workOrderId.length > 0)) {
													var aaaa = record.copy();
													this.delWorkOrderRecordArray[this.delWorkOrderRecordArrayIndex] = Ext.util.JSON
															.encode(aaaa);
													this.delWorkOrderRecordArrayIndex++;
												}
						
												workOrderStore.remove(record);
												/**
												 * 移除工单后对工单重新排序
												 */
												for (var i = 0; i < workOrderStore.getCount(); i++) {
						
													// 获取工单信息拷贝
													var a = workOrderStore.getAt(i).copy();
													// 判断需要更改的序号
													if (a.get('workOrderNumber') > number_order) {
														workOrderStore.remove(workOrderStore.getAt(i));
														var n = a.get('workOrderNumber') - 1; // 重设序号
														a.set('workOrderNumber', n);
														workOrderStore.insert(n - 1, a);
													}
												};
						
												this.fireEvent('deleteWorkOrderSuccess', index,
														total_Money);
						
												Ext.Msg.alert('提示', '工序已删除!.');
												}
										}, this);

				
					}

				},
				scope : this

			}],
			cm : new Ext.grid.ColumnModel([
				new Ext.grid.CheckboxSelectionModel(), {
					header : '序号',
					dataIndex : 'workOrderNumber',
					//sortable : true,
					width : 60
				}, {
					header : '工序名称',
					//sortable : true,
					dataIndex : 'proSortName'
				}, {
					header : '机床型号',
					dataIndex : 'machineModel',
					///sortable : true,
					editor : new fm.TextField({
								allowBlank : false
							})
				}, {
					header : '机床台数',
					dataIndex : 'machineCount',
					//sortable : true,
					editor : new fm.NumberField({
								allowBlank : false,
								allowNegative : false,
								decimalPrecision : 0,
								baseChars : '0123456789'
							})
				}, {
					header : '配套刀具套数',
					//sortable : true,
					dataIndex : 'supportAmount',
					editor : new fm.NumberField({
								allowBlank : false,
								allowNegative : false,
								decimalPrecision : 0,
								baseChars : '0123456789',
								maxValue : 100000
							})
				}, {
					header : '备用刀具套数',
					//sortable : true,
					dataIndex : 'backupAmount',
					editor : new fm.NumberField({
								allowBlank : false,
								allowNegative : false,
								decimalPrecision : 0,
								baseChars : '0123456789',
								maxValue : 100000
							})
				}, {
					header : '小计',
					//sortable : true,
					dataIndex : 'totalMoney'
				}, {
					header : 'ID',
					hidden : true,
					dataIndex : 'id'
			}]),
			ds : new Ext.data.JsonStore({
				url : PATH + '/projectQuo/workOrderListAction.do',
				root : 'workOrderList',
				autoLoad : false,
				fields : ['proSortName', 'supportAmount',
						'backupAmount', 'totalMoney', 'machineModel',
						'machineCount', 'id']
			}),
			listeners : {
				'afteredit' : function(e) {
					var workOrderRecord = e.record;
					//校验机床台数与配套刀具套数
					if((e.field == 'machineCount') || (e.field == 'supportAmount')){
						if(e.value * 1 < 1){
							workOrderRecord.set(e.field , e.originalValue);
							Ext.MessageBox.alert('错误提示', '数量必须为大于0的正整数！');
							return false;	
						}
					}
					//校验备用刀具套数
					if(e.field == 'backupAmount') {
						if(e.value * 1 < 0){
							workOrderRecord.set(e.field , e.originalValue);
							Ext.MessageBox.alert('错误提示', '数量必须为大于或等于0的正整数！');
							return false;	
						}
					}
					//触发编辑成功事件
					this.fireEvent('workOrderEditSuccess', workOrderRecord);
					//提交修改
					e.grid.getStore().commitChanges();
				},
				scope : this
			}

		});
		this.addEvents('addWorkOrderSuccess');
		this.addEvents('deleteWorkOrderSuccess');
		this.addEvents('rowSelectedSuccess');
		this.addEvents('workOrderEditSuccess');
	}
});

/**
 * 工单对应的产品列表
 */
ProQuomanager.WorkOrderProductTapPanel = Ext.extend(Ext.TabPanel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		ProQuomanager.WorkOrderProductTapPanel.superclass.constructor
				.call(this, {
			resizeTabs : true, // turn on tab resizing
			minTabWidth : 115,
			tabWidth : 135,
			height : 170,
			enableTabScroll : true,
			// height : 230,
			defaults : {
				autoScroll : false
			},
			plugins : new Ext.ux.TabCloseMenu()
		});
	}
})

/**
 * 报价单编制窗口
 * 
 * @class Quomanager.MangerWindow
 * @extends Ext.Window
 */
ProQuomanager.MangerWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	southPanel : null,
	customerRecord : null,
	quoId : null,
	isCopy : false,//是否复制报价单
	insertDeletes : null,//删除产品数组
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new ProQuomanager.QuotationForm();
		this.centerPanel = new ProQuomanager.WorkOrderGird();
		this.southPanel = new ProQuomanager.WorkOrderProductTapPanel({region : "center"});

		//如果是复制报价单，给工序注册update事件
		/*if(this.isCopy) {
			var productMoney = this.northPanel.findByType('numberfield')[0];
			var toolsTotalMoney = 0;
			var _store = this.centerPanel.getStore();
			_store.on('update', function(_store, _record) {
				if(!Ext.isEmpty(_record.modified.totalMoney) && _record.modified.totalMoney != _record.data['totalMoney']) {
					toolsTotalMoney += _record.data['totalMoney']*1;
					productMoney.setValue(toolsTotalMoney.toFixed(2));
					productMoney.fireEvent('change');
				}
				
			})
		}*/
		
		/**
		 * 注册监听添加工序成功事件，通知workOrderProductTapPanel添加对应的产品树面板
		 */
		this.centerPanel.on('addWorkOrderSuccess', function(workOrderRecord) {
			//产品树，需要改为Grid
			var newTree = new ProQuomanager.GetNewTree({
				customerRecord : this.customerRecord,
				workOrderId : workOrderRecord.id,
				isCopy : this.isCopy
			});
			
			//产品GRID
			var _grid = new QuotationManager.productsGrid({
				loaderUrl : PATH + '/projectQuo/getQuoDetailAction.do',
				customerRecord : this.customerRecord,
				quotationForm : this.northPanel,//报价单FormPanel
				workGrid : this.centerPanel,//工序Grid
				workRecord : workOrderRecord,//当前工序Record
				isEditor : this.isEditor
			})
			//附加工序ID参数
			_grid.store.baseParams.workOrderId = workOrderRecord.id;
			//如果是复制报价单 需要传入客户ID
			if(this.isCopy)
				_grid.store.baseParams.cusId = this.customerRecord.get('customerId');
			
			this.southPanel.add({
				title : workOrderRecord.get('proSortName'),
				// ---------------产品树代码-----------
				iconCls : 'tabs',
				layout:'fit',
				bodyStyle : 'width:100%',
				margins : '0 0 0 0',
				closable : false,
				items : [_grid]//[newTree]
			}).show();
			
			_grid.on({
				'onaddnode' : function(_projectgrid, _record) {
					//导出预订报价单产品添加到项目报价单后，重新计算报价单产品金额
					_grid.calculate4projectQuo(workOrderRecord);
				},
				'oninsertdelete' : function(grid, _node) {
					this.insertDeletes.push(_node);
				},scope : this
	 		})
			// ------树注册监听产品删除事件----------
			/*newTree.on('deleteProductSuccess', function(_money) {
				// 当前活动产品树
				var activePanel = this.southPanel.getActiveTab();
				// 所有产品树
				var items = this.southPanel.items.items;
				// 获取store标志位
				var workOrderflag;
				for (var i = 0; i < items.length; i++) {
					if (activePanel == items[i]) {
						workOrderflag = i;
					}
				}
				var _store = this.centerPanel.getStore();
				for (var i = 0; i < _store.getCount(); i++) {
					var a = _store.getAt(i);
					if ((workOrderflag + 1) == a.get('workOrderNumber')) {
						// 差价
						var workOrderPriceDifference = 0.00 - _money;
						a.set("totalMoney",
								parseFloat(parseFloat(a.get('totalMoney')) + parseFloat(workOrderPriceDifference))
										.toFixed(2));
						var productMoney = this.northPanel
								.findByType('numberfield')[0];
						var _tMoney = parseFloat(productMoney
								.getValue()
								+ parseFloat(workOrderPriceDifference))
								.toFixed(2);
						productMoney.setValue(_tMoney);
						productMoney.fireEvent('change');
					}
				}
				_store.commitChanges();
			}, this);

			this._treeEdit = new Ext.tree.ColumnTreeEditor(newTree, {
				completeOnEnter : true,
				autosize : true,
				ignoreNoChange : true,
				listeners : {
					specialkey : Ext.ffc.gridTreeEditorkeyMove.moveCallBack
			    }
			});
			this._treeEdit.addEvents("fccAfterUpdateNodeEvent");
			this._treeEdit.on("fccAfterUpdateNodeEvent", function(obj, ed,
					value, oldValue) {
				if (obj.editColIndex == 'projectCode')
					return;
				if(obj.editColIndex == 'memo') return;
				if(obj.editColIndex == 'workshop') return;
				if(obj.editColIndex == 'processCode') return;
				Ext.zhj.calculate(this.northPanel, this.centerPanel,
						this.southPanel, newTree, obj, ed, value,
						oldValue);
			}, this);
			newTree.on('addNewNodeSuccess', function(_tree, _newNode) {
				var editFiled = this._treeEdit.ffcFiled;
				editFiled.editNode = newTree.getNodeById(_newNode.id);
				this._treeEdit.fireEvent('fccAfterUpdateNodeEvent',
						this, editFiled, _newNode.amount, null);
			}, this);*/
			
			//复制报价单产品加载后触发事件
			if(this.isCopy) {
				/*newTree.getLoader().on('load', function() {
					var _workOrderMoney = 0;//工序小计
					var rootNode = newTree.getRootNode();
					rootNode.eachChild(function(curNode) {
						var _money = curNode.cols['money'];
						_workOrderMoney += _money * 1.00;
					});
					workOrderRecord.set('totalMoney', _workOrderMoney.toFixed(2));
				},this)*/
				
				_grid.store.on({
					'load' : function(store, records) {
						_grid.calculate4projectQuo(workOrderRecord);
					}
				})
				
			}
		}, this);

		/**
		 * 注册监听删除工序成功事件，通知workOrderProductTapPanel删除对应的产品树面板
		 */
		this.centerPanel.on('deleteWorkOrderSuccess', function(index,
				total_Money) {
			// 差价
			var workOrderPriceDifference = 0.00
					- parseFloat(total_Money);
			// 货品金额
			var productMoney = this.northPanel
					.findByType('numberfield')[0];
			// 删除工序后货品价格
			var _tMoney = parseFloat(productMoney.getValue()
					+ parseFloat(workOrderPriceDifference)).toFixed(2);
			productMoney.setValue(_tMoney);
			productMoney.fireEvent('change');

			this.southPanel
					.remove(this.southPanel.findByType('panel')[index]);
		}, this);

		/**
		 * 选择行后，设置活动的tabpanel
		 */
		this.centerPanel.on('rowSelectedSuccess', function(workOrderNumber) {
			this.southPanel.setActiveTab(this.southPanel.items.items[workOrderNumber - 1]);
		}, this);

		//修改工序后重新计算报价单的价格
		this.centerPanel.on('workOrderEditSuccess', function(workOrderRecord) {
			var record = workOrderRecord;
			//当前活动工序产品GRID
			var _productGrid = this.southPanel.getActiveTab().items.items[0];
			var _store = _productGrid.store;
			
			_store.each(function(_record) {
				// 单套刀具采购数量
				var _singleSetStockAmount = _record.data['singleSetStockAmount']* 1;// 单套刀具采购数量
				var _supportAmount = parseInt(record.get('supportAmount'));
				var _backupAmount = parseInt(record.get('backupAmount'));
				//数量 = (配套刀具数量+备用刀具数量)*单套刀具采购数量
				var _amount = (_supportAmount + _backupAmount)* _singleSetStockAmount;
				_record.set('amount', _amount);
				_productGrid.calculate(_record, null, record);
			})
			
			/*var rootNode = this.southPanel.getActiveTab().items.items[0].getRootNode();

			// 当前工序总金额
			var _workOrderMoney = 0;
			// 遍历root孩子节点，得到工序金额
			rootNode.eachChild(function(curNode) {
				// 单套刀具采购数量
				var _singleSetStockAmount = curNode.cols['singleSetStockAmount']* 1;// 单套刀具采购数量
				//数量 = (配套刀具数量+备用刀具数量)*单套刀具采购数量
				var _amount = (parseInt(record.get('supportAmount')) + parseInt(record.get('backupAmount')))* _singleSetStockAmount;
				
				var oldAmount = curNode.cols['amount'];
				if(_amount != oldAmount)
					_amount = oldAmount;
				
				curNode.cols['amount'] = _amount;
				curNode.attributes['amount'] = _amount;
				curNode.ui.setInnerHTMLValue('amount', _amount);

				var _netPrice = curNode.cols['netPrice'];

				// 金额 = n套产品 * 净价
				var _money = parseFloat(_amount * _netPrice).toFixed(2);
				curNode.cols['money'] = _money;
				curNode.attributes['money'] = _money;
				curNode.ui.setInnerHTMLValue('money', _money);
				_workOrderMoney += _money * 1.00;
			});

			// 差价（原有工序和更改后工序之间的差价）
			var workOrderPriceDifference = 0.00;

			workOrderPriceDifference = parseFloat(_workOrderMoney
					- parseFloat(record.get('totalMoney'))).toFixed(2);

			// 设置当前工序小计
			record.set('totalMoney', _workOrderMoney.toFixed(2));

			var productMoney = this.northPanel.findByType('numberfield')[0];
			var _quoToolsTotalMoney = 0;
			var _store = this.centerPanel.getStore();
			for (var i = 0; i < _store.getCount(); i++) {
				var a = _store.getAt(i);
				_quoToolsTotalMoney += a.get('totalMoney')*1;
			}
			// 删除工序后货品价格
			_tMoney = parseFloat(productMoney.getValue()
					+ parseFloat(workOrderPriceDifference)).toFixed(2);
			productMoney.setValue(_quoToolsTotalMoney.toFixed(2));
			productMoney.fireEvent('change');*/

		}, this);
		
		ProQuomanager.MangerWindow.superclass.constructor.call(this, {
			width : 1080,
			height : 600,
			plain : true,
			closable : true,
			modal : this['modal'] == null ? true : this['modal'],
			constrainHeader : true,
			maximizable : true,
			closeAction : 'hide',
			layout : "border",
			listeners : {
				'show' : function() {
					fn : this.handlerShow();
				},
				scope : this
			},
			items : [{
						title : '项目报价',
						region : "north",
						iconCls : 'icon-grid',
						height : 290,
						frame : true,
    					layout : 'fit',
						collapsible : true,
					    split:true,
						margins : '2 2 0 2',
						items : [this.northPanel]
					}, {
						region : "center",
						iconCls : 'icon-grid',
						height : 350,
						layout : 'fit',
						frame : true,
						split:true,
						collapsible : true,
						collapseMode : 'mine',
						layout : "border",
						margins : '0 2 0 2',
						items : [{
								title : '工序列表',
								region : "north",
								iconCls : 'icon-grid',
								height : 100,
								layout : 'fit',
								frame : true,
								split:true,
								collapsible : true,
								collapseMode : 'mine',
								margins : '0 0 0 0',
								items : [this.centerPanel]
							},this.southPanel
						]
					}
				]
		})
		this.addEvents('quoInfoSaveSuccess');
	},
	/**
	 * 更改报价单金额信息
	 */
	changeForm : function() {
		var _quotationForm = this.northPanel;
		// 货品金额
		var productMoney = _quotationForm.findByType('numberfield')[0];
		// 获得税率对象
		var taxCombox = _quotationForm.taxRateCombox;
		// 税金
		var taxMoney = _quotationForm.findByType('numberfield')[1];
		// 税价合计
		var totalMoney = _quotationForm.findByType('numberfield')[2];
		// 折扣
		var overallRebate = _quotationForm.findByType('numberfield')[3];
		// 最终金额
		var finalMoney = _quotationForm.findByType('numberfield')[4];

		// 货品金额
		var _productMoney = productMoney.getValue();
		var _taxCombox = taxCombox.getValue();
		// 税金
		var _taxMoney = parseFloat(_productMoney * _taxCombox).toFixed(2);
		taxMoney.setValue(_taxMoney);
		var _totalMoney = parseFloat(parseFloat(_productMoney)
				+ parseFloat(_taxMoney)).toFixed(2);
		totalMoney.setValue(_totalMoney);
		var _overallRebate = overallRebate.getValue();

		if (_overallRebate == "") {
			overallRebate.setValue(0);
			_overallRebate = 0;
		}
		var _finalMoney = parseFloat(parseFloat(_totalMoney)
				* (100 - parseFloat(_overallRebate)) / 100).toFixed(2);
		finalMoney.setValue(_finalMoney);

	},
	
	/**
	 * 税率修改后，调用此方法修改产品金额
	 * @param {} _field
	 */
	changeTaxRate : function(_field, _newValue, oldValue) {
		var workOrderList = this.centerPanel;
		// 工序对应的产品树
		var workOrderProductTapPanel = this.southPanel;
		/**
		 * 获取当前编辑的工序信息
		 */
		var workOrderStore = workOrderList.getStore();
		//
		var _productMoney = 0;
		var flag = 0;
		workOrderProductTapPanel.items.each(function(item) {
			var treeproduct = item.items.items[0];
			//var rootCode = treeproduct.getRootNode();
			
			var _store = treeproduct.store;
			var workRecord = workOrderStore.getAt(flag);//工序Record
			// 工序总金额
			var _workOrderMoney = 0;
			_store.each(function(record) {
				treeproduct.calculate4TaxRate(record,null, workRecord);
			})
			flag++;
		});
	},
	
	/**
	 * 汇率修改后调用此方法，修改产品价格
	 * @param {} _field
	 */
	changeCurrCombox : function(_field) {
		/*var _quotationForm = this.northPanel;
		// 获取币种对象
		var currCombox = _quotationForm.currCombox;*/
		// alert('@@@@@@@@@@@' + _field.rate);
		// 获取工序列表对象
		var workOrderList = this.centerPanel;
		// 工序对应的产品树
		var workOrderProductTapPanel = this.southPanel;

		/**
		 * 获取当前编辑的工序信息
		 */
		var workOrderStore = workOrderList.getStore();
		//
		var _productMoney = 0;
		var flag = 0;
		workOrderProductTapPanel.items.each(function(item) {
			var treeproduct = item.items.items[0];
			//var rootCode = treeproduct.getRootNode();
			
			var _store = treeproduct.store;
			var a = workOrderStore.getAt(flag);//工序Record
			// 工序总金额
			var _workOrderMoney = 0;
			
			_store.each(function(record) {
				treeproduct.calculate(record,_field.rate, a);
			})
			/*rootCode.eachChild(function(curNode) {
				// 汇率换算后单价
				var _newprice = curNode.cols['price']
						* _field.rate;
				var _rebate = (100 - curNode.cols['rebate'])
						/ 100;// 折扣

				curNode.cols['price'] = _newprice.toFixed(2);
				curNode.attributes['price'] = _newprice
						.toFixed(2);
				curNode.ui.setInnerHTMLValue('price', _newprice
								.toFixed(2));

				// 净价 = 汇率换算后单价*折扣
				var _netPrice = parseFloat(_newprice * _rebate).toFixed(2);
				curNode.cols['netPrice'] = _netPrice;
				curNode.attributes['netPrice'] = _netPrice;
				curNode.ui.setInnerHTMLValue('netPrice',
						_netPrice);

				// n套产品
				var _productN = 0;
				_productN = curNode.cols['amount'];
				// 金额 = n套产品 * 净价
				var _money = parseFloat(_productN * _netPrice)
						.toFixed(2);
				curNode.cols['money'] = _money;
				curNode.attributes['money'] = _money;
				curNode.ui.setInnerHTMLValue('money', _money);
				_workOrderMoney += _money * 1.00;
			});

			//修改工序小计金额
			var a = workOrderStore.getAt(flag);
			a.set('totalMoney', _workOrderMoney.toFixed(2));
			_productMoney += _workOrderMoney;*/
			flag++;
		});

	/*	if (_productMoney == "") {
			_productMoney = 0.00;
		}*/
		// 货品总价
		/*var productMoney = _quotationForm.findByType('numberfield')[0];
		productMoney.setValue(_productMoney.toFixed(2));
		productMoney.fireEvent('change');*/

	},
	handlerShow : function() {
		var _quotationForm = this.northPanel;
		// 货品金额
		var productMoney = _quotationForm.findByType('numberfield')[0];

		// 获取币种对象
		var currCombox = _quotationForm.currCombox;

		// 获得税率对象
		var taxCombox = _quotationForm.taxRateCombox;

		// 折扣
		var overallRebate = _quotationForm.findByType('numberfield')[3];

		productMoney.on("change", function() {
			fn : this.changeForm();
		}, this);

		//税率
		taxCombox.on("change", function(_field, _newValue, oldValue) {
			fn : this.changeTaxRate(_field, _newValue, oldValue);
		}, this);

		//折扣
		overallRebate.on("change", function() {
			var _overallRebate = overallRebate.getValue() * 1 ;
			if(_overallRebate < 0 || _overallRebate > 100){
				Ext.MessageBox.alert('错误提示', '折扣必须为大于0小于100的正整数！');
				overallRebate.focus();
				return false;
			}
	
			fn : this.changeForm();
		}, this);

		//汇率
		currCombox.on("change", function(_field, _newValue, oldValue) {
			fn : this.changeCurrCombox(_field);
		}, this)

		// 获取工单数据store
		var _store = this.centerPanel.getStore();
		// 在数据加载前传递报价单ID参数
		_store.on("beforeload", function() {
			_store.baseParams.quoid = this.quoId;
		}, this);
		_store.load();

		// var count = _store.getCount();
		_store.on("datachanged", function() {
			// alert(_store.getCount() + '#########');
			for (var i = 0; i < _store.getCount(); i++) {
				_store.getAt(i).set('workOrderNumber', i + 1);
				var a = _store.getAt(i);
				this.centerPanel.fireEvent('addWorkOrderSuccess', a);
			}
		}, this);
	}
})

/**
 * 项目报价单添加页面
 * 
 * @class ProQuomanager.AddWindow
 * @extends ProQuomanager.MangerWindow
 */
ProQuomanager.AddWindow = Ext.extend(ProQuomanager.MangerWindow, {
	loadMarsk : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		ProQuomanager.AddWindow.superclass.constructor.call(this, {
			title : '添加项目报价单',
			buttons : [{
				text : "保存",
				handler : function() {
					fn : this.onAddSubmit();
				},
				scope : this
			}, {
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}]
		});
	},

	onAddSubmit : function() {
		/**
		 * 表单数据
		 */
		var record = new Ext.data.Record(this.northPanel.getForm().getValues());
		//表单校验
		if(!this.northPanel.validator()) {
		    return;
		}
		var quoForm = Ext.util.JSON.encode(record);
		//alert(quoForm);
		//return false;
		var _overallRebate =  record.get('overallRebate')* 1 ;
		if(_overallRebate < 0 || _overallRebate > 100){
			Ext.MessageBox.alert('错误提示', '折扣必须为大于0小于100的正整数！');
			this.northPanel.getForm().findByType('numberfield')[3].focus();
			return false;
		}
		
		/**
		 * 工单数据
		 */
		var workOrderStore = this.centerPanel.getStore();

		var workcount = workOrderStore.getCount();
		if (workcount < 1) {
			Ext.Msg.alert('提示', '工序不能为空，请添加工序！');
			return false;
		}

		/**
		 * 工单总数
		 */
		var total = workOrderStore.getCount();// 数据行数

		/**
		 * 将工单数据转换成JSON字符串格式
		 */
		var orderWorkerInfo = new String();
		for (var i = 0; i < total; i++) {
			var a = workOrderStore.getAt(i).copy();
			orderWorkerInfo += Ext.util.JSON.encode(a);
			orderWorkerInfo += ',';
		};
		/**
		 * 将工单对应的产品信息组合成JSON字符串
		 */
		var productInfoArray = new String();

		var state = true;
		var isValidator = true;
		
		this.southPanel.items.each(function(item) {
			var treeproduct = item.items.items[0];
			try {
				  treeproduct.validator();
			} catch(_e) {
				isValidator = false;
				Ext.Msg.show({
					title:'信息提示',
					msg: _e.message,
					buttons: Ext.Msg.OK,
					width : 200,
					icon: Ext.MessageBox.INFO
				});
				return;
			}
			var _tempArr = [];
			
			_store = treeproduct.store;
			if(_store.getCount == 0) {
				state = false;
			}
			_store.each(function(_record) {
				_tempArr.push(_record.data);
			})
			
			/*//var rootCode = treeproduct.getRootNode();
			
			if (!rootCode.hasChildNodes()) {
				state = false;
			}

			rootCode.eachChild(function(curNode) {
						_tempArr.push(Ext.tree.toNewTreeNode(
								curNode.attributes, {}, false))
					})*/
			var a = Ext.encode(_tempArr);

			productInfoArray += a;
			productInfoArray += ',';
		});
		
		if(!isValidator) {
			return;
		}

		var productInfoArrayJson = '[' + productInfoArray.substring(0, productInfoArray.length - 1) + ']';

		if (!state) {
			Ext.Msg.alert('提示', '工序中产品不能为空!.');
			return false;
		}
		this.loadMarsk = new Ext.LoadMask(this.getEl(), {
		    msg : '正在保存数据，请稍候。。。。。。',
		    removeMask : true// 完成后移除
		});
		this.loadMarsk .show(); //显示
		// 将当前对象赋予_windowZJ
		var _windowZJ = this;
		
		Ext.Ajax.request({
			url : PATH + '/projectQuo/addProjectQuoAction.do',
			params : {
				quoFormPar : quoForm,
				orderWorkerInfoPar : orderWorkerInfo,
				productInfoArrayPar : productInfoArrayJson
			},
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.success == true) {
					Ext.Msg.show({
						title : '成功提示',
						msg : responseArray.msg,
						buttons : Ext.Msg.OK,
						width : 200,
						icon : Ext.MessageBox.INFO
					});
					_windowZJ.fireEvent('quoInfoSaveSuccess');
					this.loadMarsk.hide();
					_windowZJ.hide();
				} else {
					this.loadMarsk.hide();	
					Ext.Msg.show({
						title : '错误提示',
						msg : responseArray.msg,
						buttons : Ext.Msg.OK,
						width : 200,
						icon : Ext.MessageBox.ERROR
					});
				}
			},scope : this

		});
	}
})

/**
 * 项目报价单编辑页面
 * 
 * @class ProQuomanager.EditWindow
 * @extends ProQuomanager.MangerWindow
 */
ProQuomanager.EditWindow = Ext.extend(ProQuomanager.MangerWindow, {
	loadMarsk : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.insertDeletes = [];//清空产品删除数组
		ProQuomanager.EditWindow.superclass.constructor.call(this, {
			title : '修改项目报价单',
			buttons : [{
				text : "保存",
				handler : function() {
					fn : this.onAddSubmit();
				},
				scope : this
			}, {
				text : "取消",
				handler : function() {
					this.close();
				},
				scope : this
			}]
		});
	},

	onAddSubmit : function() {
		/**
		 * 表单数据
		 */
		var record = new Ext.data.Record(this.northPanel.getForm().getValues());

		//表单校验
		if(!this.northPanel.validator()) {
		    return;
		}
		
		var quoForm = Ext.util.JSON.encode(record);
		var _overallRebate =  record.get('overallRebate')* 1 ;
		if(_overallRebate < 0 || _overallRebate > 100){
			Ext.MessageBox.alert('错误提示', '折扣必须为大于0小于100的正整数！');
			this.northPanel.getForm().findByType('numberfield')[3].focus();
			return false;
		}
		
		//alert(Ext.util.JSON.encode(record));
		// return false;

		/**
		 * 工单数据
		 */
		var workOrderStore = this.centerPanel.getStore();
//		return;
		/**
		 * 工单总数
		 */
		var total = workOrderStore.getCount();// 数据行数
		if (total < 1) {
			Ext.Msg.alert('提示', '工序不能为空，请添加工序！');
			return false;
		}

		/**
		 * 将工单数据转换成JSON字符串格式
		 */
		var orderWorkerInfo = new String();
		for (var i = 0; i < total; i++) {
			var a = workOrderStore.getAt(i).copy();

			if (!a.data.id) {
				a.data.id = '00000';
			}
			orderWorkerInfo += Ext.util.JSON.encode(a);
			orderWorkerInfo += ',';
		};
		// alert(orderWorkerInfo + '@@@@@@@@@@@@');

		// 删除工序记录
		var delWorkOrderRecordArray = this.centerPanel.delWorkOrderRecordArray;
		var delWorkOrderInfoArray = new String();
		for (var i = 0; i < delWorkOrderRecordArray.length; i++) {
			delWorkOrderInfoArray += delWorkOrderRecordArray[i];
			delWorkOrderInfoArray += ',';
		}
		// 删除工序JSON串
		var delWorkOrderInfoArrayJson = '['
				+ delWorkOrderInfoArray.substring(0,
						delWorkOrderInfoArray.length - 1) + ']';
		// alert('WWWWWWWWW' + delWorkOrderInfoArrayJson);

		// 工序中现有产品组合
		var productInfoArray = new String();
		var delProductInfoArray = new String();
		
		//工序中是否有产品
		var state = true;
		var isValidator = true;
		
		this.southPanel.items.each(function(item) {
			var treeproduct = item.items.items[0];
			try {
				 treeproduct.validator();
			} catch(_e) {
				isValidator = false;
				Ext.Msg.show({
					title:'信息提示',
					msg: _e.message,
					buttons: Ext.Msg.OK,
					width : 200,
					icon: Ext.MessageBox.INFO
				});
				return;
			}
			var _tempArr = [];
			/*var rootCode = treeproduct.getRootNode();
			
			if (!rootCode.hasChildNodes()) {
				state = false;
			}
			
			rootCode.eachChild(function(curNode) {
						_tempArr.push(Ext.tree.toNewTreeNode(
								curNode.attributes, {}, true,
								['quotationProjectSortId']))
					})*/
			var _store = treeproduct.store;
			
			if(_store.getCount() == 0) {
				state = false;
			}
			
			_store.each(function(_record) {
				_tempArr.push(_record.data)
			})
			
			var a = Ext.encode(_tempArr);
			//alert(a);

			productInfoArray += a;
			productInfoArray += ',';

			/*var delProductRecordArray = treeproduct.delProductRecordArray;
			for (var i = 0; i < delProductRecordArray.length; i++) {
				delProductInfoArray += delProductRecordArray[i];
				delProductInfoArray += ',';
			}*/
		});
		if(!isValidator) {
			return;
		}
		if (!state) {
			Ext.Msg.alert('提示', '工序中产品不能为空!.');
			return false;
		}
	//	return;
		// 删除产品JSON串
		//var delProductInfoArrayJson = '[' + delProductInfoArray.substring(0, delProductInfoArray.length - 1) + ']';
		//var delProductInfoArrayJson = Ext.encode(this.insertDeletes);
		//alert(delProductInfoArrayJson);return;
		// 现有产品JSON串
		var productInfoArrayJson = '[' + productInfoArray.substring(0, productInfoArray.length - 1) + ']';
//alert(productInfoArrayJson);return;
		this.loadMarsk = new Ext.LoadMask(this.getEl(), {
		     msg : '正在修改数据，请稍候。。。。。。',
		     removeMask : true// 完成后移除
		 });
		 this.loadMarsk .show(); //显示
		// 将当前对象赋予_windowZJ
		var _windowZJ = this;
		// return false;
		Ext.Ajax.request({
			url : PATH + '/projectQuo/updateProjectQuoAction.do',
			params : {
				quoFormPar : quoForm,
				orderWorkerInfoPar : orderWorkerInfo,
				productInfoArrayPar : productInfoArrayJson,
				delWorkOrderInfoArrayJsonPar : delWorkOrderInfoArrayJson,
				delProductInfoArrayJsonPar : Ext.encode(this.insertDeletes)
			},
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.success == true) {
					this.southPanel.items.each(function(_ct) {
						this.southPanel.remove(_ct);
					}, this);
					workOrderStore.load();
					
					Ext.Msg.show({
						title : '成功提示',
						msg : responseArray.msg,
						buttons : Ext.Msg.OK,
						width : 200,
						icon : Ext.MessageBox.INFO
					});
					this.insertDeletes = [];//清空产品删除数组
					_windowZJ.fireEvent('quoInfoSaveSuccess');
					this.loadMarsk.hide();
					//_windowZJ.close();
				} else {
					this.loadMarsk.hide();
					Ext.Msg.show({
						title : '错误提示',
						msg : responseArray.msg,
						buttons : Ext.Msg.OK,
						width : 200,
						icon : Ext.MessageBox.ERROR
					});
				}
			},scope : this
		});
	}
})
