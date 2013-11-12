Ext.namespace("Ext.ftl.slave");

//命名空间别名
Slave = Ext.ftl.slave;

Slave.UploadWindow = Ext.extend(Ext.Window, {
	busId : null,
	busType : null,
	swfUploadPanel : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.swfUploadPanel = new Ext.ux.SwfUploadPanel({
			title: '附件上传',  
			border: false, 
			// Uploader Params				
			upload_url: PATH +　'/uploadManager/upload.do?method=upload', 
			//, upload_url: 'http://localhost/ext/plugins/SwfUploadPanel/upload_example.php'
			post_params: { id: 123},
			file_types: '*.jpg;*.bmp;*.png;*.chm;*.pdf;*.doc;*.xls',
			file_size_limit: "15360",
			//file_types_description: '图片文件', 
			flash_url: PATH + "/extjs/plugins/SwfUploadPanel/swfupload.swf", 
			//, single_select: true // Select only one file from the FileDialog

			// Custom Params
			single_file_select: false, // Set to true if you only want to select one file from the FileDialog.
			confirm_delete: false, // This will prompt for removing files from queue.
			remove_completed: true // Remove file from grid after uploaded.
		}); // End Dialog
		
		Slave.UploadWindow.superclass.constructor.call(this, {
			width: 514, 
			height: 330,
			
			resizable: false, 
			constrainHeader : true,
			modal : true,
			layout:'fit',
			items: this.swfUploadPanel,
			//closeAction : 'hide',
			listeners : {
				'close' : function() {
				  if(this.closeFunction)
				      this.closeFunction();
				},scope:this,
				'render' : function() {
					var JSESSIONID = null;
					var cookies = document.cookie.split(";");
					Ext.each(cookies, function(cookie) {
						var nvp = cookie.split("=");
						if (nvp[0].trim() == 'JSESSIONID')
							JSESSIONID = nvp[1];
					});
					//alert(JSESSIONID);
					this.swfUploadPanel.on('swfUploadLoaded', function() { 
						this.swfUploadPanel.addPostParam( 'busId', this.busId );
						this.swfUploadPanel.addPostParam( 'userId', LoginInfor.user.id);
						this.swfUploadPanel.addPostParam( 'userName', LoginInfor.user.trueName);
						this.swfUploadPanel.addPostParam( 'busType', this.busType );
						this.swfUploadPanel.addPostParam( 'JSESSIONID', JSESSIONID );
					},this);
				}
			}
		})
	}
})

Slave.SlaveManageStore = Ext.extend(Ext.data.JsonStore, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Slave.SlaveManageStore.superclass.constructor.call(this, {
			url: PATH +　'/uploadManager/upload.do?method=getslave',
	        root: 'slaves',
	        fields: ['id', 'fileName', 'userName', 'path', {name:'fileSize', type: 'float'}, {name:'uploadTimeStr'}]
		})
	}
})

Slave.SlaveManageListView = Ext.extend(Ext.ListView, {
	store : null,
	imageViewWindow : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Slave.SlaveManageStore();
		Slave.SlaveManageListView.superclass.constructor.call(this, {
			store: this.store,
	        multiSelect: true,
	        emptyText: '无附件信息',
	        reserveScrollOffset: true,
	
	        columns: [{
	            header: '名称',
	            width : .4,
	            dataIndex: 'fileName',
	            tpl: '{fileName:trim}'
	        },{
	            header: '上传日期',
	            dataIndex: 'uploadTimeStr',
	            width : .2,
	            tpl: '{uploadTimeStr}'
	        },{
	            header: '上传人',
	            width : .2,
	            dataIndex: 'userName',
	            tpl: '{userName}'
	        },{
	            header: '大小',
	            dataIndex: 'fileSize',
	            width : .2,
	            tpl: '{fileSize:fileSize}',
	            align: 'right'
	        }],
	        
	        listeners : {
	        	'dblclick' : function(view, index, node, e) {
	        		var _record = this.getRecord(node);
	        		var allowedView = 'jpg,jpeg,png,bmp';
	        		var fileName = _record.data['fileName'];
	        		//var fileExt = fileName.substring(fileName.lastIndexOf('.')+1, fileName.length).toLowerCase();
	        		var fileExt = Ext.util.Format.lowercase(Ext.util.Format.substr(fileName, fileName.lastIndexOf('.')+1, fileName.length));
	        		if(allowedView.indexOf(fileExt) == -1)
	        			return;
	        		var slaveStore = new Ftlmage.ImageViewStore();
	        		var records = [];
	        		records.push(_record);
	        		slaveStore.add(records);
	        		this.imageViewWindow = new Ftlmage.ImageViewWindow({slaveStore : slaveStore});
	        		this.imageViewWindow.show();
	        	}
	        }
		})
	},
	
	removeSelectNodes : function() {
		var selNodes = this.getSelectedNodes();
		for(var i = 0; i < selNodes.length; i++) {
			this.store.remove(selNodes[i]);
		}
	}
})

