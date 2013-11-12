package com.tl.common.util;

import java.util.List;

public class PaginationSupport {
	  public final static int PAGESIZE = 30;  
	  public final static int PAGES_INDEXS_SIZE = 5;
	     private int pageSize = PAGESIZE;  
	   
	     private List items;  
	   
	     private int totalCount;  
	   
	     private int[] indexes = new int[0];  
	   
	     private int startIndex = 0;  
	     private int pageCount;
	     private int currentPage;
	     private int pagesIndexsSize = PAGES_INDEXS_SIZE;
	     private int[] pageIndexs = new int[0]; 
	     private int pageIndexsStart;
	     private int pageIndexsEnd;
	     private List headers;
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}

		public PaginationSupport(List items, int totalCount) {  
	         setPageSize(PAGESIZE);  
	                 setTotalCount(totalCount);  
	         setItems(items);          
	         setStartIndex(0);  
	         initPageIndexs();
	     }  
	   
	     public PaginationSupport(List items, int totalCount, int startIndex) {  
	                 setPageSize(PAGESIZE);  
	         setTotalCount(totalCount);  
	         setItems(items);          
	         setStartIndex(startIndex);  
	         initPageIndexs();
	     }  
	   
	     public PaginationSupport(List items, int totalCount, int pageSize, int startIndex) {  
	                 setPageSize(pageSize);  
	         setTotalCount(totalCount);  
	         setItems(items);  
	         setStartIndex(startIndex);  
	         initPageIndexs();
	     }  
	   
	     public List getItems() {  
	         return items;  
	     }  
	   
	     public void setItems(List items) {  
	         this.items = items;  
	     }  
	   
	     public int getPageSize() {  
	         return pageSize;  
	     }  
	   
	     public void setPageSize(int pageSize) {  
	         this.pageSize = pageSize;  
	     }  
	   
	     public int getTotalCount() {  
	         return totalCount;  
	     }  
	   
	     public void setTotalCount(int totalCount) {  
	         if (totalCount > 0) {  
	             this.totalCount = totalCount;  
	             int count = totalCount / pageSize;  
	             if (totalCount % pageSize > 0)  
	                 count++;  
	             indexes = new int[count];  
	             for (int i = 0; i < count; i++) {  
	                 indexes[i] = pageSize * i;  
	             }  
	         } else {  
	             this.totalCount = 0;  
	         }  
	     }  
	   
	     public int[] getIndexes() {  
	         return indexes;  
	     }  
	   
	     public void setIndexes(int[] indexes) {  
	         this.indexes = indexes;  
	     }  
	   
	     public int getStartIndex() {  
	         return startIndex;  
	     }  
	   
	     public void setStartIndex(int startIndex) {  
	         if (totalCount <= 0)  
	             this.startIndex = 0;  
	         else if (startIndex >= totalCount)  
	             this.startIndex = indexes[indexes.length - 1];  
	         else if (startIndex < 0)  
	             this.startIndex = 0;  
	         else {  
	             this.startIndex = indexes[startIndex / pageSize];  
	         }  
	     }  
	   
	     public int getNextIndex() {  
	         int nextIndex = getStartIndex() + pageSize;  
	         if (nextIndex >= totalCount)  
	             return getStartIndex();  
	         else  
	             return nextIndex;  
	     }  
	   
	     public int getPreviousIndex() {  
	         int previousIndex = getStartIndex() - pageSize;  
	         if (previousIndex < 0)  
	             return 0;  
	         else  
	             return previousIndex;  
	     }  
	     public int getPageCount(){
	    	 if(this.pageSize == 0){return 0;}
	    	 if(this.totalCount % this.pageSize == 0){
	    		 return this.totalCount / this.pageSize;
	    	 }
	    	 return this.totalCount / this.pageSize + 1;
	     }
	     public int getCurrentPage() {
	    	 if(this.pageSize == 0){return 0;}
	    	 return this.startIndex / this.pageSize + 1;
		 }

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		
		public int[] getPageIndexs(){
			return this.pageIndexs;
		}

		public int getPagesIndexsSize() {
			return pagesIndexsSize;
		}

		public void setPagesIndexsSize(int pagesIndexsSize) {
			this.pagesIndexsSize = pagesIndexsSize;
		}

		public int getPageIndexsStart() {
			return pageIndexsStart;
		}

		public void setPageIndexsStart(int pageIndexsStart) {
			this.pageIndexsStart = pageIndexsStart;
		}

		public int getPageIndexsEnd() {
			return pageIndexsEnd;
		}

		public void setPageIndexsEnd(int pageIndexsEnd) {
			this.pageIndexsEnd = pageIndexsEnd;
		}

		public List getHeaders() {
			return headers;
		}

		public void setHeaders(List headers) {
			this.headers = headers;
		}

		private void initPageIndexs() {
			int [] tmp = null;
			int [] ins = this.getIndexes();
			int cur = this.getCurrentPage() - 1;
			if(ins.length <= pagesIndexsSize){
				this.pageIndexs = ins;
				this.setPageIndexsStart(1);
				this.setPageIndexsEnd(ins.length);
				return;
			}
			int fromIndex = cur - pagesIndexsSize / 2 <= 0 ? 0 : cur - pagesIndexsSize / 2;
			int endIndex  = fromIndex + pagesIndexsSize > ins.length ? ins.length : fromIndex + pagesIndexsSize;
			if(endIndex - fromIndex < pagesIndexsSize){
				fromIndex = cur - (pagesIndexsSize - (endIndex - cur + 1 )) - 1;
			}
			tmp = new int[endIndex - fromIndex];
			int j = 0;
			for (int i = fromIndex ; i < endIndex; i++,j++) {
				tmp[j] = ins[i];
			}
			this.pageIndexs = tmp;
			this.setPageIndexsStart(fromIndex + 1);
			this.setPageIndexsEnd(endIndex + 1);
		}
}