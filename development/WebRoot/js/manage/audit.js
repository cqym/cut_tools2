function onItemAudit(item, pressed){
	///-------------user select start
	var usersStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/user/usersViewAction.do?ffc=getUsersByPage'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
         {name: 'id',mapping:'id',type:'string'},
        {name: 'userName',mapping:'userName',type:'string'},
        {name: 'trueName',mapping:'trueName',type:'string'},
		{name: 'departId',mapping:'departId',type:'string'},
		{name: 'departName',mapping:'departName',type:'string'},
		{name: 'regTimeString',mapping:'regTimeString',type:'string'}
     ])
	});

    var usersGrid = new Ext.grid.GridPanel({
        id:"grid",
       // el:"example-grid",
        ds : usersStore,
        store: usersStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
			  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
              {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
			  {header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
			  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true},
              {header: "创建时间", width: 200, dataIndex: 'regTimeString'}   
     ],
       width:500,
       height:450,
	   region: 'west',
	   bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: usersStore,
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
        }),
		listeners: {
	            'rowclick': function(obj, rowIndex, evt){
				},
				'rowdblclick' : function(grid, rowIndex,  e ) {
					var record = usersStore.getAt(rowIndex);
					if(record){
				      var r = auditInforDetailStore.getAt(auditInforDetailGrid['currentEditRowNum']);
					 //alert(record.get('id') + "," + record.get('userName'));
					   r.set("auditPersonId",record.get('id'));
					   r.set("auditPersonName",record.get('userName'));

					  // alert(r.get('auditPersonId'));
					}
					usersWin.hide();
				}
	    },
		buttons: [{
            text: '确定',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var selectedm = usersGrid.getSelectionModel();
				var record = selectedm.getSelected(); 
				 if(record){
				      var r = auditInforDetailStore.getAt(auditInforDetailGrid['currentEditRowNum']);
					   r.set("auditPersonId",record.get('id'));
					   r.set("auditPersonName",record.get('userName'));
				 }
				 usersWin.hide();
			}
        }]
    });
	usersStore.load({params:{start:0,limit:14}});
	var usersWin = new Ext.Window({
				title: '用户列表',
				closable:true,
				width:520,
				height:475,
				plain:true,
                layout: 'border',
				modal : true,
				items: [usersGrid]
			});
///-------------user select end
	var auditTypesStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditFlowDefinedViewAction.do?ffc=queryAuditTypes'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
		root: 'auditTypes',  // 
		successProperty :'totalCount'
     }, 
	 [ 
        {name: 'id',mapping:'id',type:'string'},
        {name: 'auditTypeName',mapping:'auditTypeName',type:'string'},
		{name: 'className',mapping:'className',type:'string'}
     ])
	});

    var auditTypeGrid = new Ext.grid.GridPanel({
        id:"auditTypeGrid",
       // el:"example-grid",
		title:'审批类型',
        ds : auditTypesStore,
        store: auditTypesStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "编号", width: 40, dataIndex: 'id', sortable: true},
              {header: "审批类型描述", width: 180, dataIndex: 'auditTypeName', sortable: true},
              {header: "类名", width: 0, dataIndex: 'className',hidden:true}   
     ],
       width:250,
       height:600,
	   region: 'west',
	   listeners: {
	            'rowclick': function(obj, rowIndex, evt){
						   var row = obj.getStore().getAt(rowIndex);
						   var t2 =  row.id;
						   auditInforStore.load({params:{auditType:t2}});
							auditInforDetailStore.removeAll();
				}
	    }
    });
	auditTypesStore.load();

	var auditInforStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditFlowDefinedViewAction.do?ffc=queryAuditInfors'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
		
     }, 
	 [ 
        {name: 'id',mapping:'id',type:'string'},
        {name: 'flowName',mapping:'flowName',type:'string'},
		{name: 'departId',mapping:'departId',type:'string'},
		{name: 'departName',mapping:'departName',type:'string'},
		{name: 'defineTimeStr',mapping:'defineTimeStr',type:'string'},
		{name: 'enable',mapping:'enable',type:'string'}
     ])
	});

