Ext.onReady(function(){
var  addNodeForm = new Ext.FormPanel({
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
        title: '增加新模块',
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 100,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '模块编号',
                name: 'id',
			    disabled :true,
                allowBlank:false
            },{
                fieldLabel: '模块名称',
                name: 'moduleName'
            },new Ext.form.TextArea({
                fieldLabel: 'url',
                name: 'url',
				maxLength :50,
				width:100,
				height:100
            })
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
				header:false,
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
							addNodeForm.findByType("textfield")[2].getEl().dom.value = '';
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
				renderTo: 'ffc_modules_tree',
				autoScroll:true,
				animate:true,
				height:500,
				enableDD:true,
				containerScroll: true,
				root: rootNode,
				dataUrl: PATH + '/manage/modules/modulesTreeViewAction.do?m=checkboxQuery',
				listeners: {
					'click': function(tp){
						win.findByType("textfield")[0].getEl().dom.value = tp.id;
						win.findByType("textfield")[1].getEl().dom.value = tp.attributes.text;
						win.findByType("textfield")[2].getEl().dom.value = tp.attributes.url;
					},
					'beforemovenode' : function(  tree,  node,  oldParent,  newParent,  index ) {
						if(oldParent.attributes.id != newParent.attributes.id){
							return false;
						}
						return true;
					}
				}
			});

			

		var nav2 = new Ext.Panel({
			header:false,
            title: '功能模块树',
            region: 'west',
            split: true,
            width: 300,
            collapsible: true,
            margins:'3 0 3 3',
            cmargins:'3 3 3 3',
			layout:"fit",
			items:[tree]
        });
	
		var simple = new Ext.FormPanel({
		header:false,
        labelWidth: 75, 
        buttonAlign:'left',
        frame:true,
        title: '模块详细信息',
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 200,
        defaults: {width: 100},
        defaultType: 'textfield',

        items: [{
                fieldLabel: '模块编号',
                name: 'id',
			    disabled :true,
				width:200,
                allowBlank:false
            },{
                fieldLabel: '模块名称',
                name: 'moduleName',
				width:200
            },new Ext.form.TextArea({
                fieldLabel: 'URL',
                name: 'url',
				 anchor:'90%',
				height:100
            })
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
				var arr = [];
				function checkChildrenNodes(node){
					if(!node.isLeaf()){
						node.eachChild(function(child){
							arr.push({'id' : child.id,'serialNumber':node.indexOf(child),'leaf':child.attributes.leaf});
						});
						var children = node.childNodes;
						for(var i = 0,len = children.length;i < len;i++ ){
							checkChildrenNodes(children[i]);
						}
					}						
				}
				checkChildrenNodes(tree.getRootNode());
				
				Ext.Ajax.request({
					method: "post",
					params: {'datas': Ext.encode(arr)},
					url: PATH + '/manage/modules/modulesTreeMgAction.do?m=updateSerialNumbers',
					success: function(response){
						
					}
				});
				var cuNode = tree.getSelectionModel().getSelectedNode();
				if(!cuNode){Ext.MessageBox.alert('系统提示', '请选择当前节点！.', function(){});}
				updateModuleFun(cuNode,win.findByType("textfield")[1].getEl().dom.value,win.findByType("textfield")[2].getEl().dom.value);
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
				title: '模块管理',
				closable:true,
				width:750,
				height:450,
				plain:true,
                layout: 'border',
				modal : true,
				header:false,
				items: [nav2,simple]
			});

			//win.render('ffc_modules_div');
			win.show('ffc_modules_div');
			function addModuleFun(parentId,cuNode){

						Ext.Ajax.request({
						method: "post",
						params: {'moduleName': addNodeWin.findByType("textfield")[1].getEl().dom.value, 'parentId': parentId,url:addNodeWin.findByType("textfield")[2].getEl().dom.value},
						url: PATH + '/manage/modules/modulesTreeMgAction.do?m=addNode',
						success: function(response){
							var str = "var temp = " + response.responseText;
							eval(str);
							var node = new Ext.tree.TreeNode({
								text: temp.moduleName,
								draggable:false,
								id:temp.id
							});
							node.attributes.url = temp.url;
							if(!cuNode){//没有选择结点
									tree.getRootNode().appendChild(node);
							 }else{
									cuNode.appendChild(node);
							 }
						},
						requestexception :function(obj){
						    
						}
				});
			}

			function deleteModuleFun(node){
				Ext.Ajax.request({
						method: "post",
						params: {'id': node.id},
						url: PATH + '/manage/modules/modulesTreeMgAction.do?m=deleteNode',
						success: function(response){
								node.remove() ;
						},
						requestexception :function(obj){
						    alert(obj);
						}
				});
			}

		function updateModuleFun(currNode,moduleName,url){
				Ext.Ajax.request({
						method: "post",
						params: {'id': currNode.id,'moduleName':moduleName,'url':url},
						url: PATH + '/manage/modules/modulesTreeMgAction.do?m=updateNode',
						success: function(response){
								currNode.setText(moduleName);
								currNode.attributes.url = url;
								Ext.MessageBox.alert('系统提示', '修改成功.', function(){});
						},
						requestexception :function(obj){
						    alert(obj);
						}
				});
			}

 });
