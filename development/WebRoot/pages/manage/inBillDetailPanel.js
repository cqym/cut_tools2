firebow.logistics.InBillDetailPanel = Ext.extend(Ext.grid.EditorGridPanel, {
	constructor : function(_cfg) {
	alert('a');
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		// 物资编码
		var materials_ds = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'storage/ajaxGetMaterialList.do'
							}),
					reader : new Ext.data.JsonReader({
								totalProperty : 'totalProperty',// 记录数的属性
								root : 'root'
							}, ['id', 'name', 'specialType', 'unit', 'adunit1',
									'adunit2', 'alias1', 'alias2', 'orgName',
									'orgCode'])
				});
		var materials_resultTpl = new Ext.XTemplate(
				'<div class="search-item-header"><h3>'
						+ '<span style="width=100px">物资编码</span>'
						+ '<span style="width=110px">物资名称</span>'
						+ '<span style="width=160px">规格型号</span>'
						+ '<span style="width=80px">计量单位</span>'
						+ '<span style="width=80px">别名</span>' + '</h3></div>',
				'<tpl for="."><div class="search-item">',
				'<h3><span style="width=100px">{id}</span><span style="width=110px">{name}</span><span style="width=160px">{specialType}</span><span style="width=80px">{unit}</span><span style="width=80px">{alias1}</span></h3>',
				'</div></tpl>');
		// 物资明细
		var detail_reader = new Ext.data.JsonReader({
					root : '',
					fields : ['no', 'name', 'type', "unit", "did", "preAmount",
							"price", "taxPrice", "money", "taxMoney",
							"taxRate", "tax", "repoMaterialUnit",
							"repoMaterialAmount", "repoMaterialPrice",
							"adMaterialUnit", "adMaterialAmount",
							"driverTaxRate", "driverTaxMoney", 'driverTax',
							'additionalTaxMoney', 'additionalTaxRate',
							'additionalTax', 'otherMoney', 'supply',
							'moneyType', 'moneyDetail', 'moneyTypeId']
				});
		var DetailRecord = Ext.data.Record.create(['no', 'name', 'type',
				"unit", "did", "preAmount", "price", "taxPrice", "money",
				"taxMoney", "taxRate", "tax", "repoMaterialUnit",
				"repoMaterialAmount", "repoMaterialPrice", "adMaterialUnit",
				"adMaterialAmount", "driverTaxRate", "driverTaxMoney",
				'driverTax', 'additionalTaxMoney', 'additionalTaxRate',
				'additionalTax', 'otherMoney', 'supply', 'moneyType',
				'moneyDetail', 'moneyTypeId']);
		var data = Ext.util.JSON
				.decode("[{no:'',preAmount:0, price:0, taxPrice:0, money:0,taxRate:0.17,driverTaxRate:0.04,tax:0, taxMoney:0,repoMaterialAmount:0,adMaterialAmount:0,driverTaxMoney:0,driverTax:0,additionalTax:0,otherMoney:0,additionalTaxMoney:0}]");
		var detail_store = new Ext.data.Store({
					data : data,
					reader : detail_reader,
					autoLoad : true
				});
		// 资金类型combo
		var moneyType = new Ext.form.ComboBox({
					store : new Ext.data.JsonStore({
								root : "root",
								proxy : new Ext.data.HttpProxy({
											url : 'storage/ajaxGetMoneyType.do'
										}),
								fields : ["id", "name"]
							}),
					valueField : "id",
					displayField : "name",
					mode : 'local',
					id : 'oo',
					forceSelection : true,
					hiddenName : 'moneyTypeId',
					editable : false,
					triggerAction : 'all',
					allowBlank : true,
					fieldLabel : '核算项目',
					width : 310,
					name : 'moneyTypeId'
				});
		moneyType.store.load();

		function getMoneyType(value) {

			var rowIndex = moneyType.store.find("id", "" + value);
			if (rowIndex < 0)
				return '请选择..';
			var record = moneyType.store.getAt(rowIndex);
			return record ? record.get('name') : '请选择..';

		}
		// 列表模型
		var colM = new Ext.grid.ColumnModel([{
			header : "物资编码",
			dataIndex : "no",
			align : 'center',
			width : 200,
			editor : new Ext.form.ComboBox({
				store : materials_ds,
				displayField : 'id',
				typeAhead : false,
				loadingText : 'Searching...',
				listWidth : 730,
				pageSize : 10,
				hideTrigger : true,
				tpl : materials_resultTpl,
				allowBlank : false,
				minChars : 1,
				itemSelector : 'div.search-item',
				onSelect : function(record) {
					var selModel = getSelectionModel();

					if (selModel.hasSelection()) {
						var selections = selModel.getSelections();
						Ext.each(selections, function(item) {
							stopEditing();
							item.set('no', record.get('id'));
							item.set('name', record.get('name'));
							item.set('type', record.get('specialType'));
							item.set('unit', record.get('unit'));
							item.set('repoMaterialUnit', record.get('adunit1'));
							item.set('adMaterialUnit', record.get('adunit2'));
							if (record.get('unit') != record.get('adunit1')
									|| record.get('unit') != record
											.get('adunit2')) {
								for (int = 11; int < 16; int++) {
									colM.setHidden(int, true);
								}
							}
						});
					}
					this.collapse();

				}

			})

		}, {
			header : "物资名称",
			dataIndex : "name",
			align : 'center',
			width : 100
		}, {
			header : "规格型号",
			dataIndex : "type",
			align : 'center',
			width : 100
		}, {
			header : "计量单位",
			dataIndex : "unit",
			align : 'center',
			width : 70
		}, {
			header : "应收数量",
			dataIndex : "preAmount",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 4
					})
		}, {
			header : "含税单价",
			dataIndex : "taxPrice",
			align : 'right',
			width : 85.7,

			editor : new Ext.form.NumberField({

						allowBlank : false,
						allowNegative : false,
						style : 'text-align:left',
						decimalPrecision : 6
					})
		}, {
			header : "含税价款",
			dataIndex : "taxMoney",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "税率",
			dataIndex : "taxRate",
			align : 'center',
			width : 80.7,

			editor : new Ext.form.NumberField({
						minValue : 0,
						maxValue : 0.9
					})
		}, {
			header : "不含税单价",
			dataIndex : "price",
			align : 'right',
			width : 85.7,

			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 6
					})
		}, {
			header : "不含税价款",
			dataIndex : "money",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 2
					})

		}, {
			header : "税额",
			dataIndex : "tax",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "仓储计量单位",
			dataIndex : "repoMaterialUnit",
			align : 'center',
			hidden : true,
			width : 90
		}, {
			header : "仓储应收数量",
			dataIndex : "repoMaterialAmount",
			align : 'right',
			width : 85.7,
			hidden : true,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 4
					})
		}, {
			header : "仓储入库单价",
			dataIndex : "repoMaterialPrice",
			align : 'right',
			width : 85.7,
			hidden : true,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						allowNegative : false,
						style : 'text-align:left',
						decimalPrecision : 6
					})
		}, {
			header : "附加计量单位",
			dataIndex : "adMaterialUnit",
			align : 'center',
			hidden : true,
			width : 90
		}, {
			header : "附加应收数量",
			dataIndex : "adMaterialAmount",
			align : 'right',
			width : 85.7,
			hidden : true,
			editor : new Ext.form.NumberField({
						allowBlank : false,
						style : 'text-align:left',
						decimalPrecision : 4
					})
		}, {
			header : "含税运费",
			dataIndex : "driverTaxMoney",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "运费税率",
			dataIndex : "driverTaxRate",
			align : 'center',
			width : 80.7,

			editor : new Ext.form.NumberField({
						minValue : 0,
						maxValue : 0.9
					})
		}, {
			header : "运费税",
			dataIndex : "driverTax",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "含税附加费",
			dataIndex : "additionalTaxMoney",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "附加费税率",
			dataIndex : "additionalTaxRate",
			align : 'center',
			width : 85.7,
			renderer : function(v, params, record) {
				if (record.data.additionalTaxRate == '') {
					record.data.additionalTaxRate = 0.17;
					return record.data.additionalTaxRate;
				} else {
					return v;
				}
			},
			editor : new Ext.form.NumberField({
						minValue : 0,
						maxValue : 0.9
					})
		}, {
			header : "附加费税",
			dataIndex : "additionalTax",
			align : 'right',
			width : 85.7,
			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "其它费",
			dataIndex : "otherMoney",
			align : 'right',
			width : 80.7,
			editor : new Ext.form.NumberField({
						style : 'text-align:left',
						decimalPrecision : 2
					})
		}, {
			header : "核算项目",
			dataIndex : "moneyTypeId",
			width : 85.7,
			hidden : true,
			renderer : getMoneyType,
			editor : moneyType
		}, {
			header : "项目明细",
			dataIndex : "moneyDetail",
			width : 85.7,
			hidden : true,
			editor : new Ext.form.TextField()
		}]);
		// 单据明细
		var f_config ={
			region : "center",
			frame : false,
			cm : colM,
			store : detail_store,
			sm : new Ext.grid.RowSelectionModel({
						singleSelection : true
					}),
			tbar : [{
						text : '增加',
						iconCls : 'add-icon',
						handler : function(thiz) {
							detail_store.add(new DetailRecord({
										no : '',
										preAmount : 0,
										price : 0,
										taxPrice : 0,
										money : 0,
										taxRate : 0.17,
										driverTaxRate : 0.04,
										tax : 0,
										taxMoney : 0,
										repoMaterialAmount : 0,
										adMaterialAmount : 0,
										driverTaxMoney : 0,
										driverTax : 0,
										additionalTax : 0,
										otherMoney : 0,
										additionalTaxMoney : 0
									}));
						}
					}, {
						text : '删除',
						iconCls : 'delete-icon',
						handler : function(thiz) {
							var selModel = getSelectionModel();
							if (selModel.hasSelection()) {
								Ext.Msg.confirm("警告", "确定要删除吗？", function(
												button) {

											if (button == "yes") {

												var selections = selModel
														.getSelections();

												Ext.each(selections, function(
																item) {

															detail_store
																	.remove(item);
														});
											}
										});
							} else {
								Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
							}
						}
					}],
			clicksToEdit : 1, // 单击输入
			autoScroll : true,
			collapsible : false,
			stripeRows : true,
			animCollapse : false,
			trackMouseOver : true,
			enableColumnMove : false,
			enableHdMenu : false,
			listeners : {
				afteredit : function(e) {// 修改后计算总金额
					if (e.field == 'preAmount')// 若修改了数量，
					{
						e.record
								.set("taxMoney", Math.round(e.value // 重新计算含税价款
												* e.record.get("taxPrice")
												* 100)
												/ 100);

						e.record.set("money", Math.round(e.value // 重新计算不含税价款
										* e.record.get("price") * 100) / 100);
						// 如果采购计量单位等于仓储计量单位，将仓储入库数量修改成与采购数量一致的
						if (e.record.get("unit") == e.record
								.get("repoMaterialUnit")) {
							e.record.set("repoMaterialAmount", e.value);

						} else
						// 重新计算不含税仓储入库单价
						if (e.record.get("repoMaterialAmount") != ''
								&& e.record.get("repoMaterialAmount") != '0') {
							e.record
									.set(
											"repoMaterialPrice",
											Math
													.round((e.record
															.get("money")
															/ e.record
																	.get("repoMaterialAmount") * 1000000))
													/ 1000000);
						}
						if (e.record.get("unit") == e.record
								.get("adMaterialUnit")) {
							e.record.set("adMaterialAmount", e.value);

						}
						e.record.set(
								"tax", // 重新计算税额
								Math.round((e.record.get("taxMoney") - e.record
										.get("money"))
										* 100)
										/ 100);
						// 如果采购计量单位等于附加计量单位，将附加入库数量修改成与采购数量一致的
						if (e.record.get("unit") == e.record
								.get("adMaterialUnit")) {
							e.record.set("adMaterialAmount", e.value);

						}

					} else if (e.field == 'repoMaterialAmount') {
						// 若修改了仓储入库数量，在仓储计量单位与采购计量单位不一致时，重新计算仓储入库单价
						if (e.record.get("unit") != e.record
								.get("repoMaterialUnit"))
							if (e.record.get("repoMaterialAmount") != ''
									&& e.record.get("repoMaterialAmount") != '0')
								e.record
										.set(
												"repoMaterialPrice",
												Math
														.round((e.record
																.get("money")
																/ e.record
																		.get("repoMaterialAmount") * 1000000))
														/ 1000000);
						if (e.record.get("repoMaterialUnit") == e.record
								.get("adMaterialUnit")) {
							e.record.set("adMaterialAmount", e.value);

						}

					} else if (e.field == 'taxPrice')// 若修改了含税单价，
					{
						e.record.set("price", Math.round( // 重新计算不含税单价
										(e.value / (1 + parseFloat(e.record
												.get("taxRate"))))
												* 1000000)
										/ 1000000);

						e.record.set("taxMoney", Math.round((e.record // 重新计算含税价款
										.get("preAmount") * e.value) * 100)
										/ 100);
						e.record.set(
								"money", // 重新计算不含税价款
								Math.round(e.record.get("price")
										* e.record.get("preAmount") * 100)
										/ 100);
						// 重新计算不含税仓储入库单价
						if (e.record.get("unit") != e.record
								.get("repoMaterialUnit")) {
							if (e.record.get("repoMaterialAmount") != ''
									&& e.record.get("repoMaterialAmount") != '0')
								e.record
										.set(
												"repoMaterialPrice",
												Math
														.round((e.record
																.get("money")
																/ e.record
																		.get("repoMaterialAmount") * 1000000))
														/ 1000000);
						} else
							e.record.set("repoMaterialPrice", e.record
											.get("price"));

						e.record.set(
								"tax", // 重新计算税额
								Math.round((e.record.get("taxMoney") - e.record
										.get("money"))
										* 100)
										/ 100);

					} else if (e.field == 'taxMoney')// 若修改了含税金额，
					{
						e.record.set(
								"money", // 重新计算不含税价款
								Math.round((e.value - e.record.get("tax"))
										* 100)
										/ 100);
						// 重新计算不含税仓储入库单价
						if (e.record.get("unit") != e.record
								.get("repoMaterialUnit"))
							if (e.record.get("repoMaterialAmount") != ''
									&& e.record.get("repoMaterialAmount") != '0') {
								e.record
										.set(
												"repoMaterialPrice",
												Math
														.round((e.record
																.get("money")
																/ e.record
																		.get("repoMaterialAmount") * 1000000))
														/ 1000000);
							}
					}

					else if (e.field == 'price') // 若修改了不含税单价
					{
						e.record // 重新计算含税单价
								.set(
										"taxPrice",
										Math
												.round((e.value * (1 + parseFloat(e.record
														.get("taxRate"))))
														* 1000000)
												/ 1000000);

						e.record.set("taxMoney", Math.round((e.record // 重新计算含税价款
										.get("preAmount") * e.record
										.get("taxPrice"))
										* 100)
										/ 100);

						e.record.set(
								"money", // 重新计算不含税价款
								Math
										.round((e.value * e.record
												.get("preAmount"))
												* 100)
										/ 100);
						// 重新计算不含税仓储入库单价
						if (e.record.get("unit") != e.record
								.get("repoMaterialUnit")) {
							if (e.record.get("repoMaterialAmount") != ''
									&& e.record.get("repoMaterialAmount") != '0')
								e.record
										.set(
												"repoMaterialPrice",
												Math
														.round((e.record
																.get("money")
																/ e.record
																		.get("repoMaterialAmount") * 1000000))
														/ 1000000);
						} else
							e.record.set("repoMaterialPrice", e.value);

						e.record.set(
								"tax", // 重新计算税额
								Math.round((e.record.get("taxMoney") - e.record
										.get("money"))
										* 100)
										/ 100);

					}

					else if (e.field == 'money')// 不含税金额,算税额，算仓储入库单价
					{
						// 重新计算税额
						e.record.set("tax", Math.round((e.record
										.get("taxMoney") - e.value)
										* 100)
										/ 100);
						// 重新计算不含税仓储入库单价
						if (e.record.get("unit") != e.record
								.get("repoMaterialUnit"))
							if (e.record.get("repoMaterialAmount") != ''
									&& e.record.get("repoMaterialAmount") != '0') {
								e.record
										.set(
												"repoMaterialPrice",
												Math
														.round((e.value
																/ e.record
																		.get("repoMaterialAmount") * 1000000))
														/ 1000000);
							}
						// 是否需要重新计算不含税单价
					} else if (e.field == 'taxRate')// 若修改了税率
					{
						if (!flag)// 若先录入了不含税单价,重新计算含税单价
						{
							e.record
									.set(
											"taxPrice",
											Math
													.round((e.record
															.get("price") * (1 + parseFloat(e.value)))
															* 1000000)
													/ 1000000);

							e.record.set("taxMoney", Math.round((e.record
											.get("preAmount") * e.record
											.get("taxPrice"))
											* 100)
											/ 100);

						} else if (flag)// 若先录入了含税单价,重新计算不含税单价、不含税价款
						{
							e.record
									.set(
											"price",
											Math
													.round((e.record
															.get("taxPrice") / (1 + parseFloat(e.value)))
															* 1000000)
													/ 1000000);

							e.record.set("money", Math.round((e.record
											.get("preAmount") * e.record
											.get("price"))
											* 100)
											/ 100);
							// 重新计算不含税仓储入库单价
							if (e.record.get("unit") != e.record
									.get("repoMaterialUnit")) {
								if (e.record.get("repoMaterialAmount") != ''
										&& e.record.get("repoMaterialAmount") != '0')

									e.record
											.set(
													"repoMaterialPrice",
													Math
															.round((e.record
																	.get("money")
																	/ e.record
																			.get("repoMaterialAmount") * 1000000))
															/ 1000000);
							} else
								e.record.set("repoMaterialPrice", e.record
												.get("price"));

						}
						e.record.set("tax", Math.round((e.record
										.get("taxMoney") - e.record
										.get("money"))
										* 100)
										/ 100);// 重新计算税额

					}

					else if (e.field == 'driverTaxRate')// 运费税率
					{
						e.record.set("driverTax", Math.round((e.record
										.get("driverTaxMoney") * e.value)
										* 100)
										/ 100);
					} else if (e.field == 'driverTaxMoney')// 运费含税
					{
						e.record.set("driverTax", Math.round((e.record
										.get("driverTaxRate") * e.value)
										* 100)
										/ 100);
					} else if (e.field == 'additionalTaxRate')// 附加费税率
					{
						e.record.set("additionalTax", Math.round((e.record
										.get("additionalTaxMoney") * e.value)
										* 100)
										/ 100);
					} else if (e.field == 'additionalTaxMoney')// 附加费含税
					{
						e.record.set("additionalTax", Math.round((e.record
										.get("additionalTaxRate") * e.value)
										* 100)
										/ 100);
					}

					// 将编辑焦点自动下移
					if (e.column >= 4 && e.column < 22) {
						if (e.column == 5) {
							if (e.record.get("unit") != e.record
									.get("repoMaterialUnit")) {
								e.grid.startEditing(e.row, e.column + 7);
							} else if (e.record.get("unit") != e.record
									.get("adMaterialUnit"))
								e.grid.startEditing(e.row, e.column + 10);
							else
								e.grid.startEditing(e.row, e.column + 11);
						} else if (e.column == 12)
							e.grid.startEditing(e.row, e.column + 3);
						else if (e.column == 15)
							e.grid.startEditing(e.row, e.column + 1);
						else if (e.column == 16)
							e.grid.startEditing(e.row, e.column + 3);
						else if (e.column == 19)
							e.grid.startEditing(e.row, e.column + 3);
						else
							e.grid.startEditing(e.row, e.column + 1);
					} else {

						if (e.column == 22
								&& (e.row < e.grid.store.getCount() - 1)) {

							e.grid.startEditing(e.row + 1, 4);
						}
					}
					var sumMoney = 0;
					var driverTaxMoney = 0;
					var driverTax = 0;
					var additionalTaxMoney = 0;
					var additionalTax = 0;
					var otherMoney = 0;
					for (var k = 0; k < e.grid.store.getCount(); k++) {
						sumMoney += e.grid.store.getAt(k).get('taxMoney')
								+ e.grid.store.getAt(k).get('driverTaxMoney')
								+ e.grid.store.getAt(k)
										.get('additionalTaxMoney')
								+ e.grid.store.getAt(k).get('otherMoney');
						driverTaxMoney += e.grid.store.getAt(k)
								.get('driverTaxMoney');
						driverTax += e.grid.store.getAt(k).get('driverTax');
						additionalTaxMoney += e.grid.store.getAt(k)
								.get('additionalTaxMoney');
						additionalTax += e.grid.store.getAt(k)
								.get('additionalTax');
						otherMoney += e.grid.store.getAt(k).get('otherMoney');
					}

					
				}
			}

		

		};
		firebow.logistics.InBillDetailPanel.superclass.constructor.call(this,
				f_config);
	}

})
