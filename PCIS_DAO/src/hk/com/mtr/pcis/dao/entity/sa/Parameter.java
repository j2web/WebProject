package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PARAM_MAINT")
public class Parameter implements Serializable {

	private static final long serialVersionUID = 6492895228968704482L;

	@Id
	@Column(name = "PARAM_ID", nullable = false, length = 20)
	private String paramId;

	@Column(name = "PARAM_INT_VAL", nullable = true, length = 10)
	private java.lang.Integer paramIntValue;

	@Column(name = "PARAM_CHAR_VAL", nullable = true, length = 250)
	private String paramCharValue;

	@Column(name = "PARAM_DT_VAL", nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date paramDateValue;

	@Column(name = "DESCRIPTION", nullable = true, length = 200)
	private String description;

	@Column(name = "UPDUSER", nullable = true, length = 15)
	private String updateUser;

	@Column(name = "UPDTIME", nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Timestamp updateTime;

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Parameter)) {
			return false;
		}

		Parameter other = (Parameter) o;

		boolean equal = getParamId().equals(other.getParamId());
		return equal;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getParamId()).toHashCode();
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public java.lang.Integer getParamIntValue() {
		return paramIntValue;
	}

	public void setParamIntValue(java.lang.Integer paramIntValue) {
		this.paramIntValue = paramIntValue;
	}

	public String getParamCharValue() {
		return paramCharValue;
	}

	public void setParamCharValue(String paramCharValue) {
		this.paramCharValue = paramCharValue;
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

	public Date getParamDateValue() {
		return paramDateValue;
	}

	public void setParamDateValue(Date paramDateValue) {
		this.paramDateValue = paramDateValue;
	}
}
