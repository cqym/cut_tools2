Ext.onReady(function(){
	var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
	function requestTotalDatas(params,form,callBackMethod){
		Ext.Ajax.request({
			url: PATH + '/contract/contractInforsViewPanel.do?ffc=getContractTotalInfor',
			params: params,
			success : function(response) {
				 eval("var rt=" + response.responseText);
				 callBackMethod(rt);
			}
		});
	}

function insertTotalRow(){
	var arr = grid.getView().getRows();
	if(arr.length){
		var seachParams = selectForm2.getForm().getValues();
		requestTotalDatas(seachParams,selectForm2,function(po){
			var ttt = new (Ext.Template)("<div class=\"x-grid3-row\"><table class=\"x-grid3-row-table\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"750\">", "<tbody><tr ><td style='width:100px;font-height:bold;text-algin:right'>合计行: </td><td style='font-height:bold'>销售金额:</td><td style='width:100px;color:red;text-algin:right'>{final_money}</td><td style='font-height:bold'>发票金额:</td><td style='width:100px;color:red;text-algin:right'>{invoice_money}</td><td style='font-height:bold'>回款金额:</td><td style='width:100px;color:red;text-algin:right'>{money}</td><td style='font-height:bold'>交货金额:</td><td style='width:100px;color:red;text-algin:right'>{delivery_money}</td>",
				"</tr></tbody></table></div>");
			
			Ext.DomHelper.insertHtml("afterEnd", arr[arr.length - 1], ttt.apply(po));
		});
	}
}


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
       proxy: new Ext.data.HttpProxy({url:PATH + '/querystatistics/CustomerOrderQueryAction.do?m=customerOrderList&limit=' + PAGESIZE}), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'customerName',mapping:'customerName',type:'string'},
		{name: 'ownContactPerson',mapping:'ownContactPerson',type:'string'},
		{name: 'contractCode',mapping:'contractCode',type:'string'},
		{name: 'status',mapping:'status',type:'string'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'deliveryAmount',mapping:'deliveryAmount',type:'float'},
		{name: 'orderAmount',mapping:'orderAmount',type:'float'},
		{name: 'arrivalAmount',mapping:'arrivalAmount',type:'float'},
		{name: 'contractMoney',mapping:'contractMoney',type:'float'},
		{name: 'invoiceMoney',mapping:'invoiceMoney',type:'float'},
		{name: 'accountsMoney',mapping:'accountsMoney',type:'float'},
		{name: 'memo',mapping:'memo',type:'string'}
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
							{xtype:'label',text: '合同编号',x:0,y:5,style:lableStyle_},
							{xtype:'textfield',  name: 'contractCode',x:50,y:3,width:140},
							{xtype:'label',text: '客户名称',x:195,y:5,style:lableStyle_},
							{xtype:'textfield',  name: 'customerName',x:245,y:3,width:140},
							{xtype:'label',text: '合同状态',x:395,y:5,style:lableStyle_},
							new Ext.ffc.ContractStatusComboBox({x:460,y:3,width:140}),
							{xtype:'label',text: '编制时间',x:605,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'startDate',x:655,y:3, format:'Y-m-d'},
							{xtype:'label',text: '至',x:770,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'endDate',x:790,y:3, format:'Y-m-d'},
							{xtype:'label',text: '客户负责人',x:900,y:5,style:lableStyle_},
							{xtype:'textfield',x:950,y:3,name: 'ownContactPerson',width:100},
							{xtype:'label',text: '年度:',x:1075,y:5,style:this.lableStyle_},
							new Ext.ffc.YearComboBox({x:1110,y:3, width:80}),
							//2
							{xtype:'label',text: '备注',x:0,y:35,style:lableStyle_},
						    {xtype:'textfield',name: 'memo',x:50,y:33,width:140},
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
		tbar:[{
        		text:'客户对比图',
				hidden : _config.isDetailHide,
				iconCls : 'icon-chart',
				listeners : {
					'click' : function() {
						var _store = new Ext.data.Store({
						   remoteSort : true,
						   proxy: new Ext.data.HttpProxy({url:PATH + '/contract/contractChartsViewAction.do?m=getContractMoneysGroupByMonthForTowYear'}), 
						   reader: new Ext.data.JsonReader({}, 
							   [{name: 'money',mapping:'money1',type:'float'},
							   {name: 'own_contact_person',mapping:'month',type:'string'},
							   {name: 'h_money',mapping:'money2',type:'float'}])
						   });
						var dt = new Date()
						var curY = dt.format('Y');
						_store.load({params:{year1:curY,year2:curY-1}});
						var selectForm2 = new Ext.ffc.StatisticsMoneyByYearMonthsForm({_store:_store});
						var viewPanel = new Ext.ffc.StatisticsMoneyForOwnerPersonPanel({_store:_store,moneyTitle:"第一年度",hMoneyTitle:"对比年度",charXtype:'linechart',charType:'line'});
						new Ext.ffc.StatisticsWindow({
							title :  '年度同期对比图',
							items : [
								selectForm2,
								viewPanel
							]
						}).show();
					}
				}
        	}],
        columns: [
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "合同id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "客户名称", width: 180, sortable: true,  dataIndex: 'customerName'},
			{header: "我方负责人", width: 80, sortable: true,  dataIndex: 'ownContactPerson'},
            {header: "合同编号", width: 180, sortable: true,  dataIndex: 'contractCode'},
			{header: "状态", width: 65, sortable: true, dataIndex: 'status',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [[0,'<span style="color:#990000">编制</span>'],
							[1,'<span style="color:#99CC00">待审批</span>'],
							[2,'<span style="color:#0033FF">审批通过</span>'],
							[3,'<span style="color:#FF3300">审批退回</span>'],
							[4,'<span style="color:#339933">执行</span>'],
							[5,'<span style="color:#330000">终结</span>'],
							[-1,'<span style="color:#33FFFF">作废</span>']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
				}
			},
			{header: "合同数量", width: 100, sortable: true, dataIndex: 'contractAmount'},
			{header: "订货数量", width: 80, sortable: true,  dataIndex: 'orderAmount'},
			{header: "到货数量", width: 100, sortable: true, dataIndex: 'arrivalAmount'},
			{header: "交货数量", width: 65, sortable: true, dataIndex: 'deliveryAmount'},
			{header: "合同金额", width: 120, sortable: true, dataIndex: 'contractMoney'},			
			{header: "开票金额", width: 100, sortable: true, dataIndex: 'invoiceMoney'},
			{header: "付款金额", width: 100, sortable: true, dataIndex: 'accountsMoney'},
			{header: "备注", width: 100, sortable: true, dataIndex: 'memo'},
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
	select_quotations_win.render('customer_order_query_list_');
	contractListStore.setBaseParam("year", new Date().format('Y'));
	contractListStore.load({params:{start:0}});

	Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

