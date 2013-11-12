Ext.namespace('Ext.ftl.directarrival');
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	Ext.BLANK_IMAGE_URL = PATH + '/extjs/resources/images/default/s.gif';
	//储备订单
	Ext.ftl.directarrival.STORE_URL = PATH + '/arrival/getOrderByType.do?method=getReserveOrder';
	var directIndexPanel = new Ext.ftl.direct.ArrivalIndexPanel({
		//orderComboStoreUrl : Ext.ftl.directarrival.STORE_URL,
		orderType : 0
	});
		
	directIndexPanel.render('direct_arrival_index');
	//directIndexPanel.centerPanel.getColumnModel().setHidden(8, true);//隐藏合同编号
})