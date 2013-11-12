Ext.onReady(function(){
	var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
	var deliveryListStore = new Ext.data.Store({
	   remoteSort : true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/delivery/deliveryViewAction.do?ffc=viewList&deliveryType=2&limit=' + PAGESIZE}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
		{name: 'deliveryCode',mapping:'deliveryCode',type:'string'},
        {name: 'contractCode',mapping:'contractCode',type:'string'},
		{name: 'quotationCode',mapping:'quotationCode',type:'string'},
        {name: 'customerName',mapping:'customerName',type:'string'},
		{name: 'deliveryDate',mapping:'deliveryDate',type:'string'},
		{name: 'contactPerson',mapping:'contactPerson',type:'string'},
		{name: 'acceptPerson',mapping:'acceptPerson',type:'string'},
		{name: 'acceptDate',mapping:'acceptDate',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'status',mapping:'status',type:'string'},
		{name: 'fileCount',mapping:'fileCount',type:'int'},
		{name: 'memo',mapping:'memo',type:'string'}
     ])
	});


var  selectForm3 = new Ext.FormPanel({
                        labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:70,monitorValid:false,border:false,
                        items:[
                            {layout:'column',border:false,labelSeparator:':',
                            defaults:{layout: 'form',border:false,columnWidth:.25},
                            items:[
                                {items: [{xtype:'textfield',fieldLabel: '交货单编号',name: 'deliveryCode',anchor:'90%'}]},
								{items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'90%'}]},	
							    {items: [{xtype:'textfield',fieldLabel: '报价单编号',name: 'quotationCode',anchor:'90%'}]},	
								{columnWidth:0.12,items: [new Ext.ffc.DeliveryStatusComboBox({anchor:'100%'})]},
								{columnWidth:0.12,items: [new Ext.ffc.YearComboBox({anchor:'100%'})]},      
								{items: [{xtype:'textfield',fieldLabel: '客户名称',name: 'customerName',anchor:'90%'}]},
                                {items: [{xtype:'datefield',fieldLabel: '编制时间',name: 'startTime',anchor:'90%', format:'Y-m-d'}]},
								{items: [{xtype:'datefield',fieldLabel: '至',name: 'endTime',anchor:'90%', format:'Y-m-d'}]},
							    {items: [{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'90%'}]}
                                ]//items
                            }
                        ],//items
						bbar : ['->',{xtype:'button',text: '搜    索',iconCls:'icon-search',name: 'seachBt',width:80,
									handler : function() {
										var seachParams = selectForm3.getForm().getValues();
										for(var i in seachParams){
											deliveryListStore.setBaseParam(i, seachParams[i]);
										}
										deliveryListStore.load();
									}
								},'-',{  
										text:'重置'  
										,iconCls:'icon-reset',
										listeners : {
											'click' : function(){
												var bform = selectForm3.getForm();
												bform.reset();
												bform.findField('status').setValue('-1');
											},scope : this
										}
										
								}],
						listeners : {
							'render': function(p) {
								p.getEl().on('keypress', function(){
									if(window.event.keyCode == 13){
										var bts = p.getBottomToolbar().items;
									    for(var i = 0,len = bts.length; i < len;i++ ){
											var t = bts.get(i);
											if(t && t.text == '搜    索'){
												t.handler();
											}
										}
									}
								});
							}
						}
                    });//FormPanel

var gridCheckSele = new Ext.grid.CheckboxSelectionModel();

getConfig = function() {
	var modules = LoginInfor.modules
	var _configStr = "{";
	for(var i = 0; i < modules.length; i++) {
		var module = modules[i];
		if("008" == module.id) {
			var childModule = module.children;
			for(var j = 0; j < childModule.length; j++) {
				if("008002" == childModule[j].id) {
					var _configArr = childModule[j].children;
					if(_configArr.length > 0) { 
						for(var k = 0; k < _configArr.length; k++) {
							if(k != _configArr.length-1)
								_configStr += _configArr[k].url + ",";
							else 
								_configStr += _configArr[k].url + "}"
						}
					} else {
						_configStr += "}"
					}
					break;
				}
			}
			
			break;
		}
	}
	return Ext.decode(_configStr);
}

var _config = {isAddHide : true,isModifyHide : true,isDelHide : true,isSubmitHide : true,
	isDetailHide : true, isToExcelHide : true}

