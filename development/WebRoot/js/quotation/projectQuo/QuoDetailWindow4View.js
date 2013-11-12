
/**
 * 定义命名空间
 */
Ext.namespace('Ext.ftl.quoDetail');

/**
 * 表单数据
 * 
 * @type
 */
Ext.ftl.quoDetail.SimpleForm = Ext.extend(Ext.FormPanel, {
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
		this.isReadOnly = true;
		this.currIdField = new Ext.form.TextField({
			xtype:'hidden',fieldLabel: 'currency',name: 'currency',hidden : true, hideLabel : true
		})
		this.currCombox = new CurrencyCombox({disabled : this.isReadOnly,x:600,y:93,width:170});
		this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,width:170,x:860,y:33});
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
			disabled : this.isReadOnly
		})
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:860,y:213, width:170,validationEvent : false,disabled : this.isReadOnly
		});
		this.exemplarInvoiceField = new Ext.form.Checkbox({
			name: 'exemplarInvoice', x:1090,y:35
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
			{xtype:'label',text: '形式发票:',x:1000,y:35,style:this.lableStyle_},
			this.exemplarInvoiceField,

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
			{xtype:'label',text: '报价有效期:始:',x:0,y:125,style:this.lableStyle_},
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
             {xtype:'textfield' ,name: 'deliveryType', width : 680, readOnly : this.isReadOnly, allowBlank : false,x:90,y:183},
             //8
             {xtype:'label',text: '备注:',x:0,y:215,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:213, width : 680},
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true},
             this.currIdField
		];
		Ext.ftl.quoDetail.SimpleForm.superclass.constructor.call(this, {
			width : this['width'] == null ? 1000 : this['width'],
			labelAlign : 'right',
			buttonAlign : 'right',
			bodyStyle : 'padding:5px;',
			frame : true,
			labelWidth : 70,
			monitorValid : false,
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
	changeUrgentLevel : function(value){
		switch (value) {
			case 0 :
				return "一般";
				break;
			case 1 :
				return "紧急";
				break;
		}
	},
	loadFormDate : function() {
		Ext.Ajax.request({
			url : PATH + '/projectQuo/getQuoInfoByIdAction.do',
			params : {
				quoId : this.quoId
			},
			success : function(response) {
				var responseArray = Ext.util.JSON
						.decode(response.responseText);
				if (responseArray.success == true) {
					var r = new Ext.data.Record(responseArray.data);
					this.setValues(r);
				} else {
					return false;
				}
			},scope : this

		});
	},
	setValues : function(_r) {
		_r.set('status', this.changeStatus(_r.data['status']));
		_r.set('urgentLevel', this.changeUrgentLevel(_r.data['urgentLevel']));
		var _copyRecord = _r.copy();
		_copyRecord.set('customerCode', _r.get("customerCode") + "-" + _r.get("customerName"));
		this.getForm().loadRecord(_copyRecord);
	},

	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	}
})

/**
 * 工序信息
 * 
 * @class Ext.zhj.quoDetail.WorkOrderListZJ
 * @extends Ext.grid.EditorGridPanel
 */
