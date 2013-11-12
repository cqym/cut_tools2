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
       proxy: new Ext.data.HttpProxy({url:PATH + '/querystatistics/UserWorkQueryAction.do?m=userWorkQueryList&limit=' + PAGESIZE}), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'userId',mapping:'userId',type:'string'},
        {name: 'userName',mapping:'userName',type:'string'},
		{name: 'contractProductMoney',mapping:'contractProductMoney',type:'float'},
		{name: 'contractFinalMoney',mapping:'contractFinalMoney',type:'float'},
		{name: 'deliveryMoney',mapping:'deliveryMoney',type:'float'},
		{name: 'invoiceMoney',mapping:'invoiceMoney',type:'float'},
	    {name: 'accountsMoney',mapping:'accountsMoney',type:'float'}
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
							{xtype:'label',text: '客户负责人',x:0,y:5,style:lableStyle_},
							{xtype:'textfield',  name: 'ownContactPerson',x:60,y:3,width:140},
							{xtype:'label',text: '时间',x:205,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'startDate',x:245,y:3, format:'Y-m-d'},
							{xtype:'label',text: '至',x:375,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'endDate',x:430,y:3, format:'Y-m-d'},
							{xtype:'label',text: '年度:',x:605,y:5,style:this.lableStyle_},
							new Ext.ffc.YearComboBox({x:655,y:3, width:80}),
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
var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<div class="row_grid_plugin">采购情况:<div class="panel_boder_plugin"><div class="panel_progress_plugin" style="width:{orderPercent}%" title="{orderPercent}%">&nbsp</div></div></div>',
            '<div class="row_grid_plugin"><b>订单到货情况:</b><div class="panel_boder_plugin"><div class="panel_progress_plugin" style="width:{orderArrivalPercent}%" title="{orderArrivalPercent}%"></div></div></div>',
			'<div class="row_grid_plugin"><b>总体到货情况:</b><div class="panel_boder_plugin"><div class="panel_progress_plugin" style="width:{allArrivalPercent}%" title="{allArrivalPercent}%"></div></div></div>',
			'<div class="row_grid_plugin"><b>交货情况:</b><div class="panel_boder_plugin"><div class="panel_progress_plugin" style="width:{deliveryPercent}%" title="{deliveryPercent}%"></div></div></div>',
			'<div class="row_grid_plugin"><a style="cursor:hand" href=\'javascript:Ext.ffc.showContractAccountInforWin("{id}","{contractCode}","{customerName}","{customerCode}","{finalMoney}",'+_config.isReturnMoneyHide+')\'><b>货款回收情况:</b></a><div class="panel_boder_plugin"><div class="panel_progress_plugin" style="width:{contractAccountPercent}%" title="{contractAccountPercent}%"></div></div></div>',
			'<div class="row_grid_plugin"><a style="cursor:hand" href=\'javascript:Ext.ffc.showContractInvoiceInforWin("{id}","{contractCode}","{customerCode}",'+_config.isInvoiceHide+',{taxRate})\'><b>票据开据情况:</b></a><div class="{invoiceStyle}"><div class="{invoiceProgressStyle}" style="width:{invoiceMoneyPercent}%" title="{invoiceMoneyPercent}%"></div></div></div>'
        )
});
    var grid = new Ext.grid.GridPanel({
        ds : contractListStore,
        store: contractListStore,
		sm:gridCheckSele,
		frame:true,
		layout: 'fit',
		//plugins: expander,
        columns: [
			//expander,
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "合同id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "客户负责人", width: 100, sortable: true,  dataIndex: 'userName'},
			{header: "合同金额（未税）", width: 120, sortable: true,  dataIndex: 'contractProductMoney'},
			{header: "合同金额（含税）", width: 120, sortable: true,  dataIndex: 'contractFinalMoney'},
			{header: "交货金额（未税）", width: 120, sortable: true,  dataIndex: 'deliveryMoney'},
			{header: "回款金额", width: 120, sortable: true, dataIndex: 'accountsMoney'},
			{header: "开票金额", width: 120, sortable: true, dataIndex: 'invoiceMoney'}
        ]
    });

    //grid.render('contract_list_');


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
	select_quotations_win.render('user_work_query_list_');
	contractListStore.setBaseParam("year", new Date().format('Y'));
	contractListStore.load({params:{start:0}});

	Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

