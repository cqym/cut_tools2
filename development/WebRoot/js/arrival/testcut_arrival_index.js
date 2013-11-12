Ext.namespace('Ext.ftl.storeordersearch');
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//试刀订单
	Ext.ftl.storeordersearch.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getTestCutOrder';
	var storeIndexPanel = new Ext.ftl.arrival.ArrivalIndexPanel({
		orderComboStoreUrl : Ext.ftl.storeordersearch.STORE_URL,
		orderType : 6
	});
		
	storeIndexPanel.render('arrival_testcut_index');
	storeIndexPanel.centerPanel.getColumnModel().setHidden(7, true);//隐藏合同编号
	Ext.ffc.ResizeManager.addResizeObject(storeIndexPanel);
})