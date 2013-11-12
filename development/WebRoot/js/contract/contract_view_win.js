
Ext.ffc.ContractInfoViewForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var numberStyle = "text-align:right";
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
							new TaxrateCombox({hideTrigger : true,width:140,x:850,y:33,readOnly : true}),
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
							{xtype:'numberfield',fieldLabel: '整体折扣',  name: 'overallRebate',x:850,y:123,width:140},
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
							{xtype:'textfield',  name: 'effectConditions',x:600,y:213,width:390,allowBlank:false,blankText:'合同生效不能为空!'},
						//9
							{xtype:'label',text: '其他约定事项',x:0,y:245,style:this.lableStyle_},
							{xtype:'textfield', name: 'otherConvention',x:90,y:243,width:420},
							{xtype:'label',text: '备　　注',x:510,y:245,style:this.lableStyle_},
							{xtype:'textfield',  name: 'memo',x:600,y:243,width:390},
						//hidden
							{xtype:'hidden',fieldLabel: '客户编号', readOnly : true, name: 'customerCode', anchor:'90%'},
							{xtype:'hidden',fieldLabel: '客户名称', readOnly : true, name: 'customerName', anchor:'90%'}
						]
		};

		Ext.ffc.ContractInfoViewForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.ContractInfoViewForm.superclass.constructor.call
	}
	
});


Ext.ffc.ViewNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.simpleForm = new Ext.ffc.ContractInfoViewForm({readOnly : _cfg.readOnly});
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

		Ext.ffc.ViewNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
				layout: 'fit',
                iconCls:'icon-grid',
                //title: '合同信息',
                //contentEl: 'south',
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

Ext.ffc.getViewContractDetailProTree = function (quotationType,jsonData,title,sortId){
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
		  {name: 'orderAmount',mapping:'orderAmount',type:'float'},
		  {name: 'arrivalAmount',mapping:'arrivalAmount',type:'float'},
		  {name: 'deliveryAmount',mapping:'deliveryAmount',type:'float'},
			{name: 'leaf',mapping:'leaf',type:'float'},
			{name: 'fileCount',mapping:'fileCount',type:'float'}
			
        ])
    });
var rtGrid = new Ext.grid.GridPanel({
			'sortId' : sortId,
			'title' : title,
			clicksToEdit:1,
			ds : gStore,
			store : gStore,
	        columns:[new Ext.grid.RowNumberer(),
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
						return '<img style="margin:0 0 0 0" src="' + PATH + '/extjs/resources/images/default/tree/leaf.gif">';
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
					listeners : {
						focus : function(f){
							f.selectText(0,f.getValue().length);
						},
					    change : function(field, newValue, oldValue ){
							var record = rtGrid.activeEditor.record;
							var win = rtGrid.ownerCt.ownerCt;
							var productSorts = win.conctractInfor.contractProductSorts;
							var form = win.topPanel.simpleForm;
							Ext.ffc.conDetailAmountCalculate(form,record,newValue,oldValue);
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
				})
	        },{
	            header:'计量单位',
	            width:60,
	            dataIndex:'productUnit'
	        },{
	            header:'单价',
	            width:60,
	            name : 'price',
	            dataIndex:'price'
	        },{
	            header:'折扣',
	            width:50,
	            dataIndex:'rebate',
				renderer:rebateRender
	        },{
	            header:'净价',
	            width:80,
	            dataIndex:'netPrice',
				renderer:netPriceRender
	        },{
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
	            dataIndex:'deliveryDate'
	        },{
	            header:'采购数量',
	            width:80,
	            dataIndex:'orderAmount'
	        },{
	            header:'已到数量',
	            width:80,
	            dataIndex:'arrivalAmount'
	        },{
	            header:'已交数量',
	            width:80,
	            dataIndex:'deliveryAmount'
	        },{
	            header:'备注1',
	            width:80,
	            dataIndex:'memo',
				editor:new Ext.form.TextField()
	        },{
	            header:'备注2',
	            width:80,
	            dataIndex:'workshop',
				editor:new Ext.form.TextField()
	        },{
	            header:'备注3',
	            width:80,
	            dataIndex:'reportCode',
				editor:new Ext.form.TextField()
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
	        }]
	    });
	
 return rtGrid;
}



Ext.ffc.contractViewDetailTabPanel = Ext.extend(Ext.TabPanel, {
	quoProductTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ffc.contractViewDetailTabPanel.superclass.constructor.call(this, {
			region:'center', 
			//layout: 'fit',
			deferredRender:false, 
			autoScroll: true, 
			margins:'0 4 4 0', 
			activeTab:0,
			autoScroll : false
		})
	}
});


