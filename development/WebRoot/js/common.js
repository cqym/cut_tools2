var tonglian = {util:{}};
/**
function('','',7)  ==> 0000001
function('','0000001',7)  ==> 0000002
function('0000001','',7)  ==> 00000010000001
function('0000001','00000010000001',7)  ==> 00000010000002
**/
var tonglian = {util:{}};
tonglian.util.getNewTreeNode = function(pid,maxChildId,len){

	if(pid == '' && maxChildId == ''){
		return tonglian.util.getZeroString(len - 1) + "1";
	}else if(pid != '' && maxChildId == ''){
		return pid + tonglian.util.getZeroString(len - 1) + "1";
	}else{
	    var num = parseInt(maxChildId,10) + 1;
		return tonglian.util.getZeroString(maxChildId.length - ("" + num).length) + num;
	}
}
tonglian.util.getZeroString = function(len){
	var arr = [];
	for(var i  = 0 ;i < len ;i++){
		    arr.push('0');
	}
	return arr.join('');
}
tonglian.util.toString = function(obj){
	var str = '';
	for(var i in obj){
	    str += i + '=' + obj[i] + ",";
	}
	return str;
}


/**
example:
editor: new Ext.form.NumberField({
					gridObj : this,//gridEditor
					listeners : {
						specialkey : Ext.ftl.gridEditorkeyMove
					}
				})
*/
Ext.ftl.gridEditorkeyMove = function(_field, e) {
	//Ext.ffc.util.showDebugString(Ext.ffc.util.toObjectString(_field.gridEditor.container));

	var currCellNums = {'row':_field.gridEditor.row,'col':_field.gridEditor.col};
	var grid = _field.gridObj;
    if(!grid){
		alert('请将GridEditor作为field一属性，传入，属性名为gridObj!')
		return;
	}
	var colModel = grid.getColumnModel();
	var colTotal = colModel.getColumnCount();
	
	
	switch (e.getKey()) {
		case e.UP :
			if(currCellNums.row==0)
				return;
			grid.fireEvent('cellclick',grid,currCellNums.row - 1, currCellNums.col,e);
			break;
		case e.DOWN :
			if(currCellNums.row == grid.getStore().getCount()-1)
				return;
			grid.fireEvent('cellclick',grid,currCellNums.row + 1, currCellNums.col,e);
			break;
		case e.LEFT :
			if(currCellNums.col == 1) {
				return;
			} else {
				for(var j = currCellNums.col-1; j > 0; j--) {
					if(colModel.isCellEditable(j, currCellNums.row)) {
						grid.fireEvent('cellclick',grid,currCellNums.row, j,e);
						break;
					}
				}
			}
			break;
		case e.RIGHT :
			if(currCellNums.col == colTotal - 1) {
				return;
			} else {
				for(var i = currCellNums.col+1; i < colTotal; i++) {
					if(colModel.isCellEditable(i, currCellNums.row)) {
						grid.fireEvent('cellclick',grid,currCellNums.row, i,e);
						break;
					}
				}
			}
			break;
	}
}

/**
example:
this.reservePlanEditTree =  new Ext.tree.ColumnTreeEditor(this.reservePlanTree,{
				       completeOnEnter: true,
				       autosize: true,
				       ignoreNoChange: true,
					   allowBlank: false,
					   listeners : {
							specialkey : Ext.ffc.gridTreeEditorkeyMove
					   }
				    });
*/

Ext.ffc.gridTreeEditorkeyMove = {
	willExeObj : null,
	moveCallBack : function(f,e) {
		var treeEditor = f.treeEditor;
		var currentNode = treeEditor.editNode;
		var currentColIndex = treeEditor.editColIndex;
		
		if(e.getKey() == e.UP) {
			if(currentNode.previousSibling == null){
				return;
			}
			Ext.ffc.gridTreeEditorkeyMove.willExeObj = currentNode.previousSibling.ui.getColHtmlObject(currentColIndex);
		} else if(e.getKey() == e.DOWN) {
			if(currentNode.nextSibling == null){
				return;
			}
			Ext.ffc.gridTreeEditorkeyMove.willExeObj = currentNode.nextSibling.ui.getColHtmlObject(currentColIndex);
		}
		Ext.ffc.gridTreeEditorkeyMove.dispatchEvent();
	},
	dispatchEvent : function(){
		if(Ext.ffc.gridTreeEditorkeyMove.willExeObj == null){return ;}
		var willExeObj = Ext.ffc.gridTreeEditorkeyMove.willExeObj;
		if (document.createEvent){
			Ext.ffc.gridTreeEditorkeyMove.dispatchEventForFireFox();
			window.setTimeout(Ext.ffc.gridTreeEditorkeyMove.dispatchEventForFireFox,100);
		}else if (document.createEventObject){
			try{
				willExeObj.fireEvent('onclick');
				function bbb(){
					willExeObj.fireEvent('onclick');
				}
				window.setTimeout(bbb,100);
			}catch(e){alert(e);}
		}
	},
	dispatchEventForFireFox : function(){
		var willExeObj = Ext.ffc.gridTreeEditorkeyMove.willExeObj;
		var evObj = document.createEvent('MouseEvents');
			evObj.initEvent('click', true, false);
			willExeObj.dispatchEvent(evObj);
	}
};


