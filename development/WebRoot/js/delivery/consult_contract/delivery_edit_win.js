
Ext.ffc.DeliveryInfoForm = Ext.extend(Ext.FormPanel, {
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
						//1
                            {xtype:'label',text: '交货单编号',x:0,y:5,style:this.lableStyle_},
							{xtype:'textfield',  name: 'deliveryCode',readOnly : true,x:90,y:3,width:170},
							{xtype:'label',text: '客户名称',x:280,y:5,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户名称', readOnly : true, name: 'customerName',x:370,y:3,width:170},
						//2
							{xtype:'label',text: '交货日期',x:0,y:33,style:this.lableStyle_},
							{xtype:'datefield',value:new Date(),fieldLabel: '交货日期',format:'Y-m-d',  name: 'deliveryDate',emptyText:'',x:90,y:31,width:170,allowBlank:false,blankText:'出库日期不能为空!'},
							{xtype:'label',text: '客户联系人',x:280,y:33,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户联系人', readOnly : true, name: 'cusContactPerson',x:370,y:31,width:170},
						//3
							{xtype:'label',text: '交货单状态',x:0,y:61,style:this.lableStyle_},
							new Ext.ffc.DeliveryStatusComboBox({readOnly : true,disabled:true,x:90,y:59,width:170}),
							{xtype:'label',text: '客户电话',x:280,y:61,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户电话', name: 'customerPhone',x:370,y:59,width:170},
						//4
							{xtype:'label',text: '合同编号',x:0,y:91,style:this.lableStyle_},
							{xtype:'textfield',  name: 'contractCode',readOnly : true,x:90,y:88,width:170},
							{xtype:'label',text: '客户传真',x:280,y:91,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户传真', readOnly : true, name: 'customerFax',x:370,y:88,width:170},
						//5
							/*{xtype:'label',text: '报价编号',x:0,y:121,style:this.lableStyle_},
							{xtype:'textfield',  name: 'quotationCode',readOnly : true,x:90,y:118,width:170},*/
							{xtype:'label',text: '客户我方联系人',x:280,y:121,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户我方联系人', readOnly : true, name: 'contactPerson',x:370,y:118,width:170},
						//6
							{xtype:'label',text: '备　　注',x:0,y:151,style:this.lableStyle_},
							{xtype:'textfield',  name: 'memo',x:90,y:148,width:170},
							{xtype:'label',text: '制  单  人',x:280,y:151,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '制  单  人', readOnly : true, name: 'userName',x:370,y:148,width:170},
						//hidden
							{xtype:'hidden',fieldLabel: '客户编号', readOnly : true, name: 'customerCode'},
							{xtype:'hidden',fieldLabel: '交货单类型', readOnly : true, name: 'deliveryType'},
							{xtype:'hidden',fieldLabel: 'id', readOnly : true, name: 'id'},
							{xtype:'hidden',fieldLabel: '合同id', readOnly : true, name: 'contractInforId'}
                        ]//items
		};

		Ext.ffc.DeliveryInfoForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.ContractInfoForm.superclass.constructor.call
	}
	
});

Ext.ffc.DeliveryNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.simpleForm = new Ext.ffc.DeliveryInfoForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var deliveryInfor = container.ownerCt.ownerCt.deliveryInfor;
			
			var params = [];
			for(var i in deliveryInfor){
				if(!deliveryInfor[i] || !deliveryInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,deliveryInfor);
			var myNewRecord = new RecordClum(recordParams); 
			this.ownerCt.simpleForm.getForm().loadRecord(myNewRecord);

		}); 
		
		Ext.ffc.DeliveryNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
				layout:'fit',
                iconCls:'icon-grid',
                title: '合同交货',
                //contentEl: 'south',
                split: true,
                width: 1050,
                height : 230,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});

