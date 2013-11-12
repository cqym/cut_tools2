/*
 * Ext JS Library 2.0 RC 1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
Ext.tree.ColumnTree = Ext.extend(Ext.tree.TreePanel, {
    lines:false,
    borderWidth: Ext.isBorderBox ? 0 : 2, // the combined left/right border for each cell
    cls:'x-column-tree',
	collapsible: false,
    
    onRender : function(){
        Ext.tree.ColumnTree.superclass.onRender.apply(this, arguments);
        this.headers = this.body.createChild(
            {cls:'x-tree-headers'},this.innerCt.dom);

        var cols = this.columns, c;
        var totalWidth = 0;

        for(var i = 0, len = cols.length; i < len; i++){
             c = cols[i];
             totalWidth += c.width;
             var str = '';
                 if(c.hidden){
                   str += 'display:none'  
                 }
             this.headers.createChild({
                 cls:'x-tree-hd ' + (c.cls?c.cls+'-hd':''),
                 cn: {
                     cls:'x-tree-hd-text',
                     html: c.header
                 },
                 
                 style:'width:'+(c.width-this.borderWidth)+'px;' + str
                 
             });
        }
        this.headers.createChild({cls:'x-clear'});
        // prevent floats from wrapping when clipped
        this.headers.setWidth(totalWidth);
        this.innerCt.setWidth(totalWidth);
    }
});


//var tttt = 1;

Ext.tree.ColumnNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
    focus: Ext.emptyFn, // prevent odd scrolling behavior
	
    renderElements : function(n, a, targetNode, bulkRender){
    	
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';
		
        var t = n.getOwnerTree();
        var cols = t.columns;
        var bw = t.borderWidth;
        var c = cols[0];
		n.cols = new Array();
		
		var text = n.text || (c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]);
		n.cols[cols[0].dataIndex] = text;
        var buf = [
             '<li class="x-tree-node" unselectable="on"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf ', a.cls,'" unselectable="on">',
                '<div class="x-tree-col" style="width:',c.width-bw,'px;" unselectable="on">',
                    '<span class="x-tree-node-indent" unselectable="on">',this.indentMarkup,"</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" unselectable="on">',
                    '<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on">',
                    '<a hidefocus="on" class="x-tree-node-anchor" href="',a.href ? a.href : "#",'" tabIndex="1" ',
                    a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", ' unselectable="on">',
                    '<span unselectable="on">', text,"</span></a>",
                "</div>"];
		 var hiddenStr = '',eventCols = [];
         for(var i = 1, len = cols.length; i < len; i++){
             c = cols[i];
			 var text = (c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]);
			 n.cols[cols[i].dataIndex] = text;
			 
			 if(cols[i].hidden){
			     hiddenStr = 'display:none';
			 }

             buf.push('<div class="x-tree-col ',(c.cls?c.cls:''),'" style="width:',c.width-bw,'px;',hiddenStr,'" unselectable="on">',
                        '<div class="x-tree-col-text" unselectable="on">',text,"</div>",
                      "</div>");
			hiddenStr = '';

			if(cols[i].listeners){
			    eventCols.push(i);
			}
         }
         buf.push(
            '<div class="x-clear" unselectable="on"></div></div>',
            '<ul class="x-tree-node-ct" style="display:none;" unselectable="on"></ul>',
            "</li>");
		
        if(bulkRender !== true && n.nextSibling && n.nextSibling.ui.getEl()){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin",
                                n.nextSibling.ui.getEl(), buf.join(""));
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf.join(""));
        }

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.firstChild.childNodes;
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        this.anchor = cs[3];
        this.textNode = cs[3].firstChild;
		for(var i = 0,len = eventCols.length; i < len ;i++ ){
			var lis = cols[eventCols[i]].listeners;
			var ff = this.elNode.childNodes[eventCols[i]].firstChild;
			ff['node'] = n;
			for(var j in lis){
				ff["on" + j] = lis[j];
			}
		}
        //pageInfoLabel['setInfor']();
    },
	setInnerHTMLValue: function(dataIndex,value){//方法描述：dataIndex为列dataIndex(字符串),value为要给本Node的此列设定值
	    var t = this.node.getOwnerTree();
        var cols = t.columns;
		var cs = this.elNode.firstChild.childNodes;
		var index = 0;
		for(var i = 0 ;i < cols.length;i++){
		    if(cols[i].dataIndex == dataIndex){
			    index = i;
				break;
			}
		}
		
		if(this.elNode.childNodes.length > index){
			
			this.elNode.childNodes[index].firstChild.innerHTML = value;
		}
	},
	
	setColumnsClass : function(dataIndex,value){
		var t = this.node.getOwnerTree();
        var cols = t.columns;
		var cs = this.elNode.firstChild.childNodes;
		var index = 0;
		for(var i = 0 ;i < cols.length;i++){
		    if(cols[i].dataIndex == dataIndex){
			    index = i;
				break;
			}
		}
		
		if(this.elNode.childNodes.length > index){
			this.elNode.childNodes[index].firstChild.className = value;
		}	
	},
	addRowNodeClass :function(_className){
		var tempDiv = this.elNode;
			tempDiv.className += ' ' + _className;
	},
	getColHtmlObject : function(dataIndex){
		var t = this.node.getOwnerTree();
        var cols = t.columns;
		var cs = this.elNode.firstChild.childNodes;
		var index = 0;
		for(var i = 0 ;i < cols.length;i++){
		    if(cols[i].dataIndex == dataIndex){
			    index = i;
				break;
			}
		}
		
		if(this.elNode.childNodes.length > index){
			
			return this.elNode.childNodes[index].firstChild;
		}
		return null;
	}
});

Ext.tree.ColumnTreeEditor = function(tree, config){
    config = config || {};
	config.treeEditor = this;
	if(!config.listeners) config.listeners = {};
	config.listeners.focus = function(f){
		f.selectText(0,f.getValue().length);
	}

    var field = config.events ? config : new Ext.form.TextField(config);
    this.ffcFiled = field;
    Ext.tree.TreeEditor.superclass.constructor.call(this, field);

    this.tree = tree;

    if(!tree.rendered){
        tree.on('render', this.initEditor, this);
    }else{
        this.initEditor(tree);
    }
};

Ext.extend(Ext.tree.ColumnTreeEditor, Ext.Editor, {
    
    alignment: "l-l",
    autoSize: false,
    
    hideEl : false,
    
    cls: "x-small-editor x-tree-editor",
    
    shim:false,
    shadow:"frame",
    
    maxWidth: 250,
    
    editDelay: 0,

    initEditor : function(tree){
        tree.on('beforeclick', this.beforeNodeClick, this);
        this.on('complete', this.updateNode, this);
        this.on('beforestartedit', this.fitToTree, this);
        this.on('startedit', this.bindScroll, this, {delay:10});
        this.on('specialkey', this.onSpecialKey, this);
    },

    fitToTree : function(ed, el){
        var td = this.tree.getTreeEl().dom, nd = el.dom;
        if(td.scrollLeft >  nd.offsetLeft){             
			td.scrollLeft = nd.offsetLeft;
        }
        var w = Math.min(
                this.maxWidth,
                (td.clientWidth > 20 ? td.clientWidth : td.offsetWidth) - Math.max(0, nd.offsetLeft-td.scrollLeft) - 5);
        this.setSize(w, '');
    },

    triggerEdit : function(node, e){
        var obj = e.target;
		if (Ext.select(".x-tree-node-anchor", false, obj).getCount() == 1) {
			obj = Ext.select(".x-tree-node-anchor", false, obj).elements[0].firstChild;
		} else if (obj.nodeName == 'SPAN' || obj.nodeName == 'DIV'){
			obj = e.target;
		} else {
			return false;
		}
		
		
		var ffct = node.getOwnerTree();
        var ffccols = ffct.columns;
        
		var colIndex = 0;
		var brotherNodes = node.getUI().getEl().childNodes[0].childNodes;
		var ffcIndex = 0;
		for(var j = 0 ; j < ffccols.length; j++){
			//if (node.cols[i] == obj) {
			//	colIndex = i;
			//}
			if(j == 0){
				if(brotherNodes[ffcIndex] == obj.parentNode.parentNode){
				    colIndex = ffccols[j].dataIndex;
				}
			}else{
				if(brotherNodes[ffcIndex] == obj.parentNode){
					colIndex = ffccols[j].dataIndex;
				}
			}
			ffcIndex++;
		}
        for(var j = 0 ; j < ffccols.length; j++){
        	//alert(ffccols[j].disEnableEdit + "," + colIndex + "," + ffccols[j].dataIndex);
            if(ffccols[j].disEnableEdit && colIndex == ffccols[j].dataIndex){
                e.stopEvent();
				return false;
            };
        }
		this.completeEdit();
		this.editNode = node;
		this.editCol = obj;
		this.editColIndex = colIndex;
		this.startEdit(obj);
		if (obj.nodeName == 'DIV') {
			var width = obj.offsetWidth;
			this.setSize(width);
		}
    },

    bindScroll : function(){
        this.tree.getTreeEl().on('scroll', this.cancelEdit, this);
    },

    beforeNodeClick : function(node, e){
        var sinceLast = (this.lastClick ? this.lastClick.getElapsed() : 0);
        this.lastClick = new Date();
        if(sinceLast > this.editDelay && this.tree.getSelectionModel().isSelected(node)){
            e.stopEvent();
            this.triggerEdit(node, e);
            return false;
        } else {
			this.completeEdit();
		}
    },

    updateNode : function(ed, value){
		var oldValue = this.editNode.cols[this.editColIndex];
		this.fireEvent('fccBeforeUpdateNodeEvent', this,ed,value,oldValue);
        this.tree.getTreeEl().un('scroll', this.cancelEdit, this);
		this.tree.getTreeEl().on('textchange', this.cancelEdit, this);

        this.editNode.cols[this.editColIndex] = value; //for internal use only
		this.editNode.attributes[this.editColIndex] = value;//duplicate into array of node attributes
		this.editCol.innerHTML = value;
		this.fireEvent('fccAfterUpdateNodeEvent', this,ed,value,oldValue);
    },

    onHide : function(){
        Ext.tree.TreeEditor.superclass.onHide.call(this);
        if(this.editNode){
            this.editNode.ui.focus();
        }
    },

    onSpecialKey : function(field, e){
        var k = e.getKey();
        if(k == e.ESC){
            e.stopEvent();
            this.cancelEdit();
        }else if(k == e.ENTER && !e.hasModifier()){
            e.stopEvent();
            this.completeEdit();
        }
    }
});

Ext.tree.ColumnTreeNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
	
    focus: Ext.emptyFn, // prevent odd scrolling behavior

    renderElements : function(n, a, targetNode, bulkRender){
        this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';

        var t = n.getOwnerTree();
        var cols = t.columns;
        var bw = t.borderWidth;
        var c = cols[0];

		var cb = typeof a.checked == 'boolean';
		if(typeof this.checkModel != 'undefined'){
			cb = (!this.onlyLeafCheckable || n.isLeaf());
		}
		var href = a.href ? a.href : Ext.isGecko ? "" : "#";
        var buf = ['<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">',
                '<div class="x-tree-col" style="width:',c.width-bw,'px;">',
                    '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow">',
                    '<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on">',
                    cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '',
                    '<a hidefocus="on" class="x-tree-node-anchor" href="',href,'" tabIndex="1" ',
                    a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '>',
                    '<span unselectable="on">', n.text || (a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),"&nbsp;</span></a>",
                "</div>"];
         for(var i = 1, len = cols.length; i < len; i++){
             c = cols[i];
             
             var str = '';
			 if(cols[i].hidden){
			     str = 'display:none';
			 }
             
             buf.push('<div class="x-tree-col ',(c.cls?c.cls:''),'" style="width:',c.width-bw,'px;',str,'">',
                        '<div class="x-tree-col-text">',(a[c.dataIndex]?(c.renderer ? c.renderer(a[c.dataIndex], n, a) : a[c.dataIndex]):''),"&nbsp;</div>",
                      "</div>");
         }
         
         buf.push('<div class="x-clear"></div>',
            '</div>',
            '<ul class="x-tree-node-ct" style="display:none;"></ul>',
            "</li>");
            
        if(bulkRender !== true && n.nextSibling && n.nextSibling.ui.getEl()){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin",n.nextSibling.ui.getEl(), buf.join(""));
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf.join(""));
        }

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
        var cs = this.elNode.firstChild.childNodes;
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
        
        var index = 3;
        if(cb){
            this.checkbox = cs[3];
            index++;
        }
        this.anchor = cs[index];
        this.textNode = cs[index].firstChild;
    }
});


Ext.ux.ColumnTreeCheckNodeUI = function() {
	//多选: 'multiple'(默认)
	//单选: 'single'
	//级联多选: 'cascade'(同时选父和子);'parentCascade'(选父);'childCascade'(选子)
	this.checkModel = 'multiple';
	
	//only leaf can checked
	this.onlyLeafCheckable = false;
	
	Ext.ux.ColumnTreeCheckNodeUI.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.ColumnTreeCheckNodeUI, Ext.tree.ColumnTreeNodeUI, {
    
    renderElements : function(n, a, targetNode, bulkRender){
    	var t = n.getOwnerTree();
		this.checkModel = t.checkModel || this.checkModel;
		this.onlyLeafCheckable = t.onlyLeafCheckable || false;
    	
    	Ext.ux.ColumnTreeCheckNodeUI.superclass.renderElements.apply(this, arguments);
		
		var cb = (!this.onlyLeafCheckable || n.isLeaf());
        if(cb){
            Ext.fly(this.checkbox).on('click', this.check.createDelegate(this,[null]));
        }
    },
    
    // private
    check : function(checked){
        var n = this.node;
		var tree = n.getOwnerTree();
		this.checkModel = tree.checkModel || this.checkModel;
		
		if( checked === null ) {
			checked = this.checkbox.checked;
		} else {
			this.checkbox.checked = checked;
		}
		
		n.attributes.checked = checked;
		tree.fireEvent('check', n, checked);
		
		if(!this.onlyLeafCheckable){
			if(this.checkModel == 'cascade' || this.checkModel == 'parentCascade'){
				var parentNode = n.parentNode;
				if(parentNode !== null) {
					this.parentCheck(parentNode,checked);
				}
			}
			if(this.checkModel == 'cascade' || this.checkModel == 'childCascade'){
				if( !n.expanded && !n.childrenRendered ) {
					n.expand(false,false,this.childCheck);
				}else {
					this.childCheck(n);  
				}
			}
		} else if(this.checkModel == 'single'){
			var checkedNodes = tree.getChecked();
			for(var i=0;i<checkedNodes.length;i++){
				var node = checkedNodes[i];
				if(node.id != n.id){
					node.getUI().checkbox.checked = false;
					node.attributes.checked = false;
					tree.fireEvent('check', node, false);
				}
			}
		}
    },
    
    // private
	childCheck : function(node){
		var a = node.attributes;
		if(!a.leaf) {
			var cs = node.childNodes;
			var csui;
			for(var i = 0; i < cs.length; i++) {
				csui = cs[i].getUI();
				if(csui.checkbox.checked ^ a.checked)
					csui.check(a.checked);
			}
		}
	},
	
	// private
	parentCheck : function(node ,checked){
		var checkbox = node.getUI().checkbox;
		if(typeof checkbox == 'undefined')return ;
		if(!(checked ^ checkbox.checked))return;
		if(!checked && this.childHasChecked(node))return;
		checkbox.checked = checked;
		node.attributes.checked = checked;
		node.getOwnerTree().fireEvent('check', node, checked);
		
		var parentNode = node.parentNode;
		if( parentNode !== null){
			this.parentCheck(parentNode,checked);
		}
	},
	
	// private
	childHasChecked : function(node){
		var childNodes = node.childNodes;
		if(childNodes || childNodes.length>0){
			for(var i=0;i<childNodes.length;i++){
				if(childNodes[i].getUI().checkbox.checked)
					return true;
			}
		}
		return false;
	},
	
    toggleCheck : function(value){
    	var cb = this.checkbox;
        if(cb){
            var checked = (value === undefined ? !cb.checked : value);
            this.check(checked);
        }
    }
});
/**
Ext.tree.ColumnTree 分页工具条
*/
Ext.ffc.TreePagingToolbar = Ext.extend(Ext.PagingToolbar, {
	listeners:{
				change : function( pagingToolbar, pageData )  {
						var e = pagingToolbar.store.data.items;
						var tree = pagingToolbar.ownerCt;
						var root = tree.getRootNode();
						if(root.childNodes){
							var len = root.childNodes.length;
							for(var i = len - 1;i >= 0;i--){
								root.removeChild(root.childNodes[i]);
							}
						}
						if(e.length == 0) return;
						var arr = [];
						for(var i = 0 ;i < e.length;i++){
							arr.push(e[i].json);					
						}
						
						for(var i = 0; i < arr.length;i++){
							root.appendChild(arr[i]); 
						}
					
				}
	}
});