/**
 * 改变锁定状态
 * @param {} value
 * @return {String}
 */
function changeLockStatus(value) {
	switch (value) {
		case 0 :
			return "<span style='color:red;font-weight:bold;'>是</span>";
			break;
		case 1 :
			return "<span style='color:green;font-weight:bold;'>否</span>";
			break;
	}
}


/**
 * 改变信誉类别
 * @param {} value
 * @return {String}
 */
function changeReputationType(value) {
	switch (value) {
		case 1 :
			return "<span style='color:red;font-weight:bold;'>合同金额</span>";
			break;
		case 2 :
			return "<span style='color:green;font-weight:bold;'>发票金额</span>";
			break;
		case 3 :
			return "<span style='color:blue;font-weight:bold;'>交货金额</span>";
			break;
	}
}

function yesNoRender(value){
	if(value == '1'){
		return '<span style="color:green">是</span>';
	}else if(value == '-1'){
		return '<span style="color:red">否</span>';
	}
	return '';
}
function YesNoUploadFileRender(value){
	if(value == '1'){
		return '<span style="color:green">合同执行时必须传</span>';
	}else if(value == '-1'){
		return '<span style="color:red">合同执行时不用传</span>';
	}else if(value == '-2'){
		return '<span style="color:blue">合同执行和交货时均不用传</span>';
	}
	return '';
}

/**
 * 客户等级下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
Ext.zhj.CustomersDegreeCombox = Ext.extend(Ext.form.ComboBox, {
			store : null,
			constructor : function(_cfg) {
				if(_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				this.store = new Ext.data.JsonStore({
							url : PATH
									+ '/baseInfo/getCustomersDegreeAction.do',
							fields : ['id', 'degreeCode', 'degreeName'],
							root : 'customersDegree'
						});
				Ext.zhj.CustomersDegreeCombox.superclass.constructor.call(this,
						{
							fieldLabel : '客户等级',
							hiddenName : 'degreeCode',
							mode : 'remote',
							displayField : 'degreeCode',
							valueField : 'id',
							width : this['width'] == null ? 128 : this['width'],
							readOnly : true,
							frame : true,
							allowBlank : false,
							triggerAction : 'all',
							emptyText : '请选择...',
							store : this.store,
							listeners : {
								'beforequery' : function(){
									this.store.baseParams.isDelete = 'no';
								}
							}
						})
			}
		});

/**
 * 客户编号前缀下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
Ext.zhj.CustomersCodeFixCombox = Ext.extend(Ext.form.ComboBox, {
			store : null,
			constructor : function(_cfg) {
				if(_cfg == null) {
					_cfg = {};
				}
				
				Ext.apply(this, _cfg);
				this.store = new Ext.data.SimpleStore({
							fields : ['value'],
							data : [[ 'C'], [ 'A'], [ 'M'],
									[ 'V'], [ 'W']]
						});
				Ext.zhj.CustomersCodeFixCombox.superclass.constructor.call(this,
						{
							fieldLabel : '编号前缀',
							hiddenName : 'customerFixCode',
							mode : 'local',
							displayField : 'value',
							valueField : 'value',
							width : this['width'] == null ? 128 : this['width'],
							frame : true,
							allowBlank : false,
							triggerAction : 'all',
							emptyText : '请选择...',
							store : this.store
						})
			}
		});		
/**
 * 客户等级下拉框
 * @class StatusCombox
 * @extends Ext.form.ComboBox
 */
Ext.zhj.CustomersDegreeComboxSearch = Ext.extend(Ext.form.ComboBox, {
			store : null,
			constructor : function() {
				this.store = new Ext.data.JsonStore({
							url : PATH
									+ '/baseInfo/getCustomersDegreeAction.do',
							fields : ['id', 'degreeCode', 'degreeName'],
							root : 'customersDegree'
						});
				Ext.zhj.CustomersDegreeComboxSearch.superclass.constructor.call(this,
						{
							fieldLabel : '客户等级',
							hiddenName : 'degreeCode',
							mode : 'remote',
							displayField : 'degreeCode',
							valueField : 'degreeCode',
							width : 128,
							readOnly : true,
							frame : true,
							triggerAction : 'all',
							emptyText : '请选择...',
							store : this.store,
							listeners : {
								'beforequery' : function(){
									this.store.baseParams.isDelete = 'no';
								}
							}
						})
			}
		});