var auditInforGrid = new Ext.grid.EditorGridPanel({
        id:"auditInforGrid",
       // el:"example-grid",
		title:'审批流程',
        ds : auditInforStore,
        store: auditInforStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 40, dataIndex: 'id', sortable: true,hidden:true},
              {header: "流程名称", width: 190, dataIndex: 'flowName', sortable: true,editor: new Ext.form.TextField({allowBlank: false})},
              {
				  header: "所属部门", 
				  width: 100, 
				  dataIndex: 'departName',
				  editor : new cms.form.ComboTree(
							{  loader: new Ext.tree.TreeLoader({
								allowBlank:false,
								dataUrl: PATH +'/manage/depart/departViewAction.do?ffc=query'      
								}),
								listeners : {
									blur:function(obj){
										if(auditInforGrid['currentEditRowNum'] != undefined){
										    var r = auditInforStore.getAt(auditInforGrid['currentEditRowNum']);
											r.set("departId",obj.hiddenField.value);
										}
									}
								}
							})
			  },
			  {header: "设定时间", width: 120, dataIndex: 'defineTimeStr'},
			  {header: "状态", 
				width: 80, dataIndex: 'enable',
				renderer:function(value){
							if(value=='1'){
							    return '有效';
							}else{
								return '无效';
							}
						},
			    editor:new Ext.form.ComboBox({typeAhead: true,
											triggerAction: 'all',
											lazyRender:true,
											mode: 'local',
											store: new Ext.data.ArrayStore({
												id: 0,
												fields: [
													'myId',
													'displayText'
												],
												data: [[0, '无效'], [1, '有效']]
											}),
											valueField: 'myId',
											displayField: 'displayText'
										})
		  }
     ],
       width:340,
       height:200,
	   region: 'north',
		tbar: [
		 {
            text: '保存',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){ 
				var selectedm = auditTypeGrid.getSelectionModel();
				var record = selectedm.getSelected();  
				var auditType = record.get('id');
				var count = auditInforStore.getCount();
				var jsonArray = [];
				
				for(var i = 0;i < count ;i++ ){
					var temp = {auditTypeId:auditType};
					Ext.apply(temp,auditInforStore.getAt(i).data);
					jsonArray.push(temp);
				}
				if(count > 0){
						Ext.Ajax.request({
							method: "post",
							params: { infors: Ext.util.JSON.encode(jsonArray)},
							url: PATH + "/manage/audit/auditFlDefdMgeAction.do?ffc=updateAuditFlowInfor",
							success: function(response){
								auditInforStore.load({params:{auditType:auditType}});
								Ext.Msg.alert("消息", "保存成功!");
							}
						});
					}
			}///--------------
        },{
            text: '增加审批流程',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var selectedm = auditTypeGrid.getSelectionModel();
				if(selectedm.selections == null){
				    Ext.Msg.alert("消息", "请选择审批类型!");
					return ;
				}
				var Plant = auditInforGrid.getStore().recordType;
                var p = new Plant({
                    enable: '1'
                });
                auditInforGrid.stopEditing();
                auditInforGrid.getStore().insert(0, p);
                auditInforGrid.startEditing(0, 0);
			}
        }],
		listeners: {
				'beforeedit':function(e){
					auditInforGrid['currentEditRowNum'] = e.row;
				},
				'rowclick': function(obj, rowIndex, evt){
				   var row = obj.getStore().getAt(rowIndex);
				   auditInforDetailStore.load({params:{inforId:row.id}});
				   auditContentCheckSele.clearSelections();
				}
		}
    });

var auditInforDetailStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditFlowDefinedViewAction.do?ffc=queryAuditInforDetails'}),//调用的动作 
	   reader: new Ext.data.JsonReader({}, 
	 [ 
        {name: 'id',mapping:'id',type:'string'},
        {name: 'flowInforId',mapping:'flowInforId',type:'string'},
		{name: 'auditOrder',mapping:'auditOrder',type:'string'},
		{name: 'auditDegree',mapping:'auditDegree',type:'string'},
		{name: 'auditPersonId',mapping:'auditPersonId',type:'string'},
		{name: 'auditPersonName',mapping:'auditPersonName',type:'string'},
		{name: 'roleId',mapping:'roleId',type:'string'}
     ])
	});
