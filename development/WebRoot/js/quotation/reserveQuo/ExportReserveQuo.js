//导出预订报价单到普通报价单
Ext.namespace('Ext.ftl.exportquo');
Export = Ext.ftl.exportquo;
Export.SearchFormPanel = Ext.extend(Ext.FormPanel, {
	statusCombox : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.statusCombox = new StatusCombox({x:60,y:33, width:140,
            listeners : {
            	'specialkey' : function(field, e) {
            		if(e.getKey() == e.ENTER)
            			this.fireEvent('search',this, this.getValues());
            	},scope : this
        }});
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		var _config = [
			//1
            {xtype:'label',text: '报价单号:',x:-30,y:5,style:this.lableStyle_},
            {xtype:'textfield', name: 'quotationCode',x:60,y:3, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '我方负责人:',x:190,y:5,style:this.lableStyle_},
            {xtype:'textfield',name: 'userName',x:280,y:3, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '制单人:',x:385,y:5,style:this.lableStyle_},
            {xtype:'textfield',name: 'editorName',x:475,y:3, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            //2
            {xtype:'label',text: '客户:',x:570,y:5,style:this.lableStyle_},
            {xtype:'textfield', name: 'customerName',x:660,y:3, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '编制日期:',x:190,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'beginDate', readOnly : true, format:'Y-m-d',emptyText:'',x:280,y:33, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '至:',x:385,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'endDate', readOnly : true, format:'Y-m-d',emptyText:'',x:475,y:33, width:140,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            //3
            {xtype:'label',text: '状态:',x:-30,y:35,style:this.lableStyle_},
            this.statusCombox
		];
		
		Export.SearchFormPanel.superclass.constructor.call(this, {
			//width : 1000,
	        labelAlign:'right',buttonAlign:'right', border : false,
	        bbar : ['->',{
	           		text : "搜  索",
	           		iconCls : 'icon-search',
	           		handler : function() {
	           			//发布search事件
	           			this.fireEvent('search',this, this.getValues());
	           		},scope : this
           		},
           		'-',{
	           		text : "重  置",
	           		iconCls : 'icon-reset',
	           		handler : function () {
	           			this.getForm().reset();
	           		},scope : this
       		}],
	        frame:true,monitorValid:false,
	        layout: 'absolute',
	        height : 105,
	        items : _config
	        
	    })
		
		/**
		 * 添加search事件，在点击搜索时将事件发布出去
		 */
		this.addEvents('search');
	},
	
	/**
	 * 获取搜索条件
	 * @return {} 返回搜索条件:Record
	 */
	getValues : function() {
		var record = new Ext.data.Record(this.getForm().getValues());
		return record;
	}
})

/**
 * 报价单列表窗口
 * @class Export.QuoIndexPanel
 * @extends Ext.Window
 */
Export.QuoListWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	quoType : null,
	quoRecord : null,//需要导出的预订报价单
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		//alert(this.getModule(LoginInfor.modules, '003001'));
		
		this.northPanel = new Export.SearchFormPanel();
		this.centerPanel = new Ext.ftl.generalQuo.QuoGrid({quoType : -1});
		Export.QuoListWindow.superclass.constructor.call(this, {
			width : 842,
        	height : 550,
        	closeAction:'hide',
        	constrainHeader : true,
            layout: 'border',
            buttons : [{
				text : '确  定',
				handler : function() {
					fn : this.onSubmitClick();
				},scope : this
			},{
				text : '取  消',
				handler : function() {
					this.hide();
				},scope : this
			}],
            listeners : {
            	'render' : function() {
            		//监听搜索事件。
					this.northPanel.on({
						'search' : function(_form, _values) {
							var _grid = this.centerPanel;
							_grid.setSearchStr(_values);
							_grid.getStore().load({params : {start : 0, limit : PAGESIZE}});
						},
						scope : this
					})
					var _store = this.centerPanel.getStore();
					_store.load({params : {start : 0, limit : PAGESIZE}});
            	}
            },
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
                title: '报价搜索',
                split: true,
                width: 1000,
                height : 130,
                layout : 'fit',
                minSize: 165,
                maxSize: 130,
                collapsible: true,
                margins: '3 3 3 3',
                items : [this.northPanel]
                
            }, {
                region: 'center',
                split: true,
                height: 100,
                minSize: 100,
                layout : 'fit',
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 3 3 3',
                items : [this.centerPanel]
            }]
		})
		
		this.addEvents('onsubmitsuccess');
	},
	
	onSubmitClick : function() {
		try {
			var quotationType = this.centerPanel.getSelected().data['quotationType'];
			var status = this.centerPanel.getSelected().data['status'];
			if(status != 0 && status != 3) {
				Ext.Msg.show({
					title:'错误提示',
					msg: "该状态报价单，不允许导出!",
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.ERROR
				});
				return;
			}
			if(quotationType == 1) {
				//return;
				//项目报价单
				var _reserveProWindow = new Export.ReserveProListWindow();//预订报价单产品窗口
				var editWindow = this.editProjectQuo();//项目报价单窗口
				var productTree = null;//项目报价单工序对应产品树
				var _tablePanel = editWindow.southPanel;
				var _quoRecord = this.quoRecord;
				var isFirstLoad = true;
				var _projectCode = null;//项目编号
				var _serialNumber = null;//序号
				_tablePanel.on('tabchange', function(_panel, _tab) {
					productTree = _tab.items.items[0];
					/*productTree.on('load', function() {
						alert(1)
						if(isFirstLoad) {
							_projectCode = productTree.getRootNode().lastChild.attributes.projectCode;
							_reserveProWindow.reserveProTree.getLoader().baseParams.quoId = _quoRecord.get('id');
							_reserveProWindow.show();
						}
						isFirstLoad = false;
					})*/
					
					productTree.store.on('load', function(_store, records) {
						if(isFirstLoad) {
							_projectCode = _store.getAt(_store.getCount()-1).get('projectCode')+1;
							_serialNumber = _store.getAt(_store.getCount()-1).get('serialNumber')+1
							_reserveProWindow.reserveProTree.getStore().baseParams.quoId = _quoRecord.get('id');
							_reserveProWindow.reserveProTree.getStore().load();
							_reserveProWindow.show();
							_reserveProWindow.reserveProTree.getTopToolbar().hide();
						}
						isFirstLoad = false;
					})
				})
				
				editWindow.on({
					'quoInfoSaveSuccess' : function() {
						this.fireEvent('onsubmitsuccess');
					},scope : this
				})
				
				/*_reserveProWindow.on('onclick', function(_newNode) {
					var _curRate = productTree.ownerCt.ownerCt.ownerCt.ownerCt.northPanel.currCombox.curRate;
					var _price = _newNode.price * (1/_curRate);
					_newNode.price = _price.toFixed(2);
					_newNode.projectCode = _projectCode;
					//productTree.appendChild(_newNode);
					var record = new Ext.data.Record();
					Ext.apply(record.data, _newNode);
					productTree.store.add(record)
					//productTree.fireEvent("addNewNodeSuccess", this, _newNode);
					//productTree.calculate4projectQuo(workOrderRecord);
					productTree.fireEvent('onaddnode', this, record);
					productTree.getTopToolbar().items.get(4).fireEvent('click');
				}, this)*/
				
				_reserveProWindow.on('onclick', function(selectedNodes) {
					
					//取预订报价单的币别
					Ext.Ajax.request({
						url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
						params: { currencyName: _quoRecord.data['currencyName'], currencyId : _quoRecord.data['currency']},
						success : function(response) {
							var record = Ext.decode(response.responseText);
							if(!Ext.isEmpty(record))
								//正式报价单汇率
								var _curRate = productTree.ownerCt.ownerCt.ownerCt.ownerCt.northPanel.currCombox.curRate;
								//预订报价单汇率
								var resRate  = record.rate;
								for(var i = 0; i < selectedNodes.length; i++) {
									var _newNode = selectedNodes[i].copy();
									var _id = _newNode.get('id')+'#';
									var _r = resRate / _curRate;
									
									var _price = _newNode.get('price') * _r;						
									var _netPrice = _newNode.data['netPrice'] * _r;//净价
									var _money = _newNode.data['money'] * _r;
									var _taxNetPrice = _newNode.data['taxNetPrice'] * _r;
									var _taxMoney = _newNode.data['taxMoney'] * _r;

									_newNode.set('price', _price.toFixed(2));
									_newNode.set('netPrice', _netPrice.toFixed(2));
									_newNode.set('money', _money.toFixed(2));
									_newNode.set('taxNetPrice', _taxNetPrice.toFixed(2));
									_newNode.set('taxMoney', _taxMoney.toFixed(2));
									_newNode.set('projectCode', _projectCode);
									_newNode.set('serialNumber', parseInt(_serialNumber + i));
									_newNode.set('id', _id)
									_newNode.set('singleSetAssemblyAmount', 1);
									_newNode.set('singleSetStockAmount', 1);
									_productGrid.store.add(_newNode);

									//productTree.calculate(_newNode, resRate/_curRate, null);
								}
								productTree.fireEvent('onaddnode', this);//重新计算金额
						},scope : this
					});
				/*	var _curRate = productTree.ownerCt.ownerCt.ownerCt.ownerCt.northPanel.currCombox.curRate;
					
					for(var i = 0; i < selectedNodes.length; i++) {
						var _newNode = selectedNodes[i].copy();
						var _id = _newNode.get('id')+'#';
						var _price = _newNode.get('price') * (1/_curRate);
						_newNode.set('price', _price.toFixed(2));
						_newNode.set('projectCode', _projectCode);
						_newNode.set('serialNumber', parseInt(_serialNumber+i));
						_newNode.set('singleSetAssemblyAmount', 1);
						_newNode.set('singleSetStockAmount', 1);
						_newNode.set('id', _id)
						productTree.store.add(_newNode);
					}
					productTree.fireEvent('onaddnode', this);//重新计算金额
*/					//_productGrid.getTopToolbar().items.get(4).fireEvent('click');
				}, this)
				
			} else {
				var _reserveProWindow = new Export.ReserveProListWindow();//预订报价单产品窗口
				var _projectCode = null;//项目编号
				var _serialNumber = null;//序号
				var _quoRecord = this.quoRecord;
				//普通报价单
				var modifyWindow = this.centerPanel.onTriggerModify(false);
				var _productGrid = modifyWindow._grid;//产品GRID
				var isFirstLoad = true
				
				modifyWindow.on({
					'onsubmitsuccess' : function() {
						this.fireEvent('onsubmitsuccess');
					},scope : this
				})
				
				_productGrid.store.on('load' , function(_store, records) {
					if(isFirstLoad) {
						_projectCode = _store.getAt(_store.getCount()-1).get('projectCode')+1;
						_serialNumber = _store.getAt(_store.getCount()-1).get('serialNumber')+1
						//_reserveProWindow.reserveProTree.getLoader().baseParams.quoId = _quoRecord.get('id');
						_reserveProWindow.reserveProTree.getStore().baseParams.quoId = _quoRecord.get('id');
						_reserveProWindow.reserveProTree.getStore().load();
						_reserveProWindow.show();
						_reserveProWindow.reserveProTree.getTopToolbar().hide();
						
					}
						//this.ajaxPost(_productGrid);
					isFirstLoad = false;
				},this)
				
				_reserveProWindow.on('onclick', function(selectedNodes) {
					
					//取预订报价单的币别
					Ext.Ajax.request({
						url: PATH + '/generalQuo/excelAction.do?method=getCurrencyByName',
						params: { currencyName: _quoRecord.data['currencyName'], currencyId : _quoRecord.data['currency']},
						success : function(response) {
							var record = Ext.decode(response.responseText);
							if(!Ext.isEmpty(record)){
								//正式报价单汇率
								var _curRate = modifyWindow.northPanel.quotationForm.currCombox.curRate;
								//预订报价单汇率
								var resRate  = record.rate;
								for(var i = 0; i < selectedNodes.length; i++) {
									var _newNode = selectedNodes[i].copy();
									var _id = _newNode.get('id')+'#';
									var _r = resRate / _curRate;

									var _price = _newNode.get('price') * _r;						
									var _netPrice = _newNode.data['netPrice'] * _r;//净价
									var _money = _newNode.data['money'] * _r;
									var _taxNetPrice = _newNode.data['taxNetPrice'] * _r;
									var _taxMoney = _newNode.data['taxMoney'] * _r;

									_newNode.set('price', _price.toFixed(2));
									_newNode.set('netPrice', _netPrice.toFixed(2));
									_newNode.set('money', _money.toFixed(2));
									_newNode.set('taxNetPrice', _taxNetPrice.toFixed(2));
									_newNode.set('taxMoney', _taxMoney.toFixed(2));
									_newNode.set('projectCode', _projectCode);
									_newNode.set('serialNumber', parseInt(_serialNumber + i));
									_newNode.set('id', _id)
									_productGrid.store.add(_newNode);
									
									//_productGrid.calculate(_newNode, resRate/_curRate, null);
								}
								_productGrid.fireEvent('onaddnode', this);//重新计算金额
							}
						},scope : this
					});
					
					/*var _curRate = modifyWindow.northPanel.quotationForm.currCombox.curRate;
					
					for(var i = 0; i < selectedNodes.length; i++) {
						var _newNode = selectedNodes[i].copy();
						var _id = _newNode.get('id')+'#';
						var _price = _newNode.get('price') * (1/_curRate);
						_newNode.set('price', _price.toFixed(2));
						_newNode.set('projectCode', _projectCode);
						_newNode.set('serialNumber', parseInt(_serialNumber+i));
						_newNode.set('id', _id)
						_productGrid.store.add(_newNode);
					}
					_productGrid.fireEvent('onaddnode', this);//重新计算金额
*/					//_productGrid.getTopToolbar().items.get(4).fireEvent('click');
				}, this)
			}
		} catch(e) {
			Ext.Msg.show({
				title : '信息提示',
				msg : e.message,
				buttons : Ext.Msg.OK,
				width : 200,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	ajaxPost : function(productTree) {
		var _store = productTree.store;
		var lastRecord = _store.getAt(_store.getCount()-1);
		var projectCode = lastRecord.data['projectCode']+1;
		var serialNumber = lastRecord.data['serialNumber'];
		//获取需要导出报价单的产品信息，
		Ext.Ajax.request({
			url: PATH + '/generalQuo/getQuoDetailAction.do',
			params: {quoId : this.quoRecord.data['id']},
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
				for(var i = 0; i < responseArray.length; i++) {
					var curNode = responseArray[i];
					curNode.id=curNode.id + '#';
					curNode.projectCode=projectCode;
					curNode.serialNumber=serialNumber+1+i;
					//productTree.appendChild(curNode);
					var _record = new Ext.data.Record();
					Ext.apply(_record.data, curNode);
					_store.add(_record);
					if(i == responseArray.length-1){
						productTree.fireEvent('onaddnode', this, _record);
					}
				}
				
			},scope : this
		});
	},
	
	//项目报价单
	editProjectQuo : function() {
		// 报价单主信息记录
		var record = null;
		var customerId = null;
		var _copyRecord = null;
		var _customerCode = null;
		try {
			record = this.centerPanel.getSelected();
			// 根据用户编号获取用户ID
			_copyRecord = record.copy();
			var _cusCode = record.get("customerCode");
			var _customerCode = record.get("customerCode") + "-"
					+ record.get("customerName");
			_copyRecord.set("customerCode", _customerCode);
			Ext.Ajax.request({
				url : PATH + '/generalQuo/getCusByCuscodeAction.do',
				params : {
					customerCode : _cusCode
				},
				success : function(response) {
					var responseArray = Ext.util.JSON
							.decode(response.responseText);

					if (responseArray.success == true) {
						//customerId = responseArray.customerId;
						Ext.apply(_copyRecord.data, {customerId : responseArray.customerId});
					} else {

					}
				}
			});
		} catch (e) {
			Ext.Msg.show({
				title : '信息提示',
				msg : e.description,
				buttons : Ext.Msg.OK,
				width : 200,
				icon : Ext.MessageBox.INFO
			});
		}

		var editWindow = new ProQuomanager.EditWindow({
			customerRecord : _copyRecord,
			modal : false,
			quoId : record.get('id')
		});
		//editWindow.isExport=true;
		
		editWindow.show();

		// 设置报价单基本信息
		editWindow.northPanel.setValues(_copyRecord);
		
		return editWindow;
	}
})

Export.ReserveProTree = Ext.extend(Ext.tree.ColumnTree, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Export.ReserveProTree.superclass.constructor.call(this, {
			height: 325,
			bodyStyle:'width:100%',
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        title: '预订报价产品信息',
	        columns:[
	        	{header : '图标',width : 80,disEnableEdit : true},
	        	{header : '项目编号',width : 70,dataIndex : 'projectCode',disEnableEdit : true}, 
	        	{header : '序号',width : 40,dataIndex : 'serialNumber',disEnableEdit : true},
	        	{header:'牌号',width:180,dataIndex:'brandCode',disEnableEdit:true},
	        	{header:'名称',width:100,dataIndex:'productName',disEnableEdit:true},
	        	{header:'数量',width:50,dataIndex:'amount'},
	        	{header:'计量单位',width:80,dataIndex:'productUnit',disEnableEdit:true},
	        	{header:'单价',width:80,name : 'price',dataIndex: 'price',disEnableEdit:true},
	        	{header:'折扣(%)',width:60,dataIndex:'rebate',disEnableEdit : true},
	        	{header:'净价',width:80,dataIndex:'netPrice',disEnableEdit : true},
	        	{header:'金额',width:80,dataIndex:'money',disEnableEdit:true},
	        	{header:'含税净价',width:80,dataIndex:'taxNetPrice',disEnableEdit:true},
	        	{header:'含税金额',width:80,dataIndex:'taxMoney',disEnableEdit:true},
	        	{header:'交货期限',width:80,dataIndex:'deliveryDate',disEnableEdit:true},
	        	{header:'价格变动',width:0,dataIndex:'priceChange', hidden : true},
	        	{header:'品牌', width:80,dataIndex:'productBrand',disEnableEdit:true},
	        	{header:'备注1', width:80,dataIndex:'memo',disEnableEdit : true},
	        	{header:'备注2',width:80,dataIndex:'workshop',disEnableEdit : true},
	        	{header:'备注3',width:80,dataIndex:'reportCode',disEnableEdit : true},
	        	{header:'id',width:80,dataIndex:'id',disEnableEdit : true,hidden : false},
	        	{header:'预订报价单ID',width:80,dataIndex:'quotationInforId',disEnableEdit : true,hidden : false}
	        ],
	
	        loader: new Ext.tree.TreeLoader({
	            dataUrl: PATH + '/generalQuo/getQuoDetailAction.do',
	            autoLoad : false,
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        }),
	        
	        listeners : {
	        	'render' : function() {
	        		var loa = this.getLoader();
					
					loa.on('load', function() {
						var _rootNode = this.getRootNode();
						_rootNode.eachChild(function(curNode) {
							if(curNode.attributes.priceChange == 1) {
								//curNode.ui.addRowNodeClass('red-row');
								curNode.cols['priceChange'] = 1;
								curNode.attributes['priceChange'] = 1;
								curNode.ui.setInnerHTMLValue('priceChange',1);
								
								curNode.ui.setColumnsClass('rebate','blue-column-font');
								curNode.ui.setColumnsClass('netPrice','blue-column-font');
								curNode.ui.setColumnsClass('money','blue-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','blue-column-font');
								curNode.ui.setColumnsClass('taxMoney','blue-column-font');
							} else if(curNode.attributes.priceChange == 2) {
								curNode.ui.setColumnsClass('netPrice','red-column-font');
								curNode.ui.setColumnsClass('money','red-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','red-column-font');
								curNode.ui.setColumnsClass('taxMoney','red-column-font');
							} else if(curNode.attributes.priceChange == 3) {
								curNode.ui.setColumnsClass('rebate','blue-column-font');
								curNode.ui.setColumnsClass('netPrice','red-column-font');
								curNode.ui.setColumnsClass('money','red-column-font');
								curNode.ui.setColumnsClass('taxNetPrice','red-column-font');
								curNode.ui.setColumnsClass('taxMoney','red-column-font');
							} else {
								curNode.cols['priceChange'] = 0;
								curNode.attributes['priceChange'] = 0;
								curNode.ui.setInnerHTMLValue('priceChange',0);
							}
						})
					},this)
	        	}
	        }
	        
		})
	},
	
	getSelected : function() {
		return this.getSelectionModel().getSelectedNode();
	}
})

