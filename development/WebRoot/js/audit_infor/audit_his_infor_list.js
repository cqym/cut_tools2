function onAuditInfor(obj,businessId){
    var auditHisInforStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findAuditInfor&businessId=' + businessId}),//调用的动作 
	   reader: new Ext.data.JsonReader({
       //root: 'roles',  //从struts2里面传递过来的参数 
	  // successProperty :'totalCount'
	  }, 
	 [ //JSON数据的映射
        {name: 'id',mapping:'id',type:'string'},
        {name: 'auditPerson',mapping:'auditPerson',type:'string'},
        {name: 'auditOrder',mapping:'auditOrder',type:'string'},
		{name: 'auditDegree',mapping:'auditDegree',type:'string'},
		{name: 'auditOpType',mapping:'auditOpType',type:'string'},
		{name: 'auditComment',mapping:'auditComment',type:'string'},
		{name: 'auditTime',mapping:'auditTimeString',type:'string'},
		{name: 'batchId',mapping:'batchId',type:'string'},
		{name: 'auditContent',mapping:'auditContent',type:'string'}
     ]),
	 listeners:{
		 load :function(thisStore , records, options ){
			if(records != null && records.length > 0){
				var currentBatchId = records[0].get("batchId");
				for(var i = 0; i < batchRecordStore.getCount();i++){
				    if(batchRecordStore.getAt(i)["id"] == currentBatchId){
						var str = '';
						for(var j in batchRecordStore.getAt(i)){
						    str += j + '=' + batchRecordStore.getAt(i)[j] + "\n";
						}
					    selectForm.findByType("textfield")[0].setValue(batchRecordStore.getAt(i).json.batchNumber);
					}
				}
			}
		}
	 }
	});

	var batchRecordStore = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
				url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findAuditBatchRecord&businessId=' + businessId
			}
	   ),//调用的动作 
	   reader: new Ext.data.JsonReader(
		 {}, 
		 [ //JSON数据的映射
			{name: 'myId',mapping:'id',type:'string'},
			{name: 'displayText',mapping:'batchNumber',type:'string'}
		 ]),
		listeners:{
		 load :function(thisStore , records, options ){
				auditHisInforStore.load();
			}
		}
	});
	var  selectForm = new Ext.FormPanel({
			labelWidth: 75, 
			//url:'save-form.php',
			frame:true,
			region: 'center',
			bodyStyle:'padding:5px 5px 0',
			width: 850,
			items: [new Ext.form.ComboBox({
					typeAhead: true,
					triggerAction: 'all',
					lazyRender:true,
					mode: 'local',
					store: batchRecordStore,
			        fieldLabel:'审批批次' ,
					valueField: 'myId',
					displayField: 'displayText',
					listeners:{
						'select': function( combo,  record,  index ){
							
						    auditHisInforStore.load({params:{batchNumber:record.get("displayText")}});
						}
					}
					})]
		});
    var auditHisInforGrid = new Ext.grid.GridPanel({
        id:"grid33333",
       // el:"example-grid",
        ds : auditHisInforStore,
        store: auditHisInforStore,
        columns: [new Ext.grid.RowNumberer(),//自动行号
              {header: "id", width: 120, dataIndex: 'id', sortable: true,hidden:true},
			  {header: "审批操作", width: 80, dataIndex: 'auditOpType',
				    renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
						if(value == 3){
						    return '提交审批';
						}else if(value == 1){
						    return '审批通过';
						}else if(value == 0){
						    return '审批退回';
						}else{
						    return '待审批';
						}
					}
			  },
			  {header: "审批意见", width: 150, dataIndex: 'auditComment'},
			  {header: "审批级次", width: 80, dataIndex: 'auditOrder',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				        if(record.get('auditOpType') == 3){return '';}
						return value;
				  }
			  },
			  {header: "审批内容", width: 80, dataIndex: 'auditContent', sortable: true},
			  {header: "审批人", width: 80, dataIndex: 'auditPerson', sortable: true},
              {header: "审批时间", width: 100, dataIndex: 'auditTime', sortable: true},
			  {header: "审批度", width: 200, dataIndex: 'auditDegree',
				  renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				        if(record.get('auditOpType') == 3){return '';}
						if(value == 0){
						    return '本级多人情况，必须审批';
						}else{
						    return '本级多人情况，可不审';
						}
					}
			   },
			   {header: "批次id", width: 80, dataIndex: 'batchId',hidden:true}
     ],
       width:360,
       height:600,
	   region: 'center',
	   items: [selectForm]
    });


batchRecordStore.load();

			var win2 = new Ext.Window({
				title: '审批信息',
				closable:true,
				width:720,
				height:250,
				plain:true,
                layout: 'border',
			    modal : true,
				items: [auditHisInforGrid]
			});
			win2.show(this);

}
