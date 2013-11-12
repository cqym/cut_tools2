Ext.namespace('Ext.ftl.ordersearch');
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//采购订单
	Ext.ftl.ordersearch.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getStockOrder';
	var arrivalIndexPanel = new Ext.ftl.arrival.ArrivalIndexPanel({
		orderComboStoreUrl : Ext.ftl.ordersearch.STORE_URL,
		orderType : 1
	});
		
	arrivalIndexPanel.render('arrival_index');
	Ext.ffc.ResizeManager.addResizeObject(arrivalIndexPanel);	
})
