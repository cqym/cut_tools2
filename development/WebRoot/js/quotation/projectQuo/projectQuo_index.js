
/**
 * 定义命名空间
 */
Ext.namespace('Ext.zhj.qutation.projectQuo');
Ext.namespace('Ext.zhj.customer');

// *******************客户信息**********************
Ext.zhj.customer.QuoInfoFormRecord = Ext.data.Record.create([{
			name : 'customerCode'
		}, {
			name : 'customerName'
		}, {
			name : 'cusContactPerson'
		}, {
			name : 'customerFax'
		}, {
			name : 'customerPhone'
		}, {
			name : 'id'
		}, {
			name : 'customerId'
		}, {
			name : 'overallRebate'
		}, {
			name : 'editorName'
		}, {
			name : 'userName'
		}]);
Ext.zhj.customer.CustomerGrid = Ext.extend(Ext.grid.GridPanel, {
			/**
			 * 请求地址
			 * 
			 * @type String
			 */
			storeUrl : '',
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);

				Ext.zhj.customer.CustomerGrid.superclass.constructor.call(this,
						{
							title : this['title'] == null
									? '客户信息列表'
									: this['title'],
							hideHeaders : false,// 隐藏表头。
							store : new Ext.data.JsonStore({
										url : this.storeUrl,
										remoteSort : true,
										totalProperty : 'totalProperty',
										root : 'customer',
										fields : ['customerCode',
												'customerName',
												'contactPersonFirst',
												'phoneFirst', 'faxFirst',
												'contactPersonSec', 'phoneSec',
												'faxSec', 'ownContactPerson',
												'degreeCode', 'email',
												'contractAddress', 'postcode',
												'comAdress', 'bank',
												'accountNumber', 'taxCode',
												'homePage', 'memo', 'id']
									}),
							// sm : sm,
							columns : [// sm,
							{
										header : '客户编号',
										sortable : true,
										dataIndex : 'customerCode'
									}, {
										header : '客户名称',
										sortable : true,
										dataIndex : 'customerName'
									}, {
										header : '联系人1',
										sortable : true,
										dataIndex : 'contactPersonFirst'
									}, {
										header : '电话1',
										sortable : true,
										dataIndex : 'phoneFirst'
									}, {
										header : '传真1',
										sortable : true,
										dataIndex : 'faxFirst'
									}, {
										header : '联系人2',
										sortable : true,
										dataIndex : 'contactPersonSec'
									}, {
										header : '电话2',
										sortable : true,
										dataIndex : 'phoneSec'
									}, {
										header : '传真2',
										sortable : true,
										dataIndex : 'faxSec'
									}, {
										header : '我方联系人',
										sortable : true,
										dataIndex : 'ownContactPerson'
									}, {
										header : '客户等级',
										sortable : true,
										dataIndex : 'degreeCode'
									}, {
										header : 'Email',
										sortable : true,
										dataIndex : 'email'
									}, {
										header : '合同地址',
										sortable : true,
										dataIndex : 'contractAddress'
									}, {
										header : '邮编',
										sortable : true,
										dataIndex : 'postcode'
									}, {
										header : '通讯地址',
										sortable : true,
										dataIndex : 'comAdress'
									}, {
										header : '开户银行',
										sortable : true,
										dataIndex : 'bank'
									}, {
										header : '账号',
										sortable : true,
										dataIndex : 'accountNumber'
									}, {
										header : '税号',
										sortable : true,
										dataIndex : 'taxCode'
									}, {
										header : '主页',
										sortable : true,
										dataIndex : 'homePage'
									}, {
										header : '备注',
										sortable : true,
										dataIndex : 'memo'
									}, {
										header : 'id',
										dataIndex : 'id',
										hidden : true
									}],
							selModel : new Ext.grid.RowSelectionModel({
										singleSelect : true,
										listeners : {
											'rowselect' : {
												fn : function(_sel, _index, _r) {
													this.fireEvent('rowselect',
															_r)// 必须给此方法附加一个对象,否则将出错．this是CustomerGrid本身
												},
												scope : this
											}
										}
									}),
							width : 1090,
							height : 350,
							bodyStyle : 'width:100%',
							iconCls : 'icon-grid',
							stripeRows : true,
							viewConfig : {
								// forceFit : true,
								// autoFill : true,
								deferEmptyText : false,
								emptyText : '无客户信息！'
							},
							enableHdMenu : false,
							listeners : {
								'rowdblclick' : function() {
									this.fireEvent('RowDblClickSuccess');
								},
								scope : this
							}
						})
				this.addEvents('RowDblClickSuccess');
			},

			/**
			 * 获取所选取的记录。如果没有选择，抛出一个异常。
			 * 
			 * @return {} Record
			 */
			getSelected : function() {
				var _sm = this.getSelectionModel();

				if (_sm.getCount() == 0) {
					throw Error('请选择客户后再进行操作！');
				} else {
					return _sm.getSelected();
				}
			}
		})