Ext.ffc.showSlaveFillWindow = function (data,callBackFunction){
	try {
			var slaveWindow = new Slave.SlaveManageWindow({busId : data.id, busType : data.busType});
			var _slaveStore = slaveWindow.listPanel.listView.store;
			_slaveStore.baseParams.busId = data.id;
			slaveWindow.show();
			_slaveStore.load();
			callBackFunction
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
}
if (!Ext.grid.GridView.prototype.templates) {   
     Ext.grid.GridView.prototype.templates = {};   
}   
 Ext.grid.GridView.prototype.templates.cell = new Ext.Template(   
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',   
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',   
     '</td>'  
 );

Ext.ffc.ResizeManager = {
	arr : [],
	addResizeObject : function(obj){
		Ext.ffc.ResizeManager.arr.push(obj);
		//obj.on("beforedestroy",function(){alert('destory...');});
	},
	removeResizeObject : function(obj){
	    for(var i = arr.length - 1; i >= 0 ;i--){
			if(arr[i] == obj){
			    arr[i] = null;
			}
		}
	}
}
window.onresize = function(){
	var arr = Ext.ffc.ResizeManager.arr;
    for(var i = 0; i < arr.length;i++){
	    if(arr[i] && arr[i].setWidth && arr[i].setHeight){
			arr[i].setWidth(Ext.getBody().getWidth());
			arr[i].setHeight(Ext.getBody().getHeight() - 55);
		}
	}
}

Ext.ffc.selectUser = function(calllback){
	var usersStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/user/usersViewAction.do?ffc=getUsersByPage&onlyValid=true'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       root: 'items',  //从struts2里面传递过来的参数 
	   totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
         {name: 'id',mapping:'id',type:'string'},
        {name: 'userName',mapping:'userName',type:'string'},
        {name: 'trueName',mapping:'trueName',type:'string'},
		{name: 'departId',mapping:'departId',type:'string'},
		{name: 'departName',mapping:'departName',type:'string'},
		{name: 'regTimeString',mapping:'regTimeString',type:'string'}
     ])
	});

    var usersGrid = new Ext.grid.GridPanel({
        id:"grid",
       // el:"example-grid",
        ds : usersStore,
        store: usersStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: false,hidden:true},
			  {header: "用户名", width: 120, dataIndex: 'userName', sortable: true},
              {header: "真实姓名", width: 120, dataIndex: 'trueName', sortable: true},
			  {header: "所属部门id", width: 120, dataIndex: 'departId', sortable: false,hidden:true},
			  {header: "所属部门", width: 120, dataIndex: 'departName', sortable: true},
              {header: "创建时间", width: 200, dataIndex: 'regTimeString'}   
     ],
       width:500,
       height:450,
	   region: 'west',
	   bbar: new Ext.PagingToolbar({
            pageSize: 14,
            store: usersStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"//,
           /* items:[
                '-', {
                pressed: true,
                enableToggle:true,
                text: 'Show Preview',
                cls: 'x-btn-text-icon details',
                toggleHandler: function(btn, pressed){
                    var view = grid.getView();
                    view.showPreview = pressed;
                    view.refresh();
                }
            }]*/
        }),
		listeners: {
	            'rowclick': function(obj, rowIndex, evt){
				},
				'rowdblclick' : function(grid, rowIndex,  e ) {
					var record = usersStore.getAt(rowIndex);
					if(record){
						calllback(record);
					  // alert(r.get('auditPersonId'));
					}
					usersWin.hide();
				}
	    },
		buttons: [
			{xtype:'label',text: '真实姓名:',x:0,y:155},
			{
				xtype:'textfield',
				name:'trueName',
				enableKeyEvents:true,
				listeners : {
					'keypress':function(f,e){
						var v = e.getTarget().value;
						usersStore.baseParams = {'trueName':v};
						usersStore.load({params: {start: 0, limit: 14}});
					},scope : this
				}
			},
			{xtype:'label',html:'<span style="width:320px;color:green">&nbsp;</span>'},
			{
            text: '确定',
			width:50,
			margins:'0 0 0 0',
		    cmargins:'0 0 0 0',
		    handler:function(){
				var selectedm = usersGrid.getSelectionModel();
				var record = selectedm.getSelected(); 
				 if(record){
				      calllback(record);
				 }
				 usersWin.hide();
			}
        }]
    });
	usersStore.load({params:{start:0,limit:14}});
	var usersWin = new Ext.Window({
				title: '用户列表',
				closable:true,
				width:520,
				height:475,
				plain:true,
                layout: 'fit',
				modal : true,
				items: [usersGrid]
			});
	usersWin.show();
}

