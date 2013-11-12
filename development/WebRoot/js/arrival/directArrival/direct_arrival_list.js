/**
 * 定义命名空间
 */
Ext.namespace('Ext.ftl.direct');

//功能:　显示到货单明细窗口
Ext.ftl.direct.onClickDetail = function(_id) {
	var arrivalDetailWindow = new Direct.DetailWindow();
	arrivalDetailWindow.buttons[1].text = '关闭';
	arrivalDetailWindow.buttons[0].hide();//隐藏确定按钮
	//arrivalDetailWindow.buttons[2].hide();//隐藏全部交清按钮
	
	var _grid = arrivalDetailWindow.centerPanel.arrivalProductGrid;
	_grid.getStore().baseParams.arrivalId = _id;
	arrivalDetailWindow.on('render', function() {
		var arrInfoForm = this.northPanel.arrivalInfoForm;
		arrInfoForm.findByType('datefield')[0].disabled = true;
		arrInfoForm.loadData(_id);
		/*var _form = arrInfoForm.getForm();
		
		_form.load({
			url : Ext.ftl.direct.ARRIVAL_STORE_URL,
			params : {
				arrivalId: _id
			}
		})*/
	})
	arrivalDetailWindow.show();
	_grid.getStore().load();
	_grid.getTopToolbar().hide();//隐藏tbar
},

/**
 * 到货单状态下拉框
 * @class Ext.ftl.arrival.StatusCombo
 * @extends Ext.form.ComboBox
 */
Ext.ftl.direct.StatusCombo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.SimpleStore({
						fields : ['status', 'value'],
						data : [['全部',''],['入库 未确认', '0'],['入库 已确认', '1'],['入库 已作废', '-1']]
					});
		Ext.ftl.direct.StatusCombo.superclass.constructor.call(this, {
			fieldLabel : '状态',
			hiddenName : 'status',
			mode : 'local',
			displayField : 'status',
			valueField : 'value',
			//anchor:'90%',
			readOnly : true,
			frame : true,
			triggerAction : 'all',
			value : '全部',
			store : this.store
		})
	}
})

/**
 * 到货单搜索form
 * @class Ext.ftl.arrival.OrderSearchForm
 * @extends Ext.FormPanel
 */
