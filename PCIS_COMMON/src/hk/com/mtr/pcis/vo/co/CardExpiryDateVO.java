package hk.com.mtr.pcis.vo.co;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class CardExpiryDateVO extends AppBaseVO {

	private static final long serialVersionUID = -6947487341575148559L;

	private String coType;
	
	private Integer form;

	private Date expiryDate;
	
	private String description;
	
	private String stationCode;
		
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o.getClass() == this.getClass())) {
			return false;
		}

		CardExpiryDateVO other = (CardExpiryDateVO) o;

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

	public Integer getForm() {
		return form;
	}

	public void setForm(Integer form) {
		this.form = form;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}


}
