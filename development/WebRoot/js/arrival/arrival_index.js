/**
 * 定义命名空间
 */
Ext.namespace('Ext.ftl.arrival');

//功能:　显示到货单明细窗口
Ext.ftl.arrival.onClickDetail = function(_id,_orderType) {
	var _isOrder = false;
	if(_orderType == 2 || _orderType == 5) {
		_isOrder = true;
	}
	var arrivalDetailWindow = new Ftl.DetailWindow({isOrder : _isOrder, orderType : _orderType});
	arrivalDetailWindow.buttons[1].text = '关闭';
	arrivalDetailWindow.buttons[0].hide();//隐藏确定按钮
	//arrivalDetailWindow.buttons[2].hide();//隐藏全部交清按钮
	
	var _grid = arrivalDetailWindow.centerPanel.arrivalProductGrid;
	_grid.getStore().baseParams.arrivalId = _id;
	arrivalDetailWindow.on('render', function() {
		var arrInfoForm = this.northPanel.arrivalInfoForm;
		arrInfoForm.findByType('textfield')[6].readOnly = true;
		arrInfoForm.findByType('datefield')[0].disabled = true;
		var _form = arrInfoForm.getForm();
		
		_form.load({
			url : Ext.ftl.arrival.ARRIVAL_STORE_URL,
			params : {
				arrivalId: _id
			}
		})
	})
	/*Ext.Ajax.request({
			url: Ext.ftl.arrival.ARRIVAL_STORE_URL,
			params: { arrivalId: _id },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
				if(responseArray.success == true){
					arrivalDetailWindow.show();
					//_grid.
					_grid.getTopToolbar().hide();//隐藏tbar
					arrivalDetailWindow.northPanel.arrivalInfoForm.setValues(new Ext.data.Record(responseArray.data));
				}
			}
		});*/
	arrivalDetailWindow.show();
	_grid.getStore().load();
	_grid.getTopToolbar().hide();//隐藏tbar
},

/**
 * 到货单状态下拉框
 * @class Ext.ftl.arrival.StatusCombo
 * @extends Ext.form.ComboBox
 */
