function onItemRoles(item, pressed){
    showWin(item, pressed,'ffc_role_modules_div','ffc_role_modules_tree',100,100);
	showWin(item, pressed,'ffc_role_modules_div2','ffc_role_modules_tree2',300,300);
}

function showWin(item, pressed,winReaderTo,treeReaderTo,_x,_y){
	var current_roleId = '';
	//--------------------------------------------------------add start
	var  addNodeForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',
        items: [{
                fieldLabel: '角色名称',
                name: 'roleName',
                allowBlank:false
            },new Ext.form.TextArea({
                fieldLabel: '角色描述',
                name: 'memo',
				maxLength :50,
				width:100,
				height:100
            })
        ],

        buttons: [{
            text: '保存',
		    handler:function(){
			  var paramStr = "{\"id\":\"\", \"roleName\" : \"" 
													+ addNodeWin.findByType("textfield")[0].getEl().dom.value +"\", \"memo\" : \"" 
													+ addNodeWin.findByType("textfield")[1].getEl().dom.value +"\" } " 
					Ext.Ajax.request(
					{
					   url: PATH + '/manage/role/rolesManageAction.do?ffc=createRole',
					   params: { content: paramStr },
					   success: function(response){
							eval("var tmp = " + response.responseText);
							var value= tmp;
							var re=new Ext.data.Record(value,tmp.id);
							rolesStore.add(re); 
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
				title: '角色信息',
				closable:true,
				width:260,
				height:250,
				plain:true,
                layout: 'border',
				items: addNodeForm,
				listeners: {
					'show':function(win){
							addNodeForm.findByType("textfield")[0].getEl().dom.value = '';
							addNodeForm.findByType("textfield")[1].getEl().dom.value = '';
					}
				}
			});
//--------------------------------------------------------add end
//--------------------------------------------------------update start
	var  updateForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '角色名称',
                name: 'roleName',
                allowBlank:false
            },new Ext.form.TextArea({
                fieldLabel: '角色描述',
                name: 'memo',
				maxLength :50,
				width:100,
				height:100
            })
        ],

        buttons: [{
            text: '保存',
		    handler:function(){
			  var paramStr = "{\"id\":\"" + current_roleId + "\", \"roleName\" : \"" 
													+ updateNodeWin.findByType("textfield")[0].getEl().dom.value + "\", \"memo\" : \"" 
													+ updateNodeWin.findByType("textfield")[1].getEl().dom.value + "\" } " 
					Ext.Ajax.request(
					{
					   url: PATH + '/manage/role/rolesManageAction.do?ffc=updateRoleInfor',
					   params: { content: paramStr },
					   success: function(response){
							//eval("var tmp = " + response.responseText);
							//var value= tmp;
							//var re=new Ext.data.Record(value,tmp.id);
							rolesStore.reload(); 
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
				title: '角色信息',
				closable:true,
				width:260,
				height:250,
				plain:true,
                layout: 'border',
				items: updateForm,
				listeners: {
					'show':function(win){
						    if(updateNodeWin['win_roleName'] && updateNodeWin['win_memo']){
								updateForm.findByType("textfield")[0].getEl().dom.value = updateNodeWin['win_roleName'];
								updateForm.findByType("textfield")[1].getEl().dom.value = updateNodeWin['win_memo'];
							}
					}
				}
			});
//--------------------------------------------------------update end

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

    var grid = new Ext.grid.GridPanel({
		header:false,
       // id:"grid",
       // el:"example-grid",
        ds : rolesStore,
        store: rolesStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: true,hidden:true},
              {header: "角色名称", width: 120, dataIndex: 'roleName', sortable: true},
              {header: "角色描述", width: 200, dataIndex: 'memo'}   
     ],
	   buttonAlign : 'left',
       width:360,
       height:600,
	   region: 'west',
	   loadMarsk : new Ext.LoadMask(Ext.getBody(), {
						 msg : '正在保存数据，请稍候。。。。。。',
						 removeMask : true// 完成后移除
					 }),
	   buttons: [
		 {
            text: '保 存',
			width:50,
		    handler:function(){ 
				var selectedm = grid.getSelectionModel();
				 var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择角色!', function(){});
						return ;
				}
				var arr = tree.getChecked();
				var paraArr = [];
				for(var i = 0 ;i < arr.length;i++){
				   paraArr.push(arr[i].id);
				}
				var roleId = current_roleId;
				if(roleId != ''){
					grid.loadMarsk .show(); //显示
					Ext.Ajax.request({
						   url: PATH + '/manage/role/rolesManageAction.do?ffc=updateRoleDetail',
						   params: { roleId: roleId,modulesIds:Ext.util.JSON.encode(paraArr)},
						   success: function(response){
							   grid.loadMarsk.hide();
							   Ext.MessageBox.alert('系统提示', '保存成功!', function(){});
						   }
					});
			    }
			}
        },{
            text: '增加角色',
			width:50,
		    handler:function(){
				addNodeWin.show();
			}
        },{
            text: '修改角色',
			width:50,
		    handler:function(){
				var selectedm = grid.getSelectionModel();
				 var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择角色!', function(){});
						return ;
				}
				updateNodeWin.show();
			}
        },{
            text: '删除角色',
			width:50,
		    handler:function(){
				var selectedm = grid.getSelectionModel();
				var record = selectedm.getSelected();  
				if(!record){
					    Ext.MessageBox.alert('系统提示', '请选择角色!', function(){});
						return ;
				}
				Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前节点!', function(btn){
					if(btn != 'yes'){return ;}
							    Ext.Ajax.request(
								{
								   url: PATH + '/manage/role/rolesManageAction.do?ffc=deleteRole',
								   params: { roleId: current_roleId},
								   success: function(response){
										Ext.MessageBox.alert('系统提示', '删除成功!', function(){
												rolesStore.reload();
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
						   var roleId = row.id;

						   current_roleId = roleId;
						   tree.loader.baseParams = {'ffc':'getRoleModules','roleId':roleId}  
						   tree.root.reload() ;
						   //Ext.ffc.util.debug(tree.root);
						   tree.expandAll();
						   updateNodeWin['win_roleName'] = row.json.roleName;
						   updateNodeWin['win_memo'] = row.json.memo;
				}
	    }
    });
	rolesStore.load();

			var treeData = new Ext.tree.TreeLoader({  
                 url : PATH + '/manage/role/rolesViewAction.do',  
                 requestMethod : 'POST',  
                 baseParams:{ffc:'getRoleModules',roleId:''}  
             });  

			 //建立根
			 var rootNode = new Ext.tree.AsyncTreeNode( {
				text: '模块',
				draggable:false,
				id:'root'
			});

			var tree = new Ext.tree.TreePanel( {
				//header:false,
				autoScroll:true,
				animate:true,
				height:410,
				enableDD:true,
				containerScroll: true,
				root: rootNode,
				loader : treeData,  
				flag:true,
				renderTo: treeReaderTo,
				listeners: {
	            'checkchange': function(node, checked){
					function checkChildrenNodes(node,checked){
						if(!node.isLeaf()){
							node.eachChild(function(child){
								child.ui.checkbox.checked = checked;     
								child.attributes.checked = checked;    
							});
							var children = node.childNodes;
							for(var i = 0,len = children.length;i < len;i++ ){
								checkChildrenNodes(children[i],checked);
							}
						}						
					}
					checkChildrenNodes(node,checked);
					if(checked){
						function checkParentNode(node,checked){
							if(node.id != 'root'){
								node.ui.checkbox.checked = checked;   
								node.attributes.checked = checked;    
								checkParentNode(node.parentNode,checked);
							}	
						}
						checkParentNode(node.parentNode,checked);
					}
				},scope:this
	        }
			});


		var nav2 = new Ext.Panel({
			header:false,
            title: '功能模块树',
            region: 'center',
            split: true,
            //width: 200,
            collapsible: false,
            margins:'3 0 3 3',
            cmargins:'3 3 3 3',
			layout:"fit",
			items:[tree]
        });


			var win = new Ext.Window({
				header:false,
				title: '角色管理',
				closable:true,
			    x:_x,
				y:_y,
				width:700,
				height:450,
				plain:true,
                layout: 'border',
				//modal : true,
				maximizable :true,
				items: [grid,nav2]
			});
			
			win.show(winReaderTo);
			//grid.render();

    }