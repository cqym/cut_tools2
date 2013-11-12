Ext.ffc.WaitReturnCache = [];
Ext.ffc.waitReturnBt = null;
Ext.ffc.msgManager = function(){
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('br').pause(5).ghost("br", {remove:true});
        },

        init : function(){
            var lb = Ext.get('lib-bar');
            if(lb){
                lb.show();
            }
        }
    };
}();

Ext.ffc.FlowThemeWindow = Ext.extend(Ext.Window, {
    constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			var forum = _cfg.forum;
			this.theForm = new Ext.FormPanel({
				frame:true,
				items: [
				    {xtype: 'hidden',name:'parentId',value:forum.id},
					{xtype:'textfield',fieldLabel: '待处理单号/产品',name: 'title',anchor:'90%',value:forum.title,readOnly : true},
					{xtype:'label',text:"问题描述：" +  forum.content},
					{xtype:'textfield',fieldLabel: '发起人',name: 'userName',anchor:'90%',value:forum.userName,readOnly : true},
					{xtype:'datefield',fieldLabel: '要求进度',name: 'processDate',anchor:'40%',format:'Y-m-d',value:new Date(forum.processDate.time),readOnly : true},
					{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'90%',value:forum.memo,readOnly : true},	
					{   fieldLabel: '回复：',
					    xtype: 'htmleditor',
		                name: 'content',
		                allowBlank:false
					}
				]
			}); 
			Ext.ffc.FlowThemeWindow.superclass.constructor.call(this, {
				layout:'fit',
				width:700,
				height:400,
				items:this.theForm,
				buttons: [{
		            text: '保存',
				    handler:function(){
						var form = this.theForm.getForm();
						if(!form.isValid()){return;};
						Ext.Ajax.request({
							url: PATH + '/manage/forum/ForumFlowAction.do',
							params: {m:'followTheme',forum:Ext.encode(form.getValues())},
							success: function(response){
								if(response.responseText == 'true'){
									Ext.Msg.show({title : '系统提示', msg : "保存成功！", buttons : Ext.Msg.OK, width : 200 });
									for(var i = 0,len=Ext.ffc.WaitReturnCache.length ;i < len ;i++ ){
									    if(Ext.ffc.WaitReturnCache[i].id == forum.id){
											Ext.ffc.WaitReturnCache.splice(i);
											if(Ext.ffc.waitReturnBt){
											    var it = Ext.ffc.waitReturnBt;
												it.setText('待回复事项(' + Ext.ffc.WaitReturnCache.length + ')');
											}
											break;
										}
									}
									this.close();
								}else{
								    Ext.Msg.show({title : '系统提示', msg : response.responseText, buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
								}
							},scope:this
						});
					},scope:this
				},{
		            text: '关闭',
				    handler:function(){
                        this.close();
					},scope:this
				}]
		})
	}
});

Ext.ffc.ForumMainEditWindow = Ext.extend(Ext.Window, {
		loadDataById:function(_id){
			Ext.Ajax.request({
				url: PATH + '/manage/forum/ForumAction.do',
				params: {m:"getFroumById",id:_id},
				success: function(response){
					eval("var formObj=" + response.responseText);
					this.theForm.getForm().setValues(formObj);
					this.theForm.getForm().setValues({processDate:new Date(formObj.processDate.time)});
				},scope:this
			});
		},
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.theForm= new Ext.FormPanel({
				frame:true,
				items: [
					{xtype:'textfield',fieldLabel: '待处理单号/产品',name: 'title',anchor:'90%',allowBlank:false},
					{   fieldLabel: '信息内容',
					    xtype: 'htmleditor',
		                name: 'content',
		                allowBlank:false
					},
					{xtype:'datefield',fieldLabel: '要求进度',name: 'processDate',anchor:'40%',format:'Y-m-d',allowBlank:false},
					new Ext.zhj.OwnContactPersonCombox({fieldLabel: '接收人',hiddenName: 'acceptUserId',valueField:'id',autoLoad:true}),
					new Ext.zhj.OwnContactPersonCombox({fieldLabel: '抄送',hiddenName: 'copyUserId',valueField:'id',autoLoad:true}),
					{xtype:'textfield',fieldLabel: '备注',name: 'memo',anchor:'90%'},
				    {xtype: 'hidden',name:'id'}
				]
			}); 
			if(this._id){
				this.loadDataById(this._id);
			}
			Ext.ffc.ForumMainEditWindow.superclass.constructor.call(this, {
				layout:'fit',
				width:700,
				height:520,
				items:this.theForm,
				buttons: [{
		            text: '保存',
				    handler:function(){
						var form = this.theForm.getForm();
						if(!form.isValid()){return;};
						var action = 'addTheme';
						if(form.getValues().id && form.getValues().id.length > 1){
							action = "update";
						}
						alert(Ext.encode(form.getValues()));
						Ext.Ajax.request({
							url: PATH + '/manage/forum/ForumAction.do',
							params: {m:action,forum:Ext.encode(form.getValues())},
							success: function(response){
								if(response.responseText == 'true'){
									Ext.Msg.show({title : '系统提示', msg : "保存成功！", buttons : Ext.Msg.OK, width : 200 });
									this.close();
								}else{
								    Ext.Msg.show({title : '系统提示', msg : response.responseText, buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
								}
							},scope:this
						});
					},scope:this
				},{
		            text: '关闭',
				    handler:function(){
                        this.close();
					},scope:this
				}]
			});
		}
});

Ext.ffc.ForumMainGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.forumStore = new Ext.data.Store({
					     remoteSort : true,
					     autoLoad:true,
						 proxy: new Ext.data.HttpProxy({url:PATH + '/manage/forum/ForumAction.do?m=forumThemeList&forumType=0&limit=20'}), 
						 reader: new Ext.data.JsonReader({
						 root: 'items',  
						 totalProperty :'totalCount'
					}, 
					['id','title','userId','userName','editDate','status','parentId','forumType','content',"acceptUserName","memo","processDate"]
				 )
			});

			this.expander = new Ext.ux.grid.RowExpander({
				beforeExpand : function(record, body, rowIndex){
				    if(this.fireEvent('beforeexpand', this, record, body, rowIndex) !== false){
						if(this.tpl && this.lazyRender){
							Ext.Ajax.request({
										url: PATH + '/manage/forum/ForumFlowAction.do',
										params: {m:'getTheThemeFlows',id:record.id},
										success: function(response){
											eval("var flows = " + response.responseText);
											var data = record.data;
											for(var i = 0;i < flows.length ; i++){
												var editDateString = Ext.util.Format.date(new Date(flows[i].editDate.time),'Y-m-d H:m:s');
												data.content += '<br/><hr/><b>' + flows[i].userName + '</b>&nbsp;&nbsp;'+editDateString+'&nbsp;&nbsp;回复:<br/>' + flows[i].content +"<br/>" + '<hr/>';
											}
											
											body.innerHTML = this.getBodyContent(record, rowIndex);
									},scope:this
									});
						}
						return true;
					}else{
						return false;
					}
				},
				tpl : new Ext.Template(
					'<div style="width:400px;">{content}</div>'
				)
			});
			Ext.ffc.ForumMainGrid.superclass.constructor.call(this, {
							ds : this.forumStore ,
							store: this.forumStore ,
							loadMask: true,
							plugins: this.expander,
							tbar:[{
								iconCls : 'icon-add',
							    text:'新建主题',
								listeners : {
								'click' : function() {
									var _this = this;
									new Ext.ffc.ForumMainEditWindow({
									    listeners : {
										    'close':function(){
											    _this.getStore().reload();
											}
										}
									}).show();
								},scope:this
							   }
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-modify',
							    text:'修改主题',
								listeners : {
								'click' : function() {
									var _this = this;
									if(this.getSelectionModel().getSelections().length == 0){
									    Ext.Msg.show({title : '系统提示', msg : "请选择一个主题进行修改！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return ;
									}
									var data = this.getSelectionModel().getSelections()[0];
									if(data.data.userId != LoginInfor.user.id){
   									    Ext.Msg.show({title : '系统提示', msg : "只有发起人才可以修改！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return;
									}									
									new Ext.ffc.ForumMainEditWindow({
										_id:data.id,
									    listeners : {
										    'close':function(){
											    _this.getStore().reload();
											}
										}
									}).show();
								},scope:this}
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-delete',
							    text:'删除主题',
								listeners : {
								'click' : function() {
									var _this = this;
									if(this.getSelectionModel().getSelections().length == 0){
									    Ext.Msg.show({title : '系统提示', msg : "请选择一个主题进行删除！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return ;
									}
									var data = this.getSelectionModel().getSelections()[0];
									if(data.data.userId != LoginInfor.user.id){
   									    Ext.Msg.show({title : '系统提示', msg : "只有发起人才可以删除！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return;
									}
									var _id = data.id;
									Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中主题!', function(btn){
										if(btn != 'yes'){return ;}
												Ext.Ajax.request({
													method: "post",
													url: PATH + '/manage/forum/ForumAction.do',
													params: {m:"deleteById",id:_id},
													success: function(response){
														_this.getStore().reload();	
													}
												});
									});
								},scope:this}
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-contract-end',
							    text:'终结主题',
								listeners : {
								'click' : function() {
									var _this = this;
									if(this.getSelectionModel().getSelections().length == 0){
									    Ext.Msg.show({title : '系统提示', msg : "请选择一个主题进行终结！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return ;
									}
									var data = this.getSelectionModel().getSelections()[0];
									if(data.data.userId != LoginInfor.user.id){
   									    Ext.Msg.show({title : '系统提示', msg : "只有发起人才可以终结！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return;
									}
									var _id = data.id;
									Ext.MessageBox.confirm('系统提示', '请确认是否要终结当前所选中主题!', function(btn){
										if(btn != 'yes'){return ;}
										Ext.Ajax.request({
											method: "post",
											url: PATH + '/manage/forum/ForumAction.do',
											params: {m:"endById",id:_id},
											success: function(response){
												_this.getStore().reload();	
											}
										});
									});
								},scope:this}
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-detail',
							    text:'待回复事项',
								listeners : {
								'click' : function() {
									var _this = this;
									if(Ext.ffc.WaitReturnCache.length == 0){
									    Ext.Msg.show({title : '系统提示', msg : "您当前没有需要回复的事项！", buttons : Ext.Msg.OK, width : 200, icon : Ext.MessageBox.ERROR });
										return ;
									}
									var ftWindow = new Ext.ffc.FlowThemeWindow({forum:Ext.ffc.WaitReturnCache[0]});
									ftWindow.show();
								},scope:this}
							  }
								  
							  ],
						    columns:[
								 {dataIndex: 'id',hidden:true},
  								 {dataIndex: 'userId',hidden:true},
							     this.expander,
							     {header: "发起时间", width: 80,   dataIndex: 'editDate',
									 renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
										return Ext.util.Format.date(new Date(value.time),'Y-m-d');
								 }},
								 {header: "发起人", width: 60,   dataIndex: 'userName'},
								 {header: "待处理单号/产品", width: 180,   dataIndex: 'title'},
								 //{header: "信息内容", width: 300,   dataIndex: 'content'},
								 {header: "要求进度", width: 80,   dataIndex: 'processDate',
									 renderer:function(value){
									    if(!value || !value.time){
											return '';
										}
										return Ext.util.Format.date(new Date(value.time),'Y-m-d');
								 }},
								 {header: "状态", width: 60,   dataIndex: 'status',
									 renderer:function(value){
									    if(value == 0){
											return '<span style="color:red">待处理</span>';
										}else if(value == 1){
											return '<span style="color:green">终结</span>';
										}else if(value == 2){
										    return '<span style="color:green">已受理,处理中...</span>';
										}
										return '待处理';
								 }},
								 {header: "接收人", width: 60,   dataIndex: 'acceptUserName'},
							     {header: "备注", width: 200,   dataIndex: 'memo'}
							],
							bbar: new Ext.PagingToolbar({
								pageSize: 8,
								store: this.forumStore,
								displayInfo: true,
								displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg: "没有记录"
							}),
							listeners : {
								'afterrender' : function() {
									Ext.Ajax.request({
										url: PATH + '/manage/forum/ForumFlowAction.do',
										params: {m:'getWaitReturnForumMsg'},
										success: function(response){
											eval("Ext.ffc.WaitReturnCache = " + response.responseText);
											var items = this.getTopToolbar().items.items;
											for(var i = 0,len = items.length ;i < len ;i++ ){
											    if(items[i].getText && items[i].getText() == '待回复事项' ) {
													Ext.ffc.waitReturnBt = items[i];
													items[i].setText(items[i].getText() + '(' + Ext.ffc.WaitReturnCache.length + ')');
												}
											}
											if(Ext.ffc.WaitReturnCache.length > 0){
											    Ext.ffc.msgManager.msg('系统提示：',"在信息交互里，您有("+Ext.ffc.WaitReturnCache.length+")条事项待处理。");
											}
										},scope:this
									});
								},scope:this
							}
			})
		}
	});

