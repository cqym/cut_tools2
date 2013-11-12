Ext.namespace('Ext.baseInfo.clientLeader');
Clientleader = Ext.baseInfo.clientLeader;

Clientleader.WillSelectUserGrid = Ext.extend(Ext.grid.GridPanel, {
    willSeleUserStore : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.willSeleUserStore = new Ext.data.Store({
								   proxy: new Ext.data.HttpProxy({url:PATH + '/baseInfo/clientLeaderAction.do?method=getWillSelectUser&userId=' + this.userId}),//调用的动作 
								   reader: new Ext.data.JsonReader({
								   root: 'items',   
								   totalProperty :'totalCount'
								 }, 
								 [ //JSON数据的映射
								    {name: 'id',mapping:'id',type:'string'},
									{name: 'userName',mapping:'userName',type:'string'},
									{name: 'trueName',mapping:'trueName',type:'string'},
									//{name: 'departId',mapping:'departId',type:'string'},
									{name: 'departName',mapping:'departName',type:'string'}//,
									//{name: 'regTimeString',mapping:'regTimeString',type:'string'}
								 ])
								});
		this.willSeleUserStore.load();
		Clientleader.WillSelectUserGrid.superclass.constructor.call(this, {
			title : '待选用户',
			width : 450,
			height : 600,
			enableDragDrop : true,
			ddGroup : 'secondGridDDGroup',
			ds : this.willSeleUserStore,
			store: this.willSeleUserStore,
			columns: [new Ext.grid.RowNumberer(),//自动行号
				  {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
				  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
				  {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
				  //{header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
				  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true}//,
				  //{header: "创建时间", width: 200, dataIndex: 'regTimeString'}
				],
		   region: 'west',
		   listeners: {
					render : function( component ){
						var _ownId = this.ownId;
						var firstGridDropTargetEl =  component.getView().el.dom.childNodes[0].childNodes[1];
						var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
							ddGroup    : 'firstGridDDGroup',
							copy       : true,
							notifyDrop : function(ddSource, e, data){
								function addRow(record, index, allItems) {
									if(_ownId == record.data.id)
										return false;
									var firstGridStore = component.getStore();
									var foundItem = firstGridStore.findExact('id', record.data.id);
									if (foundItem  == -1) {
										firstGridStore.add(record);
										firstGridStore.sort('id', 'ASC');
										ddSource.grid.store.remove(record);
									}
								}
								Ext.each(ddSource.dragData.selections ,addRow);
								return(true);
							}
						});
					}
		   },
		   bbar: new Ext.PagingToolbar({
				pageSize: 14,
				store: this.willSeleUserStore,
				displayInfo: true,
				displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg: "没有记录"
			})
		});
	}
});

Clientleader.SelectedUserGrid = Ext.extend(Ext.grid.GridPanel, {
	seleUserStore : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.seleUserStore = new Ext.data.Store({
								   proxy: new Ext.data.HttpProxy({url:PATH + '/baseInfo/clientLeaderAction.do?method=getSelectedUser&userId=' + this.userId}),//调用的动作 
								   reader: new Ext.data.JsonReader({
								   //root: 'items',   
								   //totalProperty :'totalCount'
								 }, 
								 [ //JSON数据的映射
								    {name: 'id',mapping:'id',type:'string'},
									{name: 'userName',mapping:'userName',type:'string'},
									{name: 'trueName',mapping:'trueName',type:'string'},
									//{name: 'departId',mapping:'departId',type:'string'},
									{name: 'departName',mapping:'departName',type:'string'}//,
									//{name: 'regTimeString',mapping:'regTimeString',type:'string'}
								 ])
								});
		this.seleUserStore.load();
		Clientleader.SelectedUserGrid.superclass.constructor.call(this, {
			title : '已选用户',
			width : 450,
			height : 600,
			enableDragDrop : true,
			ddGroup : 'firstGridDDGroup',
			ds : this.seleUserStore,
			store: this.seleUserStore,
			viewConfig : {
				getRowClass : function(record,rowIndex,rowParams,store){
		
					if(this.selectedUserGrid){
						
					}
					/*if(record.data.id != '1'){
						return 'x-grid-record-gray';   
					}else{   
						return '';   
					}*/
				},scope:this
			},
			columns: [new Ext.grid.RowNumberer(),//自动行号
				  {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
				  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
				  {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
				  //{header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
				  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true}//,
				  //{header: "创建时间", width: 200, dataIndex: 'regTimeString'}
				],
		   region: 'center',
		   listeners: {
					render : function( component ){
						var secondGridDropTargetEl = component.getView().el.dom.childNodes[0].childNodes[1]
						var destGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
							ddGroup    : 'secondGridDDGroup',
							copy       : false,
							notifyDrop : function(ddSource, e, data){
								function addRow(record, index, allItems) {
									var secondGridStore = component.getStore();
									var foundItem = secondGridStore.findExact('id', record.data.id);
									if (foundItem  == -1) {
										secondGridStore.add(record);
										secondGridStore.sort('id', 'ASC');
										ddSource.grid.store.remove(record);
									}
								}
								Ext.each(ddSource.dragData.selections ,addRow);
								return(true);
							}
						});
					}
		   }
		});
	}
});


Clientleader.Window = Ext.extend(Ext.Window, {
	willSelectUserGrid : null,
	selectedUserGrid : null,
	userId : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.selectedUserGrid = new Clientleader.SelectedUserGrid(_cfg);//
		this.willSelectUserGrid = new Clientleader.WillSelectUserGrid(_cfg);
		Clientleader.Window.superclass.constructor.call(this, {
			width : 850,
			height : 600,
			plain:true,
			layout: 'border',
			modal : true,
			items: [this.willSelectUserGrid,this.selectedUserGrid],
			buttons : [
				{
					text : "保 存",
					hidden : this.isHide,
					handler : function() {
						var store = this.selectedUserGrid.getStore();
						var arr = [];
						for(var i  = 0,len = store.getCount(); i < len;i++ ){
							arr.push(store.getAt(i).data.id);
						}
						var userId = this.userId;
						Ext.Ajax.request(
						{
						   url: PATH + '/baseInfo/clientLeaderAction.do?method=saveClientLeader',
						   params: { userId: userId,userIds:arr},
						   success: function(response){
								Ext.MessageBox.alert('系统提示', '保存成功!', function(){});
						   }
						});
					},scope:this
				},{
					text : "取 消",
					handler : function() {
						this.close();
					},scope:this
				}]
		});
	}
});