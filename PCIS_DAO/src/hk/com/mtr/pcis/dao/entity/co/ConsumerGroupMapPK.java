package hk.com.mtr.pcis.dao.entity.co;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class ConsumerGroupMapPK implements Serializable {
	
	private static final long serialVersionUID = -2397210668922620454L;

	@Column(name="CO_TYPE", nullable=false, length=7)
	private String coType;
	
	@Column(name="UPDTIME", nullable=false)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Timestamp updateTime;
	
	public ConsumerGroupMapPK(String coType, Timestamp updateTime) {
		super();
		this.coType = coType;
		this.updateTime = updateTime;
	}

	public ConsumerGroupMapPK() {
		super();
	}

	public String getCoType() {
		return coType;
	}

	public void setCoType(String coType) {
		this.coType = coType;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getCoType())
									.append(this.getUpdateTime()).toHashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof ConsumerGroupMapPK)) {
			return false;
		}
		ConsumerGroupMapPK other = (ConsumerGroupMapPK) object;
		boolean equal = new EqualsBuilder().append(this.getCoType(), other.getCoType())
											.append(this.getUpdateTime(), other.getUpdateTime())
											.isEquals();
		return equal;
	}
}