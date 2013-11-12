Ext.onReady(function(){
			var tree=new Ext.tree.TreePanel( {
				renderTo: '_audit_business_div',
				autoScroll:true,
				autoHeight:true,
				animate:true,
				enableDD:true,
				containerScroll: true,
				rootVisible: false,
				border:false,
				root: new Ext.tree.AsyncTreeNode({
					id:'auditroot',
					text:'模块',
					listeners:{
					    'expand':function(n){
							var comp = Ext.getCmp('auditBusinessId');
							if(n.childNodes.length > 0){
								if(comp){
									comp.setIconJump();
								}
							}else{
								if(comp){
									comp.setIconStopJump();
								}
							}
						}
					}
				}),
				dataUrl: PATH + '/workdoing/AuditBusinessAction.do?m=getAuditBusinessNodes',
				listeners: {
					'dblclick': function(node,evt){
						if(!node.leaf) return ;
						Ext.Ajax.request({
							url: PATH + '/manage/audit/auditInforMangeAction.do',
							params: {ffc:'findWaitAuditTypeInfor'},
							success: function(response){
									eval("var temp = " + response.responseText);
									var parentId = node.parentNode.id;
									var flowId = parentId.split('-')[1];
									var currentType = null;
									for(var i = 0;i < temp.length ;i++ ){
										if(temp[i].auditFlowId == flowId){
											currentType = temp[i];
										    break;
										}
									}
									var w = new Ext.ffc.WaitOfficeWindow({auditType:currentType,waitWorks:null,
										listeners : {
											"close" : function(){
												var tabp = Ext.getCmp('auditBusinessId')
													tabp.load(tabp.autoLoad);
											}
										}
									});
									w.show();
									/*
									var arr = w.auditTypeItems;
									var parentId = node.parentNode.id;
									var flowId = parentId.split('-')[1];
									for(var i = 0;i < arr.length ;i++ ){
										if(arr[i].id == flowId){
											w.accordion.get(i).expand(true);
										    break;
										}
									}*/
							}
						});
						//alert(node.attributes.text);
					}
				}
			});
			tree.getRootNode().expand(false);
 });
