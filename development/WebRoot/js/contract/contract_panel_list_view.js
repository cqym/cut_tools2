Ext.onReady(function(){
	var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
	var contractListStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/contract/contractInforsViewPanel.do?ffc=contractList'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'contractCode',mapping:'contractCode',type:'string'},
        {name: 'customerName',mapping:'customerName',type:'string'},
		{name: 'productMoney',mapping:'productMoney',type:'string'},
		{name: 'taxRate',mapping:'taxRate',type:'string'},
		{name: 'taxMoney',mapping:'taxMoney',type:'string'},
	    {name: 'totalMoney',mapping:'totalMoney',type:'string'},
		{name: 'overallRebate',mapping:'overallRebate',type:'string'},
		{name: 'finalMoney',mapping:'finalMoney',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'status',mapping:'status',type:'string'},
		{name: 'contractAmount',mapping:'contractAmount',type:'float'},
		{name: 'deliveryAmount',mapping:'deliveryAmount',type:'float'},
		{name: 'orderAmount',mapping:'orderAmount',type:'float'},
		{name: 'outAmount',mapping:'outAmount',type:'float'},
		{name: 'tqOutAmount',mapping:'tqOutAmount',type:'float'},
		{name: 'money',mapping:'money',type:'float'}
			
     ])
	});


var  selectForm2 = new Ext.FormPanel({
                        labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:55,monitorValid:false,border:false,
                        items:[
                            {layout:'column',border:false,labelSeparator:':',
                            defaults:{layout: 'form',border:false,columnWidth:.15},
                            items:[
                                {border:false,items: [{xtype:'textfield',fieldLabel: '合同编号',name: 'contractCode',anchor:'100%'}]},
                                {border:false,items: [{xtype:'textfield',fieldLabel: '客户名称',name: 'customerName',anchor:'100%'}]},
                                {border:false,items: [new Ext.ffc.ContractStatusComboBox()]},
                                {border:false,items: [{xtype:'datefield',fieldLabel: '编制时间',name: 'startDate',anchor:'100%', format:'Y-m-d'}]},
								{border:false,items: [{xtype:'datefield',fieldLabel: '至',name: 'endDate',anchor:'100%', format:'Y-m-d'}]}
								
                                ],//items
								bbar : ['->',{xtype:'button',text: '搜    索',name: 'seachBt',width:80,
									handler : function() {
										var seachParams = selectForm2.getForm().getValues();
										contractListStore.reload({params:seachParams});
									}
								}]
                                }
                        ]//items
                    });//FormPanel

var gridCheckSele = new Ext.grid.CheckboxSelectionModel();

    var grid = new Ext.grid.GridPanel({
        ds : contractListStore,
        store: contractListStore,
		sm:gridCheckSele,
		frame:true,
        columns: [new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "合同id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "合同编号", width: 160, sortable: true,  dataIndex: 'contractCode'},
            {header: "客户名称", width: 155,   dataIndex: 'customerName'},
			{header: "最终金额", width: 100, sortable: true, dataIndex: 'finalMoney'},
			{header: "编制人", width: 75, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 120, sortable: true, dataIndex: 'editDateString'},
			{header: "状态", width: 75, sortable: true, dataIndex: 'status',
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
			{header: "审批信息", width: 75,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
					return str;
				}
			},{header: "采购情况", width: 150,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var rowData = record.data;
					var cAmount = rowData.contractAmount * 1;
					var orderAmount = rowData.orderAmount * 1;
					var bgOrderAmount = (cAmount - rowData.tqOutAmount * 1);//合同数量-已提取数量 = 应该订货数量
					bgOrderAmount = bgOrderAmount == 0 ? 1 : bgOrderAmount;
					
					var bfb =  orderAmount / bgOrderAmount;
					bfb = bfb * 100;
					bfb = bfb.toFixed(2);
					var str = '<div style="background-color:#99CC00;width:' + bfb + '%" title="' + bfb + '%">&nbsp</div>';
					return str;
				}
			},{header: "订单到货情况", width: 150,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var rowData = record.data;
					var orderAmount = rowData.orderAmount * 1;
					var tqOutAmount = rowData.tqOutAmount * 1;
					orderAmount = orderAmount == 0 ? 1 : orderAmount;
					var bfb = (rowData.outAmount * 1 - tqOutAmount) / orderAmount;
					bfb = bfb * 100;
					bfb = bfb.toFixed(2);
					var str = '<div style="background-color:#99CC00;width:' + bfb + '%" title="' + bfb + '%">&nbsp</div>';
					return str;
				}
			},{header: "总体到货情况", width: 150,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var rowData = record.data;
					var cAmount = rowData.contractAmount * 1;
					cAmount = cAmount == 0 ? 1 : cAmount;
					var bfb = (rowData.outAmount * 1) / cAmount;
					bfb = bfb * 100;
					bfb = bfb.toFixed(2);
					var str = '<div style="background-color:#99CC00;width:' + bfb + '%" title="' + bfb + '%">&nbsp</div>';
					return str;
				}
			},{header: "交货情况", width: 150,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var rowData = record.data;
					var cAmount = rowData.contractAmount * 1;
					cAmount = cAmount == 0 ? 1 : cAmount;
					var bfb = rowData.deliveryAmount * 1 / cAmount;
					bfb = bfb * 100;
					bfb = bfb.toFixed(2);
					var str = '<div style="background-color:#99CC00;width:' + bfb + '%" title="' + bfb + '%">&nbsp</div>';
					return str;
				}
			},{header: "回款情况", width: 150,  dataIndex: 'auditInfor',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var rowData = record.data;
					var finalMoney = rowData.finalMoney * 1;
					finalMoney = finalMoney == 0 ? 1 : finalMoney;
					
					var bfb = rowData.money * 1 / finalMoney;
					bfb = bfb * 100;
					bfb = bfb.toFixed(2);
					var str = '<div style="background-color:#99CC00;width:' + bfb + '%" title="' + bfb + '%">&nbsp</div>';
					return str;
				}
			}
        ],
        //stripeRows: true,
        height:460,
        width:750,
	tbar:[{
        		text:'查看合同',
				iconCls:'page-icon',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要查看的合同!");
								return;
							}
						var conId = arr[0].id;
						Ext.Ajax.request({
							method: "post",
							params: { id : conId},
							url: PATH + "/contract/contractViewAction.do?ffc=contractViewById",
							success: function(response){
									eval("var temp = " + response.responseText);
									var conEditWin = new Ext.ffc.ContractEditWindow({conctractInfor:temp,readOnly : true});
									conEditWin.show();
									//select_quotations_win.close();
							}
						});
					}
				}
        	}
		]
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
                //contentEl: 'south',
                split: true,
                //width: 200,
                height : 80,
                minSize: 140,
                maxSize: 300,
                collapsible: true,
                margins: '5 5 5 5',
                items : [selectForm2]
                
            }, {
                region: 'center',
                //contentEl: 'quogrid',
                split: true,
                //height: 10000,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 5 5 5',
				layout:"fit",
                items : [grid]
            }],
			bbar: new Ext.PagingToolbar({
            pageSize: PAGESIZE,
            store: contractListStore,
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

	select_quotations_win.render('contract_panel_list_');

	contractListStore.load({params:{start:0,limit:PAGESIZE}});

});

