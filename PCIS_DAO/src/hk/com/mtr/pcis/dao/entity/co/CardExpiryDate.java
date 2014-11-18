package hk.com.mtr.pcis.dao.entity.co;

import hk.com.mtr.pcis.dao.entity.sa.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name="CARD_EXPIRY_DATE")
public class CardExpiryDate implements Serializable{

	private static final long serialVersionUID = 2723482729713296989L;

	public CardExpiryDate() {
		super();
	}

	public CardExpiryDate(String coType, Integer form, Date expiryDate,
			String description, String updateUser, Timestamp updateTime) {
		super();
		this.coType = coType;
		this.form = form;
		this.expiryDate = expiryDate;
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
		CardExpiryDate other = (CardExpiryDate) object;

		boolean equal = new EqualsBuilder().append(this.getCoType(), other.getCoType()).isEquals();
		return equal;
	}

	@Id
	@Column(name="CO_TYPE", nullable=false, length=7)
	private String coType;
	
	@Column(name="FORM", nullable=true, length=2)
	private Integer form;
	
	@Column(name="EXPIRY_DATE", nullable=true)
	private Date expiryDate;
	
	@Column(name="DESCRIPTION", nullable=true, length=30)
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
