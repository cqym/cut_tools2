 var cms = {form:{ComboTree:function(){}}};
  cms.form.ComboTree = function(options) {  
     cms.form.ComboTree.superclass.constructor.call(this, options);  
 };  

 Ext.extend(cms.form.ComboTree, Ext.form.TriggerField, {  
     triggerClass : 'x-form-arrow-trigger',  
     shadow : 'sides',  
     lazyInit : true,  
     initComponent : function() {  
         cms.form.ComboTree.superclass.initComponent.call(this);  
     },  
   
     onRender : function(ct, position) {  
         Ext.form.ComboBox.superclass.onRender.call(this, ct, position);  
         var hiddenName = this.name;  
   
         this.hiddenField = this.el.insertSibling({  
             tag : 'input',  
             type : 'hidden',  
             name : hiddenName  
         }, 'before', true);  
   
         this.hiddenField.value = this.value !== undefined ? this.value : 0;  
   
         this.el.dom.removeAttribute('name');  
         this.id = this.name;  
   
         if (!this.lazyInit) {  
             this.initList();  
         } else {  
             this.on('focus', this.initList, this, {  
                 single : true  
             });  
         }  
     },  
   
     initList : function() {  
         if (this.list) {  
             return;  
         }  
         this.list = new Ext.Layer({  
             shadow : this.shadow,  
             cls : 'x-combo-list',  
             constrain : false  
         });  
   
         this.root = new Ext.tree.AsyncTreeNode({expanded :true});  
         this.list.setWidth(Math.max(this.wrap.getWidth(), 70));  
         tree = new Ext.tree.TreePanel({  
             autoScroll : true,  
             height : 200,  
             border : false,  
             root : this.root,  
             loader : this.loader  
         });  
   
         delete this.loader;  
   
         tree.on('click', this.onClick, this);  
         tree.render(this.list);  
   
         this.el.dom.setAttribute('readOnly', true);  
         this.el.addClass('x-combo-noedit');  
     },  
       
     expandNode : function(n, v){  
         var cs = n.childNodes;  
         for(var i = 0, len = cs.length; i < len; i++) {  
             if(cs[i].id == v){  
                 return true;  
             }  
             if(expandNode(cs[i], v)){  
                 cs[i].expand();  
                 return true;  
             }  
         }  
         return false;  
     },  
   
   
     validateValue : function(value) {  
         return true;  
     },  
   
     validateBlur : function() {  
         return !this.list || !this.list.isVisible();  
     },  
   
     onDestroy : function() {  
         if (this.wrap) {  
             this.wrap.remove();  
         }  
         if (this.list) {  
             this.list.destroy();  
         }  
         cms.form.ComboTree.superclass.onDestroy.call(this);  
     },  
   
     isExpanded : function() {  
         return this.list && this.list.isVisible();  
     },  
   
     collapse : function() {  
         if (this.isExpanded()) {  
             this.list.hide();  
         }  
     },  
   
     onClick : function(node) {  
         this.setValue(node);  
         this.collapse();  
     },  
   
   
     setValue : function(v) {  
         var val = v;  
         if(typeof v === 'object'){  
             this.hiddenField.value = ((this.root && v.id == this.root.id) ? 0 : v.id);  
             val = v.text;  
         }  
         cms.form.ComboTree.superclass.setValue.call(this, val);  
     },  
   
     initEvents : function() {  
         cms.form.ComboTree.superclass.initEvents.call(this);  
         this.el.on('mousedown', this.onTriggerClick, this);  
     },  
   
     onTriggerClick : function() {  
         if (this.disabled) {  
             return;  
         }  
   
         if (this.isExpanded()) {  
             this.collapse();  
             this.el.focus();  
         } else {  
             this.onFocus({});  
             this.list.alignTo(this.wrap, 'tl-bl?');  
             this.list.show();  
         }  
     }  
   
 });  
  
 Ext.reg('combotree', cms.form.ComboTree);  
