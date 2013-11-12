
Ext.ffc.MaterialOutStockInfoForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var f_config = {layout: 'absolute',
						defaultType: 'textfield',
						frame: true,
						items:[
							//1
							{xtype:'label',text: '出库单号',x:0,y:5,style:this.lableStyle_},
							{xtype:'textfield',  name: 'outStockCode',readOnly : true,x:90,y:3,width:170},
							{xtype:'label',text: '合同编号',x:250,y:5,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '合同编号', readOnly : true, name: 'contractCode',x:340,y:3,width:170},
							//2
							{xtype:'label',text: '出库日期',x:0,y:33,style:this.lableStyle_},
							{xtype:'datefield',value:new Date(),fieldLabel: '出库日期',format:'Y-m-d',  name: 'outStockDate',emptyText:'',x:90,y:31,width:170,allowBlank:false,blankText:'出库日期不能为空!'},
							{xtype:'label',text: '客户名称',x:250,y:33,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '客户名称', readOnly : true, name: 'customerName',x:340,y:31,width:170},
							//3
							{xtype:'label',text: '出库性质',x:0,y:61,style:this.lableStyle_},
							new Ext.ffc.OutStockTypesComboBox({readOnly : true,disabled:true,x:90,y:59,width:170}),
							{xtype:'label',text: '制   单   人',x:250,y:61,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '制   单   人', name: 'userName',x:340,y:59,width:170},
							//4
							{xtype:'label',text: '出库状态',x:0,y:91,style:this.lableStyle_},
							new Ext.ffc.OutStockStatusComboBox({readOnly : true,disabled:true,x:91,y:88,width:170}),
							//5
							{xtype:'label',text: '备　　注',x:0,y:121,style:this.lableStyle_},
							{xtype:'textfield',fieldLabel: '备　　注',  name: 'memo',x:91,y:118,width:418},
							//hidden
							{xtype:'hidden',fieldLabel: '合同id', readOnly : true, name: 'contractId'},
							{xtype:'hidden',fieldLabel: 'id', readOnly : true, name: 'id'},
							{xtype:'hidden',fieldLabel: '客户编号', readOnly : true, name: 'customerCode'}
							]//items
		};

		Ext.ffc.MaterialOutStockInfoForm.superclass.constructor.call(this, f_config)
	}
	
});

Ext.ffc.MaterialOutStockAfterUpdateNode = function(thisObj,ed,value,oldValue){
	var editColName = thisObj.editColIndex;
	var nValue = value * 1;
	var leaf = thisObj.editNode.attributes.leaf;
	leaf = leaf * 1;
	if(!leaf|| !Ext.isNumber(nValue) || nValue < 0){
		ed.editNode.cols[editColName] = oldValue;
		ed.editNode.attributes[editColName] = oldValue;
		ed.editNode.ui.setInnerHTMLValue(editColName,oldValue);
		return ;
	}
	var reserveAmount = ed.editNode.cols['reserveAmount'];//销售库存数量
	var contractAmount = ed.editNode.cols['contractAmount'];//合同数量
	var amount = ed.editNode.cols['amount'];//本次销售库提取数量
	var oldAmount = ed.editNode.cols['oldAmount'];//本次销售库出库原始值
	var needAmount = ed.editNode.cols['needAmount'];//需提数量
    var matReserveAmount = ed.editNode.cols['matReserveAmount'];//材料库数量
	var matOutAmount = ed.editNode.cols['matOutAmount'];//本次材料库提取数量
	var matOldAmount = ed.editNode.cols['matOldAmount'];//材料库出库原始值

	contractAmount = !Ext.isNumber(contractAmount) ? 0 : contractAmount;
	reserveAmount = !Ext.isNumber(reserveAmount) ? 0 : reserveAmount;
	amount = !Ext.isNumber(amount) ? 0 : amount;
	oldAmount = !Ext.isNumber(oldAmount) ? 0 : oldAmount;
	needAmount = !Ext.isNumber(needAmount) ? 0 : needAmount;
	matReserveAmount = !Ext.isNumber(matReserveAmount) ? 0 : matReserveAmount;
	matOutAmount = !Ext.isNumber(matOutAmount) ? 0 : matOutAmount;
	matOldAmount = !Ext.isNumber(matOldAmount) ? 0 : matOldAmount;

	var _sum = 0;
	_sum = _sum + (editColName == 'amount' ? nValue : amount);
	_sum = _sum + (editColName == 'matOutAmount' ? nValue : matOutAmount);
	if(_sum > needAmount){
		Ext.Msg.alert("消息", "出库数量不能大于需提数量!");	
		ed.editNode.cols[editColName] = oldValue;
		ed.editNode.attributes[editColName] = oldValue;
		ed.editNode.ui.setInnerHTMLValue(editColName,oldValue);
		return ;
	}

	if(nValue - oldValue > reserveAmount && nValue != oldAmount && editColName == 'amount' ){
		Ext.Msg.alert("消息", "销售库出库数量不能大于销售库存数量!");	
		ed.editNode.cols[editColName] = oldValue;
		ed.editNode.attributes[editColName] = oldValue;
		ed.editNode.ui.setInnerHTMLValue(editColName,oldValue);
		return ;
	}
	
	if(nValue - oldValue > matReserveAmount && nValue != matOldAmount && editColName == 'matOutAmount' ){
		Ext.Msg.alert("消息", "材料库出库数量不能大于材料库存数量!");	
		ed.editNode.cols[editColName] = oldValue;
		ed.editNode.attributes[editColName] = oldValue;
		ed.editNode.ui.setInnerHTMLValue(editColName,oldValue);
		return ;
	}
}

