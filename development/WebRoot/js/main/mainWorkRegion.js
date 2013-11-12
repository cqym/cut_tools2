Ext.onReady(function(){
//权限
 function getConfig() {
	var modules = LoginInfor.modules
	var _configStr = "{";
	for(var i = 0; i < modules.length; i++) {
		var module = modules[i];
		if("013" == module.id) {
			var childModule = module.children;
					if(childModule.length > 0) { 
						for(var k = 0; k < childModule.length; k++) {
							if(k != childModule.length-1)
								_configStr += childModule[k].url + ",";
							else 
								_configStr += childModule[k].url + "}"
						}
					} else {
						_configStr += "}";
					}
			}
		}
		if(_configStr.length == 1) _configStr += "}";
	return Ext.decode(_configStr);
}

var _config = {isReportModifyHide : true,isFileUploadHide:true,isFileDeleteHide:true}

Ext.apply(_config, getConfig());

function requestLastReportContent(report){
	Ext.Ajax.request({
				url: PATH + '/manage/mainPage/CompanyReportAction.do',
				params: {m:'getReportLast'},
				success: function(response){
					eval('var t = '+ response.responseText);
					if(!report){
					    return;	
					}
					var arr= report.findByType('label');
					if(arr.length > 0){
					    arr[0].setText(t.content,false);	
					    report.obj = t;
					}
				}
				});
	}
	
	Ext.ffc.reportViewPanel = Ext.extend(Ext.Panel, {
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			if(!_config.isReportModifyHide){
				this.bbar = [{
			      iconCls : 'icon-modify',
			      hidden:_config.isReportModifyHide,
				    handler:function(){
			    	   var reportP =  this.ownerCt;
				       reportP.remove(this);
				       var f = new Ext.ffc.editReportForm();
				       reportP.add(f);
				       reportP.doLayout(true);
				       if(this.obj){
				           f.getForm().setValues(this.obj);
				       }
			    	},scope:this
				 }];
			}
			Ext.ffc.reportViewPanel.superclass.constructor.call(this, {
				items:{xtype:'label',name:'content'},
				listeners : {
				    'afterlayout':function(){
				      requestLastReportContent(this);
				    },scope:this
			  }	
			})
		}
  });

Ext.ffc.editReportForm = Ext.extend(Ext.FormPanel, {
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			Ext.ffc.editReportForm.superclass.constructor.call(this, {
		        labelWidth: 0, 
		        frame:true,
		        layout:'fit',
		        defaultType: 'textfield',
		        items: [/*{
		                fieldLabel: '标 题',
		                name: 'title',
										width:500,
										hidden:true,
		                allowBlank:false
		            },*/{
						xtype: 'htmleditor',
		                name: 'content',
		                allowBlank:false
					},{xtype: 'hidden',name:'id',value:''}
		        ],
		
		        bbar: [{
		        iconCls : 'icon-submit',
		        text: '保存',
				    handler:function(){
						var form = this.getForm();
						if(!form.isValid()){return;};
						 var action = 'addReport';
						 if(form.getValues().id && form.getValues().id.length > 0){
							 action = 'updateReport';
						 }
						var _this = this;
					  Ext.Ajax.request({
						url: PATH + '/manage/mainPage/CompanyReportAction.do',
						params: {m:action,companyReport:Ext.encode(form.getValues())},
						success: function(response){
							if(response.responseText == 'success'){
							   var reportP =  _this.ownerCt;
					       reportP.remove(_this);
					       reportP.add(new Ext.ffc.reportViewPanel());
					       reportP.doLayout(true);
					       requestLastReportContent();
							}else{
							    Ext.Msg.alert("系统提示", "保存失败!\n" + response.responseText);
							}
						}
						});
					},scope:this
		        }
		        ]
		  })
		}
  });
var reportViewWin = new Ext.Window({
				title: '公司公告',
				closable:true,
				maximizable :true,
				closeAction:'hide',
				width:870,
				height:420,
				plain:true,
				modal : true,
       layout: 'fit'//,
				//items: reportViewPanel
			});



var reportEditWin = new Ext.Window({
				title: '公司公告',
				closable:true,
				maximizable :true,
				width:870,
				height:420,
				plain:true,
				modal : true,
        layout: 'fit',
				items: new Ext.ffc.editReportForm()
			});

