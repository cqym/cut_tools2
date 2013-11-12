Ext.namespace('Ext.ffc.PurchaseOrder');

/**添加产品**/
Ext.ffc.PurchaseOrder.add = function(btn,e){
	var grid = btn.ownerCt.ownerCt;
	var store = grid.store;
	var win = new Ext.ffc.purchaseProductsWindow({
		quotationCode:grid.orderInfor.quotationCode,
		contractCode:grid.orderInfor.contractCode,
		supplierId:grid.orderInfor.supplierId,
		orderType:grid.orderInfor.orderType,
		callBackMethod:function(reArr,win){
				for(var i=0;i<reArr.length;i++){
						var reType = store.recordType;
						var initObj = {
							'id':Math.random(100),'contractAmount':0,
						'remainAmount':0,'parentToolsId':'','contractProductDetailId':'',
						'contractProjectSortId':'','proSortName':'','projectCode':0,'serialNumber':0,'orderAmount':0,'productMoney':0,
						'deliveryDate':'','memo':'','memo2':'','contractDeliveryDate':'','toolsTypeName':''
						};
						Ext.applyIf(initObj, reArr[i].data);
						if(initObj.leaf == 'true'){
							initObj.leaf = 1;
						}else{
						  initObj.leaf = 0;
						}
					  var t = new reType(initObj);
						store.add(t);
						grid.orderInfor.orderDetail.push(initObj);
						//store.add(new reType(reArr[i].data));
				}
	  }});
	win.show();
};

/**删除产品**/
Ext.ffc.PurchaseOrder.deleteProduct = function(btn,e){
	var grid = btn.ownerCt.ownerCt;
	var store = grid.getStore();
	var arr = grid.getSelectionModel().getSelections();
	var array = [];
	if(arr.length < 1) {
			Ext.Msg.show({title:'系统提示',msg: '请选择一条记录进行操作!',buttons: Ext.Msg.OK,icon: Ext.MessageBox.INFO});
		return;
	}
	var handleDelete = function (btn){
		if(btn == 'ok') {	
			if(grid.store.getCount()<=arr.length)
			{
				Ext.Msg.show({title:'系统提示',msg: '不能全部删除!',buttons: Ext.Msg.OK,icon: Ext.MessageBox.INFO});
				return
			}
			else
			{
			  var orderDetail =	grid.orderInfor.orderDetail;
				for(var i=0;i<arr.length;i++)
				{
					if(arr[i].get('id')!=null)
					{
						array.push(arr[i].get('id'));
					}
					store.remove(arr[i]);
					for(var j = 0;j < orderDetail.length ;j++){
					  if(orderDetail[j].id == arr[i].get('id')){
						   orderDetail.remove(orderDetail[j]);
						   break; 
						}
				  }
				}
				delete_detail(grid.ownerCt.ownerCt.form.getForm(),store);
				Ext.Ajax.request({
					url: PATH + '/purchaseOrder/PurchaseOrderEditAction.do',
					params: {method:"deleteOrderDetail",ids:array,orderInfor: Ext.util.JSON.encode(grid.ownerCt.ownerCt.form.getForm().getValues())},
					success : function(response) {
						
					},
					scope : this
				});
			}

		}
	}
	Ext.MessageBox.show({title:'系统提示',msg: '请确认要删除当前记录!',buttons: Ext.MessageBox.OKCANCEL,fn: handleDelete,scope:this});			
}

/**统一设置交货期限**/
Ext.ffc.PurchaseOrder.setupDate = function(btn,e)
{
	var grid = btn.ownerCt.ownerCt;
	var store = grid.getStore();
	var deliveryDate = null;
	var dataf = new Ext.form.DateField({format:'Y-m-d'});
	var menu = new Ext.menu.DateMenu();
	menu["on"]("select", function(a,b){
		var bb = dataf.formatDate(dataf.parseDate(b));
		for(var i = 0 ;i < grid.orderInfor.orderDetail.length;i++){
		    	grid.orderInfor.orderDetail[i].deliveryDate = bb;
		}
		store.each(function(record){
			record.set('deliveryDate',bb);
		});
		menu.hide();
	});
	menu.show(btn.getEl());	
};

