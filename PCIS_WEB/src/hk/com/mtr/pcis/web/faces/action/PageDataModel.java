package hk.com.mtr.pcis.web.faces.action;

import java.io.Serializable;

import javax.faces.model.DataModel;

public abstract class PageDataModel extends DataModel implements Serializable {

	private static final long serialVersionUID = -2170909030160195866L;
	int pageSize;
	int rowIndex;
	public PageData pageData;

	public PageDataModel(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.pageData = null;

	}

	public void setWrappedData(Object o) {
		if (o instanceof PageData) {
			this.pageData = (PageData) o;
		} else {
			throw new UnsupportedOperationException(" setWrappedData ");
		}
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int index) {
		rowIndex = index;
	}

	public int getRowCount() {
		return getPageData().getRowCount();
	}

	private PageData getPageData() {
		if (pageData != null) {
			return pageData;
		}
		int rowIndex = getRowIndex();
		int startRow = rowIndex;
		if (rowIndex == -1) {
			// even when no row is selected, we still need a page
			// object so that we know the amount of data available.
			startRow = 0;
		} // invoke method on enclosing class
		pageData = fetchPage(startRow, pageSize);
		return pageData;
	}

	public Object getRowData() {
		if (rowIndex < 0) {
			throw new IllegalArgumentException(" Invalid rowIndex for PagedListDataModel; not within page ");
		} // ensure page exists; if rowIndex is beyond dataset size, then
		// we should still get back a DataPage object with the dataset size
		// in it
		if (pageData == null) {
			pageData = fetchPage(rowIndex, pageSize);
		}
		int datasetSize = pageData.getRowCount();
		int startRow = pageData.getStartRow();
		int nRows = pageData.getSearchResult().size();
		int endRow = startRow + nRows;
		if (rowIndex >= datasetSize) {
			throw new IllegalArgumentException(" Invalid rowIndex ");
		}
		if (rowIndex < startRow) {
			pageData = fetchPage(rowIndex, pageSize);
			startRow = pageData.getStartRow();
		} else if (rowIndex >= endRow) {
			pageData = fetchPage(rowIndex, pageSize);
			startRow = pageData.getStartRow();
		}

		return pageData.getSearchResult().get(Integer.parseInt(String.valueOf(rowIndex - startRow)));
	}

	public Object getWrappedData() {
		return pageData.getSearchResult();
	}

	public boolean isRowAvailable() {
		PageData pageData = getPageData();
		if (pageData == null) {
			return false;
		}
		int rowIndex = getRowIndex();
		if (rowIndex < 0) {
			return false;
		} else if (rowIndex >= pageData.getRowCount()) {
			return false;
		} else {
			return true;
		}
	}

	public abstract PageData fetchPage(int startRow, int pageSize);

}