Ext.ffc.getDeliveryEditCenterGridStore = function(jsonData){
	return new Ext.data.Store({
	   proxy:new Ext.data.MemoryProxy(jsonData),
	   reader: new Ext.data.JsonReader({
       root: 'deliveryProductDetailDto'  
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
		{name: 'contractProjectSortId',mapping:'contractProjectSortId',type:'string'},
		{name: 'contractProductDetailId',mapping:'contractProductDetailId',type:'string'},
		{name: 'proSortName',mapping:'proSortName',type:'string'},
        {name: 'projectCode',mapping:'projectCode',type:'string'},
		{name: 'productCode',mapping:'productCode',type:'string'},
        {name: 'serialNumber',mapping:'serialNumber',type:'string'},
		{name: 'brandCode',mapping:'brandCode',type:'string'},
		{name: 'productName',mapping:'productName',type:'string'},
	    {name: 'single_set_assembly_amount',mapping:'single_set_assembly_amount',type:'float'},
		{name: 'single_set_stock_amount',mapping:'single_set_stock_amount',type:'float'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'amount',mapping:'amount',type:'float'},
		{name: 'oldAmount',mapping:'amount',type:'float'},
		{name: 'productUnit',mapping:'productUnit',type:'string'},
		{name: 'price',mapping:'price',type:'float'},
		{name: 'rebate',mapping:'rebate',type:'float'},
		{name: 'netPrice',mapping:'netPrice',type:'float'},
		{name: 'productMoney',mapping:'productMoney',type:'string'},
		{name: 'taxNetPrice',mapping:'taxNetPrice',type:'string'},
		{name: 'priceChange',mapping:'priceChange',type:'string'},
		{name: 'productBrand',mapping:'productBrand',type:'string'},
		{name: 'workshop',mapping:'workshop',type:'string'},
		{name: 'toolsId',mapping:'toolsId',type:'string'},
		{name: 'parentToolsId',mapping:'parentToolsId',type:'string'},
		{name: 'leaf',mapping:'leaf',type:'string'},
		{name: 'allDeliveryAmount',mapping:'allDeliveryAmount',type:'float'},
		{name: 'allArrivalAmount',mapping:'allArrivalAmount',type:'float'},
		{name: 'memo',mapping:'memo',type:'string'},
		{name: 'memo2',mapping:'memo2',type:'string'}
     ])
	});
} 

/**
 * 容纳产品树容器
 * @class centerPanel
 * @extends Ext.Panel
 */
