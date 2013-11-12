Ext.ffc.WaitOfficeChildPanel = Ext.extend(Ext.grid.GridPanel, {  
	auditType : null,
	loadData : function(pstartIndex,ppageSize){
			var gridPanel = this;
			Ext.Ajax.request({
				method: "post",
				params: { auditType: gridPanel.auditType.auditTypeId,flowInforId:gridPanel.auditType.auditFlowId,startIndex:pstartIndex,pageSize:ppageSize},
				url: PATH + "/manage/audit/auditInforMangeAction.do?ffc=findWaitAuditBusinessInfor",
				success: function(response){
					//Ext.Msg.alert("消息", response.responseText);
					//alert(response.responseText);
					eval("var pageInfor=" + response.responseText);
					var dataArray = [];
					var mappingArray = [];
					for(var i = 0 ;i < pageInfor.items.length;i++){
						if(i == 0){
							var num = 0;
							for(var j in pageInfor.items[0].properties){
								mappingArray.push({name: j,mapping:num++,type:'string'});
							}
						}
						var arr = [];
						for(var tt in pageInfor.items[i].properties){
							arr.push(pageInfor.items[i].properties[tt]);
						}
						dataArray.push(arr);
					}
					//alert(Ext.encode(dataArray));
					gridPanel.storeDef = new Ext.data.Store({
						proxy: new Ext.data.MemoryProxy(dataArray),
						//proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findWaitAuditBusinessInfor'}),//调用的动作 
						reader: new Ext.data.ArrayReader({}, mappingArray)
					});
					
					var pcolumns = [];
						//gridCheckSele = new Ext.grid.CheckboxSelectionModel();
						pcolumns.push(new Ext.grid.RowNumberer());
						pcolumns.push(gridPanel.gridCheckSele);
						pcolumns = pcolumns.concat(pageInfor.headers);
						/*pcolumns.push(
							{"dataIndex":"auditInfor","header":"查看详细","hidden":false,"sortable":false,"width":80,
								"renderer":function(value, cellmeta, record, rowIndex, columnIndex, store){
												var str = "<a href=\"javascript:loadBusinessInfor(\'" + record.get('url') + "\',\'" + record.get('id') + "\');\">查看</a>";
												return str;
											}
							}
						);*/
						pcolumns.push(
							{"dataIndex":"auditInfor","header":"审批信息","hidden":false,"sortable":false,"width":80,
								"renderer":function(value, cellmeta, record, rowIndex, columnIndex, store) {
												var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
												return str;
											}
							}
						);
						pcolumns.push(
							{"dataIndex":"auditInfor","header":"委托人","hidden":false,"sortable":false,"width":80,
								"renderer":function(value, cellmeta, record, rowIndex, columnIndex, store) {
												var v = record.get("trustPersonName");
												var n = (v == null ? "" : v);
												var str = "<span style=\"color:red\">" + n + "</a>";
												return str;
											}
							}
						);
					try{
						gridPanel.reconfigure(gridPanel.storeDef,new Ext.grid.ColumnModel(pcolumns));
						gridPanel.storeDef.load();
					}catch(e){
					    //alert("ssss:" + e);
					}
					gridPanel.pageObject = pageInfor;
					//gridPanel.pageInfoLabel['setInfor'](gridPanel.pageObject);
				}
		   });	

	},
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.gridCheckSele = new Ext.grid.CheckboxSelectionModel();
		this.storeDef =  new Ext.data.Store();
		//this.view = new Ext.grid.GridView();
		this.pageInfoLabel = new Ext.form.Label();
		
		this.loadData(0,100);
		Ext.ffc.WaitOfficeChildPanel.superclass.constructor.call(this, {
			//cm : new Ext.grid.ColumnModel(),
	    columns:[],
			store : this.storeDef,
			ds : this.storeDef,
			//view : this.view,
			sm:this.gridCheckSele,
			listeners : {
			    rowdblclick:function(grid, rowIndex, e){
			    	var rec = grid.getStore().getAt(rowIndex);
			    	
			    	loadBusinessInfor(rec.get('url'),rec.get('id'),this.auditType,grid);
			    },scope:this
		  }
		});
	}
});  