Ext.ftl.arrival.StatusCombo = Ext.extend(Ext.form.ComboBox, {
	store : null,
	constructor : function(_cfg) {
		
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.store = new Ext.data.SimpleStore({
						fields : ['status', 'value'],
						data : [['全部',''],['入库 未确认', '0'],['入库 已确认', '1'],['入库 已作废', '2']]
					});
		Ext.ftl.arrival.StatusCombo.superclass.constructor.call(this, {
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
Ext.ftl.arrival.ArrivalSearchForm = Ext.extend(Ext.FormPanel, {
	statusField : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		var isShowQuoCode = this.orderType == 5 || this.orderType == 6 ? false : true;
		this.lableStyle_ = "font-size:9pt;text-align:right;width:85px";
		this.statusField = new Ext.ftl.arrival.StatusCombo({x:isShowQuoCode ? 1030 : 570,y:isShowQuoCode ? 3 : 33,width : 170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }});
	            
	    var _text = "入库单编号:";
		if(this.orderType == 5) {
			_text = "预订入库单编号:";
		} else if(this.orderType == 6) {
			_text = "试刀入库单编号:";
		}    
	        
		var _config = [
			//1
            {xtype:'label',text: _text,x:-8,y:5,style:this.lableStyle_},
            {xtype:'textfield', name: 'arrivalCode',x:92,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            {xtype:'label',text: '采购单号:',x:230,y:5,style:this.lableStyle_},
            {xtype:'textfield',name: 'orderCode',x:320,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
			{xtype:'label',text: '合同编号',x:480,y:5,style:this.lableStyle_},
			{xtype:'textfield',  name: 'contractCode',x:570,y:3,width:170},
			{xtype:'label',text: '状态:',x:710,y:5,style:this.lableStyle_},
            new Ext.ftl.arrival.StatusCombo({x:800,y : 3 ,width : 170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }}),
			{xtype:'label',text: '年度:',x:940,y:5,style:this.lableStyle_},
			new Ext.ffc.YearComboBox({x:1030,y:3, width:80}),

            {xtype:'label',text: '编制日期:',x:-8,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'beginDate', readOnly : true, format:'Y-m-d',emptyText:'',x:92,y:35, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            {xtype:'label',text: '至:',x:230,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'endDate', readOnly : true, format:'Y-m-d',emptyText:'',x:320,y:35, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            
			
			{xtype:'label',text: '备注:',x:480,y:35,style:this.lableStyle_},
			{xtype:'textfield',name: 'memo',x:570,y:33, width:170}
		];
		
		var _testcutconfig = [
			//1
            {xtype:'label',text: _text,x:-8,y:5,style:"font-size:9pt;text-align:right;width:95px"},
            {xtype:'textfield', name: 'arrivalCode',x:92,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            {xtype:'label',text: '采购单号:',x:230,y:5,style:this.lableStyle_},
            {xtype:'textfield',name: 'orderCode',x:320,y:3, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
	            
	         {xtype:'label',text: this.orderType == 5 ? '报价单编号:' : '试刀申请号:',x:480,y:5,style:"font-size:9pt;text-align:right;width:88px", hidden : isShowQuoCode},
            {xtype:'textfield', name: 'quotationCode',x:570,y:3, width:170,
            	enableKeyEvents: true, hidden : isShowQuoCode,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            {xtype:'label',text: '状态:',x:710,y:5,style:this.lableStyle_},
            new Ext.ftl.arrival.StatusCombo({x:800,y : 3 ,width : 170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }}),
			{xtype:'label',text: '年度:',x:940,y:5,style:this.lableStyle_},
			new Ext.ffc.YearComboBox({x:1030,y:3, width:80}),


            {xtype:'label',text: '编制日期:',x:-8,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'beginDate', readOnly : true, format:'Y-m-d',emptyText:'',x:92,y:35, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
            {xtype:'label',text: '至:',x:230,y:35,style:this.lableStyle_},
            {xtype:'datefield',name: 'endDate', readOnly : true, format:'Y-m-d',emptyText:'',x:320,y:35, width:170,
            	enableKeyEvents: true,
	            listeners : {
	            	'change' : function(field, newValue, oldValue) {
	            		this.fireEvent('search',this, this.getValues(), false);
	            	},scope : this
	            }},
			{xtype:'label',text: '备注:',x:480,y:35,style:this.lableStyle_},
			{xtype:'textfield',name: 'memo',x:570,y:33, width:170}
		];
		
		Ext.ftl.arrival.ArrivalSearchForm.superclass.constructor.call(this, {
			//width : 1000,
			height : 110,
	        labelAlign:'right',buttonAlign:'right', border : false,
	        frame:true,monitorValid:false,
	        layout : 'absolute',
	        items : isShowQuoCode ? _config : _testcutconfig,
	        bbar : ['->',{
	           		text : "搜  索",
	           		iconCls : 'icon-search',
	           		handler : function() {
	           			//发布search事件
	           			this.fireEvent('search',this, this.getValues(), true);
	           		},
	           		scope : this
           		},
           		'-',{
	           		text : "重  置",
	           		iconCls : 'icon-reset',
	           		handler : function () {
	           			this.getForm().reset();
	           		},scope : this
       		}],
       		keys : {
				key:Ext.EventObject.ENTER,
				fn:function(btn,e){
					this.fireEvent('search',this, this.getValues(), true);
				},
				scope : this
			}
	       
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
Ext.ftl.arrival.ArrivalListGrid = Ext.extend(Ext.grid.GridPanel, {
	storeUrl : null,
	OrderSearchWindow : null,
	orderComboStoreUrl : null,//订单搜索URL
	store : null,
	orderType : null,
	searchRecord : null,
	orderIdArray : null,//已经全部入库订单数组
	loadMarsk : null,
	isAddHide : true,isModifyHide : true,isDelHide : true,isSubmitHide : true,
	isDetailHide : true, isToExcelHide : true,isInvalidHide : true,exportListHide : true,
	exportDetailListHide:true,
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
					'deliveryDate','memo', 'orderInforId', 'id','editDateString','status','customerName',
					'quotationCode','quotationId','confirmUserName',
					'confirmDate']
			})
		this.sm = new Ext.grid.CheckboxSelectionModel();//复选框
		
		var _title = "入库单列表";
		var addButtonLabel = "新增入库单";
		var modifyButtonLabel = "修改入库单";
		var invalidButtonLabel = "作废入库单";
		var delButtonLabel = "删除入库单";
		var codeHeader = "入库单编号";
		var dateHeader = "入库日期";
		if(this.orderType == 5) {
			_title = "预订入库单列表";
			addButtonLabel = "新增预订入库单";
			modifyButtonLabel = "修改预订入库单";
			invalidButtonLabel = "作废预订入库单";
			delButtonLabel = "删除预订入库单";
			codeHeader = "预订入库单编号";
			dateHeader = "预订入库日期";
		} else if(this.orderType == 6) {
			_title = "试刀入库单列表";
			addButtonLabel = "新增试刀入库单";
			modifyButtonLabel = "修改试刀入库单";
			invalidButtonLabel = "作废试刀入库单";
			delButtonLabel = "删除试刀入库单";
			codeHeader = "试刀入库单编号";
			dateHeader = "试刀入库日期";
		}  
		
		Ext.ftl.arrival.ArrivalListGrid.superclass.constructor.call(this, {
			//title : _title,
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
				{header : codeHeader,dataIndex : 'arrivalCode',sortable: true},
				{header : '供应商名称' ,  dataIndex : 'supplierName',sortable: true},
				{header : '状态', dataIndex : 'status', renderer : this.renderStatus,sortable: true},
				
				{header : dateHeader, dataIndex : 'deliveryDate',sortable: true},
				{header : '采购单号', dataIndex : 'orderCode',sortable: true},
				{header : '合同编号', dataIndex : 'contractCode',sortable: true},
				{header : this.orderType == 5 ? '报价单编号' : '试刀申请编号', dataIndex : 'quotationCode',sortable: true, hidden : (this.orderType == 5 || this.orderType == 6) ? false : true},
				{header : '客户名称' ,  dataIndex : 'customerName',sortable: true},
				{header : '编制人', dataIndex : 'userName',sortable: true},
				{header : '编制时间', dataIndex : 'editDateString',sortable: true},
				{header : '确认人', dataIndex : 'confirmUserName',sortable: true},
				{header : '确认时间', dataIndex : 'confirmDate',sortable: true,
					renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
									if(!value) return '';
									if(typeof(value) == 'string') return value;
									else{
										return new Date(value.time).format('Y-m-d');
									}
				}},
				{header : '备注', dataIndex : 'memo',sortable: true},
				
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
            	text : addButtonLabel,
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
            	text : modifyButtonLabel,
            	iconCls:'icon-modify',
            	hidden : this.isModifyHide,
            	handler : function() {
            		fn : this.onModifySubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isModifyHide
			},{
            	text : invalidButtonLabel,
            	iconCls:'icon-modify',
            	hidden : this.isInvalidHide,
            	handler : function() {
            		fn : this.onInvalidSubmit();
            	},scope : this
            },{
				xtype:'tbseparator',
				hidden : this.isInvalidHide
			},{
            	text : delButtonLabel,
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
            	handler : function(_btn) {
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
				hidden : this.exportListHide
			},/*{
				text:'导出列表',
				hidden : this.exportListHide,
				iconCls:'icon-excel',
				listeners: {
					'click' : function(){
						var _searchStr = "";
						var rec = this.ownerCt.ownerCt.northPanel.getValues();
						if(!Ext.isEmpty( rec)) {
							_searchStr = Ext.util.JSON.encode(rec.data);
						}
						window.open(PATH + '/arrival/excelAction.do?method=arrivalListToExcel&orderType=' + this.orderType + '&searchStr=' + _searchStr);
					},scope : this
		 		}
			},{
				xtype:'tbseparator',
				hidden : this.exportListHide
			},*/{
				text:'导出列表',
				hidden : this.exportListHide,
				iconCls:'icon-excel',
				listeners: {
					'click' : function(){
						var _searchArr = [];
						var record = this.ownerCt.ownerCt.northPanel.getValues();
						if(!Ext.isEmpty(record)) {
							var data = record.data;
							for(var i in data){
								_searchArr.push(i + "=" + data[i]);
							}
						}
						if(record.get('beginDate') == ''){
						        Ext.MessageBox.show({title:'系统提示',msg: '请指定编制开始日期!',buttons: Ext.MessageBox.OK,scope : this});
						    return ;
						}
						if(record.get('endDate') == ''){
						    Ext.MessageBox.show({title:'系统提示',msg: '请指定编制结束日期!',buttons: Ext.MessageBox.OK,scope : this});
						    return ;
						}
						_searchArr.push('orderType=' + this.getOrderType());
						new Ext.ffc.ExpTempleteSelectWindow({
							templeteType:'26',
							exportDataMethod:function(tempId){
								window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertListExcel&" + _searchArr.join('&') + "&tempId=" + tempId);
							}
						}).show();

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
			case 2 :
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
	getOrderType :function(){
	    var orderType = this.orderType;
		orderType = orderType * 1;
		if(orderType == 1){//合同入库，则要查合同订单，加工订单
			orderType = "1,3";
		}else if(orderType == 2){//储备入库，则要查，产品储备订单，材料储备订单
			orderType = "2,4";
		}else if(orderType == 5){//预订入库，则要查，预订订单，预订加工订单
			orderType = "5,7";
		}else if(orderType == 6){//试刀入库，则要查，试刀订单，试刀加工订单
			orderType = "6,8";
		}
		return orderType;
	},
	onAddSubmit : function() {
		//实例化一个搜索窗口
		/*this.OrderSearchWindow = new Ext.ftl.search.OrderSearchWindow({
			orderComboStoreUrl : this.orderComboStoreUrl,
			orderType : this.orderType
		});
        this.OrderSearchWindow.show();
        this.OrderSearchWindow.on('onsubmitclick',function(_form, _r) {
        	this.getStore().reload();
        },this)*/
		var orderType = this.getOrderType();
		
		var that = this;
		Ext.ffc.select_orders(function(arr,select_quotations_win){
			if(!arr || arr.length == 0) return;
			var idsArr = [];
			var orderCode = arr[0].get("orderCode");
			var orderType = arr[0].get("orderType");
			var orderId = arr[0].get("id");
			var isOrderHidden = false;//1为采购订单为false不隐藏, 2为储备订单：true
			//orderType 1 合同入库 2 储备入库 5 预订入库 6 试刀入库
			if(this.orderType == 2) {
				isOrderHidden = true;
			}
			
			var arrivalAddWindow = new Ext.ftl.arrival.add.ArrivalAddWindow({
				orderId : orderId,
				isOrder : isOrderHidden,
				orderType : orderType,
				hideMethod : function(){
				    that.getStore().reload();
				}
			});

			/*arrivalAddWindow.on('onsubmit',function(_form, _record) {
				this.fireEvent("onsubmitclick",_form, _record);
			},this)
			*/
			var _arrInfoform = arrivalAddWindow.northPanel.arrivalInfoForm;
			arrivalAddWindow.show();
			_arrInfoform.setValues(arr[0]);
		},orderType);
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
				if(records[i].get('status') != 1) {
					Ext.MessageBox.show({
						title:'系统提示',
						msg: '请只选择已确认的入库单!',
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
						msg: '请只选择未确认入库的入库单，进行操作!',
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
			Ext.ftl.arrival.onClickDetail(selRecord.id, this.orderType);
		}
	},
	
	onExcelClick : function() {
		try {
			if(this.getSelections().length > 1) {
				Ext.MessageBox.alert('错误提示', '请选择一条记录进行操作！');
				return;
			}
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
			var _templeteType = 16;
			if(this.orderType*1 == 1){//合同
				_templeteType = 16;
			}else if(this.orderType*1 == 2){//储备
				_templeteType = 30;
			}else if(this.orderType*1 == 3){//
				_templeteType = 16;
			}else if(this.orderType*1 == 4){
				_templeteType = 30;	
			}else if(this.orderType*1 == 5){//预订
				_templeteType = 17;
			}else if(this.orderType*1 == 6){//试刀
				_templeteType = 18;
			}
			var arrId = _record.get('id');
			
			new Ext.ffc.ExpTempleteSelectWindow({
				templeteType:_templeteType,
				exportDataMethod:function(tempId){
					window.open(PATH + "/dataExp/ExpTempleteAction.do?m=expertExcel&busId=" + arrId + "&tempId=" + tempId);
				}
			}).show();
			//window.open(PATH + '/arrival/excelAction.do?method=toExcel&arrId=' + arrId);
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
		//如果合同编号为空视为储备订单到货单，否则为合同订单到货单
		var isOrderHidden = false;
		if(Ext.isEmpty(_record.get('contractCode'))) {
			isOrderHidden = true;
		}
		
        this.modifyWindow = new Ftl.ModifyWindow({
	       orderId : _record.get('orderInforId'),
	       isOrder : isOrderHidden,
			orderType : this.orderType
	    });
	    //this.modifyWindow.buttons[2].hide();//隐藏全部交清按钮
	    this.modifyWindow.centerPanel.arrivalProductGrid.getStore().baseParams.arrivalId = _record.get('id');
	    this.modifyWindow.show();
	    this.modifyWindow.on('onsubmit', function() {
	    	this.getStore().reload();
	    },this)
	    //取得orderType
	    Ext.Ajax.request({
			url: PATH + '/arrival/getOrderByType.do?method=getOrderByCode',
			params: { orderCode : _record.get('orderCode') },
			success : function(response) {
				var responseArray = Ext.util.JSON.decode(response.responseText); 
				_record.set("orderType", responseArray.orderType);
				this.modifyWindow.northPanel.arrivalInfoForm.setValues(_record);
			},scope :this
		})
	    
	   
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
				url : Ext.ftl.arrival.ArrivalListGrid.DELETE_URL,
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
				url : Ext.ftl.arrival.ArrivalListGrid.ARRIVAL_SUBMIT_URL,
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
			var _invalidUrl = Ext.ftl.arrival.ArrivalListGrid.ARRIVAL_INVALID_URL;
			if(this.orderType == 2) {
				//储备入库作废
				_invalidUrl = Ext.ftl.arrival.ArrivalListGrid.STOCK_ARRIVAL_INVALID_URL
			}
			
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
				url : _invalidUrl,
				params : { arrInfoId : Ext.encode(ids) },
				success : function(response) {
					var responseArray = Ext.util.JSON.decode(response.responseText);
					if (responseArray.success == true) {
						this.getStore().reload();
						var idArray = responseArray.data;
						var _codeStr = "";
						if(!Ext.isEmpty(idArray)) {
							_codeStr = this.buildCodeStr(idArray);
							
						}
						Ext.Msg.show({
							title : '成功提示',
							width : 300,
							msg : responseArray.msg + "<br>" + _codeStr,
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
	
	buildCodeStr : function(idArray) {
		var _codeStr = "[";
		for(var i = 0; i < idArray.length; i++) {
			var _record = this.store.getById(idArray[i]);
			
			if(i == idArray.length-1) {
				_codeStr += _record.get('arrivalCode') + "]";
			} else {
				_codeStr += _record.get('arrivalCode') + "<br>";
			}
		} 
		return _codeStr;
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
				url : Ext.ftl.arrival.ALLARRIVAL_URL,
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
Ext.ftl.arrival.ArrivalIndexPanel = Ext.extend(Ext.Panel, {
	northPanel : null,
	centerPanel : null,
	orderComboStoreUrl : null,//订单搜索URL
	orderType : null,//订单类型 1:合同 2:储备 5预定订单,6试刀订单,7预定加工,8试刀加工
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
//alert(this.orderComboStoreUrl);
		this.northPanel = new Ext.ftl.arrival.ArrivalSearchForm({orderType : this.orderType});
		
		var _config = this.getConfig();
		Ext.apply(_config, {
			storeUrl : Ext.ftl.arrival.ArrivalListGrid.STORE_URL,
			orderComboStoreUrl : this.orderComboStoreUrl,
			orderType : this.orderType
		})
		this.centerPanel = new Ext.ftl.arrival.ArrivalListGrid(_config);
		
		var _title = "入库单管理";
		if(this.orderType == 5) {
			_title = "预订入库单管理";
		} else if(this.orderType == 6) {
			_title = "试刀入库单管理";
		}
		
		Ext.ftl.arrival.ArrivalIndexPanel.superclass.constructor.call(this, {
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
						'search' : function(_form, _values, isPost) {
							var _grid = this.centerPanel;
							_grid.setSearchStr(_values);
							
							if(isPost) {
								_grid.getStore().baseParams.year = _values.get('year');
								_grid.getStore().load({params : {start : 0, limit : PAGESIZE}});
							}
						},
						scope : this
					})
					var _store = this.centerPanel.getStore();
					_store.on({
						'beforeload' : function() {
							//_store.baseParams.year = year;
							_store.baseParams.orderType = this.orderType;
						},scope : this
					})
					var year = new Date().format('Y');
				    _store.baseParams.year = year;
					_store.load({params : {start : 0, limit : PAGESIZE}});
            	}
            },
            items: [
            {
                region: 'north',
                iconCls:'icon-grid',
                //title: _title,
                //contentEl: 'south',
                split: true,
                //width: 1000,
                height : this.orderType == 5 || this.orderType == 6 ? 140 : 120,
                layout : 'fit',
                minSize: 150,
                maxSize: this.orderType == 5 || this.orderType == 6 ? 140 : 120,
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
		if(this.orderType == 1) {
			moduleId = "006001";
		} else if(this.orderType == 2) {
			moduleId = "006002";
		} else if(this.orderType==5) {
			moduleId = "006003";
		} else if(this.orderType==6) {
			moduleId = "006004";
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
Ext.ftl.arrival.ArrivalListGrid.STORE_URL = PATH + '/arrival/arrInfoManage.do?method=getArrInfoBySearch';
//到货单删除URL
Ext.ftl.arrival.ArrivalListGrid.DELETE_URL = PATH + '/arrival/arrInfoManage.do?method=deleteArrInfo';
//到货单信息
Ext.ftl.arrival.ARRIVAL_STORE_URL = PATH + '/arrival/arrInfoManage.do?method=getArrInfoById';
//确认到货URL
Ext.ftl.arrival.ArrivalListGrid.ARRIVAL_SUBMIT_URL = PATH + '/arrival/arrInfoManage.do?method=arrivalSubmit';

Ext.ftl.arrival.ALLARRIVAL_URL = PATH + '/arrival/arrInfoManage.do?method=allArrival';

//作废入库单URL
Ext.ftl.arrival.ArrivalListGrid.ARRIVAL_INVALID_URL = PATH + '/arrival/arrInfoManage.do?method=invalidSubmit';

//储备入库作废URL
Ext.ftl.arrival.ArrivalListGrid.STOCK_ARRIVAL_INVALID_URL = PATH + '/arrival/arrInfoManage.do?method=stockInvalidSubmit';