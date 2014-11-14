package hk.com.mtr.pcis.dao.entity.sa;

import hk.com.mtr.pcis.dao.entity.mf.Station;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 8880176079629993401L;

	public User() {

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getUserId()).toHashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof User)) {
			return false;
		}
		User other = (User) object;

		boolean equal = new EqualsBuilder().append(this.getUserId(), other.getUserId()).isEquals();
		return equal;
	}

	@Id
	@Column(name = "USER_ID", nullable = false, length = 15)
	private String userId;

	@Column(name = "USER_NAME", nullable = false, length = 30)
	private String userName;

	@Column(name = "RES_STN_NO", nullable = true, length = 3)
	private Integer resStationNo;

	@Column(name = "TEAM_NO", nullable = true, length = 2)
	private Integer teamNo;

	@Column(name = "REMARK", nullable = true, length = 160)
	private String remark;

	@Column(name = "UPDUSER", nullable = false, length = 15)
	private String updateUser;

	@Column(name = "UPDTIME", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Timestamp updateTime;

	@ManyToMany(mappedBy = "userList")
	private Set<Role> roleList;

	@ManyToOne(fetch=FetchType.LAZY)    
	@JoinColumn(name="RES_STN_NO")
	private Station station;
	
	public Station getStation() {
	return station;
	}
	public void setStation(Station station) {
	this.station = station;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getResStationNo() {
		return resStationNo;
	}

	public void setResStationNo(Integer resStationNo) {
		this.resStationNo = resStationNo;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

}