Slave.SlaveManagePanel = Ext.extend(Ext.Panel, {
	busId : null,
	busType : null,
	listView : null,
	upWindow : null,
	addBtnHidden : false,
	delBtnHidden : false,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.listView = new Slave.SlaveManageListView();
		this.upWindow = new Slave.UploadWindow({busId : this.busId, busType : this.busType});
		Slave.SlaveManagePanel.superclass.constructor.call(this, {
	        collapsible:false,
	        layout:'fit',
	        title:'附件列表',
	        iconCls:'icon-grid',
	        closeAction:'hide',
	        tbar : [{
	        	text : '添加附件',
	        	iconCls : 'icon-add',
	        	hidden : this.addBtnHidden,
	        	handler : function() {
	        		this.upWindow.show();
	        	},scope : this
	        },{
		        xtype : 'tbseparator',
		        hidden : this.addBtnHidden
	        },{
	        	text : '删除附件',
	        	iconCls : 'icon-delete',
	        	hidden : this.delBtnHidden,
	        	handler : function() {
	        		fn : this.onDeleteClick();
	        	},scope : this
	        },{
	        	xtype : 'tbseparator',
	        	hidden : this.delBtnHidden
	        },{
	        	text : '下载附件',
	        	iconCls : 'icon-down',
	        	handler : function() {
	        		fn : this.handlerDownload();
	        	},scope : this
	        }],
	        listeners : {
	        	'render' : function() {
	        		this.upWindow.swfUploadPanel.on('fileUploadComplete', function() {
						this.listView.store.reload();
					},this);
	        	}
	        },
	        items: this.listView
		})
	},
	
	onDeleteClick : function() {
		var selRecords = this.listView.getSelectedRecords();
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
				fn: this.handleDelete,//若以此种方法调用，需要指定scope
				scope : this
			});
		}
		
	},
	
	handlerDownload : function() {
		var selRecords = this.listView.getSelectedRecords();
		if(selRecords.length == 0) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择要下载的附件！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
		} else {
			var _id = selRecords[0].id;
			window.open(PATH +　'/uploadManager/upload.do?method=download&id=' + _id);
		}
	},
	
	handleDelete : function(_btn) {
		var _listView = this.listView;
		if(_btn == 'ok') {
			var ids = [];
			var selRecords = this.listView.getSelectedRecords();
			for(var i = 0; i < selRecords.length; i++) {
				var id = selRecords[i].id;
				ids.push(id);
			}
			var paramStr = Ext.encode(ids);
			
			Ext.Ajax.request({
				url : PATH +　'/uploadManager/upload.do?method=delete',
				params : { ids : paramStr },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						this.listView.store.reload();
					} else {
						Ext.Msg.show({
							title : '信息提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
					}
				},
				scope : this
			});
		}
	}
})

Slave.SlaveManageWindow = Ext.extend(Ext.Window, {
	busId : null,
	busType : null,
	listPanel : null,
	addBtnHidden : false,
	delBtnHidden : false,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.listPanel = new Slave.SlaveManagePanel({
			busId : this.busId, 
			busType : this.busType,
			addBtnHidden : this.addBtnHidden, 
			delBtnHidden : this.delBtnHidden
		});
		Slave.SlaveManageWindow.superclass.constructor.call(this, {
			width:650,
	        height:450,
	        layout:'fit',
	        title:'附件管理',
	        closeAction:'hide',
	        modal : true,
	        constrainHeader : true,
	        resizable : false,
	        listeners : {
	        	'render' : function() {
	        		var _slaveStore = this.listPanel.listView.store;
					_slaveStore.baseParams.busId = this.busId;
					_slaveStore.baseParams.busType = this.busType;
					_slaveStore.load();
	        	},scope : this
	        },
	        items: this.listPanel
		})
	}
})









