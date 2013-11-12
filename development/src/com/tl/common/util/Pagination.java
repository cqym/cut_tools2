package com.tl.common.util;

public class Pagination {

	private PageInfo pageInfo = new PageInfo();

	/**
	 * �չ���
	 */
	public Pagination() {
	}

	/**
	 * ���ι��죬�봫���ܼ�¼��
	 * 
	 * @param totalRows
	 */
	public Pagination(int totalRows) {
		int size = pageInfo.getPageSize();
		setPageInfo(totalRows, size);

	}

	/**
	 * ���صĹ��캯�����봫���ܼ�¼����ҳ��
	 * 
	 * @param totalRows
	 * @param pageSize
	 */
	public Pagination(int totalRows, int pageSize) {
		setPageInfo(totalRows, pageSize);

		pageInfo.setPageSize(pageSize);
	}

	/**
	 * ���÷�ҳ��װ�����Ϣ
	 * 
	 * @param totalRows
	 */
	public void setPageInfo(int totalRows, int pageSize) {
		int init = 0;
		int step = 1;
		pageInfo.setTotalRows(totalRows);
		pageInfo.setCurrentPage(step);
		pageInfo.setHasPerviousPage(false);
		pageInfo.setStartIndex(init);
		pageInfo.setLastIndex(this.getEndIndex(pageInfo));

		setTotalPages(pageSize);

		this.judgeHasNextPage(pageInfo);
		if (pageInfo.isHasNextPage())
			pageInfo.setNextPage(pageInfo.getCurrentPage() + step);

		setTotalCaches();

		pageInfo.setBeginRow(init);
		pageInfo.setEndRow(pageInfo.getCacheSize());
	}

