
/**
 * 定义命名空间
 */
Ext.namespace('Ext.zhj.qutation.projectQuo.product');
Ext.namespace('Ext.zhj.qutation.projectQuo.projectNumber');


/**
 * 命名空间别名 
 */
ProQuoproduct = Ext.zhj.qutation.projectQuo.product;
ProjectNumber = Ext.zhj.qutation.projectQuo.projectNumber;


//---------------------项目报价单项目号----------------
ProjectNumber.ProjectNumberCombo = Ext.extend(Ext.form.ComboBox , {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);

		this.store = new Ext.data.SimpleStore({
						fields : ['projectCode'],
						data : [[1],[2],[3],[4],[5]]
					});
		ProjectNumber.ProjectNumberCombo.superclass.constructor.call(this, {
					fieldLabel : '项目编号',
					hiddenName : 'projectCode',
					allowBlank : false,
					blankText : '项目编号不允许为空！',
					mode : 'local',
					displayField : 'projectCode',
					anchor:'90%',
					readOnly : false,
					frame : true,
					triggerAction : 'all',
					value : 1,
					store : this.store
		})
	},
	
	validator : function() {
		if(Ext.isNumber(this.getValue()*1)) {
			return true;
		} else {
			return false;
		}
	}
})

ProjectNumber.ProjectNumberForm = Ext.extend(Ext.FormPanel, {
	projectNumber : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		this.projectNumber = new ProjectNumber.ProjectNumberCombo();
		Ext.apply(this, _cfg);
		ProjectNumber.ProjectNumberForm.superclass.constructor.call(this, {
			width:280,  
			height:110,
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "280"},
			labelWidth : 55,
			items : this.projectNumber
		})
	},
	
	/**
	 * 获取表单值
	 * @return {} Record
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		return this.projectNumber.validator();
	}
})

ProjectNumber.ProjectNumerWindow = Ext.extend(Ext.Window, {
	projectNumberForm : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.projectNumberForm = new ProjectNumber.ProjectNumberForm();
		ProjectNumber.ProjectNumerWindow.superclass.constructor.call(this, {
			title: '项目编号',  
			width:300,  
			height:110,  
			plain:true,
			closable : true,
			resizable : false,
			constrainHeader : true,
			modal : true,
			closeAction:'hide',
			layout:"form",
			buttons : [{
				text : "确定",
				handler : function() {
					fn : this.onSubmitClick();
				},scope : this
			},{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			items : this.projectNumberForm
		})
		
		this.addEvents('onsubmitclick');
	},
	
	onSubmitClick : function() {
		if(!this.projectNumberForm.validator()) {
			return;
		}
		var projectNumber = this.projectNumberForm.getValues().get('projectCode')
		this.fireEvent('onsubmitclick', this, projectNumber);
		this.hide();
	}
})




//---------------------产品添加-----------------------
/**
 * 搜索Record
 */   
ProQuoproduct.SearchRecord = Ext.data.Record.create([
		    {name: 'productCode'},
		    {name: 'brandCode'},
		    {name: 'productName'}
		]);
/**
 * 产品树搜索栏
 * @class Ext.ftl.protools.SearchBar
 * @extends Ext.Toolbar
 */
