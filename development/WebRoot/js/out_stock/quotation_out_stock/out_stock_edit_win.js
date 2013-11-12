
Ext.ffc.QuotationOutStockInfoForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var f_config = {layout: 'absolute',
						defaultType: 'textfield',
						frame: true,
						items:[
							//1
							{xtype:'label',text: '出库单号',x:0,y:5,style:this.lableStyle_},
							{xtype:'textfield',  name: 'outStockCode',readOnly : true,x:90,y:3,width:170},
							{xtype:'label',text: '报价编号',x:250,y:5,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '报价编号', readOnly : true, name: 'quotationCode',x:340,y:3,width:170},
							//2
							{xtype:'label',text: '出库日期',x:0,y:33,style:this.lableStyle_},
							{xtype:'datefield',value:new Date(),fieldLabel: '出库日期',format:'Y-m-d',  name: 'outStockDate',emptyText:'',x:90,y:31,width:170,allowBlank:false,blankText:'出库日期不能为空!'},
							{xtype:'label',text: '合同编号',x:250,y:33,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '合同编号', readOnly : true, name: 'contractCode',x:340,y:31,width:170},
							//3
							{xtype:'label',text: '出库性质',x:0,y:61,style:this.lableStyle_},
							new Ext.ffc.OutStockTypesComboBox({readOnly : true,disabled:true,x:90,y:59,width:170}),
							{xtype:'label',text: '客户名称',x:250,y:61,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户名称', name: 'customerName',x:340,y:59,width:170},
							//4
							{xtype:'label',text: '出库状态',x:0,y:91,style:this.lableStyle_},
							new Ext.ffc.OutStockStatusComboBox({readOnly : true,disabled:true,x:91,y:88,width:170}),
							{xtype:'label',text: '制   单   人',x:250,y:91,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '制   单   人', name: 'userName',x:340,y:88,width:170},
							//5
							{xtype:'label',text: '备　　注',x:0,y:121,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '备　　注',  name: 'memo',x:91,y:118,width:418},
							//hidden
							{xtype:'hidden',fieldLabel: '报价id', readOnly : true, name: 'quotationId'},
							{xtype:'hidden',fieldLabel: 'id', readOnly : true, name: 'id'},
							{xtype:'hidden',fieldLabel: '客户编号', readOnly : true, name: 'customerCode'}
							]//items
		};

		Ext.ffc.QuotationOutStockInfoForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.QuotationInfoForm.superclass.constructor.call
	}
	
});

Ext.ffc.QuotationOutStockNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.simpleForm = new Ext.ffc.QuotationOutStockInfoForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var outStockInfor = container.ownerCt.ownerCt.outStockInfor;
			
			var params = [];
			for(var i in outStockInfor){
				if(!outStockInfor[i] || !outStockInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,outStockInfor);
			var myNewRecord = new RecordClum(recordParams); 
			this.ownerCt.simpleForm.getForm().loadRecord(myNewRecord);

		}); 
		
		Ext.ffc.QuotationOutStockNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
				layout: 'fit',
                iconCls:'icon-grid',
                title: '报价出库',
                //contentEl: 'south',
                split: true,
                width: 1050,
                height : 200,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});

