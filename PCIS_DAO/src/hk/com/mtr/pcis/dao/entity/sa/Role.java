package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = -4936690808431428897L;

	public Role() {

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getRoleId()).toHashCode();

	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof Role)) {
			return false;
		}
		Role other = (Role) object;

		boolean equal = new EqualsBuilder().append(this.getRoleId(), other.getRoleId()).isEquals();
		return equal;
	}

	@Id
	@Column(name = "ROLE_ID", nullable = false, length = 20)
	private String roleId;

	@Column(name = "ROLE_NAME", nullable = true, length = 30)
	private String roleName;

	@Column(name = "ROLE_DESC", nullable = true, length = 30)
	private String roleDesc;

	@Column(name = "UPDUSER", nullable = false, length = 15)
	private String updateUser;

	@Column(name = "UPDTIME", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Timestamp updateTime;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PCI_USER_IN_ROLE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Set<User> userList = new HashSet<User>(0);

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Set<User> getUserList() {
		return userList;
	}

	public void setUserList(Set<User> userList) {
		this.userList = userList;
	}

}
