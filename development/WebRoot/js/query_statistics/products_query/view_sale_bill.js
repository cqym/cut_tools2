Ext.ffc.viewSaleBill = function (param){

//搜索条件 start
var statusCombox = new StatusCombox();
	statusCombox.setValue("提交合同");
	statusCombox.disabled = true;
var	searchForm = new Ext.FormPanel({
		width : 1000,
        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;', border : false,
        frame:true,labelWidth:70,monitorValid:false,
        items:[
           {layout:'column',border:false,labelSeparator:':',frame : true,
           defaults:{layout: 'form',border:false,columnWidth:.3},
           bbar : ['->',{
		           		text : "搜  索",
		           		iconCls:'icon-search',
		           		handler : function() {
							var store = grid.getStore();
							var seachParams = searchForm.getForm().getValues();
							for(var i in seachParams){
								store.setBaseParam(i, seachParams[i]);
							}
							store.load();
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
              {items: [{xtype:'textfield',fieldLabel: '牌号',name: 'brandCode',value:param.brandCode,anchor:'90%'}]},
			  {items: [{xtype:'textfield',fieldLabel: '客户',name: 'customerName',anchor:'90%'}]},
              {items: [new Ext.ffc.YearComboBox({anchor:'90%'})]},
              {items: [{xtype:'datefield',name: 'startTime', format:'Y-m-d', fieldLabel: '日期起',emptyText:'',anchor:'90%'}]},
              {items: [{xtype:'datefield',name: 'endTime', format:'Y-m-d', fieldLabel: '至',emptyText:'',anchor:'90%'}]}
           ]//items
          }
        ]//items
   })//FormPanel 
//搜索条件 end
Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	var sm = new Ext.grid.CheckboxSelectionModel();//复选框
	// 定义一个ColumnModel
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
		            {
						header : '货品名称',
						dataIndex : 'productName'
					}, {
						header : '货品编号',
						dataIndex : 'productCode'
					}, {
						header : '牌号',
						dataIndex : 'brandCode'
					}, {
						header : '历史面价',
						dataIndex : 'price'
						
					}, {
						header : '折扣',
						dataIndex : 'rebate'
						
					}, {
						header : '净价',
						dataIndex : 'netPrice'
						
					}, {
						header : '编制人',
						dataIndex : 'userName'
						
					}, {
						header : '客户名称',
						dataIndex : 'customerName'
					}, {
						header : '报价单编号',
						dataIndex : 'quotationCode',
						width : 150
					}, {
						header : '编制时间',
						dataIndex : 'editDate',
						width : 180,
						renderer:function(value){
							return Ext.util.Format.date(new Date(value.time),'Y-m-d');
						}
					}]);
	
	/**
	 * DS报价单数据源，使用HttpProxy从服务器取得数据，用JsonReader解析
	 */
	var ds = new Ext.data.JsonStore({
		url : PATH + '/querystatistics/ProductsQueryAction.do?m=productsSaleBillsQueryList&toolsId=' + param.toolsId,
		root : 'items',
		totalProperty : 'totalCount',
		//autoLoad : true,
		fields : ['productName', 'productCode', 'brandCode',
					'price', 'rebate', 'netPrice', 'userName',
					'customerName', 'editDate','quotationCode', 'id']
	});

	var gv = new Ext.grid.GridView({
        	//forceFit:true,
            //autoFill :true,
            deferEmptyText : false,
            emptyText : '无报价信息！'
        });
var grid = new Ext.grid.GridPanel({
	    width : 1000,
		bodyStyle:'width:100%',
		height : 300,
		//autoHeight : true,
		enableHdMenu : false,
		border : false,
		stripeRows : true,
		//el : 'quogrid',
        view : gv,
		ds : ds,
		cm : cm,
		sm:sm,
		bbar : new Ext.PagingToolbar({
				pageSize : 15,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : ds
			})
});

    var select_quotations_win = new Ext.Window({
            layout: 'border',
			title: '历史报价记录',
			width : Ext.getBody().getWidth(),
			height:495,
			maximizable :true,
			modal : true,
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
                //contentEl: 'south',
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
				layout:'fit',
                //contentEl: 'quogrid',
                split: true,
                height: 100,
                minSize: 100,
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 5 5 5',
                items : [grid]
            }],
			buttons: [{
				text : "关   闭",
				handler : function() {
					
					select_quotations_win.close();
				}
	         }]
        });
		select_quotations_win.show(this);

		var obj2 = searchForm.getForm().getValues();
			
		var seachParams = {start : 0, limit : 15,searchStr:Ext.encode({data:obj2})};
		for(var i in seachParams){
				grid.getStore().setBaseParam(i, seachParams[i]);
		}
		grid.getStore().load();
}


