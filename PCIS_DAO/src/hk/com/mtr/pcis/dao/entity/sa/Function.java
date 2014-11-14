package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_FUNC")
public class Function implements Serializable {

	private static final long serialVersionUID = -3385640247151691545L;

	@Id
	@Column(name = "FUNC_ID", unique = false, nullable = false, length = 20)
	private java.lang.String functionId;

	@Column(name = "MENU_ID", unique = false, nullable = false, length = 20)
	private java.lang.String menuId;

	@Column(name = "RES_KEY", unique = false, nullable = true, length = 100)
	private java.lang.String resKey;

	@Column(name = "DISPLAY_SEQ", unique = false, nullable = true, length = 5)
	private java.lang.Integer displaySeq;

	public java.lang.String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(final java.lang.String menuId) {
		this.menuId = menuId;
	}

	public java.lang.String getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(final java.lang.String functionId) {
		this.functionId = functionId;
	}

	public java.lang.String getResKey() {
		return this.resKey;
	}

	public void setResKey(final java.lang.String resKey) {
		this.resKey = resKey;
	}

	public java.lang.Integer getDisplaySeq() {
		return this.displaySeq;
	}

	public void setDisplaySeq(final java.lang.Integer displaySeq) {
		this.displaySeq = displaySeq;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFunctionId()).hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof Function)) {
			return false;
		}
		Function other = (Function) object;
		boolean equal = new EqualsBuilder().append(this.getFunctionId(), other.getFunctionId()).isEquals();
		return equal;
	}
}
