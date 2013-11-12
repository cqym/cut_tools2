/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
// Ext.state.Manager.setProvider(
//            new Ext.state.SessionProvider({state: Ext.appState}));

Ext.onReady(function(){
	
    var tb = new Ext.Toolbar();
    //tb.render('toolbar');
     
    tb.add(
         {
        text: '模块管理',
        enableToggle: true,
        toggleHandler: function(){
		    addTab('模块管理', PATH + '/pages/manage/modules.jsp');
		},
		//onItemModule,
        pressed: false
    },'-', {
        text: '角色管理',
        enableToggle: true,
        toggleHandler: onItemRoles,
        pressed: false
    },'-',{
        text: '组织机构',
        enableToggle: true,
        toggleHandler: onItemDepart,
        pressed: false
    },'-',{
        text: '用户管理',
        enableToggle: true,
        toggleHandler: onItemUsers,
        pressed: false
    },'-', {
        text: '审批管理',
        enableToggle: true,
        toggleHandler: onItemAudit,
        pressed: false
    },'-', {
        text: '管理员密码修改',
        enableToggle: true,
        toggleHandler: onUpdateAdminPass,
        pressed: false
    },'-', {
        text:'单据管理',
        //iconCls: 'bmenu',  // <-- icon
        menu : new BillMenu()  // assign menu by instance
    },'-', {
        text:'工作委托',
		enableToggle: true,
        toggleHandler : onItemWorkTrust,
		pressed: false
    });

   //tb.doLayout();
	function onUpdateAdminPass(){
		var form = new Ext.form.FormPanel({
			labelWidth: 75, 
			frame:true,
			bodyStyle:'padding:5px 5px 0',
			width: 100,
			defaults: {width: 100},
			defaultType: 'textfield',
			items: [{
					fieldLabel: '用户名',
					name: 'userName',
					readOnly:true,
					value:'admin'
				},{
					fieldLabel: '新密码',
					inputType :'password',
					name: 'password',
					allowBlank:false,
					maxLength:10,
					maxLengthText:'密码长度最大为10个字符'
				},{
					fieldLabel: '重复新密码',
					name: 'repassword',
					inputType :'password',
					allowBlank:false
				}
			]
		});
		var userEditWin = new Ext.Window({
			constrainHeader : true,
			width : 253,
			height : 243,
			modal : true,
			title :  '修改密码',
			layout: 'fit',
			resizable :false,
			items: form,
			buttons: [{
				text: '保存',
				handler:function(){
					var vs = form.getForm().getValues();
					if(!form.form.isValid()){return;};
					if(vs.password != vs.repassword){
						Ext.Msg.alert("消息", "您两次输入密码不同,请重新输入!");
						return ;
					}
					Ext.Ajax.request({
						url: PATH + '/manage/user/usersManageAction.do',
						params: {ffc:'updateUserInfor',content:Ext.encode({id:1,password:vs.password})},
						success: function(response){
							Ext.Msg.alert("消息", "密码修改成功!");
							userEditWin.close();
						}
					});
				}
			},{
				text: '关闭',
				handler:function(){
					userEditWin.close();
			}
		}]}).show();

	}
    var actionPanel = new Ext.Panel({ 
		id:'action-panel', 
		region:'north', 
		split:false, 
		collapsible: false, 
		collapseMode: 'mini', 
		width:200, 
	    height:27, 
		minWidth: 150, 
		border: false,
		baseCls:'x-plain', 
		items: [tb]
    });

	var tabPanel = new Ext.TabPanel({ 
		    region:'center', 
			deferredRender:false, 
			autoScroll: true, 
			margins:'0 4 4 0', 
			activeTab:0, 
			items:[{ id:'tab1', contentEl:'tabs', title: '主工作区', closable:false, autoScroll:true }] 
	}); 
	// 配置视图viewport 
	viewport = new Ext.Viewport({ layout:'border', items:[actionPanel,tabPanel]});

	function addTab(tabTitle, targetUrl){ 
		tabPanel.add({ 
			title: tabTitle, 
			iconCls: 'tabs', 
			autoLoad: {url: targetUrl, callback: this.initSearch, scope: this,scripts:true}, 
			closable:true 
		}).show(); 
	}
});