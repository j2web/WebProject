package hk.com.mtr.pcis.criteria.sa;


import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

public class UserCriteriaVO extends AppBaseCriteriaVO {

	private static final long serialVersionUID = -8418045331481101018L;

	private String userId;

	private String userName;

	private Long resSationNo;

	private Long teamNo;

	private String remark;
	
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

	public Long getResSationNo() {
		return resSationNo;
	}

	public void setResSationNo(Long resSationNo) {
		this.resSationNo = resSationNo;
	}

	public Long getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Long teamNo) {
		this.teamNo = teamNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