Ext.ffc.DeliveryEditCenterPanel = Ext.extend(Ext.grid.EditorGridPanel, {
	checkboxSelection:null,
	ffc_key : 'Ext.ffc.DeliveryEditCenterPanel',
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.checkboxSelection = new Ext.grid.CheckboxSelectionModel();
		Ext.ffc.DeliveryEditCenterPanel.superclass.constructor.call(this, {
			region: 'center',
			layout: 'fit',
			clicksToEdit:1,//单击修改
			sm:this.checkboxSelection,
            columns:[new Ext.grid.RowNumberer(),//自动行号
			this.checkboxSelection,
			{
	            header:'合同分项',
	            width:190,
	            resizable : true,
	            dataIndex:'proSortName'
	        },
			{
	            header:'合同分项id',
	            width:100,
				hidden:true,
	            dataIndex:'contractProjectSortId'
	        },
			{
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
	            header:'货品编号',
	            width:100,
	            resizable : true,
	            dataIndex:'productCode'
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
	            header:'合同数量',
	            width:100,
	            dataIndex:'contractAmount'
	        },/*{
	            header:'已到数量',
	            width:100,
	            dataIndex:'allArrivalAmount'
	        },*/{
	            header:'累计交货数量',
	            width:100,
	            dataIndex:'allDeliveryAmount'
	        },{
	            header:'可交数量',
	            width:100,
	            dataIndex:'needAmount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var rowData = record.data;
							var _allArrivalAmount = rowData.allArrivalAmount * 1;
							var _allDeliveryAmount = rowData.allDeliveryAmount * 1;
							_allArrivalAmount = !Ext.isNumber(_allArrivalAmount) ? 0 : _allArrivalAmount;
							_allDeliveryAmount = !Ext.isNumber(_allDeliveryAmount) ? 0 : _allDeliveryAmount;
							return _allArrivalAmount - _allDeliveryAmount;
				}
	        },{
	            header:'本次交货数量',
	            width:120,
	            dataIndex:'amount',
				editor: new Ext.form.NumberField({
					gridObj : this,
					listeners : {
						focus : function(f){
							f.selectText(0,f.getValue().length);
						},
						'specialkey' : Ext.ftl.gridEditorkeyMove,
					    change : function(field, newValue, oldValue ){
							try{
									var re = this.gridEditor['record'];
									var rowData = re.data;
									var _allArrivalAmount = rowData.allArrivalAmount * 1;
									var _allDeliveryAmount = rowData.allDeliveryAmount * 1;
									var _oldAmount = rowData.oldAmount * 1;//oldAmount
									_allArrivalAmount = !Ext.isNumber(_allArrivalAmount) ? 0 : _allArrivalAmount;
									_allDeliveryAmount = !Ext.isNumber(_allDeliveryAmount) ? 0 : _allDeliveryAmount;
									_oldAmount = !Ext.isNumber(_oldAmount) ? 0 : _oldAmount;
									if(newValue < 0){
										Ext.Msg.alert("消息", "交货数量不能为负数!");
										this.gridEditor.setValue(oldValue);
										return false;
									}
									if(newValue - _oldAmount <= 0){//如果改小了，直接过
										var _price = re.get('netPrice');
										_price = !Ext.isNumber(_price) ? 0 : _price;
										var _money = _price * newValue;
											_money = _money.toFixed(2);
										rowData.productMoney = _money;
									    return true;
									}
									var _needAmount = _allArrivalAmount - _allDeliveryAmount;
									if(newValue - _oldAmount > _needAmount){
										Ext.Msg.alert("消息", "交货数量不能大于可交数量!");
										this.gridEditor.setValue(oldValue);
										return false;
									}
									var _price = re.get('netPrice');
									_price = !Ext.isNumber(_price) ? 0 : _price;
									var _money = _price * newValue;
									_money = _money.toFixed(2);
									rowData.productMoney = _money;
									return true;
							}catch(e){alert(e);}
						}
					}//
				})
	        },{
	            header:'计量单位',
	            width:60,
	            dataIndex:'productUnit'
	        },{
	            header:'单价',
	            width:60,
				hidden:true,
	            name : 'price',
	            dataIndex:'price'
	        },{
	            header:'折扣',
	            width:50,
				hidden:true,
	            dataIndex:'rebate'
	        },{
	            header:'净价',
	            width:80,
	            dataIndex:'netPrice'
	        },{
	            header:'金额',
	            width:80,
	            dataIndex:'productMoney'
	        },{
	            header:'含税净价',
	            width:80,
				hidden:true,
	            dataIndex:'taxNetPrice'
	        },{
	            header:'含税金额',
	            width:80,
				hidden:true,
	            dataIndex:'taxMoney'
	        },{
	            header:'价格变动',
	            width:80,
				hidden:true,
	            dataIndex:'priceChange'
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand'
	        },{
	            header:'备注',
	            width:80,
				editor:new Ext.form.TextField({
						maxLength : 100,
						listeners : {
							focus : function(f){
								f.selectText(0,f.getValue().length);
							}
						}
					}
				),
	            dataIndex:'memo'
	        },{
	            header:'备注2',
	            width:80,
				editor:new Ext.form.TextField({
						maxLength : 100,
						listeners : {
							focus : function(f){
								f.selectText(0,f.getValue().length);
							}
						}
					}
				),
	            dataIndex:'memo2'
	        },{
	            header:'合同分项id',
	            width:100,
				hidden:true,
	            dataIndex:'toolsId'
	        },{
	            header:'合同分项id',
	            width:100,
				hidden:true,
	            dataIndex:'parentToolsId'
	        },{
	            header:'合同分项id',
	            width:100,
				hidden:true,
	            dataIndex:'leaf'
	        },{
	            header:'合同明细id',
	            width:100,
				hidden:true,
	            dataIndex:'contractProductDetailId'
	        }],
			tbar :	[
					    {text : "删除",iconCls : 'icon-delete',hidden: this.readOnly,
							handler : function(){
								var grid = this;
								var arr = this.checkboxSelection.getSelections();
								if(arr.length < 1) {
										Ext.Msg.show({title:'系统提示',msg: '请选择一条记录进行操作!',buttons: Ext.Msg.OK,icon: Ext.MessageBox.INFO});
									return;
								}
								Ext.MessageBox.show({title:'系统提示',msg: '请确认要删除当前记录!',buttons: Ext.MessageBox.OKCANCEL,fn: function(btn){
									if(btn == 'ok') {
										 var ids = [];
										 for(var i = 0;i < arr.length ;i++ ){
											 ids.push(arr[i].get("id"));
										 }
										 Ext.Ajax.request({
											method: "post",
											params: { 'ids' : ids},
											url: PATH + "/delivery/deliveryEditAction.do?ffc=delDeliveryDetail",
											success: function(response){
													var store = grid.getStore();
													for(var i = 0;i < arr.length ;i++ ){
														store.remove(arr[i]);
													}
											}
										});
									}
								},scope:this});	
							},scope:this
						}
					],
			bbar : [
					{xtype:'label',html: "<span>合同交货地点及运输方式：</span><span style=\"color:green\">" + this.deliveryAddressType + "</span>,<span style=\"color:red\">" + this.trafficMode + "</span>"}
				]
		})
	}
})