var reportListStore = new Ext.data.Store({
	   remoteSort : true,
	   autoLoad:true,
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/mainPage/CompanyReportAction.do?m=findReports'}), 
	   reader: new Ext.data.JsonReader({
		    root: 'items',  
		    totalProperty :'totalCount'
			}, 
			['id','title','editDate','content','editUserName','editUserId']
		 )
	});

var reportGrid = new Ext.grid.GridPanel({
							ds : reportListStore,
							store: reportListStore,
						    columns:[
								{id:'id',width: 160,  dataIndex: 'id',hidden:true},
								 new Ext.grid.RowNumberer(),
								{header: "公告标题", width: 200, sortable: true,  dataIndex: 'title'},
								{header: "发布人", width: 80, sortable: true,  dataIndex: 'editUserName'},
							    {header: "发布时间", width: 200, sortable: true,  dataIndex: 'editDate',renderer:function(v){return Ext.util.Format.date(new Date(v.time),'Y-m-d');}},
								{header: " ", width: 50,hidden:true}
							],
							listeners : {
								rowdblclick : function(grid, rowIndex, e){
									var arr = reportGrid.getSelectionModel().getSelections();
									if(arr.length > 0){
										var parr = reportViewWin.findByType('label', true);
										for(var i = 0;i < parr.length ;i++ ){
											if(parr[i].name == 'title'){
											    parr[i].setText (arr[0].data.title);
											}else if(parr[i].name == 'content'){
											    parr[i].setText(arr[0].data.content,false);											
											}
										}
									    reportViewWin.show();
									}
								}
							},
						    bbar:[
							  {
								iconCls : 'icon-add',
								listeners : {
								'click' : function() {
									editReportForm.getForm().setValues({id:'',title:'',content:''});
									reportEditWin.show();		
								}}
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-modify',
								listeners : {
								'click' : function() {
									var arr = reportGrid.getSelectionModel().getSelections();
									if(arr.length > 0){
										editReportForm.getForm().setValues(arr[0].data);
										reportEditWin.show();
									}
								}}
							  },{xtype:'tbseparator'},{
								iconCls : 'icon-delete',
								listeners : {
								'click' : function() {
									var arr = reportGrid.getSelectionModel().getSelections();
									if(arr.length > 0){
										Ext.MessageBox.confirm('系统提示', '请确认是否要删除当前公告!', function(btn){
										if(btn != 'yes'){return ;}
										Ext.Ajax.request({
												url: PATH + '/manage/mainPage/CompanyReportAction.do',
												params: {m:'deleteById',id:arr[0].id},
												success: function(response){
													if(response.responseText == 'success'){
														reportListStore.reload();
														reportEditWin.hide();
													}else{
														Ext.Msg.alert("系统提示", "删除失败!\n" + response.responseText);
													}
												}
											});
										});
										
									}
								}}
							  }
							]
						});
						


