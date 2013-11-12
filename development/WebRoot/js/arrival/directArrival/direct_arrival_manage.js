/**
 * 定义命名空间
 */
Ext.namespace('Ext.ftl.direct.add');
Ext.namespace('Ext.ftl.direct.modify');

//命名空间别名
Direct = Ext.ftl.direct.modify;
/**
库存类型 0 产品库，-1材料库
*/
Ext.ftl.direct.KuCunType = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.SimpleStore({
						fields : ['arrivalType', 'value'],
						data : [['产品库', '0'],['材料库', '-1']]
					});
		Ext.ftl.direct.StatusCombo.superclass.constructor.call(this, {
			fieldLabel : '状态',
			hiddenName : 'arrivalType',
			mode : 'local',
			displayField : 'arrivalType',
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
 * 到货单信息Form
 * @class Ext.ftl.arrival.add.ArrivalInfoForm
 * @extends Ext.FormPanel
 */
Ext.ftl.direct.add.ArrivalInfoForm = Ext.extend(Ext.FormPanel, {
	isOrder : null,
	supIdField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.supIdField = new Ext.form.Hidden({
			fieldLabel: '供应商ID', readOnly : true, name: 'supplierId',anchor:'45%',hidden : true, hideLabel : true
		})
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		//合同
		var Config = [
            	//1
            	{xtype:'label',text: '入库单号:',x:0,y:5,style:this.lableStyle_},
            	{xtype:'textfield',name: 'arrivalCode',readOnly : true, x:90,y:3, width:170},
            	/*{xtype:'label',text: '订单编号:',x:0,y:125,style:this.lableStyle_,hidden : true},
            	{xtype:'textfield',name: 'orderCode',readOnly : true, x:90,y:123, width:170,hidden : true},*/
            	//2
            	{xtype:'label',text: '入库日期:',x:0,y:35,style:this.lableStyle_},
            	{xtype:'datefield',name: 'deliveryDate', format:'Y-m-d',allowBlank : false,x:90,y:33, width:170},
            	{xtype:'label',text: '供应商名称:',x:250,y:5,style:this.lableStyle_},
            	{xtype:'textfield',name: 'supplierName', x:340,y:3, width:170,
            	listeners: {
					'focus' : function(field){
						var win = new supplierWin4Direct();
						win.supplier_grid.store.load({params:{start:0,limit:15}});
						win.on({
							'onsubmit' : function(record) {
								field.setValue(record.get('supplierName'));
								this.supIdField.setValue(record.get('supplierId'));
							},scope : this
						})
						win.show();
					},scope : this
				}},
            	//3
            	{xtype:'label',text: '入库性质:',x:0,y:65,style:this.lableStyle_},
            	{xtype:'textfield',name: 'orderTypeStr',readOnly : true, x:90,y:63, width:170},
            	{xtype:'label',text: '库存类型:',x:250,y:65,style:this.lableStyle_},
				new Ext.ftl.direct.KuCunType({x:340,y:63, width:170}),
            	//{xtype:'textfield',name: 'contractCode',readOnly : true, x:340,y:63, width:170},
            	//4
            	{xtype:'label',text: '制单人:',x:250,y:35,style:this.lableStyle_},
            	{xtype:'textfield',name: 'userName',readOnly : true, x:340,y:33, width:170},
            	/*{xtype:'label',text: '客户名称:',x:250,y:95,style:this.lableStyle_,hidden : true},
            	{xtype:'textfield',name: 'customerName',readOnly : true, x:340,y:93, width:170,hidden : true},*/
            	//5
            	{xtype:'label',text: '备　　注',x:0,y:95,style:this.lableStyle_},
				{xtype:'textfield',  name: 'memo',x:90,y:93,width:420},
				
				//hidden
				//{xtype:'hidden',fieldLabel: 'arrivalType', readOnly : true, name: 'arrivalType',anchor:'45%',hidden : true, hideLabel : true},
                this.supIdField,
                {xtype:'hidden',fieldLabel: 'orderId_arrid',name: 'id',hidden : true, hideLabel : true}
            ]
		Ext.ftl.direct.add.ArrivalInfoForm.superclass.constructor.call(this, {
			width : this['width'] == null ? 1000 : this['width'],
			heigth : 100,
            labelAlign:'right',buttonAlign:'right',bodyStyle:'padding:0px;',
            frame:true,monitorValid:false,
            layout: 'absolute',
            items : Config
		})
	},
	
	/**
	 * 设置表单的值
	 * @param {} _r
	 */
	setValues : function(_r) {
		//this.getForm().loadRecord(_r);
		var _record = new Ext.data.Record(this.getForm().getValues())
		//_record.set('id', _r.get('id'));
		_record.set('deliveryDate', new Date());
		_record.set('orderTypeStr', this.changeOrderType(0));
		_record.set('userName', LoginInfor.user.trueName);
		_record.set('arrivalType', 0);
		//_record.set('supplierName', _r.get("supplierName"));
		//alert(Ext.encode(_record));
		this.getForm().loadRecord(_record);
	},
	
	/**
	 * 丛远程加载数据
	 * @param {} _id
	 */
	loadData : function(_id) {
		var _form = this.getForm();
		_form.load({
			url : PATH + '/arrival/arrInfoManage.do?method=getDirectArr',
			params : {
				arrivalId: _id
			},
			success : function(_form, _action) {
				var record = this.getValues();
				record.set('orderTypeStr',this.changeOrderType(_action.result.data.arrivalType));
				this.getForm().loadRecord(record);
			},scope :this
		})
	},
	
	/**
	 * 获取表单的值
	 * @return {}
	 */
	getValues : function() {
		return new Ext.data.Record(this.getForm().getValues());
	},
	
	/**
	 * 校验表单输入值是否合法
	 * @return {} 如果表单在客户端校验合法，返回true 
	 */
	validator : function() {
		return this.getForm().isValid();
	},
	
	changeOrderType : function(value) {
		switch (value) {
			case  1:
			return "合同入库";
			case 3 :
			return "合同入库";
			case  2:
			return "储备入库";
			case 4 :
			return "储备入库";
			case 5 :
			return "预订入库";
			case 6 :
				return "试刀入库";
			case 0 :
				return "直接入库";
		}
	}
})

//------------------------------------Grid列表------------------------------------

/**
 * 表格列
 * @class Ext.ftl.arrival.add.ArrivalProductGridColumn
 * @extends Ext.grid.ColumnModel
 */
Ext.ftl.direct.add.ArrivalProductGridColumn = Ext.extend(Ext.grid.ColumnModel, {
	readOnly : null,
	sm : null,
	numbField : null,
	priceNumbField : null,
	parentCt : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		this.numbField = new Ext.form.NumberField({
		   gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,
           readOnly : this.readOnly == null ? false : this.readOnly,
           maxValue: 100000,
           enableKeyEvent : true,
           validator : function(_value) {
           		if(_value >= 0) {
           			return true;
           		} else {
           			return '入库数量必须大于0';
           		}
           	  
           },
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		}
           }
  		})
  		
  		this.priceNumbField = new Ext.form.NumberField({
		   gridObj : this.parentCt,
           allowBlank: false,
           allowNegative: false,
           readOnly : this.readOnly == null ? false : this.readOnly,
           maxValue: 100000,
           enableKeyEvent : true,
           validator : function(_value) {
           		if(_value >= 0) {
           			return true;
           		} else {
           			return '单价必须大于0';
           		}
           	  
           },
           listeners : {
           		'specialkey' : Ext.ftl.gridEditorkeyMove,
           		'focus' : function(field) {
           			field.selectText();
           		}
           }
  		})
		Ext.ftl.direct.add.ArrivalProductGridColumn.superclass.constructor.call(this, [this.sm,
			new Ext.grid.RowNumberer({
								header : '序号',
								width : 35
			}),{
	            header:'名称',width:100,dataIndex:'productName',sortable: true
	        },{
	        	header:'牌号',width:130,resizable : true,dataIndex:'brandCode',sortable: true
			},{
	            header:'货品编号',width:100,dataIndex:'productCode',sortable: true
	        },{
	            header:'库区/库位',width:80,dataIndex:'productPosition',sortable: true,
				editor : new Ext.form.TextField({
	            	readOnly : this.readOnly == null ? false : this.readOnly,
	            	gridObj : this.parentCt,
	            	listeners : {
	            		'specialkey' : Ext.ftl.gridEditorkeyMove,
	            		'focus' : function(field) {
		           			field.selectText();
		           		}
	            	}
          		})
	        },{
	            header:'库存代码',width:80,dataIndex:'reserveCode',sortable: true,
				editor : new Ext.form.TextField({
	            	readOnly : this.readOnly == null ? false : this.readOnly,
	            	gridObj : this.parentCt,
	            	listeners : {
	            		'specialkey' : Ext.ftl.gridEditorkeyMove,
	            		'focus' : function(field) {
		           			field.selectText();
		           		}
	            	}
          		})
	        },{
	            header:'计量单位',width:75,dataIndex:'productUnit',sortable: true
	        },{
	            header:'累计入库数量',width:100,dataIndex:'hasArrivalAmount',sortable: true
	        },{
	            header:'本次入库数量',width:100,dataIndex:'arrivalAmount',editor : this.numbField,sortable: true
	        },{ 
				header:'单价',width:80,dataIndex:'price',editor : this.priceNumbField,sortable: true
			},{ 
				header:'货品金额',width:80,dataIndex:'productMoney',sortable: true
			},{
	            header:'品牌',width:80,dataIndex:'productBrand',sortable: true
	        },{
	            header:'备注',width:80,dataIndex:'memo',sortable: true,
	            editor : new Ext.form.TextField({
	            	readOnly : this.readOnly == null ? false : this.readOnly,
	            	gridObj : this.parentCt,
	            	listeners : {
	            		'specialkey' : Ext.ftl.gridEditorkeyMove,
	            		'focus' : function(field) {
		           			field.selectText();
		           		}
	            	}
          		})
	        },{
	            header:'实际交付数量',width:0,dataIndex:'actualAmount',hidden : true
	        },{
	            header:'id',width:80,dataIndex:'id',hidden : true
	        },{
	            header:'toolsId',width:80,dataIndex:'toolsId',hidden : true
	        },{
	            header:'productArrivalInforId',width:80,dataIndex:'productArrivalInforId',hidden : true
	        }])
	}
})

