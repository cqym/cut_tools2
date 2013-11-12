orderStatusCombox =	Ext.extend(Ext.form.ComboBox, {
		store : null,
		constructor : function(_cfg) {
			if(_cfg == null) {
				_cfg = {};
			}
			Ext.apply(this, _cfg);
			this.store = new Ext.data.SimpleStore({
							fields : ['status', 'value'],
							data : [['编制',''],['编制', '0'],['待审批', '1'],['审批通过', '2'],
							['审批退回', '3'],['提交订货', '6'], ['提交合同', '4'], ['已经生成合同', '5'], ['已作废', '-1']]
						});
			StatusCombox.superclass.constructor.call(this, {
						fieldLabel : '状态',
						hiddenName : 'status',
						mode : 'local',
						displayField : 'status',
						valueField : 'value',
						//anchor:'90%',
						readOnly : true,
						frame : true,
						triggerAction : 'all',
						value : '0',
						store : this.store
			})
		}
	})
 

	orderUrgentLevelCombox =Ext.extend(Ext.form.ComboBox, {
		store : null,
		constructor : function() {
		this.store = new Ext.data.SimpleStore({
						fields : ['urgentLevel', 'abbr'],
						data : [['0', '一般'],['1', '紧急']]
					});
		orderUrgentLevelCombox.superclass.constructor.call(this, {
				fieldLabel: '紧急程度',
				hiddenName:'urgentLevel',
				mode : 'local',
				valueField:'urgentLevel',
				displayField:'abbr',
				typeAhead: true,
				mode: 'local',
				anchor:'100%',
				style: 'margin-top:1px;',
				readOnly:true,
				disabled:true
			})
		}
	});

	function checkOrderInfor(formV)
	{
		var bDate = Date.parseDate(formV.orderDate,'Y-m-d');
		var eDate = Date.parseDate(formV.deliveryDate,'Y-m-d');
		if(formV.currencyId != undefined && formV.currencyId.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择币别！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.urgentLevel != undefined && formV.urgentLevel.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择紧急程度！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.orderDate != undefined && formV.orderDate.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择订货日期！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.deliveryDate != undefined && formV.deliveryDate.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择交货日期！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.taxRate != undefined && formV.taxRate.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择税率！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.overallRebate != undefined && formV.overallRebate.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请填写整体折扣！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.overallRebate != undefined && (formV.overallRebate<0||formV.overallRebate>=100))
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '整体折扣应大于等于0，小于100!',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.companyName != undefined && formV.companyName.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '请选择卖方名称！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.qualityStandard != undefined && formV.qualityStandard.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '质量标准不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.deliveryAddressType != undefined && formV.deliveryAddressType.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '交提货地点不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.trafficMode != undefined && formV.trafficMode.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '运输方式及费用不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.closingAccountMode != undefined && formV.closingAccountMode.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '结算方式不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.otherConvention != undefined && formV.otherConvention.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '其他约定事项不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.defaultDuty != undefined && formV.defaultDuty.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '合同违约责任不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		if(formV.effectConditions != undefined && formV.effectConditions.length<1)
		{
			Ext.Msg.show({
			   title: '系统提示',
			   msg: '合同生效条件不能为空！',
			   width: 300,
			   buttons: Ext.MessageBox.OK,
			   icon: Ext.MessageBox.INFO
			});
			return false;
		}
		return true;
	}

	function checkOrderDetail(childrenArray,form)
	{
		for(var i=0;i<childrenArray.length;i++)
		{

			var bDate = Date.parseDate(form.orderDate,'Y-m-d');
			var eDate = Date.parseDate(form.deliveryDate,'Y-m-d');
			var nDate = childrenArray[i].deliveryDate;
			if(childrenArray[i].deliveryDate == undefined)
			{
				Ext.Msg.show({
					   title: '系统提示',
					   msg: '产品交货日期不能为空!',
					   width: 300,
					   buttons: Ext.MessageBox.OK,
					   icon: Ext.MessageBox.INFO
					});
					return false;
			}
			else if(childrenArray[i].deliveryDate.length != undefined)
			{
				nDate = Date.parseDate(childrenArray[i].deliveryDate.substring(0,10),'Y-m-d');
			}
			else{
				childrenArray[i].deliveryDate = childrenArray[i].deliveryDate.format('Y-m-d');
			}
			if(nDate == null || nDate=='')
			{
				Ext.Msg.show({
				   title: '系统提示',
				   msg: '产品交货日期不能为空!',
				   width: 300,
				   buttons: Ext.MessageBox.OK,
				   icon: Ext.MessageBox.INFO
				});
				return false;
			}
			if(childrenArray[i].orderAmount * 1 == 0){
			    Ext.Msg.show({
				   title: '系统提示',
				   msg: '采购数量不能为0!',
				   width: 300,
				   buttons: Ext.MessageBox.OK,
				   icon: Ext.MessageBox.INFO
				});
				return false;
			}
		}
		return true;
	}


