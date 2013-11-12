firebow.logistics.InBillByContractPanel = Ext.extend(Ext.Panel, {
	inBill_detail : null,
	dept : "",
	tpanel : null,
	companyCombo:null,
	repoCombo_move:null,
	repoCombo:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.addEvents('aftersave');
		
		// 直进直出领料单位
		this.companyCombo = new Ext.form.ComboBox({
					store : new Ext.data.JsonStore({
								root : "",
								proxy : new Ext.data.HttpProxy({
											url : 'system/ajaxGetOrgNodes.do'
										}),
								fields : ["id", "text"]
							}),
					listeners : {
						select : function(combo, record, index) {
							repoCombo.store.reload();
						}
					},
					valueField : "id",
					displayField : "text",
					mode : 'local',
					forceSelection : false,
					hiddenName : 'reqCompanyId',
					editable : false,
					disabled : true,
					triggerAction : 'all',
					allowBlank : true,
					fieldLabel : '领料单位',
					emptyText : '请选择单位',
					blankText : '请选择单位',
					name : 'reqCompanyId'
				});
		this.companyCombo.store.load();

		// 直进直拨

		this.repoCombo_move = new Ext.form.ComboBox({
			store : new Ext.data.JsonStore({
				root : "root",
				proxy : new Ext.data.HttpProxy({
							url : 'storage/ajaxGetRepositoryByCompany.do?command=all'
						}),
				fields : ["id", "name"]

			}),
			disabled : true,
			valueField : "id",
			displayField : "name",
			mode : 'local',
			forceSelection : true,
			hiddenName : 'moveRepoId',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			fieldLabel : '调拨库房',

			name : 'moveRepoId'
		});
		this.repoCombo_move.store.load();
		// 收料库房
		this.repoCombo = new Ext.form.ComboBox({
					store : new Ext.data.JsonStore({

								root : "root",
								proxy : new Ext.data.HttpProxy({
											url : 'storage/ajaxGetReposByUser.do'
										}),

								fields : ["repoId", "repoName"]

							}),

					valueField : "repoId",

					displayField : "repoName",

					mode : 'local',

					forceSelection : true,

					hiddenName : 'repoId',
					fieldLabel : '收料库房',
					editable : false,

					triggerAction : 'all',

					allowBlank : false,

					blankText : '请选择库房',
					emptyText : '请选择库房',
					name : 'repoId'
				});
		this.repoCombo.store.load();
		// 单据明细
		this.inBill_detail = new firebow.logistics.InBillDetailPanel({
					clicksToEdit : 1
				});
		// 添加在北区的grid表头上方信息
		this.tpanel = new Ext.form.FormPanel({
			bodyStyle : 'padding:5px 5px 0',
			frame : true,
			layout : 'column', // 定义该元素布局为列布局方式
			border : false,
			labelSeparator : ':',// 标题的分隔符号我们用中文
			items : [{
						columnWidth : 0.258,
						layout : 'form',
						labelWidth : 55,
						border : false,
						items : [this.repoCombo]
					}, {
						columnWidth : 0.3,
						layout : 'form',
						labelWidth : 55,
						border : false,
						items : [{
									xtype : 'textfield',
									fieldLabel : '供货单位',
									name : 'supply',
									allowBlank : false,
									anchor : '95%',
									value : ""
								}, {
									xtype : 'hidden',
									fieldLabel : '供货单位id',
									name : 'supplyId',
									anchor : '95%',
									value : ""
								}]
					}, {
						columnWidth : 0.3,
						layout : 'form',
						labelWidth : 65,
						border : false,
						items : [{
									xtype : 'textfield',
									name : 'billAmount',
									fieldLabel : '附单据(张)',
									value : '0',
									anchor : '95%'
								}]
					}, {
						columnWidth : 0.3,
						layout : 'form',
						border : false,
						labelWidth : 55,
						items : [{
									xtype : 'datefield',// 控件的类型为datefield
									fieldLabel : '到货时间',
									name : 'arriveDate',
									value : new Date(),
									invalidText : '格式不对，如2009-01-01',
									readOnly : true,
									format : 'Y-m-d',
									allowBlank : true,
									anchor : '95%'

								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 55,
						border : false,
						items : [{
									xtype : 'hidden',
									fieldLabel : 'inBillType',
									name : 'inBillType',
									value : "3"
								}, {
									xtype : 'textfield',
									fieldLabel : '入库类别',
									value : '依据合同入库',
									readOnly : true,
									name : 'last',
									anchor : '95%'
								}]
					}, {
						columnWidth : .10,
						layout : 'form',
						border : false,
						hideLabels : true,
						items : [{
							xtype : 'checkbox',
							boxLabel : '直进直出',
							name : 'inoutflag',
							anchor : '95%',
							gridObj : this.inBill_detail,
							listeners : {
								check : function(chk, checked) {
									if (checked) {
										this.tpanel
												.find('name', 'reqCompanyId')[0]
												.setDisabled(false);
										this.tpanel.find('name', 'moveRepoId')[0]
												.setDisabled(true);
										this.tpanel.find('name', 'inmoveflag')[0]
												.setValue(false);

										this.gridObj.getColumnModel()
												.setHidden(24, false);
										this.gridObj.getColumnModel()
												.setHidden(23, false);
									} else {
										this.tpanel
												.find('name', 'reqCompanyId')[0]
												.setDisabled(true);
										if (!this.tpanel.find('name',
												'inmoveflag')[0].getValue())
											this.tpanel.find('name',
													'moveRepoId')[0]
													.setDisabled(true);
										this.gridObj.getColumnModel()
												.setHidden(24, true);
										this.gridObj.getColumnModel()
												.setHidden(23, true);
									}
								}
							}
						}, {
							xtype : 'checkbox',
							boxLabel : '直进直拨',
							anchor : '95%',
							name : 'inmoveflag',
							gridObj : this.inBill_detail,
							listeners : {
								check : function(chk, checked) {
									if (checked) {

										this.tpanel
												.find('name', 'reqCompanyId')[0]
												.setDisabled(true);
										this.tpanel.find('name', 'moveRepoId')[0]
												.setDisabled(false);
										this.tpanel.find('name', 'inoutflag')[0]
												.setValue(false);
										this.gridObj.getColumnModel()
												.setHidden(24, true);
										this.gridObj.getColumnModel()
												.setHidden(23, true);
									} else {
										if (!this.tpanel.find('name',
												'inoutflag')[0].getValue())
											this.tpanel.find('name',
													'reqCompanyId')[0]
													.setDisabled(true);
										this.tpanel.find('name', 'moveRepoId')[0]
												.setDisabled(true);

									}
								}
							}
						}]
					}, {
						columnWidth : .258,
						layout : 'form',
						labelWidth : 55,
						name : 'inout',
						disabled : false,
						border : false,
						items : [this.companyCombo]
					}, {
						columnWidth : .258,
						layout : 'form',
						labelWidth : 55,
						name : 'inout',
						disabled : false,
						border : false,
						items : [this.repoCombo_move]
					}]
		});

		// 底层面板(将整个Panel划分为三个区域：分别是北区、南区、中区)
		var f_config = {
			layout : "border",
			frame : true,
			title : '依据合同入库',
			items : [{ // 面板北区
				region : "north",
				height : 100,
				items : [this.tpanel]
			}, {	// 面板南区
						region : "south",
						bodyStyle : 'padding:5px 5px 0',
						buttonAlign : 'center',
						border : false,
						labelWidth : 90,
						frame : true,
						height : 100,
						items : [{
							layout : 'column',
							border : false,
							labelSeparator : ':',
							items : [{// 第一列
								columnWidth : .22,
								layout : 'form',
								border : false,
								labelWidth : 55,
								items : [{
											xtype : 'textfield',
											fieldLabel : '运费',
											name : 'driverTaxMoney',
											readOnly : true,
											anchor : '95%'
										}, {
											xtype : 'textfield',
											fieldLabel : '运费抵税',
											name : 'driverTax',
											readOnly : true,
											anchor : '95%'
										}]
							}, {	// 第二列
										columnWidth : .22,
										layout : 'form',
										border : false,
										labelWidth : 55,
										items : [{
													xtype : 'textfield',
													fieldLabel : '附加费',
													name : 'additionalTaxMoney',
													readOnly : true,
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '附加费税',
													name : 'additionalTax',
													readOnly : true,
													anchor : '95%'
												}]
									}, {// 第三列
										columnWidth : .22,
										layout : 'form',
										border : false,
										labelWidth : 55,
										items : [{
													xtype : 'textfield',
													fieldLabel : '其它费',
													name : 'otherMoney',
													readOnly : true,
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '税率',
													name : 'taxRate',
													readOnly : true,
													anchor : '95%'
												}]
									}, {// 第四列
										columnWidth : .34,
										layout : 'form',
										border : false,
										labelWidth : 90,
										items : [{
													xtype : 'textfield',
													fieldLabel : '承付金额',
													name : 'sumMoney',
													readOnly : true,
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '承付金额(大写)',
													name : 'sumMoneyZh',
													readOnly : true,
													anchor : '95%'
												}]
									}]
						}, {
							columnWidth : 1,
							layout : 'form',
							border : false,
							labelWidth : 55,
							items : [{
										xtype : 'textfield',
										fieldLabel : '备注',
										name : 'remark',
										width : 825
									}]
						}]
					}, this.inBill_detail]

		};

		firebow.logistics.InBillByContractPanel.superclass.constructor.call(this,
				f_config);
	},

	// 初始化事件
	initEvents : function() {
		firebow.logistics.InBillByContractPanel.superclass.initEvents.call(this);
		this.inBill_detail.on("afteredit", this.inBill_detail.onAfteredit,
				this.inBill_detail, [true]);
		this.inBill_detail.on("afteredit", this.onAfteredit, this, [true]);
		// 删除一行记录时更新汇总数据
		this.inBill_detail.on("deleterow", this.onAfteredit, this, [true]);

	},

	onAfteredit : function(e) {

		var sumMoney = 0;
		var driverTaxMoney = 0;
		var driverTax = 0;
		var additionalTaxMoney = 0;
		var additionalTax = 0;
		var otherMoney = 0;
		for (var k = 0; k < e.grid.store.getCount(); k++) {
			sumMoney += e.grid.store.getAt(k).get('taxMoney')
					+ e.grid.store.getAt(k).get('driverTaxMoney')
					+ e.grid.store.getAt(k).get('additionalTaxMoney')
					+ e.grid.store.getAt(k).get('otherMoney');
			driverTaxMoney += e.grid.store.getAt(k).get('driverTaxMoney');
			driverTax += e.grid.store.getAt(k).get('driverTax');
			additionalTaxMoney += e.grid.store.getAt(k)
					.get('additionalTaxMoney');
			additionalTax += e.grid.store.getAt(k).get('additionalTax');
			otherMoney += e.grid.store.getAt(k).get('otherMoney');
		}
		this.find("name", "sumMoney")[0].setValue(sumMoney);
		this.find("name", "sumMoneyZh")[0].setValue(convertCurrency(sumMoney));
		this.find("name", "taxRate")[0].setValue(e.record.get("taxRate"));
		this.find("name", "driverTaxMoney")[0].setValue(driverTaxMoney);
		this.find("name", "driverTax")[0].setValue(driverTax);
		this.find("name", "additionalTaxMoney")[0].setValue(additionalTaxMoney);
		this.find("name", "additionalTax")[0].setValue(additionalTax);
		this.find("name", "otherMoney")[0].setValue(otherMoney);
	},
	aftershow : function() {
		firebow.logistics.InBillByContractPanel.superclass.onShow.call(this);

		// 下拉框的供应商显示
		var provider_ds = new Ext.data.Store({
					proxy : new Ext.data.HttpProxy({
								url : 'provider/ajaxGetProvider.do'
							}),
					reader : new Ext.data.JsonReader({
								totalProperty : 'totalProperty',// 记录数的属性
								root : 'root'
							}, ['id', 'no', 'name', 'brand', 'linkman']),
					baseParams : {
						dept : this.dept
					}
				});

		var provider_resultTpl = new Ext.XTemplate(
				'<div class="search-item-header"><h3>'
						+ '<span style="width=125px">供应商编码</span>'
						+ '<span style="width=125px">供应商名称</span>'
						+ '<span style="width=125px">代理品牌</span>'
						+ '<span style="width=125px">联系人</span>'
						+ '</h3></div>',
				'<tpl for="."><div class="search-item">', '<h3>'
						+ '<span style="width=125px">{no}</span>'
						+ '<span style="width=125px">{name}</span>'
						+ '<span style="width=125px">{brand}</span>'
						+ '<span style="width=125px">{linkman}</span>'
						+ '</h3>', '</div></tpl>');
		new Ext.form.ComboBox({
					store : provider_ds,
					displayField : 'no',
					typeAhead : false,
					loadingText : '正在查找...',
					listWidth : 600,
					pageSize : 10,
					gridObj : this,
					hideTrigger : true,
					tpl : provider_resultTpl,
					minChars : 1,
					applyTo : this.tpanel.find("name", "supply")[0].getId(),
					itemSelector : 'div.search-item',
					onSelect : function(record) {
						this.gridObj.tpanel.find("name", "supplyId")[0]
								.setValue(record.get("id"));
						this.gridObj.tpanel.find("name", "supply")[0]
								.setValue(record.get("name"));
						this.collapse();
					}
				});
	},
	save : function() {
		alert('a');
		// 校验数据
		var verify = true;
		var comboes = this.findByType("combo");
		for (var i = 0; i < comboes.length; i++) {
			if (comboes[i].disabled == false) {
				if (!comboes[i].getValue()) {
					verify = false;
					comboes[i].focus();

					break;
				}
			}
		}
		if (verify && this.tpanel.getForm().isValid()) {
			// 保存单据

			var params = "";
			var items = this.findByType("hidden");
			for (i = 0; i < items.length; i++) {
				if (params.length > 0)
					params += "&";

				params += items[i].getName() + "=" + items[i].getValue();

			}
			items = this.findByType("textfield");
			for (i = 0; i < items.length; i++) {
				if (params.length > 0)
					params += "&";
				if (items[i].getName() == 'arriveDate') {
					params += items[i].getName() + "="
							+ new Date(items[i].getValue()).format('Y-m-d');
				} else {
					params += items[i].getName() + "=" + items[i].getValue();
				}

			}

			var p_center = this.findByType("inbilldetailpanel")[0];

			for (var int2 = 0; int2 < p_center.getStore().getCount(); int2++) {
				var record = p_center.getStore().getAt(int2);
				if (record.get("name") == '') {
					Ext.MessageBox.alert('提示', '物资必须选择');

					return;
				}
				if (Math.abs(record.get("taxMoney")) < Math.abs(record
						.get("money"))) {
					Ext.MessageBox.alert('提示', '含税价款必须大于不含税价款！');

					return;
				}
				if (record.get("repoMaterialUnit") == record
						.get("adMaterialUnit")) {
					if (record.get("repoMaterialAmount") != record
							.get("adMaterialAmount")) {
						Ext.MessageBox.alert('提示', '计量单位相同,数量必须相同！');

						return;
					}
					if (record.get("price") != record.get("repoMaterialPrice")) {
						Ext.MessageBox.alert('提示', '计量单位相同,价格必须相同！');

						return;
					}
				}
				if (record.get("repoMaterialUnit") == record.get("unit")) {
					if (record.get("repoMaterialAmount") != record
							.get("preAmount")) {
						Ext.MessageBox.alert('提示', '计量单位相同,数量必须相同！');

						return;
					}
				}
				params += "&did=" + record.get("no") + "&preAmount="
						+ record.get("preAmount") + "&price="
						+ record.get("price") + "&taxPrice="
						+ record.get("taxPrice") + "&money="
						+ record.get("money") + "&taxMoney="
						+ record.get("taxMoney") + "&taxRate="
						+ record.get("taxRate") + "&tax=" + record.get("tax")
						+ "&repoMaterialUnit=" + record.get("repoMaterialUnit")
						+ "&repoMaterialAmount="
						+ record.get("repoMaterialAmount")
						+ "&repoMaterialPrice="
						+ record.get("repoMaterialPrice") + "&adMaterialUnit="
						+ record.get("adMaterialUnit") + "&adMaterialAmount="
						+ record.get("adMaterialAmount") + "&driverTaxRate="
						+ record.get("driverTaxRate") + "&driverTaxMoney="
						+ record.get("driverTaxMoney") + "&driverTax="
						+ record.get("driverTax") + "&additionalTaxMoney="
						+ record.get("additionalTaxMoney")
						+ "&additionalTaxRate="
						+ record.get("additionalTaxRate") + "&additionalTax="
						+ record.get("additionalTax") + "&otherMoney="
						+ record.get("otherMoney") + "&moneyTypeId="
						+ record.get("moneyTypeId") + "&moneyDetail="
						+ record.get("moneyDetail");
			}

			var obj = this;
			Ext.Ajax.request({// 保存入库单信息
				url : 'storage/ajaxSaveInBill.do',
				params : params,
				method : 'POST',

				success : function() {
					Ext.Msg.alert('提示', '操作成功！');

					obj.fireEvent('aftersave', obj);

				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败！');
				}
			});
		} else {

			Ext.Msg.alert('提示', '请输入必填项！');

		}
	}
})
