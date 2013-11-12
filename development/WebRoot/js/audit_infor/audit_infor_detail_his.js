function onItemHisAuditDetail(obj,auditTypeId, auditFlowId){

	var pageObject = {pageSize:10};
	var pageInfoLabel = new Ext.form.Label();
    pageInfoLabel['setInfor'] = function(){
        this.setText("      当前页: " + pageObject.currentPage + "/" + pageObject.pageCount);
    }
	var gridCheckSele = new Ext.grid.CheckboxSelectionModel();
	var store = new Ext.data.Store({});
	/*var bbarBut = new Ext.PagingToolbarEx({
					pageSize: 10,
					store: store,
					displayInfo: true,
					displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
					emptyMsg: "没有记录"//,
				});
     */
	var grid = new Ext.grid.GridPanel({
				id : 'audit-detail-grid',
				ds : store,
				store: store,
				columns :  [],
				sm:gridCheckSele,
				//stripeRows: true,
				height:350,
				width:850,
				title:'已办工作',
				bbar: new Ext.Toolbar({
					items: [{
						text: '首页',
						listeners : {
							'click' : function() {
								loadData(0,10);
							}
						}
					},{
						text: '上一页',
						listeners : {
							'click' : function() {
								loadData(pageObject.previousIndex,pageObject.pageSize);
							}
						}
					},{
						text: '下一页',
						listeners : {
							'click' : function() {
								loadData(pageObject.nextIndex,pageObject.pageSize);
							}
						}
					},{
						text: '末页',
						listeners : {
							'click' : function() {
								loadData(pageObject.indexes[pageObject.indexes.length - 1],pageObject.pageSize);
							}
						}
					},pageInfoLabel]
				}),
				buttonAlign: 'right'
			});



	var win = new Ext.Window({
		//el:'audit-detail-win',
		title: '已审批信息',
		closable:true,
		width:880,
		height:400,
		plain:true,
	    closeAction:'hide',
		modal : true,
		items: [grid]
	});

win.show();
loadData(0,pageObject.pageSize);



function renderAuditInfor(value, cellmeta, record, rowIndex, columnIndex, store) {
	var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
    return str;
}

function loadData(pstartIndex,ppageSize){
	Ext.Ajax.request({
		method: "post",
		params: { auditType: auditTypeId,flowInforId:auditFlowId,startIndex:pstartIndex,pageSize:ppageSize},
		url: PATH + "/manage/audit/auditInforMangeAction.do?ffc=findAlreadyAuditBusinessInfor",
		success: function(response){
			//Ext.Msg.alert("消息", response.responseText);
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

			store = new Ext.data.Store({
				proxy: new Ext.data.MemoryProxy(dataArray),
				//proxy: new Ext.data.HttpProxy({url:PATH + '/manage/audit/auditInforMangeAction.do?ffc=findWaitAuditBusinessInfor'}),//调用的动作 
				reader: new Ext.data.ArrayReader({}, mappingArray)
			});
			
			var pcolumns = [];
			    //gridCheckSele = new Ext.grid.CheckboxSelectionModel();
				pcolumns.push(new Ext.grid.RowNumberer());
				pcolumns.push(gridCheckSele);
				pcolumns = pcolumns.concat(pageInfor.headers);
				pcolumns.push(
					{"dataIndex":"auditInfor","header":"审批信息","hidden":false,"sortable":false,"width":80,
						"renderer":renderAuditInfor
					}
				);

			grid.reconfigure(store,new Ext.grid.ColumnModel(pcolumns));
			store.load();
			pageObject = pageInfor;
			pageInfoLabel['setInfor']();
		}
   });	

}//end loadData


	var Load=new Object();//实例化对像
	Load.onLoadJs=function(jsName,callback){
		/*Ext.Ajax.request({
			url:jsName,
			success:callback,
			failure:function(d){
				alert("出错了:"+d.responseText);
			},
			jsonData:{},
			method:'GET'
		});*/
	}
}