//-----------------------------------Grid------------------------------------
/**
 * 到货单产品GirdPanel 编制到货时StoreUrl为加载订单产品，修改到货及到货明细时StoreUrl加载到货产品
 * @class Ext.ftl.arrival.add.ArrivalProductGrid
 * @extends Ext.grid.EditorGridPanel
 */
Ext.ftl.direct.add.ArrivalProductGrid = Ext.extend(Ext.grid.EditorGridPanel, {
	cm : null,
	storeUrl : null,
	store : null,
	enableHdMenu : false,
	orderId : null,//订单ID
	addButtonHide : null,//添加按钮是否隐藏
	deleteButtonHide : null,//删除按钮是否隐藏
	sm : null,//复选框
	inserted : null,//新添加产品数组
	//selModel : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.sm = new Ext.grid.CheckboxSelectionModel();
		this.cm = new Ext.ftl.direct.add.ArrivalProductGridColumn({
			readOnly : this.addButtonHide,
			sm : this.sm,
			parentCt : this
		});
		//this.selModel = new Ext.grid.CellSelectionModel(),
		
		this.store = new Ext.data.JsonStore({
			url : this.storeUrl,
			//autoLoad : true,
			//totalProperty : 'totalProperty',
			root : 'orderProducts',
			fields : ['brandCode','productCode','productName','actualAmount',
					'arrivalAmount','productUnit','price', 'productMoney', 
					'toolsId','memo','id','productArrivalInforId','hasArrivalAmount','productBrand','productPosition','reserveCode']
		})
		this.inserted = [];
		Ext.ftl.direct.add.ArrivalProductGrid.superclass.constructor.call(this, {
			store : this.store,
			cm : this.cm,
			sm : this.sm,
			width : 1250,
			height : 350,
			clicksToEdit:1,//单击修改
			bodyStyle:'width:100%',
			iconCls:'icon-grid',
			//frame : true,
			stripeRows :true,
			selModel : new Ext.grid.CellSelectionModel(),
			
			viewConfig: {   
	            //forceFit:true,
	            //autoFill : true,
	            deferEmptyText : false,
	            //enableRowBody : false,
	            emptyText : '无产品信息！'
        	},
          	listeners : {
          		'afteredit' : function(e) {
          			fn : this.calculate(e);
          		}

          	},
          	tbar : [{
          		text : '添加产品',
          		iconCls:'icon-add',
          		hidden : this['addButtonHide'] == null ? false : this['addButtonHide'],
          		handler : function() {
          			fn : this.onAddSubmit();
          		},scope : this
          	},'-',{
          		text : '删除产品',
          		iconCls:'icon-delete',
          		hidden : this['deleteButtonHide'] == null ? false : this['deleteButtonHide'],
          		handler : function() {
          			fn : this.onDeleteSubmit();
          		},scope : this
          	},'-',{
          		text : '全部入库',
          		iconCls:'icon-add',
          		//hidden : this['deleteButtonHide'] == null ? false : this['deleteButtonHide'],
          		handler : function() {
          			fn : this.onAllArrival();
          		},scope : this
          	}]
		})
	},
	
	setOrderId : function(_orderId) {
		this.orderId = _orderId;
	},
	/**
	 * 获取所选取的记录。如果没有选择，抛出一个异常。
	 * @return {} Record
	 */
	getSelected : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择产品后再进行操作！');
		} else {
			return _sm.getSelected();
		}
	},
	
	/**
	 * 返回所有选中的记录
	 * @return {} Record数组
	 */
	getSelections : function() {
		var _sm = this.getSelectionModel();
		
		if(!_sm.hasSelection()) {
			throw Error('请选择产品后再进行操作！');
		} else {
			return _sm.getSelections();
		}
	},
	
	remove : function() {
		try {
			var _record = this.getSelected();
			this.getStore().remove(_record);
		}catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择入库单再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
		
	},
	
	/** 
	 * 批量删除记录 删除记录之前先删除 inserted 数组 及 被修改的记录数组
	 * @param {} _records
	 */
	removeRecords : function(_records) {
		for(var i = 0; i < _records.length; i++) {
			this.inserted.remove(_records[i]);
			this.getStore().getModifiedRecords().remove(_records[i]);
			this.remove(_records[i]);
		}
	},
	
	onAddSubmit : function() {
		fn : this.handleAdd();
	},
	
	/**
	 * 校验所添加的产品是否已经在到货单列表中，如果在则返回重复数据。
	 * @param {} _r 准备添加到到货单列表的产品数组
	 * @return {} 重复数据数组
	 */
	validator : function(_r) {
		var _temp = [];
		this.getStore().each(function(_record) {
			for(var i = 0; i < _r.length; i++) {
				if(_r[i].get('id') == _record.get('id') || _r[i].get('id') == _record.get('orderDetailId')) {
					_temp.push(_r[i]);
				}
			}
		})
		return _temp;
	},
	
	/**
	 * 校验到货数量小于0 或者 大于采购数量-已经到货数量 返回false
	 * @param {} _selRecords
	 * @return {Boolean}
	 */
	validatorAmount : function(_selRecords) {
		
		for(var j = 0; j < _selRecords.length; j++) {
			if(_selRecords[j].get('arrivalAmount') <= 0) {
				var idx = this.getStore().indexOf(_selRecords[j]);//获取行号
				var row = this.getView().getRow(idx);//根据行号获取行对象
				var element = Ext.get(row);
				element.removeClass('x-grid3-row-selected');
				element.addClass('red-row');
					Ext.Msg.show({
							title:'信息提示',
							msg: '入库数量须大于0！',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
					return false;
			} else if(_selRecords[j].get('arrivalAmount') > _selRecords[j].get('orderAmount')-_selRecords[j].get('hasArrivalAmount')){
				var idx = this.getStore().indexOf(_selRecords[j]);//获取行号
				var row = this.getView().getRow(idx);//根据行号获取行对象
				var element = Ext.get(row);
				//element.removeClass('x-grid3-row-selected');
				element.addClass('red-row');
				Ext.Msg.show({
							title:'信息提示',
							msg: '入库数量不得大于,合同数量与已经入库数量差值！',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
					return false;
			}
		}
		
		return true
	},
	
	handleAdd : function() {
		var productListWindow = new Quoproduct.ProductWindow();
		productListWindow.on({
			'onclick' : function(node) {
				//Ext.ffc.util.debug(node);
				var record = new Ext.data.Record();
				Ext.apply(record.data, node);
				record.set('price', 0);
				this.store.add(record);
			},scope : this
		})
		productListWindow.show();
	},
	
	onDeleteSubmit : function() {
		try {
			this.getSelected();
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '确定要删除当前所选产品？',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleDelete,//若以此种方法调用，需要指定scope
				scope : this
			});
		} catch(_err) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _err.description == null ? '请选择产品再进行操作！' : _err.description,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	//全部入库
	onAllArrival : function() {
		try {
			var records = this.getSelections();
			Ext.MessageBox.show({
				title:'系统提示',
				msg: '确定所选产品全部入库？',
				buttons: Ext.MessageBox.OKCANCEL,
				fn: this.handleArrival,//若以此种方法调用，需要指定scope
				scope : this
			});
		} catch(_e) {
			Ext.Msg.show({
				title : '信息提示',
				msg : _e.message,
				width : 260,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
		}
	},
	
	handleArrival : function(_btn) {
		if(_btn == 'ok') {
			var _records = this.getSelections();
			for(var i = 0; i < _records.length; i++) {
				var _record = _records[i];
				_record.set('arrivalAmount', _record.get('orderAmount')-_record.get('hasArrivalAmount'));
			}
		}
	},
	
	handleDelete : function(_btn) {
		if(_btn == 'ok') {
			var _records = this.getSelections();
			var _temp = [];//前台直接删除
			var _temp2 = [];//需要提交后台删除后，再进行删除
			for(var i = 0; i < _records.length; i++) {
				var arrInfoId = _records[i].get('productArrivalInforId');
				if(Ext.isEmpty(arrInfoId)) {
					_temp.push(_records[i])
				} else {
					_temp2.push(_records[i])
				}
			}
			
			if(_temp.length > 0) {
				this.removeRecords(_temp);
			}
			
			if(_temp2.length > 0) {
				var idArray = [];
				for(var i = 0; i < _temp2.length; i++) {
					_data = {};
					idArray.push(Ext.apply(_data, {id : _temp2[i].get('id')}))
				}
				var _id = Ext.encode(idArray);
				//删除到货单产品信息
				Ext.Ajax.request({
					url : Ext.ftl.direct.add.ArrivalProductGrid.DELETE_URL,
					params : { arrProId : _id },
					success : function(response) {
						var responseArray = Ext.util.JSON.decode(response.responseText);
						if (responseArray.success == true) {
							Ext.Msg.show({
										title : '成功提示',
										msg : responseArray.msg,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.INFO
									});
							this.removeRecords(_temp2);
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
	},
	
	/**
	 * 计算产品金额
	 * @param {} e
	 */
	calculate : function(e) {
		//如果不是到货数量直接返回，否则才执行计算逻辑
		if(e.field != 'arrivalAmount' && e.field != 'price')
			return;
		if(e.field == 'arrivalAmount') {
			var record = e.record;
			var productMoney = record.get('price') * e.value;
			record.set('productMoney', productMoney);
		}
		
		if(e.field == 'price') {
			var record = e.record;
			var productMoney = record.get('arrivalAmount') * e.value;
			record.set('productMoney', productMoney.toFixed(2));
		}
	},

	getModifyRecords : function() {
		var _m = this.getStore().getModifiedRecords();
		var _temp = [];
		if(_m.length == 0) {
			Ext.Msg.show({
				title : '系统提示',
				msg : '请输入产品入库数量！',
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.INFO
			});
			return _temp;
		} else if(_m.length > 0) {
			if(!this.validatorAmount(_m))
				return _temp;
		}
		
		for(var i = 0; i < _m.length; i++) {
			var _data = {};
			if(_m[i].data.arrivalAmount == 0)
				continue
			_temp.push(Ext.apply(_data,  _m[i].data))
		}
		return _temp;
	},
	
	getSelectedRecords : function() {
		var _selRecords = this.getSelections();
		var _temp = [];
		for(var i = 0; i < _selRecords.length; i++) {
			var _data = {};
			_temp.push(Ext.apply(_data,  _selRecords[i].data))
		}
		
		return _temp;
	},
	
	/**
	 * 校验修改的数据是否是新添加的。
	 * @param {} _r 修改后的数据
	 * @return {} 是 返回true
	 */
	hasRepeat : function(_r) {
		var flag = false;
		var _temp = this.inserted;
		for(var i = 0; i < _temp.length; i++) {
			if(_temp[i].get('id') == _r.get('id'))
				flag = true;
		}
		
		return flag;
	},
	
	/**
	 * 获取修改的值
	 * @return {}
	 */
	getChangeValues : function() {
		var _m = this.getStore().getModifiedRecords();
		if(_m.length > 0) {
			if(!this.validatorAmount(_m))
				return false;
		}
		if(this.inserted.length > 0) {
			if(!this.validatorAmount(this.inserted));
				return false;
		}
		
		var _temp = [];
		
		for(var i = 0; i < _m.length; i++) {
			if(_m[i].get('id') == "" || this.hasRepeat(_m[i])) {
				continue;
			}
			
			var _data = {};
			var _j = '';
		
			for(_j in _m[i].modified) {
				_data[_j] = _m[i].get(_j);
			}
			
			_temp.push(Ext.apply(_data, {id : _m[i].get('id')}))
		}
		
		for(var j = 0; j < this.inserted.length; j++) {
			_temp.push(this.inserted[j].data);
		}
		
		return _temp;
	}
})

/**
 * 到货单信息容器Panel
 * @class Ext.ftl.arrival.add.NorthPanel
 * @extends Ext.Panel
 */
Ext.ftl.direct.add.NorthPanel = Ext.extend(Ext.Panel, {
	arrivalInfoForm : null,
	isOrder : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.arrivalInfoForm = new Ext.ftl.direct.add.ArrivalInfoForm({
			isOrder : this.isOrder
		});
		Ext.ftl.direct.add.NorthPanel.superclass.constructor.call(this, {
			region: 'north',
            iconCls:'icon-grid',
            title: this.isOrder == false ? '合同订单入库单信息' : '储备订单入库单信息',
            //contentEl: 'south',
            split: true,
            width: 200,
            layout: 'fit',
            height : 160,
            minSize: 160,
            maxSize: 160,
            collapsible: true,
            margins: '5 5 5 5',
            items : [this.arrivalInfoForm]
		})
	}
})

/**
 * 到货产品树容器
 * @class Ext.ftl.arrival.add.CenterPanel
 * @extends Ext.Panel
 */
Ext.ftl.direct.add.CenterPanel = Ext.extend(Ext.Panel, {
	arrivalProductGrid : null,
	loaderUrl : null,
	addButtonHide : null,//添加按钮是否隐藏
	deleteButtonHide : null,//删除按钮是否隐藏
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.arrivalProductGrid = new Ext.ftl.direct.add.ArrivalProductGrid({
			storeUrl : this['loaderUrl'] == null ? "#" : this['loaderUrl'],
			addButtonHide : this.addButtonHide,//添加按钮是否隐藏
			deleteButtonHide : this.deleteButtonHide//删除按钮是否隐藏
		});
		
		Ext.ftl.direct.add.CenterPanel.superclass.constructor.call(this, {
			region: 'center',
            //contentEl: 'grid',
            split: true,
            height: 100,
            minSize: 100,
            maxSize: 200,
            layout : 'fit',
            collapsible: true,
            //title: 'South',
            margins: '-5 5 5 5',
            items : [this.arrivalProductGrid]
		})
	}
})

//--------------------------------添加窗口----------------------------------------
/**
 * 到货单添加Window
 * @class Ext.ftl.arrival.add.ArrivalAddWindow
 * @extends Ext.Window
 */
Ext.ftl.direct.add.ArrivalAddWindow = Ext.extend(Ext.Window, {
	northPanel : null,
	centerPanel : null,
	orderId : null,
	submitUrl : null,
	loaderUrl : null,//修改时付值
	isOrder : null,//通过此值判断到货单是否显示合同编号，true 显示 false 不显示
	addButtonHide : null,//添加按钮是否隐藏
	deleteButtonHide : null,//删除按钮是否隐藏
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.northPanel = new Ext.ftl.direct.add.NorthPanel({
			isOrder : this.isOrder
		});
		this.centerPanel = new Ext.ftl.direct.add.CenterPanel( {
			loaderUrl : this.loaderUrl,
			addButtonHide : this.addButtonHide,//添加按钮是否隐藏
			deleteButtonHide : this.deleteButtonHide//删除按钮是否隐藏
		});
//alert("this.submitUrl = " + this.submitUrl);
//alert("this.loaderUrl = " + this.loaderUrl);
		Ext.ftl.direct.add.ArrivalAddWindow.superclass.constructor.call(this, {
			title: this['title'] == null ? "添加入库单" : this['title'],  
			width:1000,  
			height:600,  
			plain:true,
			modal : true,
			closable : true,
			constrainHeader : true,
			maximizable : true,
			closeAction:'hide',
			layout:"border",  
			buttons : [{
				text : "保存",
				hidden : this.addButtonHide,
				handler : function() {
					var _grid = this.centerPanel.arrivalProductGrid;
					var _form = this.northPanel.arrivalInfoForm;
					//alert(Ext.encode(_grid.getSelected().data));return;
					if(_grid.getStore().getCount() == 0) {
						Ext.Msg.show({
							title:'信息提示',
							msg: '请添加入库单产品!',
							buttons: Ext.Msg.OK,
							icon: Ext.MessageBox.INFO
						});
						return;
					}
					if(!_form.validator()) {
						return;
					}
					var _r = _form.getValues();
					//alert(_r.get('arrivalCode'));
					var parmStr1 = null;
					
					if(this['submitUrl'] == null) {
						//var _selRecords = _grid.getSelections();
						var _modifyRecords = _grid.getModifyRecords();//_grid.getModifyRecords();
						if(_modifyRecords.length == 0) {
							return;
						}
						parmStr1 = Ext.encode(_modifyRecords);
					} else {
						if(!_grid.getChangeValues())
							return;
						parmStr1 = Ext.encode(_grid.getChangeValues());
						
					}
					
					var parmStr2 = Ext.encode(this.northPanel.arrivalInfoForm.getValues().data);
					//alert(parmStr1);return;
					//保存到货单信息
					Ext.Ajax.request({
						url: this['submitUrl'] == null ? Ext.ftl.direct.add.ArrivalAddWindow.SUBMIT_URL : this['submitUrl'],
						params: { arrProduct: parmStr1, arrForm : parmStr2 },
						success : function(response) {
							var responseArray = Ext.util.JSON.decode(response.responseText); 
												   	
							if(responseArray.success == true){
								Ext.Msg.show({
									title:'成功提示',
									msg: responseArray.msg,
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.INFO
								});
								_r.set('id', responseArray.id);
								this.fireEvent("onsubmit",this, _r);//用于更新到货单列表事件
								this.centerPanel.arrivalProductGrid.inserted = [];
								this.hide();
							} else {
								Ext.Msg.show({
									title:'错误提示',
									msg: responseArray.msg,
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.ERROR
								});
							} 
						},
						scope : this //this 为ArrivalAddWindow本身
					});
					
				},
				scope : this
			},{
				text : "取消",
				handler : function() {
					this.centerPanel.arrivalProductGrid.inserted = [];//清空新添加产品数组
					this.hide();
				},
				scope : this
			}],
			listeners : {
				'show' : function() {
					this.centerPanel.arrivalProductGrid.setOrderId(this.orderId);
					this.centerPanel.arrivalProductGrid.store.load({params : {orderId : this.orderId}});
				}
			},
			items : [this.northPanel, this.centerPanel]
		})
		
		this.addEvents("onsubmit");
	},
	
	onAllArrival : function() {
		Ext.MessageBox.show({
			title:'系统提示',
			msg: '所有产品已入库，是否确定产品全部交清？',
			buttons: Ext.MessageBox.OKCANCEL,
			fn: this.handlerAllArrival,//若以此种方法调用，需要指定scope
			scope : this
		});
	},
	
	handlerAllArrival : function(btn) {
		if(btn == 'ok') {
		  var _form = this.northPanel.arrivalInfoForm;
		  var _orderId = _form.getValues().get('orderId');
		  
			//全部到货后，修改订单状态
			Ext.Ajax.request({
				url : Ext.ftl.direct.add.ArrivalAddWindow.ALLARRIVAL_URL,
				params : { orderId : _orderId },
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

//-----------------------------------修改窗口--------------------------------------
/**
 * 到货单修改窗口，继承自添加窗口
 * @class Ftl.ModifyWindow 到货单修改窗口
 * @extends Ext.ftl.arrival.add.ArrivalAddWindow 添加窗口
 */
Direct.ModifyWindow = Ext.extend(Ext.ftl.direct.add.ArrivalAddWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {}
		}
		Ext.apply(this, _cfg);
		Direct.ModifyWindow.superclass.constructor.call(this, {
			title : '修改入库单',
			submitUrl : Direct.SUBMIT_URL,
			loaderUrl : Direct.LOADER_URL
			
		})
	}
})
//-----------------------------------明细窗口--------------------------------------
/**
 * 到货单明细窗口
 * @class Ftl.DetailWindow
 * @extends Ext.ftl.arrival.add.ArrivalAddWindow
 */
Direct.DetailWindow = Ext.extend(Ext.ftl.direct.add.ArrivalAddWindow, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Direct.DetailWindow.superclass.constructor.call(this, {
			title : '入库单明细',
			addButtonHide : true,
			loaderUrl : Direct.LOADER_VIEW_URL
		})
	}
})
//----------------------------------订单产品列表窗口---------------------------------------


//修改到货单明细URL
Direct.LOADER_URL = PATH + '/arrival/arrInfoManage.do?method=getDirectArrDetail';
//查看明细到货单明细URL
Direct.LOADER_VIEW_URL = PATH + '/arrival/arrInfoManage.do?method=getDirectArrDetail';
//到货单修改URL
Direct.SUBMIT_URL = PATH + '/arrival/arrInfoManage.do?method=modifyArrival';
//提交后台URL（保存到货单信息）
Ext.ftl.direct.add.ArrivalAddWindow.SUBMIT_URL = PATH + '/arrival/arrInfoManage.do?method=addArrInfo';
//删除到货单产品
Ext.ftl.direct.add.ArrivalProductGrid.DELETE_URL = PATH + '/arrival/arrInfoManage.do?method=deleteArrPro';
//全部交清
Ext.ftl.direct.add.ArrivalAddWindow.ALLARRIVAL_URL = PATH + '/arrival/arrInfoManage.do?method=allArrival';




























