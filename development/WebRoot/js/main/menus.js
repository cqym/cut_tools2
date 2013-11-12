Ext.onReady(function(){

//Ext.chart.Chart.CHART_URL = PATH + '/extjs/resources/charts.swf';

Ext.ffc.QuickTips = {
	win : null,
	show : function(x,y,msg){
		if(Ext.ffc.QuickTips.win != null) Ext.ffc.QuickTips.win.close();
		Ext.ffc.QuickTips.win=new Ext.Window({
			width:290,
			height:170,
			closeAction:'close',
			constrain:false,
			html : msg,
			pageX : x - 200,
			pageY : y * 1 + 20,
			resizable :true,
			autoScroll :true 
		});
		Ext.ffc.QuickTips.win.show(); 
	},
	close: function(){
	    if(Ext.ffc.QuickTips.win != null) Ext.ffc.QuickTips.win.close();
	}
}

Ext.ffc.NoticeMsg = {
	waitOfficeButtton : null,
	waitWorks : null,
	requestWaitWorks : function(){
		
			Ext.Ajax.request({
				url: PATH + '/WaitWorksInforAction.do',
				params: {ffc:'requestInfos'},
				success: function(response){
					
					eval("var temp = " + response.responseText);
					Ext.ffc.NoticeMsg.waitWorks = temp;
					var rt = [];
					var flashFlag = false;
					var preStr = "<div style='font-size:9pt;'>现有 [";
					if(temp.waitorderConCount > 0){
						flashFlag = true;
						rt.push(preStr , temp.waitorderConCount , "] 份合同，需要编制合同订单!</div>");
					}
					if(temp.waitContractQuoCount > 0){
						flashFlag = true;
						rt.push(preStr , temp.waitContractQuoCount , "] 份报价单，需要编制合同!</div>");
					}
					if(temp.waitSelfOrderConCount > 0){
						flashFlag = true;
						rt.push(preStr , temp.waitSelfOrderConCount , "] 份合同，需要加工订单!</div>");
					}
					if(temp.waitExpectedQuoCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.waitExpectedQuoCount , "] 份预定报价单，需要编制预定订单!</div>");
					}
					if(temp.waitExpectedQuo4SelfOrderCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.waitExpectedQuo4SelfOrderCount , "] 份预定报价单，需要编制预定加工订单!</div>");
					}
					if(temp.waitTryToolsQuoCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.waitTryToolsQuoCount , "] 份试刀申请，需要编制试刀订单!</div>");
					}
					if(temp.waitTryToolsQuo4SelfOrderCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.waitTryToolsQuo4SelfOrderCount , "] 份试刀申请，需要编制试刀加工订单!</div>");
					}
					if(temp.contractCountCouldUploadFile > 0){
						//flashFlag = true;
						rt.push(preStr , temp.contractCountCouldUploadFile , "] 份合同，需要上传附件!</div>");
					}
					if(temp.expectedQuo2QuoCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.expectedQuo2QuoCount , "] 份预定报价单，需要转正式报价单!</div>");
					}
					if(temp.tryTools2DeliveryCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.tryTools2DeliveryCount , "] 份试刀申请，需要交货!</div>");
					}
					if(temp.tryTools2UploadReportCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.tryTools2UploadReportCount , "] 份试刀申请，需要上传试刀报告!</div>");
					}
					if(temp.tryTools2UploadApplyCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.tryTools2UploadApplyCount , "] 份试刀申请，需要上传试刀申请!</div>");
					}
					if(temp.wait2PlanSelfOrderCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.wait2PlanSelfOrderCount , "] 份加工订单，需要编制采购计划!</div>");
					}
					if(temp.wait2PlanOrderPlanCount > 0){
						//flashFlag = true;
						rt.push(preStr , temp.wait2PlanOrderPlanCount , "] 份计划，需要编制材料采购订单!</div>");
					}
					if(temp.waitAuditCount > 0){
						flashFlag = true;
						rt.push(preStr , temp.waitAuditCount , "] 份单据，需要审批!</div>");
					}else{
						with(Ext.ffc.auditWorkButton.btnEl.dom.style){
							backgroundImage = '';
						}
					}
					if(flashFlag){
						with(Ext.ffc.auditWorkButton.btnEl.dom.style){
							backgroundImage = 'url(' + PATH + '/images/4.gif)';
							backgroundPosition = 'left top';
							backgroundRepeat = 'no-repeat';
							var t = Ext.ffc.auditWorkButton.btnEl.dom.offsetWidth;
							if(t < 80){
							    width =  t + 14 + 'px';
							}
							textAlign =  'right';
						}
					}
					var xy = Ext.ffc.NoticeMsg.waitOfficeButtton.getPosition();
					if(rt.length > 0){
						var str = rt.join("\n");
					    //Ext.ffc.QuickTips.show(xy[0],xy[1],str); 2011-10-31 暂时不用此项提示功能
						wdw.setPosition(xy[0]-130,xy[1] + 30);
						wdw.show("juzixiangshui");
						if((window.webkitNotifications) && (window.webkitNotifications.checkPermission() == 0)){
							if(Ext.ffc.notification && Ext.ffc.notification != null){
							    Ext.ffc.notification.cancel();
							}
							Ext.ffc.notification = webkitNotifications.createHTMLNotification(    
							  PATH + '/pages/wait_works_infor.jsp'
							);    
							//Ext.ffc.notification.show(); //2011-10-31 暂时不用此项提示功能
						}
					}
				}
			});
	}
}