/**
 * 
 * 客户信息搜索
 * 
 */
Ext.zhj.SearchCustomers = Ext.extend(Ext.FormPanel, {
			CustomersDegreeComboxSearch : null,
			ownContactPersonCombox : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				this.CustomersDegreeComboxSearch = new Ext.zhj.CustomersDegreeComboxSearch();
				this.ownContactPersonCombox = new Ext.zhj.OwnContactPersonCombox();
				Ext.zhj.SearchCustomers.superclass.constructor.call(this, {
							labelAlign : 'right',
							buttonAlign : 'right',
							bodyStyle : 'padding:5px;',
							el : 'searchCustomers',
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
									columnWidth : .25
								},
								listeners : {
									'render': function(p) {
										p.getEl().on('keypress', function(e){
											if(e.getKey() == e.ENTER){
												this.ownerCt.fireEvent('searchCustomers', this.ownerCt, this.ownerCt.getValues());
											}
										},this);
									}
								},
								bbar : ['->', {
									text : "搜  索",
									iconCls : 'icon-search',
									handler : function() {
										// 发布search事件
										this.fireEvent('searchCustomers', this,
												this.getValues());
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
											items : [{
														xtype : 'textfield',
														fieldLabel : '客户名称',
														name : 'customerName',
														anchor : '90%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '客户编号',
														name : 'customerCode',
														anchor : '90%'
													}]
										},{
											items : [this.CustomersDegreeComboxSearch]
										}, {
											items : [this.ownContactPersonCombox]
										}]
							}]
						});
				/**
				 * 当前对象添加searchConpany方法
				 */
				this.addEvents("searchCustomers");
			},

			/**
			 * 获取搜索条件
			 * 
			 * @return {} 返回搜索条件:Record
			 */
			getValues : function() {
				var record = new Ext.data.Record(this.getForm().getValues());
				return record;
			}

		});


		
		
		
/**
 * 客户基本信息列表
 * 
 * @class Ext.zhj.CompanyGrid
 * @extends Ext.grid.GridPanel
 */