Ext.zhj.customer.CustomerWindow = Ext.extend(Ext.Window, {
	isCopy : false,
	quoRecord : null,
	grid : null,
	paramRecord : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.grid = new Ext.zhj.customer.CustomerGrid({
					storeUrl : PATH + "/generalQuo/getCustomerAction.do"
				});
		var _window = this;
		this.grid.on('RowDblClickSuccess', function() {
					_window.buttons[0].fireEvent('click');
				});
		this.paramRecord = new Ext.zhj.customer.QuoInfoFormRecord();
		Ext.zhj.customer.CustomerWindow.superclass.constructor.call(this, {
			title : '客户信息',
			width : 1100,
			height : 450,
			closable : true,
			draggable : true,
			resizable : true,
			layout : 'fit',
			items : this.grid,
			closeAction : 'hide',
			listeners : {
				'render' : function() {
					// grid.render();
					var _customerStore = this.grid.getStore();
					_customerStore.on({
								"beforeLoad" : function() {
									_customerStore.baseParams.searchStr = Ext.util.JSON
											.encode(this.paramRecord);
								},
								scope : this
							});
				},
				scope : this
			},
			buttons : [{
				text : "确定",
				listeners : {
					'click' : function() {
						var _windowDD = this;
						var myRecord = null;
						try {
							var record = _windowDD.grid.getSelected();
							if(Ext.isEmpty(record.get('ownContactPerson'))) {
								Ext.Msg.show({
									title:'信息提示',
									msg: '请添加我方联系人！',
									buttons: Ext.Msg.OK,
									width : 200,
									icon: Ext.MessageBox.INFO
								});
								return;
							}
							myRecord = new Ext.zhj.customer.QuoInfoFormRecord({
										customerCode : record
												.get("customerCode")
												+ "-"
												+ record.get("customerName"),
										cusContactPerson : record
												.get("contactPersonFirst"),
										customerPhone : record
												.get("phoneFirst"),
										customerFax : record.get("faxFirst"),
										customerId : record.get("id"),
										editorName : _editerName,
			    						userName : record.get("ownContactPerson")
									});
						} catch (e) {
							Ext.Msg.show({
										title : '信息提示',
										msg : '请选择客户信息！',
										buttons : Ext.Msg.OK,
										width : 200,
										icon : Ext.MessageBox.INFO
									});
							return;
						}

						_windowDD.hide();
						
						if(this.isCopy) {
							var _quoRecord = this.quoRecord.copy();
							_quoRecord.data.status=0;
							Ext.apply(_quoRecord.data, myRecord.data);
							_quoRecord.data.quotationCode="";
							
							var addWindow = new ProQuomanager.AddWindow({
										customerRecord : myRecord,
										quoId : this.quoRecord.get('id'),
										isCopy : this.isCopy,
										isEditor : this.isEditor
									});
							addWindow.on('quoInfoSaveSuccess', function() {
										_windowDD.fireEvent('quoInfoSaveSuccess');
									});
							addWindow.isCopy=true;
							addWindow.show();
							addWindow.northPanel.setValues(_quoRecord);
						} else {
						
							// 新增报价单传递报价单ID为'00000'表示不存在
							var addWindow = new ProQuomanager.AddWindow({
										customerRecord : myRecord,
										quoId : 00000,
										isEditor : this.isEditor
									});
							addWindow.on('quoInfoSaveSuccess', function() {
										_windowDD.fireEvent('quoInfoSaveSuccess');
									});
							addWindow.show();
							addWindow.northPanel.sallerCombox.store.load();
							addWindow.northPanel.currCombox.store.load();// 加载币别
							addWindow.northPanel.taxRateCombox.store.load();//加载税率
							addWindow.northPanel.setValues(myRecord);
							addWindow.northPanel.quoDateField.setValue(new Date());
						}

					},
					scope : this
				},
				scope : this
			}, {
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			// 搜索栏
			tbar : [new Ext.form.Label({
								html : "&nbsp;客户编号:&nbsp;"

							}), new Ext.form.TextField({
						name : 'customerCode',
						id : 'customerCode',
						listeners : {
							'specialkey' : function(field, e) {
			            		if(e.getKey() == e.ENTER) {
			            			this.fireEvent('change');
			            			this.ownerCt.ownerCt.grid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
			            		}
			            	},
							'change' : function() {
								this.ownerCt.ownerCt.paramRecord.customerCode = this
										.getValue();
							}
						}

					}), '-', new Ext.form.Label({
								html : "客户名称:&nbsp;"

							}), new Ext.form.TextField({
						name : 'customerName',
						id : 'customerName',
						listeners : {
							'specialkey' : function(field, e) {
			            		if(e.getKey() == e.ENTER) {
			            			this.fireEvent('change');
			            			this.ownerCt.ownerCt.grid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
			            		}
			            	},
							'change' : function() {
								this.ownerCt.ownerCt.paramRecord.customerName = this
										.getValue();
							}
						}

					}), '-', {
						text : '搜索',
						iconCls : 'icon-search',
						listeners : {
							'click' : function() {
								this.grid.getStore().reload({params : {start : 0, limit : PAGESIZE}});
							},
							scope : this
						}

					}, '-', {
						text : '重置',
						iconCls : 'icon-reset',
						listeners : {
							'click' : function() {
								var cusNameCMP = Ext.getCmp("customerName");
								var cusCodeCMP = Ext.getCmp("customerCode");
								cusNameCMP.reset();
								cusNameCMP.fireEvent("change");
								cusCodeCMP.reset();
								cusCodeCMP.fireEvent("change");

							},
							scope : this
						}

					}],
			// 分页信息栏
			bbar : new Ext.PagingToolbar({
						pageSize : PAGESIZE,
						emptyMsg : "没有记录",
						displayInfo : true,
						displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
						store : this.grid.getStore()
					})
		})
		this.addEvents('quoInfoSaveSuccess');
	}
})

// *****************项目报价单*******************
/**
 * 项目报价单搜索
 * 
 * @class Ext.zhj.qutation.projectQuo.SearchForm
 * @extends Ext.FormPanel
 */
Ext.zhj.qutation.projectQuo.SearchForm = Ext.extend(Ext.FormPanel, {
			statusCombox : null,
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.apply(this, _cfg);
				this.statusCombox = new StatusCombox({x:90,y:33, width:170,quoType: 2});
				this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
				var _config = [
					//1
		            {xtype:'label',text: '报价单号:',x:0,y:5,style:this.lableStyle_},
		            {xtype:'textfield', name: 'quotationCode',x:90,y:3, width:170},
		            {xtype:'label',text: '我方负责人:',x:250,y:5,style:this.lableStyle_},
		            {xtype:'textfield',name: 'userName',x:340,y:3, width:170},
		            {xtype:'label',text: '制单人:',x:510,y:5,style:this.lableStyle_},
		            {xtype:'textfield',name: 'editorName',x:600,y:3, width:170},
		            //2
		            {xtype:'label',text: '客户:',x:760,y:5,style:this.lableStyle_},
		            {xtype:'textfield', name: 'customerName',x:850,y:3, width:170},
					{xtype:'label',text: '年度:',x:1010,y:5,style:this.lableStyle_},
					new Ext.ffc.YearComboBox({
							x:1100,y:3, width:80,
							listeners : {
							'specialkey' : function(field, e) {
								if(e.getKey() == e.ENTER)
									this.fireEvent('search',this, this.getValues());
							},scope : this
						}
					}),

		            {xtype:'label',text: '编制日期:',x:250,y:35,style:this.lableStyle_},
		            {xtype:'datefield',name: 'beginDate', format:'Y-m-d',emptyText:'',x:340,y:33, width:170},
		            {xtype:'label',text: '至:',x:510,y:35,style:this.lableStyle_},
		            {xtype:'datefield',name: 'endDate', format:'Y-m-d',emptyText:'',x:600,y:33, width:170},
		            //3
		            {xtype:'label',text: '状态:',x:0,y:35,style:this.lableStyle_},
		            this.statusCombox,
		            {xtype:'label',text: '生成合同:',x:760,y:35,style:this.lableStyle_},
			          new TransferContractCombox({x:850,y:33, width:170,quoType:2,
			          	listeners : {
				            	'specialkey' : function(field, e) {
				            		if(e.getKey() == e.ENTER)
				            			this.fireEvent('search',this, this.getValues());
				            	},scope : this
				            }})
				    
				];
				Ext.zhj.qutation.projectQuo.SearchForm.superclass.constructor
						.call(this, {
									labelAlign : 'right',
									buttonAlign : 'right',
									border : false,
									bbar : ['->',{
							           		text : "搜  索",
							           		iconCls : 'icon-search',
							           		handler : function() {
							           			//发布search事件
							           			this.fireEvent('searchQuoInfo',this, this.getValues());
							           		},scope : this
						           		},
						           		'-',{
							           		text : "重  置",
							           		iconCls : 'icon-reset',
							           		handler : function () {
							           			this.getForm().reset();
							           		},scope : this
						       		}],
							        frame:true,monitorValid:false,
							        layout: 'absolute',
							        height : 105,
							        items : _config,

									keys : {
										key:Ext.EventObject.ENTER,
										fn:function(btn,e){
											this.fireEvent('searchQuoInfo', this, this.getValues());
										},scope : this
									}
								});
				/**
				 * 当前对象添加searchConpany方法
				 */
				this.addEvents("searchQuoInfo");
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

function changeStatusZJ(value) {
	switch (value) {
		case 0 :
			return "<span style='color:#606E7F;font-weight:bold;'>编制</span>";
		case 1 :
			return "<span style='color:#A1A09D;font-weight:bold;'>待审批</span>";
		case 2 :
			return "<span style='color:green;font-weight:bold;'>审批通过</span>";
		case 3 :
			return "<span style='color:red;font-weight:bold;'>审批退回</span>";
		case 4 :
			return "<span style='color:#4597E3;font-weight:bold;'>提交合同</span>";
		case 5 :
			return "<span style='color:#2D61B5;font-weight:bold;'>已经生成合同</span>";
		case 6 :
			return "<span style='color:#2D61B5;font-weight:bold;'>提交预订</span>";
	}
}
function changeUrgentLevel(value){
	switch(value){
		case 0 :
			return "<span style='color:#606E7F;font-weight:bold;'>一般</span>";
			break;
		case 1 :
			return "<span style='color:red;font-weight:bold;'>紧急</span>";
			break;
	}

}

function changeOrderExpected(value) {
	switch(value) {
		case 0 : 
		return "<span style='color:#990000;font-weight:bold;'>否</span>";
		case 1 : 
		return "<span style='color:#339933;font-weight:bold;'>是</span>";
	}
}

Ext.zhj.onClickDetail = function(_id) {

	var _quoInfoDetailWindow = new Ext.ftl.QuoInfoDetailWindow({
				quoId : _id
			});
	_quoInfoDetailWindow.show();
}

// 查看详细渲染器
Ext.zhj.detailQuoWindow = function(quoId) {
	return '<a href="#" class="[color=red]demoClass[/color]" onclick="Ext.zhj.onClickDetail(\''
			+ quoId + '\')">查看</a>'
}

Ext.zhj.onSlaveClick = function(_id, _busType) {
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : _busType});
	slaveWindow.show();
}

Ext.ftl.onViewImpQuo = function(_quoStr) {
	var quoInfoViewWindow = new Ext.ftl.QuoInfoViewWindow({quoIdStr : _quoStr});
	quoInfoViewWindow.show();
}

/**
 * @class Ext.zhj.qutation.projectQuo.GridList
 * @extends Ext.grid
 */

Ext.zhj.qutation.projectQuo.GridList = Ext.extend(Ext.grid.GridPanel, {
	isAddHide : true,isModifyHide : true,isDelHide : true,isSubmitHide : true,
	isContractHide : true, isDetailHide : true, isToExcelHide : true,
	isWillOrderHide: true,isUploadHide : true,isTaxPriceEdit : false,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer({
					renderer:function(value,metadata,record,rowIndex){
						var record_start = record.store.lastOptions.params.start
					    return record_start + 1 + rowIndex;
					}
				}), sm, {
					header : '报价单编号',
					width : 170,
					sortable : true,
					dataIndex : 'quotationCode'
				}, {
					header : '客户名称',
					sortable : true,width : 160,
					dataIndex : 'customerName'
				}, {
					header : '状态',
					dataIndex : 'status',
					sortable : true,
					width : 80,
					renderer : changeStatusZJ
				},
				{header : '生成合同',dataIndex : 'transferContract',sortable: true,
					renderer : function(val){
				  	switch(val) {
							case 0 :
							    return "<span style='color:#A1A09D;font-weight:bold;'>未转合同</span>";
							case 1 : 
							    return "<span style='color:#dac60e;font-weight:bold;'>部分转合同</span>";
							case 2 : 
							    return "<span style='color:green;font-weight:bold;'>已转合同</span>";
						}
				  }
				}, 
				 {
					header : '紧急程度',
					dataIndex : 'urgentLevel',
					sortable : true,
					width : 70,
					renderer : changeUrgentLevel
				},
				{header : '合同编号', width : 180,dataIndex : 'contractCode',sortable: true},
				{
					header : '报价日期',
					sortable : true,
					dataIndex : 'quotationDate',
					width : 90
				}, {
					header : '我方负责人',
					width : 80,
					sortable : true,
					dataIndex : 'userName'
				}, {
					header : '币别',
					sortable : true,width : 60,
					dataIndex : 'currencyName',
					width : 50
				}, {
					header : '最终金额',
					width : 70,
					sortable : true,
					dataIndex : 'finalMoney'
				}, {
					header : '编制人',
					width : 50,
					sortable : true,
					dataIndex : 'editorName'
				},{header : '编制时间',dataIndex : 'editTimeStr',sortable: true,width : 110},
				{
					header : '备注',
					sortable : true,width : 150,
					dataIndex : 'memo'
				},
				{header:'附件',width:50,dataIndex:'slaveFile', hidden : this.quoType == 4 ? true : false, renderer : function(colValue, node, data) {
	        		if(colValue > 0) {
	        			var id = data.id
	        			var str = '<a href=\"#\" onclick=Ext.zhj.onSlaveClick("' + id + '",5)><span style="color:blue;font-weight:bold;">查看</span></a>';
						return str;
	        		}
	        	}},
				{
					header : "审批信息",
					width : 70,
					sortable : true,
					dataIndex : 'auditInfor',
					renderer : function(value, cellmeta, record, rowIndex,
							columnIndex, store) {
						var str = "<a href=\"javascript:onAuditInfor(this,\'"
								+ record.get('id') + "\');\">查看</a>";
						return str;
					}
				}, 
				{header: "预订报价单号", width: 90, dataIndex: 'impToQuoCode',
					renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
						if(!Ext.isEmpty(value)) {
							var str = '<a href=\"#\" onclick=Ext.ftl.onViewImpQuo("'+ value + '")><span style="color:blue;font-weight:bold;">查看</span></a>';
							return str;
						}
					}
				},{
					header : '卖方',
					sortable : true,
					hidden : true,
					dataIndex : 'sellerName'
				}, {
					header : '报价开始日期',
					sortable : true,hidden : true,
					dataIndex : 'validStartDate',
					width : 100
				}, {
					header : '报价结束日期',
					sortable : true,hidden : true,
					dataIndex : 'validEndDate',
					width : 100
				}, {
					header : '交货方式',
					sortable : true,hidden : true,
					dataIndex : 'deliveryType'
				}, {
					header : '货品金额',
					width : 70,hidden : true,
					sortable : true,
					dataIndex : 'productMoney'

				}, {
					header : '税率',
					width : 50,hidden : true,
					sortable : true,
					dataIndex : 'taxRate'
				}, {
					header : '税金',
					width : 70,hidden : true,
					sortable : true,
					dataIndex : 'taxMoney'
				}, {
					header : '税价合计',
					width : 70,hidden : true,
					sortable : true,
					dataIndex : 'totalMoney'
				}, {
					header : '付款条件',
					sortable : true,hidden : true,
					dataIndex : 'paymentCondition'
				}, {
					header : 'ID',
					hidden : true,
					dataIndex : 'id',
					width : 0
				}, {
					header : '客户联系人',
					hidden : true,
					dataIndex : 'cusContactPerson',
					width : 0
				}, {
					header : '客户编号',
					hidden : true,
					dataIndex : 'customerCode',
					width : 0
				}, {
					header : '客户电话',
					sortable : true,hidden : true,
					dataIndex : 'customerPhone'
				}, {
					header : '客户传真',
					sortable : true,hidden : true,
					dataIndex : 'customerFax'
				}, {
					header : '交货期限',
					dataIndex : 'deliveryDate',
					hidden : true,
					width : 0
				}, {
					header : '整单折扣',
					sortable : true,hidden : true,
					width : 70,
					dataIndex : 'overallRebate'
				}, {
					header : '查看详细',
					width : 0,
					dataIndex : 'id',
					renderer : Ext.zhj.detailQuoWindow,
					hidden : true
				},{header : '币别编号',hidden : true,dataIndex : 'currency',sortable: true},
				{header : '是否预订', width : 60, dataIndex : 'willOrderExpected',
					renderer : changeOrderExpected, hidden : true,sortable: true},
				{header : 'willOrderExpected', dataIndex : 'willOrderExpected', hidden : true},
				{header : 'willFormalDate', dataIndex : 'willFormalDate', hidden : true}

		]);
		// Grid用户界面
		var gv = new Ext.grid.GridView({
					deferEmptyText : false,
					emptyText : '无报价信息！'
				});
		// jsonStore 数据
		var ds = new Ext.data.JsonStore({
					url : PATH + '/projectQuo/listAction.do',
					root : 'quoList',
					totalProperty : 'totalProperty',
					remoteSort : true,
					fields : ['quotationCode', 'status', 'customerName',
							'sellerName', 'editorName', 'quotationDate',
							'deliveryType', 'currencyName', 'productMoney',
							'taxRate', 'taxMoney', 'totalMoney',
							'paymentCondition', 'memo', 'id',
							'cusContactPerson', 'customerCode', 'phoneFirst',
							'deliveryDate', 'overallRebate', 'finalMoney',
							'customerFax', 'customerPhone', 'userName','validEndDate',
							'validStartDate','urgentLevel','currency',
							'willOrderExpected','willFormalDate', 'slaveFile','editTimeStr',
							'contractCode', 'impToQuoCode']
				});
		ds.on({
					'beforeload' : function() {
						if (this.searchRecord != null) {
							var _searchStr = Ext.util.JSON
									.encode(this.searchRecord.data);
							ds.baseParams.searchStr = _searchStr;
							// alert('我被调用了orderType = ' + _searchStr);
						}
					},
					scope : this
				});
		var tbar = new Ext.Toolbar({
			items : [{
						text : "新增报价单",
						iconCls : 'icon-add',
						hidden : this.isAddHide,
						name : 'addQuo',
						handler : function() {
							this.addProQuo();
						},
						scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isDetailHide
					}, {
						text : '查看报价',
						iconCls : 'icon-detail',
						hidden : this.isDetailHide,
						listeners : {
							'click' : function() {
									if (this.isSelectedRecord()) {
										var array = this.getSelections();
										if (array.length > 1) {
											Ext.Msg.show({
														title : '错误提示',
														msg : '只能对一条记录进行查看操作！',
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO,
														width : 300
													});
											return;
										} else {
											var selRecord = this.getSelectionModel()
													.getSelected();
											Ext.zhj.onClickDetail(selRecord.id);
										}
									}
							},scope:this
						}
					},{
						xtype:'tbseparator',
						hidden : this.isModifyHide
					}, {
						text : "修改报价",
						iconCls : 'icon-modify',
						hidden : this.isModifyHide,
						name : 'editQuo',
						handler : function() {
							
							if (this.isSelectedRecord()){
								var array = this.getSelections();
									
									if(array.length > 1 ){
										Ext.Msg.show({
														title : '信息提示',
														msg : '只能对一条信息进行此操作！',
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO,
														width : 300
										});
										return false;
									}else{
										var status = array[0].get('status');
										
										if(status == 5 || status == 1) {
											Ext.Msg.show({
												title:'错误提示',
												msg: "该状态报价单，不允许修改!",
												buttons: Ext.Msg.OK,
												icon: Ext.MessageBox.ERROR
											});
											return;
										}
										this.editProQuo();
										/*if((status != 5) ||(status != 1) ){
											this.editProQuo();
										}else{
											Ext.Msg.show({
														title : '错误提示',
														msg : '该状态下的报价单不能被修改！',
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO,
														width : 300
													});
										    return false;
										}*/
									}
							}
						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isCopyHide
					},{
						text : '复制报价',
						hidden : this.isCopyHide,
						iconCls : 'icon-add',
						handler : function() {
							fn : this.onCopySubmit();
						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isDelHide
					}, {
						text : "删除报价",
						iconCls : 'icon-delete',
						hidden : this.isDelHide,
						name : 'deleteQuo',
						handler : function() {
							if (this.isSelectedRecord()) {
								var array = this.getSelections();
								for (var i = 0; i < array.length; i++) {
									if ((array[i].get('status') != 0)
												&& (array[i].get('status') != 3)) {
											Ext.Msg.show({
												title : '错误提示',
												msg : "只能删除编制、审批退回状态报价单信息!",
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO,
												width : 300
											});
											return;
										}
								}
								
								Ext.MessageBox.confirm('信息提示', '确定删除记录吗?', function(button, text) {
									if (button == "yes") {
										this.deleteProQuo();
									}
								}, this);
							}
						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isSubmitHide
					}, {
						text : "提交审批",
						iconCls : 'icon-submit',
						hidden : this.isSubmitHide,
						name : '',
						handler : function() {
							if (this.isSelectedRecord()) {
								var array = this.getSelections();
								if (array.length > 0) {
									for (var i = 0; i < array.length; i++) {
										if ((array[i].get('status') != 0)
												&& (array[i].get('status') != 3)) {
											Ext.Msg.show({
												title : '错误提示',
												msg : '只能提交编制、退回状态报价单到评审！',
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO,
												width : 300
											});
											return;
										}
									}

									Ext.Msg.confirm('信息提示', '确定提交评审吗?',function(button, text) {
										if (button == "yes") {
											this.onProSubmitAudit();
										}
									}, this);
								}
							}
						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isContractHide
					},{
						text : "提交合同",
						iconCls : 'icon-submit',
						hidden : this.isContractHide,
						name : '',
						handler : function() {
							if (this.isSelectedRecord()) {
								var array = this.getSelections();
								if (array.length > 0) {
									for (var i = 0; i < array.length; i++) {
										if (array[i].get('status') != 2) {
											Ext.Msg.show({
														title : '错误提示',
														msg : '未通过审核报价单不能提交合同！',
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO,
														width : 300
													});
											return;
										}
									}
									Ext.Msg.confirm('信息提示', '确定提交合同吗?',
											function(button, text) {
												if (button == "yes") {
													this.onSubmitContract();
												}
											}, this);
								}
							}

						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : this.isUploadHide
					}, {
						text : '附件上传',
						hidden : this.isUploadHide,
						iconCls : 'icon-submit',
						handler : function() {
							fn : this.onSlaveClick(5);
						},
						scope : this
					}, {
						xtype:'tbseparator',
						hidden : this.isToExcelHide
					}, {
						text : '导出Excel',
						iconCls : 'icon-excel',
						hidden : this.isToExcelHide,
						handler : function() {
							fn : this.onExcelClick();
						},
						scope : this
					},{
						xtype:'tbseparator',
						hidden : false
					},{
						text:'导出列表',
						hidden : false,
						iconCls:'icon-excel',
						listeners: {
							'click' : function(){	
							    /*var _searchStr = Ext.util.JSON.encode(this.searchRecord);
								//alert(_searchStr);
								window.open(PATH + '/generalQuo/excelAction.do?method=quoListToExcel&quotationType=1&searchStr=' + _searchStr);
								*/
								var values = {};
								if(this.searchRecord){
								    values = this.searchRecord.data;
								}
								var para = [];
								for(var i in values){
									if(values[i] && values[i] != ''){
									   para.push(i + "=" + values[i]);
									}
								}
								para.push("quotationType=1");
								new Ext.ffc.ExpTempleteSelectWindow({
									templeteType:'29',
									exportDataMethod:function(tempId){
										window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertListExcel&" + para.join('&') + "&tempId=" + tempId);
									}
								}).show();
							},scope : this
				 		}
					}]
		});

		Ext.zhj.qutation.projectQuo.GridList.superclass.constructor.call(this,
				
					{
					bodyStyle : 'width:100%',
					height : Ext.getBody().getHeight() - 223,
					enableHdMenu : true,
					border : false,
					stripeRows : true,
					view : gv,
					ds : ds,
					sm : new Ext.grid.CheckboxSelectionModel(),
					selModel : new Ext.grid.RowSelectionModel({
								singleSelect : false
							}),
					bbar : new Ext.PagingToolbar({
								pageSize : PAGESIZE,
								emptyMsg : "没有记录",
								displayInfo : true,
								displayMsg : '显示第 {0} - {1} 条 共 {2} 条',
								store : ds
							}),
					tbar : tbar,
					cm : cm,
					listeners : {
						rowdblclick : function(grid, rowIndex, e){
						    grid.getTopToolbar().find('text','查看报价')[0].fireEvent('click',null);
					    }
					}
				});
	},
	
	//附件管理
	onSlaveClick : function(_busType) {
		try {
			var selNode = this.getSelected();
			var slaveWindow = new Slave.SlaveManageWindow({busId : selNode.id, busType : _busType});
			slaveWindow.listPanel.upWindow.swfUploadPanel.on('allUploadsComplete', function() { 
				this.reload();
			},this);
			slaveWindow.show();
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	/**
	 * 重新载入表单数据
	 */
	reload : function() {
		this.getStore().reload();
	},
	
	/**
	 * 为搜索条件设值
	 * 
	 * @param {}
	 *            _value
	 */
	setSearchStr : function(_value) {
		this.searchRecord = _value;
	},
	
	/**
	 * 获取所选取的记录。如果没有选择，抛出一个异常。
	 * @return {} Record
	 */
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择一条记录进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	/**
	 * 返回已选择记录集
	 */
	getSelections : function() {
		var _sm = this.getSelectionModel();

		if (_sm.getCount() == 0) {
			throw Error('请选择一条记录进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	/**
	 * 将数组转化成JSON串
	 * 
	 * @param {}
	 *            _arr
	 * @return {}
	 */
	arry2Json : function(_arr) {
		var str = "[";
		for (var i = 0; i < _arr.length; i++) {
			str += "{ id : '" + _arr[i].get("id") + "'}";

			if (i == _arr.length - 1) {
				break;
			} else {
				str += ","
			}
		}
		str += "]"
		return str;
	}

	,
	/**
	 * 是否已经选择记录，retrun 'true' or 'false'
	 */
	isSelectedRecord : function() {
		var _sm = this.getSelectionModel();
		var _record = _sm.getSelected();
		if (_sm.getCount() == 0) {
			Ext.MessageBox.alert('错误提示', '请选择一条报价信息进行操作！');
			return false;
		} else {
			return true;
		}
	},

	/**
	 * 添加新的报价单
	 */
	addProQuo : function() {
		// alert('新增加！');
		var customerWindow = new Ext.zhj.customer.CustomerWindow({isEditor : this.isTaxPriceEdit});
		customerWindow.on('quoInfoSaveSuccess', function() {
					this.getStore().reload();
				}, this);
		customerWindow.grid.getStore().load({
					params : {
						start : 0,
						limit : PAGESIZE
					}
				});
		customerWindow.show();
	},

	/**
	 * 编辑报价信息
	 */
	editProQuo : function() {
		if (this.isSelectedRecord()) {
			// 报价单主信息记录
			var record = null;
			var customerId = null;
			var _copyRecord = null;
			var _customerCode = null;
			try {
				record = this.getSelectionModel().getSelected();
				// 根据用户编号获取用户ID
				_copyRecord = record.copy();
				var _cusCode = record.get("customerCode");
				var _customerCode = record.get("customerCode") + "-"
						+ record.get("customerName");
				_copyRecord.set("customerCode", _customerCode);
				Ext.Ajax.request({
					url : PATH + '/generalQuo/getCusByCuscodeAction.do',
					params : {
						customerCode : _cusCode
					},
					success : function(response) {
						var responseArray = Ext.util.JSON
								.decode(response.responseText);

						if (responseArray.success == true) {
							//customerId = responseArray.customerId;
							Ext.apply(_copyRecord.data, {customerId : responseArray.customerId});
						} else {

						}
					}
				});
			} catch (e) {
				Ext.Msg.show({
					title : '信息提示',
					msg : e.description,
					buttons : Ext.Msg.OK,
					width : 200,
					icon : Ext.MessageBox.INFO
				});
			}

			var editWindow = new ProQuomanager.EditWindow({
				customerRecord : _copyRecord,
				quoId : record.get('id'),
				isEditor : this.isTaxPriceEdit
			});
			editWindow.on('quoInfoSaveSuccess', function() {
				this.getStore().reload();
			}, this);
			editWindow.show();

			// 设置报价单基本信息
			//editWindow.northPanel.setValues(_copyRecord);
			
			var _form = editWindow.northPanel;
			
			Ext.Ajax.request({
				url: PATH + '/generalQuo/getQuoAction.do',
				params: { quoId: record.get('id') },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if(responseArray.success == true){
						_form.setValues(new Ext.data.Record(responseArray.data));
					}
				}
			});
		}
	},

	//复制报价单----------------------------------------------------------------
	onCopySubmit : function() {
		var _sm = this.getSelectionModel();
		if(!_sm.hasSelection() || _sm.getCount() > 1) {
			Ext.MessageBox.alert('错误提示', '请选择一条记录进行操作！');
		} else {
			this.handlerCopy();
		}
	},
	
	handlerCopy : function() {
		
		var record = null;
		record = this.getSelected();
		_copyRecord = record.copy();
		var _cusCode = record.get("customerCode");
		var _customerCode = record.get("customerCode") + "-" + record.get("customerName");
		_copyRecord.set("customerCode", _customerCode);
		
		var customerWindow = new Ext.zhj.customer.CustomerWindow({isCopy : true, quoRecord : record});
		customerWindow.on('quoInfoSaveSuccess', function() {
					this.getStore().reload();
				}, this);
		customerWindow.grid.getStore().load({
					params : {
						start : 0,
						limit : PAGESIZE
					}
				});
		customerWindow.show();
	},
	//提交预订
	onSubmitOrderExpected : function() {
		var _sm = this.getSelectionModel();
		if(!_sm.hasSelection()) {
			Ext.MessageBox.alert('错误提示', '请选择一条记录进行操作！');
		} else {
			var array = this.getSelections();
			if (array.length > 0) {
				for(var i = 0; i < array.length; i++) {
					var status = array[i].get('status');
					var willOrderExpected = array[i].get('willOrderExpected');
					if((status != 0 && status != 2) || willOrderExpected != 1) {
						Ext.Msg.show({
							title:'错误提示',
							msg: "请只选择状态为编制或审批通过的预订报价单!",
							width : 300,
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
						});
						return;
					}
				}
				Ext.Msg.confirm('信息提示', '确定提交预订吗?', this.handlerSubmitOrderExpected, this);
			}
		}
	},
	
	handlerSubmitOrderExpected : function(_btn) {
		if(_btn == 'yes') {
			var array = null;
			try {
				array = this.getSelections();
				var paramStr = "";
				if(array.length > 0) {
					paramStr = this.arry2Json(array);
				}
				//alert(paramStr);
				Ext.Ajax.request({
					url: PATH + '/generalQuo/submitOrderExpectedAction.do?method=updateQuotationStatus',
					params: { quoId: paramStr },
					success : function(response) {
					if(response.responseText != "" && response.responseText != null){
						Ext.Msg.show({
							title:'错误提示',
							msg: response.responseText,
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.ERROR
						});
					} else {
						Ext.Msg.show({
							title:'成功提示',
							msg: '提交预订成功！',
							buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
							this.getStore().reload();
							Ext.ffc.sendMsg2Server();//向服务器发送消息，进行广播
						} 
					},scope : this
				});
								
			} catch(e) {
				Ext.MessageBox.alert('错误提示', e.description);
			}
		}
	},
	
	/**
	 * 删除报价信息
	 */
	deleteProQuo : function() {
		var _store = this.getStore();
		var array = null;
		try {
			array = this.getSelections();
			var paramStr = "";
			if (array.length > 0) {
				paramStr = this.arry2Json(array);
			}
			Ext.Ajax.request({
						url : PATH + '/projectQuo/deleteProjectQuoAction.do',
						params : {
							quoId : paramStr
						},
						success : function(response) {
							var responseArray = Ext.util.JSON
									.decode(response.responseText);
							if (responseArray.success == true) {
								Ext.Msg.show({
											title : '成功提示',
											msg : responseArray.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
								_store.reload();
								Ext.ffc.sendMsg2Server();// 向服务器发送消息，进行广播
							} else {
								Ext.Msg.show({
											title : '错误提示',
											msg : responseArray.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.ERROR
										});
							}
						}
					});

		} catch (e) {
			Ext.MessageBox.alert('错误提示', e.description);
		}

	},

	/**
	 * 提交评审报价信息
	 */
	onProSubmitAudit : function() {
		var _store = this.getStore();
		var array = null;
		try {
			array = this.getSelections();
			var paramStr = "";
			if (array.length > 0) {
				paramStr = this.arry2Json(array);
			}
			Ext.Ajax.request({
						url : PATH + '/projectQuo/submitAuditAction.do',
						params : {
							quoId : paramStr
						},
						success : function(response) {
							if (response.responseText != ""
									&& response.responseText != null) {
								Ext.Msg.show({
											title : '错误提示',
											msg : response.responseText,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.ERROR
										});
							} else {
								Ext.Msg.show({
											title : '成功提示',
											msg : '提交评审成功！',
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
								_store.reload();
								Ext.ffc.sendMsg2Server();// 向服务器发送消息，进行广播
							}
						}
					});

		} catch (e) {
			Ext.MessageBox.alert('错误提示', e.description);
		}
	},

	/**
	 * 提交合同
	 */
	onSubmitContract : function() {
		var _store = this.getStore();
		var array = null;
		try {
			array = this.getSelections();
			var paramStr = "";
			if (array.length > 0) {
				paramStr = this.arry2Json(array);
			}
			Ext.Ajax.request({
						url : PATH + '/projectQuo/submitContractAction.do',
						params : {
							quoId : paramStr
						},
						success : function(response) {
							if (response.responseText != ""
									&& response.responseText != null) {
								Ext.Msg.show({
											title : '错误提示',
											msg : response.responseText,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.ERROR
										});
								// this.remove();
							} else {
								Ext.Msg.show({
											title : '成功提示',
											msg : '提交合同成功！',
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
								_store.reload();
								Ext.ffc.sendMsg2Server();// 向服务器发送消息，进行广播
							}
						}
					}, this);

		} catch (e) {
			Ext.MessageBox.alert('错误提示', e.description);
		}
	},
	/**
	 * 到处excel
	 */
	onExcelClick : function() {
		if (this.isSelectedRecord()) {
			
			var array = this.getSelections();
			var status = this.getSelected().get('status');
			if (array.length > 0) {
				for (var i = 0; i < array.length; i++) {
					if(status != 2 && status != 4 && status != 5) {
						Ext.Msg.show({
									title : '错误提示',
									msg : '只能导出通过审核的报价单！',
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.INFO,
									width : 300
								});
						return;
					}
				}
				
			}
			
			var record = this.getSelectionModel().getSelected();
			window.open(PATH + '/projectQuo/excelAction.do?quoId='
					+ record.get('id'));
		}

	}
});

/**
 * 项目报价入口
 */
Ext.onReady(function() {
	var searchForm = new Ext.zhj.qutation.projectQuo.SearchForm();
	
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("003" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("003002" == childModule[j].id) {
						var _configArr = childModule[j].children;
						if(_configArr.length > 0) {
							for(var k = 0; k < _configArr.length; k++) {
								if(k != _configArr.length-1)
									_configStr += _configArr[k].url + ",";
								else 
									_configStr += _configArr[k].url + "}"
							}
						} else {
							_configStr += "}"
						}
						break;
					}
				}
				
				break;
			}
		}
		return Ext.decode(_configStr);
	}
	var gridList = new Ext.zhj.qutation.projectQuo.GridList(getConfig());
	
	//监听生成合同事件
	EventMger.on("createdContractEvent",function(){
		var _store = gridList.getStore();
		_store.load({params : {start : 0, limit : PAGESIZE}});
	});
	
	// 监听搜索事件
	searchForm.on({
		'searchQuoInfo' : function(_form, _values) {
			gridList.setSearchStr(_values);
			//首页开始搜索
			gridList.getBottomToolbar().changePage(0);
			//alert('BBBBB');
			gridList.getStore().reload();
		},
		scope : this
	});
    gridList.getStore().baseParams.searchStr = Ext.util.JSON.encode({year:new Date().format('Y')});
	gridList.getStore().load({
		params : {
			start : 0,
			limit : PAGESIZE
		}
	});
	var viewportProQuo = new Ext.Panel({
		width : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight() - 70,
		layout : 'border',
		items : [{
					region : "north",
					height : 130,
					frame : true,
					split: true,
					layout : 'fit',
					collapsible : true,
					margins : '5 5 5 5',
					items : [searchForm]
				}, {

					region : "center",
					height : Ext.getBody().getHeight() - 200,
					collapsible : true,
					margins : '5 5 5 5',
					items : [gridList]

				}]

	});
	viewportProQuo.render("viewportProQuo");
	Ext.ffc.ResizeManager.addResizeObject(viewportProQuo);
})