Ext.namespace("Ext.ftl.image");
Ftlmage = Ext.ftl.image;

Ftlmage.ImageViewStore = Ext.extend(Ext.data.JsonStore, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		Ftlmage.ImageViewStore.superclass.constructor.call(this, {
			url: '#',
	        root: 'slave',
	        fields: ['id', 'fileName', 'path', {name:'fileSize', type: 'float'}, {name:'uploadTimeStr'}]
		})
	}
})

Ftlmage.ImageDataView = Ext.extend(Ext.DataView, {
	store : null,
	tpl : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		
		this.tpl = new Ext.XTemplate(
			'<div><img src="' + PATH + this.store.getAt(0).data['path'] + '"></div>'
		),
		Ftlmage.ImageDataView.superclass.constructor.call(this, {
			store: this.store,
            tpl: this.tpl,
            autoHeight:true,
            multiSelect: true,
            overClass:'x-view-over',
            itemSelector:'div',
            emptyText: '无图片信息'
		})
	},
	
	formatUrl : function(path) {
		var startIndex = path.indexOf('webapps')
		var endIndex = path.length;
		var resultUrl = path.substring(startIndex+7, endIndex);
		return resultUrl.split('\\').join('\/'); 
	}
})

Ftlmage.ImageViewWindow = Ext.extend(Ext.Window, {
	imageDataView : null,
	panel : null,
	slaveStore : null,
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.imageDataView = new Ftlmage.ImageDataView({store : this.slaveStore});
		Ftlmage.ImageViewWindow.superclass.constructor.call(this, {
			title : '图片预览',
			frame:true,
	        width:535,
	        autoHeight:true,
	        layout:'fit',
	        closeAction : 'hide',
	        constrainHeader : true,
	        maximizable : true,
	        modal : true,
	        pageX : 450,
	        pageY : 70,
	        items : this.imageDataView
		})
	}
})