/**历史价格查询**/
Ext.ffc.PurchaseOrder.queryHistory = function(btn,e)
{
	var grid = btn.ownerCt.ownerCt;
	var store = grid.getStore();
	try
	{	
		var favlues = grid.ownerCt.ownerCt.form.getForm().getValues();
		var supplierId = favlues.supplierId;
		var supplierName = favlues.supplierName;
		var brandCode = favlues.supplierId;
		new Ext.ffc.OrderHisPriceSearchWin({
			supplierId:supplierId,
			supplierName:supplierName,
			brandCode:brandCode
		}).show();
	}
	catch (ex){alert(ex);}
};

/**合同订单详细grid**/
	Ext.ffc.PurchaseOrder.PurchaseOrderDetail =  Ext.extend(Ext.grid.EditorGridPanel,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.Store({
			proxy:new Ext.data.MemoryProxy(_cfg.orderInfor.orderDetail),
			reader: new Ext.data.JsonReader({},
			['id','brandCode','productCode','productName','productUnit','contractAmount',
				'remainAmount','productBrand','price','parentToolsId','toolsId','leaf','contractProductDetailId',
				'contractProjectSortId','proSortName','projectCode','serialNumber','orderAmount','productMoney',
				'deliveryDate','memo','memo2','contractDeliveryDate','toolsTypeName','fileCount'
			])
		});
		
		this.sm = new Ext.grid.CheckboxSelectionModel();
		if(!this.btToolsHidden){
		 this.tbar = [
		      {text : "添加",iconCls:'icon-add',handler :Ext.ffc.PurchaseOrder.add,scope : this,hidden:isNotReserveOrder(_cfg.orderInfor.orderType)},
					{xtype:'tbseparator',hidden:isNotReserveOrder(_cfg.orderInfor.orderType)},
					{text : "删除",iconCls : 'icon-delete',handler : Ext.ffc.PurchaseOrder.deleteProduct,scope:this},
					{xtype:'tbseparator'},
					{text : '统一设置交货期限',iconCls : 'icon-date',handler :Ext.ffc.PurchaseOrder.setupDate,scope : this},
					{xtype:'tbseparator'},
					{text : '历史价格查询',iconCls : 'icon-date',handler :Ext.ffc.PurchaseOrder.queryHistory,scope : this}]

		}
		if(isContractOrder(this.orderInfor.orderType)){
          this.bbar  = [{xtype:'label',html : "销售合同交货地点及运输方式:<font color = 'red'>"+this.orderInfor.contractDeliveryAddress+"</font>,<font color='green'>"+this.orderInfor.contractTrafficMode+"</font>,合同紧急程度:<font color = 'blue'>" + Ext.ffc.UrgentLevelTranction(this.orderInfor.contractUrgentLevel) + "</font>"}];
		}
		var orderTypeTitlePart = getOrderTypeTitlePart(this.orderInfor.orderType);
		
		Ext.ffc.PurchaseOrder.PurchaseOrderDetail.superclass.constructor.call(this, {
			layout : 'fit',
			clicksToEdit : 1,
			frame : true,
			sm : this.sm,
			store : this.store,
			columns:[
					 new Ext.grid.RowNumberer(),
					 this.sm,
					{header:'订单ID',width:100,dataIndex:'id',hidden:true},
					{
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
	        },
					{header:orderTypeTitlePart + '分项名称',width:100,dataIndex:'proSortName',sortable:true,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'proSortName')},
					{header:'项目',width:50,dataIndex:'projectCode',sortable:true,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'projectCode')},
					{header:'序号',width:50,dataIndex:'serialNumber',sortable:true,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'serialNumber')},
					{header:'货品编号',width:100,dataIndex:'productCode',sortable:true},
					{header:'名称',width:125,dataIndex:'productName',sortable:true},
					{header:'牌号',width:230,dataIndex:'brandCode',sortable:true},
					{header:'计量单位',width:70,dataIndex:'productUnit',sortable:true},
					{header:orderTypeTitlePart + '数量',width:100,dataIndex:'contractAmount',sortable:true,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'contractAmount')},
					{header:orderTypeTitlePart + '剩余数量',width:120,dataIndex:'remainAmount',sortable:true,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'remainAmount')},
					{header:'采购数量',width:70,dataIndex:'orderAmount',sortable:true,editor: new Ext.form.NumberField({
						   allowBlank: false,
						   allowNegative: false,
						   style: 'text-align:left',
						   gridObj:this,
						   listeners : {
								focus : function(f){
										f.selectText(0,f.getValue().length);
									}
								,'specialkey' : Ext.ftl.gridEditorkeyMove
							}
						}),
						renderer:function(value,metadata,record)
						{
							if(value == null || value == '')
							{
								record.set('orderAmount',0);
								return 0;
							}
							else
								return value;
						}
					},
					{header:'采购单价',width:70,dataIndex:'price',editor: new Ext.form.NumberField({
						   allowBlank: false,
						   allowNegative: false,
						   style: 'text-align:left',
						   gridObj:this,
						listeners : {
							focus : function(f){
									f.selectText(0,f.getValue().length);
								}
							,'specialkey' : Ext.ftl.gridEditorkeyMove
							}
						})
					},
					{header:'小计金额',width:100,dataIndex:'productMoney',sortable:true},
					{header:'品牌',width:100,dataIndex:'productBrand',sortable:true},
					{header:'产品类别',width:100,dataIndex:'toolsTypeName',sortable:true},
					{header:'采购交货期',width:100,dataIndex:'deliveryDate',
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
					},
					{header:orderTypeTitlePart + '交货期',width:100,dataIndex:'contractDeliveryDate',hidden:isOrderTypeColHidden(this.orderInfor.orderType,'contractDeliveryDate')},
					
					{header:'备注1',width:100,dataIndex:'memo',editor:new Ext.form.TextField({
						listeners : {
							'specialkey' : function(_field, e){
								_field.gridObj = rtGrid;
								Ext.ftl.gridEditorkeyMove(_field, e);
							}
					   }
					})},
					{header:'备注2',width:100,dataIndex:'memo2',editor:new Ext.form.TextField({
						listeners : {
							'specialkey' : function(_field, e){
								_field.gridObj = rtGrid;
								Ext.ftl.gridEditorkeyMove(_field, e);
							}
					   }
					})},
					{
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
					},
					{header:'货品工具父节点id',width:0,hidden : true,dataIndex:'parentToolsId',sortable:true},
					{header:'货品工具叶子节点',width:0,hidden : true,dataIndex:'leaf',sortable:true},
					{header:'合同货主键',width:100,hidden : true,dataIndex:'contractProductDetailId',sortable:true},
					{header:'合同分_主键',width:70,dataIndex:'contractProjectSortId',hidden : true}
			],
			
			listeners : {
				/**注册事件编辑事件(判断采购数量)**/
				'afteredit' : function(e) {
					if(e.field == 'memo' || e.field == 'memo2'){return ;}
					var orderDetail = this.orderInfor.orderDetail;
					
					if(e.field == 'orderAmount' 
					&& e.record.get('orderAmount')>e.record.get('remainAmount')+e.originalValue 
					&& (this.orderInfor.orderType != 2 && this.orderInfor.orderType != 4)){
						Ext.MessageBox.alert('系统提示', '采购数量超出需采购数量');
						e.record.set('orderAmount',e.originalValue);
						return
					}
					if(e.field == 'orderAmount'){
						e.record.set('remainAmount',e.record.get('remainAmount')-e.record.get('orderAmount')+e.originalValue);
					}
					e.record.set('productMoney',(e.record.get('orderAmount')*e.record.get('price')*1).toFixed(2));
					for(var i = 0;i < orderDetail.length;i++){
					    if(e.record.get('id') == orderDetail[i].id){
					    	   Ext.apply(orderDetail[i],e.record.data);
									 break;
					    }
					}
					delete_detail(this.ownerCt.ownerCt.form.getForm(),this.store);
				},scope:this
			}
		   })
		}
	})

