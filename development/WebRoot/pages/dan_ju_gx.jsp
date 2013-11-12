<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<canvas id='mycanvas' width='1400' height="1000" onclick="inputCode()"></canvas>
<input type="text" name="code" value="12222">
<SCRIPT LANGUAGE="JavaScript">
<!--
HTML5 = {
	cov : null,
	context : null
};

HTML5.init = function(canvasId){
	HTML5.canvas = document.getElementById(canvasId);
	HTML5.context = HTML5.canvas.getContext("2d")
}

HTML5.Model = {};
HTML5.Draw = function(){};

HTML5.Model.Rect = function(config){
    for(var i in config){
		this[i] = config[i];
	}
};

HTML5.Model.Rect.prototype.getLeftLinkPoint = function(){
	var x = this.x;
	var y = this.y + this.height / 2.0;
	return {x:x,y:y};
}

HTML5.Model.Rect.prototype.getRightLinkPoint = function(){
	var x = this.x + this.width;
	var y = this.y + this.height / 2.0;
	return {x:x,y:y};
}

HTML5.Model.Rect.prototype.getTextStartPoint = function(){
	var x = this.x + 10;
	var y = this.y + this.height / 1.5;
	return {x:x,y:y};
}

HTML5.Model.Rect.prototype.draw = function(){
	var cxt = HTML5.context; 
    cxt.strokeRect(this.x,this.y,this.width,this.height); 
	cxt.font = "14px";  
	var txtXY = this.getTextStartPoint();
	cxt.strokeText(this.txt, txtXY.x, txtXY.y);  
}

HTML5.Model.Rect.prototype.linkToRect = function(rect){
	var ctx = HTML5.context; 
    var step = 5;
	var tempXY = rect.getLeftLinkPoint();
	var nx = tempXY.x,ny = tempXY.y;
	var fromXY = this.getRightLinkPoint();
	ctx.beginPath();
	ctx.moveTo(fromXY.x,fromXY.y);
	ctx.lineTo(nx,ny);
	ctx.stroke();
	ctx.moveTo(nx-step,ny-step);
	ctx.lineTo(nx,ny);
	ctx.lineTo(nx-step,ny+step);
	ctx.lineTo(nx-step,ny-step);
	ctx.fill();
}

HTML5.Draw.prototype.drawLine = function (x,y,nx,ny){
	var ctx = HTML5.context;
	ctx.beginPath();
	ctx.moveTo(x,y);
	ctx.lineTo(nx,ny);
	ctx.stroke();
}

HTML5.Draw.prototype.drawText = function (txt,x,y){
	var cxt = HTML5.context;
	//cxt.strokeStyle="blue"; 
	//cxt.font = "14px";  
	cxt.strokeText(txt, x, y );  
}

HTML5.Draw.prototype.clean = function (){
	var cxt = HTML5.context;
	cxt.fillStyle="#ffffff"; 
	//cxt.font = "14px";  
	cxt.fillRect(0,0,1000,1000);  
}

HTML5.init('mycanvas');
var draw = new HTML5.Draw();
function requestDanJuGxData(code){
	Ext.Ajax.request({
		method: "post",
		url:  '<%=path%>/DanJuGuanXiSearchAction.do?m=requestDanJu&code=' + code,
		success: function(response){
			eval("var temp = " + response.responseText);
			if(temp.msg){
				Ext.Msg.alert("消息", temp.msg);
				return ;
			}
			var quoList = temp.quo;
			drawData(quoList,0,'quotationCode');
			var conList = temp.con;
			drawData(conList,200,'contractCode');
			var orderList = temp.order;
			drawData(orderList,400,'orderCode');
			var arriList = temp.arri;
			drawData(arriList,600,'arrivalCode');
			var outList = temp.out;
			drawData(outList,800,'outStockCode');
			var delList = temp.del;
			drawData(delList,1000,'deliveryCode');
		}
	});	
}
function drawData(list,xx,codeName){
	for(var i = 0;i < list.length;i++){
		var rect = new HTML5.Model.Rect({
			txt : list[i][codeName],
			x : 25 + xx,
			y : 120 + i*40,
			width : 150,
			height : 30
		});
		rect.draw();
	}
}

function init(){
	draw.drawLine(0,100,window.screen.width,100);//画横线
	var rectArray = [];
	var dataTxt = ['报价单','合同','采购订单','入库单','出库单','交货单'];
	for(var i = 0 ;i < 6;i++){
		draw.drawLine(i*200,50,i*200,1000);//画竖线
		draw.drawText(dataTxt[i],i*200+20,90);//标示模块名称
		/*var rect = new HTML5.Model.Rect({
			txt : '1679-BJWW-A0019-110930',
			x : i*200 + 25,
			y : 100,
			width : 150,
			height : 30
		});
		*/
		//rectArray.push(rect);
		//rect.draw();
		if(i > 0){
		   // rectArray[i - 1].linkToRect(rectArray[i]);
		} 
	}
}

init();

function inputCode(){
    draw.clean();
	init();
	//requestDanJuGxData();
	ftlWin.show();
}

Ext.ftl.DanJuSearchWindow = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if(_cfg == null) {
			_cfg = {};
		}
		Ext.apply(this, _cfg);
		this.items = new Ext.FormPanel({
			labelWidth: 75, 
			frame:true,
			region: 'center',
			bodyStyle:'padding:5px 5px 0',
			defaults: {width: 200},
			defaultType: 'textfield',
			items: [{
					fieldLabel: '单据编号',
					name: 'code',
					allowBlank:false
				}
			]
		});
		Ext.ftl.DanJuSearchWindow.superclass.constructor.call(this, {
			title: "单据搜索",  
			width:350,  
			height:115,  
			constrainHeader : true,
			closeAction:'hide',
			layout:"form",
			buttonAlign : 'center',
			buttons : [{
				text : "搜索",
				handler : function() {
					fn : this.onSubmitClick();
				},scope : this
			},{
				text : "关闭",
				handler : function() {
					this.hide();
				},
				scope : this
			}]
		})
		
		this.addEvents("onsubmitclick");
	},
	onSubmitClick : function() {
		var codeObj = this.findByType("textfield")[0].getEl().dom;
		    requestDanJuGxData(codeObj.value);
		    this.hide();
		
	}
})
var ftlWin = new Ext.ftl.DanJuSearchWindow();
ftlWin.show();
//-->
</SCRIPT>