ProQuoproduct.SearchBar = Ext.extend(Ext.Toolbar, {
	targetTree : null,
	paramRecord : null,
	yearCombo : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.paramRecord = new ProQuoproduct.SearchRecord();
		this.yearCombo = new Ext.ftl.HistoryPriceYearCombox();
		this.proBrand = new Ext.zhj.ProductBrankCombox();
		this.yearCombo.on({
			'select' : function(_combo) {
				this.paramRecord.salePriceDate = _combo.getValue();
			},scope : this
		});
		this.proBrand.on({
			'specialkey' : function(field, e) {
        		if(e.getKey() == e.ENTER) {
					for(var i = 0 ,len = this.items.length;i < len ; i++){
						if(this.items.get(i).text == '搜索'){
							field.fireEvent('change', field);
							this.items.get(i).fireEvent('click');
							break;
						}
					}
	            }
        	},
			'select' : function(_combo) {
				this.paramRecord.productBrand = _combo.getValue();
				this.yearCombo.reset();
				this.yearCombo.store.reload({params:{productBrand:_combo.getValue()}});
			},scope : this
		});
		ProQuoproduct.SearchBar.superclass.constructor.call(this, {
			renderTo:this.targetTree.tbar,
            items:[new Ext.form.Label({
            	html : "牌号:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'brandCode',
		      	id : 'brandCode',
		      	listeners : {
					'specialkey' : function(field, e) {
						if(e.getKey() == e.ENTER) {
							for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
									this.items.get(i).fireEvent('click');
									break;
								}
							}
						}
					},
		      		'change' : function(_field) {
		      			this.paramRecord.brandCode = _field.getValue().trim();
		      		},scope : this
		      	}
            
            }),'-',new Ext.form.Label({
            	html : "货品编号:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'productCode',
		      	id : 'productCode',
		      	listeners : {
					'specialkey' : function(field, e) {
						if(e.getKey() == e.ENTER) {
							for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
									this.items.get(i).fireEvent('click');
									break;
								}
							}
						}
					},
		      		'change' : function(_field) {
		      			this.paramRecord.productCode = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-', new Ext.form.Label({
            	html : "名称:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'productName',
		      	id : 'productName',
		      	listeners : {
					'specialkey' : function(field, e) {
						if(e.getKey() == e.ENTER) {
							for(var i = 0 ,len = this.items.length;i < len ; i++){
								if(this.items.get(i).text == '搜索'){
									field.fireEvent('change', field);
									this.items.get(i).fireEvent('click');
									break;
								}
							}
						}
					},
		      		'change' : function(_field) {
		      			this.paramRecord.productName = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-',new Ext.form.Label({
            	html : "品牌:&nbsp;"
            
            }),this.proBrand ,
			'-',
			new Ext.form.Label({
            	html : "价格执行期:&nbsp;"
            
            }), this.yearCombo,'-',{  
					text:'搜索'  
					,iconCls:'icon-search',
					listeners : {
						'click' : function(){
							var _searchStr = Ext.util.JSON.encode(this.paramRecord);
							this.fireEvent('onsearch',this, _searchStr);
						},scope:this
					}
					
				},'-',{  
					text:'重置'  
					,iconCls:'icon-reset',
					listeners : {
						'click' : function(){
							this.reset();
						},scope:this
					}
					
				}  
            ]
		})
		this.addEvents('onsearch');
	},
	
	reset : function() {
		this.yearCombo.reset();
		this.items.itemAt(1).reset();
		this.items.itemAt(4).reset();
		this.items.itemAt(7).reset();
		this.paramRecord = new ProQuoproduct.SearchRecord();
	}
})

