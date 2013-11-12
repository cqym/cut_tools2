Ext.ffc.select_process_plans = function (callBackMethod,initParams){
var	searchForm = new Ext.FormPanel({
		width : 810,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:80,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.4},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var obj2 = searchForm.getForm().getValues();
							if(initParams){
								Ext.applyIf(obj2,initParams);
							}
 						    
							for(var i in obj2){
								contractListStore.setBaseParam(i, obj2[i]);
							}
							contractListStore.load();
		           		}
	           		},'-',
					{  
						text:'重置'  
						,iconCls:'icon-reset',
						listeners : {
							'click' : function(){
								searchForm.getForm().reset();
							},scope : this
						}					
					}],
           items:[
			  {items: [{xtype:'textfield',fieldLabel: '计划编号',name: 'planCode',anchor:'100%'},
					   {xtype:'hidden', readOnly : true, name: 'status',value:"2"}]},
              {columnWidth:0.6,items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'65%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange', endDateField: 'endDate', fieldLabel: '日期起',name: 'startTime', format:'Y-m-d', emptyText:'',anchor:'100%'}]},
              {items: [{xtype : 'datefield', vtype: 'daterange',startDateField: 'beginDate',fieldLabel: '至',name: 'endTime', format:'Y-m-d', emptyText:'',labelSeparator:'',anchor:'100%'}]}
           ]//items
          }
        ]//items
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm,{
		header : '计划编号',
		dataIndex : 'planCode',
		width: 120
	},{
		header : '加工订单编号',
		dataIndex : 'orderCode',
		width: 180
	},{
		header : '合同编号',
		dataIndex : 'contractCode',
		width: 180
	}, {
		header : '制单人',
		dataIndex : 'userName',
		width: 60
	},{
		header : '制单时间',
		dataIndex : 'editDateString',
		width: 120
	},{
		header : '备注',
		dataIndex : 'memo',
		width: 120
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	},{
		header : 'ID',
		hidden : true,
		dataIndex : 'id'
	}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var contractListStore = new Ext.data.JsonStore({
		url : PATH + '/reservePlan/ReservePlanViewAction.do?ffc=reservePlanList&limit=14',
		root : 'items',
		totalProperty : 'totalCount',
		//autoLoad : true,
		fields : ['id', 'contractCode', 'orderCode','userName', 'editDateString','memo','planCode']
	});
	
	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '加工订单信息！'
        });
var grid = new Ext.grid.GridPanel({
		// width : 450,
		bodyStyle:'width:100%',
		height : 300,
		//autoHeight : true,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		//el : 'quogrid',
        view : gv,
		ds : contractListStore,
		cm : cm,
		sm:sm,
		listeners : {
			rowclick : function(grid, rowIndex,e ){
				var s = grid.getStore();
				record = s.getAt(rowIndex);
				//this.cOContractTree.store.setBaseParam('mainId',record.id);
				planDetialInstance.store.load({params:{start:0,limit:15,mainId:record.id,status:2}});
			}
		},
		bbar : new Ext.PagingToolbar({
				pageSize : 20,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : contractListStore
			})
});

var PlanDetail =  Ext.extend(Ext.grid.GridPanel,{
		contractId:null,
		store:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			var proxy = new Ext.data.HttpProxy({url: PATH + "/reservePlan/ReservePlanViewAction.do?ffc=findPlanDetailByMainId"});
			var Bill = Ext.data.Record.create([
					{name: 'id',mapping:'id',type:'string'},
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
				   {name: 'arrivalAmount',mapping:'arrivalAmount',type:'float'}
			]);
			var reader = new Ext.data.JsonReader({}, Bill);
			this.store = new Ext.data.Store({
				proxy: proxy,
				reader: reader
			});
			//this.store.baseParams.contractId = this.contractId;

			PlanDetail.superclass.constructor.call(this, {
				height:520,
				store:this.store,
				columns: [
					 new Ext.grid.RowNumberer(),
					{header:'计划明细id',width:100,dataIndex:'id',hidden:true},
					{header:'名称',width:85,dataIndex:'productName'},
					{header:'工具牌号',width:200,dataIndex:'brandCode'},
					{header:'采购数量',width:100,dataIndex:'orderAmount'},
					{header:'计划数量',width:70,dataIndex:'planAmount',
						renderer:function(value,metadata,record){
							if(value == null || value == ''){
								record.set('planAmount',0);
								return 0;
							}
							else{
								record.set('planAmount',value);
								return value
							};
						}
					},
					{header:'交货日期',width:100,dataIndex:'deliveryDate',
						renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							if(!value) return '';
							if(typeof(value) == 'string') return value;
							else{
								var str = value.format('Y-m-d');
								record.set('deliveryDate',str);
								return str;
							}
						}
					 },
					{header:'提取数量',width:70,dataIndex:'outAmount'},
					{header:'订货数量',width:70,dataIndex:'orderAmount',hidden:true},
					{header:'到货数量',width:70,dataIndex:'arrivalAmount',hidden:true},
					//{header:'计量单位',width:70,dataIndex:'productUnit'},
					//{header:'货品金额',width:100,dataIndex:'productMoney'},
					
					{header:'品牌',width:70,dataIndex:'productBrand'},
					{header:'备注1',width:100,dataIndex:'memo'},
					{header:'备注2',width:100,dataIndex:'memo2'},
					//{header:'合同分名称',width:100,dataIndex:'proSortName'},
					{header:'项目号',width:50,dataIndex:'projectCode',hidden:true},
					{header:'序号',width:50,dataIndex:'serialNumber',hidden:true},
					{header:'货品工具主键',width:0,hidden : true,dataIndex:'toolsId'},
					{header:'货品工具父节点id',width:0,hidden : true,dataIndex:'parentToolsId'},
					{header:'货品工具叶子节点',width:0,hidden : true,dataIndex:'leaf'},
					{header:'合同货主键',width:100,hidden : true,dataIndex:'contractProductDetailId'},
					{header:'货品编号',width:100,dataIndex:'productCode'},
					{header:'合同分_主键',width:70,dataIndex:'contractProjectSortId',hidden : true}
				]
			})
		}
	})

var planDetialInstance = new PlanDetail();

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '选择储备计划',
			width:920,
			height:595,
			buttonAlign:'right',
			maximizable :true,
			modal : true,
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
				layout: 'fit',
                split: true,
                width: 200,
                height : 120,
                minSize: 140,
                maxSize: 300,
                collapsible: true,
                margins: '5 5 5 5',
                items : [searchForm]
                
            }, {
                region: 'center',
                layout: 'fit',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                margins: '-5 5 5 5',
                items : [grid]
            },{
				region: 'south',
				split: true,
				height : 150,
				collapsible: true,
				layout: 'fit',
				margins: '-5 5 5 5',
				items : [planDetialInstance]
			}],
			buttons: [{
				text : "确  定",
				iconCls : 'save-icon',
				handler : function(obj) {
					//currencyName,customerCode,taxRate,sellerName
					var arr = sm.getSelections();
					if(arr.length == 0){
						Ext.Msg.alert("消息", "请选择加工订单!");
						return ;
					}
					callBackMethod(arr,select_quotations_win,'');
				}
	         },{
				text : "取  消",
				iconCls : 'save-icon',
				handler : function() {
					select_quotations_win.close();
				}
	         }]
        });
		select_quotations_win.show(this);

		var obj2 = searchForm.getForm().getValues();
		if(initParams){
			Ext.applyIf(obj2,initParams);
		}
		for(var i in obj2){
			contractListStore.setBaseParam(i, obj2[i]);
		}
		contractListStore.load();
}