Ext.ffc.showOrderAccountInforWin = function(contractId,contractCode,customerName,customerCode,finalMoney,hidden){
		new Ext.ffc.ContractAccountWindow({contractType:1,contractId:contractId,contractCode:contractCode,customerName:customerName,customerCode:customerCode,finalMoney:finalMoney,hidden:hidden,titleDef:'付款'}).show();
}
Ext.ffc.showOrderInvoiceInforWin = function (contractInforId,contractCode,customerCode,hidden){
		Ext.Ajax.request({
			method: "post",
			params: null,
			url: PATH + "/contract/invoiceAction.do?ffc=getInvoiceInfor&contractInforId=" + contractInforId + "&customerCode=" + customerCode + "&invoiceType=1",
			success: function(response){

				eval(response.responseText);
				if(invoiceInfor == null){
					invoiceInfor = {memo:'',id:''};
				}
				new Ext.ffc.InvoiceEditWindow({
					invoiceMainInfor : {
						'contractId' : contractInforId,
						'contractCode'  : contractCode,
						'memo' : invoiceInfor.memo,
						'id' : invoiceInfor.id,
						'isHidden' : hidden,
						'invoiceType' : 1//0合同发票1订单发票
					},
					customerInfor : customerInfor
				}).show();	
			}
		});
}

function requestTotalDatas(params,form,callBackMethod){
		Ext.Ajax.request({
			url: PATH + '/contractOrder/OrderTotalMoneysAction.do?mydear=getOrderTotalMoneys',
			params: params,
			success : function(response) {
				 eval("var rt=" + response.responseText);
				 callBackMethod(rt);
			}
		});
}

//选择税率
function change_rate(form)
{
	var rat = form.getValues().taxRate;
	var overallRebate = form.getValues().overallRebate;
	if(rat == '' || rat == null || isNaN(rat))
	{
		rat = 0;
	}
	if(overallRebate == null)
	{
		overallRebate = 0;
	}
	if(form.getValues().productMoney.length<1)
	{
		form.setValues({productMoney:0}); 
	}
	var productMoney = form.getValues().productMoney;
	var totalMoney = productMoney*1+rat*productMoney*1;
	form.setValues({totalMoney:totalMoney==0?totalMoney:totalMoney.toFixed(2)});
	form.setValues({finalMoney:(totalMoney*1*(1- overallRebate/100))==0?(totalMoney*1*(1- overallRebate/100)):(totalMoney*1*(1- overallRebate/100)).toFixed(2)});
	var finalMoney = form.getValues().finalMoney;
	form.setValues({taxMoney:(totalMoney - productMoney)==0?(totalMoney - productMoney):(totalMoney - productMoney).toFixed(2)});
}

//填写最终金额
function change_overallRebate(form,newValue)
{
	var overallRebate = newValue;
	if(overallRebate == null)
	{
	overallRebate = 0;
	}
	var totalMoney = form.getValues().totalMoney;
	form.setValues({finalMoney:(totalMoney*1*(1- overallRebate/100))==0?(totalMoney*1*(1- overallRebate/100)):(totalMoney*1*(1- overallRebate/100)).toFixed(2)});
	var productMoney = form.getValues().productMoney;
	form.setValues({taxMoney:(totalMoney - productMoney)==0?(totalMoney - productMoney):(totalMoney - productMoney).toFixed(2)});
}