var gridCheckSele = new Ext.grid.CheckboxSelectionModel();
var auditInforDetailGrid = new Ext.grid.EditorGridPanel({
        id:"auditInforDetailGrid",
        collapsible: true,
	    split: true,
		title:'审批流程详细信息',
        ds : auditInforDetailStore,
        store: auditInforDetailStore,
	    sm:gridCheckSele,
        columns: [new Ext.grid.RowNumberer(),//自动行号
		      gridCheckSele,
              {header: "id", width: 40, dataIndex: 'id', sortable: true,hidden:true},
              {header: "审批人", width: 100, dataIndex: 'auditPersonName', sortable: true},
              {header: "审批级次", width: 70, dataIndex: 'auditOrder',
				  editor:new Ext.form.ComboBox({typeAhead: true,
												triggerAction: 'all',
												lazyRender:true,
												mode: 'local',
												store: new Ext.data.ArrayStore({
													id: 111110,
													fields: [
														'myId',
														'displayText'
													],
													data: [[1, '1'], [2, '2'],[3, '3'],[4, '4'],[5, '5'],[6, '6']]
												}),
												valueField: 'myId',
												displayField: 'displayText'
											})
			  },
			  {header: "审批度", width: 150, dataIndex: 'auditDegree',
			   renderer:function(value){
							if(value=='1'){
							    return '本审批人在同级中可不审';
							}else{
								return '本审批人在同级中必须审';
							}
				},editor:new Ext.form.ComboBox({typeAhead: true,
												triggerAction: 'all',
												lazyRender:true,
												mode: 'local',
												store: new Ext.data.ArrayStore({
													id: 11220,
													fields: [
														'myId',
														'displayText'
													],
													data: [[1, '在同级中可不审'], [0, '在同级中必须审']]
												}),
												valueField: 'myId',
												displayField: 'displayText'
											})
			  }
     ],
       width:240,
       height:200,
	   region: 'center',
	   tbar: [
		 {
            text: '保存',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){ 
				var selectedm = auditInforGrid.getSelectionModel();
				if(selectedm.selection == null){
				    Ext.Msg.alert("消息", "请选择审批流程!");
					return ;
				}
				var rect = auditInforStore.getAt(selectedm.selection.cell[0]);
				var auditInforId = rect.get('id');
				var count = auditInforDetailStore.getCount();
				var jsonArray = [];
				
				for(var i = 0;i < count ;i++ ){
					var temp = {flowInforId:auditInforId};
					Ext.apply(temp,auditInforDetailStore.getAt(i).data);
					jsonArray.push(temp);
				}

				if(count > 0){
					Ext.Ajax.request({
						method: "post",
						params: { infors: Ext.util.JSON.encode(jsonArray)},
						url: PATH + "/manage/audit/auditFlDefdMgeAction.do?ffc=updateAuditFlowDetailInfor",
						success: function(response){
							auditInforDetailStore.load({params:{inforId:auditInforId}});
							Ext.Msg.alert("消息", "保存成功!");
						}
					});
				}
			}
        },{
            text: '增加审批人',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var selectedm = auditInforGrid.getSelectionModel();
				if(selectedm.selection == null){
				    Ext.Msg.alert("消息", "请选择审批流程!");
					return ;
				}
				var Plant = auditInforDetailStore.recordType;
                var p = new Plant({
                    
                });
                auditInforDetailGrid.stopEditing();
                auditInforDetailGrid.getStore().insert(0, p);
                auditInforDetailGrid.startEditing(0, 0);
			}
        },{
            text: '删除审批人',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var arr = gridCheckSele.getSelections();
				if(arr.length == 0){
					Ext.Msg.alert("消息", "请选择要删除的审批人!");
					return false;
				}
				var paraArr = [];
				for(var i = 0 ;i < arr.length;i++){
				   paraArr.push(arr[i].id);
				}
				var selectedm = auditInforGrid.getSelectionModel();
				var rect = auditInforStore.getAt(selectedm.selection.cell[0]);
				var auditInforId = rect.get('id');

				Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中审批人!', function(btn){
					if(btn != 'yes'){return ;}
						Ext.Ajax.request({
									method: "post",
									params: { ids: Ext.util.JSON.encode(paraArr)},
									url: PATH + "/manage/audit/auditFlDefdMgeAction.do?ffc=deleteAuditFlowDetailInfor",
									success: function(response){
										auditInforDetailStore.load({params:{inforId:auditInforId}});
										Ext.Msg.alert("消息", "删除成功!");
									}
						});		
				});
			}
        }],
	   listeners: {
	            'rowclick': function(obj, rowIndex, evt){
				    var row = obj.getStore().getAt(rowIndex);
				    Ext.Ajax.request({
						method: "post",
						params: { auditDetailId: row.id},
						url: PATH + "/manage/audit/auditContentAction.do?m=findAuditDetailAuditContentList",
						success: function(response){
							eval("var temp = " + response.responseText);
							var nums = [],rownum = 0;
							auditContentStore.each(function(o){
								   for(var i  = 0; i < temp.length ;i++){
									   if(o.id == temp[i].id){
											nums.push(rownum);
									   }
								   }
								   rownum++;
							});
							auditContentCheckSele.selectRows(nums);
						}
					});		
				},
				'celldblclick':function(grid, rowIndex, columnIndex, e){
					auditInforDetailGrid['currentEditRowNum'] = rowIndex;
					if(grid.getColumnModel().getDataIndex(columnIndex) == 'auditPersonName'){
					    usersWin.show();
					};
				}
	    }
    });
