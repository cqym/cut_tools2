/**
 * 命名空间
 */
Ext.namespace('Ext.ftl.protools');

/**
 * 搜索Record
 */   
Ext.ftl.protools.SearchRecord = Ext.data.Record.create([
    {name: 'productCode'},
    {name: 'brandCode'},
    {name: 'productName'},
    {name: 'productBrand'}
]);
/**
 * 产品树搜索栏
 * @class Ext.ftl.protools.SearchBar
 * @extends Ext.Toolbar
 */
Ext.ftl.protools.SearchBar = Ext.extend(Ext.Toolbar, {
	targetTree : null,
	paramRecord : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.paramRecord = new Ext.ftl.protools.SearchRecord();
		Ext.ftl.protools.SearchBar.superclass.constructor.call(this, {
			renderTo:this.targetTree.tbar,
            items:[new Ext.form.Label({
            	html : "牌号:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'brandCode',
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
	            			field.fireEvent('change', field);
	            			this.items.get(12).fireEvent('click');
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
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
	            			field.fireEvent('change', field);
	            			this.items.get(12).fireEvent('click');
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
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
	            			field.fireEvent('change', field);
	            			this.items.get(12).fireEvent('click');
	            		}
	            	},
		      		'change' : function(_field) {
		      			this.paramRecord.productName = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-', new Ext.form.Label({
            	html : "品牌:&nbsp;"
            
            }), new Ext.form.TextField({
            	name : 'productBrand',
		      	listeners : {
		      		'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER) {
	            			field.fireEvent('change', field);
	            			this.items.get(12).fireEvent('click');
	            		}
	            	},
		      		'change' : function(_field) {
		      			this.paramRecord.productBrand = _field.getValue();
		      		},scope : this
		      	}
            
            }),'-',{  
					text:'搜索'  
					,iconCls:'icon-search',
					listeners : {
						'click' : function(){
							var _searchStr = Ext.util.JSON.encode(this.paramRecord);
							this.fireEvent('onsearch',this, _searchStr);
						},scope:this
					}
					
				}, '-',{  
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
		this.items.itemAt(1).reset();
		this.items.itemAt(4).reset();
		this.items.itemAt(7).reset();
		this.items.itemAt(10).reset();
		this.paramRecord = new Ext.ftl.protools.SearchRecord();
	}
})
    
Ext.ftl.protools.MyRecord = Ext.data.Record.create([
	{name: 'id'},
    {name: 'brandCode'},
    {name : 'productName'},
    {name : 'productUnit'},
    {name : 'sortCode'},
    {name : 'productBrand'},
    {name : 'productSource'},
    {name : 'leaf'},
    {name : 'memo'}
]);

//-------------------------------级联父类----------------------------------
/**
 * 级联菜单父类。
 * @class ·
 * @extends Ext.form.ComboBox
 */
Ext.ftl.protools.AutoCompleteCombo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	flagName : null,
	cascadeCombo : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.ftl.protools.AutoCompleteCombo.superclass.constructor.call(this, {
			store: this.store,
	        //hiddenName : this.flagName,
	        //valueField:this.flagName,
			//width : 1000,
			//listWidth : 200,
	        allowBlank : false,
	        editable : true,
	        anchor : '95.5%',
	        hideTrigger : false,//隐藏触发按钮
	        displayField: this['displayField'] == null ? this.flagName : this['displayField'],
	        typeAhead: true,//当开始输入字符时，在指定的延迟之后会自动匹配剩下的内容，如果找到了匹配的内容则自动选中它
	        forceSelection: true,//限制选择的值必须是下拉列表中的值
	        triggerAction: 'all',
	        queryParam : this.flagName,//发送至后台的查寻参数值
	        selectOnFocus:true,//当获得焦点时立即选中输入栏中存在的所有文本
			listeners : {
				'select' : function(combo, record,index) {
					if(this.cascadeCombo != null) {
						this.cascadeCombo.clearValue(); //可以实现当来源下拉值变更时，清空之前品牌下拉选项中的值 
						this.cascadeCombo.store.baseParams.name = this.getValue();
						this.cascadeCombo.store.load();
					}	
				}
			}
		})
	},
	
	/**
	 * 根据订单Id获取订单Record
	 * @param {} _id 订单Id
	 * @return {} 订单 Record
	 */
	getRecordById : function(_id) {
		if(Ext.isEmpty(_id)) {
			throw Error('');
		} else {
			return this.getStore().getById(_id);
		}
	}
})
//-------------------------------组别----------------------------------
Ext.ftl.protools.ProSortStore = Ext.extend(Ext.data.JsonStore, {
	ownerCt : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		
		Ext.ftl.protools.ProSortStore.superclass.constructor.call(this, {
			url : Ext.ftl.protools.ProSortStore.STORE_URL,
			fields : ['id', 'sortCode', 'sortName'],
			root : 'proSort',
			listeners : {
				'load' : function(_store , _records) {
					for(var i = 0; i < _records.length; i++) {
						_records[i].set('sortName', _records[i].get('sortCode') + '-' + _records[i].get('sortName'))
					}
					/*if(_records.length > 0)
						this.ownerCt.select(_records[0].get('sortName'))*/
						
				},scope : this
			}
		})
	}
})

Ext.ftl.protools.ProSortCombo = Ext.extend(Ext.ftl.protools.AutoCompleteCombo, {
	getRecordByCode:function(code){
		var record = null;
	    if(Ext.isEmpty(code)) {
			throw Error('');
		} else {
			this.store.each(function(item){
				if(code == item.get("sortCode")){
					record = item;
				}
			});
		}
		return record;
	},
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.ftl.protools.ProSortCombo.superclass.constructor.call(this, {
			store : new Ext.ftl.protools.ProSortStore({ownerCt : this}),
			minChars : 2,
			hiddenName : 'sortCode',
			fieldLabel : '组别',
			valueField : 'sortCode',
			//mode: 'local',
			flagName : 'sortCode',
			displayField : 'sortName',
			emptyText:'请选择组别'
		})
	}
})
//-------------------------------品牌----------------------------------
Ext.ftl.protools.ProBrandStore = Ext.extend(Ext.data.JsonStore, {
	ownerCt : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		
		Ext.ftl.protools.ProBrandStore.superclass.constructor.call(this, {
			url : Ext.ftl.protools.ProBrandStore.STORE_URL,
			fields : ['id', 'name'],
			root : 'proBrand',
			listeners : {
				'load' : function(_store , _records) {
					if(_records.length > 0) {
						//this.ownerCt.setValue(_records[0].get('name'))
						//this.ownerCt.fireEvent('select', this.ownerCt);
					}
				},scope : this
			}
		})
	}
})

