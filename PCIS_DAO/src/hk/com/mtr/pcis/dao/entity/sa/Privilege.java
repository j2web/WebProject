package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_PRIVILEGE")
public class Privilege implements Serializable {

	private static final long serialVersionUID = -6854848758601030175L;

	@EmbeddedId
	private PrivilegeId id;

	@Column(name = "UPDUSER", unique = false, nullable = false, length = 15)
	private java.lang.String updateUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDTIME", unique = false, nullable = false, length = 7)
	private Timestamp updateTime;

	public PrivilegeId getId() {
		return this.id;
	}

	public void setId(final PrivilegeId id) {
		this.id = id;
	}

	public java.lang.String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(final java.lang.String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(final Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Privilege)) {
			return false;
		}

		Privilege other = (Privilege) o;
		boolean equal = new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
		return equal;
	}

	@Embeddable
	public static class PrivilegeId implements java.io.Serializable {

		private static final long serialVersionUID = -166541641566515052L;

		@Column(name = "FUNC_ID", unique = false, nullable = false, length = 20)
		private java.lang.String functionId;

		@Column(name = "ROLE_ID", unique = false, nullable = false, length = 20)
		private java.lang.String roleId;

		public PrivilegeId() {

		}

		public PrivilegeId(final java.lang.String functionId, final java.lang.String roleId) {
			this.functionId = functionId;
			this.roleId = roleId;
		}

		public java.lang.String getFunctionId() {
			return this.functionId;
		}

		public void setFunctionId(final java.lang.String functionId) {
			this.functionId = functionId;
		}

		public java.lang.String getRoleId() {
			return this.roleId;
		}

		public void setRoleId(final java.lang.String roleId) {
			this.roleId = roleId;
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(getRoleId()).append(getFunctionId()).toHashCode();
		}

		@Override
		public boolean equals(Object object) {
			if ((this == object))
				return true;
			if (object == null || !(object instanceof PrivilegeId)) {
				return false;
			}
			PrivilegeId other = (PrivilegeId) object;
			boolean equal = new EqualsBuilder().append(this.getRoleId(), other.getRoleId()).append(this.getFunctionId(), other.getFunctionId()).isEquals();
			return equal;
		}
	}
}