Ext.zhj.CustomersGrid = Ext.extend(Ext.grid.GridPanel, {
	ownContactPersonId:null,
	isAddHide : true,isModifyHide : true,isDelHide : true, isClientLeaderHide : true,
	searchRecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var editor = new Ext.ux.grid.RowEditor({
					saveText : '提交',
					cancelText : '取消'
				});
		/**
		 * 公司信息更新
		 */
		editor.on('afteredit', function(editorow, changes, r, rowIndex) {
					if(this.ownerCt.ownContactPersonId!=null)
					{
						r.set('ownContactPersonId',this.ownerCt.ownContactPersonId);
						this.ownerCt.ownContactPersonId = null;
					}
					var updateCustomersInfo = Ext.util.JSON.encode(r.data);
					var _store = this.ownerCt.getStore();
					Ext.Ajax.request({
								url : PATH
										+ '/baseInfo/updateCustomersAction.do',
								params : {
									updateCustomersInfoPar : updateCustomersInfo
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
										return true;
										// _addCompanyWindow.close();
									} else {
										Ext.Msg.show({
													title : '错误提示',
													msg : responseArray.msg,
													buttons : Ext.Msg.OK,
													width : 200,
													icon : Ext.MessageBox.ERROR
												});
										return false;
									}
								}

							});

				});
		var ds = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/customersListAction.do',
					root : 'customersList',
					totalProperty : 'totalProperty',
					autoLoad : false,
					remoteSort : true,
					fields : ['customerName', 'customerCode',
							'customerShortName', 'contactPersonFirst',
							'phoneFirst', 'faxFirst', 'contactPersonSec',
							'phoneSec', 'faxSec', 'ownContactPerson','ownContactPersonId',
							'degreeCode', 'contractAddress', 'postcode',
							'comAdress', 'bank', 'accountNumber', 'taxCode',
							'homePage', 'email', 'memo','reputationMoney','reputationPeriod','lockStatus', 'reputationType','id','closingAccountMode',
						    'customerFixCode','editDateString','lastUpdateDateString','contractRunCondi','tryCutDoCondi','scheduleDoCondi']
				});
		ds.on({'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								ds.baseParams.searchStr = _searchStr;
								ds.baseParams['start'] = 0;
								//Ext.ffc.util.debug(ds.baseParams);
							}
						},scope : this
					});

		var _store = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/getCustomersDegreeAction.do',
					fields : ['id', 'degreeCode', 'degreeName'],
					root : 'customersDegree'
				});
				
		var _storeUserInfo = new Ext.data.JsonStore({
					url : PATH + '/baseInfo/getUserInforListAction.do',
					fields : ['id','userName','trueName'],
					root : 'userInfo'
		});	
		var _storeReputationType =  new Ext.data.SimpleStore({
							fields : ['id', 'value'],
							data : [['1', '合同金额'], ['2', '发票金额'],['3', '交货金额']]
						});

		Ext.zhj.CustomersGrid.superclass.constructor.call(this, {
			height : Ext.getBody().getHeight() - 175,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			el : "customersGrid",
			frame : true,
			autoExpandMax : true,
			ds : ds,
			view : new Ext.grid.GridView({
						//forceFit : true,
						//autoFill : true,
						deferEmptyText : false,
						emptyText : '无客户信息！'
					}),
			selModel : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			tbar : [{
						text : "新增客户",
						iconCls : 'icon-add',
						hidden : this.isAddHide,
						handler : function() {
							var addCustomersWindow = new Ext.zhj.AddCustomersWindow({
							    formUrl : '/baseInfo/addCustomersAction.do',
								codeFix:false
							});
							addCustomersWindow.on('customerAddSuccess',function(){
								this.getStore().reload();								
							},this);
							addCustomersWindow.show();
						},scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isAddHide
					},{
						text : "修改客户",
						iconCls : 'icon-add',
						hidden : this.isModifyHide,
						handler : function() {
							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();
							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要修改的客户信息!.');
								return;
							} else {
								var record = selectionModel.getSelected();
								var updateCustomersWindow = new Ext.zhj.AddCustomersWindow({
									formUrl : '/baseInfo/updateCustomersAction.do',
								    codeFix:true
								});
								var farr = updateCustomersWindow.findByType('form');
								if(farr.length == 0) return ;
								var f = farr[0];
								if(record.data.customerCode != null){
									record.data.customerFixCode = record.data.customerCode.substring(0,1);
								}
								updateCustomersWindow.on('show',function() {
										f.getForm().setValues(record.data);
									}, this);
								updateCustomersWindow.on('close',function() {
										this.getStore().reload();
									}, this);
								updateCustomersWindow.show();
							}
						},scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isModifyHide
					}, {
						text : "删除客户",
						iconCls : 'icon-delete',
						hidden : this.isDelHide,
						handler : function() {

							var _store = this.getStore();

							var selectionModel = this.getSelectionModel();
							var selectedCount = selectionModel.getCount();

							if (selectedCount == 0) {
								Ext.Msg.alert('提示', '请选择要删除的客户信息!.');
								return;
							} else {
								Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中客户信息!', function(btn){
									if(btn != 'yes'){return ;}
									var record = selectionModel.getSelected();
									Ext.Ajax.request({
										url : PATH
												+ '/baseInfo/deleteCustemersAction.do',
										params : {
											customersIdPar : record.get("id")
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
												_store.remove(record);
												_store.reload();
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

									});
								});
							}
						},
						scope : this
					}],
			cm : new Ext.grid.ColumnModel([
					new Ext.grid.CheckboxSelectionModel(),
					new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
							}), {
						header : '客户名称',
						dataIndex : 'customerName',
						sortable : true,
						width : 220
					}, {
						header : '客户编号',
						dataIndex : 'customerCode',
						sortable : true,
						width : 80
					}, {
						header : '简称',
						sortable : true,
						dataIndex : 'customerShortName'
					},{
						header : '客户级别',
						dataIndex : 'degreeCode',
						width : 80,
						sortable : true
					},{
						header : '是否锁定',
						dataIndex : 'lockStatus',
						sortable : true,
						width : 80,
						trueText: '<span style="color:red;font-weight:bold;">已锁定</span>',
           				falseText: '<span style="color:#00aa00;font-weight:bold;">正常</span>',
						renderer : function(v) {
							    if(v == 1){
								    return '<span style="color:red;font-weight:bold;">已锁定</span>';
								}else{
								    return '<span style="color:#00aa00;font-weight:bold;">正常</span>';
								}
								return v;
							
						}
					},{
						header : '结算方式',
						sortable : true,
						dataIndex : 'closingAccountMode',
						width : 80
					},{
						header : '联系人1',
						sortable : true,
						dataIndex : 'contactPersonFirst',
						width : 80
					}, {
						header : '电话1',
						sortable : true,
						dataIndex : 'phoneFirst',
						width : 100
					}, {
						header : '传真1',
						sortable : true,
						dataIndex : 'faxFirst',
						width : 100
					}, {
						header : '联系人2',
						sortable : true,
						dataIndex : 'contactPersonSec',
						width : 80
					}, {
						header : '电话2',
						sortable : true,
						dataIndex : 'phoneSec',
						width : 100
					}, {
						header : '传真2',
						sortable : true,
						dataIndex : 'faxSec',
						width : 100
					}, {
						header : '我方负责人',
						sortable : true,
						dataIndex : 'ownContactPerson',
						width : 80

					}, {
						header : '我方负责人助理',
						renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							  var id = record.data.id;
							  var ownId = record.data.ownContactPersonId;
							  var isHide = this.isClientLeaderHide;
							  var viewStr = "设定";
							  if(isHide)
							  	viewStr = "查看";
							  return "<a href='#' onclick='javascript:new Clientleader.Window({title:\"客户负责人设定\", ownId:\"" + ownId + "\",userId:\"" + id + "\", isHide:" + isHide + "}).show()'>" + viewStr + "</a>";
						  },scope : this
					},{
						header : '我方负责人Id',
						sortable : true,
						dataIndex : 'ownContactPersonId',
						width : 80,
						hidden:true
					},{
						header : '联系地址',
						sortable : true,
						dataIndex : 'contractAddress',
						width : 80
					}, {
						header : '邮编',
						sortable : true,
						dataIndex : 'postcode',
						width : 80
					}, {
						header : '注册地址',
						sortable : true,
						dataIndex : 'comAdress',
						width : 80
					},{
						header : '信誉类别',
						sortable : true,
						dataIndex : 'reputationType',
						width : 80,
						renderer : changeReputationType
					}, {
						header : '信誉额度',
						sortable : true,
						dataIndex : 'reputationMoney',
						width : 80,
						sortable : true
					}, {
						header : '信誉有效期(天)',
						dataIndex : 'reputationPeriod',
						width : 80,
						sortable : true
					},{
						header : '开户银行',
						dataIndex : 'bank',
						sortable : true,
						width : 80
					}, {
						header : '帐号',
						dataIndex : 'accountNumber',
						sortable : true,
						width : 80
					}, {
						header : '税号',
						dataIndex : 'taxCode',
						sortable : true,
						width : 80
					}, {
						header : '主页',
						dataIndex : 'homePage',
						sortable : true,
						width : 80
					}, {
						header : 'E-mail',
						dataIndex : 'email',
						sortable : true,
						width : 80
					},{
						header : '必须传附件',
						dataIndex : 'contractRunCondi',
						sortable : true,
						width : 150,
						renderer : YesNoUploadFileRender
					},{
						header : '可做试刀',
						dataIndex : 'tryCutDoCondi',
						sortable : true,
						width : 80,
						renderer : yesNoRender
					},{
						header : '可做预定',
						dataIndex : 'scheduleDoCondi',
						sortable : true,
						width : 80,
						renderer : yesNoRender
					}, {
						header : '备注',
						sortable : true,
						dataIndex : 'memo',
						width : 80
					},{
						header : '编制时间',
						dataIndex : 'editDateString',
						sortable : true,
						width : 120,
						
					},{
						header : '最后修改时间',
						dataIndex : 'lastUpdateDateString',
						sortable : true,
						width : 120
					}, {
						header : 'ID',
						hidden : true,
						dataIndex : 'id'
					}, {
						header : 'customerFixCode',
						hidden : true,
						dataIndex : 'customerFixCode'
					}]),
			bbar : new Ext.PagingToolbar({
						pageSize : PAGESIZE,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : ds
					})
		});

	},
	/**
	 * 为搜索条件设值
	 * @param {} _value
	 */
	setSearchStr : function(_value) {
		this.searchRecord = _value;
	}

});