Ext.ftl.protools.ProBrandCombo = Ext.extend(Ext.ftl.protools.AutoCompleteCombo, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		Ext.ftl.protools.ProBrandCombo.superclass.constructor.call(this, {
			store : new Ext.ftl.protools.ProBrandStore({ownerCt : this}),
			minChars : 4,
			hiddenName : 'productBrand',
			fieldLabel : '品牌',
			valueField : 'name',
			//mode: 'remote',
			flagName : 'name',
			emptyText:'请选择品牌',
			typeAhead: true
		})
	}
})
//-------------------------------来源----------------------------------
Ext.ftl.protools.ProSourceStore = Ext.extend(Ext.data.JsonStore, {
	isNonStand : false,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		Ext.ftl.protools.ProSourceStore.superclass.constructor.call(this, {
			url : Ext.ftl.protools.ProSourceStore.STORE_URL,
			fields : ['id', 'name'],
			root : 'proSource',
			listeners : {
				'load' : function(_store) {
					if(this.isNonStand) {
						_store.each(function(_record) {
							if(_record.data['name'] != '自制'){
								_store.remove(_record);
							}
						})
					}else{
						_store.each(function(_record) {
							if(_record.data['name'] != '采购'){
								_store.remove(_record);
							}
						})
					}
				}
			}
		})
	}
})

Ext.ftl.protools.ProSourceCombo = Ext.extend(Ext.ftl.protools.AutoCompleteCombo, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.ftl.protools.ProSourceStore();
		Ext.ftl.protools.ProSourceCombo.superclass.constructor.call(this, {
			store : this.store,
			minChars : 2,
			fieldLabel : '来源',
			hiddenName : 'productSource',
			valueField : 'name',
			flagName : 'name',
			emptyText:'请选择产品来源'
		})
	}
})

/**
 * 增加产品Form
 * @class Ext.ftl.ProductForm
 * @extends Ext.FormPanel
 */
Ext.ftl.ProductForm = Ext.extend(Ext.FormPanel, {
	proSort : null,//组别
	proBrand : null,//品牌
	proSource : null,//来源
	proUnit : null, //单位
	toolsType:null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.proSort = new Ext.ftl.protools.ProSortCombo();
		this.proBrand = new Ext.ftl.protools.ProBrandCombo({
			cascadeCombo : this.proSort
		});
		this.proSource = new Ext.ftl.protools.ProSourceCombo({
			cascadeCombo : this.proBrand
		});
		
		this.proBrand.on(
			'beforequery' , function(queryEvent) {
				queryEvent.combo.store.baseParams.name = this.proSource.getValue();
				queryEvent.combo.store.baseParams.brandName = queryEvent.query;
				queryEvent.combo.store.load();
			},this
		)
		
		this.proSort.on(
			'beforequery' , function(queryEvent) {
				queryEvent.combo.store.baseParams.name = this.proBrand.getValue();
				queryEvent.combo.store.load();
			},this
		)
		
		this.proUnit = new Ext.ftl.UnitComboBox();

		this.toolsType = new Ext.ffc.ToolsTypeComboBox({
		    hidden : !this.isSelfMake,
			hideLabel : !this.isSelfMake
		});
		Ext.ftl.ProductForm.superclass.constructor.call(this, {
			width : 490,
			height : 250,
			defaultType : "textfield",
			style:"padding:5px",
			margins:'3 0 3 3',
	        cmargins:'3 3 3 3',
	        baseCls : 'x-plain',
			defaults : {width : "390"},
			labelWidth : 55,
			items : [{fieldLabel : "牌号", name : 'brandCode', allowBlank : false,emptyText:'请输入牌号'},
					{fieldLabel : "历史牌号", name : 'brandCodeHistory'},
					{fieldLabel : "名称", name : 'productName', allowBlank : false,emptyText:'请输入名称'}, 
					//{fieldLabel : "数量", name : 'amount'},
					this.proUnit, 
					this.proSource,
					this.proBrand, 
					this.proSort, 
					this.toolsType,
					{fieldLabel : "备注", name : 'memo'},
					{fieldLabel : "leaf", name : 'leaf', hidden : true, hideLabel : true},
					{fieldLabel : "id", name : 'id', hidden : true, hideLabel : true},
					{xtype:'hidden',fieldLabel: '是否同步单据', name: 'sycQuoToosInfor'}]
		})
	},
	
	/**
	 * 设置表单值
	 * @param {} _r
	 */
	setValues : function(_r) {
		this.getForm().loadRecord(_r);
	},
	
	/**
	 * 获取表单值
	 * @return {}
	 */
	getValues : function() {
		var _record = new Ext.data.Record(this.getForm().getValues());
		return _record;
	},
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		return this.getForm().isValid();
	}
});

/**
 * 产品工具信息Form容器窗口
 * @class Ext.ftl.ProductWindow
 * @extends Ext.Window
 */
Ext.ftl.ProductWindow = Ext.extend(Ext.Window, {
	selectedItem : null,
	productForm : null,
	title : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.productForm = new Ext.ftl.ProductForm({isSelfMake:_cfg.isSelfMake});
		Ext.ftl.ProductWindow.superclass.constructor.call(this, {
			title : this['title'] == null ? "增加自制产品" : this['title'],
			width : 500,
			height : 325,
			plain : true,
			modal : true,
			constrainHeader : true,
			items : this.productForm,
			buttons : [{
				text : "确定",
				handler : function() {
					fn : this.onAddSubmit();
				},scope : this
	
			}, {
				text : "取消",
				handler : function() {
					this.hide();
				},scope : this
			}]
		});
		
		//添加submit事件
		this.addEvents('submit');
	},
	
	/**
	 * 点击确定按钮时触发，将submit事件发布出去。
	 */
	onAddSubmit : function() {
		var _form = this.productForm;
		//校验未通过直接返回
		if(!_form.validator()) {
			return;
		}
		var formValue = _form.getForm().getValues();
		if(!formValue.toolsTypeId && formValue.toolsTypeId == ''){
		    formValue.toolsTypeName = '';
			formValue.toolsTypeId = '';
		}else{
		    formValue.toolsTypeName = this.productForm.toolsType.getNameById(formValue.toolsTypeId);
		}
		var _proSortRecord = this.productForm.proSort.getRecordByCode(formValue.sortCode);
		if(_proSortRecord){
			formValue.productSortId = _proSortRecord.get('id');
			formValue.productSortCode = _proSortRecord.get('sortCode');
		}
		
		this.hide();
		this.fireEvent('submit', this, formValue);
	}
})



