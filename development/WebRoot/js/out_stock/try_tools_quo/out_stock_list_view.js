Ext.onReady(function(){
	var PAGESIZE = parseInt((Ext.getBody().getHeight()-220)/24);
	var OutStockInforListStore = new Ext.data.Store({
		remoteSort : true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/outStock/outStockViewAction.do?ffc=outStockList&outStockType=6,7&limit=' + PAGESIZE}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
		{name: 'outStockCode',mapping:'outStockCode',type:'string'},
        {name: 'memo',mapping:'memo',type:'string'},
        {name: 'outStockType',mapping:'outStockType',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'status',mapping:'status',type:'string'},
		{name: 'contractCode',mapping:'contractCode',type:'string'},
		{name: 'quotationCode',mapping:'quotationCode',type:'string'},
		{name: 'outStockDate',mapping:'outStockDate',type:'string'},
	    {name: 'customerName',mapping:'customerName',type:'string'},
		{name: 'confirmDate',mapping:'confirmDate'},
		{name: 'confirmUserName',mapping:'confirmUserName',type:'string'}
     ])
	});


var  selectForm3 = new Ext.FormPanel({
                        labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:70,monitorValid:false,border:false,
                        items:[
                            {layout:'column',border:false,labelSeparator:':',
                            defaults:{layout: 'form',border:false,columnWidth:.14},
                            items:[
                                {items: [{xtype:'textfield',fieldLabel: '出库单编号',name: 'outStockCode',anchor:'100%'}]},	
								{items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'100%'}]},	
								{items: [{xtype:'textfield',fieldLabel: '报价单编号',name: 'quotationCode',anchor:'100%'}]},	
                                {items: [new Ext.ffc.OutStockStatusComboBox()]},
                                {items: [{xtype:'datefield',fieldLabel: '编制时间',name: 'startDate',anchor:'100%', format:'Y-m-d'}]},
								{items: [{xtype:'datefield',fieldLabel: '至',name: 'endDate',anchor:'100%', format:'Y-m-d'}]},
								{items: [new Ext.ffc.YearComboBox({anchor:'100%'})]},
								{items: [{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'100%'}]}
                                ]//items
                            }
                        ],//items
						bbar : ['->',{xtype:'button',text: '搜    索',iconCls:'icon-search',name: 'seachBt',width:80,
									handler : function() {
										var seachParams = selectForm3.getForm().getValues();
										for(var i in seachParams){
											OutStockInforListStore.setBaseParam(i, seachParams[i]);
										}
										OutStockInforListStore.load();
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
		if("007" == module.id) {
			var childModule = module.children;
			for(var j = 0; j < childModule.length; j++) {
				if("007003" == childModule[j].id) {
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
        ds : OutStockInforListStore,
        store: OutStockInforListStore,
		sm:gridCheckSele,
		frame:true,
        columns: [new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "出库单id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "出库单编号", width: 155, sortable: true,  dataIndex: 'outStockCode'},
			{header: "状态", width: 75, sortable: true, dataIndex: 'status',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [[0,'编制'],
							[1,'<span style="color:red">已确认</span>']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
				}
			},{header: "出库类型", width: 80, sortable: true,  dataIndex: 'outStockType',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var arr = [[6,'提取库存'],
					[7,'<span style="color:green">采购出库</span>']];
					for(var i = 0;i < arr.length ;i++){
						if(value == arr[i][0]){
							return arr[i][1];
						}
					}
					return value;
				}	
			},
			{header: "出库日期", width: 80, sortable: true,  dataIndex: 'outStockDate'},
			{header: "试刀申请编号", width: 170, sortable: true,  dataIndex: 'quotationCode'},
			{header: "编制人", width: 75, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 150, sortable: true, dataIndex: 'editDateString'},
			{header: "确认人", width: 65, sortable: true, dataIndex: 'confirmUserName'},
			{header: "确认时间", width: 100, sortable: true, dataIndex: 'confirmDate',renderer:function(value){
					if(value == null){
					    return '';
					}
				    return Ext.util.Format.date(new Date(value.time),'Y-m-d');
				}},
			{header: "备注", width: 100, sortable: true, dataIndex: 'memo'},
			{header: "附件", width: 60,  dataIndex: 'fileCount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = null;
					if(value <= 0){
					    str = '<a href="#">未上传</a>';
					}else{
					    str = '<a href="javascript:Ext.ffc.showSlaveFillWindow({id:\'' + record.data.id + '\',busType:8})">查看</a>';
					}
					return str;
				}	
			}
        ],
        //stripeRows: true,
        height:580,
        width:1270,
		tbar:[{
        		text:'新增出库单',
				hidden : _config.isAddHide,
				iconCls : 'icon-add',
				listeners : {
					'click' : function() {
						Ext.ffc.select_quotations_delivery('4','6',function(arr,select_quotations_win){
							if(!arr || arr.length == 0) return;
							var idsArr = [];
							
							var quotationCode = arr[0].get("quotationCode");
							var customerName = arr[0].get("customerName");
							var customerCode = arr[0].get("customerCode");
							var quotationId = arr[0].get("id");
							var memo = arr[0].get("memo");
							
							Ext.Ajax.request({
									method: "post",
									params: { 'quotationInforId' : quotationId},
									url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockQuotationDetail',
									success: function(response){
											eval("var detail=" + response.responseText);
											if(detail.length == 0){
												Ext.Msg.alert("消息", "所选合同明细中，没有产品可提取库存!");
												return ;
											}
											select_quotations_win.close();//关闭报价选择窗口
												var conEditWin = new Ext.ffc.QuotationOutStockEditWindow({			
																		title :  '新增出库单',
																		outStockInfor:{quotationId:quotationId,userName:LoginInfor.user.trueName,customerCode:customerCode,customerName:customerName,status:0,outStockType:6,quotationCode:quotationCode,outStockDetails:detail,memo:memo},
																		listeners :{
																			close : function(p){
																				OutStockInforListStore.reload();
																			}
																		}	
																	}
																);		
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
        		text:'查看出库单',
				hidden : _config.isDetailHide,
				iconCls : 'icon-detail',
				listeners : {
					'click' : function() {
							var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要查看的出库单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行查看操作!");
								return;
							}
							var conId = arr[0].id;
							var dData = {};
							Ext.apply(dData,arr[0].data);

							Ext.Ajax.request({
									method: "post",
									params: { 'id' : dData.id},
									url: PATH + '/outStock/outStockViewAction.do?ffc=outStockViewById',
									success: function(response){
										try{
											//alert(response.responseText);
											eval("var outStockInfor=" + response.responseText);
												var conEditWin = new Ext.ffc.QuotationOutStockEditWindow({
													'outStockInfor':outStockInfor,
													title :  '查看出库单',
													listeners :{
														close : function(p){
															OutStockInforListStore.reload();
														}
													},
													readOnly: true
												});
												conEditWin.show();
										}catch(e){alert(e);}
									}
							});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDetailHide
			},{
        		text:'修改出库单',
				hidden : _config.isModifyHide,
				iconCls : 'icon-modify',
				listeners : {
					'click' : function() {
							var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要修改的出库单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行修改操作!");
								return;
							}
							var conId = arr[0].id;
							if(arr[0].get("status") != 0 && arr[0].get("status") != 3){
								Ext.Msg.alert("消息", "所选择出库单不允许修改!");
							    return ;
							}
							if(arr[0].data.outStockType * 1 == 7){
								Ext.Msg.alert("消息", "加工采购出库单,不允许修改!");
							    return ;
						    }
							var dData = {};
							Ext.apply(dData,arr[0].data);

							Ext.Ajax.request({
									method: "post",
									params: { 'id' : dData.id},
									url: PATH + '/outStock/outStockViewAction.do?ffc=outStockViewById',
									success: function(response){
										try{
											//alert(response.responseText);
											eval("var outStockInfor=" + response.responseText);
												var conEditWin = new Ext.ffc.QuotationOutStockEditWindow({
													'outStockInfor':outStockInfor,
													title :  '修改出库单',
													listeners :{
														close : function(p){
															OutStockInforListStore.reload();
														}
													}	
												});
												conEditWin.show();
										}catch(e){alert(e);}
									}
							});
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isModifyHide
			},{
        		text:'删除出库单',
				hidden : _config.isDelHide,
				iconCls : 'icon-delete',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
						if(arr.length <= 0){
							Ext.Msg.alert("消息", "请选择要删除的出库单!");
							return;
						}
						var ids = [];
						for(var i = 0 ;i < arr.length;i++){
						     ids.push(arr[i].id);
							  if((arr[i].get("status") != 0 && arr[i].get("status") != 3) || arr[i].get("outStockType") * 1 == 7){
								Ext.Msg.alert("消息", "所选择出库单不允许删除!");
							    return ;
							 }
						}
						Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中出库单!', function(btn){
							if(btn != 'yes'){return ;}
							    Ext.Ajax.request({
									method: "post",
									params: { 'ids' : ids},
									url: PATH + '/outStock/outStockEditAction.do?ffc=deleteOutStock',
									success: function(response){
											OutStockInforListStore.reload();
									}
								});
						   });
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isDelHide
			},{
        		text:'出库确认',
				hidden : _config.isSubmitHide,
				iconCls : 'icon-submit',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要确认的出库单!");
								return;
							}
							var ids = [];
							for(var i = 0 ;i < arr.length;i++){
								 ids.push(arr[i].id);
								 if(arr[i].get("status") != 0){
									Ext.Msg.alert("消息", "只有[编制]状态,才允许出库确认!");
									return ;
								 }
							}
							
						    Ext.MessageBox.confirm('系统提示', '出库单确认后，将不允许修改，是否继续!', function(btn){
							if(btn != 'yes'){return ;}
							    Ext.Ajax.request({
									method: "post",
									params: { 'ids' : ids},
									url: PATH + '/outStock/outStockEditAction.do?ffc=affirmOutStock',
									success: function(response){
											OutStockInforListStore.reload();
									}
								});
						   });
					}
				}
        	},{
				xtype:'tbseparator',
				hidden : _config.isSubmitHide
			},{
        		text:'导出Excel',
				iconCls:'icon-excel',
				hidden : _config.isToExcelHide,
				expFlag : true,
				listeners : {
					'click' : function(obj) {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要导出的按报价出库单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条合同出库单，进行按报价出库单导出操作!");
								return ;
							}
							var status = arr[0].get("status");
							if(status != 1){
								Ext.Msg.alert("消息", "只有确认后的出库单，才允许导出!");
							    return ;
							}
						var data = arr[0].data;
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:24,
							exportDataMethod:function(tempId){
								window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertExcel&busId=" + data.id + "&tempId=" + tempId);
							}
						}).show();
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
								Ext.Msg.alert("消息", "请选择要上传附件的出库单!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条出库单，进行上传附件操作!");
								return ;
							}
						var data = arr[0].data;
						Ext.ffc.showSlaveFillWindow({id:data.id,busType:8});
					}
				}
        	},/*{
        		text:'导出列表',
				//hidden : _config.isToExcelHide,
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
						window.open(PATH + "/outStock/outStockOutExcelAction.do?ffc=expertList2Excel&outStockType=6&" + para.join("&"));
					}
				}
        	},*/{
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
						para.push('outStockType=6,7');
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:'27',
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
				grid.getTopToolbar().find('text','查看出库单')[0].fireEvent('click',null);
			}
		}
    });



    //grid.render('Quotation_list_');

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
                height : 108,
                minSize: 140,
                maxSize: 300,
                collapsible: true,
                margins: '5 5 5 5',
                items : [selectForm3]
                
            }, {
                region: 'center',
                layout:'fit',
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
            pageSize: PAGESIZE,
            store: OutStockInforListStore,
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
	select_quotations_win.render('try_tools_quo_out_stock_list_');
	OutStockInforListStore.setBaseParam("year", new Date().format('Y'));
	OutStockInforListStore.load({params:{start:0}});
	Ext.ffc.ResizeManager.addResizeObject(select_quotations_win);
});

