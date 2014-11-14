package hk.com.mtr.pcis.web.faces.action.sa.parameter;

import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessage;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.facade.sa.ParameterFacade;
import hk.com.mtr.pcis.vo.AppBaseVO;
import hk.com.mtr.pcis.vo.sa.ParameterVO;
import hk.com.mtr.pcis.web.faces.action.TxnListBaseAction;
import hk.com.mtr.pcis.web.faces.util.ListboxUtil;

@Name("listParamAction")
public class ListParameterAction extends TxnListBaseAction {

	private static final long serialVersionUID = 1L;

	private ParameterCriteriaVO paramCriteriaVO;

	@Override
	protected void onCancel() {

	}

	@Override
	protected void onDelete() {

	}

	@Override
	protected void onEdit() {

	}

	@Override
	protected AppBaseVO onNew() {
		return null;
	}

	@Override
	protected void onSave() {

	}

	@Override
	protected void onUpdate() throws Exception {
		ParameterFacade paramFacade = this.getService(ParameterFacade.class);
		ParameterVO paramVO = (ParameterVO) this.currentVO;

		paramFacade.updateParam(paramVO);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.paramMaint.updatedSuccess", paramVO.getParamId());
		ListboxUtil.clearParameterVOMap();
		this.refresh();

	}

	@Override
	protected List<ParameterVO> fetchPage(PageInfoVO pageInfoVO) throws Exception {
		List<ParameterVO> paramVOList = null;
		ParameterFacade paramFacade = this.getService(ParameterFacade.class);

		paramVOList = paramFacade.findAllParamByPage(paramCriteriaVO, pageInfoVO);

		return paramVOList;
	}

	@Override
	protected void onLoad() {
		paramCriteriaVO = this.loadCriteriaVO(ParameterCriteriaVO.class);
		this.sortedExpression.add("paramId");
		this.pageSize = 1000;
	}

	public ParameterCriteriaVO getParamCriteriaVO() {
		return paramCriteriaVO;
	}

	public void setParamCriteriaVO(ParameterCriteriaVO paramCriteriaVO) {
		this.paramCriteriaVO = paramCriteriaVO;
	}
}
