Ext.onReady(function(){
   
	Ext.ffc.UserMsg = {
		msgWindowCache:{},
		userMsgCahe:{},
		requestCharMsg : function(){
			Ext.Ajax.request({
				url: PATH + '/workdoing/UsersMessageBusinessAction.do',
				params: {m:'requestMsg',userId:LoginInfor.user.id},
				success: function(response){				
					eval("var userMsgModel = " + response.responseText);
					if(userMsgModel != null){
						Ext.ffc.UserMsg.viewUserMsg(userMsgModel);
					}
				}
			});
		},
		registMessageWindow:function(win){
		    this.msgWindowCache[win.userId] = win;
		},
		removeMessageWindow:function(win){
		    this.msgWindowCache[win.userId] = null;
		},
		viewUserMsg:function(userMsgModel){
			var userWin = this.msgWindowCache[userMsgModel.fromUserId];
			if(userWin){
			    this.msgWindowCache[userMsgModel.fromUserId].viewMsg(userMsgModel);
			}else{
				if(this.userMsgCahe[userMsgModel.fromUserId]){
				    this.userMsgCahe[userMsgModel.fromUserId].push(userMsgModel);
				}else{
				    this.userMsgCahe[userMsgModel.fromUserId] = [];
					this.userMsgCahe[userMsgModel.fromUserId].push(userMsgModel);
				}
			    var comp = Ext.getCmp('usersBusinessId');
				if(comp){
				   comp.setIconJump();
				}
				var departId = userMsgModel.fromUserDepart;
				var root = xxxtree.getRootNode();
				var arr = root.childNodes;
				for(var i = 0;i < arr.length ;i++ ){
					if(arr[i].id == departId){
						if(arr[i].isExpanded()){//如果已经展开，那么直接标示出谁发得消息就行了
						    Ext.ffc.UserMsg.biaoJiUserUnderDepart(arr[i],userMsgModel.fromUserId);				
						}else{//没有展开，就展开后再标示
							arr[i].on('expand',function(nr){
									Ext.ffc.UserMsg.biaoJiUserUnderDepart(nr,userMsgModel.fromUserId);
							});
							arr[i].expand(false);
						}
					    break;
					}
				}
			}
		},
	    biaoJiUserUnderDepart:function(nr,userId){
			var chArr = nr.childNodes;
			for(var j = 0;j<chArr.length;j++ ){
				if(chArr[j].id == userId){
					chArr[j].getUI().getTextEl().style.color='red';
					break;
				}
			}
		}
	};


	var UsersMessageWindow = Ext.extend(Ext.Window,{
		contract_list:null,
		selectForm2:null,
		tOContractTree:null,
		viewMsg:function(userMsgModel){
			var namestring =  '<div style="color:green;width:100%">' + userMsgModel.fromUserName + "  " + userMsgModel.sendDateString + '</div>';
			var message = "<div style='width:100%'>"+ userMsgModel.message + "</div>";
			this.messageRecord.getEl().createChild(namestring + message);
			if(this.messageRecord.getEl()){
			    this.messageRecord.getEl().dom.scrollTop = 1000;
			}
			
		},
		sendMsg:function(){
			var that = this;
			if(that.messageInput.getValue() == ''){
				return;
			}
			Ext.Ajax.request({
				url: PATH + '/workdoing/UsersMessageBusinessAction.do',
				params: {m:'sendMsg','toUserId':that.userId,'message':that.messageInput.getValue(),'toDepart':that.departId},
				success: function(response){		
					NoticeMessageUtil.sendMessage("Ext.ffc.UserMsg.requestCharMsg()",PATH + "/loginAction.do",function(){});
					that.messageInput.setValue('');
				}
			});
		},
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.messageRecord = new Ext.BoxComponent({tag:'div',style:'overflow-y:auto'});
			this.messageInput = new Ext.form.TextArea({
			    listeners:{
				    'specialkey':function(ar,ev){
					    if(ev.getKey() == ev.ENTER){
						    this.sendMsg();
						}
					},scope:this
				}
			});
			this.messageListStore = new Ext.data.Store({
				   remoteSort : true,
				   proxy: new Ext.data.HttpProxy({url:PATH + '/workdoing/UsersMessageBusinessAction.do?m=getMessageHistory&userId='+ _cfg.userId}), 
				   reader: new Ext.data.JsonReader({
				   root: 'items',  
				   totalProperty :'totalCount'
				 }, 
				 [ //JSON数据的映射
					{name: 'id',mapping:'id',type:'string'},
					{name: 'fromUserName',mapping:'fromUserName',type:'string'},
					{name: 'message',mapping:'message'},
					{name: 'sendDateString',mapping:'sendDateString',type:'string'}
				 ])
				});
			this.messageHistory = new Ext.grid.GridPanel({
									 ds : this.messageListStore,
									 store: this.messageListStore,
									 layout:'fit',
									 height:407,
									 columns: [
										{id:'id',header: "id", width: 160,  dataIndex: 'id',hidden:true},
										{header: "发送人", width: 60,   dataIndex: 'fromUserName'},
										{header: "消息内容", width: 150, dataIndex: 'message'},
										{header: "发送时间", width: 50, dataIndex: 'sendDateString'}
									 ],
									 viewConfig: {
										 forceFit: true,
									     autoFill:true
									 },
									 bbar: new Ext.PagingToolbar({
											pageSize: 10,
											store: this.messageListStore,
											displayInfo: true,
											displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
											emptyMsg: "没有记录"
										}) 
									});
			//this.messageListStore.load();
			UsersMessageWindow.superclass.constructor.call(this, {
				renderTo: Ext.getBody(),    
				resizable :false,
				maximizable:true,
				title:"与【" + this.userName + "】聊天窗口",                                                               
				height:358,                                                                     
				width:400,    
				 items : [
					{
						region: 'center',
						split: true,
						height : 200,
						minSize: 200,
						maxSize: 200,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : [this.messageRecord]
					},{
						region: 'south',
						split: true,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : [this.messageInput],
						tbar:['->',{                                                                    
							text : "聊天记录",                                                              
								handler : function() {
									if(this.getHeight() > 500){
									    this.setHeight(358);
									}else{
									    this.setHeight(558);
										this.messageListStore.load({params:{start:0}});
									}
								},scope : this                                                                
						}],
						bbar:['->',{                                                                    
							text : "发送(ctrl+enter)", 
							iconCls :'icon-add',
								handler : function() {    
									this.sendMsg();
								},scope : this                                                                
							}, {                                                                            
								text : "关闭",   
								iconCls :'out',
								handler : function() {           
								 
								 this.close();
								},scope : this                                                                
							 }]
					},{
						region: 'north',
						split: true,
						height : 200,
						minSize: 200,
						maxSize: 200,
						collapsible: true,
						layout: 'fit',
						margins: '-5 5 5 5',
						items : this.messageHistory
					}
				],
				listeners: {
					'close': function(){
						Ext.ffc.UserMsg.removeMessageWindow(this);
					},scope:this,
					'show' : function(){
					    if(Ext.ffc.UserMsg.userMsgCahe[this.userId]){
							var msgArr = Ext.ffc.UserMsg.userMsgCahe[this.userId];
							for(var i = 0;i < msgArr.length ;i++ ){
								this.viewMsg(msgArr[i]);
							}
							Ext.ffc.UserMsg.userMsgCahe[this.userId] = [];
						}
					},scope:this,
					'resize':function(win,width,height){
						this.messageHistory.setWidth(width);
						//this.messageHistory.setHeight(height);
					},scope:this
				}
				})
			}
		});
	var UserTreeLoader = Ext.extend(Ext.tree.TreeLoader, {
					'createNode' : function(node){
						if(node.leaf * 1 == 1){
						    node.iconCls = 'author-m';
						}
						return UserTreeLoader.superclass.createNode.call(this, node);
					}
		});
			 var xxxtree = new Ext.tree.TreePanel( {
				renderTo: '_users_business_div',
				autoScroll:true,
				autoHeight:true,
				animate:true,
				enableDD:true,
				containerScroll: true,
				border:false,
				rootVisible: false,
				root: new Ext.tree.AsyncTreeNode({id:'odatebroot',text:'模块'}),
				loader: new UserTreeLoader({
					dataUrl: PATH + '/workdoing/UsersMessageBusinessAction.do?m=getUsersNodes'
				}),
				listeners: {
					'dblclick': function(node,evt){
						if(!node.leaf){
							return ;
						}
						node.getUI().getTextEl().style.color='';
						var win = new UsersMessageWindow({userName:node.text,userId:node.id,departId:node.parentNode.id}).show();
						Ext.ffc.UserMsg.registMessageWindow(win);
					}
				}
			});
			xxxtree.getRootNode().expand(false);
 });
