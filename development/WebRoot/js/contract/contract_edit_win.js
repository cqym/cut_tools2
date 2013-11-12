
Ext.ffc.ContractInfoForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var f_config = {
						layout: 'absolute',
						defaultType: 'textfield',
						frame: true,
						items:[
						//1 line
							{xtype:'label',text: '合同编号',x:0,y:5,style:this.lableStyle_},
							{xtype:'textfield',  name: 'contractCode',readOnly : true,x:90,y:3,width:170},
							{xtype:'label',text: '客户名称',x:250,y:5,style:this.lableStyle_},
							{xtype:'textfield',  name: 'customerCodeName',readOnly : true,x:340,y:3,width:170},
							{xtype:'label',text: '卖方名称',x:510,y:5,style:this.lableStyle_},
							new SallerCombox({hideTrigger : true,width:170,readOnly : true,x:600,y:3}),
							{xtype:'label',text: '货品金额',x:760,y:5,style:this.lableStyle_},
							{xtype:'numberfield',  name: 'productMoney',readOnly : true,x:850,y:3,width:140},
						//2 line
							{xtype:'label',text: '签订日期',x:0,y:35,style:this.lableStyle_},
							{xtype:'datefield', name: 'signDate',x:90,y:33,width:170,format:'Y-m-d', value:new Date(),allowBlank:false,blankText:'签订时间不能为空!'},
							{xtype:'label',text: '客户联系人',x:250,y:35,style:this.lableStyle_},
							{xtype:'textfield',  name: 'cusContactPerson',readOnly : true,x:340,y:33,width:170},
							{xtype:'label',text: '我方负责人',x:510,y:35,style:this.lableStyle_},
							{xtype:'textfield',  name: 'ownContactPerson',readOnly : true,x:600,y:33,width:170},
							{xtype:'label',text: '税　　率',x:760,y:35,style:this.lableStyle_},
							new TaxrateCombox({hideTrigger : false,
															width:140,
															x:850,y:33,readOnly : false,
															listeners:{"change":function(field, newValue, oldValue ){
																var formPanel = field.ownerCt;
																var form = formPanel.getForm();
																var currWin = formPanel.ownerCt.ownerCt;
																//alert(currWin.xtype);
																var currConctractInfor = currWin.conctractInfor;
																var productSorts = currConctractInfor.contractProductSorts;
																var tabs = currWin.centerPanel.items;
																  if(newValue * 1 == 0){
																		form.findField('exemplarInvoice').setDisabled(false);
																	}else{
																	  form.findField('exemplarInvoice').setDisabled(true);
																	  form.findField('exemplarInvoice').setValue(0);
																	}
																	Ext.ffc.conDetailTaxrateCalculate(productSorts,formPanel,tabs);
																	var formValues = form.getValues();
																	Ext.apply(currConctractInfor, formValues);
															}}
															}),
						  new Ext.form.Checkbox({
								name: 'exemplarInvoice', x:1000,y:35
							}),
							{xtype:'label',text: '形式发票',x:1020,y:35,style:"font-size:9pt;text-align:left;width:85px"},
							
						//3 line
							{xtype:'label',text: '紧急程度',x:0,y:65,style:this.lableStyle_},
							new Ext.ffc.UrgentLevelCombox({x:90,y:63,width:170}),
							{xtype:'label',text: '客户电话',x:250,y:65,style:this.lableStyle_},
							{xtype:'textfield',  name: 'customerPhone',readOnly : true,x:340,y:63,width:170},
							{xtype:'label',text: '制 单 人',x:510,y:65,style:this.lableStyle_},
							{xtype:'textfield', name: 'userName',readOnly : true,x:600,y:63,width:170},
							{xtype:'label',text: '税　　金',x:760,y:65,style:this.lableStyle_},
							{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:850,y:63,width:140},
						//4
							{xtype:'label',text: '合同状态',x:0,y:95,style:this.lableStyle_},
							new Ext.ffc.ContractStatusComboBox({readOnly : true,disabled:true,x:90,y:93,width:170}),
							{xtype:'label',text: '客户传真',x:250,y:95,style:this.lableStyle_},
							{xtype:'textfield',  name: 'customerFax',readOnly : true,x:340,y:93,width:170},
							{xtype:'label',text: '币　别',x:510,y:95,style:this.lableStyle_},
							{xtype:'textfield', name: 'currencyName',readOnly : true,x:600,y:93,width:170},
							{xtype:'label',text: '税价合计',x:760,y:95,style:this.lableStyle_},
							{xtype:'numberfield',  name: 'totalMoney',readOnly : true,x:850,y:93,width:140},
						//5
							{xtype:'label',text: '质量标准',x:0,y:125,style:this.lableStyle_},
							{xtype:'textfield',  name: 'reference',x:90,y:123,width:420,allowBlank:false,blankText:'质量标准不能为空!'},
							{xtype:'label',text: '整体折扣',x:760,y:125,style:this.lableStyle_},
							{xtype:'numberfield',fieldLabel: '整体折扣',  name: 'overallRebate',x:850,y:123,width:140,
															listeners:{"change":function(field, newValue, oldValue ){
																var formPanel = field.ownerCt;
																var form = formPanel.getForm();
																var _totalMoney = form.findField('totalMoney').getValue();
																if(!Ext.isNumber(newValue)) {
																			Ext.MessageBox.alert('错误提示', '整体折扣必须为数字！');
																			field.setValue(oldValue);
																			return;
																}
																if(newValue*1 > 100 || newValue*1 < 0){
																	Ext.MessageBox.alert('错误提示', '整体折扣只能为0-100之间的数字！');
																	field.setValue(oldValue);
																	return;
																}
																var v = _totalMoney * ((100 - newValue) / 100);
																v = v.toFixed(2);
																form.findField('finalMoney').setValue(v);
															}}
															},
						//6
							{xtype:'label',text: '交提货地点',x:0,y:155,style:this.lableStyle_},
							{xtype:'textfield', name: 'deliveryAddressType',x:90,y:153,width:420,allowBlank:false,blankText:'交提货地点不能为空!'},
							{xtype:'label',text: '最终金额',x:760,y:155,style:this.lableStyle_},
							{xtype:'numberfield',  name: 'finalMoney',x:850,y:153,width:140},
					   //7
							{xtype:'label',text: '交货方式',x:0,y:185,style:this.lableStyle_},
							new Ext.ffc.TrafficModeComboBox({x:90,y:183,width:420,allowBlank:false,blankText:'运输方式及费用负担不能为空!'}),
							{xtype:'label',text: '合同违约责任',x:510,y:185,style:this.lableStyle_},
							{xtype:'textfield',  name: 'signAddress',x:600,y:183,width:390,allowBlank:false,blankText:'合同违约责任不能为空!'},
						//8
							{xtype:'label',text: '结算方式及期限',x:0,y:215,style:this.lableStyle_},
							new Ext.ffc.ClosingAccountModeComboBox({x:90,y:213,width:420}),
							{xtype:'label',text: '合同生效条件',x:510,y:215,style:this.lableStyle_},
							{xtype:'textfield',  name: 'effectConditions',x:600,y:213,width:390,allowBlank:false,blankText:'合同生效不能为空!',value:'本合同一式两份，卖方一份，买方一份，双方签字盖章后生效。'},
						//9
							{xtype:'label',text: '其他约定事项',x:0,y:245,style:this.lableStyle_},
							{xtype:'textfield', name: 'otherConvention',x:90,y:243,width:420},
							{xtype:'label',text: '备　　注',x:510,y:245,style:this.lableStyle_},
							{xtype:'textfield',  name: 'memo',x:600,y:243,width:390},
						//hidden
							{xtype:'hidden',fieldLabel: '客户编号', readOnly : true, name: 'customerCode'},
							{xtype:'hidden',fieldLabel: '客户名称', readOnly : true, name: 'customerName'},
							{xtype:'hidden',fieldLabel: '币别id', readOnly : true, name: 'currencyId'}
						]
		};

		Ext.ffc.ContractInfoForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.ContractInfoForm.superclass.constructor.call
	}
	
});

