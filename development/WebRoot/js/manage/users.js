function onItemUsers(item, pressed){
	var current_userId = '';
	//--------------------------------------------------------add start
	var  addForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
		    region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '用户名',
                name: 'userName',
                allowBlank:false
            },{
                fieldLabel: '真实姓名',
                name: 'trueName',
                allowBlank:false
            },new cms.form.ComboTree(
				{loader: new Ext.tree.TreeLoader({
					fieldLabel: '所属部门',
					name: 'departName',
					allowBlank:false,
                    dataUrl: PATH +'/manage/depart/departViewAction.do?ffc=query'      
                })}) 
        ],

        buttons: [{
            text: '保存',
		    handler:function(){
			  var paramStr = "{\"id\":\"\", \"userName\" : \"" 
													+ addNodeWin.findByType("textfield")[0].getEl().dom.value + "\", \"trueName\" : \"" 
													+ addNodeWin.findByType("textfield")[1].getEl().dom.value + "\",\"departName\":\"" 
													+ addNodeWin.findByType("textfield")[2].getEl().dom.value + "\",\"departId\":\""
													+ addNodeWin.findByType("textfield")[2].hiddenField.value + "\" } " ;
					Ext.Ajax.request(
					{
					   url: PATH + '/manage/user/usersManageAction.do?ffc=addUserInfor',
					   params: { content: paramStr },
					   success: function(response){
							//eval("var tmp = " + response.responseText);
							//var value= tmp;
							//var re=new Ext.data.Record(value,tmp.id);
							usersStore.reload(); 
					   }
					});

			   addNodeWin.hide();
			}
        },{
            text: '关闭',
		    handler:function(){
			   addNodeWin.hide();
			}
        }]
    });

var addNodeWin = new Ext.Window({
				title: '用户信息',
				closable:true,
				width:260,
				height:250,
				plain:true,
				modal : true,
        layout: 'border',
				items: addForm,
				listeners: {
					'show':function(win){
								addForm.findByType("textfield")[0].getEl().dom.value = '';
								addForm.findByType("textfield")[1].getEl().dom.value = '';
								addForm.findByType("textfield")[2].getEl().dom.value = '';
						
					}
				}
			});

//--------------------------------------------------------add end
//--------------------------------------------------------update start
	var  updateForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
        layout:'form',
		    region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',
        items: [{
                fieldLabel: '用户名',
                name: 'userName',
                allowBlank:false
            },{
                fieldLabel: '真实姓名',
                name: 'trueName',
                allowBlank:false
            },new cms.form.ComboTree(
				{loader: new Ext.tree.TreeLoader({
					fieldLabel: '所属部门',
					name: 'departName',
					allowBlank:false,
                    dataUrl: PATH +'/manage/depart/departViewAction.do?ffc=query'      
                })}) 
        ],

        buttons: [{
            text: '保存',
		    handler:function(){
			  var paramStr = "{\"id\":\"" + updateNodeWin['win_id'] + "\", \"userName\" : \"" 
													+ updateNodeWin.findByType("textfield")[0].getEl().dom.value + "\", \"trueName\" : \"" 
													+ updateNodeWin.findByType("textfield")[1].getEl().dom.value + "\",\"departName\":\"" 
													+ updateNodeWin.findByType("textfield")[2].getEl().dom.value + "\",\"departId\":\""
													+ updateNodeWin.findByType("textfield")[2].hiddenField.value + "\"} " ;
					Ext.Ajax.request(
					{
					   url: PATH + '/manage/user/usersManageAction.do?ffc=updateUserInfor',
					   params: { content: paramStr },
					   success: function(response){
							//eval("var tmp = " + response.responseText);
							//var value= tmp;
							//var re=new Ext.data.Record(value,tmp.id);
							usersStore.reload(); 
					   }
					});

			   updateNodeWin.hide();
			}
        },{
            text: '关闭',
		    handler:function(){
			   updateNodeWin.hide();
			}
        }]
    });

var updateNodeWin = new Ext.Window({
				title: '用户信息',
				closable:true,
				width:260,
				height:250,
				plain:true,
                layout: 'border',
				items: updateForm,
				listeners: {
					'show':function(win){
						    if(updateNodeWin['win_id'] && updateNodeWin['win_userName']){
								updateForm.findByType("textfield")[0].getEl().dom.value = updateNodeWin['win_userName'];
								updateForm.findByType("textfield")[1].getEl().dom.value = updateNodeWin['win_trueName'];
								updateForm.findByType("textfield")[2].getEl().dom.value = updateNodeWin['win_departName'];
								updateForm.findByType("textfield")[2].hiddenField.value  = updateNodeWin['win_departId'];
							}
					}
				}
			});
