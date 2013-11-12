Ext.onReady(function(){
var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);

Ext.ffc.TempleteComboBox = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
//1 普通报价单，2 项目报价单，3  预定报价单，4  试刀报价，5  合同，6  合同订单，7  预定订单，8  试刀订单，9  加工订单，10  预定加工，11 试刀加工，12  出库单，13 合同交货单 14 预定交货 15 试刀交货
		this.store = new Ext.data.SimpleStore({
						fields : ['value','templeteType'],
						data : [
							[-1,'全部'],
							[1,'普通报价单'],
							[2,'项目报价单'],
							[3,'预定报价单'],
							[4,'试刀报价单'],
							[0,'-------------'],
							[5,'销售合同'],
							[6,'合同订单'],
							[7,'预定订单'],
							[8,'试刀订单'],
						    [20,'产品储备订单'],
							[21,'材料储备订单'],	
							[0,'-------------'],
							[9,'加工订单'],
							[10,'预定加工'],
							[11,'试刀加工'],
							[0,'-------------'],
							[16,'合同入库'],
							[30,'储备入库'],
							[17,'预订入库'],
							[18,'试刀入库'],
							[19,'直接入库'],
							[0,'-------------'],
							[12,'合同出库单'],
							[22,'预订出库单'],
							[24,'试刀出库单'],
							[25,'直接出库单'],
							[23,'原材料出库单'],
							[0,'-------------'],
							[13,'合同交货单'],
							[14,'预定交货单'],
							[15,'试刀交货单'],
							[0,'-------------'],
							[29,'报价产品明细'],
							[31,'合同产品明细'],
							[32,'采购订单产品明细'],
							[33,'加工订单产品明细'],
							[26,'入库产品明细'],
							[27,'出库产品明细'],
							[28,'交货产品明细']
						]
					});
		Ext.ffc.TempleteComboBox.superclass.constructor.call(this, {
					fieldLabel : '模板类型',
					valueField : 'value',
					hiddenName : 'templeteType',
					width:100,
					mode : 'local',
					displayField : 'templeteType',
					readOnly : true,
					frame : true,
					triggerAction : 'all',
					store : this.store
		})
	}
})

 var NotepadEditWindow = Ext.extend(Ext.Window, {  
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		var temp = new Ext.FormPanel({
				labelWidth: 75,
				frame:true,
				bodyStyle:'padding:5px 5px 0',
				width: 350,
				defaults: {width: 230},
				defaultType: 'textfield',
				items: [{
					xtype: 'hidden',
					name : 'id',
					value: _cfg.defData ? _cfg.defData.id : ''
				},{
						fieldLabel: '模板名称',
						name: 'templeteName',
						value: _cfg.defData ? _cfg.defData.templeteName : ''
					},new Ext.ffc.TempleteComboBox({value: _cfg.defData ? _cfg.defData.templeteType : ''}),{
						fieldLabel: '备注',
						xtype: 'textarea' ,
						name: 'memo',
						height: 135,
						value: _cfg.defData ? _cfg.defData.memo : ''
					}
				],
				buttons: [{
					text: '保 存',
					handler : function() {
						var vs = temp.getForm().getValues();
						var params = 'updateTemplete';
						if(Ext.isEmpty(vs.id)){
							params = 'addTemplete';
						}
						var ttt = this;
						Ext.Ajax.request({
								method: "post",
								params: {'expTemplete': Ext.encode(vs),m:params},
								url: PATH + '/dataExp/ExpTempleteAction.do',
								success: function(response){
									Ext.MessageBox.alert('系统提示', '保存成功.', function(){});
									ttt.close();
								}
						});
					},scope:this
				},{
					text: '取 消',
					handler : function() {
						this.close();
					},scope:this
				}]
			});
		this.items = temp;
		NotepadEditWindow.superclass.constructor.call(this, {
			closable:true,
			width:360,
			height:250,
			layout: 'fit'
		});
	}
});

	var contractListStore = new Ext.data.Store({
	   remoteSort : true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/dataExp/ExpTempleteAction.do?m=selectTemplete&limit=' + PAGESIZE }), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'templeteName',mapping:'templeteName',type:'string'},
        {name: 'memo',mapping:'memo',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editTimeString',mapping:'editTimeString',type:'string'},
		{name: 'templeteType',mapping:'templeteType',type:'string'}
     ])
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
						{xtype:'label',text: '模板名称',x:0,y:5,style:lableStyle_},
						{xtype:'textfield',  name: 'templeteName',x:50,y:3,width:140},
						{xtype:'label',text: '模板类型',x:195,y:5,style:lableStyle_},
						new Ext.ffc.TempleteComboBox({x:245,y:3,width:140}),
						{xtype:'label',text: '备注',x:395,y:5,style:lableStyle_},
						{xtype:'textfield',  name: 'memo',x:445,y:3,width:140},
						{xtype:'label',text: '编制时间',x:595,y:5,style:lableStyle_},
						{xtype:'datefield',name: 'startDate',x:645,y:3, format:'Y-m-d',width:140},
						{xtype:'label',text: '至',x:795,y:5,style:lableStyle_},
						{xtype:'datefield',name: 'endDate',x:825,y:3, format:'Y-m-d',width:140},
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
								var f = selectForm2.getForm();
								f.setValues({title:'',memo:'',startDate:'',endDate:''});
							}
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

    var grid = new Ext.grid.GridPanel({
        ds : contractListStore,
        store: contractListStore,
		sm:gridCheckSele,
		frame:true,
		layout: 'fit',
        columns: [
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "模板名称", width: 180, sortable: true,  dataIndex: 'templeteName'},
            {header: "模板类型", width: 180, sortable: true,  dataIndex: 'templeteType',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
							var arr = [
							[-1,'未定义'],
							[1,'普通报价单'],
							[2,'项目报价单'],
							[3,'预定报价单'],
							[4,'试刀报价单'],
							[5,'销售合同'],
							[6,'合同订单'],
							[7,'预定订单'],
							[8,'试刀订单'],
							[9,'加工订单'],
							[10,'预定加工'],
							[11,'试刀加工'],
							[12,'出库单'],
							[13,'合同交货单'],
							[14,'预定交货单'],
							[15,'试刀交货单'],
							[16,'合同入库'],
							[17,'预订入库'],
							[18,'试刀入库'],
							[19,'直接入库'],
							[20,'产品采购订单'],
							[21,'材料储备订单'],
							[22,'预订出库单'],
							[23,'原材料出库单'],
							[24,'试刀出库单'],
							[25,'直接出库单'],
							[26,'入库产品明细'],
							[27,'出库产品明细'],
							[28,'交货产品明细'],
						    [30,'储备入库'],
							[29,'报价产品明细'],
							[31,'合同产品明细'],
							[32,'采购订单产品明细'],
							[33,'加工订单产品明细']
						];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
				}
			},
			{header: "编制人", width: 75, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 120, sortable: true, dataIndex: 'editTimeString'},
			{header: "备注", width: 180, sortable: true,  dataIndex: 'memo'},
			{header: "合同附件", width: 60,  dataIndex: 'fileCount',
				renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = null;
					if(value <= 0){
					    str = '<a href="#">未上传</a>';
					}else{
					    str = '<a href="javascript:Ext.ffc.showSlaveMyWindow({id:\'' + record.data.id + '\',busType:50})">查看</a>';
					}
					return str;
				}	
			}
        ],
		tbar:[{
        		text:'新增',
				iconCls : 'icon-add',
				listeners : {
					'click' : function() {
						new NotepadEditWindow({
						    listeners : {
							    'close' : function(){
									contractListStore.reload();
								}
							}
						}).show();
					}
				}
        	},{
				xtype:'tbseparator'
			},{
        		text:'修改',
				iconCls : 'icon-modify',
				listeners : {
					'click' : function() {
					    var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要修改的数据!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "只能选择一条数据进行修改操作!");
								return;
							}
							
						new NotepadEditWindow({defData:arr[0].data,
						 listeners : {
							    'close' : function(){
									contractListStore.reload();
								},scope:this
							}	
						}).show();
					}
				}
        	},{
				xtype:'tbseparator'
			},{
        		text:'删除',
				iconCls : 'icon-delete',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
						if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要删除的数据!");
								return;
						}
						var ids = [];
						for(var i = 0;i < arr.length ;i++ ){
							ids.push(arr[i].id);
						}
					    Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中的数据!', function(btn){
							if(btn != 'yes'){return ;}
							Ext.Ajax.request({
								method: "post",
								params: {'ids': ids,m:'deleteTemplete'},
								url: PATH + '/dataExp/ExpTempleteAction.do',
								success: function(response){
									Ext.MessageBox.alert('系统提示', '删除成功.', function(){});
									contractListStore.reload();
								}
							});
						});
					}
				}
        	},{
				xtype:'tbseparator'
			},{
        		text:'上传附件',
				iconCls : 'icon-add',
				listeners : {
					'click' : function() {
						var arr = gridCheckSele.getSelections();
							if(arr.length <= 0){
								Ext.Msg.alert("消息", "请选择要上传附件的数据!");
								return;
							}
							if(arr.length > 1){
								Ext.Msg.alert("消息", "请选择一条数据，进行上传附件操作!");
								return ;
							}
						var data = arr[0].data;
						Ext.ffc.showSlaveMyWindow({id:data.id,busType:50});
					}
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
	select_quotations_win.render('excel_templete_list_');

	contractListStore.load({params:{start:0}});

});