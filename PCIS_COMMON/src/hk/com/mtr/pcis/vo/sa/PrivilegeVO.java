package hk.com.mtr.pcis.vo.sa;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class PrivilegeVO extends AppBaseVO {

	private static final long serialVersionUID = 2599959926325519663L;
	private java.lang.String functionId;

	public java.lang.String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(java.lang.String functionId) {
		this.functionId = functionId;
	}

	private java.lang.String roleId;

	public PrivilegeVO() {
	}

	public java.lang.String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(java.lang.String value) {
		this.roleId = value;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getFunctionId()).append(this.getRoleId()).toHashCode();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof PrivilegeVO)) {
			return false;
		}

		PrivilegeVO other = (PrivilegeVO) o;
		return new EqualsBuilder().append(this.getFunctionId(), other.getFunctionId()).append(this.getRoleId(), other.getRoleId()).isEquals();
	}
}
