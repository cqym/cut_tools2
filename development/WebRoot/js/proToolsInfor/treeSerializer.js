/* tostring.js */

/**
 * Returns a string of Json that represents the tree
 * @param {Function} (optional) A function, which when passed the node, returns true or false to include
 * or exclude the node.
 * @param {Function} (optional) A function, which when passed an attribute name, and an attribute value,
 * returns true or false to include or exclude the attribute.
 * @return {String}
 */
Ext.tree.TreePanel.prototype.toJsonString = function(nodeFilter, attributeFilter, attributeMapping){
      return this.getRootNode().toJsonString(nodeFilter, attributeFilter, attributeMapping);
};

/**
 * Returns a string of Json that represents the node
 * @param {Function} (optional) A function, which when passed the node, returns true or false to include
 * or exclude the node.
 * @param {Function} (optional) A function, which when passed an attribute name, and an attribute value,
 * returns true or false to include or exclude the attribute.
 * @return {String}
 */
Ext.tree.TreeNode.prototype.toJsonString = function(nodeFilter, attributeFilter, attributeMapping){
//	Exclude nodes based on caller-supplied filtering function
    if (nodeFilter && (nodeFilter(this) == false)) {
        return '';
    }
    var c = false, result = "{";

//	Add the id attribute unless the attribute filter rejects it.
    if (!attributeFilter || attributeFilter("id", this.id)) {
        result += '"id":"' + this.id + '"';
        c = true;
    }
    
	
//	Add all user-added attributes unless rejected by the attributeFilter.
    for(var key in this.attributes) {
        if ((key != 'id')&&(key != 'children') && (!attributeFilter || attributeFilter(key, this.attributes[key]))) {
        	
			if (attributeMapping && attributeMapping[key]) {
				thisKey = attributeMapping[key];
			} else {
				thisKey = key;
			}
			
			if(thisKey == 'uiProvider') {
				break;
			}
        	
	        if (c) result += ',';
			var typestr = typeof this.attributes[key];
	        if( typestr == 'boolean' || typestr == 'number'){
	            result += '"' + thisKey + '":' + this.attributes[key];
	        }else{
	            result += '"' + thisKey + '":"' + this.attributes[key] + '"';
	        }
			c = true;
	        
	    }
    }

//	Add child nodes if any
    var children = this.childNodes;
    var clen = children.length;
    
    if(clen != 0){
        if (c) result += ',';
        result += '"children":['
        for(var i = 0; i < clen; i++){
            if (i > 0) result += ',';
            result += children[i].toJsonString(nodeFilter, attributeFilter, attributeMapping);
        }
        result += ']';
    }
    return result + "}";
};
/**
node 为 {} json串对象
definedProperty 值影射对象 要给每一个节点，附加的公共属性
needId 是否需要原id
noNeedPropertys 数组 新节点不需要的属性
返回为Node json传
*/
Ext.tree.toNewTreeNode = function(node,definedProperty,needId,noNeedPropertys){
	var newNode = {};
	if(!noNeedPropertys){
		noNeedPropertys = [];
	}
	if(noNeedPropertys.length == 0 || noNeedPropertys[noNeedPropertys.length - 1] != 'children'){
		noNeedPropertys.push('dataUrl');
		noNeedPropertys.push('events');
		noNeedPropertys.push('loader');
		noNeedPropertys.push('children');
	}
	var flag = true;
	for(var i in node){
		flag = true;
		for(var j = 0 ;j < noNeedPropertys.length;j++){
			if(noNeedPropertys[j] == i){
				flag = false;
				break;
			}
		}
		if(!flag) continue;
		if(!needId && i == 'id'){
		    continue;
		}
	    newNode[i] = node[i];
	}
	for(var i in definedProperty){
		newNode[i] = definedProperty[i];
	}
	
	if(!node.leaf){
		    newNode["children"] = [];
		    var carr = node.children;
		    if(carr){
				for(var i = 0 ;i < carr.length;i++){
					newNode["children"].push(Ext.tree.toNewTreeNode(carr[i],definedProperty,needId,noNeedPropertys));
				} 
		    }
	}
	
	return newNode;
}
/**
node 为 {} json串对象
definedProperty 值影射对象 要给每一个节点，附加的公共属性
noNeedPropertys 数组 新节点不需要的属性
返回为所有叶子节点Node json传
*/
Ext.tree.getAllLeafNode = function(node,definedProperty,noNeedPropertys){
	var leafNodes = [];
	var newNode = {};
	if(!noNeedPropertys){
		noNeedPropertys = [];
	}
	if(noNeedPropertys.length == 0 || noNeedPropertys[noNeedPropertys.length - 1] != 'children'){
		noNeedPropertys.push('dataUrl');
		noNeedPropertys.push('events');
		noNeedPropertys.push('loader');
		noNeedPropertys.push('children');
	}
	var flag = true;
	for(var i in node){
		flag = true;
		for(var j = 0 ;j < noNeedPropertys.length;j++){
			if(noNeedPropertys[j] == i){
				flag = false;
				break;
			}
		}
		if(!flag) continue;
	    newNode[i] = node[i];
	}
	for(var i in definedProperty){
		newNode[i] = definedProperty[i];
	}
	
	if(!node.leaf){
		    var carr = node.children;
		    if(carr){
				for(var i = 0 ;i < carr.length;i++){
					leafNodes = leafNodes.concat(Ext.tree.getAllLeafNode(carr[i],definedProperty,noNeedPropertys));
				} 
		    }
	}else{
		leafNodes.push(newNode);
	}
	 return leafNodes;
}

