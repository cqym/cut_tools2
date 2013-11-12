function onItemDepart(item, pressed){
var  addNodeForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
        title: '增加新机构',
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '机构编号',
                name: 'id',
			    disabled :true,
                allowBlank:false
            },{
                fieldLabel: '机构名称',
                name: 'departName'
            }
        ],

        buttons: [{
            text: '保存',
		    handler:function(){
			   addModuleFun(addNodeForm["parentId"],addNodeForm["cuNode"]);
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
			//建立根
			 var rootNode = new Ext.tree.AsyncTreeNode( {
				text: '模块',
				draggable:false,
				id:'root'
			});

			var tree=new Ext.tree.TreePanel( {
				autoScroll:true,
				animate:true,
				height:200,
				enableDD:true,
				containerScroll: true,
				root: rootNode,
				
				dataUrl: PATH + '/manage/depart/departViewAction.do?ffc=query',
				listeners: {
	            'click': function(tp){
					win.findByType("textfield")[0].getEl().dom.value = tp.id;
					win.findByType("textfield")[1].getEl().dom.value = tp.attributes.text;
	            }
	        }
			});

		
		var nav2 = new Ext.Panel({
            title: '组织机构树',
            region: 'west',
            split: true,
            width: 200,
            collapsible: true,
            margins:'3 0 3 3',
            cmargins:'3 3 3 3',
			layout:"fit",
			items:[tree]
        });
	
		var simple = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
        title: '机构详细信息',
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '机构编号',
                name: 'id',
			    disabled :true,
                allowBlank:false
            },{
                fieldLabel: '机构名称',
                name: 'departName'
            }
        ],

        buttons: [{
            text: '增加下级',
			width:50,
		    handler:function(){
				var cuNode = tree.getSelectionModel().getSelectedNode();
				if(!cuNode){Ext.MessageBox.alert('系统提示', '请选择当前节点！.', function(){});}
				var parentId = '';
				if(cuNode.getDepth() == 0){
				    parentId = '';
				}else{
					parentId = cuNode.id;
				}
					//addModuleFun(parentId,cuNode);
				addNodeForm["parentId"] = parentId;
				addNodeForm["cuNode"] = cuNode;
				addNodeWin.show();
			}
        },{
            text: '增加同级',
				width:50,
		    handler:function(){
				var cuNode = tree.getSelectionModel().getSelectedNode();
				if(!cuNode){Ext.MessageBox.alert('系统提示', '请选择当前节点！.', function(){});}
				if(cuNode.getDepth()  == 0){//没有选择结点,或者选择的是根
						Ext.MessageBox.alert('系统提示', '不能给根节点添加同级！.', function(){});
				 }else{
					addNodeForm["parentId"] = cuNode.parentNode.id;
					addNodeForm["cuNode"] = cuNode.parentNode;
					 if(cuNode.getDepth()  == 1){//如果父亲节点是跟节点
					     addNodeForm["parentId"] = "";
					 }
					addNodeWin.show();

						//addModuleFun(cuNode.parentNode.id,cuNode.parentNode);
				 }
			}
        },{
            text: '保存',
				width:50,
		    handler:function(){
				var cuNode = tree.getSelectionModel().getSelectedNode();
				if(!cuNode){Ext.MessageBox.alert('系统提示', '请选择当前节点！.', function(){});}
				updateModuleFun(cuNode,win.findByType("textfield")[1].getEl().dom.value);
			}
        },{
            text: '删除',
				width:30,
		    handler:function(){
				var cuNode = tree.getSelectionModel().getSelectedNode();
				if(!cuNode){Ext.MessageBox.alert('系统提示', '请选择当前节点！.', function(){});}
				if(cuNode.getDepth()  == 0){//
						Ext.MessageBox.alert('系统提示', '根节点不能删除！.', function(){});
				 }else{
					 Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前节点!', function(btn){
							    if(btn == 'yes'){
								    deleteModuleFun(cuNode);
								}
						   });
					    
				 }
			}
        },{
            text: '关闭',
				width:30,
		    handler:function(){
			   win.hide();
			}
        }]
    });

			var win = new Ext.Window({
				title: '机构管理',
				closable:true,
				width:500,
				height:450,
				plain:true,
                layout: 'border',
				modal : true,
				items: [nav2,simple]
			});
			win.show(this);

			function addModuleFun(parentId,cuNode){

						Ext.Ajax.request({
						method: "post",
						params: {'departName': addNodeWin.findByType("textfield")[1].getEl().dom.value, 'parentId': parentId},
						url: PATH + '/manage/depart/departManageAction.do?ffc=addNode',
						success: function(response){
							var str = "var temp = " + response.responseText;
							eval(str);
							var node = new Ext.tree.TreeNode( {
								text: temp.text,
								draggable:false,
								id:temp.id
							});
							if(!cuNode){//没有选择结点
									tree.getRootNode().appendChild(node);
							 }else{
									cuNode.appendChild(node);
							 }
						},
						requestexception :function(obj){
						    alert(obj);
						}
				});
			}

			function deleteModuleFun(node){
				Ext.Ajax.request({
						method: "post",
						params: {'id': node.id},
						url: PATH + '/manage/depart/departManageAction.do?ffc=deleteNode',
						success: function(response){
								node.remove() ;
						},
						requestexception :function(obj){
						    alert(obj);
						}
				});
			}

		function updateModuleFun(currNode,moduleName){
				Ext.Ajax.request({
						method: "post",
						params: {'id': currNode.id,'departName':moduleName},
						url: PATH + '/manage/depart/departManageAction.do?ffc=updateNode',
						success: function(response){
								currNode.setText(moduleName);
								Ext.MessageBox.alert('系统提示', '修改成功.', function(){});
						},
						requestexception :function(obj){
						    alert(obj);
						}
				});
			}

    }