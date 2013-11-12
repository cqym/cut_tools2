<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String notepadType = request.getParameter("notepadType");
%>
<script type="text/javascript" src="<%=path%>/js/notepad/notepad_list_view.js"></script>

<div id="notepad_list_<%=notepadType%>"></div>
<SCRIPT LANGUAGE="JavaScript">
<!--
Ext.onReady(function(){
var PAGESIZE = parseInt((Ext.getBody().getHeight()-270)/24);
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
					xtype: 'hidden',
					name : 'typeId',
					value: <%=notepadType%>
				},{
						fieldLabel: '标题',
						name: 'title',
						value: _cfg.defData ? _cfg.defData.title : ''
					},{
						fieldLabel: '描述',
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
						var params = 'updateNotepad';
						if(Ext.isEmpty(vs.id)){
							params = 'addNotepad';
						}
						var ttt = this;
						Ext.Ajax.request({
								method: "post",
								params: {'notepad': Ext.encode(vs),m:params},
								url: PATH + '/notepad/NotepadAction.do',
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
       proxy: new Ext.data.HttpProxy({url:PATH + '/notepad/NotepadAction.do?m=notepadList&limit=' + PAGESIZE + '&typeId=<%=notepadType%>' }), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'title',mapping:'title',type:'string'},
        {name: 'memo',mapping:'memo',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editTimeString',mapping:'editTimeString',type:'string'}
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
						{xtype:'label',text: '标题',x:0,y:5,style:lableStyle_},
						{xtype:'textfield',  name: 'title',x:50,y:3,width:140},
						{xtype:'label',text: '描述',x:195,y:5,style:lableStyle_},
						{xtype:'textfield',  name: 'memo',x:245,y:3,width:140},
						{xtype:'label',text: '编制时间',x:395,y:5,style:lableStyle_},
						{xtype:'datefield',name: 'startDate',x:445,y:3, format:'Y-m-d',width:140},
						{xtype:'label',text: '至',x:595,y:5,style:lableStyle_},
						{xtype:'datefield',name: 'endDate',x:625,y:3, format:'Y-m-d',width:140},
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
            {header: "标题", width: 180, sortable: true,  dataIndex: 'title'},
            {header: "描述", width: 180, sortable: true,  dataIndex: 'memo'},
			{header: "编制人", width: 75, sortable: true, dataIndex: 'userName'},
			{header: "编制时间", width: 120, sortable: true, dataIndex: 'editTimeString'},
			{header: "附件", width: 75, dataIndex: 'ss',
			     renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var str = null;
					//if(value <= 0){
					    str = '<a href="#">未上传</a>';
					//}else{
					    str = '<a href="javascript:Ext.ffc.showSlaveFillWindow({id:\'' + record.data.id + '\',busType:12})">查看</a>';
					//}
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
							Ext.Ajax.request({
								method: "post",
								params: {'ids': ids,m:'deleteNotepad'},
								url: PATH + '/notepad/NotepadAction.do',
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
						showSlaveFillWindow({id:data.id,busType:12});
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
	select_quotations_win.render('notepad_list_<%=notepadType%>');

	contractListStore.load({params:{start:0}});

});

//-->
</SCRIPT>