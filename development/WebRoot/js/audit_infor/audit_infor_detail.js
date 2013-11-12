
function onItemAuditDetail(obj,auditTypeId, auditFlowId){

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
				title:'待办工作',
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
var textAreaObj = new Ext.form.TextArea({
		   fieldLabel: '审批意见',
		   name:'auditMessage',
		   //id:'auditMessage',
		   width: 800
		});
var  auditForm = new Ext.FormPanel({
	    id:'auditInforForm',
        labelWidth: 75, 
        //url:'save-form.php',
        frame:true,
		region: 'center',
        bodyStyle:'padding:5px 5px 0',
        width: 850,
        defaults: {width: 740},
        defaultType: 'textfield',

        items: [textAreaObj],

        buttons: [{
            text: '审批通过',
		    handler:function(){
				var arr = gridCheckSele.getSelections();
				if(arr.length == 0){
					Ext.MessageBox.alert('系统提示', '请选择要审批的记录!', function(){});
				    return false;
				}
				
				if(auditForm.findByType("textfield")[0].getEl().dom.value == ''){
					Ext.MessageBox.alert('系统提示', '请填写审批意见!', function(){});
				    return false;
				}
				
				var pbussinessIds = [];
				for(var i = 0 ;i < arr.length;i++){
				    pbussinessIds.push(arr[i].get("id"));
				}
				
				var pcomment = auditForm.findByType("textfield")[0].getEl().dom.value;
			    Ext.MessageBox.confirm('系统提示', '请确认是否要要将所选记录,审批通过!', function(btn){
					if(btn != 'yes'){return ;}
					executeAudit(pbussinessIds,auditTypeId,auditFlowId,pcomment,1)
				});
			}
        },{
            text: '审批退回',
		    handler:function(){
				var arr = gridCheckSele.getSelections();
				if(arr.length == 0){
					Ext.MessageBox.alert('系统提示', '请选择要审批的记录!', function(){});
				    return false;
				}
				if(auditForm.findByType("textfield")[0].getEl().dom.value == ''){
					Ext.MessageBox.alert('系统提示', '请填写审批意见!', function(){});
				    return false;
				}
				var pbussinessIds = [];
				for(var i = 0 ;i < arr.length;i++){
				    pbussinessIds.push(arr[i].get("id"));
				}
				var pcomment = auditForm.findByType("textfield")[0].getEl().dom.value;
				Ext.MessageBox.confirm('系统提示', '请确认是否要要将所选记录,审批退回!', function(btn){
					if(btn != 'yes'){return ;}
					executeAudit(pbussinessIds,auditTypeId,auditFlowId,pcomment,0)
				});
			}
        }]
    });

//auditForm.add(textAreaObj);

	var win = new Ext.Window({
		//el:'audit-detail-win',
		title: '待审批信息',
		closable:true,
		width:880,
		height:500,
		plain:true,
	    closeAction:'hide',
		modal : true,
		items: [grid,auditForm]
	});
//alert(win.findById('auditInforForm'));
//if(!win.findById('auditInforForm')){
//	win.add(grid);
//	win.add(auditForm);
//}

win.show();
loadData(0,pageObject.pageSize);


function renderAuditInfor(value, cellmeta, record, rowIndex, columnIndex, store) {
	var str = "<a href=\"javascript:onAuditInfor(this,\'" + record.get('id') + "\');\">查看</a>";
    return str;
}
function renderBusinessInfor(value, cellmeta, record, rowIndex, columnIndex, store){
	//alert(record.get('id'));
	//alert(record.get('url'));
    var str = "<a href=\"javascript:loadBusinessInfor(\'" + record.get('url') + "\',\'" + record.get('id') + "\');\">查看</a>";
    return str;
}
function loadData(pstartIndex,ppageSize){
	Ext.Ajax.request({
		method: "post",
		params: { auditType: auditTypeId,flowInforId:auditFlowId,startIndex:pstartIndex,pageSize:ppageSize},
		url: PATH + "/manage/audit/auditInforMangeAction.do?ffc=findWaitAuditBusinessInfor",
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
					{"dataIndex":"auditInfor","header":"查看详细","hidden":false,"sortable":false,"width":80,
						"renderer":renderBusinessInfor
					}
				);
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

	function executeAudit(pbussinessIds,auditTypeId,auditFlowId,pcomment,popType){
		Ext.Ajax.request({
				method: "post",
				params: { bussinessIds:pbussinessIds,auditType: auditTypeId,auditInforId:auditFlowId,comment:pcomment,opType:popType},
				url: PATH + "/manage/audit/auditInforMangeAction.do?ffc=executeAudit",
				success: function(response){
					Ext.MessageBox.alert('系统提示', '审批成功!', function(){
					    loadData(pageObject.startIndex,pageObject.pageSize);
						auditForm.get('auditMessage').getEl().dom.value = '';
					});
					
				}
		});
	}
	
}