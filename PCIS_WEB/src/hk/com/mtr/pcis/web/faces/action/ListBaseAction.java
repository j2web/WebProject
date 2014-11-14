package hk.com.mtr.pcis.web.faces.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.enums.OrderMode;
import hk.com.mtr.pcis.enums.PageMode;

public abstract class ListBaseAction extends AppBaseAction {

	private static final long serialVersionUID = -6447723718577997572L;

	protected int currentPageIndex = 1;

	protected int pageSize = 20;

	// protected int recordCount;

	protected List<String> sortedExpression = new ArrayList<String>();

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

	protected PageMode mode = PageMode.SEARCH;

	protected boolean search;

	public boolean isSearch() {
		search = mode.equals(PageMode.SEARCH);
		return search;
	}

	protected List<OrderMode> order = new ArrayList<OrderMode>();

	protected PageDataModel pageDataModel;

	public void refresh() {
		mode = PageMode.SEARCH;
		currentPageIndex = 1;
		pageDataModel = null;
		pageDataModel = getPageDataModel();

	}

	protected abstract List<?> fetchPage(PageInfoVO pageInfoVO) throws Exception;

	private PageData getPageData(int startRow, int pageSize) {
		PageInfoVO pageInfoVO = new PageInfoVO(this.currentPageIndex, pageSize, this.sortedExpression, this.order);
		List<?> result = null;
		try {
			result = fetchPage(pageInfoVO);
		} catch (Exception e) {
			this.handleException(e);
		}
		PageData pageData = null;
		if (result != null){
			pageData = new PageData(pageInfoVO.getRecordCount(), startRow, result);
//			System.out.println("Record Count:"+pageInfoVO.getRecordCount()+"  :TEST");
//			System.out.println(order.size());
//			System.out.println(order);
//			System.out.println(sortedExpression);
		}
			
		return pageData;
	}
//	public static void main(String[] args) throws Exception{
//		List<OrderMode> order = new ArrayList<OrderMode>();
//		List<String> sortedExpression = new ArrayList<String>();
//		PageInfoVO pageInfoVO = new PageInfoVO(5, 10, sortedExpression, order);
//		System.out.println(pageInfoVO.getRecordCount());
//	}
	@SuppressWarnings("serial")
	public PageDataModel getPageDataModel() {

		if (pageDataModel == null) {
			pageDataModel = new PageDataModel(pageSize) {

				public PageData fetchPage(int startRow, int pageSize) {
					return getPageData(startRow, pageSize);
				}
			};

		}
		return pageDataModel;
	}

	public void sort(ActionEvent event) {
		UIComponent component = event.getComponent();
		for (UIComponent child : component.getChildren()) {
			if (child instanceof UIParameter) {
				UIParameter param = (UIParameter) child;
				if ("sortColumn".equals(param.getName())) {
					String sortColumn = (String) param.getValue();
					if (this.sortedExpression.size() == 1) {
						String sortedColumn = sortedExpression.get(0);
						OrderMode orderMode = OrderMode.ASC;

						if (this.order.size() > 0)
							orderMode = this.order.get(0);
						if (sortColumn.equals(sortedColumn)) {
							if (orderMode == OrderMode.ASC) {
								orderMode = OrderMode.DESC;
							} else {
								orderMode = OrderMode.ASC;
							}
						} else {
							orderMode = OrderMode.ASC;
						}
						this.sortedExpression.clear();
						this.sortedExpression.add(sortColumn);
						this.order.clear();
						this.order.add(orderMode);

					} else {

						OrderMode orderMode = OrderMode.ASC;

						this.sortedExpression.clear();
						this.sortedExpression.add(sortColumn);
						this.order.clear();
						this.order.add(orderMode);
					}
				}
			}
		}
		log.debug("sort by #0 #1", this.sortedExpression, this.order);
		this.refresh();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageMode getMode() {
		return mode;
	}

	public void setMode(PageMode mode) {
		this.mode = mode;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

}
