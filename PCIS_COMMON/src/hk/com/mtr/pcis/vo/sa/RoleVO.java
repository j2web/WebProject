package hk.com.mtr.pcis.vo.sa;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class RoleVO extends AppBaseVO {

	private static final long serialVersionUID = 6552233233607889849L;

	private String roleId;

	private String roleName;

	private String roleDesc;

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

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