Ext.ftl.quoDetail.WorkOrderList = Ext.extend(Ext.grid.GridPanel, {
	quoId : null,
	productTapPanel : null,
	quoForm : null,//报价单
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		var ds = new Ext.data.JsonStore({
			url : PATH + '/projectQuo/workOrderListAction.do',
			root : 'workOrderList',
			autoLoad : false,
			fields : ['proSortName', 'supportAmount', 'backupAmount',
					'totalMoney', 'machineModel','machineCount','id']
		});
		var _quoid = this.quoId;
		ds.on('beforeload', function() {
			ds.baseParams.quoid = _quoid;
		});

		var _productTapPanel = this.productTapPanel;
		ds.on("datachanged", function() {
			var count = ds.getCount();
			for (var i = 0; i < count; i++) {
				var oldRecord = ds.getAt(i).copy();
				
				var _proTree = Ext.ftl.quoDetail.GetNewTree(oldRecord.get('id'), oldRecord.get('id'));
				/*var _historyPriceGrid = new Ext.ftl.CusSalesProductGrid(); 
				_proTree.on('click', function(_node) {
		    		var _cusName = this.quoForm.getValues().get("customerCode")
		    		var _customerCode = _cusName.substring(0, _cusName.indexOf('-'));
		    		var _store = _historyPriceGrid.getStore();
		    		_store.baseParams.customerCode = _customerCode;
		    		_store.baseParams.pid = _node.attributes.toolsId;
		    		_historyPriceGrid.getStore().load({
						params : {
							start : 0,
							limit : 10
						}
					});
		    	},this)*/
		    	
				_productTapPanel.add({
					title : oldRecord.get('proSortName'),
					iconCls : 'tabs',
					layout : 'border',
					items : [_proTree/*,_historyPriceGrid*/],
					id : oldRecord.get('id'),
					closable : false
				}).show();
			}
		},this);
		Ext.ftl.quoDetail.WorkOrderList.superclass.constructor.call(this, {
			bodyStyle : 'width:100%',
			height : 250,
			enableHdMenu : false,
			border : false,
			stripeRows : true,
			ds : ds,
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
							_productTapPanel.setActiveTab(record.get("id"));
						},
						scope : this
					}
				}
			}),

			cm : new Ext.grid.ColumnModel([
				new Ext.grid.CheckboxSelectionModel(),
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 35
						}), {
					header : '工序名称',
					dataIndex : 'proSortName'
				}, {
					header : '机床型号',
					dataIndex : 'machineModel'

				}, {
					header : '机床台数',
					dataIndex : 'machineCount'

				}, {
					header : '配套刀具套数',
					dataIndex : 'supportAmount'

				}, {
					header : '备用刀具套数',
					dataIndex : 'backupAmount'

				}, {
					header : '小计',
					dataIndex : 'totalMoney'
				}, {
					header : 'ID',
					hidden : true,
					dataIndex : 'id'
			}])
		});
	}
});

/**
 * 工序对应产品列表
 * 
 * @class Ext.zhj.quoDetail.WorkOrderProductTapPanel
 * @extends Ext.TabPanel
 */
Ext.ftl.quoDetail.WorkOrderProductTapPanel = Ext.extend(Ext.TabPanel, {
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.ftl.quoDetail.WorkOrderProductTapPanel.superclass.constructor
				.call(this, {
			resizeTabs : true, // turn on tab resizing
			minTabWidth : 115,
			tabWidth : 135,
			enableTabScroll : true,
			width : 1000,
			height : 230,
			// activeTab: 0,
			defaults : {
				autoScroll : false
			},
			plugins : new Ext.ux.TabCloseMenu()
		});
	}
});

/**
 * 查看报价单详细
 * 
 * @class Ext.zhj.QuoInfoDetailWindow
 * @extends Ext.Window
 */
