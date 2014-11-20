package hk.com.mtr.pcis.dao.entity.co;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name="CARD_EXPIRY_DATE")
public class CardExpiryDate implements Serializable{

	private static final long serialVersionUID = 2723482729713296989L;

	
	public CardExpiryDate() {
		super();
	}

	public CardExpiryDate(ConsumerGroupMapPK consumerGroupMapPK, Integer form,
			Date expiryDate, String description, String updateUser) {
		super();
		this.consumerGroupMapPK = consumerGroupMapPK;
		this.form = form;
		this.expiryDate = expiryDate;
		this.description = description;
		this.updateUser = updateUser;
	}
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getConsumerGroupMapPK())
									.toHashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof CardExpiryDate)) {
			return false;
		}
		CardExpiryDate other = (CardExpiryDate) object;

		boolean equal = new EqualsBuilder().append(this.getConsumerGroupMapPK(), other.getConsumerGroupMapPK())
										   .isEquals();
		return equal;
	}

	@EmbeddedId
	private ConsumerGroupMapPK consumerGroupMapPK;
	
	@Column(name="FORM", nullable=true, length=2)
	private Integer form;
	
	@Column(name="EXPIRY_DATE", nullable=true)
	private Date expiryDate;
	
	@Column(name="DESCRIPTION", nullable=true, length=30)
	private String description;
	
	@Column(name="UPDUSER", nullable=true, length=15)
	private String updateUser;

	
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

	public ConsumerGroupMapPK getConsumerGroupMapPK() {
		return consumerGroupMapPK;
	}

	public void setConsumerGroupMapPK(ConsumerGroupMapPK consumerGroupMapPK) {
		this.consumerGroupMapPK = consumerGroupMapPK;
	}

}
