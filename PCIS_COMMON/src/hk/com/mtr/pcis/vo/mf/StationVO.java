package hk.com.mtr.pcis.vo.mf;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class StationVO extends AppBaseVO {

	private static final long serialVersionUID = -1026096585381865316L;

	public int hashCode() {
		return new HashCodeBuilder().append(this.getStationNo()).toHashCode();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof StationVO)) {
			return false;
		}

		StationVO other = (StationVO) o;
		return new EqualsBuilder().append(this.getStationNo(), other.getStationNo()).isEquals();
	}

	private Integer stationNo;
	private String stationCode;
	private String stationName;
	private String emailAddress;

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
}
