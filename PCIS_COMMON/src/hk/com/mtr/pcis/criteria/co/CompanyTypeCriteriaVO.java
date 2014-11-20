package hk.com.mtr.pcis.criteria.co;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

//创建一个实体类PCI_2002 的属性
public class CompanyTypeCriteriaVO extends AppBaseCriteriaVO {

	private static final long serialVersionUID = -413904993761692198L;

	private String coType;
	
	private Integer adminFee;
	
	private String description;

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
