  function setIconJumpFunction(){
		var obj = this;
		
		//window.setInterval(teset,500);
	    var task = {
			run: function(){
				if(obj.iconCls != ''){
					obj.setIconClass('');
				}else{
					obj.setIconClass(obj.iconDef);
				}
			},
			interval: 1000 //1 second
		}
		obj['jump'] = task; 
		Ext.TaskMgr.start(task);
 }
function setIconStopJumpFunction(){
	var obj = this;
	if(obj['jump']){
	    Ext.TaskMgr.stop(obj['jump']);
		obj.setIconClass(obj.iconDef);
	}
}
Ext.ffc.WorkDoingWindow = Ext.extend(Ext.Window, {  
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.itemsDef = new Ext.TabPanel({ 
		    region:'center', 
			//layout: 'fit',
			deferredRender:false, 
			autoScroll: true, 
			margins:'0 4 4 0', 
			activeTab:0, 
			items:[
				{id:'editBusinessId',title:'　',iconCls: 'x-edit',iconDef:'x-edit', autoLoad:{url:PATH + '/pages/work_doing_view/edit_business.jsp',scripts:true}, closable:false, autoScroll:true, setIconJump:setIconJumpFunction,setIconStopJump:setIconStopJumpFunction},
				{id:'auditBusinessId',title:'　',iconCls: 'x-commit',iconDef:'x-commit',autoLoad:{url:PATH + '/pages/work_doing_view/audit_business.jsp',scripts:true}, closable:false, autoScroll:true , setIconJump:setIconJumpFunction,setIconStopJump:setIconStopJumpFunction},
				{id:'dateTipBusinessId',title:'　',iconCls: 'icon-date',iconDef:'icon-date',autoLoad:{url:PATH + '/pages/work_doing_view/date_tip_business.jsp',scripts:true}, closable:false, autoScroll:true , setIconJump:setIconJumpFunction,setIconStopJump:setIconStopJumpFunction},
				{id:'usersBusinessId',title:'　',iconCls: 'icon-user',iconDef:'icon-user',autoLoad:{url:PATH + '/pages/work_doing_view/users_business.jsp',scripts:true}, closable:false, autoScroll:true , setIconJump:setIconJumpFunction,setIconStopJump:setIconStopJumpFunction},
				{id:'notepadBusinessId',title:'　',iconCls: 'icon-modify',autoLoad:{url:PATH + '/pages/work_doing_view/notepad_business.jsp',
					scripts:true}, closable:false, autoScroll:true,
					bbar:[{//不得已，将按钮写在此处
						text:'',
						iconCls : 'icon-add',
						listeners : {
							'click' : function() {
									new Ext.ffc.NotepadEditWindow({
										listeners : {
											'close' : function(){
												 var tabp = Ext.getCmp('notepadBusinessId');
												     tabp.load(tabp.autoLoad);
											}
										}
									}).show();
							}
						}
					},{
						xtype:'tbseparator'
					},{
						text:'',
						iconCls : 'icon-delete',
						listeners : {
							'click' : function() {
									var arr = gridCheckSele.getSelections();
									if(arr.length <= 0){
											Ext.Msg.alert("消息", "请选择要删除的数据!");
											return;
									}
									var ids = [];
									for(var i = 0;i < arr.length ;i++ ){
										ids.push(arr[i].id);
									}
									Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前所选中的数据!', function(btn){
										Ext.Ajax.request({
											method: "post",
											params: {'ids': ids,m:'deleteNotepad'},
											url: PATH + '/notepad/NotepadAction.do',
											success: function(response){
												Ext.MessageBox.alert('系统提示', '删除成功.', function(){});
												contractListStore.reload();
											}
										});
									});
							}
						}
				},{
					xtype:'tbseparator'
				},{
							text:'',
							iconCls : 'icon-modify',
							listeners : {
								'click' : function() {
									var arr = gridCheckSele.getSelections();
										if(arr.length <= 0){
											Ext.Msg.alert("消息", "请选择要修改的数据!");
											return;
										}
										if(arr.length > 1){
											Ext.Msg.alert("消息", "只能选择一条数据进行修改操作!");
											return;
										}
										
									new Ext.ffc.NotepadEditWindow({defData:arr[0].data,
									 listeners : {
											'close' : function(){
												contractListStore.reload();
											},scope:this
										}	
									}).show();
								}
							}
						}]
				}
			]
	    }); 
		Ext.ffc.WorkDoingWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			maximizable :false,
			minimizable:true ,
			width : 250,
			height : 500,
			title :  '待办工作',
			layout :  'border',
			closeAction : 'hide',
			items : this.itemsDef,
			listeners:{
				minimize:function(){
					if(this.minimizable){
						this.hide("juzixiangshui");
						this.minimizable = true; 
					}
				},scope:this
			}
		});
	}
});  