/**
 * 客户信息
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
					if("001002" == childModule[j].id) {
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
	var customersGrid = new Ext.zhj.CustomersGrid(getConfig());
	var searchCustomersForm = new Ext.zhj.SearchCustomers();
	
	//监听搜索事件
	searchCustomersForm.on({
				'searchCustomers' : function(_form, _values) {
					var p = {start : 0,limit : PAGESIZE,searchStr:Ext.encode(_values.data)};
					var store = customersGrid.getStore();
					for(var i in p){
						store.setBaseParam(i, p[i]);
					}
					store.load();
				},
				scope : this
	});
	
	customersGrid.getStore().load({
				params : {
					start : 0,
					limit : PAGESIZE
				}
			});
	var customersViewPort = new Ext.Panel({
				width  : Ext.getBody().getWidth(),
				height : Ext.getBody().getHeight() - 55,
				layout: 'border',
				items : [{
							region : "north",
							layout: 'fit',
							height : 105,
							frame : true,
							collapsible : true,
							margins : '5 5 5 5',
							items : [searchCustomersForm]
						}, {
							region : "center",
							layout: 'fit',
							collapsible : true,
							margins : '0 0 0 0',
							items : [customersGrid]
						}]
			});
	customersViewPort.render("customersViewPort");
});

/**
 * 添加客户基本信息窗口
 */