Ext.ffc.DeliveryEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	deliveryInfor : null,
	readOnly : false,
	DeliveryEditCenterGridStore : null,
	addDetailTabPanel:function(sortArr){
		var centerPanels = [];
		for(var i = 0 ;i < sortArr.length;i++){
			var pan = new Ext.ffc.CenterPanel({
				'title' :  sortArr[i].name,
				'id' : sortArr[i].id,
				'proDetail' :  sortArr[i].conProductDetail
			});
		    centerPanels.push(pan);
		}

		this.centerPanel.add(centerPanels);
		return centerPanels;
	},
	removeDetailTabPanel:function(tabp){
		var conctractInfor = this.conctractInfor;
		var contractProductSorts = conctractInfor.contractProductSorts;
		var nArr = [];
		for(var i = 0;i < contractProductSorts.length;i++){
			if(contractProductSorts[i].id !== tabp.id){
				nArr.push(contractProductSorts[i]);
			}
		}
		conctractInfor.contractProductSorts = nArr;
	},
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.topPanel = new Ext.ffc.DeliveryNorthPanel({readOnly:_cfg.readOnly});
		this.DeliveryEditCenterGridStore = Ext.ffc.getDeliveryEditCenterGridStore(this.deliveryInfor);
		this.centerPanel = new Ext.ffc.DeliveryEditCenterPanel({
			readOnly:_cfg.readOnly,
			ds : this.DeliveryEditCenterGridStore,
			store: this.DeliveryEditCenterGridStore,
			deliveryAddressType:this.deliveryInfor.deliveryAddressType,
			trafficMode:this.deliveryInfor.trafficMode
		});
		this.DeliveryEditCenterGridStore.load();
		
		Ext.ffc.DeliveryEditWindow.superclass.constructor.call(this, {
			//el:'contractEditWinEl',
			width : 1050,
			height : 600,
			maximizable :true,
			//title :  '交货单编制',
			layout :  'border',
			//closeAction:'hide',
			items : [this.topPanel,this.centerPanel],
			buttons: [{
				text : "保  存",
				hidden: this.readOnly,
				handler : function(obj) {
					var form = this.topPanel.simpleForm;//obj.ownerCt.ownerCt;
					var currWin = form.ownerCt.ownerCt;
					var formValues = form.getForm().getValues();
					if(Ext.isEmpty(formValues.deliveryDate)){
						Ext.Msg.alert("消息", "交货日期不能为空！!");
						return ;
					}
					
					
					
					var gridStore = currWin.DeliveryEditCenterGridStore;
					var records = gridStore.getModifiedRecords();
					var resultObjs = [];
					for(var i = 0 ;i < records.length;i++){
						var da = records[i].data;
						var _amount = da.amount * 1
						_amount = !Ext.isNumber(_amount) ? 0 : _amount;
						if( _amount > 0){
							resultObjs.push(da);					
						}
					}
					formValues["deliveryProductDetailDto"] = resultObjs;
					var serUrl = '';
					if(formValues["id"] == ''){//add
					    if(resultObjs.length == 0){
						    Ext.Msg.alert("消息", "产品明细中至少要有一项，交货数量大于零!");
							return ;
						}
						serUrl = PATH + "/delivery/deliveryEditAction.do?ffc=addDelivery";
					}else{//update
						serUrl = PATH + "/delivery/deliveryEditAction.do?ffc=updateDelivery";
					}
					Ext.Ajax.request({
							method: "post",
							params: { data : Ext.encode(formValues)},
							url: serUrl,
							success: function(response){
								Ext.Msg.alert("消息", "保存成功!");	
								currWin.close();
							}
					});
				
				},scope : this
	         },{
				text : "取  消",
				handler : function() {
					this.close();
				},scope : this
	         }]
		});
	}
});  

