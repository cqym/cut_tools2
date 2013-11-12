Ext.onReady(function(){
			 xxxtree=new Ext.tree.TreePanel( {
				renderTo: '_edit_business_div',
				autoScroll:true,
				autoHeight:true,
				animate:true,
				enableDD:true,
				containerScroll: true,
				border:false,
				rootVisible: false,
				root: new Ext.tree.AsyncTreeNode({id:'edbroot',text:'模块',
					listeners:{
					    'expand':function(n){
							var carr = n.childNodes;
							var f = false;
							for(var i = 0;i < carr.length ;i++){
								if(carr[i].attributes.businessCount > 0){
								    f = true;
									break;
								}
							}
							var comp = Ext.getCmp('editBusinessId');
							if(f){	
								if(comp){
									comp.setIconJump();
								}
							}else{
								if(comp){
									comp.setIconStopJump();
								}
							}
						}
					}
				}),
				dataUrl: PATH + '/workdoing/EditBusinessAction.do?m=getEditBusinessNodes',
				listeners: {
					'dblclick': function(node,evt){
						if(!node.leaf){
							return ;
						}
						if(node.parentNode.id == 'waitEditContract'){//编制合同
							ShowEditContractWindow({id:node.id,quotationType:node.attributes.description},function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return;
						}else if(node.parentNode.id == 'waitEditContractOrder'){//编制合同订单
							ShowEditContractOrderWindow({id:node.id,contract_code:node.attributes.text},function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return ;
						}else if(node.parentNode.id == 'waitEditSelfOrder'){//编制加工订单
							ShowEditContractSelfOrderWindow(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return ;
						}else if(node.parentNode.id == 'waitEditScheduleOrder'){//编制预订订单
						    showScheduleOrderWin(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return ;
						}else if(node.parentNode.id == 'waitEditTryOrder'){//编制试刀订单
							showTryToolsOrderWin(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
							return ;
						}else if(node.parentNode.id == 'waitEditScheduleSelfOrder'){
							showScheduleSelfOrder(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
							return ;
						}else if(node.parentNode.id == 'waitEditTrySelfOrder'){
							showTrySelfOrderWin(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return ;
						}else if(node.parentNode.id == 'waitEditPlan'){
							showPlanEditWin(node.attributes.businessData,function(){
							    var tabp = Ext.getCmp('editBusinessId')
									tabp.load(tabp.autoLoad);
							});
						    return ;
						}
						addTab(node.attributes.description,PATH + node.attributes.url);
					}
				}
			});
			xxxtree.getRootNode().expand(false);

			var ShowEditContractWindow = function(quoData,callBackMethod){
				var quotationType = quoData.quotationType;
				var _url = null;
				var _params = {};
				var idsArr = [quoData.id];
				if(quotationType * 1 == 1){//项目报价
					if(idsArr.length > 1){
						Ext.Msg.alert("消息", "多个项目报价单，不能生成一个合同!");
						return ;
					}
					_url = PATH + "/contract/contractEditAction.do?ffc=consultProjectQuo";
					_params['id'] = idsArr[0];
				}else{
					_params['ids'] = idsArr;
					_url = PATH + "/contract/contractEditAction.do?ffc=consultGeneralQuo";
				}

				Ext.Ajax.request({
				method: "post",
				params: _params,
				url: _url,
				success: function(response){
						//alert(response.responseText);
						eval("var temp = " + response.responseText);
						var conEditWin = new Ext.ffc.ContractEditWindow(
							{
								conctractInfor:temp,
									listeners:{
										"close" : function(){
											callBackMethod();
										}
									}
							}
						);
						conEditWin.show();
				}
				});
		}

		var ShowEditContractOrderWindow = function(data,callBackMethod){
				var contractInforId = data.id;
				var contractCode = data.contract_code;
				Ext.Ajax.request({
						method: "post",
						params: { 'contractId' : contractInforId},
						url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockContractDetail',
						success: function(response){
								eval("var detail=" + response.responseText);
								if(detail.length == 0){
									var win = new cOSupplierWin({
															contract_code:contractCode,
															closeCallBackMethd:function(){
																callBackMethod();
															}
														});
									var store = win.supplier_grid.getStore();
									store.baseParams.contractId = contractInforId;                   
									store.load({params: {start: 0, limit: 15}}); 
									win.show();  
									callBackMethod();
								}else{
									var conEditWin = new Ext.ffc.ContractOutStockEditWindow(
										{   outStockInfor:{contractId:contractInforId,outStockType:1,status:0,contractCode:contractCode,outStockDetails:detail},
											listeners :{
												close : function(p){
													var win = new cOSupplierWin(
														{
															contract_code:contractCode,
															closeCallBackMethd:function(){
																callBackMethod();
															}
														}
													);
													var store = win.supplier_grid.getStore();
													store.baseParams.contractId = contractInforId;                   
													store.load({params: {start: 0, limit: 15}}); 
													win.show();  
													callBackMethod();
												}
											}	
										}
									);
									conEditWin.show();
								}
						}
				});
			}

			var ShowEditContractSelfOrderWindow = function(config,callBackMethod){
				var contractCode = config["contractCode"];
				var customerName = config["customerName"];
				var customerCode = config["customerCode"];
				var contractInforId = config["id"];
				var memo = config["memo"];
				var node = null;
				//var node = this.sOContractTree.getSelectionModel().getSelectedNode();
				Ext.Ajax.request({
							method: "post",
							params: { 'contractId' : contractInforId},
							url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockContractDetail',
							success: function(response){
								eval("var detail=" + response.responseText);
								if(detail.length == 0){
									if(node == null){
										var win = new sOSupplierWin({contract_code:config['contractCode'],ownContactPerson:config['ownContactPerson'],memo:memo});
									}else{
										var win = new sOSupplierWin({contractId:config['id'],contract_code:config['contractCode'],node:Ext.tree.toNewTreeNode(node.attributes,{},true),ownContactPerson:config['ownContactPerson'],memo:memo});
									}
									var store = win.supplier_grid.getStore();
									store.baseParams = {contractId:config['id']};                   
									store.load({params: {start: 0, limit: 15}}); 
									win.show(); 
								}else{
									try{
										var conEditWin = new Ext.ffc.ContractOutStockEditWindow({
												outStockInfor:{contractId:contractInforId,outStockType:1,status:0,contractCode:contractCode,outStockDetails:detail,memo:memo},
												listeners :{
													close : function(p){
														if(node == null){
															var win = new sOSupplierWin({contract_code:config['contractCode'],ownContactPerson:config['ownContactPerson'],memo:memo});
														}else{
															var win = new sOSupplierWin({contractId:config['id'],contract_code:config['contractCode'],node:Ext.tree.toNewTreeNode(node.attributes,{},true),ownContactPerson:config['ownContactPerson'],memo:memo});
														}
														var store = win.supplier_grid.getStore();
														store.baseParams = {contractId:config['id']};                   
														store.load({params: {start: 0, limit: 15}}); 
														win.show(); 
													}
												}	
											});
										conEditWin.show();
									}catch(e){
										alert(e);
									}
								}
							}
						});//ajax
			}

			var showScheduleOrderWin = function(config,callBackMethod){
					var quotationCode = config["quotationCode"];
					var customerName = config["customerName"];
					var customerCode = config["customerCode"];
					var quotationId = config["id"];
					var memo = config["memo"];
					try{
							Ext.Ajax.request({
								method: "post",
								params: { 'quotationInforId' : quotationId},
								url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockQuotationDetail',
								success: function(response){
										eval("var detail=" + response.responseText);
										if(detail.length == 0){
											//Ext.Msg.alert("消息", "所选合同明细中，没有产品可提取库存,确定后将编制采购订单!");
											var win = new qOSupplierWin({quotationId:quotationId,deliveryAddressType:config['deliveryType'],quotationId:config['id'],
												quotationCode:config['quotationCode'],ownContactPerson:config['userName'],customerName:customerName,customerCode:customerCode,memo:memo});
											var store = win.supplier_grid.getStore();
											store.baseParams.qId = quotationId;  
											store.baseParams.leaf = 1;      
											store.load({params: {start: 0, limit: 20}}); 
											win.show();  
											return ;
										}
										var conEditWin = new Ext.ffc.QuotationOutStockEditWindow(
										{
											outStockInfor:{quotationId:quotationId,userName:LoginInfor.user.trueName,customerCode:customerCode,customerName:customerName,outStockType:5,status:0,quotationCode:quotationCode,outStockDetails:detail,memo:memo},
											listeners :{
												close : function(p){
													var win = new qOSupplierWin({quotationId:quotationId,deliveryAddressType:config['deliveryType'],
														quotationCode:config['quotationCode'],ownContactPerson:config['userName'],customerName:customerName,customerCode:customerCode,memo:memo});
													var store = win.supplier_grid.getStore();
													store.baseParams.qId = quotationId;      
													store.baseParams.leaf = 1;      
													store.load({params: {start: 0, limit: 20}}); 
													win.show();  
												}
											}	
										}
									);	
									conEditWin.show();
								}
							});
					}catch(e){
						alert(e);
					}
			}
		var showTryToolsOrderWin = function(config,callBackMethod){
			var quotationCode = config["quotationCode"];
			var customerName = config["customerName"];
			var customerCode = config["customerCode"];
			var contractInforId = config["id"];
			var memo = config["memo"];
			try{
					Ext.Ajax.request({
						method: "post",
						params: { 'quotationInforId' : contractInforId},
						url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockQuotationDetail',
						success: function(response){
								eval("var detail=" + response.responseText);
								if(detail.length == 0){
									//Ext.Msg.alert("消息", "所选合同明细中，没有产品可提取库存,确定后将编制采购订单!");
									var win = new tOSupplierWin({quotationId:contractInforId,deliveryAddressType:config['deliveryType'],
										quotationCode:config['quotationCode'],ownContactPerson:config['userName'],customerName:customerName,customerCode:customerCode,memo:memo});
									var store = win.supplier_grid.getStore();
									store.baseParams.qId = config['id'];       
									store.baseParams.leaf = 1;      
									store.load({params: {start: 0, limit: 20}}); 
									win.show();  
									return ;
								}
								var conEditWin = new Ext.ffc.QuotationOutStockEditWindow(
								{
									outStockInfor:{quotationId:contractInforId,userName:LoginInfor.user.trueName,customerCode:customerCode,outStockType:6,status:0,quotationCode:quotationCode,outStockDetails:detail,memo:memo},
									nextStepButtonTitle: '直接采购',
									listeners :{
										close : function(p){
											var win = new tOSupplierWin({quotationId:contractInforId,deliveryAddressType:config['deliveryType'],
												quotationCode:config['quotationCode'],ownContactPerson:config['userName'],customerName:customerName,customerCode:customerCode,memo:memo});
											var store = win.supplier_grid.getStore();
											store.baseParams.qId = config['id'];   
											store.baseParams.leaf = 1;      
											store.load({params: {start: 0, limit: 20}}); 
											win.show();  
										}
									}	
								}
							);
							conEditWin.show();
						}
					});
			}catch(e){
				alert(e);
			}
		}

		var showScheduleSelfOrder = function(config,callBackMethod){
				var quotationCode = config["quotationCode"];
				var customerName = config["customerName"];
				var customerCode = config["customerCode"];
				var contractInforId = config["id"];
				var memo = config["memo"];
				var customerCode = config["customerCode"];
				var customerName = config["customerName"];
				var node = null;
//							var node = this.qsOContractTree.getSelectionModel().getSelectedNode();
				try{
					Ext.Ajax.request({
							method: "post",
							params: { 'quotationInforId' : contractInforId},
							url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockQuotationDetail',
							success: function(response){
								eval("var detail=" + response.responseText);
								if(detail.length == 0){
									if(node == null){
										var win = new qsOSupplierWin({contract_code:config['quotationCode'],
											ownContactPerson:config['ownContactPerson'],
											memo:memo,
											customerCode:customerCode,
											customerName:customerName});
									}else{
										var win = new qsOSupplierWin({quotationId:config['id'],
											contract_code:config['quotationCode'],
											node:Ext.tree.toNewTreeNode(node.attributes,{},true),
											ownContactPerson:config['ownContactPerson'],
											memo:memo,
											customerCode:customerCode,
											customerName:customerName});
									}
									var store = win.supplier_grid.getStore();
									store.baseParams = {qId:config['id'],leaf:0};                   
									store.load({params: {start: 0, limit: 15}}); 
									win.show(); 
									return ;
								}
								var conEditWin = new Ext.ffc.ContractOutStockEditWindow(
									{
										outStockInfor:{quotationId:contractInforId,outStockType:1,status:0,quotationCode:quotationCode,outStockDetails:detail,memo:memo},
										listeners :{
											close : function(p){
												if(node == null){
													var win = new qsOSupplierWin({
														contract_code:config['quotationCode'],
														ownContactPerson:config['ownContactPerson'],
														memo:memo,
														customerCode:customerCode,
														customerName:customerName});
												}else{
													var win = new qsOSupplierWin({quotationId:config['id'],
														contract_code:config['quotationCode'],
														node:Ext.tree.toNewTreeNode(node.attributes,{},true),
														ownContactPerson:config['ownContactPerson'],
														memo:memo,
														customerCode:customerCode,
														customerName:customerName});
												}
												var store = win.supplier_grid.getStore();
												store.baseParams = {qId:config['id'],leaf:0};                   
												store.load({params: {start: 0, limit: 15}}); 
												win.show(); 
											}
										}	
									}
								);
								conEditWin.show();
						}
					});
				}catch(e){
					alert(e);
				}
		}	

		var showTrySelfOrderWin = function(config,callBackMethod){
			var quotationCode = config["quotationCode"];
			var customerName = config["customerName"];
			var customerCode = config["customerCode"];
			var contractInforId = config["id"];
			var memo = config["memo"];
			var customerCode = config["customerCode"];
			var customerName = config["customerName"];
			var node = null;
			try{
				Ext.Ajax.request({
				method: "post",
				params: { 'quotationInforId' : contractInforId},
				url: PATH + '/outStock/outStockEditAction.do?ffc=getWillOutStockQuotationDetail',
				success: function(response){
						eval("var detail=" + response.responseText);
						if(detail.length == 0){
							if(node == null){
								var win = new tsOSupplierWin({contract_code:config['quotationCode'],
									ownContactPerson:config['ownContactPerson'],
									memo:memo,
									customerCode:customerCode,
									customerName:customerName
								});
							}else{
								var win = new tsOSupplierWin({quotationId:config['id'],
									contract_code:config['quotationCode'],
									node:Ext.tree.toNewTreeNode(node.attributes,{},true),
									ownContactPerson:config['ownContactPerson'],
									memo:memo,
									customerCode:customerCode,
									customerName:customerName
								});
							}
							var store = win.supplier_grid.getStore();
							store.baseParams = {qId:config['id'],leaf:0};                   
							store.load({params: {start: 0, limit: 15}}); 
							win.show(); 
							return ;
						}
						var conEditWin = new Ext.ffc.ContractOutStockEditWindow(
							{
								outStockInfor:{quotationId:contractInforId,outStockType:6,status:0,quotationCode:quotationCode,outStockDetails:[],memo:memo},
								listeners :{
									close : function(p){
										if(node == null){
											var win = new tsOSupplierWin({contract_code:config['quotationCode'],
												ownContactPerson:config['ownContactPerson'],
												memo:memo,
												customerCode:customerCode,
												customerName:customerName});
										}else{
											var win = new tsOSupplierWin({quotationId:config['id'],
												contract_code:config['quotationCode'],
												node:Ext.tree.toNewTreeNode(node.attributes,{},true),
												ownContactPerson:config['ownContactPerson'],
												memo:memo,
												customerCode:customerCode,
												customerName:customerName
											});
										}
										var store = win.supplier_grid.getStore();
										store.baseParams = {qId:config['id'],leaf:0};                   
										store.load({params: {start: 0, limit: 15}}); 
										win.show(); 
									}
								}	
							}
						);
						conEditWin.show();
					}
				});
			}catch(e){
				alert(e);
			}
		}

		var showPlanEditWin = function(config,callBackMethod){
			
			var idsArr = [];
			var process_orderId = config["id"];
			var orderCode = config["orderCode"];
			var contractCode = config["contractCode"];

			Ext.Ajax.request({
					method: "post",
					params: { 'orderId' : process_orderId},
					url: PATH + '/reservePlan/ReservePlanEditAction.do?ffc=consultReserveInfors',
					success: function(response){
						try{
							eval("var ReservePlanMainInfor=" + response.responseText);
							var conEditWin = new Ext.ffc.ReservePlanEditWindow(
								{
									ReservePlanInfor:ReservePlanMainInfor,isAddWin:true
								}
							);
							conEditWin.on("close", function(){
								ReservePlanListStore.reload();
								EventMger.fireEvent("createdPlanEvent");
							});
							conEditWin.show();
						}catch(e){alert(e);}
					}
			});
		}
 });
