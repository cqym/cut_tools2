Ext.onReady(function(){
			 xxxtree=new Ext.tree.TreePanel( {
				renderTo: '_date_tip_business_div',
				autoScroll:true,
				autoHeight:true,
				animate:true,
				enableDD:true,
				containerScroll: true,
				border:false,
				rootVisible: false,
				root: new Ext.tree.AsyncTreeNode({id:'odatebroot',text:'模块',
					listeners:{
					    'expand':function(n){
							var carr = n.childNodes;
							var f = false;
							for(var i = 0;i < carr.length ;i++){
								if(carr[i].attributes.businessCount > 0){
								    f = true;
									break;
								}
							}
							if(f){
								var comp = Ext.getCmp('dateTipBusinessId');
								if(comp){
									comp.setIconJump();
								}else{
								    comp.setIconStopJump();
								}
							}
						}
					}	
				}),
				dataUrl: PATH + '/workdoing/DateOverBusinessAction.do?m=getOverDateBusinessNodes',
				listeners: {
					'dblclick': function(node,ev){
						if(!node.leaf){
							return ;
						}
						loadBusinessInfor( node.attributes.url,node.id);
						//addTab(node.attributes.description,PATH + node.attributes.url);
					}
				}
			});
			xxxtree.getRootNode().expand(false);
 });