Ext.ffc.addContractInfor = function(conInfor,currWin){
		Ext.Ajax.request({
			method: "post",
			params: { 'conctractInfor' : Ext.encode(conInfor)},
			url: PATH + "/contract/contractEditAction.do?ffc=addContract",
			success: function(response){
				Ext.Msg.alert("消息", "保存成功!");
				currWin.close();
			}
		});
}

Ext.ffc.updateContractInfor = function(conInfor,currWin){
		Ext.Ajax.request({
			method: "post",
			params: { 'conctractInfor' : Ext.encode(conInfor)},
			url: PATH + "/contract/contractEditAction.do?ffc=updateContract",
			success: function(response){
				Ext.Msg.alert("消息", "保存成功!");
				currWin.close();
			}
		});
}

Ext.ffc.NorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.simpleForm = new Ext.ffc.ContractInfoForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var conctractInfor = container.ownerCt.ownerCt.conctractInfor;
			
			var params = [];
			for(var i in conctractInfor){
				if(!conctractInfor[i] || !conctractInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,conctractInfor);
			var myNewRecord = new RecordClum(recordParams); 
			with(this.ownerCt.simpleForm.getForm()){
			    loadRecord(myNewRecord);
			    if(conctractInfor.taxRate > 0){
			    	findField('exemplarInvoice').setDisabled(true);
			    }else{
			    	findField('exemplarInvoice').setDisabled(false);
			    }
		  }
		}); 

		Ext.ffc.NorthPanel.superclass.constructor.call(this, {
			          region: 'north',
                iconCls:'icon-grid',
				        layout: 'fit',
                split: true,
                width: 1050,
                height : 280,
                minSize: 175,
                maxSize: 840,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});