Ext.ffc.DeliveryAuditWindow = Ext.extend(Ext.ffc.AuditBusinessDetailWindow, {  
	topPanel : null,
	centerPanel : null,
	deliveryInfor : null,
	readOnly : false,
	DeliveryEditCenterGridStore : null,
	addDetailTabPanel:function(sortArr){
		var centerPanels = [];
		for(var i = 0 ;i < sortArr.length;i++){
			var pan = new Ext.ffc.CenterPanel({
				'title' :  sortArr[i].name,
				'id' : sortArr[i].id,
				'proDetail' :  sortArr[i].conProductDetail
			});
		    centerPanels.push(pan);
		}

		this.centerPanel.add(centerPanels);
		return centerPanels;
	},
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.topPanel = new Ext.ffc.DeliveryNorthPanel({readOnly:_cfg.readOnly});
		this.DeliveryEditCenterGridStore = Ext.ffc.getDeliveryEditCenterGridStore(this.deliveryInfor);
		this.centerPanel = new Ext.ffc.DeliveryEditCenterPanel({
			readOnly:_cfg.readOnly,
			ds : this.DeliveryEditCenterGridStore,
			store: this.DeliveryEditCenterGridStore,
			deliveryAddressType:this.deliveryInfor.deliveryAddressType,
			trafficMode:this.deliveryInfor.trafficMode
		});
		this.DeliveryEditCenterGridStore.load();
		
		Ext.ffc.DeliveryAuditWindow.superclass.constructor.call(this, {
			//el:'contractEditWinEl',
			width : 1050,
			height : 600,
			maximizable :true,
			//title :  '交货单编制',
			layout :  'border',
			//closeAction:'hide',
			items : [this.topPanel,this.centerPanel]
		});
	}
});  

 DetailWindow = function(){
   this.method = null;
   this.id = null,
   this.on = function(paraString,fun){
		this.method = fun;
   };
   this.setId = function(tid){
		this.id = tid;
   };
   this.setAuditType = function(_auditType){
     this.auditType = _auditType;	
   };
   this.setGrid = function(_grid){
   	 this.grid = _grid
   };
   this.show = function(){
       if(this.method != null){
	       this.method();
			var conId = this.id;
			var _this = this;
			Ext.Ajax.request({
				method: "post",
				params: { id : conId},
				url: PATH + '/delivery/deliveryViewAction.do?ffc=deliveryViewById',
				success: function(response){
						eval("var temp = " + response.responseText);
						var conEditWin = new Ext.ffc.DeliveryAuditWindow({
							deliveryInfor : temp,
							readOnly:true,
							title :  '查看交货单',
							auditType:_this.auditType,
							_grid:_this.grid,
							_id:_this.id
						});
						conEditWin.show();	
				}
			});
	   }
   }
}