/**添加订单form**/
	Ext.ffc.PurchaseOrder.mainForm = Ext.extend(Ext.FormPanel,{
		detailFlag:null,//是否明细页面
		isReadOnly:null,//输入框是否为只读
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
//			if(this.detailFlag)
//			{
//				this.isReadOnly = true;
//			}
			this.status = new orderStatusCombox({disabled : true,x:100,y:93,width:170});
			this.taxRateCombox = new TaxrateCombox({disabled : this.isReadOnly,x:900,y:33,width:170});
			this.taxRateCombox.on('select',function(combo, record,index){ change_rate(this.getForm());},this);	 
			this.companyCombox = new companyCombox({disabled : this.isReadOnly,x:630,y:63,width:170});
			this.orderDate = new Ext.form.DateField({
				fieldLabel: '订货日期', format:'Y-m-d',name: 'orderDate' ,value:new Date(),x:100,y:30, width:170, format:'Y-m-d', 
				emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
			});
			this.currCombox = new orderCurrencyCombox({disabled : this.isReadOnly,x:630,y:152,width:170,ownerForm:this,
			'changeCallBack' : Ext.ffc.orderCurrenyRateChangeCallBack	
			});
			this.deliveryDate = new Ext.form.DateField({
				fieldLabel: '交货日期', format:'Y-m-d',name: 'deliveryDate' ,x:350,y:120, width:170, format:'Y-m-d', 
				emptyText:'', allowBlank : false, blankText:'该项为必填项!',disabled : this.isReadOnly
			});
			this.OrderClosingAccountModeComboBox = new Ext.ffc.OrderClosingAccountModeComboBox({disabled : this.isReadOnly,x:100,y:210,width:418,allowBlank : false});
//			this.status = new StatusCombox({disabled : true,x:100,y:93,width:170,value:0});
			this.lableStyle_ = "font-size:9pt;text-align:right;width:100px;";
			var config = [
				//
					{xtype:'label',text: '订单编号:',x:0,y:5,style:this.lableStyle_},
					{xtype:'textfield', readOnly : true, x:100,y:3, width:170,name: 'orderCode'},
					{xtype:'label',text: '供应商:',x:250,y:5,style:this.lableStyle_},
					{xtype:'textfield',  name: 'supplierName',readOnly : true,x:350,y:3,width:170},
					
					{xtype:'label',text: '合同编号:',x:530,y:5,style:this.lableStyle_,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'contractCode')},
					{xtype:'textfield',  name: 'contractCode',readOnly : true,x:630,y:2,width:170,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'contractCode')},
					
					{xtype:'label',text: '报价单号:',x:530,y:5,style:this.lableStyle_,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'quotationCode')},
					{xtype:'textfield',  name: 'quotationCode',readOnly : true,x:630,y:2,width:170,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'quotationCode')},
					
					{xtype:'label',text: '货品金额:',x:800,y:5,value:0,style:this.lableStyle_},
					{xtype:'numberfield',  name: 'productMoney',readOnly : true,x:900,y:3,width:170,value:0},
					//2
					{xtype:'label',text: '订货日期:',x:0,y:35,style:this.lableStyle_},
					this.orderDate,
					{xtype:'label',text: '供应商负责人:',x:275,y:35},
					{xtype:'textfield',  name: 'supplierContactPerson',readOnly : true,x:350,y:33,width:170},
					{xtype:'label',text: '客户我方负责人:',x:530,y:35,style:this.lableStyle_,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'ownContactPerson')},
					{xtype:'textfield',  name: 'ownContactPerson',readOnly : true,x:630,y:33,width:170,hidden:isOrderTypeColHidden(this.orderInfor.orderType,'ownContactPerson')},
					{xtype:'label',text: '税  率:',x:800,y:35,style:this.lableStyle_},
					this.taxRateCombox,
					//3
					{xtype:'label',text: '紧急程度:',x:0,y:65,style:this.lableStyle_},
					new Ext.ffc.UrgentLevelCombox({x:100,y:62, width:170,disabled : this.isReadOnly}),
					{xtype:'label',text: '供应商电话:',x:250,y:65,style:this.lableStyle_},
					{xtype:'textfield',  name: 'supplierPhone',readOnly : true,x:350,y:63,width:170},
					{xtype:'label',text: '卖方名称:',x:530,y:65,style:this.lableStyle_},
					this.companyCombox,
					{xtype:'label',text: '税　　金:',x:800,y:65,style:this.lableStyle_},
					{xtype:'numberfield',  name: 'taxMoney',readOnly : true,x:900,y:63,width:170,value:0},
					//4
					{xtype:'label',text: '订单状态:',x:0,y:95,style:this.lableStyle_},
					this.status,
