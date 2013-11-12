Ext.ffc.reservePlanForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var f_config = {
						width : 1045,//this['width'] == null ? 1000 : this['width'],
                        labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:80,monitorValid:false,
                        items:[
                            {layout:'column',border:false,labelSeparator:':',
                            defaults:{layout: 'form',border:false,columnWidth:.3},
							items:[
                            {items: [{xtype:'textfield',fieldLabel: '计划编号',readOnly : true,name: 'planCode',anchor:'100%'}]},	
							{items: [{xtype:'textfield',fieldLabel: '加工订单编号',readOnly : true,name: 'orderCode',anchor:'100%'}]},
							{items: [{xtype:'textfield',fieldLabel: '合同编号',readOnly : true,name: 'contractCode',anchor:'100%'}]},
							{items: [{xtype:'textfield',fieldLabel: '编制时间',readOnly : true,name: 'editDateString',anchor:'100%'}]},
							{items: [{xtype:'textfield',fieldLabel: '编制人',readOnly : true,name: 'userName',anchor:'100%'}]},
							{items: [new Ext.ffc.UrgentLevelCombox({fieldLabel: '紧急程度',readOnly : true,anchor:'100%'})]},
							{items: [{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'100%'},
							{xtype:'hidden',fieldLabel: '',name: 'orderId'},
							{xtype:'hidden',fieldLabel: '',name: 'id'},
							{xtype:'hidden',fieldLabel: '',name: 'planType'}]}
							]}
                        ]//items
		};

		Ext.ffc.reservePlanForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.ContractInfoForm.superclass.constructor.call
	}
	
});

Ext.ffc.ReservePlanNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.simpleForm = new Ext.ffc.reservePlanForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var ReservePlanInfor = container.ownerCt.ownerCt.ReservePlanInfor;
			
			var params = [];
			for(var i in ReservePlanInfor){
				if(!ReservePlanInfor[i] || !ReservePlanInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,ReservePlanInfor);
			var myNewRecord = new RecordClum(recordParams); 
			this.ownerCt.simpleForm.getForm().loadRecord(myNewRecord);

		}); 
		
		Ext.ffc.ReservePlanNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
                iconCls:'icon-grid',
                layout: 'fit',
                split: true,
                width: 1050,
                height : 100,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});


/**
 * 容纳产品树容器
 * @class centerPanel
 * @extends Ext.Panel
 */
