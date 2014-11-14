package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_FUNC_DETAIL")
public class FunctionDetail implements Serializable {

	private static final long serialVersionUID = 1304050892964556699L;
	@EmbeddedId
	private FunctionDetailId id;

	

	public FunctionDetailId getId() {
		return this.id;
	}

	public void setId(final FunctionDetailId id) {
		this.id = id;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || !(object instanceof FunctionDetail)) {
			return false;
		}

		FunctionDetail other = (FunctionDetail) object;
		boolean equal=new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
		return equal;
	}

	@Embeddable
	public static class FunctionDetailId implements java.io.Serializable {

		private static final long serialVersionUID = -7872433157244601388L;

		@Column(name = "FUNC_ID", unique = false, nullable = false, length = 20)
		private java.lang.String functionId;

		@Column(name = "URL", unique = false, nullable = false, length = 500)
		private java.lang.String url;

		public FunctionDetailId() {

		}

		public FunctionDetailId(final java.lang.String functionId, final java.lang.String url) {
			this.functionId = functionId;
			this.url = url;
		}

		public java.lang.String getFunctionId() {
			return this.functionId;
		}

		public void setFunctionId(final java.lang.String functionId) {
			this.functionId = functionId;
		}

		public java.lang.String getUrl() {
			return this.url;
		}

		public void setUrl(final java.lang.String url) {
			this.url = url;
		}

		public int hashCode() {
			return new HashCodeBuilder().append(this.getFunctionId()).append(this.getUrl()).toHashCode();
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			if (object == null || !(object instanceof FunctionDetailId)) {
				return false;
			}

			FunctionDetailId other = (FunctionDetailId) object;
			boolean equal=new EqualsBuilder().append(this.getFunctionId(), other.getFunctionId()).append(this.getUrl(), other.getUrl()).isEquals();
			return equal;
		}
	}
}