//删除详细
function delete_detail(form,store){
	var prom = 0;
	store.each(function(record){
		prom += record.get('price')*record.get('orderAmount')*1;
//		prom += record.get('productMoney')*1;
	});
	var rat = form.getValues().taxRate;
	var overallRebate = form.getValues().overallRebate;
	if(rat == '' || rat == null || isNaN(rat))
	{
		rat = 0;
	}
	if(overallRebate == null)
	{
		overallRebate = 0;
	}
	var totalMoney = prom*rat*1 + prom*1;
	form.setValues({productMoney:prom==0?prom:prom.toFixed(2)});
	form.setValues({totalMoney:totalMoney==0?totalMoney:totalMoney.toFixed(2)});
	form.setValues({finalMoney:(totalMoney*1*(1- overallRebate/100))==0?(totalMoney*1*(1- overallRebate/100)):(totalMoney*1*(1- overallRebate/100)).toFixed(2)});
//	var productMoney = form.getValues().productMoney;
	form.setValues({taxMoney:(totalMoney - prom)==0?(totalMoney - prom):(totalMoney - prom).toFixed(2)});
}


/**非标品查看附件窗口**/
onSlaveClick = function(_id) {
	var slaveWindow = new Slave.SlaveManageWindow({busId : _id, busType : 1});
	slaveWindow.show();
}
Ext.ffc.orderCurrenyRateChangeCallBack = function(combox,rate) {
	alert(1111);
	var win = combox.ownerCt.ownerCt.ownerCt;
	var grid = win.grid;
	var store = grid.getStore();
	var sumPmoney = 0;
	store.each(function(record){
		var _price = record.get('price') * 1;
		var _productMoney = record.get('productMoney') * 1;
		var _orderAmount = record.get('orderAmount') * 1;
		if(!Ext.isNumber(_price)) {
			_price = 0;
		}
		if(!Ext.isNumber(_productMoney)) {
			_productMoney = 0;
		}
		if(!Ext.isNumber(_orderAmount)) {
			_orderAmount = 0;
		}
		_price = Math.round(_price * rate*100)/100;
		_productMoney = _price * _orderAmount;
		record.set('price',_price);
		record.set('productMoney',_productMoney.toFixed(2));
		sumPmoney += _productMoney;
	});
	var form = win.form.getForm();
	var fValues = form.getValues();
	sumPmoney = Math.round(sumPmoney * 100) / 100;
	form.setValues({"productMoney":sumPmoney});
	var _taxRate = fValues.taxRate;
	var _taxMoney = Math.round(_taxRate * sumPmoney*100)/100;
	form.setValues({"taxMoney":_taxMoney});
	form.setValues({"totalMoney":(_taxMoney + sumPmoney).toFixed(2)});
	var _overallRebate = fValues.overallRebate;
	var _finalMoney = (_taxMoney + sumPmoney) * ((100 -_overallRebate)/100) 
	form.setValues({"finalMoney":_finalMoney.toFixed(2)});
}

//对客户欠款金额进行校验
function checkCustomerAccount(config){
	Ext.Ajax.request({
		method: "post",
		params: { 'searchStr' : '{customerCode:\"' + config.customerCode + '\"}'},
		url: PATH + '/generalQuo/getCustomerAction.do',
		success: function(response){
			eval("var record=" + response.responseText);
			var cus = record.customer[0];
			if(cus.qianKuanOverDate != null && cus.qianKuanDay > cus.reputationPeriod){
				var qianKuanJinE = cus.sumContractMoney - cus.sumAccount;
				if(cus.reputationMoney == 0){
					Ext.Msg.show({
						title:'信息提示',
						msg: '该客户欠款为' + qianKuanJinE + '元<br/>',
						buttons: Ext.Msg.OK,
						width : 520,
						icon: Ext.MessageBox.INFO
					});
				}else{
					Ext.Msg.show({
						title:'信息提示',
						msg: '【信誉额度】为' + cus.reputationMoney + '元,【信誉有效期】为' + cus.reputationPeriod + '天<br/>'
							  + '该客户欠款为' + qianKuanJinE + '元<br/>'
							  + '欠款已超【信誉额度】' + cus.qianKuanDay + '天，已超过【信誉有效期】，不允许再做单据<br/>'
							  + '详情请咨询系统管理员!',
						buttons: Ext.Msg.OK,
						width : 520,
						icon: Ext.MessageBox.INFO
					});
				}
				return ;
			}

			if(cus.qianKuanOverDate != null && cus.qianKuanDay <= cus.reputationPeriod) {
				var qianKuanJinE = cus.sumContractMoney - cus.sumAccount;
				if(cus.reputationMoney == 0){
					Ext.Msg.show({
						title:'信息提示',
						msg: '该客户欠款为' + qianKuanJinE + '元<br/>',
						buttons: Ext.Msg.OK,
						width : 520,
						icon: Ext.MessageBox.INFO
					});
				}else{
					Ext.Msg.show({
						title:'信息提示',
						msg: '【信誉额度】为' + cus.reputationMoney + '元,【信誉有效期】为' + cus.reputationPeriod + '天<br/>'
							  + '该客户欠款为' + qianKuanJinE + '元<br/>'
							  + '欠款已超【信誉额度】' + cus.qianKuanDay + '天，但未超过【信誉有效期】，只可做报价单不允许订货，但可以交货<br/>'
							  + '详情请咨询系统管理员!',
						buttons: Ext.Msg.OK,
						width : 520,
						icon: Ext.MessageBox.INFO
					});
				}
				return ;
			}

			config.callBackMethod({
			  callBackMethod:function(){
					
				}
			});
		}
	});
}

