package hk.com.mtr.pcis.vo.co;

import org.apache.commons.lang.builder.HashCodeBuilder;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class CompanyTypeVO extends AppBaseVO {

	private static final long serialVersionUID = -6947487341575148559L;

	private String coType;
	
	private Integer adminFee;
	
	private String description;
		
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o.getClass() == this.getClass())) {
			return false;
		}

		CompanyTypeVO other = (CompanyTypeVO) o;

		if (getCoType() == null) {
			return false;
		}

		return getCoType().equals(other.getCoType());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getCoType()).toHashCode();
	}

	public String getCoType() {
		return coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}

	public Integer getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(Integer adminFee) {
		this.adminFee = adminFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