Ext.ffc.sendMsg2Server = function(){
	NoticeMessageUtil.sendMessage("Ext.ffc.NoticeMsg.requestWaitWorks()",PATH + "/loginAction.do",function(){});
}


Ext.ffc.UserInforEditWindow = Ext.extend(Ext.Window, {  
		constrainHeader : true,
		width : 253,
		height : 243,
		modal : true,
		title :  '修改密码',
		layout: 'fit',
		resizable :false
});
var tb = new Ext.Toolbar();
    //tb.render('toolbar');
Ext.Ajax.request({
		url: PATH + '/loginAction.do',
		params: {ffc:'getUserModules'},
		success: function(response){
			eval("var temp=" + response.responseText);
			if(temp){
				for(var i = 0 ; i < temp.length ;i++){
					if(temp[i].text == '首页权限'){
					  continue;	
					}
					if(temp[i].url.match(/\/*/) != ''){
			            tb.add({id:temp[i].id,text:temp[i].text,url:PATH + temp[i].url,enableToggle :true,
							toggleHandler: function(){
								addTab(this.text, this.url);
							}
						});
					}else{
					    tb.add({id:temp[i].id,text:temp[i].text,menu:addMenu(temp[i].children)});
					}
					if(i < temp.length - 1 ){
						tb.add('-');
					}
				}
				tb.add('->');

				Ext.Ajax.request({
							sync:true,
							url: PATH + '/manage/worktrust/WorkTrustAction.do',
							params: {m:'getOrToRight'},
							success: function(response){
								eval("var temp=" + response.responseText);
								var grantRightUserName = '';
								for(var i=0;i<temp.grantRightUserList.length ;i++ ){
								    grantRightUserName += "[<font color=red>" + temp.grantRightUserList[i].authorizePerson + '</font>]';
								}
								var tipString = grantRightUserName == '' ? '' : grantRightUserName + "已将工作委托给您办理!";
								var toolTipString = temp.getRightUserName == '' ? '单击对审批工作进行委托<br/>' + tipString : '单击收回工作委托';
								var textString = temp.getRightUserName == '' ? '工作委托' : '工作已委托给 ' + temp.getRightUserName;
									var buttonTp = new Ext.Button(
									{
										id: "3232323",
										tooltip : toolTipString,
										text : textString,
										tooltipType:'qtip',
										handler : function(){
											if(buttonTp.getText() == '工作委托'){
												Ext.ffc.selectUser(function(record){
															var userId = record.get('id');
															var trueName = record.get('trueName');
															Ext.MessageBox.confirm('系统提示', '请确认是否要将审批工作委托给' + trueName + '!', function(btn){
																if(btn != 'yes'){return ;}
																	Ext.Ajax.request({
																		method: "post",
																		params: { m:'grandRight','getRightUserId' : userId,'authorUserId' : LoginInfor.user.id},
																		url: PATH + "/manage/worktrust/WorkTrustAction.do",
																		success: function(response){
																			eval("var temp = " + response.responseText);
																			if(temp.msg == null || temp.msg == 'null'){
																				Ext.Msg.alert("消息", "您已经将工作委托给" + trueName + "！");
																				buttonTp.setText('工作已委托给' + trueName);
																				buttonTp.setTooltip('单击收回工作委托');
																			}else{
																			    Ext.Msg.alert("消息", temp.msg);
																			}
																		}
																	});
														   });	
													});
											}else{
												Ext.MessageBox.confirm('系统提示', '请确认是否要收回工作委托!', function(btn){
													if(btn != 'yes'){return ;}
														Ext.Ajax.request({
															method: "post",
															params: { m:'cancelRight','authorUserId' : LoginInfor.user.id},
															url: PATH + "/manage/worktrust/WorkTrustAction.do",
															success: function(response){
																Ext.Msg.alert("消息", "您已经将委托给" + str + "的工作收回！");
																buttonTp.setText('工作委托');
																buttonTp.setTooltip('单击对审批工作进行委托<br/>' + tipString);
															}
														});
												});	
											}
										}
									}	
									);
									tb.add(buttonTp);
								
								Ext.QuickTips.init();
							}
						});
				
				tb.add({
					tooltip :'单击修改密码',
					text:'登录人:' + LoginInfor.user.departName + "-" + LoginInfor.user.trueName,
					handler : function(){
						var userInfor = LoginInfor.user;
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
											value:userInfor.userName
										},{
											fieldLabel: '真实姓名',
											name: 'trueName',
											readOnly:true,
											value:userInfor.trueName
										},{
											fieldLabel: '所属部门',
											name: 'departName',
											readOnly:true,
											value:userInfor.departName
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
								var userEditWin = new Ext.ffc.UserInforEditWindow({
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
												params: {ffc:'updateUserInfor',content:Ext.encode({id:userInfor.id,password:vs.password})},
												success: function(response){
													Ext.Msg.alert("消息", "保存成功!");
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
				}); 
				Ext.ffc.auditWorkButton = tb.add({
					id: "juzixiangshui",
					text:'待办工作',
					enableToggle :true,
					listeners : {
						afterrender : function( component ){
							Ext.apply(Ext.ffc.NoticeMsg,{waitOfficeButtton : component});
							Ext.ffc.sendMsg2Server();
						} 
					},
					handler : function(){
						wdw.show("juzixiangshui");
						/*
						Ext.Ajax.request({
							url: PATH + '/manage/audit/auditInforMangeAction.do',
							params: {ffc:'findWaitAuditTypeInfor'},
							success: function(response){
									eval("var temp = " + response.responseText);
									var w = new Ext.ffc.WaitOfficeWindow({auditTypes:temp,waitWorks:Ext.ffc.NoticeMsg.waitWorks});
									w.show();
									//w.loadData();
									Ext.ffc.QuickTips.close();
							}
						});*/
					}
				});
				viewMenu();
			}
		}
	});