function loadOrderWindowById(param){
			var win = this;
		    Ext.Ajax.request({
				method: "post",
				params: { id : param.loadDataParam.orderId,method:'orderViewById'},
				url: PATH + "/purchaseOrder/PurchaseOrderViewAction.do",
				success: function(response){
					  eval("var orderInfor =" + response.responseText);
					  var winInitParam = {orderInfor:orderInfor};
					  Ext.apply(winInitParam,param);
					  var conEditWin = new Ext.ffc.PurchaseOrder.editWin(winInitParam);
				  conEditWin.show();	
				}
			});
}

function loadOrderWindowByConsult(param){//参照 获取数据
			var win = this;
			var dataparam = param.loadDataParam;
		  Ext.Ajax.request({
							method: "post",
							params: { 
								contractId : dataparam.contractId,
								quotationId : dataparam.quotationId,
								supplierId : dataparam.supplierId,
								brand : dataparam.brand,
								planId: dataparam.planId,
								leaf:   dataparam.leaf,
								toolsTypeName: dataparam.toolsTypeName,
								method:dataparam.loadActionMethod},
							url: PATH + "/purchaseOrder/PurchaseOrderEditAction.do",
							success: function(response){
								  eval("var orderInfor =" + response.responseText);
								  
								  var conEditWin = new Ext.ffc.PurchaseOrder.editWin({
																			   title:param.title,
																				 orderInfor:orderInfor,
																				 auditButtonHiden:true,
																				 SaveBtnHidden : false,
																				 listGrid:param.listGrid
																		});
									conEditWin.show();
							}
			});
}

function loadOrderAuditWindowById(param){
					Ext.Ajax.request({
							method: "post",
							params: { id : param.loadDataParam.orderId,method:'orderViewById'},
							url: PATH + "/purchaseOrder/PurchaseOrderViewAction.do",
							success: function(response){
								  eval("var orderInfor =" + response.responseText);
								  var winInitParam = {orderInfor:orderInfor};
								  Ext.apply(winInitParam,param);
								  var conEditWin = new Ext.ffc.PurchaseOrder.AuditWin(winInitParam);
						      conEditWin.show();	
							}
						});
}

function getOrderTypeTitlePart(type){
	if(type == 1 || type == 3){
	    return "合同";
	}else if(type == 5 || type == 6 || type == 7 || type == 8){
	    return "报价单";
	}
	return '';
}

function isOrderTypeColHidden(orderType,colName){
	if(orderType == 1 || orderType == 3){
		if(colName == 'quotationCode'){
		    return true;	
		}
	}else if(orderType == 5 || orderType == 6 || orderType == 7 || orderType == 8){
		if(colName == 'proSortName' || colName == 'contractCode'){
	     return true;	
		}
  }else if(orderType == 2 || orderType == 4){
     if(colName == 'proSortName' || colName == 'contractDeliveryDate' || colName == 'remainAmount' 
     || colName == 'contractAmount' || colName == 'serialNumber' || colName == 'projectCode' 
    || colName == 'ownContactPerson' || colName == 'quotationCode' || colName == 'contractCode'){
	     return true;	
		 }
  }
	return false;
}

function isNotReserveOrder(orderType){
	return orderType != 2;
}

function isCaiGou(orderType){
	if(orderType == 1 || orderType == 5 || orderType == 6){
		return true;
	}
	return false;
}

function isJiaGong(orderType){
	if(orderType == 3 || orderType == 7 || orderType == 8){
		return true;
	}
	return false;
}


function isContractOrder(orderType){
	return orderType == 1 || orderType == 3;
}

