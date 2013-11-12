/**
 * 报价信息form
 * @class QuotationInfoForm
 * @extends Ext.FormPanel
 */
QuotationInfoForm = Ext.extend(Ext.FormPanel, {
	isReadOnly : false,
	currCombox : null,
	sallerCombox : null,
	taxRateCombox : null,
	paymentCombox : null,
	quoDateField : null,
	startDateField : null,
	endDateField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.isReadOnly = true;
		this.currCombox = new CurrencyCombox({disabled : this.isReadOnly,x:600,y:93,width:170});
		this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,width:170,x:860,y:33});
		this.sallerCombox = new SallerCombox({disabled : this.isReadOnly,x:600,y:3,width:170});
		this.paymentCombox = new Ext.zhj.PaymentConditionComboBox({width : 680,disabled : this.isReadOnly, x:90,y:153});
		this.quoDateField = new Ext.form.DateField({
			fieldLabel: '报价日期', format:'Y-m-d',name: 'quotationDate' ,x:90,y:33, width:170, format:'Y-m-d', 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
		})
		this.startDateField = new Ext.form.DateField({
			fieldLabel: '报价有效期', format:'Y-m-d',name: 'validStartDate', format:'Y-m-d', x:90,y:123, width:170, 
			emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly,
			invalidText : '试刀开始日期不能晚于结束日期',value:new Date(),
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
			invalidText : '试刀结束日期不能早于开始日期',
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
			width : 170, readOnly : true, allowBlank : false,x:790,y:183,
			disabled : true
		})
		
		//预订转正日期
		this.willFormalDateField = new Ext.form.DateField({
			xtype:'datefield',name: 'willFormalDate', format:'Y-m-d',allowBlank : true,
			x:860,y:213, width:170,validationEvent : false,disabled : true
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
			{xtype:'label',text: '试刀开始日期:',x:-10,y:125,style:"font-size:9pt;text-align:right;width:95px"},
			this.startDateField,
			{xtype:'label',text: '试刀完成日期:',x:250,y:125,style:this.lableStyle_},
			this.endDateField,
			/*{xtype:'label',text: '整单折扣(%):',x:770,y:125,style:this.lableStyle_},
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
                   },*/
             //6
             /*{xtype:'label',text: '付款条件:',x:0,y:155,style:this.lableStyle_},
             this.paymentCombox,
             {xtype:'label',text: '最终金额:',x:770,y:155,style:this.lableStyle_},
             {xtype:'numberfield', name: 'finalMoney', allowBlank : false, x:860,y:153,width:170},
             //7
             {xtype:'label',text: '交货方式:',x:0,y:185,style:this.lableStyle_},
             {xtype:'textfield' ,name: 'deliveryType', width : 680, readOnly : this.isReadOnly, allowBlank : false,x:90,y:183},
             {xtype:'label',text: '是否预订:',x:770,y:185,style:this.lableStyle_},
             this.checkBox,*/
             //8
             {xtype:'label',text: '备注:',x:0,y:155,style:this.lableStyle_},
             {xtype:'textfield',name: 'memo', readOnly : this.isReadOnly,x:90,y:153, width : 680}
             /*{xtype:'label',text: '预计转正日期:',x:770,y:215,style:this.lableStyle_},
             this.willFormalDateField,
             //hidden
             {xtype:'hidden',fieldLabel: 'id',name: 'id',hidden : true, hideLabel : true}*/
		];
		
		QuotationInfoForm.superclass.constructor.call(this, {
			width : 1000,
            labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
            frame:true,labelWidth:75,monitorValid:false,
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
	 * @param {} _r
	 */
	setValues : function(_r) {
		Ext.Ajax.request({
			url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
			params: { currencyName: _r.data['currencyName']},
			success : function(response) {
				var record = Ext.decode(response.responseText);
				if(!Ext.isEmpty(record))
					this.currCombox.curRate = record.rate;							   	
			},scope : this
		});
		
		_r.set('status', this.changeStatus(_r.data['status']));
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		var _overallRebate = this.getValues().data['overallRebate'];
		
		var startDate = this.startDateField.getValue();
		var endDate = this.endDateField.getValue();
		
		if(!Ext.isEmpty(startDate) && !Ext.isEmpty(endDate)) {
			if(startDate.format("Y-m-d") > endDate.format("Y-m-d")) {
				return false;
			}
		}
		if(_overallRebate < 0 || _overallRebate > 100)
			return false;
		return this.getForm().isValid();
	}
})

/**
 * 容纳报价信息面板容器
 * @class NorthPanel
 * @extends Ext.Panel
 */
NorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.simpleForm = new QuotationInfoForm();
		NorthPanel.superclass.constructor.call(this, {
				region: 'north',
				layout: 'fit',
                iconCls:'icon-grid',
                title: '试刀申请',
                //contentEl: 'south',
                split: true,
                width: 200,
                height : 285,
                minSize: 100,
                maxSize: 575,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
})
//报价信息ID
//var _id = "";
/**
 * 报价产品树
 */
var _quoProductTree = new Ext.tree.ColumnTree({
			    height: 325,
					//bodyStyle:'width:100%',
			    rootVisible:false,
			    autoScroll:true,
					expandable:false,
					//spilt : true,
					region : 'center',
					enableDD:false,
	        title: '产品信息',
	        columns:[{header : '图标',width : 80,disEnableEdit : true},
	        {header : '项目编号',width : 70,dataIndex : 'projectCode'}, 
	        {header : '序号',width : 40,dataIndex : 'serialNumber',disEnableEdit : true},
	        {
	            header:'牌号',
	            width:180,
	            resizable : true,
	            dataIndex:'brandCode',
	            disEnableEdit:true
	        },{
	            header:'名称',
	            width:100,
	            dataIndex:'productName',
	            disEnableEdit:true
	        },{
	            header:'数量',
	            width:50,
	            dataIndex:'amount'
	        },{
	            header:'计量单位',
	            width:80,
	            dataIndex:'productUnit',
	            disEnableEdit:true
	        },{
	            header:'单价',
	            width:80,
	            name : 'price',
	            dataIndex:'price',
	            disEnableEdit:true
	        },{
	            header:'折扣(%)',
	            width:50,
	            dataIndex:'rebate'
	        },{
	            header:'净价',
	            width:80,
	            dataIndex:'netPrice',
	            disEnableEdit:true
	        },{
	            header:'金额',
	            width:80,
	            dataIndex:'money',
	            disEnableEdit:true
	        },{
	            header:'含税净价',
	            width:80,
	            dataIndex:'taxNetPrice',
	            disEnableEdit:true
	        },{
	            header:'含税金额',
	            width:80,
	            dataIndex:'taxMoney',
	            disEnableEdit:true
	        },{header:'交货期限',width:80,dataIndex:'deliveryDate',disEnableEdit:true},
	        {
	            header:'价格变动',
	            width:80,
	            dataIndex:'priceChange',
	            hidden : true
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand'
	        },{header:'备注', width:80,dataIndex:'memo'},{
	            header:'使用车间',
	            width:0,
	            dataIndex:'workshop',
	            hidden : true
	        },{
	            header:'工序号',
	            width:0,
	            dataIndex:'processCode',
	            hidden : true
	        },{
	            header:'报告号',
	            width:0,
	            dataIndex:'reportCode',
	            hidden : true
	        }],
	        
	        listeners : {
	        	'beforerender' : function() {
	        		var loa = _quoProductTree.getLoader();
	        		loa.on("beforeload", function(loa, node) {
						    this.baseParams.quoId = _quoProductTree._id;
					    })
	        	},
	        	
	        	'render' : function() {
	        		var loa = this.getLoader();
					
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
							} else {
								curNode.cols['priceChange'] = 0;
								curNode.attributes['priceChange'] = 0;
								curNode.ui.setInnerHTMLValue('priceChange',0);
							}
						})
					},this)
	        	}
	        },
	
	        loader: new Ext.tree.TreeLoader({
	            dataUrl:PATH + '/generalQuo/getQuoDetailAction.do',
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
	    });

