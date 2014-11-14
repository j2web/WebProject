package hk.com.mtr.pcis.vo.sa;


import org.apache.commons.lang.builder.HashCodeBuilder;
import hk.com.mtr.pcis.vo.AppBaseVO;

public class UserVO extends AppBaseVO {

	private static final long serialVersionUID = 7813159076313318093L;

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o.getClass() == this.getClass())) {
			return false;
		}

		UserVO other = (UserVO) o;

		if (getUserId() == null) {
			return false;
		}

		return getUserId().equals(other.getUserId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getUserId()).toHashCode();
	}

	public UserVO() {

	}

	private String userId;

	private String userName;

	private Integer resStationNo;

	private Integer teamNo;

	private String remark;
	
	private String stationCode;

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

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	
}