ProQuoproduct.ProductTree = Ext.extend(Ext.tree.ColumnTree, {
	store : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.Store({
	       proxy: new Ext.data.HttpProxy({url:PATH + '/proTools/listAction.do'}),//调用的动作 
		   reader: new Ext.data.JsonReader(
				{
				   root: 'items',  //从struts2里面传递过来的参数 
				   totalProperty :'totalCount'
				}, 
				 [ //JSON数据的映射
					{name: 'id',mapping:'id',type:'string'}
				 ]
		  )
		});
		ProQuoproduct.ProductTree.superclass.constructor.call(this, {
			width:document.body.clientWidth,
			height: window.screen.availHeight-300,
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			tbar : [],
			listeners : {
				'render' : function() {
					var tl = this.store;
					var _searchStr = null;
					tl.on('beforeload', function(tl, node) {
						this.baseParams.searchStr = _searchStr;
					})
					var searchBar = new ProQuoproduct.SearchBar({
						targetTree : this
					})
					searchBar.on('onsearch', function(_searchBar, _str) {
						_searchStr = _str;
						tl.reload();
					})
					
					this.store.load({params:{start:0,limit:PAGESIZE}});
				},
				'load' : function(node) {
					this.fireEvent('click', node);
				},
				
				'click' : function(node, e) {
					if(node.isLeaf())
						return;
					if(!node.isExpanded()) { 
						node.expand();
					} else {
						node.collapse();
					}
					if(node.parentNode.id != 'root'  || node.hasChildNodes()) 
						return;
					Ext.Ajax.request({
						url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
						params: { nodeId: node.id },
						success : function(response) {
							var responseArray = Ext.util.JSON.decode(response.responseText); 
							var nodes = responseArray.items;
							if(nodes.length == 0) return;
							var arr = [];
							for(var i = 0 ;i < nodes.length;i++){
								arr.push(nodes[i]);					
							}
							if(node.hasChildNodes()) 
								return;
							for(var i = 0; i < arr.length;i++){
								node.appendChild(arr[i]); 
							}
							
						},scope : this
					});
				}
			},
			
			enableDD:false,
	        title: '产品信息',
	        columns:[
	        	{header:'牌号',width:300,dataIndex:'brandCode'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
		        {header:'名称',width:100,dataIndex:'productName'},
		        {header:'计量单位',width:100,dataIndex:'productUnit'},
		        {header:'组别',width:100,dataIndex:'productSortCode'},
		        {header:'单价',width:50,dataIndex:'salePrice'},
		        {header:'折扣',width:100,dataIndex:'rebate'},
		        {header:'品牌',width:100,dataIndex:'productBrand'},
		        {header:'来源',width:100,dataIndex:'productSource'},
		        {header:'备注',width:180,dataIndex:'memo'},
		        {header:'附件',width:0,hidden : true, dataIndex:'slaveFile'},
		        {header:'父ID',width:0,hidden : true,dataIndex:'parentId'},
		        {header:'toolsId',width:0,hidden : true,dataIndex:'toolsId'}
	        ],
	        
	        bbar : new Ext.ffc.TreePagingToolbar({
	            pageSize: PAGESIZE,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	            emptyMsg: "没有记录"
	        }),
	        
	        loader: new Ext.tree.TreeLoader({
	            //dataUrl:PATH + '/proTools/listAction.do?pageObjectName=pageObject&pageCount=' + ProQuoproduct.PageObj.pageCount,
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeLoader({
	        	id:"root",
	            text:'Tasks'
	        })
		})
	},
	
	getSelected : function() {
		return this.getSelectionModel().getSelectedNode();
	}
})

ProQuoproduct.HistoryProductTree = Ext.extend(Ext.tree.ColumnTree, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		ProQuoproduct.HistoryProductTree.superclass.constructor.call(this, {
			height: 400,
			bodyStyle:'width:100%',
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        title: '历史报价信息',
	        columns:[
	        	{header:'牌号', sortable : true,width:300,dataIndex:'brandCode'},
	        	{header:'货品编号',sortable : true,width:100,dataIndex:'productCode'},
	        	{header:'状态',sortable : true,width:100,dataIndex:'status'},
	        	{header:'历史面价',sortable : true,width:100,dataIndex:'historyPrice'},
	        	{header:'折扣',sortable : true,width:100,dataIndex:'rebate'},
	        	{header:'净价',sortable : true,width:100,dataIndex:'netPrice'},
	        	{header:'编制时间',sortable : true,width:100,dataIndex:'editDateString'},
	        	{header:'编制人',sortable : true,width:100,dataIndex:'userName'},
	        	{header : '品牌', sortable : true,width : 0, dataIndex : 'productBrand', hidden : true},
		        {header : '名称', sortable : true,width : 0, dataIndex : 'productName', hidden : true},
		        {header : '计量单位',sortable : true, width : 0, dataIndex : 'productUnit', hidden : true},
		        {header:'toolsId',width:0,hidden : true,dataIndex:'productToolInforId'}
		    ],
	
	        loader: new Ext.tree.TreeLoader({
	            dataUrl: PATH + '/generalQuo/getHistoryPriAction.do',
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		})
	},
	getSelected : function() {
		return this.getSelectionModel().getSelectedNode();
	}
})

ProQuoproduct.NorthPanel = Ext.extend(Ext.Panel, {
	productTree : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.productTree = new ProQuoproduct.ProductTree({
			customerRecord : this.customerRecord
		});
		ProQuoproduct.NorthPanel.superclass.constructor.call(this, {
			region: 'north',
            iconCls:'icon-grid',
            //title: '工具信息',
            //contentEl: 'south',
            split: true,
            width: 200,
            height : 400,
            minSize: 175,
            layout : 'fit',
            maxSize: 400,
            collapsible: true,
            margins: '5 5 5 5',
            items : [this.productTree]
		})
	}
})

ProQuoproduct.CenterPanel = Ext.extend(Ext.Panel, {
	historyTree : null,
	
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.historyTree = new ProQuoproduct.HistoryProductTree();
		ProQuoproduct.CenterPanel.superclass.constructor.call(this, {
			region: 'center',
            split: true,
            height: 100,
            minSize: 100,
            layout : 'fit',
            maxSize: 200,
            collapsible: false,
            margins: '-5 5 5 5',
            items : [this.historyTree]
		})
	}
})

