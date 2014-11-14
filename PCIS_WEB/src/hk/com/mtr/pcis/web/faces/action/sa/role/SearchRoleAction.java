package hk.com.mtr.pcis.web.faces.action.sa.role;

import hk.com.mtr.pcis.web.faces.action.SearchBaseAction;

import org.jboss.seam.annotations.Name;

import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
@Name("searchRoleAction")
public class SearchRoleAction extends SearchBaseAction {

	private static final long serialVersionUID = 3630472932837464501L;

	private RoleCriteriaVO roleCriteriaVO;

	public RoleCriteriaVO getRoleCriteriaVO() {
		return roleCriteriaVO;
	}

	public void setRoleCriteriaVO(RoleCriteriaVO roleCriteriaVO) {
		this.roleCriteriaVO = roleCriteriaVO;
	}

	@Override
	protected void onReset() {
		this.resetCriteriaVO(roleCriteriaVO);
	}

	@Override
	protected String onSearch() {
		this.saveCriteriaVO(roleCriteriaVO);
		return "/sa/role/listRole.xhtml";
	}

	@Override
	protected void onLoad() {
		roleCriteriaVO = this.loadCriteriaVO(RoleCriteriaVO.class);

	}

}