/**
获得一个节点下，所有叶子节点
*/
Ext.tree.getAllLeafNodeObject = function(node){
	var leafNodes = [];
	if(!node.leaf || node.id == 'root'){
		    var carr = node.childNodes;
		    if(carr){
				for(var i = 0 ;i < carr.length;i++){
					leafNodes = leafNodes.concat(Ext.tree.getAllLeafNodeObject(carr[i]));
				} 
		    }
	}else{
		leafNodes.push(node);
	}
	 return leafNodes;
}

Ext.ffc.findChildNodeByAtr = function(currNode,atrName,atrValue){
	var children = currNode.childNodes;
	var node = null;
	for(var i = 0;i < children.length ;i++){
	    if(children[i].attributes[atrName] == atrValue){
		    node = children[i];
			break;
		}else{
			if(!children[i].isLeaf()){
				node = Ext.ffc.findChildNodeByAtr(children[i],atrName,atrValue);
				if(node){
				    break;
				}
			}
		}
	}
	return node;
}

/**
 * Returns a string of XML that represents the tree
 * @param {Function} (optional) A function, which when passed the node, returns true or false to include
 * or exclude the node.
 * @param {Function} (optional) A function, which when passed an attribute name, and an attribute value,
 * returns true or false to include or exclude the attribute.
 * @return {String}
 */
Ext.tree.TreePanel.prototype.toXmlString = function(nodeFilter, attributeFilter, attributeMapping){
	return '\u003C?xml version="1.0"?>\u003Ctree>' +
		this.getRootNode().toXmlString(nodeFilter, attributeFilter, attributeMapping) +
		'\u003C/tree>';
};

/**
 * Returns a string of XML that represents the node
 * @param {Function} (optional) A function, which when passed the node, returns true or false to include
 * or exclude the node.
 * @param {Function} (optional) A function, which when passed an attribute name, and an attribute value,
 * returns true or false to include or exclude the attribute.
 * @return {String}
 */
Ext.tree.TreeNode.prototype.toXmlString = function(nodeFilter, attributeFilter, attributeMapping){
//	Exclude nodes based on caller-supplied filtering function
    if (nodeFilter && (nodeFilter(this) == false)) {
        return '';
    }
    var result = '\u003Cnode';

//	Add the id attribute unless the attribute filter rejects it.
    if (!attributeFilter || attributeFilter("id", this.id)) {
        result += ' id="' + this.id + '"';
    }

//	Add all user-added attributes unless rejected by the attributeFilter.
    for(var key in this.attributes) {
        if ((key != 'id') && (!attributeFilter || attributeFilter(key, this.attributes[key]))) {
	        if (attributeMapping && attributeMapping[key]) {
				thisKey = attributeMapping[key];
			} else {
				thisKey = key;
			}
			result += ' ' + thisKey + '="' + this.attributes[key] + '"';
	    }
    }

//	Add child nodes if any
    var children = this.childNodes;
    var clen = children.length;
    if(clen == 0){
        result += '/>';
    }else{
        result += '>';
        for(var i = 0; i < clen; i++){
            result += children[i].toXmlString(nodeFilter, attributeFilter, attributeMapping);
        }
        result += '\u003C/node>';
    }
    return result;
};
//hasChildNodes() : Boolean 
Ext.tree.TreeNode.prototype.findAllChildren = function(){
	var nodesArr = [];
	//nodesArr.push(node);
	var children = this.childNodes;
    var clen = children.length;
	if(clen == 0){
	    return null;
	}else{
	    for(var i = 0; i < clen; i++){
			nodesArr.push(children[i]);
			if(children[i].hasChildNodes()){
			   nodesArr = nodesArr.concat(children[i].findAllChildren());
			}
		}
	}
	return nodesArr;
}