Ext.apply(_config, getConfig());

    var grid = new Ext.grid.GridPanel({
        ds : deliveryListStore,
        store: deliveryListStore,
		sm:gridCheckSele,
		frame:true,
        columns: [new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "到货单id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "交货单编号", width: 155, sortable: true,  dataIndex: 'deliveryCode'},
			{header: "状态", width: 75, sortable: true, dataIndex: 'status',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [[0,'<span style="color:#990000">编制</span>'],
							[1,'<span style="color:#99CC00">待审批</span>'],
							[2,'<span style="color:#0033FF">审批通过</span>'],
							[3,'<span style="color:#FF3300">审批退回</span>'],
							[-1,'<span style="color:#33FFFF">作废</span>']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
				}
			},
			{header: "交货日期", width: 75, sortable: true, dataIndex: 'deliveryDate'},
			{header: "合同编号", width: 170, sortable: true,  dataIndex: 'contractCode'},
			{header: "试刀申请单号", width: 170, sortable: true,  dataIndex: 'quotationCode'},
            {header: "客户名称", width: 155,   sortable: true,dataIndex: 'customerName'},
			{header: "客户我方联系人", width: 115, sortable: true, dataIndex: 'contactPerson'},
			{header: "编制人", width: 75, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 130, sortable: true, dataIndex: 'editDateString'},
			{header: "备注", width: 150, sortable: true, dataIndex: 'memo'},
			{header: "审批信息", width: 75, sortable: true, dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
					return str;
				}
			},{header: "附件", width: 60,  dataIndex: 'fileCount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = null;
					if(value <= 0){
					    str = '<a href="#">未上传</a>';
					}else{
					    str = '<a href="javascript:Ext.ffc.showSlaveFillWindow({id:\'' + record.data.id + '\',busType:9})">查看</a>';
					}
					return str;
				}	
			}
        ],
        //stripeRows: true,
        //height:580,
        //width:1450,
		tbar:[{
        		text:'新增交货单',
        		hidden : _config.isAddHide,
				iconCls : 'icon-add',
				listeners : {
					'click' : function() {
						Ext.ffc.select_quotations('4','6',function(arr,select_quotations_win){
							if(!arr || arr.length == 0) return;
							var idsArr = [];
							
							var quotationId = arr[0].get("id");
							//alert(quotationId);
							select_quotations_win.close();//关闭合同选择窗口
							
							Ext.Ajax.request({
									method: "post",
									//params: { 'ids' : ids},
									url: PATH + '/delivery/deliveryEditAction.do?ffc=consultQuotation&id=' + quotationId,
									success: function(response){
											eval("var temp = " + response.responseText);
											var conEditWin = new Ext.ffc.DeliveryEditWindow({
											title : '新增交货单',
											deliveryInfor : temp,
												listeners :{
														close : function(p){
															deliveryListStore.reload();
														}
													}	
											});
											conEditWin.show();									
									}
							});
						});
					}//---------------click
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isAddHide
			},{
        		text:'查看交货单',
        		hidden : _config.isDetailHide,
				iconCls : 'icon-detail',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要查看的交货单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行查看操作!");
								return;
							}
							Ext.Ajax.request({
									method: "post",
									//params: { 'ids' : ids},
									url: PATH + '/delivery/deliveryViewAction.do?ffc=deliveryViewById&id=' + arr[0].data.id,
									success: function(response){
												eval("var temp = " + response.responseText);
												var conEditWin = new Ext.ffc.DeliveryEditWindow({
													title : '查看交货单',
													deliveryInfor : temp,
													readOnly:true,
													listeners :{
														close : function(p){
															deliveryListStore.reload();
														}
													}	
												});
												conEditWin.show();									
									}
							});			
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDetailHide
			},{
        		text:'修改交货单',
        		hidden : _config.isModifyHide,
				iconCls : 'icon-modify',
				listeners : {
					'click' : function() {
							var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要修改的交货单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行修改操作!");
								return;
							}
							var conId = arr[0].id;
							if(arr[0].get("status") != 0 && arr[0].get("status") != 3){
								Ext.Msg.alert("消息", "所选择交货单不允许修改!");
							    return ;
							}
							

							Ext.Ajax.request({
									method: "post",
									//params: { 'ids' : ids},
									url: PATH + '/delivery/deliveryViewAction.do?ffc=deliveryViewById&id=' + conId,
									success: function(response){
												eval("var temp = " + response.responseText);
												var conEditWin = new Ext.ffc.DeliveryEditWindow({
													title : '修改交货单',
													deliveryInfor : temp,
													listeners :{
														close : function(p){
															deliveryListStore.reload();
														}
													}	
												});
												conEditWin.show();									
									}
							});			
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isModifyHide
			},{
        		text:'删除交货单',
        		hidden : _config.isDelHide,
				iconCls : 'icon-delete',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
						if(arr.length <= 0){
							Ext.Msg.alert("消息", "请选择要删除的交货单!");
							return;
						}
						var ids = [];
						for(var i = 0 ;i < arr.length;i++){
						     ids.push(arr[i].id);
							 if(arr[i].get("status") != 0 && arr[i].get("status") != 3){
								Ext.Msg.alert("消息", "所选择交货单不允许删除!");
							    return ;
							 }
						}
						Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中交货单!', function(btn){
							if(btn != 'yes'){return ;}
							    Ext.Ajax.request({
									method: "post",
									params: { 'ids' : ids},
									url: PATH + "/delivery/deliveryEditAction.do?ffc=deleteDelivery",
									success: function(response){
											deliveryListStore.reload();
									}
								});
						   });
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDelHide
			},{
        		text:'提交审批',
        		hidden : _config.isSubmitHide,
				iconCls : 'icon-submit',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要提交审批的交货单!");
								return;
							}
							var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id);
								 if(arr[i].get("status") != 0 && arr[i].get("status") != 3){
									Ext.Msg.alert("消息", "只有[编制]状态，或者[退回]状态交货单才允许提交审批!");
									return ;
								 }
							}
							
						    Ext.Ajax.request({
							method: "post",
							params: { 'ids' : ids,'deliveryType' : 'tryToools'},
							url: PATH + "/delivery/submitAuditdeliveryAction.do",
							success: function(response){
								    if(response.responseText != ''){
										Ext.Msg.alert("消息", response.responseText);
									}else{
										Ext.Msg.alert("消息", "提交审批成功！");
										deliveryListStore.load();
									}
									
							}
							});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isSubmitHide
			},{
        		text:'作废交货单',
        		hidden : _config.isDelHide,
				iconCls : 'icon-delete',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
						if(arr.length <= 0){
							Ext.Msg.alert("消息", "请选择要作废的交货单!");
							return;
						}
						var ids = [];
						for(var i = 0 ;i < arr.length;i++){
						     ids.push(arr[i].id);
						}
						Ext.MessageBox.confirm('系统提示', '请确认是否要作废当前所选中交货单!', function(btn){
							if(btn != 'yes'){return ;}
							    Ext.Ajax.request({
									method: "post",
									params: { 'ids' : ids},
									url: PATH + "/delivery/deliveryEditAction.do?ffc=invoidDelivery",
									success: function(response){
											deliveryListStore.reload();
									}
								});
						   });
					}
				}
        	},{
        		text:'导出Excel',
        		hidden : _config.isToExcelHide,
				iconCls:'icon-excel',
				expFlag : true,
				listeners : {
					'click' : function(obj) {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要导出的交货单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条交货单，进行交货单导出操作!");
								return ;
							}
							var status = arr[0].get("status");
							if(status != 2){
								Ext.Msg.alert("消息", "只有审批通过后的交货单，才允许导出!");
							    return ;
							}
						var data = arr[0].data;
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:15,
							exportDataMethod:function(tempId){
								window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertExcel&busId=" + data.id + "&tempId=" + tempId);
							}
						}).show();
						//window.open(PATH + "/delivery/outExcelAction.do?ffc=expertExcel&id=" + data.id);
					}
				}
        	},{
        		text:'上传附件',
				hidden : _config.isUploadHide,
				iconCls:'icon-add',
				listeners : {
					'click' : function(obj) {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要上传附件的交货单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条交货单，进行上传附件操作!");
								return ;
							}
						var data = arr[0].data;
						Ext.ffc.showSlaveFillWindow({id:data.id,busType:9});
					}
				}
        	},{
        		text:'导出列表',
				hidden : _config.exportListHide,
				iconCls:'icon-excel',
				listeners : {
					'click' : function(obj) {
						var values = selectForm3.getForm().getValues();
						var para = [];
						for(var i in values){
							if(values[i] && values[i] != ''){
						       para.push(i + "=" + values[i]);
							}
						}
						para.push('deliveryType=2');
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:'28',
							exportDataMethod:function(tempId){
								window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertListExcel&" + para.join('&') + "&tempId=" + tempId);
							}
						}).show();
					}
				}
        	}
		],	
		listeners : {
			rowdblclick : function(grid, rowIndex, e){
				grid.getTopToolbar().find('text','查看交货单')[0].fireEvent('click',null);
			}
		}
    });



    //grid.render('contract_list_');

	var select_quotations_win = new Ext.Panel({
            layout: 'border',
			//title: '参照报价单',
			width  : Ext.getBody().getWidth(),
			height : Ext.getBody().getHeight() - 50,
			buttonAlign:'right',
            listeners : {
            	'render' : function() {
            		
            		//grid.render();
            		//_store = grid.getStore();
            		//_store.on("beforeLoad", function() {
		           		//this.baseParams.searchStr = Ext.util.JSON.encode(_searchRecord);
		           //	})
            	}
            },
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
                layout:'fit',
                split: true,
                //width: 200,
                height : 104,
                minSize: 140,
                maxSize: 300,
                collapsible: true,
                margins: '5 5 5 5',
                items : [selectForm3]
                
            }, {
                region: 'center',
				layout: 'fit',
                //contentEl: 'quogrid',
                split: true,
                //height: 10000,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 5 5 5',
                items : [grid]
            }],
			bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: deliveryListStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"//,
           /* items:[
                '-', {
                pressed: true,
                enableToggle:true,
                text: 'Show Preview',
                cls: 'x-btn-text-icon details',
                toggleHandler: function(btn, pressed){
                    var view = grid.getView();
                    view.showPreview = pressed;
                    view.refresh();
                }
            }]*/
        })
        });
	select_quotations_win.render('try_tools_quo_delivery_list_');
	deliveryListStore.setBaseParam("year", new Date().format('Y'));
	deliveryListStore.load({params:{start:0}});
Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

