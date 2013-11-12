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
 getConfig = function() {
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
       proxy: new Ext.data.HttpProxy({url:PATH + '/contract/contractInforsViewPanel.do?ffc=contractList&limit=' + PAGESIZE}), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'contractCode',mapping:'contractCode',type:'string'},
        {name: 'customerName',mapping:'customerName',type:'string'},
		{name: 'customerCode',mapping:'customerCode',type:'string'},
		
		{name: 'currencyName',mapping:'currencyName',type:'string'},
		{name: 'urgentLevel',mapping:'urgentLevel',type:'string'},
		{name: 'signDate',mapping:'signDate',type:'string'},

		{name: 'productMoney',mapping:'productMoney',type:'float'},
		{name: 'taxRate',mapping:'taxRate',type:'float'},
		{name: 'taxMoney',mapping:'taxMoney',type:'float'},
	    {name: 'totalMoney',mapping:'totalMoney',type:'float'},
		{name: 'overallRebate',mapping:'overallRebate',type:'float'},
		{name: 'finalMoney',mapping:'finalMoney',type:'float'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'ownContactPerson',mapping:'ownContactPerson',type:'string'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'status',mapping:'status',type:'string'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'deliveryAmount',mapping:'deliveryAmount',type:'float'},
		{name: 'orderAmount',mapping:'orderAmount',type:'float'},
		{name: 'outAmount',mapping:'outAmount',type:'float'},
		{name: 'tqOutAmount',mapping:'tqOutAmount',type:'float'},
		{name: 'money',mapping:'money',type:'float'},
		{name: 'memo',mapping:'memo',type:'string'},
		{name: 'orderPercent',mapping:'orderPercent',type:'float'},
		{name: 'orderArrivalPercent',mapping:'orderArrivalPercent',type:'float'},
		{name: 'allArrivalPercent',mapping:'allArrivalPercent',type:'float'},
		{name: 'deliveryPercent',mapping:'deliveryPercent',type:'float'},
		{name: 'contractAccountPercent',mapping:'contractAccountPercent',type:'float'},
		{name: 'invoiceMoneyPercent',mapping:'invoiceMoneyPercent',type:'float'},
		{name: 'fileCount',mapping:'fileCount',type:'float'},
		{name: 'invoiceStyle',mapping:'invoiceStyle',type:'string'},
		{name: 'invoiceProgressStyle',mapping:'invoiceProgressStyle',type:'string'}	
     ]),
		listeners : {
			'load' : function( store, records, ops ){
				insertTotalRow();
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
							new Ext.ffc.ContractStatusComboBox({x:445,y:3,width:100}),
							{xtype:'label',text: '编制时间',x:550,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'startDate',x:600,y:3, format:'Y-m-d'},
							{xtype:'label',text: '至',x:695,y:5,style:lableStyle_},
							{xtype:'datefield',name: 'endDate',x:715,y:3, format:'Y-m-d'},
							{xtype:'label',text: '编制人',x:835,y:5,style:lableStyle_},
							{xtype:'textfield',x:875,y:3,name: 'userName',width:100},
						//2
							{xtype:'label',text: '采购情况',x:0,y:35,style:lableStyle_},
							new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:50,y:33,width:140,
														fieldLabel : '采购情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'orderStatus',
																'orderStatusText'
															],
															data: [[-1, '全部'],[0, '未采购'], [1, '部分采购'],[2, '已全部采购']]
														}),
														valueField: 'orderStatus',
														hiddenName : 'orderStatus',
														displayField: 'orderStatusText'
													}),
						   {xtype:'label',text: '订单到货',x:195,y:35,style:lableStyle_},
							new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:245,y:33,width:140,
														fieldLabel : '订单到货情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'orderArrivalStatus',
																'orderArrivalStatusText'
															],
															data: [[-1, '全部'],[0, '订单未到货'], [1, '订单部分到货'],[2, '订单已全部到货']]
														}),
														valueField: 'orderArrivalStatus',
														hiddenName : 'orderArrivalStatus',
														displayField: 'orderArrivalStatusText'
													}),
						  {xtype:'label',text: '总体到货',x:395,y:35,style:lableStyle_},
						  new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:445,y:33,width:100,
														fieldLabel : '总体到货情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'allArrivalStatus',
																'allArrivalStatusText'
															],
															data: [[-1, '全部'],[0, '未到货'], [1, '部分到货'],[2, '已全部到货']]
														}),
														valueField: 'allArrivalStatus',
														hiddenName : 'allArrivalStatus',
														displayField: 'allArrivalStatusText'
													}),
						{xtype:'label',text: '开票时间',x:550,y:35,style:lableStyle_},
						{xtype:'datefield',name: 'invoiceStartDate',x:600,y:33, format:'Y-m-d'},
						{xtype:'label',text: '至',x:695,y:35,style:lableStyle_},
						{xtype:'datefield',name: 'invoiceEndDate',x:715,y:33, format:'Y-m-d'},
						{xtype:'label',text: '我方负责人',x:815,y:35,style:lableStyle_},
						{xtype:'textfield',name: 'ownContactPerson',x:875,y:33,width:100},
						//3
						{xtype:'label',text: '交货情况',x:0,y:65,style:lableStyle_},
						new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:50,y:63,width:140,
														fieldLabel : '交货情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'deliveryStatus',
																'deliveryStatusText'
															],
															data: [[-1, '全部'],[0, '未交货'], [1, '部分交货'],[2, '已全部交货']]
														}),
														valueField: 'deliveryStatus',
														hiddenName : 'deliveryStatus',
														displayField: 'deliveryStatusText'
													}),
						{xtype:'label',text: '回款情况',x:195,y:65,style:lableStyle_},
						new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:245,y:63,width:140,
														fieldLabel : '回款情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'contractAccountStatus',
																'contractAccountStatusText'
															],
															data: [[-1, '全部'],[0, '未回款'], [1, '部分回款'],[2, '已全部回款']]
														}),
														valueField: 'contractAccountStatus',
														hiddenName : 'contractAccountStatus',
														displayField: 'contractAccountStatusText'
													}),
						{xtype:'label',text: '开票情况',x:395,y:65,style:lableStyle_},
						new Ext.form.ComboBox({
														typeAhead: true,
														triggerAction: 'all',
														lazyRender:true,
														mode: 'local',
														x:445,y:63,width:100,
														fieldLabel : '开票情况',
														store: new Ext.data.ArrayStore({
															fields: [
																'invoiceStatus',
																'invoiceStatusText'
															],
															data: [[-1, '全部'],[0, '未开票'], [1, '部分开票'],[2, '已全部开票']]
														}),
														valueField: 'invoiceStatus',
														hiddenName : 'invoiceStatus',
														displayField: 'invoiceStatusText'
													}),
						{xtype:'label',text: '回款时间',x:550,y:65,style:lableStyle_},
						{xtype:'datefield',name: 'accountStartDate',x:600,y:63, format:'Y-m-d'},
						{xtype:'label',text: '至',x:695,y:65,style:lableStyle_},
						{xtype:'datefield',name: 'accountEndDate',x:715,y:63, format:'Y-m-d'},
						{xtype:'label',text: '合同备注',x:815,y:65,style:lableStyle_},
						{xtype:'textfield',name: 'memo',x:875,y:63,width:100},
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
												bform.findField('status').setValue('-2');
												bform.findField('orderStatus').setValue('-1');
												bform.findField('orderArrivalStatus').setValue('-1');
												bform.findField('allArrivalStatus').setValue('-1');
												bform.findField('deliveryStatus').setValue('-1');
												bform.findField('contractAccountStatus').setValue('-1');
												bform.findField('invoiceStatus').setValue('-1');
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
								    customerInfor : customerInfor
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
		plugins: expander,
        columns: [
			expander,
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "合同id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "合同编号", width: 180, sortable: true,  dataIndex: 'contractCode'},
            {header: "客户名称", width: 180, sortable: true,  dataIndex: 'customerName'},
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
			{header: "紧急程度", width: 60, sortable: true,  dataIndex: 'urgentLevel',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					if(value == 0){return '一般'}
					return '<span style="color:#ff0000">紧急</span>';
				}
			},
			{header: "签订日期", width: 80, sortable: true,  dataIndex: 'signDate'},
			{header: "我方负责人", width: 80, sortable: true,  dataIndex: 'ownContactPerson'},
			{header: "币别", width: 80, sortable: true,  dataIndex: 'currencyName'},
			{header: "最终金额", width: 100, sortable: true, dataIndex: 'finalMoney'},
			{header: "编制人", width: 65, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 120, sortable: true, dataIndex: 'editDateString'},			
			{header: "备注", width: 100, sortable: true, dataIndex: 'memo'},
			{header: "合同附件", width: 60,  dataIndex: 'fileCount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = null;
					if(value <= 0){
					    str = '<a href="#">未上传</a>';
					}else{
					    str = '<a href="javascript:Ext.ffc.showSlaveFillWindow({id:\'' + record.data.id + '\',busType:2})">查看</a>';
					}
					return str;
				}	
			},			
			{header: "审批信息", width: 75,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
					return str;
				}
			}
        ],
		tbar:[{
        		text:'新增合同',
				hidden : _config.isAddHide,
				iconCls : 'icon-add',
				listeners : {
					'click' : function() {
						Ext.ffc.select_quotations(function(arr,select_quotations_win){
							if(!arr || arr.length == 0) return;
							var idsArr = [];
							var currencyName0 = arr[0].get("currencyName");
							var customerCode0 = arr[0].get("customerCode");
							var taxRate0 = arr[0].get("taxRate");
							var sellerName0 = arr[0].get("sellerName");
							var quotationType = arr[0].get("quotationType");
							var paymentCondition = arr[0].get("paymentCondition");

							for(var i = 0 ;i < arr.length ;i++){
								if(arr[i].get("currencyName") != currencyName0){
									Ext.Msg.alert("消息", "所选择报价单，币别不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("customerCode") != customerCode0){
									Ext.Msg.alert("消息", "所选择报价单，客户不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("taxRate") != taxRate0){
									Ext.Msg.alert("消息", "所选择报价单，税率不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("sellerName") != sellerName0){
									Ext.Msg.alert("消息", "所选择报价单，买方不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("quotationType") != quotationType){
									Ext.Msg.alert("消息", "所选择报价单，类型不一致，不能生成一个合同!");
									return ;
								}else if(arr[i].get("paymentCondition") != paymentCondition){
									Ext.Msg.alert("消息", "所选择报价单，付款条件不一致，不能生成一个合同!");
									return ;
								}
								idsArr.push(arr[i].get("id"));
							}
							
							select_quotations_win.close();//关闭报价单选择窗口
							
							var _url = null;
							var _params = {};
							if(quotationType * 1 == 1){//项目报价
								if(idsArr.length > 1){
									Ext.Msg.alert("消息", "多个项目报价单，不能生成一个合同!");
									return ;
								}
								_url = PATH + "/contract/contractEditAction.do?ffc=consultProjectQuo";
								_params['id'] = idsArr[0];
							}else{
								_params['ids'] = idsArr;
								_url = PATH + "/contract/contractEditAction.do?ffc=consultGeneralQuo";
							}
							
						    Ext.Ajax.request({
							method: "post",
							params: _params,
							url: _url,
							success: function(response){
									//alert(response.responseText);
									eval("var temp = " + response.responseText);
									var conEditWin = new Ext.ffc.ContractEditWindow(
										{
											conctractInfor : temp,
											quotationType  : quotationType,
											_config : _config,
												listeners:{
													"close" : function(){
														contractListStore.reload();
														EventMger.fireEvent("createdContractEvent");
													}
												}
										}
									);
									conEditWin.show();
							}
							});
						});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDetailHide
			},{
        		text:'查看合同',
				hidden : _config.isDetailHide,
				iconCls : 'icon-detail',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要查看的合同!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行查看操作!");
								return;
							}
						var conId = arr[0].id;
						Ext.Ajax.request({
							method: "post",
							params: { id : conId},
							url: PATH + "/contract/contractViewAction.do?ffc=contractViewById",
							success: function(response){
									eval("var temp = " + response.responseText);
									var conEditWin = new Ext.ffc.ContractViewWindow({conctractInfor:temp,readOnly : true});
									conEditWin.show();
							}
						});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isModifyHide
			},{
        		text:'修改合同',
				hidden : _config.isModifyHide,
				iconCls : 'icon-modify',
				listeners : {
					'click' : function() {
							var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要修改的合同!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行修改操作!");
								return;
							}
							var conId = arr[0].id;
							if(arr[0].get("status") != 0 && arr[0].get("status") != 3){
								Ext.Msg.alert("消息", "所选择合同不允许修改!");
							    return ;
							}
						    Ext.Ajax.request({
							method: "post",
							params: { id : conId},
							url: PATH + "/contract/contractViewAction.do?ffc=contractViewById",
							success: function(response){
									eval("var temp = " + response.responseText);
									var conEditWin = new Ext.ffc.ContractEditWindow(
										{
										    canDetailDel:_config.canDetailDel,
											conctractInfor:temp,
											_config : _config,
												listeners:{
													"close" : function(){
														contractListStore.reload();
													}
												}
										}
									);
									conEditWin.show();
							}
							});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDeleteHide
			},{
        		text:'删除合同',
				hidden : _config.isDeleteHide,
				iconCls : 'icon-delete',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
						if(arr.length <= 0){
							Ext.Msg.alert("消息", "请选择要删除的合同!");
							return;
						}
						var ids = [];
						for(var i = 0 ;i < arr.length;i++){
						     ids.push(arr[i].id);
							 if(arr[i].get("status") != 0 && arr[i].get("status") != 3){
								Ext.Msg.alert("消息", "所选择合同不允许删除!");
							    return ;
							 }
						}
						Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中合同!', function(btn){
							if(btn != 'yes'){return ;}
							    Ext.Ajax.request({
									method: "post",
									params: { 'ids' : ids},
									url: PATH + "/contract/contractEditAction.do?ffc=deleteContract",
									success: function(response){
											contractListStore.reload();
									}
								});
						   });
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isSubmitHide
			},{
        		text:'提交审批',
				hidden : _config.isSubmitHide,
				iconCls : 'icon-submit',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要提交审批的合同!");
								return;
							}
							var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id);
								 if(arr[i].get("status") != 0 && arr[i].get("status") != 3){
									Ext.Msg.alert("消息", "只有[编制]状态，或者[退回]状态合同才允许提交审批!");
									return ;
								 }
							}
							Ext.MessageBox.confirm('系统提示', '请确认是否提交审批当前所选中合同!', function(btn){
								if(btn != 'yes'){return ;}
								Ext.Ajax.request({
								method: "post",
								params: { 'ids' : ids},
								url: PATH + "/contract/submitAuditContractAction.do",
								success: function(response){
										if(response.responseText != ''){
											Ext.Msg.alert("消息", response.responseText);
										}else{
											Ext.Msg.alert("消息", "提交审批成功！");
											contractListStore.reload();
											Ext.ffc.sendMsg2Server();
										}
										
								}
								});
							});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isExecuteHide
			},{
        		text:'执行合同',
				hidden : _config.isExecuteHide,
				iconCls : 'icon-run',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要执行的合同!");
								return;
							}
						var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id);
								 if(arr[i].get("status") != 2){
									Ext.Msg.alert("消息", "只有[审核通过]状态的合同，才允许被执行!");
									return ;
								 }
							}
						Ext.MessageBox.confirm('系统提示', '请确认是否要执行当前所选中合同!', function(btn){
							if(btn != 'yes'){return ;}
							Ext.Ajax.request({
								method: "post",
								params: { ids : ids},
								url: PATH + "/contract/contractEditAction.do?ffc=runContract",
								success: function(response){
										contractListStore.reload();
										Ext.ffc.sendMsg2Server();
								}
							});
						});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isEndHide
			},{
        		text:'终结合同',
				hidden : _config.isEndHide,
				iconCls:'icon-contract-end',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要终结的合同!");
								return;
							}
						var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id);
							}
						Ext.MessageBox.confirm('系统提示', '请确认是否要终结当前所选中合同!', function(btn){
							if(btn != 'yes'){return ;}
							Ext.Ajax.request({
								method: "post",
								params: { ids : ids},
								url: PATH + "/contract/contractEditAction.do?ffc=endContract",
								success: function(response){
										eval("var temp = " + response.responseText);
										if(temp.message != ''){
											Ext.Msg.alert("消息", temp.message);
											var rnum = -1;
											var grid = gridCheckSele.grid;
											var store = grid.getStore();
											for(var j = 0,len = store.getCount();j < len ;j++ )
											{
												if(store.getAt(j).id == temp.id){
													rnum = j;
													break;
												}; 
											}
											if(rnum >= 0){
												grid.getView().getRow(rnum).style.backgroundColor = '#66FFCC';
												grid.getView().getRow(rnum).style.color = '#FF0000';
											}
											
										}
										contractListStore.reload();
								}
							});
						});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isCancelHide
			},{
        		text:'作废合同',
				hidden : _config.isCancelHide,
				iconCls:'icon-zuofei',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要作废的合同!");
								return;
							}
						var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id); 
							}
						Ext.MessageBox.confirm('系统提示', '请确认是否要作废当前所选中合同!', function(btn){
							if(btn != 'yes'){return ;}
							Ext.Ajax.request({
								method: "post",
								params: { ids : ids},
								url: PATH + "/contract/contractEditAction.do?ffc=voidContract",
								success: function(response){
										Ext.Msg.alert("消息", "所选合同已经作废!");
										contractListStore.reload();
										//select_quotations_win.close();
								}
							});
						});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isPlanHide
			},{
        		text:'合同进度图',
				hidden : _config.isPlanHide,
				iconCls:'icon-contract-img',
				expFlag : true,
				listeners : {
					'click' : function(obj) {
						var len = grid.getStore().getCount();
						if(obj.expFlag){
							for (var i = 0 ;i < len ;i++ ){
								expander.expandRow(i);
							}
						}else{
							for (var i = 0 ;i < len ;i++ ){
								expander.collapseRow(i);
							}
						}
						obj.expFlag = !obj.expFlag;
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isUploadHide
			},{
        		text:'上传附件',
				hidden : _config.isUploadHide,
				iconCls:'icon-add',
				listeners : {
					'click' : function(obj) {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要上传附件的合同!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条合同，进行上传附件操作!");
								return ;
							}
						var data = arr[0].data;
						Ext.ffc.showSlaveFillWindow({id:data.id,busType:2});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isToExcelHide
			},{
        		text:'导出Excel',
				hidden : _config.isToExcelHide,
				iconCls:'icon-excel',
				listeners : {
					'click' : function(obj) {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要导出的合同!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条合同，进行合同导出操作!");
								return ;
							}
							var status = arr[0].get("status");
							if(status != 2 && status != 4 && status != 5){
								Ext.Msg.alert("消息", "只有审批通过后的合同，才允许导出!");
							    return ;
							}
						var data = arr[0].data;
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:5,
							exportDataMethod:function(tempId){
								window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertExcel&busId=" + data.id + "&tempId=" + tempId);
							}
						}).show();
						
					}
				}
        	},{
        		text:'导出合同列表',
				//hidden : _config.isToExcelHide,
				iconCls:'icon-excel',
				listeners : {
					'click' : function(obj) {
						var values = selectForm2.getForm().getValues();
						var para = [];
						for(var i in values){
							if(values[i] && values[i] != ''){
						       para.push(i + "=" + values[i]);
							}
						}
						window.open(PATH + "/contract/contractOutExcelAction.do?ffc=expertList2Excel&" + para.join("&"));
					}
				}
        	},{
        		text:'统计图',
				hidden : _config.chartsHidden,
				iconCls:'icon-chart',
				expFlag : true,
				menu : new Ext.menu.Menu({
					items: [
						{
                            text: '销售业绩月份分布图',
                            group: 'theme',
                            handler: function(){
							   var _store = new Ext.data.Store({
							   remoteSort : true,
							   proxy: new Ext.data.HttpProxy({url:PATH + '/contract/contractChartsViewAction.do?m=contractMonthMoneysChart'}), 
							   reader: new Ext.data.JsonReader({}, 
										 ['NAME','visits'])
							   });
							   _store.load();
								var selectForm2 = new Ext.ffc.StatisticsMoneyByMonthsForm({_store:_store});
								var viewPanel = new Ext.ffc.StatisticsMoneyPanel({_store:_store});
								new Ext.ffc.StatisticsWindow({
									title :  '本年销售业绩月份分布图',
									items : [
										selectForm2,
										viewPanel
									]
								}).show();
							}
                        },
						{
                            text: '员工业绩对比图',
                            group: 'theme',
                            handler: function(){
							   var _store = new Ext.data.Store({
							   remoteSort : true,
							   proxy: new Ext.data.HttpProxy({url:PATH + '/contract/contractChartsViewAction.do?m=contractMoneysForOwnPerson'}), 
							   reader: new Ext.data.JsonReader({}, 
										 ['money','own_contact_person','h_money'])
							   });
							   _store.load();
								var selectForm2 = new Ext.ffc.StatisticsMoneyForOwnerPersonForm({_store:_store});
								var viewPanel = new Ext.ffc.StatisticsMoneyForOwnerPersonPanel({_store:_store,moneyTitle:'销售额',hMoneyTitle:'回款额',charXtype:'columnchart',charType:'column'});
								new Ext.ffc.StatisticsWindow({
									title :  '员工业绩对比图',
									items : [
										selectForm2,
										viewPanel
									]
								}).show();
							}
                        },
						{
                            text: '年度同期对比图',
                            group: 'theme',
                            handler: function(){
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
					]
				})
        	}
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
	select_quotations_win.render('contract_list_');

	contractListStore.load({params:{start:0}});

	Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

