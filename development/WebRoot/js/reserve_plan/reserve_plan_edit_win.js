Ext.ffc.reservePlanForm = Ext.extend(Ext.FormPanel, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var f_config = {
						width : 1045,//this['width'] == null ? 1000 : this['width'],
                        labelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;',
                        frame:true,labelWidth:100,monitorValid:false,
                        items:[
                            {layout:'column',border:false,labelSeparator:':',
                            defaults:{layout: 'form',border:false,columnWidth:.3},
                            items:[
                                {items: [{xtype:'textfield',fieldLabel: '合同编号', readOnly : true, name: 'contractCode',anchor:'95%'}]},
								{items: [{xtype:'textfield',fieldLabel: '加工订单编号', readOnly : true, name: 'orderCode',anchor:'95%'},{xtype:'hidden',fieldLabel: '订单id', readOnly : true, name: 'orderInforId',anchor:'90%'}]}
                                ]//items
                                }
                        ]//items
		};

		Ext.ffc.reservePlanForm.superclass.constructor.call(this, f_config)////-----------------------------Ext.ffc.ContractInfoForm.superclass.constructor.call
	}
	
});

Ext.ffc.ReservePlanNorthPanel = Ext.extend(Ext.Panel, {
	simpleForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.simpleForm = new Ext.ffc.reservePlanForm({readOnly : _cfg.readOnly});
		this.simpleForm.on("afterrender",function(container, layout ){
			var ReservePlanInfor = container.ownerCt.ownerCt.ReservePlanInfor;
			
			var params = [];
			for(var i in ReservePlanInfor){
				if(!ReservePlanInfor[i] || !ReservePlanInfor[i].length){
				    params.push({name:i,mapping:i});
				}
			}

			var RecordClum = Ext.data.Record.create(params);
			var recordParams = {};

			Ext.apply(recordParams,ReservePlanInfor);
			var myNewRecord = new RecordClum(recordParams); 
			this.ownerCt.simpleForm.getForm().loadRecord(myNewRecord);

		}); 
		
		Ext.ffc.ReservePlanNorthPanel.superclass.constructor.call(this, {
			    region: 'north',
                iconCls:'icon-grid',
                layout: 'fit',
                split: true,
                width: 1050,
                height : 50,
                minSize: 175,
                maxSize: 840,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.simpleForm]
		})
	}
});