Ext.ffc.conDetailAmountCalculate = function(form,record,newValue,oldValue,columnName){
//	record.set('amount',newValue);
record.set(columnName,newValue);
	var _amout = record.get('amount') * 1;//数量
	var _rebate = (100 - record.get('rebate'))/100;//折扣
	var _price = record.get('price') * 1; //单价
	var _netPrice = record.get('netPrice') * 1;
	if(columnName == 'price' || columnName == 'rebate'){//如果修改的是单价，折扣，则重新计算净价
		_netPrice = (_rebate * _price).toFixed(2);//净价=折扣*单价
	}
	
	var _priceChange = record.get('priceChange') * 1;
	
	var _money = (_amout * _netPrice * 1).toFixed(2);//金额=数量*单价
	record.set('money',_money);
	record.set('netPrice',_netPrice);

	var formValues = form.getForm().getValues();
	var taxRate = formValues.taxRate * 1;
	
	var nP = parseFloat(_netPrice * taxRate).toFixed(2);
	var taxNetPrice = _netPrice*1 + nP*1;					//含税净价=净价 + 净价*税率
		taxNetPrice = taxNetPrice.toFixed(2);
	record.set('taxNetPrice',taxNetPrice);

	var _curMoney = _money;
	var tM = parseFloat(_curMoney * taxRate).toFixed(2);
	var _taxM = _curMoney*1 + tM*1;//含税金额=金额*税率 + 金额
	var _taxMoney = parseFloat(_taxM).toFixed(2);
	record.set('taxMoney',_taxMoney);
}
Ext.ffc.conMainInforCalculate = function(productSorts,form){
	var formValues = form.getForm().getValues();
	var taxRate = formValues.taxRate;
	var prod_money = 0;
	for(var i = 0;i < productSorts.length;i++){
		var proDetailArr =  productSorts[i].conProductDetail;
		for(var j = 0 ;j < proDetailArr.length;j++){
		    prod_money += proDetailArr[j].money*1;
		}
	}

	var t_totalMoney = prod_money + (prod_money * taxRate);
		t_totalMoney = t_totalMoney.toFixed(2);
	var t_taxMoney = prod_money * taxRate;
		t_taxMoney = t_taxMoney.toFixed(2);
	var t_finalMoney = (prod_money + prod_money * taxRate)*((100 - formValues.overallRebate*1) / 100);
		t_finalMoney = t_finalMoney.toFixed(2);
		prod_money = prod_money.toFixed(2);
	with(form.getForm()){
		findField('productMoney').setValue(prod_money);
		findField('taxMoney').setValue(t_taxMoney);
		findField('totalMoney').setValue(t_totalMoney);
		findField('finalMoney').setValue(t_finalMoney);
	}
}
Ext.ffc.conDetailTaxrateCalculate = function(productSorts,form,tabs){
	var formValues = form.getForm().getValues();
	var taxRate = formValues.taxRate * 1;

	var prod_money = 0;
	for(var i = 0;i < productSorts.length;i++){
		var proDetailArr =  productSorts[i].conProductDetail;
		for(var j = 0 ;j < proDetailArr.length;j++){
			var nP = parseFloat(proDetailArr[j].netPrice * taxRate).toFixed(2);
			var taxNetPrice = proDetailArr[j].netPrice * 1 + nP*1;
			proDetailArr[j].taxNetPrice = taxNetPrice;
			
			var tM = parseFloat(proDetailArr[j].money * taxRate).toFixed(2);
			var _taxM = proDetailArr[j].money * 1 + tM*1;//含税金额=金额*税率 + 金额
			var _taxMoney = parseFloat(_taxM).toFixed(2);
			proDetailArr[j].taxMoney = _taxMoney;

			prod_money += proDetailArr[j].money * 1;
		}
	}
	for(var i = 0,len = tabs.length; i < len;i++ ){
		for(var j = 0;j < productSorts.length;j++){
		    if(tabs.get(i).sortId == productSorts[j].id){
				tabs.get(i).store.removeAll();
				tabs.get(i).store.loadData(productSorts[j].conProductDetail);
				break;
			}
		}
	}
	var t_totalMoney = prod_money + (prod_money * taxRate);
		t_totalMoney = t_totalMoney.toFixed(2);
	var t_taxMoney = prod_money * taxRate;
		t_taxMoney = t_taxMoney.toFixed(2);
	var t_finalMoney = (prod_money + prod_money * taxRate)*((100 - formValues.overallRebate*1) / 100);
		t_finalMoney = t_finalMoney.toFixed(2);
	with(form.getForm()){
		findField('productMoney').setValue(prod_money);
		findField('taxMoney').setValue(t_taxMoney);
		findField('totalMoney').setValue(t_totalMoney);
		findField('finalMoney').setValue(t_finalMoney);
	}
}
Ext.ffc.getContractDetailProTree = function (quotationType,jsonData,title,sortId,_config){
	var rebateRender = function(value, cellmeta, record, rowIndex, columnIndex, store){
		var c = record.get('priceChange') * 1;
		if(c == 1 || c == 3){
			return "<span style='color:blue'>" + value + "</span>";
		}
		return value;
	}
	var netPriceRender = function(value, cellmeta, record, rowIndex, columnIndex, store){
		var c = record.get('priceChange') * 1;
		if(c == 2 || c == 3){
			return "<span style='color:red'>" + value + "</span>";
		}else if(c == 1){
			return "<span style='color:blue'>" + value + "</span>";
		}
		return value;
	}
	var moneyRender = function(value, cellmeta, record, rowIndex, columnIndex, store){
		var c = record.get('priceChange') * 1;
		if(c == 2 || c == 3){
			return "<span style='color:red'>" + value + "</span>";
		}else if(c == 1){
			return "<span style='color:blue'>" + value + "</span>";
		}
		return value;
	}
	var taxNetPriceRender = function(value, cellmeta, record, rowIndex, columnIndex, store){
		var c = record.get('priceChange') * 1;
		if(c == 2 || c == 3){
			return "<span style='color:red'>" + value + "</span>";
		}else if(c == 1){
			return "<span style='color:blue'>" + value + "</span>";
		}
		return value;
	}
	var taxMoneyRender = function(value, cellmeta, record, rowIndex, columnIndex, store){
		var c = record.get('priceChange') * 1;
		if(c == 2 || c == 3){
			return "<span style='color:red'>" + value + "</span>";
		}else if(c == 1){
			return "<span style='color:blue'>" + value + "</span>";
		}
		return value;
	}
	var _price=	{
			header:'单价',
			width:60,
			name : 'price',
			dataIndex:'price'
		};
	var _rebate = {
			header:'折扣',
			width:50,
			dataIndex:'rebate',
			renderer:rebateRender
		};
	var _netPrice = {
			header:'净价',
			width:80,
			dataIndex:'netPrice',
			renderer:netPriceRender
		};
    if(_config && _config.priceUpdate){
		_price.editor = new Ext.form.NumberField({
					enableKeyEvents : true,
					listeners : {
						'focus' : function(f){
							f.selectText(0,f.getValue().length);
						}
					}
				});
		_rebate.editor = new Ext.form.NumberField({
							enableKeyEvents : true,
							listeners : {
								'focus' : function(f){
									f.selectText(0,f.getValue().length);
								}
							}
						});
			_netPrice.editor = new Ext.form.NumberField({
				enableKeyEvents : true,
				listeners : {
					'focus' : function(f){
						f.selectText(0,f.getValue().length);
					}
				}
			});
	}
	var gStore = new Ext.data.Store({
		   proxy:new Ext.data.MemoryProxy(jsonData),
		   reader: new Ext.data.JsonReader({}, 
		 [
      {name: 'id',mapping:'id',type:'string'},
			{name: 'toolsId',mapping:'toolsId',type:'string'},
			{name: 'productCode',mapping:'productCode',type:'string'},
			{name: 'projectCode',mapping:'projectCode',type:'float'},
			{name: 'serialNumber',mapping:'serialNumber',type:'float'},
			{name: 'brandCode',mapping:'brandCode',type:'string'},
			{name: 'productName',mapping:'productName',type:'string'},
			{name: 'singleSetAssemblyAmount',mapping:'singleSetAssemblyAmount',type:'float'},
			{name: 'singleSetStockAmount',mapping:'singleSetStockAmount',type:'float'},
			{name: 'amount',mapping:'amount',type:'float'},
			{name: 'productUnit',mapping:'productUnit',type:'string'},
			{name: 'price',mapping:'price',type:'float'},
			{name: 'rebate',mapping:'rebate',type:'float'},
			{name: 'netPrice',mapping:'netPrice',type:'float'},
			{name: 'money',mapping:'money',type:'float'},
			{name: 'taxNetPrice',mapping:'taxNetPrice',type:'float'},
			{name: 'taxMoney',mapping:'taxMoney',type:'float'},
			{name: 'productBrand',mapping:'productBrand',type:'string'},
			{name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
			{name: 'memo',mapping:'memo',type:'string'},
			{name: 'workshop',mapping:'workshop',type:'string'},
			{name: 'reportCode',mapping:'reportCode',type:'string'},
			{name: 'priceChange',mapping:'priceChange',type:'float'},
			{name: 'leaf',mapping:'leaf',type:'float'},
			{name: 'fileCount',mapping:'fileCount',type:'float'}
        ])
    });
var gridCheckSele = new Ext.grid.CheckboxSelectionModel();
var rtGrid = new Ext.grid.EditorGridPanel({
			'sortId' : sortId,
			'title' : title,
			clicksToEdit:1,
			ds : gStore,
			store : gStore,
			closable : true,
			sm:gridCheckSele,
	        columns:[
			new Ext.grid.RowNumberer({width:20}),
		    gridCheckSele,
			{
	            dataIndex:'id',
				hidden:true
	        },{
	            header:'',
	            width:40,
	            resizable : true,
	            dataIndex:'leaf',
				renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
					if(!value) {
						return '<img src="' + PATH + '/extjs/resources/images/default/tree/folder.gif" style="cursor:hand" onclick="new QuotationManager.ProToolsListWindow({\'nodeId\' : \'' + record.get('toolsId') + '\'}).show();">';
					} else {
						return '<img src="' + PATH + '/extjs/resources/images/default/tree/leaf.gif">';
					}
				}
	        },{
	            header:'货品编号',
	            width:100,
	            resizable : true,
	            dataIndex:'productCode'
	        },{
	            header:'项目编号',
	            width:60,
	            resizable : true,
	            dataIndex:'projectCode'
	        },{
	            header:'序号',
	            width:40,
	            resizable : true,
	            dataIndex:'serialNumber'
	        },{
	            header:'工具牌号',
	            width:200,
	            resizable : true,
	            dataIndex:'brandCode'
	        },{
	            header:'名称',
	            width:100,
	            dataIndex:'productName'
	        },{
	            header:'单套刀具装配数量',
	            width:120,
	            dataIndex:'singleSetAssemblyAmount',
				hidden : quotationType != 1
	        },{
	            header:'单套刀具采购数量',
	            width:120,
	            dataIndex:'singleSetStockAmount',
				hidden : quotationType != 1
	        },{
	            header:'数量',
	            width:50,
	            dataIndex:'amount',
				editor:new Ext.form.NumberField({
					enableKeyEvents : true,
					listeners : {
						'focus' : function(f){
							f.selectText(0,f.getValue().length);
						},
		           		'specialkey' : function(_field, e){
							_field.gridObj = rtGrid;
							Ext.ftl.gridEditorkeyMove(_field, e);
						}
					}
				})
	        },{
	            header:'计量单位',
	            width:60,
	            dataIndex:'productUnit'
	        },
				_price,
				_rebate,
				_netPrice,
			{
	            header:'金额',
	            width:80,
	            dataIndex:'money',
				renderer:moneyRender
	        },{
	            header:'含税净价',
	            width:80,
	            dataIndex:'taxNetPrice',
				renderer:taxNetPriceRender
	        },{
	            header:'含税金额',
	            width:80,
	            dataIndex:'taxMoney',
				renderer:taxMoneyRender
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand'
			},{
	            header:'交货期限',
	            width:80,
	            dataIndex:'deliveryDate',
				editor: {xtype:'datefield',format:'Y-m-d',value:new Date(),altFormats:'Y-m-d'},
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					if(!value) return '';
					if(typeof(value) == 'string') return value;
					else{
						var str = value.format('Y-m-d');
						record.set('invoiceDate',str);
						return str;
					}
				}
	        },{
	            header:'备注1',
	            width:80,
	            dataIndex:'memo',
				editor:new Ext.form.TextField({
				   listeners : {
		           		'specialkey' : function(_field, e){
							_field.gridObj = rtGrid;
							Ext.ftl.gridEditorkeyMove(_field, e);
						}
		           }
				})
	        },{
	            header:'备注2',
	            width:80,
	            dataIndex:'workshop',
				editor:new Ext.form.TextField({
				   listeners : {
		           		'specialkey' : function(_field, e){
							_field.gridObj = rtGrid;
							Ext.ftl.gridEditorkeyMove(_field, e);
						}
		           }
				})
	        },{
	            header:'备注3',
	            width:80,
	            dataIndex:'reportCode',
				editor:new Ext.form.TextField({
					listeners : {
		           		'specialkey' : function(_field, e){
							_field.gridObj = rtGrid;
							Ext.ftl.gridEditorkeyMove(_field, e);
						}
		           }
				})
	        },{
	            header:'客户确认方案图',
	            width:100,
	            dataIndex:'toolsId',
				      renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
					    var fileCount = record.get('fileCount');
							 if(fileCount > 0){
							 	  return '<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick("' + value + '")><span style="color:blue;font-weight:bold;">查看</span></a>';
							 }
							 return '';
	        	}
	        },{
	            dataIndex:'priceChange',
				hidden:true
	        }],
			listeners : {
				'afteredit' : function(e){
					var field = e.field, newValue = e.value, oldValue = e.originalValue;
					var record = e.record;
					var win = rtGrid.ownerCt.ownerCt;
					var productSorts = win.conctractInfor.contractProductSorts;
					var columnName = e.grid.colModel.getDataIndex(e.column);
					if(columnName == 'netPrice'){
						var _priceChange = record.get('priceChange');
						record.set('priceChange', Ext.ffc.getPriceChangeForNetPri(_priceChange));
					}
					if(columnName == 'rebate'){
					    record.set('priceChange', 1);
					}
					if(columnName != 'amount' && columnName != 'price' && columnName != 'rebate' && columnName != 'netPrice'){
						for(var i = 0;i < productSorts.length;i++){
							if(productSorts[i].id == rtGrid.sortId){
								var proDetailArr =  productSorts[i].conProductDetail;
								for(var j = 0 ;j < proDetailArr.length;j++){
									if(record.get('id') == proDetailArr[j].id){
										Ext.apply(proDetailArr[j],record.data);
										break;	
									}
								}
								break;
							}
						}
						return ;
					};
					var form = win.topPanel.simpleForm;
					Ext.ffc.conDetailAmountCalculate(form,record,newValue,oldValue,columnName);
					for(var i = 0;i < productSorts.length;i++){
						if(productSorts[i].id == rtGrid.sortId){
							var proDetailArr =  productSorts[i].conProductDetail;
							for(var j = 0 ;j < proDetailArr.length;j++){
								if(record.get('id') == proDetailArr[j].id){
									Ext.apply(proDetailArr[j],record.data);
									break;	
								}
							}
							break;
						}
					}
					Ext.ffc.conMainInforCalculate(productSorts,form);
				}
			}
	    });
	
 return rtGrid;
}

