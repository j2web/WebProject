package hk.com.mtr.pcis.dao.entity.co;

import hk.com.mtr.pcis.dao.entity.sa.User;

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
@Table(name="COMPANY_TYPE")
public class CompanyType {
	
	public CompanyType() {
		super();
	}

	public CompanyType(String coType, Integer adminFee,
			String description, String updateUser, Timestamp updateTime) {
		super();
		this.coType = coType;
		this.adminFee = adminFee;
		this.description = description;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
	}
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCoType()).toHashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof User)) {
			return false;
		}
		CompanyType other = (CompanyType) object;

		boolean equal = new EqualsBuilder().append(this.getCoType(), other.getCoType()).isEquals();
		return equal;
	}

	@Id
	@Column(name="CO_TYPE", nullable=false, length=7)
	private String coType;
	
	@Column(name="ADMIN_FEE", nullable=true, length=3)
	private Integer adminFee;
	
	@Column(name="DESCRIPTION", nullable=true, length=50)
	private String description;
	
	@Column(name="UPDUSER", nullable=true, length=15)
	private String updateUser;

	@Column(name="UPDTIME", nullable=false)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Timestamp updateTime;
	
	public String getCoType() {
		return coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}

	public Integer getAdminFee() {
		return adminFee;
	}

	public void setAdminFree(Integer adminFee) {
		this.adminFee = adminFee;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