	/**
	 * ������ҳ��
	 * 
	 * @param pageSize
	 */
	public void setTotalPages(int pageSize) {
		if (pageInfo.getTotalRows() % pageSize == 0) {
			pageInfo.setTotalPages(pageInfo.getTotalRows()
					/ pageInfo.getPageSize());
		} else {
			pageInfo.setTotalPages(pageInfo.getTotalRows() / pageSize + 1);
		}
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * ��ҳ
	 * 
	 * @return PageInfo
	 */
	public PageInfo startPage() {
		this.judgeHasBeforePage(pageInfo);
		this.judgeHasNextPage(pageInfo);

		pageInfo.setCurrentPage(1);
		pageInfo.setStartIndex(0);
		pageInfo.setLastIndex(pageInfo.getPageSize());

		return pageInfo;
	}

	/**
	 * Ĭ��βҳ
	 * 
	 * @return PageInfo
	 */
	public PageInfo lastPage() {
		this.judgeHasBeforePage(pageInfo);
		this.judgeHasNextPage(pageInfo);

		pageInfo.setCurrentPage(pageInfo.getTotalPages());
		pageInfo.setStartIndex((pageInfo.getTotalPages() - 1)
				* pageInfo.getPageSize());
		pageInfo.setLastIndex(pageInfo.getTotalRows());

		return pageInfo;
	}

	/**
	 * ǰһҳ
	 * 
	 * @return PageInfo
	 */
	public PageInfo beforePage() {
		this.judgeHasBeforePage(pageInfo);

		if (pageInfo.isHasPerviousPage()) {
			pageInfo.setCurrentPage(pageInfo.getCurrentPage() - 1);
			pageInfo.setStartIndex((pageInfo.getCurrentPage() - 1)
					* pageInfo.getPageSize());
			pageInfo.setLastIndex(pageInfo.getStartIndex()
					+ pageInfo.getPageSize());
		} else
			pageInfo = startPage();

		return pageInfo;
	}

	/**
	 * ��һҳ
	 * 
	 * @return
	 */
	public PageInfo nextPage() {
		this.judgeHasNextPage(pageInfo);

		if (pageInfo.isHasNextPage()) {
			pageInfo.setStartIndex(pageInfo.getCurrentPage()
					* pageInfo.getPageSize());
			pageInfo.setLastIndex(pageInfo.getStartIndex()
					+ pageInfo.getPageSize());
			pageInfo.setCurrentPage(pageInfo.getCurrentPage() + 1);
		} else
			pageInfo = lastPage();

		return pageInfo;
	}

	/**
	 * ��תҳ
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return PageInfo
	 */

	public PageInfo gotoPage(int pageNo) {

		if (pageNo > 0 && pageNo <= pageInfo.getTotalPages()) {
			pageInfo.setCurrentPage(pageNo);
			pageInfo.setStartIndex((pageNo - 1) * pageInfo.getPageSize());
		} else if (pageNo > pageInfo.getTotalPages()) {
			pageInfo.setCurrentPage(pageInfo.getTotalPages());
			pageInfo.setStartIndex((pageInfo.getCurrentPage() - 1)
					* pageInfo.getPageSize());
		} else {
			pageInfo.setCurrentPage(1);
			pageInfo.setStartIndex(0);
		}
		pageInfo
				.setLastIndex(pageInfo.getStartIndex() + pageInfo.getPageSize());

		return pageInfo;
	}

	/**
	 * ����β������
	 * 
	 * @param pageInfo
	 * @return
	 */
	public int getEndIndex(PageInfo pageInfo) {
		int endIndex = 0;

		int totalRows = pageInfo.getTotalRows();
		int currentPage = pageInfo.getCurrentPage();
		int totalPages = pageInfo.getTotalPages();
		int pageSize = pageInfo.getPageSize();

		if (totalRows < pageSize) {
			endIndex = pageInfo.getTotalRows();
		} else if ((totalRows % pageSize == 0)
				|| (totalRows % pageSize != 0 && currentPage < pageSize)) {
			endIndex = currentPage * pageSize;
		} else if (totalRows % pageSize != 0 && currentPage == totalPages) {
			endIndex = totalRows;// ���һҳ
		}

		return endIndex;
	}

	/**
	 * �ж��Ƿ�����һҳ
	 * 
	 * @param pageInfo
	 * @return
	 */
	public boolean judgeHasBeforePage(PageInfo pageInfo) {
		boolean hasBeforePage = false;

		if (pageInfo.getCurrentPage() > 1)
			pageInfo.setHasPerviousPage(true);
		else
			pageInfo.setHasPerviousPage(false);

		return hasBeforePage;
	}

	/**
	 * �ж��Ƿ�����һҳ
	 * 
	 * @param pageInfo
	 * @return
	 */
	public boolean judgeHasNextPage(PageInfo pageInfo) {
		boolean hasNextPage = false;

		if (pageInfo.getCurrentPage() < pageInfo.getTotalPages())
			hasNextPage = true;
		else
			hasNextPage = false;

		pageInfo.setHasNextPage(hasNextPage);

		return hasNextPage;
	}

	/**
	 * ���û�����������
	 */
	public void setTotalCaches() {
		if (pageInfo.getTotalRows() % pageInfo.getCacheSize() == 0) {
			pageInfo.setTotalCache(pageInfo.getTotalRows()
					/ pageInfo.getCacheSize());
		} else {
			pageInfo.setTotalCache(pageInfo.getTotalRows()
					/ pageInfo.getCacheSize() + 1);
		}
	}

	/**
	 * �ж����ѯ�������Ƿ񳬳�������߽�
	 * 
	 * @param pageInfo
	 */
	public boolean judgeOutOfBounds(PageInfo pageInfo) {
		boolean outOfBound = false;
		if (pageInfo.getStartIndex() >= pageInfo.getEndRow()
				|| pageInfo.getLastIndex() <= pageInfo.getBeginRow()
				|| (pageInfo.getStartIndex() < pageInfo.getEndRow() && pageInfo
						.getLastIndex() > pageInfo.getEndRow())
				|| (pageInfo.getLastIndex() > pageInfo.getBeginRow() && pageInfo
						.getStartIndex() < pageInfo.getBeginRow()))
			outOfBound = true;

		pageInfo.setOutOfBound(outOfBound);
		return outOfBound;
	}

	/**
	 * �����������ѯ���������ʼ�ͽ�������
	 * 
	 * @param pageInfo
	 */
	public void resetResultPointer(PageInfo pageInfo) {
		int queryPointer = pageInfo.getQueryPointer();
		queryPointer = queryPointer < 1 ? 1 : queryPointer;

		judgeOutOfBounds(pageInfo);

		if (pageInfo.isOutOfBound())
			pageInfo.setBeginRow((queryPointer - 1) * pageInfo.getCacheSize());

		pageInfo.setEndRow(pageInfo.getBeginRow() + pageInfo.getCacheSize());

	}

}