Ext.ffc.MaterialOutStockNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.simpleForm = new Ext.ffc.MaterialOutStockInfoForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var materialOutStockInfor = container.ownerCt.ownerCt.materialOutStockInfor;
			
			var params = [];
			for(var i in materialOutStockInfor){
				if(!materialOutStockInfor[i] || !materialOutStockInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,materialOutStockInfor);
			var myNewRecord = new RecordClum(recordParams); 
			this.ownerCt.simpleForm.getForm().loadRecord(myNewRecord);

		}); 
		
		Ext.ffc.MaterialOutStockNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
				layout: 'fit',
                iconCls:'icon-grid',
                title: '原材料出库',
                split: true,
                width: 1050,
                height : 200,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});

Ext.ffc.getMaterialOutStockEditTree = function (_id){
 return new Ext.tree.ColumnTree({
			height: 470,
			bodyStyle:'width:100%',
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        columns:[{
	            header:'',
	            width:100,
	            resizable : true,
	            dataIndex:'ss',
	            disEnableEdit:true
	        },{
	            header:'项目编号',
	            width:100,
	            //hidden : true,
	            dataIndex:'projectCode',
	            disEnableEdit:true
	        },{
	            header:'序号',
	            width:50,
	            hidden : true,
	            dataIndex:'serialNumber',
	            disEnableEdit:true
	        },{
	            header:'货品编号',
	            width:100,
	            resizable : true,
	            dataIndex:'productCode',
	            disEnableEdit:true
	        },{
	            header:'工具牌号',
	            width:200,
	            resizable : true,
	            dataIndex:'brandCode',
	            disEnableEdit:true
	        },{
	            header:'名称',
	            width:100,
	            dataIndex:'productName',
	            disEnableEdit:true
	        },{
	            header:'数量',
	            width:80,
	            dataIndex:'contractAmount',
				disEnableEdit:true
	        },{
	            header:'已提数量',
	            width:80,
	            dataIndex:'allOutAmount',
				disEnableEdit:true
	        },{
	            header:'需提数量',
	            width:80,
	            dataIndex:'needAmount',
				disEnableEdit:true
	        },{
	            header:'销售库存数量',
	            width:100,
	            dataIndex:'reserveAmount',
				disEnableEdit:true
	        },{
	            header:'本次销售库提取数量',
	            width:120,
	            dataIndex:'amount'
	        },{
	            header:'材料库数量',
	            width:80,
	            dataIndex:'matReserveAmount',
				disEnableEdit:true
	        },{
	            header:'本次材料库提取数量',
	            width:120,
	            dataIndex:'matOutAmount'
	        },{
	            header:'计量单位',
	            width:80,
	            dataIndex:'productUnit',
	            disEnableEdit:true
	        },{
	            header:'品牌',
	            width:80,
	            dataIndex:'productBrand',
				disEnableEdit:true
	        },{
	            header:'备注',
	            width:120,
	            dataIndex:'memo'
	        },{
	            header:'备注2',
	            width:120,
	            dataIndex:'memo2'
	        },{
	            header:'销售库出库原始值',
	            width:80,
	            dataIndex:'oldAmount',
				hidden : true,
				disEnableEdit:true
	        },{
	            header:'材料库出库原始值',
	            width:80,
	            dataIndex:'matOldAmount',
				hidden : true,
				disEnableEdit:true
	        },{
	            header:'',
	            width:80,
	            dataIndex:'contractProductDetailId',
				hidden : true,
				disEnableEdit:true
	        }],
	        loader: new Ext.tree.TreeLoader({
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeNode({
	        	id:"root",
	            text:'Tasks'
	        }),
			listeners : {
	        	'afterrender' : function(tree) {
					var root = tree.getRootNode();
						tree.expandAll();
	        	}
	        }
	    });
}

/**
 * 容纳产品树容器
 * @class centerPanel
 * @extends Ext.Panel
 */
Ext.ffc.MaterialOutStockEditCenterPanel = Ext.extend(Ext.Panel, {
	ffc_key : 'Ext.ffc.MaterialOutStockEditCenterPanel',
	reservePlanTree:null,
	reservePlanEditTree:null,
	proDetail:null,
	loadTreeNode:function(){
		var root = this.reservePlanTree.getRootNode();
		var treeLoader = this.reservePlanTree.getLoader() ;
		for(var i = 0; i < this.proDetail.length;i++){
			root.appendChild(treeLoader.createNode(this.proDetail[i])); 
		}
	},
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		if(_cfg.readOnly){
			delete _cfg.tbar;
		}
		Ext.apply(this, _cfg);
		this.reservePlanTree = Ext.ffc.getMaterialOutStockEditTree();
		var sm = this.reservePlanTree.getSelectionModel()
		if(sm){
			sm.on('beforeselect',function(thisObj, bNode, aNode){
				if(bNode.attributes.leaf != 1){
					return false;
				}
				return true;
			});
		}
		this.reservePlanEditTree =  new Ext.tree.ColumnTreeEditor(this.reservePlanTree,{
				       completeOnEnter: true,
				       autosize: true,
				       ignoreNoChange: true,
					   allowBlank: false,
					   listeners : {
							specialkey : Ext.ffc.gridTreeEditorkeyMove
					   }
				    });
		this.loadTreeNode();
		this.reservePlanEditTree.on('fccAfterUpdateNodeEvent',Ext.ffc.MaterialOutStockAfterUpdateNode);
		Ext.ffc.MaterialOutStockEditCenterPanel.superclass.constructor.call(this, {
			region: 'center',
			layout: 'fit',
			split: true,
			height: 100,
			minSize: 100,
			maxSize: 200,
			collapsible: true,
			margins: '-5 5 5 5',
			items : [this.reservePlanTree]
		})
	}
})

Ext.ffc.MaterialOutStockEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	outStockInfor : null,
	readOnly : false,
	materialOutStockInfor : null,
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.topPanel = new Ext.ffc.MaterialOutStockNorthPanel(
				{
					readOnly:_cfg.readOnly
				}
			);
		
		this.centerPanel = new Ext.ffc.MaterialOutStockEditCenterPanel(
			{
			 proDetail:this.materialOutStockInfor.outStockDetails,
			 readOnly:_cfg.readOnly,
					tbar:[
						{xtype:'button',text:'自动提取销售库存',iconCls : 'icon-out-stock',
							handler : function(obj) {
								try{
									var tree = this.ownerCt.ownerCt.reservePlanTree;
									var root = tree.getRootNode();
										tree.expandAll();
										var leafNodes = Ext.tree.getAllLeafNodeObject(root);
										var minValue = 0;
										var needAmount = 0;
										var reserveAmount = 0;
										var oldAmount = 0;
										var matOutAmount = 0;
										for(var i = 0 ;i < leafNodes.length;i++){
											needAmount = leafNodes[i].attributes['needAmount'];
											reserveAmount = leafNodes[i].attributes['reserveAmount'];
											oldAmount = leafNodes[i].attributes['oldAmount'];
											matOutAmount = leafNodes[i].attributes['matOutAmount'];
											

											reserveAmount = !Ext.isNumber(reserveAmount) ? 0 : reserveAmount;
											needAmount = !Ext.isNumber(needAmount) ? 0 : needAmount;
											oldAmount = !Ext.isNumber(oldAmount) ? 0 : oldAmount;
											matOutAmount = !Ext.isNumber(matOutAmount) ? 0 : matOutAmount;
											needAmount = needAmount - matOutAmount;

											minValue = reserveAmount > needAmount ? needAmount : reserveAmount;
											minValue = minValue > oldAmount ? minValue : oldAmount;
											leafNodes[i].cols['amount'] = minValue;
											leafNodes[i].attributes['amount'] = minValue;
											leafNodes[i].ui.setInnerHTMLValue('amount',minValue);
										}
								}catch(e){
									alert(e);
								}
							}
						},{xtype:'button',text:'自动提取材料库存',iconCls : 'icon-out-stock',
							handler : function(obj) {
								try{
									var tree = this.ownerCt.ownerCt.reservePlanTree;
									var root = tree.getRootNode();
										tree.expandAll();
										var leafNodes = Ext.tree.getAllLeafNodeObject(root);
										var minValue = 0;
										var needAmount = 0;
										var matReserveAmount = 0;
										var matOldAmount = 0;
										var amount = 0;
										for(var i = 0 ;i < leafNodes.length;i++){
											needAmount = leafNodes[i].attributes['needAmount'];
											matReserveAmount = leafNodes[i].attributes['matReserveAmount'];
											matOldAmount = leafNodes[i].attributes['matOldAmount'];
											amount = leafNodes[i].attributes['amount'];

											matReserveAmount = !Ext.isNumber(matReserveAmount) ? 0 : matReserveAmount;
											needAmount = !Ext.isNumber(needAmount) ? 0 : needAmount;
											matOldAmount = !Ext.isNumber(matOldAmount) ? 0 : matOldAmount;
											amount = !Ext.isNumber(amount) ? 0 : amount;
											needAmount = needAmount - amount;

											minValue = matReserveAmount > needAmount ? needAmount : matReserveAmount;
											minValue = minValue > matOldAmount ? minValue : matOldAmount;
											leafNodes[i].cols['matOutAmount'] = minValue;
											leafNodes[i].attributes['matOutAmount'] = minValue;
											leafNodes[i].ui.setInnerHTMLValue('matOutAmount',minValue);
										}
								}catch(e){
									alert(e);
								}
							}
						}
					]}
		);
		
		Ext.ffc.MaterialOutStockEditWindow.superclass.constructor.call(this, {
			constrainHeader : true,
			width : 1050,
			height : 600,
			//title :  '出库单编制',
			layout :  'border',
			maximizable :true,
			items : [this.topPanel,this.centerPanel],
			buttons: [{
							text : "保  存",
							hidden : this.readOnly,
							handler : function() {
									var form = this.topPanel.simpleForm;//obj.ownerCt.ownerCt;
									var currWin = form.ownerCt.ownerCt;
									var formValues = form.getForm().getValues();
									var outStockInfor = {};
									Ext.apply(outStockInfor,formValues);

									var outStockDetails = currWin.materialOutStockInfor.outStockDetails;
									//得到所有出库数量不为0的叶子节点
									var leafNodes = [];
									var tempArray = [];
									for(var i = 0 ;i < outStockDetails.length;i++){
										tempArray = tempArray.concat(Ext.tree.getAllLeafNode(outStockDetails[i]));
									}
									for(var i = 0 ;i < tempArray.length;i++){
										//if(tempArray[i].amount * 1 > 0){
											tempArray[i]['projectCode'] = 0;
											tempArray[i]['serialNumber'] = 0;
											leafNodes.push(tempArray[i]);
										//}
									}
									if(leafNodes.length == 0){ 
										Ext.Msg.alert("消息", "请至少有一个材料出库数量大于零!");	
										return;
									}
									outStockInfor["outStockType"] = 3;//材料出库
									outStockInfor["outStockDetails"] = leafNodes;
									
									var serUrl = '';
									if(formValues["id"] == ''){//add
										serUrl = PATH + '/outStock/MaterialOutStockEditAction.do?ffc=addOutStock';
									}else{//update
										serUrl = PATH + '/outStock/MaterialOutStockEditAction.do?ffc=updateOutStock';
									}
								    //Ext.ffc.util.showDebugString(Ext.encode(outStockInfor));
									Ext.Ajax.request({
											method: "post",
											params: { OutStockInfor : Ext.encode(outStockInfor)},
											url: serUrl,
											success: function(response){
												
												if(response.responseText * 1 == 1){
													Ext.Msg.alert("消息", "保存成功!");	
													currWin.close();
												}else{
													//response.responseText,可返回以得到当前超量数据
													eval("var tt = " + response.responseText);
													try{
														var tree = currWin.centerPanel.reservePlanTree;
														var root = tree.getRootNode();
															tree.expandAll();
															var node = Ext.ffc.findChildNodeByAtr(root,'id',tt.id);
															if(node){
																node.select();
																node.cols['reserveAmount'] = tt.reserveAmount;
																node.attributes['reserveAmount'] = tt.reserveAmount;
																node.ui.setInnerHTMLValue('reserveAmount',tt.reserveAmount);
															} 
													}catch(e){
														alert(e);
													}

													Ext.Msg.alert("消息", "有超量出库!");	
												}
											}
									});
								
							},scope : this
					   },
					  {
							text : "取 消",
							handler : function() {
								this.close();
							},scope : this	
					   }
					 ]
		});
	}
});  