Ext.ffc.IndexPageFileGrid = Ext.extend(Ext.grid.GridPanel, {
	constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.fileListStore = new Ext.data.Store({
					   remoteSort : true,
					   autoLoad:true,
				     proxy: new Ext.data.HttpProxy({url:PATH + '/uploadManager/upload.do?method=getslave&busType=100&limit=8'}), 
					   reader: new Ext.data.JsonReader({
				     root: 'slaves',  
				     totalProperty :'totalCount'
					}, 
					['id','fileName','userName','uploadTime']
				 )
			});
			if(!_config.isFileUploadHide || !_config.isFileDeleteHide){
			   this.tbar = [
						  {
								iconCls : 'icon-add',
								hidden:_config.isFileUploadHide,
								listeners : {
								'click' : function() {
									var _this = this;
									new Slave.UploadWindow({busId : '', busType : 100,
										closeFunction: function() {
										     _this.getStore().reload();
										 },scope:this}).show();
								},scope:this}
							},{
							xtype:'tbseparator'
			     		},{
			     		  iconCls : 'icon-delete',
								hidden:_config.isFileDeleteHide,
								listeners : {
								'click' : function() {
									var selRecords = this.getSelectionModel().getSelections();
									if(selRecords.length == 0) {
										Ext.Msg.show({
											title:'信息提示',
											msg: '请选择要删除的附件！',
											buttons: Ext.Msg.OK,
											icon: Ext.MessageBox.INFO
										});
									} else {
										Ext.MessageBox.show({
											title:'系统提示',
											msg: '确定要删除当前所选附件？',
											buttons: Ext.MessageBox.OKCANCEL,
											fn: function(){
												var idArr = [];
												for(var i = 0; i < selRecords.length; i++) {
														idArr.push(selRecords[i].id);
												}
												Ext.Ajax.request({
													url: PATH + '/uploadManager/upload.do',
													params: {method:'delete',ids:Ext.encode(idArr)},
													success: function(response){
														eval('var t = '+ response.responseText);
														if(t.success){
															this.getStore().reload();
														}else{
														    Ext.Msg.alert("系统提示",t.msg);
														}
													},scope:this
													});			
											},scope : this
										});
									}
									
								},scope:this}
								}
							];
			}
			Ext.ffc.IndexPageFileGrid.superclass.constructor.call(this, {
							ds : this.fileListStore ,
							store: this.fileListStore ,
						    columns:[
								new Ext.grid.RowNumberer(),
								{header: "文件标题", width: 300, sortable: true,  dataIndex: 'fileName'},
								{header: "上传人", width: 80, sortable: true,  dataIndex: 'userName'},
							  {header: "上传时间", width: 80, sortable: true,  dataIndex: 'uploadTime',
							  	renderer:function(v){
							  		return Ext.util.Format.date(new Date(v.time),'Y-m-d');
							  	}
							  },
							  {id:'id',header: "",width: 40,  dataIndex: 'id',
							  	renderer:function(v){
							  		return '<a href="'+PATH + '/uploadManager/upload.do?method=download&id=' + v + '">下载</a>';
							  	}
							  }
							]	,
							bbar: new Ext.PagingToolbar({
								pageSize: 8,
								store: this.fileListStore,
								displayInfo: true,
								displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
								emptyMsg: "没有记录"
							}),
							listeners : {
								'afterrender' : function() {
									var div = this.getEl().dom;
									div.ondragover = function(){
									    //alert('over');
									};								
									div.ondragend = function(){
										//alert('end');
									};
									div.ondrop = function(e){
										e.preventDefault();
    								readfiles(e.dataTransfer.files);
									}
								},scope:this
							}
			})
		}
	});

var main_work_region_panel = new Ext.Panel({
            layout: 'border',
			//region:'fit',
			width  : Ext.getBody().getWidth() - 6,
			height : Ext.getBody().getHeight() - 60,
            items: [
            {
                region: 'west',
				layout: 'border',
                split: true,
				width:(Ext.getBody().getWidth() - 6)/2.5,
                margins: '0 0 0 0',
				items:[
				    { 
						region: 'north',
						layout: 'fit',
						split:true,
						title:'公司公告',
						height:280,
						margins: '0 0 0 0',
						collapsible: true,
						items:new Ext.ffc.reportViewPanel()
					},
					{
						region: 'center',
					  layout: 'fit',
						split:true,
						title:'公司文件',
						items:new Ext.ffc.IndexPageFileGrid()
					}
				]
            }, {
                region: 'center',
                split: true,
                margins: '0 0 0 0',
				layout:'fit',
				items:[
				    {
					   layout: 'fit',
					   split:true,
					   title:'互动信息',
					   items: new Ext.ffc.ForumMainGrid()
				    }	
			    ]
            }]
        });
	main_work_region_panel.render('main_panel_');

function readfiles(files) {
    var formData = new FormData() ;
    for (var i = 0; i < files.length; i++) {
      formData.append('file', files[i]);
      formData.append('busType', 100);
      formData.append('userId', LoginInfor.user.id);
      formData.append('userName', LoginInfor.user.userName);
      previewfile(files[i]);
    }
		
    // now post a new XHR request
      var xhr = new XMLHttpRequest();
      xhr.open('POST', PATH + '/uploadManager/upload.do?method=upload');
      
      xhr.onload = function() {
						if (xhr.status === 200) {
　　　　　　    eval('var t = ' + xhr.responseText);
								if(!t.success){
									Ext.Msg.alert("系统提示", t.msg);
								}else{
								   var arr = main_work_region_panel.find("title", "公司文件" );
								   if(arr.length>0){
								      arr[0].items.items[0].getStore().reload();
								   }
								}
　　　　　　} else {
　　　　　　　　alert(xhr.responseText);
　　　　　　}
      };
	
     
        xhr.upload.onprogress = function (event) {
          if (event.lengthComputable) {
            
          }
        }
        
      xhr.send(formData);
}
function previewfile(file) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    
}
	Ext.ffc.ResizeManager.addResizeObject(main_work_region_panel);
});