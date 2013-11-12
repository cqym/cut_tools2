package com.tl.common.util;

import com.tl.common.SystemConstants;

public class PageInfo {

	/*
	 * 定义普通分页所需字段
	 */
	private int pageSize = SystemConstants.PAGE_SIZE;// 页长

	private int totalRows = 0;// 总记录数

	private int totalPages = 0;// 总页数

	private int currentPage = 1;// 当前页号

	private int startIndex = 0;// 起始条数

	private int lastIndex = 0;// 结束条数

	private boolean hasNextPage = false;// 是否存在下一页

	private boolean hasPerviousPage = false;// 是否存在上一页

	private int nextPage = 0;// 下一页的页号

	private int perviousPage = 0;// 上一页的页号

	/*
	 * 定义缓存分页需要的字段
	 */
	private int cacheSize = SystemConstants.CACHE_RECORD_SIZE;// 设置缓存分页一次读取数据的数量

	private int queryPointer = 0;// 设置连接数据库查询结果集的指针

	private int totalCache = 0;// 设置结果集的个数

	private boolean outOfBound = false;// 需访问的记录是否超出结果集

	private int beginRow = 0;// 设置结果集起始行

	private int endRow = 0;// 设置结果集结束行

	/*
	 * 属性设置器
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPerviousPage() {
		return hasPerviousPage;
	}

	public void setHasPerviousPage(boolean hasPerviousPage) {
		this.hasPerviousPage = hasPerviousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPerviousPage() {
		return perviousPage;
	}

	public void setPerviousPage(int perviousPage) {
		this.perviousPage = perviousPage;
	}

	/**
	 * 缓存分页 属性设置*
	 */

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public int getQueryPointer() {
		return queryPointer;
	}

	public void setQueryPointer(int queryPointer) {
		this.queryPointer = queryPointer;
	}

	public int getTotalCache() {
		return totalCache;
	}

	public void setTotalCache(int totalCache) {
		this.totalCache = totalCache;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public boolean isOutOfBound() {
		return outOfBound;
	}

	public void setOutOfBound(boolean outOfBound) {
		this.outOfBound = outOfBound;
	}

}