Ext.ffc.contractDetailTabPanel = Ext.extend(Ext.TabPanel, {
	quoProductTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ffc.contractDetailTabPanel.superclass.constructor.call(this, {
			region:'center', 
			//layout: 'fit',
			deferredRender:false, 
			autoScroll: true, 
			margins:'0 4 4 0', 
			activeTab:0,
			autoScroll : false,
			listeners:{
				'beforeremove' : function(it,evt){
					if(this.items.length == 1){
						alert("合同中至少要有一个合同分项!");
						return false;
					}
					return window.confirm("请确认是否要移去当前报价单");
				},scope:this,
				'remove' : function(it,com){
					var win = this.ownerCt;
					var conSorts = win.conctractInfor.contractProductSorts;
					var	form = win.topPanel.simpleForm;
					var items = this.items;
					var newArr = [];
					
					for(var i = 0;i < conSorts.length; i++){
						items.each(function(item){
							if(conSorts[i].id == item.sortId){
								newArr.push(conSorts[i]);
							}
						});
					}
					win.conctractInfor.contractProductSorts = newArr;
					Ext.ffc.conMainInforCalculate(newArr,form);
				},scope:this
			}
		})
	}
});

Ext.ffc.ContractEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	conctractInfor: null,
	readOnly : false,
	addDetailTabPanel:function(sortArr){
		var centerPanels = [];
				
		for(var i = 0 ;i < sortArr.length;i++){
			var pan = Ext.ffc.getContractDetailProTree(this.quotationType,sortArr[i].conProductDetail,sortArr[i].name,sortArr[i].id,this._config);
		    centerPanels.push(pan);
			pan.store.load();
		}
		this.centerPanel.add(centerPanels);
		return centerPanels;
	},
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.topPanel = new Ext.ffc.NorthPanel({readOnly:_cfg.readOnly});
		this.centerPanel = new Ext.ffc.contractDetailTabPanel();
		var sortArr = this.conctractInfor.contractProductSorts;
		this.addDetailTabPanel(sortArr);
		
		//{items:centerPanels}
		Ext.ffc.ContractEditWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : Ext.getBody().getWidth(),
			height : 600,
			maximizable :true,
			title :  '合同编制',
			layout :  'border',
			modal : true,
			items : [this.topPanel,this.centerPanel],
			buttons : [{
					text : "删除产品",
					hidden: !this.canDetailDel,
					handler : function() {
						var grid = this.centerPanel.getActiveTab();
						var form = this.topPanel.simpleForm;
						if(!grid) return;
						var list = grid.getSelectionModel().getSelections();
						var store = grid.getStore();
						var conInfor = this.conctractInfor;
						var conSorts = conInfor.contractProductSorts;
						var ids = [];
						for(var i = 0;i < list.length ;i++ ){
							ids.push(list[i].id);
						}
						Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中产品!', function(btn){
							if(btn != 'yes'){return ;}
							for(var i = 0,len = list.length;i < len ;i++ ){
								store.remove(list[i]);
							}	
							for(var i = 0;i < conSorts.length; i++){
								if(conSorts[i].id == grid.sortId){
									var detail = conSorts[i].conProductDetail;
									var tem = [];
									for(var j = 0;j < detail.length ;j++ ){
										var flag = true;
										for(var jj = 0;jj < ids.length ;jj++ ){
											if(detail[j].id == ids[jj]){
												flag = false;
												break;
											}
										}
										if(flag){
											tem.push(detail[j]);
										}
									}
									conSorts[i].conProductDetail = tem;
								}
							}
							Ext.ffc.conMainInforCalculate(conSorts,form);
							var values = form.getForm().getValues();
							Ext.Ajax.request({
								method: "post",
								params: { 'ids' : ids,"contractId":conInfor.id,"overallRebate":values.overallRebate},
								url: PATH + "/contract/contractEditAction.do?ffc=deleteContractDetail",
								success: function(response){
									
								}
							});
						   });
					},scope : this
				},
				{
				text : "增加报价单",
				hidden: this.readOnly,
				handler : function() {
				    var topPanel = this.topPanel;
					Ext.ffc.select_quotations(function(arr,select_quotations_win){
							if(!arr || arr.length == 0) return;
							var form = topPanel.simpleForm;
							var formValues = form.getForm().getValues();
							
							var idsArr = [];
							var currencyName0 = formValues.currencyName;
							var customerCode0 = formValues.customerCode;
							var taxRate0 = formValues.taxRate;
							var sellerName0 = formValues.sellerName;
							var quotationType0 = arr[0].get("quotationType");
							var paymentCondition = formValues.closingAccountMode;
							

							for(var i = 0 ;i < arr.length ;i++){
								if(arr[i].get("currencyName") != currencyName0){
									Ext.Msg.alert("消息", "所选择报价单，与现有合同币别不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("customerCode") != customerCode0){
									Ext.Msg.alert("消息", "所选择报价单，与现有合同客户不一致，不能生成一个合同!");
									return ;
								}else if(quotationType0 != 3 && arr[i].get("taxRate") != taxRate0){
									Ext.Msg.alert("消息", "所选择报价单，与现有合同税率不一致，不能生成一个合同!");
									return ;
								}else if(quotationType0 != 3 && arr[i].get("sellerName") != sellerName0){
									Ext.Msg.alert("消息", "所选择报价单，与现有合同买方不一致，不能生成一个合同!");
									return ;
								}/*else if(arr[i].get("quotationType") != quotationType){
									Ext.Msg.alert("消息", "所选择报价单，类型不一致，不能生成一个合同!");
									return ;
								}*/else if(quotationType0 != 3 && arr[i].get("paymentCondition") != paymentCondition){
									Ext.Msg.alert("消息","所选择报价单，付款条件不一致，不能生成一个合同!");
									return ;
								}
								if(arr[0].get("quotationType") * 1 == 1 && arr.length > 1){
								    Ext.Msg.alert("消息", "添加项目报价单，每次只允许添加一个!");
									return ;
								}
								idsArr.push(arr[i].get("id"));
							}
							
							select_quotations_win.close();//关闭报价单选择窗口...consultProjectQuo
							var tUrl = "/contract/contractEditAction.do?ffc=consultGeneralQuo";
							var tparams = { ids : idsArr};
							if(idsArr.length == 1 && arr[0].get("quotationType") * 1 == 1){
							    tUrl = "/contract/contractEditAction.do?ffc=consultProjectQuo";
								tparams = { id : idsArr[0]};
							}else if(arr[0].get("quotationType") * 1 == 3){
							    tUrl = "/contract/contractEditAction.do?ffc=consultYuDingQuo";
								tparams = { ids : idsArr,contractTaxRate:taxRate0};
							}
							Ext.Ajax.request({
								method: "post",
								params: tparams,
								url: PATH + tUrl,
								success: function(response){
									    var currWin = topPanel.ownerCt;
										var currConctractInfor = currWin.conctractInfor;
									    var conSorts = currConctractInfor.contractProductSorts;
										eval("var temp = " + response.responseText);
										var newConSorts = temp.contractProductSorts;
										var willAddSorts = [];
										for(var i = 0;i < newConSorts.length;i++){
											var j = 0;
											for(; j < conSorts.length ;j++){
											    if(newConSorts[i].id == conSorts[j].id){
													Ext.Msg.alert("消息", "报价单［" + newConSorts[i].name + "］已经存在!");
												    break;
												}
											}
											if(j >= conSorts.length){
											    conSorts.push(newConSorts[i]);
												willAddSorts.push(newConSorts[i]);
											}
										}
										if(willAddSorts.length > 0){
											currWin.addDetailTabPanel(willAddSorts);
											Ext.ffc.conMainInforCalculate(conSorts,form);
										}else{
										    return;
										}
										var t_overallRebate = temp.overallRebate * 1;
										var t_finalMoney = temp.finalMoney * 1;
										var t_totalMoney = temp.totalMoney * 1;
										if(t_overallRebate > 0 || t_finalMoney != t_totalMoney){
											Ext.Msg.alert("消息", "新添加的报价单，最终金额与税价合计不相等，请注意当前合同最终金额是否要修改！");
											currConctractInfor.overallRebate = 0;
											currConctractInfor.finalMoney = currConctractInfor.totalMoney + t_totalMoney;
											form.getForm().setValues({"overallRebate":0,"finalMoney":currConctractInfor.totalMoney + t_totalMoney});
										}
										var _overallRebate = currConctractInfor.overallRebate * 1;
										var _finalMoney = currConctractInfor.finalMoney * 1;
										var _totalMoney = currConctractInfor.totalMoney * 1;
										if(_overallRebate > 0 || _finalMoney != _totalMoney){
											Ext.Msg.alert("消息", "当前合同在追加之前，最终金额与税价合计不相等，请注意当前合同最终金额是否要修改！");
											currConctractInfor.overallRebate = 0;
											currConctractInfor.finalMoney = currConctractInfor.totalMoney + t_totalMoney;
											form.getForm().setValues({"overallRebate":0,"finalMoney":currConctractInfor.totalMoney + t_totalMoney});
										}
										//Ext.ffc.conMainInforCalculate(conSorts,form);
								}
							});
						});
				},scope : this
			},{
				text : "保  存",
				hidden: this.readOnly,
				handler : function() {
					var form = this.topPanel.simpleForm;
					if(!form.form.isValid()){return;};
					var currWin = this;
					var currConctractInfor = currWin.conctractInfor;
					if(currConctractInfor.contractProductSorts.length <= 0){
						Ext.Msg.alert("消息", "合同明细为空，不能保存!");
					    return ;
					}
					var formValues = form.getForm().getValues();
					if(formValues.signDate == ''){
						Ext.Msg.alert("消息", "请选择签订时间!");
					    return ;
					}
					if(formValues.deliveryAddressType == ''){
						Ext.Msg.alert("消息", "请填写交货地点及其方式!");
					    return ;
					}

					Ext.apply(currConctractInfor, formValues);
					
					if(currConctractInfor.exemplarInvoice == 'on'){
						currConctractInfor.exemplarInvoice = '1';
					}else{
						currConctractInfor.exemplarInvoice = '0';
					}
					if(currConctractInfor.id == null || currConctractInfor.id == ''){
						Ext.ffc.addContractInfor(currConctractInfor,currWin);
					}else{
						Ext.ffc.updateContractInfor(currConctractInfor,currWin);
					}	 		
				},
				scope : this
			  },{
				text : "关  闭",
				handler : function() {
					this.close();
				},
				scope : this
			  }]
		});
	}
});  

DetailWindow = function(){
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
			var conId = this.id;
			Ext.Ajax.request({
				method: "post",
				params: { id : conId},
				url: PATH + "/contract/contractViewAction.do?ffc=contractViewById",
				success: function(response){
						eval("var temp = " + response.responseText);
						var conEditWin = new Ext.ffc.ContractEditWindow({conctractInfor:temp,readOnly : true,_config : {priceUpdate:false}});
						conEditWin.show();
						//select_quotations_win.close();
				}
			});
	   }
   }
}