/**模板选择窗口*/	
Ext.ffc.ExpTempleteSelectGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.Store({
					   remoteSort : true,
					   proxy: new Ext.data.HttpProxy({url:PATH + '/dataExp/ExpTempleteAction.do?m=selectTemplete&limit=10' }), 
					   reader: new Ext.data.JsonReader({
					   root: 'items',  
					   totalProperty :'totalCount'
					 }, 
					 [ //JSON数据的映射
						{name: 'id',mapping:'id',type:'string'},
						{name: 'templeteName',mapping:'templeteName',type:'string'},
						{name: 'memo',mapping:'memo',type:'string'},
						{name: 'userName',mapping:'userName',type:'string'},
						{name: 'editTimeString',mapping:'editTimeString',type:'string'},
						{name: 'templeteType',mapping:'templeteType',type:'string'}
					 ])
					});
		this.pageToolBbar = new Ext.PagingToolbar({
					store: this.store,
					pageSize: 20,
					displayInfo: true,
					displayMsg: "当前显示第{0}条到第{1}条，共{2}条",
					emptyMsg: "<i>没有数据</i>"
				});
		this.gridCheckSele = new Ext.grid.CheckboxSelectionModel();
		Ext.ffc.ExpTempleteSelectGrid.superclass.constructor.call(this, {
				ds : this.store,
				columns: [
			new Ext.grid.RowNumberer(),//自动行号
			this.gridCheckSele,
            {id:'id',header: "id", width: 160,  dataIndex: 'id',hidden:true},
            {header: "模板名称", width: 200, sortable: true,  dataIndex: 'templeteName'},
			{header: "备注", width: 180, sortable: true,  dataIndex: 'memo'}
        ],
				
				bbar: this.pageToolBbar
		})
	}	
});	

Ext.ffc.ExpTempleteSelectWindow = Ext.extend(Ext.Window, {  
	grid:null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.grid = new Ext.ffc.ExpTempleteSelectGrid();
		Ext.ffc.ExpTempleteSelectWindow.superclass.constructor.call(this, {
			height : 300,
			width: 350,
			constrainHeader : true,
			modal : true,
			title :  '请选择导出模板',
			layout :  'fit',
			items : [this.grid],
			buttons : [
				{
				text : "导   出",
				handler : function() {
					var selectionModel = this.grid.getSelectionModel();
					var selectedCount = selectionModel.getCount();
					if(selectedCount <= 0){
						Ext.Msg.alert("消息", "请选择要导出的模板!");
						return;
					}
					var record = selectionModel.getSelected();
					this.exportDataMethod(record.get('id'));	
					this.close();
				},
				scope : this
			  },{
				text : "关  闭",
				handler : function() {
					this.close();
				},
				scope : this
			  }
			],
			listeners: {
				show : function(){
					this.grid.store.load({params:{templeteType:this.templeteType}});
				}
			}
		});
	}
});

Ext.ffc.showSlaveMyWindow = function (data){
	try {
			var slaveWindow = new Slave.SlaveManageWindow({busId : data.id, busType : data.busType});
			var _slaveStore = slaveWindow.listPanel.listView.store;
			_slaveStore.baseParams.busId = data.id;
			slaveWindow.show();
			_slaveStore.load();
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
}