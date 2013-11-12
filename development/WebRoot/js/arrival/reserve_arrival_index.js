Ext.namespace('Ext.ftl.subscribesearch');
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//预订订单
	Ext.ftl.subscribesearch.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getSubscribeOrder';
	var storeIndexPanel = new Ext.ftl.arrival.ArrivalIndexPanel({
		orderComboStoreUrl : Ext.ftl.subscribesearch.STORE_URL,
		orderType : 5
	});
		
	storeIndexPanel.render('arrival_subscribe_index');
	//storeIndexPanel.centerPanel.getColumnModel().setHidden(8, true);//隐藏合同编号
	Ext.ffc.ResizeManager.addResizeObject(storeIndexPanel);
})