//--------------------------------------------------------update end
Ext.ffc.BillPurviewSet = {};
Ext.ffc.BillPurviewSet.WillSelectUserGrid = Ext.extend(Ext.grid.GridPanel, {
    willSeleUserStore : null,
	businessType : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.willSeleUserStore = new Ext.data.Store({
								   proxy: new Ext.data.HttpProxy({url:PATH + '/manage/user/usersViewAction.do?ffc=getWillSelectUser&businessType=' + this.businessType + "&userId=" + this.userId}),//调用的动作 
								   reader: new Ext.data.JsonReader({
								   root: 'items',   
								   totalProperty :'totalCount'
								 }, 
								 [ //JSON数据的映射
								    {name: 'id',mapping:'id',type:'string'},
									{name: 'userName',mapping:'userName',type:'string'},
									{name: 'trueName',mapping:'trueName',type:'string'},
									{name: 'departId',mapping:'departId',type:'string'},
									{name: 'departName',mapping:'departName',type:'string'}//,
									//{name: 'regTimeString',mapping:'regTimeString',type:'string'}
								 ])
								});
		this.willSeleUserStore.load();
		Ext.ffc.BillPurviewSet.WillSelectUserGrid.superclass.constructor.call(this, {
			title : '待选用户',
			width : 450,
			height : 600,
			enableDragDrop : true,
			ddGroup : 'secondGridDDGroup',
			ds : this.willSeleUserStore,
			store: this.willSeleUserStore,
			columns: [new Ext.grid.RowNumberer(),//自动行号
				  {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
				  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
				  {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
				  {header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
				  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true}//,
				  //{header: "创建时间", width: 200, dataIndex: 'regTimeString'}
				],
		   region: 'west',
		   listeners: {
					render : function( component ){
						var firstGridDropTargetEl =  component.getView().el.dom.childNodes[0].childNodes[1];
						var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
							ddGroup    : 'firstGridDDGroup',
							copy       : true,
							notifyDrop : function(ddSource, e, data){
								function addRow(record, index, allItems) {
									var firstGridStore = component.getStore();
									var foundItem = firstGridStore.findExact('id', record.data.id);
									if (foundItem  == -1) {
										firstGridStore.add(record);
										firstGridStore.sort('id', 'ASC');
										ddSource.grid.store.remove(record);
									}
								}
								Ext.each(ddSource.dragData.selections ,addRow);
								return(true);
							}
						});
					}
		   },
		   bbar: new Ext.PagingToolbar({
				pageSize: 14,
				store: this.willSeleUserStore,
				displayInfo: true,
				displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg: "没有记录"
			})
		});
	}
});

Ext.ffc.BillPurviewSet.SelectedUserGrid = Ext.extend(Ext.grid.GridPanel, {
	seleUserStore : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.seleUserStore = new Ext.data.Store({
								   proxy: new Ext.data.HttpProxy({url:PATH + '/manage/user/usersViewAction.do?ffc=getSelectedUser&businessType=' + this.businessType + "&userId=" + this.userId}),//调用的动作 
								   reader: new Ext.data.JsonReader({
								   //root: 'items',   
								   //totalProperty :'totalCount'
								 }, 
								 [ //JSON数据的映射
								    {name: 'id',mapping:'id',type:'string'},
									{name: 'userName',mapping:'userName',type:'string'},
									{name: 'trueName',mapping:'trueName',type:'string'},
									{name: 'departId',mapping:'departId',type:'string'},
									{name: 'departName',mapping:'departName',type:'string'}//,
									//{name: 'regTimeString',mapping:'regTimeString',type:'string'}
								 ])
								});
		this.seleUserStore.load();
		Ext.ffc.BillPurviewSet.SelectedUserGrid.superclass.constructor.call(this, {
			title : '已选用户',
			width : 450,
			height : 600,
			enableDragDrop : true,
			ddGroup : 'firstGridDDGroup',
			ds : this.seleUserStore,
			store: this.seleUserStore,
			viewConfig : {
				getRowClass : function(record,rowIndex,rowParams,store){
		
					if(this.selectedUserGrid){
						
					}
					/*if(record.data.id != '1'){
						return 'x-grid-record-gray';   
					}else{   
						return '';   
					}*/
				},scope:this
			},
			columns: [new Ext.grid.RowNumberer(),//自动行号
				  {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
				  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
				  {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
				  {header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
				  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true}//,
				  //{header: "创建时间", width: 200, dataIndex: 'regTimeString'}
				],
		   region: 'center',
		   listeners: {
					render : function( component ){
						var secondGridDropTargetEl = component.getView().el.dom.childNodes[0].childNodes[1]
						var destGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
							ddGroup    : 'secondGridDDGroup',
							copy       : false,
							notifyDrop : function(ddSource, e, data){
								function addRow(record, index, allItems) {
									var secondGridStore = component.getStore();
									var foundItem = secondGridStore.findExact('id', record.data.id);
									if (foundItem  == -1) {
										secondGridStore.add(record);
										secondGridStore.sort('id', 'ASC');
										ddSource.grid.store.remove(record);
									}
								}
								Ext.each(ddSource.dragData.selections ,addRow);
								return(true);
							}
						});
					}
		   }
		});
	}
});