Ext.ftl.QuoInfoDetailWindow = Ext.extend(Ext.Window, {
	quoId : null,
	simpleForm : null,
	workOrderList : null,
	workOrderProductTapPanel : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.workOrderProductTapPanel = new Ext.ftl.quoDetail.WorkOrderProductTapPanel({region : "center"});
		this.simpleForm = new Ext.ftl.quoDetail.SimpleForm({
					quoId : this.quoId
				});
		// 转载数据
		this.simpleForm.loadFormDate();
		
		this.workOrderList = new Ext.ftl.quoDetail.WorkOrderList({
			quoId : this.quoId,
			productTapPanel : this.workOrderProductTapPanel,
			quoForm : this.simpleForm 
		});
		this.workOrderList.getStore().reload();

		Ext.ftl.QuoInfoDetailWindow.superclass.constructor.call(this, {
			title : "查看项目报价单详细",
			width : 1080,
			height : 600,
			plain : true,
			closable : true,
			closeAction : 'hide',
			constrain : true,
			maximizable : true,
			modal : true,
			layout : "border",
			bbar :[{xtype:'label',html : "审批内容:<font color = 'red'></font>,<font color='green'></font>"}],
			setAuditContent:function(){
				      var _this = this;
		          var busId = _this.quoId;
			    	  	Ext.Ajax.request({
									   url: PATH + '/manage/audit/auditContentAction.do',
									   params: { busId: busId,m:'findAllAuditContentList' },
									   success: function(response){
											  eval("var arr = " + response.responseText);
											  var html = [];
											  html.push('<div>审批内容：');
											  for(var i = 0;i < arr.length;i++){
											  	html.push('<span style="color:green">');
											  	html.push(arr[i].auditContentName);
											  	html.push('</span>(<span style="color:red">');
											  	if(arr[i].auditPerson == ''){
											  	    html.push('待审');
											    }else{
											        html.push(arr[i].auditPerson);
											    }
											  	html.push('</span>)&nbsp;&nbsp;');
											  }
											  html.push('</div>');
											  _this.bbar.update(html.join(''));//setTitle('dfdsfds','');
									   }
									});
			},scope:this,
			buttons : [{
				text : "关闭",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			items : [{
					title : '项目报价',
					region : "north",
					frame : true,
					height : 290,
					layout : 'fit',
					collapsible : true,
					split:true,
					margins : '2 2 0 2',
					items : [this.simpleForm]
				}, {
					region : "center",
					collapsible : true,
					height : 350,
					split:true,
					layout : 'border',
					margins : '0 2 0 2',
					items : [{
							title : '工序列表',
							region : "north",
							collapsible : true,
							height : 100,
							split:true,
							layout : 'fit',
							margins : '0 2 0 2',
							items : [this.workOrderList]
						}, this.workOrderProductTapPanel]
				}],
				listeners : {
					'show' : function() {
						this.setAuditContent();
					},scope:this
			  }
		})
	}
})


/**
the function will be calling by audit
*/
/*DetailWindow = function(){
   this.method = null;
   this.id = null;
   this.on = function(paraString,fun){
		this.method = fun;
   };
   this.setId = function(tid){
		this.id = tid;
   };
   this.show = function(){
       if(this.method != null){
	       this.method();
		   new Ext.ftl.QuoInfoDetailWindow({quoId:this.id}).show();
	   }
   }
}*/


Ext.ftl.quoDetail.GetNewTree = function(pId, workOrderId) {
	return new Ext.tree.ColumnTree({
		// id : pId,
		height : 200,
		bodyStyle : 'width:100%',
		// renderTo : 'productTreeDetail',
		rootVisible : false,
		autoScroll : true,
		expandable : false,
		region : 'center',
		enableDD : false,
		//title : '产品信息',
		columns : [{
					header : '图标',
					width : 70,
					disEnableEdit : true
				}, {
					header : '项目编号',
					width : 70,
					dataIndex : 'projectCode'
					// disEnableEdit : true
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
					dataIndex : 'singleSetAssemblyAmount'
				}, {
					header : '单套刀具采购数量',
					width : 130,
					dataIndex : 'singleSetStockAmount'
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
					dataIndex : 'rebate'
				}, {
					header : '净价',
					width : 80,
					dataIndex : 'netPrice',
					disEnableEdit : true
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
					disEnableEdit : true
				}, {
					header : '价格变动',
					width : 80,
					dataIndex : 'priceChange',
					disEnableEdit : true,
					hidden : true
				}, {
					header : '品牌',
					width : 80,
					dataIndex : 'productBrand',
					disEnableEdit : true
				}, {
					header : '备注1',
					width : 80,
					dataIndex : 'memo',
					disEnableEdit : true
				}, {
					header : '备注2',
					width : 80,
					dataIndex : 'workshop',
					disEnableEdit : true
				}, {
					header : '备注3',
					width : 80,
					dataIndex : 'processCode',
				    disEnableEdit : true
				}],

		listeners : {
			'render' : function() {
				var loa = this.getLoader();
				loa.on("beforeload", function(loa, node) {
					this.baseParams.workOrderId = workOrderId;
				})

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
			uiProviders : {
				'col' : Ext.tree.ColumnNodeUI
			}
		}),

		root : new Ext.tree.AsyncTreeNode({
			id : "root",
			text : 'Tasks'
		})
	});
}
