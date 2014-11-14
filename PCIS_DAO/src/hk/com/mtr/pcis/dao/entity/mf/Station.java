package hk.com.mtr.pcis.dao.entity.mf;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "STATION_MASTER")
public class Station implements Serializable {

	private static final long serialVersionUID = -5544363435848639885L;

	public int hashCode() {
		return new HashCodeBuilder().append(this.getStationNo()).toHashCode();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Station)) {
			return false;
		}

		Station other = (Station) o;
		boolean equal = new EqualsBuilder().append(this.getStationNo(), other.getStationNo()).isEquals(); 
		return equal;
	}

	@Id
	@Column(name = "STATION_NO", nullable = false)
	private Integer stationNo;

	@Column(name = "STATION_CODE", nullable = false, length = 4)
	private String stationCode;

	@Column(name = "STATION_NAME", nullable = false, length = 80)
	private String stationName;

	@Column(name = "EMAIL_ADDR", nullable = false, length = 40)
	private String emailAddress;

	@Column(name = "UPDUSER", nullable = false, length = 15)
	private String updateUser;

	@Column(name = "UPDTIME", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Timestamp updateTime;
	
	public Integer getStationNo() {
		return stationNo;
	}

	public void setStationNo(Integer stationNo) {
		this.stationNo = stationNo;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
}