/**
 * 产品工具信息树
 * @class Ext.ftl.protools.ProToolsTree
 * @extends Ext.tree.ColumnTree
 */
Ext.ftl.protools.ProToolsTree = Ext.extend(Ext.tree.ColumnTree, {
	store : null,
	searchBar : null,
	isAddNoneHide : true, isAddStandHide : true, isModifyHide : true,
	isDelHide : true, isSlaveHide : true, isImportHide : true,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.Store({
	       proxy: new Ext.data.HttpProxy({url:PATH + '/proTools/listAction.do?method=getTools4Product'}),//调用的动作 
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
		Ext.ftl.protools.ProToolsTree.superclass.constructor.call(this, {
			//el:'tree-ct',
	        width : Ext.getBody().getWidth()+100,
        	height : Ext.getBody().getHeight()-50,
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			listeners : {
				'render' : function() {
					var tl = this.store;
					var _searchStr = null;
					tl.on('beforeload', function() {
						this.baseParams.searchStr = _searchStr;
					})
					
					this.searchBar = new Ext.ftl.protools.SearchBar({
						targetTree : this
					})
					this.searchBar.on('onsearch', function(_searchBar, _str) {
						_searchStr = _str;
						tl.reload({params : {start : 0, limit : PAGESIZE}});
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
	        iconCls:'icon-grid',
	        columns:[{header : '图标',width : 100,disEnableEdit : true},
	        	{header:'牌号',width:200,dataIndex:'brandCode'},
	        	{header : "历史牌号",width : 200, dataIndex : 'brandCodeHistory'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
	        	{header:'名称',width:100,dataIndex:'productName', renderer : function(colValue, node, data) {
	        		
	        		return '<span id="tip" class="tip-target" ext:qtip="' + colValue + '">' + colValue +'</span>'
	        	}},
	        	{header:'数量',width:40,dataIndex:'amount'},
	        	{header:'计量单位',width:100,dataIndex:'productUnit'},
	        	{header:'组别',width:100,dataIndex:'productSortCode'},
	        	{header:'品牌',width:100,dataIndex:'productBrand'},
				{header:'产品类别',width:100,dataIndex:'toolsTypeId',hidden:true},
				{header:'产品类别',width:100,dataIndex:'toolsTypeName'},
	        	{header:'来源',width:100,dataIndex:'productSource'},
	        	{header:'创建日期',width:110,dataIndex:'createDateStr'},
	        	{header:'客户确认方案图',width:120,datadex:'slaveFile', renderer : function(colValue, node, data) {
	        		if(data.parentId != 'root')
	        			return;
	        		if(data.slaveFile*1 > 0) {
	        			var id = data.id
	        			var str = "<a href=\"#\" onclick=Ext.ftl.protools.onSlaveClick('" + id + "');><span style='color:blue;font-weight:bold;'>查看</span></a>";
						return str;
	        		}
	        	}},
	        	{header:'备注',width:180,dataIndex:'memo'}
	        ],
	        tbar : [{
	        	text:'增加自制产品',
	        	hidden : this.isAddNoneHide,
				iconCls:'icon-nonpro-add',
				handler : function() {
					this.onNonStandSubmit();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : this.isAddNoneHide
	        }, {
	        	text:'增加一般产品',
	        	hidden : this.isAddStandHide,
				iconCls:'icon-add',
				handler : function() {
					fn : this.onstandSubmit();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : this.isAddStandHide
	        }, {
	        	text:'修改产品',
	        	hidden : this.isModifyHide,
				iconCls:'icon-pro-modify',
				handler : function() {
					fn : this.onModifySubmit();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : this.isModifyHide
	        }, {
	        	text:'删除产品',
	        	hidden : this.isDelHide,
				iconCls:'icon-delete',
				handler : function() {
					fn : this.onDeleteSubmit();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : this.isDelHide
	        }, {
	        	text:'附件管理',
	        	hidden : this.isSlaveHide,
				iconCls:'icon-add',
				handler : function() {
					fn : this.onSlaveClick();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : this.isSlaveHide
	        }, {
	        	text:'产品导入',
	        	hidden : this.isImportHide,
				iconCls:'icon-import-data',
				handler : function() {
					fn : this.onImportClick();
				},scope : this
	        },{
		        xtype:'tbseparator',
		        hidden : false
	        },{
				text:'产品导出',
				hidden : false,
				iconCls:'icon-excel',
				listeners: {
					'click' : function(){	
						var _searchStr = Ext.util.JSON.encode(this.searchBar.paramRecord);
						window.open(PATH + '/proTools/importToolsAction.do?method=exportTools&searchStr=' + _searchStr);
					},scope : this
		 		}
			}],
	        
	        bbar : new Ext.ffc.TreePagingToolbar({
	            pageSize: PAGESIZE,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	            emptyMsg: "没有记录"
	        }),
	        
	        loader: new Ext.tree.TreeLoader({
	            //dataUrl:PATH + '/proTools/listAction.do?pageObjectName=pageObject&pageCount=' + PageObj.pageCount,
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		});
	},
	
	/**
	 * 获取选中的节点，如果未选，则抛出异常。
	 * @return {} 返回选中的节点 TreeNode
	 */
	getSelected : function() {
		var node = this.getSelectionModel().getSelectedNode();
		if(!node) {
			throw Error("请选择节点再进行操作！")
		} else {
			return node;
		}
	},
	
	/**
	 * 生成一新节点
	 * @param {} _r
	 * @return {} 返回新节点
	 */
	buildLeafNode : function(values) {
		var node = new Ext.tree.TreeNode({
			id : values.id,
			brandCode : values.brandCode,
			productName : values.productName,
			productUnit : values.productUnit,
			productSortCode : values.productSortCode,
			productBrand : values.productBrand,
			productSource : values.productSource,
			memo : values.memo,
			brandCodeHistory : values.brandCodeHistory,
			amount : values.amount,
			leaf : 1,
			toolsTypeId:values.toolsTypeId,
			toolsTypeName:values.toolsTypeName,
			allowChildren:false,//false :不允许该节点有孩子节点
			uiProvider : Ext.tree.ColumnNodeUI
		})
		
		return node;
	},
	
	/**
	 * 添加新节点
	 * @param {} node
	 */
	appendChild : function(node) {
		this.getRootNode().appendChild(node);
	},
	
	/**
	 * 添加非标产品 
	 */
	onNonStandSubmit : function() {
		//打开添加非标产品窗口
		var nonStandWindow = new Ext.ftl.protools.AddNonStandProWindow({
			hideModify : true,
			sycQuoToosInfor :false
		});
		
		nonStandWindow.centerPanel.proToolsTree.on({
				'ondelete' : function(_tree, _node) {
					this.getNodeById(_node.id).remove();
				},
				scope : this
			})
		nonStandWindow.centerPanel.proToolsTree.on({
				'onadd' : function(_tree, _node) {
					var _n = Ext.util.JSON.decode(_node.toJsonString())
					_n["uiProvider"] = Ext.tree.ColumnNodeUI;//必须要的
					this.appendChild(_n);
				},scope : this
			});
		nonStandWindow.on('onsubmit' , function() {
			this.store.reload();
		},this)
		
		nonStandWindow.on('oncancel' , function() {
			
		},this)
		
		nonStandWindow.show();
		
	},
	/**
	 * 添加一般产品
	 */
	onstandSubmit : function() {
		var standWindow = new Ext.ftl.ProductWindow({
			title : '增加一般产品',isSelfMake:false
		});
		/*var parId = null;
		try {
			var selNode = this.getSelected();
			if(selNode.isLeaf()) {
				parId = selNode.parentNode.id;
			} else {
				parId = selNode.id;
			}
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}*/
		
		standWindow.on('submit', function(_form, formValues) {
			Ext.Ajax.request({
				url: PATH + '/proTools/getProSortByCode.do?method=getTreeDto',
				params: { treeDto: Ext.encode(formValues) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
												   	
					if(responseArray.success == true){
						Ext.Msg.show({
							title:'信息提示',
							msg: "该产品已经存在！",
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
						
					} else {
						formValues.leaf = 1;//1 叶子节点
						Ext.Ajax.request({
							url: PATH + '/proTools/addAction.do',
							params: { content: Ext.encode(formValues) },
							success : function(response) {
								var responseArray = Ext.util.JSON.decode(response.responseText); 
								var _id = responseArray.id;
								formValues.id = _id;
								if(responseArray.success == true){
									Ext.Msg.show({
										title:'成功提示',
										msg: responseArray.msg,
										buttons: Ext.Msg.OK,
										icon: Ext.MessageBox.INFO
									});
									this.store.reload();
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
					}
				},scope : this
			});
			
		},this)
		standWindow.show();
	},
	/**
	 * 修改产品
	 */
	onModifySubmit : function() {
		var selNode = this.getSelected();
		var qx = this.canUpdate;
		var _this = this;
		Ext.Ajax.request({
					url: PATH + '/proTools/checkToolsBeEditQuoAction.do',
					params: { toolsId: selNode.id },
					success : function(response) {
						if((response.responseText == 'true' || response.responseText == true) && !qx){
							Ext.Msg.show({
								title : '系统提示',
								msg : '当前工具信息，已被报价单引用过，请联系管理员进行修改！',
								width : 260,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.INFO
							});
						    return ;
						}
						if((response.responseText == 'true' || response.responseText == true) && qx){
							Ext.MessageBox.confirm('系统提示', '当前工具信息，已被报价单引用过，是否同时修改单据？', function(btn){
								if(btn != 'yes'){
									_this.onModify({sycQuoToosInfor:false});
								}else{
								  _this.onModify({sycQuoToosInfor:true});
							}
							});
						}else{
						    _this.onModify();
						}
					},scope : this
				});
	},
	onModify : function(conf){
		if(!conf){
			conf = {sycQuoToosInfor:false};
		}
		var modifyWindow = new Ext.ftl.ProductWindow({
			title : '修改产品信息',isSelfMake:false
		});
		
		try {
			var selNode = this.getSelected();
			if(selNode.parentNode.id != 'root') {
					Ext.Msg.show({
						title : '信息提示',
						msg : '请选择根节点进行操作！',
						width : 260,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
					return;
			}
			if(selNode.attributes.leaf) {
				//构造一新的Record
				var _record = new Ext.ftl.protools.MyRecord({
					id : selNode.id,
					brandCode : selNode.attributes.brandCode,
				    productName : selNode.attributes.productName,
				    productUnit : selNode.attributes.productUnit,
				    sortCode : selNode.attributes.productSortCode,
				    productBrand : selNode.attributes.productBrand,
				    productSource : selNode.attributes.productSource,
				    leaf : selNode.attributes.leaf,
				    memo : selNode.attributes.memo,
				    sycQuoToosInfor :conf.sycQuoToosInfor,
					toolsTypeId:selNode.attributes.toolsTypeId
				});
				modifyWindow.show();
				modifyWindow.productForm.setValues(_record);//应在窗口show之后调用设值方法
				//modifyWindow.productForm.proBrand.store.load();
			} else {
				var nonStandModifyWindow = new Ext.ftl.protools.ModifyNonStandProWindow({
					hideAdd : true,
					sycQuoToosInfor :conf.sycQuoToosInfor
				});
				
				nonStandModifyWindow.on('onsubmit' , function() {
					this.store.reload();
				},this)
				
				nonStandModifyWindow.show();
				var _newTree = nonStandModifyWindow.northPanel.newTree;
				_newTree.isModify = true;
				var targetNode = _newTree.getRootNode();
				
				Ext.Ajax.request({
					url: PATH + '/proTools/listAction.do?method=getToolsById',
					params: { toolsId: selNode.id },
					success : function(response) {
						var node = Ext.util.JSON.decode(response.responseText); 
						targetNode.appendChild(node);
						targetNode.expandChildNodes(true);
					},scope : this
				});
				
			}
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择节点再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
		
		modifyWindow.on('submit', function(_form, values) {
								Ext.Ajax.request({
									url : PATH + '/proTools/updateProAction.do',
									params : {content : Ext.encode(values)},
									success : function(response) {
										var responseArray = Ext.util.JSON.decode(response.responseText);
										if (responseArray.success == true) {
											this.store.reload();
											modifyWindow.hide();//隐藏修改窗口
										} else {
											Ext.Msg.show({
												title : '信息提示',
												msg : responseArray.message,
												width : 260,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.ERROR
											});
										}
									},scope : this
								});								
		},this);
	},
	/**
	 * 删除产品
	 */
	onDeleteSubmit : function() {
		try {
			this.getSelected();
			if(this.getSelected().parentNode.id != 'root') {
				Ext.Msg.show({
					title : '信息提示',
					msg : '请选择根节点进行操作！',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '请确认要删除当前产品!',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleDelete,//若以此种方法调用，需要指定scope
				scope : this
			});
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择节点再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
		
		
	},
	//附件管理
	onSlaveClick : function() {
		try {
			var selNode = this.getSelected();
			if(selNode.attributes.parentId != 'root')
				return;
			var slaveWindow = new Slave.SlaveManageWindow({busId : selNode.id, busType : 1});
			slaveWindow.show();
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	//数据导入
	onImportClick : function() {
		var impWindow = new Toolsimport.ImportSalesPriceWindow()
		var impForm = impWindow.importDataForm;
		impWindow.show();
		impForm.on("importsuccess", function() {
			this.store.reload();
		},this)
	},
	
	handleDelete : function (btn){
		if(btn == 'ok') {
				var _id = this.getSelected().id;
				Ext.Ajax.request({
					url: PATH + '/proTools/deleteProAction.do',
					params: { id: _id },
					success : function(response) {
						var responseArray = Ext.util.JSON.decode(response.responseText); 
													   	
						if(responseArray.success == true){
							Ext.Msg.show({
								title:'成功提示',
								msg: responseArray.msg,
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
							//this.getRootNode().reload();
							this.getSelected().remove();
							//standWindow.hide();
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
		}
	}
	
})
//---------------------------增加非标产品------------------------------
/**
 * 增加非标产品树
 * @class Ext.ftl.protools.NewTree
 * @extends Ext.tree.ColumnTree
 */
Ext.ftl.protools.NewTree = Ext.extend(Ext.tree.ColumnTree, {
	hideModify : null,
	hideAdd : null,
	isModify : false,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ftl.protools.NewTree.superclass.constructor.call(this, {
			width:650,
			height: 400,
	        // autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			enableDD:false,
	        title: '自制产品',
	        columns:[
	        	{header : '图标',width : 100,disEnableEdit : true},
	        	{header:'牌号',width:200,dataIndex:'brandCode' ,disEnableEdit : true},
	        	{header : "数量",width : 40, dataIndex : 'amount'},
	        	{header : "历史牌号",width : 200, dataIndex : 'brandCodeHistory',disEnableEdit : true},
	        	{header:'名称',width:100,dataIndex:'productName',disEnableEdit : true},
	        	{header:'计量单位',width:100,dataIndex:'productUnit',disEnableEdit : true},
	        	{header:'组别',width:100,dataIndex:'productSortCode',disEnableEdit : true},
	        	{header:'品牌',width:100,dataIndex:'productBrand',disEnableEdit : true},
				{header:'产品类别',width:100,dataIndex:'toolsTypeName',disEnableEdit : true},
				{header:'产品类别id',width:100,dataIndex:'toolsTypeId',disEnableEdit : true,hidden : true},
	        	{header:'来源',width:100,dataIndex:'productSource',disEnableEdit : true},
	        	{header:'创建日期',width:100,dataIndex:'createDateStr',disEnableEdit : true},
	        	{header:'附件',width:100,dataIndex:'slaveFile',disEnableEdit : true},
	        	{header:'proSortId',width:0,dataIndex:'productSortId',hidden : true,disEnableEdit : true},
	        	{header:'备注',width:180,dataIndex:'memo',disEnableEdit : true}
	        ],
	        loader: new Ext.tree.TreeLoader({
	            dataUrl:"#",
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
	        root: new Ext.tree.AsyncTreeNode({
	        	id:"root",
	            text:'Tasks'
	        }),
	        tbar : [{
				text:'增加根节点',
				iconCls:'icon-nonpro-add',
				hidden : this.hideAdd == null ? false : this.hideAdd,
				handler : function() {
					fn : this.onAddRootSubmit();
				},scope : this
			},{
				text:'修改根节点',
				iconCls:'icon-modify',
				hidden : this.hideModify == null ? false : this.hideModify,
				handler : function() {
					fn : this.onModifyRootSubmit();
				},scope : this
			},'-',{
				text:'增加同级自制产品',
				iconCls:'icon-pro-brother',
				handler : function() {
					fn : this.onAddBrotherSubmit();
				},scope : this
			},'-',{
				text:'增加下级自制产品',
				iconCls:'icon-pro-child',
				handler : function() {
					fn : this.onAddChildSubmit();
				},scope : this
			},'-',{
	        	text:'增加一般产品',
				iconCls:'icon-add',
				//hidden : true,
				handler : function() {
					fn : this.onstandSubmit();
				},scope : this
	        },{
				xtype:'tbseparator'
			},{
				text:'移除产品',
				iconCls:'icon-delete',
				handler : function() {
					fn : this.onDeleteSubmit();
				},scope : this
			}]
		})
	},
	
	getSelected : function() {
		var node = this.getSelectionModel().getSelectedNode();
		if(!node) {
			throw Error("请选择节点再进行操作！")
		} else {
			return node;
		}
	},
	
	/**
	 * 生成一新节点
	 * @param {} _r
	 * @return {} 返回新节点
	 */
	buildNoLeafNode : function(_form, values) {
		var node = new Ext.tree.TreeNode({
			id : values.id,
			brandCode : values.brandCode,
			productName : values.productName,
			productUnit : values.productUnit,
			productSortCode : values.sortCode,
			productSortId : values.productSortId,
			productBrand : values.productBrand,
			productSource : values.productSource,
			memo : values.memo,
			toolsId : '',
			brandCodeHistory : values.brandCodeHistory,
			amount : values.amount,
			toolsTypeId:values.toolsTypeId,
			toolsTypeName:values.toolsTypeName,
			leaf : false,
			expandable : true,
			//allowChildren:false,//false :不允许该节点有孩子节点
			uiProvider : Ext.tree.ColumnNodeUI
		})
		
		return node;
	},
	
	/**
	 * 生成一新节点
	 * @param {} _r
	 * @return {} 返回新节点
	 */
	buildLeafNode : function(_r) {
		var node = new Ext.tree.TreeNode({
			id : _r.get('id'),
			brandCode : _r.get('brandCode'),
			productName : _r.get('productName'),
			productUnit : _r.get('productUnit'),
			productSortCode : _r.get('productSortCode'),
			productSortId : _r.get('productSortId'),
			productBrand : _r.get('productBrand'),
			productSource : _r.get('productSource'),
			memo : _r.get('memo'),
			toolsId : '',
			brandCodeHistory : _r.get('brandCodeHistory'),
			amount : _r.get('amount'),
			leaf : true,
			//expandable : true,
			allowChildren:false,//false :不允许该节点有孩子节点
			uiProvider : Ext.tree.ColumnNodeUI
		})
		
		return node;
	},
	
	/**
	 * 添加新节点
	 * @param {} node
	 */
	appendChild : function(parentNode, node) {
		parentNode.appendChild(node);
	},
	
	onAddRootSubmit : function() {
		var proWindow = new Ext.ftl.ProductWindow({title:'新增根节点',isSelfMake:true});
		proWindow.show();
		proWindow.productForm.proSource.store.isNonStand = true;
		proWindow.on('submit', function(_form, values) {
			Ext.Ajax.request({
				url: PATH + '/proTools/getProSortByCode.do?method=getTreeDto',
				params: { treeDto: Ext.encode(values) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
												   	
					if(responseArray.success == true){
						Ext.Msg.show({
							title:'信息提示',
							msg: "该产品已经存在！",
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
					} else {
						this.appendChild(this.getRootNode(), this.buildNoLeafNode(_form, values));
						proWindow.hide();
					} 
				},scope : this
			});
		},this)
	},
	
	setNodeValue : function(_node, dataIndex, newValue) {
		_node.cols[dataIndex] = newValue;
		_node.attributes[dataIndex] = newValue;
		_node.ui.setInnerHTMLValue(dataIndex, newValue);
	},
	
	onModifyRootSubmit : function() {
		try {
			var selNode = this.getSelected();
			if(selNode.parentNode.id != 'root') {
				Ext.Msg.show({
					title : '信息提示',
					msg : '请选择根结点，进行修改!',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			
			var updateProWindow = new Ext.ftl.ProductWindow({title:'修改根节点',isSelfMake:true});
			var _record = new Ext.ftl.protools.MyRecord({
				id : selNode.id,
				brandCode : selNode.attributes.brandCode,
				productName : selNode.attributes.productName,
				productUnit : selNode.attributes.productUnit,
				sortCode : selNode.attributes.productSortCode,
				productBrand : selNode.attributes.productBrand,
				productSource : selNode.attributes.productSource,
				toolsTypeId : selNode.attributes.toolsTypeId,
				leaf : selNode.attributes.leaf,
				memo : selNode.attributes.memo
			});
			updateProWindow.on('submit', function(_form, values) {
				var _node = this.getRootNode().firstChild;
				
				this.setNodeValue(_node, 'brandCode', values.brandCode);
				this.setNodeValue(_node, 'productName', values.productName);
				this.setNodeValue(_node, 'productUnit', values.productUnit);
				
				if(values.productSortCode){
					this.setNodeValue(_node, 'productSortCode', values.productSortCode);
					this.setNodeValue(_node, 'productSortId', values.productSortId);
				}
				this.setNodeValue(_node, 'productBrand', values.productBrand);
				this.setNodeValue(_node, 'productSource', values.productSource);
				this.setNodeValue(_node, 'toolsTypeId', values.toolsTypeId);
				this.setNodeValue(_node, 'toolsTypeName', values.toolsTypeName);
				this.setNodeValue(_node, 'memo', values.memo);
			},this)
			updateProWindow.show();
			updateProWindow.productForm.proSource.store.isNonStand = true;
			updateProWindow.productForm.setValues(_record);
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : '请选择根结点后，再进行修改!',
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	onAddBrotherSubmit : function() {
		var proWindow = new Ext.ftl.ProductWindow({isSelfMake:true});
		proWindow.show();
		proWindow.productForm.proSource.store.isNonStand = true;
		proWindow.on('submit', function(_form, values) {
			var selectNode = this.getSelected();
			if(!this.isModify && !Ext.isEmpty(selectNode.parentNode.attributes.toolsId)){
				Ext.Msg.show({
					title : '信息提示',
					msg : '该产品不允许添加同级自制品!',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			if(selectNode.parentNode.id == 'root') {
				Ext.Msg.show({
					title : '信息提示',
					msg : '不能为根结点添加同级产品!',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			Ext.Ajax.request({
				url: PATH + '/proTools/getProSortByCode.do?method=getTreeDto',
				params: { treeDto: Ext.encode(values) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);    	
					if(responseArray.success == true){
						Ext.Msg.show({
							title:'信息提示',
							msg: "该产品已经存在！",
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
					} else {
						this.appendChild(selectNode.parentNode, this.buildNoLeafNode(_form, values));
						proWindow.hide();
					} 
				},scope : this
			});
		},this)
	},
	
	onAddChildSubmit : function() {
		var proWindow = new Ext.ftl.ProductWindow({isSelfMake:false});
		proWindow.show();
		proWindow.productForm.proSource.store.isNonStand = true;
		proWindow.on('submit', function(_form, values) {
			var selectNode = this.getSelected();
			
			if(!this.isModify && !Ext.isEmpty(selectNode.attributes.toolsId)){
				Ext.Msg.show({
					title : '信息提示',
					msg : '该产品不允许添加下级自制品!',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			
			if(selectNode.isLeaf()) {
				Ext.Msg.show({
					title : '信息提示',
					msg : '不能为一般产品添加下级产品!',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
				return;
			}
			
			Ext.Ajax.request({
				url: PATH + '/proTools/getProSortByCode.do?method=getTreeDto',
				params: { treeDto: Ext.encode(values) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText); 
												   	
					if(responseArray.success == true){
						Ext.Msg.show({
							title:'信息提示',
							msg: "该产品已经存在！",
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
					} else {
						this.appendChild(selectNode, this.buildNoLeafNode(_form, values));
						selectNode.expand();
						proWindow.hide();
					} 
				},scope : this
			});
		},this)
	},
	
	onDeleteSubmit : function() {
		try {
			if(this.hideModify == null && this.getSelected().parentNode.id == 'root')
				return;
			this.getSelected().remove();
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : '请选择节点后，再进行删除！',
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	/**
	 * 添加一般产品
	 */
	onstandSubmit : function() {
		var standWindow = new Ext.ftl.ProductWindow({title : '增加一般产品',isSelfMake:false});
		standWindow.on('submit', function(_form, values) {
			try {
				var selectNode = this.getSelected();
				if(!this.isModify && !Ext.isEmpty(selectNode.attributes.toolsId)){
					Ext.Msg.show({
						title : '信息提示',
						msg : '该产品不允许添加下级自制品!',
						width : 260,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
					return;
				}
				
				if(selectNode.isLeaf()) {
					Ext.Msg.show({
						title : '信息提示',
						msg : '不能为一般产品添加下级产品!',
						width : 260,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
					return;
				}
				Ext.Ajax.request({
					url: PATH + '/proTools/getProSortByCode.do?method=getTreeDto',
					params: { treeDto: Ext.encode(values) },
					success : function(response) {
						var responseArray = Ext.util.JSON.decode(response.responseText); 
													   	
						if(responseArray.success == true){
							Ext.Msg.show({
								title:'信息提示',
								msg: "该产品已经存在！",
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO
							});
						} else {
							values.leaf = true;				
							var _node = this.buildLeafNode(values);
							this.appendChild(selectNode, _node);
							selectNode.expand();
							standWindow.hide();
						} 
					},scope : this
				});
			} catch(_e) {
				Ext.Msg.show({
					title : '信息提示',
					msg : '请首先添加根结点！',
					width : 260,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.INFO
				});
			}
		},this)
		standWindow.show();
	}
});

/**
 * 产品工具信息树列表
 * @class Ext.ftl.protools.ProToolsTree
 * @extends Ext.tree.ColumnTree
 */
Ext.ftl.protools.ProToolsListTree = Ext.extend(Ext.tree.ColumnTree, {
	store : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.Store({
	       proxy: new Ext.data.HttpProxy({url:PATH + '/proTools/listAction.do?method=getToolsWithChildren'}),//调用的动作 
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

		
		Ext.ftl.protools.ProToolsListTree.superclass.constructor.call(this, {
			//el:'tree-ct',
	        width:647,
			height: 400,
	        //autoHeight:true,
	        rootVisible:false,
	        autoScroll:true,
			expandable:false,
			listeners : {
				'render' : function() {
					var tl = this.store;
					var _searchStr = null;
					tl.on('beforeload', function() {
						this.baseParams.searchStr = _searchStr;
					})
					
					var searchBar = new Ext.ftl.protools.SearchBar({
						targetTree : this
					})
					searchBar.on('onsearch', function(_searchBar, _str) {
						_searchStr = _str;
						tl.reload({params : {start : 0, limit : PAGESIZE}});
					})
					
					this.store.load({params:{start:0,limit:PAGESIZE}});
				}
			},
			enableDD:false,
	        title: '产品信息列表',
	        columns:[
	        	{header : '图标',width : 100,disEnableEdit : true},
	        	{header:'牌号',width:200,dataIndex:'brandCode'},
	        	{header : "历史牌号", width:200,dataIndex : 'brandCodeHistory'},
	        	{header:'货品编号',width:100,dataIndex:'productCode'},
	        	{header : "数量", width:30,dataIndex : 'amount'},
	        	{header:'名称',width:100,dataIndex:'productName'},
	        	{header:'计量单位',width:100,dataIndex:'productUnit'},
	        	{header:'组别',width:100,dataIndex:'productSortCode'},
	        	{header:'品牌',width:100,dataIndex:'productBrand'},
	        	{header:'来源',width:100,dataIndex:'productSource'},
	        	{header:'创建日期',width:100,dataIndex:'createDateStr'},
	        	{header:'附件',width:100,dataIndex:'slaveFile'},
	        	{header:'ui',width:0,dataIndex:'uiProvider', hidden : true},
	        	{header:'备注',width:180,dataIndex:'memo'}
	        ],
	        tbar : [],
	        
	        bbar : new Ext.ffc.TreePagingToolbar({
	            pageSize: PAGESIZE,
	            store: this.store,
	            displayInfo: true,
	            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	            emptyMsg: "没有记录"
	        }),

	        
	        loader: new Ext.tree.TreeLoader({
	            uiProviders:{
	                'col': Ext.tree.ColumnNodeUI
	            }
	        }),
			
	        root: new Ext.tree.TreeNode({
	        	id:"root",
	            text:'Tasks'
	        })
		});
		
		this.addEvents('ondelete');
		this.addEvents('onadd');
	
	}
	
});

/**
 * 布局面板
 * @class Ext.ftl.protools.NorthPanel
 * @extends Ext.Panel
 */
Ext.ftl.protools.NorthPanel = Ext.extend(Ext.Panel, {
	hideModify : null,
	hideAdd : null,
	newTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.newTree = new Ext.ftl.protools.NewTree({
			hideModify : this.hideModify,
			hideAdd : this.hideAdd
		});
		
		Ext.ftl.protools.NorthPanel.superclass.constructor.call(this, {
			region: 'west',
            split: true,
            layout : 'fit',
            width: 445,
            collapsible: true,
            margins:'3 0 3 3',
            cmargins:'3 3 3 3',
            items : [this.newTree]
		})
	}
})

/**
 * 布局面板
 * @class Ext.ftl.protools.CenterPanel
 * @extends Ext.Panel
 */
Ext.ftl.protools.CenterPanel = Ext.extend(Ext.Panel, {
	proToolsTree : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.proToolsTree = new Ext.ftl.protools.ProToolsListTree();
		Ext.ftl.protools.CenterPanel.superclass.constructor.call(this, {
			region: 'center',
            split: true,
            width: 200,
            layout : 'fit',
            collapsible: true,
            margins:'3 0 3 0',
            cmargins:'3 3 3 3',
            items : [this.proToolsTree]
		})
	}
})

/**
 * 非标产品弹出窗口
 * @class Ext.ftl.protools.AddNonStandProWindow
 * @extends Ext.Window
 */
Ext.ftl.protools.ManagerNonStandProWindow = Ext.extend(Ext.Window, {
	hideModify : null,
	hideAdd : null,
	northPanel : null,
	centerPanel : null,
	_treeEdit : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new Ext.ftl.protools.NorthPanel({
			hideModify : this.hideModify,
			hideAdd : this.hideAdd
		});
		this.centerPanel = new Ext.ftl.protools.CenterPanel();
		
		this._treeEdit = new Ext.tree.ColumnTreeEditor(this.northPanel.newTree,{
			completeOnEnter: true,
			autosize: true,
			ignoreNoChange: true,
			listeners : {
				specialkey : Ext.ffc.gridTreeEditorkeyMove.moveCallBack
		    }
		});
		
		Ext.ftl.protools.ManagerNonStandProWindow.superclass.constructor.call(this, {
			//title: "增加非标产品",  
			width:1060,  
			height:485,  
			plain:true,
			closable : true,
			modal : true,
			constrainHeader : true,
			maximizable : true,
			closeAction:'hide',
			layout:"border",
			buttonAlign : 'left',
			listeners : {
				'render' : function() {
					this.centerPanel.proToolsTree.on({
						'dblclick' : function(node, e) {
							try {
								var t = Ext.tree.toNewTreeNode(node.attributes,{'uiProvider':'col'},false);
								var _newTree = this.northPanel.newTree;
								var targetNode = _newTree.getSelected();
								if(targetNode.isLeaf()) {
									Ext.Msg.show({
										title : '信息提示',
										msg : '请选择父节点，进行添加操作！',
										width : 260,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.INFO
									});
								} else {
									this.northPanel.newTree.appendChild(targetNode, t);
									this.northPanel.newTree.getRootNode().expandChildNodes(true);
								}
								
							} catch(_err) {
								var _errInfo = null;
								if(!_newTree.getRootNode().hasChildNodes()) {
									_errInfo = '请首先在左侧产品树中，添加自制产品节点！'
								} else if(!targetNode) {
									_errInfo = '请选择左侧产品树中自制产品节点！'
								}
								Ext.Msg.show({
									title : '信息提示',
									msg : _errInfo,
									width : 260,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.INFO
								});
							}
						},scope : this
					})
				}
			},
			items : [this.northPanel,this.centerPanel]
		})
		this.addEvents('onsubmit');
		this.addEvents('oncancel')
	},
	
	ajaxPost : function(postUrl, parmStr) {
		Ext.Ajax.request({
			url : postUrl,
			params : {
				content : parmStr
			},
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.success == true) {
					this.fireEvent('onsubmit');
					this.hide();
				} else {
					Ext.Msg.show({
								title : '错误提示',
								msg : responseArray.msg,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.ERROR
							});
				}
			},scope : this
		});
	}
	
	
})

Ext.ftl.protools.AddNonStandProWindow = Ext.extend(Ext.ftl.protools.ManagerNonStandProWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ftl.protools.AddNonStandProWindow.superclass.constructor.call(this, {
			title : '增加自制产品',
			buttons : [{
				text : '保存',
				handler : function() {
					fn : this.onAddSubmit();
				},scope : this
			},{
				text : '取消',
				handler : function() {
					this.fireEvent('oncancel');
					this.hide();
				},scope : this
			}]
		})
	},
	
	//执行后台保存，并刷新产品树
	onAddSubmit : function() {
		//var selectedNode = this.northPanel.newTree.getRootNode().firstChild ;
		//alert(selectedNode.id);
		var rootCode = this.northPanel.newTree.getRootNode();
		/*if(!rootCode.hasChildNodes() || !rootCode.firstChild.hasChildNodes())
			return;*/
		//rootCode.expandChildNodes(true);
		//var _temp = null;
		//_temp = Ext.tree.toNewTreeNode(selectedNode.attributes,{'uiProvider':'col'},false);
		//alert(Ext.encode(_temp));
		
		var parmStr = "{proTools : " + rootCode.toJsonString() + ",sycQuoToosInfor :" + this.sycQuoToosInfor + "}";
		//alert(parmStr);
		//return;
		var postUrl = PATH + '/proTools/addNonStandAction.do';
		this.ajaxPost(postUrl, parmStr);
	}
})

Ext.ftl.protools.ModifyNonStandProWindow = Ext.extend(Ext.ftl.protools.ManagerNonStandProWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ext.ftl.protools.ModifyNonStandProWindow.superclass.constructor.call(this, {
			title : '修改自制产品',
			buttons : [{
				text : '保存',
				handler : function() {
					fn : this.onAddSubmit();
				},scope : this
			},{
				text : '取消',
				handler : function() {
					this.fireEvent('oncancel');
					this.hide();
				},scope : this
			}]
		})
	},
	
	//执行后台保存，并刷新产品树
	onAddSubmit : function() {
		var selectedNode = this.northPanel.newTree.getRootNode().firstChild ;
		if(selectedNode == null) {
			return;
		}
		var rootCode = this.northPanel.newTree.getRootNode();
		var _temp = null;
		_temp = Ext.tree.toNewTreeNode(selectedNode.attributes,{'uiProvider':'col'},true);
		
		var parmStr = "{proTools : " + rootCode.toJsonString() + ",sycQuoToosInfor:" + this.sycQuoToosInfor + "}";
		//alert(rootCode.toJsonString());
		//return;
		var postUrl = PATH + '/proTools/updateNonStandAction.do';
		this.ajaxPost(postUrl, parmStr);
	}
})

// ------------------------------end--------------------------------------
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//组别URL
	Ext.ftl.protools.ProSortStore.STORE_URL = PATH + '/proTools/getProSortByCode.do?method=getProSort';
	Ext.ftl.protools.ProBrandStore.STORE_URL = PATH + '/proTools/getProSortByCode.do?method=getProBrand';
	Ext.ftl.protools.ProSourceStore.STORE_URL = PATH + '/proTools/getProSortByCode.do?method=getProSource';
	
	getConfig = function() {
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("002" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if("002002" == childModule[j].id) {
						var _configArr = childModule[j].children;
						if(_configArr.length > 0) {
							for(var k = 0; k < _configArr.length; k++) {
								if(k != _configArr.length-1)
									_configStr += _configArr[k].url + ",";
								else 
									_configStr += _configArr[k].url + "}"
							}
						} else {
							_configStr += "}";
						}
						
						break;
					}
				}
				
				break;
			}
		}
		return Ext.decode(_configStr);
	}
	var _config = this.getConfig();
	Ext.ftl.protools.proToolsTree = new Ext.ftl.protools.ProToolsTree(_config);
	Ext.ftl.protools.proToolsTree.render('pro_tree-ct');
	Ext.ffc.ResizeManager.addResizeObject(Ext.ftl.protools.proToolsTree);

})