Ext.ffc.WaitOfficeWindow = Ext.extend(Ext.Window, {  
	waitWorks : null,
	auditTypeItems : null,
	grids : null,
	itemsDef : null,
	loadData : function(){
	    for(var i = 0;i < this.grids.length ; i++){
			this.grids[i].loadData(0,100);
	    }
	},
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.grids = [];
		this.itemsDef = [];
				var it = new Ext.ffc.WaitOfficeChildPanel({
									auditType : this.auditType,
									//height:200
	
									});
		this.grids.push(it);
		this.auditTypeItems = [
					{
						layout:'fit',
						height:200,
						id : this.auditType.auditFlowId,
						title : this.auditType.auditFlowName,
						items : it
					}
				];
		Ext.ffc.WaitOfficeWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			maximizable :true,
			width : Ext.getBody().getWidth() - 100,
			height : 500,
			title :  '待办工作',
			layout :  'fit',
			//closeAction:'hide',
			items : this.auditTypeItems
		});
	}
});  

Ext.ffc.AuditBusinessDetailWindow = Ext.extend(Ext.Window, {  
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ffc.AuditBusinessDetailWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			maximizable :true,
			bbar :[{xtype:'label'}],
			buttons : [{
				text : "审   核",
				hidden:this.auditButtonHiden,
				handler : function() {
					var arr = Ext.DomQuery.select('input[name=auditContent]',Ext.getDom(this.getBottomToolbar().getEl())[0]);
					var win = new Ext.ffc.AuditMsgWindow({busId:this._id,auditContentsObj:arr,auditType:this.auditType,grid:this._grid,bussinessWindow:this});
					win.show();
				},scope : this
			},{
				text : "关闭",
				handler : function() {
					this.close();
				},scope : this
			}],
			listeners : {
				'beforeshow' : function() {
					//重新载入报价产品树
					this.setAuditContent(this._id);
					
					var _id = this._id;
					Ext.Ajax.request({
						url: PATH + '/generalQuo/getQuoAction.do',
						params: { quoId: _id },
						success : function(response) {
							var responseArray = Ext.util.JSON.decode(response.responseText); 
							if(responseArray.success == true){
								this.northPanel.simpleForm.setValues(new Ext.data.Record(responseArray.data));
							}
						},scope : this
					});
					
					Ext.Ajax.request({
						url: PATH + '/generalQuo/excelAction.do?method=getOrderPrice4Quo' ,
						params: { quoId: _id },
						success : function(response) {
							//var responseArray = Ext.util.JSON.decode(response.responseText);
							if(response.responseText.length == 0)
								return;
							Ext.Msg.show({
								title:'净价低于或者等于采购价格产品：',
								msg: response.responseText,
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO,
								width : 700
							});
						},scope : this
					});
				},scope:this
			},
			setAuditContent:function(_id){
				      var _this = this;
		              var busId = _id;
					Ext.Ajax.request({
							   url: PATH + '/manage/audit/auditContentAction.do',
							   params: { busId: busId,m:'findWaitAuditContent' },
							   success: function(response){
									  eval("var arr = " + response.responseText);
									  var html = [];
									  html.push('<div>审批内容：');
									  for(var i = 0;i < arr.length;i++){
										html.push('<span style="color:green">');
										html.push(arr[i].auditContentName);
										html.push('</span>(');
										if(arr[i].auditPerson == ''){
											  if(arr[i].waitAudit){
												  html.push('<span style="color:blue"><input type="checkbox" name="auditContent" value="' + arr[i].id + '">');
											  }else{
												  html.push('<span style="color:blue">待审');
											  }
										}else{
											html.push('<span style="color:red">');
											html.push(arr[i].auditPerson);
										}
										html.push('</span>)&nbsp;&nbsp;');
									  }
									  html.push('</div>');
									  _this.bbar.update(html.join(''));
								}
						});
			},scope:this
		});
	},
	setId : function(id) {
	   this._id = id;
	},
	setAuditType : function(auditType){
	  this.auditType = auditType;
	},
	setGrid : function(_grid){
	   this._grid = _grid;
	}
});  