Ext.zhj.AddCustomersWindow = Ext.extend(Ext.Window, {
	customersDegreeCombox : null,
	ownContactPersonCombox : null,
	reputationTypeCombox : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.customersDegreeCombox = new Ext.zhj.CustomersDegreeCombox({width : 140});
		var w = this;
		this.ownContactPersonCombox = new Ext.zhj.OwnContactPersonCombox(
			{
				width : 140,
				listeners : {
					'select' : function( combo, record, index ){
						var f = w.findByType('form')[0];
						f.getForm().setValues({'ownContactPersonId':record.id});
					}
				}
			});
		this.reputationTypeCombox = new Ext.zhj.ReputationTypeCombox({width : 140});
		Ext.zhj.AddCustomersWindow.superclass.constructor.call(this, {
					bodyStyle : 'width:100%',
					title : "新增客户信息",
					height : 500,
					width : 785,
					plain : true,
					bodyStyle : 'padding:15px',
					closeAction : 'close',
					constrain : true,
					modal : true,
					frame : true,
					items : [{
								xtype : 'form',
								layout : 'column',
								border : false,
								labelSeparator : ':',
								frame : true,
								defaults : {
									layout : 'form',
									border : false,
									columnWidth : .5,
									labelAlign : 'right',
									labelWidth : '60'
								},
								items : [{
											xtype : 'hidden',
											name : 'ownContactPersonId'
										},{
											items : [{
														xtype : 'hidden',
														fieldLabel : 'ID',
														name : 'id'
													},{
														xtype : 'textfield',
														fieldLabel : '客户名称',
														name : 'customerName',
														allowBlank : false,
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '简称',
														name : 'customerShortName',
														allowBlank : false,
														anchor : '100%'
													}]
										}, {
											items : [new Ext.zhj.CustomersCodeFixCombox({disabled:this.codeFix})]
										},{
											items : [{
														xtype : 'textfield',
														fieldLabel : '客户编号',
														name : 'customerCode',
														//allowBlank : false,
														anchor : '100%',
														disabled:true
													}]
										},{
											items : [this.customersDegreeCombox]
										},{
											items : [{
														xtype : 'textfield',
														fieldLabel : '电话1',
														name : 'phoneFirst',
														emptyText : '',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '传真1',
														name : 'faxFirst',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '联系人2',
														name : 'contactPersonSec',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '电话2',
														name : 'phoneSec',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '传真2',
														name : 'faxSec',
														anchor : '100%'
													}]
										}, {
											items : [this.ownContactPersonCombox]
										},{
												items : [new Ext.ffc.ClosingAccountModeComboBox({width : 260})]

											}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '联系人1',
														name : 'contactPersonFirst',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '联系地址',
														name : 'contractAddress',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '邮编',
														name : 'postcode',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '注册地址',
														name : 'comAdress',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '开户银行',
														name : 'bank',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '帐号',
														name : 'accountNumber',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '税号',
														name : 'taxCode',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '主页',
														name : 'homePage',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : 'E-mail',
														name : 'email',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'textfield',
														fieldLabel : '备注',
														name : 'memo',
														anchor : '100%'
													}]
										}, {
											items : [new Ext.ffc.LockStatusComboBox({width : 140})]
										}, {
											items : [this.reputationTypeCombox]
										}, {
											items : [{
														xtype : 'numberfield',
														fieldLabel : '信誉额度',
														name : 'reputationMoney',
														anchor : '100%'
													}]
										}, {
											items : [{
														xtype : 'numberfield',
														fieldLabel : '信誉有效期(天)',
														name : 'reputationPeriod',
														anchor : '100%'
													}]
										}, {
											items : [new Ext.ffc.YesNoUploadFileCombox({
												fieldLabel:'必须传附件',
												hiddenName:'contractRunCondi',
												width : 220
											})]
										}, {
											items : [new Ext.ffc.YesNoCombox({
												fieldLabel:'可做预定',
												hiddenName:'scheduleDoCondi',
												anchor : '100%'
											})]
										}, {
											items : [new Ext.ffc.YesNoCombox({
												fieldLabel:'可做试刀',
												hiddenName:'tryCutDoCondi',
												anchor : '100%'
											})]
										}]
							}],
					buttons : [{
						text : '保存',
						handler : function() {
							// 当前窗口
							var _addCustomersWindow = this.ownerCt.ownerCt;
							var _formUrl = _addCustomersWindow.formUrl;
							var addForm = _addCustomersWindow.findByType('form')[0].getForm();
							if(!addForm.isValid())return;
							var customersFormInfo = Ext.util.JSON.encode(addForm.getValues());
							Ext.Ajax.request({
										url : PATH + _formUrl,
										params : {
											customersFormInfoPar : customersFormInfo
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
												_addCustomersWindow.fireEvent('customerAddSuccess');
												_addCustomersWindow.close();
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

									});

						}

					}, {
						text : '取消',
						handler : function() {
							this.ownerCt.ownerCt.close();
						}
					}]

				});
		this.addEvents('customerAddSuccess');
	}

});