//--审批内容 start
	var auditContentStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditContentAction.do?m=findAllContentList'}),//调用的动作 
	   reader: new Ext.data.JsonReader({}, 
	 [ //JSON数据的映射
         {name: 'id',mapping:'id',type:'string'},
         {name: 'auditContentName',mapping:'auditContentName',type:'string'},
	     {name: 'memo',mapping:'memo',type:'string'}
     ])
	});
	var auditContentCheckSele = new Ext.grid.CheckboxSelectionModel();
	var auditContentGrid = new Ext.grid.EditorGridPanel({
		title:'审批内容',
		collapsible: true,
	    split: true,
        ds : auditContentStore,
        store: auditContentStore,
	    sm:auditContentCheckSele,
        columns: [new Ext.grid.RowNumberer(),
		      auditContentCheckSele,
              {header: "id", dataIndex: 'id',hidden:true},
              {header: "审批内容", width: 100, dataIndex: 'auditContentName'},
		      {header: "备注", width: 100, dataIndex: 'memo'}
     ],
       width:100,
       height:200,
	   region: 'east',
	   tbar: [{
            text: '保存',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var arr = auditContentCheckSele.getSelections();
				var ids = [];
				for(var i = 0;i < arr.length ;i++ ){
					ids.push(arr[i].id);
				}
				var dArr = gridCheckSele.getSelections();
				if(dArr.length == 0 || dArr.length > 1){
				    Ext.Msg.alert("消息", "请选择一条审批流程详细信息!");
				}
				var auditDetailId = dArr[0].id;
				Ext.Ajax.request({
					method: "post",
					params: { 'auditDetailId':auditDetailId,'ids': ids},
					url: PATH + "/manage/audit/auditContentAction.do?m=saveAuditDetailAuditContent",
					success: function(response){
						Ext.Msg.alert("消息", "审批内容保存成功!");
					}
				});	
			}
        }]
    });
	auditContentStore.load();
//--审批内容 end
	var rightBottom = new Ext.Panel({
            region: 'center',
            split: true,
			layout:"border",
			items:[auditInforDetailGrid,auditContentGrid]
        });
		var nav2 = new Ext.Panel({
            region: 'center',
            split: true,
			layout:"border",
			items:[auditInforGrid,rightBottom]
        });

		var win = new Ext.Window({
			title: '审批管理',
			closable:true,
			width:900,
			height:550,
			plain:true,
			layout: 'border',
			modal : true,
			items: [auditTypeGrid,nav2]
		});
		win.show(this);
}