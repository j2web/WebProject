package hk.com.mtr.pcis.criteria;

import java.io.Serializable;
import java.util.List;

import hk.com.mtr.pcis.enums.OrderMode;

public class PageInfoVO implements Serializable {

	private static final long serialVersionUID = 1092049111525377235L;

	public PageInfoVO(List<String> sortedExpression, List<OrderMode> order) {
		super();
		this.sortedExpression = sortedExpression;
		this.order = order;
	}

	public PageInfoVO(int currentPageIndex, int pageSize, List<String> sortedExpression, List<OrderMode> order) {
		super();
		this.currentPageIndex = currentPageIndex;
		this.pageSize = pageSize;
		this.sortedExpression = sortedExpression;
		this.order = order;
	}

	private int currentPageIndex;

	private int recordCount;

	private int pageSize;

	private List<String> sortedExpression;

	public List<String> getSortedExpression() {
		return sortedExpression;
	}

	public void setSortedExpression(List<String> sortedExpression) {
		this.sortedExpression = sortedExpression;
	}

	public List<OrderMode> getOrder() {
		return order;
	}

	public void setOrder(List<OrderMode> order) {
		this.order = order;
	}

	private List<OrderMode> order;

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getStartRow() {
		return (currentPageIndex - 1) * pageSize;
	}

}
