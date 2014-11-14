package hk.com.mtr.pcis.web.faces.action;

import hk.com.mtr.pcis.web.faces.util.FacesUtil;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

public abstract class SearchBaseAction extends AppBaseAction {

	private static final long serialVersionUID = 6087471315396004173L;

	public String doSearch() {
		return onSearch();
	}

	public void doReset() {
		onReset();
	}

	protected void saveCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		FacesUtil.saveCriteriaVO(appBaseCriteriaVO);
	}

	protected void resetCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		FacesUtil.resetCriteriaVO(appBaseCriteriaVO);
	}

	protected abstract String onSearch();

	protected abstract void onReset();
}