ProQuoproduct.ProductListWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	customerRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new ProQuoproduct.NorthPanel({
			customerRecord : this.customerRecord
		});
		this.centerPanel = new ProQuoproduct.CenterPanel();
		ProQuoproduct.ProductListWindow.superclass.constructor.call(this, {
			title:"添加产品",  
			width:1110,  
			height:600,  
			plain:true,
			closable : true,
			modal : true,
			constrainHeader : true,
			closeAction:'hide',
			layout:"border",  
			buttons : [{
				text : "确定",
				handler : function() {
					fn : this.onSubmitClick();
				},
				scope : this
			},{
				text : "取消",
				handler : function() {
					this.hide();
				},
				scope : this
			}],
			listeners : {
				'render' : function() {
					var proTree = this.northPanel.productTree;
					var historyTree = this.centerPanel.historyTree;
					
					proTree.on('dblclick', function() {
						this.buttons[0].fireEvent('click');
					},this)
					
					historyTree.on('dblclick', function() {
						this.buttons[0].fireEvent('click');
					},this)
				}
			},
			items : [this.northPanel, this.centerPanel]
		})
		this.addEvents('onclick')
	},
	
	onSubmitClick : function() {
		var projectNumber = null; 
		var selectedNode = null;
		var proTree = this.northPanel.productTree;
		var historTree = this.centerPanel.historyTree;
		var isHistory = false;
		
		if(proTree.getSelected()) {
			selectedNode = proTree.getSelected();
		} else if(historTree.getSelected()) {
			selectedNode = historTree.getSelected();
			isHistory = true;
		}
		
		if(!selectedNode) {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择一条记录进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(selectedNode.parentNode.id != 'root') {
			Ext.Msg.show({
				title:'信息提示',
				msg: '请选择根结点进行操作！',
				buttons: Ext.Msg.OK,
				icon: Ext.MessageBox.INFO
			});
			return;
		}
		
		if(!selectedNode.isLeaf() && !isHistory) {
			Ext.Ajax.request({
				url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
				params: { nodeId: selectedNode.id },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
					var nodes = responseArray.items;
					Ext.apply(selectedNode.attributes, {'children' : nodes})
					this.addNodeToTree(selectedNode,1);
				},scope : this
			});
		} else {
			this.addNodeToTree(selectedNode,1, isHistory);
		}
		
		//选择项目编号窗口
		/*var projectNumberWindow = new ProjectNumber.ProjectNumerWindow();
		projectNumberWindow.on('onsubmitclick', function(_win, _projectNumber) {
			projectNumber = _projectNumber;
			
			if(proTree.getSelected()) {
				selectedNode = proTree.getSelected();
			} else if(historTree.getSelected()) {
				selectedNode = historTree.getSelected();
				isHistory = true;
			}
			
			if(!selectedNode) {
				Ext.Msg.show({
					title:'信息提示',
					msg: '请选择一条记录进行操作！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return;
			}
			
			if(selectedNode.parentNode.id != 'root') {
				Ext.Msg.show({
					title:'信息提示',
					msg: '请选择根结点进行操作！',
					buttons: Ext.Msg.OK,
					icon: Ext.MessageBox.INFO
				});
				return;
			}
			
			if(!selectedNode.isLeaf() && !isHistory) {
				Ext.Ajax.request({
					url: PATH + '/proTools/listAction.do?method=getTools4RootNode',
					params: { nodeId: selectedNode.id },
					success : function(response) {
						var responseArray = Ext.util.JSON.decode(response.responseText); 
						var nodes = responseArray.items;
						Ext.apply(selectedNode.attributes, {'children' : nodes})
						this.addNodeToTree(selectedNode,projectNumber);
					},scope : this
				});
			} else {
				this.addNodeToTree(selectedNode,projectNumber, isHistory);
			}
		},this)
		
		
		projectNumberWindow.show();*/
	},
	addNodeToTree : function(selectedNode,projectNumber, isHistory) {
		
		var newNode = Ext.tree.toNewTreeNode(selectedNode.attributes,{'uiProvider':'col','priceChange':''},false,['salePriceDate','stockPriceDate','editDate','productToolInforId']);
		if(isHistory) {
			_price = selectedNode.attributes.historyPrice;
			Ext.apply(newNode, {'priceChange' : 4, 'toolsId' : selectedNode.attributes.productToolInforId})
		} else {
			_price = selectedNode.attributes.salePrice;
		}
		Ext.apply(newNode, {'projectCode' : projectNumber, 'price' : _price, 'serialNumber' : 10000})
		newNode.amount=0;
		newNode.singleSetAssemblyAmount = 1;
		newNode.singleSetStockAmount = 1;
		this.fireEvent('onclick',newNode);
		//Ext.ffc.util.debug(newNode);
	}
})