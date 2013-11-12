cut_tools.contract_account.FfcEditForm = Ext.extend(Ext.form.FormPanel,{
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			cut_tools.contract_account.FfcEditForm.superclass.constructor.call(this, {
					 width:500,  
					 height:270,   
					 frame:true,
					 items:[{
								xtype:'hidden',
								fieldLabel: '帐id',
								name: 'id'
							},{
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
								xtype:'numberfield',
								fieldLabel: this.titleDef + '金额',
								allowBlank:false,
								nanText :this.titleDef + '金额必须为大于零数字',
								name: 'money',
								anchor:'70%',
								style: 'margin-top:1px;'
							},{
								xtype:'textarea',
								fieldLabel: '备注',
								name: 'memo',
								height:50,
								maxLength:30,
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
							},{
								xtype:'hidden',
								fieldLabel: '合同类型',
								name: 'contractType',
								value:0,
								anchor:'70%',
								hidden:true,
								readOnly:true,
								style: 'margin-top:1px;'
							}
							],
						listeners: {
							'render' : function(obj){
								var conInfor = obj.contractAccountInfor;
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
cut_tools.contract_account._FccEditWin = Ext.extend(Ext.Window,{
		nav2:null,
		constructor : function(_cfg){
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.nav2 = new cut_tools.contract_account.FfcEditForm({contractAccountInfor:_cfg.contractAccountInfor,titleDef:this.titleDef});
			cut_tools.contract_account._FccEditWin.superclass.constructor.call(this, {
				title:"创建合同" + this.titleDef,  
				width : Ext.getBody().getWidth(),
				height : 270,
				maximizable :true,	
				resizable:false,
				modal:true,
				plain:true,
				draggable:true,
				constrainHeader : true,
				layout:"fit",  
				buttons : [{
					text : "确定",
					handler : function() {
						var f = this.nav2.getForm();
						var record = new Ext.data.Record(f.getValues());
						if(!f.isValid()){
							return ;
						}
						var m = record.data.money;
						if(Ext.isEmpty(m) || Ext.isNumber(m) || m * 1 <= 0){
							Ext.Msg.show({
								title:'提示',
								msg: this.titleDef + '金额必须为大于零数字!',
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
							return ;
						}
						var contract_account = Ext.util.JSON.encode(record);		
						var url = null;
						if(record.data.id == ''){
							url = PATH + '/contract/contractAccountsAction.do?ffc=addContractAccount';
						}else{
							url = PATH + '/contract/contractAccountsAction.do?ffc=updateContractAccount';
						}
						Ext.Ajax.request({
							url: url,
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
						
					},scope : this
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