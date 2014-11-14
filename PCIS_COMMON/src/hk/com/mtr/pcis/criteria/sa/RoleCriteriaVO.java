package hk.com.mtr.pcis.criteria.sa;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

public class RoleCriteriaVO extends AppBaseCriteriaVO {

	private static final long serialVersionUID = 5212288331790911550L;

	private String roleId;

	private String roleName;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
