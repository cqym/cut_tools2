function getReserve2ContractStore(quoDetailId){
	return new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({url: PATH + "/contract/Schedue2ContractAction.do?ffc=findSchedue2ConInfors&quoDetailId=" + quoDetailId}),
					reader: new Ext.data.JsonReader(
								{ totalProperty: "totalCount",root: "items"}, 
								["contractProductDetailId","contractCode","brandCode","amount","sumOrderAmount","sumArrivalAmount",
						         "sumOutAmount","sumDeliveryAmount","status"]
							)
				});
}

Ext.ffc.Reserve2ContractGrid = Ext.extend(Ext.grid.EditorGridPanel,{
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = getReserve2ContractStore(_cfg.quoDetailId);
		this.store.load();
			this.tbar = [{
						text:'保存',
						iconCls:'save-icon',
						listeners: {
							'click' : function(obj){
								var g = obj.ownerCt.ownerCt;
								//var selectedItem = g.getSelectionModel().getCount();					
								var arr = [];
								var store = g.getStore();
								for(var i = 0 ,len = store.getCount(); i < len ; i++){
							        var data = store.getAt(i).data;
									arr.push(data);
								}
											Ext.Ajax.request({
												url: PATH + '/contract/Schedue2ContractAction.do?ffc=updateShedQuo2ConDetail',
												params: { conDetailJsonStr: Ext.encode(arr)},
												success : function(response) {
													var responseArray = Ext.util.JSON.decode(response.responseText); 
													 if(responseArray.success == true){
														Ext.Msg.show({
															title:'成功提示',
															msg: responseArray.msg,
															buttons: Ext.Msg.OK,
															icon: Ext.MessageBox.INFO
														});
														store.reload();
													 } else {
														Ext.Msg.show({
															title:'错误提示',
															msg: responseArray.msg,
															buttons: Ext.Msg.OK,
															icon: Ext.MessageBox.ERROR
														});
													 } 
												},scope : this
											});										
							},scope:this
						}
					}];
		
		Ext.ffc.Reserve2ContractGrid.superclass.constructor.call(this, {
				ds : this.store,
				columns: [
					new Ext.grid.RowNumberer(),
					{header: "contractProductDetailId",dataIndex: "contractProductDetailId",width: 200,hidden:true},
					{header: "合同编号",dataIndex: 'contractCode',width: 200},
					{header: "牌号",dataIndex: 'brandCode', width: 200},
					{header: "合同数量",dataIndex: 'amount',width: 100},
					{header: "合同订单数量",dataIndex: 'sumOrderAmount',width: 100,editor:new Ext.form.NumberField()},
					{header: "合同已到数量",dataIndex: 'sumArrivalAmount',width: 100,editor:new Ext.form.NumberField()},
					{header: "合同提取库存数量",dataIndex: 'sumOutAmount',width: 110,editor:new Ext.form.NumberField()},
					{header: "合同已交数量",dataIndex: 'sumDeliveryAmount',width: 100,editor:new Ext.form.NumberField()},
					{header: "合同状态",dataIndex: 'status',width: 80,
						renderer:function(value){
							var arr = [[0,'<span style="color:#990000">编制</span>'],
							[1,'<span style="color:#99CC00">待审批</span>'],
							[2,'<span style="color:#0033FF">审批通过</span>'],
							[3,'<span style="color:#FF3300">审批退回</span>'],
							[4,'<span style="color:#339933">执行</span>'],
							[5,'<span style="color:#330000">终结</span>'],
							[-1,'<span style="color:#33FFFF">作废</span>']];
							for(var i = 0;i < arr.length ;i++){
							    if(value == arr[i][0]){
								    return arr[i][1];
								}
							}
							return value;
						}
					}
				]
		})
	}	
});	
	
Ext.ffc.Reserve2ContractGridWindow = Ext.extend(Ext.Window, {  
	grid:null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.grid = new Ext.ffc.Reserve2ContractGrid({quoDetailId:_cfg.quoDetailId});
		Ext.ffc.Reserve2ContractGridWindow.superclass.constructor.call(this, {
			width : Ext.getBody().getWidth(),
			height : 600,
			constrainHeader : true,
			modal : true,
			title :  '预订转合同明细信息',
			layout :  'fit',
			items : [this.grid]
		});
	}
});