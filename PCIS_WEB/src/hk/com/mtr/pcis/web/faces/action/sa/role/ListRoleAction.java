package hk.com.mtr.pcis.web.faces.action.sa.role;

import hk.com.mtr.pcis.facade.sa.RoleFacade;
import hk.com.mtr.pcis.web.faces.action.TxnListBaseAction;

import java.util.List;

import org.jboss.seam.annotations.Name;

import org.jboss.seam.international.StatusMessage;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.vo.AppBaseVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

@Name("listRoleAction")
public class ListRoleAction extends TxnListBaseAction {

	private static final long serialVersionUID = -1023544058976484311L;

	@Override
	public List<RoleVO> fetchPage(PageInfoVO pageInfoVO) throws Exception {
		RoleCriteriaVO roleCriteriaVO = this.loadCriteriaVO(RoleCriteriaVO.class);

		RoleFacade roleFacade = getService(RoleFacade.class);
		List<RoleVO> roleList = null;

		roleList = roleFacade.findAllRoleByPage(roleCriteriaVO, pageInfoVO);

		return roleList;
	}

	@Override
	protected void onLoad() {
		this.sortedExpression.add("roleName");
	}

	@Override
	protected void onCancel() {

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
	protected void onUpdate() {

	}

	@Override
	protected void onDelete() throws Exception {
		RoleFacade roleFacade = getService(RoleFacade.class);
		RoleVO roleVO = (RoleVO) this.currentVO;

		roleFacade.deleteRole(roleVO.getRoleId());
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.roleMaint.deletedSuccess", roleVO.getRoleId());
		this.refresh();

	}

}
