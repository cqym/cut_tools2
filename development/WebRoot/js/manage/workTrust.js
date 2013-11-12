function onItemWorkTrust(item, pressed){
var usersStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/worktrust/WorkTrustAction.do?m=getUserListWorkTrust'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'authorizePersonId',mapping:'authorizePersonId',type:'string'},
        {name: 'authorizePerson',mapping:'authorizePerson',type:'string'},
		{name: 'getRightPersonId',mapping:'getRightPersonId',type:'string'},
		{name: 'getRightPerson',mapping:'getRightPerson',type:'string'}
     ])
	});

    var usersGrid = new Ext.grid.GridPanel({
        //id:"grid",
       // el:"example-grid",
        ds : usersStore,
        store: usersStore,
		split: true,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
			  {header: "用户名", width: 80, dataIndex: 'authorizePerson', sortable: true},
			  {header: "委托情况", width: 200, dataIndex: 'getRightPerson', sortable: true,
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					  if(value != null && value != ''){
					      return '已委托给[<span style="color:red">' + value + '</span>]处理';
					  }
					  return "";
				  }
			  }
     ],
       width:500,
       height:600,
	   region: 'west',
	   selModel : new  Ext.grid.RowSelectionModel({singleSelect : true}),
	   bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: usersStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"
        }),
	   buttons: [{
            text: '工作委托',
			width:50,
		    handler:function(){ 
				var selectedm = usersGrid.getSelectionModel();
				var userRecord = selectedm.getSelected();  
				if(!userRecord){
					Ext.MessageBox.alert('系统提示', '请选择用户!', function(){});
					return ;
				}

				if(userRecord.get('getRightPersonId') != null && userRecord.get('getRightPersonId') != ''){return ;}
				Ext.ffc.selectUser(function(record){
						var userId = record.get('id');
						var trueName = record.get('trueName');
						Ext.MessageBox.confirm('系统提示', '请确认是否要将审批工作委托给' + trueName + '!', function(btn){
							if(btn != 'yes'){return ;}
								Ext.Ajax.request({
									method: "post",
									params: { m:'grandRight','getRightUserId' : userId,'authorUserId' : userRecord.get('authorizePersonId')},
									url: PATH + "/manage/worktrust/WorkTrustAction.do",
									success: function(response){
										eval("var temp = " + response.responseText);
										if(temp.msg == null || temp.msg == 'null'){
										    Ext.Msg.alert("消息", "您已经将工作委托给" + trueName + "！");
										    usersGrid.getStore().reload();
										}else{
										    Ext.Msg.alert("消息", temp.msg);
										}
									}
								});
					   });	
				});
			},scope:this
        },{
            text: '收回委托',
			width:50,
		    handler:function(){ 
				var selectedm = usersGrid.getSelectionModel();
				var userRecord = selectedm.getSelected();  
				if(!userRecord){
					Ext.MessageBox.alert('系统提示', '请选择用户!', function(){});
					return ;
				}
				if(userRecord.get('getRightPersonId') == null || userRecord.get('getRightPersonId') == ''){return ;}
				Ext.MessageBox.confirm('系统提示', '请确认是否要收回工作委托!', function(btn){
					if(btn != 'yes'){return ;}
						Ext.Ajax.request({
							method: "post",
							params: { m:'cancelRight','authorUserId' : userRecord.get('authorizePersonId')},
							url: PATH + "/manage/worktrust/WorkTrustAction.do",
							success: function(response){
								Ext.Msg.alert("消息", "您已经将[" + userRecord.get("authorizePerson") + "]工作委托收回！");
								usersGrid.getStore().reload();
							}
						});
				});	
			},scope:this
        }],
		listeners: {
			'rowclick': function(obj, rowIndex, evt){
				var row = obj.getStore().getAt(rowIndex);
				var userId = row.get("authorizePersonId");
				rolesStore.load({params:{start:0,limit:20,userId:userId}});
			}
	    }
    });
	usersStore.load({params:{start:0,limit:14}});

		var rolesStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/worktrust/WorkTrustAction.do?m=getUserWorkTrustHistory'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   successProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'authorizePersonId',mapping:'authorizePersonId',type:'string'},
        {name: 'authorizePerson',mapping:'authorizePerson',type:'string'},
		{name: 'getRightPersonId',mapping:'getRightPersonId',type:'string'},
		{name: 'getRightPerson',mapping:'getRightPerson',type:'string'},
		{name: 'opType',mapping:'opType',type:'float'},
		{name: 'editDateString',mapping:'editDateString',type:'string'},
		{name: 'opPerson',mapping:'opPerson',type:'string'}
     ])
	});
	//gridCheckSele.singleSelect = true;
    var sysRolesGrid = new Ext.grid.GridPanel({
       // id:"grid2",
       // el:"example-grid",
	    split: true,
        ds : rolesStore,
		
        store: rolesStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: true,hidden:true},
			  {header: "操作类型", width: 80, dataIndex: 'opType',
				 renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					  if(value * 1== 0 ){
					      return '当前委托';
					  }
					  if(value * 1== 1 ){
					      return '收回委托';
					  }
					  if(value * 1== 2 ){
					      return '历史委托';
					  }
					  return "";
				  }}, 
              {header: "接受人", width: 80, dataIndex: 'getRightPerson'},
              {header: "操作人", width: 80, dataIndex: 'opPerson'}, 
			  {header: "操作时间", width: 150, dataIndex: 'editDateString'} 
     ],bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: rolesStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"
        }),
       width:360,
       height:600,
	   region: 'center'
    });
	rolesStore.load();

			var win = new Ext.Window({
				title: '工作委托管理',
				closable:true,
				width:980,
				height:450,
				plain:true,
                layout: 'border',
			    modal : true,
				maximizable :true,
				items: [usersGrid,sysRolesGrid]
			});
			win.show(this);
			//grid.render();

    }