/**
 * 预订报价单产品列表窗口
 * @class Export.ReserveProListWindow
 * @extends Ext.Window
 */
Export.ReserveProListWindow = Ext.extend(Ext.Window, {
	reserveProTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.reserveProTree = new QuotationManager.productsGrid({loaderUrl : PATH + '/generalQuo/getQuoDetailAction.do'});
		Export.ReserveProListWindow.superclass.constructor.call(this, {
			width : 800,
        	height : 500,
        	closeAction:'hide',
        	constrainHeader : true,
        	layout : 'fit',
        	buttons : [{
				text : '确  定',
				handler : function() {
					fn : this.onSubmitClick();
				},scope : this
			},{
				text : '取  消',
				handler : function() {
					this.hide();
				},scope : this
			}],
			items : [this.reserveProTree]
		})
	},
	
	onSubmitClick : function() {
		var projectNumber = null; 
		var selectedNodes = null;
		var proTree = this.reserveProTree;
		
		try {
			selectedNodes = proTree.getSelections();
		} catch(_e) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择一条记录进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
		}
		this.fireEvent('onclick',selectedNodes);
		//this.addNodeToTree(selectedNode,1);
	},
	
	addNodeToTree : function(selectedNode,projectNumber, isHistory) {
		_price = selectedNode.attributes.price;
		var newNode = Ext.tree.toNewTreeNode(selectedNode.attributes);
		Ext.apply(newNode, {'serialNumber' : 10000, 'id' : selectedNode.attributes.id+'#'})
		//newNode.amount=0;
		newNode.singleSetAssemblyAmount = 1;
		newNode.singleSetStockAmount = 1;
		this.fireEvent('onclick',newNode);
	}
})


























