Ext.onReady(function(){
	var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
//权限
 var getConfig = function() {
	var modules = LoginInfor.modules
	var _configStr = "{";
	for(var i = 0; i < modules.length; i++) {
		var module = modules[i];
		if("004" == module.id) {
			var childModule = module.children;
					if(childModule.length > 0) { 
						for(var k = 0; k < childModule.length; k++) {
							if(k != childModule.length-1)
								_configStr += childModule[k].url + ",";
							else 
								_configStr += childModule[k].url + "}"
						}
					} else {
						_configStr += "}"
					}
			}
		}
	return Ext.decode(_configStr);
}

var _config = {isAddHide : true,isModifyHide : true,isDeleteHide : true,isSubmitHide : true,
	isDetailHide : true,isExecuteHide : true,isEndHide : true,isCancelHide : true,isPlanHide : true,
	isUploadHide : true, isToExcelHide : true,isInvoiceHide : true,isReturnMoneyHide : true,canDetailDel:false,chartsHidden:true,priceUpdate:false}

Ext.apply(_config, getConfig());
	var contractListStore = new Ext.data.Store({
	   remoteSort : true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/querystatistics/ProductsQueryAction.do?m=productsQueryList&limit=' + PAGESIZE}), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'toolsId',mapping:'toolsId',type:'string'},
        {name: 'productName',mapping:'productName',type:'string'},
        {name: 'brandCode',mapping:'brandCode',type:'string'},
		{name: 'productSortCode',mapping:'productSortCode',type:'string'},
		{name: 'productBrand',mapping:'productBrand',type:'string'},
		{name: 'productSource',mapping:'productSource',type:'string'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'scheduleAmount',mapping:'scheduleAmount',type:'float'},
		{name: 'tryAmount',mapping:'tryAmount',type:'float'},
		{name: 'money',mapping:'money',type:'float'}
     ]),
		listeners : {
			'load' : function( store, records, ops ){
				//insertTotalRow();
			}
		}
	});

var lableStyle_ = "font-size:9pt;text-align:left;width:85px";
var  selectForm2 = new Ext.FormPanel({
                        layout: 'absolute',
						defaultType: 'textfield',
						frame: true,
						width: Ext.getBody().getWidth(),
						height : 280,
                        items:[
						//line 1
							{xtype:'label',text: '产品名称:',x:0,y:5,style:lableStyle_},
							{xtype:'textfield',  name: 'productName',x:55,y:3,width:140},
							{xtype:'label',text: '牌号:',x:215,y:5,style:lableStyle_},
							{xtype:'textfield',  name: 'brandCode',x:245,y:3,width:140},
							{xtype:'label',text: '品牌:',x:430,y:5,style:this.lableStyle_},
							{xtype:'textfield',x:460,y:3,name: 'productBrand',width:100},
							{xtype:'label',text: '组别:',x:625,y:5,style:lableStyle_},
							{xtype:'textfield',x:655,y:3,name: 'productSortCode',width:100},
							{xtype:'label',text: '来源:',x:790,y:5,style:lableStyle_},
							{xtype:'textfield',name: 'productSource',x:820,y:3,width:140},
							{xtype:'label',text: '年度:',x:1075,y:5,style:this.lableStyle_},
							new Ext.ffc.YearComboBox({x:1110,y:3, width:80}),
						{xtype:'button',text:'搜    索',x:Ext.getBody().getWidth() - 200 > 815 ? Ext.getBody().getWidth() - 200 : 815,y:63,width:80,
										handler : function() {
											var seachParams = selectForm2.getForm().getValues();
											for(var i in seachParams){
												contractListStore.setBaseParam(i, seachParams[i]);
											}
											contractListStore.load();
										}
						},
						{xtype:'button',text:'重    置',x:Ext.getBody().getWidth() - 100 > 910 ? Ext.getBody().getWidth() - 100 : 910,y:63,width:80,
										handler : function() {
											var bform = selectForm2.getForm();
												bform.reset();
										},scope:this	
						}
						],//items
						listeners : {
							'render': function(p) {
								p.getEl().on('keypress', function(){
									if(window.event.keyCode == 13){
									    for(var i = 0,len = p.items.length; i < len;i++ ){
											var t = p.items.get(i);
											if(t.xtype == 'button' && t.text == '搜    索'){
												t.handler();
											}
									    }
									}
								});
							}
						}
                    });//FormPanel