Ext.ffc.getQuotationOutStockEditCenterGridStore = function(jsonData){
	return new Ext.data.Store({
       //proxy: new Ext.data.HttpProxy({url:pUrl}),//调用的动作 
	   proxy:new Ext.data.MemoryProxy(jsonData),
	   reader: new Ext.data.JsonReader({
       root: 'outStockDetails'//,  //从struts2里面传递过来的参数 
	   //totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
		{name: 'contractProjectSortId',mapping:'contractProjectSortId',type:'string'},
		{name: 'contractProductDetailId',mapping:'contractProductDetailId',type:'string'},
		{name: 'proSortName',mapping:'proSortName',type:'string'},
		{name: 'productCode',mapping:'productCode',type:'string'},
        {name: 'projectCode',mapping:'projectCode',type:'string'},
        {name: 'serialNumber',mapping:'serialNumber',type:'string'},
		{name: 'brandCode',mapping:'brandCode',type:'string'},
		{name: 'productName',mapping:'productName',type:'string'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'reserveAmount',mapping:'reserveAmount',type:'float'},
		{name: 'amount',mapping:'amount',type:'float'},
		{name: 'oldAmount',mapping:'amount',type:'float'},
		{name: 'productUnit',mapping:'productUnit',type:'string'},
		{name: 'price',mapping:'price',type:'float'},
		{name: 'salePrice',mapping:'salePrice',type:'float'},
		{name: 'money',mapping:'money',type:'float'},
		{name: 'productBrand',mapping:'productBrand',type:'string'},
		{name: 'toolsId',mapping:'toolsId',type:'string'},
		{name: 'parentToolsId',mapping:'parentToolsId',type:'string'},
		{name: 'leaf',mapping:'leaf',type:'string'},
		{name: 'reserveInforId',mapping:'reserveInforId',type:'string'},
		{name: 'newRecord',mapping:'newRecord',type:'string'},
		{name: 'needAmount',mapping:'needAmount',type:'float'},
		{name: 'orderAmount',mapping:'orderAmount',type:'float'},
		{name: 'allOutAmount',mapping:'allOutAmount',type:'float'},
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
Ext.ffc.QuotationOutStockEditCenterPanel = Ext.extend(Ext.grid.EditorGridPanel, {
	ffc_key : 'Ext.ffc.QuotationOutStockEditCenterPanel',
	checkboxObjs : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		if(_cfg.readOnly){
			delete _cfg.tbar;
		}
		Ext.apply(this, _cfg);
		this.checkboxObjs = new Ext.grid.CheckboxSelectionModel();

		Ext.ffc.QuotationOutStockEditCenterPanel.superclass.constructor.call(this, {
			region: 'center',
			layout: 'fit',
			sm:this.checkboxObjs,
			clicksToEdit:1,//单击修改
            columns:[new Ext.grid.RowNumberer(),//自动行号
			this.checkboxObjs,
			{
	            header:'报价单编号',
	            width:100,
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
	            width:100,
	            resizable : true,
	            dataIndex:'projectCode'
	        },{
	            header:'序号',
	            width:50,
	            resizable : true,
	            dataIndex:'serialNumber'
	        },{
	            header:'货品编号',
	            width:100,
	            resizable : true,
	            dataIndex:'productCode'
	        },{
	            header:'工具牌号',
	            width:100,
	            resizable : true,
	            dataIndex:'brandCode'
	        },{
	            header:'名称',
	            width:100,
	            dataIndex:'productName'
	        },{
	            header:'报价单数量',
	            width:100,
	            dataIndex:'contractAmount'
	        },{
	            header:'已提数量',
	            width:100,
	            dataIndex:'allOutAmount'
	        },{
	            header:'需提数量',
	            width:100,
	            dataIndex:'needAmount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var _contractAmount = record.get("contractAmount") * 1;
							var _orderAmount = record.get("allOutAmount") * 1;
							_contractAmount = !Ext.isNumber(_contractAmount) ? 0 : _contractAmount;
							_orderAmount = !Ext.isNumber(_orderAmount) ? 0 : _orderAmount;
							return _contractAmount - _orderAmount;
				}
	        },{
	            header:'库存剩余数量',
	            width:100,
	            dataIndex:'reserveAmount'
	        },{
	            header:'出库数量',
	            width:100,
	            dataIndex:'amount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					cellmeta.css = 'ffc-edit-cell';
					return value;
				},
				editor: new Ext.form.NumberField({
					gridObj : this,
					validator : function(_value) {
						var newValue = _value * 1;
						if(_value < 0) {
							return '出库数量必须大于 0 ';
						}
						var ge = this.gridEditor;
						var store = this.gridObj.getStore();
						var record = store.getAt(ge.row);
						var data = record.data;
						var oldAmount = data.oldAmount * 1;
						var reserveAmount = data.reserveAmount * 1;

						if(newValue > oldAmount){//这小子把出库数量改大了
							if(reserveAmount < newValue - oldAmount){
								return "出库数量不能超过库存数量!";
								
							}
						}
						var _contractAmount = data.contractAmount * 1;
						var _orderAmount = data.allOutAmount * 1;
						_contractAmount = !Ext.isNumber(_contractAmount) ? 0 : _contractAmount;
						_orderAmount = !Ext.isNumber(_orderAmount) ? 0 : _orderAmount;
						var _needAmount = _contractAmount - _orderAmount;
						if(newValue > _needAmount){
							return "出库数量不能大于需提数量!";
						}
						return true;
					},
					listeners : {
						specialkey : Ext.ftl.gridEditorkeyMove,
						focus : function(f){
							f.selectText(0,f.getValue().length);
						}
					}
				})
	        },{
	            header:'计量单位',
	            width:60,
	            dataIndex:'productUnit'
	        },{
	            header:'销售价格',
	            width:60,
	            name : 'salePrice',
	            dataIndex:'salePrice'
	        },{
	            header:'订单价格',
	            width:60,
	            name : 'price',
	            dataIndex:'price'
	        },{
	            header:'金额',
	            width:80,
	            dataIndex:'money'
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand'
	        },{
	            header:'工具id',
	            width:100,
				hidden:true,
	            dataIndex:'toolsId'
	        },{
	            header:'工具父Id',
	            width:100,
				hidden:true,
	            dataIndex:'parentToolsId'
	        },{
	            header:'是否叶子',
	            width:100,
				hidden:true,
	            dataIndex:'leaf'
	        },{
	            header:'合同明细id',
	            width:100,
				hidden:true,
	            dataIndex:'contractProductDetailId'
	        },{
	            header:'起始数量',
	            width:100,
				hidden:true,
	            dataIndex:'oldAmount'
	        },{
	            header:'库存id',
	            width:100,
				hidden:true,
	            dataIndex:'reserveInforId'
	        },{
	            header:'新纪录',
	            width:100,
				hidden:true,
	            dataIndex:'newRecord'
	        },	
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
					})}]
		})
	}
})


