 Ext.PagingToolbarEx = Ext.extend(Ext.PagingToolbar, {  
     afterPageText: '/ {0}',  
     beforePageText: '页',  
     displayInfo: true,  
     displayMsg: '显示 {0} - {1} / {2}',  
     emptyMsg: '没有数据',  
     firstText: '第一页',  
     prevText: '前一页',  
     nextText: '后一页',  
     lastText: '最后一页',  
     refreshText: '刷新',  
       
     updateInfo: function(){  
         if(this.displayEl){  
             var count = this.store.getCount();  
               
             var msg = count == 0 ? this.emptyMsg : String.format(this.displayMsg, this.cursor, this.cursor + count - 1, this.store.getTotalCount());  
               
             this.displayEl.update(msg);  
         }  
     },  
       
     onLoad: function(store, r, o){  
         if(!this.rendered){  
             this.dsLoaded = [store, r, o];  
               
             return;  
         }  
           
         if(!o.params || this.store.getTotalCount() == 0){  
             this.cursor = 0;  
         }  
         else{  
             this.cursor = (o.params[this.paramNames.start] - 1) * this.pageSize + 1;  
         }  
           
         var d =this.getPageData(), ap = d.activePage, ps = d.pages;  
           
         this.afterTextEl.el.innerHTML = String.format(this.afterPageText, d.pages);  
         this.field.dom.value = ap;  
           
         this.first.setDisabled(ap == 1 || ps == 0);  
         this.prev.setDisabled( ap == 1 || ps == 0);  
         this.next.setDisabled(ap == ps || ps == 0);  
         this.last.setDisabled(ap == ps || ps == 0);  
         this.loading.enable();  
         this.loading.setDisabled(ps == 0);  
         this.updateInfo();      
     },  
       
     getPageData: function(){  
         var total = this.store.getTotalCount();  
           
         return {  
             total: total,  
             activePage: total != 0 && this.cursor == 0 ? 1 : Math.ceil(this.cursor / this.pageSize),  
             pages: total !=0 && total < this.pageSize ? 1 : Math.ceil(total / this.pageSize)  
         }  
     },  
       
     onClick: function(which){  
         var store = this.store;  
         var d = this.getPageData();  
           
         switch(which){  
             case 'first':  
                 this.doLoad(1);  
                 break;  
             case 'prev':  
                 this.doLoad(Math.max(1, d.activePage - 1));  
                 break;  
             case 'next':  
                 this.doLoad(Math.min(d.pages, d.activePage + 1));  
                 break;  
             case 'last':  
                 this.doLoad(d.pages);  
                 break;  
             case 'refresh':  
                 this.doLoad(d.activePage);  
                 break;  
         }  
     },  
       
     onPagingKeydown: function(e){  
         var k = e.getKey(), d = this.getPageData(), pageNum;  
           
         if(k == e.RETURN){  
             e.stopEvent();  
               
             pageNum = this.readPage(d)  
               
             if(pageNum >= d.pages){  
                 pageNum = d.pages;  
             }  
             else if(pageNum <= 1){  
                 pageNum = 1;  
             }  
               
             this.doLoad(pageNum);  
       
         }  
         else if(k == e.HOME || k == e.END){  
             e.stopEvent();  
             pageNum = k == e.HOME ? 1 : d.pages;  
             this.field.dom.value = pageNum;  
         }  
         else if(k == e.UP || k == e.PAGEUP || k == e.DOWN || k == e.PAGEDOWN){  
             e.stopEvent();  
               
             if(pageNum = this.readPage(d)){  
                 var increment = e.shiftKey ? 10 : 1;  
                   
                 if(k == e.DOWN || k == e.PAGEDOWN){  
                     increment *= -1;  
                 }  
                   
                 pageNum += increment;  
                   
                 if(pageNum >= 1 & pageNum <= d.pages){  
                     this.field.dom.value = pageNum;  
                 }  
             }  
         }  
     }  
 });  
   
 Ext.reg('pagingtoolbarex', Ext.PagingToolbarEx);  