Ext.ffc.getReservePlanEditTree = function (_id){
 return new Ext.tree.ColumnTree({
			//el:'quoProductTreeEl',
	        //renderTo : Ext.getBody(),
	        //width:1050,
			height: 470,
			bodyStyle:'width:100%',
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	       // title: '产品信息',
	        columns:[{
	            //header:'货品编号',
	            width:100,
	            resizable : true,
	            //dataIndex:'productCode',
	            disEnableEdit:true
	        },{
	            header:'合同分项',
	            width:200,
	            resizable : true,
	            dataIndex:'proSortName',
	            disEnableEdit:true
	        },{
	            header:'项目编号',
	            width:80,
	            resizable : true,
	            dataIndex:'projectCode',
	            disEnableEdit:true
	        },{
	            header:'序号',
	            width:40,
	            resizable : true,
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
	            width:190,
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
	            dataIndex:'orderAmount'
	        },{
	            header:'已出库数量',
	            width:80,
	            dataIndex:'remainAmount',
				disEnableEdit:true
	        },{
	            header:'计划数量',
	            width:80,
	            dataIndex:'planAmount'
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
	            width:80,
	            dataIndex:'memo',
				disEnableEdit:false
	        }],
	        
	        listeners : {
	        	'afterrender' : function(tree) {
					var defultSelectPlanId = tree.ownerCt.ownerCt.defultSelectPlanId;
					var root = tree.getRootNode();
						tree.expandAll();
	        	},
				'expandnode' : function( node ) {
					var defultSelectPlanId = node.getOwnerTree().ownerCt.ownerCt.defultSelectPlanId;
					if(defultSelectPlanId == null) return ;	
					if(node.id == defultSelectPlanId){
						node.select();
					} 
				}
	        },
	
	        loader: new Ext.tree.TreeLoader({
	            //dataUrl:PATH + '/generalQuo/getQuoDetailAction.do',
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
	    });
}

Ext.ffc.reservePlanAfterUpdateNode = function(thisObj,ed,value,oldValue){
	//var productSorts = thisObj.tree.ownerCt.ownerCt.ReservePlanInfor.reservePlanDetail;
	var editColName = thisObj.editColIndex;
	var nValue = value * 1;
	var leaf = thisObj.editNode.attributes.leaf;
	leaf = leaf * 1;
	if(leaf && editColName == 'memo'){
		return;
	}
	if(!leaf|| !Ext.isNumber(nValue) || nValue < 0){
		ed.editNode.cols[editColName] = oldValue;
		ed.editNode.attributes[editColName] = oldValue;
		ed.editNode.ui.setInnerHTMLValue(editColName,oldValue);
		return ;
	}
	var reserveAmount = ed.editNode.cols['reserveAmount'];
	reserveAmount = !Ext.isNumber(reserveAmount) ? 0 : reserveAmount;
	if(editColName == 'orderAmount'){//如果当前修改的是 订单数量，自动计算出，计划数量
		var needAmount = nValue - reserveAmount;
		needAmount = needAmount < 0 ? 0 : needAmount;
		ed.editNode.cols['planAmount'] = needAmount;
		ed.editNode.attributes['planAmount'] = needAmount;
		ed.editNode.ui.setInnerHTMLValue('planAmount',needAmount);
	}
}

/**
 * 容纳产品树容器
 * @class centerPanel
 * @extends Ext.Panel
 */
Ext.ffc.ReservePlanEditCenterPanel = Ext.extend(Ext.Panel, {
	ffc_key : 'Ext.ffc.ReservePlanEditCenterPanel',
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
		Ext.apply(this, _cfg);
		this.reservePlanTree = Ext.ffc.getReservePlanEditTree();
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
		this.reservePlanEditTree.on('fccAfterUpdateNodeEvent',Ext.ffc.reservePlanAfterUpdateNode);
		Ext.ffc.ReservePlanEditCenterPanel.superclass.constructor.call(this, {
			region: 'center',
			layout: 'fit',
			split: true,
			height: 100,
			minSize: 100,
			maxSize: 200,
			collapsible: true,
			//title: 'South',
			margins: '-5 5 5 5',
			items : [this.reservePlanTree]
		})
	}
})


Ext.ffc.ReservePlanEditWindow = Ext.extend(Ext.Window, {  
	topPanel : null,
	centerPanel : null,
	ReservePlanInfor : null,
	readOnly : false,
	ReservePlanEditCenterGridStore : null,
	defultSelectPlanId:null,
	isAddWin:false,
	constructor : function(_cfg) {

		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.topPanel = new Ext.ffc.ReservePlanNorthPanel({readOnly:_cfg.readOnly});
		this.centerPanel = new Ext.ffc.ReservePlanEditCenterPanel({proDetail:this.ReservePlanInfor.reservePlanDetail});
		this.centerPanel.loadTreeNode();
		Ext.ffc.ReservePlanEditWindow.superclass.constructor.call(this, {
			modal : true,
			width : Ext.getBody().getWidth(),
			height : 600,
			title :  '储备计划编制',
			layout :  'border',
			maximizable :true,
			items : [this.topPanel,this.centerPanel],
			buttons: [{
						text : "确 定",
						hidden : this.readOnly,
						handler : function() {
								var form = this.topPanel.simpleForm;//obj.ownerCt.ownerCt;
								var currWin = form.ownerCt.ownerCt;
								var planDetail = currWin.ReservePlanInfor.reservePlanDetail;
								//得到所有计划数量不为0的叶子节点
								var leafNodes = [];
								for(var i = 0 ;i < planDetail.length;i++){
									leafNodes = leafNodes.concat(Ext.tree.getAllLeafNode(planDetail[i]));
								}
								if(leafNodes.length == 0) return;
								var formValues = form.getForm().getValues();
								formValues["reservePlanDetail"] = leafNodes;
								var serUrl = '';
								
								if(currWin.isAddWin){//add
									
									serUrl = PATH + "/reservePlan/ReservePlanEditAction.do?ffc=addReservePlan";
								}else{//update
									
									serUrl = PATH + "/reservePlan/ReservePlanEditAction.do?ffc=updateReservePlan";
								}
								Ext.Ajax.request({
										method: "post",
										params: { reservePlanInfor : Ext.encode(formValues)},
										url: serUrl,
										success: function(response){
											Ext.Msg.alert("消息", "保存成功!");	
											currWin.close();
										}
								});
						},scope : this
					  },{
						text : "取 消",
						handler : function() {
							this.close();
						},scope : this
					  }]
		});
	}
});  


