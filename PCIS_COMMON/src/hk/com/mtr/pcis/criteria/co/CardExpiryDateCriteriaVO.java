package hk.com.mtr.pcis.criteria.co;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

import java.util.Date;

public class CardExpiryDateCriteriaVO extends AppBaseCriteriaVO {

	private static final long serialVersionUID = -413904993761692198L;

	private String coType;
	
	private Integer form;

	private Date expiryDate;
	
	private String description;

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
	
	
}