Ext.ftl.direct.ArrivalSearchForm = Ext.extend(Ext.FormPanel, {
	statusField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		this.statusField = new Ext.ftl.direct.StatusCombo({x:90,y:33,width : 170,
            	enableKeyEvents: true,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }});
		var _config = [
			//1
            {xtype:'label',text: '入库单号:',x:0,y:5,style:this.lableStyle_},
            {xtype:'textfield', name: 'arrivalCode',x:90,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '采购单号:',x:250,y:5,style:this.lableStyle_},
            {xtype:'textfield',name: 'orderCode',x:340,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '日期起:',x:490,y:5,style:this.lableStyle_},
            {xtype:'datefield',name: 'beginDate', readOnly : true, format:'Y-m-d',emptyText:'',x:580,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
            {xtype:'label',text: '至:',x:710,y:5,style:this.lableStyle_},
            {xtype:'datefield',name: 'endDate', readOnly : true, format:'Y-m-d',emptyText:'',x:800,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'specialkey' : function(field, e) {
	            		if(e.getKey() == e.ENTER)
	            			this.fireEvent('search',this, this.getValues());
	            	},scope : this
	            }},
			{xtype:'label',text: '年度:',x:940,y:5,style:this.lableStyle_},
			new Ext.ffc.YearComboBox({x:1030,y:3, width:80}),
            {xtype:'label',text: '状态:',x:0,y:35,style:this.lableStyle_},
            this.statusField
		];
		Ext.ftl.direct.ArrivalSearchForm.superclass.constructor.call(this, {
			//width : 1000,
			height : 100,
	        labelAlign:'right',buttonAlign:'right', border : false,
	        frame:true,monitorValid:false,
	        layout : 'absolute',
	        items : _config,
	        bbar : ['->',{
	           		text : "搜  索",
	           		iconCls : 'icon-search',
	           		handler : function() {
	           			//发布search事件
	           			this.fireEvent('search',this, this.getValues());
	           		},
	           		scope : this
           		},
           		'-',{
	           		text : "重  置",
	           		iconCls : 'icon-reset',
	           		handler : function () {
	           			this.getForm().reset();
	           		},scope : this
       		}]
	       
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
 * 到货单列表
 * @class Ext.ftl.arrival.ArrivalListGrid
 * @extends Ext.grid
 */
Ext.ftl.direct.ArrivalListGrid = Ext.extend(Ext.grid.GridPanel, {
	storeUrl : null,
	OrderSearchWindow : null,
	orderComboStoreUrl : null,//订单搜索URL
	store : null,
	orderType : null,
	searchRecord : null,
	orderIdArray : null,//已经全部入库订单数组
	loadMarsk : null,
	isAddHide : true,isModifyHide : true,isDelHide : true,isSubmitHide : true,
	isDetailHide : true, isToExcelHide : true,
	/**
	 * 构造方法
	 * @param {} _cfg 配置参数
	 */
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.store = new Ext.data.JsonStore({
				url : this.storeUrl,
				remoteSort : true,
				totalProperty : 'totalProperty',
				root : 'arrivalInfo',
				fields : ['arrivalCode','supplierName','orderCode','contractCode','userName',
					'deliveryDate','memo', 'orderInforId', 'id','editDateString','status','customerName','arrivalType']
			})
		this.sm = new Ext.grid.CheckboxSelectionModel();//复选框
		Ext.ftl.direct.ArrivalListGrid.superclass.constructor.call(this, {
			//title : this['title'] == null ? '入库单列表' : this['title'],
			hideHeaders : false,//隐藏表头。
			store : this.store,
			sm : this.sm,
			columns : [this.sm, 
				new Ext.grid.RowNumberer({
					renderer:function(value,metadata,record,rowIndex){
						var record_start = record.store.lastOptions.params.start
					    return record_start + 1 + rowIndex;
					}
				}),
				{header : '入库单编号',dataIndex : 'arrivalCode',sortable: true},
				{header : '供应商名称' ,  dataIndex : 'supplierName',sortable: true},
				{header : '状态', dataIndex : 'status', renderer : this.renderStatus,sortable: true},
				{header : '库存类型', dataIndex : 'arrivalType',sortable: true, 
					renderer : function(value){
						if(value * 1 == 0){
						    return '产品库';
						}else if(value * 1 == -1){
						    return "<span style='color:#2D61B5;font-weight:bold;'>材料库</span>";
						}
					 return '不确定';
				}},
				{header : '入库日期', dataIndex : 'deliveryDate',sortable: true},
				{header : '编制人', dataIndex : 'userName',sortable: true},
				{header : '编制时间', dataIndex : 'editDateString',sortable: true},
				{header : '备注', dataIndex : 'memo',sortable: true},
				
				{header : '采购单号', dataIndex : 'orderCode',sortable: true, hidden: true},
				{header : '合同单号', dataIndex : 'contractCode',sortable: true, hidden: true},
				{header : '客户名称' ,  dataIndex : 'customerName',sortable: true, hidden: true},
				{header : 'orderId', dataIndex : 'orderInforId', hidden : true},
				{header : '查看详细', dataIndex : 'id', hidden : true}
			],
			selModel : new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : {
						fn : function(_sel, _index, _r) {
							this.fireEvent('rowselect', _r)//必须给此方法附加一个对象,否则将出错．this是ArrivalListGrid本身
						},
						scope : this
					}
				}
			}),
			width : 1250,
			height : 350,
			bodyStyle:'width:100%',
			iconCls:'icon-grid',
			//frame : true,
			stripeRows :true,
			viewConfig: {   
	            forceFit:true,
	            autoFill : true,
	            deferEmptyText : false,
	            //enableRowBody : false,
	            emptyText : '无入库单信息！'
        	},
			enableHdMenu : true,
            tbar : [{
            	text : "新增入库单",
            	iconCls:'icon-add',
            	hidden : this.isAddHide,
            	handler : function() {
            		this.onAddSubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isAddHide
			},{
				text : '查看详细',
				iconCls : 'icon-detail',
				hidden : this.isDetailHide,
				handler : function() {
					fn : this.onDetailClick();
				},
				scope : this
			},{
				xtype:'tbseparator',
				hidden : this.isDetailHide
			},{
            	text : "修改入库单",
            	iconCls:'icon-modify',
            	hidden : this.isModifyHide,
            	handler : function() {
            		fn : this.onModifySubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isModifyHide
			},{
            	text : "作废入库单",
            	iconCls:'icon-modify',
            	hidden : false,
            	handler : function() {
            		fn : this.onInvalidSubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : false
			},{
            	text : "删除入库单",
            	iconCls:'icon-delete',
            	hidden : this.isDelHide,
            	handler : function() {
            		fn : this.onDeleteSubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isDelHide
			},{
            	text : "确认入库",
            	iconCls:'icon-submit',
            	hidden : this.isSubmitHide,
            	handler : function() {
            		fn : this.onArrivalSubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isSubmitHide
			}, {
				text : '导出Excel',
				iconCls : 'icon-excel',
				hidden : this.isToExcelHide,
				handler : function() {
					fn : this.onExcelClick();
				},
				scope : this
			},{
				xtype:'tbseparator',
				hidden : false
			},{
				text:'导出入库单列表',
				hidden : false,
				iconCls:'icon-excel',
				listeners: {
					'click' : function(){	
						window.open(PATH + '/arrival/excelAction.do?method=arrivalListToExcel&orderType=' + this.orderType);
					},scope : this
		 		}
			}],
            //分页信息栏
			bbar : new Ext.PagingToolbar({
				pageSize : PAGESIZE,
				emptyMsg: "没有记录",
				displayInfo: true,
        		displayMsg: '显示第 {0} - {1} 条 共 {2} 条',
				store : this.store
			}),
			listeners : {
				'render' : function() {
					var _store = this.getStore();
					_store.on({
						'beforeload' : function() {
							if(this.searchRecord != null) {
								var _searchStr = Ext.util.JSON.encode(this.searchRecord.data);
								_store.baseParams.searchStr = _searchStr;
								//alert('我被调用了orderType = ' + this.orderType);
								//_store.baseParams.orderType = this.orderType;
							}
						},scope : this
					})
					//加载数据
					//_store.load({params : {start : 0, limit : PAGESIZE}});
				},
				rowdblclick : function(grid, rowIndex, e){
					grid.onDetailClick();
				}
			}
		});
		
		this.addEvents('rowselect');
	},
	
	renderDetail : function(arrId) {
		_orderType = this.orderType;
		return '<a href=javascript:Ext.ftl.arrival.onClickDetail(\'' + arrId + "\',\'" +_orderType +  '\')>查看</a>'
	},
	
	renderStatus : function(value) {
		switch (value) {
			case 0 :
			return "<span style='color:green;font-weight:bold;'>入库 未确认</span>";
			break;
			case 1 :
			return "<span style='color:#2D61B5;font-weight:bold;'>入库 已确认</span>";
			break;
			case -1 :
			return "<span style='color:#606E7F;font-weight:bold;'>入库 已作废</span>";
			break;
		}
	},
	
	/**
	 * 获取所选取的记录。如果没有选择，抛出一个异常。
	 * @return {} Record
	 */
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(_sm.getCount() == 0) {
			throw Error('请选择入库单后再进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	getSelections : function() {
		var _sm = this.getSelectionModel();
		
		if(_sm.getCount() == 0) {
			throw Error('请选择一条记录进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	
	remove : function() {
		try {
			var _records = this.getSelections();
			for(var i = 0; i < _records.length; i++) {
				this.getStore().remove(_records[i]);
			}
		}catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description,// == null ? '请选择到货单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
		
	},
	
	/**
	 * 重新载入表单数据
	 */
	reload : function() {
		this.getStore().reload();
	},
	
	/**
	 * 为搜索条件设值
	 * @param {} _value
	 */
	setSearchStr : function(_value) {
		this.searchRecord = _value;
	},
	
	onAddSubmit : function() {
		var arrivalAddWindow = new Ext.ftl.direct.add.ArrivalAddWindow();
		arrivalAddWindow.on('onsubmit', function() {
	    	this.getStore().reload();
	    },this)
		arrivalAddWindow.show();
		arrivalAddWindow.northPanel.arrivalInfoForm.setValues();
	},
	
	onModifySubmit : function() {
		try {
			if(this.getSelections().length > 1) {
				Ext.MessageBox.alert('错误提示', '请选择一条记录进行操作！');
				return;
			}
			this.getSelected();
			this.handleModify();//若以此种方法调用，需要指定scope
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择入库单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	//作废入库单
	onInvalidSubmit : function() {
		try {
			var records = this.getSelections();
			for(var i = 0; i < records.length; i++) {
				if(records[i].get('status') == -1) {
					Ext.MessageBox.show({
						title:'系统提示',
						msg: '请只选择未作废的入库单!',
						buttons: Ext.MessageBox.OK,
						scope : this
					});
					return;
				}
			}
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '确定作废吗!',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleInvalid,//若以此种方法调用，需要指定scope
				scope : this
			});
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择入库单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	onDeleteSubmit : function() {
		try {
			var _record = this.getSelected();
			var records = this.getSelections();
			for(var i = 0; i < records.length; i++) {
				if(records[i].get('status') != 0) {
					Ext.MessageBox.show({
						title:'系统提示',
						msg: '请只选择未确认状态的入库单，进行删除操作!',
						buttons: Ext.MessageBox.OK,
						scope : this
					});
					return;
				}
			}
			
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '确定要删除当前入库单!',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleDelete,//若以此种方法调用，需要指定scope
				scope : this
			});
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
	
	onArrivalSubmit : function() {
		try {
			var records = this.getSelections();
			for(var i = 0; i < records.length; i++) {
				if(records[i].get('status') != 0) {
					Ext.MessageBox.show({
						title:'系统提示',
						msg: '请只选择未确认状态的入库单，进行操作!',
						buttons: Ext.MessageBox.OK,
						scope : this
					});
					return;
				}
			}
			
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '确定入库吗!',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleArrival,//若以此种方法调用，需要指定scope
				scope : this
			});
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择入库单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	onDetailClick : function() {
		var _sm = this.getSelectionModel();
		if(!_sm.hasSelection() || _sm.getCount() > 1) {
			Ext.MessageBox.alert('错误提示', '请选择一条记录进行操作！');
		} else {
			var selRecord = this.getSelected();
			Ext.ftl.direct.onClickDetail(selRecord.id);
		}
	},
	
	onExcelClick : function() {
		try {
			var _record = this.getSelected();
			if(_record.get('status') != 1) {
				Ext.MessageBox.show({
					title:'系统提示',
					msg: '请选择确认入库的入库单!',
					buttons: Ext.Msg.OK,
					scope : this
				});
				return;
			}
			var arrId = _record.get('id');
			new Ext.ffc.ExpTempleteSelectWindow({
				templeteType:19,
				exportDataMethod:function(tempId){
					window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertExcel&busId=" + arrId + "&tempId=" + tempId);
				}
			}).show();
			//window.open(PATH + '/arrival/excelAction.do?method=directArrToExcel&arrId=' + arrId);
		} catch(_e) {
			Ext.MessageBox.alert('错误提示', _e.message);
		}
	},
	
	handleModify : function(btn) {
		var _record = this.getSelected();
		if(_record.get('status') != 0) {
				Ext.MessageBox.show({
					title:'系统提示',
					msg: '请只选择未确认状态的入库单，进行操作!',
					buttons: Ext.MessageBox.OKCANCEL,
					scope : this
				});
				return;
			}
        this.modifyWindow = new Direct.ModifyWindow();
	    //this.modifyWindow.buttons[2].hide();//隐藏全部交清按钮
	    this.modifyWindow.centerPanel.arrivalProductGrid.getStore().baseParams.arrivalId = _record.get('id');
	    this.modifyWindow.show();
	    this.modifyWindow.on('onsubmit', function() {
	    	this.getStore().reload();
	    },this)
	   // Ext.ffc.util.debug(_record.data);
	    this.modifyWindow.northPanel.arrivalInfoForm.loadData(_record.get('id'));
	   
	},
	
	handleDelete : function (btn){
		if(btn == 'ok') {
			var _records = this.getSelections();
			var _record = this.getSelected();
			var ids = [];
		
			for(var i = 0; i < _records.length; i++) {
				ids.push(_records[i].id);
			}
			//删除到货单产品信息
			Ext.Ajax.request({
				url : Ext.ftl.direct.ArrivalListGrid.DELETE_URL,
				params : { arrInfoId : Ext.encode(ids) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						Ext.Msg.show({
									title : '成功提示',
									msg : responseArray.msg,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.INFO
								});
						this.remove();
					} else {
						Ext.Msg.show({
									title : '错误提示',
									msg : responseArray.msg,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR
								});
					}
				},
				scope : this
			});
		}
	},
	//确认到货
	handleArrival : function(btn) {
		if(btn == 'ok') {
			var _records = this.getSelections();
			var ids = [];
		
			for(var i = 0; i < _records.length; i++) {
				ids.push(_records[i].id);
			}
			//alert(Ext.encode(ids));return;
			this.loadMarsk = new Ext.LoadMask(this.getEl(), {
			     msg : '正在保存数据，请稍候。。。。。。',
			     removeMask : true// 完成后移除
			 });
			this.loadMarsk .show(); //显示
			//确认到货
			Ext.Ajax.request({
				url : Ext.ftl.direct.ArrivalListGrid.ARRIVAL_SUBMIT_URL,
				params : { arrInfoId : Ext.encode(ids) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						this.getStore().reload();
						Ext.Msg.show({
							title : '成功提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
						this.loadMarsk.hide();
						/*if(responseArray.showWindow) {
							var array = Ext.decode(responseArray.orderIds);
							if(array.length > 0) {
								this.orderIdArray = array;
								this.onAllArrival();
							}
						}*/
					} else {
						Ext.Msg.show({
							title : '错误提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
						this.loadMarsk.hide();
					}
				},
				scope : this
			});
		}
	},
	
	//作废入库单
	handleInvalid : function(btn) {
		if(btn == 'ok') {
			var _records = this.getSelections();
			var ids = [];
		
			for(var i = 0; i < _records.length; i++) {
				ids.push(_records[i].id);
			}
			//alert(Ext.encode(ids));return;
			this.loadMarsk = new Ext.LoadMask(this.getEl(), {
			     msg : '正在保存数据，请稍候。。。。。。',
			     removeMask : true// 完成后移除
			 });
			this.loadMarsk .show(); //显示
			//作废入库
			Ext.Ajax.request({
				url : Ext.ftl.arrival.ArrivalListGrid.ARRIVAL_INVALID_URL,
				params : { arrInfoId : Ext.encode(ids) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						this.getStore().reload();
						Ext.Msg.show({
							title : '成功提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.INFO
						});
						this.loadMarsk.hide();
					} else {
						Ext.Msg.show({
							title : '错误提示',
							msg : responseArray.msg,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
						this.loadMarsk.hide();
					}
				},
				scope : this
			});
		}
	},
	
	//全部交清（未用）
	onAllArrival : function() {
		Ext.MessageBox.show({
			title:'系统提示',
			msg: '所有产品已入库，是否确定产品全部交清？',
			buttons: Ext.MessageBox.OKCANCEL,
			fn: this.handlerAllArrival,//若以此种方法调用，需要指定scope
			scope : this
		});
	},
	
	//全部到货后，修改订单状态（未用）
	handlerAllArrival : function(btn) {
		if(btn == 'ok') {
			//alert(Ext.encode(this.orderIdArray));return;
		 // var _form = this.northPanel.arrivalInfoForm;
		  //var _orderId = _form.getValues().get('orderId');
		  
			//全部到货后，修改订单状态
			Ext.Ajax.request({
				url : Ext.ftl.direct.ALLARRIVAL_URL,
				params : { orderId : Ext.encode(this.orderIdArray) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						Ext.Msg.show({
									title : '成功提示',
									msg : responseArray.msg,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.INFO
								});
					} else {
						Ext.Msg.show({
									title : '错误提示',
									msg : responseArray.msg,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.ERROR
								});
					}
				},
				scope : this
			});
		}
	}
})



/**
 * 到货单入口Panel，上下布局，上方为搜索，下方到货单列表。
 * @class Ext.ftl.arrival.ArrivalListPanel
 * @extends Ext.Panel
 */
Ext.ftl.direct.ArrivalIndexPanel = Ext.extend(Ext.Panel, {
	northPanel : null,
	centerPanel : null,
	orderComboStoreUrl : null,//订单搜索URL
	orderType : null,//订单类型 1:合同 2:储备
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
//alert(this.orderComboStoreUrl);
		this.northPanel = new Ext.ftl.direct.ArrivalSearchForm();
		
		var _config = this.getConfig();
		Ext.apply(_config, {
			storeUrl : Ext.ftl.direct.ArrivalListGrid.STORE_URL,
			orderComboStoreUrl : this.orderComboStoreUrl,
			orderType : this.orderType
		})
		this.centerPanel = new Ext.ftl.direct.ArrivalListGrid(_config);
		
		Ext.ftl.direct.ArrivalIndexPanel.superclass.constructor.call(this, {
			width : Ext.getBody().getWidth(),
        	height : Ext.getBody().getHeight()-50,
            layout: 'border',
            listeners : {
            	'beforerender' : function() {
            		//this.centerPanel.render(Ext.getBody());
            	},
            	'render' : function() {
            		//监听搜索事件。
					this.northPanel.on({
						'search' : function(_form, _values) {
							var _grid = this.centerPanel;
							_grid.setSearchStr(_values);
							_grid.getStore().baseParams.year = _values.get('year');
							_grid.getStore().load({params : {start : 0, limit : PAGESIZE}});
						
						},
						scope : this
					})
					var _store = this.centerPanel.getStore();
					_store.on({
						'beforeload' : function() {
							_store.baseParams.orderType = this.orderType;
						},scope : this
					});
					var year = new Date().format('Y');
				    _store.baseParams.year = year;
					_store.load({params : {start : 0, limit : PAGESIZE, orderType : this.orderType}});
            	}
            },
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
                //title: '入库单管理',
                //contentEl: 'south',
                split: true,
                //width: 1000,
                height : 120,
                layout : 'fit',
                minSize: 140,
                maxSize: 120,
                collapsible: true,
                margins: '5 5 5 5',
                items : [this.northPanel]
                
            }, {
                region: 'center',
                //contentEl: 'grid',
                split: true,
                height: 100,
                minSize: 100,
                layout : 'fit',
                maxSize: 200,
                collapsible: true,
                //title: 'South',
                margins: '-5 5 5 5',
                items : [this.centerPanel]
            }]
        });
	},
	
	getConfig : function() {
		var moduleId = "";
		if(this.orderType == 0) {
			moduleId = "006005";
		}
		var modules = LoginInfor.modules
		var _configStr = "{";
		for(var i = 0; i < modules.length; i++) {
			var module = modules[i];
			if("006" == module.id) {
				var childModule = module.children;
				for(var j = 0; j < childModule.length; j++) {
					if(moduleId == childModule[j].id) {
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
})
//到货单Store URL
Ext.ftl.direct.ArrivalListGrid.STORE_URL = PATH + '/arrival/arrInfoManage.do?method=getArrInfoBySearch';
//到货单删除URL
Ext.ftl.direct.ArrivalListGrid.DELETE_URL = PATH + '/arrival/arrInfoManage.do?method=deleteArrInfo';
//到货单信息
Ext.ftl.direct.ARRIVAL_STORE_URL = PATH + '/arrival/arrInfoManage.do?method=getDirectArr';
//确认到货URL
Ext.ftl.direct.ArrivalListGrid.ARRIVAL_SUBMIT_URL = PATH + '/arrival/arrInfoManage.do?method=directArrAudit';

Ext.ftl.direct.ALLARRIVAL_URL = PATH + '/arrival/arrInfoManage.do?method=allArrival';















