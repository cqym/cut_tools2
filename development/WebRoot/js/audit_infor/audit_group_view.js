Ext.onReady(function(){
	

	var waitAuditStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findWaitAuditTypeInfor'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       //root: 'items',  //从struts2里面传递过来的参数 
	   //totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'auditTypeId',mapping:'auditTypeId',type:'string'},
        {name: 'auditTypeName',mapping:'auditTypeName',type:'string'},
        {name: 'auditFlowId',mapping:'auditFlowId',type:'string'},
		{name: 'auditFlowName',mapping:'auditFlowName',type:'string'},
		{name: 'departId',mapping:'departId',type:'string'},
		{name: 'departName',mapping:'departName',type:'string'},
	    {name: 'count',mapping:'count',type:'string'}
     ])
	});

function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store) {
	var str = "<a href=\"javascript:onItemAuditDetail(this,\'" + record.get('auditTypeId') + "\',\'" + record.get('auditFlowId') + "\');\">" + value + "</a>";
    return str;
}
    var grid = new Ext.grid.GridPanel({
        ds : waitAuditStore,
        store: waitAuditStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
            {id:'auditTypeId',header: "auditTypeId", width: 160,  dataIndex: 'auditTypeId',hidden:true},
            {header: "审批类型", width: 155, sortable: true,  dataIndex: 'auditTypeName'},
            {header: "auditFlowId", width: 75,   dataIndex: 'auditFlowId',hidden:true},
            {header: "审批流程", width: 155, sortable: true, dataIndex: 'auditFlowName',renderer:renderDescn},
			{header: "departId", width: 75,  dataIndex: 'departId',hidden:true},
			{header: "所属部门", width: 105, sortable: true, dataIndex: 'departName'},
			{header: "份数", width: 75, sortable: true, dataIndex: 'count'}
        ],
        //stripeRows: true,
        height:250,
        width:550,
        title:'待办工作',
		listeners:{
		    afterrender : function(obj){
				
				Ext.ffc.setAuditInforsPosition();
			}
		}
    });
    grid.render('grid-audit');
	waitAuditStore.load();
//
function renderDescn2(value, cellmeta, record, rowIndex, columnIndex, store) {
	var str = "<a href=\"javascript:onItemHisAuditDetail(this,\'" + record.get('auditTypeId') + "\',\'" + record.get('auditFlowId') + "\');\">" + value + "</a>";
    return str;
}

	var historyAuditStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findAlreadyAuditTypeInfor'}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       //root: 'items',  //从struts2里面传递过来的参数 
	   //totalProperty :'totalCount'
     }, 
	 [ //JSON数据的映射
        {name: 'auditTypeId',mapping:'auditTypeId',type:'string'},
        {name: 'auditTypeName',mapping:'auditTypeName',type:'string'},
        {name: 'auditFlowId',mapping:'auditFlowId',type:'string'},
		{name: 'auditFlowName',mapping:'auditFlowName',type:'string'},
		{name: 'departId',mapping:'departId',type:'string'},
		{name: 'departName',mapping:'departName',type:'string'},
	    {name: 'count',mapping:'count',type:'string'}
     ])
	});

	var historyAuditGrid = new Ext.grid.GridPanel({
			ds : historyAuditStore,
			store: historyAuditStore,
			region: 'east',
			columns: [new Ext.grid.RowNumberer(),//自动行号
				{id:'auditTypeId',header: "auditTypeId", width: 160,  dataIndex: 'auditTypeId',hidden:true},
				{header: "审批类型", width: 155, sortable: true,  dataIndex: 'auditTypeName',renderer:renderDescn2},
				{header: "auditFlowId", width: 75,   dataIndex: 'auditFlowId',hidden:true},
				{header: "审批流程", width: 155, sortable: true, dataIndex: 'auditFlowName',hidden:true},
				{header: "departId", width: 75,  dataIndex: 'departId',hidden:true},
				{header: "所属部门", width: 105, sortable: true, dataIndex: 'departName',hidden:true},
				{header: "份数", width: 75, sortable: true, dataIndex: 'count'}
			],
			//stripeRows: true,
			height:250,
			width:550,
			title:'已办工作'
		});
		historyAuditGrid.render('grid-audit_his');
		historyAuditStore.load();

});

//