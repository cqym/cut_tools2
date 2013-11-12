cut_tools.contract_account._updateGrid = Ext.extend(Ext.form.FormPanel,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			cut_tools.contract_account._updateGrid.superclass.constructor.call(this, {
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
								fieldLabel: 'id',
								name: 'id',
								anchor:'70%',
								hidden:true,
								readOnly:true,
								style: 'margin-top:1px;'
							}
							]

			})
		}
	})

	 cut_tools.contract_account.update_nav2 = Ext.extend(Ext.Panel,{
		updateGrid:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.updateGrid = new cut_tools.contract_account._updateGrid();
			cut_tools.contract_account.update_nav2.superclass.constructor.call(this, {
				width: 500,
				height:270,  
				items : [this.updateGrid]
			})
		}
	 })

		cut_tools.contract_account._updatewin = Ext.extend(Ext.Window,{
				nav2:null,
				constructor : function(_cfg){
					if(_cfg == null) {
						_cfg = {};
					}
					Ext.apply(this, _cfg);
					this.nav2 = new cut_tools.contract_account.update_nav2();					
					cut_tools.contract_account._updatewin.superclass.constructor.call(this, {
						renderTo: Ext.getBody(),
						title:"修改合同回款记录",  
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
								var record = new Ext.data.Record(this.nav2.updateGrid.getForm().getValues());
								var contract_account = Ext.util.JSON.encode(record);					 		
								Ext.Ajax.request({
									url: PATH + '/contract/contractAccountsAction.do?ffc=updateContractAccount',
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

												cut_tools.contract_account.index_grid.getStore().reload();

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