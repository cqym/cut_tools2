Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget = 'side';
	//报价单类型0 普通 1 项目 2 零兴 3 预订 4 试刀
	var generalQuo = new Ext.ftl.generalQuo.QuoIndexPanel({quoType : 4});
	generalQuo.render('index_ct_testCut');
	Ext.ffc.ResizeManager.addResizeObject(generalQuo);
})