Ext.ffc.ContractViewWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	conctractInfor: null,
	readOnly : false,
	addDetailTabPanel:function(sortArr){
		var centerPanels = [];
		for(var i = 0 ;i < sortArr.length;i++){
			var pan = Ext.ffc.getViewContractDetailProTree(this.quotationType,sortArr[i].conProductDetail,sortArr[i].name,sortArr[i].id);
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
		this.topPanel = new Ext.ffc.ViewNorthPanel({readOnly:_cfg.readOnly});
		this.centerPanel = new Ext.ffc.contractViewDetailTabPanel();
		var sortArr = this.conctractInfor.contractProductSorts;
		this.addDetailTabPanel(sortArr);
		//{items:centerPanels}
		Ext.ffc.ContractViewWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : Ext.getBody().getWidth(),
			height : 600,
			maximizable :true,
			title :  '合同查看',
			layout :  'border',
			modal : true,
			items : [this.topPanel,this.centerPanel],
			buttons : [{
				text : "关  闭",
				handler : function() {
					this.close();
				},
				scope : this
			 }]
		});
	}
});  



Ext.ffc.ContractViewAuditWindow = Ext.extend(Ext.ffc.AuditBusinessDetailWindow, {  
	topPanel : null,
	centerPanel : null,
	conctractInfor: null,
	readOnly : false,
	addDetailTabPanel:function(sortArr,func){
		var centerPanels = [];
		for(var i = 0 ;i < sortArr.length;i++){
			var pan = Ext.ffc.getViewContractDetailProTree(this.quotationType,sortArr[i].conProductDetail,sortArr[i].name,sortArr[i].id);
		    centerPanels.push(pan);
		    pan.on('rowclick',func ,this);
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
		this.topPanel = new Ext.ffc.ViewNorthPanel({readOnly:_cfg.readOnly});
		this.centerPanel = new Ext.ffc.contractViewDetailTabPanel();
		var sortArr = this.conctractInfor.contractProductSorts;
		this.addDetailTabPanel(sortArr,function(grid, rowIndex, e) {
	    	 var conDetailRecord = grid.getStore().getAt(rowIndex);
	    		var _store = this.historyPriceGrid.getStore();
	    		_store.baseParams.customerCode = this.conctractInfor.customerCode;
	    		_store.baseParams.pid = conDetailRecord.get("toolsId");
	    		_store.load({
					params : {
						start : 0,
						limit : 10
					}
					});
					
					this.productOrderPriceGrid.netPrice = conDetailRecord.get("netPrice");
					var orderStore = this.productOrderPriceGrid.getStore();
					orderStore.baseParams.productId = conDetailRecord.get("toolsId");
					orderStore.load({
					params : {
						start : 0,
						limit : 10
					}	
					});
	    	});

   	this.historyPriceGrid = new Ext.ftl.CusSalesProductGrid({region : 'center',noPageView:true});
   	this.productOrderPriceGrid = new Ext.ftl.ProductOrderProductGrid({region : 'south'});
   	
		Ext.ffc.ContractViewAuditWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : Ext.getBody().getWidth(),
			height : 600,
			maximizable :true,
			title :  '合同查看',
			layout :  'border',
			modal : true,
			items : [this.topPanel,
			{
                region: 'center',
								layout: 'border',
                iconCls:'icon-grid',
                split: true,
                width:280,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.centerPanel,
                {
	                region: 'east',
									layout: 'border',
	                iconCls:'icon-grid',
	                split: true,
	                width:280,
	                collapsible: true,
	                margins: '5 5 5 5',
	                items :[this.historyPriceGrid,this.productOrderPriceGrid]
                }
                ]     
       }
			],
			buttons : [{
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
   this.auditType = null;
   this.grid = null;
   this.setGrid = function(_grid){
   	 this.grid = _grid
   };
   this.setAuditType = function(_auditType){
     this.auditType = _auditType;	
   };
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
			var _this = this;
			
			Ext.Ajax.request({
				method: "post",
				params: { id : conId},
				url: PATH + "/contract/contractViewAction.do?ffc=contractViewById",
				success: function(response){
						eval("var temp = " + response.responseText);
						var conEditWin = new Ext.ffc.ContractViewAuditWindow({conctractInfor:temp,readOnly : true,
							auditType:_this.auditType,_grid:_this.grid});
						conEditWin.setId(conId);
						
						conEditWin.show();
						//select_quotations_win.close();
				}
			});
	   }
   }
}