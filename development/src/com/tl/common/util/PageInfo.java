package com.tl.common.util;

import com.tl.common.SystemConstants;

public class PageInfo {

	/*
	 * ������ͨ��ҳ�����ֶ�
	 */
	private int pageSize = SystemConstants.PAGE_SIZE;// ҳ��

	private int totalRows = 0;// �ܼ�¼��

	private int totalPages = 0;// ��ҳ��

	private int currentPage = 1;// ��ǰҳ��

	private int startIndex = 0;// ��ʼ����

	private int lastIndex = 0;// ��������

	private boolean hasNextPage = false;// �Ƿ������һҳ

	private boolean hasPerviousPage = false;// �Ƿ������һҳ

	private int nextPage = 0;// ��һҳ��ҳ��

	private int perviousPage = 0;// ��һҳ��ҳ��

	/*
	 * ���建���ҳ��Ҫ���ֶ�
	 */
	private int cacheSize = SystemConstants.CACHE_RECORD_SIZE;// ���û����ҳһ�ζ�ȡ���ݵ�����

	private int queryPointer = 0;// �����������ݿ��ѯ�������ָ��

	private int totalCache = 0;// ���ý�����ĸ���

	private boolean outOfBound = false;// ����ʵļ�¼�Ƿ񳬳������

	private int beginRow = 0;// ���ý������ʼ��

	private int endRow = 0;// ���ý����������

	/*
	 * ����������
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
	 * �����ҳ ��������*
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
