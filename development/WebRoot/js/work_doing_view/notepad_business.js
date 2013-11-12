Ext.onReady(function(){

	Ext.ffc.NotepadEditWindow = Ext.extend(Ext.Window, {  
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
					value: '10'
				},{
						fieldLabel: '描述',
						xtype: 'textarea' ,
						name: 'memo',
						height: 135,
						value: _cfg.defData ? _cfg.defData.memo : ''
					},{
						fieldLabel: '提醒日期',
						name: 'txDate',
						xtype: 'datefield',
						format:'Y-m-d',
						value: _cfg.defData ? new Date(_cfg.defData.editTime.time).format('Y-m-d') : ''
					},{
						fieldLabel: '提醒时间',
						name: 'txTime',
						xtype: 'timefield',
						format:'H:i',
						minValue: '08:00',
					    maxValue: '20:00',
						value: _cfg.defData ? new Date(_cfg.defData.editTime.time).format('H:i') : ''
					}
				],
				buttons: [{
					text: '保 存',
					handler : function() {
						var vs = temp.getForm().getValues();
						if(vs.txDate == ''){
							Ext.MessageBox.alert('系统提示', '请设置提醒日期.', function(){});
						    return;
						}
						if(vs.txTime == ''){
						    Ext.MessageBox.alert('系统提示', '请设置提醒时间.', function(){});
						    return;
						}
						vs['editTime'] = vs.txDate + " " + vs.txTime;
						var params = 'updateNoticeNotepad';
						if(Ext.isEmpty(vs.id)){
							params = 'addNoticeNotepad';
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
		Ext.ffc.NotepadEditWindow.superclass.constructor.call(this, {
			closable:true,
			width:360,
			height:290,
			layout: 'fit'
		});
	}
});


	var contractListStore = new Ext.data.Store({
	   remoteSort : true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/notepad/NotepadAction.do?m=notepadList&limit=20&typeId=10' }), 
	   reader: new Ext.data.JsonReader({
       root: 'items',  
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'title',mapping:'title',type:'string'},
        {name: 'memo',mapping:'memo',type:'string'},
		{name: 'userName',mapping:'userName',type:'string'},
		{name: 'editTime',mapping:'editTime'},
		{name: 'editTimeString',mapping:'editTimeString',type:'string'}
     ])
	});

	var gridCheckSele = new Ext.grid.CheckboxSelectionModel();
   // var toolbar = new Ext.Toolbar({height:30});

	 var _grid = new Ext.grid.GridPanel({
		 id : 'notepadGrid',
		 ds : contractListStore,
         store: contractListStore,
		 sm:gridCheckSele,
		 layout:'fit',
	     height:407,
		 columns: [
			new Ext.grid.RowNumberer(),//自动行号
			gridCheckSele,
            {id:'id',header: "id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "描述", width: 110,   dataIndex: 'memo'},
			{header: "提醒时间", width: 110, dataIndex: 'editTime',hidden:true},
			{header: "提醒时间", width: 70, dataIndex: 'editTimeString'}
		 ],
		 viewConfig: {
             forceFit: true
		 }
	});
	contractListStore.load();

	_grid.render('_notepad_business_div');
	//toolbar.add();
	
	
	
	//var b= true;
	var noticeTask = {
		run: function(){
			//if(b){toolbar.doLayout();b=!b;}
			contractListStore.each(function(rec){
				var currDate = Ext.util.Format.date(new Date(),'Y-m-d H:i');
				var noticeDate = Ext.util.Format.date(new Date(rec.data.editTime.time),'Y-m-d H:i');
				var winId = 'win' + rec.data.id;
				var winCmp = Ext.getCmp(winId);
			    if(currDate >= noticeDate && !winCmp){
				    var win=new Ext.Window({
						id:winId,
						noticeId:rec.data.id,
						constrainHeader : true,
						title:'系统提醒',
						width:290,
						height:170,
						closeAction:'close',
						constrain:false,
						html : rec.data.memo,
						resizable :true,
						autoScroll :true, 
						bbar:[{xtype:'label',html: "<input type='checkbox' ><span style=\"color:green\">&nbsp;不再提醒</span>"}],
						listeners : {
							'beforeclose' : function(win){
								var ck = Ext.DomQuery.selectNode('input:checked',win.getEl().dom);
								var ids = [];
								ids.push(win.noticeId);
								if(ck){
								    Ext.Ajax.request({
										method: "post",
										params: {'ids': ids,m:'deleteNotepad'},
										url: PATH + '/notepad/NotepadAction.do',
										success: function(response){
											contractListStore.reload();
										}
									});
								}
							},scope:this
						}	
					});
					win.show(); 
				}
			});
		},
		interval: 10000 //1 second
	}
	Ext.TaskMgr.start(noticeTask);
 });