Ext.ffc.BillPurviewSetWindow = Ext.extend(Ext.Window, {
	willSelectUserGrid : null,
	selectedUserGrid : null,
	businessType : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.selectedUserGrid = new Ext.ffc.BillPurviewSet.SelectedUserGrid(_cfg);//
		this.willSelectUserGrid = new Ext.ffc.BillPurviewSet.WillSelectUserGrid(_cfg);
		Ext.ffc.BillPurviewSetWindow.superclass.constructor.call(this, {
			width : 850,
			height : 600,
			plain:true,
			layout: 'border',
			modal : true,
			items: [this.willSelectUserGrid,this.selectedUserGrid],
			buttons : [
				{
					text : "确 定",
					handler : function() {
						var store = this.selectedUserGrid.getStore();
						var arr = [];
						for(var i  = 0,len = store.getCount(); i < len;i++ ){
							arr.push(store.getAt(i).data.id);
						}
						var userId = this.userId;
						var businessType = this.businessType;
						Ext.Ajax.request(
						{
						   url: PATH + '/manage/user/usersManageAction.do?ffc=saveResourcePurview',
						   params: { userId: userId,businessType:businessType,userIds:arr},
						   success: function(response){
								Ext.MessageBox.alert('系统提示', '保存成功!', function(){});
						   }
						});
					},scope:this
				},{
					text : "取 消",
					handler : function() {
						this.close();
					},scope:this
				}]
		});
	}
});

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
		{name: 'regTimeString',mapping:'regTimeString',type:'string'},
		{name: 'valid',mapping:'valid',type:'float'}
     ]),
	listeners : {
		load:function(){
			setRowViewStatus();
		}
	}
	});

    var usersGrid = new Ext.grid.GridPanel({
        //id:"grid",
       // el:"example-grid",
        ds : usersStore,
        store: usersStore,
		split: true,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
			  {header: "用户名", width: 80, dataIndex: 'userName', sortable: true},
              {header: "真实姓名", width: 80, dataIndex: 'trueName', sortable: true},
			  {header: "所属部门id", width: 80, dataIndex: 'departId', sortable: false,hidden:true},
			  {header: "所属部门", width: 80, dataIndex: 'departName', sortable: true},
              
			  {header: "报价单权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					  var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"报价单权限设定\",businessType:1,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			  },  
			  {header: "合同权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
					var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"合同权限设定\",businessType:2,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			   },  
			  {header: "订单权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				   var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"订单权限设定\",businessType:3,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			   },  
			  {header: "入库单权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				   var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"入库单权限设定\",businessType:4,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			  },
			  {header: "出库单权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				  var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"出库单权限设定\",businessType:5,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			   },  
			  {header: "交货单权限", width: 70, dataIndex: 'regTimeString',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				   var id = record.data.id;
					  return "<a href='#' onclick='javascript:new Ext.ffc.BillPurviewSetWindow({title:\"交货单权限设定\",businessType:6,userId:\"" + id + "\"}).show()'>设定</a>";
				  }
			  },
			  {header: "禁用", width: 40, dataIndex: 'valid',renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				    var valid = record.data.valid;
					var userId = record.data.id;
					var str = '';
					if(valid * 1 == 1){
					   str = 'checked';
					}
					return "<input type='checkbox' userId='" + userId + "'" + str + " onclick='setUserValid(this)'/>";
			  }},
			  {header: "创建时间", width: 200, dataIndex: 'regTimeString'}
     ],
       width:500,
       height:600,
	   region: 'west',
	   bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: usersStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"
        }),
	   buttons: [{
            text: '保存',
			width:50,
		    handler:function(){ 
				var selectedm = usersGrid.getSelectionModel();
				var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择用户!', function(){});
						return ;
				}
				var arr = gridCheckSele.getSelections();
				
				var paraArr = [];
				for(var i = 0 ;i < arr.length;i++){
				   paraArr.push(arr[i].id);
				}
				var ttt = { userId: record.id,rolesIds:Ext.util.JSON.encode(paraArr)};
				Ext.Ajax.request({
					   url: PATH + '/manage/user/usersManageAction.do?ffc=updateUserDetailInfor',
					   params: { userId: record.id,rolesIds:Ext.util.JSON.encode(paraArr)},
					   success: function(response){
							Ext.MessageBox.alert('系统提示', '保存成功!', function(){});
					   }
				});
			}
        },{
            text: '增加用户',
			width:50,
		    handler:function(){
				//alert(addUserWin);
				//alert(addUserWin.show);
				addNodeWin.show();
			}
        },{
            text: '修改用户',
			width:50,
		    handler:function(){
				var selectedm = usersGrid.getSelectionModel();
				 var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择用户!', function(){});
						return ;
				}
				updateNodeWin.show();
			}
        },{
            text: '删除用户',
			width:50,
		    handler:function(){
				var selectedm = usersGrid.getSelectionModel();
				var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择用户!', function(){});
						return ;
				}
				Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前用户!', function(btn){
								if(btn != 'yes'){return ;}
							    Ext.Ajax.request(
								{
								   url: PATH + '/manage/user/usersManageAction.do?ffc=deleteUserInfor',
								   params: { userId: updateNodeWin['win_id']},
								   success: function(response){
										Ext.MessageBox.alert('系统提示', '删除成功!', function(){
												usersStore.reload();
										});
								   }
								});
								tree.root.reload() ;
						   });
				
			}
        }],
		listeners: {
	            'rowclick': function(obj, rowIndex, evt){
						   var row = obj.getStore().getAt(rowIndex);
						   var userId = row.id;
						   
						   updateNodeWin['win_id'] = userId;
						   updateNodeWin['win_userName'] = row.json.userName;
						   updateNodeWin['win_trueName'] = row.json.trueName;
						   updateNodeWin['win_departName'] = row.json.departName;
						   updateNodeWin['win_departId'] = row.json.departId;
						   var roleLen = gridCheckSele.getCount();
							for(var i  = 0;i < roleLen;i++){
							    gridCheckSele.deselectRow(i);
							}
							Ext.Ajax.request({
								   url: PATH + '/manage/user/usersViewAction.do?ffc=getUsersRolesDetail',
								   params: { 'userId': userId},
								   success: function(response){
									   eval("var tmp=" + response.responseText);
									   if(tmp.length > 0){
										   var s = sysRolesGrid.getStore();
										   var nums = [];
										   var rownum = 0;
										   s.each(function(o){
											   for(var i  = 0; i < tmp.length ;i++){
											       if(o.id == tmp[i].id){
														nums.push(rownum);
												   }
											   }
											   rownum++;
										   });
										   gridCheckSele.selectRows(nums);
									   }
								   }
								});
			}
	    }
    });
	usersStore.load({params:{start:0,limit:14}});

		var rolesStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/role/rolesViewAction.do?ffc=getAllRoles'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'roles',  //从struts2里面传递过来的参数 
	   successProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
         // {name: 'id',mapping:'id',type:'string'},
        {name: 'roleName',mapping:'roleName',type:'string'},
        {name: 'memo',mapping:'memo',type:'string'}
     ])
	});
	// alert(333);
    var gridCheckSele = new Ext.grid.CheckboxSelectionModel();
	//gridCheckSele.singleSelect = true;
    var sysRolesGrid = new Ext.grid.GridPanel({
       // id:"grid2",
       // el:"example-grid",
	    split: true,
        ds : rolesStore,
		sm:gridCheckSele,//实现多选
        store: rolesStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
		      gridCheckSele,
              {header: "id", width: 120, dataIndex: 'id', sortable: true,hidden:true},
              {header: "角色名称", width: 120, dataIndex: 'roleName', sortable: true},
              {header: "角色描述", width: 150, dataIndex: 'memo'}   
     ],
       width:360,
       height:600,
	   region: 'center'
    });
	rolesStore.load();

			var win = new Ext.Window({
				title: '用户管理',
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

		function setRowViewStatus(grid){
			var s = usersGrid.getStore();
			for(var rowIndex = 0,len = s.getCount();rowIndex < len ; rowIndex++){
				var r = s.getAt(rowIndex);
				if(r.data.valid * 1 == 1){
					usersGrid.getView().getRow(rowIndex).style.backgroundColor = '#FFCCFF';
					usersGrid.getView().getRow(rowIndex).style.color = '#FF0000';
				}
			}
		}
    }
function setUserValid(obj){
	var v = 0;
	if(obj.checked){
		v = 1;
	}
	Ext.Ajax.request({
	   url: PATH + '/manage/user/usersManageAction.do?ffc=setUserValid',
	   params: { 'userId': obj.getAttribute('userId'),'valid':v},
	   success: function(response){
		   
	   }
	});
}
