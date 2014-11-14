package hk.com.mtr.pcis.web.faces.action;

import java.util.List;

import org.jboss.seam.annotations.In;

import hk.com.mtr.pcis.enums.PageMode;
import hk.com.mtr.pcis.enums.RowMode;
import hk.com.mtr.pcis.vo.AppBaseVO;

public abstract class TxnListBaseAction extends ListBaseAction {

	private static final long serialVersionUID = 3644390182477801428L;

	@In(required = false, value = "vo")
	protected AppBaseVO currentVO;

	protected boolean add;

	@SuppressWarnings("unchecked")
	public void doNew() {

		this.mode = PageMode.ADD;

		List list = this.getPageDataModel().pageData.getSearchResult();

		AppBaseVO vo = null;
		try {
			vo = onNew();
		} catch (Exception e) {
			this.handleException(e);
		}
		if (vo != null) {
			vo.setMode(RowMode.ADD);
			list.add(0, vo);
			pageDataModel.pageData.setRowCount(pageDataModel.pageData.getRowCount() + 1);
		} else {
			log.warn("cannot get new VO with RowMode=ADD.");
		}

	}

	public void doSave() {
		try {
			onSave();
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void doEdit() {
		this.mode = PageMode.EDIT;
		this.currentVO.setMode(RowMode.EDIT);
		try {
			onEdit();
		} catch (Exception e) {
			this.handleException(e);
		}

	}

	public void doUpdate() {
		try {
			onUpdate();
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void doDelete() {
		try {
			onDelete();
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void doCancel() {
		mode = PageMode.SEARCH;
		pageDataModel = null;
		pageDataModel = getPageDataModel();
		this.currentVO = null;

		try {
			onCancel();
		} catch (Exception e) {
			this.handleException(e);
		}

	}

	protected abstract AppBaseVO onNew() throws Exception;

	protected abstract void onSave() throws Exception;

	protected abstract void onEdit() throws Exception;

	protected abstract void onUpdate() throws Exception;

	protected abstract void onDelete() throws Exception;

	protected abstract void onCancel() throws Exception;

	public boolean isAdd() {
		add = mode == PageMode.ADD;
		return add;
	}

	public AppBaseVO getCurrentVO() {
		return currentVO;
	}

	public void setCurrentVO(AppBaseVO currentVO) {
		this.currentVO = currentVO;
	}

}