function addMenu(arr){
    var childrenList = [];
	for(var i = 0 ; i < arr.length ;i++){
		if(arr[i].url.match(/\/*/) == ''){
			childrenList.push({id: arr[i].id,text:arr[i].text,menu: {items:addMenu(arr[i].children)}});
		}else{
		    childrenList.push({
					id: arr[i].id,
					text:arr[i].text,
					url:PATH + arr[i].url,
					enableToggle :true,
					handler : function(){
						//alert(this.text+","+ this.url);
						addTab(this.text, this.url);
					}
				});
		}
	    
	}
	return childrenList;
}

var actionPanel = new Ext.Panel({ 
		region:'north', 
		layout: 'fit',
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
			//layout: 'fit',
			deferredRender:false, 
			autoScroll: true, 
			margins:'0 4 4 0', 
			activeTab:0, 
			items:[
				{ id:'tab1', contentEl:'tabs', title: '主工作区', closable:false, autoScroll:true,
				  autoLoad: {url: PATH + "/pages/main_work_view.jsp",scripts:true}
			    }
			]
	}); 

	addTab = function (tabTitle, targetUrl,pclosable){ 
		if(typeof(pclosable) == 'undefined'){
		    pclosable = true;
		}
		var allTabs = tabPanel.items;
		var theTab = null;
		allTabs.find(function(obj){
			if(obj.autoLoad && obj.autoLoad.url == targetUrl){
			    theTab = obj;
			}
		});

		if(theTab != null){//如果已经存在
			tabPanel.activate(theTab);
		}else{
			tabPanel.add({ 
				title: tabTitle, 
				iconCls: 'tabs', 
				autoLoad: {url: targetUrl, 
					callback: function(){
							var s = "";
							for (var n=0; n< arguments.length; n++){
								  s += arguments[n];
								  s += " ";
							}
							//alert(s);
					}, scope: this,scripts:true}, 
				closable:pclosable,
				layout: 'fit',
				listeners : {
					close : function(p){
					    p.destroy();
					},
					resize  : function( component ,  adjWidth,  adjHeight,  rawWidth,  rawHeight  ){
						if(component.body){
							var s = component.body.last();
							if(s){
								//Ext.ffc.util.debug(s);
								//alert(s.getWidth() + "[,]" + s.getHeight());
								//alert(adjWidth + "," + adjHeight);
								//s.setWidth(adjWidth);
								//s.setHeight(adjHeight);
							}
						}
					}
				}
			}).show(); 
		}
	}
//addTab("主工作区",PATH + "/pages/audit_infor.jsp",false);
function viewMenu(){
	// 配置视图viewport 
	viewport = new Ext.Viewport({ layout:'border', items:[actionPanel,tabPanel]});
}

var wdw = new Ext.ffc.WorkDoingWindow();	

});
Ext.ffc.autoFlush = function (){
	Ext.Ajax.request({
		url: PATH + '/loginAction.do',
		params: {ffc:'autoFlush'},
		success: function(response){
				
		}
	});
}    
window.setInterval("Ext.ffc.autoFlush()",1000 * 60);  
var EventMger = new Ext.util.Observable();// 