var gridCheckSele = new Ext.grid.CheckboxSelectionModel();

Ext.ffc.showContractAccountInforWin = function(contractId,contractCode,customerName,customerCode,finalMoney,hidden){
		new Ext.ffc.ContractAccountWindow({contractId:contractId,contractCode:contractCode,customerName:customerName,customerCode:customerCode,finalMoney:finalMoney,hidden:hidden,titleDef:'回款'}).show();
}
Ext.ffc.showContractInvoiceInforWin = function (contractInforId,contractCode,customerCode,hidden,taxRate){
	if(taxRate == null || taxRate * 1 == 0){
		//Ext.Msg.alert("消息", "此合同税率为0,不能开发票!");
		return ;
	}
	Ext.Ajax.request({
		method: "post",
		params: null,
		url: PATH + "/contract/invoiceAction.do?ffc=getInvoiceInfor&contractInforId=" + contractInforId + "&customerCode=" + customerCode + "&invoiceType=0",
		success: function(response){
			eval(response.responseText);
			if(invoiceInfor == null){
				invoiceInfor = {memo:'',id:''};
			}
			new Ext.ffc.InvoiceEditWindow({
				invoiceMainInfor : {
					'contractId' : contractInforId,
					'contractCode'  : contractCode,
					'memo' : invoiceInfor.memo,
					'id' : invoiceInfor.id,
					'isHidden' : hidden,
					'invoiceType' : 0//0合同发票1订单发票
				},
				listeners : {
					'close' : function(obj) {
						var p = selectForm2;
						for(var i = 0,len = p.items.length; i < len;i++ ){
							var t = p.items.get(i);
							if(t.xtype == 'button' && t.text == '搜    索'){
								t.handler();
							}
						}
					}
				}
			}).show();	
		}
	});
}

    var grid = new Ext.grid.GridPanel({
        ds : contractListStore,
        store: contractListStore,
		sm:gridCheckSele,
		frame:true,
		layout: 'fit',
        columns: [
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "产品id", width: 160,  dataIndex: 'toolsId',hidden:true},
            {header: "产品名称", width: 180, sortable: true,  dataIndex: 'productName'},
            {header: "牌号", width: 180, sortable: true,  dataIndex: 'brandCode'},
			{header: "组别", width: 80, sortable: true,  dataIndex: 'productSortCode'},
			{header: "品牌", width: 80, sortable: true,  dataIndex: 'productBrand'},
			{header: "来源", width: 80, sortable: true,  dataIndex: 'productSource'},
			{header: "合同数量", width: 100, sortable: true, dataIndex: 'contractAmount'},
			{header: "预订数量", width: 65, sortable: true, dataIndex: 'scheduleAmount'},
			{header: "试刀数量", width: 120, sortable: true, dataIndex: 'tryAmount'},	
			{header: "销售金额(不含税)", width: 120, sortable: true, dataIndex: 'money'},
			{header: "销售单据查询", width: 120,  dataIndex: 'fileCount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = '<a href="javascript:Ext.ffc.viewSaleBill({toolsId:\'' + record.get('toolsId') + '\',brandCode:\'' + record.get('brandCode') + '\'})">查看</a>';
					return str;
				}	
			}
        ]
    });

	var select_quotations_win = new Ext.Panel({
            layout: 'border',
			width  : Ext.getBody().getWidth(),
			height : Ext.getBody().getHeight() - 55,
			buttonAlign:'right',
            items: [
            {
                region: 'north',
				layout:'fit',
                iconCls:'icon-grid',
                split: true,
                height : 100,
                collapsible: true,
                margins: '5 5 5 5',
                items : [selectForm2]
                
            }, {
                region: 'center',
				layout:'fit',
                split: true,
                collapsible: true,
                margins: '-5 5 5 5',
                items : [grid]
            }],
			bbar: new Ext.PagingToolbar({
				pageSize: PAGESIZE,
				store: contractListStore,
				displayInfo: true,
				displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg: "没有记录"
			})
        });
	select_quotations_win.render('products_query_list_');
	contractListStore.setBaseParam("year", new Date().format('Y'));
	contractListStore.load({params:{start:0}});

	Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

