

/** 
IsInteger: 用于判断一个数字型字符串是否为整形， 
还可判断是否是正整数或负整数，返回值为true或false 
string: 需要判断的字符串 
sign: 若要判断是正负数是使用，是正用´+´，负´-´，不用则表示不作判断 
Author: PPDJ 
*/

function IsInteger(string ,sign){ 
	var integer; 
	if ((sign!=null) && (sign!='-') && (sign!='+')){ 
		alert('IsInter(string,sign)的参数出错： sign为null或\"-\"或\"+\"'); 
		return false; 
	} 
	integer = parseInt(string); 
	
	if (isNaN(integer))	{ 
		return false; 
	} 
	else if (integer.toString().length==string.length)	{ 
		if ((sign==null) || (sign=='-' && integer<0) || (sign=='+' && integer>=0)) { 
			return true; 
		} 
		else{
			return false; 
		} 
	} 
	else {
		return false; 
	}
}


Ext.onReady(function () {
	/**
	 * 更改产品信息触发
	 */
	Ext.zhj.calculate = function (northPanel,centerPanel,southPanel,newTree,obj,ed,value,oldValue) {
		
		// 获取表单对象 simpleFormZJ
		var simpleForm = northPanel;
		// 获取工序列表对象
		var workOrderList = centerPanel;
		
		var workOrderProductTapPanel = southPanel;
		
		// 获取产品信息列表
		var productTree = newTree;
		
		// root节点
		var rootNode = productTree.getRootNode();							
						
		var _singleSetAssemblyAmount = ed.editNode.cols['singleSetAssemblyAmount']*1;// 单套刀具装配数量
		// 校验数量
		if(!Ext.isNumber(_singleSetAssemblyAmount)) {
  				Ext.MessageBox.alert('错误提示', '单套刀具装配数量必须为整数！');
  				ed.editNode.cols['singleSetAssemblyAmount'] = oldValue;
  				ed.editNode.attributes['singleSetAssemblyAmount'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('singleSetAssemblyAmount',oldValue);
  				return;
  		}else if(!IsInteger(_singleSetAssemblyAmount.toString(),'+')){
  				Ext.MessageBox.alert('错误提示', '单套刀具装配数量必须为整数1！');
  				ed.editNode.cols['singleSetAssemblyAmount'] = oldValue;
  				ed.editNode.attributes['singleSetAssemblyAmount'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('singleSetAssemblyAmount',oldValue);
  				return;
  		}
  			
		
  		var _singleSetStockAmount = ed.editNode.cols['singleSetStockAmount']*1;// 单套刀具采购数量
		// 校验数量
  		//alert(_singleSetStockAmount);
		if(!Ext.isNumber(_singleSetStockAmount)) {
  				Ext.MessageBox.alert('错误提示', '单套刀具采购数量必须为整数！');
  				ed.editNode.cols['singleSetStockAmount'] = oldValue;
  				ed.editNode.attributes['singleSetStockAmount'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('singleSetStockAmount',oldValue);
  				return;
  		}else if(!IsInteger(_singleSetStockAmount.toString(),'+')){
  				Ext.MessageBox.alert('错误提示', '单套刀具采购数量必须为整数！');
  				ed.editNode.cols['singleSetStockAmount'] = oldValue;
  				ed.editNode.attributes['singleSetStockAmount'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('singleSetStockAmount',oldValue);
  				return;
  		}
		
		var _amout = ed.editNode.cols['amount']*1;// 数量
		// 校验数量
		if(!Ext.isNumber(_amout)) {
  				Ext.MessageBox.alert('错误提示', '数量必须为整数！');
  				ed.editNode.cols['amount'] = oldValue;
  				ed.editNode.attributes['amount'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('amount',oldValue);
  				return;
  		}
  		
  		// 折扣计算值
		var _rebate = (100 - ed.editNode.cols['rebate'])/100;// 折扣
		
		// 折扣输入数据
		var _isRebate = ed.editNode.cols['rebate'] * 1;
		//alert(_isRebate + "DDDDDDDDd");
		// 校验折扣
		if(!Ext.isNumber(_isRebate)) {
  				Ext.MessageBox.alert('错误提示', '折扣必须为0到100之间的整数！');
  				ed.editNode.cols['rebate'] = oldValue;
  				ed.editNode.attributes['rebate'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('rebate',oldValue);
  				return;
  		}else if(_isRebate < 0 || _isRebate > 100){
  				Ext.MessageBox.alert('错误提示', '折扣必须为0到100之间的整数！');
  				ed.editNode.cols['rebate'] = oldValue;
  				ed.editNode.attributes['rebate'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('rebate',oldValue);
  				return;
  		}else if(!IsInteger(_isRebate.toString(),'+')){
  				Ext.MessageBox.alert('错误提示', '折扣必须为0到100之间的整数！');
  				ed.editNode.cols['rebate'] = oldValue;
  				ed.editNode.attributes['rebate'] = oldValue;
				ed.editNode.ui.setInnerHTMLValue('rebate',oldValue);
  				return;
  		}
		
  		// 改变价格是否变动状态
		if(obj.editColIndex == 'rebate' &&  oldValue != value) {
			var selectedNode = rootNode.getOwnerTree().getSelectionModel().getSelectedNode();
			// selectedNode.ui.addRowNodeClass('red-row');
			
			ed.editNode.cols['priceChange'] = 1;
			ed.editNode.attributes['priceChange'] = 1;
			ed.editNode.ui.setInnerHTMLValue('priceChange',1);
			
			if(obj.editColIndex == 'rebate') {
				ed.editNode.ui.setColumnsClass('rebate','blue-column-font');
			}
			
			ed.editNode.ui.setColumnsClass('netPrice','blue-column-font');
			ed.editNode.ui.setColumnsClass('money','blue-column-font');
			ed.editNode.ui.setColumnsClass('taxNetPrice','blue-column-font');
			ed.editNode.ui.setColumnsClass('taxMoney','blue-column-font');
		}  else if(obj.editColIndex == 'netPrice' && ed.editNode.cols['priceChange'] == 0 &&  oldValue != value) {
				ed.editNode.cols['priceChange'] = 2;
				ed.editNode.attributes['priceChange'] = 2;
				ed.editNode.ui.setInnerHTMLValue('priceChange',2);
				
				if(obj.editColIndex == 'rebate') {
					ed.editNode.ui.setColumnsClass('rebate','blue-column-font');
				}
				
				ed.editNode.ui.setColumnsClass('netPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('money','red-column-font');
				ed.editNode.ui.setColumnsClass('taxNetPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('taxMoney','red-column-font');
		} else if(obj.editColIndex == 'netPrice' && ed.editNode.cols['priceChange'] == 1 &&  oldValue != value ) {
				ed.editNode.cols['priceChange'] = 3;
				ed.editNode.attributes['priceChange'] = 3;
				ed.editNode.ui.setInnerHTMLValue('priceChange',3);
				
				if(obj.editColIndex == 'rebate') {
					ed.editNode.ui.setColumnsClass('rebate','blue-column-font');
				}
				
				ed.editNode.ui.setColumnsClass('netPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('money','red-column-font');
				ed.editNode.ui.setColumnsClass('taxNetPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('taxMoney','red-column-font');
		}  else {
			if(ed.editNode.cols['priceChange'] == 4) {
				ed.editNode.ui.setColumnsClass('rebate','green-column-font');
				ed.editNode.ui.setColumnsClass('netPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('money','red-column-font');
				ed.editNode.ui.setColumnsClass('taxNetPrice','red-column-font');
				ed.editNode.ui.setColumnsClass('taxMoney','red-column-font');
			}
		}
		
		// 单价
		var _price = ed.editNode.cols['price'];
		var _netPrice = ed.editNode.cols['netPrice'];
		if(Ext.isEmpty(_netPrice))
			_netPrice = 0;
		if((obj.editColIndex != 'netPrice')){
			// 净价 = 单价*折扣
			if(_price != 0) {
				_netPrice = parseFloat( _price * _rebate ).toFixed(2);
				ed.editNode.cols['netPrice'] = _netPrice;
				ed.editNode.attributes['netPrice'] = _netPrice;
				ed.editNode.ui.setInnerHTMLValue('netPrice',_netPrice);
			}
		}
		
		
		/**
		 * 税率
		 */
		var taxCombox = simpleForm.taxRateCombox;
		var _taxCombox = taxCombox.getValue();
		// 含税净价= 净价*（1+税率)
		var _taxNetPrice = parseFloat((1+_taxCombox*1) * _netPrice).toFixed(2);
		ed.editNode.cols['taxNetPrice'] = _taxNetPrice;
		ed.editNode.attributes['taxNetPrice'] = _taxNetPrice;
		ed.editNode.ui.setInnerHTMLValue('taxNetPrice',_taxNetPrice);
		
	   /**
		 * 获取当前编辑的工序信息
		 */
    	var workOrderStore = workOrderList.getStore();
		//当前活动面板
		var activeTab = workOrderProductTapPanel.getActiveTab();
		//所有面板项
		var itemsPanel = workOrderProductTapPanel.items;
		// 活动面板序号
		var activeTabNumber ;
		
		for(var i = 0 ; i < itemsPanel.length ; i++ ){
			if(itemsPanel.items[i] == activeTab ){
				activeTabNumber = i;
			}
		}
		
		// n套产品
		var _productN = 0;	
		for (var i = 0; i < workOrderStore.getCount(); i++) {
			// 获取工单信息拷贝
			var a =	workOrderStore.getAt(i).copy();
			// 判断需要更改的序号
			if (a.get('workOrderNumber') == (activeTabNumber+1)) {
				_productN = parseInt(parseInt(a.get('supportAmount')) + parseInt(a.get('backupAmount'))) * _singleSetStockAmount;
				break;
			}
		};
		var oldAmount = ed.editNode.cols['amount'];
		/*if(oldAmount != 0 && _productN != oldAmount) {
			Ext.MessageBox.alert('错误提示', '（配套刀具套数+备用刀具套数）*单套刀具采购数量与导入数量不等！');
			_productN = ed.editNode.cols['amount'];
		}*/
		
		ed.editNode.cols['amount'] = _productN;
  		ed.editNode.attributes['amount'] = _productN;
		ed.editNode.ui.setInnerHTMLValue('amount',_productN);
	
		// 金额 = n套产品 * 净价
		var _money = parseFloat(_productN * _netPrice).toFixed(2);
		ed.editNode.cols['money'] = _money;
		ed.editNode.attributes['money'] = _money;
		ed.editNode.ui.setInnerHTMLValue('money',_money);
		
		/**
		 * 含税金额
		 */
		// 含税金额 = 金额 * (1+税率)
		var _taxMoney = parseFloat((1+_taxCombox*1) * _money).toFixed(2);
		ed.editNode.cols['taxMoney'] = _taxMoney;
		ed.editNode.attributes['taxMoney'] = _taxMoney;
		ed.editNode.ui.setInnerHTMLValue('taxMoney',_taxMoney);
									
		// 工序总金额
		var _workOrderMoney = 0;
		
		// 遍历root孩子节点，得到工序金额
		rootNode.eachChild(function(curNode) {
			// 如果不是叶子节点，展开该节点
			if(!curNode.isLeaf()) {
				curNode.expand();
			}
			if(curNode.id != 'root' && curNode.cols['money'] != null) {
				var _curMoney = curNode.cols['money'];
				_workOrderMoney += _curMoney*1.00;
			}
							
		});
		// 差价（原有工序和更改后工序之间的差价）
		var workOrderPriceDifference = 0.00;
		
		// 设置工序信息
		var _quoToolsTotalMoney = 0;
		for (var i = 0; i < workOrderStore.getCount(); i++) {
				// 获取工单信息拷贝
				var a =	workOrderStore.getAt(i);
				// 判断需要更改的序号
				if (a.get('workOrderNumber') == (activeTabNumber+1)) {
					workOrderPriceDifference = parseFloat(_workOrderMoney - parseFloat(a.get('totalMoney'))).toFixed(2);
					a.set('totalMoney', _workOrderMoney.toFixed(2));
					//break;
				}
				_quoToolsTotalMoney += a.get('totalMoney')*1;
				
		};	
		
		// 货品总价
		var productMoney = simpleForm.findByType('numberfield')[0]
		/*var _productMoney = productMoney.getValue();
		if(_productMoney == ""){
			_productMoney = 0.00;	
		}
		// 货品总价 = 原有总价 + 差价
		_productMoney  = parseFloat(_productMoney + parseFloat(workOrderPriceDifference)).toFixed(2);*/
		productMoney.setValue(_quoToolsTotalMoney.toFixed(2));
		productMoney.fireEvent('change');
		
	}
})