package hk.com.mtr.pcis.web.faces.action;

import java.io.Serializable;
import java.util.List;

public class PageData implements Serializable {

	private static final long serialVersionUID = -7906993813830752270L;

	private int rowCount;
	private int startRow;

	private List<?> searchResult;

	public PageData(int rowCount, int startRow, List<?> searchResult) {

		this.rowCount = rowCount;
		this.startRow = startRow;
		this.searchResult = searchResult;

	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setSearchResult(List<?> searchResult) {
		this.searchResult = searchResult;
	}

	public int getRowCount() {
		return rowCount;

	}

	public int getStartRow() {
		return startRow;

	}

	public List<?> getSearchResult() {
		return searchResult;

	}

}
