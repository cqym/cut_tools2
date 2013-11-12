Ext.onReady(function () {
	//删除时调用
	calculateOnDelete = function(rootNode,_taxRate, _quForm) {
		var product_money = 0; //货品金额
						
		rootNode.eachChild(function(curNode) {
			
			if(curNode.id != 'root' && curNode.cols['money'] != null) {
				var _curMoney = curNode.cols['money'];
				var tM = parseFloat(_curMoney*_taxRate).toFixed(2);
				var _taxM = _curMoney*1 + tM*1;
				
				product_money += _curMoney*1.00;
			}
							
		})
						
		var _overallRebate = _quForm.findByType('numberfield')[3].getValue();//整单折扣
		if(_overallRebate == "") {
			_overallRebate = 0;
		}
						
		var _formTaxMoney = parseFloat(product_money * _taxRate).toFixed(2); //税金
		var _totalMoney = parseFloat(parseFloat(product_money) + parseFloat(_formTaxMoney)).toFixed(2);//税价合计
		var _finalMoney = parseFloat(_totalMoney*(100-_overallRebate)/100).toFixed(2);//最终金额
		_quForm.findByType('numberfield')[0].getEl().dom.value=product_money.toFixed(2)//.setValue(product_money);
		_quForm.findByType('numberfield')[1].setValue(_formTaxMoney);
		_quForm.findByType('numberfield')[2].setValue(_totalMoney);
		_quForm.findByType('numberfield')[4].setValue(_finalMoney);
	}
	
	calculate = function (rootNode,_taxRate, _quForm, obj,ed,value,oldValue, _rate) {
		if(Ext.isEmpty(_rate)) {
			_rate = 1;
		}
		var product_money = 0; //货品金额
		var _amout = ed.editNode.cols['amount']*1;//数量
		//校验数量
		if(!Ext.isNumber(_amout)) {
			Ext.MessageBox.alert('错误提示', '数量必须为整数！');
			ed.editNode.cols['amount'] = oldValue;
			ed.editNode.attributes['amount'] = oldValue;
			ed.editNode.ui.setInnerHTMLValue('amount',oldValue);
			return;
  		}
  		
  		if(obj.editColIndex == 'rebate' && value == oldValue)
  			return;
		var _rebate = (100 - ed.editNode.cols['rebate'])/100;//折扣
		//校验折扣
		if(!Ext.isNumber(_rebate)) {
			Ext.MessageBox.alert('错误提示', '折扣必须为整数！');
			ed.editNode.cols['rebate'] = oldValue;
			ed.editNode.attributes['rebate'] = oldValue;
			ed.editNode.ui.setInnerHTMLValue('rebate',oldValue);
			return;
  		}
  		
		var _price = ed.editNode.cols['salePrice']; //单价
		
		if(Ext.isEmpty(_price)) {
			_price = ed.editNode.cols['price'];
		}
		_price *= _rate;
		_price = _price.toFixed(2);
		
		var _netPrice = 0;
		if(obj.editColIndex == 'netPrice') {
			if(Ext.isNumber(value*1)) {
				_netPrice = value;
			} else {
				_netPrice = oldValue;
			}
		} else if(_price != 0){
			_netPrice = (_rebate*_price*1).toFixed(2);//净价=折扣*单价
		} else if(_price == 0 && !Ext.isEmpty(ed.editNode.cols['netPrice'])) {
			_netPrice = (ed.editNode.cols['netPrice'] *_rate).toFixed(2);
		}
		 
		
		var _money = (_amout * _netPrice * 1).toFixed(2);//金额=数量*净价
		
		//alert("折扣 " + _rebate + ' 单价 ' + _price + ' 净价 ' + _netPrice + ' 金额 ' + _money);
		//改变价格是否变动状态
		if(obj.editColIndex == 'rebate' &&  oldValue != value) {
			var selectedNode = rootNode.getOwnerTree().getSelectionModel().getSelectedNode();
			//selectedNode.ui.addRowNodeClass('red-row');
			
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
		} else {
			if(ed.editNode.cols['priceChange'] == 4) {
				ed.editNode.ui.setColumnsClass('rebate','green-column-font');
				ed.editNode.ui.setColumnsClass('netPrice','green-column-font');
				ed.editNode.ui.setColumnsClass('money','green-column-font');
				ed.editNode.ui.setColumnsClass('taxNetPrice','green-column-font');
				ed.editNode.ui.setColumnsClass('taxMoney','green-column-font');
			}
		}
						
		//
		ed.editNode.cols['price'] = _price;
		ed.editNode.attributes['price'] = _price;
		ed.editNode.ui.setInnerHTMLValue('price',_price);
		
		ed.editNode.cols['money'] = _money;
		ed.editNode.attributes['money'] = _money;
		ed.editNode.ui.setInnerHTMLValue('money',_money);
						
		ed.editNode.cols['netPrice'] = _netPrice;
		ed.editNode.attributes['netPrice'] = _netPrice;
		ed.editNode.ui.setInnerHTMLValue('netPrice',_netPrice);
						
		rootNode.eachChild(function(curNode) {
			if(curNode.id != 'root' && curNode.cols['money'] != null) {
				var _curMoney = curNode.cols['money'];
				var tM = parseFloat(_curMoney*_taxRate).toFixed(2);
				var _taxM = _curMoney*1 + tM*1;
				
				var _curNetPri = curNode.cols['netPrice'];
				var nP = parseFloat(_curNetPri*_taxRate).toFixed(2);
				var _netPri = _curNetPri*1 + nP*1;//含税净价=净价*税率
				
				var _taxNetPrice = parseFloat(_netPri).toFixed(2);//含税净价=净价*税率
				var _taxMoney = parseFloat(_taxM).toFixed(2);//含税金额=金额*税率 + 金额
				curNode.cols['taxNetPrice'] = _taxNetPrice;
				curNode.attributes['taxNetPrice'] = _taxNetPrice;
				curNode.ui.setInnerHTMLValue('taxNetPrice',_taxNetPrice);
								
				curNode.cols['taxMoney'] = _taxMoney;
				curNode.attributes['taxMoney'] = _taxMoney;
				curNode.ui.setInnerHTMLValue('taxMoney',_taxMoney);
				
				product_money += _curMoney*1.00;
			}
							
		})
						
		var _overallRebate = _quForm.findByType('numberfield')[3].getValue();//整单折扣
		if(_overallRebate == "") {
			_overallRebate = 0;
		}
		var _formTaxMoney = parseFloat(product_money * _taxRate).toFixed(2); //税金
		var _totalMoney = parseFloat(parseFloat(product_money) + parseFloat(_formTaxMoney)).toFixed(2);//税价合计
		var _finalMoney = parseFloat(_totalMoney*(100-_overallRebate)/100).toFixed(2);//最终金额
		_quForm.findByType('numberfield')[0].getEl().dom.value=product_money.toFixed(2)//.setValue(product_money);
		_quForm.findByType('numberfield')[1].setValue(_formTaxMoney);
		_quForm.findByType('numberfield')[2].setValue(_totalMoney);
		_quForm.findByType('numberfield')[4].setValue(_finalMoney);
	}
	
})