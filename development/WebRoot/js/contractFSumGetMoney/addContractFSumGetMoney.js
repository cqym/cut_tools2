Ext.onReady(function(){

	Ext.BLANK_IMAGE_URL = PATH + "/extjs/resources/images/default/s.gif";
	

	cut_tools.contract_account._addGrid = Ext.extend(Ext.form.FormPanel,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			cut_tools.contract_account._addGrid.superclass.constructor.call(this, {
					 width:500,  
					 height:270,   
					 frame:true,
					 items:[
							{
								xtype:'textfield',
								fieldLabel: '合同编号',
								name: 'contractCode',
								anchor:'70%',
								readOnly:true,
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '客户编号',
								name: 'customerCode',
								anchor:'70%',
								readOnly:true,
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '客户名称',
								name: 'customerName',
								anchor:'70%',
								readOnly:true,
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '合同金额',
								name: 'contractMoney',
								anchor:'70%',
								readOnly:true,
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '回款金额',
								name: 'money',
								anchor:'70%',
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '备注',
								name: 'memo',
								height:50,
								anchor:'70%',
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '合同信息ID',
								name: 'contractInforId',
								anchor:'70%',
								hidden:true,
								readOnly:true,
								style: 'margin-top:1px;'
							},{
								xtype:'textfield',
								fieldLabel: '合同信息ID',
								name: 'status',
								value:0,
								anchor:'70%',
								hidden:true,
								readOnly:true,
								style: 'margin-top:1px;'
							}
							],
						listeners: {
							'render' : function(obj){
								var conInfor = obj.contractInfor;
								var fparams = [];
								for(var i in conInfor){
									fparams.push({name:i,mapping:i});
								}
								var RecordClum = Ext.data.Record.create(fparams);
								var myNewRecord = new RecordClum(conInfor); 
								this.getForm().loadRecord(myNewRecord);
							}
						}
			})
		}
	})
		cut_tools.contract_account._addwin = Ext.extend(Ext.Window,{
				nav2:null,
				constructor : function(_cfg){
					if(_cfg == null) {
						_cfg = {};
					}
					Ext.apply(this, _cfg);
					this.nav2 = new cut_tools.contract_account._addGrid({contractInfor:_cfg});
					cut_tools.contract_account._addwin.superclass.constructor.call(this, {
						renderTo: Ext.getBody(),
						title:"创建合同回款",  
						width:500,  
						height:270,  
						resizable:false,
						modal:true,
						plain:true,
						draggable:false,
						layout:"fit",  
						buttons : [{
							text : "确定",
							handler : function() {
								var record = new Ext.data.Record(this.nav2.getForm().getValues());
								var m = record.data.money;
								if(Ext.isEmpty(m) || Ext.isNumber(m) || m * 1 <= 0){
									Ext.Msg.show({
										title:'提示',
										msg: '回款金额必须为大于零数字!',
										buttons: Ext.Msg.OK,
										icon: Ext.MessageBox.INFO
									});
									return ;
								}
								var contract_account = Ext.util.JSON.encode(record);					 		
								Ext.Ajax.request({
									url: PATH + '/contract/contractAccountsAction.do?ffc=addContractAccount',
									params: { conctractAccount : contract_account },
									success : function(response) {
										var responseArray = Ext.util.JSON.decode(response.responseText); 
											 if(responseArray.success == true){
												Ext.Msg.show({
													title:'成功提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.INFO
												});
												this.close();
											 } else {
												Ext.Msg.show({
													title:'错误提示',
													msg: responseArray.msg,
													buttons: Ext.Msg.OK,
													icon: Ext.MessageBox.ERROR
												});
											 } 
									},scope : this
								}
								);
								
							},
						scope : this
						 },{
							text : "取消",
							handler : function() {
								this.close();
							},
							scope : this
						 }],
					items : [this.nav2]
					})
				}
			 })
	
});