Ext.ffc.QuotationOutStockEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	outStockInfor : null,
	readOnly : false,
	QuotationOutStockEditCenterGridStore : null,
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
		var QuotationProductSorts = conctractInfor.QuotationProductSorts;
		var nArr = [];
		for(var i = 0;i < QuotationProductSorts.length;i++){
			if(QuotationProductSorts[i].id !== tabp.id){
				nArr.push(QuotationProductSorts[i]);
			}
		}
		conctractInfor.QuotationProductSorts = nArr;
	},
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.topPanel = new Ext.ffc.QuotationOutStockNorthPanel({readOnly:_cfg.readOnly});
		this.QuotationOutStockEditCenterGridStore = Ext.ffc.getQuotationOutStockEditCenterGridStore(this.outStockInfor);
		this.centerPanel = new Ext.ffc.QuotationOutStockEditCenterPanel({
			readOnly:_cfg.readOnly,
			ds : this.QuotationOutStockEditCenterGridStore,
			store: this.QuotationOutStockEditCenterGridStore,
			tbar:[{xtype:'button',text:'参照报价单货品',iconCls : 'icon-add',
					handler : function(obj) {
						var grid = obj.ownerCt.ownerCt;
						var editWin = grid.ownerCt;
						var outStockInfor = editWin.outStockInfor;
						try{

						Ext.ffc.select_Quotation_products(outStockInfor.quotationId,
								function(dataArray,win){
										
											if(dataArray.length == 0) return;

											var TopicRecord = Ext.data.Record.create([
												{name: 'toolsId',mapping:'toolsId',type:'string'},
												{name: 'brandCode',mapping:'brandCode',type:'string'},
												{name: 'productCode',mapping:'productCode',type:'string'},
												{name: 'parentToolsId',mapping:'parentToolsId',type:'string'},
												{name: 'leaf',mapping:'leaf',type:'string'},
												{name: 'productName',mapping:'productName',type:'string'},
												{name: 'productUnit',mapping:'productUnit',type:'string'},
												//{name: 'reserveAmount',mapping:'amount',type:'float'},
												{name: 'price',mapping:'price',type:'string'},
												//{name: 'productSort',mapping:'productSort',type:'string'},
												//{name: 'currencyName',mapping:'currencyName',type:'string'},
												{name: 'productBrand',mapping:'productBrand',type:'string'},
												//{name: 'productSource',mapping:'productSource',type:'string'},
												{name: 'reserveAmount',mapping:'reserveAmount',type:'string'},
												{name: 'newRecord',mapping:'newRecord',type:'string'},
												//{name: 'contractAmount',mapping:'QuotationAmount',type:'float'},
												{name: 'allOutAmount',mapping:'allOutAmount',type:'float'},
												{name: 'contractProjectSortId',mapping:'quotationProjectSortId',type:'string'},
												{name: 'contractProductDetailId',mapping:'id',type:'string'}
											]);
											try{
												var store = grid.getStore();
												var reArray = [];
												var cuDate = new Date();
												for(var i = 0;i < dataArray.length;i++){
													var rowData = {};
													Ext.apply(rowData,dataArray[i]);
													rowData['contractProductDetailId'] = rowData['id'];
													rowData['id'] = Math.round(Math.random()*2000000000000000);
													rowData['contractAmount'] = rowData['amount'];
													rowData['amount'] = 0;
													rowData['oldAmount'] = 0;
													rowData['newRecord'] = 1;
													rowData['allOutAmount'] = rowData['arrivalAmount'];
													var record = new TopicRecord(rowData);
													reArray.push(record);
												}
												store.add(reArray);
											}catch(e){
												alert(e);
											}
										},function(id){
											var gridStore = grid.getStore();
											var rowCount = gridStore.getCount();
											for(var i = 0; i < rowCount;i++){
												var da = gridStore.getAt(i).data;
												if(da.contractProductDetailId == id){
													return true;
												}
											}
											return false;
										}
									);
						}catch(e){alert(e);}
						
					}//handler
				},{xtype:'button',text:'删除货品',iconCls : 'icon-delete',
					handler : function(obj) {
						var grid = obj.ownerCt.ownerCt;
							var arr = grid.checkboxObjs.getSelections();
							if(arr.length == 0){
								Ext.Msg.alert("消息", "请选择要删除的产品!");
								return ;
							}
							Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中产品!', function(btn){
									if(btn != 'yes'){return ;}
									var store = grid.getStore();
									for(var i = 0;i < arr.length;i++){
										store.remove(arr[i]);
									}
									
						   });
						
					}//handler
				},{xtype:'button',text:'自动提库',iconCls : 'icon-out-stock',
					handler : function(obj) {
						var grid = obj.ownerCt.ownerCt;
						var gridStore = grid.getStore();
						var rowCount = gridStore.getCount();
						
						for(var i = 0; i < rowCount;i++){
							var theRecord = gridStore.getAt(i);
							var rowData = theRecord.data;
							var _contractAmount = rowData.contractAmount * 1;
							var _orderAmount = rowData.allOutAmount * 1;
							var _reserveAmount = rowData.reserveAmount * 1;

							_contractAmount = !Ext.isNumber(_contractAmount) ? 0 : _contractAmount;
							_orderAmount = !Ext.isNumber(_orderAmount) ? 0 : _orderAmount;
							_reserveAmount = !Ext.isNumber(_reserveAmount) ? 0 : _reserveAmount;
							var _needAmount = _contractAmount - _orderAmount;

							if(_reserveAmount >= _needAmount){
								theRecord.set("amount",_needAmount);
							}else{
							    theRecord.set("amount",_reserveAmount);
							}
						}
					}//handler
				}
			]
		});
		this.QuotationOutStockEditCenterGridStore.load();
		
		Ext.ffc.QuotationOutStockEditWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : 1050,
			height : 600,
			//title :  '出库单编制',
			layout :  'border',
			maximizable :true,
			items : [this.topPanel,this.centerPanel],
			buttons: [{
						text : "保  存",
						hidden : this.readOnly,
						handler : function() {
							this.buttons[0].disabled = true;
							var self = this;
							var form = self.topPanel.simpleForm;//obj.ownerCt.ownerCt;
							var currWin = form.ownerCt.ownerCt;
							var formValues = form.getForm().getValues();
							var gridStore = currWin.QuotationOutStockEditCenterGridStore;
							var rowCount = gridStore.getCount();
							
							var resultObjs = [];
							for(var i = 0; i < rowCount;i++){
								var da = gridStore.getAt(i).data;
								if(da.amount * 1 <= 0){
									Ext.Msg.alert("消息", "出库数量必须大于零!");	
									return;
								}
								resultObjs.push(da);
							}
							if(resultObjs.length == 0){
								Ext.Msg.alert("消息", "本出库单没有出库产品，不允许保存!");	
								return;
							}
							formValues["outStockDetails"] = resultObjs;
							formValues["outStockType"] = currWin.outStockInfor.outStockType;
							var serUrl = '';
							if(formValues["id"] == ''){//add
								serUrl = PATH + '/outStock/outStockEditAction.do?ffc=addOutStock';
							}else{//update
								serUrl = PATH + '/outStock/outStockEditAction.do?ffc=updateOutStock';
							}
							Ext.Ajax.request({
									method: "post",
									params: { OutStockInfor : Ext.encode(formValues)},
									url: serUrl,
									success: function(response){
										
										if(response.responseText * 1 == 1){
											Ext.Msg.alert("消息", "保存成功!");	
											currWin.close();
										}else{
											//response.responseText,可返回以得到当前超量数据
											eval("var tt = " + response.responseText);
											for(var i = 0;i < resultObjs.length;i++){
												if(tt.id == resultObjs[i].id){
													var theRecord = gridStore.getAt(i);
														theRecord.set("reserveAmount",tt.reserveAmount);
														theRecord.commit();
														currWin.centerPanel.getView().getRow(i).style.backgroundColor='#FFCCFF';
														currWin.centerPanel.getView().getRow(i).style.color = '#FF0000';
														break;
												}
											}
											Ext.Msg.alert("消息", "有超量出库!");	
											self.buttons[0].disabled = false;
										}
									}
							});
						},scope : this
					},{
						text : "关 闭",
						handler : function() {
							this.close();
						},scope : this
					}
					]
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
   this.show = function(){
       if(this.method != null){
	       this.method();
			var conId = this.id;
			
			Ext.Ajax.request({
				method: "post",
				params: { id : conId},
				url: PATH + '/delivery/deliveryViewAction.do?ffc=deliveryViewById',
				success: function(response){
						eval("var temp = " + response.responseText);
						var conEditWin = new Ext.ffc.QuotationOutStockEditWindow({
							deliveryInfor : temp,
							readOnly:true
						});
						conEditWin.show();	
				}
			});
	   }
   }
}