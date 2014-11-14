package hk.com.mtr.pcis.dao.entity.sa;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "PCI_MENU")
public class Menu implements Serializable {

	private static final long serialVersionUID = 6764981810631096187L;

	@Id
	@Column(name = "MENU_ID", unique = false, nullable = false, length = 20)
	private java.lang.String menuId;

	@Column(name = "PARENT_MENU_ID", unique = false, nullable = true, length = 20)
	private java.lang.String parentMenuId;

	@Column(name = "RES_KEY", unique = false, nullable = true, length = 50)
	private java.lang.String resourceKey;

	@Column(name = "LOCATION", unique = false, nullable = true, length = 500)
	private java.lang.String location;

	@Column(name = "DISPLAY_SEQ", unique = false, nullable = true, length = 5)
	private java.lang.Integer displaySeq;

	public java.lang.String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}

	public java.lang.String getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(java.lang.String value) {
		this.parentMenuId = value;
	}

	public java.lang.String getResourceKey() {
		return this.resourceKey;
	}

	public void setResourceKey(java.lang.String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public java.lang.String getLocation() {
		return this.location;
	}

	public void setLocation(java.lang.String value) {
		this.location = value;
	}

	public java.lang.Integer getDisplaySeq() {
		return this.displaySeq;
	}

	public void setDisplaySeq(java.lang.Integer value) {
		this.displaySeq = value;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMenuId()).hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if ((this == object))
			return true;
		if (object == null || !(object instanceof Menu)) {
			return false;
		}
		Menu other = (Menu) object;
		boolean equal = new EqualsBuilder().append(this.getMenuId(), other.getMenuId()).isEquals();
		return equal;
	}
}