/**
 * 容纳产品树容器
 * @class centerPanel
 * @extends Ext.Panel
 */
CenterPanel = Ext.extend(Ext.Panel, {
	quoProductTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.quoProductTree = _quoProductTree;
		
		this.quoProductTree['_id'] = this._id;
    this.historyPriceGrid = new Ext.ftl.CusSalesProductGrid({region : 'center',noPageView:true});
    	this.productOrderPriceGrid = new Ext.ftl.ProductOrderProductGrid({region : 'south'});
    	this.quoProductTree.on('click', function(_node) {
    		var _cusName = this.topPanel.simpleForm.getValues().get("customerCode")
    		var _customerCode = _cusName.substring(0, _cusName.indexOf('-'));
    		var _store = this.historyPriceGrid.getStore();
    		_store.baseParams.customerCode = _customerCode;
    		_store.baseParams.pid = _node.attributes.toolsId;
    		_store.load({
				params : {
					start : 0,
					limit : 10
				}
				});
				
				this.productOrderPriceGrid.netPrice = _node.attributes.netPrice;
				var orderStore = this.productOrderPriceGrid.getStore();
				orderStore.baseParams.productId = _node.attributes.toolsId;
				orderStore.load({
				params : {
					start : 0,
					limit : 10
				}	
				});
    	},this)
    	
    	
		CenterPanel.superclass.constructor.call(this, {
								region: 'center',
								layout: 'border',
							  height: 100,
					      margins: '-5 5 5 5',
                items : [
                this.quoProductTree,      
                {
                	
	                region: 'east',
									layout: 'border',
									width:280,
	                iconCls:'icon-grid',
	                split: true,
	                collapsible: true,
	                margins: '5 5 5 5',
	                items : [this.historyPriceGrid,this.productOrderPriceGrid]
                }
      ]
		})
	}
})

/**
 * 报价详细窗口
 * @class QuoDetailWindow
 * @extends Ext.Window
 */
DetailWindow = Ext.extend(Ext.ffc.AuditBusinessDetailWindow, {
	northPanel : null,
	centerPanel : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new NorthPanel();
		
		
	  this.centerPanel = new CenterPanel({topPanel:this.northPanel,_id:this._id});
		
		DetailWindow.superclass.constructor.call(this, {
			title:"查看报价单",  
			width : Ext.getBody().getWidth() - 100,
			height : 600,
			maximizable :true,   
			plain:true,
			closable : true,
			closeAction:'close',
			layout:"border",
			constrainHeader : true,
			listeners : {
				'beforeshow' : function() {
					//重新载入报价产品树
					if(this.centerPanel.quoProductTree.rendered) {
						this.reload();
					}
					var _this = this;
					Ext.Ajax.request({
						url: PATH + '/generalQuo/getQuoAction.do',
						params: { quoId: _this._id },
						success : function(response) {
							var responseArray = Ext.util.JSON.decode(response.responseText); 
							if(responseArray.success == true){
								this.northPanel.simpleForm.setValues(new Ext.data.Record(responseArray.data));
							}
						},scope : this
					});
				}
			},
		items : [this.northPanel, this.centerPanel]
		})
	},
	reload : function() {
		this.centerPanel.quoProductTree.getRootNode().reload();
	}
})