var Load=new Object();//实例化对像
	Load.onLoadJs=function(jsName,callback){
		Ext.Ajax.request({
			url:jsName,
			success:function(response){
				eval(response.responseText);
				if(callback instanceof Function ){
			        callback();
				}
			},
			failure:function(d){
				alert("出错了:"+d.responseText);
			},
			jsonData:{},
			method:'GET'
		});
	}
function loadBusinessInfor(url,id,auditType,grid){
	Load.onLoadJs(PATH + url,function(){
		//try{
			//alert(Ext.zhj.QuoInfoDetailWindow);
			var qdw = new DetailWindow({
				    _id:id,
				    auditType : auditType,
				    grid : grid
				});
			
			qdw.on("beforerender", function() {
				this.setId(id);
				this.setAuditType(auditType);
				this.setGrid(grid);
			})
			qdw.show();
		//}catch(e){
		//    alert(e);
		//}
		
	});
}