//					{xtype:'textfield',  name: '',value:'编制',readOnly : true,x:100,y:93,width:170},
					{xtype:'label',text: '供应商传真:',x:250,y:95,style:this.lableStyle_},
					{xtype:'textfield',  name: 'supplierFax',readOnly : true,x:350,y:93,width:170},
					{xtype:'label',text: '供应商我方负责人:',x:530,y:95,style:this.lableStyle_},
					{xtype:'textfield', name: 'supplierOwnContactPerson',readOnly : true,x:630,y:95,width:170},
					{xtype:'label',text: '税价合计:',x:800,y:95,style:this.lableStyle_},
					{xtype:'numberfield',  name: 'totalMoney',readOnly : true,x:900,y:93,width:170,value:0},
					//5
					{xtype:'label',text: '质量标准:',x:0,y:125,style:this.lableStyle_},
					{xtype:'textfield',  name: 'qualityStandard',allowBlank : false,value:'参照供方标准',readOnly : this.isReadOnly,x:100,y:122,width:170},
					//{xtype:'label',text: '交货日期:',x:250,y:125,style:this.lableStyle_},
					//this.deliveryDate,
					{xtype:'label',text: '制 单 人:',x:530,y:125,style:this.lableStyle_},
					{xtype:'textfield', name: 'userName',value:LoginInfor.user.userName,readOnly : true,x:630,y:125,width:170},
					{xtype:'label',text: '整单折扣(%):',x:800,y:125,style:this.lableStyle_},
					{xtype:'numberfield',name: 'overallRebate',value:0, readOnly : this.isReadOnly,
							allowDecimals : false,
							allowNegative : false,
							decimalPrecision : 0,
							minValue : 0,
							maxValue : 100,
							x:900,y:123,width:170,
							validator : function(_value) {
								if(_value >= 0 && _value <= 100) {
									return true;
								} else {
									return false;
								}
						   },
						   listeners:{  
							   'change':function(filed,newValue,oldValue ){ 
									change_overallRebate(this.getForm(),newValue);
							    },scope:this
							}
					   },
					//6
					 {xtype:'label',text: '交提货地点:',x:0,y:155,style:this.lableStyle_},
					 {xtype:'textfield', name: 'deliveryAddressType', allowBlank : false, x:100,y:152,width:420},
					 {xtype:'label',text: '币　别:',x:530,y:155,style:this.lableStyle_},
					 this.currCombox,
					 {xtype:'label',text: '最终金额:',x:800,y:155,style:this.lableStyle_},
					 {xtype:'numberfield', name: 'finalMoney', allowBlank : false, x:900,y:152,width:170,value:0},
					 //7
					 {xtype:'label',text: '交货方式:',x:0,y:185,style:this.lableStyle_},
					 new Ext.ffc.TrafficModeComboBox({x:100,y:182, width : 420,disabled : this.isReadOnly}),
					 {xtype:'label',text: '合同违约责任:',x:530,y:185,style:this.lableStyle_},
					 {xtype:'textfield' ,name: 'defaultDuty', readOnly : this.isReadOnly,value:'无。', allowBlank : false,x:630,y:182, width : 440},
					 //8
					 {xtype:'label',text: '结算方式及期限:',x:0,y:215,style:this.lableStyle_},
					 this.OrderClosingAccountModeComboBox,
					 {xtype:'label',text: '合同生效条件:',x:530,y:215,style:this.lableStyle_},
					 {xtype:'textfield' ,name: 'effectConditions',x:630,y:212 ,width : 440, readOnly : this.isReadOnly,value:'本合同一式两份，卖方一份，买方一份，双方签字盖章后生效。', allowBlank : false},
					 //8
					 {xtype:'label',text: '其他约定事项:',x:0,y:245,style:this.lableStyle_},
					 {xtype:'textfield' ,name: 'otherConvention', width : 420, readOnly : this.isReadOnly,value:'无。', allowBlank : false,x:100,y:240},
					 {xtype:'label',text: '备注:',x:530,y:245,style:this.lableStyle_},
					 {xtype:'textfield' ,name: 'mome', width : 440, readOnly : this.isReadOnly,x:630,y:242},
					//hidden
					 {xtype:'hidden',  name: 'customerCode'},
					 {xtype:'hidden',  name: 'customerName'},
					 {xtype:'hidden',  name: 'supplierId'},
					 {xtype:'hidden',  name: 'id'},
					 {xtype:'hidden',  name: 'orderType'},
					 {xtype:'hidden',  name: 'currencyId'},
					 {xtype:'hidden',  name: 'quotationId'}  
				//
				];
			Ext.ffc.PurchaseOrder.mainForm.superclass.constructor.call(this, {
					width : 1000,
					labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
					frame:true,labelWidth:75,monitorValid:false,
					layout : 'absolute',
					items:config
			})
		}
 })
	 

	Ext.ffc.PurchaseOrder.editWin = Ext.extend(Ext.Window,{
		grid:null,
		form:null,
		orderInfor:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.form = new Ext.ffc.PurchaseOrder.mainForm({orderInfor:this.orderInfor});
			this.form.on("afterrender",function(container, layout ){
				var orderInfor = container.ownerCt.ownerCt.orderInfor;
				var params = [];
				for(var i in orderInfor){
					if(!orderInfor[i] || !orderInfor[i].length){
						params.push({name:i,mapping:i});
					}
				}
				var RecordClum = Ext.data.Record.create(params);
				var recordParams = {};
				Ext.apply(recordParams,orderInfor);
				var myNewRecord = new RecordClum(recordParams); 
				with(container.getForm()){
					loadRecord(myNewRecord);
			  }
			});
			
			this.grid = new Ext.ffc.PurchaseOrder.PurchaseOrderDetail({orderInfor:this.orderInfor,btToolsHidden:this.btToolsHidden});
			this.grid.getStore().load();
      
			Ext.ffc.PurchaseOrder.editWin.superclass.constructor.call(this, {
				    renderTo: Ext.getBody(),
						width:1140,  
						height:610,  
						plain:true,
						draggable:true,
						resizable:false,
						maximizable: true,
						layout:"border",  
						buttons : [{
							text : "保存",
							hidden:this.SaveBtnHidden,
							handler : function() {
								var formV = this.form.getForm().getValues();
								if(!this.form.getForm().isValid()){return;};
								
								Ext.apply(this.orderInfor, formV);
								if(!checkOrderDetail(this.orderInfor.orderDetail,formV)){return;}
								for(var i = 0; i < this.orderInfor.orderDetail.length ;i++ ){
									if(Ext.isNumber(this.orderInfor.orderDetail[i].id)){
										this.orderInfor.orderDetail[i].id = null;
									}
								}
								var method = 'addPurchaseOrder';
								if(this.orderInfor.id && this.orderInfor.id != ''){
								    method = 'updatePurchaseOrder';
								}
								var win = this;
								Ext.Ajax.request({
								url: PATH + '/purchaseOrder/PurchaseOrderEditAction.do',
									params: { orderInfor: Ext.util.JSON.encode(this.orderInfor),method:method},
									success : function(response) {
											 if(response.responseText != 'false'){
											 	 var orderId = response.responseText;
													Ext.Ajax.request({
														url: PATH + '/contractOrder/CmprStockPrice.do',
														params: { conType:"contract", orderInforId : orderId},
														success : function(_response) {
															var _responseArray = Ext.util.JSON.decode(_response.responseText); 
															 if(_responseArray.success == true){
																 Ext.Msg.show({title:'价格提示',msg:"序列号为:"+ _responseArray.numStr+"产品的采购价格高于合同中采购价格",buttons: Ext.Msg.OK,icon: Ext.MessageBox.INFO,width: 300});
															 }
													}})
												 Ext.Msg.show({title:'成功提示',msg: '保存成功',buttons: Ext.Msg.OK,icon: Ext.MessageBox.INFO});
											   win.close();
											   
											 } 
											 else {Ext.Msg.show({title:'错误提示',msg: responseArray.msg,buttons: Ext.Msg.OK,icon: Ext.MessageBox.ERROR});} 
									},scope : this
								});
							},scope : this
						 },{
							text : "关闭",
							handler : function() {
								this.close();
							},scope : this
						 }],
					items : [ {
								region: 'north',
								title: this.TopTitle,
								iconCls:'icon-grid',
								split: true,
								height : 305,
								minSize: 10,
								maxSize: 305,
								layout:"fit",
								collapsible: true,
								margins: '0 0 0 0',
								items : [this.form]
							}, {
								region: 'center',
								iconCls:'icon-grid',
								split: true,
								layout: 'fit',
								collapsible: true,
								margins: '0 0 0 0',
								items : [this.grid]
							}],
					listeners : {
					    'close':function(){
					       if(this.listGrid){
										this.listGrid.getStore().reload();
								 }
					    },scope:this
					}
			})
		}
	})


	Ext.ffc.PurchaseOrder.AuditWin = Ext.extend(Ext.ffc.AuditBusinessDetailWindow,{
		grid:null,
		form:null,
		orderInfor:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.form = new Ext.ffc.PurchaseOrder.mainForm({orderInfor:this.orderInfor});
			this.form.on("afterrender",function(container, layout ){
				var orderInfor = container.ownerCt.ownerCt.orderInfor;
				var params = [];
				for(var i in orderInfor){
					if(!orderInfor[i] || !orderInfor[i].length){
						params.push({name:i,mapping:i});
					}
				}
				var RecordClum = Ext.data.Record.create(params);
				var recordParams = {};
				Ext.apply(recordParams,orderInfor);
				var myNewRecord = new RecordClum(recordParams); 
				with(container.getForm()){
					loadRecord(myNewRecord);
			  }
			});
			
			this.grid = new Ext.ffc.PurchaseOrder.PurchaseOrderDetail({orderInfor:this.orderInfor,btToolsHidden:this.btToolsHidden});
			this.grid.getStore().load();
      
			Ext.ffc.PurchaseOrder.AuditWin.superclass.constructor.call(this, {
				    renderTo: Ext.getBody(),
						width:1140,  
						height:610,  
						plain:true,
						draggable:true,
						resizable:false,
						maximizable: true,
						layout:"border",  
					items : [ {
								region: 'north',
								title: this.TopTitle,
								iconCls:'icon-grid',
								split: true,
								height : 305,
								minSize: 10,
								maxSize: 305,
								layout:"fit",
								collapsible: true,
								margins: '0 0 0 0',
								items : [this.form]
							}, {
								region: 'center',
								iconCls:'icon-grid',
								split: true,
								layout: 'fit',
								collapsible: true,
								margins: '0 0 0 0',
								items : [this.grid]
							}],
					listeners : {
					    'close':function(){
					       if(this.listGrid){
										this.listGrid.getStore().reload();
								 }
					    },scope:this
					}
			})
		}
	})
	
DetailWindow = function(id){
   this.method = null;
   this.id = null;
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
	    var _this = this;	    
			loadOrderAuditWindowById({
								  title:'查看订单',
							    SaveBtnHidden : true,
							    btToolsHidden:true,
							    auditButtonHiden:false,
							    auditType:this.auditType,
							    _grid:this.grid,
							    _id:this.id,
							    loadDataParam:{orderId:_this.id}
							});
	   }
   }
}