Ext.ffc.ReservePlanEditCenterPanel = Ext.extend(Ext.grid.EditorGridPanel, {
	ffc_key : 'Ext.ffc.ReservePlanEditCenterPanel',
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.defStore = new Ext.data.Store({
			   proxy:new Ext.data.MemoryProxy(_cfg.jsonString),
			   reader: new Ext.data.JsonReader({}, 
			 [      {name: 'id',mapping:'id',type:'string'},
			        {name: 'brandCode',mapping:'brandCode',type:'string'},
				    {name: 'productCode',mapping:'productCode',type:'string'},
				    {name: 'productName',mapping:'productName',type:'string'},
				    {name: 'productUnit',mapping:'productUnit',type:'string'},
				    {name: 'contractAmount',mapping:'contractAmount',type:'float'},
				    {name: 'remainAmount',mapping:'remainAmount',type:'float'},
				    {name: 'productBrand',mapping:'productBrand',type:'string'},
				    {name: 'price',mapping:'price',type:'float'},
				    {name: 'parentToolsId',mapping:'parentToolsId',type:'string'},
				    {name: 'toolsId',mapping:'toolsId',type:'string'},
				    {name: 'leaf',mapping:'leaf',type:'float'},
				    {name: 'contractProductDetailId',mapping:'contractProductDetailId',type:'string'},
				    {name: 'contractProjectSortId',mapping:'contractProjectSortId',type:'string'},
				   //{name: 'proSortName',mapping:'proSortName',type:'string'},
				   {name: 'projectCode',mapping:'projectCode',type:'string'},
				   {name: 'serialNumber',mapping:'serialNumber',type:'string'},
				   {name: 'orderAmount',mapping:'orderAmount',type:'float'},
				   {name: 'productMoney',mapping:'productMoney',type:'float'},
				   {name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
				   {name: 'planAmount',mapping:'planAmount',type:'float'},
				   {name: 'memo',mapping:'memo',type:'string'},
				   {name: 'memo2',mapping:'memo2',type:'string'},
				   {name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
				   {name: 'outAmount',mapping:'outAmount',type:'float'},
				   {name: 'orderAmount',mapping:'orderAmount',type:'float'},
				   {name: 'arrivalAmount',mapping:'arrivalAmount',type:'float'},
				   {name: 'reserveAmount',mapping:'reserveAmount',type:'float'}
			       ]
			)
			});
	    this.defStore.load();
		this.defStore.sort('parentToolsId','ASC');
		this.gridCheckSele = new Ext.grid.CheckboxSelectionModel();
		Ext.ffc.ReservePlanEditCenterPanel.superclass.constructor.call(this, {
			region: 'center',
			layout: 'fit',
			clicksToEdit: 1,
			width : 550,
			split: true,
		    title: '下级组件',
			sm:this.gridCheckSele,
			store:this.defStore,
			ds : this.defStore,
			columns:[
					 new Ext.grid.RowNumberer(),
					this.gridCheckSele,
					{header:'计划明细id',width:100,dataIndex:'id',hidden:true},
					{header:'名称',width:85,dataIndex:'productName'},
					{header:'工具牌号',width:200,dataIndex:'brandCode'},
					{header:'采购数量',width:60,dataIndex:'orderAmount'},
				    {header:'到货数量',width:60,dataIndex:'arrivalAmount'},
					{header:'计划数量',width:60,dataIndex:'planAmount',editor: new Ext.form.NumberField({
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
						renderer:function(value,metadata,record){
							if(value == null || value == ''){
								record.set('planAmount',0);
								return "<span style='color:#0033FF'>0</span>";
							}
							else{
								record.set('planAmount',value);
								return "<span style='color:#0033FF'>" + value + "</span>";
							};
						}
					},
					{header:'提取数量',width:60,dataIndex:'outAmount',editor: new Ext.form.NumberField({
						   allowBlank: false,
						   allowNegative: false,
						   style: 'text-align:left',
						   gridObj:this,
						   listeners : {
								focus : function(f){
									f.selectText(0,f.getValue().length);
								}
								,'specialkey' : Ext.ftl.gridEditorkeyMove
							},
							validator : function(_value) {
								var newValue = _value * 1;
								if(newValue < 0) {
									return '提取数量必须大于 0 ';
								}
							    var ge = this.gridEditor;
								var store = this.gridObj.getStore();
								var record = store.getAt(ge.row);
								var data = record.data;
								//var oldAmount = data.oldAmount * 1;
								var brandCode = data.brandCode;
								if(brandCode == null || brandCode.length == 0){
								    return false;
								}
								var reserveAmount = data.reserveAmount * 1;
								var planAmount = data.planAmount * 1;
								if(newValue > planAmount){
									return "【提取数量】不能大于【计划数量】！";
								}
								if(newValue > reserveAmount){
									return "【提取数量】不能大于【库存数量】！";
								}
								return true;
							}
						})/*,
						renderer:function(value,metadata,record){
							if(value == null || value == ''){
								record.set('outAmount',0);
								return '<span style="color:#0033FF">0</span>';
							}
							else{
								record.set('outAmount',value);
								return '<span style="color:#0033FF">' + value + "</span>";
							}
						}*/
					},
					{header:'库存数量',width:60,dataIndex:'reserveAmount'/*,
						renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							return "<span onclick=\"alert(1);\">" + value + "</span>";
						}*/
					},
					{header:'交货日期',width:100,dataIndex:'deliveryDate',
					    editor: {xtype:'datefield',format:'Y-m-d',value:new Date(),altFormats:'Y-m-d'},
						renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							if(!value) return '';
							if(typeof(value) == 'string') return value;
							else{
								var str = value.format('Y-m-d');
								record.set('deliveryDate',str);
								return "<span style='color:#0033FF'>" + str + "</span>";
							}
						}
					 },
					{header:'计量单位',width:70,dataIndex:'productUnit'},
					//{header:'货品金额',width:100,dataIndex:'productMoney'},
					
					{header:'品牌',width:70,dataIndex:'productBrand'},
					{header:'备注1',width:100,dataIndex:'memo',editor:new Ext.form.TextField({
						listeners : {
							'specialkey' : function(_field, e){
								_field.gridObj = this;
								Ext.ftl.gridEditorkeyMove(_field, e);
							},scope : this
					   }
					})},
					{header:'备注2',width:100,dataIndex:'memo2',editor:new Ext.form.TextField({
						listeners : {
							'specialkey' : function(_field, e){
								_field.gridObj = this;
								Ext.ftl.gridEditorkeyMove(_field, e);
							},scope : this
					   }
					})},
					//{header:'合同分名称',width:100,dataIndex:'proSortName'},
					{header:'项目号',width:50,dataIndex:'projectCode',hidden:true},
					{header:'序号',width:50,dataIndex:'serialNumber',hidden:true},
					{header:'货品工具主键',width:0,hidden : true,dataIndex:'toolsId'},
					{header:'货品工具父节点id',width:0,hidden : true,dataIndex:'parentToolsId'},
					{header:'货品工具叶子节点',width:0,hidden : true,dataIndex:'leaf'},
					{header:'合同货主键',width:100,hidden : true,dataIndex:'contractProductDetailId'},
					{header:'货品编号',width:100,dataIndex:'productCode'},
					{header:'合同分_主键',width:70,dataIndex:'contractProjectSortId',hidden : true}
				],
				listeners : {
					'afteredit' : function(e){
						var grid = e.grid;
						var record = e.record;
						var planDetail = grid.ownerCt.ReservePlanInfor.reservePlanDetail;
						if(planDetail == null){
							planDetail = [];
						}
						if(record.get('id') == null || record.get('id') == ''){
							var newR = {};
							Ext.apply(newR,record.data);
							planDetail.push(newR);
						}else{
							for(var i = 0; i < planDetail.length ;i++ ){
								if(planDetail[i].id == record.get('id')){
									Ext.apply(planDetail[i],record.data);
								}
							}
						}
					},
					'bodyscroll':function(scrollLeft,scrollTop){
						var win = this.ownerCt;
						var leftpanel = win.leftPanel;
						var scroller = leftpanel.getView().getEditorParent();
						scroller.scrollTop = scrollTop;
					},scope : this
				},
			tbar : [{
          		text : '',
          		iconCls:'icon-delete',
				hidden : !this.readOnly,
          		handler : function() {
				}
			},{
          		text : '添加组件',
          		iconCls:'icon-add',
          		hidden : this.readOnly,
          		handler : function(e) {
					var win = this.ownerCt;
					var plandetail = win.ReservePlanInfor.reservePlanDetail;
					var orderProducts = win.leftPanel.getSelectionModel().getSelections();
					var orderCode = win.ReservePlanInfor.orderCode;
					if(orderProducts.length == 0 && typeof(orderCode) != 'undefined'){
						Ext.MessageBox.alert('系统提示', '请选择要添加组件的非标品！');
						return ;
					}
					var fbproduct = null;
					if(orderProducts.length > 0){
					    fbproduct = orderProducts[0];
					}
					plandetail = typeof(plandetail) == 'undefined' ? [] : plandetail;
					var fbproduct = orderProducts[0];
          			var productListWindow = new Quoproduct.ProductWindow();
					productListWindow.on({
						'onclick' : function(node) {
							//Ext.ffc.util.debug(node);
							Ext.Ajax.request({
									method: "post",
									params: { m:'getResInforByProdCode',productCode:node.productCode},
									url: PATH + "/baseInfo/matReserveManageAction.do",
									success: function(response){
										eval("var temp=" + response.responseText);
										var amount = 0;
										if(temp != null){
										    amount = temp.amount;
										}
										var record = {};
										Ext.apply(record, node);
										record['planAmount'] = 0;
										record['orderAmount']=0;
										record['serialNumber']=0;
										record['reserveAmount']=0;
										record['remainAmount']=0;
										record['price']=0;
										record['productMoney']=0;
										record['outAmount']=0;
										record['orderAmount']=0;
										record['arrivalAmount']=0;
										record['reserveAmount'] = amount;
										record['parentToolsId']= fbproduct == null ? null : fbproduct.id;
										record['proSortName']= fbproduct == null ? null : fbproduct.get('proSortName');
										record['id']=Math.random()+"";
										plandetail.push(record);
										win.leftPanel.refreshData();
									}
								});
						},scope : this
					})
					productListWindow.show();
          		},scope : this
          	},'-',{
          		text : '删除产品',
          		iconCls:'icon-delete',
          		hidden : this.readOnly,
          		handler : function() {
							var _records = this.getSelectionModel().getSelections();
							if(_records.length == 0){
								Ext.Msg.show({
									title : '信息提示',
									msg : '请选择产品再进行操作！',
									width : 260,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.INFO
								});
							    return ;
							}
							Ext.MessageBox.show({
								title:'系统提示',
								msg: '确定要删除当前所选产品？',
								buttons: Ext.MessageBox.OKCANCEL,
								fn:function(_btn) {
									if(_btn == 'ok') {
									   var win = this.ownerCt;
									   var plans = win.ReservePlanInfor.reservePlanDetail;
									   var _records = this.getSelectionModel().getSelections();
										for(var i = 0;i < _records.length ;i++ ){
											if(_records[i].id.substring(0,2) == '0.'){//新增加的
												for(var j = plans.length - 1;j >= 0 ;j-- ){
													if(plans[j].id == _records[i].id){
													   plans.remove(plans[j]);
													}
												}
											}else if(_records[i].get('status') == 2){
												Ext.Msg.show({
													title : '信息提示',
													msg : '您选择的第['+ (i+1) +']条数据为已确认数据，不能删除！',
													width : 260,
													buttons : Ext.Msg.OK,
													icon : Ext.MessageBox.INFO
												});
												continue;
											}else{
											win.leftPanel.addDeleteId(_records[i].id);
												Ext.Ajax.request({
													method: "post",
													params: { 'ids' : [_records[i].id]},
													url: PATH + '/reservePlan/ReservePlanEditAction.do?ffc=deleteReservePlan',
													success: function(response){
														eval("var reIds=" + response.responseText);
														for(var j = plans.length - 1;j >= 0 ;j-- ){
															for (var t = 0; t < reIds.length ;t++ ){
																if(plans[j].id == reIds[i]){
																	plans.remove(plans[j]);
																}
															}
														}
													}
												});
											}
											
										}
										win.leftPanel.refreshData();
									}
								},scope : this
							});
          		},scope : this
          	},'-',{
          		text : '统一设置交货期',
          		iconCls:'icon-date',
          		hidden : this.readOnly,
          		handler : function(btn,e) {
					var win = this.ownerCt;
					var plandetail = win.ReservePlanInfor.reservePlanDetail;
					var deliveryDate = null;
					var dataf = new Ext.form.DateField({format:'Y-m-d'});
					var menu = new Ext.menu.DateMenu();
					menu["on"]("select", function(a,b){
						var bb = dataf.formatDate(dataf.parseDate(b));
						var arr = plandetail;
						for(var i =0 ;i < arr.length ;i++ ){
							arr[i].deliveryDate = bb;
						}
						win.leftPanel.refreshData();
						menu.hide();
					});
					menu.show(btn.getEl());	
          		},scope : this
          	}]
		})
	}
})

Ext.ffc.ReservePlanEditLeftPanel = Ext.extend(Ext.grid.GridPanel, {
	ffc_key : 'Ext.ffc.ReservePlanEditLeftPanel',
	reservePlanTree:null,
	reservePlanEditTree:null,
	proDetail:null,
	deleteIds:{},
	initPlanRows:function(){
		var win = this.ownerCt;
		var centerStore = win.centerPanel.getStore();
		var detail = this.ReservePlanInfor.orderDetailList;
		var plans = this.ReservePlanInfor.reservePlanDetail;
		var leftStore = this.getStore();
		var rowCount = detail.length;
		var leftIndex = 1;
		var centerIndex = 0;
		if(rowCount == 0){
			centerStore.loadData(plans,true);
		}

		for(var i = 0;i < rowCount;i++ ){
			var orderId = detail[i].id;
			var arr = [];
			for(var j = 0;j < plans.length ;j++ ){	
				if(orderId == plans[j].parentToolsId && !this.deleteIds[plans[j].id]){
					arr.push(plans[j]);
				}
			}
			if(arr.length == 0){
				var centerry = centerStore.recordType;
					var p = new centerry({
						'brandCode':'',
						'productCode':'',
						'productName':'',
						'productUnit':'',
						'contractAmount':0,
						'remainAmount':0,
						'productBrand':'',
						'price':'',
						'parentToolsId':'',
						'toolsId':'',
						'leaf':'',
						'contractProductDetailId':'',
						'contractProjectSortId':'',
						'proSortName':'',
						'projectCode':'',
						'serialNumber':0,
						'orderAmount':0,
						'productMoney':'',
						'deliveryDate':'',
						'planAmount':0,
						'memo':''
					});
				var t = [];
				t.push(p);
				if(centerIndex == 0){
				    centerStore.insert(0,p);
				}else{
				    centerStore.loadData(t,true);
				}
				centerIndex++;
			}else{
			    centerStore.loadData(arr,true);
				centerIndex = centerIndex + arr.length;
			}
			var Leftry = leftStore.recordType;
			var leftArr = [];
			for(var r =0;r < arr.length-1;r++ ){
				var p = new Leftry({
					enable: '1',
					planAmount:0
				});
				leftArr.push(p);
			}
			if(leftArr.length > 0){
			    leftStore.insert(leftIndex,leftArr);
			}
			if(arr.length > 0){
			    leftIndex = leftIndex + arr.length;
			}else{
			    leftIndex++;
			}
		}
	},
	addDeleteId:function(id){
	    this.deleteIds[id] = 1;
	},scope : this,
	refreshData:function(){
		var win = this.ownerCt;
		var leftStore = this.getStore();
		var centerStore = win.centerPanel.getStore();
			leftStore.removeAll();
			centerStore.removeAll();
		
		leftStore.loadData(win.ReservePlanInfor.orderDetailList);
	},scope : this,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.defStore = new Ext.data.Store({
			   proxy:new Ext.data.MemoryProxy(_cfg.jsonString),
			   reader: new Ext.data.JsonReader({}, 
			   [    {name: 'id',mapping:'id',type:'string'},
			        {name: 'brandCode',mapping:'brandCode',type:'string'},
			        {name: 'productCode',mapping:'productCode',type:'string'},
				    {name: 'productName',mapping:'productName',type:'string'},
				    {name: 'productUnit',mapping:'productUnit',type:'string'},
				    {name: 'contractAmount',mapping:'contractAmount',type:'float'},
				    {name: 'remainAmount',mapping:'remainAmount',type:'float'},
				    {name: 'productBrand',mapping:'productBrand',type:'string'},
				    {name: 'price',mapping:'price',type:'float'},
				    {name: 'parentToolsId',mapping:'parentToolsId',type:'string'},
				    {name: 'toolsId',mapping:'toolsId',type:'string'},
				    {name: 'leaf',mapping:'leaf',type:'float'},
				    {name: 'contractProductDetailId',mapping:'contractProductDetailId',type:'string'},
				    {name: 'contractProjectSortId',mapping:'contractProjectSortId',type:'string'},
				   {name: 'proSortName',mapping:'proSortName',type:'string'},
				   {name: 'projectCode',mapping:'projectCode',type:'string'},
				   {name: 'serialNumber',mapping:'serialNumber',type:'string'},
				   {name: 'orderAmount',mapping:'orderAmount',type:'float'},
				   {name: 'productMoney',mapping:'productMoney',type:'float'},
				   {name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
				   {name: 'memo',mapping:'memo',type:'string'},
				   {name: 'memo2',mapping:'memo2',type:'string'}]
			),
			listeners : {
				'load':function(){
					this.initPlanRows();
					this.getView().scroller.setStyle('overflow-y', 'hidden');
				},scope : this
			}
			});
		Ext.ffc.ReservePlanEditLeftPanel.superclass.constructor.call(this, {
			region: 'west',
			layout: 'fit',
			//width: 350,
			split: this.splitValue,
			title: '非标产品',
			store:this.defStore,
			ds : this.defStore,
			columns:[
					 new Ext.grid.RowNumberer(),
					{header:'合同订单ID',width:100,dataIndex:'id',hidden:true},
					{
						header:'',
						width:20,
						resizable : true,
						dataIndex:'leaf',
						renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
							return '-';
						}
				    },
					{header:'项目号',width:50,dataIndex:'projectCode'},
					{header:'序号',width:50,dataIndex:'serialNumber'},
					{header:'名称',width:125,dataIndex:'productName'},
					{header:'工具牌号',width:200,dataIndex:'brandCode'},
					{header:'合同数量',width:100,dataIndex:'contractAmount'},
					{header:'合同剩余数量',width:120,dataIndex:'remainAmount'},
					{header:'采购数量',width:70,dataIndex:'orderAmount'},
					{header:'货品金额',width:100,dataIndex:'productMoney'},
					{header:'交货日期',width:100,dataIndex:'deliveryDate'},
					{header:'品牌',width:100,dataIndex:'productBrand'},
					{header:'备注1',width:100,dataIndex:'memo'},
					{header:'备注2',width:100,dataIndex:'memo2'},
					{header:'合同分名称',width:100,dataIndex:'proSortName'},
					{header:'货品编号',width:100,dataIndex:'productCode'},
					{header:'货品工具主键',width:0,hidden : true,dataIndex:'toolsId'},
					{header:'货品工具父节点id',width:0,hidden : true,dataIndex:'parentToolsId'},
					{header:'合同货主键',width:100,hidden : true,dataIndex:'contractProductDetailId'},
					{header:'合同分_主键',width:70,dataIndex:'contractProjectSortId',hidden : true}
				],
			listeners : {
				rowclick : function(grid, rowIndex, e){
					var s = grid.getStore();
					var r = s.getAt(rowIndex);
					var centerPanel = grid.ownerCt.centerPanel;
					var centerStore = grid.ownerCt.centerPanel.store;
					var rowCount = centerStore.totalLength;
					var view = centerPanel.getView();
					//centerStore.sort('parentToolsId','ASC');
					for(var i = 0; i < rowCount ;i++ ){
						var rec = centerStore.getAt(i);
						var row = view.getRow(i);//根据行号获取行对象
						var element = Ext.get(row);
						element.removeClass('x-grid3-row-selected');
					}
					for(var i = 0; i < rowCount ;i++ ){
						var rec = centerStore.getAt(i);
						if(rec.get('parentToolsId') == r.id){
							var row = view.getRow(i);//根据行号获取行对象
							var element = Ext.get(row);
							element.addClass('x-grid3-row-selected');
						}
					}
				}
			},
			tbar : [{
          		text : '全部展开',
          		iconCls:'icon-delete',
          		handler : function() {
					//this.getStore().reload();
					var win = this.ownerCt;
					//this.getStore().loadData(win.ReservePlanInfor.orderDetailList);
				},scope : this
			},'-',{
          		text : '全部收起',
          		iconCls:'icon-delete',
          		handler : function() {
					
				},scope : this
			}]
		})
	}
})

Ext.ffc.ReservePlanEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	ReservePlanInfor : null,
	readOnly : false,
	ReservePlanEditCenterGridStore : null,
	defultSelectPlanId:null,
	isAddWin:false,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.splitValue = true;
		var leftWidth = 350;
		if(_cfg.ReservePlanInfor.orderDetailList.length == 0){
		    leftWidth = 1;
			this.splitValue = false;
		}
		this.topPanel = new Ext.ffc.ReservePlanNorthPanel({readOnly:_cfg.readOnly});
		this.centerPanel = new Ext.ffc.ReservePlanEditCenterPanel({ readOnly:this.readOnly,
			jsonString:[],
			ReservePlanInfor:_cfg.ReservePlanInfor});
		this.leftPanel = new Ext.ffc.ReservePlanEditLeftPanel({
			jsonString : [],
		 	ReservePlanInfor :_cfg.ReservePlanInfor,
		    splitValue : this.splitValue,
			width : leftWidth
		});
		
		Ext.ffc.ReservePlanEditWindow.superclass.constructor.call(this, {
			modal : true,
			width : Ext.getBody().getWidth(),
			height : 600,
			title :  '储备计划编制',
			layout :  'border',
			maximizable :true,
			split: this.splitValue,
			items : [this.topPanel,this.centerPanel,this.leftPanel],
			buttons: [{
						text : "保 存",
						hidden : this.readOnly,
						handler : function() {
								var form = this.topPanel.simpleForm;//obj.ownerCt.ownerCt;
								var currWin = form.ownerCt.ownerCt;
								var planDetail = currWin.ReservePlanInfor.reservePlanDetail;
								
								var commitPlanDetail = [];
								for(var i = 0;i< planDetail.length;i++ )
								{
									var p = planDetail[i].planAmount;
									p = p == '' ? 0 : p * 1;
									if(planDetail[i].toolsId != '' && p > 0){
										commitPlanDetail.push(planDetail[i]);
									}
								}
								if(commitPlanDetail.length == 0) {
									Ext.Msg.alert("消息", "无计划可以保存!");	
									return;
								}

								var formValues = form.getForm().getValues();
								formValues["reservePlanDetail"] = commitPlanDetail;
								var serUrl = '';
								if(currWin.isAddWin){//add
									serUrl = PATH + "/reservePlan/ReservePlanEditAction.do?ffc=addReservePlan";
								}else{//update
									
									serUrl = PATH + "/reservePlan/ReservePlanEditAction.do?ffc=updateReservePlan";
								}
								Ext.Ajax.request({
										method: "post",
										params: { reservePlanInfor : Ext.encode(formValues)},
										url: serUrl,
										success: function(response){
											if(response.responseText * 1 == 1){
											    Ext.Msg.alert("消息", "保存成功!");	    
											    currWin.close();
											}else{
											    Ext.Msg.alert("消息",response.responseText);
											}
										}
								});
						},scope : this
					  },{
						text : "关 闭",
						handler : function() {
							this.close();
						},scope : this
					  }],
			listeners : {
					'show' : function(){
						var left = this.leftPanel;
						var leftstore = left.getStore();
						leftstore.loadData(this.ReservePlanInfor.orderDetailList);
					},scope : this	
			}
		});
	}
});  


