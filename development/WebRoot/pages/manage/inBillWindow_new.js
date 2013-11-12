function newInBill_new(f) {
	var p1 = new firebow.logistics.InBillByNoPlanPanel({
				dept : dept
			});
	p1.on("aftersave", onAftersave);
	var p2 = new firebow.logistics.InBillByContractPanel({
				dept : dept
			});
	p2.on("aftersave", onAftersave);
	var card1 = new Ext.TabPanel({
				title : "选择入库类型",
				region : 'center',
				activeTab : 0,
				items : [p1,p2]
			});
	function onAftersave() {
		win.close();
		f.call();
	}
	var win = new Ext.Window({
				renderTo : 'mainBody',
				title : '到货验收入库单',
				width : 950,
				height : 600,
				x: 10,
				y:10,
				layout : 'border',
				modal : true,
				buttons : [{
							text : '保存',
							handler : function(thiz) {
								var item=card1.getActiveTab();
								item.save();
							}
						}, {
							text : '取消',
							handler : function(thiz) {
								win.close();
							}
						}],
				listeners : {
					show : function() {
						var item=card1.getActiveTab();
						item.aftershow();
					}
				},
				items : [card1]
			});
	win.show();

}