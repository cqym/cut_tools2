Ext.BLANK_IMAGE_URL = PATH + "/extjs/resources/images/default/s.gif";

cut_tools.contract_account.index_grid = null;    
Ext.onReady(function(){


	var record = Ext.data.Record.create([
	    {name: 'contractCode'},
	    {name: 'customerName'},
	    {name: 'startDate'},
		{name: 'endDate'},
		{name: 'status'}
	]);
 
	var orderparmRecord = new record();


	var proxy = new Ext.data.HttpProxy({url: PATH + "/contract/contractAccountsAction.do?ffc=viewContractAccountList"});

	var Bill = Ext.data.Record.create([
									   {name: 'id', type: 'string',mapping:"id"},
	                                   {name: 'contractInforId', type: 'string',mapping:"contractInforId"},
	                                   {name: 'contractCode', type: 'string',mapping:"contractCode"},
	                                   {name: 'customerCode', type: 'string',mapping:"customerCode"},
	                                   {name: 'customerName', type: 'string',mapping:"customerName"},
	                                   {name: 'memo', type: 'string',mapping:"memo"},
	                                   {name: 'editDate', type: 'string',mapping:"editDateStr"},
	                                   {name: 'status', type: 'int',mapping:"status"},
									   {name: 'contractMoney', type: 'string',mapping:"contractMoney"},
	                       			   {name: 'money', type: 'string',mapping:"money"},
									   {name: 'userId', type: 'string',mapping:"userId"},
	                                   {name: "userName", type: "string", mapping: "userName"}
	]);
	var reader = new Ext.data.JsonReader({ totalProperty: "totalCount",root: "items"}, Bill);
	var store = new Ext.data.Store({
		proxy: proxy,
		reader: reader
	});

	store.load({params: {start: 0, limit: 20}});
	var cm = new Ext.grid.ColumnModel([
	    new Ext.grid.RowNumberer(),
	    {header: "id",dataIndex: "id",width: 200,hidden:true},
		{header: "合同信_主键",dataIndex: 'contractInforId',width: 200,hidden:true},
		{header: "合同编号",dataIndex: 'contractCode',width: 200,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
		{header: "客户编号",dataIndex: 'customerCode', width: 200},
		{header: "客户名称",dataIndex: 'customerName',width: 100,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){return '<span>' + value + "</span>";}},
		{header: "合同金额",dataIndex: 'contractMoney',width: 100},
		{header: "回款金额",dataIndex: 'money',width: 100},
		{header: "状态",dataIndex: 'status',width: 100,renderer:orderStatus},
		{header: "编制时间",dataIndex: 'editDate',width: 100},	
		{header: "编制人Id",dataIndex: 'userId',width: 100,hidden:true},
		{header: "编制人", dataIndex: "userName",width: 100},
		{header: "备注",dataIndex: 'memo',width: 100, id: "remark", 
		renderer: function(v){
			return Ext.util.Format.stripTags(v);
		}}
	]);
	
	function orderStatus(value)
	{
		if(value == 0)
		{
			return "编制";
		}
		else if(value == 1)
		{
			return "确认";
		}
	}
	
	cut_tools.contract_account.index_grid = new Ext.grid.GridPanel({
		//renderTo:'contractFSumGetMoney',
		width  : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight() - 50,
		store: store,
		cm: cm,
		autoExpandColumn: "remark",
			 tbar : [{
				text:'创建合同回款记录',
				tooltip: '创建合同回款记录',
				iconCls:'save-icon',
				listeners: {
					'click' : function(){
						var win = new cut_tools.contract_account.contract_win();
						win.show();
					}
				}
			},{
				xtype:'tbseparator'
			},{
				text:'修改合同回款记录',
				tooltip: '修改合同回款记录',
				iconCls:'save-icon',
				listeners: {
					'click' : function(){
						var selectedItem = cut_tools.contract_account.index_grid.getSelectionModel().getCount();
						var record  = cut_tools.contract_account.index_grid.getSelectionModel().getSelected();
							if (1!=selectedItem) {
								Ext.Msg.alert('系统提示', '请选择要修改的合同回款记录！');
								return;
							}
							var stu = record.get('status');
							if(stu != 0)
							{
								Ext.Msg.alert('系统提示', '该状态下合同回款记录不能被修改！');
								return;
							}
							var win = new cut_tools.contract_account._updatewin();
							win.nav2.updateGrid.on('afterrender',function(){
							  try
							  {
								  win.nav2.updateGrid.getForm().loadRecord(record);
							  }catch(error)
							  {
								  alert(error);
							  }
						  });
							 win.show();
						
					}
				}
			},{
				xtype:'tbseparator'
			},{
				text:'删除合同回款记录',
				tooltip: '删除合同回款记录',
				iconCls:'delete-icon',
				listeners: {
					'click' : function(){
						var selectedItem = cut_tools.contract_account.index_grid.getSelectionModel().getCount();					
							if (1!=selectedItem) {
								Ext.Msg.alert('系统提示', '请选择要删除的合同回款记录！');
								return;
							}
							var record = cut_tools.contract_account.index_grid.getSelectionModel().getSelected();
							var stu = record.get('status');
							if(stu != 0)
							{
								Ext.Msg.alert('系统提示', '该状态下合同回款记录不能被删除！');
								return;
							}
							var handleDelete = function (btn){
								var ds = cut_tools.contract_account.index_grid.getStore() 
								if(btn == 'ok') {	
									var _id = record.get('id');		
									Ext.Ajax.request({
										url: PATH + '/contract/contractAccountsAction.do?ffc=deleteContractAccount',
										params: { ids: _id },
										success : function(response) {
											var responseArray = Ext.util.JSON.decode(response.responseText); 
											 if(responseArray.success == true){
												Ext.Msg.show({
													title:'成功提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.INFO
												});
												ds.remove(record);
											 } else {
												Ext.Msg.show({
													title:'错误提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.ERROR
												});
											 } 
										},scope : this
									});
								
								}
							}
							Ext.MessageBox.show({
								title:'系统提示',
								msg: '请确认要删除合同回款记录!',
								buttons: Ext.MessageBox.OKCANCEL,
								fn: handleDelete
							});													
					}
				}
			},{
				xtype:'tbseparator'
			},{
				text:'确认合同回款记录',
				tooltip: '确认合同回款记录',
				iconCls:'save-icon',
				listeners: {
					'click' : function(){
						var selectedItem = cut_tools.contract_account.index_grid.getSelectionModel().getCount();					
							if (1!=selectedItem) {
								Ext.Msg.alert('系统提示', '请选择要确认的合同回款记录！');
								return;
							}
							var record = cut_tools.contract_account.index_grid.getSelectionModel().getSelected();
							var stu = record.get('status');
							if(stu != 0)
							{
								Ext.Msg.alert('系统提示', '该合同回款记录已经确认过！');
								return;
							}
						var handleDelete = function (btn){
								var ds = cut_tools.contract_account.index_grid.getStore() 
								if(btn == 'ok') {	
									var _id = record.get('id');		
									Ext.Ajax.request({
										url: PATH + '/contract/contractAccountsAction.do?ffc=confirmContractAccount',
										params: { ids: _id },
										success : function(response) {
											var responseArray = Ext.util.JSON.decode(response.responseText); 
											 if(responseArray.success == true){
												Ext.Msg.show({
													title:'成功提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.INFO
												});
												store.reload();
											 } else {
												Ext.Msg.show({
													title:'错误提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.ERROR
												});
											 } 
										},scope : this
									});
								
								}
							}
							Ext.MessageBox.show({
								title:'系统提示',
								msg: '是否确认该合同回款记录!',
								buttons: Ext.MessageBox.OKCANCEL,
								fn: handleDelete
							});											
					}
				},scope:this
			}],
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: 20,
			displayInfo: true,
			displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
			emptyMsg: "<i>没有数据</i>"
		})
	});	
		//搜索
	var bbar2 = new Ext.Toolbar({  
	    renderTo:cut_tools.contract_account.index_grid.tbar,
	    items:[new Ext.form.Label({
	    	html : "合同编号:&nbsp;"
	    
	    }), new Ext.form.TextField({
	    	name : 'contractCode',
	      	listeners : {
	      		'change' : function() {
	      			orderparmRecord.contractCode = this.getValue();
	      		}
	      	}
	    
	    }), '-', new Ext.form.Label({
	    	html : "客户名称:&nbsp;"
	    
	    }), new Ext.form.TextField({
	    	name : 'customerName',
	      	listeners : {
	      		'change' : function() {
	      			orderparmRecord.customerName = this.getValue();
	      		}
	      	}
	    
	    }),'-', new Ext.form.Label({
	    	html : "编制日期:&nbsp;"
	    
	    }),new Ext.form.DateField({
        name: 'startDate',
        format:'Y-m-d',
		selectOnFocus:true,
		listeners : {
			'change' : function() {
				orderparmRecord.startDate = this.getValue();
			}
		}
      }),'至',new Ext.form.DateField({
        name: 'endDate',
        format:'Y-m-d',
		selectOnFocus:true,
		listeners : {
			'change' : function() {
				orderparmRecord.endDate = this.getValue();
			}
		}
      }), new Ext.form.Label({
	    	html : "状态:&nbsp;"
	    }),  new Ext.form.ComboBox({
            fieldLabel: 'status',
            hiddenName:'status',
            store: new Ext.data.ArrayStore({
    	        fields: ['status','abbr'],
    	        data : [
    		            ['0', '编制'],
    		            ['1', '确认']
    		           ]
    	    }),
            valueField:'status',
            displayField:'abbr',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText:'选择状态',
            selectOnFocus:true,
			readOnly:true,
			listeners : {
				'change' : function() {
					orderparmRecord.status = this.getValue();
				}
			}
        }),'-',{  
				text:'搜索'  
				,iconCls:'icon-key',
				listeners : {
					'click' : function(){
						var _searchStr = Ext.util.JSON.encode(orderparmRecord);

						store.baseParams = {searchStr:_searchStr};
						store.reload();
					}
				}
				
			}, '-'  
	    ]  
	});
	
	cut_tools.contract_account.index_grid.render('contractFSumGetMoney');
})
