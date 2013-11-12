Ext.namespace('Ext.ftl.storeordersearch');
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//储备订单
	Ext.ftl.storeordersearch.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getReserveOrder';
	var storeIndexPanel = new Ext.ftl.arrival.ArrivalIndexPanel({
		orderComboStoreUrl : Ext.ftl.storeordersearch.STORE_URL,
		orderType : 2
	});
		
	storeIndexPanel.render('arrival_store_index');
	storeIndexPanel.centerPanel.getColumnModel().setHidden(7, true);//隐藏合同编号
	storeIndexPanel.centerPanel.getColumnModel().setHidden(9, true);//隐藏客户名称
	Ext.ffc.ResizeManager.addResizeObject(storeIndexPanel);
})